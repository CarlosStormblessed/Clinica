package Vistas;

import java.awt.Color;
import Utilitarios.Utilitarios;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.net.ConnectException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

public class MenuPrincipal extends javax.swing.JFrame {

    Utilitarios util = new Utilitarios();
    JPanel nuevoContenido = new JPanel();
    int xMouse, yMouse;
    public String usuario_id;
    String contenidoActual = "";
    public MenuPrincipal() {
        initComponents();
        construirEtiquetas();
        txt_Home.setIcon(util.construirImagen("/Imagenes/Home.png", txt_Home.getWidth(), txt_Home.getHeight()));
        Inicio inicio = new Inicio();
        nuevoContenido = inicio.getContenido();
        cambioContenido(nuevoContenido, inicio.nombreContenido);
        //contenido.removeAll();
    }
    
    private void construirEtiquetas(){
        txt_FichaAccidente.setText("<html>&emsp;Ficha Accidente</html>");
        txt_EvaluacionPeriodica.setText("<html>&emsp;Ficha Clínica</html>");
        txt_PacienteCronico.setText("<html>&emsp;Seguimiento Pacientes<br>&emsp;Crónicos</html>");
        txt_Preempleo.setText("<html>&emsp;Ficha Preempleo</html>");
        txt_EvaluacionPeriodica.setText("<html>&emsp;Revisión Periódica</html>");
    }
    
    private void cambioContenido(JPanel panel, String nombreContenido){
        contenido.removeAll();
        contenido.add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,-1,-1));
        contenido.revalidate();
        contenido.repaint();
        contenidoActual = nombreContenido;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        barra = new javax.swing.JPanel();
        exit = new javax.swing.JPanel();
        txtExit = new javax.swing.JLabel();
        barraOpciones = new javax.swing.JPanel();
        btn_Home = new javax.swing.JPanel();
        txt_Home = new javax.swing.JLabel();
        btn_Preempleo = new javax.swing.JPanel();
        txt_Preempleo = new javax.swing.JLabel();
        btn_FichaClinica = new javax.swing.JPanel();
        txt_FichaClinica = new javax.swing.JLabel();
        btn_EvaluacionPeriodica = new javax.swing.JPanel();
        txt_EvaluacionPeriodica = new javax.swing.JLabel();
        btn_PacienteCronico = new javax.swing.JPanel();
        txt_PacienteCronico = new javax.swing.JLabel();
        btn_FichaAccidente = new javax.swing.JPanel();
        txt_FichaAccidente = new javax.swing.JLabel();
        btn_VidaSaludable = new javax.swing.JPanel();
        txt_VidaSaludable = new javax.swing.JLabel();
        contenido = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        txtExit.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtExit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtExit.setText("X");
        txtExit.setMaximumSize(new java.awt.Dimension(40, 40));
        txtExit.setPreferredSize(new java.awt.Dimension(40, 40));
        txtExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtExitMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtExitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtExitMouseExited(evt);
            }
        });

        javax.swing.GroupLayout exitLayout = new javax.swing.GroupLayout(exit);
        exit.setLayout(exitLayout);
        exitLayout.setHorizontalGroup(
            exitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, exitLayout.createSequentialGroup()
                .addComponent(txtExit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        exitLayout.setVerticalGroup(
            exitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exitLayout.createSequentialGroup()
                .addComponent(txtExit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout barraLayout = new javax.swing.GroupLayout(barra);
        barra.setLayout(barraLayout);
        barraLayout.setHorizontalGroup(
            barraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, barraLayout.createSequentialGroup()
                .addGap(0, 1240, Short.MAX_VALUE)
                .addComponent(exit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        barraLayout.setVerticalGroup(
            barraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, barraLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(exit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.add(barra, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 40));

        barraOpciones.setBackground(new java.awt.Color(172, 211, 227));
        barraOpciones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        btn_Home.setBackground(new java.awt.Color(172, 211, 227));
        btn_Home.setMinimumSize(new java.awt.Dimension(200, 100));
        btn_Home.setPreferredSize(new java.awt.Dimension(200, 85));
        btn_Home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_HomeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_HomeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_HomeMouseExited(evt);
            }
        });
        btn_Home.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_Home.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_Home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_HomeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txt_HomeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txt_HomeMouseExited(evt);
            }
        });
        btn_Home.add(txt_Home, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 85, 85));

        barraOpciones.add(btn_Home);

        btn_Preempleo.setBackground(new java.awt.Color(195, 200, 230));
        btn_Preempleo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btn_Preempleo.setPreferredSize(new java.awt.Dimension(200, 85));
        btn_Preempleo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_Preempleo.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txt_Preempleo.setText("Preempleo");
        txt_Preempleo.setToolTipText("");
        txt_Preempleo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        txt_Preempleo.setMaximumSize(new java.awt.Dimension(200, 40));
        txt_Preempleo.setMinimumSize(new java.awt.Dimension(200, 40));
        txt_Preempleo.setPreferredSize(new java.awt.Dimension(200, 40));
        txt_Preempleo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_PreempleoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txt_PreempleoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txt_PreempleoMouseExited(evt);
            }
        });
        btn_Preempleo.add(txt_Preempleo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 85));

        barraOpciones.add(btn_Preempleo);

        btn_FichaClinica.setBackground(new java.awt.Color(172, 180, 227));
        btn_FichaClinica.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btn_FichaClinica.setPreferredSize(new java.awt.Dimension(200, 85));
        btn_FichaClinica.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_FichaClinica.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txt_FichaClinica.setText("Ficha Clínica");
        txt_FichaClinica.setToolTipText("");
        txt_FichaClinica.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        txt_FichaClinica.setMaximumSize(new java.awt.Dimension(200, 40));
        txt_FichaClinica.setMinimumSize(new java.awt.Dimension(200, 40));
        txt_FichaClinica.setPreferredSize(new java.awt.Dimension(200, 40));
        txt_FichaClinica.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_FichaClinicaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txt_FichaClinicaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txt_FichaClinicaMouseExited(evt);
            }
        });
        btn_FichaClinica.add(txt_FichaClinica, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 85));

        barraOpciones.add(btn_FichaClinica);

        btn_EvaluacionPeriodica.setBackground(new java.awt.Color(195, 200, 230));
        btn_EvaluacionPeriodica.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btn_EvaluacionPeriodica.setPreferredSize(new java.awt.Dimension(200, 85));
        btn_EvaluacionPeriodica.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_EvaluacionPeriodica.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txt_EvaluacionPeriodica.setText("Evaluación Periódica");
        txt_EvaluacionPeriodica.setToolTipText("");
        txt_EvaluacionPeriodica.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        txt_EvaluacionPeriodica.setMaximumSize(new java.awt.Dimension(200, 40));
        txt_EvaluacionPeriodica.setMinimumSize(new java.awt.Dimension(200, 40));
        txt_EvaluacionPeriodica.setPreferredSize(new java.awt.Dimension(200, 40));
        txt_EvaluacionPeriodica.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_EvaluacionPeriodicaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txt_EvaluacionPeriodicaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txt_EvaluacionPeriodicaMouseExited(evt);
            }
        });
        btn_EvaluacionPeriodica.add(txt_EvaluacionPeriodica, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 85));

        barraOpciones.add(btn_EvaluacionPeriodica);

        btn_PacienteCronico.setBackground(new java.awt.Color(172, 180, 227));
        btn_PacienteCronico.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btn_PacienteCronico.setPreferredSize(new java.awt.Dimension(200, 85));
        btn_PacienteCronico.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_PacienteCronico.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txt_PacienteCronico.setText("Seguimiento Pacientes Crónicos");
        txt_PacienteCronico.setToolTipText("");
        txt_PacienteCronico.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        txt_PacienteCronico.setMaximumSize(new java.awt.Dimension(200, 40));
        txt_PacienteCronico.setMinimumSize(new java.awt.Dimension(200, 40));
        txt_PacienteCronico.setPreferredSize(new java.awt.Dimension(200, 40));
        txt_PacienteCronico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_PacienteCronicoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txt_PacienteCronicoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txt_PacienteCronicoMouseExited(evt);
            }
        });
        btn_PacienteCronico.add(txt_PacienteCronico, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 85));

        barraOpciones.add(btn_PacienteCronico);

        btn_FichaAccidente.setBackground(new java.awt.Color(195, 200, 230));
        btn_FichaAccidente.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btn_FichaAccidente.setPreferredSize(new java.awt.Dimension(200, 85));
        btn_FichaAccidente.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_FichaAccidente.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txt_FichaAccidente.setText("Ficha Accidente");
        txt_FichaAccidente.setToolTipText("");
        txt_FichaAccidente.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        txt_FichaAccidente.setMaximumSize(new java.awt.Dimension(200, 40));
        txt_FichaAccidente.setMinimumSize(new java.awt.Dimension(200, 40));
        txt_FichaAccidente.setPreferredSize(new java.awt.Dimension(200, 40));
        txt_FichaAccidente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_FichaAccidenteMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txt_FichaAccidenteMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txt_FichaAccidenteMouseExited(evt);
            }
        });
        btn_FichaAccidente.add(txt_FichaAccidente, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 85));

        barraOpciones.add(btn_FichaAccidente);

        btn_VidaSaludable.setBackground(new java.awt.Color(172, 180, 227));
        btn_VidaSaludable.setPreferredSize(new java.awt.Dimension(200, 85));
        btn_VidaSaludable.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_VidaSaludable.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txt_VidaSaludable.setText("Seguimiento Vida Saludable");
        txt_VidaSaludable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_VidaSaludableMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txt_VidaSaludableMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txt_VidaSaludableMouseExited(evt);
            }
        });
        btn_VidaSaludable.add(txt_VidaSaludable, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 85));

        barraOpciones.add(btn_VidaSaludable);

        jPanel1.add(barraOpciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 200, 600));

        contenido.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout contenidoLayout = new javax.swing.GroupLayout(contenido);
        contenido.setLayout(contenidoLayout);
        contenidoLayout.setHorizontalGroup(
            contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1080, Short.MAX_VALUE)
        );
        contenidoLayout.setVerticalGroup(
            contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        jPanel1.add(contenido, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, 1080, 600));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtExitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtExitMouseEntered
        exit.setBackground(Color.red);
        txtExit.setForeground(Color.white);
    }//GEN-LAST:event_txtExitMouseEntered

    private void txtExitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtExitMouseExited
        exit.setBackground(barra.getBackground());
        txtExit.setForeground(Color.black);
    }//GEN-LAST:event_txtExitMouseExited

    private void txtExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtExitMouseClicked
        System.exit(0);
    }//GEN-LAST:event_txtExitMouseClicked

    private void barraMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barraMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_barraMousePressed

    private void barraMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barraMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_barraMouseDragged

    private void txt_PreempleoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_PreempleoMouseEntered
        btn_Preempleo.setBackground(util.colorCursorEntra(btn_Preempleo.getBackground()));
    }//GEN-LAST:event_txt_PreempleoMouseEntered

    private void txt_PreempleoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_PreempleoMouseExited
        btn_Preempleo.setBackground(util.colorCursorSale(btn_Preempleo.getBackground()));
    }//GEN-LAST:event_txt_PreempleoMouseExited

    private void txt_FichaClinicaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_FichaClinicaMouseEntered
        btn_FichaClinica.setBackground(util.colorCursorEntra(btn_FichaClinica.getBackground()));
    }//GEN-LAST:event_txt_FichaClinicaMouseEntered

    private void txt_FichaClinicaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_FichaClinicaMouseExited
        btn_FichaClinica.setBackground(util.colorCursorSale(btn_FichaClinica.getBackground()));
    }//GEN-LAST:event_txt_FichaClinicaMouseExited

    private void txt_FichaAccidenteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_FichaAccidenteMouseEntered
        btn_FichaAccidente.setBackground(util.colorCursorEntra(btn_FichaAccidente.getBackground()));
    }//GEN-LAST:event_txt_FichaAccidenteMouseEntered

    private void txt_FichaAccidenteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_FichaAccidenteMouseExited
        btn_FichaAccidente.setBackground(util.colorCursorSale(btn_FichaAccidente.getBackground()));
    }//GEN-LAST:event_txt_FichaAccidenteMouseExited

    private void txt_EvaluacionPeriodicaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_EvaluacionPeriodicaMouseEntered
        btn_EvaluacionPeriodica.setBackground(util.colorCursorEntra(btn_EvaluacionPeriodica.getBackground()));
    }//GEN-LAST:event_txt_EvaluacionPeriodicaMouseEntered

    private void txt_EvaluacionPeriodicaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_EvaluacionPeriodicaMouseExited
        btn_EvaluacionPeriodica.setBackground(util.colorCursorSale(btn_EvaluacionPeriodica.getBackground()));
    }//GEN-LAST:event_txt_EvaluacionPeriodicaMouseExited

    private void btn_HomeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_HomeMouseEntered
        btn_Home.setBackground(util.colorCursorEntra(btn_Home.getBackground()));
    }//GEN-LAST:event_btn_HomeMouseEntered

    private void txt_HomeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_HomeMouseEntered
        btn_Home.setBackground(util.colorCursorEntra(btn_Home.getBackground()));
    }//GEN-LAST:event_txt_HomeMouseEntered

    private void btn_HomeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_HomeMouseExited
        btn_Home.setBackground(util.colorCursorSale(btn_Home.getBackground()));
    }//GEN-LAST:event_btn_HomeMouseExited

    private void txt_HomeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_HomeMouseExited
        btn_Home.setBackground(util.colorCursorSale(btn_Home.getBackground()));
    }//GEN-LAST:event_txt_HomeMouseExited

    private void txt_PreempleoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_PreempleoMouseClicked
        try {
            Preempleo preempleo = new Preempleo();
            if (!preempleo.nombreContenido.equals(contenidoActual)){
                preempleo.responsable = usuario_id;
                nuevoContenido = preempleo.getContenido();
                cambioContenido(nuevoContenido, preempleo.nombreContenido);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConnectException ex) {
            Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception ex) {
            Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
    }//GEN-LAST:event_txt_PreempleoMouseClicked

    private void txt_FichaClinicaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_FichaClinicaMouseClicked
        try{
            ConsultaGeneral consultaGeneral= new ConsultaGeneral();
            if (!consultaGeneral.nombreContenido.equals(contenidoActual)){
                consultaGeneral.responsable = usuario_id;
                nuevoContenido = consultaGeneral.getContenido();
                cambioContenido(nuevoContenido, consultaGeneral.nombreContenido);
            }
        }catch (Exception ex) {
            Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        } 
    }//GEN-LAST:event_txt_FichaClinicaMouseClicked

    private void btn_HomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_HomeMouseClicked
        Inicio dashboard = new Inicio();
        if (!dashboard.nombreContenido.equals(contenidoActual)){
            nuevoContenido = dashboard.getContenido();
            cambioContenido(nuevoContenido, dashboard.nombreContenido);
        }
    }//GEN-LAST:event_btn_HomeMouseClicked

    private void txt_HomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_HomeMouseClicked
        Inicio dashboard = new Inicio();
        if (!dashboard.nombreContenido.equals(contenidoActual)){
            nuevoContenido = dashboard.getContenido();
            cambioContenido(nuevoContenido, dashboard.nombreContenido);
        }
    }//GEN-LAST:event_txt_HomeMouseClicked

    private void txt_EvaluacionPeriodicaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_EvaluacionPeriodicaMouseClicked

        try{
            EvaluacionPeriodica evaluacionPeriodica = new EvaluacionPeriodica();
            if (!evaluacionPeriodica.nombreContenido.equals(contenidoActual)){
                evaluacionPeriodica.responsable = usuario_id;
                nuevoContenido = evaluacionPeriodica.getContenido();
                cambioContenido(nuevoContenido, evaluacionPeriodica.nombreContenido);
            }
        }catch (Exception ex) {
            Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        } 
    }//GEN-LAST:event_txt_EvaluacionPeriodicaMouseClicked

    private void txt_PacienteCronicoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_PacienteCronicoMouseClicked
        try{
            SeguimientoCronicos seguimientoCronicos = new SeguimientoCronicos();
            if (!seguimientoCronicos.nombreContenido.equals(contenidoActual)){
                seguimientoCronicos.responsable = usuario_id;
                nuevoContenido = seguimientoCronicos.getContenido();
                cambioContenido(nuevoContenido, seguimientoCronicos.nombreContenido);
            }
        }catch (Exception ex) {
            Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        } 
    }//GEN-LAST:event_txt_PacienteCronicoMouseClicked

    private void txt_PacienteCronicoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_PacienteCronicoMouseEntered
        btn_PacienteCronico.setBackground(util.colorCursorEntra(btn_PacienteCronico.getBackground()));
    }//GEN-LAST:event_txt_PacienteCronicoMouseEntered

    private void txt_PacienteCronicoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_PacienteCronicoMouseExited
        btn_PacienteCronico.setBackground(util.colorCursorSale(btn_PacienteCronico.getBackground()));
    }//GEN-LAST:event_txt_PacienteCronicoMouseExited

    private void txt_FichaAccidenteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_FichaAccidenteMouseClicked
        try{
            Accidentes accidentes = new Accidentes();
            if (!accidentes.nombreContenido.equals(contenidoActual)){
                accidentes.responsable = usuario_id;
                nuevoContenido = accidentes.getContenido();
                cambioContenido(nuevoContenido, accidentes.nombreContenido);
            }
        }catch (Exception ex) {
            Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        } 
    }//GEN-LAST:event_txt_FichaAccidenteMouseClicked

    private void txt_VidaSaludableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_VidaSaludableMouseEntered
        btn_VidaSaludable.setBackground(util.colorCursorEntra(btn_VidaSaludable.getBackground()));
    }//GEN-LAST:event_txt_VidaSaludableMouseEntered

    private void txt_VidaSaludableMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_VidaSaludableMouseExited
        btn_VidaSaludable.setBackground(util.colorCursorSale(btn_VidaSaludable.getBackground()));
    }//GEN-LAST:event_txt_VidaSaludableMouseExited

    private void txt_VidaSaludableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_VidaSaludableMouseClicked
        try{
            VidaSaludable vidaSaludable = new VidaSaludable();
            if (!vidaSaludable.nombreContenido.equals(contenidoActual)){
                vidaSaludable.responsable = usuario_id;
                nuevoContenido = vidaSaludable.getContenido();
                cambioContenido(nuevoContenido, vidaSaludable.nombreContenido);
            }
        }catch (Exception ex) {
            Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
    }//GEN-LAST:event_txt_VidaSaludableMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel barra;
    private javax.swing.JPanel barraOpciones;
    private javax.swing.JPanel btn_EvaluacionPeriodica;
    private javax.swing.JPanel btn_FichaAccidente;
    private javax.swing.JPanel btn_FichaClinica;
    private javax.swing.JPanel btn_Home;
    private javax.swing.JPanel btn_PacienteCronico;
    private javax.swing.JPanel btn_Preempleo;
    private javax.swing.JPanel btn_VidaSaludable;
    private javax.swing.JPanel contenido;
    private javax.swing.JPanel exit;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel txtExit;
    private javax.swing.JLabel txt_EvaluacionPeriodica;
    private javax.swing.JLabel txt_FichaAccidente;
    private javax.swing.JLabel txt_FichaClinica;
    private javax.swing.JLabel txt_Home;
    private javax.swing.JLabel txt_PacienteCronico;
    private javax.swing.JLabel txt_Preempleo;
    private javax.swing.JLabel txt_VidaSaludable;
    // End of variables declaration//GEN-END:variables
}
