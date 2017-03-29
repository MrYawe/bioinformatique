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
        Assert.assertEquals("join(26906..26970,35005..35024,35807..36160,36799..36945,37880..38202,38683..38856)", testCDS1);

        String testCDS2 = CDS.format("  CDS     (1..1)  ");
        Assert.assertEquals("(1..1)", testCDS2);
    }
    @Test
    public void listInstructTest() throws Exception{

        //test normal
        ArrayList<CDS> testCDS1 = CDS.processCDS("complement(join(26969..35004,35005..35024,35807..36160,36799..36945,37880..38202,38683..38856))");
        Assert.assertEquals(6, testCDS1.size());
        Assert.assertEquals(26969, testCDS1.get(0).getStart());
        Assert.assertEquals(35004, testCDS1.get(0).getEnd());
        Assert.assertEquals(38683, testCDS1.get(5).getStart());
        Assert.assertEquals(38856, testCDS1.get(5).getEnd());

        //test normal avec l'appel à la normalisation du format
        ArrayList<CDS> testCDS2 = CDS.processCDS(CDS.format("     CDS             join(26901..26902,35005..35024,35807..36160,36799..36945,                     37880..38202,38600..40090)"));
        Assert.assertEquals(6, testCDS2.size());
        Assert.assertEquals(26901, testCDS2.get(0).getStart());
        Assert.assertEquals(26902, testCDS2.get(0).getEnd());
        Assert.assertEquals(38600, testCDS2.get(5).getStart());
        Assert.assertEquals(40090, testCDS2.get(5).getEnd());

        //test sans mot clé
        ArrayList<CDS> testCDS3 = CDS.processCDS("26906..26970");
        Assert.assertEquals(1, testCDS3.size());
        Assert.assertEquals(26906, testCDS3.get(0).getStart());
        Assert.assertEquals(26970, testCDS3.get(0).getEnd());

        //test avec erreur sur les coordonnées
        ArrayList<CDS> testCDS4 = CDS.processCDS("26906..26904");
        Assert.assertEquals(0, testCDS4.size());

        //test avec les coordonnées qui se chevauchent
        ArrayList<CDS> testCDS5 = CDS.processCDS("join(35005..35807,35024..36160)");
        Assert.assertEquals(0, testCDS5.size());

        //test avec erreur sur le format ('.' au lieu de '..')
        ArrayList<CDS> testCDS6 = CDS.processCDS("join(35005.35807,36024..36160)");
        Assert.assertEquals(0, testCDS6.size());

        //test avec erreur sur le format (chiffre....chiffre)
        ArrayList<CDS> testCDS7 = CDS.processCDS("join(35005..3580736024..36160)");
        Assert.assertEquals(0, testCDS7.size());

        //test avec paranthèse manquante à la fin
        ArrayList<CDS> testCDS8 = CDS.processCDS("join(35005.35807,36024..36160");
        Assert.assertEquals(0, testCDS8.size());

        //test avec paranthèse manquante à la fin
        ArrayList<CDS> testCDS9 = CDS.processCDS("join(35005..35807)");
        Assert.assertEquals(1, testCDS9.size());
    }

    @Test
    public void identicalCDSTest()
    {
        ArrayList<ArrayList<CDS>> listCDS = new ArrayList<>();

        ArrayList<CDS> testCDS = CDS.processCDS ("join(26901..26902,35005..35024,35807..36160,36799..36945)");
        Assert.assertTrue(CDS.isUnique(testCDS, listCDS));
        listCDS.add(testCDS);

        ArrayList<CDS> testCDS2 = CDS.processCDS("join(26901..26902,35005..35024,35807..36160,36799..36946)");
        Assert.assertTrue(CDS.isUnique(testCDS2, listCDS));
        listCDS.add(testCDS2);

        ArrayList<CDS> testCDS3 = CDS.processCDS("join(26901..26902,35005..35024,35807..36160,36799..36945,36975..36985)");
        Assert.assertTrue(CDS.isUnique(testCDS3, listCDS));
        listCDS.add(testCDS3);

        ArrayList<CDS> testCDS4 = CDS.processCDS("join(26901..26902,35005..35024,35807..36160,36799..36945)");
        Assert.assertFalse(CDS.isUnique(testCDS4, listCDS));

        ArrayList<CDS> testCDS5 = CDS.processCDS("join(26901..26902,35005..35024,35807..36160)");
        Assert.assertTrue(CDS.isUnique(testCDS5, listCDS));
        listCDS.add(testCDS5);

        ArrayList<CDS> testCDS6 = CDS.processCDS("complement(join(26901..26902,35005..35024,35807..36160,36799..36945,36975..36985))");
        Assert.assertTrue(CDS.isUnique(testCDS6, listCDS));
        listCDS.add(testCDS6);

        ArrayList<CDS> testCDS7 = CDS.processCDS("26901..26902");
        Assert.assertTrue(CDS.isUnique(testCDS7, listCDS));
        listCDS.add(testCDS7);

        ArrayList<CDS> testCDS8 = CDS.processCDS("join(26901..26902)");
        Assert.assertFalse(CDS.isUnique(testCDS8, listCDS));
        listCDS.add(testCDS8);
    }
}