package view;

/**
 * Created by germain on 05/02/2017.
 **/
import tree.Tree;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import tree.TreeBuilderService.OrganismType;

public class MainFrameAcryl extends JFrame {

    //private JPanel backgroundPanel;
    private JScrollPane westPanel;
    private ConsolePanel pnlConsole;
    private LoadingTreePanel pnlLoadingEukaryote;
    private LoadingTreePanel pnlLoadingProkaryote;
    private LoadingTreePanel pnlLoadingVirus;

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

    public LoadingTreePanel getLoadingPanel(OrganismType type) {
        LoadingTreePanel res=null;
        if(type.equals(pnlLoadingEukaryote.getType())){
            res=pnlLoadingEukaryote;
        } else if(type.equals(pnlLoadingProkaryote.getType())){
            res=pnlLoadingProkaryote;
        } else if(type.equals(pnlLoadingVirus.getType())){
            res=pnlLoadingVirus;
        }
        return res;
    }

    public MainFrameAcryl() {
        super("Bioinformatique");
        //backgroundPanel = new JPanel();

        // setup menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem menuItem = new JMenuItem("Quitter");
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

        contentPanel.add(splitPane, BorderLayout.CENTER);
        setContentPane(contentPanel);

        // add listeners
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // 75% de la taille de l'écran, centré
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width * 3/4, screenSize.height * 3/4);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);

        //JTree de largeur 1/4 de l'écran
        splitPane.setDividerLocation(screenSize.width / 4);

        //Redim westPanel
        JPanel pnlSouth = new JPanel(new BorderLayout());
        //pnlSouth.setPreferredSize(new Dimension(100,100));

        contentPanel.add(pnlSouth, BorderLayout.SOUTH);

        pnlLoadingEukaryote = new LoadingTreePanel(OrganismType.EUKARYOTES);
        pnlLoadingProkaryote = new LoadingTreePanel(OrganismType.PROKARYOTES);
        pnlLoadingVirus = new LoadingTreePanel(OrganismType.VIRUSES);

        JPanel pnlWest = new JPanel(new BorderLayout());
        pnlSouth.add(pnlWest, BorderLayout.WEST);

        pnlWest.add(pnlLoadingEukaryote, BorderLayout.NORTH);
        pnlWest.add(pnlLoadingVirus, BorderLayout.CENTER);
        pnlWest.add(pnlLoadingProkaryote, BorderLayout.SOUTH);
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