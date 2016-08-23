/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Datos.Assets.Assets;
import Datos.Assets.Definicion;
import Datos.Idioma.Localizacion;
import Datos.Textos.Bloque;
import Datos.Textos.Fichero;
import Datos.Textos.Idioma;
import Datos.Textos.Linea;
import Datos.Textos.Textos;
import Gui.Gui;
import IO.Analizador;
import IO.Conversion;
import IO.Escritura;
import IO.XML;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.BufferOverflowException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jdom2.JDOMException;

/**
 *
 * @author César
 */
public class Controlador {

    private Gui interfaz;

    public Controlador(Gui interfaz) {
        this.interfaz = interfaz;
    }

    private void crearIdiomaV(Idioma org, String nombre, String[] lineas) {
        Bloque b = org.getBloque();
        int tamIdioma = org.getTamIdioma();
        Idioma idioma = new Idioma(nombre);//Nuevo idioma
        idioma.setBloque(org.getBloque());
        Linea[] orgLineas = org.getLineas();
        Linea[] nLineas = new Linea[orgLineas.length];
        idioma.setLineas(nLineas);
        //Creamos un idoma imitando el de referencia
        for (int i = 0; i < nLineas.length; i++) {
            nLineas[i] = new Linea();
            if (i < lineas.length && lineas[i] != null) {
                nLineas[i].setTexto(lineas[i]);
            } else {
                nLineas[i].setTexto(" ");//Si no hay linea la escribimos vacia
            }
            //Copiamos el id de linea
            nLineas[i].setId(orgLineas[i].getId());
            try {
                nLineas[i].setTamLinea(nLineas[i].getTexto().getBytes("UTF-8").length);
            } catch (UnsupportedEncodingException ex) {
            }
            //Ajustamos el resto de tamaños
            //Difencia entre el espacio de los tamaños que ocupan los tamaños
            int difE = Linea.espacioTam(orgLineas[i].getTamLinea())
                    - Linea.espacioTam(nLineas[i].getTamLinea());
            //Diferencia entre el tamaño de las lineas
            int difT = orgLineas[i].getTamLinea() - nLineas[i].getTamLinea();
            //La diferencia entre el tam de lineas, menos la dif de lo que ocupa la anterior
            nLineas[i].setTamPreLinea(orgLineas[i].getTamPreLinea() - difT - difE);
            //Recalculamos el espacio teneion en cuenta el espacio de la anterior
            difE += Linea.espacioTam(orgLineas[i].getTamPreLinea())
                    - Linea.espacioTam(nLineas[i].getTamPreLinea());
            nLineas[i].setTamTotal(orgLineas[i].getTamTotal() - difT - difE);
            difE += Linea.espacioTam(orgLineas[i].getTamTotal())
                    - Linea.espacioTam(nLineas[i].getTamTotal());
            //Calculamos tamaño del bloque
            tamIdioma -= difE + difT;
            nLineas[i].setFinLinea(orgLineas[i].isFinLinea());
        }
        idioma.setTamIdioma(tamIdioma);
        int aumentoPadres = idioma.getTamIdioma();//Aumento de los fichero que lo contienen
        Idioma idiomaExistente = b.getIdiomas().get(idioma.getIdioma());
        if (idiomaExistente != null) {
            aumentoPadres -= idiomaExistente.getTamIdioma();
        } else {
            //Si el idioma no existe la cabecera del idioma tambien ocupa
            aumentoPadres += idioma.getEspacioIdioma() - idioma.getTamIdioma();
            b.setNumeroIdiomas(b.getNumeroIdiomas() + 1);
        }
        //Guardamos el idioma
        b.getIdiomas().put(idioma.getIdioma(), idioma);
        //Aumentalos a sus padres
        Fichero f = b.getFichero();
        Assets a = f.getAssets();
        //aumentamos el tamaño del fichero
        Definicion def = a.getDefiniciones()[f.getPosicion() - 1];
        //calculamos su espacio en bloques de 4
        double bloques = (def.getTam() + aumentoPadres) / 4.0 - (def.getTam() / 4.0);
        int desplazamiento = (int) Math.ceil(bloques) * 4; //numero de bloques
        def.setTam(def.getTam() + aumentoPadres);
        if (desplazamiento != 0) {
            //desplazar puntero
            avanzarDefiniciones(a.getDefiniciones(), f.getPosicion(), desplazamiento);
            //aumentamos tamaño de assests
            a.setTamAssets(a.getTamAssets() + desplazamiento);
        }
    }

    /**
     * Crea un nuevo idioma de subtitulos
     *
     * @param org Idioma de referencia
     * @param nombre Nombre del nuevo idioma (2 letras)
     * @param lineas Lineas de texto del nuevo idioma
     */
    private void crearIdioma(Idioma org, String nombre, String[] lineas) {
        Bloque b = org.getBloque();
        Idioma idioma = new Idioma(nombre);//Nuevo idioma
        idioma.setBloque(org.getBloque());
        Linea[] orgLineas = org.getLineas();
        Linea[] nLineas = new Linea[orgLineas.length];
        idioma.setLineas(nLineas);
        int tamIdioma = 0;
        //Creamos un idoma imitando el de referencia
        for (int i = 0; i < nLineas.length; i++) {
            nLineas[i] = new Linea();
            nLineas[i].setFinLinea(orgLineas[i].isFinLinea());
            int finLinea = 0;//aumento si hay fin de linea
            if (nLineas[i].isFinLinea()) {
                finLinea = 2;
            }
            if (i < lineas.length && lineas[i] != null) {
                nLineas[i].setTexto(lineas[i]);
            } else {
                nLineas[i].setTexto(" ");//Si no hay linea la escribimos vacia
            }
            //Copiamos el id de linea
            nLineas[i].setId(orgLineas[i].getId());
            //copiamos el tamaño de la linea
            nLineas[i].setTamLinea(Conversion.tamUTF8(nLineas[i].getTexto()));
            //Tamaño de prelinea(A0 + Tamlinea+ Linea + finLinea)
            nLineas[i].setTamPreLinea(1 + Linea.espacioTam(nLineas[i].getTamLinea())
                    + nLineas[i].getTamLinea() + finLinea);
            //Tam total(Id linea + tam preLinea+ 12 + Prelinea
            nLineas[i].setTamTotal(nLineas[i].getId().length + 1
                    + Linea.espacioTam(nLineas[i].getTamPreLinea()) + nLineas[i].getTamPreLinea());
            //Tam idioma+(12 +Tam Total + total)
            tamIdioma += 1 + Linea.espacioTam(nLineas[i].getTamTotal()) + nLineas[i].getTamTotal();

        }
        tamIdioma += b.getId().length;//añadimos el tamaño del id del bloque
        idioma.setTamIdioma(tamIdioma);
        int aumentoPadres = idioma.getEspacioIdioma();
        Idioma idiomaExistente = b.getIdiomas().get(idioma.getIdioma());
        Fichero f = b.getFichero();
        Assets a = f.getAssets();
        Definicion def = a.getDefiniciones()[f.getPosicion() - 1];
        if (idiomaExistente != null) {
            aumentoPadres -= idiomaExistente.getEspacioIdioma();
        } else {
            b.setNumeroIdiomas(b.getNumeroIdiomas() + 1);
        }
        //Guardamos el idioma
        b.getIdiomas().put(idioma.getIdioma(), idioma);
        //aumentamos la definicion
        def.setTam(def.getTam() + aumentoPadres);
        //aumentamos tamaño de assests
        a.setTamAssets(a.getTamAssets() + aumentoPadres);
        //desplazar puntero
        avanzarDefiniciones(a.getDefiniciones(), f.getPosicion(), aumentoPadres);
    }

    /**
     * Borra un idioma
     *
     * @param idioma Idioma a borrar
     */
    private void borrarIdioma(Idioma idioma) {
        //Cargamos los datos necesarios
        Bloque b = idioma.getBloque();
        Fichero f = b.getFichero();
        Assets a = f.getAssets();
        int ficheroIndex = f.getPosicion();
        Definicion def = f.getAssets().getDefiniciones()[ficheroIndex - 1];
        //Realizamos las redimensiones
        //Lo borramos del bloque
        b.getIdiomas().remove(idioma.getIdioma());
        b.setNumeroIdiomas(b.getNumeroIdiomas() - 1);
        //Reducimos los contenedores
        //calculamos su espacio en bloques de 4
        double bloques = def.getTam() / 4.0 - (def.getTam() - idioma.getEspacioIdioma()) / 4.0;
        int desplazamiento = (int) Math.ceil(bloques) * 4; //numero de bloques
        def.setTam(def.getTam() - idioma.getEspacioIdioma());
        a.setTamAssets(a.getTamAssets() - desplazamiento);
        avanzarDefiniciones(a.getDefiniciones(), f.getPosicion(), -idioma.getEspacioIdioma());
    }

    /*
     *Sumar desplazamiento a todas las definicones despues de index
     */
    private void avanzarDefiniciones(Definicion[] defs, int index, int desplazamiento) {
        for (int i = index; i < defs.length; i++) {
            defs[i].setPosicion(defs[i].getPosicion() + desplazamiento);
        }
    }

    private Textos importarXml(File path) {
        try {
            Textos t = XML.leer(path);
            //Comprbar la validez del idioma
            if (t.getIdioma().length() != 2) {
                JOptionPane.showMessageDialog(interfaz, "El idioma (" + t.getIdioma() + ") de (" + path.getName() + ") tiene un formato incorrecto.\n",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            return t;
        } catch (JDOMException ex) {
            JOptionPane.showMessageDialog(interfaz, "El fichero XML ha perdido su integridad.\n"
                    + "Error:\n" + toMl(ex.getMessage()),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(interfaz, "No se ha encontrado el fichero XML.\n"
                    + "Error:\n" + toMl(ex.getMessage()),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(interfaz, "Ha ocurrido un error durante la lectura.\n"
                    + "Error:\n" + toMl(ex.getMessage()),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(interfaz, "Se ha encontrado un id en (" + path.getName() + ") que no es un número.\n",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ArrayIndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(interfaz, "Se ha encontrado un id negativo en (" + path.getName() + ").\n",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    /**
     * Añade un idioma al archivo de subtitulos
     *
     * @param l Localizacion
     * @param idioma Idioma a añadir
     */
    public void addLocalizacion(Localizacion l, String idioma) {
        Assets a = l.getAssets();
        //Coge el numero el subtitulos
        int nSubs = l.getSubtitulos().length;
        String[] nuevos = new String[nSubs + 1];
        int i = 0;
        //Copiamos los subtitulos
        for (String s : l.getSubtitulos()) {
            if (!s.equals(idioma)) {
                nuevos[i++] = s;
            } else {
                //No puede haber dos iguales
                JOptionPane.showMessageDialog(interfaz, "El subtítulo que intentas añadir ya existe.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        nuevos[i] = idioma;//Añadimos el subtitulo
        //Ajustamos y lo guardamos
        ajustarLocalizacion(l, nuevos.length);
        l.setSubtitulos(nuevos);
        JOptionPane.showMessageDialog(interfaz, "Subtitulo añadido correctamente, a partir de ahora el juego"
                + "reconocera bloques con el nombre '" + idioma + "'",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Borra un idoma del archivo de subtitulos
     *
     * @param l Localizacion
     * @param idioma Idioma a borrar
     */
    public void removeLocalizacion(Localizacion l, String idioma) {
        Assets a = l.getAssets();
        //Coge el numero el subtitulos
        int nSubs = l.getSubtitulos().length;
        String[] nuevos = new String[nSubs - 1];
        int i = 0;
        //Copiamos los subtitulos
        for (String s : l.getSubtitulos()) {
            if (s.equals(idioma)) {
                nuevos[i++] = s;
            }
        }
        //Ajustamos y lo guardamos
        ajustarLocalizacion(l, nuevos.length);
        l.setSubtitulos(nuevos);
        JOptionPane.showMessageDialog(interfaz, "Subtitulo borrado correctamente, a partir de ahora el juego"
                + "ignorara bloques con el nombre '" + idioma + "'",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void ajustarLocalizacion(Localizacion l, int n) {
        Assets a = l.getAssets();
        //Reservamos espacio para el idioma 4 y su tamaño 4
        int aumento = (n - l.getSubtitulos().length) * 4 * 2;
        //Aumentamos el Assets
        a.setTamAssets(a.getTamAssets() + aumento);
        //Aumentamos el fichero
        Definicion def = a.getDefiniciones()[l.getIndex() - 1];
        def.setTam(def.getTam() + aumento);
        //Avanzamos los punteros
        avanzarDefiniciones(a.getDefiniciones(), l.getIndex(), aumento);
    }

    /**
     * Lee un assets
     *
     * @param path ruta
     */
    public void leerAssets(File path) {
        try (Analizador ana = new Analizador(path)) {
            interfaz.nuevoAssets(ana.leerAssets());
            JOptionPane.showMessageDialog(interfaz, "El Assets se ha cargado correctamente\n",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(interfaz, "El archivo Assets ya no existe.\n"
                    + "Error:\n" + toMl(ex.getMessage()),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(interfaz, "Ha ocurrido un error de lectura.\n"
                    + "Error:\n" + toMl(ex.getMessage()),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(interfaz, "El archivo Assets esta corrupto o no es compatible"
                    + "con esta apliacion.\n",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Lee una carpeta con Assets
     *
     * @param path ruta
     */
    public void leerCarpeta(File path) {
        File[] assets = path.listFiles((File dir, String name) -> name.endsWith(".assets"));
        try {
            for (File f : assets) {
                Analizador ana = new Analizador(f);
                interfaz.nuevoAssets(ana.leerAssets());
                ana.close();
            }
            JOptionPane.showMessageDialog(interfaz, "Los Assets se ha cargado correctamente\n",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(interfaz, "Un archivo Assets ya no existe.\n"
                    + "Error:\n" + toMl(ex.getMessage()),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(interfaz, "Ha ocurrido un error de lectura.\n"
                    + "Error:\n" + toMl(ex.getMessage()),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(interfaz, "Un archivo Assets esta corrupto o no es compatible"
                    + "con esta apliacion.\n",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void escribirAssets(File path, Assets a) {
        try {
            Escritura esc = new Escritura(a, path);
            if (esc.escribir()) {
                JOptionPane.showMessageDialog(interfaz, "El Assets se ha guardado correctamente\n",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(interfaz, "El Assets se ha creado correctamente, pero "
                        + "no ha podido substituirse por el original.\n Puedes hacerlo manualmente borrando el original y eliminado"
                        + "la extensión '.temp' del fichero guardado.",
                        "Éxito", JOptionPane.WARNING_MESSAGE);
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(interfaz, "El archivo Assets ya no existe.\n"
                    + "Error:\n" + toMl(ex.getMessage()),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(interfaz, "Ha ocurrido un error de escritura.\n"
                    + "Error:\n" + toMl(ex.getMessage()),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }catch (Exception ex) {
            JOptionPane.showMessageDialog(interfaz, "Ha ocurrido un error inesperado durante la escritura.\n"
                    + "Error:\n" + toMl(ex.getMessage()),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Exporta un Idioma en xml
     *
     * @param path ruta fichero
     * @param idioma Idioma a exportar
     */
    public void exportar(File path, Idioma idioma) {
        try {
            XML.escribir(path, new Textos(idioma));
            JOptionPane.showMessageDialog(interfaz, "El fichero se ha exportado correctamente\n",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(interfaz, "No se ha podido crear el fichero.\n"
                    + "Error:\n" + toMl(ex.getMessage()),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(interfaz, "Error de escritura\n"
                    + "Error:\n" + toMl(ex.getMessage()),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Exporta un Idioma en xml
     *
     * @param path ruta fichero
     * @param f fichero a exportar
     * @param idioma idioma del fichero a exportar
     */
    public void exportar(File path, Fichero f, String idioma) {
        try {
            //recorremos todos los bloques del fichero
            int p = 1;
            for (Bloque b : f.getBloque()) {
                XML.escribir(new File(path, f.getNombre() + "_" + idioma + "_" + p + ".xml"), new Textos(b.getIdiomas().get(idioma)));
                p++;
            }
            JOptionPane.showMessageDialog(interfaz, "Los ficheros se han exportado correctamente\n",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(interfaz, "No se ha podido crear un fichero.\n"
                    + "Error:\n" + toMl(ex.getMessage()),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(interfaz, "Error de escritura\n"
                    + "Error:\n" + toMl(ex.getMessage()),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Importa un idioma de xml
     *
     * @param path ruta fichero
     * @param org idioma original
     */
    public void importar(File path, Idioma org) {
        Textos t = importarXml(path);
        if (t == null) {
            return;
        }
        if (Arrays.equals(org.getBloque().getId(), t.getId())) {
            if (org.getIdioma().equals(t.getIdioma())) {
                crearIdioma(org, org.getIdioma(), t.getLineas());
                JOptionPane.showMessageDialog(interfaz, "El fichero se ha importado correctamente.\n",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(interfaz, "El fichero no pertenece a este idioma.\n",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(interfaz, "El fichero no pertenece a este bloque.\n",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Importa un idioma de xml
     *
     * @param path carpeta con los ficheros
     * @param f idioma original
     */
    public void importar(File path, Fichero f) {
        //obtenemos los ficheros xml
        File[] xml = path.listFiles((File dir, String name) -> name.endsWith(".xml"));
        int cargados = 0;//ficheros cargados correctamente
        //Recorremos todas
        for (File actual : xml) {
            Textos t = importarXml(actual);
            if (t == null) {
                continue;
            }
            //Si encontramos su idioma
            for (Bloque org : f.getBloque()) {
                if (Arrays.equals(org.getId(), t.getId())) {
                    Idioma idioma = org.getIdiomas().get(t.getIdioma().toLowerCase());
                    if (idioma != null) {
                        crearIdioma(idioma, t.getIdioma().toLowerCase(), t.getLineas());
                        cargados++;
                    }
                    break;
                }
            }

        }
        JOptionPane.showMessageDialog(interfaz, "Se han cargado " + cargados + " xml de " + xml.length + "xml.\n",
                "Terminado", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Crea una copia exacta de un idoma existente
     *
     * @param f fichero
     * @param org Idioma a copiar
     * @param nombre Nombre del nuevo idioma
     */
    public void copiarIdioma(Fichero f, String org, String nombre) {
        //recorremos todos los bloques del fichero
        for (Bloque b : f.getBloque()) {
            Idioma idioma = b.getIdiomas().get(org);
            String[] lineas = new String[idioma.getLineas().length];
            //Copiamos el texto
            for (int i = 0; i < lineas.length; i++) {
                lineas[i] = idioma.getLineas()[i].getTexto();
            }
            //creamos el idioma
            crearIdioma(idioma, nombre, lineas);
        }
        JOptionPane.showMessageDialog(interfaz, "El idioma '" + nombre + "' se ha creado correctamente, para que el juego"
                + "lo reconozca el idioma debe ser añadido en el Assets de subtitulos.\n",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Borra un bloque
     *
     * @param idioma Idioma a borrar
     */
    public void borrar(Idioma idioma) {
        borrarIdioma(idioma);
        JOptionPane.showMessageDialog(interfaz, "El idioma bloque se ha borrado correctamente\n",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Borra un idioma
     *
     * @param idioma Idioma a borrar
     */
    public void borrar(Fichero f, String idioma) {
        for (Bloque b : f.getBloque()) {
            borrarIdioma(b.getIdiomas().get(idioma));
        }
        JOptionPane.showMessageDialog(interfaz, "El idioma '" + idioma + "' se ha borrado correctamente\n",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private String toMl(String tex) {
        StringBuilder lineas = new StringBuilder(tex);
        int lineaMax = 100;//Caracteres por linea
        for (int i = 0; i < tex.length(); i = i + lineaMax) {
            lineas.insert(i, '\n');
        }
        return lineas.toString();
    }

    public void cambiarIdioma(File path, String nuevo, Assets[] assets) {
        try {
            Textos t = importarXml(path);
            if (t == null) {
                return;
            }
            Idioma org = null, dest = null;
            //buscamos el bloque al que pertenece
            bucle:
            for (Assets a : assets) {
                for (Fichero f : a.getFicheros()) {
                    for (Bloque b : f.getBloque()) {
                        if (Arrays.equals(t.getId(), b.getId())) {
                            org = b.getIdiomas().get(t.getIdioma());
                            dest = b.getIdiomas().get(nuevo);
                            break bucle;
                        }
                    }
                }
            }
            if (org == null || dest == null) {
                JOptionPane.showMessageDialog(interfaz, "Para realizar esta operación debes"
                        + "tener el Assets correspondiente abierto.\n", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            t.setLineas(ajustarLineasO(org.getLineas(), dest.getLineas(), t));
            File salida = new File(path.getAbsolutePath() + ".tmp");
            t.setIdioma(nuevo);
            XML.escribir(salida, t);
            if (path.delete() && salida.renameTo(path)) {
                JOptionPane.showMessageDialog(interfaz, "El idioma del archivo ha sido cambiado.\n",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(interfaz, "El idioma del archivo ha sido cambiado"
                        + "pero no se ha podido sustituirlo por el original.",
                        "Éxito", JOptionPane.WARNING_MESSAGE);
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(interfaz, "No se ha podido modificar el fichero.\n"
                    + "Error:\n" + toMl(ex.getMessage()),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(interfaz, "Error de escritura\n"
                    + "Error:\n" + toMl(ex.getMessage()),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String[] ajustarLineasO(Linea[] org, Linea[] dest, Textos t) {
        String[] listas = new String[org.length];
        HashMap<String, Integer> posicion = new HashMap<>(listas.length);
        int i = 0;
        //mapeamos las posiciones en los ids
        for (Linea l : dest) {
            posicion.put(Textos.toString(l.getId()), i++);
        }
        //ponemos las lineas en la posicion de su id
        for (i = 0; i < t.getLineas().length; i++) {
            listas[posicion.get(Textos.toString(org[i].getId()))] = t.getLineas()[i];
        }
        return listas;
    }

    public void actualizarXmlRef(File path) {
        try {
            if (!new File(path.getAbsolutePath() + ".ref").exists()) {
                JOptionPane.showMessageDialog(interfaz, "No se ha encontrado el archivo .ref\n",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            XML.actualizarXmlRef(path);
            JOptionPane.showMessageDialog(interfaz, "El XML se ha actualizado correctamente.\n",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (JDOMException ex) {
            JOptionPane.showMessageDialog(interfaz, "El fichero XML ha perdido su integridad.\n"
                    + "Error:\n" + toMl(ex.getMessage()),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(interfaz, "No se ha encontrado el fichero XML.\n"
                    + "Error:\n" + toMl(ex.getMessage()),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(interfaz, "Ha ocurrido un error durante la lectura.\n"
                    + "Error:\n" + toMl(ex.getMessage()),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
