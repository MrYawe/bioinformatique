package view;

/**
 * Created by germain on 05/02/2017.
 **/

import tree.ExampleTree;
import tree.Tree;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainFrameAcryl extends JFrame {

    private static JTree buildJTree()
    {
        Tree exampleTree = ExampleTree.getTree();
        JTree tree = new JTree();
        //TODO: Convertir l'arbre de yannis en JTree
       /* for(comp : exampleTree)
        {

        }*/
       return tree;
    }

    public MainFrameAcryl() {
        super("Minimal-Frame-Application");

        // setup menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem menuItem = new JMenuItem("Exit");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        menu.add(menuItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        // setup widgets
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
        JScrollPane westPanel = new JScrollPane(buildJTree());
        JEditorPane editor = new JEditorPane("text/plain", "BioInformatique");
        JScrollPane eastPanel = new JScrollPane(editor);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, westPanel,eastPanel);
        splitPane.setDividerLocation(148);
        contentPanel.add(splitPane, BorderLayout.CENTER);
        setContentPane(contentPanel);

        // add listeners
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // show application
        setLocation(32, 32);
        setSize(800, 600);

        //add gif
        Icon icon = new ImageIcon(System.getProperty("user.dir")+"/res/img/brinFinal.gif");
        JLabel label = new JLabel(icon);
        contentPanel.add(label, BorderLayout.SOUTH);

    }
}