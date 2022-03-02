package Structures.Network;

import Structures.BinaryTree.BinaryTreeExceptions;
import Structures.Graph.*;
import Structures.Lists.ListExceptions;
import Structures.Lists.UnorderedArray;
import Structures.Lists.UnorderedListADT;
import Structures.PriorityQueue.PriorityQueue;
import Structures.Queues.LinkedQueue;
import Structures.Stack.EmptyCollectionException;
import Structures.Stack.LinkedStack;

import java.util.Iterator;

/**
 *
 * @author Pedro Machado "pedroma2000"
 */
public class NetworkInList<T> implements NetworkADT<T> {

    protected int numVertices;
    protected UnorderedListADT<NetworkNode<T>> nodesList;

    /**
     * Network in list constructor
     */
    public NetworkInList() {
        this.numVertices = 0;
        this.nodesList = new UnorderedArray<>();
    }

    @Override
    public void addVertex(T vertex) {
        NetworkNode<T> node = new NetworkNode<>(vertex);
        nodesList.addToRear(node);
        numVertices++;
    }

    @Override
    public void removeVertex(T vertex) throws GraphExceptions, ListExceptions {
        if (isEmpty()) {
            throw new GraphExceptions(GraphExceptions.EMPTY_GRAPH);
        }

        NetworkNode<T> nodeToRemove = this.getNode(vertex);
        Iterator<NetworkNode<T>> itr = nodesList.iterator();

        while (itr.hasNext()) {
            NetworkNode<T> tmpNode = itr.next();
            Iterator<Edge<T>> itrEdge = tmpNode.edgeList.iterator();
            UnorderedListADT<Edge<T>> found = new UnorderedArray<>();

            while (itrEdge.hasNext()) {
                Edge<T> tmpEdge = itrEdge.next();
                if (tmpEdge.nodeTo.equals(nodeToRemove)) {
                    found.addToRear(tmpEdge);
                }
            }

            Iterator<Edge<T>> itrFound = found.iterator();
            while (itrFound.hasNext()) {
                tmpNode.edgeList.remove(itrFound.next());
            }
        }

        nodesList.remove(nodeToRemove);
        numVertices--;
    }

    /**
     * returns the node which has the target vertex
     *
     * @param targetVertex the target vertex
     * @return a network node
     * @throws GraphExceptions GraphExceptions
     */
    protected NetworkNode<T> getNode(T targetVertex) throws GraphExceptions {
        boolean found = false;
        NetworkNode<T> node = null;
        Iterator<NetworkNode<T>> searchItr = nodesList.iterator();

        while (!found && searchItr.hasNext()) {
            NetworkNode<T> tmp = searchItr.next();
            if (tmp.element.equals(targetVertex)) {
                node = tmp;
                found = true;
            }
        }

        if (found == false) {
            throw new GraphExceptions(GraphExceptions.ELEMENT_NOT_FOUND);
        }

        return node;
    }

    @Override
    public void addEdge(T vertex1, T vertex2) throws GraphExceptions {
        this.addEdge(vertex1, vertex2, 0);
    }

    @Override
    public void addEdge(T vertex1, T vertex2, double weight) throws GraphExceptions {
        if (isEmpty()) {
            throw new GraphExceptions(GraphExceptions.EMPTY_GRAPH);
        }

        NetworkNode<T> node1 = this.getNode(vertex1);
        NetworkNode<T> node2 = this.getNode(vertex2);
        Edge<T> edgeNode = new Edge<>(node2, weight);

        node1.edgeList.addToRear(edgeNode);
    }

    @Override
    public void removeEdge(T vertex1, T vertex2) throws GraphExceptions, ListExceptions {
        if (isEmpty()) {
            throw new GraphExceptions(GraphExceptions.EMPTY_GRAPH);
        }
        NetworkNode<T> node1 = this.getNode(vertex1);
        NetworkNode<T> node2 = this.getNode(vertex2);

        Iterator<Edge<T>> itrEdge = node1.edgeList.iterator();
        Edge<T> found = null;

        while (itrEdge.hasNext()) {
            Edge<T> tmpEdge = itrEdge.next();
            if (tmpEdge.nodeTo.equals(node2)) {
                found = tmpEdge;
                break;
            }
        }

        node1.edgeList.remove(found);
    }

    @Override
    public Iterator iteratorBFS(T startVertex) {
        LinkedQueue<NetworkNode<T>> traversalQueue = new LinkedQueue<>();
        UnorderedArray<T> resultList = new UnorderedArray<>();
        NetworkNode<T> tmpNode;
        NetworkNode<T> startNode;

        try {
            startNode = this.getNode(startVertex);
        } catch (GraphExceptions graphExceptions) {
            return resultList.iterator();
        }

        UnorderedListADT<NetworkNode<T>> visited = new UnorderedArray<>();

        traversalQueue.enqueue(startNode);
        visited.addToRear(startNode);

        while (!traversalQueue.isEmpty()) {
            tmpNode = traversalQueue.dequeue();
            resultList.addToRear(tmpNode.element);

            /** Find all vertices adjacent to x that have
             not been visited and queue them up */
            Iterator<Edge<T>> itrEdges = tmpNode.edgeList.iterator();
            while (itrEdges.hasNext()) {
                Edge<T> nextNode = itrEdges.next();
                if (!visited.contains(nextNode.nodeTo)) {
                    traversalQueue.enqueue(nextNode.nodeTo);
                    visited.addToRear(nextNode.nodeTo);
                }
            }
        }

        return resultList.iterator();
    }

    @Override
    public Iterator iteratorDFS(T startVertex) throws EmptyCollectionException {
        LinkedStack<NetworkNode<T>> traversalStack = new LinkedStack();
        UnorderedArray<T> resultList = new UnorderedArray();
        NetworkNode<T> tmpNode;
        NetworkNode<T> startNode;
        boolean found;

        try {
            startNode = this.getNode(startVertex);
        } catch (GraphExceptions graphExceptions) {
            return resultList.iterator();
        }

        UnorderedListADT<NetworkNode<T>> visited = new UnorderedArray<>();

        traversalStack.push(startNode);
        resultList.addToRear(startNode.element);
        visited.addToRear(startNode);

        while (!traversalStack.isEmpty()) {
            tmpNode = traversalStack.peek();
            found = false;

            /** Find a vertex adjacent to x that has not been visited
             and push it on the stack */

            Iterator<Edge<T>> itrEdges = tmpNode.edgeList.iterator();
            while (itrEdges.hasNext() && !found) {
                Edge<T> nextNode = itrEdges.next();
                if (!visited.contains(nextNode.nodeTo)) {
                    traversalStack.push(nextNode.nodeTo);
                    resultList.addToRear(nextNode.nodeTo.element);
                    visited.addToRear(nextNode.nodeTo);
                    found = true;
                }
            }
            if (!found && !traversalStack.isEmpty()) {
                traversalStack.pop();
            }
        }

        return resultList.iterator();
    }

    @Override
    public Iterator iteratorShortestPath(T startVertex, T targetVertex) throws GraphExceptions {
        UnorderedArray<T> resultList = new UnorderedArray<>();

        try {
            getNode(startVertex);
        } catch (GraphExceptions ex) {
            return resultList.iterator();
        }

        Pair<T> lastPair = null;
        try {
            lastPair = findLastPairInShortestPair(startVertex, targetVertex);
            while (lastPair != null) {
                resultList.addToFront(lastPair.vertex);
                lastPair = lastPair.previous;
            }

            return resultList.iterator();
        } catch (GraphExceptions | BinaryTreeExceptions graphExceptions) {
            return resultList.iterator();
        }
    }

    /**
     * finds and returns the last pair with the last
     * vertex from the shortes past and the cost to the vertex
     *
     * @param startVertex the start vertex
     * @param targetVertex the target vertex
     * @return a pair with the final vertex and cost
     * @throws BinaryTreeExceptions BinaryTreeExceptions
     * @throws GraphExceptions GraphExceptions
     */
    private Pair<T> findLastPairInShortestPair(T startVertex, T targetVertex) throws BinaryTreeExceptions, GraphExceptions {
        PriorityQueue<Pair<T>> priorityQueue = new PriorityQueue<Pair<T>>();
        UnorderedListADT<T> verticesInPath = new UnorderedArray<>();
        Pair<T> startPair = new Pair<>(null, startVertex, 0.0);

        priorityQueue.addElement(startPair, (int) startPair.cost);

        while (!priorityQueue.isEmpty()) {
            Pair<T> pair = priorityQueue.removeNext();
            T vertex = pair.vertex;
            double minCostToVertex = pair.cost;

            if (vertex.equals(targetVertex)) {
                return pair;
            }

            verticesInPath.addToRear(vertex);
            Iterator<Edge<T>> itr = getNode(vertex).edgeList.iterator();

            while (itr.hasNext()) {
                Edge<T> tmpEdge = itr.next();
                if (!verticesInPath.contains(tmpEdge.nodeTo.element)) {
                    double minCostToI = minCostToVertex + tmpEdge.weight;
                    Pair<T> tmpPair = new Pair<>(pair, tmpEdge.nodeTo.element, minCostToI);
                    priorityQueue.addElement(tmpPair, (int) tmpPair.cost);
                }
            }
        }

        throw new GraphExceptions(GraphExceptions.PATH_NOT_FOUND);
    }

    @Override
    public double shortestPathWeight(T vertex1, T vertex2) throws BinaryTreeExceptions, GraphExceptions {
        try {
            getNode(vertex1);
        } catch (GraphExceptions ex) {
            throw new GraphExceptions(GraphExceptions.ELEMENT_NOT_FOUND);
        }

        return findLastPairInShortestPair(vertex1, vertex2).cost;
    }

    @Override
    public boolean isEmpty() {
        return (numVertices == 0);
    }

    @Override
    public boolean isConnected() throws GraphExceptions, ListExceptions {
        if (isEmpty()) {
            throw new GraphExceptions(GraphExceptions.EMPTY_GRAPH);
        }

        Iterator itr = iteratorBFS(nodesList.first().element);
        int counter = 0;

        while (itr.hasNext()) {
            itr.next();
            counter++;
        }

        return (counter == numVertices);
    }

    @Override
    public int size() {
        return numVertices;
    }

    @Override
    public String toString() {
        String text = "";
        Iterator<NetworkNode<T>> printItr = nodesList.iterator();
        while (printItr.hasNext()) {
            text += printItr.next().toString();
        }
        text += "\n";
        return text;
    }
}
