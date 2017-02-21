import models.CDS;
import org.junit.Test;

import static org.junit.Assert.*;

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
        CDS.listInstruct("complement(join(26906..26970,35005..35024,35807..36160,36799..36945,37880..38202,38683..38856))");
        CDS.listInstruct("complement(26906..26970,35005..35024,35807..36160,36799..36945,37880..38202,38683..38856)");
        CDS.listInstruct("join(26906..26970,35005..35024,35807..36160,36799..36945,37880..38202,38683..38856)");
        CDS.listInstruct("26906..26970");
    }
}