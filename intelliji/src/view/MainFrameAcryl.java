package view;

/**
 * Created by germain on 05/02/2017.
 **/

import tree.ExampleTree;
import tree.Tree;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;
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

    public JPanel backgroundPanel;


    public MainFrameAcryl() {
        super("Minimal-Frame-Application");
        backgroundPanel = new JPanel();

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
        try
        {
            URL path = MainFrameAcryl.class.getClass().getResource("/img/brinFinal.gif");
            Icon icon = new ImageIcon(path);
            JLabel label = new JLabel(icon);

            JLayeredPane jlpGIF = new JLayeredPane();
            jlpGIF.setPreferredSize(label.getPreferredSize());
            contentPanel.add(jlpGIF, BorderLayout.SOUTH);

            backgroundPanel.setBackground(Color.LIGHT_GRAY);
            backgroundPanel.setLocation(0, 0);
            backgroundPanel.setSize(label.getPreferredSize());
            jlpGIF.add(backgroundPanel, new Integer(1));

            label.setLocation(0,0);
            label.setSize(label.getPreferredSize());
            jlpGIF.add(label,new Integer(0));


            this.pack();
            this.setVisible(true);

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }
}