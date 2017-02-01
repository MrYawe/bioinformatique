import models.XlsExport;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class TemplateTest
{
	/**
	 * Main function
	 * @param args
	 */
    public static void main(String[] args)
    {
        XSSFWorkbook workbook = XlsExport.getWorkbookFromTemplate();
        XlsExport.exportTrinucleotides(workbook);
        XlsExport.exportExcelFile(workbook, "test.xlsx");
    }

}
