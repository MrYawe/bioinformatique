import config.ConfigManager;
import config.DevelopmentConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import tree.Organism;
import tree.OrganismTree;
import tree.Tree;
import tree.TreeWalker;
import view.JCheckBoxTree;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by yannis on 28/01/17.
 */
public class TreeTest {
    private Tree tree;

    @Before
    public void setup() {
        // Organism (level 4)
        Tree<String> blastocatelliaTree= new Tree<>();
        blastocatelliaTree.add("Chloracidobacterium thermophilum", null);
        blastocatelliaTree.add("Chloracidobacterium thermophilum", null);
        blastocatelliaTree.add("Pyrinomonas methylaliphatogenes", null);

        Tree<String> holophagaeTree= new Tree<>();
        holophagaeTree.add("Geothrix fermentans", null);
        holophagaeTree.add("Holophaga foetida", null);

        Tree<String> solibacteresTree= new Tree<>();
        solibacteresTree.add("Bryobacter aggregatus", null);
        solibacteresTree.add("Candidatus Solibacter usitatus ", null);

        // Subgroup (level 3)
        Tree<Tree> acidobacteriaTree= new Tree<>();
        acidobacteriaTree.add("Acidobacteriia", null);
        acidobacteriaTree.add("Blastocatellia", blastocatelliaTree);
        acidobacteriaTree.add("Holophagae", holophagaeTree);
        acidobacteriaTree.add("Solibacteres", solibacteresTree);
        acidobacteriaTree.add("unclassified Acidobacteria", null);

        // Group (level 2)
        Tree<Tree> bacteriaTree= new Tree<>();
        bacteriaTree.add("Acidobacteria", acidobacteriaTree);
        bacteriaTree.add("Aquificae", null);
        bacteriaTree.add("Caldiserica", null);

        // Kingdom (level 1)
        Tree<Tree> kingdomTree = new Tree<>();
        kingdomTree.add("Archaea", null);
        kingdomTree.add("Bacteria", bacteriaTree);
        kingdomTree.add("Eukaryota", null);
        kingdomTree.add("Viruses", null);

        this.tree = kingdomTree;

        //Setup
        ConfigManager.setConfig(new DevelopmentConfig());
        OrganismTree.load();
    }

    @Test
    public void testGet() throws Exception
    {
        Object[] nodes = ((Tree<?>) ((Tree<?>) (((Tree<?>)
                tree
                .get("Bacteria"))
                .get("Acidobacteria")))
                .get("Holophagae"))
                .nodes();
        Assert.assertEquals("Geothrix fermentans", nodes[0]);
        Assert.assertEquals("Holophaga foetida", nodes[1]);
    }

    @Test
    public void testNbActivated()
    {
        Assert.assertEquals(3, OrganismTree.getInstance().activatedNodes().length);
    }

    @Test
    public void testUpdateNode()
    {
        Organism org = new Organism("bla", "bla", "bla", "bla", "bla", "bla", "bla");

        org.setActivated(true);
        OrganismTree.getInstance().add("bla", org);
        Assert.assertEquals(4, OrganismTree.getInstance().activatedNodes().length);
        org.setActivated(false);
        Assert.assertEquals(3, OrganismTree.getInstance().activatedNodes().length);
    }

    @Test
    public void testJTree()
    {
        Organism org = new Organism("bla", "bla", "bla", "bla", "bla", "bla", "bla");
        OrganismTree.getInstance().add("bla", org);

        JCheckBoxTree jtree = new JCheckBoxTree(new DefaultMutableTreeNode(org));

        DefaultMutableTreeNode root = (DefaultMutableTreeNode) jtree.getModel().getRoot();
        Organism orgJtree = (Organism) root.getUserObject();
        Assert.assertTrue(org == orgJtree); // reference check

        orgJtree.setActivated(true);
        Assert.assertTrue(org.getActivated());
        Assert.assertEquals(4, OrganismTree.getInstance().activatedNodes().length);

        orgJtree.setActivated(false);
        Assert.assertFalse(org.getActivated());
        Assert.assertEquals(3, OrganismTree.getInstance().activatedNodes().length);
    }
}
