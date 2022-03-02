package Structures.Lists;

/**
 *
 * @author Pedro Machado "pedroma2000"
 */
public class UnorderedArray<T> extends ArrayList<T> implements UnorderedListADT<T> {

    @Override
    public void addToFront(T element) {
        if (isEmpty()) {
            list[0] = element;
        } else {
            if (rear == list.length) {
                expandCapacity();
            }

            int first = 0;
            for (int i = rear - 1; i >= first; i--) {

                list[i + 1] = list[i];

            }
            list[first] = element;
        }
        rear++;
        modCount++;
    }

    @Override
    public void addToRear(T element) {
        if (isEmpty()) {
            list[0] = element;
        } else {
            if (rear == list.length) {
                expandCapacity();
            }

            list[rear] = element;
        }
        rear++;
        modCount++;
    }

    @Override
    public void addAfter(T element, T target) throws ListExceptions {
        if (!isEmpty()) {
            if (rear == list.length) {
                expandCapacity();
            }

            boolean found = false;
            int current = 0;

            while (current < size() && found == false) {
                if (list[current].equals(target)) {
                    found = true;
                } else {
                    current++;
                }
            }

            if (found == true) {
                for (int i = rear - 1; i > current; i--) {
                    list[i + 1] = list[i];
                }
                list[current + 1] = element;
                rear++;
                modCount++;
            } else {
                throw new ListExceptions(ListExceptions.ELEMENT_NOT_FOUND);
            }
        } else {
            throw new ListExceptions(ListExceptions.EMPTY_LIST);
        }
    }

}
