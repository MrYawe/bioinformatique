package tree;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import config.Config;
import config.ConfigManager;

import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.google.common.io.Resources;
import com.google.common.util.concurrent.AbstractExecutionThreadService;
import view.UIManager;

/**
 * Created by yannis on 20/03/17.
 */
public class OrganismFetcherService extends AbstractExecutionThreadService {
    private Config config;
    private String baseURL;
    private Organism organism;

    private Retryer<List<Organism>> retryer;

    /*
    private Callable<List<Organism>> pageCallable = new Callable<List<Organism>>(){
        public List<Organism> call() throws MalformedURLException, IOException{
            return parseCurrentPage();
        }
    };
    */

    OrganismFetcherService(Organism organism) {
        this.organism = organism;
        this.config = ConfigManager.getConfig();
        this.retryer = RetryerBuilder.<List<Organism>>newBuilder()
                .retryIfExceptionOfType(IOException.class)
                .retryIfRuntimeException()
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                .withWaitStrategy(WaitStrategies.fibonacciWait())
                .build();
    }

    private void fetchOrganism() {
        System.out.println("Download "+organism.getName()+" ...");

        if (organism != null){

            String basePath = ConfigManager.getConfig().getResFolder()+"/organisms/"+organism.getName();
            File dir = new File(basePath);

            if(Files.exists(Paths.get(basePath))) {
                if(dir.list().length >= organism.getReplicons().size() ) {
                    UIManager.writeLog("All replicons of "+organism.getName()+" are already downloaded.");
                    return;
                }
            } else {
                dir.mkdirs();
            }

            UIManager.writeLog("Download "+organism.getName()+ "...");
            for(String replicon : organism.getReplicons().keySet()){
                try {
                    String repliconPath = basePath+"/"+replicon+".txt";
                    if(!Files.exists(Paths.get(repliconPath))) {
                        UIManager.writeLog("--- Download replicon \""+replicon+"\" of \""+organism.getName()+"\" ...");
                        String url = ConfigManager.getConfig().getGenDownloadUrl().replaceAll("<ID>", organism.getReplicons().get(replicon));
                        InputStream stream = Resources.asByteSource(new URL(url)).openBufferedStream();
                        Files.copy(stream, Paths.get(repliconPath));
                        UIManager.writeLog("--- Download of replicon \""+replicon+"\" ended.");
                    }
                } catch (Exception ex) {

                    ex.printStackTrace();
                }

            }
        }
    }



    @Override
    protected void run() throws Exception {
        this.fetchOrganism();
    }
}
