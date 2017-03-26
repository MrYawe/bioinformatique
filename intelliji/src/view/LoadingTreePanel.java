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
    LoadingTreePanel(OrganismType type) {
        //////////////////////////////////////////
        //              DECLARATION             //
        //////////////////////////////////////////
        JPanel pnlMain;         //Panel that will get

        //////////////////////////////////////////
        //             INSTANTIATION            //
        //////////////////////////////////////////
        this.type = type;       //type referring to the OrganismType of the loading Panel
        pnlLoadingTree = new LoadingPanel();
        pnlMain = new JPanel(new BorderLayout());
        lblTypePercent = new JLabel(this.type.toString().toLowerCase() + " : "+ percent + "%");

        //////////////////////////////////////////
        //                 LAYOUT               //
        //////////////////////////////////////////
        this.setLayout(new BorderLayout());
        pnlMain.add(lblTypePercent, BorderLayout.NORTH);
        pnlMain.add(pnlLoadingTree, BorderLayout.CENTER);
        this.add(pnlMain, BorderLayout.CENTER);
    }

    //Fonction used to update the text of the label referring to the current loading tree
    public void updatePercent() {
        if(percent==100)
            lblTypePercent.setText(getType().toString().toLowerCase() + " : DONE");
        else
            lblTypePercent.setText(getType().toString().toLowerCase() + " : "+ percent + "%");
    }

}
