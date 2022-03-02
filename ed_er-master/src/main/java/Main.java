import Structures.BinaryTree.BinaryTreeExceptions;
import Structures.Graph.GraphExceptions;
import Structures.Lists.ListExceptions;
import org.json.simple.parser.ParseException;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.io.IOException;

/**
 * Classe Main
 * Esta classe possui o método "main", utilizado para executar o programa.
 *
 * @author Pedro Machado(8180238) & Tiago Moreira(8180275)
 */
public class Main {
    private static Menus menus = new Menus();
    public static void main(String[] args) {
        try {
            menus.mainMenu();
        } catch (IOException | ListExceptions | NotFound | GraphExceptions | BinaryTreeExceptions | ParseException | InterruptedException e) {
            e.printStackTrace();
        }

    }

}
