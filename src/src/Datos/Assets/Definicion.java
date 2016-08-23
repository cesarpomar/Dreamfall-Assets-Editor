
package Datos.Assets;


public class Definicion {
    private int index;         //Index dentro del Assets
    private int posicion;   //Posicion del archivo
    private int tam;        //Tamaño del archivo
    private int tipo;       //Tipo de archivo
    private int uc;         //Valor desconocido

    public Definicion(){
        
    }
    
    public Definicion(int id, int posicion, int tam, int tipo,int uc) {
        this.index = id;
        this.posicion = posicion;
        this.tam = tam;
        this.tipo=tipo;
        this.uc=uc;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public void setTam(int tam) {
        this.tam = tam;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public void setUc(int uc) {
        this.uc = uc;
    }

    public int getIndex() {
        return index;
    }

    public int getPosicion() {
        return posicion;
    }

    public int getTam() {
        return tam;
    }

    public int getTipo() {
        return tipo;
    }

    public int getUc() {
        return uc;
    }

    @Override
    public String toString() {
        return "{" + "id=" + index + ", posicion=" + posicion + ", tam=" + tam + '}';
    } 
    
}
