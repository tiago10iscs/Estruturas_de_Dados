/**
 * Classe GameTry
 * Esta classe representa uma tentativa de jogo.
 *
 * @author Pedro Machado(8180238) & Tiago Moreira(8180275)
 */
public class GameTry implements Comparable<GameTry> {
    private String cod_mission;
    private int version;
    private String[] path;
    private boolean success;
    private int health;

    /**
     * Contrutor da classe GameTry
     *
     * @param cod_mission Nome da missão
     * @param success Resultado da missão ("true" caso a missão tenha sido concluida com sucesso, caso contrário "false")
     * @param path  Array de Strings com o caminho percorrido pelo Tó Cruz durante a missão
     * @param health Vida restante do Tó Cruz no fim da missão
     */
    public GameTry(String cod_mission, boolean success, String[] path, int health) {
        this.cod_mission = cod_mission;
        this.success = success;
        this.path = path;
        this.health = health;
    }

    /**
     * Obter código de missão da tentativa
     *
     * @return Retorna o código da missão
     */
    public String getCod_mission() {
        return cod_mission;
    }

    /**
     * Atribuir código de missão da tentativa
     *
     * @param cod_mission Nome da missão
     */
    public void setCod_mission(String cod_mission) {
        this.cod_mission = cod_mission;
    }

    /**
     * Obter versão do mapa da tentativa
     *
     * @return Retorna a versão do mapa da tentativa
     */
    public int getVersion() {
        return version;
    }

    /**
     *  Alterar versão do mapa da tentativa
     *
     * @param version Versão do mapa da tentativa
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * Obter array com o caminho percorrido
     *
     * @return Retorna um array de Strings com cada zona percorrida e o respetivo dano
     */
    public String[] getPath() {
        return path;
    }

    /**
     *  Alterar array com o caminho percorrido
     *
     * @param path Array de Strings com cada zona percorrida e o respetivo dano
     */
    public void setPath(String[] path) {
        this.path = path;
    }

    /**
     * Obter resultado da missão
     *
     * @return Retorna "true" caso a missão tenha sido concluída com sucesso, caso contrário retorna "false"
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     *  Alterar o resultado da tentativa
     *
     * @param success Resultado da tentativa
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Obter pontos de vida do Tó Cruz no final da missão
     *
     * @return Vida do Tó Cruz no fim da missão
     */
    public int getHealth() {
        return health;
    }

    /**
     * Alterar vida do Tó Cruz no fim da tentativa
     *
     * @param health Vida do Tó Cruz
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Comparador que permite comparar utilizando os pontos de vida restantes
     *
     * @param o Tentativa de jogo a ser comparada
     * @return Retorna "0" caso a vida das tentativas seja igual
     *         <p> Retorna "-1" caso a vida da tentativa compararada seja inferior</p>
     *         <p> Retorna "1" caso a vida da tentativa compararada seja inferior</p>
     */

    @Override
    public int compareTo(GameTry o) {
        if (this.health == o.health) {
            return 0;
        } else if (this.health > o.health) {
            return -1;
        } else {
            return 1;
        }
    }
}
