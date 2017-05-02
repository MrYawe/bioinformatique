package tree;

import java.io.*;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import config.Config;
import config.ConfigManager;

import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.google.common.util.concurrent.AbstractExecutionThreadService;

import io.Net;
import statistics.StatsExport;
import view.MainFrameAcryl;
import view.UIManager;

/**
 * Created by yannis on 20/03/17.
 */
public class OrganismFetcherService extends AbstractExecutionThreadService {
    private Config config;
    private String baseURL;
    private Organism organism;

    private Retryer<Void> retryer;

    private Callable<Void> fetchCallable = () -> {
        return this.fetchOrganism();
    };

    OrganismFetcherService(Organism organism) {
        this.organism = organism;
        this.config = ConfigManager.getConfig();
        this.retryer = RetryerBuilder.<Void>newBuilder()
                .retryIfExceptionOfType(IOException.class)
                .retryIfRuntimeException()
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                .withWaitStrategy(WaitStrategies.fibonacciWait())
                .build();
    }

    public Void fetchOrganism() {
        if (organism != null){
            StatsExport export = new StatsExport(this.organism);
            String basePath = organism.getOrganismPath();
            File dir = new File(basePath);
            FileOutputStream zip = null;
            ZipOutputStream zos = null;

            if(Files.exists(Paths.get(basePath))) {
                if(dir.list().length >= organism.getReplicons().size() ) {
                    UIManager.writeLog("All replicons of "+organism.getName()+" are already downloaded.");
                } else {
                    UIManager.writeLog("Begin "+organism.getName()+ "...");
                }
            } else {
                dir.mkdirs();
                UIManager.writeLog("Begin "+organism.getName()+ "...");
            }

            if (MainFrameAcryl.getInstance().isKeepFilesOfSelectedOrganismsEnabled()) {
                try {
                    zip = new FileOutputStream(basePath + ".zip");
                    zos = new ZipOutputStream(zip);
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            String resultsPath = "";
            if (MainFrameAcryl.getInstance().isComputeStatsOnSelectedOrganismsEnabled()) {
                resultsPath = organism.getResultsPath();
                File resultsDir = new File(resultsPath);
                resultsDir.mkdirs();
            }

            Date lastStatsDate = organism.getLastStatsDate();
            Boolean willDowloadAndComputeStats = lastStatsDate == null || organism.getModificationDate().compareTo(lastStatsDate) > 0;
            for(String replicon : organism.getReplicons().keySet()){

                String repliconPath = basePath+config.getFolderSeparator()+replicon+".txt";

                if(willDowloadAndComputeStats) {
                    if(lastStatsDate != null) {
                        UIManager.writeLog("--- Older stats of replicon \""+replicon+"\" of \""+organism.getName()+"\" found ...");
                    }
                    if (Files.exists(Paths.get(repliconPath))) {
                        try {
                            Files.delete(Paths.get(repliconPath));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }
                }

                if (willDowloadAndComputeStats || (MainFrameAcryl.getInstance().isKeepFilesOfSelectedOrganismsEnabled() && !MainFrameAcryl.getInstance().isComputeStatsOnSelectedOrganismsEnabled()) && !Files.exists(Paths.get(repliconPath))) {
                    UIManager.writeLog("--- Download replicon \""+replicon+"\" of \""+organism.getName()+"\" ...");
                    String url = ConfigManager.getConfig().getGenDownloadUrl().replaceAll("<ID>", organism.getReplicons().get(replicon));
                    ReadableByteChannel rbc = Net.getUrlAsByteChannel(url);
                    try {
                        FileOutputStream fos = new FileOutputStream(repliconPath);
                        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                        fos.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        throw new RuntimeException(); // will be catched in run()
                    }
                    UIManager.writeLog("--- Download of replicon \""+replicon+"\" of \"" + organism.getName() + "\" ended.");

                    if (MainFrameAcryl.getInstance().isComputeStatsOnSelectedOrganismsEnabled()) {
                        export.treatReplicon(repliconPath, replicon);
                    }

                    if (MainFrameAcryl.getInstance().isKeepFilesOfSelectedOrganismsEnabled()) {
                        UIManager.writeLog("--- Writing replicon \"" + replicon + "\" of \"" + organism.getName() + "\" in zipfile");
                    }
                }
                else {
                    if (!MainFrameAcryl.getInstance().isKeepFilesOfSelectedOrganismsEnabled()) {
                        UIManager.writeLog("--- Latest stats of replicon \""+replicon+"\" of \""+organism.getName()+"\" already found.");
                    }
                    else {
                        UIManager.writeLog("--- Writing replicon \"" +replicon+ "\" of \""+organism.getName()+ "\" in zipfile");
                    }
                }

                if (Files.exists(Paths.get(repliconPath))) {
                    if (!MainFrameAcryl.getInstance().isKeepFilesOfSelectedOrganismsEnabled()) {
                        UIManager.writeLog("--- Delete replicon \"" + replicon + "\" of \"" + organism.getName() + "\" ...");
                        //System.out.println("Delete "+repliconPath);
                        File repliconFile = new File(repliconPath);
                        repliconFile.delete();
                    }
                    else {
                        if (zos != null) {
                            try {
                                addToZipFile(repliconPath, zos);
                            }
                            catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                UIManager.addProgressTree(0);
            }

            if (MainFrameAcryl.getInstance().isComputeStatsOnSelectedOrganismsEnabled() && willDowloadAndComputeStats) {
                export.exportOrganism(resultsPath);
            }

            if (MainFrameAcryl.getInstance().isKeepFilesOfSelectedOrganismsEnabled() && zip != null) {
                try {
                    zos.close();
                    zip.close();
                    UIManager.writeLog("--- [ZIP] Zipfile of organism \"" + organism.getName() + "\" created");
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (!MainFrameAcryl.getInstance().isKeepFilesOfSelectedOrganismsEnabled() && Files.exists(Paths.get(basePath))) {
                dir.delete();
                if (Files.exists(Paths.get(basePath + ".zip"))) {
                    try {
                        Files.delete(Paths.get(basePath + ".zip"));
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }


    @Override
    protected void run() throws Exception {
        try {
            this.retryer.call(this.fetchCallable);
        } catch (Exception e) {
            UIManager.writeError("[ERROR] An error occured while downloading the organism \""+this.organism.getName()+"\": Genbank is unreachable.");
            System.out.println("[ERROR "+e.getClass()+"] An error occured while downloading the organism \"" + this.organism.getName() + "\". ");
            e.printStackTrace();
        }
    }

    protected static void addToZipFile(String fileName, ZipOutputStream zos) throws IOException {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        int buffer = 1024;
        BufferedInputStream origin = new BufferedInputStream(fis, buffer);
        ZipEntry zipEntry = new ZipEntry(file.getName());
        zos.putNextEntry(zipEntry);

        byte[] bytes = new byte[buffer];
        int length;
        while ((length = origin.read(bytes, 0, buffer)) != -1) {
            zos.write(bytes, 0, length);
        }

        zos.closeEntry();
        origin.close();
        fis.close();
    }
}
