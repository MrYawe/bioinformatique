package view;

import javax.swing.*;

import config.Config;
import config.ConfigManager;

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
    LoadingPanel(boolean mainLoader) {
        //////////////////////////////////////////
        //              DECLARATION             //
        //////////////////////////////////////////
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
        	Config config = ConfigManager.getConfig();
            imageADN = new ImageIcon(config.getResourcesFolder() + config.getFolderSeparator() + "img" + config.getFolderSeparator() + "brinFinal.gif");

            if(!mainLoader) {
                this.widthGif = imageADN.getIconWidth()/3;
                this.heightGif = imageADN.getIconHeight()/3;
                imageADN.setImage(imageADN.getImage().getScaledInstance(widthGif, heightGif, Image.SCALE_DEFAULT));
                this.widthGif = 350;

            }
            else {
                this.widthGif = imageADN.getIconWidth()/2;
                this.heightGif = imageADN.getIconHeight()/2;
                imageADN.setImage(imageADN.getImage().getScaledInstance(widthGif, heightGif, Image.SCALE_DEFAULT));
                //this.widthGif = 350;

            }



            JLabel label = new JLabel(imageADN);
            JLayeredPane jlpGIF = new JLayeredPane();

            //////////////////////////////////////////
            //                 LAYOUT               //
            //////////////////////////////////////////
            pnlForeground.setBackground(new Color(220, 220, 220));
            pnlForeground.setLocation(0, 0);
            pnlForeground.setSize(new Dimension(widthGif,heightGif));

            jlpGIF.setPreferredSize(new Dimension(widthGif,heightGif));

            label.setPreferredSize(new Dimension(widthGif,heightGif));
            label.setLocation(0,0);
            label.setSize(label.getPreferredSize());

            jlpGIF.add(pnlForeground, new Integer(1));
            jlpGIF.add(label,new Integer(0));

            this.setPreferredSize(new Dimension(this.widthGif,this.heightGif));
            this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));

            this.add(jlpGIF, BorderLayout.CENTER);

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void reset() {
        pnlForeground.setLocation(0,0);
    }
}
