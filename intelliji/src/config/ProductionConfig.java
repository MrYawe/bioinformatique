package config;

import java.io.File;

/**
 * Created by yannis on 29/01/17.
 */
public class ProductionConfig implements Config {

    @Override
    public String getFolderSeparator() {
        return File.separator;
    }

    @Override
    public String getBaseFolder() {
        return "/tmp/results/";
    }

    @Override
    public String getResFolder()
    {
        return System.getProperty("user.dir") + "/res";
    }

    @Override
    public boolean useGui() {
        return false;
    }

    @Override
    public int getNetMaxDownloadTries() {
        return 10;
    }

    @Override
    public int getNetTimeBetweenTries() {
        return 10000;
    }

    @Override
    public int getIdPerPage() {
        return 100;
    }

    @Override
    public int getIdMaxTries() {
        return 10;
    }

    @Override
    public String getIdSearchUrl() {
        return "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=nuccore&retmax=<PER_PAGE>&term=<TERM>[Organism]&retstart=<START>";
    }

    @Override
    public String getTreeUrl() {
        return "http://www.ncbi.nlm.nih.gov/genomes/Genome2BE/genome2srv.cgi?action=GetGenomeList4Grid&amp;filterText=%7CAll&amp;page=";
    }

    @Override
    public int getGenPerDownload() {
        return 10;
    }

    @Override
    public String getGenDownloadUrl() {
        return "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=nuccore&id=<ID>&rettype=gb";
    }

    @Override
    public int getNbThreads() {
        return 40;
    }
}