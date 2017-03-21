package tree;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

import config.Config;
import config.ConfigManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.google.common.base.Predicates;
import com.google.common.io.Resources;
import com.google.common.util.concurrent.AbstractExecutionThreadService;
import com.google.common.util.concurrent.AbstractService;
import view.MainFrameAcryl;
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
            System.out.println(basePath);
            (new File(basePath)).mkdirs();

            for(String replicon : organism.getReplicons().keySet()){
                try {
                    System.out.println("Download "+replicon+" of "+organism.getName()+ "...");
                    String url = ConfigManager.getConfig().getGenDownloadUrl().replaceAll("<ID>", organism.getReplicons().get(replicon));
                    InputStream stream = Resources.asByteSource(new URL(url)).openBufferedStream();
                    Files.copy(stream, Paths.get(basePath+"/"+replicon+".txt"));
                } catch (Exception ex) {

                    ex.printStackTrace();
                }

            }
        }
    }



    @Override
    protected void run() throws Exception {
        // UIManager.log(this.type.toString()+ " Starting");
        UIManager.writeLog(this.organism.getName()+ " Starting");
        this.fetchOrganism();
        // UIManager.log(this.type.toString() + " DONE !");
        UIManager.writeLog(this.organism.getName() + " DONE !");

    }
}
