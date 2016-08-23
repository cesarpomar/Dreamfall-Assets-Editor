
package Datos.Textos;

import Datos.Assets.Assets;
import java.util.List;


public class Fichero {
    
    private Assets assets;      //Assets que contiene el fichero

    private String nombre;      //Nombre del fichero
    private int posicion;       //Posicion del fichero dentro del assets
    private Bloque[] bloque;    //Bloques de textos dentro del fichero
    
    public Fichero(String nombre){
        this.nombre=nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Bloque[] getBloque() {
        return bloque;
    }

    public void setBloque(Bloque[] bloque) {
        this.bloque = bloque;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public Assets getAssets() {
        return assets;
    }

    public void setAssets(Assets assets) {
        this.assets = assets;
    }
  
}
