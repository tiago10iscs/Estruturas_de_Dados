package Structures.Graph;

import Structures.BinaryTree.BinaryTreeExceptions;
import Structures.Lists.ListExceptions;
import Structures.Stack.EmptyCollectionException;

import java.util.Iterator;

/**
 *
 * @author Pedro Machado "pedroma2000"
 */
public interface GraphADT<T> {

    /**
     * Adds a vertex to this graph, associating object with vertex.
     *
     * @param vertex the vertex to be added to this graph
     */
    public void addVertex(T vertex);

    /**
     * Removes a single vertex with the given value from this graph.
     *
     * @param vertex the vertex to be removed from this graph
     * @throws GraphExceptions GraphExceptions
     * @throws ListExceptions  ListExceptions
     */
    public void removeVertex(T vertex) throws GraphExceptions, ListExceptions;

    /**
     * Inserts an edge between two vertices of this graph.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @throws GraphExceptions GraphExceptions
     */
    public void addEdge(T vertex1, T vertex2) throws GraphExceptions;

    /**
     * Removes an edge between two vertices of this graph.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @throws GraphExceptions GraphExceptions
     * @throws ListExceptions  ListExceptions
     */
    public void removeEdge(T vertex1, T vertex2) throws GraphExceptions, ListExceptions;

    /**
     * Returns a breadth first iterator starting with the given vertex.
     *
     * @param startVertex the starting vertex
     * @return a breadth first iterator beginning at
     * the given vertex
     */
    public Iterator iteratorBFS(T startVertex);

    /**
     * Returns a depth first iterator starting with the given vertex.
     *
     * @param startVertex the starting vertex
     * @return a depth first iterator starting at the
     * given vertex
     * @throws EmptyCollectionException EmptyCollectionException
     */
    public Iterator iteratorDFS(T startVertex) throws EmptyCollectionException;

    /**
     * Returns an iterator that contains the shortest path between
     * the two vertices.
     *
     * @param startVertex  the starting vertex
     * @param targetVertex the ending vertex
     * @return an iterator that contains the shortest
     * path between the two vertices
     * @throws BinaryTreeExceptions BinaryTreeExceptions
     * @throws GraphExceptions      GraphExceptions
     * @throws ListExceptions       ListExceptions
     */
    public Iterator iteratorShortestPath(T startVertex, T targetVertex) throws BinaryTreeExceptions, GraphExceptions, ListExceptions;

    /**
     * Returns true if this graph is empty, false otherwise.
     *
     * @return true if this graph is empty
     */
    public boolean isEmpty();

    /**
     * Returns true if this graph is connected, false otherwise.
     *
     * @return true if this graph is connected
     * @throws GraphExceptions GraphExceptions
     * @throws ListExceptions ListExceptions
     */
    public boolean isConnected() throws GraphExceptions, ListExceptions;

    /**
     * Returns the number of vertices in this graph.
     *
     * @return the integer number of vertices in this graph
     */
    public int size();

    /**
     * Returns a string representation of the adjacency matrix.
     *
     * @return a string representation of the adjacency matrix
     */
    @Override
    public String toString();


}
