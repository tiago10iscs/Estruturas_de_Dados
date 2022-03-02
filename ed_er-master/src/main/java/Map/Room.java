package Map;

import Structures.Lists.ListExceptions;
import Structures.Lists.UnorderedArray;
import Structures.Lists.UnorderedListADT;

import java.util.Iterator;

/**
 * Classe Room
 *  Esta classe representa uam zona do mapa importado pelo utilizador.
 *
 * @author Pedro Machado(8180238) & Tiago Moreira(8180275)
 */
public class Room {

    private String roomName;
    private int badguy_power;
    private UnorderedListADT<Room> connections;
    private String target_type;
    private boolean target;
    private boolean pw_restoreHP;
    private boolean pw_shield;

    /**
     * Contrutor do Room
     * Este contrutor é usado para um room que não possui o alvo
     *
     * @param roomName Nome da sala
     * @param badguy_power O poder dos inimigos que se encontram na zona
     */
    public Room(String roomName, int badguy_power) {
        this.roomName = roomName;
        this.badguy_power = badguy_power;
        this.connections = new UnorderedArray();
        this.target = false;
        this.target_type = "";
    }

    /**
     * Contrutor do Room
     * Este contrutor é usado para um room que possui o alvo
     *
     * @param roomName Nome da sala
     * @param badguy_power O poder dos inimigos que se encontram na zona
     * @param target_type   Tipo do alvo
     */
    public Room(String roomName, int badguy_power, String target_type) {
        this.roomName = roomName;
        this.badguy_power = badguy_power;
        this.connections = new UnorderedArray();
        this.target_type = target_type;
        this.target = true;
    }

    /**
     * Verificar se tem o powerUp RestoreHP
     *
     * @return Retorna true se tem o power up e falso se não tem
     */
    public boolean hasPw_restoreHP() {
        return pw_restoreHP;
    }

    /**
     * Atribui o powerUp RestoreHP ao Room
     *
     * @param pw_restoreHP true para atribuir o power up
     */
    public void setPw_restoreHP(boolean pw_restoreHP) {
        this.pw_restoreHP = pw_restoreHP;
    }

    /**
     * Verificar se tem o powerUp Shield
     *
     * @return Retorna true se tem o power up e falso se não tem
     */
    public boolean hasPw_shield() {
        return pw_shield;
    }

    /**
     * Atribui o powerUp Shield ao Room
     *
     * @param pw_shield true para atribuir o power up
     */
    public void setPw_shield(boolean pw_shield) {
        this.pw_shield = pw_shield;
    }

    /**
     * Atribui o dano dado pelos inimigos na Room
     *
     * @param badguy_power true para atribuir o power up
     */
    public void setBadguy_power(int badguy_power) {
        this.badguy_power = badguy_power;
    }

    /**
     * Obter nome da zona
     *
     * @return Retorna o nome da zona
     */
    public String getRoomName() {
        return this.roomName;
    }

    /**
     * Obter poder dos enimigos da zona
     *
     * @return Retorna o poder dos enimigos da zona
     */
    public int getBadguy_power() {
        return this.badguy_power;
    }

    /**
     * Obter tipo do alvo
     *
     * @return Retorna o tipo do alvo
     */
    public String getTargetTpe() {
        return this.target_type;
    }

    /**
     * Verficiar se a zona possui inimigos
     *
     * @return Retorna "true" caso existam inimigos na zona ou "false" caso não existam inimigos
     */
    public boolean hasBadGuy() {
        return (!(this.badguy_power == 0));
    }

    /**
     * Verficiar se o alvo se encontra na zona
     *
     * @return Retorna "true" caso o alvo se encontre na zona, caso contrário retorna "false"
     */
    public boolean hasTarget() {
        return this.target;
    }

    /**
     * Função utilizada para adicionar conexões à zona
     * Estas conecções são adicionadas ao UnorderedArray de conexões
     *
     * @param room Zona a adicionar às conexões
     */
    public void addConnection(Room room) {
        this.connections.addToRear(room);
    }

    /**
     * Função utilizada para remover conexões da zona
     *
     * @param room Zona a remover das conexões
     * @throws ListExceptions Lança exceção caso a zona não se encontre no array de conexões
     */
    public void removeConnection(Room room) throws ListExceptions {
        this.connections.remove(room);
    }

    /**
     * Verifica a zona tem conexão com outra dada como parâmetro
     *
     * @param room Zona que pertendemos verificar se existe conexão
     * @return Retorna "true" caso exista conexão entre as zonas, caso contrário retorna "false"
     */
    public boolean hasConnection(Room room) {
        return this.connections.contains(room);
    }

    /**
     * Obter iterador das conexões
     *
     * @return Retorna um iterador no UnorderedArray de conexões
     */
    public Iterator<Room> getConnectionsIterator() {
        return this.connections.iterator();
    }

    /**
     * Obter o número de conexões da zona
     *
     * @return Retorna o número de conexões da zona
     */
    public int numberOfConnections() {
        return connections.size();
    }

    /**
     * Método para criar String com as conexões e o poder do inimigo em cada uma delas
     * Caso o pârametro seja "true", na String será apresentado o poder dos inimigos, caso seja "false", apenas
     * serão apresentadas a conexões
     *
     * @param viewBadGuy Valor do tipo boolean que indica se quer que na String seja apresentado o dano dos inimigos
     * @return  String que representa as conexões e dano dos inimigos
     */
    public String toString(boolean viewBadGuy) {
        String text = "" + this.getRoomName();
        text += "\nConnections : ";
        Iterator<Room> connects = connections.iterator();
        while (connects.hasNext()) {
            Room currentConnect = connects.next();
            text += "\n- " + currentConnect.toString();
            if (viewBadGuy && currentConnect.getBadguy_power() != 0) {
                text += " ->" + " BadGuy Damage : " + currentConnect.getBadguy_power();
            }
            text += ";";
        }
        return text;
    }

    /**
     * Função que permite obter um Menu de Conexões da zona gerada automáticamente no formato de String
     *
     * @return Retorna o menu de conexões no formato de String
     */
    public String connectionsMenuToString() {
        int counter = 1;
        String text = "";
        Iterator<Room> connects = connections.iterator();
        while (connects.hasNext()) {
            Room currentConnect = connects.next();
            text += "\n" + counter + " -> " + currentConnect.toString();
            text += ";";
            counter++;
        }
        return text;
    }

    @Override
    public String toString() {
        return roomName;
    }
}
