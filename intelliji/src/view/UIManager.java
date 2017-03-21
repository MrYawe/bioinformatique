package view;

import tree.TreeBuilderService.OrganismType;

import javax.swing.*;
import java.awt.*;


/**
 * Created by saetlan on 15/03/17.
 */
public class UIManager {
    public static MainFrameAcryl frame = MainFrameAcryl.getInstance();

    public static int nbProkaryote = 84;
    public static int nbVirus = 72;
    public static int nbEukaryote = 41;

    public static int currentProkaryote = 0;
    public static int currentVirus = 0;
    public static int currentEukaryote = 0;

    public static void writeError(String text) {
        frame.getConsole().println(text, Color.RED);
    }

    public static void writeLog(String text) {
        frame.getConsole().println(text);
    }

    public static void addProgressTree(OrganismType type) {
        LoadingTreePanel loadingPanel=frame.getLoadingPanel(type);

        if(type.equals(OrganismType.EUKARYOTES)) {
            currentEukaryote++;
            //progressPanel.setLocation((int)Math.round(progressPanel.getLocation().getX()+(loadingPanel.getWidthGif()/(nbEukaryote))),0);
            loadingPanel.setPercent(currentEukaryote*100/nbEukaryote);
            loadingPanel.updatePercent();
            loadingPanel.getBackgroundPanel().setLocation(currentEukaryote*loadingPanel.getWidthGif()/nbEukaryote,0);
        } else if(type.equals(OrganismType.VIRUSES)) {
            currentVirus++;
            //progressPanel.setLocation((int)progressPanel.getLocation().getX()+(loadingPanel.getWidthGif()/(nbVirus)),0);
            loadingPanel.setPercent(currentVirus*100/nbVirus);
            loadingPanel.updatePercent();
            loadingPanel.getBackgroundPanel().setLocation(currentVirus*loadingPanel.getWidthGif()/nbVirus,0);
        } else if(type.equals(OrganismType.PROKARYOTES)) {
            currentProkaryote++;
            //progressPanel.setLocation((int)progressPanel.getLocation().getX()+(loadingPanel.getWidthGif()/(nbProkaryote)),0);
            loadingPanel.setPercent(currentProkaryote*100/nbProkaryote);
            loadingPanel.updatePercent();
            loadingPanel.getBackgroundPanel().setLocation(currentProkaryote*loadingPanel.getWidthGif()/nbProkaryote,0);
        }
    }
}
