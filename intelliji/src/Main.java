import config.ConfigManager;
import config.ProductionConfig;
import tree.OrganismTree;
import tree.Tree;

public class Main {


    public static void main(String[] args) {
        ConfigManager.setConfig(new ProductionConfig());

        OrganismTree tree = OrganismTree.fromGenBank();
        tree.printTree();
    }
}
