package tree;

import java.util.ArrayList;
import java.util.List;

import com.google.common.util.concurrent.ServiceManager;

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

        for(Organism o : organisms){
            o.updateTree(mainTree);
        }

        // UIManager.log("End of tree build !");
        System.out.println("End of tree build !");
        return mainTree;
    }
}
