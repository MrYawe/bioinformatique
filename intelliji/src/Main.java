import config.ConfigManager;
import config.ProductionConfig;
import tree.OrganismTree;
import tree.Tree;
import view.MainFrame;

import javax.swing.*;

public class Main {


    public static void main(String[] args) {

        JFrame jfMain = new MainFrame();
        jfMain.setVisible(true);


        ConfigManager.setConfig(new ProductionConfig());

        OrganismTree tree = OrganismTree.fromGenBank();
        tree.printTree();



    }
}
