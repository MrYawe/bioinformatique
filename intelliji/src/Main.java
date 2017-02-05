import config.ConfigManager;
import config.ProductionConfig;
import tree.OrganismTree;
import view.MainFrameAcryl;

import javax.swing.*;

public class Main {


    public static void main(String[] args) {
        //Main view
        JFrame jfMain;
        try {
            //Apply acryl theme
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
            //Start application
            jfMain = new MainFrameAcryl();
            jfMain.setVisible(true);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        ConfigManager.setConfig(new ProductionConfig());
        OrganismTree tree = OrganismTree.fromGenBank();
        tree.printTree();
    }
}
