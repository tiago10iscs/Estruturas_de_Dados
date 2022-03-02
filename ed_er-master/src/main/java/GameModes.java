import Map.Map;
import Map.Room;
import Structures.BinaryTree.BinaryTreeExceptions;
import Structures.Graph.GraphExceptions;
import Structures.Lists.ListExceptions;
import Structures.Lists.UnorderedArray;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Random;

/**
 * Classe GameModes
 *  Esta classe contém as funções que executam os modos de jogo.
 *
 * @author Pedro Machado(8180238) & Tiago Moreira(8180275)
 */
public class GameModes {

    public GameModes() {
    }

    /**
     * Função que executa o modo de jogo manual
     *
     * @param entrance Zona escolhida para entrar no edificio
     * @param map Mapa escolhido
     * @param name Nome da missão
     * @return Retorna o resultado da missão
     * @throws IOException Exceção lançada caso haja algum erro com a leituras das opções inseridas consola
     */
    public GameTry manualMode(Room entrance,Map map, String name) throws IOException {

        int health = 100;
        boolean isOut = false;
        String mapView = "ENTRADA";
        Room current = entrance;
        Room[] roomArr = {};
        UnorderedArray<Room> path = new UnorderedArray<Room>();
        boolean hasTarget = false;
        boolean repeat = false;
        int pw_restoreHP = limitedRandom(0,map.getAddedRooms().size());
        int pw_shield = limitedRandom(0, map.getAddedRooms().size());
        boolean hasShield = false;

        int count = 0;
        Iterator<Room> roomList = map.getRoomsIterator();

        while (roomList.hasNext()) {
            Room room = roomList.next();

            if(count == pw_restoreHP) {
                room.setPw_restoreHP(true);
            }

            if(count == pw_shield) {
                room.setPw_shield(true);
            }
            count++;
        }


        while(!isOut && health >= 0) {
            repeat = false;
            boolean roomHasExit = false;
            roomArr = new Room[current.numberOfConnections()];
            int arrCount = 0;

            System.out.println("nome: " + current.getRoomName() + " - POWER: " + current.getBadguy_power());

            if(current.hasPw_restoreHP()) {
                System.out.println("\n\n ENCONTROU UM RESTORE HP POWERUP");
                System.out.println("  > Pontos de vida restaurados <  ");
                System.out.println("  > Vida atual: 100 <  ");
                health = 100;
                current.setPw_restoreHP(false);
            }

            if(current.hasPw_shield()) {
                System.out.println("\n\n ENCONTROU UM SHIELD POWERUP");
                System.out.println("  > O Tó Cruz encontrou um shield e não levará dano do próximo inimigo <  ");
                hasShield = true;
                current.setPw_shield(false);
            }

            if(current.hasBadGuy()) {
                if(hasShield) {
                    System.out.println("\n > O Tó Cruz usou o Shield e evitou o dano do inimigo <");
                    hasShield = false;
                } else {
                    health -= current.getBadguy_power();
                }
            }

            for(int i = 0; i < map.getExits().length; i++) {
                if(map.getExits()[i].equals(current.getRoomName())) {
                    roomHasExit = true;
                }
            }

            if(!mapView.equals("ENTRADA")) {
                if (roomHasExit) {
                    String opcao;
                    do {
                        System.out.println("---------" + current.getRoomName() + "---------");
                        System.out.println("ENCONTROU UMA SAIDA!");
                        System.out.println("> Deseja de sair do edifcio? <\n");
                        System.out.println("  1 - Sim\n");
                        System.out.println("  2 - Não\n");


                        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                        opcao = reader.readLine();

                    } while (Integer.valueOf(opcao) < 1 || Integer.valueOf(opcao) > 2);

                    switch (opcao) {
                        case "1":
                            isOut = true;
                            mapView += (" -> " + current.getRoomName() + " -> SAIDA");
                            path.addToRear(current);
                            break;
                        case "2":
                            Iterator<Room> currItr = current.getConnectionsIterator();
                            String opcao2;
                                if (current.hasTarget()) {
                                    hasTarget = true;

                                    do {
                                        System.out.println("---------" + current.getRoomName() + "---------");
                                        System.out.println("ENCONTROU O ALVO, SAIA DO EDIFICIO!");
                                        System.out.println("TIPO DE ALVO: " + current.getTargetTpe());
                                        System.out.println("> Escolha a próxima posição <");
                                        System.out.println(current.connectionsMenuToString());
                                        while (currItr.hasNext()) {
                                            roomArr[arrCount] = currItr.next();
                                            arrCount++;
                                        }

                                        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                                        opcao2 = reader.readLine();

                                    } while (Integer.valueOf(opcao2) < 1 || Integer.valueOf(opcao2) > current.numberOfConnections());
                                } else {
                                    do {
                                        System.out.println("---------" + current.getRoomName() + "---------");
                                        System.out.println("> Escolha a próxima posição <");
                                        System.out.println(current.connectionsMenuToString());
                                        while (currItr.hasNext()) {
                                            roomArr[arrCount] = currItr.next();
                                            arrCount++;
                                        }

                                        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                                        opcao2 = reader.readLine();

                                    } while (Integer.valueOf(opcao2) < 1 || Integer.valueOf(opcao2) > current.numberOfConnections());
                                }
                                mapView += (" -> " + current.getRoomName());
                                path.addToRear(current);
                                current = roomArr[Integer.parseInt(opcao2) - 1];

                            repeat = true;
                            break;
                        default:
                            break;
                    }
                }
            }

            if(!repeat) {
                Iterator<Room> currItr = current.getConnectionsIterator();
                String opcao;

                if (!isOut) {

                    if (current.hasTarget()) {
                        hasTarget = true;

                        do {
                            System.out.println("---------" + current.getRoomName() + "---------");
                            System.out.println("ENCONTROU O ALVO, SAIA DO EDIFICIO!");
                            System.out.println("TIPO DE ALVO: " + current.getTargetTpe());
                            System.out.println("> Escolha a próxima posição <");
                            System.out.println(current.connectionsMenuToString());
                            while (currItr.hasNext()) {
                                roomArr[arrCount] = currItr.next();
                                arrCount++;
                            }

                            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                            opcao = reader.readLine();

                        } while (Integer.valueOf(opcao) < 1 || Integer.valueOf(opcao) > current.numberOfConnections());
                    } else {
                        do {
                            System.out.println("---------" + current.getRoomName() + "---------");
                            System.out.println("> Escolha a próxima posição <");
                            System.out.println(current.connectionsMenuToString());
                            while (currItr.hasNext()) {
                                roomArr[arrCount] = currItr.next();
                                arrCount++;
                            }

                            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                            opcao = reader.readLine();

                        } while (Integer.valueOf(opcao) < 1 || Integer.valueOf(opcao) > current.numberOfConnections());
                    }
                    mapView += (" -> " + current.getRoomName());
                    path.addToRear(current);
                    moveEnemies(map);
                    current = roomArr[Integer.parseInt(opcao) - 1];
                }
            }
        }
            boolean isComplete = false;
        if(hasTarget && health > 0) {
            System.out.println("-------- FIM --------");
            System.out.println("Parabéns!");
            System.out.println("A missão '" + name + "' foi concluida com sucesso.");
            System.out.println("Pontos de vida restantes: " + health);
            System.out.println("\nCaminho Percorrido: ");
            System.out.println(mapView);
            System.out.println("-------- FIM --------");
            isComplete = true;
        } else if(health <= 0) {
            System.out.println("-------- FIM --------");
            System.out.println("A missão '" + name + "' falhou.");
            System.out.println("O Tó Cruz faleceu.");
            System.out.println("Pontos de vida restantes: 0");
            System.out.println("\nCaminho Percorrido: ");
            System.out.println(mapView);
            System.out.println("-------- FIM --------");
        } else if(!hasTarget) {
            System.out.println("-------- FIM --------");
            System.out.println("A missão '" + name + "' falhou.");
            System.out.println("O Tó Cruz não conseguiu encontrar o alvo.");
            System.out.println("Pontos de vida restantes: " + health);
            System.out.println("\nCaminho Percorrido: ");
            System.out.println(mapView);
            System.out.println("-------- FIM --------");
        }
        Iterator<Room> roomItr = path.iterator();
        String[] steps = new String[path.size()];
        int cont = 0;
        while (roomItr.hasNext()){
            Room room = roomItr.next();
            String step = room.getRoomName() + "-" + room.getBadguy_power();
            steps[cont] = step;
            cont ++;
        }
        if(health < 0){
            health = 0;
        }

        GameTry gameTry = new GameTry(name, isComplete, steps, health);

        return gameTry;
    }


    /**
     * Função que executa o modo automático.
     * Este modo permite obter o caminho mais seguro para o Tó Cruz.
     *
     * @param map Mapa escolhido pelo utilizador
     * @throws NotFound Exceção lançada caso exista um erro em alguma conexão entre zonas do mapa
     * @throws BinaryTreeExceptions Exceção lançada quando ocorre algum erro associado ao cálculo do caminho mais seguro
     * @throws GraphExceptions  Exceção lançada quando existe algum erro com o grafo do mapa
     * @throws ListExceptions   Exceção lançada quando ocorre algum erro associado ao iterador
     */
    public void automaticMode(Map map) throws NotFound, BinaryTreeExceptions, GraphExceptions, ListExceptions {
        int damage = 0;
        int damage2 = 0;
        String entrance;
        String exit;
        String[] entrancesArr = map.getEntrances();
        String[] exitsArr = map.getExits();

        for(int i = 0; i < exitsArr.length; i++) {
            System.out.println(exitsArr[i]);
        }

        UnorderedArray<Room> roomsArr = new UnorderedArray<Room>();
        int cont = 1;

        Room room = map.getRoom(entrancesArr[0]);
        Iterator<Room> bestPath = map.bestPath(room, map.targetRoom());
        entrance = room.getRoomName();

        while(bestPath.hasNext()){
            roomsArr.addToRear(bestPath.next());
        }

        Iterator<Room> firstIterator = roomsArr.iterator();
        while(firstIterator.hasNext()){
            damage += firstIterator.next().getBadguy_power();
        }

        while(cont < entrancesArr.length){
            Room currentRoom = map.getRoom(entrancesArr[cont]);
            Iterator<Room> bestPathFinder = map.bestPath(currentRoom, map.targetRoom());
            while(bestPathFinder.hasNext()){
                roomsArr.addToRear(bestPathFinder.next());
            }
            int pathDamage = 0;
            Iterator<Room> roomsIterator = roomsArr.iterator();
            if(roomsIterator.hasNext()){
                roomsIterator.next();
            }
            while(roomsIterator.hasNext()){
                pathDamage += roomsIterator.next().getBadguy_power();
            }
            if(pathDamage < damage){
                damage = pathDamage;
                entrance = currentRoom.getRoomName();
            }
            cont ++;
        }

        Room room2 = map.getRoom(exitsArr[0]);
        Iterator<Room> bestExitPath = map.bestPath(room2, map.targetRoom());
        exit = room2.getRoomName();
        roomsArr = new UnorderedArray<Room>();

        while(bestExitPath.hasNext()) {
            roomsArr.addToRear(bestExitPath.next());
        }


        Iterator<Room> secondIterator = roomsArr.iterator();
        while(secondIterator.hasNext()){
            damage2 += secondIterator.next().getBadguy_power();
        }

        while(cont < exitsArr.length){
            Room currentRoom = map.getRoom(exitsArr[cont]);
            Iterator<Room> bestPathFinder = map.bestPath(currentRoom, map.targetRoom());
            while(bestPathFinder.hasNext()){
                roomsArr.addToRear(bestPathFinder.next());
            }
            int pathDamage = 0;
            Iterator<Room> roomsIterator = roomsArr.iterator();
            if(roomsIterator.hasNext()){
                roomsIterator.next();
            }
            while(roomsIterator.hasNext()){
                pathDamage += roomsIterator.next().getBadguy_power();
            }
            if(pathDamage < damage2){
                damage2 = pathDamage;
                exit = currentRoom.getRoomName();
            }
            cont ++;
        }

        Iterator<Room> entranceToTarget = map.bestPath(map.getRoom(entrance), map.targetRoom());
        Iterator<Room> targetToExit = map.bestPath(map.targetRoom(), map.getRoom(exit));
        boolean isSuccess = false;

        System.out.println("----------------- Simulação Automática ----------------------");
        while(entranceToTarget.hasNext()){
            System.out.println("Próxima Divisão: " + entranceToTarget.next().getRoomName());
            System.out.println("---------------------------------------------------------");
        }
        System.out.println("\nENCONTRAMOS O ALVO! ");
        if((damage) == 0){
            System.out.println(" Chegou Ao Alvo Com 100 Pontos De Vida");
            System.out.println(" Ainda É Possível Concluir A Missão Com Sucesso");
            isSuccess = true;
        }else{
            System.out.println(" Chegou Ao Alvo Com " +(100 - damage)+ " Pontos De Vida");
            System.out.println(" Já Não É Possível Concluir A Missão Com Sucesso");
        }
        System.out.println("\nESTA NA HORA DE SAIR DO EDIFICIO! ");
        System.out.println("---------------------------------------------------------");

        targetToExit.next();

        while(targetToExit.hasNext()){
            System.out.println("Próxima Divisão: " + targetToExit.next().getRoomName());
            System.out.println("---------------------------------------------------------");
        }
        if(isSuccess) {
            System.out.println("\nMISSAO CONCLUIDA COM SUCESSO! ");
            System.out.println("\nPARABENS");
            System.out.println("\nPontos de Vida Restantes: " + (100 - damage));
            System.out.println("---------------------------------------------------------");
        }else{
            System.out.println("\nMISSAO CONCLUIDA SEM SUCESSO! ");
            System.out.println("\nO TÓ CRUZ CHEGOU AO ALVO COM PONTOS DE VIDA MENOR QUE 100 ");
        }
    }

    /**
     * Metodo que gera aleatoriamente um numero limitado entre o minimo e maximo
     * @param min numero inteiro minimo
     * @param max numero inteiro maximo
     * @return o numero gerado
     */
    private int limitedRandom(int min, int max) {
        Random rdm = new Random();
        return rdm.nextInt(max - min) + min;
    }

    /**
     * Metodo que usa as propriedades recursivas do metodo moveEnemy para mover os inimigos de sala
     * @param map mapa escolhido
     */
    private void moveEnemies(Map map) {
        Iterator<Room> itr = map.getRoomsIterator();

        while (itr.hasNext()) {
            Room current = itr.next();
            if (current.hasBadGuy()) {
                moveEnemy(current);
            }
        }
    }

    /**
     * Metodo recursivo utilizado para mover os inimigos de sala
     * @param current corresponde a uma sala do mapa
     */
    private void moveEnemy(Room current) {

        int pos = limitedRandom(0, current.numberOfConnections());
        int cont = 0;

        Iterator<Room> connects = current.getConnectionsIterator();

        while (connects.hasNext()) {
            Room connect = connects.next();

            if (cont == pos) {
                if (connect.hasBadGuy()) {
                    moveEnemy(connect);
                } else {
                    connect.setBadguy_power(current.getBadguy_power());
                    current.setBadguy_power(0);
                }
            }

            cont++;
        }
    }
}
