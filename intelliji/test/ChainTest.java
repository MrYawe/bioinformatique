import models.Chain;
import org.junit.Assert;
import org.junit.Test;


public class ChainTest {
    @Test
    public void testLength() throws Exception
    {
        Assert.assertTrue(Chain.isLengthMultipleOf3("abc"));
        Assert.assertFalse(Chain.isLengthMultipleOf3("abcd"));
        Assert.assertFalse(Chain.isLengthMultipleOf3("a"));
        Assert.assertFalse(Chain.isLengthMultipleOf3("ab"));
        Assert.assertTrue(Chain.isLengthMultipleOf3("abcdef"));
        Assert.assertTrue(Chain.isLengthMultipleOf3("abcdefghi"));
        Assert.assertFalse(Chain.isLengthMultipleOf3("abcdefghij"));
    }
    @Test
    public void testchain() throws  Exception
    {
        Assert.assertTrue(Chain.isChainValid("ATC"));
        Assert.assertFalse(Chain.isChainValid("UTC"));
        Assert.assertFalse(Chain.isChainValid("UVC"));
        Assert.assertFalse(Chain.isChainValid("Uxy"));
        Assert.assertTrue(Chain.isChainValid("agt"));


    }
    @Test
    public void testStartEnd() throws  Exception
    {
        Assert.assertTrue(Chain.startEnd("ATTTAG"));/*RAS normal*/
        Assert.assertTrue(Chain.startEnd("TTGCATATTTGA"));/*RAS normal*/
        Assert.assertFalse(Chain.startEnd("TGGACCACCTAG"));/* Random codon init*/

    }

    @Test
    public void testComplement() throws Exception
    {
        Assert.assertEquals(Chain.complement("TTGCATATTTGA"), "TCAAATATGCAA");
        Assert.assertEquals(Chain.complement("ACGTATCGGTT"), "AACCGATACGT");

    }
}