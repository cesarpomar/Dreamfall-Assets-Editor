package dreamfall_asset_editor.datos.interfaz;

import javax.swing.JPopupMenu;

/**
 * Clase que representa un elemento almacenado en el arbol de la Interfaz
 * grafica
 *
 * @author César Pomar
 * @param <T> Elemento almacenado
 */
public final class Nodo<T> {

    private T contenido;
    private String nombre;
    private JPopupMenu menu;

    public Nodo(String nombre, T contenido, JPopupMenu menu) {
        this.contenido = contenido;
        this.nombre = nombre;
        this.menu = menu;
    }

    public Nodo(String nombre) {
        this.nombre = nombre;
    }

    public T getContenido() {
        return contenido;
    }

    public void setContenido(T contenido) {
        this.contenido = contenido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public JPopupMenu getMenu() {
        return menu;
    }

    public void setMenu(JPopupMenu menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {
        return nombre;
    }

}
