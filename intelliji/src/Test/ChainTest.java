import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by paoli on 24/01/2017.
 */

//TODO: Fix Package dependency for Junit on ChainTest to be on Test and use the Chain Class
//TODO : Provide more test case

public class ChainTest {
    @Test
    public void testLength() throws Exception
    {
        Assert.assertTrue(Chain.length("abc"));
        Assert.assertFalse(Chain.length("abcd"));
        Assert.assertFalse(Chain.length("a"));
        Assert.assertFalse(Chain.length("ab"));
    }
    @Test
    public void testchain() throws  Exception
    {
        Assert.assertTrue(Chain.chainValid("ATC"));
        Assert.assertFalse(Chain.chainValid("UTC"));
    }
    @Test
    public void testStartEnd() throws  Exception
    {
        Assert.assertTrue(Chain.chainValid("ATTATT"));
        Assert.assertTrue(Chain.chainValid("ATGTAA"));
    }

}