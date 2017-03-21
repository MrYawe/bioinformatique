import models.CDSResult;
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
            CDSResult speciesResults = new CDSResult();

            /// UNE FOIS POUR CHAQUE CHROMOSOME D'UN ORGANISME ///
            InputStream is = new FileInputStream("tests/sequence.gbk");
            CDSResult results = TreatFile.processFile(is);
            results.setChromosomeName("NC_12");
            results.setSpecies("test");
            XlsExport.exportStats(workbook, results, speciesResults);


            /// UNE FOIS PAR ORGANISME ///
            speciesResults.setSpecies("test");
            XlsExport.exportExcelFile(workbook, speciesResults);
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
    }

}
