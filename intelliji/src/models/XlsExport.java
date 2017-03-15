package models;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Classe permettant de gérer l'export Xls des statistiques
 * @author brice
 * @version 1.0
 */
public class XlsExport
{
    /**
     * Indice de la ligne de début des stats
     */
    private final static int START_ROW = 6;
    /**
     * Indice de la colonne de début des stats pour les trinucléotides
     */
    private final static int TRINUCLEOTIDES_START_COLUMN = 0;
    /**
     * Indice de la colonne de début des stats pour les dinucléotides
     */
    private final static int DINUCLEOTIDES_START_COLUMN = 11;

	/**
     * Permet de créer le dossier qui contiendra tous les résultats
     */
    public static void createResultsDirectory()
    {
        new File(System.getProperty("user.dir") + "/results").mkdir();
    }

    /**
     * Permet de récupérer un workbook à partir de la template des résultats
     * @return Un workbook correspondant à un fichier excel
     */
    public static XSSFWorkbook getWorkbookFromTemplate()
    {

        InputStream is = XlsExport.class.getClass().getResourceAsStream("/template.xlsx");

        XSSFWorkbook workbook = null;

        try
		{
            workbook = (XSSFWorkbook) WorkbookFactory.create(is);
            is.close();
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }

        return workbook;
    }

    /**
     * Permet de créer un nouvel onglet
     * @param workbook Fichier excel pour lequel on veut créer un nouvel onglet
     * @param name Nom du nouvel onglet
     */
    private static void createNewSheet(XSSFWorkbook workbook, String name)
    {
        workbook.cloneSheet(1, name);
    }

    /**
     * Permet d'exporter les statistiques dans un onglet d'un fichier Excel
     * @param workbook Fichie excel dans lequel on souhaite insérer les statistiques
     * @param results Objet contenant les résultats des statistiques effectuées
     */
    public static void exportStats(XSSFWorkbook workbook, CDSResult results)
    {
        XlsExport.createNewSheet(workbook, results.getChromosomeName());
        Sheet sheet = workbook.getSheet(results.getChromosomeName());
        sheet.getRow(0).getCell(1).setCellValue(results.getSpecies());
        sheet.getRow(1).getCell(1).setCellValue(results.getNbCDS());
        sheet.getRow(3).getCell(1).setCellValue(results.getNbInvalidCDS());
        for (int i = START_ROW; i < START_ROW + results.getTriPhase0().size(); i++)
        {
            Row r = sheet.getRow(i);
            String key = r.getCell(TRINUCLEOTIDES_START_COLUMN).getStringCellValue();
            r.getCell(TRINUCLEOTIDES_START_COLUMN + 1).setCellValue(results.getTriPhase0().get(key));
            r.getCell(TRINUCLEOTIDES_START_COLUMN + 3).setCellValue(results.getTriPhase1().get(key));
            r.getCell(TRINUCLEOTIDES_START_COLUMN + 5).setCellValue(results.getTriPhase2().get(key));
            if (i < START_ROW + results.getDiPhase0().size())
            {
                key = r.getCell(DINUCLEOTIDES_START_COLUMN).getStringCellValue();
                r.getCell(DINUCLEOTIDES_START_COLUMN + 1).setCellValue(results.getDiPhase0().get(key));
                r.getCell(DINUCLEOTIDES_START_COLUMN + 3).setCellValue(results.getDiPhase1().get(key));
            }
        }
    }

    /**
     * Permet d'exporter le fichier excel correspondant au workbook passé en paramètre
     * @param workbook Workbook à exporter
     * @param results Objet contenant les résultats des statistiques effectuées
     */
    public static void exportExcelFile(XSSFWorkbook workbook, CDSResult results)
    {
        String path = System.getProperty("user.dir") + File.separator + "results" + File.separator + results.getSpecies() + ".xlsx";
        try
        {
            FileOutputStream fileOut = new FileOutputStream(path);
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-YYYY HH:mm");
			workbook.getSheetAt(0).getRow(2).createCell(1).setCellValue(results.getSpecies());
			workbook.getSheetAt(0).getRow(4).createCell(1).setCellValue(format.format(cal.getTime()));
            XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
            workbook.write(fileOut);
            System.out.println("XLSX generated: " + path);
            fileOut.close();
            workbook.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
