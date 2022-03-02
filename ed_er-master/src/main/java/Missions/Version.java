package Missions;

/**
 *  Classe Version
 *  Esta classe representa uma versão de uma detrminada missão
 *
 * @author Pedro Machado(8180238) & Tiago Moreira(8180275)
 */
public class Version implements Comparable<Version> {
    private int versionNumber;
    private String filename;

    public Version() {
    }

    /**
     * Metodo para obter o numero da versão
     * @return retorna o numero da versão
     */
    public int getVersionNumber() {
        return versionNumber;
    }

    /**
     * Metodo para atribuir um numero á versão
     * @param versionNumber numero a atribuir á versão
     */
    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    /**
     * Metodo para obter o nome do ficheiro
     * @return o nome do ficheiro
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Metodo para atribuir um nome ao ficheiro
     * @param filename nome a ser atribuido ao ficheiro
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Comparador que permite comparar os numeros das versões
     * @param o versão a ser comparada
     * @return Retorna "0" caso as versões sejam iguais
     *             <p> Retorna "-1" caso a versão comprada seja inferior</p>
     *             <p> Retorna "1" caso a a versão comparada seja superior</p>
     */
    @Override
    public int compareTo(Version o) {

        if(o.versionNumber == this.versionNumber) {
            return 0;
        } else if (this.versionNumber > o.versionNumber) {
            return 1;
        } else {
            return -1;
        }
    }
}
