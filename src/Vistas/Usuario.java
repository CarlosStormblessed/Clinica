package Vistas;
import javax.swing.JPanel;
import Utilitarios.Utilitarios;
import Utilitarios.DatosPaginacion;
import Utilitarios.ModeloTabla;
import Modelos.UsuarioMod;
import Controladores.PaginadorTabla;
import Controladores.UsuarioCtrl;
import javax.swing.ButtonModel;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.ConnectException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.CardLayout;
import java.util.ArrayList;

/**
 *
 * @author Carlos
 */
public class Usuario extends javax.swing.JFrame implements ActionListener, TableModelListener{

    String contenidoActual;
    int xMouse, yMouse;
    private JComboBox<Integer> filasPermitidasUsuario;
    Utilitarios util = new Utilitarios();
    
    //Modelos
    private UsuarioMod usuario = new UsuarioMod();
    //Controladores
    private UsuarioCtrl usrCtrl = new UsuarioCtrl();
    
    private PaginadorTabla <ConsultaGeneral> paginadorUsuario;
    public Usuario() {
        initComponents();
        contenidoActual = "Inicio";
        setCartaContenido(panelInicio);
        btn_Confirmar.setVisible(false);
        lbl_btn_Confirmar.setEnabled(false);
        jtUsuario.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent Mouse_evt){
                JTable tabla = (JTable) Mouse_evt.getSource();
                Point punto = Mouse_evt.getPoint();
                int fila = tabla.rowAtPoint(punto);
                if (Mouse_evt.getClickCount() == 1)
                    lbl_SeleccionUsuario.setText(jtUsuario.getValueAt(jtUsuario.getSelectedRow(), 2).toString());
            }
        });
        reset();
    }
    
    private void reset(){
        resetUsuario();
        setTabla();
        lbl_SeleccionUsuario.setText("");
        panelPaginacionUsuario.removeAll();
        panelPaginacionUsuario.removeAll();
    }
    
    private void resetUsuario(){
        txtNombreUsuario.setText("");
        txtNombre.setText("");
        txtContrasena.setText("");
        txtConfirmarContrasena.setText("");
        comboRol.setSelectedIndex(1);
        
        usuario.setUsuario("");
        usuario.setNombre("");
        usuario.setPassword("");
        usuario.setRol("");
        usuario.setEstado("1");
    }
    
    private void getUsuario(){
        usuario.setUsuario(txtNombreUsuario.getText());
        usuario.setNombre(txtNombre.getText());
        usuario.setPassword(String.valueOf(txtContrasena.getPassword()));
        switch(comboRol.getSelectedIndex()){
            case 0:
                usuario.setRol("admin");
                break;
            case 1:
                usuario.setRol("medico");
                break;
            default:
                break;
        }
        usuario.setEstado("1");
    }
    
    private void setUsuario(){
        txtNombreUsuario.setText(usuario.getUsuario());
        txtNombre.setText(usuario.getNombre());
        txtContrasena.setText(usuario.getPassword());
        txtConfirmarContrasena.setText(usuario.getPassword());
        switch(usuario.getRol()){
            case "admin":
                comboRol.setSelectedIndex(0);
                break;
            case "medico":
                comboRol.setSelectedIndex(1);
                break;
            default:
                comboRol.setSelectedIndex(1);
                break;
        }
        
    }
    
    private boolean verificarUsuario(){
        boolean valido = false;
        if(txtNombre.getText().length() > 0){
            if (contenidoActual.equals("Crear")){
                List<UsuarioMod> usr = new ArrayList<UsuarioMod>();
                try {
                    usr = usrCtrl.seleccionarTodos();
                    for(int i = 0; i < usr.size(); i++){
                        if(usr.get(i).getUsuario().equals(txtNombreUsuario.getText())){
                            JOptionPane.showMessageDialog(this, "El usuario ya existe", "Nombre de usuario", JOptionPane.INFORMATION_MESSAGE);
                            return valido;
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ConnectException ex) {
                    Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(String.valueOf(txtContrasena.getPassword()).equals(String.valueOf(txtConfirmarContrasena.getPassword())))
                valido = true;
            else
                JOptionPane.showMessageDialog(this, "Las contraseñas no son iguales", "Contraseña", JOptionPane.INFORMATION_MESSAGE);
        }else
            JOptionPane.showMessageDialog(this, "Ingrese su nombre", "Nombre y Apellido", JOptionPane.INFORMATION_MESSAGE);
        return valido;
    }
    
    //Todo lo relacionado a la Tabla
    private TableModel crearModeloTablaFicha() {
        return new ModeloTabla<UsuarioMod>() {
            @Override
            public Object getValueAt(UsuarioMod t, int columna) {
                switch(columna){
                    case 0:
                        return t.getId();
                    case 1:
                        return t.getUsuario();
                    case 2:
                        return t.getNombre();
                    case 3:
                        return t.getRol();
                }
                return null;
            }

            @Override
            public String getColumnName(int columna) {
                switch(columna){
                    case 0:
                        return "Id";
                    case 1:
                        return "Nombre de Usuario";
                    case 2:
                        return "Nombre";
                    case 3:
                        return "Rol";
                }
                return null;
            }

            @Override
            public int getColumnCount() {
                return 4;
            }
        };
    }
    
    private void setTabla(){
        jtUsuario.setModel(crearModeloTablaFicha());
        DatosPaginacion<UsuarioMod> datosPaginacionFicha;
        try {
            datosPaginacionFicha = crearDatosPaginacionUsuario();
            paginadorUsuario = new PaginadorTabla(jtUsuario, datosPaginacionFicha, new int[]{5,10,20,25,50,75,100}, 10);
            paginadorUsuario.crearListadoFilasPermitidas(panelPaginacionUsuario);
            filasPermitidasUsuario = paginadorUsuario.getComboboxFilasPermitidas();
            filasPermitidasUsuario.addActionListener(this);
            jtUsuario.getModel().addTableModelListener(this);
            filasPermitidasUsuario.setSelectedItem(Integer.parseInt("10"));
            
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaGeneral.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConnectException ex) {
            Logger.getLogger(ConsultaGeneral.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private DatosPaginacion <UsuarioMod> crearDatosPaginacionUsuario() throws SQLException, ConnectException{
        List <UsuarioMod> lista = usrCtrl.seleccionarTodos();
        return new DatosPaginacion<UsuarioMod>(){
            @Override
            public int getTotalRowCount() {
                return lista.size();
            }

            @Override
            public List<UsuarioMod> getRows(int startIndex, int endIndex) {
                return lista.subList(startIndex, endIndex);
            }
        };
    }
    
    private void setCartaContenido(JPanel carta){
        panelCartasContenido.removeAll();
        panelCartasContenido.add(carta);
        panelCartasContenido.repaint();
        panelCartasContenido.revalidate();
    }
    
    private void crear() throws SQLException{
        try {
            int usr = 0;
                usr = usrCtrl.Crear(usuario);
                if (usr > 0) {
                    JOptionPane.showMessageDialog(this, "USUARIO INGRESADO CON EXITO", "Inserción de Usuarios", JOptionPane.INFORMATION_MESSAGE);
                    setCartaContenido(panelInicio);
                    reset();
                } else {
                    JOptionPane.showMessageDialog(this, "ERROR AL INSERTAR EL USUARIO. COMUNIQUESE CON TI", "Inserción de Usuarios", JOptionPane.ERROR_MESSAGE);
                }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR " + e.toString() + " AL INSERTAR EL USUARIO. COMUNIQUESE CON TI", "Inserción de Usuarios", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void actualizar() throws SQLException{
        try {
            int usr = 0;
                usr = usrCtrl.Actualizar(usuario);
                if (usr > 0) {
                    JOptionPane.showMessageDialog(this, "USUARIO ACTUALIZADO CON EXITO", "Inserción de Usuarios", JOptionPane.INFORMATION_MESSAGE);
                    reset();
                    setTabla();
                    btn_Confirmar.setVisible(false);
                    setCartaContenido(panelCombobox);
                } else {
                    JOptionPane.showMessageDialog(this, "ERROR AL ACTUALIZAR EL USUARIO. COMUNIQUESE CON TI", "Inserción de Usuarios", JOptionPane.ERROR_MESSAGE);
                }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR " + e.toString() + " AL ACTUALIZAR EL REGISTRO. COMUNIQUESE CON TI", "Inserción de Usuarios", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminar() throws SQLException{
        try {
            int usr = 0;
                usr = usrCtrl.Eliminar(usuario);
                if (usr > 0) {
                    JOptionPane.showMessageDialog(this, "USUARIO ELIMINADO CON EXITO", "Inserción de Usuarios", JOptionPane.INFORMATION_MESSAGE);
                    reset();
                    setTabla();
                    btn_Confirmar.setVisible(false);
                    setCartaContenido(panelCombobox);
                } else {
                    JOptionPane.showMessageDialog(this, "ERROR AL ELIMINADO EL USUARIO. COMUNIQUESE CON TI", "Inserción de Usuarios", JOptionPane.ERROR_MESSAGE);
                }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR " + e.toString() + " AL ELIMINADO EL REGISTRO. COMUNIQUESE CON TI", "Inserción de Usuarios", JOptionPane.ERROR_MESSAGE);
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

        cont_Usuario = new javax.swing.JPanel();
        barra = new javax.swing.JPanel();
        exit = new javax.swing.JPanel();
        txtExit = new javax.swing.JLabel();
        panelTitulo = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        btn_Confirmar = new javax.swing.JPanel();
        lbl_btn_Confirmar = new javax.swing.JLabel();
        panelCartasContenido = new javax.swing.JPanel();
        panelFormulario = new javax.swing.JPanel();
        lblNombreUsuario = new javax.swing.JLabel();
        txtNombreUsuario = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblContrasena = new javax.swing.JLabel();
        txtContrasena = new javax.swing.JPasswordField();
        lblConfirmarContrasena = new javax.swing.JLabel();
        txtConfirmarContrasena = new javax.swing.JPasswordField();
        lblRol = new javax.swing.JLabel();
        comboRol = new javax.swing.JComboBox<>();
        btn_Regresar1 = new javax.swing.JPanel();
        lbl_Regresar1 = new javax.swing.JLabel();
        panelCombobox = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtUsuario = new javax.swing.JTable();
        btn_Ingresar = new javax.swing.JPanel();
        lbl_btnIngresar = new javax.swing.JLabel();
        btn_Regresar = new javax.swing.JPanel();
        lbl_Regresar = new javax.swing.JLabel();
        panelPaginacionUsuario = new javax.swing.JPanel();
        lbl_SeleccionUsuario = new javax.swing.JLabel();
        lbl_TituloSeleccion = new javax.swing.JLabel();
        lblTituloCombobox = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        panelInicio = new javax.swing.JPanel();
        btn_Crear = new javax.swing.JPanel();
        lbl_btnCrear = new javax.swing.JLabel();
        btn_Actualizar = new javax.swing.JPanel();
        lbl_btnActualizar = new javax.swing.JLabel();
        btn_Eliminar = new javax.swing.JPanel();
        lbl_BtnEliminar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cont_Usuario.setBackground(new java.awt.Color(255, 255, 255));
        cont_Usuario.setPreferredSize(new java.awt.Dimension(480, 360));
        cont_Usuario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
                .addGap(0, 440, Short.MAX_VALUE)
                .addComponent(exit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        barraLayout.setVerticalGroup(
            barraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, barraLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(exit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        cont_Usuario.add(barra, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 480, 40));

        panelTitulo.setBackground(new java.awt.Color(255, 255, 255));
        panelTitulo.setPreferredSize(new java.awt.Dimension(1080, 100));
        panelTitulo.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        lblTitulo.setFont(new java.awt.Font("Roboto", 0, 35)); // NOI18N
        lblTitulo.setText("Administración de Usuarios");
        lblTitulo.setMaximumSize(new java.awt.Dimension(445, 50));
        lblTitulo.setMinimumSize(new java.awt.Dimension(445, 50));
        lblTitulo.setPreferredSize(new java.awt.Dimension(432, 50));
        panelTitulo.add(lblTitulo);

        cont_Usuario.add(panelTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 480, 50));

        btn_Confirmar.setBackground(new java.awt.Color(255, 255, 255));
        btn_Confirmar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        lbl_btn_Confirmar.setBackground(new java.awt.Color(255, 255, 255));
        lbl_btn_Confirmar.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_btn_Confirmar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_btn_Confirmar.setText("Ingresar");
        lbl_btn_Confirmar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_btn_Confirmar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btn_ConfirmarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_btn_ConfirmarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_btn_ConfirmarMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btn_ConfirmarLayout = new javax.swing.GroupLayout(btn_Confirmar);
        btn_Confirmar.setLayout(btn_ConfirmarLayout);
        btn_ConfirmarLayout.setHorizontalGroup(
            btn_ConfirmarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_btn_Confirmar, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
        );
        btn_ConfirmarLayout.setVerticalGroup(
            btn_ConfirmarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_btn_Confirmar, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
        );

        cont_Usuario.add(btn_Confirmar, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 420, 90, 35));

        panelCartasContenido.setBackground(new java.awt.Color(255, 255, 255));
        panelCartasContenido.setMinimumSize(new java.awt.Dimension(480, 360));
        panelCartasContenido.setPreferredSize(new java.awt.Dimension(480, 480));
        panelCartasContenido.setLayout(new java.awt.CardLayout());

        panelFormulario.setBackground(new java.awt.Color(255, 255, 255));
        panelFormulario.setPreferredSize(new java.awt.Dimension(360, 300));
        panelFormulario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblNombreUsuario.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblNombreUsuario.setText("Nombre de Usuario (Login)");
        panelFormulario.add(lblNombreUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 0, -1, 20));

        txtNombreUsuario.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtNombreUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtNombreUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNombreUsuarioFocusLost(evt);
            }
        });
        panelFormulario.add(txtNombreUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, 200, 35));

        lblNombre.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblNombre.setText("Nombre y Apellido");
        panelFormulario.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, -1, 20));

        txtNombre.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtNombre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelFormulario.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 80, 200, 35));

        lblContrasena.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblContrasena.setText("Contraseña");
        panelFormulario.add(lblContrasena, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, -1, 20));

        txtContrasena.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtContrasena.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelFormulario.add(txtContrasena, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 140, 200, 35));

        lblConfirmarContrasena.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblConfirmarContrasena.setText("Confirmar Contraseña");
        panelFormulario.add(lblConfirmarContrasena, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 180, -1, -1));

        txtConfirmarContrasena.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtConfirmarContrasena.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelFormulario.add(txtConfirmarContrasena, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 200, 200, 35));

        lblRol.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblRol.setText("Rol");
        panelFormulario.add(lblRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 240, -1, 20));

        comboRol.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        comboRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "Personal Médico" }));
        comboRol.setSelectedIndex(1);
        panelFormulario.add(comboRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 260, 200, 35));

        btn_Regresar1.setBackground(new java.awt.Color(255, 255, 255));
        btn_Regresar1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        lbl_Regresar1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Regresar1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Regresar1.setText("Regresar");
        lbl_Regresar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_Regresar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_Regresar1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_Regresar1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_Regresar1MouseExited(evt);
            }
        });

        javax.swing.GroupLayout btn_Regresar1Layout = new javax.swing.GroupLayout(btn_Regresar1);
        btn_Regresar1.setLayout(btn_Regresar1Layout);
        btn_Regresar1Layout.setHorizontalGroup(
            btn_Regresar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_Regresar1, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
        );
        btn_Regresar1Layout.setVerticalGroup(
            btn_Regresar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_Regresar1, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
        );

        panelFormulario.add(btn_Regresar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, 90, 35));

        panelCartasContenido.add(panelFormulario, "card2");

        panelCombobox.setBackground(new java.awt.Color(255, 255, 255));
        panelCombobox.setPreferredSize(new java.awt.Dimension(360, 300));
        panelCombobox.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtUsuario.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jtUsuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id", "Usuario", "Nombre", "Rol"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jtUsuario);

        panelCombobox.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 440, 170));

        btn_Ingresar.setBackground(new java.awt.Color(92, 92, 235));
        btn_Ingresar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        lbl_btnIngresar.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_btnIngresar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_btnIngresar.setText("Ingresar");
        lbl_btnIngresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_btnIngresar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btnIngresarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_btnIngresarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_btnIngresarMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btn_IngresarLayout = new javax.swing.GroupLayout(btn_Ingresar);
        btn_Ingresar.setLayout(btn_IngresarLayout);
        btn_IngresarLayout.setHorizontalGroup(
            btn_IngresarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_btnIngresar, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
        );
        btn_IngresarLayout.setVerticalGroup(
            btn_IngresarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_btnIngresar, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
        );

        panelCombobox.add(btn_Ingresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 90, 90, 35));

        btn_Regresar.setBackground(new java.awt.Color(255, 255, 255));
        btn_Regresar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        lbl_Regresar.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Regresar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Regresar.setText("Regresar");
        lbl_Regresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_Regresar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_RegresarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_RegresarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_RegresarMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btn_RegresarLayout = new javax.swing.GroupLayout(btn_Regresar);
        btn_Regresar.setLayout(btn_RegresarLayout);
        btn_RegresarLayout.setHorizontalGroup(
            btn_RegresarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_Regresar, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
        );
        btn_RegresarLayout.setVerticalGroup(
            btn_RegresarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_Regresar, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
        );

        panelCombobox.add(btn_Regresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 90, 90, 35));

        panelPaginacionUsuario.setBackground(new java.awt.Color(255, 255, 255));
        panelCombobox.add(panelPaginacionUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 440, 30));

        lbl_SeleccionUsuario.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lbl_SeleccionUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lbl_SeleccionUsuario.setPreferredSize(new java.awt.Dimension(200, 40));
        panelCombobox.add(lbl_SeleccionUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 200, 35));

        lbl_TituloSeleccion.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lbl_TituloSeleccion.setText("Usuario Seleccionado");
        panelCombobox.add(lbl_TituloSeleccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        lblTituloCombobox.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        lblTituloCombobox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTituloCombobox.setText("Título");
        panelCombobox.add(lblTituloCombobox, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 480, -1));

        jLabel1.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Seleccione un Usuario");
        panelCombobox.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 480, -1));

        panelCartasContenido.add(panelCombobox, "card3");

        panelInicio.setBackground(new java.awt.Color(255, 255, 255));
        panelInicio.setPreferredSize(new java.awt.Dimension(360, 300));
        panelInicio.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 50, 5));

        btn_Crear.setBackground(new java.awt.Color(40, 235, 40));
        btn_Crear.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btn_Crear.setPreferredSize(new java.awt.Dimension(200, 100));

        lbl_btnCrear.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        lbl_btnCrear.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_btnCrear.setText("Crear");
        lbl_btnCrear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_btnCrear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btnCrearMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_btnCrearMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_btnCrearMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btn_CrearLayout = new javax.swing.GroupLayout(btn_Crear);
        btn_Crear.setLayout(btn_CrearLayout);
        btn_CrearLayout.setHorizontalGroup(
            btn_CrearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_btnCrear, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
        );
        btn_CrearLayout.setVerticalGroup(
            btn_CrearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_btnCrear, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
        );

        panelInicio.add(btn_Crear);

        btn_Actualizar.setBackground(new java.awt.Color(92, 92, 235));
        btn_Actualizar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btn_Actualizar.setPreferredSize(new java.awt.Dimension(200, 100));

        lbl_btnActualizar.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        lbl_btnActualizar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_btnActualizar.setText("Actualizar");
        lbl_btnActualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_btnActualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btnActualizarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_btnActualizarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_btnActualizarMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btn_ActualizarLayout = new javax.swing.GroupLayout(btn_Actualizar);
        btn_Actualizar.setLayout(btn_ActualizarLayout);
        btn_ActualizarLayout.setHorizontalGroup(
            btn_ActualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_btnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
        );
        btn_ActualizarLayout.setVerticalGroup(
            btn_ActualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_btnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
        );

        panelInicio.add(btn_Actualizar);

        btn_Eliminar.setBackground(new java.awt.Color(235, 91, 91));
        btn_Eliminar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btn_Eliminar.setPreferredSize(new java.awt.Dimension(200, 100));

        lbl_BtnEliminar.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        lbl_BtnEliminar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_BtnEliminar.setText("Eliminar");
        lbl_BtnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_BtnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_BtnEliminarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_BtnEliminarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_BtnEliminarMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btn_EliminarLayout = new javax.swing.GroupLayout(btn_Eliminar);
        btn_Eliminar.setLayout(btn_EliminarLayout);
        btn_EliminarLayout.setHorizontalGroup(
            btn_EliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_BtnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
        );
        btn_EliminarLayout.setVerticalGroup(
            btn_EliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_BtnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
        );

        panelInicio.add(btn_Eliminar);

        panelCartasContenido.add(panelInicio, "card4");

        cont_Usuario.add(panelCartasContenido, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 480, 360));

        getContentPane().add(cont_Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 480, 480));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lbl_btnCrearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnCrearMouseClicked
        if (!contenidoActual.equals("Formulario")){
            setCartaContenido(panelFormulario);
            btn_Crear.setBackground(util.colorCursorSale(btn_Crear.getBackground()));
            btn_Confirmar.setBackground(new Color(40,235,40));
            btn_Confirmar.setVisible(true);
            lbl_btn_Confirmar.setText("Ingresar");
            contenidoActual = "Crear";
            lbl_btn_Confirmar.setEnabled(true);
            reset();
        }
    }//GEN-LAST:event_lbl_btnCrearMouseClicked

    private void lbl_btnCrearMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnCrearMouseEntered
        btn_Crear.setBackground(util.colorCursorEntra(btn_Crear.getBackground()));
    }//GEN-LAST:event_lbl_btnCrearMouseEntered

    private void lbl_btnCrearMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnCrearMouseExited
        btn_Crear.setBackground(util.colorCursorSale(btn_Crear.getBackground()));
    }//GEN-LAST:event_lbl_btnCrearMouseExited

    private void lbl_btnActualizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnActualizarMouseClicked
        if (!contenidoActual.equals("Actualizar")){
            btn_Confirmar.setVisible(false);
            setCartaContenido(panelCombobox);
            btn_Actualizar.setBackground(util.colorCursorSale(btn_Actualizar.getBackground()));
            btn_Ingresar.setBackground(new Color(92,92,235));
            lblTituloCombobox.setText("Actualizar");
            lbl_btn_Confirmar.setText("Actualizar");
            contenidoActual = "Actualizar";
            lbl_btn_Confirmar.setEnabled(true);
            reset();
            setTabla();
        }
    }//GEN-LAST:event_lbl_btnActualizarMouseClicked

    private void lbl_btnActualizarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnActualizarMouseEntered
        btn_Actualizar.setBackground(util.colorCursorEntra(btn_Actualizar.getBackground()));
    }//GEN-LAST:event_lbl_btnActualizarMouseEntered

    private void lbl_btnActualizarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnActualizarMouseExited
        btn_Actualizar.setBackground(util.colorCursorSale(btn_Actualizar.getBackground()));
    }//GEN-LAST:event_lbl_btnActualizarMouseExited

    private void lbl_BtnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_BtnEliminarMouseClicked
        if (!contenidoActual.equals("Eliminar")){
            btn_Confirmar.setVisible(false);
            setCartaContenido(panelCombobox);
            btn_Eliminar.setBackground(util.colorCursorSale(btn_Eliminar.getBackground()));
            btn_Ingresar.setBackground(new Color(235,91,91));
            lblTituloCombobox.setText("Eliminar");
            lbl_btn_Confirmar.setText("Eliminar");
            contenidoActual = "Eliminar";
            lbl_btn_Confirmar.setEnabled(true);
            reset();
            setTabla();
        }
    }//GEN-LAST:event_lbl_BtnEliminarMouseClicked

    private void lbl_BtnEliminarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_BtnEliminarMouseEntered
        btn_Eliminar.setBackground(util.colorCursorEntra(btn_Eliminar.getBackground()));
    }//GEN-LAST:event_lbl_BtnEliminarMouseEntered

    private void lbl_BtnEliminarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_BtnEliminarMouseExited
        btn_Eliminar.setBackground(util.colorCursorSale(btn_Eliminar.getBackground()));
    }//GEN-LAST:event_lbl_BtnEliminarMouseExited

    private void lbl_btnIngresarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnIngresarMouseClicked
        if (lbl_SeleccionUsuario.getText().length() > 0){
            if (contenidoActual.equals("Actualizar")){
                setCartaContenido(panelFormulario);
                btn_Confirmar.setBackground(new Color(92,92,235));

                btn_Confirmar.setVisible(true);
            }else if (contenidoActual.equals("Eliminar")){
                setCartaContenido(panelFormulario);
                btn_Confirmar.setBackground(new Color(235,91,91));

                btn_Confirmar.setVisible(true);
            }
            try {
                usuario = usrCtrl.buscarFila(jtUsuario.getValueAt(jtUsuario.getSelectedRow(), 0).toString());
                setUsuario();
            } catch (SQLException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ConnectException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else
        JOptionPane.showMessageDialog(null, "Seleccione un registro de la tabla");
    }//GEN-LAST:event_lbl_btnIngresarMouseClicked

    private void lbl_btnIngresarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnIngresarMouseEntered
        btn_Ingresar.setBackground(util.colorCursorEntra(btn_Ingresar.getBackground()));
    }//GEN-LAST:event_lbl_btnIngresarMouseEntered

    private void lbl_btnIngresarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnIngresarMouseExited
        btn_Ingresar.setBackground(util.colorCursorSale(btn_Ingresar.getBackground()));
    }//GEN-LAST:event_lbl_btnIngresarMouseExited

    private void lbl_btn_ConfirmarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_ConfirmarMouseClicked
        if (lbl_btn_Confirmar.isEnabled()){
            if (verificarUsuario()){
                try {
                    UsuarioMod usrAux = new UsuarioMod();
                    usrAux.setRol(usuario.getRol());
                    getUsuario();
                    switch (contenidoActual){
                        case "Eliminar":
                            if(usrAux.getRol().equals("admin") && (usrCtrl.contarUsuarios(1) == 1)){
                                JOptionPane.showMessageDialog(this, "Debe existir por lo menos 1 usuario administrador");
                                comboRol.setSelectedIndex(0);
                                usuario.setRol("admin");
                                return;
                            }
                            eliminar();
                            break;
                        case "Actualizar":
                            if(usrAux.getRol().equals("admin") && (usuario.getRol().equals("medico")) && (usrCtrl.contarUsuarios(1) == 1)){
                                JOptionPane.showMessageDialog(this, "Debe existir por lo menos 1 usuario administrador");
                                comboRol.setSelectedIndex(0);
                                usuario.setRol("admin");
                                return;
                            }
                            actualizar();
                            break;
                        case "Crear":
                            crear();
                            break;
                        default:
                        break;
                    }
                }catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "ERROR " + e.toString() + " AL INSERTAR EL REGISTRO. COMUNIQUESE CON TI", "Inserción de Datos", JOptionPane.ERROR_MESSAGE);
                } catch (ConnectException ex) {
                    Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_lbl_btn_ConfirmarMouseClicked

    private void lbl_btn_ConfirmarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_ConfirmarMouseEntered
        if (lbl_btn_Confirmar.isEnabled())
        btn_Confirmar.setBackground(util.colorCursorEntra(btn_Confirmar.getBackground()));
    }//GEN-LAST:event_lbl_btn_ConfirmarMouseEntered

    private void lbl_btn_ConfirmarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_ConfirmarMouseExited
        if (lbl_btn_Confirmar.isEnabled())
        btn_Confirmar.setBackground(util.colorCursorSale(btn_Confirmar.getBackground()));
    }//GEN-LAST:event_lbl_btn_ConfirmarMouseExited

    private void txtExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtExitMouseClicked
        System.exit(0);
    }//GEN-LAST:event_txtExitMouseClicked

    private void txtExitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtExitMouseEntered
        exit.setBackground(Color.red);
        txtExit.setForeground(Color.white);
    }//GEN-LAST:event_txtExitMouseEntered

    private void txtExitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtExitMouseExited
        exit.setBackground(barra.getBackground());
        txtExit.setForeground(Color.black);
    }//GEN-LAST:event_txtExitMouseExited

    private void barraMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barraMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_barraMouseDragged

    private void barraMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barraMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_barraMousePressed

    private void txtNombreUsuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNombreUsuarioFocusLost
        if ((txtNombreUsuario.getText().contains(" "))){
            JOptionPane.showMessageDialog(this, "El nombre de usuario no puede contener espacios","Nombre de Usuario", JOptionPane.INFORMATION_MESSAGE);
            txtNombreUsuario.requestFocus();
        }
    }//GEN-LAST:event_txtNombreUsuarioFocusLost

    private void lbl_RegresarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_RegresarMouseClicked
        setCartaContenido(panelInicio);
        reset();
        contenidoActual = "Inicio";
    }//GEN-LAST:event_lbl_RegresarMouseClicked

    private void lbl_RegresarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_RegresarMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_RegresarMouseEntered

    private void lbl_RegresarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_RegresarMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_RegresarMouseExited

    private void lbl_Regresar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_Regresar1MouseClicked
        setCartaContenido(panelInicio);
        reset();
        contenidoActual = "Inicio";
    }//GEN-LAST:event_lbl_Regresar1MouseClicked

    private void lbl_Regresar1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_Regresar1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_Regresar1MouseEntered

    private void lbl_Regresar1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_Regresar1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_Regresar1MouseExited

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel barra;
    public javax.swing.JPanel btn_Actualizar;
    public javax.swing.JPanel btn_Confirmar;
    public javax.swing.JPanel btn_Crear;
    public javax.swing.JPanel btn_Eliminar;
    public javax.swing.JPanel btn_Ingresar;
    public javax.swing.JPanel btn_Regresar;
    public javax.swing.JPanel btn_Regresar1;
    private javax.swing.JComboBox<String> comboRol;
    private javax.swing.JPanel cont_Usuario;
    private javax.swing.JPanel exit;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JTable jtUsuario;
    private javax.swing.JLabel lblConfirmarContrasena;
    private javax.swing.JLabel lblContrasena;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNombreUsuario;
    private javax.swing.JLabel lblRol;
    private javax.swing.JLabel lblTitulo;
    public javax.swing.JLabel lblTituloCombobox;
    public javax.swing.JLabel lbl_BtnEliminar;
    public javax.swing.JLabel lbl_Regresar;
    public javax.swing.JLabel lbl_Regresar1;
    private javax.swing.JLabel lbl_SeleccionUsuario;
    private javax.swing.JLabel lbl_TituloSeleccion;
    public javax.swing.JLabel lbl_btnActualizar;
    public javax.swing.JLabel lbl_btnCrear;
    public javax.swing.JLabel lbl_btnIngresar;
    public javax.swing.JLabel lbl_btn_Confirmar;
    private javax.swing.JPanel panelCartasContenido;
    private javax.swing.JPanel panelCombobox;
    private javax.swing.JPanel panelFormulario;
    private javax.swing.JPanel panelInicio;
    private javax.swing.JPanel panelPaginacionUsuario;
    private javax.swing.JPanel panelTitulo;
    private javax.swing.JPasswordField txtConfirmarContrasena;
    private javax.swing.JPasswordField txtContrasena;
    private javax.swing.JLabel txtExit;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombreUsuario;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.equals(filasPermitidasUsuario))
            paginadorUsuario.eventComboBox(filasPermitidasUsuario);
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        Object evt = e.getSource();
        if (evt.equals(filasPermitidasUsuario))
            paginadorUsuario.actualizarBotonesPaginacion();
    }
}
