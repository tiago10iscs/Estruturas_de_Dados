package Structures.Stack;

/**
 *
 * @author Pedro Machado "pedroma2000"
 */
public class LinearNode<T> {
    T data;
    LinearNode<T> next;

    public LinearNode(T data, LinearNode<T> next) {
        this.data = data;
        this.next = next;
    }

    public LinearNode<T> getNext() {
        return next;
    }

    public void setNext(LinearNode<T> next) {
        this.next = next;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LinkNode{" +
                "data=" + data +
                '}';
    }
}
