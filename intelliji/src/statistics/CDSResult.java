package statistics;

import java.util.HashMap;

/**
 * Class représentant les résultats des statistiques
 * @author brice
 * @version 1.0
 */

public class CDSResult
{
	/**
	 * Enumération des types des stats
	 */
	public enum Type {CHROMOSOME, MITOCHONDRION, DNA, PLASMID, PLAST, LINKAGE};

	/**
	 * Type des stats courantes (type CHROMOSOME par défaut)
	 */
	private Type type;

	/////// HashMap pour les statistiques des trinucléotides //////
	/**
	 * Table de hachage associant un trinucléotide à un nombre d'occurences en phase 0
	 */
	private HashMap<String, Integer> triPhase0;
	/**
	 * Table de hachage associant un trinucléotide à un nombre d'occurences en phase 1
	 */
	private HashMap<String, Integer> triPhase1;
	/**
	 * Table de hachage associant un trinucléotide à un nombre d'occurences en phase 2
	 */
	private HashMap<String, Integer> triPhase2;

	/**
	 * Table de hachage associant la phase préférentielle 0 à un nombre de trinucléotides
	 */
	private HashMap<String, Integer> triPrefPhase0;
	/**
	 * Table de hachage associant la phase préférentielle 1 à un nombre de trinucléotides
	 */
	private HashMap<String, Integer> triPrefPhase1;
	/**
	 * Table de hachage associant la phase préférentielle 2 à un nombre de trinucléotides
	 */
	private HashMap<String, Integer> triPrefPhase2;


	/////// HashMap pour les statistiques des dinucléotides //////
	/**
	 * Table de hachage associant un dinucléotide à un nombre d'occurences en phase 0
	 */
	private HashMap<String, Integer> diPhase0;
	/**
	 * Table de hachage associant un dinucléotide à un nombre d'occurences en phase 1
	 */
	private HashMap<String, Integer> diPhase1;

	/**
	 * Nom de l'organisme
	 */
	private String organism;
	/**
	 * Nom du locus (NC_...)
	 */
	private String locusName;


	/**
	 * Nombre total de CDS dans le fichier
	 */
	private int nbCDS;

	/**
	 * Nombre de CDS mal formés, donc jetés dès la première lecture
	 */
	private int nbMalformedCDS;

	/**
	 * Nombre de CDS identiques à d'autres, donc non traités
	 */
	private int nbIdenticalCDS;

	/**
	 * Nombre de CDS bien formés mais invalides, donc jetés lors des statistiques
	 */
	private int nbInvalidCDS;

    /**
     * Permet d'accéder au type des résultats du fichier courant
     * @return Le type des résultats du fichier courant
     * @see Type
     */
	public Type getType()
    {
        return this.type;
    }

    /**
     * Permet d'assigner le type des résultats du fichier courant
     * @param type Type des résultats du fichier courant
     * @see Type
     */
    public void setType(Type type)
    {
        this.type = type;
    }

	/**
	 * Permet de récupérer le HashMap de la phase 0 des trinucléotides
	 * @return Une table de hachage associant un trinucléotide à un nombre d'occurences en phase 0
	 */
	public HashMap<String, Integer> getTriPhase0()
	{
		return triPhase0;
	}

	/**
	 * Permet d'assigner le HashMap de la phase 0 des trinucléotides
	 * @param triPhase0 Table de hachage associant un trinucléotide à un nombre d'occurences en phase 0
	 */
	public void setTriPhase0(HashMap<String, Integer> triPhase0)
	{
		this.triPhase0 = triPhase0;
	}

	/**
	 * Permet de récupérer le HashMap de la phase 1 des trinucléotides
	 * @return Une table de hachage associant un trinucléotide à un nombre d'occurences en phase 1
	 */
	public HashMap<String, Integer> getTriPhase1()
	{
		return triPhase1;
	}

	/**
	 * Permet d'assigner le HashMap de la phase 1 des trinucléotides
	 * @param triPhase1 Table de hachage associant un trinucléotide à un nombre d'occurences en phase 1
	 */
	public void setTriPhase1(HashMap<String, Integer> triPhase1)
	{
		this.triPhase1 = triPhase1;
	}

	/**
	 * Permet de récupérer le HashMap de la phase 2 des trinucléotides
	 * @return Une table de hachage associant un trinucléotide à un nombre d'occurences en phase 2
	 */
	public HashMap<String, Integer> getTriPhase2()
	{
		return triPhase2;
	}

	/**
	 * Permet d'assigner le HashMap de la phase 2 des trinucléotides
	 * @param triPhase2 Table de hachage associant un trinucléotide à un nombre d'occurences en phase 2
	 */
	public void setTriPhase2(HashMap<String, Integer> triPhase2)
	{
		this.triPhase2 = triPhase2;
	}

	/**
	 * Permet de récupérer le nombre de trinucléotides ayant comme phase préférentielle la phase 0
	 * @return Une table de hachage associant la phase préférentielle 0 à un nombre de trinucléotides
	 */
	public HashMap<String, Integer> getTriPrefPhase0()
	{
		return triPrefPhase0;
	}

	/**
	 * Permet d'assigner le HashMap des trinucléotides ayant comme phase préférentielle la phase 0
	 * @param triPrefPhase0 Table de hachage associant la phase préférentielle 0 à un nombre de trinucléotides
	 */
	public void setTriPrefPhase0(HashMap<String, Integer> triPrefPhase0)
	{
		this.triPrefPhase0 = triPrefPhase0;
	}

	/**
	 * Permet de récupérer le nombre de trinucléotides ayant comme phase préférentielle la phase 1
	 * @return Une table de hachage associant la phase préférentielle 1 à un nombre de trinucléotides
	 */
	public HashMap<String, Integer> getTriPrefPhase1()
	{
		return triPrefPhase1;
	}

	/**
	 * Permet d'assigner le HashMap des trinucléotides ayant comme phase préférentielle la phase 1
	 * @param triPrefPhase1 Table de hachage associant la phase préférentielle 1 à un nombre de trinucléotides
	 */
	public void setTriPrefPhase1(HashMap<String, Integer> triPrefPhase1)
	{
		this.triPrefPhase1 = triPrefPhase1;
	}

	/**
	 * Permet de récupérer le nombre de trinucléotides ayant comme phase préférentielle la phase 2
	 * @return Une table de hachage associant la phase préférentielle 2 à un nombre de trinucléotides
	 */
	public HashMap<String, Integer> getTriPrefPhase2()
	{
		return triPrefPhase2;
	}

	/**
	 * Permet d'assigner le HashMap des trinucléotides ayant comme phase préférentielle la phase 2
	 * @param triPrefPhase2 Table de hachage associant la phase préférentielle 2 à un nombre de trinucléotides
	 */
	public void setTriPrefPhase2(HashMap<String, Integer> triPrefPhase2)
	{
		this.triPrefPhase2 = triPrefPhase2;
	}

	/**
	 * Permet de récupérer le HashMap de la phase 0 des dinucléotides
	 * @return Une table de hachage associant un dinucléotide à un nombre d'occurences en phase 0
	 */
	public HashMap<String, Integer> getDiPhase0()
	{
		return diPhase0;
	}

	/**
	 * Permet d'assigner le HashMap de la phase 0 des dinucléotides
	 * @param diPhase0 Table de hachage associant un dinucléotide à un nombre d'occurences en phase 0
	 */
	public void setDiPhase0(HashMap<String, Integer> diPhase0)
	{
		this.diPhase0 = diPhase0;
	}

	/**
	 * Permet de récupérer le HashMap de la phase 1 des dinucléotides
	 * @return Une table de hachage associant un dinucléotide à un nombre d'occurences en phase 1
	 */
	public HashMap<String, Integer> getDiPhase1()
	{
		return diPhase1;
	}

	/**
	 * Permet d'assigner le HashMap de la phase 1 des dinucléotides
	 * @param diPhase1 Table de hachage associant un dinucléotide à un nombre d'occurences en phase 1
	 */
	public void setDiPhase1(HashMap<String, Integer> diPhase1)
	{
		this.diPhase1 = diPhase1;
	}

	/**
	 * Permet d'accéder au nom de l'organisme traité
	 * @return Le nom de l'organisme
	 */
	public String getOrganism()
	{
		return organism;
	}

	/**
	 * Permet d'assigner le nom de l'organisme traité
	 * @param organism Nom de l'organisme
	 */
	public void setOrganism(String organism)
	{
		this.organism = organism;
	}

	/**
	 * Permet d'accéder au nom du locus
	 * @return Le nom du locus traité (NC_...)
	 */
	public String getLocusName()
	{
		return locusName;
	}

	/**
	 * Permet d'assigner le nom du locus
	 * @param locusName Nom du locus traité (NC_...)
	 */
	public void setLocusName(String locusName)
	{
		this.locusName = locusName;
	}

	/**
	 * Permet d'accéder au nombre total de CDS dans le fichier
	 * @return Le nombre total de CDS contenus dans le fichier
	 */
	public int getNbCDS()
	{
		return nbCDS;
	}

	/**
	 * Permet d'assigner le nombre total de CDS du fichier
	 * @param nbCDS Nombre total de CDS contenus dans le fichier
	 */
	public void setNbCDS(int nbCDS)
	{
		this.nbCDS = nbCDS;
	}

	/**
	 * Permet d'accéder au nombre de CDS mal formés donc jetés dès la première lecture du fichier
	 * @return Le nombre de CDS mal formés
	 */
	public int getNbMalformedCDS()
	{
		return nbMalformedCDS;
	}

	/**
	 * Permet d'assigner le nombre de CDS mal formés dans le fichier
	 * @param nbMalformedCDS Nombre de CDS mal formés
	 */
	public void setNbMalformedCDS(int nbMalformedCDS)
	{
		this.nbMalformedCDS = nbMalformedCDS;
	}

	/**
	 * Permet d'accéder au nombre de CDS identiques à d'autres
	 * @return Le nombre de CDS bien identiqeus à d'autres
	 */
	public int getNbIdenticalCDS()
	{
		return nbIdenticalCDS;
	}

	/**
	 * Permet d'assigner le nombre de CDS identiques à d'autres
	 * @param nbIdenticalCDS Nombre de CDS bien identiques à d'autres
	 */
	public void setNbIdenticalCDS(int nbIdenticalCDS)
	{
		this.nbIdenticalCDS = nbIdenticalCDS;
	}

	/**
	 * Permet d'accéder au nombre de CDS bien formés mais invalides et donc jetés pour le chromosome en cours de traitement
	 * @return Le nombre de CDS bien formés mais invalides
	 */
	public int getNbInvalidCDS()
	{
		return nbInvalidCDS;
	}

	/**
	 * Permet d'assigner le nombre de CDS bien formés mais invalides
	 * @param nbInvalidCDS Nombre de CDS bien formés mais invalides
	 */
	public void setNbInvalidCDS(int nbInvalidCDS)
	{
		this.nbInvalidCDS = nbInvalidCDS;
	}

	/**
	 * Constructeur de la classe CDSResult
	 */
	public CDSResult()
	{
		this.type = Type.DNA;

		this.triPhase0 = this.initializationHmap();
		this.triPhase1 = this.initializationHmap();
		this.triPhase2 = this.initializationHmap();
		this.triPrefPhase0 = this.initializationHmap();
		this.triPrefPhase1 = this.initializationHmap();
		this.triPrefPhase2 = this.initializationHmap();

		this.diPhase0 = this.initializationHmapDi();
		this.diPhase1 = this.initializationHmapDi();

		this.locusName = "";
		this.organism = "";
		this.nbCDS = 0;
		this.nbMalformedCDS = 0;
		this.nbIdenticalCDS = 0;
		this.nbInvalidCDS = 0;
	}

    /**
     * Permet d'initialiser un HashMap pour les statistiques des trinucléotides
     * @return Un HashMap initialisé
     */
    public HashMap<String, Integer> initializationHmap() {

      /*Adding elements to HashMap*/
        HashMap<String, Integer> hmap = new HashMap<>();
        hmap.put("TTT", 0);hmap.put("TCT", 0);hmap.put("TAT", 0);hmap.put("TGT", 0);
        hmap.put("TTC", 0);hmap.put("TCC", 0);hmap.put("TAC", 0);hmap.put("TGC", 0);
        hmap.put("TTA", 0);hmap.put("TCA", 0);hmap.put("TAA", 0);hmap.put("TGA", 0);
        hmap.put("TTG", 0);hmap.put("TCG", 0);hmap.put("TAG", 0);hmap.put("TGG", 0);

        hmap.put("CTT", 0);hmap.put("CCT", 0);hmap.put("CAT", 0);hmap.put("CGT", 0);
        hmap.put("CTC", 0);hmap.put("CCC", 0);hmap.put("CAC", 0);hmap.put("CGC", 0);
        hmap.put("CTA", 0);hmap.put("CCA", 0);hmap.put("CAA", 0);hmap.put("CGA", 0);
        hmap.put("CTG", 0);hmap.put("CCG", 0);hmap.put("CAG", 0);hmap.put("CGG", 0);

        hmap.put("ATT", 0);hmap.put("ACT", 0);hmap.put("AAT", 0);hmap.put("AGT", 0);
        hmap.put("ATC", 0);hmap.put("ACC", 0);hmap.put("AAC", 0);hmap.put("AGC", 0);
        hmap.put("ATA", 0);hmap.put("ACA", 0);hmap.put("AAA", 0);hmap.put("AGA", 0);
        hmap.put("ATG", 0);hmap.put("ACG", 0);hmap.put("AAG", 0);hmap.put("AGG", 0);

        hmap.put("GTT", 0);hmap.put("GCT", 0);hmap.put("GAT", 0);hmap.put("GGT", 0);
        hmap.put("GTC", 0);hmap.put("GCC", 0);hmap.put("GAC", 0);hmap.put("GGC", 0);
        hmap.put("GTA", 0);hmap.put("GCA", 0);hmap.put("GAA", 0);hmap.put("GGA", 0);
        hmap.put("GTG", 0);hmap.put("GCG", 0);hmap.put("GAG", 0);hmap.put("GGG", 0);

        return hmap;
    }

    /**
     * Permet d'initialiser un HashMap pour les statistiques des dinucléotides
     * @return Un HashMap initialisé
     */
    public HashMap<String, Integer> initializationHmapDi()
    {
        HashMap<String, Integer> hash = new HashMap<>();
        hash.put("AA", 0);
        hash.put("AC", 0);
        hash.put("AG", 0);
        hash.put("AT", 0);
        hash.put("CA", 0);
        hash.put("CC", 0);
        hash.put("CG", 0);
        hash.put("CT", 0);
        hash.put("GA", 0);
        hash.put("GC", 0);
        hash.put("GG", 0);
        hash.put("GT", 0);
        hash.put("TA", 0);
        hash.put("TC", 0);
        hash.put("TG", 0);
        hash.put("TT", 0);
        return hash;
    }
}
