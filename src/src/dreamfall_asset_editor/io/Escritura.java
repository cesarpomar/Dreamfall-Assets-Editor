package dreamfall_asset_editor.io;

import dreamfall_asset_editor.datos.assets.Assets;
import dreamfall_asset_editor.datos.assets.Definicion;
import dreamfall_asset_editor.datos.idioma.Localizacion;
import dreamfall_asset_editor.datos.textos.Bloque;
import dreamfall_asset_editor.datos.textos.Fichero;
import dreamfall_asset_editor.datos.textos.Idioma;
import dreamfall_asset_editor.datos.textos.Linea;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Clase que actualiza los datos en memoria de un asset en el fichero
 *
 * @author César Pomar
 */
public final class Escritura implements Closeable {

    private Assets assets;              //Assets con los datos
    private File salida;                //Archivo de salida final 
    private File temp;                  //Archivo temporal de salida
    private Printer printer;            //Gestion de entrada y salida
    ///Datos para el analisis
    private int cabFichero;             //tamaño de las cabecera del fichero
    private boolean localizacion;         //espacio antes de los idiomas

    public Escritura(Assets a, File salida) throws FileNotFoundException {
        assets = a;
        this.salida = salida;
        this.temp = new File(salida.getParentFile(), salida.getName() + ".temp");
        printer = new Printer(new File(a.getRuta()), temp);
    }

    public boolean escribir() throws IOException {
        //Escribir la cabecera
        if (assets.getVersion().startsWith("4.")) {
            escribirCabeceraV4();
        } else {
            escribirCabeceraV5();
        }
        //Si el archivo contiene localizacion
        if (assets.getLocalizacion() != null) {
            Definicion def = assets.getDefiniciones()[assets.getLocalizacion().getIndex() - 1];
            //Calculamos la posicion del fichero
            int espacio = def.getPosicion() + assets.getInicioFicheros();
            //Copiamos la entrada tal cual hasta ese punto
            printer.copiar(espacio - printer.getPosicionOut());
            escribirLocalizacion(def);
        }
        if (assets.getFicheros() != null) {
            //Recorremos los ficheros
            for (Fichero f : assets.getFicheros()) {
                Definicion def = assets.getDefiniciones()[f.getPosicion() - 1];
                //Calculamos la posicion del fichero
                int espacio = def.getPosicion() + assets.getInicioFicheros();
                //Copiamos la entrada tal cual hasta ese punto
                printer.copiar(espacio - printer.getPosicionOut());
                escribirFichero(f);
            }
        }
        //Escribimos el resto del Assets
        int resto = assets.getTamAssets() - printer.getPosicionOut();
        printer.copiar(resto);
        //Cerramos los archivos
        printer.close();
        //Ponemos al fichero salida su nombre final
        boolean renombrado = true;
        if (salida.exists()) {
            renombrado &= salida.delete();
        }
        renombrado &= temp.renameTo(salida);
        return renombrado;
    }

    private void escribirCabeceraV5() throws IOException {
        //Escribimos el tamaño cabecera
        printer.printBigEndian(assets.getTamCabecera());
        //Escribimos el tamaño del Assets
        printer.printBigEndian(assets.getTamAssets());
        //Saltamos hasta los tipos
        printer.copiar(24);
        //copiamos los tipos tal cual
        printer.copiar(assets.getEspecificaciondeTipos());
        //copiamos el numero de fichero
        printer.copiar(7);
        //Imprimimos los punteros
        for (Definicion def : assets.getDefiniciones()) {
            printer.print(def.getIndex());
            printer.copiar(4);//campo vacio
            printer.print(def.getPosicion());
            printer.print(def.getTam());
            printer.print(def.getTipo());
            printer.print(def.getUc());
            printer.copiar(4);//campo vacio
        }
        localizacion = true;
        cabFichero = 28;
    }

    private void escribirCabeceraV4() throws IOException {
        //Escribimos el tamaño cabecera
        printer.printBigEndian(assets.getTamCabecera());
        //Escribimos el tamaño del Assets
        printer.printBigEndian(assets.getTamAssets());
        //Resto hasta los punteros
        printer.copiar(36);
        //Imprimimos los punteros
        for (Definicion def : assets.getDefiniciones()) {
            printer.print(def.getIndex());
            printer.print(def.getPosicion());
            printer.print(def.getTam());
            printer.print(def.getTipo());
            printer.print(def.getUc());
        }
        localizacion = false;
        cabFichero = 20;
    }

    private void escribirFichero(Fichero f) throws IOException {
        printer.copiar(cabFichero);//saltar cabecera fichero
        printer.print(Conversion.tamUTF8(f.getNombre()));
        printer.printM4(f.getNombre());
        printer.copiar(4);//saltar separador
        int i=0;
        for (Bloque b : f.getBloque()) {
            escribirBloque(b);
            i++;
        }
    }

    private void escribirBloque(Bloque b) throws IOException {
        //saltamos la basura entes del texto
        for (int basura : b.getBasura()) {
            printer.copiaM4(basura);
        }
        printer.setLeerEscritura(false);//Desincronizamos el input
        /*LECTURA*/
        printer.skip(b.getTamOrg());
        /*ESCRITURA*/
        printer.print(b.getNumeroIdiomas());
        //Imprimimos los idiomas
        for (Idioma idioma : b.getIdiomas().values()) {
            escribirIdioma(b, idioma);
        }
        printer.setLeerEscritura(true);//Sincronizamos el input
    }

    private void escribirIdioma(Bloque b, Idioma idioma) throws IOException {
        //Escribir nombre del idioma
        printer.print(idioma.getIdioma().length());
        printer.printM4(idioma.getIdioma());
        //Escribimos el tamaño del idioma
        printer.print(idioma.getTamIdioma());
        //Cabecera bloque
        printer.print(b.getId());
        //Escribimos las lineas
        for (Linea l : idioma.getLineas()) {
            printer.print((byte) 0x12);
            printer.printCompressInt(l.getTamTotal());
            printer.print(l.getId());
            printer.print((byte) 0x12);
            printer.printCompressInt(l.getTamPreLinea());
            printer.print((byte) 0xA);
            printer.printCompressInt(l.getTamLinea());
            printer.print(l.getTexto());
            if (l.isFinLinea()) {
                printer.print((byte) 0x10);
                printer.print((byte) 0x02);
            }
        }
        //Redondeamos el tamaño del bloque a multiplo de 4
        int redondeo = (4 - (idioma.getTamIdioma() % 4)) % 4;
        for (int i = 0; i < redondeo; i++) {
            printer.print((byte) 0);
        }
        //Si existe recuros asociados al bloque los escribimos
        if (idioma.getRecursos() != null) {
            printer.print(idioma.getRecursos().length);
            printer.printM4(idioma.getRecursos());
        }
    }

    private void escribirLocalizacion(Definicion def) throws IOException {
        Localizacion l = assets.getLocalizacion();
        printer.copiar(cabFichero); //saltar cabecera fichero
        printer.setLeerEscritura(false);
        /*AVANZAR LECTURA*/
        printer.skip(l.getTamOrg());
        /*ESCRITURA*/
        printer.print(l.getNombre().length());
        printer.printM4(l.getNombre());
        if (assets.getVersion().compareTo("5.3") > 0) {
            escribirLocalizacionNueva(def);
        } else {
            escribirLocalizacionClasica(def);
        }
        printer.setLeerEscritura(true);
    }

    private void escribirLocalizacionClasica(Definicion def) throws IOException {
        Localizacion l = assets.getLocalizacion();
        //hay espacios despues del nombre
        if (localizacion) {
            printer.print(1);
        }
        //Escribimos las voces
        printer.print(l.getVoces().length);
        for (String s : l.getVoces()) {
            printer.print(s.length());
            printer.printM4(s);
        }
        //Escribimos los textos
        printer.print(l.getSubtitulos().length);
        for (String s : l.getSubtitulos()) {
            printer.print(s.length());
            printer.printM4(s);
        }
        //Escribimos la version 
        printer.print(l.getVersion().length());
        printer.printM4(l.getVersion());
        printer.print(0);//espacio final
        printer.print(0);
        printer.print(0);
    }

    private void escribirLocalizacionNueva(Definicion def) throws IOException {
        Localizacion l = assets.getLocalizacion();
        //Escribimos la plataforma
        printer.print(l.getPlataforma().length());
        printer.printM4(l.getPlataforma());
        //hay espacios despues del nombre
        if (localizacion) {
            printer.print(1);
        }
        //Escribimos la version 
        printer.print(l.getVersion().length());
        printer.printM4(l.getVersion());
        //Escribimos las voces
        printer.print(l.getVoces().length);
        for (String s : l.getVoces()) {
            printer.print(s.length());
            printer.printM4(s);
        }
        //Escribimos los textos
        printer.print(l.getSubtitulos().length);
        for (String s : l.getSubtitulos()) {
            printer.print(s.length());
            printer.printM4(s);
        }
        printer.print(0);//espacio final
        printer.print(0);
    }

    @Override
    public void close() throws IOException {
        printer.close();
    }

}
