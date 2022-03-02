package Structures.Lists;

/**
 *
 * @author Pedro Machado "pedroma2000"
 */
public class OrderedList<T> extends ArrayList<T>
        implements OrderedListADT<T> {

    @Override
    public void add(T element) throws ListExceptions {
        if (element instanceof Comparable) {
            if (isEmpty()) {
                list[0] = element;
            } else {

                Comparable rearPosition = (Comparable) list[rear - 1];

                if (rearPosition.compareTo(element) < 0) {
                    list[rear] = element;
                } else {

                    boolean found = false;
                    int current = 0;

                    while (current < size() && found == false) {
                        Comparable<T> tmp = (Comparable<T>) list[current];
                        if (tmp.compareTo(element) > 0) {
                            found = true;
                        } else {
                            current++;
                        }
                    }

                    if (found == true) {
                        for (int i = rear - 1; i >= current; i--) {
                            list[i + 1] = list[i];
                        }
                        list[current] = element;
                    }
                }
            }

            rear++;
            modCount++;
        } else {
            throw new ListExceptions(ListExceptions.OBJECT_NOT_COMPARABLE);
        }
    }
}
