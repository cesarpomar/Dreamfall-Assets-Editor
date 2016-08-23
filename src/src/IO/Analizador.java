package IO;

import Datos.Assets.Assets;
import Datos.Textos.Bloque;
import Datos.Assets.Definicion;
import Datos.Idioma.Localizacion;
import Datos.Textos.Fichero;
import Datos.Textos.Idioma;
import Datos.Textos.Linea;
import IO.Exception.FormatException;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Analizador implements Closeable {

    private File fichero;               //Assets
    private RandomAccessFile raf;       //Fichero de entrada
    private Assets assets;              //Clase para guardar los datos
    ///Datos para el analisis
    private int tamCabecera;            //tamaño de las cabeceras de fichero
    private int tamIds;                 //tamaño de los ids antes de las lineas
    private int espIdiomas;             //espacio antes de los idiomas
    

    public Analizador(File file) throws FileNotFoundException {
        raf = new RandomAccessFile(file, "r");
        fichero = file;
    }

    public Assets leerAssets() throws Exception {
        leerDefinicionAssets();
        //Buscar el contenido segun la version
        if (assets.getVersion().startsWith("4.")) {
            buscarContenidoV4();
        } else {
            buscarContenidoV5();
        }
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
        tamCabecera=20;
        tamIds=48;
    }

    /*
     *Lee la cabecera de un archivo assets version unity 5
     */
    private void leerCabeceraV5Assets() throws IOException {
        Buffer buffer = new Buffer(assets.getTamCabecera() - 8);//quitamos 8 de la version ya leida
        raf.read(buffer.getBuffer());
        buffer.skip(4);//Saltamos el separador
        buffer.skip(1);//Saltamos el byte en desuso
        int nTipos=buffer.readInt();//Numero de tipos
        int sumaTipos=5;//guardamos lo que ocupan para copiarlos directamente en la escritura
        for (int i = 0; i < nTipos; i++) {
            int tipo = buffer.readInt();
            if (tipo < 0) {//El numero indica la longitud de la especificacion
                buffer.skip(32);
                sumaTipos+=36;
            } else {
                buffer.skip(16);
                sumaTipos+=20;
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
        tamCabecera=28;
        tamIds=52;
        espIdiomas=4;
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
    private void buscarContenidoV4() throws IOException {
        byte[] cabFichero = new byte[20];  //Cabecera de un fichero
        List<Fichero> lista = new ArrayList<>(10);
        for (Definicion def : assets.getDefiniciones()) {
            /*Moverse a inico bloque*/
            raf.seek(assets.getInicioFicheros() + def.getPosicion());
            raf.read(cabFichero);
            /*Comprobamos los campos cabecera*/
            if (cabFichero[17] == (byte) 0x01//TEXTO
                    && cabFichero[16] == (byte) 0xF5
                    && cabFichero[12] == (byte) 0x02
                    && cabFichero[8] == (byte) 0x01) {
                lista.add(ficheroTexto(def));
            } else if (cabFichero[17] == (byte) 0x4//IDIOMA
                    && cabFichero[16] == (byte) 0xB1
                    && cabFichero[12] == (byte) 0x19
                    && cabFichero[8] == (byte) 0x1) {
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
     *Busca un fichero dentro del assets
     */
    private void buscarContenidoV5() throws IOException {
        byte[] cabFichero = new byte[20];  //Cabecera de un fichero
        List<Fichero> lista = new ArrayList<>(10);
        for (Definicion def : assets.getDefiniciones()) {
            /*Moverse a inico bloque*/
            raf.seek(assets.getInicioFicheros() + def.getPosicion());
            raf.skipBytes(4);//Los primeros bytes son inutiles
            raf.read(cabFichero);
            raf.skipBytes(4);//Los ultimos igual
            if (cabFichero[17] == (byte) 0x02//TEXTO
                    && (cabFichero[16] == (byte) 0x2A || cabFichero[16] == (byte) 0x2B)
                    && cabFichero[12] == (byte) 0x01
                    && cabFichero[8] == (byte) 0x01) {
                lista.add(ficheroTexto(def));
            } else if (cabFichero[17] == (byte) 0x4//IDIOMA
                    && cabFichero[16] == (byte) 0xFA
                    && cabFichero[12] == (byte) 0x2
                    && cabFichero[8] == (byte) 0x1) {
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
    private Fichero ficheroTexto(Definicion prop) throws IOException {
        Buffer buffer = new Buffer(prop.getTam() - tamCabecera);//Restamos la cabecera
        /*Almacenar todo el fichero*/
        raf.read(buffer.getBuffer());
        //Leer nomber fichero
        Fichero f = new Fichero(buffer.readStringM4(buffer.readInt()));
        f.setPosicion(prop.getIndex());
        buffer.skip(4);//Saltar separador 9000 8000 7000
        List<Bloque> lista = new ArrayList<>(30);
        //Leeo hasta terminar el buffer
        while (buffer.hasNext(1)) {
            lista.add(bloqueTexto(buffer, f));
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
        for (int i = 0; i < bloque.getNumeroIdiomas(); i++) {//leemos los idiomas
            Idioma idioma = IdiomaTexto(buffer, bloque);
            bloque.getIdiomas().put(idioma.getIdioma(), idioma);
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
        while (buffer.getPosicion() != inicio + idioma.getTamIdioma()) {
            lista.add(leerlinea(buffer));
        }
        buffer.alinearM4();//redondea el tamaño del bloque
        //Guardamos las lineas en el idioma
        Linea[] lineas = new Linea[lista.size()];
        lista.toArray(lineas);
        idioma.setLineas(lineas);
        idioma.setBloque(b);
        return idioma;
    }

    private Linea leerlinea(Buffer buffer) {
        buffer.skip(1);//saltar12
        Linea linea = new Linea();
        linea.setTamTotal(buffer.readCompressInt());
        linea.setId(buffer.readBytes(20));
        buffer.skip(1);//saltar 12
        linea.setTamPreLinea(buffer.readCompressInt());
        buffer.skip(1);//saltar 0A
        linea.setTamLinea(buffer.readCompressInt());
        linea.setTexto(buffer.readString(linea.getTamLinea()));//Leer texto
        if (buffer.hasNext(1) && buffer.get() == 0x10) {//Si la liena termina con 10 02
            buffer.skip(2);//lo saltamos
            linea.setFinLinea(true);//y lo tenemos en cuenta
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
        //Leer nomber fichero
        Localizacion localizacion = new Localizacion(buffer.readStringM4(buffer.readInt()));
        localizacion.setIndex(prop.getIndex());
        localizacion.setAssets(assets);
        //Saltamos un espacio si lo hay
        buffer.skip(espIdiomas);
        //guardar el tamaño
        localizacion.setTamOrg(buffer.getBuffer().length);
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
        //Guardamos la localizacion
        assets.setLocalizacion(localizacion);
    }

    @Override
    public void close() throws IOException {
        raf.close();
    }

}
