package dreamfall_asset_editor.gui;

import dreamfall_asset_editor.datos.assets.Assets;

/**
 * Representacion abstracta de una interfaz de usuario
 *
 * @author César Pomar
 */
public interface Igui {

    public Assets[] getAssets();

    public void nuevoAssets(Assets a);

    public void mostrar(Object message, String title, int messageType);

}
