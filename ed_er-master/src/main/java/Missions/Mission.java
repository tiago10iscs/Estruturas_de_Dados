package Missions;

import Structures.Lists.ListExceptions;
import Structures.Lists.OrderedList;

/**
 *  Classe Mission
 *  Esta classe representa uma missão a ser jogada
 *
 * @author Pedro Machado(8180238) & Tiago Moreira(8180275)
 */
public class Mission {
    private String missionName;
    private OrderedList<Version> versions;

    public Mission(String missionName) {
        this.missionName = missionName;
    }

    public Mission() {
    }

    /**
     * Metodo para obter o nome da missão
     * @return o nome da missão
     */
    public String getMissionName() {
        return missionName;
    }

    /**
     * Metodo para atribuir um nome á missão
     * @param missionName nome da missão que vai ser atribuido
     */
    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    /**
     * Metodo para obter a lista de versões de uma missão
     * @return a lista de versões
     */
    public OrderedList<Version> getVersions() {
        return versions;
    }

    /**
     *Metodo para atribuir uma lista de versões
     * @param versions lista a atribuir
     */
    public void setVersions(OrderedList<Version> versions) {
        this.versions = versions;
    }

    /**
     * Metodo de adicionar uma versão á lista de versões
     * @param version versão a ser adicionada á lista
     * @return um tipo booleano que é true quando a versão é adicionada á lista e false quando não o é
     */
    public boolean addVersion(Version version) {
        try {
            versions.add(version);
            return true;
        } catch (ListExceptions listExceptions) {
            listExceptions.printStackTrace();
            return false;
        }
    }
}
