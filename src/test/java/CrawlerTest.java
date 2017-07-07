import crawler.Crawler;
import crawler.CrawlerImpl;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by sl on 06.07.17.
 */
public class CrawlerTest {
    @Test
    public void test(){
        try {
            Crawler c = new CrawlerImpl(20);
            c.load("https://yandex.ru");
        }
        catch (Exception e){
            Assert.fail("Mistake occured");
        }

    }
}
