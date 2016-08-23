package dreamfall_asset_editor.io;

import dreamfall_asset_editor.datos.textos.Textos;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.jdom2.Comment;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * Clase para almacenar y leer los textos en XML y poder editarlos de forma comoda y
 * facil.
 *
 * @author César Pomar
 */
public final class XML {

    public static void escribir(File path, Textos t) throws FileNotFoundException, IOException {
        //Preparamos el documento xml
        Document doc = new Document();
        Element raiz = new Element("Dreamfall");
        doc.addContent(new Comment("Documento creado automáticamente por "
                + "Dreamfall Assets Editor."));
        doc.setRootElement(raiz);
        raiz.addContent(new Element("identificador").setText(Textos.toString(t.getId())));
        raiz.addContent(new Element("assets").setText(t.getAssets()));
        raiz.addContent(new Element("fichero").setText(t.getFichero()));
        raiz.addContent(new Element("idioma").setText(t.getIdioma()));
        Element texto = new Element("texto");
        raiz.addContent(texto);
        texto.setAttribute("lineas", t.getLineas().length + "");
        for (int i = 1; i <= t.getLineas().length; i++) {
            Element linea = new Element("linea");
            Element ref = new Element("r" + i);
            linea.setAttribute("id", "" + i);
            linea.setText(t.getLineas()[i - 1]);
            texto.addContent(linea);
        }
        try ( //Grabamos el documento en un fichero
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path))) {
            XMLOutputter salida = new XMLOutputter();
            salida.setFormat(Format.getPrettyFormat());
            salida.output(doc, bos);
        }
    }

    public static Textos leer(File path) throws FileNotFoundException, JDOMException, IOException, NumberFormatException, ArrayIndexOutOfBoundsException {
        Textos t = new Textos();
        //Definimos la entrada
        SAXBuilder entrada = new SAXBuilder();
        String[] lineas;
        //Obtenemos el documento
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path))) {
            //Obtenemos el documento
            Document doc = (Document) entrada.build(bis);
            Element raiz = doc.getRootElement();
            t.setId(Textos.toByte(raiz.getChildText("identificador")));
            t.setAssets(raiz.getChildText("assets"));
            t.setFichero(raiz.getChildText("fichero"));
            t.setIdioma(raiz.getChildText("idioma"));
            Element texto = raiz.getChild("texto");
            lineas = new String[Integer.parseInt(texto.getAttributeValue("lineas"))];
            for (Element e : texto.getChildren("linea")) {
                Integer id = Integer.parseInt(e.getAttributeValue("id"));
                lineas[id - 1] = e.getText();
            }
        }
        t.setLineas(lineas);
        return t;
    }

    public static void actualizarXmlRef(File path) throws FileNotFoundException, JDOMException, IOException {
        File ref = new File(path.getAbsolutePath() + ".ref");
        byte[] idbloque = new byte[20];
        //leemos el id;
        try (FileInputStream in = new FileInputStream(ref)) {
            in.read(idbloque, 3, 17);
            idbloque[0] = 0x0a;
            idbloque[1] = 0x12;
            idbloque[2] = 0x09;
        }
        //actualizarmos el xml
        //Definimos la entrada
        SAXBuilder entrada = new SAXBuilder();
        Document doc;
        //documento
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path))) {
            //documento
            doc = (Document) entrada.build(bis);
            Element raiz = doc.getRootElement();
            doc.removeContent(0);
            doc.addContent(0, new Comment("Documento creado automáticamente por "
                    + "Dreamfall Assets Editor."));
            raiz.removeChild("bloque");//borramos la etiqueta bloque
            raiz.addContent(0, new Element("identificador").setText(Textos.toString(idbloque)));
            //Eliminar lineas
            Element texto = raiz.getChild("texto");
            texto.setAttribute("lineas", ref.length() / 19 - 1 + "");
            for (Element e : texto.getChildren("linea")) {
                e.removeAttribute("tam");
            }
        }
        //Grabamos el documento en un fichero
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path))) {
            XMLOutputter salida = new XMLOutputter();
            salida.setFormat(Format.getPrettyFormat());
            salida.output(doc, bos);
        }

    }

}
