import models.XlsExport;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.HashMap;


public class TemplateTest
{
	/**
	 * Fonction de test de la template
	 * @param args Arguments du programme
	 */
    public static void main(String[] args)
    {
        XlsExport.createResultsDirectory();
        XSSFWorkbook workbook = XlsExport.getWorkbookFromTemplate();
        XlsExport.createNewSheet(workbook, "Sum_chromosomes");
        HashMap<String, Integer> phase0 = new HashMap<>();
        phase0.put("AA", 3);
        phase0.put("AC", 10);
        phase0.put("AG", 7);
        phase0.put("AT", 8);
        phase0.put("CA", 6);
        phase0.put("CC", 15);
        phase0.put("CG", 13);
        phase0.put("CT", 12);
        phase0.put("GA", 9);
        phase0.put("GG", 22);
        phase0.put("GT", 26);
        phase0.put("TA", 17);
        phase0.put("TC", 8);
        phase0.put("TG", 10);
        phase0.put("GC", 25);
        phase0.put("TT", 16);
        HashMap<String, Integer> phase1 = new HashMap<>();
        phase1.put("AA", 15);
        phase1.put("AC", 7);
        phase1.put("AG", 12);
        phase1.put("AT", 9);
        phase1.put("CA", 11);
        phase1.put("CC", 10);
        phase1.put("CG", 16);
        phase1.put("CT", 14);
        phase1.put("GA", 12);
        phase1.put("GC", 17);
        phase1.put("GG", 25);
        phase1.put("GT", 20);
        phase1.put("TA", 13);
        phase1.put("TC", 11);
        phase1.put("TG", 9);
        phase1.put("TT", 6);
        XlsExport.exportDinucleotides(workbook.getSheetAt(2), phase0, phase1);
        XlsExport.exportExcelFile(workbook, "test.xlsx");
    }

}
