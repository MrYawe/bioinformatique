import models.CDSResult;
import models.TreatFile;
import models.XlsExport;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.FileNotFoundException;
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
            java.io.File file = new java.io.File("tests/test7.gbk");
            CDSResult results = TreatFile.processFile(file);
            results.setChromosomeName("NC_12");
            results.setSpecies("test");
            // Appelée une seule fois
            XSSFWorkbook workbook = XlsExport.getWorkbookFromTemplate();
            // Appelée pour chaque fichier récupéré de la base
            XlsExport.exportStats(workbook, results);
            // Appelée une seule fois
            XlsExport.exportExcelFile(workbook, results);
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
    }

}
