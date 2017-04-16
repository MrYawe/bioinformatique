import config.ConfigManager;
import config.DevelopmentConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import tree.Organism;
import tree.OrganismTree;
import tree.Tree;
import view.JCheckBoxTree;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

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

    @Test
    public void testDupicatedOrganism()
    {
        ArrayList<Organism> list = new ArrayList<Organism>(){
            {
                add(new Organism("VIRUSES", "dsDNA_viruses,_no_RNA_stage", "Adenoviridae", "Bovine_adenovirus_2", "", "", ""));
                add(new Organism("VIRUSES", "dsDNA_viruses,_no_RNA_stage", "Adenoviridae", "Bovine_adenovirus_3", "", "", ""));

                add(new Organism("VIRUSES", "dsDNA_viruses,_no_RNA_stage", "Adenoviridae", "Human_adenovirus_54", "", "", ""));
                add(new Organism("VIRUSES", "dsDNA_viruses,_no_RNA_stage", "Adenoviridae", "Human_mastadenovirus_A", "", "", ""));
                add(new Organism("VIRUSES", "fake_group", "Adenoviridae", "Human_mastadenovirus_B", "", "", ""));

                add(new Organism("JPP", "fake_group", "fake_sub", "Blattabacterium_sp._(Nauphotea_cinerea)", "", "", ""));
                add(new Organism("JPP", "fake_group", "fake_sub", "Blattabacterium_sp._(Blatta_orientalis)_str._Tarazona", "", "", ""));
            }
        };
        Assert.assertTrue(list.get(0).isDuplicatedOrganism(list.get(1)));
        Assert.assertFalse(list.get(0).isDuplicatedOrganism(list.get(2)));

        Assert.assertFalse(list.get(2).isDuplicatedOrganism(list.get(3)));
        Assert.assertFalse(list.get(2).isDuplicatedOrganism(list.get(4)));

        Assert.assertTrue(list.get(5).isDuplicatedOrganism(list.get(6)));
        Assert.assertTrue(list.get(6).isDuplicatedOrganism(list.get(5)));

        // Corchorus_yellow_vein_virus_-_[Hoa?(?=[_][0-9A-Z(])
        // Is Cryptococcus_neoformans_var._neoformans_B-3501A a duplication of Cryptococcus_neoformans_var._grubii_H99 ?
    }
}
