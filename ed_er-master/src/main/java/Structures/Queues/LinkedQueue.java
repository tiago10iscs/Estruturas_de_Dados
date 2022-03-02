package Structures.Queues;

/**
 *
 * @author Pedro Machado "pedroma2000"
 */
public class LinkedQueue<T> implements QueueADT<T> {

    private LinearNode<T> front;
    private LinearNode<T> rear;
    private int size = 0;

    public LinkedQueue() {
        front = null;
        rear = null;
    }

    @Override
    public void enqueue(T element) {
        if (size() == 0) {
            front = new LinearNode<>(element, null);
            rear = front;
        } else {
            rear.setNext(new LinearNode<>(element, null));
            rear = rear.getNext();
        }

        size++;
    }

    @Override
    public T dequeue() {
        T tmp = null;

        if (size() == 1) {
            tmp = front.getData();
            front = rear = null;
            size--;
        } else if (size() != 0){
            tmp = front.getData();
            front = front.getNext();
            size--;
        }

        return tmp;
    }

    @Override
    public T first() {
        if (size() == 0) {
            return null;
        }
        return front.getData();

    }

    @Override
    public boolean isEmpty() {
        return (size() == 0);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        LinearNode<T> current = front;
        String text = "";

        while (current != null) {
            text += "\n" + current.toString();
            current = current.getNext();
        }

        return text;
    }
}
