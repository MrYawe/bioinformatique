package view;

import tree.TreeBuilderService.OrganismType;

import javax.swing.*;
import java.awt.*;


/**
 * Created by saetlan on 15/03/17.
 */
public class UIManager {
    private static MainFrameAcryl frame = MainFrameAcryl.getInstance();

    private static int nbProkaryote = 87;
    private static int nbVirus = 72;
    private static int nbEukaryote = 42;
    private static int nbReplicons = -1;

    private static int currentProkaryote = 0;
    private static int currentVirus = 0;
    private static int currentEukaryote = 0;

    private static int currentStats = 0;

    public synchronized static void writeError(String text) {
        frame.getConsole().println(text, Color.RED);
    }

    public synchronized static void writeLog(String text) {
        frame.getConsole().println(text);
    }

    /*public static void lockOn(int value) {
        unlock0=-value;
        JButton[] btns = frame.getBtn();
        for(int i=0;i<btns.length;i++){
            btns[i].setEnabled(false);
        }
    }*/

    public static void lock() {
        JButton[] btns = frame.getBtn();
        frame.setResizable(false);
        for(int i=0;i<btns.length;i++){
            btns[i].setEnabled(false);
        }
    }

    public static void unlock() {
        JButton[] btns = frame.getBtn();
        frame.setResizable(true);
        for(int i=0;i<btns.length;i++){
            btns[i].setEnabled(true);
        }
    }



    public static void setNbReplicons(int nbReplicons){
        UIManager.nbReplicons=nbReplicons;
        System.out.println(nbReplicons);
    }

    public static void resetLoadingStatsPanel() {
        currentStats=0;
        frame.getPnlLoadingMain().reset();
    }

    public static void resetLoadingTreePanel() {
        currentVirus=0;
        currentEukaryote=0;
        currentProkaryote=0;
        LoadingTreePanel[] panels = frame.getLoadingTrees();
        for(LoadingTreePanel p:panels) {
            p.reset();
        }
    }

    public synchronized static void addProgressTree() {
        LoadingTreePanel loadingStatsPanel = frame.getLoadingPanel(null);
        int pnlSize = (int)loadingStatsPanel.getPnlLoadingTree().getSize().getWidth();
        currentStats++;

        loadingStatsPanel.setPercent(currentStats*100.0/nbReplicons);
        loadingStatsPanel.updatePercent();
        loadingStatsPanel.getPnlForeground().setLocation(currentStats*pnlSize/nbReplicons,0);

    }

    public synchronized static void addProgressTree(OrganismType type) {
        LoadingTreePanel loadingPanel=frame.getLoadingPanel(type);
        int pnlSize = (int)loadingPanel.getPnlLoadingTree().getSize().getWidth();
        if(type.equals(OrganismType.EUKARYOTES)) {
            currentEukaryote++;
            //progressPanel.setLocation((int)Math.round(progressPanel.getLocation().getX()+(loadingPanel.getWidthGif()/(nbEukaryote))),0);
            loadingPanel.setPercent(currentEukaryote*100.0/nbEukaryote);
            loadingPanel.updatePercent();
            loadingPanel.getPnlForeground().setLocation(currentEukaryote*pnlSize/nbEukaryote,0);
        } else if(type.equals(OrganismType.VIRUSES)) {
            currentVirus++;
            //progressPanel.setLocation((int)progressPanel.getLocation().getX()+(loadingPanel.getWidthGif()/(nbVirus)),0);
            loadingPanel.setPercent(currentVirus*100.0/nbVirus);
            loadingPanel.updatePercent();
            loadingPanel.getPnlForeground().setLocation(currentVirus*pnlSize/nbVirus,0);
        } else if(type.equals(OrganismType.PROKARYOTES)) {
            currentProkaryote++;
            //progressPanel.setLocation((int)progressPanel.getLocation().getX()+(loadingPanel.getWidthGif()/(nbProkaryote)),0);
            loadingPanel.setPercent(currentProkaryote*100.0/nbProkaryote);
            loadingPanel.updatePercent();
            loadingPanel.getPnlForeground().setLocation(currentProkaryote*pnlSize/nbProkaryote,0);
        }
    }
}
