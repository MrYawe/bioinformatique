package tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Tree<T> {

    private HashMap<String, T> nodes;
    int size = -1;

    public Tree() {
        this.nodes = new HashMap<String, T>();
    }

    public boolean contains(String node) {
        return this.nodes.containsKey(node);
    }

    public boolean add(String node, T obj) {
        if (!this.contains(node)) {
            this.nodes.put(node, obj);
            return true;
        } else {
            return false;
        }
    }

    public T get(String node) {
        return this.nodes.get(node);
    }

    public Object[] nodes() {
        return this.nodes.keySet().toArray();
    }

    public Object[] activatedNodes() {
        List<Object> res = new ArrayList<>();
        for(String key : nodes.keySet()) {
            Object curr = nodes.get(key);
            if(curr.getClass().equals(Organism.class) && ((Organism) curr).getActivated()) {
                res.add(key);
            }
            else if(!curr.getClass().equals(Organism.class)) {
                res.add(key);
            }
        }
        return res.toArray();
    }

    public int countAllActivatedNodes() {
        int res = 0;
        for(String key : nodes.keySet()) {
            Object curr = nodes.get(key);
            if(curr.getClass().equals(Organism.class) && ((Organism) curr).getActivated()) {
                res += 1;
            }
            else if (!curr.getClass().equals(Organism.class)) {
                Tree t = (Tree) curr;
                res += t.countAllActivatedNodes();
            }
        }
        return res;
    }

    public void printTreeAtLevel(int level) {
        if (this != null) {
            Object[] nodess = this.nodes();
            for (Object node : nodess) {
                for (int i = 0; i < level; i++) {
                    System.out.print("-");
                }
                System.out.println(node);

                if (this.get((String) node) != null && (this.get((String) node)) instanceof Tree) {
                    ((Tree<?>) this.get((String) node)).printTreeAtLevel(level + 1);
                }
            }
        }
    }

    public void printTree() {
        printTreeAtLevel(0);
    }

    public int computeSize() {
        int size = 0;
        for (Object a : this.nodes()) {
            String node = (String) a;
            if (this.isLeaf(node)) {
                Organism o = (Organism) this.get(node);
                size += o.size();
            } else {
                Tree t = (Tree) this.get(node);
                size += t.size();
            }
        }
        this.size = size;
        return size;
    }

    public int size() {
        return this.computeSize();
    }

    public boolean isLeaf(String node) {
        return !(this.nodes.get(node) instanceof Tree);
    }

}
