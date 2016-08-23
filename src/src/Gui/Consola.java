/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Controlador.Controlador;
import Datos.Assets.Assets;
import Datos.Textos.Fichero;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author César
 */
public class Consola implements Igui {

    private Assets asset;
    private Controlador controlador;
    private boolean error;

    public Consola(String[] args) {
        controlador = new Controlador(this);
        String[][] comandos=controlador.getComandos(args);
        if(comandos!=null){
            ejecutar(comandos);
        }
    }

    private void ejecutar(String[][] comandos) {
        for (String[] cmd : comandos) {
            if(error){
                break;
            }
            switch (cmd[0]) {
                //Abrir
                case "-a":
                    if (!error(cmd, 1)) {
                        controlador.leerAssets(new File(cmd[1]));
                    }
                    break;
                //Guargar   
                case "-g":
                    if (!error(cmd, 1) && abierto()) {
                        controlador.escribirAssets(new File(cmd[1]), asset);
                    }
                    break;
                //Nuevo idioma
                case "-ni":
                    if (!error(cmd, 3) && abierto() && fichero()) {
                        controlador.copiarIdioma(getFichero(cmd[1]), cmd[3], cmd[2]);
                    }
                    break;
                //Borrar idioma
                case "-bi":
                    if (!error(cmd, 2) && abierto() && fichero()) {
                        controlador.borrar(getFichero(cmd[1]), cmd[2]);
                    }
                    break;
                //Importar idioma
                case "-ii":
                    if (!error(cmd, 2) && abierto() && fichero()) {
                        controlador.importar(new File(cmd[1]),getFichero(cmd[2]));
                    }
                    break;
                //Exportar idioma
                case "-ei":
                    if (!error(cmd, 3) && abierto() && fichero()) {
                        controlador.exportar(new File(cmd[1]),getFichero(cmd[2]),cmd[3]);
                    }
                    break;
                //Nuevo subtitulo
                case "-ns":
                    if (!error(cmd, 1) && abierto() && localizacion()) {
                        controlador.addLocalizacion(asset.getLocalizacion(), cmd[1]);
                    }
                    break;
                //Borrar subtitulo
                case "-bs":
                    if (!error(cmd, 1) && abierto() && localizacion()) {
                        controlador.removeLocalizacion(asset.getLocalizacion(), cmd[1]);
                    }
                    break;                     
                default:
                    System.out.println("Comando " + cmd[0] + " desconocido");
                case "-h":
                    System.out.println("Comandos: \n"
                            + "-a ruta                                      -> Abre un assets.\n"
                            + "-g ruta                                      -> Guarda el assets.\n"
                            + "-ni nombre_fichero nuevo_idioma idioma_ref   -> Crea un nuevo idioma.\n"
                            + "-bi nombre_fichero idioma                    -> Borra un idioma.\n"
                            + "-ii carpeta_xml nombre_fichero               -> Carga una carpeta de xml.\n"
                            + "-ei carpeta_xml nombre_fichero idioma        -> Exporta un idioma en xml.\n"
                            + "-ns idioma                                   -> Crea un subtítulo para ese idioma\n"
                            + "-bs idioma                                   -> Borra el subtítulo para ese idioma\n"
                            + "-h                                           -> muestra esta ayuda.");
            }
        }
    }

    /*
    *Da error si un comando no recive el numero correcto de parametros
     */
    private boolean error(String[] args, int params) {
        if (args.length - 1 != params) {
            if (args.length - 1 > params) {
                System.out.println("El comando " + args[0] + " requiere solo " + params + " parametros.");
            } else {
                System.out.println("El comando " + args[0] + " requiere al menos " + params + " parametros.");
            }
            error=true;
        }
        return false;
    }

    private boolean abierto() {
        if (asset == null) {
            System.out.println("Primero hay que abrir un assets.");
            error=true;
        }
        return true;
    }
    
    private boolean fichero() {
        if (asset.getFicheros()==null) {
            System.out.println("El assets no contiene ficheros.");
            error=true;
        }
        return true;
    }
    
    private boolean localizacion() {
        if (asset == null) {
            System.out.println("El assets no contiene localizaciones.");
            error=true;
        }
        return true;
    }

    private Fichero getFichero(String fichero) {
        Fichero find = null;
        for (Fichero f : asset.getFicheros()) {
            if (f.getNombre().equals(fichero)) {
                find = f;
                break;
            }
        }
        if (find == null) {
            System.out.println("Fichero " + fichero + " no encontrado en " + asset.getNombre()+".");
            System.exit(-1);
        }
        return find;
    }

    @Override
    public Assets[] getAssets() {
        return new Assets[]{asset};
    }

    @Override
    public void nuevoAssets(Assets a) {
        asset = a;
    }

    @Override
    public void mostrar(Object message, String title, int messageType) {
        if (messageType == JOptionPane.ERROR_MESSAGE) {
            System.out.println(message);
            error=true;
        }
    }

}
