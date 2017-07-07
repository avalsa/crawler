package crawler;

import utils.Downlader;
import utils.ResultSet;
import utils.Saver;
import utils.SaverImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import static java.lang.Thread.interrupted;
import static java.lang.Thread.sleep;

/**
 * Created by sl on 05.07.17.
 */
public class CrawlerImpl implements Crawler {

    private final int limitFiles;
    private static final int limitLoaders = 20;
    private AtomicInteger cntReady;

    private final List<Thread> loaders = Collections.synchronizedList(new ArrayList<Thread>());

    private final List<Saver> savers = Collections.synchronizedList(new ArrayList<Saver>());

    private final List<String> toDoURLs = Collections.synchronizedList(new ArrayList<String>());

    public CrawlerImpl(int limitFiles) {
        cntReady = new AtomicInteger(0);
        this.limitFiles = limitFiles;
    }

    class Loader implements Runnable {

        private ResultSet result;
        private String regexp;
        private String url;

        public Loader(String url, String regexp) {
            this.regexp = regexp;
            this.url = url;
        }

        @Override
        public void run() {
            if (interrupted()) return;
            result = Downlader.download(url, regexp);
            if (result == null || interrupted()) {
                return;
            }
            cntReady.incrementAndGet();

            Saver s = new SaverImpl();
            if (interrupted()) return;
            savers.add(s);
            s.save(result);

            Pattern p = Pattern.compile("^/.*$");
            for (String link: result.getLinks()){
                String toAdd = p.matcher(link).matches() ? url.concat(link) : link;
                if (interrupted()) return;
                toDoURLs.add(toAdd);
            }
        }
    }

    @Override
    public void load(String url) {
        toDoURLs.add(url);
        List<String> doneURLs = new ArrayList<String>();
        while(cntReady.get() < limitFiles && (!loaders.isEmpty() || !toDoURLs.isEmpty() || !savers.isEmpty())) {
            if (!toDoURLs.isEmpty() && loaders.size() < limitLoaders) {
                String curURL;
                synchronized (toDoURLs) {
                    curURL = toDoURLs.remove(toDoURLs.size() - 1);
                }
                if (!doneURLs.contains(curURL)) {
                    doneURLs.add(curURL);
                    Thread t = new Thread(new Loader(curURL, "^(" + url + "|/[^/]).*$"));
                    loaders.add(t);
                    t.start();
                }
            }
            loaders.removeIf(thread -> !thread.isAlive());
            savers.removeIf(Saver::isDone);
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        loaders.forEach(Thread::interrupt);
    }
}
