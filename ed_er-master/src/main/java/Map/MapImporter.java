package Map;

import Structures.Graph.GraphExceptions;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

/**
 * Classe MapImport
 *  Esta classe contém as funções para importar e criar conexões do mapa.
 *
 * @author Pedro Machado(8180238) & Tiago Moreira(8180275)
 */
public class MapImporter {
    private String name;
    private int version;
    private JSONArray edificio;
    private JSONArray ligacoes;
    private JSONArray inimigos;
    private JSONArray entradas;
    private JSONArray saidas;
    private JSONObject target;

    /**
     * Construtor do MapImport
     */
    public MapImporter() {
    }

    /**
     * Função utilizada para importar o mapa a partir do ficheiro JSON
     *
     * @param fileName nome do ficheiro do mapa (localizado na pasta maps)
     * @return  Retorna o mapa
     * @throws IOException  Exceção lançada caso o ficheiro não exista
     * @throws ParseException   Exceção lançada caso haja algum erro com o ficehiro JSON
     * @throws NotFound Exceção lançada quando existe algum erro durante a criação do mapa
     */
    public Map mapImport(String fileName) throws IOException, ParseException, NotFound {
        JSONParser parser = new JSONParser();
        String fileLocation = "./maps/" + fileName;
        Object object = parser.parse(new FileReader(fileLocation));
        JSONObject jsonObject = (JSONObject) object;

            name = (String) jsonObject.get("cod-missao");
            long longVersion = (long) jsonObject.get("versao");
            version = Math.toIntExact(longVersion);

            edificio = (JSONArray) jsonObject.get("edificio");
            ligacoes = (JSONArray) jsonObject.get("ligacoes");
            inimigos = (JSONArray) jsonObject.get("inimigos");
            entradas = (JSONArray) jsonObject.get("entradas");
            saidas = (JSONArray) jsonObject.get("saidas");
            target = (JSONObject) jsonObject.get("alvo");

            String[] entradasArr = new String[entradas.size()];
            String[] saidasArr = new String[saidas.size()];

            for(int i = 0; i < entradasArr.length; i++) {
                entradasArr[i] = (String) entradas.get(i);
            }

            for(int i = 0; i < saidasArr.length; i++) {
                saidasArr[i] = (String) saidas.get(i);
            }

            Map newMap = new Map(name, version,entradasArr, saidasArr);

            for(int i = 0; i < edificio.size(); i++) {
                Room newRoom;
                String roomName = (String) edificio.get(i);
                int badguy_power = 0;

                for(int j = 0; j < inimigos.size(); j++) {
                    JSONObject inimigo = (JSONObject) inimigos.get(j);
                    if(inimigo.get("divisao").equals(roomName)) {
                        long longPower = (long) inimigo.get("poder");
                        badguy_power += Math.toIntExact(longPower);
                    }
                }

                if(target.get("divisao").equals(roomName)) {
                    newRoom = new Room(roomName, badguy_power, (String) target.get("tipo"));
                } else {
                    newRoom = new Room(roomName, badguy_power);
                }

                newMap.addRoom(newRoom);
            }

            Iterator<Room> roomItr = newMap.getRoomsIterator();

            while (roomItr.hasNext()) {
                Room rm = roomItr.next();
                for(int j = 0; j < ligacoes.size(); j++) {
                    JSONArray ligacao = (JSONArray) ligacoes.get(j);
                    if(((String) ligacao.get(0)).equals(rm.getRoomName())) {
                        rm.addConnection(newMap.getRoom((String) ligacao.get(1)));
                    }
                }
                for(int j = 0; j < ligacoes.size(); j++) {
                    JSONArray ligacao = (JSONArray) ligacoes.get(j);
                    if((ligacao.get(1)).equals(rm.getRoomName())) {
                        rm.addConnection(newMap.getRoom((String) ligacao.get(0)));
                    }
                }
            }

            return newMap;
    }

    /**
     * Função utilizada para criar as conexões do mapa
     *
     * @param map Mapa que foi carregado
     * @return  Retorna o mapa com as conexões devidamente criadas
     * @throws NotFound Exceção lançada caso exista algum erro com as conexões das zonas
     * @throws GraphExceptions  Exceção lançada caso haja algum erro com o grafo do mapa
     */
    public  Map createMapConnects(Map map) throws NotFound, GraphExceptions {
        Iterator<Room> mapItr = map.getRoomsIterator();

        while(mapItr.hasNext()) {
            Room room = mapItr.next();
            Iterator<Room> conItr = room.getConnectionsIterator();

            while (conItr.hasNext()) {
                Room connect = conItr.next();

                map.addConnects(room.getRoomName(), connect.getRoomName());
            }
        }
        return map;
    }
}
