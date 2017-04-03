package view;

/**
 * Created by germain on 05/02/2017.
 **/
import tree.OrganismTree;
import tree.Tree;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import tree.TreeBuilderService.OrganismType;

public class MainFrameAcryl extends JFrame {

    //private JPanel backgroundPanel;
    private TreePanel pnlTree;
    private ConsolePanel pnlConsole;
    private LoadingTreePanel pnlLoadingEukaryote;
    private LoadingTreePanel pnlLoadingProkaryote;
    private LoadingTreePanel pnlLoadingVirus;
    private JButton btnLoadTree;
    private JButton btnRun;

    public JButton[] getBtn() {
        JButton[] res = {btnRun,btnLoadTree};
        return res;
    }

    public LoadingTreePanel[] getLoadingTrees() {
        LoadingTreePanel[] res = {pnlLoadingEukaryote,pnlLoadingVirus,pnlLoadingProkaryote};
        return res;
    }


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
        pnlTree = new TreePanel();
        pnlConsole = new ConsolePanel();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, pnlTree, pnlConsole);

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
        pnlWest.add(pnlLoadingEukaryote, BorderLayout.NORTH);
        pnlWest.add(pnlLoadingVirus, BorderLayout.CENTER);
        pnlWest.add(pnlLoadingProkaryote, BorderLayout.SOUTH);

        pnlSouth.add(pnlWest, BorderLayout.WEST);

        btnLoadTree = new JButton("Load Tree");
        btnRun = new JButton("Download selected organisms");

        btnLoadTree.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Executing the Tree
                if(UIManager.unlock0==0) {
                    UIManager.lockOn(3);
                    UIManager.resetLoadingTreePanel();
                    new Thread(() -> MainFrameAcryl.getInstance().updateDisplayedTree(OrganismTree.getInstance())).start();
                } else {
                    UIManager.writeError("Button is currently locked");
                }
            }
        });
        //pnlSouth.add(btnLoadTree, BorderLayout.CENTER);
        JPanel pnlTest = new JPanel(new GridLayout(2,1));
        pnlTest.setAlignmentY(JComponent.LEFT_ALIGNMENT);
        JCheckBox box1 = new JCheckBox("Download selected items");
        JCheckBox box2 = new JCheckBox("Compute stats on selected items");
        pnlTest.add(box1);
        pnlTest.add(box2);

        JPanel pnlTest2 = new JPanel(new BorderLayout());
        pnlTest2.add(pnlTest, BorderLayout.NORTH);
        pnlTest2.add(btnRun, BorderLayout.CENTER);


        pnlSouth.add(pnlTest2, BorderLayout.CENTER);


        btnRun.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(UIManager.unlock0==0) {
                    //UIManager.lockOn(1);
                    pnlTree.updateSelectedOrganisms();
                    new Thread(() -> OrganismTree.downloadSelectedOrganisms()).start();
                } else {
                    UIManager.writeError("Button is currently locked");
                }

            }
        });
        pnlSouth.add(btnLoadTree, BorderLayout.EAST);

    }

    public void updateDisplayedTree(Tree tree){
        pnlTree.updateDisplayedTree(tree);
    }
}