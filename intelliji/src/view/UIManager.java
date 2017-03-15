package view;

import java.awt.*;

/**
 * Created by saetlan on 15/03/17.
 */
public class UIManager {
    public static MainFrameAcryl frame = MainFrameAcryl.getInstance();

    public static void writeError(String text) {
        frame.getConsole().println(text, Color.RED);
    }

    public static void writeLog(String text) {
        frame.getConsole().println(text);
    }


}
