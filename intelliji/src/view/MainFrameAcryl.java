package view;

/**
 * Created by germain on 05/02/2017.
 **/
import tree.Tree;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class MainFrameAcryl extends JFrame {

    //private JPanel backgroundPanel;
    private JScrollPane westPanel;
    private ConsolePanel pnlConsole;
    private LoadingTreePanel pnlLoading;

    private static MainFrameAcryl instance;

    public static MainFrameAcryl getInstance() {
        if(instance==null) {
            instance = new MainFrameAcryl();
        }
        return instance;
    }

    public ConsolePanel getConsole() {
        return this.pnlConsole;
    }

    public JPanel getBackgroundPanel() {
        return this.pnlLoading.getBackgroundPanel();
    }

    public MainFrameAcryl() {
        super("Bioinformatique");
        //backgroundPanel = new JPanel();

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
        //JTree
        //new JScrollPane(buildJTree(tree));
        westPanel = new JScrollPane(getDefaultTree());
        pnlConsole = new ConsolePanel();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, westPanel,pnlConsole);
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

        pnlLoading = new LoadingTreePanel();
        contentPanel.add(pnlLoading, BorderLayout.SOUTH);
    }

    public void updateDisplayedTree(Tree tree)
    {
        this.westPanel.setViewportView(buildJTree(tree));
    }

    private static JTree getDefaultTree(){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Chargement ...");
        return new JTree(root);
    }

    private static JTree buildJTree(Tree mainTree) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Type");
        Object[] nodes = mainTree.nodes();
        Arrays.sort(nodes);
        for(Object nodeName : nodes)
        {
            root = buildTreeAux(root, nodeName, mainTree);
        }
        JTree res = new JTree(root);
        return new JTree(root);
    }

    private static DefaultMutableTreeNode buildTreeAux(DefaultMutableTreeNode cur, Object nodeName, Tree tree)
    {
        //Récupère l'objet associé au String du noeud
        Object nodeObj = tree.get((String) nodeName);
        //S'il c'est un arbre qui contient des nodes, on les ajoute récursivement
        if (nodeObj != null && nodeObj instanceof Tree) {
            Tree subTree = (Tree<?>) nodeObj;
            Object[] nodes = subTree.nodes();
            Arrays.sort(nodes);
            DefaultMutableTreeNode newTree = new DefaultMutableTreeNode(nodeName);
            for(Object it : nodes)
            {
                newTree = buildTreeAux(newTree, it, subTree);
            }
            cur.add(newTree);
            return cur;
        }
        //Si ce n'est pas un noeud, on l'ajoute et on retourne le tout
        else
        {
            cur.add(new DefaultMutableTreeNode(nodeName));
            return cur;
        }
    }
}