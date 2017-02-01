package models;

import config.Config;
import config.ConfigManager;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Classe permettant de gérer l'export Xls des statistiques
 * @author brice
 * @version 1.0
 */
public class XlsExport
{
    /**
     * Permet de récupérer un workbook à partir de la template des résultats
     * @return Un workbook correspondant à un fichier excel
     */
    public static XSSFWorkbook getWorkbookFromTemplate()
    {
        Config config = ConfigManager.getConfig();
        String resDir = config.getResFolder();
        String templateName = resDir + "/template.xlsx";

        XSSFWorkbook workbook = null;

        try
        {
            InputStream inp = new FileInputStream(templateName);
            workbook = (XSSFWorkbook) WorkbookFactory.create(inp);
            inp.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return workbook;
    }

    /**
     * Permet de créer le fichier excel contenant les statistiques des trinucléotides
     * @param workbook Fichier excel à remplir
     */
    public static void exportTrinucleotides(XSSFWorkbook workbook)
    {
        Sheet s = workbook.getSheetAt(0);
        Row r = s.getRow(13);
        r.getCell(1).setCellValue(12);
        s.getRow(16).getCell(1).setCellValue(37);
    }

    /**
     * Permet d'exporter le fichier excel correspondant au workbook passé en paramètre
     * @param workbook Workbook à exporter
     * @param outputName Nom du fichier excel à créer
     */
    public static void exportExcelFile(XSSFWorkbook workbook, String outputName)
    {
        Config config = ConfigManager.getConfig();
        try
        {
            FileOutputStream fileOut = new FileOutputStream(config.getResFolder() + "/" + outputName);
            XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
