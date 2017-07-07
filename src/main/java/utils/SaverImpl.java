package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by sl on 05.07.17.
 */
public class SaverImpl implements Saver {
    private Thread worker;
    class Worker implements Runnable{
        private ResultSet rs;

        public Worker(ResultSet rs) {
            this.rs = rs;
        }

        @Override
        public void run() {
            Path pathToFile = Paths.get(rs.makePath());

            try {
                if (pathToFile.getParent() != null)
                    Files.createDirectories(pathToFile.getParent());
                Files.createFile(pathToFile);
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(pathToFile.toFile())))
            {
                bufferedWriter.write(rs.getHtml());
            }
            catch (IOException e){
                e.printStackTrace();
            }

            //open, close file, save
        }

    }

    @Override
    public boolean isDone(){
        return worker != null && !worker.isAlive();
    }

    @Override
    public void save(ResultSet rs) {
        worker = new Thread(new Worker(rs));
        worker.start();
    }
}
