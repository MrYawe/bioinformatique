import config.ConfigManager;
import config.ProductionConfig;
import tree.OrganismTree;
import tree.Tree;
import view.MainFrame;
import view.MainFrameAcryl;

import javax.swing.*;

public class Main {


    public static void main(String[] args) {
        //Main view
        MainFrameAcryl jfMain;
        try {
            //Apply acryl theme
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
            //Start application
            jfMain = new MainFrameAcryl();
            jfMain.setVisible(true);

            for (int i = 0; i<1000;i++) {
                jfMain.backgroundPanel.setLocation(i,0);
                Thread.sleep(50);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        //ConfigManager.setConfig(new ProductionConfig());
        //OrganismTree tree = OrganismTree.fromGenBank();
        //tree.printTree();
    }
}
