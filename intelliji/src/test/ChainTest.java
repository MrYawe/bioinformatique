import Class.Chain;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;


public class ChainTest {
    @Test
    public void testLength() throws Exception
    {
        Assert.assertTrue(Chain.length("abc"));
        Assert.assertFalse(Chain.length("abcd"));
        Assert.assertFalse(Chain.length("a"));
        Assert.assertFalse(Chain.length("ab"));
        Assert.assertTrue(Chain.length("abcdef"));
        Assert.assertTrue(Chain.length("abcdefghi"));
        Assert.assertFalse(Chain.length("abcdefghij"));
    }
    @Test
    public void testchain() throws  Exception
    {
        Assert.assertTrue(Chain.chainValid("ATC"));
        Assert.assertFalse(Chain.chainValid("UTC"));
        Assert.assertFalse(Chain.chainValid("UVC"));
        Assert.assertFalse(Chain.chainValid("Uxy"));
        Assert.assertTrue(Chain.chainValid("agt"));


    }
    @Test
    public void testStartEnd() throws  Exception
    {
        Assert.assertTrue(Chain.startEnd("ATTTAG"));/*RAS normal*/
        Assert.assertTrue(Chain.startEnd("TTGCATATTTGA"));/*RAS normal*/
        Assert.assertFalse(Chain.startEnd("TGGACCACCTAG"));/* Random codon init*/

    }

}