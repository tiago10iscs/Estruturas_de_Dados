import Structures.Lists.ListExceptions;
import Structures.Lists.OrderedList;
import Structures.Queues.LinkedQueue;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

/**
 * Classe JsonExportImport
 *  Esta classe possui as funções de importação e exportação dos resultados em formato JSON.
 *
 * @author Pedro Machado(8180238) & Tiago Moreira(8180275)
 */
public class JsonExportImport {

    /**
     * Construtor da classe JsonExportImport
     *
     */
    public JsonExportImport() {
    }

    /**
     * Função utilizada para exportar os resultados para um ficheiro JSON
     *
     * @param gameTryQueue Queue com as tentativas do utilizador
     * @return Retorna uma Queue de tentativas
     * @throws IOException Exceção lançada caso o ficheiro não exista
     */
    public LinkedQueue<GameTry> export (LinkedQueue<GameTry> gameTryQueue) throws IOException {
        LinkedQueue<GameTry> newQueue = new LinkedQueue();
        FileWriter fwriter = new FileWriter("src/paths.json");
        ObjectMapper mapper = new ObjectMapper();
        JSONArray json = new JSONArray();
        while(!gameTryQueue.isEmpty()){
            GameTry game = gameTryQueue.dequeue();

            JSONObject obj = new JSONObject();
            obj.put("Missao", game.getCod_mission());
            obj.put("Versao", game.getVersion());
            obj.put("Resultado", game.isSuccess());
            obj.put("Vida", game.getHealth());

            JSONArray path = new JSONArray();

            for(int i = 0; i < game.getPath().length; i++){
                JSONObject step = new JSONObject();
                step.put("Room", game.getPath()[i].split("-")[0]);
                step.put("Damage", game.getPath()[i].split("-")[1]);
                path.add(step);
            }
            obj.put("Path", path);
            json.add(obj);
            newQueue.enqueue(game);

        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("GameTries", json);
        fwriter.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject));

        fwriter.close();
        return newQueue;
    }

    /**
     * Função utilizada para importar os dados dos resultados de um ficheiro JSON
     *
     * @param gameTryQueue Queue onde serão armazenadas as tentativas
     * @param results Lista Ordenada onde serão armazenados os resultados ordenados por pontos de vida
     * @throws IOException Exceção lançada caso o ficheiro não exista
     * @throws ParseException Exceção lançada caso haja algum erro na leitura do ficheiro JSON
     * @throws ListExceptions Exceção lançada caso haja algum erro associado à lista ordenada
     */
    public void importer(LinkedQueue<GameTry> gameTryQueue, OrderedList<GameTry> results) throws IOException, ParseException, ListExceptions {
        JSONParser parser = new JSONParser();
        Object object = parser.parse(new FileReader("src/paths.json"));
        JSONObject jsonObject = (JSONObject) object;
        JSONArray gameTries = (JSONArray) jsonObject.get("GameTries");

        for(int i = 0; i < gameTries.size(); i++) {
            JSONObject obj = (JSONObject) gameTries.get(i);
            String codMission = (String) obj.get("Missao");
            long longVersion = (long) obj.get("Versao");
            int version = Math.toIntExact(longVersion);
            long longHealth = (long) obj.get("Vida");
            int health = Math.toIntExact(longHealth);;
            boolean success = (Boolean) obj.get("Resultado");

            JSONArray path = (JSONArray) obj.get("Path");
            String[] pathArr = new String[path.size()];

            for(int j = 0; j < path.size(); j++) {
                JSONObject room = (JSONObject) path.get(j);
                String path1 = room.get("Room") + "-" + room.get("Damage");
                pathArr[j] = (path1);
            }

            GameTry gameTry = new GameTry(codMission, success, pathArr, health);
            gameTry.setVersion(version);

            gameTryQueue.enqueue(gameTry);
            results.add(gameTry);
        }
    }
}
