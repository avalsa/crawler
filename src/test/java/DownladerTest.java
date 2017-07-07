import org.junit.Assert;
import org.junit.Test;
import utils.Downlader;
import utils.ResultSet;

/**
 * Created by sl on 06.07.17.
 */
public class DownladerTest {

    @Test
    public void test(){
        ResultSet rs = Downlader.download("https://ya.ru", ".*");
        Assert.assertNotNull(rs.getUrl());
        Assert.assertNotNull(rs.getHtml());
        Assert.assertNotNull(rs.getLinks());
    }
}
