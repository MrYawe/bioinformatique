package tree;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

// import ui.UIManager;

public class TreeBuilderService extends AbstractExecutionThreadService {

    public static enum OrganismType {
        EUKARYOTES,
        PROKARYOTES,
        VIRUSES,
    }

    private Config config;
    private OrganismType type;
    private String baseURL;
    private int currentPage;
    private List<Organism> organismList;

    private Retryer<List<Organism>> retryer;

    private Callable<List<Organism>> pageCallable = new Callable<List<Organism>>(){
        public List<Organism> call() throws MalformedURLException, IOException{
            return parseCurrentPage();
        }
    };

    public TreeBuilderService(OrganismType type){
        this.config = ConfigManager.getConfig();
        this.retryer = RetryerBuilder.<List<Organism>>newBuilder()
                .retryIfExceptionOfType(IOException.class)
                .retryIfRuntimeException()
                .withStopStrategy(StopStrategies.neverStop())
                .withWaitStrategy(WaitStrategies.fixedWait(config.getSecondsBetweenTries(), TimeUnit.SECONDS))
                .build();

        switch(type){
            case EUKARYOTES:
                this.baseURL = this.config.getTreeEukaryotesUrl();
                break;
            case PROKARYOTES:
                this.baseURL = this.config.getTreeProkaryotesUrl();
                break;
            case VIRUSES:
                this.baseURL = this.config.getTreeVirusesUrl();
        }
        this.type = type;
        this.organismList = new ArrayList<Organism>();
    }

    public void readAllPages(){

        this.currentPage = 1;
        boolean cont = true;
        while(cont) {
            try{

                List<Organism> result = retryer.call(this.pageCallable);
                if(result == null){
                    cont = false;
                } else {
                    this.organismList.addAll(result);
                    UIManager.writeLog(this.type.toString()+ " page : "+this.currentPage);
                    UIManager.addProgressTree(this.type);
                }
            }catch(Exception e){
                UIManager.writeError("[ERROR] An error occured while downloading the organism tree: Genbank is unreachable.");

                System.out.println("[ERROR "+e.getClass()+"] An error occured while downloading the organism tree.");
                e.printStackTrace();
            }

            currentPage ++;
        }
    }

    public List<Organism> parseCurrentPage() throws MalformedURLException, IOException{
        String webPage = "";
        try {
            webPage = new String(Resources.toByteArray(new URL(this.baseURL+this.currentPage)));
        } catch (Exception ex) {
            UIManager.writeError("["+this.type+"] Genbank is unreachable.\n Retrying in "+config.getSecondsBetweenTries()+" seconds...");
        }


        if(webPage.split("-->")[1].trim().length() == 0){
            return null;
        }

        List<Organism> organismsList = new ArrayList<Organism>();

        Document doc = Jsoup.parse("<table>"+webPage+"</table>");

        Elements organisms = doc.select(".Odd,.Even");

        for(Iterator<Element> it = organisms.iterator(); it.hasNext();){
            Element organism = it.next();
            Elements replicons = organism.select("table");

            if(replicons.size() != 0){
                Elements organismTDs = organism.select("td");
                Iterator<Element> tdIterator = organismTDs.iterator();
                String organismName = tdIterator.next().text();
                if(type == OrganismType.PROKARYOTES) {
                    tdIterator.next(); // Skip CladeID
                }
                if(type != OrganismType.VIRUSES) {
                    tdIterator.next(); // Skip Strain
                    tdIterator.next(); // Skip BioSample
                }
                String organismBioProject = tdIterator.next().text();
                String organismGroup = tdIterator.next().text();
                String organismSubGroup = tdIterator.next().text();
                String creationDate =  "";
                String modificationDate = "";
                String buffer = "";
                while(tdIterator.hasNext()){
                    creationDate = modificationDate;
                    modificationDate = buffer;
                    buffer = tdIterator.next().text();
                }

                Organism currentOrganism = new Organism(type.name(), organismGroup, organismSubGroup, organismName, organismBioProject, creationDate, modificationDate);

                boolean validOrganism = false;

                Elements repliconsTDs = replicons.iterator().next().select("td");
                for(Iterator<Element> it2 = repliconsTDs.iterator(); it2.hasNext();) {
                    Element replicon = it2.next();
                    if(replicon.id().length() != 0) {
                        // Skip the "show more button"
                        // The show more button is the only one with an id
                        continue;
                    }
                    String repliconName = "";
                    if(type != OrganismType.VIRUSES) {
                        repliconName = replicon.text().split("/")[0].replace(" ", "_").replace(":", "_");
                    }
                    String[] repliconIDs = replicon.select("a").text().split(" ");
                    String repliconID = "";
                    boolean validRepliconFound = false;
                    for(String rID : repliconIDs) {
                        if(rID.startsWith("NC") ||
                                rID.startsWith("MT") ||
                                rID.startsWith("CL") ||
                                rID.startsWith("CH")){
                            repliconID = rID;
                            validRepliconFound = true;
                            break;
                        }
                    }
                    if(validRepliconFound){
                        validOrganism = true;
                        if(type ==OrganismType.VIRUSES){
                            repliconName = repliconID;
                        }
                        currentOrganism.addReplicon(repliconName, repliconID);
                    }
                }
                if(validOrganism){
                    organismsList.add(currentOrganism);
                }
            }
        }
        return organismsList;
    }

    public List<Organism> organisms(){
        return this.organismList;
    }

    @Override
    protected void run() throws Exception {
        // UIManager.log(this.type.toString()+ " Starting");
        UIManager.writeLog(this.type.toString()+ " Starting");
        this.readAllPages();

        // UIManager.log(this.type.toString() + " DONE !");
        UIManager.writeLog(this.type.toString() + " DONE !");
    }
}
