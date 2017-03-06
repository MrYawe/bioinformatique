import models.CDSResult;
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
        CDSResult result = new CDSResult();
        //XlsExport.exportDinucleotides(workbook.getSheetAt(2), phase0, phase1);
        XlsExport.exportExcelFile(workbook, "test");
    }

}
