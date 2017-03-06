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
        CDSResult results = new CDSResult();
        results.setChromosomeName("test");
        results.setSpecies("abc");
        XlsExport.createExcelFileWithStats(results);
    }

}
