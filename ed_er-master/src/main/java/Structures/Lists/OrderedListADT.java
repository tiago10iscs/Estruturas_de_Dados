package Structures.Lists;

/**
 *
 * @author Pedro Machado "pedroma2000"
 */
public interface OrderedListADT<T> extends ListADT<T> {

    /**
     * Adds the specified element to this list at
     * the proper location
     *
     * @param element the element to be added to this list
     * @throws ListExceptions ListExceptions
     */
    public void add(T element) throws ListExceptions;
}
