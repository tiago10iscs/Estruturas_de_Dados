package Structures.Graph;

/**
 *
 * @author Pedro Machado "pedroma2000"
 */
public class GraphExceptions extends Exception {

    public final static String EMPTY_GRAPH = "Grafo está vazio.";
    public final static String ELEMENT_NOT_FOUND = "Elemento não encontrado.";
    public final static String PATH_NOT_FOUND = "Caminho não encontrado.";

    public GraphExceptions() {
    }

    public GraphExceptions(String message) {
        super(message);
    }
}
