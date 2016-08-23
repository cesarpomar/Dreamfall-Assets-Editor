package dreamfall_asset_editor.datos.idioma;

import dreamfall_asset_editor.datos.assets.Assets;

/**
 * Clase que almacena los idomas del juego almacenados en resources.assets
 *
 * @author César Pomar
 */
public final class Localizacion {

    private Assets assets;          //Assets de la localizacion
    private int tamOrg;             //tamaño orginal el bloque para acelerar escritura
    private int index;              //index dentro del Assets
    private String nombre;          //nombre del fichero
    private String[] voces;         //voces del juego
    private String[] subtitulos;    //subtitulos del juego
    private String version;         //version del juego
    //A partir de Unity 5.3
    private String plataforma;

    public Localizacion(String nombre) {
        this.nombre = nombre;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String[] getVoces() {
        return voces;
    }

    public void setVoces(String[] voces) {
        this.voces = voces;
    }

    public String[] getSubtitulos() {
        return subtitulos;
    }

    public void setSubtitulos(String[] subtitulos) {
        this.subtitulos = subtitulos;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Assets getAssets() {
        return assets;
    }

    public void setAssets(Assets assets) {
        this.assets = assets;
    }

    public int getTamOrg() {
        return tamOrg;
    }

    public void setTamOrg(int tamOrg) {
        this.tamOrg = tamOrg;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

}
