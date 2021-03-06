package dreamfall_asset_editor.gui;

import dreamfall_asset_editor.controlador.Controlador;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 * Interfaz grafica para la generacion de scripts
 * @author César
 */
public final class ScriptGui extends javax.swing.JDialog {

    /**
     * Creates new form ScriptGui
     */
    public ScriptGui(Frame parent, boolean modal, Image icon) {
        super(parent, modal);
        initComponents();
        setIconImage(icon);
        setLocationRelativeTo(parent);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        abrir_xml = new javax.swing.JPanel();
        abrir_xml_ruta = new javax.swing.JTextField();
        abrir_xml_chooser = new javax.swing.JButton();
        abrir_xml_text = new javax.swing.JLabel();
        abrir_xml_text2 = new javax.swing.JLabel();
        abrir_xml_fichero = new javax.swing.JTextField();
        export_asset = new javax.swing.JPanel();
        exportar_xml_ruta = new javax.swing.JTextField();
        exportar_xml_chooser = new javax.swing.JButton();
        exportar_xml_text1 = new javax.swing.JLabel();
        exportar_xml_text3 = new javax.swing.JLabel();
        exportar_xml_fichero = new javax.swing.JTextField();
        exportar_xml_text2 = new javax.swing.JLabel();
        exportar_xml_idioma = new javax.swing.JTextField();
        nuevo_idioma = new javax.swing.JPanel();
        nuevo_idioma_text1 = new javax.swing.JLabel();
        nuevo_idioma_fichero = new javax.swing.JTextField();
        nuevo_idioma_nuevo = new javax.swing.JTextField();
        nuevo_idioma_text2 = new javax.swing.JLabel();
        nuevo_idioma_ref = new javax.swing.JTextField();
        nuevo_idioma_text3 = new javax.swing.JLabel();
        borrar_idioma = new javax.swing.JPanel();
        borrar_idioma_text1 = new javax.swing.JLabel();
        borrar_idioma_fichero = new javax.swing.JTextField();
        borrar_idioma_nombre = new javax.swing.JTextField();
        borrar_idioma_text2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable(){
            public String getToolTipText(MouseEvent event)
            {
                int row = convertColumnIndexToModel(rowAtPoint(event.getPoint()));
                int col  = convertColumnIndexToModel(columnAtPoint(event.getPoint()));
                DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
                Object sel=modelo.getValueAt(row, col);
                if(sel instanceof String){
                    return (String)sel;
                }
                return getColumnName(col);
            }
        }; ;
        fondo = new javax.swing.JPanel();
        add = new javax.swing.JButton();
        remove = new javax.swing.JButton();
        open = new javax.swing.JButton();
        save = new javax.swing.JButton();
        export = new javax.swing.JButton();
        run = new javax.swing.JButton();

        abrir_xml_chooser.setText("Abrir");
        abrir_xml_chooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abrir_xml_chooserActionPerformed(evt);
            }
        });

        abrir_xml_text.setText("Carpeta xml");

        abrir_xml_text2.setText("Nombre fichero");

        javax.swing.GroupLayout abrir_xmlLayout = new javax.swing.GroupLayout(abrir_xml);
        abrir_xml.setLayout(abrir_xmlLayout);
        abrir_xmlLayout.setHorizontalGroup(
            abrir_xmlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abrir_xmlLayout.createSequentialGroup()
                .addGroup(abrir_xmlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(abrir_xml_text)
                    .addComponent(abrir_xml_text2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(abrir_xmlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(abrir_xml_fichero, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(abrir_xmlLayout.createSequentialGroup()
                        .addComponent(abrir_xml_ruta, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(abrir_xml_chooser, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0))
        );
        abrir_xmlLayout.setVerticalGroup(
            abrir_xmlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abrir_xmlLayout.createSequentialGroup()
                .addGroup(abrir_xmlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(abrir_xml_text, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(abrir_xml_ruta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(abrir_xml_chooser))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(abrir_xmlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(abrir_xml_text2)
                    .addComponent(abrir_xml_fichero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        exportar_xml_chooser.setText("Abrir");
        exportar_xml_chooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportar_xml_chooserActionPerformed(evt);
            }
        });

        exportar_xml_text1.setText("Carpeta xml");

        exportar_xml_text3.setText("Nombre fichero");

        exportar_xml_text2.setText("Nombre idioma");

        javax.swing.GroupLayout export_assetLayout = new javax.swing.GroupLayout(export_asset);
        export_asset.setLayout(export_assetLayout);
        export_assetLayout.setHorizontalGroup(
            export_assetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(export_assetLayout.createSequentialGroup()
                .addGroup(export_assetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(exportar_xml_text2)
                    .addComponent(exportar_xml_text1)
                    .addComponent(exportar_xml_text3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(export_assetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(export_assetLayout.createSequentialGroup()
                        .addGroup(export_assetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(exportar_xml_ruta, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(exportar_xml_fichero, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(exportar_xml_chooser, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(exportar_xml_idioma, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );
        export_assetLayout.setVerticalGroup(
            export_assetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(export_assetLayout.createSequentialGroup()
                .addGroup(export_assetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exportar_xml_text1, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(exportar_xml_ruta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(exportar_xml_chooser))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(export_assetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exportar_xml_text3)
                    .addComponent(exportar_xml_fichero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(export_assetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exportar_xml_text2)
                    .addComponent(exportar_xml_idioma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        nuevo_idioma_text1.setText("Nombre fichero");

        nuevo_idioma_text2.setText("Nuevo idioma");

        nuevo_idioma_text3.setText("Idioma referencia");

        javax.swing.GroupLayout nuevo_idiomaLayout = new javax.swing.GroupLayout(nuevo_idioma);
        nuevo_idioma.setLayout(nuevo_idiomaLayout);
        nuevo_idiomaLayout.setHorizontalGroup(
            nuevo_idiomaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, nuevo_idiomaLayout.createSequentialGroup()
                .addComponent(nuevo_idioma_text3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nuevo_idioma_ref, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, nuevo_idiomaLayout.createSequentialGroup()
                .addComponent(nuevo_idioma_text2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nuevo_idioma_nuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, nuevo_idiomaLayout.createSequentialGroup()
                .addComponent(nuevo_idioma_text1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nuevo_idioma_fichero, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        nuevo_idiomaLayout.setVerticalGroup(
            nuevo_idiomaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nuevo_idiomaLayout.createSequentialGroup()
                .addGroup(nuevo_idiomaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nuevo_idioma_text1)
                    .addComponent(nuevo_idioma_fichero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(nuevo_idiomaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nuevo_idioma_text2)
                    .addComponent(nuevo_idioma_nuevo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(nuevo_idiomaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nuevo_idioma_text3)
                    .addComponent(nuevo_idioma_ref, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        borrar_idioma_text1.setText("Nombre fichero");

        borrar_idioma_text2.setText("Idioma");

        javax.swing.GroupLayout borrar_idiomaLayout = new javax.swing.GroupLayout(borrar_idioma);
        borrar_idioma.setLayout(borrar_idiomaLayout);
        borrar_idiomaLayout.setHorizontalGroup(
            borrar_idiomaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(borrar_idiomaLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(borrar_idiomaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, borrar_idiomaLayout.createSequentialGroup()
                        .addComponent(borrar_idioma_text2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(borrar_idioma_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, borrar_idiomaLayout.createSequentialGroup()
                        .addComponent(borrar_idioma_text1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(borrar_idioma_fichero, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        borrar_idiomaLayout.setVerticalGroup(
            borrar_idiomaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(borrar_idiomaLayout.createSequentialGroup()
                .addGroup(borrar_idiomaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(borrar_idioma_text1)
                    .addComponent(borrar_idioma_fichero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(borrar_idiomaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(borrar_idioma_text2)
                    .addComponent(borrar_idioma_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setTitle("Gestor Scripts");
        setBackground(new java.awt.Color(255, 255, 255));

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null}
            },
            new String [] {
                "Funciones"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tabla);
        if (tabla.getColumnModel().getColumnCount() > 0) {
            tabla.getColumnModel().getColumn(0).setResizable(false);
        }

        fondo.setBackground(new java.awt.Color(255, 255, 255));

        add.setText("Añadir");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        remove.setText("Eliminar");
        remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeActionPerformed(evt);
            }
        });

        open.setText("Abrir");
        open.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openActionPerformed(evt);
            }
        });

        save.setText("Guardar");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });

        export.setText("Exportar");
        export.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportActionPerformed(evt);
            }
        });

        run.setText("Ejecutar");
        run.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout fondoLayout = new javax.swing.GroupLayout(fondo);
        fondo.setLayout(fondoLayout);
        fondoLayout.setHorizontalGroup(
            fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fondoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(add, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(remove, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                    .addComponent(open, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                    .addComponent(save, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                    .addComponent(export, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                    .addComponent(run, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE))
                .addContainerGap())
        );
        fondoLayout.setVerticalGroup(
            fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fondoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(add)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(remove)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(open)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(save)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(export)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(run)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(fondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(fondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        int ultimo = tabla.getSelectedRow();
        if (ultimo == -1) {
            ultimo = tabla.getRowCount() - 1;
        }
        String[] opts = new String[]{"Abrir asset", "Guardar asset", "Nuevo Idioma", "Borrar Idioma", "Cargar xml", "Exportar xml", "Nuevo subtítulo", "Borrar subtítulo"};
        Object seleccion = JOptionPane.showInputDialog(
                this,
                "Selecciona una función",
                "Funciones",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opts,
                null);
        String comando = null;
        if (seleccion != null) {
            switch ((String) seleccion) {
                case "Abrir asset":
                    comando = abrirAsset();
                    break;
                case "Guardar asset":
                    comando = guardarAsset();
                    break;
                case "Nuevo Idioma":
                    comando = nuevoIdioma();
                    break;
                case "Borrar Idioma":
                    comando = borrarIdioma();
                    break;
                case "Cargar xml":
                    comando = cargaXml();
                    break;
                case "Exportar xml":
                    comando = exportaXml();
                    break;
                case "Nuevo subtítulo":
                    comando = nuevoSub();
                    break;
                case "Borrar subtítulo":
                    comando = borrarSub();
                    break;
            }
            if (comando != null) {
                modelo.insertRow(ultimo, new String[]{comando});
                tabla.setToolTipText("hola");
            }
        }
    }//GEN-LAST:event_addActionPerformed

    private void removeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeActionPerformed
        int[] sels = tabla.getSelectedRows();
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        for (int i = sels.length - 1; i > -1; i--) {
            if (i != tabla.getRowCount() - 1) {
                modelo.removeRow(i);
            }
        }
    }//GEN-LAST:event_removeActionPerformed

    private void openActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openActionPerformed
        JFileChooser abrir = new JFileChooser();
        abrir.setFileSelectionMode(JFileChooser.FILES_ONLY);
        abrir.setFileFilter(new FileNameExtensionFilter("Dreamfall Assets Editor Script (*.daes)", "daes"));
        if (abrir.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            String[] comandos = Controlador.abrirScript(this, abrir.getSelectedFile());
            if (comandos != null) {
                DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
                modelo.removeRow(0);
                for (String row : comandos) {
                    modelo.addRow(new Object[]{row});
                }
                modelo.addRow(new Object[]{""});
            }
        }
    }//GEN-LAST:event_openActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        String[] comandos = new String[tabla.getRowCount() - 1];
        for (int i = 0; i < comandos.length; i++) {
            comandos[i] = (String) modelo.getValueAt(i, 0);
        }
        JFileChooser abrir = new JFileChooser();
        abrir.setFileSelectionMode(JFileChooser.FILES_ONLY);
        abrir.setFileFilter(new FileNameExtensionFilter("Dreamfall Assets Editor Script (*.daes)", "daes"));
        if (abrir.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {

            Controlador.guardarScript(this, addEx(abrir.getFileFilter(), abrir.getSelectedFile()), comandos);
        }
    }//GEN-LAST:event_saveActionPerformed

    private void exportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportActionPerformed
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        String[] comandos = new String[tabla.getRowCount() - 1];
        for (int i = 0; i < comandos.length; i++) {
            comandos[i] = (String) modelo.getValueAt(i, 0);
        }
        JFileChooser abrir = new JFileChooser();
        abrir.setFileSelectionMode(JFileChooser.FILES_ONLY);
        abrir.setFileFilter(new FileNameExtensionFilter("Linux Script (*.sh)", "sh"));
        abrir.setFileFilter(new FileNameExtensionFilter("Windows Script (*.bat)", "bat"));
        if (abrir.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            Controlador.exportarScript(this, addEx(abrir.getFileFilter(), abrir.getSelectedFile()), comandos);
        }
    }//GEN-LAST:event_exportActionPerformed

    private void abrir_xml_chooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrir_xml_chooserActionPerformed
        JFileChooser abrir = new JFileChooser();
        abrir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (abrir.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            abrir_xml_ruta.setText(abrir.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_abrir_xml_chooserActionPerformed

    private void exportar_xml_chooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportar_xml_chooserActionPerformed
        JFileChooser abrir = new JFileChooser();
        abrir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (abrir.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            exportar_xml_ruta.setText(abrir.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_exportar_xml_chooserActionPerformed

    private void runActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runActionPerformed
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        String[] comandos = new String[tabla.getRowCount() - 1];
        for (int i = 0; i < comandos.length; i++) {
            comandos[i] = (String) modelo.getValueAt(i, 0);
        }
        Controlador.ejecutarScript(this, comandos);
    }//GEN-LAST:event_runActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel abrir_xml;
    private javax.swing.JButton abrir_xml_chooser;
    private javax.swing.JTextField abrir_xml_fichero;
    private javax.swing.JTextField abrir_xml_ruta;
    private javax.swing.JLabel abrir_xml_text;
    private javax.swing.JLabel abrir_xml_text2;
    private javax.swing.JButton add;
    private javax.swing.JPanel borrar_idioma;
    private javax.swing.JTextField borrar_idioma_fichero;
    private javax.swing.JTextField borrar_idioma_nombre;
    private javax.swing.JLabel borrar_idioma_text1;
    private javax.swing.JLabel borrar_idioma_text2;
    private javax.swing.JButton export;
    private javax.swing.JPanel export_asset;
    private javax.swing.JButton exportar_xml_chooser;
    private javax.swing.JTextField exportar_xml_fichero;
    private javax.swing.JTextField exportar_xml_idioma;
    private javax.swing.JTextField exportar_xml_ruta;
    private javax.swing.JLabel exportar_xml_text1;
    private javax.swing.JLabel exportar_xml_text2;
    private javax.swing.JLabel exportar_xml_text3;
    private javax.swing.JPanel fondo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel nuevo_idioma;
    private javax.swing.JTextField nuevo_idioma_fichero;
    private javax.swing.JTextField nuevo_idioma_nuevo;
    private javax.swing.JTextField nuevo_idioma_ref;
    private javax.swing.JLabel nuevo_idioma_text1;
    private javax.swing.JLabel nuevo_idioma_text2;
    private javax.swing.JLabel nuevo_idioma_text3;
    private javax.swing.JButton open;
    private javax.swing.JButton remove;
    private javax.swing.JButton run;
    private javax.swing.JButton save;
    private javax.swing.JTable tabla;
    // End of variables declaration//GEN-END:variables

    private String abrirAsset() {
        JFileChooser abrir = new JFileChooser();
        abrir.setFileSelectionMode(JFileChooser.FILES_ONLY);
        abrir.setFileFilter(new FileNameExtensionFilter("Unity Asset File (*.assets)", "assets"));
        if (abrir.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            return "-a \"" + abrir.getSelectedFile().getAbsolutePath() + "\"";
        }
        return null;
    }

    private String guardarAsset() {
        JFileChooser abrir = new JFileChooser();
        abrir.setFileSelectionMode(JFileChooser.FILES_ONLY);
        abrir.setFileFilter(new FileNameExtensionFilter("Unity Asset File (*.assets)", "assets"));
        if (abrir.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            return "-g \"" + abrir.getSelectedFile().getAbsolutePath() + "\"";
        }
        return null;
    }

    private String nuevoIdioma() {
        nuevo_idioma_fichero.setText("");
        nuevo_idioma_nuevo.setText("");
        nuevo_idioma_ref.setText("");
        if (JOptionPane.showOptionDialog(this,
                nuevo_idioma,
                "Importar xml",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                new Object[]{"Aceptar", "Cancelar"},
                null) == 0) {
            return "-ni " + nuevo_idioma_fichero.getText() + " " + nuevo_idioma_nuevo.getText() + " " + nuevo_idioma_ref.getText();
        }
        return null;
    }

    private String borrarIdioma() {
        borrar_idioma_fichero.setText("");
        borrar_idioma_nombre.setText("");
        if (JOptionPane.showOptionDialog(this,
                borrar_idioma,
                "Importar xml",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                new Object[]{"Aceptar", "Cancelar"},
                null) == 0) {
            return "-bi " + borrar_idioma_fichero.getText() + " " + borrar_idioma_nombre.getText();
        }
        return null;
    }

    private String cargaXml() {
        abrir_xml_ruta.setText("");
        abrir_xml_fichero.setText("");
        if (JOptionPane.showOptionDialog(this,
                abrir_xml,
                "Importar xml",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                new Object[]{"Aceptar", "Cancelar"},
                null) == 0) {
            return "-ii \"" + abrir_xml_ruta.getText() + "\" " + abrir_xml_fichero.getText();
        }
        return null;
    }

    private String exportaXml() {
        exportar_xml_ruta.setText("");
        exportar_xml_fichero.setText("");
        exportar_xml_idioma.setText("");
        JFileChooser abrir = new JFileChooser();
        abrir.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (abrir.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            return "-ei \"" + exportar_xml_ruta.getText() + "\" " + exportar_xml_fichero.getText() + " " + exportar_xml_idioma.getText();
        }
        return null;
    }

    private String nuevoSub() {
        String sub = JOptionPane.showInputDialog(
                this,
                "Nuevo Subtítulo",
                ""); 
        if (sub != null && sub.length() > 0) {
            return "-ns " + sub;
        }
        return null;
    }

    private String borrarSub() {
        String sub = JOptionPane.showInputDialog(
                this,
                "Borrar Subtítulo",
                "");  // el icono sera un iterrogante
        if (sub != null && sub.length() > 0) {
            return "-bs " + sub;
        }
        return null;
    }

    private File addEx(FileFilter f, File out) {
        if (f instanceof FileNameExtensionFilter) {
            String ext = ((FileNameExtensionFilter) f).getExtensions()[0];
            String ruta = out.getAbsolutePath();
            if (ruta.endsWith("." + ext)) {
                return out;
            } else {
                return new File(ruta + "." + ext);
            }
        }
        return null;
    }

}
