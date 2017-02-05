package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by arnaudSteinmetz on 29/01/2017.
 **/
public class MainFrame extends JFrame {

    private JPanel jpnWest;
    private JPanel jpnEast;
    private JPanel jpnNorth;
    private JPanel jpnSouth;


    public MainFrame() {
        this.setSize(700,500);

        jpnWest = new JPanel();
        jpnEast = new JPanel();
        jpnNorth = new JPanel();
        jpnSouth = new JPanel();

        JScrollPane jspMain = new JScrollPane();
        JTree jtMain = new JTree();

        jspMain.add(jtMain);
        jpnWest.add(jspMain);


        jpnWest.setBackground(Color.BLUE);
        jpnEast.setBackground(Color.BLUE);
        jpnNorth.setBackground(Color.BLUE);
        jpnSouth.setBackground(Color.BLUE);

        this.add(jpnWest, BorderLayout.WEST);
        this.add(jpnEast, BorderLayout.EAST);
        this.add(jpnNorth, BorderLayout.NORTH);
        this.add(jpnSouth, BorderLayout.SOUTH);

    }
}
