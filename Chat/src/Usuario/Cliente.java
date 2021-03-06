package Usuario;

import Modelo.User;
import java.awt.Color;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.ListModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author alumno
 */
public class Cliente extends javax.swing.JFrame {

    static final String HOST = "localhost";
    static final int PUERTO = 6000;

    private static boolean conectado = false;

    private static User datosUsuario;

    private static HiloCliente hiloConversacion;

    private ListModel model;

    public Cliente() {
        initComponents();

        //creamos un nuevo usuario
        datosUsuario = new User();
        //comprobamos el estado
        checkConnection();
        //tomamos el listmodel del jlist
        model = listadoUsuarios.getModel();
    }

    //metodo para cambiar el color y los botones disponibles
    private void checkConnection() {
        if (!conectado) {
            menuConexion.setForeground(Color.RED);
            botonDesconectarse.setEnabled(false);
            botonConectarse.setEnabled(true);
        } else {
            menuConexion.setForeground(Color.GREEN);
            botonDesconectarse.setEnabled(true);
            botonConectarse.setEnabled(false);
        }
    }

    //añade texto al textField de conversacion
    public void addMessage(String msg) {
        textAreaChat.setText(textAreaChat.getText() + "\n" + msg);
    }

    public User getUser() {
        return datosUsuario;
    }

    //metodo que cambia el conteneros visible en la ventana
    private void cambiarContenedor(JPanel aux) {
        this.setContentPane(aux);
        pack();
    }

    //resetea el chat y la caja de mensaje
    private void reset() {
        textAreaChat.setText("");
        textAreaMensaje.setText("");
    }

    //metodo que añade datos de un arraylist al jlist
    public void mostrarUsuarios(ArrayList n) {
        String[] rows = new String[n.size()];

        for (int i = 0; i < n.size(); i++) {

            rows[i] = n.get(i).toString();

        }

        listadoUsuarios.setListData(rows);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelChat = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        textAreaChat = new javax.swing.JTextArea();
        panelUsuarios = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listadoUsuarios = new javax.swing.JList<>();
        panelMensaje = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textAreaMensaje = new javax.swing.JTextArea();
        panelBotonEnviar = new javax.swing.JPanel();
        botonEnviar = new javax.swing.JButton();
        panelConexion = new javax.swing.JPanel();
        jLabelInicioSesion = new javax.swing.JLabel();
        panelBotones = new javax.swing.JPanel();
        jButtonAceptar = new javax.swing.JButton();
        panelDatos = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabelUsuario = new javax.swing.JLabel();
        jTextFieldUsuario = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        menuPrincipal = new javax.swing.JMenuBar();
        menuConexion = new javax.swing.JMenu();
        botonConectarse = new javax.swing.JMenuItem();
        botonDesconectarse = new javax.swing.JMenuItem();

        panelChat.setMaximumSize(new java.awt.Dimension(750, 550));
        panelChat.setMinimumSize(new java.awt.Dimension(750, 550));
        panelChat.setPreferredSize(new java.awt.Dimension(750, 550));
        panelChat.setLayout(new java.awt.BorderLayout());

        textAreaChat.setEditable(false);
        textAreaChat.setColumns(20);
        textAreaChat.setRows(5);
        jScrollPane2.setViewportView(textAreaChat);

        panelChat.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        panelUsuarios.setLayout(new java.awt.BorderLayout());

        listadoUsuarios.setFocusable(false);
        listadoUsuarios.setPreferredSize(new java.awt.Dimension(99, 6));
        listadoUsuarios.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                elementSelected(evt);
            }
        });
        jScrollPane3.setViewportView(listadoUsuarios);

        panelUsuarios.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        panelChat.add(panelUsuarios, java.awt.BorderLayout.LINE_END);

        panelMensaje.setLayout(new java.awt.BorderLayout());

        textAreaMensaje.setColumns(20);
        textAreaMensaje.setRows(5);
        jScrollPane1.setViewportView(textAreaMensaje);

        panelMensaje.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        panelBotonEnviar.setLayout(new java.awt.GridLayout(2, 1));

        botonEnviar.setText("   Enviar   ");
        botonEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEnviarActionPerformed(evt);
            }
        });
        panelBotonEnviar.add(botonEnviar);

        panelMensaje.add(panelBotonEnviar, java.awt.BorderLayout.LINE_END);

        panelChat.add(panelMensaje, java.awt.BorderLayout.PAGE_END);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(750, 550));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.BorderLayout());

        panelConexion.setMaximumSize(new java.awt.Dimension(750, 550));
        panelConexion.setMinimumSize(new java.awt.Dimension(750, 550));
        panelConexion.setLayout(new java.awt.BorderLayout());

        jLabelInicioSesion.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabelInicioSesion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelInicioSesion.setText("INICIAR SESIÓN");
        panelConexion.add(jLabelInicioSesion, java.awt.BorderLayout.NORTH);

        panelBotones.setLayout(new java.awt.GridLayout(1, 0));

        jButtonAceptar.setText("Aceptar");
        jButtonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAceptarActionPerformed(evt);
            }
        });
        panelBotones.add(jButtonAceptar);

        panelConexion.add(panelBotones, java.awt.BorderLayout.SOUTH);

        panelDatos.setLayout(new java.awt.GridLayout(4, 2));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 375, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 124, Short.MAX_VALUE)
        );

        panelDatos.add(jPanel3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 375, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 124, Short.MAX_VALUE)
        );

        panelDatos.add(jPanel4);

        jLabelUsuario.setText("Usuario");
        panelDatos.add(jLabelUsuario);
        panelDatos.add(jTextFieldUsuario);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 375, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 124, Short.MAX_VALUE)
        );

        panelDatos.add(jPanel5);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 375, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 124, Short.MAX_VALUE)
        );

        panelDatos.add(jPanel6);

        panelConexion.add(panelDatos, java.awt.BorderLayout.CENTER);

        getContentPane().add(panelConexion, java.awt.BorderLayout.CENTER);

        menuConexion.setText("Conexión");

        botonConectarse.setText("Conectarse");
        botonConectarse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonConectarseActionPerformed(evt);
            }
        });
        menuConexion.add(botonConectarse);

        botonDesconectarse.setText("Desconectarse");
        botonDesconectarse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonDesconectarseActionPerformed(evt);
            }
        });
        menuConexion.add(botonDesconectarse);

        menuPrincipal.add(menuConexion);

        setJMenuBar(menuPrincipal);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //realmente este boton es inutil en este punto del desarrollo
    private void botonConectarseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonConectarseActionPerformed
        cambiarContenedor(panelConexion);
        checkConnection();
    }//GEN-LAST:event_botonConectarseActionPerformed

    private void jButtonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAceptarActionPerformed

        //si hay datos en el campo del nombre de usuario
        if (!jTextFieldUsuario.getText().equals("")) {
            //cambiamos a chat
            cambiarContenedor(panelChat);

            //realizar la conexion con el servidor
            Socket cliente = null;
            try {
                //tomamos el nombre de usuario del textfield
                datosUsuario.setNombre(jTextFieldUsuario.getText());
                //creamos el socket
                cliente = new Socket(HOST, PUERTO);
                //creamos el hilo
                hiloConversacion = new HiloCliente(this, cliente);
                //iniciamos su ejecucion
                hiloConversacion.start();

            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }

            //bandera de conectado levantada
            conectado = true;
            //comprobamos estado para activar/desactivar botones
            checkConnection();
        } else {
            //mostramos mensaje de error
        }
    }//GEN-LAST:event_jButtonAceptarActionPerformed

    private void botonDesconectarseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonDesconectarseActionPerformed
        //cambiamos al panel conexion
        cambiarContenedor(panelConexion);
        //bajamos la bandera de conectado
        conectado = false;
        //modificamos botones
        checkConnection();
        //mandamos una señal al hilo para que se desconecte
        hiloConversacion.desconectar();
        //reseteamos conversacion y caja de mensaje
        reset();
    }//GEN-LAST:event_botonDesconectarseActionPerformed

    private void botonEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEnviarActionPerformed
        //si la caja de mensaje no esta vacia
        if (!textAreaMensaje.getText().equals("")) {
            //le enviamos al hilo los datos que debe mandarle al servidor
            hiloConversacion.enviar(datosUsuario.getNombre(), textAreaMensaje.getText());
            //borramos el campo de mensaje
            textAreaMensaje.setText("");
        }

    }//GEN-LAST:event_botonEnviarActionPerformed

    private void elementSelected(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_elementSelected
        listadoUsuarios.setSelectedIndex(-1);

    }//GEN-LAST:event_elementSelected

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        //si el usuario cierra la ventana pulsando el boton de la X
        //mandamos una señal al hilo para que desconecte del servidor de forma segura
        hiloConversacion.desconectar();
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Cliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem botonConectarse;
    private javax.swing.JMenuItem botonDesconectarse;
    private javax.swing.JButton botonEnviar;
    private javax.swing.JButton jButtonAceptar;
    private javax.swing.JLabel jLabelInicioSesion;
    private javax.swing.JLabel jLabelUsuario;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextFieldUsuario;
    private javax.swing.JList<String> listadoUsuarios;
    private javax.swing.JMenu menuConexion;
    private javax.swing.JMenuBar menuPrincipal;
    private javax.swing.JPanel panelBotonEnviar;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JPanel panelChat;
    private javax.swing.JPanel panelConexion;
    private javax.swing.JPanel panelDatos;
    private javax.swing.JPanel panelMensaje;
    private javax.swing.JPanel panelUsuarios;
    private javax.swing.JTextArea textAreaChat;
    private javax.swing.JTextArea textAreaMensaje;
    // End of variables declaration//GEN-END:variables
}
