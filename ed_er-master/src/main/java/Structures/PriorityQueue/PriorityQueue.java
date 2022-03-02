package Structures.PriorityQueue;

import Structures.BinaryTree.ArrayMinHeap;
import Structures.BinaryTree.BinaryTreeExceptions;

/**
 *
 * @author Pedro Machado "pedroma2000"
 */
public class PriorityQueue<T> extends ArrayMinHeap<PriorityQueueNode<T>> {

    public PriorityQueue() {
    }

    public void addElement(T object, int priority) {
        PriorityQueueNode<T> node = new PriorityQueueNode<T>(object, priority);
        super.addElement(node);
    }

    public T removeNext() throws BinaryTreeExceptions {
        PriorityQueueNode<T> temp = (PriorityQueueNode<T>) super.removeMin();
        return temp.getElement();
    }

}
