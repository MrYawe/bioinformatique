import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import config.ConfigManager;
import config.DevelopmentConfig;
import config.ProductionConfig;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import tree.*;
import view.MainFrameAcryl;

import com.google.common.io.Resources;

import javax.annotation.Resource;
import javax.swing.*;
import java.io.*;

import static java.io.FileDescriptor.in;

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

            //tree.downloadAllOrganisms();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
