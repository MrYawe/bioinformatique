import com.google.common.collect.ImmutableList;
import config.ConfigManager;
import config.ProductionConfig;
import tree.*;
import view.MainFrameAcryl;

import com.google.common.io.Resources;
import javax.swing.*;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

import static java.io.FileDescriptor.in;

public class Main {


    public static void main(String[] args) {
        //BuildTree
        ConfigManager.setConfig(new ProductionConfig());
        ExampleTree.setup();
        Tree tree = ExampleTree.getTree();
        // tree.printTree();

        //Main view
        MainFrameAcryl jfMain;
        try {
            //Apply acryl theme
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
            //Start application
            jfMain = MainFrameAcryl.getInstance();
            jfMain.setVisible(true);


            //OrganismTree tree = OrganismTree.fromGenBank();

            jfMain.updateDisplayedTree(tree);

            //tree.downloadAllOrganisms();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
