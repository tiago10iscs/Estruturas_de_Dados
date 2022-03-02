package Structures.Stack;

/**
 *
 * @author Pedro Machado "pedroma2000"
 */
public class LinkedStack<T> implements StackADT<T> {

    private LinearNode<T> top;
    private int size = 0;

    public LinkedStack() {
        top = null;
    }

    @Override
    public void push(T element) {
        if(size() == 0){
            this.top = new LinearNode<T>(element, null);
        }else{
            LinearNode<T> tmp = new LinearNode<T>(element, top);
            top = tmp;
        }
        this.size++;
    }

    @Override
    public T pop() throws EmptyCollectionException {
        if(isEmpty()){
            throw new EmptyCollectionException(EmptyCollectionException.EMPTYCOLLECTION);
        }else{
            LinearNode<T> currentTop = top;
            top = top.getNext();
            this.size--;
            return currentTop.getData();
        }
    }

    @Override
    public T peek() throws EmptyCollectionException {
        if(isEmpty()){
            throw new EmptyCollectionException(EmptyCollectionException.EMPTYCOLLECTION);
        }else{
            return top.getData();
        }
    }

    @Override
    public boolean isEmpty() {
        if(size == 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        String text = "";
        LinearNode<T> tmp = top;

        while (tmp != null){
            text+= "Node Hash: " + tmp.hashCode() + " ------> Content: " + tmp.getData() + "\n";
            tmp = tmp.getNext();
        }

        return text;
    }
}
