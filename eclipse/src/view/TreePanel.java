package view;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import tree.Organism;
import tree.Tree;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by germain on 21/03/17.
 */
public class TreePanel extends JPanel {

    private JScrollPane panel;
    private JCheckBoxTree currentTree = null;

    public TreePanel()
    {
        super();
        panel = new JScrollPane(getDefaultTree("Click on Load Tree..."));
        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);
    }

    public void updateDisplayedTree(Tree tree){
        currentTree = buildJTree(tree);
        panel.setViewportView(currentTree);
    }

    public void updateDisplayedTree(JTree tree){
        panel.setViewportView(tree);
    }

    public static JTree getDefaultTree(String text){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(text);
        return new JTree(root);
    }

    public void updateSelectedOrganisms()
    {
        if(currentTree == null)
        {
            UIManager.writeError("L'arbre n'a pas été initialisé");
        }
        else
        {
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) currentTree.getModel().getRoot();
            walkTroughTree(root);
        }
    }

    private void walkTroughTree(DefaultMutableTreeNode currentNode)
    {

        if(currentNode.isLeaf())
        {
            Organism org = (Organism) currentNode.getUserObject();
            org.setActivated(currentTree.isChecked(currentNode));
        }
        for(int i = 0 ; i < currentNode.getChildCount() ; i++)
        {
            walkTroughTree((DefaultMutableTreeNode)currentNode.getChildAt(i));
        }
    }

    private JCheckBoxTree buildJTree(Tree mainTree) {
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
            Organism org = (Organism)nodeObj;
            cur.add(new DefaultMutableTreeNode(org));
            return cur;
        }
    }
}
