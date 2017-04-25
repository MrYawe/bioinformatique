package view;

import tree.TreeBuilderService.OrganismType;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;

/**
 * Created by saetlan on 16/03/17.
 */
public class LoadingTreePanel extends JPanel {

    //////////////////////////////////////////
    //     INSTANCE MEMBER DECLARATION      //
    //////////////////////////////////////////
    //private JPanel pnlBackground;
    //private int widthGif;
    //private int heightGif;
    private LoadingPanel pnlLoadingTree;

    private OrganismType type;
    private double percent;
    DecimalFormat df;
    JLabel lblTypePercent;

    //////////////////////////////////////////
    //          GETTERS AND SETTERS         //
    //////////////////////////////////////////
    public OrganismType getType() { return this.type; }

    JPanel getPnlForeground() {
        return pnlLoadingTree.getPnlForeground();
    }

    int getWidthGif() { return pnlLoadingTree.getWidthGif(); }

    void setPercent(double p) { this.percent = p; }

    public LoadingPanel getPnlLoadingTree(){
        return pnlLoadingTree;
    }

    //////////////////////////////////////////
    //              CONSTRUCTOR             //
    //////////////////////////////////////////
    LoadingTreePanel() {
        this(null);
    }

    LoadingTreePanel(OrganismType type) {
        this.type = type;
        //////////////////////////////////////////
        //              DECLARATION             //
        //////////////////////////////////////////
        JPanel pnlMain;         //Panel that will get
        df = new DecimalFormat("#.###"); //Decimal formatting to show percentage
        df.setRoundingMode(RoundingMode.CEILING);

        //////////////////////////////////////////
        //             INSTANTIATION            //
        //////////////////////////////////////////
        if(type==null)
            pnlLoadingTree = new LoadingPanel(true);
        else
            pnlLoadingTree = new LoadingPanel(false);

        pnlMain = new JPanel(new BorderLayout());
        String base = getType()!=null?getType().toString().toLowerCase():"Computing stats";
        lblTypePercent = new JLabel(base + " : "+ df.format(percent) + "%");

        //////////////////////////////////////////
        //                 LAYOUT               //
        //////////////////////////////////////////
        this.setLayout(new BorderLayout());
        pnlMain.add(lblTypePercent, BorderLayout.CENTER);
        pnlMain.add(pnlLoadingTree, BorderLayout.SOUTH);
        this.add(pnlMain, BorderLayout.CENTER);
    }

    //Fonction used to update the text of the label referring to the current loading tree
    public void updatePercent() {
        String base = getType()!=null?getType().toString().toLowerCase():"Computing stats";
        if(percent>=100)
            lblTypePercent.setText(base + " : DONE");
        else
            lblTypePercent.setText(base + " : "+ df.format(percent) + "%");
    }

    public void reset() {
        String base = getType()!=null?getType().toString().toLowerCase():"Computing stats";
        percent=0;
        lblTypePercent.setText(base + " : "+ df.format(percent) + "%");
        pnlLoadingTree.reset();
    }

}
