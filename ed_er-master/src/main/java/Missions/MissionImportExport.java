package Missions;
import Structures.Lists.ListExceptions;
import Structures.Lists.OrderedList;
import Structures.Lists.UnorderedArray;
import Structures.Queues.LinkedQueue;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Iterator;

/**
 * Classe MissionImportExport
 *  Esta classe contém as funções que exportam para um ficheiro uma missão e suas versões
 *  assim como importa para a lista de missões o conteudo do ficheiro
 *
 * @author Pedro Machado(8180238) & Tiago Moreira(8180275)
 */
public class MissionImportExport {

    public MissionImportExport() {
    }

    /**
     * Metodo que exporta para o ficheiro as missões e suas versões
     * @param missions missões a serem exportadas para o ficheiro
     * @throws IOException Exceção lançada caso haja algum erro com a leituras das opções inseridas consola
     */
    public void export (UnorderedArray<Mission> missions) throws IOException {

        FileWriter fwriter = new FileWriter("src/missionsDB.json");
        ObjectMapper mapper = new ObjectMapper();
        JSONArray json = new JSONArray();

            Iterator<Mission> itr = missions.iterator();

            while(itr.hasNext()) {
                Mission mission = itr.next();

                JSONObject obj = new JSONObject();
                obj.put("MissionName", mission.getMissionName());

                JSONArray versions = new JSONArray();

                Iterator<Version> v_itr = mission.getVersions().iterator();

                while(v_itr.hasNext()) {
                    Version v = v_itr.next();
                    System.out.println(v.getVersionNumber());

                    JSONObject version = new JSONObject();
                    version.put("VersionNumber", v.getVersionNumber());
                    version.put("Filename", v.getFilename());

                    System.out.println(version.get("Filename"));
                    versions.add(version);
                }

                obj.put("Versions", versions);


                json.add(obj);

            }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Missions", json);
        fwriter.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject));
        fwriter.close();
    }

    /**
     * Metodo que importa do ficheiro o seu conteudo
     * @return retorna um UnorderedArray com o conteudo do ficheiro
     * @throws IOException Exceção lançada caso haja algum erro com a leituras das opções inseridas consola
     * @throws ParseException Exceção lançada caso haja algum erro na leitura do ficheiro JSON
     * @throws ListExceptions Exceção lançada caso haja algum erro associado à lista ordenada
     */
    public UnorderedArray<Mission> importer() throws IOException, ParseException, ListExceptions {
        UnorderedArray<Mission> res = new UnorderedArray();
        JSONParser parser = new JSONParser();
        Object object = parser.parse(new FileReader("src/missionsDB.json"));
        JSONObject jsonObject = (JSONObject) object;
        JSONArray missions = (JSONArray) jsonObject.get("Missions");

        for(int i = 0; i < missions.size(); i++) {
            JSONObject missionObj = (JSONObject) missions.get(i);
            Mission mission = new Mission();

            mission.setMissionName((String) missionObj.get("MissionName"));

            JSONArray versions = (JSONArray) missionObj.get("Versions");
            OrderedList<Version> versionsList = new OrderedList();

            for(int j = 0; j < versions.size(); j++) {
                JSONObject version = (JSONObject) versions.get(j);
                Version v = new Version();

                long longVersionNumber = (long) version.get("VersionNumber");
                int vNumber = Math.toIntExact(longVersionNumber);
                v.setVersionNumber(vNumber);
                v.setFilename((String) version.get("Filename"));

                versionsList.add(v);
            }

            mission.setVersions(versionsList);
            res.addToRear(mission);
        }

        return res;
    }
}