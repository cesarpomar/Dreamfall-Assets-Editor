/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos.Textos;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author César
 */
public class Bloque {

    private Fichero fichero;                   //fichero que contiene el bloque
    private List<Integer> basura;              //Basura antes de los textos
    private int tamOrg;                        //tamaño original de todo el bloque
    private int numeroIdiomas;                 //numero de idiomas en un bloque
    private byte[] id;                         //Identificador del bloque
    private HashMap<String, Idioma> idiomas;     //Idiomas dentro del Assets

    public int getNumeroIdiomas() {
        return numeroIdiomas;
    }

    public void setNumeroIdiomas(int numeroIdiomas) {
        this.numeroIdiomas = numeroIdiomas;
    }

    public byte[] getId() {
        return id;
    }

    public void setId(byte[] id) {
        this.id = id;
    }

    public Fichero getFichero() {
        return fichero;
    }

    public void setFichero(Fichero fichero) {
        this.fichero = fichero;
    }

    public HashMap<String, Idioma> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(HashMap<String, Idioma> idiomas) {
        this.idiomas = idiomas;
    }

    public List<Integer> getBasura() {
        return basura;
    }

    public void setBasura(List<Integer> basura) {
        this.basura = basura;
    }

    public int getTamOrg() {
        return tamOrg;
    }

    public void setTamOrg(int tamOrg) {
        this.tamOrg = tamOrg;
    }

}
