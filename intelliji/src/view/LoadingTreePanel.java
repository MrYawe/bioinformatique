package view;

import tree.TreeBuilderService.OrganismType;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.net.URL;

/**
 * Created by saetlan on 16/03/17.
 */
public class LoadingTreePanel extends JPanel {

    private JPanel backgroundPanel;
    private int widthGif;
    private int heightGif;
    private OrganismType type;
    private int percent;
    JLabel jlbTypePercent;

    public OrganismType getType() {
        return this.type;
    }

    public void setPercent(int p) {
        this.percent = p;
    }

    public void updatePercent() {
        jlbTypePercent.setText(this.type.toString().toLowerCase() + " : "+ percent + "%");
    }

    public JPanel getBackgroundPanel() {
        return backgroundPanel;
    }

    public int getWidthGif() { return this.widthGif; }

    public LoadingTreePanel(OrganismType type) {
        this.type = type;
        JPanel pnlMain = new JPanel(new BorderLayout());
        backgroundPanel = new JPanel();

        this.setLayout(new BorderLayout());
        try
        {
            URL path = MainFrameAcryl.class.getClass().getResource("/img/brinFinal.gif");
            ImageIcon image = new ImageIcon(path);
            this.widthGif = image.getIconWidth()/3;
            this.heightGif = image.getIconHeight()/3;
            image.setImage(image.getImage().getScaledInstance(widthGif, heightGif, Image.SCALE_DEFAULT));
            this.widthGif = 350;
            /////////this.setPreferredSize(new Dimension(widthGif,heightGif));

            JLabel label = new JLabel(image);
            JLayeredPane jlpGIF = new JLayeredPane();
            jlpGIF.setPreferredSize(new Dimension(widthGif,heightGif));



            label.setPreferredSize(new Dimension(widthGif,heightGif));
            jlpGIF.setPreferredSize(label.getPreferredSize());
            pnlMain.add(jlpGIF, BorderLayout.CENTER);

            backgroundPanel.setBackground(Color.LIGHT_GRAY);
            backgroundPanel.setLocation(0, 0);
            backgroundPanel.setSize(new Dimension(widthGif,heightGif));
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

        jlbTypePercent = new JLabel(this.type.toString().toLowerCase() + " : "+ percent + "%");
        pnlMain.add(jlbTypePercent, BorderLayout.NORTH);

        this.add(pnlMain, BorderLayout.CENTER);
    }


}
