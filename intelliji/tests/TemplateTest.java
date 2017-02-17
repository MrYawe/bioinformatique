import models.XlsExport;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class TemplateTest
{
	/**
	 * Fonction de test de la template
	 * @param args Arguments du programme
	 */
    public static void main(String[] args)
    {
        XSSFWorkbook workbook = XlsExport.getWorkbookFromTemplate();
        XlsExport.createNewSheet(workbook, "Sum_chromosomes");
        XlsExport.exportTrinucleotides(workbook);
        XlsExport.exportExcelFile(workbook, "test.xlsx");
    }

}
