import tree.Tree;

public class Main {


    public static void main(String[] args) {

        // Organism
        Tree<Tree> blastocatelliaTree= new Tree();
        blastocatelliaTree.add("Chloracidobacterium thermophilum", null);
        blastocatelliaTree.add("Chloracidobacterium thermophilum", null);
        blastocatelliaTree.add("Pyrinomonas methylaliphatogenes", null);

        Tree<Tree> holophagaeTree= new Tree();
        holophagaeTree.add("Geothrix fermentans", null);
        holophagaeTree.add("Holophaga foetida", null);

        Tree<Tree> solibacteresTree= new Tree();
        solibacteresTree.add("Bryobacter aggregatus", null);
        solibacteresTree.add("Candidatus Solibacter usitatus ", null);

        // Subgroup
        Tree<Tree> acidobacteriaTree= new Tree();
        acidobacteriaTree.add("Acidobacteriia", null);
        acidobacteriaTree.add("Blastocatellia", blastocatelliaTree);
        acidobacteriaTree.add("Holophagae", holophagaeTree);
        acidobacteriaTree.add("Solibacteres", solibacteresTree);
        acidobacteriaTree.add("unclassified Acidobacteria", null);

        // Group
        Tree<Tree> bacteriaTree= new Tree();
        bacteriaTree.add("Acidobacteria", acidobacteriaTree);
        bacteriaTree.add("Aquificae", null);
        bacteriaTree.add("Caldiserica", null);

        // Kingdom
        Tree kingdomTree = new Tree();
        kingdomTree.add("Archaea", null);
        kingdomTree.add("Bacteria", bacteriaTree);
        kingdomTree.add("Eukaryota", null);
        kingdomTree.add("Viruses", null);

        kingdomTree.printTree();
    }
}
