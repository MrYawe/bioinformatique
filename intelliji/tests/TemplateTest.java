import statistics.CDSResult;
import statistics.SumResults;
import models.TreatFile;
import statistics.XlsExport;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


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
            File file = new File("tests/test.gbk");
            CDSResult results = TreatFile.processFile(file);
            results.setLocusName("NC_12");
            results.setOrganism("test");
            XlsExport.exportStats(workbook, results, sumResults);


            /// UNE FOIS PAR ORGANISME ///
            String path = System.getProperty("user.dir") + File.separator + "results";
            XlsExport.exportExcelFile(workbook, sumResults, path);
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
    }

}
