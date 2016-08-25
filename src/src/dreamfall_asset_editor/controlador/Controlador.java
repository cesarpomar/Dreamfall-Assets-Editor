package dreamfall_asset_editor.controlador;

import dreamfall_asset_editor.datos.assets.Assets;
import dreamfall_asset_editor.datos.assets.Definicion;
import dreamfall_asset_editor.datos.idioma.Localizacion;
import dreamfall_asset_editor.datos.textos.Bloque;
import dreamfall_asset_editor.datos.textos.Fichero;
import dreamfall_asset_editor.datos.textos.Idioma;
import dreamfall_asset_editor.datos.textos.Linea;
import dreamfall_asset_editor.datos.textos.Textos;
import dreamfall_asset_editor.gui.Consola;
import dreamfall_asset_editor.gui.Igui;
import dreamfall_asset_editor.io.Analizador;
import dreamfall_asset_editor.io.Conversion;
import dreamfall_asset_editor.io.Escritura;
import dreamfall_asset_editor.io.Exception.FormatException;
import dreamfall_asset_editor.io.XML;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import org.jdom2.JDOMException;

/**
 * Clase controlador
 *
 * @author César Pomar
 */
public final class Controlador {

    private Igui interfaz;

    public Controlador(Igui interfaz) {
        this.interfaz = interfaz;
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
        if (org.getRecursos() != null) {
            idioma.setRecursos(new byte[0]);
        }
        if (idiomaExistente != null) {
            aumentoPadres -= idiomaExistente.getEspacioIdioma();
        } else {
            b.setNumeroIdiomas(b.getNumeroIdiomas() + 1);
            if (org.getRecursos() != null) {
                aumentoPadres += 4;
            }
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
        int recursos = 0;
        if (idioma.getRecursos() != null) {
            recursos = 4;
        }
        def.setTam(def.getTam() - idioma.getEspacioIdioma() - recursos);
        a.setTamAssets(a.getTamAssets() - desplazamiento - recursos);
        avanzarDefiniciones(a.getDefiniciones(), f.getPosicion(), -idioma.getEspacioIdioma() - recursos);
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
                interfaz.mostrar("El idioma (" + t.getIdioma() + ") de (" + path.getName() + ") tiene un formato incorrecto.\n",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            return t;
        } catch (JDOMException ex) {
            interfaz.mostrar("El fichero XML ha perdido su integridad.\n"
                    + "Error:\n" + toMl(ex.getMessage()),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (FileNotFoundException ex) {
            interfaz.mostrar("No se ha encontrado el fichero XML.\n"
                    + "Error:\n" + toMl(ex.getMessage()),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            interfaz.mostrar("Ha ocurrido un error durante la lectura.\n"
                    + "Error:\n" + toMl(ex.getMessage()),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            interfaz.mostrar("Se ha encontrado un id en (" + path.getName() + ") que no es un número.\n",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ArrayIndexOutOfBoundsException ex) {
            interfaz.mostrar("Se ha encontrado un id negativo en (" + path.getName() + ").\n",
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
                interfaz.mostrar("El subtítulo que intentas añadir ya existe.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        nuevos[i] = idioma;//Añadimos el subtitulo
        //Ajustamos y lo guardamos
        ajustarLocalizacion(l, nuevos.length);
        l.setSubtitulos(nuevos);
        interfaz.mostrar("Subtitulo añadido correctamente, a partir de ahora el juego"
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
        //Si el idioma no existe no hacemos nada
        if(!Arrays.asList(l.getSubtitulos()).contains(idioma)){
            return;
        }
        //Coge el numero el subtitulos
        int nSubs = l.getSubtitulos().length;
        String[] nuevos = new String[nSubs - 1];
        int i = 0;
        //Copiamos los subtitulos
        for (String s : l.getSubtitulos()) {
            if (!s.equals(idioma)) {
                nuevos[i++] = s;
            }
        }
        //Ajustamos y lo guardamos
        ajustarLocalizacion(l, nuevos.length);
        l.setSubtitulos(nuevos);
        interfaz.mostrar("Subtitulo borrado correctamente, a partir de ahora el juego"
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
     * Lee un assets y lo carga en la interfaz
     *
     * @param path ruta
     * @return Assets leido
     */
    public Assets leerAssets(File path) {
        try (Analizador ana = new Analizador(path)) {
            Assets assets = ana.leerAssets();
            interfaz.nuevoAssets(assets);
            interfaz.mostrar("El Assets se ha cargado correctamente\n",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            return assets;
        } catch (FileNotFoundException ex) {
            interfaz.mostrar("El archivo Assets ya no existe.\n"
                    + "Error:\n" + toMl(ex.getMessage()),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            interfaz.mostrar("Ha ocurrido un error de lectura.\n"
                    + "Error:\n" + toMl(ex.getMessage()),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (FormatException ex) {
            interfaz.mostrar("El archivo no es compatible con la aplicación.\n",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            interfaz.mostrar("El archivo Assets esta corrupto.\n",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
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
            interfaz.mostrar("Los Assets se ha cargado correctamente\n",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException ex) {
            interfaz.mostrar("Un archivo Assets ya no existe.\n"
                    + "Error:\n" + toMl(ex.getMessage()),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            interfaz.mostrar("Ha ocurrido un error de lectura.\n"
                    + "Error:\n" + toMl(ex.getMessage()),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            interfaz.mostrar("Un archivo Assets esta corrupto o no es compatible"
                    + "con esta apliacion.\n",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void escribirAssets(File path, Assets a) {
        try {
            Escritura esc = new Escritura(a, path);
            if (esc.escribir()) {
                interfaz.mostrar("El Assets se ha guardado correctamente\n",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                interfaz.mostrar("El Assets se ha creado correctamente, pero "
                        + "no ha podido substituirse por el original.\n Puedes hacerlo manualmente borrando el original y eliminado"
                        + "la extensión '.temp' del fichero guardado.",
                        "Éxito", JOptionPane.WARNING_MESSAGE);
            }
        } catch (FileNotFoundException ex) {
            interfaz.mostrar("El archivo Assets ya no existe.\n"
                    + "Error:\n" + toMl(ex.getMessage()),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            interfaz.mostrar("Ha ocurrido un error de escritura.\n"
                    + "Error:\n" + toMl(ex.getMessage()),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            interfaz.mostrar("Ha ocurrido un error inesperado durante la escritura.\n"
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
            interfaz.mostrar("El fichero se ha exportado correctamente\n",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException ex) {
            interfaz.mostrar("No se ha podido crear el fichero.\n"
                    + "Error:\n" + toMl(ex.getMessage()),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            interfaz.mostrar("Error de escritura\n"
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
            interfaz.mostrar("Los ficheros se han exportado correctamente\n",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException ex) {
            interfaz.mostrar("No se ha podido crear un fichero.\n"
                    + "Error:\n" + toMl(ex.getMessage()),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            interfaz.mostrar("Error de escritura\n"
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
                interfaz.mostrar("El fichero se ha importado correctamente.\n",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                interfaz.mostrar("El fichero no pertenece a este idioma.\n",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            interfaz.mostrar("El fichero no pertenece a este bloque.\n",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Importa una carpeta de xml en un fichero
     *
     * @param path carpeta con los ficheros
     * @param f idioma original
     */
    public void importar(File path, Fichero f) {
        //obtenemos los ficheros xml
        File[] xml = path.listFiles((File dir, String name) -> name.endsWith(".xml"));
        importar(xml, f);
    }

    /**
     * Importa varios xml en un fichero
     *
     * @param xml ficheros xml a importar
     * @param f idioma original
     */
    public void importar(File[] xml, Fichero f) {
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
        interfaz.mostrar("Se han cargado " + cargados + " xml de " + xml.length + "xml.\n",
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
        interfaz.mostrar("El idioma '" + nombre + "' se ha creado correctamente, para que el juego"
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
        interfaz.mostrar("El idioma bloque se ha borrado correctamente\n",
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
        interfaz.mostrar("El idioma '" + idioma + "' se ha borrado correctamente\n",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Ajusta la cantidad de caracteres que se muestran en una linea
     *
     * @param tex Texto
     * @return Texto ajustado
     */
    private static String toMl(String tex) {
        if (tex == null) {
            return "";
        }
        StringBuilder lineas = new StringBuilder(tex);
        int lineaMax = 100;//Caracteres por linea
        for (int i = 0; i < tex.length(); i = i + lineaMax) {
            lineas.insert(i, '\n');
        }
        return lineas.toString();
    }

    /**
     * Cambiar el idioma de un xml actualizando el orden de las lineas
     *
     * @param path Fichero
     * @param nuevo Idioma nuevo
     * @param assets Assets
     */
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
                interfaz.mostrar("Para realizar esta operación debes"
                        + "tener el Assets correspondiente abierto.\n", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            t.setLineas(ajustarLineas(org.getLineas(), dest.getLineas(), t));
            File salida = new File(path.getAbsolutePath() + ".tmp");
            t.setIdioma(nuevo);
            XML.escribir(salida, t);
            if (path.delete() && salida.renameTo(path)) {
                interfaz.mostrar("El idioma del archivo ha sido cambiado.\n",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                interfaz.mostrar("El idioma del archivo ha sido cambiado"
                        + "pero no se ha podido sustituirlo por el original.",
                        "Éxito", JOptionPane.WARNING_MESSAGE);
            }
        } catch (FileNotFoundException ex) {
            interfaz.mostrar("No se ha podido modificar el fichero.\n"
                    + "Error:\n" + toMl(ex.getMessage()),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            interfaz.mostrar("Error de escritura\n"
                    + "Error:\n" + toMl(ex.getMessage()),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Cambiar el orden de las lineas del idioma actual al nuevo
     *
     * @param org Lineas del idioma original
     * @param dest Lineas del idioma destino
     * @param t Textos importados xml
     * @return Texto lineas
     */
    private String[] ajustarLineas(Linea[] org, Linea[] dest, Textos t) {
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

    /**
     * Actualiza un xml con .ref de la version extractor a la editor
     *
     * @param path Ruta del xml
     */
    public void actualizarXmlRef(File path) {
        try {
            if (!new File(path.getAbsolutePath() + ".ref").exists()) {
                interfaz.mostrar("No se ha encontrado el archivo .ref\n",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            XML.actualizarXmlRef(path);
            interfaz.mostrar("El XML se ha actualizado correctamente.\n",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (JDOMException ex) {
            interfaz.mostrar("El fichero XML ha perdido su integridad.\n"
                    + "Error:\n" + toMl(ex.getMessage()),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (FileNotFoundException ex) {
            interfaz.mostrar("No se ha encontrado el fichero XML.\n"
                    + "Error:\n" + toMl(ex.getMessage()),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            interfaz.mostrar("Ha ocurrido un error durante la lectura.\n"
                    + "Error:\n" + toMl(ex.getMessage()),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
//-------------------------SCRIPS------------------------

    /**
     * Obtiene los comandos desde los argumentos del programa
     *
     * @param args Lista de comandos y argumentos mezclados
     * @return Comandos con sus respectivos argumentos
     */
    public String[][] getComandos(String[] args) {
        ArrayList<String> cmds = null;
        ArrayList<ArrayList<String>> lista = new ArrayList<>(10);
        for (String arg : args) {
            if (arg.startsWith("-")) {
                if (cmds != null) {
                    lista.add(cmds);
                }
                cmds = new ArrayList<>(10);
            }
            if (cmds != null) {
                cmds.add(arg);
            }
        }
        if (cmds != null) {
            lista.add(cmds);
        }
        //Generamos la matriz de comandos con argumentos
        String m[][] = null;
        if (!lista.isEmpty()) {
            m = new String[lista.size()][];
            for (int i = 0; i < lista.size(); i++) {
                if (!lista.get(i).isEmpty()) {
                    m[i] = lista.get(i).toArray(new String[1]);
                }
            }
        }
        return m;
    }

    /**
     * Guarda un script para su posterior edición
     *
     * @param d Dialogo
     * @param ruta Ruta
     * @param comandos Comandos
     */
    public static void guardarScript(JDialog d, File ruta, String[] comandos) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(ruta));
            for (String comando : comandos) {
                bw.write(comando);
                bw.newLine();
            }
            bw.close();
            JOptionPane.showMessageDialog(d, "Script guardado correctamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(d, "No se ha podido guardar el script.\n"
                    + "Error:\n" + toMl(ex.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Exporta un archivo para su ejecucion de forma nativa en el sistema
     *
     * @param d Dialogo
     * @param ruta Ruta
     * @param comandos Comandos
     */
    public static void exportarScript(JDialog d, File ruta, String[] comandos) {
        String jar = "DreamfallAssetsEditor.jar";
        String sep = System.getProperty("file.separator");
        String java = "\"" + System.getProperty("java.home") + sep + "bin" + sep + "java\"";

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(ruta));
            bw.write(java + " ");
            bw.write("-jar ");
            bw.write(jar + " ");
            for (String comando : comandos) {
                bw.write(comando + " ");
            }
            bw.close();
            JOptionPane.showMessageDialog(d, "Script exportado correctamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(d, "No se ha podido exportar el script.\n"
                    + "Error:\n" + toMl(ex.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Abre un script para su edicion o ejecución
     *
     * @param d Dialogo
     * @param ruta Ruta
     * @return Comandos
     */
    public static String[] abrirScript(JDialog d, File ruta) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(ruta));
            List<String> comandos = new ArrayList<>(10);
            String linea;
            while ((linea = br.readLine()) != null) {
                comandos.add(linea);
            }
            br.close();
            return comandos.toArray(new String[]{});
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(d, "No se ha podido abrir el script.\n"
                    + "Error:\n" + toMl(ex.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    /**
     * Ejecuta un script
     *
     * @param d Dialogo
     * @param comandos Comandos
     */
    public static void ejecutarScript(JDialog d, String[] comandos) {
        List<String> args = new ArrayList<>(comandos.length * 4);
        StringBuilder sb = new StringBuilder(300);
        for (String comando : comandos) {
            char[] array = comando.toCharArray();
            for (int i = 0; i < array.length; i++) {
                if (array[i] == '"') {
                    i++;
                    while (i < array.length && array[i] != '"') {
                        sb.append(array[i]);
                        i++;
                    }
                } else if (array[i] != ' ') {
                    sb.append(array[i]);
                } else {
                    args.add(sb.toString());
                    sb = new StringBuilder(300);
                }
            }
            if (sb.length() > 0) {
                args.add(sb.toString());
                sb = new StringBuilder(300);
            }
        }
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("log.txt"));
            System.setOut(new PrintStream(bos));
            new Consola(args.toArray(new String[]{}));
            bos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.setOut(System.out);
        JOptionPane.showMessageDialog(d, "El script ha terminado su ejecución, comprueba\n"
                + "el fichero log.txt para comprobar si se ejecutó\n"
                + "correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
    }
}
