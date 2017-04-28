package view;

/**
 * Created by germain on 05/02/2017.
 **/
import sun.applet.Main;
import tree.Organism;
import tree.OrganismTree;
import tree.SelectedTreeWalker;
import tree.Tree;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import tree.TreeBuilderService.OrganismType;

public class MainFrameAcryl extends JFrame {

    private TreePanel pnlTree;
    private ConsolePanel pnlConsole;

    private LoadingTreePanel pnlLoadingMain;
    private LoadingTreePanel pnlLoadingEukaryote;
    private LoadingTreePanel pnlLoadingProkaryote;
    private LoadingTreePanel pnlLoadingVirus;
    private LoadingTreePanel pnlRun;

    private JButton btnLoadTree;
    private JButton btnRun;

    private JRadioButton rdbKeepAll;
    private JRadioButton rdbKeepNothing;
    private JRadioButton rdbCompute;
    private JRadioButton rdbDownloadOnly;

    private boolean computeStatsOnSelectedOrganisms = false;
    private boolean keepFilesOfSelectedOrganisms = false;

    //////////////////////////////////////////
    //               ACCESSORS              //
    //////////////////////////////////////////
    public boolean isComputeStatsOnSelectedOrganismsEnabled()
    {
        return computeStatsOnSelectedOrganisms;
    }

    public void setComputeStatsOnSelectedOrganisms(boolean computeStatsOnSelectedOrganisms)
    {
        this.computeStatsOnSelectedOrganisms = computeStatsOnSelectedOrganisms;
    }

    public boolean isKeepFilesOfSelectedOrganismsEnabled()
    {
        return keepFilesOfSelectedOrganisms;
    }

    public void setKeepFilesOfSelectedOrganisms(boolean keepFilesOfSelectedOrganisms)
    {
        this.keepFilesOfSelectedOrganisms = keepFilesOfSelectedOrganisms;
    }

    public JRadioButton[] getRadioBtns() {
        return new JRadioButton[] {rdbCompute, rdbDownloadOnly, rdbKeepAll, rdbKeepNothing};
    }

    public JButton[] getBtn() {
        JButton[] res = {btnRun,btnLoadTree};
        return res;
    }

    public LoadingTreePanel[] getLoadingTrees() {
        LoadingTreePanel[] res = {pnlLoadingEukaryote,pnlLoadingVirus,pnlLoadingProkaryote};
        return res;
    }

    public LoadingTreePanel getPnlLoadingMain(){
        return pnlLoadingMain;
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
        if(type==null){
            res=pnlLoadingMain;
        } else if(type.equals(pnlLoadingEukaryote.getType())){
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

        //////////////////////////////////////////
        //              DECLARATION             //
        //////////////////////////////////////////

        JSplitPane spTreeVSLogs;

        JPanel pnlLoadingTree;
        JPanel pnlStats;
        JPanel contentPanel;
        JPanel pnlSouth;
        JPanel pnlOptions;


        //Declaration of the menu items
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem = new JMenuItem("Quitter");


        JLabel lblDownloadBehavior;
        JLabel lblComputeBehavior;

        Dimension screenSize;
        Dimension dimension;

        //////////////////////////////////////////
        //             INSTANTIATION            //
        //////////////////////////////////////////

        //Instantiation of the menu items
        menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        menuItem = new JMenuItem("Quitter");


        contentPanel = new JPanel(new BorderLayout());
        pnlTree = new TreePanel();
        pnlConsole = new ConsolePanel();
        spTreeVSLogs = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, pnlTree, pnlConsole);
        pnlOptions = new JPanel(new GridLayout(6,1));
        pnlSouth = new JPanel(new BorderLayout());
        pnlStats = new JPanel(new BorderLayout());
        pnlLoadingTree = new JPanel(new GridLayout(3,1));


        //Instantiation of the Loaders
        pnlLoadingEukaryote = new LoadingTreePanel(OrganismType.EUKARYOTES);
        pnlLoadingProkaryote = new LoadingTreePanel(OrganismType.PROKARYOTES);
        pnlLoadingVirus = new LoadingTreePanel(OrganismType.VIRUSES);
        pnlRun = new LoadingTreePanel();
        pnlLoadingMain = new LoadingTreePanel();

        ButtonGroup btnGroupDownload = new ButtonGroup();
        ButtonGroup btnGroupBehavior = new ButtonGroup();

        lblDownloadBehavior = new JLabel("Keep genome files:");
        rdbKeepAll = new JRadioButton("Yes", false);
        rdbKeepNothing = new JRadioButton("No", true);
        lblComputeBehavior = new JLabel("Compute behavior:");
        rdbCompute = new JRadioButton("Compute statistics on selected organisms", true);
        rdbDownloadOnly = new JRadioButton("Only download the selected organisms' genomes", false);

        btnLoadTree = new JButton("Load Tree");
        btnRun = new JButton("Download and compute statistics");

        // 75% de la taille de l'écran, centré
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        dimension = Toolkit.getDefaultToolkit().getScreenSize();


        //////////////////////////////////////////
        //              PROPERTIES              //
        //////////////////////////////////////////

        spTreeVSLogs.setDividerLocation(screenSize.width / 4);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
        pnlLoadingTree.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        btnRun.setEnabled(false);



        //Setting the application to 75% of the size of the screen and center it.
        this.setSize(screenSize.width * 3/4, screenSize.height * 3/4);
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);

        //////////////////////////////////////////
        //                 LAYOUT               //
        //////////////////////////////////////////

        //Adding menu components
        menu.add(menuItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        //Setting radio buttons in groups
        btnGroupDownload.add(rdbKeepAll);
        btnGroupDownload.add(rdbKeepNothing);
        btnGroupBehavior.add(rdbCompute);
        btnGroupBehavior.add(rdbDownloadOnly);

        //Adding panels hierarchy
        this.setContentPane(contentPanel);
        contentPanel.add(spTreeVSLogs, BorderLayout.CENTER);
        contentPanel.add(pnlSouth, BorderLayout.SOUTH);

        pnlSouth.add(pnlLoadingTree, BorderLayout.WEST);
        pnlSouth.add(pnlStats, BorderLayout.CENTER);
        //pnlSouth.add(, BorderLayout.EAST);


            pnlLoadingTree.add(btnLoadTree);
            //pnlLoadingTree.add(pnlLoadingEukaryote, BorderLayout.NORTH);
            //pnlLoadingTree.add(pnlLoadingVirus, BorderLayout.CENTER);
            //pnlLoadingTree.add(pnlLoadingProkaryote, BorderLayout.SOUTH);

            pnlStats.add(pnlOptions, BorderLayout.NORTH);
            pnlStats.add(btnRun, BorderLayout.CENTER);
            pnlStats.add(pnlLoadingMain, BorderLayout.SOUTH);

                pnlOptions.add(lblDownloadBehavior);
                pnlOptions.add(rdbKeepAll);
                pnlOptions.add(rdbKeepNothing);
                pnlOptions.add(lblComputeBehavior);
                pnlOptions.add(rdbCompute);
                pnlOptions.add(rdbDownloadOnly);

        //////////////////////////////////////////
        //           ACTION LISTENERS           //
        //////////////////////////////////////////

        //Adding ActionLister to the menu items
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        btnLoadTree.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                pnlLoadingTree.add(btnLoadTree, BorderLayout.CENTER);


                // Executing the Tree
                if(btnLoadTree.isEnabled()) {
                    UIManager.lock();
                    UIManager.resetLoadingTreePanel();

                    pnlLoadingTree.remove(btnLoadTree);
                    pnlLoadingTree.add(pnlLoadingEukaryote);
                    pnlLoadingTree.add(pnlLoadingVirus);
                    pnlLoadingTree.add(pnlLoadingProkaryote);

                    MainFrameAcryl.getInstance().updateDisplayedTree(TreePanel.getDefaultTree("Loading ..."));
                    new Thread(() -> {
                        OrganismTree.load();
                        MainFrameAcryl.getInstance().updateDisplayedTree(OrganismTree.getInstance());
                        UIManager.unlock();

                        pnlLoadingTree.remove(pnlLoadingEukaryote);
                        pnlLoadingTree.remove(pnlLoadingVirus);
                        pnlLoadingTree.remove(pnlLoadingProkaryote);
                        pnlLoadingTree.add(btnLoadTree);
                    }).start();
                } else {
                    UIManager.writeError("Button is currently locked");
                }
            }
        });

        rdbKeepAll.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent itemEvent)
            {
                if (itemEvent.getStateChange() == ItemEvent.SELECTED)
                {
                    JOptionPane.showMessageDialog(MainFrameAcryl.getInstance(),
                            "Be careful, genome files are quite heavy (up to 150MB per file).\nPlease make sure you have enough space left on your hard drive.",
                            "Download warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        rdbKeepNothing.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent itemEvent)
            {
                if (itemEvent.getStateChange() == ItemEvent.SELECTED)
                {
                    rdbDownloadOnly.setSelected(false);
                    rdbCompute.setSelected(true);
                }
            }
        });

        rdbDownloadOnly.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent itemEvent)
            {
                if (itemEvent.getStateChange() == ItemEvent.SELECTED)
                {
                    rdbKeepNothing.setSelected(false);
                    rdbKeepAll.setSelected(true);
                }
            }
        });


        btnRun.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(btnRun.isEnabled()) {
                    if (rdbKeepAll.isSelected())
                    {
                        MainFrameAcryl.getInstance().setKeepFilesOfSelectedOrganisms(true);
                    }
                    else
                    {
                        MainFrameAcryl.getInstance().setKeepFilesOfSelectedOrganisms(false);
                    }
                    if (rdbCompute.isSelected())
                    {
                        MainFrameAcryl.getInstance().setComputeStatsOnSelectedOrganisms(true);
                    }
                    else
                    {
                        MainFrameAcryl.getInstance().setComputeStatsOnSelectedOrganisms(false);
                    }
                    //UIManager.lockOn(1);
                    pnlTree.updateSelectedOrganisms();

                    if (OrganismTree.getInstance().countAllActivatedNodes() == 0)
                    {
                        JOptionPane.showMessageDialog(MainFrameAcryl.getInstance(),
                                "Please select at least one organism in the tree",
                                "No organism selected",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    else
                    {
                        UIManager.lock();
                        UIManager.resetLoadingStatsPanel();
                        UIManager.setNbReplicons(OrganismTree.countReplicons());

                        new Thread(() -> {
                            OrganismTree.downloadSelectedOrganisms();
                            UIManager.unlock();
                        }).start();
                    }
                }
                else {
                    UIManager.writeError("Button is currently locked");
                }
            }
        });
    }

    public void updateDisplayedTree(Tree tree){
        pnlTree.updateDisplayedTree(tree);
    }

    public void updateDisplayedTree(JTree tree){
        pnlTree.updateDisplayedTree(tree);
    }
}