import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;


public class TemplateTest
{
	/**
	 * Main function
	 * @param args
	 */
    public static void main(String[] args)
    {
        String resDirectory = System.getProperty("user.dir") + "/res";

        String templateName = resDirectory + "/template.xlsx";
        String outputName = resDirectory + "/output.xlsx";

        try
        {
            // We load the template and open a new Workbook from that
            InputStream inp = new FileInputStream(templateName);
            XSSFWorkbook wb = (XSSFWorkbook) WorkbookFactory.create(inp);

            // Clone the first sheet and rename it
            XSSFSheet sheet = wb.cloneSheet(0, "newSheetName");

            // We edit the new sheet
            Row r = sheet.getRow(10);
            r.getCell(1).setCellValue(125);
			r.getCell(3).setCellValue(12);
			r.getCell(5).setCellValue(26);

			r = sheet.getRow(15);
			r.getCell(1).setCellValue(17);
			r.getCell(3).setCellValue(35);
			r.getCell(5).setCellValue(67);

			XSSFFormulaEvaluator.evaluateAllFormulaCells(wb);

            // We write the file with the output name
            FileOutputStream fileOut = new FileOutputStream(outputName);
            wb.write(fileOut);
            fileOut.close();
        }
        catch (Exception e)
        {
            System.out.println( e.getMessage() );
        }
    }

}
