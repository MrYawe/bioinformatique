package config;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yannis on 29/01/17.
 */
public class ProductionConfig implements Config {


    // General Configuration
    @Override
    public String getFolderSeparator() {
        return File.separator;
    }

    @Override
    public String getResultsFolder() {
        return System.getProperty("user.dir") + getFolderSeparator() + "Results";
    }

    @Override
    public String getOrganismsFolder() {
        return System.getProperty("user.dir") + getFolderSeparator() + "organisms";
    }

    @Override
    public String getResourcesFolder()
    {
        return System.getProperty("user.dir") + getFolderSeparator() + "resources";
    }

    @Override
    public boolean useGui() {
        return false;
    }


    // Net Configuration
    @Override
    public int getNetMaxDownloadTries() {
        return 10;
    }

    @Override
    public int getNetTimeBetweenTries() {
        return 10000;
    }

    @Override
    public int getSecondsBetweenTries() {return 3; }


    // Fetcher Configuration
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


    // Tree Configuration
    @Override
    public boolean getLoadTreeFromGenbank() {
        return true;
    }

    @Override
    public String getTreeUrl() {
        return "http://www.ncbi.nlm.nih.gov/genomes/Genome2BE/genome2srv.cgi?action=GetGenomeList4Grid&filterText=%7CAll&page=";
    }

    @Override
    public String getTreeEukaryotesUrl() {
        return "https://www.ncbi.nlm.nih.gov/genomes/Genome2BE/genome2srv.cgi?action=GetGenomes4Grid&king=Eukaryota&mode=2&filterText=%7C%7C--+All+Eukaryota+--%7C--+All+Eukaryota+--%7C%7C50%2C40%2C30%2C20%7Cnopartial%7Cnoanomalous&pageSize=100&page=";
    }

    @Override
    public String getTreeProkaryotesUrl() {
        return "https://www.ncbi.nlm.nih.gov/genomes/Genome2BE/genome2srv.cgi?action=GetGenomes4Grid&king=Bacteria&mode=2&filterText=%7C%7C--+All+Prokaryotes+--%7C--+All+Prokaryotes+--%7C%7C50%2C40%7Cnopartial%7Cnoanomalous&pageSize=100&page=";
    }

    @Override
    public String getTreeVirusesUrl() {
        return "https://www.ncbi.nlm.nih.gov/genomes/Genome2BE/genome2srv.cgi?action=GetGenomes4Grid&king=Viruses&mode=2&pageSize=100&page=";
    }

    @Override
    public List<String> getArchaeaGroups() {
        return new ArrayList<String>(Arrays.asList("Asgard_group", "Euryarchaeota", "TACK_group", "DPANN_group", "environmental_samples", "unclassified_Archaea"));
    }


    // Parser Configuration
    @Override
    public int getGenPerDownload() {
        return 10;
    }

    @Override
    public String getGenDownloadUrl() {
        return "https://www.ncbi.nlm.nih.gov/sviewer/viewer.cgi?tool=portal&save=file&log$=seqview&db=nuccore&report=gbwithparts&sort=&from=begin&to=end&maxplex=3&id=<ID>";
    }


    // Threads Configuration
    @Override
    public int getNbThreads() {
        return 8;
    }
}