package view;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.scijava.swing.checkboxtree.CheckBoxNodeData;
import org.scijava.swing.checkboxtree.CheckBoxNodeEditor;
import org.scijava.swing.checkboxtree.CheckBoxNodeRenderer;
import tree.Tree;

import java.awt.*;
import java.util.Arrays;

/**
 * Created by germain on 21/03/17.
 */
public class TreePanel extends JPanel {

    private JScrollPane panel;

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
        return createCheckboxTree(root);
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
            //DefaultMutableTreeNode newTree = new DefaultMutableTreeNode(nodeName);
            DefaultMutableTreeNode newTree = addToCheckBoxTree(cur, nodeName.toString(), true);

            for(Object it : nodes)
            {
                newTree = buildTreeAux(newTree, it, subTree);
            }
            //cur.addToCheckBoxTree(newTree);
            return cur;
        }
        //Si ce n'est pas un noeud, on l'ajoute et on retourne le tout
        else
        {
            //cur.addToCheckBoxTree(new DefaultMutableTreeNode(nodeName));
            addToCheckBoxTree(cur, nodeName.toString(), true);
            return cur;
        }
    }

    public static JTree createCheckboxTree(DefaultMutableTreeNode root) {
        final DefaultTreeModel treeModel = new DefaultTreeModel(root);
        final JTree tree = new JTree(treeModel);

        final CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
        tree.setCellRenderer(renderer);

        final CheckBoxNodeEditor editor = new CheckBoxNodeEditor(tree);
        tree.setCellEditor(editor);
        tree.setEditable(true);

        // listen for changes in the selection
        tree.addTreeSelectionListener(new TreeSelectionListener() {

            @Override
            public void valueChanged(final TreeSelectionEvent e) {
                UIManager.writeLog(System.currentTimeMillis() + ": selection changed");
            }
        });

        // listen for changes in the model (including check box toggles)
        treeModel.addTreeModelListener(new TreeModelListener() {

            @Override
            public void treeNodesChanged(final TreeModelEvent e) {
                //Repercuter les modifs sur les enfants
                //DefaultMutableTreeNode test = (DefaultMutableTreeNode)fils;
                //test.setChecked(false);
                UIManager.writeLog("" + e.getChildren().length);
            }

            @Override
            public void treeNodesInserted(final TreeModelEvent e) {
                UIManager.writeLog(System.currentTimeMillis() + ": nodes inserted");
            }

            @Override
            public void treeNodesRemoved(final TreeModelEvent e) {
                UIManager.writeLog(System.currentTimeMillis() + ": nodes removed");
            }

            @Override
            public void treeStructureChanged(final TreeModelEvent e) {
                UIManager.writeLog(System.currentTimeMillis() + ": structure changed");
            }
        });

        return tree;
    }

    private static DefaultMutableTreeNode addToCheckBoxTree(
        final DefaultMutableTreeNode parent,
        final String text,
        final boolean checked
    )
    {
        final CheckBoxNodeData data = new CheckBoxNodeData(text, checked);
        final DefaultMutableTreeNode node = new DefaultMutableTreeNode(data);
        parent.add(node);
        return node;
    }
}
