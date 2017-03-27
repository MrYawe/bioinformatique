import models.CDS;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by mourse on 20/02/17.
 */
public class CDSTest {
    @Test
    public void formatTest() throws Exception {

        String testCDS1 = CDS.format("     CDS             join(26906..26970,35005..35024,35807..36160,36799..36945,                     37880..38202,38683..38856)");
        Assert.assertEquals(testCDS1, "join(26906..26970,35005..35024,35807..36160,36799..36945,37880..38202,38683..38856)");

        String testCDS2 = CDS.format("  CDS     (1..1)  ");
        Assert.assertEquals(testCDS2,"(1..1)");
    }
    @Test
    public void listInstructTest() throws Exception{

        //test normal
        ArrayList<CDS> testCDS1 = CDS.processCDS("complement(join(26969..35004,35005..35024,35807..36160,36799..36945,37880..38202,38683..38856))");
        Assert.assertEquals(testCDS1.size(),6);
        Assert.assertEquals(testCDS1.get(0).getStart(),26969);
        Assert.assertEquals(testCDS1.get(0).getEnd(),35004);
        Assert.assertEquals(testCDS1.get(5).getStart(),38683);
        Assert.assertEquals(testCDS1.get(5).getEnd(),38856);

        //test normal avec l'appel à la normalisation du format
        ArrayList<CDS> testCDS2 = CDS.processCDS(CDS.format("     CDS             join(26901..26902,35005..35024,35807..36160,36799..36945,                     37880..38202,38600..40090)"));
        Assert.assertEquals(testCDS2.size(), 6);
        Assert.assertEquals(testCDS2.get(0).getStart(),26901);
        Assert.assertEquals(testCDS2.get(0).getEnd(),26902);
        Assert.assertEquals(testCDS2.get(5).getStart(),38600);
        Assert.assertEquals(testCDS2.get(5).getEnd(),40090);

        //test sans mot clé
        ArrayList<CDS> testCDS3 = CDS.processCDS("26906..26970");
        Assert.assertEquals(testCDS3.size(),1);
        Assert.assertEquals(testCDS3.get(0).getStart(),26906);
        Assert.assertEquals(testCDS3.get(0).getEnd(),26970);

        //test avec erreur sur les coordonnées
        ArrayList<CDS> testCDS4 = CDS.processCDS("26906..26904");
        Assert.assertEquals(testCDS4.size(),0);

        //test avec les coordonnées qui se chevauchent
        ArrayList<CDS> testCDS5 = CDS.processCDS("join(35005..35807,35024..36160)");
        Assert.assertEquals(testCDS5.size(),0);

        //test avec erreur sur le format ('.' au lieu de '..')
        ArrayList<CDS> testCDS6 = CDS.processCDS("join(35005.35807,36024..36160)");
        Assert.assertEquals(testCDS6.size(),0);

        //test avec erreur sur le format (chiffre....chiffre)
        ArrayList<CDS> testCDS7 = CDS.processCDS("join(35005..3580736024..36160)");
        Assert.assertEquals(testCDS7.size(),0);

        //test avec paranthèse manquante à la fin
        ArrayList<CDS> testCDS8 = CDS.processCDS("join(35005.35807,36024..36160");
        Assert.assertEquals(testCDS8.size(),0);

        //test avec paranthèse manquante à la fin
        ArrayList<CDS> testCDS9 = CDS.processCDS("join(35005..35807)");
        Assert.assertEquals(testCDS9.size(),1);
    }
}