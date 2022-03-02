import Map.Map;
import Map.MapImporter;
import Missions.Mission;
import Missions.MissionImportExport;
import Missions.Version;
import Structures.BinaryTree.BinaryTreeExceptions;
import Structures.Graph.GraphExceptions;
import Structures.Lists.ListExceptions;
import Structures.Lists.OrderedList;
import Structures.Lists.UnorderedArray;
import Structures.Queues.LinkedQueue;
import org.json.simple.parser.ParseException;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

/**
 * Classe Menus
 * Esta classe possui os menus apresentados ao utilizador.
 *
 * @author Pedro Machado(8180238) & Tiago Moreira(8180275)
 */
public class Menus {
    private Map map;
    private GameModes modes = new GameModes();
    private MapImporter importer = new MapImporter();
    private LinkedQueue<GameTry> gameTryQueue = new LinkedQueue();
    private JsonExportImport jsonExporter = new JsonExportImport();
    private MissionImportExport missionImportExport = new MissionImportExport();
    private OrderedList<GameTry> results = new OrderedList();
    private UnorderedArray<Mission> missionsList = new UnorderedArray();

    /**
     * Construtor da classe Menus
     */
    public Menus() {
        File paths = new File("src/paths.json");
        if (paths.exists() && paths.length() != 0) {
            try {
                jsonExporter.importer(gameTryQueue, results);
            } catch (IOException | ParseException | ListExceptions e) {
                e.printStackTrace();
            }
        }

        File missions = new File("src/missionsDB.json");
        if (missions.exists() && missions.length() != 0) {
            try {
                missionsList = missionImportExport.importer();
            } catch (IOException | ParseException | ListExceptions e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Função que executa o menu para escolher o modo de jogo
     *
     * @throws IOException          Exceção lançada caso o ficheiro não exista
     * @throws NotFound             Exceção lançada caso a zona não se encontre no mapa
     * @throws BinaryTreeExceptions Exceção lançada quando ocorre algum erro associado ao cálculo do caminho mais curto
     * @throws GraphExceptions      Exceção lançada caso haja algum erro como o grafo do mapa
     * @throws ListExceptions       Exceção lançada caso haja algum erro associado à lista ordenada
     * @throws ParseException       Exceção lançada caso haja algum erro na leitura do ficheiro JSON
     * @throws InterruptedException Exceção lançada quando existe um erro na Thread
     */
    public void askGameMode() throws IOException, NotFound, BinaryTreeExceptions, GraphExceptions, ListExceptions, ParseException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String opcao;
        System.out.println("-------------ESCOLHA O MODO DE JOGO--------------");
        System.out.println("1 - Modo Manual ");
        System.out.println("2 - Modo Automático ");
        System.out.println("0 - Voltar ");
        System.out.println("-------------------------------------------------");

        opcao = reader.readLine();
        switch (opcao) {
            case "1":
                askForEntrance();
                break;
            case "2":
                modes.automaticMode(map);
                Thread.sleep(3000);
                mainMenu();
                break;
            case "0":
                mainMenu();
                break;
            default:
                askGameMode();
                break;
        }

    }

    /**
     * Função que executa o menu de escolha da entrada no edificio
     *
     * @throws IOException          Exceção lançada caso o ficheiro não exista
     * @throws NotFound             Exceção lançada caso a zona não se encontre no mapa
     * @throws BinaryTreeExceptions Exceção lançada quando ocorre algum erro associado ao cálculo do caminho mais curto
     * @throws GraphExceptions      Exceção lançada caso haja algum erro como o grafo do mapa
     * @throws ListExceptions       Exceção lançada caso haja algum erro associado à lista ordenada
     * @throws ParseException       Exceção lançada caso haja algum erro na leitura do ficheiro JSON
     * @throws InterruptedException Exceção lançada quando existe um erro na Thread
     */
    public void askForEntrance() throws IOException, NotFound, InterruptedException, ParseException, BinaryTreeExceptions, GraphExceptions, ListExceptions {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String opcao;
        do {
            System.out.println("---------" + map.getCod_missao().toUpperCase() + "---------");
            System.out.println("> Escolha a entrada:  <");
            System.out.println(map.entranceMenuToString());

            opcao = reader.readLine();
        } while (Integer.valueOf(opcao) < 1 || Integer.valueOf(opcao) > map.getEntrances().length);

        GameTry newGameTry = modes.manualMode(map.getRoom(map.getEntrances()[Integer.valueOf(opcao) - 1]), map, map.getCod_missao());
        newGameTry.setVersion(map.getVersion());
        gameTryQueue.enqueue(newGameTry);
        results.add(newGameTry);
        gameTryQueue = jsonExporter.export(gameTryQueue);
        Thread.sleep(3000);
        mainMenu();
    }

    /**
     * Função que executa o menu principal da aplicação
     *
     * @throws IOException          Exceção lançada caso o ficheiro não exista
     * @throws NotFound             Exceção lançada caso a zona não se encontre no mapa
     * @throws BinaryTreeExceptions Exceção lançada quando ocorre algum erro associado ao cálculo do caminho mais curto
     * @throws GraphExceptions      Exceção lançada caso haja algum erro como o grafo do mapa
     * @throws ListExceptions       Exceção lançada caso haja algum erro associado à lista ordenada
     * @throws ParseException       Exceção lançada caso haja algum erro na leitura do ficheiro JSON
     * @throws InterruptedException Exceção lançada quando existe um erro na Thread
     */
    public void mainMenu() throws IOException, ListExceptions, NotFound, GraphExceptions, BinaryTreeExceptions, ParseException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String opcao;
        System.out.println("-------------MENU PRINCIPAL--------------");
        System.out.println("1 - Jogar ");
        System.out.println("2 - Ver Resultados ");
        System.out.println("3 - Adicionar Missão/Versão ");
        System.out.println("0 - Sair ");
        System.out.println("-------------------------------------------------");

        opcao = reader.readLine();
        switch (opcao) {
            case "1":
                mapsSelection();
                break;
            case "2":
                System.out.println("------------RESULTADOS------------\n");
                printOrderedResults();
                Thread.sleep(5000);
                mainMenu();
                break;
            case "3":
                addMissionMenu();
                break;
            case "0":
                System.exit(1);
                break;
            default:
                mainMenu();
                break;
        }
    }

    /**
     * Menu que permite adicionar uma missão ao programa
     * @throws IOException          Exceção lançada caso o ficheiro não exista
     * @throws NotFound             Exceção lançada caso a zona não se encontre no mapa
     * @throws BinaryTreeExceptions Exceção lançada quando ocorre algum erro associado ao cálculo do caminho mais curto
     * @throws GraphExceptions      Exceção lançada caso haja algum erro como o grafo do mapa
     * @throws ListExceptions       Exceção lançada caso haja algum erro associado à lista ordenada
     * @throws ParseException       Exceção lançada caso haja algum erro na leitura do ficheiro JSON
     * @throws InterruptedException Exceção lançada quando existe um erro na Thread
     */
    public void addMissionMenu() throws ListExceptions, InterruptedException, ParseException, IOException, GraphExceptions, NotFound, BinaryTreeExceptions {
        System.out.println("-------------------ADICIONAR MISSÃO--------------------");
        System.out.println("       > Insira o nome do ficheiro do mapa <");
        System.out.println("(O ficheiro deve estar na pasta maps e em formato JSON)");
        System.out.println("-------------------------------------------------------");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String filename = reader.readLine();
        File file = new File("./maps/" + filename);
        if (file.exists()) {
            addMission(filename);
            missionImportExport.export(missionsList);
            System.out.println("AKI4");
            mainMenu();
        } else {
            System.out.println("\n\n\n\n");
            System.out.println("O ficheiro nao existe!\n\n\n\n");
            Thread.sleep(1000);
            mainMenu();
        }
    }


    /**
     * Função que executa o menu de informações para escolha do mapa
     *
     * @throws IOException Exceção lançada caso o ficheiro não exista
     * @throws NotFound Exceção lançada caso a zona não se encontre no mapa
     * @throws BinaryTreeExceptions Exceção lançada quando ocorre algum erro associado ao cálculo do caminho mais curto
     * @throws GraphExceptions Exceção lançada caso haja algum erro como o grafo do mapa
     * @throws ListExceptions Exceção lançada caso haja algum erro associado à lista ordenada
     * @throws ParseException Exceção lançada caso haja algum erro na leitura do ficheiro JSON
     * @throws InterruptedException Exceção lançada quando existe um erro na Thread
     */
    public void mapsSelection() throws IOException, NotFound, ParseException, GraphExceptions, InterruptedException, ListExceptions, BinaryTreeExceptions {
        int count = 1;
        Iterator<Mission> itr = missionsList.iterator();
        Mission[] missions = new Mission[missionsList.size()];
        String opcao;
        do {
            System.out.println("-------------Selecione a missão---------------\n");
            while (itr.hasNext()) {
                Mission m = itr.next();
                System.out.println(count + " - " + m.getMissionName());
                missions[count - 1] = m;
                count++;
            }
            System.out.println("0 - Voltar");


            BufferedReader reader;
            reader = new BufferedReader(new InputStreamReader(System.in));
            opcao = reader.readLine();

        }while (Integer.parseInt(opcao) < 0 || Integer.parseInt(opcao) > missions.length);

        if(Integer.parseInt(opcao) == 0) {
            mainMenu();
        } else {
            Mission mission = missions[Integer.parseInt(opcao) - 1];
            versionsMenu(mission);
        }

    }

    /**
     * Função que executa o menu para escolher as versões ou versão a ser jogada
     *
     * @throws IOException Exceção lançada caso o ficheiro não exista
     * @throws NotFound Exceção lançada caso a zona não se encontre no mapa
     * @throws BinaryTreeExceptions Exceção lançada quando ocorre algum erro associado ao cálculo do caminho mais curto
     * @throws GraphExceptions Exceção lançada caso haja algum erro como o grafo do mapa
     * @throws ListExceptions Exceção lançada caso haja algum erro associado à lista ordenada
     * @throws ParseException Exceção lançada caso haja algum erro na leitura do ficheiro JSON
     * @throws InterruptedException Exceção lançada quando existe um erro na Thread
     */
    public void versionsMenu(Mission mission) throws IOException, NotFound, ParseException, GraphExceptions, BinaryTreeExceptions, InterruptedException, ListExceptions {

        Iterator<Version> itr = mission.getVersions().iterator();
        int count = 1;
        Version[] versions = new Version[mission.getVersions().size()];
        String opcao;

        do {
            System.out.println("-------------" + mission.getMissionName().toUpperCase() + "---------------");
            System.out.println("   > Selecione a versão\n");
            while (itr.hasNext()) {
                Version v = itr.next();
                System.out.println(count + " - Versão " + v.getVersionNumber());
                versions[count - 1] = v;
                count++;
            }
            System.out.println("0 - Voltar");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            opcao = reader.readLine();

        } while (Integer.parseInt(opcao) < 0 || Integer.parseInt(opcao) > versions.length);

        if(Integer.parseInt(opcao) == 0) {
            mainMenu();
        } else {
            Version version = versions[Integer.parseInt(opcao) - 1];
            String filename = version.getFilename();

            File file = new File("./maps/" + filename);
            if (file.exists()) {
                map = importer.mapImport(filename);
                map = importer.createMapConnects(map);
                askGameMode();
            } else {
                System.out.println("\n\n\n\n");
                System.out.println("O ficheiro nao existe!\n\n\n\n");
                Thread.sleep(1000);
                mapsSelection();
            }
        }

    }

    /**
     *  Função utilizada para escrever na consola os resultados de cada simulação manual por ordem decrescente de pontos de vida
     *
     */
    public void printOrderedResults() {
        Iterator<GameTry> gameItr = results.iterator();
        while (gameItr.hasNext()) {
            GameTry gameTry = gameItr.next();
            System.out.println("-------------------------------------------");
            System.out.println("Missao: " + gameTry.getCod_mission());
            System.out.println("Versao: " + gameTry.getVersion());
            System.out.println("Pontos de Vida Restantes: " + gameTry.getHealth());
            if (gameTry.isSuccess()) {
                System.out.println("Resultado: Missao concluida com sucesso");
            } else {
                System.out.println("Resultado: Missao falhada");
            }
            System.out.println("-------------------------------------------");
        }
    }

    /**
     * Metodo que adiciona uma nova missão
     * @param filename ficheiro a ser utilizado
     * @throws ParseException Exceção lançada caso haja algum erro na leitura do ficheiro JSON
     * @throws IOException Exceção lançada caso o ficheiro não exista
     * @throws NotFound Exceção lançada caso a zona não se encontre no mapa
     */
    private void addMission(String filename) throws ParseException, IOException, NotFound {
        Version version = new Version();
        Map map = importer.mapImport(filename);
        boolean found = false;

        version.setFilename(filename);
        version.setVersionNumber(map.getVersion());

        Iterator<Mission> itr = missionsList.iterator();

        while (itr.hasNext() && !found) {
            Mission m = itr.next();
            if(m.getMissionName().equals(map.getCod_missao())) {
                m.addVersion(version);
                found = true;
            }
        }

        if(!found) {
            Mission mission = new Mission(map.getCod_missao());
            mission.addVersion(version);
            missionsList.addToRear(mission);
        }


    }

}
