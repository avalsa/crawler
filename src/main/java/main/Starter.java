package main;

import crawler.CrawlerImpl;

/**
 * Created by sl on 04.07.17.
 */
public class Starter {
    public static void main(String ...args){
        if (args.length == 0){
            System.out.println("Usage:\n"+
            "Args:\n"+
                    "\tString - URL\n"+
                    "\tNumber - Limit of pages\n"+
            "Result: Folders & files with html pages");
            return;
        }
        new CrawlerImpl(Integer.parseInt(args[1])).load(args[0]);
    }
}
