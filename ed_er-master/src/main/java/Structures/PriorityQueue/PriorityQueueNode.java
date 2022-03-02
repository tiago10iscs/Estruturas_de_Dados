package Structures.PriorityQueue;

/**
 *
 * @author Pedro Machado "pedroma2000"
 */
public class PriorityQueueNode<T> implements Comparable<PriorityQueueNode> {

    private static int nextorder = 0;// BIG BRAIN
    private int priority;
    private int order;
    private T element;

    public PriorityQueueNode(T obj, int prio) {
        element = obj;
        priority = prio;
        order = nextorder;
        nextorder++;
    }

    public T getElement() {
        return element;
    }

    public int getPriority() {
        return priority;
    }

    public int getOrder() {
        return order;
    }

    @Override
    public String toString() {
        String temp = ("Elemento: " + element.toString() + " Prioridade: " + priority + " Ordem: " + order);
        return temp;
    }

    @Override
    public int compareTo(PriorityQueueNode o) {
        int result;

        PriorityQueueNode<T> temp = o;

        if (priority > temp.getPriority()) {
            result = 1;
        } else if (priority < temp.getPriority()) {
            result = -1;
        } else if (order > temp.getOrder()) {
            result = 1;
        } else {
            result = -1;
        }

        return result;
    }
}
