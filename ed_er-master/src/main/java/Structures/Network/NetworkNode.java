package Structures.Network;

import Structures.Lists.UnorderedArray;
import Structures.Lists.UnorderedListADT;

import java.util.Iterator;

/**
 *
 * @author Pedro Machado "pedroma2000"
 */
public class NetworkNode<T> {

    protected T element;
    protected UnorderedListADT<Edge<T>> edgeList;

    public NetworkNode(T element) {
        this.element = element;
        this.edgeList = new UnorderedArray<>();
    }

    @Override
    public String toString() {
        String text = "\nElement: " + element + "";
        Iterator<Edge<T>> printItr = edgeList.iterator();
        while (printItr.hasNext()) {
            Edge<T> tmpNode = printItr.next();
            text += "\nEdge: " + element + "->" + tmpNode.nodeTo.element + " Weight :" + tmpNode.weight;
        }
        return text;
    }

}
