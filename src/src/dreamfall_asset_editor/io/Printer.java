package dreamfall_asset_editor.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import static dreamfall_asset_editor.io.Conversion.*;

/**
 * Clase para gestionar la escritura de datos
 *
 * @author César Pomar
 */
public final class Printer implements Closeable {

    private File in;                    //Entrada
    private File out;                   //Salida
    private BufferedOutputStream bos;   //Buffer salida
    private BufferedInputStream bis;    //Buffer entrada   
    private byte[] buffer;              //buffer de copia de entrada a salida 
    private int posicionIn;             //Posicion de la entrada
    private int posicionOut;            //Posicion de la salida
    private boolean leerEscritura;      //Si es true al escribir avanza la lectura

    public Printer(File in, File out) throws FileNotFoundException {
        this.in = in;
        this.out = out;
        bis = new BufferedInputStream(new FileInputStream(in));
        bos = new BufferedOutputStream(new FileOutputStream(out));
        buffer = new byte[8192];
        leerEscritura = true;
    }

    public void print(int i) throws IOException {
        bos.write(toBytesInt(i));
        avanzar(4);
    }

    public void printBigEndian(int i) throws IOException {
        bos.write(toBytesBigEndianInt(i));
        avanzar(4);
    }

    public void print(byte b) throws IOException {
        bos.write(b);
        avanzar(1);
    }

    public void print(byte[] b) throws IOException {
        bos.write(b);
        avanzar(b.length);
    }

    public void printM4(byte[] b) throws IOException {
        bos.write(b);
        int redondeo = (4 - (b.length % 4)) % 4;
        for (int i = 0; i < redondeo; i++) {
            bos.write(0);
        }
        avanzar(b.length + redondeo);
    }

    public void print(String s) throws IOException {
        byte[] array = s.getBytes(codificacion);
        print(array);
    }

    public void printM4(String s) throws IOException {
        byte[] array = s.getBytes(codificacion);
        printM4(array);
    }

    /*
     * Imprime un int comprimido del formato Assets
     */
    public void printCompressInt(int n) throws IOException {
        int p1 = n % 0x80;
        int p2 = n / 0x80;
        if (p2 == 0) {
            print((byte) p1);
        } else {
            print((byte) (p1 + 0x80));
            p2 = p2 % 0x80;
            int p3 = n / 0x4000;
            if (p3 == 0) {
                print((byte) p2);
            } else {
                print((byte) (p2 + 0x80));
                p3 = p3 % 0x80;
                int p4 = n / 0x200000;
                if (p4 == 0) {
                    print((byte) (p3));
                } else {
                    print((byte) (p3 + 0x80));
                }
            }
        }
    }

    /*
     *Copia la entrada a la salida
     */
    public void copiar(int n) throws IOException {
        int copias = (n / buffer.length);
        for (int i = 0; i < copias; i++) {
            bis.read(buffer);
            bos.write(buffer);
        }
        bis.read(buffer, 0, n % buffer.length);
        bos.write(buffer, 0, n % buffer.length);
        posicionIn += n;
        posicionOut += n;
    }

    public void copiaM4(int n) throws IOException {
        int redondeo = (4 - (n % 4)) % 4;
        copiar(n + redondeo);
    }

    /*
     *Salta n bytes de la entrada
     */
    public void skip(int n) throws IOException {
        int skiped = 0;
        int salto;
        do {
            salto = (int) bis.skip(n - skiped);
            skiped += salto;
        } while (skiped != n && salto != 0);
        posicionIn += skiped;
    }

    public void skipM4(int n) throws IOException {
        int redondeo = (4 - (n % 4)) % 4;
        skip(n + redondeo);
    }

    /*
     *Retorno si la leerEscritura esta activada
     */
    public boolean isLeerEscritura() {
        return leerEscritura;
    }

    /*
     *Cambia el estado de la leerEscritura, con false al escribir no avanza la leerEscritura
     */
    public void setLeerEscritura(boolean lectura) {
        this.leerEscritura = lectura;
    }

    public int getPosicionIn() {
        return posicionIn;
    }

    public int getPosicionOut() {
        return posicionOut;
    }

    @Override
    public void close() throws IOException {
        bos.close();
        bis.close();
    }

    private void avanzar(int n) throws IOException {
        posicionOut += n;
        if (leerEscritura) {
            skip(n);
        }
    }

}
