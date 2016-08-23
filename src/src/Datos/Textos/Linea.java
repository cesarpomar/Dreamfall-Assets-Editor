/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos.Textos;

/**
 *
 * @author César
 */
public class Linea {

    private byte[] id;          //Identificador de la linea
    private int tamTotal;       //tamaño total de la linea
    private int tamPreLinea;    //tamaño del texto de la linea con su tamaño
    private int tamLinea;       //tamaño del texto de una linea
    private String texto;       //texto de la linea
    private boolean finLinea;   //la linea contiene 10 02 al final

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

    public boolean isFinLinea() {
        return finLinea;
    }

    public void setFinLinea(boolean finLinea) {
        this.finLinea = finLinea;
    }

    public static int espacioTam(int tam) {
        if (tam / 0x80 == 0) {
            return 1;
        } else {
            if (tam / 0x4000 == 0) {
                return 2;
            } else {
                if (tam / 0x200000 == 0) {
                    return 3;
                } else {
                    return 4;
                }
            }
        }
    }

}
