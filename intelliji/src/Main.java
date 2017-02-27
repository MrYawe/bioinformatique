import config.ConfigManager;
import config.ProductionConfig;
import tree.ExampleTree;
import tree.OrganismTree;
import tree.Tree;
import view.MainFrame;
import view.MainFrameAcryl;

import javax.swing.*;

public class Main {


    public static void main(String[] args) {
        //BuildTree
        ConfigManager.setConfig(new ProductionConfig());
        // OrganismTree tree = OrganismTree.fromGenBank();
        ExampleTree.setup();
        Tree tree = ExampleTree.getTree();
        tree.printTree();

        //Main view
        JFrame jfMain;
        try {
            //Apply acryl theme
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
            //Start application
            jfMain = new MainFrameAcryl(tree);
            jfMain.setVisible(true);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
