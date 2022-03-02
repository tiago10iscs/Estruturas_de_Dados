package Structures.Network;

import Structures.BinaryTree.BinaryTreeExceptions;
import Structures.Graph.GraphADT;
import Structures.Graph.GraphExceptions;

/**
 * NetworkADT defines the interface to a network.
 *
 * @author Pedro Machado "pedroma2000"
 */
public interface NetworkADT<T> extends GraphADT<T> {

    /**
     * Inserts an edge between two vertices of this graph.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @param weight  the weight
     * @throws GraphExceptions GraphExceptions
     */
    public void addEdge(T vertex1, T vertex2, double weight) throws GraphExceptions;

    /**
     * Returns the weight of the shortest path in this network.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return the weight of the shortest path in this network
     * @throws BinaryTreeExceptions BinaryTreeExceptions
     * @throws GraphExceptions GraphExceptions
     */
    public double shortestPathWeight(T vertex1, T vertex2) throws BinaryTreeExceptions, GraphExceptions;

}
