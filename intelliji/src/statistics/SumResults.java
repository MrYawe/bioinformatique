package statistics;

import tree.Organism;

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
     * Objet contenant les statistiques correspondant à la somme des plasmides
     */
    private CDSResult sumPlasmids;

    /**
     * Objet contenant les statistiques correspondant à la somme des plastes
     */
    private CDSResult sumPlasts;

    /**
     * Objet contenant les statistiques correspondant à la somme des liens génétiques
     */
    private CDSResult sumLinkages;

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
     * Nombre de plasmides de l'organisme
     */
    private int nbPlasmids;

    /**
     * Nombre de chloroplastes de l'organisme
     */
    private int nbPlasts;

    /**
     * Nombre de liens génétiques de l'organisme
     */
    private int nbLinkages;

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
     * Permet d'accéder à l'objet contenant les statistiques correspondant à la somme des plasmides
     * @return L'objet contenant les statistiques correspondant à la somme des plasmides
     */
    public CDSResult getSumPlasmids()
    {
        return sumPlasmids;
    }

    /**
     * Permet d'assigner l'objet contenant les statistiques correspondant à la somme des plasmides
     * @param sumPlasmids Objet contenant les statistiques correspondant à la somme des plasmides
     */
    public void setSumPlasmids(CDSResult sumPlasmids)
    {
        this.sumPlasmids = sumPlasmids;
    }

    /**
     * Permet d'accéder à l'objet contenant les statistiques correspondant à la somme des plastes
     * @return L'objet contenant les statistiques correspondant à la somme des plastes
     */
    public CDSResult getSumPlasts()
    {
        return sumPlasts;
    }

    /**
     * Permet d'assigner l'objet contenant les statistiques correspondant à la somme des plastes
     * @param sumPlasts Objet contenant les statistiques correspondant à la somme des plastes
     */
    public void setSumPlasts(CDSResult sumPlasts)
    {
        this.sumPlasts = sumPlasts;
    }

    /**
     * Permet d'accéder à l'objet contenant les statistiques correspondant à la somme des liens génétiques
     * @return L'objet contenant les statistiques correspondant à la somme des liens génétiques
     */
    public CDSResult getSumLinkages()
    {
        return sumLinkages;
    }

    /**
     * Permet d'assigner l'objet contenant les statistiques correspondant à la somme des liens génétiques
     * @param sumLinkages Objet contenant les statistiques correspondant à la somme des liens génétiques
     */
    public void setSumLinkages(CDSResult sumLinkages)
    {
        this.sumLinkages = sumLinkages;
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
     * Permet d'accéder au nombre de plasmides de l'organisme
     * @return Le nombre de plasmides de l'organisme
     */
    public int getNbPlasmids()
    {
        return nbPlasmids;
    }

    /**
     * Permet d'assigner le nombre de plasmides de l'organisme
     * @param nbPlasmids Nombre de plasmides de l'organisme
     */
    public void setNbPlasmids(int nbPlasmids)
    {
        this.nbPlasmids = nbPlasmids;
    }

    /**
     * Permet d'accéder au nombre de chloroplastes de l'organisme
     * @return Le nombre de chloroplastes de l'organisme
     */
    public int getNbPlasts()
    {
        return nbPlasts;
    }

    /**
     * Permet d'assigner le nombre de chloroplastes de l'organisme
     * @param nbPlasts Nombre de chloroplastes de l'organisme
     */
    public void setNbPlasts(int nbPlasts)
    {
        this.nbPlasts = nbPlasts;
    }

    /**
     * Permet d'accéder au nombre de liens génétiques de l'organisme
     * @return Le nombre de liens génétiques de l'organisme
     */
    public int getNbLinkages()
    {
        return nbLinkages;
    }

    /**
     * Permet d'assigner le nombre de liens génétiques de l'organisme
     * @param nbLinkages Nombre de liens génétiques de l'organisme
     */
    public void setNbLinkages(int nbLinkages)
    {
        this.nbLinkages = nbLinkages;
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
        this.sumPlasmids = new CDSResult();
        this.sumPlasts = new CDSResult();
        this.sumLinkages = new CDSResult();

        this.nbChromosomes = 0;
        this.nbMitochondrions = 0;
        this.nbDNA = 0;
        this.nbPlasmids = 0;
        this.nbPlasts = 0;
        this.nbLinkages = 0;
    }

    /**
     * Constructeur prenant en paramètre un organisme
     * @param organism Organisme en cours de traitement
     */
    public SumResults(Organism organism)
    {
        this();
        this.organism = organism.getName();
    }
}
