package view;

import tree.TreeBuilderService.OrganismType;

import javax.swing.*;
import java.awt.*;


/**
 * Created by saetlan on 15/03/17.
 */
public class UIManager {
    private static MainFrameAcryl frame = MainFrameAcryl.getInstance();

    private static int unlock0 = 0;

    public static int getUnlock0()
    {
        return unlock0;
    }

    public static void setUnlock0(int unlock0)
    {
        UIManager.unlock0 = unlock0;
    }

    private static int nbProkaryote = 85;
    private static int nbVirus = 72;
    private static int nbEukaryote = 41;

    private static int currentProkaryote = 0;
    private static int currentVirus = 0;
    private static int currentEukaryote = 0;

    public static void writeError(String text) {
        frame.getConsole().println(text, Color.RED);
    }

    public static void writeLog(String text) {
        frame.getConsole().println(text);
    }

    public static void lockOn(int value) {
        unlock0=-value;
        JButton[] btns = frame.getBtn();
        for(int i=0;i<btns.length;i++){
            btns[i].setEnabled(false);
        }
    }

    public static void lockOff() {
        if(unlock0!=0) {
            unlock0+=1;
            if(unlock0==0) {
                JButton[] btns = frame.getBtn();
                for(int i=0;i<btns.length;i++){
                    btns[i].setEnabled(true);
                }
            }
        } else {
            UIManager.writeError("lock value already null");
        }
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

    public static void addProgressTree(OrganismType type) {
        LoadingTreePanel loadingPanel=frame.getLoadingPanel(type);

        if(type.equals(OrganismType.EUKARYOTES)) {
            currentEukaryote++;
            //progressPanel.setLocation((int)Math.round(progressPanel.getLocation().getX()+(loadingPanel.getWidthGif()/(nbEukaryote))),0);
            loadingPanel.setPercent(currentEukaryote*100/nbEukaryote);
            loadingPanel.updatePercent();
            loadingPanel.getPnlForeground().setLocation(currentEukaryote*loadingPanel.getWidthGif()/nbEukaryote,0);
        } else if(type.equals(OrganismType.VIRUSES)) {
            currentVirus++;
            //progressPanel.setLocation((int)progressPanel.getLocation().getX()+(loadingPanel.getWidthGif()/(nbVirus)),0);
            loadingPanel.setPercent(currentVirus*100/nbVirus);
            loadingPanel.updatePercent();
            loadingPanel.getPnlForeground().setLocation(currentVirus*loadingPanel.getWidthGif()/nbVirus,0);
        } else if(type.equals(OrganismType.PROKARYOTES)) {
            currentProkaryote++;
            //progressPanel.setLocation((int)progressPanel.getLocation().getX()+(loadingPanel.getWidthGif()/(nbProkaryote)),0);
            loadingPanel.setPercent(currentProkaryote*100/nbProkaryote);
            loadingPanel.updatePercent();
            loadingPanel.getPnlForeground().setLocation(currentProkaryote*loadingPanel.getWidthGif()/nbProkaryote,0);
        }
    }
}
