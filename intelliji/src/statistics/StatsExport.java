package statistics;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;

import models.TreatFile;
import tree.Organism;
import view.UIManager;

/**
 * Created by Brice on 01/04/2017.
 */

public class StatsExport
{
	/**
	 * Fichier Excel qui sera exporté
	 */
	private XSSFWorkbook workbook;

	/**
	 * Organisme en cours de traitement
	 */
	private Organism organism;

	/**
	 * Objet contenant les statistiques de toutes les sommes
	 */
	private SumResults sumResults;

	/**
	 * Constructeur de la classe permettant de traiter entièrement un organisme
	 * @param organism Organisme à traiter
	 */
	public StatsExport(Organism organism)
	{
		this.workbook = XlsExport.getWorkbookFromTemplate();
		this.organism = organism;
		this.sumResults = new SumResults(this.organism);
	}

	/**
	 * Permet de traiter un replicon d'un organisme (un fichier complet)
	 * @param repliconPath Chemin où se trouve le fichier à traiter
	 * @param replicon Nom du replicon à traiter
	 */
	public void treatReplicon(String repliconPath, String replicon)
	{
		try
		{
			File f = new File(repliconPath);
			CDSResult result = TreatFile.processFile(f);
			result.setLocusName(replicon);
			result.setOrganism(this.organism.getName());
			if (replicon.contains("chromosome_"))
			{
				result.setType(CDSResult.Type.CHROMOSOME);
			}
			XlsExport.exportStats(this.workbook, result, this.sumResults);
			UIManager.writeLog("--- [STATS] Replicon \"" + replicon + "\" of \"" + this.organism.getName() + "\" treated");
		}
		catch (FileNotFoundException e)
		{
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Permet d'exporter le fichier excel d'un organisme complet
	 * @param path Chemin d'export du fichier excel
	 */
	public void exportOrganism(String path)
	{
		XlsExport.exportExcelFile(this.workbook, this.sumResults, path);
		UIManager.writeLog("--- [EXCEL] Excel file of organism \"" + this.organism.getName() + "\" created");
	}
}