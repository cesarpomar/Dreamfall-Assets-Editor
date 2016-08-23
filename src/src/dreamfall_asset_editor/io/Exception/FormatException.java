package dreamfall_asset_editor.io.Exception;

/**
 * Clase que representa una excepcion de formato al leer la cabcera del assets
 *
 * @author César Pomar
 */
public final class FormatException extends Exception {

    public FormatException() {
    }

    public FormatException(String msg) {
        super(msg);
    }
}
