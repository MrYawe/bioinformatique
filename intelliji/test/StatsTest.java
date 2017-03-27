import models.CDSResult;
import models.TreatFile;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * Class to test statistics.
 */
public class StatsTest
{
    private CDSResult results = null;

    private void init()
    {
        try
        {
            FileInputStream is = new FileInputStream("tests/test7.gbk");
            this.results = TreatFile.processFile(is);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void basicsTest()
    {
        if (this.results == null)
        {
            init();
        }
        assertEquals("Number of CDS has to be equal to 1", 1, this.results.getNbCDS());
        assertEquals("Number of invalid CDS has to be equal to 0", 0, this.results.getNbInvalidCDS());
        assertEquals("Organism has to be equal to Acropora digitifera", "Acropora digitifera", this.results.getOrganism());
        assertEquals("Locus name has to be equal to NW_015441057", "NW_015441057", this.results.getLocusName());
    }

    @Test
    public void triPhase0Test()
    {
        if (this.results == null)
        {
            init();
        }
        HashMap<String, Integer> triPhase0 = this.results.getTriPhase0();
        assertEquals("Number of AAA has to be equal to 6", 6, (long)triPhase0.get("AAA"));
        assertEquals("Number of AAC has to be equal to 1", 1, (long)triPhase0.get("AAC"));
        assertEquals("Number of AAG has to be equal to 0", 0, (long)triPhase0.get("AAG"));
        assertEquals("Number of AAT has to be equal to 4", 4, (long)triPhase0.get("AAT"));
        assertEquals("Number of ACA has to be equal to 5", 5, (long)triPhase0.get("ACA"));
        assertEquals("Number of ACC has to be equal to 1", 1, (long)triPhase0.get("ACC"));
        assertEquals("Number of ACG has to be equal to 1", 1, (long)triPhase0.get("ACG"));
        assertEquals("Number of ACT has to be equal to 1", 1, (long)triPhase0.get("ACT"));
        assertEquals("Number of AGA has to be equal to 0", 0, (long)triPhase0.get("AGA"));
        assertEquals("Number of AGC has to be equal to 5", 5, (long)triPhase0.get("AGC"));
        assertEquals("Number of AGG has to be equal to 1", 1, (long)triPhase0.get("AGG"));
        assertEquals("Number of AGT has to be equal to 3", 3, (long)triPhase0.get("AGT"));
        assertEquals("Number of ATA has to be equal to 3", 3, (long)triPhase0.get("ATA"));
        assertEquals("Number of ATC has to be equal to 4", 4, (long)triPhase0.get("ATC"));
        assertEquals("Number of ATG has to be equal to 1", 1, (long)triPhase0.get("ATG"));
        assertEquals("Number of ATT has to be equal to 5", 5, (long)triPhase0.get("ATT"));
        assertEquals("Number of CAA has to be equal to 3", 3, (long)triPhase0.get("CAA"));
        assertEquals("Number of CAC has to be equal to 1", 1, (long)triPhase0.get("CAC"));
        assertEquals("Number of CAG has to be equal to 2", 2, (long)triPhase0.get("CAG"));
        assertEquals("Number of CAT has to be equal to 0", 0, (long)triPhase0.get("CAT"));
        assertEquals("Number of CCA has to be equal to 4", 4, (long)triPhase0.get("CCA"));
        assertEquals("Number of CCC has to be equal to 0", 0, (long)triPhase0.get("CCC"));
        assertEquals("Number of CCG has to be equal to 5", 5, (long)triPhase0.get("CCG"));
        assertEquals("Number of CCT has to be equal to 4", 4, (long)triPhase0.get("CCT"));
        assertEquals("Number of CGA has to be equal to 2", 2, (long)triPhase0.get("CGA"));
        assertEquals("Number of CGC has to be equal to 0", 0, (long)triPhase0.get("CGC"));
        assertEquals("Number of CGG has to be equal to 0", 0, (long)triPhase0.get("CGG"));
        assertEquals("Number of CGT has to be equal to 0", 0, (long)triPhase0.get("CGT"));
        assertEquals("Number of CTA has to be equal to 0", 0, (long)triPhase0.get("CTA"));
        assertEquals("Number of CTC has to be equal to 1", 1, (long)triPhase0.get("CTC"));
        assertEquals("Number of CTG has to be equal to 3", 3, (long)triPhase0.get("CTG"));
        assertEquals("Number of CTT has to be equal to 3", 3, (long)triPhase0.get("CTT"));
        assertEquals("Number of GAA has to be equal to 8", 8, (long)triPhase0.get("GAA"));
        assertEquals("Number of GAC has to be equal to 5", 5, (long)triPhase0.get("GAC"));
        assertEquals("Number of GAG has to be equal to 5", 5, (long)triPhase0.get("GAG"));
        assertEquals("Number of GAT has to be equal to 8", 8, (long)triPhase0.get("GAT"));
        assertEquals("Number of GCA has to be equal to 3", 3, (long)triPhase0.get("GCA"));
        assertEquals("Number of GCC has to be equal to 1", 1, (long)triPhase0.get("GCC"));
        assertEquals("Number of GCG has to be equal to 0", 0, (long)triPhase0.get("GCG"));
        assertEquals("Number of GCT has to be equal to 4", 4, (long)triPhase0.get("GCT"));
        assertEquals("Number of GGA has to be equal to 0", 0, (long)triPhase0.get("GGA"));
        assertEquals("Number of GGC has to be equal to 0", 0, (long)triPhase0.get("GGC"));
        assertEquals("Number of GGG has to be equal to 1", 1, (long)triPhase0.get("GGG"));
        assertEquals("Number of GGT has to be equal to 0", 0, (long)triPhase0.get("GGT"));
        assertEquals("Number of GTA has to be equal to 2", 2, (long)triPhase0.get("GTA"));
        assertEquals("Number of GTC has to be equal to 2", 2, (long)triPhase0.get("GTC"));
        assertEquals("Number of GTG has to be equal to 3", 3, (long)triPhase0.get("GTG"));
        assertEquals("Number of GTT has to be equal to 4", 4, (long)triPhase0.get("GTT"));
        assertEquals("Number of TAA has to be equal to 0", 0, (long)triPhase0.get("TAA"));
        assertEquals("Number of TAC has to be equal to 0", 0, (long)triPhase0.get("TAC"));
        assertEquals("Number of TAG has to be equal to 0", 0, (long)triPhase0.get("TAG"));
        assertEquals("Number of TAT has to be equal to 2", 2, (long)triPhase0.get("TAT"));
        assertEquals("Number of TCA has to be equal to 2", 2, (long)triPhase0.get("TCA"));
        assertEquals("Number of TCC has to be equal to 3", 3, (long)triPhase0.get("TCC"));
        assertEquals("Number of TCG has to be equal to 1", 1, (long)triPhase0.get("TCG"));
        assertEquals("Number of TCT has to be equal to 2", 2, (long)triPhase0.get("TCT"));
        assertEquals("Number of TGA has to be equal to 0", 0, (long)triPhase0.get("TGA"));
        assertEquals("Number of TGC has to be equal to 1", 1, (long)triPhase0.get("TGC"));
        assertEquals("Number of TGG has to be equal to 0", 0, (long)triPhase0.get("TGG"));
        assertEquals("Number of TGT has to be equal to 1", 1, (long)triPhase0.get("TGT"));
        assertEquals("Number of TTA has to be equal to 2", 2, (long)triPhase0.get("TTA"));
        assertEquals("Number of TTC has to be equal to 0", 0, (long)triPhase0.get("TTC"));
        assertEquals("Number of TTG has to be equal to 2", 2, (long)triPhase0.get("TTG"));
        assertEquals("Number of TTT has to be equal to 3", 3, (long)triPhase0.get("TTT"));

        int sum = 0;
        for (int l : triPhase0.values())
        {
            sum += l;
        }
        assertEquals("Number of trinucleotides in phase 0 has to be 134", 134, sum);
    }

    @Test
    public void triPhase1Test()
    {
        if (this.results == null)
        {
            init();
        }
        HashMap<String, Integer> triPhase1 = this.results.getTriPhase1();
        assertEquals("Number of AAA has to be equal to 3", 3, (long)triPhase1.get("AAA"));
        assertEquals("Number of AAC has to be equal to 4", 4, (long)triPhase1.get("AAC"));
        assertEquals("Number of AAG has to be equal to 9", 9, (long)triPhase1.get("AAG"));
        assertEquals("Number of AAT has to be equal to 1", 1, (long)triPhase1.get("AAT"));
        assertEquals("Number of ACA has to be equal to 3", 3, (long)triPhase1.get("ACA"));
        assertEquals("Number of ACC has to be equal to 1", 1, (long)triPhase1.get("ACC"));
        assertEquals("Number of ACG has to be equal to 2", 2, (long)triPhase1.get("ACG"));
        assertEquals("Number of ACT has to be equal to 1", 1, (long)triPhase1.get("ACT"));
        assertEquals("Number of AGA has to be equal to 3", 3, (long)triPhase1.get("AGA"));
        assertEquals("Number of AGC has to be equal to 2", 2, (long)triPhase1.get("AGC"));
        assertEquals("Number of AGG has to be equal to 2", 2, (long)triPhase1.get("AGG"));
        assertEquals("Number of AGT has to be equal to 0", 0, (long)triPhase1.get("AGT"));
        assertEquals("Number of ATA has to be equal to 4", 4, (long)triPhase1.get("ATA"));
        assertEquals("Number of ATC has to be equal to 2", 2, (long)triPhase1.get("ATC"));
        assertEquals("Number of ATG has to be equal to 8", 8, (long)triPhase1.get("ATG"));
        assertEquals("Number of ATT has to be equal to 0", 0, (long)triPhase1.get("ATT"));
        assertEquals("Number of CAA has to be equal to 6", 6, (long)triPhase1.get("CAA"));
        assertEquals("Number of CAC has to be equal to 1", 1, (long)triPhase1.get("CAC"));
        assertEquals("Number of CAG has to be equal to 4", 4, (long)triPhase1.get("CAG"));
        assertEquals("Number of CAT has to be equal to 3", 3, (long)triPhase1.get("CAT"));
        assertEquals("Number of CCA has to be equal to 3", 3, (long)triPhase1.get("CCA"));
        assertEquals("Number of CCC has to be equal to 0", 0, (long)triPhase1.get("CCC"));
        assertEquals("Number of CCG has to be equal to 1", 1, (long)triPhase1.get("CCG"));
        assertEquals("Number of CCT has to be equal to 1", 1, (long)triPhase1.get("CCT"));
        assertEquals("Number of CGA has to be equal to 2", 2, (long)triPhase1.get("CGA"));
        assertEquals("Number of CGC has to be equal to 3", 3, (long)triPhase1.get("CGC"));
        assertEquals("Number of CGG has to be equal to 0", 0, (long)triPhase1.get("CGG"));
        assertEquals("Number of CGT has to be equal to 2", 2, (long)triPhase1.get("CGT"));
        assertEquals("Number of CTA has to be equal to 0", 0, (long)triPhase1.get("CTA"));
        assertEquals("Number of CTC has to be equal to 5", 5, (long)triPhase1.get("CTC"));
        assertEquals("Number of CTG has to be equal to 3", 3, (long)triPhase1.get("CTG"));
        assertEquals("Number of CTT has to be equal to 3", 3, (long)triPhase1.get("CTT"));
        assertEquals("Number of GAA has to be equal to 0", 0, (long)triPhase1.get("GAA"));
        assertEquals("Number of GAC has to be equal to 0", 0, (long)triPhase1.get("GAC"));
        assertEquals("Number of GAG has to be equal to 1", 1, (long)triPhase1.get("GAG"));
        assertEquals("Number of GAT has to be equal to 1", 1, (long)triPhase1.get("GAT"));
        assertEquals("Number of GCA has to be equal to 3", 3, (long)triPhase1.get("GCA"));
        assertEquals("Number of GCC has to be equal to 2", 2, (long)triPhase1.get("GCC"));
        assertEquals("Number of GCG has to be equal to 0", 0, (long)triPhase1.get("GCG"));
        assertEquals("Number of GCT has to be equal to 1", 1, (long)triPhase1.get("GCT"));
        assertEquals("Number of GGA has to be equal to 0", 0, (long)triPhase1.get("GGA"));
        assertEquals("Number of GGC has to be equal to 0", 0, (long)triPhase1.get("GGC"));
        assertEquals("Number of GGG has to be equal to 2", 2, (long)triPhase1.get("GGG"));
        assertEquals("Number of GGT has to be equal to 0", 0, (long)triPhase1.get("GGT"));
        assertEquals("Number of GTA has to be equal to 0", 0, (long)triPhase1.get("GTA"));
        assertEquals("Number of GTC has to be equal to 1", 1, (long)triPhase1.get("GTC"));
        assertEquals("Number of GTG has to be equal to 2", 2, (long)triPhase1.get("GTG"));
        assertEquals("Number of GTT has to be equal to 1", 1, (long)triPhase1.get("GTT"));
        assertEquals("Number of TAA has to be equal to 3", 3, (long)triPhase1.get("TAA"));
        assertEquals("Number of TAC has to be equal to 1", 1, (long)triPhase1.get("TAC"));
        assertEquals("Number of TAG has to be equal to 2", 2, (long)triPhase1.get("TAG"));
        assertEquals("Number of TAT has to be equal to 1", 1, (long)triPhase1.get("TAT"));
        assertEquals("Number of TCA has to be equal to 4", 4, (long)triPhase1.get("TCA"));
        assertEquals("Number of TCC has to be equal to 1", 1, (long)triPhase1.get("TCC"));
        assertEquals("Number of TCG has to be equal to 0", 0, (long)triPhase1.get("TCG"));
        assertEquals("Number of TCT has to be equal to 2", 2, (long)triPhase1.get("TCT"));
        assertEquals("Number of TGA has to be equal to 3", 3, (long)triPhase1.get("TGA"));
        assertEquals("Number of TGC has to be equal to 1", 1, (long)triPhase1.get("TGC"));
        assertEquals("Number of TGG has to be equal to 3", 3, (long)triPhase1.get("TGG"));
        assertEquals("Number of TGT has to be equal to 2", 2, (long)triPhase1.get("TGT"));
        assertEquals("Number of TTA has to be equal to 3", 3, (long)triPhase1.get("TTA"));
        assertEquals("Number of TTC has to be equal to 4", 4, (long)triPhase1.get("TTC"));
        assertEquals("Number of TTG has to be equal to 7", 7, (long)triPhase1.get("TTG"));
        assertEquals("Number of TTT has to be equal to 1", 1, (long)triPhase1.get("TTT"));

        int sum = 0;
        for (int l : triPhase1.values())
        {
            sum += l;
        }
        assertEquals("Number of trinucleotides in phase 1 has to be 134", 134, sum);
    }

    @Test
    public void triPhase2Test()
    {
        if (this.results == null)
        {
            init();
        }
        HashMap<String, Integer> triPhase2 = this.results.getTriPhase2();
        assertEquals("Number of AAA has to be equal to 3", 3, (long)triPhase2.get("AAA"));
        assertEquals("Number of AAC has to be equal to 3", 3, (long)triPhase2.get("AAC"));
        assertEquals("Number of AAG has to be equal to 2", 2, (long)triPhase2.get("AAG"));
        assertEquals("Number of AAT has to be equal to 4", 4, (long)triPhase2.get("AAT"));
        assertEquals("Number of ACA has to be equal to 2", 2, (long)triPhase2.get("ACA"));
        assertEquals("Number of ACC has to be equal to 2", 2, (long)triPhase2.get("ACC"));
        assertEquals("Number of ACG has to be equal to 0", 0, (long)triPhase2.get("ACG"));
        assertEquals("Number of ACT has to be equal to 2", 2, (long)triPhase2.get("ACT"));
        assertEquals("Number of AGA has to be equal to 9", 9, (long)triPhase2.get("AGA"));
        assertEquals("Number of AGC has to be equal to 2", 2, (long)triPhase2.get("AGC"));
        assertEquals("Number of AGG has to be equal to 0", 0, (long)triPhase2.get("AGG"));
        assertEquals("Number of AGT has to be equal to 5", 5, (long)triPhase2.get("AGT"));
        assertEquals("Number of ATA has to be equal to 0", 0, (long)triPhase2.get("ATA"));
        assertEquals("Number of ATC has to be equal to 3", 3, (long)triPhase2.get("ATC"));
        assertEquals("Number of ATG has to be equal to 2", 2, (long)triPhase2.get("ATG"));
        assertEquals("Number of ATT has to be equal to 1", 1, (long)triPhase2.get("ATT"));
        assertEquals("Number of CAA has to be equal to 1", 1, (long)triPhase2.get("CAA"));
        assertEquals("Number of CAC has to be equal to 3", 3, (long)triPhase2.get("CAC"));
        assertEquals("Number of CAG has to be equal to 5", 5, (long)triPhase2.get("CAG"));
        assertEquals("Number of CAT has to be equal to 4", 4, (long)triPhase2.get("CAT"));
        assertEquals("Number of CCA has to be equal to 1", 1, (long)triPhase2.get("CCA"));
        assertEquals("Number of CCC has to be equal to 1", 1, (long)triPhase2.get("CCC"));
        assertEquals("Number of CCG has to be equal to 0", 0, (long)triPhase2.get("CCG"));
        assertEquals("Number of CCT has to be equal to 2", 2, (long)triPhase2.get("CCT"));
        assertEquals("Number of CGA has to be equal to 3", 3, (long)triPhase2.get("CGA"));
        assertEquals("Number of CGC has to be equal to 0", 0, (long)triPhase2.get("CGC"));
        assertEquals("Number of CGG has to be equal to 0", 0, (long)triPhase2.get("CGG"));
        assertEquals("Number of CGT has to be equal to 0", 0, (long)triPhase2.get("CGT"));
        assertEquals("Number of CTA has to be equal to 0", 0, (long)triPhase2.get("CTA"));
        assertEquals("Number of CTC has to be equal to 2", 2, (long)triPhase2.get("CTC"));
        assertEquals("Number of CTG has to be equal to 1", 1, (long)triPhase2.get("CTG"));
        assertEquals("Number of CTT has to be equal to 2", 2, (long)triPhase2.get("CTT"));
        assertEquals("Number of GAA has to be equal to 5", 5, (long)triPhase2.get("GAA"));
        assertEquals("Number of GAC has to be equal to 2", 2, (long)triPhase2.get("GAC"));
        assertEquals("Number of GAG has to be equal to 1", 1, (long)triPhase2.get("GAG"));
        assertEquals("Number of GAT has to be equal to 0", 0, (long)triPhase2.get("GAT"));
        assertEquals("Number of GCA has to be equal to 1", 1, (long)triPhase2.get("GCA"));
        assertEquals("Number of GCC has to be equal to 4", 4, (long)triPhase2.get("GCC"));
        assertEquals("Number of GCG has to be equal to 1", 1, (long)triPhase2.get("GCG"));
        assertEquals("Number of GCT has to be equal to 0", 0, (long)triPhase2.get("GCT"));
        assertEquals("Number of GGA has to be equal to 5", 5, (long)triPhase2.get("GGA"));
        assertEquals("Number of GGC has to be equal to 0", 0, (long)triPhase2.get("GGC"));
        assertEquals("Number of GGG has to be equal to 1", 1, (long)triPhase2.get("GGG"));
        assertEquals("Number of GGT has to be equal to 1", 1, (long)triPhase2.get("GGT"));
        assertEquals("Number of GTA has to be equal to 0", 0, (long)triPhase2.get("GTA"));
        assertEquals("Number of GTC has to be equal to 1", 1, (long)triPhase2.get("GTC"));
        assertEquals("Number of GTG has to be equal to 0", 0, (long)triPhase2.get("GTG"));
        assertEquals("Number of GTT has to be equal to 3", 3, (long)triPhase2.get("GTT"));
        assertEquals("Number of TAA has to be equal to 2", 2, (long)triPhase2.get("TAA"));
        assertEquals("Number of TAC has to be equal to 0", 0, (long)triPhase2.get("TAC"));
        assertEquals("Number of TAG has to be equal to 1", 1, (long)triPhase2.get("TAG"));
        assertEquals("Number of TAT has to be equal to 4", 4, (long)triPhase2.get("TAT"));
        assertEquals("Number of TCA has to be equal to 2", 2, (long)triPhase2.get("TCA"));
        assertEquals("Number of TCC has to be equal to 6", 6, (long)triPhase2.get("TCC"));
        assertEquals("Number of TCG has to be equal to 1", 1, (long)triPhase2.get("TCG"));
        assertEquals("Number of TCT has to be equal to 3", 3, (long)triPhase2.get("TCT"));
        assertEquals("Number of TGA has to be equal to 9", 9, (long)triPhase2.get("TGA"));
        assertEquals("Number of TGC has to be equal to 6", 6, (long)triPhase2.get("TGC"));
        assertEquals("Number of TGG has to be equal to 0", 0, (long)triPhase2.get("TGG"));
        assertEquals("Number of TGT has to be equal to 5", 5, (long)triPhase2.get("TGT"));
        assertEquals("Number of TTA has to be equal to 2", 2, (long)triPhase2.get("TTA"));
        assertEquals("Number of TTC has to be equal to 2", 2, (long)triPhase2.get("TTC"));
        assertEquals("Number of TTG has to be equal to 0", 0, (long)triPhase2.get("TTG"));
        assertEquals("Number of TTT has to be equal to 1", 1, (long)triPhase2.get("TTT"));

        int sum = 0;
        for (int l : triPhase2.values())
        {
            sum += l;
        }
        assertEquals("Number of trinucleotides in phase 2 has to be 134", 134, sum);
    }

    @Test
    public void diPhase0Test()
    {
        if (this.results == null)
        {
            init();
        }
        HashMap<String, Integer> diPhase0 = this.results.getDiPhase0();
        assertEquals("Number of AA has to be equal to 20", 20, (long)diPhase0.get("AA"));
        assertEquals("Number of AC has to be equal to 9", 9, (long)diPhase0.get("AC"));
        assertEquals("Number of AG has to be equal to 14", 14, (long)diPhase0.get("AG"));
        assertEquals("Number of AT has to be equal to 18", 18, (long)diPhase0.get("AT"));
        assertEquals("Number of CA has to be equal to 16", 16, (long)diPhase0.get("CA"));
        assertEquals("Number of CC has to be equal to 12", 12, (long)diPhase0.get("CC"));
        assertEquals("Number of CG has to be equal to 6", 6, (long)diPhase0.get("CG"));
        assertEquals("Number of CT has to be equal to 12", 12, (long)diPhase0.get("CT"));
        assertEquals("Number of GA has to be equal to 18", 18, (long)diPhase0.get("GA"));
        assertEquals("Number of GC has to be equal to 9", 9, (long)diPhase0.get("GC"));
        assertEquals("Number of GG has to be equal to 5", 5, (long)diPhase0.get("GG"));
        assertEquals("Number of GT has to be equal to 11", 11, (long)diPhase0.get("GT"));
        assertEquals("Number of TA has to be equal to 11", 11, (long)diPhase0.get("TA"));
        assertEquals("Number of TC has to be equal to 14", 14, (long)diPhase0.get("TC"));
        assertEquals("Number of TG has to be equal to 17", 17, (long)diPhase0.get("TG"));
        assertEquals("Number of TT has to be equal to 9", 9, (long)diPhase0.get("TT"));

        int sum = 0;
        for (int l : diPhase0.values())
        {
            sum += l;
        }
        assertEquals("Number of dinucleotides in phase 0 has to be 201", 201, sum);
    }

    @Test
    public void diPhase1Test()
    {
        if (this.results == null)
        {
            init();
        }
        HashMap<String, Integer> diPhase1 = this.results.getDiPhase1();
        assertEquals("Number of AA has to be equal to 20", 20, (long)diPhase1.get("AA"));
        assertEquals("Number of AC has to be equal to 12", 12, (long)diPhase1.get("AC"));
        assertEquals("Number of AG has to be equal to 18", 18, (long)diPhase1.get("AG"));
        assertEquals("Number of AT has to be equal to 15", 15, (long)diPhase1.get("AT"));
        assertEquals("Number of CA has to be equal to 17", 17, (long)diPhase1.get("CA"));
        assertEquals("Number of CC has to be equal to 10", 10, (long)diPhase1.get("CC"));
        assertEquals("Number of CG has to be equal to 6", 6, (long)diPhase1.get("CG"));
        assertEquals("Number of CT has to be equal to 11", 11, (long)diPhase1.get("CT"));
        assertEquals("Number of GA has to be equal to 18", 18, (long)diPhase1.get("GA"));
        assertEquals("Number of GC has to be equal to 11", 11, (long)diPhase1.get("GC"));
        assertEquals("Number of GG has to be equal to 5", 5, (long)diPhase1.get("GG"));
        assertEquals("Number of GT has to be equal to 8", 8, (long)diPhase1.get("GT"));
        assertEquals("Number of TA has to be equal to 5", 5, (long)diPhase1.get("TA"));
        assertEquals("Number of TC has to be equal to 13", 13, (long)diPhase1.get("TC"));
        assertEquals("Number of TG has to be equal to 14", 14, (long)diPhase1.get("TG"));
        assertEquals("Number of TT has to be equal to 18", 18, (long)diPhase1.get("TT"));

        int sum = 0;
        for (int l : diPhase1.values())
        {
            sum += l;
        }
        assertEquals("Number of dinucleotides in phase 1 has to be 201", 201, sum);
    }
}
