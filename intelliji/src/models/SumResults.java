package models;

/**
 * Classe permettant de stocker les résultats pour un organisme complet (pour faire les sommes)
 */
public class SumResults
{
    /**
     * Nom de l'organisme traité
     */
    private String organism;
    /**
     * Objet contenant les statistiques correspondant à la somme des chromosomes
     */
    private CDSResult sumChromosomes;
    /**
     * Objet contenant les statistiques correspondant à la somme des mitochondrions
     */
    private CDSResult sumMitochondrions;
    /**
     * Objet contenant les statistiques correspondant à la somme des ADN
     */
    private CDSResult sumDNA;
    /**
     * Objet contenant les statistiques correspondant à la somme des chloroplastes
     */
    private CDSResult sumChloroplasts;

    /**
     * Nombre de chromosomes de l'organisme
     */
    private int nbChromosomes;

    /**
     * Nombre de mitochondrions de l'organisme
     */
    private int nbMitochondrions;

    /**
     * Nombre d'ADN de l'organisme
     */
    private int nbDNA;

    /**
     * Nombre de chloroplastes de l'organisme
     */
    private int nbChloroplasts;

    /**
     * Permet d'accéder au nom de l'organisme traité
     * @return Le nom de l'organisme traité
     */
    public String getOrganism()
    {
        return organism;
    }

    /**
     * Permet d'assigner le nom de l'organisme traité
     * @param organism Nom de l'organisme traité
     */
    public void setOrganism(String organism)
    {
        this.organism = organism;
    }

    /**
     * Permet d'accéder à l'objet contenant les statistiques correspondant à la somme des chromosomes
     * @return L'objet contenant les statistiques correspondant à la somme des chromosomes
     */
    public CDSResult getSumChromosomes()
    {
        return sumChromosomes;
    }

    /**
     * Permet d'assigner l'objet contenant les statistiques correspondant à la somme des chromosomes
     * @param sumChromosomes Objet contenant les statistiques correspondant à la somme des chromosomes
     */
    public void setSumChromosomes(CDSResult sumChromosomes)
    {
        this.sumChromosomes = sumChromosomes;
    }

    /**
     * Permet d'accéder à l'objet contenant les statistiques correspondant à la somme des mitochondrions
     * @return L'objet contenant les statistiques correspondant à la somme des mitochondrions
     */
    public CDSResult getSumMitochondrions()
    {
        return sumMitochondrions;
    }

    /**
     * Permet d'assigner l'objet contenant les statistiques correspondant à la somme des mitochondrions
     * @param sumMitochondrions Objet contenant les statistiques correspondant à la somme des mitochondrions
     */
    public void setSumMitochondrions(CDSResult sumMitochondrions)
    {
        this.sumMitochondrions = sumMitochondrions;
    }

    /**
     * Permet d'accéder à l'objet contenant les statistiques correspondant à la somme des ADN
     * @return L'objet contenant les statistiques correspondant à la somme des ADN
     */
    public CDSResult getSumDNA()
    {
        return sumDNA;
    }

    /**
     * Permet d'assigner l'objet contenant les statistiques correspondant à la somme des ADN
     * @param sumDNA Objet contenant les statistiques correspondant à la somme des ADN
     */
    public void setSumDNA(CDSResult sumDNA)
    {
        this.sumDNA = sumDNA;
    }

    /**
     * Permet d'accéder à l'objet contenant les statistiques correspondant à la somme des chloroplastes
     * @return L'objet contenant les statistiques correspondant à la somme des chloroplastes
     */
    public CDSResult getSumChloroplasts()
    {
        return sumChloroplasts;
    }

    /**
     * Permet d'assigner l'objet contenant les statistiques correspondant à la somme des chloroplastes
     * @param sumChloroplasts Objet contenant les statistiques correspondant à la somme des chloroplastes
     */
    public void setSumChloroplasts(CDSResult sumChloroplasts)
    {
        this.sumChloroplasts = sumChloroplasts;
    }

    /**
     * Permet d'accéder au nombre de chromosomes de l'organisme
     * @return Le nombre de chromosomes de l'organisme
     */
    public int getNbChromosomes()
    {
        return nbChromosomes;
    }

    /**
     * Permet d'assigner le nombre de chromosomes de l'organisme
     * @param nbChromosomes Nombre de chromosomes de l'organisme
     */
    public void setNbChromosomes(int nbChromosomes)
    {
        this.nbChromosomes = nbChromosomes;
    }

    /**
     * Permet d'accéder au nombre de mitochondrions de l'organisme
     * @return Le nombre de mitochondrions de l'organisme
     */
    public int getNbMitochondrions()
    {
        return nbMitochondrions;
    }

    /**
     * Permet d'assigner le nombre de mitochondrions de l'organisme
     * @param nbMitochondrions Nombre de mitochondrions de l'organisme
     */
    public void setNbMitochondrions(int nbMitochondrions)
    {
        this.nbMitochondrions = nbMitochondrions;
    }

    /**
     * Permet d'accéder au nombre d'ADN de l'organisme
     * @return Le nombre d'ADN de l'organisme
     */
    public int getNbDNA()
    {
        return nbDNA;
    }

    /**
     * Permet d'assigner le nombre d'ADN de l'organisme
     * @param nbDNA Nombre d'ADN de l'organisme
     */
    public void setNbDNA(int nbDNA)
    {
        this.nbDNA = nbDNA;
    }

    /**
     * Permet d'accéder au nombre de chloroplastes de l'organisme
     * @return Le nombre de chloroplastes de l'organisme
     */
    public int getNbChloroplasts()
    {
        return nbChloroplasts;
    }

    /**
     * Permet d'assigner le nombre de chloroplastes de l'organisme
     * @param nbChloroplasts Nombre de chloroplastes de l'organisme
     */
    public void setNbChloroplasts(int nbChloroplasts)
    {
        this.nbChloroplasts = nbChloroplasts;
    }

    /**
     * Constructeur de la classe SumResults
     */
    public SumResults()
    {
        this.organism = "";
        this.sumChromosomes = new CDSResult();
        this.sumMitochondrions = new CDSResult();
        this.sumDNA = new CDSResult();
        this.sumChloroplasts = new CDSResult();

        this.nbChromosomes = 0;
        this.nbMitochondrions = 0;
        this.nbDNA = 0;
        this.nbChloroplasts = 0;
    }
}
