package Datos.Textos;

public class Textos {

    private String assets;
    private String fichero;
    private byte[] id;
    private String idioma;
    private String[] lineas;

    public Textos() {
    }

    public Textos(Idioma idioma) {
        assets = idioma.getBloque().getFichero().getAssets().getNombre();
        fichero = idioma.getBloque().getFichero().getNombre();
        id = idioma.getBloque().getId();
        lineas = new String[idioma.getLineas().length];
        this.idioma = idioma.getIdioma();
        int i = 0;
        for (Linea l : idioma.getLineas()) {
            lineas[i] = l.getTexto();
            i++;
        }
    }

    public String getAssets() {
        return assets;
    }

    public void setAssets(String assets) {
        this.assets = assets;
    }

    public String getFichero() {
        return fichero;
    }

    public void setFichero(String fichero) {
        this.fichero = fichero;
    }

    public String[] getLineas() {
        return lineas;
    }

    public void setLineas(String[] lineas) {
        this.lineas = lineas;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public byte[] getId() {
        return id;
    }

    public void setId(byte[] id) {
        this.id = id;
    }

    public static byte[] toByte(String id) {
        if (id == null) {
            return null;
        }
        byte[] bytes = new byte[id.length() / 2];
        for (int i = 0; i < id.length(); i = i + 2) {
            bytes[i / 2] = (byte) Integer.parseUnsignedInt("" + id.charAt(i) + id.charAt(i + 1), 16);
        }
        return bytes;
    }

    public static String toString(byte[] id) {
        if (id == null) {
            return null;
        }
        StringBuilder cadena = new StringBuilder(id.length * 3);
        for (byte b : id) {
            int digito = ((int) b) & 0xFF;
            if (digito < 0x10) {
                cadena.append("0");
            }
            cadena.append(Integer.toHexString(digito));
        }
        return cadena.toString();
    }
}
