package statistics;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;

import models.TreatFile;
import tree.Organism;
import view.MainFrameAcryl;
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
		this.sumResults.setNbOrganisms(1);
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
			if (!MainFrameAcryl.getInstance().isKeepFilesOfSelectedOrganismsEnabled())
			{
				f.delete();
			}
		}
		catch (FileNotFoundException e)
		{
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Permet d'exporter le fichier excel d'un organisme complet
	 * @param resultPath Chemin d'export du fichier excel
	 * @param organismPath Chemin du dossier contenant les fichiers téléchargés de l'organisme
	 */
	public void exportOrganism(String resultPath, String organismPath)
	{
		XlsExport.exportExcelFile(this.workbook, this.sumResults, resultPath);
		UIManager.writeLog("--- [EXCEL] Excel file of organism \"" + this.organism.getName() + "\" created");
		File f = new File(organismPath);
		if (f.exists() && !MainFrameAcryl.getInstance().isKeepFilesOfSelectedOrganismsEnabled())
		{
			f.delete();
		}
	}
}
