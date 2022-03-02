package Structures.Lists;

/**
 *
 * @author Pedro Machado "pedroma2000"
 */
public interface UnorderedListADT<T> extends ListADT<T> {

    /**
     * Adds the specified element to the front of
     * this list at
     * the proper location
     *
     * @param element the element to be added to this list
     */
    public void addToFront(T element);

    /**
     * Adds the specified element to the rear of
     * this list at
     * the proper location
     *
     * @param element the element to be added to this list
     */
    public void addToRear(T element);

    /**
     * Adds the specified element to the position next of
     * the target element
     * the proper location
     * @param element the element to be added to this list
     * @param target the element to be previous to the added
     * @throws ListExceptions ListExceptions
     */
    public void addAfter(T element, T target) throws ListExceptions;

}
