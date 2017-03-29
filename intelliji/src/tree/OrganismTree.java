package tree;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.util.concurrent.ServiceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import config.ConfigManager;
import tree.TreeBuilderService.OrganismType;
// import ui.UIManager;

/**
 * Created by yannis on 29/01/17.
 */
public class OrganismTree {

    private static Tree<Tree> tree;

    public static Tree getInstance() {
        if(OrganismTree.tree == null) {
            OrganismTree.load();
        }
        return OrganismTree.tree;
    }

    public static void load() {
        if(ConfigManager.getConfig().getLoadTreeFromGenbank()) {
            loadFromGenBank();
        } else {
            loadFromLocalResource();
        }
    }

    private static void loadFromLocalResource() {
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Tree.class, new TreeSerializer());
            builder.registerTypeAdapter(Tree.class, new TreeDeserializer());
            builder.setPrettyPrinting();
            Gson gson = builder.create();

            File initialFile = new File(ConfigManager.getConfig().getResFolder()+"/organismTree.json");
            InputStream stream = com.google.common.io.Files.asByteSource(initialFile).openStream();
            Reader reader = new InputStreamReader(stream, "UTF-8");
            JsonReader jsonReader = new JsonReader(reader);
            jsonReader.setLenient(true);
            OrganismTree.tree = gson.fromJson(jsonReader, Tree.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveToLocalResource() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Tree.class, new TreeSerializer());
        builder.registerTypeAdapter(Tree.class, new TreeDeserializer());
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        String treeJson = gson.toJson(OrganismTree.tree, Tree.class);
        try (PrintStream out = new PrintStream(new File(ConfigManager.getConfig().getResFolder()+"/organismTree.json"), "UTF-8")) {
            out.println(treeJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadFromGenBank() {

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

        Tree mainTree = new Tree<Tree>();

        for (Organism o : organisms) {
            o.updateTree(mainTree);
        }

        // UIManager.log("End of tree build !");
        System.out.println("End of tree build !");
        OrganismTree.tree = mainTree;
    }

    public static void downloadSelectedOrganisms() {
        TreeWalker walker = new TreeWalker(OrganismTree.tree);
        Organism org;

        ExecutorService executor = Executors.newFixedThreadPool(ConfigManager.getConfig().getNbThreads());
        while(walker.hasNext()){
            org = walker.next();
            OrganismFetcherService fs = new OrganismFetcherService(org);
            executor.submit(() -> {
                try{fs.run();}
                catch (Exception e ) {
                    e.printStackTrace();
                }
            });
        }
    }
}
