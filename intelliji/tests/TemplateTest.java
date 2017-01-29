import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;


public class TemplateTest
{

    public static void main(String[] args)
    {
        String filename;
        if (System.getProperty("os.name").toLowerCase().contains("win"))
        {
            filename = "C:/Users/" + System.getProperty("user.name");
        }
        else
        {
            filename = "/home/" + System.getProperty("user.name");
        }

        String templateName = filename + "/template.xlsx";
        String outputName = filename + "/output.xlsx";

        try
        {
            // We load the template and open a new Workbook from that
            InputStream inp = new FileInputStream(templateName);
            XSSFWorkbook wb = (XSSFWorkbook) WorkbookFactory.create(inp);

            // Clone the first sheet and rename it
            XSSFSheet sheet = wb.cloneSheet(0, "newSheetName");

            // We edit the new sheet
            Row r = sheet.createRow(10);
            Cell c1 = r.createCell(0, CellType.STRING);
            c1.setCellValue("Edit worked !");

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
