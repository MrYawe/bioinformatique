package config;

/**
 * Created by yannis on 29/01/17.
 */
public interface Config {

    // General Configuration
    String getFolderSeparator();
    String getOrganismsFolder();
    String getResultsFolder();
    String getResourcesFolder();
    boolean useGui();

    // Net Configuration
    int getNetMaxDownloadTries();
    int getNetTimeBetweenTries();

    // Fetcher Configuration
    int getIdPerPage();
    int getIdMaxTries();
    String getIdSearchUrl();

    // Tree Configuration
    boolean getLoadTreeFromGenbank();
    String getTreeUrl();
    String getTreeEukaryotesUrl();
    String getTreeProkaryotesUrl();
    String getTreeVirusesUrl();

    // Parser Configuration
    int getGenPerDownload();
    String getGenDownloadUrl();

    // Threads Configuration
    int getNbThreads();
}
