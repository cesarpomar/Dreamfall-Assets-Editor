package IO;

import java.nio.BufferUnderflowException;
import static IO.Conversion.*;

public class Buffer {

    private final byte[] buffer;
    private int posicion;
    private final int limite;

    /**
     * Crea un buffer con el array de bytes
     *
     * @param b array de bytes
     */
    public Buffer(byte[] b) {
        buffer = b;
        limite = b.length;
    }

    /**
     * Crea un buffer con un buffer vacio
     *
     * @param tam tamaño del buffer
     */
    public Buffer(int tam) {
        buffer = new byte[tam];
        limite = tam;
    }

    /**
     * Obtiene el buffer de bytes
     *
     * @return buffer
     */
    public byte[] getBuffer() {
        return buffer;
    }

    /**
     * Muestra el byte de la posicion actual, sin avanzar la lectura.
     *
     * @return byte actual
     */
    public byte get() {
        return buffer[posicion];
    }

    /**
     * Comprueba si quedam al menos bytes
     *
     * @param n numero de bytes
     * @return true si quedan al menos n bytes, false en otro caso
     */
    public boolean hasNext(int n) {
        return posicion + n <= limite;
    }

    /**
     * Lee el siguiente int
     *
     * @return int
     */
    public int readInt() {
        islimit(4);
        posicion += 4;
        return toInt(buffer, posicion - 4);
    }

    /**
     * Lee el siguiente int en big endian
     *
     * @return int
     */
    public int readBigEndianInt() {
        islimit(4);
        posicion += 4;
        return toBigEndianInt(buffer, posicion - 4);
    }

    /**
     * Lee la siguiente cadena
     *
     * @param bytes numero de bytes de la cadena
     * @return cadena
     */
    public String readString(int bytes) {
        islimit(bytes);
        posicion += bytes;
        return toStringB(buffer, posicion - bytes, bytes);
    }

    /**
     * Lee la siguiente cadena con longitud multiplo de 4
     *
     * @param bytes numero de bytes de la cadena
     * @return cadena
     */
    public String readStringM4(int bytes) {
        int redondeo = (4 - (bytes % 4)) % 4;//Redondeamos la lectura
        islimit(bytes + redondeo);
        String cadena = toStringB(buffer, posicion, bytes);
        posicion += bytes + redondeo;
        return cadena;
    }

    /**
     * Lee los siguientes bytes
     *
     * @param bytes numero de bytes
     * @return array de bytes
     */
    public byte[] readBytes(int bytes) {
        islimit(bytes);
        byte[] array = new byte[bytes];
        for (int i = 0; i < bytes; i++) {
            array[i] = buffer[posicion++];
        }
        return array;
    }

    /**
     * Lee los siguientes bytes con longitud multiplo de 4
     *
     * @param bytes numero de bytes
     * @return array de bytes
     */
    public byte[] readBytesM4(int bytes) {
        int redondeo = (4 - (bytes % 4)) % 4;//Redondeamos la lectura
        islimit(bytes + redondeo);
        byte[] array = new byte[bytes];
        for (int i = 0; i < bytes; i++) {
            array[i] = buffer[posicion++];
        }
        posicion += bytes + redondeo;
        return array;
    }

    /**
     * Lee el siguiente byte
     *
     * @return siguiente byte
     */
    public byte readByte() {
        islimit(1);
        return buffer[posicion++];
    }

    /**
     * Lee el siguiente byte en forma positiva
     *
     * @return representacion entera del siguietne byte
     */
    public int readByteInt() {
        islimit(1);
        return buffer[posicion++] & 0xFF;
    }

    /**
     * Lee un int comprimido del formato Assets
     *
     * @return entero
     */
    public int readCompressInt() {
        islimit(1);
        int p1 = ((int) buffer[posicion++]) & 0xff;
        if (p1 >= 0x80) {
            islimit(2);
            int p2 = ((int) buffer[posicion++]) & 0xff;
            if (p2 > 0x80) {
                int p3 = ((int) buffer[posicion++]) & 0xff;
                if (p3 > 0x80) {
                    int p4 = ((int) buffer[posicion++]) & 0xff;
                    p1 += 0x200000 * (p4 - 1);
                }
                p1 += 0x4000 * (p3 - 1);
            }
            p1 += 0x80 * (p2 - 1);
        }
        return p1;
    }

    /**
     * Salta n bytes
     *
     * @param n salto
     */
    public void skip(int n) {
        islimit(n);
        posicion += n;
    }

    /**
     * Salta n bytes multiplo de 4
     *
     * @param n salto
     */
    public void skipM4(int n) {
        int redondeo = (4 - (n % 4)) % 4;//Redondeamos la lectura
        islimit(n + redondeo);
        posicion += n + redondeo;
    }

    /**
     * Alinea el buffer a un multiplo de 4
     */
    public void alinearM4() {
        int redondeo = (4 - (posicion % 4)) % 4;
        posicion += redondeo;
    }

    /**
     * Retorna la poscion dentro del buffer
     *
     * @return posicon
     */
    public int getPosicion() {
        return posicion;
    }

    private void islimit(int t) {
        if (posicion + t > limite) {
            throw new BufferUnderflowException();
        }
    }

}
