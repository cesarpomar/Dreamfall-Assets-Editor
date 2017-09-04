package dreamfall_asset_editor.io;

import dreamfall_asset_editor.datos.assets.Assets;
import dreamfall_asset_editor.datos.textos.Bloque;
import dreamfall_asset_editor.datos.assets.Definicion;
import dreamfall_asset_editor.datos.idioma.Localizacion;
import dreamfall_asset_editor.datos.textos.Fichero;
import dreamfall_asset_editor.datos.textos.Idioma;
import dreamfall_asset_editor.datos.textos.Linea;
import dreamfall_asset_editor.io.Exception.FormatException;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Clase que analiza el Assets y almacena el cotnenido editable
 *
 * @author César Pomar
 */
public final class Analizador implements Closeable {

    private static Cabeceras cabs = new Cabeceras();//Gestor de cabeceras dinamicas

    private File fichero;               //Assets
    private RandomAccessFile raf;       //Fichero de entrada
    private Assets assets;              //Clase para guardar los datos
    ///Datos para el analisis
    private int tamCabecera;            //tamaño de las cabeceras de fichero
    private int tamIds;                 //tamaño de los ids antes de las lineas
    protected int espacio;                //espacio antes de los idiomas
    ///Depuracion
    private int nBloque;
    private int nIdioma;

    public Analizador(File file) throws FileNotFoundException {
        raf = new RandomAccessFile(file, "r");
        fichero = file;
    }

    public Assets leerAssets() throws Exception {
        leerDefinicionAssets();
        byte[] cabTexto = cabs.get(Cabeceras.TEXTO, assets.getVersion());
        byte[] cabIdioma = cabs.get(Cabeceras.IDIOMA, assets.getVersion());
        if (cabTexto == null || cabIdioma == null) {
            cabs.buscar(this, assets, raf);
            cabTexto = cabs.get(Cabeceras.TEXTO, assets.getVersion());
            cabIdioma = cabs.get(Cabeceras.IDIOMA, assets.getVersion());
        }
        buscarContenido(assets.getDefiniciones(), cabTexto, cabIdioma);
        return assets;
    }


    /*
     *Lee la cabecera de un archivo assets version unity 4
     */
    private void leerCabeceraV4Assets() throws IOException {
        Buffer buffer = new Buffer(assets.getTamCabecera() - 8);//quitamos 8 de la version ya leida
        raf.read(buffer.getBuffer());
        buffer.skip(12);//Sata 16 bytes inservibles
        assets.setNumeroFicheros(buffer.readInt());
        /*Leemos las definiciones de los archivos de la cabecera*/
        Definicion[] definiciones = new Definicion[assets.getNumeroFicheros()];
        for (int i = 0; i < definiciones.length; i++) {
            definiciones[i] = new Definicion(buffer.readInt(), buffer.readInt(),
                    buffer.readInt(), buffer.readInt(), buffer.readInt());
        }
        assets.setDefiniciones(definiciones);
        /*Definicones de la version*/
        tamCabecera = 20;
        tamIds = 48;
    }

    /*
     *Lee la cabecera de un archivo assets version unity 5
     */
    private void leerCabeceraV5Assets() throws IOException {
        Buffer buffer = new Buffer(assets.getTamCabecera() - 8);//quitamos 8 de la version ya leida
        raf.read(buffer.getBuffer());
        buffer.skip(4);//Saltamos el separador
        buffer.skip(1);//Saltamos el byte en desuso
        int nTipos = buffer.readInt();//Numero de tipos
        int sumaTipos = 5;//guardamos lo que ocupan para copiarlos directamente en la escritura
        for (int i = 0; i < nTipos; i++) {
            int tipo = buffer.readInt();
            if (tipo < 0) {//El numero indica la longitud de la especificacion
                buffer.skip(32);
                sumaTipos += 36;
            } else {
                buffer.skip(16);
                sumaTipos += 20;
            }
        }
        assets.setEspecificaciondeTipos(sumaTipos);
        assets.setNumeroFicheros(buffer.readInt());
        /*Saltamos los bytes de mayor precision, por ahora es dicifil que haya mas de 2^32 archivos*/
        buffer.skip(3);
        /*Leemos las definiciones de los archivos de la cabecera*/
        Definicion[] definiciones = new Definicion[assets.getNumeroFicheros()];
        for (int i = 0; i < definiciones.length; i++) {
            int id = buffer.readInt();
            buffer.skip(4);//saltar campo en blanco
            definiciones[i] = new Definicion(id, buffer.readInt(),
                    buffer.readInt(), buffer.readInt(), buffer.readInt());
            buffer.skip(4);//saltar campo en blanco
        }
        assets.setDefiniciones(definiciones);
        /*Definicones de la version*/
        tamCabecera = 28;
        tamIds = 52;
        espacio = 4;
    }

    /*
     *Lee la definicion del archivo assets
     */
    private void leerDefinicionAssets() throws IOException, FormatException {
        assets = new Assets(fichero.getName(), fichero.getAbsolutePath());
        Buffer buffer = new Buffer(28); //Facilitar conversion de bytes  
        raf.read(buffer.getBuffer());
        assets.setTamCabecera(buffer.readBigEndianInt());
        assets.setTamAssets(buffer.readBigEndianInt());
        buffer.skip(4);//Sata 4 byes inservibles.
        assets.setInicioFicheros(buffer.readBigEndianInt());
        buffer.skip(4);//Sata 4 byes inservibles.
        assets.setVersion(buffer.readString(8));
        //Chequea la version a la que pertenece el assets
        if (assets.getVersion().startsWith("4.")) {
            leerCabeceraV4Assets();
        } else if (assets.getVersion().startsWith("5.")) {
            leerCabeceraV5Assets();
        } else {
            throw new FormatException();
        }
    }


    /*
     *Busca un fichero dentro del assets
     */
    protected void buscarContenido(Definicion[] definiciones, byte[] cabTexto, byte[] cabIdioma) throws IOException {
        byte[] cabFichero = new byte[20];  //Cabecera de un fichero
        List<Fichero> lista = new ArrayList<>(10);
        for (Definicion def : definiciones) {
            /*Moverse a inico bloque*/
            raf.seek(assets.getInicioFicheros() + def.getPosicion());
            raf.skipBytes(espacio);//Los primeros bytes son inutiles
            raf.read(cabFichero);
            raf.skipBytes(espacio);//Los ultimos igual
            if (cabTexto != null
                    && cabFichero[17] == cabTexto[0]//TEXTO
                    && cabFichero[16] == cabTexto[1]
                    && cabFichero[12] == cabTexto[2]
                    && cabFichero[8] == cabTexto[3]) {
                lista.add(ficheroTexto(def));
            } else if (cabIdioma != null
                    && cabFichero[17] == cabIdioma[0]//IDIOMA
                    && cabFichero[16] == cabIdioma[1]
                    && cabFichero[12] == cabIdioma[2]
                    && cabFichero[8] == cabIdioma[3]) {
                ficheroIdioma(def);
            }
        }
        if (!lista.isEmpty()) {
            Fichero[] ficheros = new Fichero[lista.size()];
            lista.toArray(ficheros);
            assets.setFicheros(ficheros);
        }
    }

    /*
     * Analiza en texto dentro de un fichero
     */
    protected Fichero ficheroTexto(Definicion prop) throws IOException {
        Buffer buffer = new Buffer(prop.getTam() - tamCabecera);//Restamos la cabecera
        /*Almacenar todo el fichero*/
        raf.read(buffer.getBuffer());
        //Leer nomber fichero
        Fichero f = new Fichero(buffer.readStringM4(buffer.readInt()));
        f.setPosicion(prop.getIndex());
        buffer.skip(4);//Saltar separador 9000 8000 7000
        List<Bloque> lista = new ArrayList<>(30);
        //Leeo hasta terminar el buffer
        nBloque = 0;
        while (buffer.hasNext(1)) {
            lista.add(bloqueTexto(buffer, f));
            nBloque++;
        }
        //Guardo los bloques leidos
        Bloque[] bloques = new Bloque[lista.size()];
        lista.toArray(bloques);
        f.setBloque(bloques);
        f.setAssets(assets);
        return f;
    }

    private Bloque bloqueTexto(Buffer buffer, Fichero f) {
        List<Integer> basura = new ArrayList<>(10);
        int saltar = buffer.readInt();//cadena inicio
        basura.add(4);//saltar entero
        basura.add(saltar);//saltar cadena
        buffer.skipM4(saltar);//salta la cadena
        saltar = buffer.readInt();
        basura.add(4);
        basura.add(saltar);
        buffer.skipM4(saltar);//Saltar nombre bloque
        saltar = buffer.readInt();
        basura.add(4);
        basura.add(saltar);
        buffer.skipM4(saltar);//saltar bloque basura
        int nIds = buffer.readInt();//Saltos identificadores si existen
        basura.add(4);
        for (int i = 0; i < nIds; i++) {
            buffer.skip(tamIds);
            basura.add(tamIds);
        }
        //Inicio de los idiomas
        int inicioIdiomas = buffer.getPosicion();
        Bloque bloque = new Bloque();//Leemos el numero idiomas del bloque
        bloque.setNumeroIdiomas(buffer.readInt());
        bloque.setIdiomas(new HashMap<>(bloque.getNumeroIdiomas() * 2));
        nIdioma = 0;
        for (int i = 0; i < bloque.getNumeroIdiomas(); i++) {//leemos los idiomas
            Idioma idioma = IdiomaTexto(buffer, bloque);
            bloque.getIdiomas().put(idioma.getIdioma(), idioma);
            nIdioma++;
        }
        bloque.setTamOrg(buffer.getPosicion() - inicioIdiomas);
        bloque.setFichero(f);
        bloque.setBasura(basura);
        return bloque;
    }

    private Idioma IdiomaTexto(Buffer buffer, Bloque b) {
        //Leemos el nombre del idioma
        Idioma idioma = new Idioma(buffer.readStringM4(buffer.readInt()));
        //Guardamos el tamaño del idioma
        idioma.setTamIdioma(buffer.readInt());
        int inicio = buffer.getPosicion();//inico del idioma
        //Le asignamos el id al bloque
        b.setId(buffer.readBytes(20));
        List<Linea> lista = new ArrayList<>(100);
        //Repetimos haste leer el equivalente al tamaño del idioma
        int fin =inicio + idioma.getTamIdioma();
        while (buffer.getPosicion() != fin) {
            lista.add(leerlinea(buffer,fin));
        }
        buffer.alinearM4();//redondea el tamaño del bloque
        //Si la version es superior a la 5.3
        if (assets.getVersion().compareTo("5.3") > 0) {
            idioma.setRecursos(buffer.readBytesM4(buffer.readInt()));
        }
        //Guardamos las lineas en el idioma
        Linea[] lineas = new Linea[lista.size()];
        lista.toArray(lineas);
        idioma.setLineas(lineas);
        idioma.setBloque(b);
        return idioma;
    }

    private Linea leerlinea(Buffer buffer, int fin) {
        buffer.skip(1);//saltar12
        Linea linea = new Linea();
        linea.setTamTotal(buffer.readCompressInt());
        linea.setId(buffer.readBytes(20));
        buffer.skip(1);//saltar 12
        linea.setTamPreLinea(buffer.readCompressInt());
        buffer.skip(1);//saltar 0A
        linea.setTamLinea(buffer.readCompressInt());
        linea.setTexto(buffer.readString(linea.getTamLinea()));//Leer texto
        if (buffer.hasNext(1) && buffer.get() == 0x10 && buffer.getPosicion() != fin) {//Si la liena termina con 10 02
            linea.setFinLinea(buffer.readBytes(2));
        }else{
            linea.setFinLinea(new byte[0]);
        }
        return linea;
    }

    /*
     * Analiza el idioma contenido en el fichero
     */
    private void ficheroIdioma(Definicion prop) throws IOException {
        Buffer buffer = new Buffer(prop.getTam() - tamCabecera);//Restamos la cabecera
        /*Almacenar todo el fichero*/
        raf.read(buffer.getBuffer());
        //Leer nombre fichero
        Localizacion localizacion = new Localizacion(buffer.readStringM4(buffer.readInt()));
        localizacion.setIndex(prop.getIndex());
        localizacion.setAssets(assets);
        localizacion.setTamOrg(buffer.getBuffer().length);
        if (assets.getVersion().compareTo("5.3") > 0) {
            ficheroIdiomaNuevo(prop, buffer, localizacion);
        } else {
            ficheroIdiomaClasico(prop, buffer, localizacion);
        }
        //Guardamos la localizacion
        assets.setLocalizacion(localizacion);
    }

    /*
     * Analiza el idioma contenido en el fichero en versiones anterirores a 5.3
     */
    private void ficheroIdiomaClasico(Definicion prop, Buffer buffer, Localizacion localizacion) throws IOException {
        //Saltamos un espacio si lo hay
        buffer.skip(espacio);
        String[] voces = new String[buffer.readInt()];
        //Leemos los idiomas con voces
        for (int i = 0; i < voces.length; i++) {
            voces[i] = buffer.readStringM4(buffer.readInt());
        }
        localizacion.setVoces(voces);
        String[] subtitulos = new String[buffer.readInt()];
        //Leemos los idomas con subtitulos
        for (int i = 0; i < subtitulos.length; i++) {
            subtitulos[i] = buffer.readStringM4(buffer.readInt());
        }
        localizacion.setSubtitulos(subtitulos);
        //Leemos la version del juego
        localizacion.setVersion(buffer.readString(buffer.readInt()));
    }

    /*
     * Analiza el idioma contenido en el fichero en versiones posteriroes a 5.3
     */
    private void ficheroIdiomaNuevo(Definicion prop, Buffer buffer, Localizacion localizacion) throws IOException {
        //Leemos la plataforma
        localizacion.setPlataforma(buffer.readStringM4(buffer.readInt()));
        //Saltamos un espacio si lo hay
        buffer.skip(espacio);
        //Leemos la version del juego
        localizacion.setVersion(buffer.readString(buffer.readInt()));
        buffer.alinearM4();
        String[] voces = new String[buffer.readInt()];
        //Leemos los idiomas con voces
        for (int i = 0; i < voces.length; i++) {
            voces[i] = buffer.readStringM4(buffer.readInt());
        }
        localizacion.setVoces(voces);
        String[] subtitulos = new String[buffer.readInt()];
        //Leemos los idomas con subtitulos
        for (int i = 0; i < subtitulos.length; i++) {
            subtitulos[i] = buffer.readStringM4(buffer.readInt());
        }
        localizacion.setSubtitulos(subtitulos);
    }

    @Override
    public void close() throws IOException {
        raf.close();
    }

}
