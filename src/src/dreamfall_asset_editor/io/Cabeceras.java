package dreamfall_asset_editor.io;

import dreamfall_asset_editor.datos.assets.Assets;
import dreamfall_asset_editor.datos.assets.Definicion;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase para gestionar las cabeceras de ficheros de forma dinamica
 *
 * @author César Pomar
 */
public final class Cabeceras {

    private static final String nombre = "cabeceras"; //Nombre del fichero que almacena los datos
    public static final String TEXTO = "T";  //Prefijo para las cabeceras de texto
    public static final String IDIOMA = "I"; //Prefijo para las cabeceras de idioma
    private Map<String, byte[]> cabeceras;   //Cabeceras conocidas

    public Cabeceras() {
        File file = new File(nombre);
        if (!file.exists()) {
            defecto();
        } else {
            abrir();
        }
    }

    /*
    * Abre el fichero de cabeceras
     */
    private void abrir() {
        try (FileInputStream fis = new FileInputStream(nombre);
                BufferedInputStream bis = new BufferedInputStream(fis);
                ObjectInputStream input = new ObjectInputStream(bis);) {
            cabeceras = (Map<String, byte[]>) input.readObject();
        } catch (Exception ex) {
            defecto();
        }
    }

    /*
    * Actualiza el fichero de cabeceras volviendo a escribir
     */
    private void guardar() {
        try (FileOutputStream fis = new FileOutputStream(nombre);
                BufferedOutputStream fos = new BufferedOutputStream(fis);
                ObjectOutputStream output = new ObjectOutputStream(fos);) {
            output.writeObject(cabeceras);
        } catch (Exception ex) {
        }
    }

    /*
    * Cabeceras definidas por defecto
     */
    private void defecto() {
        cabeceras = new HashMap<>(10);
        add(TEXTO, "4.6.6p2", new byte[]{(byte) 0x01, (byte) 0xF5, (byte) 0x02, (byte) 0x01});
        add(IDIOMA, "4.6.6p2", new byte[]{(byte) 0x4, (byte) 0xB1, (byte) 0x19, (byte) 0x1});
        add(TEXTO, "5.2.4f1", new byte[]{(byte) 0x02, (byte) 0x2B, (byte) 0x01, (byte) 0x01});
        add(IDIOMA, "5.2.4f1", new byte[]{(byte) 0x4, (byte) 0xFA, (byte) 0x2, (byte) 0x1});
        add(TEXTO, "5.3.5p2", new byte[]{(byte) 0x02, (byte) 0x24, (byte) 0x01, (byte) 0x01});
        add(IDIOMA, "5.3.5p2", new byte[]{(byte) 0x4, (byte) 0xDC, (byte) 0x1, (byte) 0x1});
    }

    private void add(String tipo, String version, byte[] cabecera) {
        cabeceras.put(tipo + version.trim(), cabecera);
    }

    public byte[] get(String tipo, String version) {
        return cabeceras.get(tipo + version.trim());
    }


    /*
     * Busca la cabecera a base de probar
     */
    public boolean buscar(Analizador ana, Assets asset, RandomAccessFile raf) {
        boolean flag = false;
        byte[] cabecera = new byte[]{0, 0, 0, 0}; //Cabecera por defecto
        byte[] cabFichero = new byte[20]; //Cabecera recien leida
        int texto = 0; //Numero de caracteres texturales
        String idioma = "BuildInfo"; //Palabra en bloque idioma
        String global = "GlobalDialogue";//Palabra en bloque texto
        int idp = 0;//Numero de letras de la cadena idioma encontradas
        int glp = 0;//Numero de letras de la cadena global encontradas
        //Probamos los bloques uno a uno
        for (Definicion def : asset.getDefiniciones()) {
            try {
                //inicio del bloque
                raf.seek(asset.getInicioFicheros() + def.getPosicion());
                //Saltamos el espacio igual que hace el analizador
                raf.skipBytes(ana.espacio);
                //leemos la cabecera
                raf.read(cabFichero);
                //Leemos todo el bloque
                byte[] buffer = new byte[def.getTam()];
                raf.read(buffer);
                //Para todos los caracteres del bloque
                for (int i = 0; i < buffer.length; i++) {
                    byte e = buffer[i];
                    //Filtramos por caracteres textuales
                    if (('a' <= e && 'z' >= e) || ('A' <= e && 'Z' >= e) || ' ' == e || '\'' == e || ',' == e || '.' == e) {
                        texto++;
                        //Filtramos por palabra
                        if (idioma.charAt(idp) == e) {
                            idp++;
                        }
                        if (global.charAt(glp) == e) {
                            glp++;
                        }
                    } else {
                        texto = 0;
                        idp = 0;
                        glp = 0;
                    }
                    if (asset.getNombre().startsWith("resources")) {
                        if (idp == idioma.length()) {
                            //Forzamos el analisis del bloque
                            ana.buscarContenido(new Definicion[]{def}, cabecera, reducir(cabFichero));
                            //Si no salto una escepcion es que el bloque es correcto, guardamos la cabecera y terminamos
                            add(IDIOMA, asset.getVersion(), reducir(cabFichero));
                            guardar();
                            flag = true;
                        } else if (glp == global.length()) {
                            //Forzamos el analisis del bloque
                            ana.buscarContenido(new Definicion[]{def}, reducir(cabFichero), cabecera);
                            //Si no salto una escepcion es que el bloque es correcto, guardamos la cabecera y terminamos
                            add(TEXTO, asset.getVersion(), reducir(cabFichero));
                            guardar();
                            flag = true;
                        }
                    } else if (texto == 100) {
                        //Forzamos el analisis del bloque
                        ana.buscarContenido(new Definicion[]{def}, reducir(cabFichero), cabecera);
                        //Si no salto una escepcion es que el bloque es correcto, guardamos la cabecera y terminamos
                        add(TEXTO, asset.getVersion(), reducir(cabFichero));
                        guardar();
                        return true;
                    }
                }
            } catch (Exception ex) {
            }
        }
        return flag;
    }

    /*
    * Solo cogemos las partes de la cabecera que no son nulas
     */
    private byte[] reducir(byte[] cabFichero) {
        return new byte[]{cabFichero[17], cabFichero[16], cabFichero[12], cabFichero[8]};
    }

}
