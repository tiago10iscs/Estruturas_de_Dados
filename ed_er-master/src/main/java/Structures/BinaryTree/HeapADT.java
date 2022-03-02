package Structures.BinaryTree;

/**
 *
 * @author Pedro Machado "pedroma2000"
 */
public interface HeapADT<T> extends BinaryTreeADT<T> {

    /**
     * Adds the specified object to this heap.
     *
     * @param obj the element to added to this head
     */
    public void addElement(T obj);

    /**
     * Removes element with the lowest value from this heap.
     *
     * @return the element with the lowest value from this heap
     * @throws BinaryTreeExceptions BinaryTreeExceptions
     */
    public T removeMin() throws BinaryTreeExceptions;

    /**
     * Returns a reference to the element with the lowest value in
     * this heap.
     *
     * @return a reference to the element with the lowest value
     * in this heap
     */
    public T findMin();

}
