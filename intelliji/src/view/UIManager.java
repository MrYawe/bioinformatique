package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by saetlan on 15/03/17.
 */
public class UIManager {
    public static MainFrameAcryl frame = MainFrameAcryl.getInstance();
    public static JPanel progressPanel = frame.getBackgroundPanel();
    public static int nbProkaryote = 85;
    public static int nbVirus = 73;
    public static int nbEukaryote = 41;

    public static void writeError(String text) {
        frame.getConsole().println(text, Color.RED);
    }

    public static void writeLog(String text) {
        frame.getConsole().println(text);
    }

    public static void addProgress() {
        progressPanel.setLocation((int)progressPanel.getLocation().getX()+(frame.getWidth()/(nbEukaryote+nbVirus+nbProkaryote)),0);
        System.out.println(frame.getWidth());
        System.out.println(frame.getWidth()/(nbEukaryote+nbVirus+nbProkaryote));
    }
}
