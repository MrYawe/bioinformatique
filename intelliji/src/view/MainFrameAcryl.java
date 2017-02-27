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
import javax.swing.tree.DefaultMutableTreeNode;

public class MainFrameAcryl extends JFrame {

    private static JTree buildJTree(Tree tree) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Type");
        Object[] nodes = tree.nodes();
        for(Object nodeName : nodes)
        {
            root = buildTreeAux(root, nodeName, tree);
        }
        return new JTree(root);
    }

    private static DefaultMutableTreeNode buildTreeAux(DefaultMutableTreeNode cur, Object nodeName, Tree tree)
    {
        Object nodeObj = tree.get((String) nodeName);
        //S'il c'est un arbre qui contient des nodes, on les ajoute recursivement
        if (nodeObj != null && nodeObj instanceof Tree) {
            Tree tmp = (Tree<?>) nodeObj;
            Object[] nodes = tmp.nodes();
            DefaultMutableTreeNode newTree = new DefaultMutableTreeNode(nodeName);
            for(Object it : nodes)
            {
                newTree = buildTreeAux(newTree, it, tree);
            }
            cur.add(newTree);
            return cur;
        }
        else
        {
            //Si c'est pas un noeud, on l'ajoute et on retourne le tout
            cur.add(new DefaultMutableTreeNode(nodeName));
            return cur;
        }
    }

    public JPanel backgroundPanel;

    public MainFrameAcryl(Tree tree) {
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
        JScrollPane westPanel = new JScrollPane(buildJTree(tree));
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