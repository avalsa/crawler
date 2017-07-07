import org.junit.Assert;
import org.junit.Test;
import utils.ResultSet;
import utils.Saver;
import utils.SaverImpl;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

/**
 * Created by sl on 06.07.17.
 */
public class SaverTest {

    @Test
    public void test1() {
        Saver s = new SaverImpl();
        ResultSet rs = new ResultSet("a/b/c", new ArrayList<String>(),"code ");
        s.save(rs);
        while (!s.isDone())
        {
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Assert.assertEquals(s.isDone(), true);
    }

}
