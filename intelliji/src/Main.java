import config.ConfigManager;
import config.ProductionConfig;
import tree.ExampleTree;
import tree.OrganismTree;
import tree.Tree;
import view.MainFrameAcryl;

import javax.swing.*;

public class Main {


    public static void main(String[] args) {
        //BuildTree
        ConfigManager.setConfig(new ProductionConfig());
        /*ExampleTree.setup();
        Tree tree = ExampleTree.getTree();
        tree.printTree();*/
        //ConfigManager.setConfig(new ProductionConfig());
        //OrganismTree tree = OrganismTree.fromGenBank();
        //tree.printTree();

        //Main view
        MainFrameAcryl jfMain;
        try {
            //Apply acryl theme
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
            //Start application
            jfMain = new MainFrameAcryl();
            jfMain.setVisible(true);


            OrganismTree tree = OrganismTree.fromGenBank();
            /*ExampleTree.setup();
            Tree tree = ExampleTree.getTree();*/
            jfMain.updateDisplayedTree(tree);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
