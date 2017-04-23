package statistics;

import config.Config;
import config.ConfigManager;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import view.UIManager;

import java.io.File;
import java.io.FileInputStream;
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
    private final static int START_ROW = 12;
    /**
     * Indice de la colonne de début des stats pour les trinucléotides
     */
    private final static int TRINUCLEOTIDES_START_COLUMN = 0;
    /**
     * Indice de la colonne de début des stats pour les dinucléotides
     */
    private final static int DINUCLEOTIDES_START_COLUMN = 11;

    /**
     * Nombre de colonnes de la template
     */
    private final static int COLUMNS_NUMBER = 16;

    /**
     * Nom de l'onglet contenant la somme des stats des chromosomes
     */
    private final static String SUM_CHROMOSOMES_SHEET = "Sum_Chromosomes";

    /**
     * Nom de l'onglet contenant la somme des stats des mitochondrions
     */
    private final static String SUM_MITOCHONDRIONS_SHEET = "Sum_Mitochondrions";

    /**
     * Nom de l'onglet contenant la somme des stats des ADN
     */
    private final static String SUM_DNA_SHEET = "Sum_DNA";

    /**
     * Nom de l'onglet contenant la somme des stats des plasmides
     */
    private final static String SUM_PLASMIDS_SHEET = "Sum_Plasmids";

    /**
     * Nom de l'onglet contenant la somme des stats des plastes
     */
    private final static String SUM_PLASTS_SHEET = "Sum_Plastids";

    /**
     * Nom de l'onglet contenant la somme des stats des liens génétiques
     */
    private final static String SUM_LINKAGES_SHEET = "Sum_Linkages";

    /**
     * Permet de créer le dossier qui contiendra tous les résultats
     */
    public static void createResultsDirectory()
    {
        new File(ConfigManager.getConfig().getResultsFolder()).mkdir();
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
            workbook.cloneSheet(1, SUM_MITOCHONDRIONS_SHEET);
            workbook.cloneSheet(1, SUM_DNA_SHEET);
            workbook.cloneSheet(1, SUM_PLASMIDS_SHEET);
            workbook.cloneSheet(1, SUM_PLASTS_SHEET);
            workbook.cloneSheet(1, SUM_LINKAGES_SHEET);
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
     * Permet d'incrémenter les compteurs de CDS des sommes
     * @param results Objet contenant les stats du locus actuel
     * @param sum Objet contenant les compteurs à incrémenter
     */
    private static void incrementSumCDSNumbers(CDSResult results, CDSResult sum)
    {
        sum.setNbMalformedCDS(sum.getNbMalformedCDS() + results.getNbMalformedCDS());
        sum.setNbInvalidCDS(sum.getNbInvalidCDS() + results.getNbInvalidCDS());
        sum.setNbIdenticalCDS(sum.getNbIdenticalCDS() + results.getNbIdenticalCDS());
        sum.setNbCDS(sum.getNbCDS() + results.getNbCDS());
    }

    /**
     * Permet d'incrémenter les stats des trinucléotides
     * @param sum Objet contenant les HashMap des trinucléotides à incrémenter
     * @param key Clé du HashMap à incrémenter
     * @param nbTriPhase0 Nombre de trinucléotides en phase 0
     * @param nbTriPhase1 Nombre de trinucléotides en phase 1
     * @param nbTriPhase2 Nombre de trinucléotides en phase 2
     */
    private static void incrementSumTrinucleotides(CDSResult sum, String key, int nbTriPhase0, int nbTriPhase1, int nbTriPhase2)
    {
        sum.getTriPhase0().put(key, sum.getTriPhase0().get(key) + nbTriPhase0);
        sum.getTriPhase1().put(key, sum.getTriPhase1().get(key) + nbTriPhase1);
        sum.getTriPhase2().put(key, sum.getTriPhase2().get(key) + nbTriPhase2);
    }

    /**
     * Permet d'incrémenter les stats des phases préférentielles
     * @param sum Objet contenant les HashMap des phases préférentielles à incrémenter
     * @param key Clé du HashMap à incrémenter
     * @param nbTriPrefPhase0 Nombre de CDS dans lequel la phase 0 est la phase préférentielle de la clé
     * @param nbTriPrefPhase1 Nombre de CDS dans lequel la phase 1 est la phase préférentielle de la clé
     * @param nbTriPrefPhase2 Nombre de CDS dans lequel la phase 2 est la phase préférentielle de la clé
     */
    private static void incrementSumPhasePref(CDSResult sum, String key, int nbTriPrefPhase0, int nbTriPrefPhase1, int nbTriPrefPhase2)
    {
        sum.getTriPrefPhase0().put(key, sum.getTriPrefPhase0().get(key) + nbTriPrefPhase0);
        sum.getTriPrefPhase1().put(key, sum.getTriPrefPhase1().get(key) + nbTriPrefPhase1);
        sum.getTriPrefPhase2().put(key, sum.getTriPrefPhase2().get(key) + nbTriPrefPhase2);
    }

    /**
     * Permet d'incrémenter les stats des dinucléotides
     * @param sum Objet contenant les HashMap des dinucléotides à incrémenter
     * @param key Clé du HashMap à incrémenter
     * @param nbDiPhase0 Nombre de dinucléotides en phase 0
     * @param nbDiPhase1 Nombre de dinucléotides en phase 1
     */
    private static void incrementSumDinucleotides(CDSResult sum, String key, int nbDiPhase0, int nbDiPhase1)
    {
        sum.getDiPhase0().put(key, sum.getDiPhase0().get(key) + nbDiPhase0);
        sum.getDiPhase1().put(key, sum.getDiPhase1().get(key) + nbDiPhase1);
    }

    /**
     * Permet d'exporter les statistiques dans un onglet d'un fichier Excel
     * @param workbook Fichier excel dans lequel on souhaite insérer les statistiques
     * @param results Objet contenant les résultats des statistiques effectuées
     * @param sumResults Object contenant les résultats des sommes des statistiques
     */
    public static void exportStats(XSSFWorkbook workbook, CDSResult results, SumResults sumResults)
    {
		if (results.getLocusName().length() > 31)
		{
			results.setLocusName(results.getLocusName().substring(0, 30));
		}
        XlsExport.createNewSheet(workbook, results.getLocusName());
        Sheet sheet = workbook.getSheet(results.getLocusName());
        sheet.getRow(0).getCell(1).setCellValue(results.getOrganism());
        sheet.getRow(1).getCell(1).setCellValue(results.getNbCDS());
        sheet.getRow(2).getCell(1).setCellValue(results.getNbMalformedCDS());
        sheet.getRow(3).getCell(1).setCellValue(results.getNbIdenticalCDS());
        sheet.getRow(5).getCell(1).setCellValue(results.getNbInvalidCDS());
        CDSResult.Type type = results.getType();
        switch (type)
        {
            case CHROMOSOME:
                sumResults.setNbChromosomes(sumResults.getNbChromosomes() + 1);
                incrementSumCDSNumbers(results, sumResults.getSumChromosomes());
                break;

            case MITOCHONDRION:
                sumResults.setNbMitochondrions(sumResults.getNbMitochondrions() + 1);
                incrementSumCDSNumbers(results, sumResults.getSumMitochondrions());
                break;

            case DNA:
                sumResults.setNbDNA(sumResults.getNbDNA() + 1);
                incrementSumCDSNumbers(results, sumResults.getSumDNA());
                break;

            case PLASMID:
                sumResults.setNbPlasmids(sumResults.getNbPlasmids() + 1);
                incrementSumCDSNumbers(results, sumResults.getSumPlasmids());
                break;

            case PLAST:
                sumResults.setNbPlasts(sumResults.getNbPlasts() + 1);
                incrementSumCDSNumbers(results, sumResults.getSumPlasts());
                break;

            default:
                sumResults.setNbLinkages(sumResults.getNbLinkages() + 1);
                incrementSumCDSNumbers(results, sumResults.getSumLinkages());
                break;
        }

        for (int i = START_ROW; i < START_ROW + results.getTriPhase0().size(); i++)
        {
            Row r = sheet.getRow(i);
            String key = r.getCell(TRINUCLEOTIDES_START_COLUMN).getStringCellValue();
            int nbTriPhase0 = results.getTriPhase0().get(key);
            int nbTriPhase1 = results.getTriPhase1().get(key);
            int nbTriPhase2 = results.getTriPhase2().get(key);
            int nbTriPrefPhase0 = results.getTriPrefPhase0().get(key);
            int nbTriPrefPhase1 = results.getTriPrefPhase1().get(key);
            int nbTriPrefPhase2 = results.getTriPrefPhase2().get(key);
            switch (type)
            {
                case CHROMOSOME:
                    incrementSumTrinucleotides(sumResults.getSumChromosomes(), key, nbTriPhase0, nbTriPhase1, nbTriPhase2);
                    incrementSumPhasePref(sumResults.getSumChromosomes(), key, nbTriPrefPhase0, nbTriPrefPhase1, nbTriPrefPhase2);
                    break;

                case MITOCHONDRION:
                    incrementSumTrinucleotides(sumResults.getSumMitochondrions(), key, nbTriPhase0, nbTriPhase1, nbTriPhase2);
                    incrementSumPhasePref(sumResults.getSumMitochondrions(), key, nbTriPrefPhase0, nbTriPrefPhase1, nbTriPrefPhase2);
                    break;

                case DNA:
                    incrementSumTrinucleotides(sumResults.getSumDNA(), key, nbTriPhase0, nbTriPhase1, nbTriPhase2);
                    incrementSumPhasePref(sumResults.getSumDNA(), key, nbTriPrefPhase0, nbTriPrefPhase1, nbTriPrefPhase2);
                    break;

                case PLASMID:
                    incrementSumTrinucleotides(sumResults.getSumPlasmids(), key, nbTriPhase0, nbTriPhase1, nbTriPhase2);
                    incrementSumPhasePref(sumResults.getSumPlasmids(), key, nbTriPrefPhase0, nbTriPrefPhase1, nbTriPrefPhase2);
                    break;

                case PLAST:
                    incrementSumTrinucleotides(sumResults.getSumPlasts(), key, nbTriPhase0, nbTriPhase1, nbTriPhase2);
                    incrementSumPhasePref(sumResults.getSumPlasts(), key, nbTriPrefPhase0, nbTriPrefPhase1, nbTriPrefPhase2);
                    break;

                default:
                    incrementSumTrinucleotides(sumResults.getSumLinkages(), key, nbTriPhase0, nbTriPhase1, nbTriPhase2);
                    incrementSumPhasePref(sumResults.getSumLinkages(), key, nbTriPrefPhase0, nbTriPrefPhase1, nbTriPrefPhase2);
                    break;
            }
            r.getCell(TRINUCLEOTIDES_START_COLUMN + 1).setCellValue(nbTriPhase0);
            r.getCell(TRINUCLEOTIDES_START_COLUMN + 3).setCellValue(nbTriPhase1);
            r.getCell(TRINUCLEOTIDES_START_COLUMN + 5).setCellValue(nbTriPhase2);

            r.getCell(TRINUCLEOTIDES_START_COLUMN + 7).setCellValue(nbTriPrefPhase0);
            r.getCell(TRINUCLEOTIDES_START_COLUMN + 8).setCellValue(nbTriPrefPhase1);
            r.getCell(TRINUCLEOTIDES_START_COLUMN + 9).setCellValue(nbTriPrefPhase2);

            if (i < START_ROW + results.getDiPhase0().size())
            {
                key = r.getCell(DINUCLEOTIDES_START_COLUMN).getStringCellValue();
                int nbDiPhase0 = results.getDiPhase0().get(key);
                int nbDiPhase1 = results.getDiPhase1().get(key);
                switch (type)
                {
                    case CHROMOSOME:
                        incrementSumDinucleotides(sumResults.getSumChromosomes(), key, nbDiPhase0, nbDiPhase1);
                        break;

                    case MITOCHONDRION:
                        incrementSumDinucleotides(sumResults.getSumMitochondrions(), key, nbDiPhase0, nbDiPhase1);
                        break;

                    case DNA:
                        incrementSumDinucleotides(sumResults.getSumDNA(), key, nbDiPhase0, nbDiPhase1);
                        break;

                    case PLASMID:
                        incrementSumDinucleotides(sumResults.getSumPlasmids(), key, nbDiPhase0, nbDiPhase1);
                        break;

                    case PLAST:
                        incrementSumDinucleotides(sumResults.getSumPlasts(), key, nbDiPhase0, nbDiPhase1);
                        break;

                    default:
                        incrementSumDinucleotides(sumResults.getSumLinkages(), key, nbDiPhase0, nbDiPhase1);
                        break;
                }
                r.getCell(DINUCLEOTIDES_START_COLUMN + 1).setCellValue(nbDiPhase0);
                r.getCell(DINUCLEOTIDES_START_COLUMN + 3).setCellValue(nbDiPhase1);
            }
        }

        for (int i = 0; i < COLUMNS_NUMBER; i++)
		{
			if (i != TRINUCLEOTIDES_START_COLUMN+1 && i != TRINUCLEOTIDES_START_COLUMN+3 && i != TRINUCLEOTIDES_START_COLUMN+5 && i != DINUCLEOTIDES_START_COLUMN+1 && i != DINUCLEOTIDES_START_COLUMN+3)
			{
				sheet.autoSizeColumn(i);
			}
		}
    }

    /**
     * Permet d'exporter les statistiques correspondant à la somme de tous les onglets des chromosomes
     * @param sumResults Objet contenant les statistiques correspondant à la somme de tous les onglets des chromosomes
     * @param sheet Onglet dans lequel on souhaite insérer les stats
     * @param organism Nom de l'organisme traité actuellement
     * @param workbook Fichier excel dans lequel on insère les stats
	 */
    private static void exportSumStats(CDSResult sumResults, Sheet sheet, String organism, XSSFWorkbook workbook)
    {
		if (sumResults.getNbCDS() == 0)
		{
			int index = workbook.getSheetIndex(sheet);
			workbook.removeSheetAt(index);
		}
		else
		{
			sheet.getRow(0).getCell(1).setCellValue(organism);
			sheet.getRow(1).getCell(1).setCellValue(sumResults.getNbCDS());
			sheet.getRow(2).getCell(1).setCellValue(sumResults.getNbMalformedCDS());
			sheet.getRow(3).getCell(1).setCellValue(sumResults.getNbIdenticalCDS());
			sheet.getRow(5).getCell(1).setCellValue(sumResults.getNbInvalidCDS());
			for (int i = START_ROW; i < START_ROW + sumResults.getTriPhase0().size(); i++)
			{
				Row r = sheet.getRow(i);
				String key = r.getCell(TRINUCLEOTIDES_START_COLUMN).getStringCellValue();
				r.getCell(TRINUCLEOTIDES_START_COLUMN + 1).setCellValue(sumResults.getTriPhase0().get(key));
				r.getCell(TRINUCLEOTIDES_START_COLUMN + 3).setCellValue(sumResults.getTriPhase1().get(key));
				r.getCell(TRINUCLEOTIDES_START_COLUMN + 5).setCellValue(sumResults.getTriPhase2().get(key));
				r.getCell(TRINUCLEOTIDES_START_COLUMN + 7).setCellValue(sumResults.getTriPrefPhase0().get(key));
				r.getCell(TRINUCLEOTIDES_START_COLUMN + 8).setCellValue(sumResults.getTriPrefPhase1().get(key));
				r.getCell(TRINUCLEOTIDES_START_COLUMN + 9).setCellValue(sumResults.getTriPrefPhase2().get(key));
				if (i < START_ROW + sumResults.getDiPhase0().size())
				{
					key = r.getCell(DINUCLEOTIDES_START_COLUMN).getStringCellValue();
					r.getCell(DINUCLEOTIDES_START_COLUMN + 1).setCellValue(sumResults.getDiPhase0().get(key));
					r.getCell(DINUCLEOTIDES_START_COLUMN + 3).setCellValue(sumResults.getDiPhase1().get(key));
				}
			}

			for (int i = 0; i < COLUMNS_NUMBER; i++)
			{
				if (i != TRINUCLEOTIDES_START_COLUMN+1 && i != TRINUCLEOTIDES_START_COLUMN+3 && i != TRINUCLEOTIDES_START_COLUMN+5 && i != DINUCLEOTIDES_START_COLUMN+1 && i != DINUCLEOTIDES_START_COLUMN+3)
				{
					sheet.autoSizeColumn(i);
				}
			}
		}
    }

    /**
     * Permet d'exporter les stats sur la feuille "General_Information"
     * @param workbook Fichier Excel dans lequel on souhaite insérer les stats
     * @param sumResults Objet contenant les résultats des sommes
     */
    private static void exportGeneralSheet(XSSFWorkbook workbook, SumResults sumResults)
    {
        Sheet sheet = workbook.getSheetAt(0);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-YYYY HH:mm");

        Row r2 = sheet.getRow(2);
        Row r3 = sheet.getRow(3);
        Row r4 = sheet.getRow(4);
        Row r5 = sheet.getRow(5);
        Row r6 = sheet.getRow(6);
        Row r7 = sheet.getRow(7);
        Row r8 = sheet.getRow(8);
        r2.createCell(1).setCellValue(sumResults.getOrganism());
        r3.createCell(5).setCellValue(sumResults.getNbChromosomes());
        r4.createCell(1).setCellValue(format.format(cal.getTime()));
        r4.createCell(5).setCellValue(sumResults.getNbMitochondrions());
        r5.createCell(5).setCellValue(sumResults.getNbDNA());
        r6.createCell(1).setCellValue(sumResults.getNbOrganisms());
        r6.createCell(5).setCellValue(sumResults.getNbPlasmids());
        r7.createCell(5).setCellValue(sumResults.getNbPlasts());
        r8.createCell(5).setCellValue(sumResults.getNbLinkages());

		sheet.autoSizeColumn(1);
    }

    /**
     * Permet d'exporter le fichier excel correspondant au workbook passé en paramètre
     * @param workbook Workbook à exporter
     * @param sumResults Objet contenant les résultats des statistiques effectuées pour un organisme complet (contient toutes les sommes)
     * @param path Chemin du fichier exporté
     */
    public static void exportExcelFile(XSSFWorkbook workbook, SumResults sumResults, String path)
    {
        exportSumStats(sumResults.getSumChromosomes(), workbook.getSheet(XlsExport.SUM_CHROMOSOMES_SHEET), sumResults.getOrganism(), workbook);
        exportSumStats(sumResults.getSumMitochondrions(), workbook.getSheet(XlsExport.SUM_MITOCHONDRIONS_SHEET), sumResults.getOrganism(), workbook);
        exportSumStats(sumResults.getSumDNA(), workbook.getSheet(XlsExport.SUM_DNA_SHEET), sumResults.getOrganism(), workbook);
        exportSumStats(sumResults.getSumPlasmids(), workbook.getSheet(XlsExport.SUM_PLASMIDS_SHEET), sumResults.getOrganism(), workbook);
        exportSumStats(sumResults.getSumPlasts(), workbook.getSheet(XlsExport.SUM_PLASTS_SHEET), sumResults.getOrganism(), workbook);
        exportSumStats(sumResults.getSumLinkages(), workbook.getSheet(XlsExport.SUM_LINKAGES_SHEET), sumResults.getOrganism(), workbook);

        exportGeneralSheet(workbook, sumResults);

        XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);

        try
        {
            String finalPath = path + File.separator + sumResults.getOrganism() + ".xlsx";
            FileOutputStream fileOut = new FileOutputStream(finalPath);
            workbook.write(fileOut);
            System.out.println("XLSX generated: " + finalPath);
            fileOut.close();
            workbook.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Permet de sommer l'état actuel du CDSResult avec les valeurs contenues dans la feuille
     * @param sheet Feuille d'un workbook dont les valeurs sont à ajouter
     * @param sum Objet qui contient des valeurs et dans lequel on va sommer la feuille du workbook
     */
    private static void addSheetToSumResult(Sheet sheet, CDSResult sum)
    {
        if (sheet != null)
        {
            sum.setNbCDS(sum.getNbCDS() + (int) sheet.getRow(1).getCell(1).getNumericCellValue());
            sum.setNbMalformedCDS(sum.getNbMalformedCDS() + (int) sheet.getRow(2).getCell(1).getNumericCellValue());
            sum.setNbIdenticalCDS(sum.getNbIdenticalCDS() + (int) sheet.getRow(3).getCell(1).getNumericCellValue());
            sum.setNbInvalidCDS(sum.getNbInvalidCDS() + (int) sheet.getRow(5).getCell(1).getNumericCellValue());
            for (int i = START_ROW; i < START_ROW + sum.getTriPhase0().size(); i++)
            {
                Row r = sheet.getRow(i);
                String key = r.getCell(TRINUCLEOTIDES_START_COLUMN).getStringCellValue();

                int nbPhase0 = (int) r.getCell(TRINUCLEOTIDES_START_COLUMN + 1).getNumericCellValue();
                int nbPhase1 = (int) r.getCell(TRINUCLEOTIDES_START_COLUMN + 3).getNumericCellValue();
                int nbPhase2 = (int) r.getCell(TRINUCLEOTIDES_START_COLUMN + 5).getNumericCellValue();
                incrementSumTrinucleotides(sum, key, nbPhase0, nbPhase1, nbPhase2);

                int nbPref0 = (int) r.getCell(TRINUCLEOTIDES_START_COLUMN + 7).getNumericCellValue();
                int nbPref1 = (int) r.getCell(TRINUCLEOTIDES_START_COLUMN + 8).getNumericCellValue();
                int nbPref2 = (int) r.getCell(TRINUCLEOTIDES_START_COLUMN + 9).getNumericCellValue();
                incrementSumPhasePref(sum, key, nbPref0, nbPref1, nbPref2);

                if (i < START_ROW + sum.getDiPhase0().size())
                {
                    key = r.getCell(DINUCLEOTIDES_START_COLUMN).getStringCellValue();

                    int nbDiPhase0 = (int) r.getCell(DINUCLEOTIDES_START_COLUMN + 1).getNumericCellValue();
                    int nbDiPhase1 = (int) r.getCell(DINUCLEOTIDES_START_COLUMN + 3).getNumericCellValue();

                    incrementSumDinucleotides(sum, key, nbDiPhase0, nbDiPhase1);
                }
            }
        }
    }


    /**
     * Permet de sommer les valeurs d'un workbook dans un SumResult
     * @param workbook Workbook à parcourir pour récuperer les données
     * @param currentSum Objet dans lequel on va stocker les valeurs
     */
    private static void fillSumResultWithWorkbook(XSSFWorkbook workbook, SumResults currentSum)
    {
        // SUM GENERAL
        Sheet sheet = workbook.getSheetAt(0);

        int nbOrganisms = (int) sheet.getRow(6).getCell(1).getNumericCellValue();
        int nbChromosomes = (int) sheet.getRow(3).getCell(5).getNumericCellValue();
        int nbMito = (int) sheet.getRow(4).getCell(5).getNumericCellValue();
        int nbADN = (int) sheet.getRow(5).getCell(5).getNumericCellValue();
        int nbPlasmides = (int) sheet.getRow(6).getCell(5).getNumericCellValue();
        int nbPlastes = (int) sheet.getRow(7).getCell(5).getNumericCellValue();
        int nbLiens = (int) sheet.getRow(8).getCell(5).getNumericCellValue();

        currentSum.setNbOrganisms( currentSum.getNbOrganisms() + nbOrganisms);
        currentSum.setNbChromosomes( currentSum.getNbChromosomes() + nbChromosomes );
        currentSum.setNbMitochondrions( currentSum.getNbMitochondrions() + nbMito );
        currentSum.setNbDNA( currentSum.getNbDNA() + nbADN );
        currentSum.setNbPlasmids( currentSum.getNbPlasmids() + nbPlasmides );
        currentSum.setNbPlasts( currentSum.getNbPlasts() + nbPlastes );
        currentSum.setNbLinkages( currentSum.getNbLinkages() + nbLiens );

        // SUM STATS
        // Chromosomes
        addSheetToSumResult(workbook.getSheet(XlsExport.SUM_CHROMOSOMES_SHEET), currentSum.getSumChromosomes());

        // Mitochondrions
        addSheetToSumResult(workbook.getSheet(XlsExport.SUM_MITOCHONDRIONS_SHEET), currentSum.getSumMitochondrions());

        // DNA
        addSheetToSumResult(workbook.getSheet(XlsExport.SUM_DNA_SHEET), currentSum.getSumDNA());

        // Plasmids
        addSheetToSumResult(workbook.getSheet(XlsExport.SUM_PLASMIDS_SHEET), currentSum.getSumPlasmids());

        // Plasts
        addSheetToSumResult(workbook.getSheet(XlsExport.SUM_PLASTS_SHEET), currentSum.getSumPlasts());

        // Linkages
        addSheetToSumResult(workbook.getSheet(XlsExport.SUM_LINKAGES_SHEET), currentSum.getSumLinkages());
    }


    /**
     * Permet de calculer tous les totaux intermédiaires
     * @param currentPath Chemin du dossier à analyser
     */
    public static void computePartialSums (String currentPath)
    {
        File currentDir = new File(currentPath);
        // List all files and directories of the current one
        File[] filesList = currentDir.listFiles();
        Config config = ConfigManager.getConfig();
        String resultsPath = config.getResultsFolder();
        String group = currentDir.getName();

        if (filesList != null)
        {
            SumResults currentSum = new SumResults();
            XSSFWorkbook wb = XlsExport.getWorkbookFromTemplate();

            // For each file of the current dir
            for (File f : filesList)
            {
                if (f.isDirectory())
                {
                    computePartialSums(f.getPath());
                    try
                    {
                        String subTotal = currentPath + config.getFolderSeparator() + "Total_" + f.getName() + ".xlsx";
                        FileInputStream is = new FileInputStream(subTotal);
                        XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(is);

                        fillSumResultWithWorkbook(workbook, currentSum);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else if (!f.getName().contains("Total") && f.getName().toLowerCase().endsWith(".xlsx"))
                {
                    try
                    {
                        FileInputStream is = new FileInputStream(f.getPath());
                        XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(is);

                        fillSumResultWithWorkbook(workbook, currentSum);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            // Export the file
            try
            {
                if (!currentPath.equals(resultsPath))
                {
                    currentSum.setOrganism("Total_" + group);
                    XlsExport.exportExcelFile(wb, currentSum, currentDir.getParent());
                    UIManager.writeLog("--- [EXCEL] Excel file of group \"" + group + "\" created");
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
