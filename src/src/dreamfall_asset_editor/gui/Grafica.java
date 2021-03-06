package dreamfall_asset_editor.gui;

import dreamfall_asset_editor.controlador.Controlador;
import dreamfall_asset_editor.datos.assets.Assets;
import dreamfall_asset_editor.datos.idioma.Localizacion;
import dreamfall_asset_editor.datos.interfaz.Nodo;
import dreamfall_asset_editor.datos.textos.Bloque;
import dreamfall_asset_editor.datos.textos.Fichero;
import dreamfall_asset_editor.datos.textos.Idioma;
import java.awt.Desktop;
import java.awt.Image;
import java.io.File;
import java.net.URI;
import java.util.Enumeration;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 * Interfaz grafica de la apliación
 *
 * @author César Pomar
 */
public final class Grafica extends javax.swing.JFrame implements Igui {

    /**
     * Creates new form Gui
     */
    public Grafica() {
        initComponents();
        setLocationRelativeTo(null);
        icono = new ImageIcon(getClass().getResource("/dreamfall_asset_editor/gui/images/logo.jpg")).getImage();
        setIconImage(icono);
        controlador = new Controlador(this);
        cargarChoosets();
        //mostrarAssetsTestFile();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popAssets = new javax.swing.JPopupMenu();
        guardarComo = new javax.swing.JMenuItem();
        cerrarAssets = new javax.swing.JMenuItem();
        popFichero = new javax.swing.JPopupMenu();
        nuevoIdioma = new javax.swing.JMenuItem();
        importarIdioma = new javax.swing.JMenuItem();
        popIdioma = new javax.swing.JPopupMenu();
        exportarIdioma = new javax.swing.JMenuItem();
        borrarIdioma = new javax.swing.JMenuItem();
        popBloque = new javax.swing.JPopupMenu();
        importarBloque = new javax.swing.JMenuItem();
        exportarBloque = new javax.swing.JMenuItem();
        popLocalizacion = new javax.swing.JPopupMenu();
        anadirSubtitulo = new javax.swing.JMenuItem();
        popSubtitulo = new javax.swing.JPopupMenu();
        borrarSubtitulo = new javax.swing.JMenuItem();
        arbolScroll = new javax.swing.JScrollPane();
        arbol = new javax.swing.JTree();
        menuSuperior = new javax.swing.JMenuBar();
        archivo = new javax.swing.JMenu();
        abrirAssets = new javax.swing.JMenuItem();
        abrirCarpeta = new javax.swing.JMenuItem();
        cerrarTodo = new javax.swing.JMenuItem();
        separadorMenu = new javax.swing.JPopupMenu.Separator();
        salir = new javax.swing.JMenuItem();
        script = new javax.swing.JMenu();
        gestor_script = new javax.swing.JMenuItem();
        herramientas = new javax.swing.JMenu();
        cambiarIdiomaXml = new javax.swing.JMenuItem();
        actualizarXmlRef = new javax.swing.JMenuItem();
        about = new javax.swing.JMenu();
        clandlan = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem1 = new javax.swing.JMenuItem();

        guardarComo.setText("Guardar como");
        guardarComo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarComoActionPerformed(evt);
            }
        });
        popAssets.add(guardarComo);

        cerrarAssets.setText("Cerrar");
        cerrarAssets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cerrarAssetsActionPerformed(evt);
            }
        });
        popAssets.add(cerrarAssets);

        nuevoIdioma.setText("Nuevo Idioma");
        nuevoIdioma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoIdiomaActionPerformed(evt);
            }
        });
        popFichero.add(nuevoIdioma);

        importarIdioma.setText("Importar Idioma");
        importarIdioma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importarIdiomaActionPerformed(evt);
            }
        });
        popFichero.add(importarIdioma);

        exportarIdioma.setText("Exportar");
        exportarIdioma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportarIdiomaActionPerformed(evt);
            }
        });
        popIdioma.add(exportarIdioma);

        borrarIdioma.setText("Borrar");
        borrarIdioma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrarIdiomaActionPerformed(evt);
            }
        });
        popIdioma.add(borrarIdioma);

        importarBloque.setText("Importar");
        importarBloque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importarBloqueActionPerformed(evt);
            }
        });
        popBloque.add(importarBloque);

        exportarBloque.setText("Exportar");
        exportarBloque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportarBloqueActionPerformed(evt);
            }
        });
        popBloque.add(exportarBloque);

        anadirSubtitulo.setText("Nuevo");
        anadirSubtitulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                anadirSubtituloActionPerformed(evt);
            }
        });
        popLocalizacion.add(anadirSubtitulo);

        borrarSubtitulo.setText("Borrar");
        borrarSubtitulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrarSubtituloActionPerformed(evt);
            }
        });
        popSubtitulo.add(borrarSubtitulo);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Dreamfall Assest Editor 1.4.2");
        setIconImages(null);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        arbol.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        arbol.setComponentPopupMenu(popAssets);
        arbol.setRootVisible(false);
        arbol.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                arbolValueChanged(evt);
            }
        });
        arbolScroll.setViewportView(arbol);

        archivo.setText("Archivo");

        abrirAssets.setText("Abrir Assets");
        abrirAssets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abrirAssetsActionPerformed(evt);
            }
        });
        archivo.add(abrirAssets);

        abrirCarpeta.setText("Abrir Carpeta");
        abrirCarpeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abrirCarpetaActionPerformed(evt);
            }
        });
        archivo.add(abrirCarpeta);

        cerrarTodo.setText("Cerrar Todo");
        cerrarTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cerrarTodoActionPerformed(evt);
            }
        });
        archivo.add(cerrarTodo);
        archivo.add(separadorMenu);

        salir.setText("Salir");
        salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirActionPerformed(evt);
            }
        });
        archivo.add(salir);

        menuSuperior.add(archivo);

        script.setText("Script");

        gestor_script.setText("Abrir gestor");
        gestor_script.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gestor_scriptActionPerformed(evt);
            }
        });
        script.add(gestor_script);

        menuSuperior.add(script);

        herramientas.setText("Herramientas");

        cambiarIdiomaXml.setText("Cambiar idioma Xml");
        cambiarIdiomaXml.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cambiarIdiomaXmlActionPerformed(evt);
            }
        });
        herramientas.add(cambiarIdiomaXml);

        actualizarXmlRef.setText("Actualizar Xml con ref");
        actualizarXmlRef.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizarXmlRefActionPerformed(evt);
            }
        });
        herramientas.add(actualizarXmlRef);

        menuSuperior.add(herramientas);

        about.setText("About");

        clandlan.setText("Foro Traduccion");
        clandlan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clandlanActionPerformed(evt);
            }
        });
        about.add(clandlan);
        about.add(jSeparator1);

        jMenuItem1.setText("About");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        about.add(jMenuItem1);

        menuSuperior.add(about);

        setJMenuBar(menuSuperior);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(arbolScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(arbolScroll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void arbolValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_arbolValueChanged
        //Cargamos el popMenu del nodo actual
        DefaultMutableTreeNode sel = getSelecion();
        if (sel != null) {
            Nodo nodo = (Nodo) getSelecion().getUserObject();
            arbol.setComponentPopupMenu(nodo.getMenu());
        }
    }//GEN-LAST:event_arbolValueChanged

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        salir();
    }//GEN-LAST:event_formWindowClosing

    private void salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirActionPerformed
        salir();
    }//GEN-LAST:event_salirActionPerformed

    private void abrirAssetsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrirAssetsActionPerformed
        chooserAssets.setDialogTitle("Abrir Asset");
        chooserAssets.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (chooserAssets.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            controlador.leerAssets(chooserAssets.getSelectedFile());
        }
    }//GEN-LAST:event_abrirAssetsActionPerformed

    private void abrirCarpetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrirCarpetaActionPerformed
        chooserAssets.setDialogTitle("Abrir carpeta Asset");
        chooserAssets.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooserAssets.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            controlador.leerCarpeta(chooserAssets.getSelectedFile());
        }
    }//GEN-LAST:event_abrirCarpetaActionPerformed

    private void cerrarTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrarTodoActionPerformed
        Object[] opciones = {"Aceptar", "Cancelar"};
        int opcion = JOptionPane.showOptionDialog(rootPane, "¿Estás seguro de que deseas cerrar todos los Assets?, "
                + "perderás todos los cambios no guardados.", "¿Cerrar Assets?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE, null, opciones, "Aceptar");
        //Si acepta cerramos todos los assets
        if (opcion == JOptionPane.YES_OPTION) {
            DefaultTreeModel modelo = (DefaultTreeModel) arbol.getModel();
            DefaultMutableTreeNode raiz = (DefaultMutableTreeNode) modelo.getRoot();
            raiz.removeAllChildren();
            modelo.reload();
        }
    }//GEN-LAST:event_cerrarTodoActionPerformed

    private void guardarComoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarComoActionPerformed
        Nodo<Assets> nodo = (Nodo<Assets>) getSelecion().getUserObject();
        chooserAssets.setDialogTitle("Guardar Asset");
        chooserAssets.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooserAssets.setSelectedFile(new File(nodo.getNombre()));
        if (chooserAssets.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            controlador.escribirAssets(chooserAssets.getSelectedFile(), nodo.getContenido());
        }
    }//GEN-LAST:event_guardarComoActionPerformed

    private void cerrarAssetsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrarAssetsActionPerformed
        Object[] opciones = {"Aceptar", "Cancelar"};
        int opcion = JOptionPane.showOptionDialog(rootPane, "¿Estás seguro de que deseas cerrar este Assets?, "
                + "perderás todos los cambios no guardados.", "¿Cerrar Assets?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE, null, opciones, "Aceptar");
        if (opcion == JOptionPane.YES_OPTION) {
            DefaultTreeModel modelo = (DefaultTreeModel) arbol.getModel();
            //borramos el nodo
            modelo.removeNodeFromParent(getSelecion());
            //borramos todo si no hay mas assets
            DefaultMutableTreeNode raiz = (DefaultMutableTreeNode) modelo.getRoot();
            if (raiz.getChildAt(0).isLeaf()) {
                raiz.removeAllChildren();
                modelo.reload();
            }
        }
    }//GEN-LAST:event_cerrarAssetsActionPerformed

    private void nuevoIdiomaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoIdiomaActionPerformed
        String idioma;
        Enumeration lid = getSelecion().children();
        JComboBox combo = new JComboBox();
        //Idiomas disponibles
        while (lid.hasMoreElements()) {
            Nodo elec = (Nodo) ((DefaultMutableTreeNode) lid.nextElement()).getUserObject();
            combo.addItem(elec);
        }
        do {
            Object[] mostrar = {"Idioma de referencia\n", combo,
                "Identificador del idioma (2 letras)\n ejem (en , fr, de, it, es...)"};
            idioma = JOptionPane.showInputDialog(this, mostrar,
                    "Introduce idioma", JOptionPane.PLAIN_MESSAGE);
            //idioma incorrecto
            if (idioma != null && !idioma.matches("[a-zA-Z]{2}")) {
                JOptionPane.showMessageDialog(this, "Identificador invalido.\n",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                break;
            }
        } while (idioma != null);
        //Si no cancelo la operacion
        if (idioma != null) {
            idioma = idioma.toLowerCase();
            Nodo<Fichero> nodo = (Nodo<Fichero>) getSelecion().getUserObject();
            controlador.copiarIdioma(nodo.getContenido(), ((Nodo) combo.getSelectedItem()).getNombre(), idioma);
            //actualizar arbol
            getSelecion().removeAllChildren();
            getModelo().reload(getSelecion());
            cargarFichero(getSelecion(), nodo.getContenido());
        }
    }//GEN-LAST:event_nuevoIdiomaActionPerformed

    private void exportarIdiomaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportarIdiomaActionPerformed
        chooserXml.setDialogTitle("Exportar idioma");
        chooserXml.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooserXml.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            //Obtenemos el fichero y el idioma
            DefaultMutableTreeNode padre = (DefaultMutableTreeNode) getSelecion().getParent();
            DefaultMutableTreeNode nodo = getSelecion();
            controlador.exportar(chooserXml.getSelectedFile(),
                    ((Nodo<Fichero>) padre.getUserObject()).getContenido(),
                    ((Nodo) nodo.getUserObject()).getNombre());
        }
    }//GEN-LAST:event_exportarIdiomaActionPerformed

    private void borrarIdiomaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrarIdiomaActionPerformed
        Object[] opciones = {"Aceptar", "Cancelar"};
        int opcion = JOptionPane.showOptionDialog(rootPane, "¿Estás seguro de que deseas borrar este idioma?",
                "¿Borrar bloque?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE, null, opciones, "Aceptar");
        if (opcion == JOptionPane.YES_OPTION) {
            Nodo<Fichero> nodo = (Nodo<Fichero>) getSelecion().getUserObject();
            //Asi impedimos que borre todos los bloque, y corromper el assets
            if (nodo.getNombre().equals("en")) {
                JOptionPane.showMessageDialog(this, "No se puede borrar un bloque del idioma por defecto",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            controlador.borrar(nodo.getContenido(), nodo.getNombre());
            getModelo().removeNodeFromParent(getSelecion());
        }
    }//GEN-LAST:event_borrarIdiomaActionPerformed

    private void exportarBloqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportarBloqueActionPerformed
        DefaultMutableTreeNode padre = (DefaultMutableTreeNode) getSelecion().getParent();
        DefaultMutableTreeNode nodo = getSelecion();
        //Idioma seleccionando un bloque de un idioma
        Idioma idioma = ((Nodo<Bloque>) nodo.getUserObject()).getContenido()
                .getIdiomas().get(((Nodo) padre.getUserObject()).getNombre());
        Fichero f = idioma.getBloque().getFichero();
        //chooser
        chooserXml.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooserXml.setDialogTitle("Exportar Bloque");
        chooserXml.setSelectedFile(new File(f.getNombre() + "_" + idioma.getIdioma()
                + "_" + ((Nodo) nodo.getUserObject()).getNombre() + ".xml"));
        if (chooserXml.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {

            controlador.exportar(chooserXml.getSelectedFile(), idioma);
        }
    }//GEN-LAST:event_exportarBloqueActionPerformed

    private void anadirSubtituloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_anadirSubtituloActionPerformed
        String idioma;
        //Pedimos un idioma con dos letras minusculas
        do {
            idioma = JOptionPane.showInputDialog(this, "Identificador del idioma "
                    + "(2 letras)\nejem (en , fr, de, it, es...)",
                    "Introduce idioma", JOptionPane.PLAIN_MESSAGE);
            if (idioma != null && !idioma.matches("[a-zA-Z]{2}")) {
                JOptionPane.showMessageDialog(this, "Identificador invalido.\n",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                break;
            }
        } while (idioma != null);
        if (idioma != null) {
            Nodo<Localizacion> nodo = (Nodo<Localizacion>) getSelecion().getUserObject();
            controlador.addLocalizacion(nodo.getContenido(), idioma);
            //añadir localizacion
            getModelo().insertNodeInto(new DefaultMutableTreeNode(new Nodo(idioma)), getSelecion(), getSelecion().getChildCount());
        }
    }//GEN-LAST:event_anadirSubtituloActionPerformed

    private void borrarSubtituloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrarSubtituloActionPerformed
        Nodo<Localizacion> padre = (Nodo<Localizacion>) ((DefaultMutableTreeNode) getSelecion().getParent()).getUserObject();
        Nodo nodo = (Nodo) getSelecion().getUserObject();
        controlador.removeLocalizacion(padre.getContenido(), nodo.getNombre());
        //borrar localizacion
        getModelo().removeNodeFromParent(getSelecion());
    }//GEN-LAST:event_borrarSubtituloActionPerformed

    private void importarIdiomaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importarIdiomaActionPerformed
        chooserXml.setDialogTitle("Importar idioma");
        chooserXml.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooserXml.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            //Obtenemos el fichero y el idioma
            Nodo<Fichero> nodo = (Nodo<Fichero>) getSelecion().getUserObject();
            controlador.importar(chooserXml.getSelectedFile(), nodo.getContenido());
        }
    }//GEN-LAST:event_importarIdiomaActionPerformed

    private void importarBloqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importarBloqueActionPerformed
        chooserXml.setDialogTitle("Importar Bloque");
        chooserXml.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (chooserXml.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            DefaultMutableTreeNode padre = (DefaultMutableTreeNode) getSelecion().getParent();
            DefaultMutableTreeNode nodo = getSelecion();
            //Idioma seleccionando un bloque de un idioma
            Idioma idioma = ((Nodo<Bloque>) nodo.getUserObject()).getContenido()
                    .getIdiomas().get(((Nodo) padre.getUserObject()).getNombre());
            controlador.importar(chooserXml.getSelectedFile(), idioma);
        }
    }//GEN-LAST:event_importarBloqueActionPerformed

    private void cambiarIdiomaXmlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cambiarIdiomaXmlActionPerformed
        String idioma;
        do {
            idioma = JOptionPane.showInputDialog(this, "Identificador del idioma "
                    + "(2 letras)\nejem (en , fr, de, it, es...)",
                    "Nuevo idioma", JOptionPane.PLAIN_MESSAGE);
            if (idioma != null && !idioma.matches("[a-zA-Z]{2}")) {
                JOptionPane.showMessageDialog(this, "Identificador invalido.\n",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                break;
            }
        } while (idioma != null);
        if (idioma != null) {
            chooserXml.setDialogTitle("Cambiar XML");
            chooserXml.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if (chooserXml.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                controlador.cambiarIdioma(chooserXml.getSelectedFile(), idioma, getAssets());
            }
        }
    }//GEN-LAST:event_cambiarIdiomaXmlActionPerformed

    private void actualizarXmlRefActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actualizarXmlRefActionPerformed
        chooserXml.setDialogTitle("Cambiar XML");
        chooserXml.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (chooserXml.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            controlador.actualizarXmlRef(chooserXml.getSelectedFile());
        }
    }//GEN-LAST:event_actualizarXmlRefActionPerformed

    private void clandlanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clandlanActionPerformed
        try {
            Desktop.getDesktop().browse(new URI("http://www.clandlan.net/foros/topic/76661-traduccion-dreamfall-chapters/"));
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_clandlanActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        String mensaje = "Yo, Dreamfall Assets Editor fui creado por César Pomar con el unico propósito "
                + "\nde ayudarte a traducir Dreamfall Chapters al castellano. Si me encuentras algún error "
                + "\no quieres que aprenda a hacer más cosas, encontrarás a mi creador en el foro de Clan Dlan.";

        JOptionPane.showMessageDialog(this, mensaje, "About", -1);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void gestor_scriptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gestor_scriptActionPerformed
        ScriptGui a = new ScriptGui(this, true, icono);
        a.pack();
        a.setVisible(true);
    }//GEN-LAST:event_gestor_scriptActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        if (args.length > 0) {
            new Consola(args);
            return;
        }
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Grafica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Grafica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Grafica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Grafica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        new Grafica().setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu about;
    private javax.swing.JMenuItem abrirAssets;
    private javax.swing.JMenuItem abrirCarpeta;
    private javax.swing.JMenuItem actualizarXmlRef;
    private javax.swing.JMenuItem anadirSubtitulo;
    private javax.swing.JTree arbol;
    private javax.swing.JScrollPane arbolScroll;
    private javax.swing.JMenu archivo;
    private javax.swing.JMenuItem borrarIdioma;
    private javax.swing.JMenuItem borrarSubtitulo;
    private javax.swing.JMenuItem cambiarIdiomaXml;
    private javax.swing.JMenuItem cerrarAssets;
    private javax.swing.JMenuItem cerrarTodo;
    private javax.swing.JMenuItem clandlan;
    private javax.swing.JMenuItem exportarBloque;
    private javax.swing.JMenuItem exportarIdioma;
    private javax.swing.JMenuItem gestor_script;
    private javax.swing.JMenuItem guardarComo;
    private javax.swing.JMenu herramientas;
    private javax.swing.JMenuItem importarBloque;
    private javax.swing.JMenuItem importarIdioma;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenuBar menuSuperior;
    private javax.swing.JMenuItem nuevoIdioma;
    private javax.swing.JPopupMenu popAssets;
    private javax.swing.JPopupMenu popBloque;
    private javax.swing.JPopupMenu popFichero;
    private javax.swing.JPopupMenu popIdioma;
    private javax.swing.JPopupMenu popLocalizacion;
    private javax.swing.JPopupMenu popSubtitulo;
    private javax.swing.JMenuItem salir;
    private javax.swing.JMenu script;
    private javax.swing.JPopupMenu.Separator separadorMenu;
    // End of variables declaration//GEN-END:variables

    private JFileChooser chooserAssets;
    private JFileChooser chooserXml;
    private Controlador controlador;
    private Image icono;

    private void cargarChoosets() {
        //de assets
        chooserAssets = new JFileChooser();
        chooserAssets.setFileFilter(new FileNameExtensionFilter("Unity Asset File (*.assets)", "assets"));
        //de xml
        chooserXml = new JFileChooser();
        chooserXml.setFileFilter(new FileNameExtensionFilter("XML (*.xml)", "xml"));
    }

    private void salir() {
        Object[] opciones = {"Aceptar", "Cancelar"};
        int opcion = JOptionPane.showOptionDialog(rootPane, "¿Estás seguro de que deseas salir?, "
                + "perderás todos los cambios no guardados.", "¿Salir de Dreamfall Assets Editor?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE, null, opciones, "Aceptar");
        if (opcion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private DefaultTreeModel getModelo() {
        return (DefaultTreeModel) arbol.getModel();
    }

    private DefaultMutableTreeNode getSelecion() {
        TreePath path = arbol.getSelectionPath();
        if (path != null) {
            return ((DefaultMutableTreeNode) path.getLastPathComponent());
        }
        return null;
    }

    @Override
    public Assets[] getAssets() {
        //obtenemos las raiz
        DefaultMutableTreeNode raiz = (DefaultMutableTreeNode) getModelo().getRoot();
        //si no hay nad
        if (raiz.isLeaf()) {
            return null;
        }
        Enumeration hijos = raiz.getChildAt(0).children();
        Assets[] assets = new Assets[raiz.getChildAt(0).getChildCount()];
        //Añadimos los hijos del nodo dreamfall
        for (int i = 0; hijos.hasMoreElements(); i++) {
            assets[i] = ((Nodo<Assets>) ((DefaultMutableTreeNode) hijos.nextElement()).getUserObject()).getContenido();
        }
        return assets;
    }

    private void cargarLocalizacion(DefaultMutableTreeNode assets, Localizacion l) {
        DefaultTreeModel modelo = getModelo();
        DefaultMutableTreeNode fichero = new DefaultMutableTreeNode(new Nodo("Subtítulos", l, popLocalizacion));
        modelo.insertNodeInto(fichero, assets, 0);
        for (String sub : l.getSubtitulos()) {
            modelo.insertNodeInto(new DefaultMutableTreeNode(new Nodo(sub, l, popSubtitulo)),
                    fichero, fichero.getChildCount());
        }
    }

    private void cargarFichero(DefaultMutableTreeNode fichero, Fichero f) {
        DefaultTreeModel modelo = getModelo();
        /*hashMap para agrupar bloques por idioma en vez de idiomas por bloque*/
        HashMap<String, DefaultMutableTreeNode> map = new HashMap<>(10);
        int p = 0;
        for (Bloque b : f.getBloque()) {
            p++;
            for (Idioma idi : b.getIdiomas().values()) {
                DefaultMutableTreeNode idioma = map.get(idi.getIdioma());
                //Si no aparecio aun el idioma lo añadirmos
                if (idioma == null) {
                    idioma = new DefaultMutableTreeNode(new Nodo(idi.getIdioma(), f, popIdioma));
                    modelo.insertNodeInto(idioma, fichero, fichero.getChildCount());
                    map.put(idi.getIdioma(), idioma);
                }
                modelo.insertNodeInto(new DefaultMutableTreeNode(new Nodo("" + p, b, popBloque)),
                        idioma, idioma.getChildCount());
            }
        }

    }

    @Override
    public void nuevoAssets(Assets a) {
        DefaultTreeModel modelo = (DefaultTreeModel) arbol.getModel();
        DefaultMutableTreeNode raiz = (DefaultMutableTreeNode) modelo.getRoot();
        DefaultMutableTreeNode dreamfall, assets;
        //Añadimos el nodo el juego
        if (raiz.isLeaf()) {
            dreamfall = new DefaultMutableTreeNode(new Nodo("Dreamfall"));
            modelo.insertNodeInto(dreamfall, raiz, 0);
            modelo.reload();
        } else {
            dreamfall = (DefaultMutableTreeNode) raiz.getChildAt(0);
        }
        //Añadimos el nuevo Assets
        assets = new DefaultMutableTreeNode(new Nodo(a.getNombre(), a, popAssets));
        if (a.getLocalizacion() != null) {
            cargarLocalizacion(assets, a.getLocalizacion());
        }
        if (a.getFicheros() != null) {
            for (Fichero f : a.getFicheros()) {
                DefaultMutableTreeNode fichero = new DefaultMutableTreeNode(new Nodo(f.getNombre(), f, popFichero));
                modelo.insertNodeInto(fichero, assets, assets.getChildCount());
                cargarFichero(fichero, f);
            }
        }
        //Intertamos el assets en el juego
        modelo.insertNodeInto(assets, dreamfall, dreamfall.getChildCount());
    }

    @Override
    public void mostrar(Object message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

///////////////////////////////PRUEBAS///////////////////////////////////    
    private void mostrarAssetsTestFile() {
        //controlador.leerAssets(new File("D:\\Program Files (x86)\\Dreamfall Chapters Special Edition\\Dreamfall Chapters_Data\\sharedassets57.assets"));
        //Assets a = controlador.leerAssets(new File("D:\\Program Files\\Dreamfall Chapters\\Dreamfall Chapters_Data\\sharedassets48.assets"));
        //controlador.escribirAssets(new File("D:\\sharedassets48.assets"), a);
        //controlador.leerAssets(new File("D:\\Program Files\\Dreamfall Chapters2\\Dreamfall Chapters_Data\\resources.assets"));
        //Assets a = controlador.leerAssets(new File("D:\\Program Files\\Dreamfall Chapters\\Dreamfall Chapters_Data\\resources.assets"));
        //controlador.leerAssets(new File("D:\\Program Files (x86)\\Dreamfall Chapters Special Edition\\Dreamfall Chapters_Data\\resources.assets"));
        //controlador.leerAssets(new File("D:\\Users\\Informatica\\otros\\Instalador\\Dreamfall_Linux\\original\\resources.assets"));
        //controlador.leerAssets(new File("D:\\Program Files (x86)\\Dreamfall Chapters Special Edition\\Dreamfall Chapters_Data\\sharedassets15.assets"));
        //controlador.leerAssets(new File("D:\\Users\\Informatica\\otros\\Instalador\\Dreamfall_Linux\\original\\sharedassets15.assets"));
        Assets a = controlador.leerAssets(new File("D:\\Program Files\\Dreamfall Chapters\\Dreamfall Chapters_Data\\sharedassets11.assets"));
        controlador.copiarIdioma(a.getFicheros()[0], "en", "es");
        controlador.importar(new File("D:\\Users\\Informatica\\otros\\DreamfallPath\\textos\\sharedassets11.assets - StoryTimeDialogue\\StoryTimeDialogue"), a.getFicheros()[0]);
        controlador.escribirAssets(new File("D:\\sharedassets11.assets"), a);
        controlador.leerAssets(new File("D:\\sharedassets11.assets"));
    }

}
