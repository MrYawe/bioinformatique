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

    //////////////////////////////////////////
    //     INSTANCE MEMBER DECLARATION      //
    //////////////////////////////////////////
    //private JPanel pnlBackground;
    //private int widthGif;
    //private int heightGif;
    private LoadingPanel pnlLoadingTree;

    private OrganismType type;
    private int percent;
    JLabel lblTypePercent;

    //////////////////////////////////////////
    //          GETTERS AND SETTERS         //
    //////////////////////////////////////////
    public OrganismType getType() { return this.type; }

    JPanel getPnlForeground() {
        return pnlLoadingTree.getPnlForeground();
    }

    int getWidthGif() { return pnlLoadingTree.getWidthGif(); }

    void setPercent(int p) { this.percent = p; }

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

        //////////////////////////////////////////
        //             INSTANTIATION            //
        //////////////////////////////////////////
        if(type==null)
            pnlLoadingTree = new LoadingPanel(true);
        else
            pnlLoadingTree = new LoadingPanel(false);

        pnlMain = new JPanel(new BorderLayout());
        String base = getType()!=null?getType().toString().toLowerCase():"Computing stats";
        lblTypePercent = new JLabel(base + " : "+ percent + "%");

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
            lblTypePercent.setText(base + " : "+ percent + "%");
    }

    public void reset() {
        String base = getType()!=null?getType().toString().toLowerCase():"Computing stats";
        percent=0;
        lblTypePercent.setText(base + " : "+ percent + "%");
        pnlLoadingTree.reset();
    }

}
