package tree;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.common.io.Resources;
import com.google.common.util.concurrent.ServiceManager;

import com.sun.org.apache.xpath.internal.operations.Or;
import config.ConfigManager;
import tree.TreeBuilderService.OrganismType;
// import ui.UIManager;

/**
 * Created by yannis on 29/01/17.
 */
public class OrganismTree<T> extends Tree {

    public static OrganismTree fromGenBank() {

        // UIManager.setMaxProgress(37+72+60+10);

        // We create one TreeBuilderServices for each organism type
        List<TreeBuilderService> services = new ArrayList<TreeBuilderService>();
        TreeBuilderService eukaryotes = new TreeBuilderService(OrganismType.EUKARYOTES);
        TreeBuilderService prokaryotes = new TreeBuilderService(OrganismType.PROKARYOTES);
        TreeBuilderService viruses = new TreeBuilderService(OrganismType.VIRUSES);
        services.add(eukaryotes);
        services.add(prokaryotes);
        services.add(viruses);

        // We create a ServiceManager that will manage our three TreeBuilderServices
        ServiceManager sm = new ServiceManager(services);
        sm.startAsync(); // Initiates service startup on all the services being managed.
        // UIManager.log("Tree builder services launched");
        System.out.println("Tree builder services launched");
        sm.awaitStopped(); // Waits for the all the services to reach a terminal state.

        // UIManager.log("End of tree fetch !");
        System.out.println("End of tree fetch !");
        // UIManager.log("Starting tree build.");
        System.out.println("Starting tree build.");
        List<Organism> organisms = new ArrayList<Organism>();
        organisms.addAll(eukaryotes.organisms());
        organisms.addAll(prokaryotes.organisms());
        organisms.addAll(viruses.organisms());

        OrganismTree mainTree = new OrganismTree<Tree>();

        for (Organism o : organisms) {
            o.updateTree(mainTree);
        }

        // UIManager.log("End of tree build !");
        System.out.println("End of tree build !");
        return mainTree;
    }

    public void downloadAllOrganisms() {
        TreeWalker walker = new TreeWalker(this);
        Organism org = walker.next();
        System.out.println("Download "+org.getName()+" ...");

        while(org != null){

            String basePath = ConfigManager.getConfig().getResFolder()+"/organisms/"+org.getName();
            System.out.println(basePath);
            (new File(basePath)).mkdirs();

            for(String replicon : org.getReplicons().keySet()){
                try {
                    System.out.println("Download "+replicon+" of "+org.getName()+ "...");
                    String url = ConfigManager.getConfig().getGenDownloadUrl().replaceAll("<ID>", org.getReplicons().get(replicon));
                    InputStream stream = Resources.asByteSource(new URL(url)).openBufferedStream();
                    Files.copy(stream, Paths.get(basePath+"/"+replicon+".txt"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
            org = walker.next();
        }
    }
}
