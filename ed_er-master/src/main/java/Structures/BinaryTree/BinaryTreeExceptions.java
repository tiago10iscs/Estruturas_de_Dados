package Structures.BinaryTree;

/**
 *
 * @author Pedro Machado "pedroma2000"
 */
public class BinaryTreeExceptions extends Exception {

    public static final String EMPTY_TREE = "Arvore vazia";
    public static final String ELEMENT_NOT_FOUND = "Elemento não encontrado.";

    public BinaryTreeExceptions() {
    }

    public BinaryTreeExceptions(String message) {
        super(message);
    }
}
