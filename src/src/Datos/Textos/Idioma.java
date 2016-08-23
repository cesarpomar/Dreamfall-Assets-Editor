package Datos.Textos;

public class Idioma {

    private Bloque bloque;      //Bloque al que pertenece el idioma;
    private String idioma;      //Idioma
    private int tamIdioma;      //Tamaño del bloque deste idioma
    private Linea[] lineas;     //Lineas dentro de texto

    public Idioma(String idioma) {
        this.idioma = idioma;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public int getTamIdioma() {
        return tamIdioma;
    }

    public void setTamIdioma(int tamIdioma) {
        this.tamIdioma = tamIdioma;
    }

    public Linea[] getLineas() {
        return lineas;
    }

    public void setLineas(Linea[] lineas) {
        this.lineas = lineas;
    }

    public Bloque getBloque() {
        return bloque;
    }

    public void setBloque(Bloque bloque) {
        this.bloque = bloque;
    }
    
    /**
     * Tamaño del idioma mas su propia cabecera. 
     * @return
     */
    public int getEspacioIdioma(){
        int redondeoNombre=(4-(idioma.length()%4))%4;
        int redondeoTam=(4-(tamIdioma%4))%4;
        //bloque tam nombre, nombre idioma, redondeo nombre idioma, bloque tam idioma, tam idioma y redondeo tam idioma
        return 4+idioma.length()+redondeoNombre+4+tamIdioma+redondeoTam;
    }

}
