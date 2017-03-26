package view;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import tree.Organism;
import tree.Tree;
import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by germain on 21/03/17.
 */
public class TreePanel extends JPanel {

    private JScrollPane panel;
    private HashMap<Organism, Boolean> organisms = new HashMap<Organism, Boolean>();

    public TreePanel()
    {
        super();
        panel = new JScrollPane(getDefaultTree());
        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);
    }

    public void updateDisplayedTree(Tree tree){
        panel.setViewportView(buildJTree(tree));
    }

    public Set<Organism> getOrganisms()
    {
        return getKeysByValue(organisms, true);
    }

    public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
        return map.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), value))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    private JTree getDefaultTree(){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Chargement ...");
        return new JTree(root);
    }

    private JTree buildJTree(Tree mainTree) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Organismes");
        Object[] nodes = mainTree.nodes();
        Arrays.sort(nodes);
        for(Object nodeName : nodes)
        {
            root = buildTreeAux(root, nodeName, mainTree);
        }
        JCheckBoxTree res = new JCheckBoxTree(root);
        return res;
    }

    private DefaultMutableTreeNode buildTreeAux(DefaultMutableTreeNode cur, Object nodeName, Tree tree)
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
            //TODO: Peut etre true par defaut & trouvé le nom organisme (tostring ou getName) & update les check
            Organism org = (Organism)nodeObj;
            organisms.put(org, false);
            cur.add(new DefaultMutableTreeNode(nodeName));
            return cur;
        }
    }
}
