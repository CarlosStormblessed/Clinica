/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import Utilitarios.Utilitarios;
import java.awt.Color;
import java.sql.Connection;
import Conexion.Conexion;
import com.sun.glass.events.KeyEvent;
import java.awt.HeadlessException;
import java.net.ConnectException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import Utilitarios.Utilitarios;
import java.sql.PreparedStatement;

/**
 *
 * @author Carlos
 */
public class Login extends javax.swing.JFrame {

    Utilitarios util = new Utilitarios();
    int xMouse, yMouse;
    public Login() {
        initComponents();
        Utilitarios util = new Utilitarios();        
        lblAG.setIcon(util.construirImagen("/Imagenes/AG_logo.png", lblAG.getWidth(), lblAG.getHeight()));
    }
    
    private void ingresar(){
        if (txt_Usuario.getText().isEmpty() || txt_Usuario.getText().equals("Ingrese su usuario") || String.valueOf(txt_Contrasena.getPassword()).isEmpty() || String.valueOf(txt_Contrasena.getPassword()).equals("********"))
        {
            javax.swing.JOptionPane.showMessageDialog(this, "Ingrese todos los datos");
        }
        else
        {
            try{
                PreparedStatement smt = null;
                Connection conn;
                Conexion conex = new Conexion();
                conn = conex.connect();
                ResultSet resultado = null;
                String sql = "select * from clinica.usuario where USR_USUARIO = '" + txt_Usuario.getText() + "' AND USR_PASSWORD ='" + String.valueOf(txt_Contrasena.getPassword()) + "'";
                smt = conn.prepareStatement(sql);
                resultado = smt.executeQuery(sql);
                if (resultado.next()) {
                    String id;
                    id = resultado.getString(1);
                    MenuPrincipal menu = new MenuPrincipal();
                    menu.usuario_id = id;
                    menu.setVisible(true);
                    menu.setLocationRelativeTo(null);
                    this.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Consulta", JOptionPane.INFORMATION_MESSAGE);
                }
                }catch (HeadlessException | ConnectException | SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Ocurrió un error al intentar validar el usuario en Base de Datos, intente nuevamente." + ex.getMessage());
                }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Background = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        txt_Usuario = new javax.swing.JTextField();
        label_codigo = new javax.swing.JLabel();
        label_contrasena = new javax.swing.JLabel();
        txt_Contrasena = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        lblAG = new javax.swing.JLabel();
        ingresar = new javax.swing.JPanel();
        txt_btnIngresar = new javax.swing.JLabel();
        barra = new javax.swing.JPanel();
        exit = new javax.swing.JPanel();
        lblX = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setUndecorated(true);
        setResizable(false);

        Background.setBackground(new java.awt.Color(255, 255, 255));
        Background.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(182, 230, 255));

        txt_Usuario.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        txt_Usuario.setForeground(new java.awt.Color(153, 153, 153));
        txt_Usuario.setText("Ingrese su usuario");
        txt_Usuario.setBorder(null);
        txt_Usuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_UsuarioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_UsuarioFocusLost(evt);
            }
        });
        txt_Usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_UsuarioKeyPressed(evt);
            }
        });

        label_codigo.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        label_codigo.setText("Usuario");

        label_contrasena.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        label_contrasena.setText("Contraseña");

        txt_Contrasena.setForeground(new java.awt.Color(204, 204, 204));
        txt_Contrasena.setText("********");
        txt_Contrasena.setBorder(null);
        txt_Contrasena.setPreferredSize(new java.awt.Dimension(170, 30));
        txt_Contrasena.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_ContrasenaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_ContrasenaFocusLost(evt);
            }
        });
        txt_Contrasena.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_ContrasenaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(label_contrasena)
                    .addComponent(label_codigo)
                    .addComponent(txt_Usuario)
                    .addComponent(txt_Contrasena, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(32, 32, 32))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(label_codigo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(label_contrasena)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_Contrasena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        Background.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, 240, 210));

        jLabel1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("INICIAR SESIÓN");
        Background.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, -1, -1));

        lblAG.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Background.add(lblAG, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 240, 90));

        ingresar.setBackground(new java.awt.Color(204, 204, 204));
        ingresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        txt_btnIngresar.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        txt_btnIngresar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_btnIngresar.setText("Ingresar");
        txt_btnIngresar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_btnIngresarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txt_btnIngresarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txt_btnIngresarMouseExited(evt);
            }
        });

        javax.swing.GroupLayout ingresarLayout = new javax.swing.GroupLayout(ingresar);
        ingresar.setLayout(ingresarLayout);
        ingresarLayout.setHorizontalGroup(
            ingresarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ingresarLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(txt_btnIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        ingresarLayout.setVerticalGroup(
            ingresarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ingresarLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(txt_btnIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        Background.add(ingresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 430, -1, -1));

        barra.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                barraMouseDragged(evt);
            }
        });
        barra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                barraMousePressed(evt);
            }
        });

        exit.setForeground(new java.awt.Color(255, 0, 0));

        lblX.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblX.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblX.setText("X");
        lblX.setPreferredSize(new java.awt.Dimension(40, 40));
        lblX.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblXMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblXMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblXMouseExited(evt);
            }
        });

        javax.swing.GroupLayout exitLayout = new javax.swing.GroupLayout(exit);
        exit.setLayout(exitLayout);
        exitLayout.setHorizontalGroup(
            exitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, exitLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        exitLayout.setVerticalGroup(
            exitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, exitLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout barraLayout = new javax.swing.GroupLayout(barra);
        barra.setLayout(barraLayout);
        barraLayout.setHorizontalGroup(
            barraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(barraLayout.createSequentialGroup()
                .addContainerGap(370, Short.MAX_VALUE)
                .addComponent(exit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        barraLayout.setVerticalGroup(
            barraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(barraLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(exit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        Background.add(barra, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 410, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Background, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void barraMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barraMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_barraMousePressed

    private void barraMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barraMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_barraMouseDragged

    private void lblXMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseClicked
        System.exit(0);
    }//GEN-LAST:event_lblXMouseClicked

    private void lblXMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseEntered
        exit.setBackground(Color.red);
        lblX.setForeground(Color.white);
    }//GEN-LAST:event_lblXMouseEntered

    private void lblXMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseExited
        exit.setBackground(barra.getBackground());
        lblX.setForeground(Color.black);
    }//GEN-LAST:event_lblXMouseExited

    private void txt_btnIngresarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_btnIngresarMouseEntered
        ingresar.setBackground(util.colorCursorEntra(ingresar.getBackground()));
    }//GEN-LAST:event_txt_btnIngresarMouseEntered

    private void txt_btnIngresarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_btnIngresarMouseExited
        ingresar.setBackground(util.colorCursorSale(ingresar.getBackground()));
    }//GEN-LAST:event_txt_btnIngresarMouseExited

    private void txt_btnIngresarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_btnIngresarMouseClicked
        ingresar();
    }//GEN-LAST:event_txt_btnIngresarMouseClicked

    private void txt_UsuarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_UsuarioFocusGained
        if (txt_Usuario.getText().equals("Ingrese su usuario"))
        {
            txt_Usuario.setText("");
            txt_Usuario.setForeground(Color.black);
        }
        if (String.valueOf(txt_Contrasena.getPassword()).isEmpty())
        {
            txt_Contrasena.setText("********");
            txt_Contrasena.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txt_UsuarioFocusGained

    private void txt_ContrasenaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_ContrasenaFocusGained
        if (String.valueOf(txt_Contrasena.getPassword()).equals("********"))
        {
            txt_Contrasena.setText("");
            txt_Contrasena.setForeground(Color.black);
        }
        if (txt_Usuario.getText().isEmpty())
        {
            txt_Usuario.setText("Ingrese su usuario");
            txt_Usuario.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txt_ContrasenaFocusGained

    private void txt_UsuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_UsuarioFocusLost
        if (txt_Usuario.getText().isEmpty()){
            txt_Usuario.setText("Ingrese su usuario");
            txt_Usuario.setForeground(Color.black);
        }
    }//GEN-LAST:event_txt_UsuarioFocusLost

    private void txt_ContrasenaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_ContrasenaFocusLost
        if (String.valueOf(txt_Contrasena).isEmpty()){
            txt_Contrasena.setText("********");
            txt_Contrasena.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txt_ContrasenaFocusLost

    private void txt_ContrasenaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ContrasenaKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
            ingresar();
    }//GEN-LAST:event_txt_ContrasenaKeyPressed

    private void txt_UsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_UsuarioKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
            ingresar();
    }//GEN-LAST:event_txt_UsuarioKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Background;
    private javax.swing.JPanel barra;
    private javax.swing.JPanel exit;
    private javax.swing.JPanel ingresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel label_codigo;
    private javax.swing.JLabel label_contrasena;
    private javax.swing.JLabel lblAG;
    private javax.swing.JLabel lblX;
    private javax.swing.JPasswordField txt_Contrasena;
    private javax.swing.JTextField txt_Usuario;
    private javax.swing.JLabel txt_btnIngresar;
    // End of variables declaration//GEN-END:variables
}
