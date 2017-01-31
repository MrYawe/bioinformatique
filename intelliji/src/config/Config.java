package config;

/**
 * Created by yannis on 29/01/17.
 */
public interface Config {

    // General
    String getFolderSeparator();
    String getBaseFolder();
    String getResFolder();
    boolean useGui();

    // Net
    int getNetMaxDownloadTries();
    int getNetTimeBetweenTries();

    // IdFetcher Configuration
    int getIdPerPage();
    int getIdMaxTries();
    String getIdSearchUrl();
    String getTreeUrl();

    // Parser
    int getGenPerDownload();
    String getGenDownloadUrl();

    // Thread
    int getNbThreads();
}
