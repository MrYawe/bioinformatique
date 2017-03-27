package view;

import tree.TreeBuilderService;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Created by saetlan on 21/03/17.
 */
public class LoadingPanel extends JPanel {
    private JPanel pnlForeground;
    private int widthGif;
    private int heightGif;

    JPanel getPnlForeground() { return this.pnlForeground; }

    int getWidthGif() { return this.widthGif; }

    //////////////////////////////////////////
    //              CONSTRUCTOR             //
    //////////////////////////////////////////
    LoadingPanel() {
        //////////////////////////////////////////
        //              DECLARATION             //
        //////////////////////////////////////////
        URL pathADN;            //Path to the ADN GIF
        ImageIcon imageADN;     //Image icon of the ADN GIF

        //////////////////////////////////////////
        //             INSTANTIATION            //
        //////////////////////////////////////////
        pnlForeground = new JPanel();

        //////////////////////////////////////////
        //                 LAYOUT               //
        //////////////////////////////////////////
        this.setLayout(new BorderLayout());

        try
        {
            pathADN = MainFrameAcryl.class.getClass().getResource("/img/brinFinal.gif");
            imageADN = new ImageIcon(pathADN);

            this.widthGif = imageADN.getIconWidth()/3;
            this.heightGif = imageADN.getIconHeight()/3;
            imageADN.setImage(imageADN.getImage().getScaledInstance(widthGif, heightGif, Image.SCALE_DEFAULT));

            this.widthGif = 350;

            JLabel label = new JLabel(imageADN);
            JLayeredPane jlpGIF = new JLayeredPane();

            //////////////////////////////////////////
            //                 LAYOUT               //
            //////////////////////////////////////////
            pnlForeground.setBackground(new Color(220, 220, 220));
            this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
            pnlForeground.setLocation(0, 0);
            pnlForeground.setSize(new Dimension(widthGif,heightGif));

            jlpGIF.setPreferredSize(new Dimension(widthGif,heightGif));

            label.setPreferredSize(new Dimension(widthGif,heightGif));
            label.setLocation(0,0);
            label.setSize(label.getPreferredSize());

            jlpGIF.add(pnlForeground, new Integer(1));
            jlpGIF.add(label,new Integer(0));

            this.add(jlpGIF, BorderLayout.CENTER);

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
