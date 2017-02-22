import models.CDS;
import org.junit.Test;

/**
 * Created by mourse on 20/02/17.
 */
public class CDSTest {
    @Test
    public void formatTest() throws Exception {
        CDS.format("     CDS             join(26906..26970,35005..35024,35807..36160,36799..36945,                     37880..38202,38683..38856)");
    }
    @Test
    public void listInstructTest() throws Exception{
        CDS.processCDS("complement(join(26906..26970,35005..35024,35807..36160,36799..36945,37880..38202,38683..38856))");
        CDS.processCDS("complement(26906..26970,35005..35024,35807..36160,36799..36945,37880..38202,38683..38856)");
        CDS.processCDS("join(26906..26970,35005..35024,35807..36160,36799..36945,37880..38202,38683..38856)");
        CDS.processCDS("26906..26970");
    }
}