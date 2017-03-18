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

    public static JPanel backgroundPanel;
    private JScrollPane westPanel;
    private ConsolePanel pnlConsole;

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


    public static void addProgress() {
        backgroundPanel.setLocation((int)backgroundPanel.getLocation().getX()+1,0);
    }

    public MainFrameAcryl() {
        super("Bioinformatique");
        backgroundPanel = new JPanel();

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
        pnlConsole.println("Bonjour yannis", Color.RED);

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

            //Fenster too large (Germain - Win10)
            //this.pack();
            this.setVisible(true);

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
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