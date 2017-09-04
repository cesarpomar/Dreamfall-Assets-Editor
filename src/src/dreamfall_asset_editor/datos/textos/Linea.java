package dreamfall_asset_editor.datos.textos;

/**
 * Clase que representa cada linea que forma la traduccion de en un idioma
 *
 * @author César Pomar
 */
public final class Linea {

    private byte[] id;          //Identificador de la linea
    private int tamTotal;       //tamaño total de la linea
    private int tamPreLinea;    //tamaño del texto de la linea con su tamaño
    private int tamLinea;       //tamaño del texto de una linea
    private String texto;       //texto de la linea
    private byte[] finLinea;   //la linea contiene 10 02 al final

    public int getTamTotal() {
        return tamTotal;
    }

    public void setTamTotal(int tamTotal) {
        this.tamTotal = tamTotal;
    }

    public int getTamPreLinea() {
        return tamPreLinea;
    }

    public void setTamPreLinea(int tamPreLinea) {
        this.tamPreLinea = tamPreLinea;
    }

    public int getTamLinea() {
        return tamLinea;
    }

    public void setTamLinea(int tamLinea) {
        this.tamLinea = tamLinea;
    }

    public byte[] getId() {
        return id;
    }

    public void setId(byte[] id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public byte[] getFinLinea() {
        return finLinea;
    }

    public void setFinLinea(byte[] finLinea) {
        this.finLinea = finLinea;
    }

    public static int espacioTam(int tam) {
        if (tam / 0x80 == 0) {
            return 1;
        } else if (tam / 0x4000 == 0) {
            return 2;
        } else if (tam / 0x200000 == 0) {
            return 3;
        } else {
            return 4;
        }
    }

}
