import models.CDSResult;
import models.SumResults;
import models.TreatFile;
import models.XlsExport;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
        try
        {
            /// UNE FOIS PAR ORGANISME ///
            XSSFWorkbook workbook = XlsExport.getWorkbookFromTemplate();
            SumResults sumResults = new SumResults();
            sumResults.setOrganism("test");

            /// UNE FOIS POUR CHAQUE NC D'UN ORGANISME ///
            InputStream is = new FileInputStream("tests/test.gbk");
            CDSResult results = TreatFile.processFile(is);
            results.setLocusName("NC_12");
            results.setOrganism("test");
            XlsExport.exportStats(workbook, results, sumResults);


            /// UNE FOIS PAR ORGANISME ///
            String path = System.getProperty("user.dir") + File.separator + "results" + File.separator + sumResults.getOrganism() + ".xlsx";
            XlsExport.exportExcelFile(workbook, sumResults, path);
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
    }

}
