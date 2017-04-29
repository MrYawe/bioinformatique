package tree;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SimpleTreeWalker {
    private Queue<Organism> organisms;
    private static Lock mainLock;

    public Queue<Organism> getOrganisms()
    {
        return organisms;
    }

    public void setOrganisms(Queue<Organism> organisms)
    {
        this.organisms = organisms;
    }

    public SimpleTreeWalker(Tree t){
        mainLock = new ReentrantLock();
        mainLock.lock();
        this.organisms = new ConcurrentLinkedDeque<>();
        this.toQueue(t);
        mainLock.unlock();
    }

    private void toQueue(Tree t){
        for (Object o : t.nodes())
        {
            if (t.isLeaf((String) o))
            {
                this.organisms.add((Organism) t.get((String) o));
            }
            else
            {
                toQueue((Tree) t.get((String) o));
            }
        }
    }

    public boolean hasNext(){
        mainLock.lock();
        boolean res = ! this.organisms.isEmpty();
        mainLock.unlock();
        return res;
    }

    public Organism next(){
        mainLock.lock();
        Organism res = this.organisms.poll();
        mainLock.unlock();
        return res;
    }

}

