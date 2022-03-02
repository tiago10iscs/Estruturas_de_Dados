package Structures.Lists;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 *
 * @author Pedro Machado "pedroma2000"
 */
abstract class ArrayList<T> implements ListADT<T> {

    protected T[] list;
    private final static int DEFAULT_CAPACITY = 10;
    protected int rear;
    protected int modCount;

    public ArrayList() {
        list = (T[]) (new Object[DEFAULT_CAPACITY]);
        rear = 0;
        modCount = 0;
    }

    public ArrayList(int capacity) {
        list = (T[]) (new Object[capacity]);
        rear = 0;
        modCount = 0;
    }

    @Override
    public T removeFirst() throws ListExceptions {
        if (isEmpty()) {
            throw new ListExceptions(ListExceptions.EMPTY_LIST);
        }

        T tmp = list[0];

        for (int i = 0; i < size() - 1; i++) {
            list[i] = list[i + 1];
        }

        rear--;
        modCount++;
        return tmp;
    }

    @Override
    public T removeLast() throws ListExceptions {
        if (isEmpty()) {
            throw new ListExceptions(ListExceptions.EMPTY_LIST);
        }
        T tmp = list[rear - 1];
        list[rear - 1] = null;

        rear--;
        modCount++;

        return tmp;
    }

    @Override
    public T remove(T element) throws ListExceptions {
        int position = search(element);

        if (position == -1) {
            throw new ListExceptions(ListExceptions.ELEMENT_NOT_FOUND);
        }

        T tmp = list[position];

        if (position == 0) {
            tmp = removeFirst();
        } else if (position == rear - 1) {
            tmp = removeLast();
        } else {

            for (int i = position; i < size() - 1; i++) {
                list[i] = list[i + 1];
            }

            rear--;
            modCount++;
        }

        return tmp;

    }

    @Override
    public T first() throws ListExceptions {
        if (isEmpty()) {
            throw new ListExceptions(ListExceptions.EMPTY_LIST);
        }
        return list[0];
    }

    @Override
    public T last() throws ListExceptions {
        if (isEmpty()) {
            throw new ListExceptions(ListExceptions.EMPTY_LIST);
        }
        return list[rear - 1];
    }

    @Override
    public boolean contains(T target) {
        return (search(target) != -1);
    }

    private int search(T target) {
        int position = -1;

        if (isEmpty()) {
            return position;
        }

        for (int i = 0; i < size(); i++) {
            if (target.equals(list[i])) {
                return i;
            }
        }

        return position;
    }

    @Override
    public boolean isEmpty() {
        return (size() == 0);
    }

    @Override
    public int size() {
        return rear;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayListIterator<T>(modCount);
    }

    private class ArrayListIterator<T> implements Iterator<T> {

        private int current;
        private int expectedModCount;

        public ArrayListIterator(int expectedModCount) {
            this.current = 0;
            this.expectedModCount = expectedModCount;
        }

        @Override
        public boolean hasNext() {
            if (expectedModCount == modCount) {
                return (current < size());
            }
            throw new ConcurrentModificationException();
        }

        @Override
        public T next() {
            if (!this.hasNext()) {
                throw new ArrayIndexOutOfBoundsException();
            }
            T tmp = (T) list[current];
            current++;
            return tmp;
        }
    }

    protected void expandCapacity(){
        T[] tmp = (T[])(new Object[list.length + DEFAULT_CAPACITY]);
        for(int i = 0; i < list.length; i++){
            tmp[i] = list[i];
        }
        list = tmp;
    }

    @Override
    public String toString() {
        String text = "";
        Iterator itr = iterator();

        while (itr.hasNext()) {
            text += "\n" + itr.next().toString();
        }

        return text;
    }

}
