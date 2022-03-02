package Map;

import Structures.BinaryTree.BinaryTreeExceptions;
import Structures.Graph.GraphExceptions;
import Structures.Lists.ListExceptions;
import Structures.Lists.UnorderedArray;
import Structures.Lists.UnorderedListADT;
import Structures.Network.NetworkADT;
import Structures.Network.NetworkInList;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.util.Iterator;

/**
 * Classe Map
 *  Esta classe representa o mapa importado pelo utilizador.
 *
 * @author Pedro Machado(8180238) & Tiago Moreira(8180275)
 */
public class Map {

    private String cod_missao;
    private int version;
    private NetworkADT<Room> networkMap;
    private UnorderedListADT<Room> addedRooms;
    private String[] entrances;
    private String[] exits;

    /**
     * Construtor do Map
     *
     * @param cod_missao Nome da missão
     * @param version Versão do Mapa
     * @param entrances Array de Strings que contém o nome dos rooms que são entrada
     * @param exits Array de Strings que contém o nome dos rooms que são saída
     */
    public Map(String cod_missao, int version, String[] entrances, String[] exits) {
        this.cod_missao = cod_missao;
        this.version = version;
        this.networkMap = new NetworkInList<>();
        this.addedRooms = new UnorderedArray();
        this.entrances = entrances;
        this.exits = exits;
    }

    /**
     * Obter código de missão
     *
     * @return Retorna o código da missão
     */
    public String getCod_missao() {
        return cod_missao;
    }

    /**
     * Obter versão do mapa
     *
     * @return Retorna a versão do mapa
     */
    public int getVersion() {
        return version;
    }

    /**
     * Função para adicionar as zonas do edificio ao mapa
     *
     * @param room Zona do edificio a adicionar ao mapa
     */
    public void addRoom(Room room) {
        networkMap.addVertex(room);
        addedRooms.addToRear(room);
    }

    /**
     * Obter Lista com os Rooms do mapa
     *
     * @return Retorna lista de Rooms do mapa
     */
    public UnorderedListADT<Room> getAddedRooms() {
        return addedRooms;
    }

    /**
     * Função que permite obter um Room(zona do mapa) através do seu nome
     *
     * @param roomName Nome da zona a pesquisar
     * @return Retorna o Room com o nome pretendido
     * @throws NotFound Exceção lançada caso a zona não se encontre no mapa
     */
    public Room getRoom(String roomName) throws NotFound {
        boolean found = false;
        Room room = null;
        Iterator<Room> searchItr = addedRooms.iterator();

        while (!found && searchItr.hasNext()) {
            Room tmp = searchItr.next();
            if(tmp.getRoomName().equals(roomName)) {
                room = tmp;
                found = true;
                break;
            }
        }

        if (found == false) {
            throw new NotFound();
        }

        return room;
    }

    /**
     * Obter array de entradas
     *
     * @return Retorna um array de strings com as entradas
     */
    public String[] getEntrances() {
        return this.entrances;
    }

    /**
     * Obter array de saidas
     *
     * @return Retorna um array de strings com as saidas
     */
    public String[] getExits() {
        return this.exits;
    }

    /**
     * Obter um iterador das diferentes zonas do mapa
     *
     * @return Iterador das zonas do mapa
     */
    public Iterator<Room> getRoomsIterator() {
        return addedRooms.iterator();
    }

    /**
     * Função que cria uma ligação entre uma zona e outra.
     *
     * @param source Zona que funciona como vértice 1
     * @param dest Zona como funciona como vértice 2
     * @throws NotFound Exceção lançada caso as zonas source ou dest não se encontrem no mapa
     * @throws GraphExceptions Exceção lançada caso haja algum erro como o grafo do mapa
     */
    public void addConnects(String source, String dest) throws GraphExceptions, NotFound {
        Room sourceRoom = getRoom(source);
        Room destRoom = getRoom(dest);

        networkMap.addEdge(sourceRoom, destRoom, destRoom.getBadguy_power());

        if(destRoom.getRoomName().equals(entrances) && destRoom.numberOfConnections() ==  0) {
            destRoom.addConnection(sourceRoom);
            networkMap.addEdge(destRoom, sourceRoom, sourceRoom.getBadguy_power());
        }
    }

    /**
     * Função utilizada para obter a sala que contém o alvo
     *
     * @return Zona do edificio que contém o alvo
     */
    public Room targetRoom(){
        Iterator<Room> roomIterator = getRoomsIterator();
        Room targetRoom = null;
        Room roomCurrent;
        while (roomIterator.hasNext()){
            roomCurrent = roomIterator.next();
            if(roomCurrent.hasTarget()){
                targetRoom = roomCurrent;
            }
        }
        return targetRoom;
    }

    /**
     * Função utilizada para obter o caminho mais curto até ao alvo
     *
     * @param start Zona que representa o ponto inicial
     * @param end Zona que representa o ponto final
     * @return Retorna um iterador de zonas com o caminho mais curto
     * @throws BinaryTreeExceptions Exceção lançada quando ocorre algum erro associado ao cálculo do caminho mais curto
     * @throws GraphExceptions  Exceção lançada caso haja algum erro como o grafo do mapa
     * @throws ListExceptions   Exceção lançada quando ocorre algum erro associado ao iterador
     */
    public Iterator<Room> bestPath(Room start, Room end) throws BinaryTreeExceptions, GraphExceptions, ListExceptions {
        return networkMap.iteratorShortestPath(start, end);
    }

    /**
     * Função que permite obter um Menu de Entradas do mapa gerada automáticamente no formato de String
     *
     * @return Retorna o menu de entradas no formato de String
     */
    public String entranceMenuToString() {
        String text = "";
       for(int i = 0; i < entrances.length; i++) {
           text += "\n" + (i+1) + " -> " +  entrances[i];
           text += ";";

       }
        return text;
    }

}
