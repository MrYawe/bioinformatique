import config.ConfigManager;
import config.DevelopmentConfig;
import tree.*;
import view.MainFrameAcryl;
import javax.swing.*;

public class Main {


    public static void main(String[] args) {
        // Use "new ProductionConfig()" to load the organism tree from Genbank
        // or "new DevelopementConfig()" to load it from the local file "organismeTree.json"
        ConfigManager.setConfig(new DevelopmentConfig());

        //Main view
        MainFrameAcryl jfMain;
        try {
            //Apply acryl theme
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
            //Start application
            jfMain = MainFrameAcryl.getInstance();
            jfMain.setVisible(true);

            OrganismTree.load();
            jfMain.updateDisplayedTree(OrganismTree.getInstance());

            // OrganismTree.downloadSelectedOrganisms();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
