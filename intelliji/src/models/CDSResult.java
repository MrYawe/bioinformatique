package models;

import java.util.HashMap;

/**
 * Class which represents a CDS result
 */

public class CDSResult
{
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
	 * Table de hachage associant la phase préférentielle 0 à un nombre de dinucléotides
	 */
	private HashMap<String, Integer> diPrefPhase0;
	/**
	 * Table de hachage associant la phase préférentielle 1 à un nombre de dinucléotides
	 */
	private HashMap<String, Integer> diPrefPhase1;

	/**
	 * Nom de l'espèce
	 */
	private String species;
	/**
	 * Nom du chromosome (NC_...)
	 */
	private String chromosomeName;

	/**
	 * Nombre de CDS au total
	 */
	private int nbCDS;
	/**
	 * Nombre de CDS invalides, donc jetés
	 */
	private int nbInvalidCDS;

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
	 * Permet de récupérer le nombre de dinucléotides ayant comme phase préférentielle la phase 0
	 * @return Une table de hachage associant la phase préférentielle 0 à un nombre de dinucléotides
	 */
	public HashMap<String, Integer> getDiPrefPhase0()
	{
		return diPrefPhase0;
	}

	/**
	 * Permet d'assigner le HashMap des dinucléotides ayant comme phase préférentielle la phase 0
	 * @param diPrefPhase0 Table de hachage associant la phase préférentielle 0 à un nombre de dinucléotides
	 */
	public void setDiPrefPhase0(HashMap<String, Integer> diPrefPhase0)
	{
		this.diPrefPhase0 = diPrefPhase0;
	}

	/**
	 * Permet de récupérer le nombre de dinucléotides ayant comme phase préférentielle la phase 1
	 * @return Une table de hachage associant la phase préférentielle 1 à un nombre de dinucléotides
	 */
	public HashMap<String, Integer> getDiPrefPhase1()
	{
		return diPrefPhase1;
	}

	/**
	 * Permet d'assigner le HashMap des dinucléotides ayant comme phase préférentielle la phase 1
	 * @param diPrefPhase1 Table de hachage associant la phase préférentielle 1 à un nombre de dinucléotides
	 */
	public void setDiPrefPhase1(HashMap<String, Integer> diPrefPhase1)
	{
		this.diPrefPhase1 = diPrefPhase1;
	}

	/**
	 * Permet d'accéder au nom de l'espèce traitée
	 * @return Le nom de l'espèce
	 */
	public String getSpecies()
	{
		return species;
	}

	/**
	 * Permet d'assigner le nom de l'espèce traitée
	 * @param species Nom de l'espèce
	 */
	public void setSpecies(String species)
	{
		this.species = species;
	}

	/**
	 * Permet d'accéder au nom du chromosome
	 * @return Le nom du chromosome traité (NC_...)
	 */
	public String getChromosomeName()
	{
		return chromosomeName;
	}

	/**
	 * Permet d'assigner le nom du chromosome
	 * @param chromosomeName Nom du chromosome traité (NC_...)
	 */
	public void setChromosomeName(String chromosomeName)
	{
		this.chromosomeName = chromosomeName;
	}

	/**
	 * Permet d'accéder au nombre total de CDS
	 * @return Le nombre total de CDS
	 */
	public int getNbCDS()
	{
		return nbCDS;
	}

	/**
	 * Permet d'assigner le nombre total de CDS
	 * @param nbCDS Nombre total de CDS
	 */
	public void setNbCDS(int nbCDS)
	{
		this.nbCDS = nbCDS;
	}

	/**
	 * Permet d'accéder au nombre de CDS invalides et donc jetés pour le chromose en cours de traitement
	 * @return Le nombre de CDS invalides
	 */
	public int getNbInvalidCDS()
	{
		return nbInvalidCDS;
	}

	/**
	 * Permet d'assigner le nombre de CDS invalides
	 * @param nbInvalidCDS Nombre de CDS invalides
	 */
	public void setNbInvalidCDS(int nbInvalidCDS)
	{
		this.nbInvalidCDS = nbInvalidCDS;
	}
}
