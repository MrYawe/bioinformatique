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
        try
        {
            URL path = MainFrameAcryl.class.getClass().getResource("/img/brinFinal.gif");
            ImageIcon image = new ImageIcon(path);
            int width = image.getIconWidth()/2;
            int height = image.getIconHeight()/2;
            image.setImage(image.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
            this.setPreferredSize(new Dimension(350,height));



            JLabel label = new JLabel(image);

            JLayeredPane jlpGIF = new JLayeredPane();

            //label.setPreferredSize(new Dimension(500,100));
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
