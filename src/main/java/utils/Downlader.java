package utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sl on 05.07.17.
 */
public class Downlader {

    @Nullable
    @Contract("null -> null")
    public static ResultSet download(String url, String regexp){
        if (url == null)
            return null;
        try {
            Document document = Jsoup.connect(url).get();
            Elements links = document.select("a[href]");
            List<String> innerlinks = new ArrayList<String>();
            Pattern p = Pattern.compile(regexp);
            for (Element element : links){
                String link = element.attr("href");
                if (p.matcher(link).matches())
                    innerlinks.add(link);
            }
            return new ResultSet(url, innerlinks, document.html());
        }
        catch (Exception e) {
            return null;
        }
    }
}
