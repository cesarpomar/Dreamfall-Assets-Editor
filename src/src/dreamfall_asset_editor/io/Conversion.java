package dreamfall_asset_editor.io;

import java.io.UnsupportedEncodingException;

/**
 * Clase de utilidades para conversion entre formatos
 *
 * @author César Pomar
 */
public final class Conversion {

    public static final String codificacion = "UTF-8";

    public static int toInt(byte[] bytes, int inicio) {
        return (bytes[inicio + 3] << 24) & 0xff000000
                | (bytes[inicio + 2] << 16) & 0x00ff0000
                | (bytes[inicio + 1] << 8) & 0x0000ff00
                | (bytes[inicio] << 0) & 0x000000ff;
    }

    public static byte[] toBytesInt(int i) {
        return new byte[]{
            (byte) i,
            (byte) (i >>> 8),
            (byte) (i >>> 16),
            (byte) (i >>> 24)};
    }

    public static int toBigEndianInt(byte[] bytes, int inicio) {
        return (bytes[inicio] << 24) & 0xff000000
                | (bytes[inicio + 1] << 16) & 0x00ff0000
                | (bytes[inicio + 2] << 8) & 0x0000ff00
                | (bytes[inicio + 3] << 0) & 0x000000ff;
    }

    public static byte[] toBytesBigEndianInt(int i) {
        return new byte[]{
            (byte) (i >>> 24),
            (byte) (i >>> 16),
            (byte) (i >>> 8),
            (byte) i};
    }

    public static String toStringB(byte[] bytes, int inicio, int tam) {
        try {
            return new String(bytes, inicio, tam, codificacion);
        } catch (UnsupportedEncodingException ex) {
        }
        return null;
    }

    public static byte[] toBytesString(String cad) {
        try {
            return cad.getBytes(codificacion);
        } catch (UnsupportedEncodingException ex) {
        }
        return null;
    }

    public static int tamUTF8(String cad) {
        try {
            return cad.getBytes(codificacion).length;
        } catch (UnsupportedEncodingException ex) {
        }
        return 0;
    }

}
