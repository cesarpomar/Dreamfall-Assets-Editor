package Datos.Assets;

import Datos.Idioma.Localizacion;
import Datos.Textos.Fichero;

public class Assets {

    private String nombre;              //Nombre del Assets
    private String ruta;                //Ruta del Assets
    //Campos ordenados segun aparecen en la cabecera Assets
    private int tamCabecera;            //Tamaño de la cabera
    private int tamAssets;              //Tamaño total del Assets
    //Saltamos 4 bytes que no son necesarios
    private String version;             //Version del assets
    private int inicioFicheros;         //Posicion del primer archivo del Assets
    //Saltos 28 bytes que son necesarios
    private int numeroFicheros;         //Numero de ficheros dentro del Assets
    private Definicion[] definiciones;  //Punteros a los archivos dentro del Assets
    //Contenido del Assets
    private Fichero[] ficheros;
    //Idiomas del juego
    private Localizacion localizacion;
    //Solo unity 5
    private int especificaciondeTipos;  //Especificacion de los tipos de ficheros en assets

    public Assets(String nombre,String ruta) {
        this.nombre=nombre;
        this.ruta=ruta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public int getTamCabecera() {
        return tamCabecera;
    }

    public void setTamCabecera(int tamCabecera) {
        this.tamCabecera = tamCabecera;
    }

    public int getTamAssets() {
        return tamAssets;
    }

    public void setTamAssets(int tamAssets) {
        this.tamAssets = tamAssets;
    }

    public int getInicioFicheros() {
        return inicioFicheros;
    }

    public void setInicioFicheros(int inicioFicheros) {
        this.inicioFicheros = inicioFicheros;
    }

    public int getNumeroFicheros() {
        return numeroFicheros;
    }

    public void setNumeroFicheros(int numeroFicheros) {
        this.numeroFicheros = numeroFicheros;
    }

    public Definicion[] getDefiniciones() {
        return definiciones;
    }

    public void setDefiniciones(Definicion[] definiciones) {
        this.definiciones = definiciones;
    }

    public Fichero[] getFicheros() {
        return ficheros;
    }

    public void setFicheros(Fichero[] ficheros) {
        this.ficheros = ficheros;
    }

    public Localizacion getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(Localizacion localizacion) {
        this.localizacion = localizacion;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getEspecificaciondeTipos() {
        return especificaciondeTipos;
    }

    public void setEspecificaciondeTipos(int especificaciondeTipos) {
        this.especificaciondeTipos = especificaciondeTipos;
    }

}
