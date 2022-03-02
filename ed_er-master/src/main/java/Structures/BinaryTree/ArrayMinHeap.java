package Structures.BinaryTree;

/**
 *
 * @author Pedro Machado "pedroma2000"
 */
public class ArrayMinHeap<T> extends ArrayBinaryTree<T> implements HeapADT<T> {

    public ArrayMinHeap() {
    }

    @Override
    public void addElement(T obj) {

        if (size == tree.length) {
            expandCapacity();
        }

        tree[size] = obj;
        size++;

        if (size > 1) {
            heapifyAdd();
        }

    }

    private void expandCapacity() {
        T[] tmp = (T[]) (new Object[tree.length + CAPACITY]);
        for (int i = 0; i < tree.length; i++) {
            tmp[i] = tree[i];
        }
        tree = tmp;
    }

    /**
     * Reorders this heap to maintain the ordering property after
     * adding a node.
     */
    private void heapifyAdd() {
        T temp;
        int next = size - 1;

        temp = tree[next];

        while ((next != 0) && (((Comparable) temp).compareTo(tree[(next - 1) / 2]) < 0)) {
            tree[next] = tree[(next - 1) / 2];
            next = (next - 1) / 2;
        }

        tree[next] = temp;
    }

    @Override
    public T removeMin() throws BinaryTreeExceptions {
        if (isEmpty()) {
            throw new BinaryTreeExceptions(BinaryTreeExceptions.EMPTY_TREE);
        }

        T minElement = tree[0];
        tree[0] = tree[size - 1];
        heapifyRemove();
        tree[size - 1] = null;
        size--;

        return minElement;
    }

    private void heapifyRemove() {
        T temp;
        int node = 0;
        int left = 1;
        int right = 2;
        int next;

        if ((tree[left] == null) && (tree[right] == null)) {
            next = size;
        } else if (tree[left] == null) {
            next = right;
        } else if (tree[right] == null) {
            next = left;
        } else if (((Comparable) tree[left]).compareTo(tree[right]) < 0) {
            next = left;
        } else {
            next = right;
        }
        temp = tree[node];

        while ((next < size) && (((Comparable) tree[next]).compareTo(temp) < 0)) {
            tree[node] = tree[next];
            node = next;
            left = 2 * (node + 1);
            right = 2 * (node + 1);

            if ((tree[left] == null) && (tree[right] == null)) {
                next = size;
            } else if (tree[left] == null) {
                next = right;
            } else if (tree[right] == null) {
                next = left;
            } else if (((Comparable) tree[left]).compareTo(tree[right]) < 0) {
                next = left;
            } else {
                next = right;
            }
        }

        tree[node] = temp;
    }

    @Override
    public T findMin() {
        return tree[0];
    }
}
