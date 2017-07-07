import org.junit.Assert;
import org.junit.Test;
import utils.ResultSet;

/**
 * Created by sl on 06.07.17.
 */
public class ResultSetTest {
    @Test
    public void test1(){
        ResultSet rs = new ResultSet("a/b/c", null, "code");
        Assert.assertEquals(rs.makePath(), "a/b/c.html");
    }

    @Test
    public void test2(){
        ResultSet rs = new ResultSet("a/b/c.html", null, "code");
        Assert.assertEquals(rs.makePath(), "a/b/c.html");
    }

    @Test
    public void test3(){
        ResultSet rs = new ResultSet("http://a/b/c", null, "code");
        Assert.assertEquals(rs.makePath(), "a/b/c.html");
    }
}
