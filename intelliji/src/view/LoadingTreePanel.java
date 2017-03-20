package view;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Created by saetlan on 16/03/17.
 */
public class LoadingTreePanel extends JPanel {

    private JPanel backgroundPanel;

    public JPanel getBackgroundPanel() {
        return backgroundPanel;
    }

    public LoadingTreePanel() {
        backgroundPanel = new JPanel();
        this.setLayout(new BorderLayout());
        //this.setPreferredSize(new Dimension(800,190));
        try
        {
            URL path = MainFrameAcryl.class.getClass().getResource("/img/brinFinal.gif");
            Icon icon = new ImageIcon(path);
            JLabel label = new JLabel(icon);

            JLayeredPane jlpGIF = new JLayeredPane();
            jlpGIF.setPreferredSize(label.getPreferredSize());
            this.add(jlpGIF, BorderLayout.CENTER);

            backgroundPanel.setBackground(Color.LIGHT_GRAY);
            backgroundPanel.setLocation(0, 0);
            backgroundPanel.setSize(label.getPreferredSize());
            jlpGIF.add(backgroundPanel, new Integer(1));

            label.setLocation(0,0);
            label.setSize(label.getPreferredSize());
            jlpGIF.add(label,new Integer(0));
            //jlpGIF.setPreferredSize(new Dimension(800,190));

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }


}
