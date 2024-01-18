package Vistas;
import Utilitarios.*;
import Modelos.EmpleadoMod;
import Modelos.EmpleadoEmpleadoMod;
import Controladores.EmpleadoCtrl;
import Controladores.PaginadorTabla;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.ConnectException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
public class Empleado extends javax.swing.JFrame implements ActionListener, TableModelListener{

    private JComboBox<Integer> filasPermitidasEmpleado;
    private Utilitarios util = new Utilitarios();
    private EmpleadoEmpleadoMod empleado = new EmpleadoEmpleadoMod();
    private EmpleadoMod empleadoAux = new EmpleadoMod();
    private List <EmpleadoEmpleadoMod> empleados = new ArrayList<EmpleadoEmpleadoMod>();
    private EmpleadoCtrl empCtrl = new EmpleadoCtrl();
    
    public String nombreContenido = "Empleado";
    
    private PaginadorTabla <Empleado> paginadorEmpleado;
    
    public Empleado(){
        initComponents();
        lblAdvertencia.setVisible(false);
        jtEmpleado.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent Mouse_evt){
                JTable tabla = (JTable) Mouse_evt.getSource();
                Point punto = Mouse_evt.getPoint();
                int fila = tabla.rowAtPoint(punto);
                if (Mouse_evt.getClickCount() == 1){
                    txtSeleccionEmpleado.setText(jtEmpleado.getValueAt(jtEmpleado.getSelectedRow(), 1).toString());
                }
            }
        });
    }
    
    public JPanel getContenido(){
        try{
            return contEmpleado;
        }catch (Exception ex) {
            Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    private void reset(){
        empleado = null;
        txtNombre.setText("");
        txtCodigo.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
        grbtn_Sexo.setSelected(rbtn_SexoF.getModel(), false);
        grbtn_Sexo.setSelected(rbtn_SexoM.getModel(), false);
        rbtn_SexoF.setSelected(false);
        rbtn_SexoM.setSelected(false);
        
        txtSeleccionEmpleado.setText("");
    }
    
    private EmpleadoEmpleadoMod getEmpleado(){
        EmpleadoEmpleadoMod empAux = new EmpleadoEmpleadoMod();
        empAux.setNombreNuevo(txtNombre.getText());
        empAux.setCodigo(txtCodigo.getText());
        empAux.setDireccionNuevo(txtDireccion.getText());
        if(rbtn_SexoF.isSelected())
            empAux.setSexoNuevo("F");
        else if(rbtn_SexoM.isSelected())
            empAux.setSexoNuevo("M");
        empAux.setTelefonoNuevo(txtTelefono.getText());
        try {
            empleadoAux = empCtrl.buscarFilaPorCodigo(txtCodigo.getText());
            if(empleadoAux != null){
                empAux.setNombreAnterior(empleadoAux.getNombre());
                empAux.setSexoAnterior(empleadoAux.getSexo());
                empAux.setTelefonoAnterior(empleadoAux.getTelefono());
                empAux.setDireccionAnterior(empleadoAux.getDireccion());
            }else{
                empAux.setNombreAnterior("N/A");
                empAux.setSexoAnterior("N/A");
                empAux.setTelefonoAnterior("N/A");
                empAux.setDireccionAnterior("N/A");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConnectException ex) {
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return empAux;
    }
    
    private boolean verificarEmpleado(){
        boolean valido = false;
        if((txtNombre.getText().length()>0) && (verificarSexo()) && (util.verificarNumero(txtCodigo.getText())) 
        && (util.verificarNumero(txtTelefono.getText())) && (txtDireccion.getText().length()>0))
            valido = true;
        return valido;
    }
    
    private boolean verificarSexo(){
        boolean valido = false;
        if(rbtn_SexoF.isSelected() || rbtn_SexoM.isSelected())
            valido = true;
        return valido;
    }
    
    private void anadirEmpleado(EmpleadoEmpleadoMod empEmp) throws SQLException, ConnectException{
        boolean nuevo = true;
        for(int i = 0; i < empleados.size(); i++){
            if(empEmp.getCodigo().equals(empleados.get(i).getCodigo())){
                nuevo = false;
                empleados.get(i).setNombreNuevo(empEmp.getNombreNuevo());
                empleados.get(i).setSexoNuevo(empEmp.getSexoNuevo());
                empleados.get(i).setDireccionNuevo(empEmp.getDireccionNuevo());
                empleados.get(i).setTelefonoNuevo((empEmp.getTelefonoNuevo()));
            }
        }
        if(nuevo)
            empleados.add(empEmp);
    }
    
    private TableModel crearModeloTablaEmpleado() {
        return new ModeloTabla<EmpleadoEmpleadoMod>() {
            @Override
            public Object getValueAt(EmpleadoEmpleadoMod t, int columna) {
                switch(columna){
                    case 0:
                        return t.getCodigo();
                    case 1:
                        return t.getNombreNuevo();
                    case 2:
                        return t.getSexoNuevo();
                    case 3:
                        return t.getTelefonoNuevo();
                    case 4:
                        return t.getDireccionNuevo();
                    case 5:
                        return t.getNombreAnterior();
                    case 6:
                        return t.getSexoAnterior();
                    case 7:
                        return t.getTelefonoAnterior();
                    case 8:
                        return t.getDireccionAnterior();
                }
                return null;
            }

            @Override
            public String getColumnName(int columna) {
                switch(columna){
                    case 0:
                        return "Código";
                    case 1:
                        return "Nombre";
                    case 2:
                        return "Sexo";
                    case 3:
                        return "Telefono";
                    case 4:
                        return "Dirección";
                    case 5:
                        return "Nombre anterior";
                    case 6:
                        return "Sexo anterior";
                    case 7:
                        return "Teléfono anterior";
                    case 8:
                        return "Dirección anterior";
                }
                return null;
            }

            @Override
            public int getColumnCount() {
                return 9;
            }
        };
    }
    
    private void setTabla(){
        jtEmpleado.setModel(crearModeloTablaEmpleado());
        try {
            DatosPaginacion <EmpleadoEmpleadoMod> datosPaginacionEmpleado = crearDatosPaginacionEmpleado();
            paginadorEmpleado = new PaginadorTabla(jtEmpleado, datosPaginacionEmpleado, new int[]{5,10,20,25,50,75,100},10);
            paginadorEmpleado.crearListadoFilasPermitidas(panelPaginacionEmpleado);
            filasPermitidasEmpleado = paginadorEmpleado.getComboboxFilasPermitidas();
            filasPermitidasEmpleado.addActionListener(this);
            filasPermitidasEmpleado.setSelectedItem(Integer.parseInt("10"));
        } catch (SQLException ex) {
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConnectException ex) {
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private DatosPaginacion <EmpleadoEmpleadoMod> crearDatosPaginacionEmpleado() throws SQLException, ConnectException{
        List <EmpleadoEmpleadoMod> lista = empleados;
        return new DatosPaginacion<EmpleadoEmpleadoMod>(){
            @Override
            public int getTotalRowCount() {
                return lista.size();
            }

            @Override
            public List<EmpleadoEmpleadoMod> getRows(int startIndex, int endIndex) {
                return lista.subList(startIndex, endIndex);
            }
        };
    }
    
    private int crear(EmpleadoMod empleado){
        int emp = 0;
        try {
            emp = empCtrl.Crear(empleado);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR " + e.toString() + " AL INSERTAR EL REGISTRO. COMUNIQUESE CON TI", "Inserción de Datos", JOptionPane.ERROR_MESSAGE);
        }
        return emp;
    }
    
    private int actualizar(EmpleadoMod empleado){
        int emp = 0;
        try {
            
            emp = empCtrl.Actualizar(empleado);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR " + e.toString() + " AL ACTUALIZAR EL REGISTRO. COMUNIQUESE CON TI", "Actualización de Datos", JOptionPane.ERROR_MESSAGE);
        }
        return emp;
    }
    
    
    private void removerFila(){
        try {
            empleadoAux = empCtrl.buscarFilaPorCodigo(jtEmpleado.getValueAt(jtEmpleado.getSelectedRow(), 0).toString());
            for(int i = 0; i < empleados.size(); i++){
                if(empleados.get(i).getCodigo().equals(empleadoAux.getCodigo()))
                    empleados.remove(i);
            }
            setTabla();
            reset();
        } catch (SQLException ex) {
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConnectException ex) {
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void cargarArchivo(File archivo){
        FileReader fr = null;
        BufferedReader br = null;
        try{
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            String linea;
            while((linea = br.readLine()) != null){
                String arregloLinea[] = linea.split(",");
                if(arregloLinea.length >= 5){
                    EmpleadoEmpleadoMod emp = new EmpleadoEmpleadoMod();
                    emp.setCodigo(arregloLinea[0]);
                    emp.setNombreNuevo(arregloLinea[1]);
                    emp.setSexoNuevo(arregloLinea[2]);
                    emp.setTelefonoNuevo(arregloLinea[3]);
                    emp.setDireccionNuevo(arregloLinea[4]);
                    if(!(util.verificarNumero(emp.getCodigo()))){
                        JOptionPane.showMessageDialog(this, "CÓDIGO DE EMPLEADO NO VÁLIDO. VERIFICAR EL CÓDIGO DEL EMPLEADO: " + emp.getNombreNuevo(), "Ingreso de datos con archivo CSV", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    EmpleadoMod empAux = empCtrl.buscarFilaPorCodigo(emp.getCodigo());
                    if(empAux != null){
                        emp.setNombreAnterior(empAux.getNombre());
                        emp.setSexoAnterior(empAux.getSexo());
                        emp.setTelefonoAnterior(empAux.getTelefono());
                        emp.setDireccionAnterior(empAux.getDireccion());
                    }else{
                        emp.setNombreAnterior("N/A");
                        emp.setSexoAnterior("N/A");
                        emp.setTelefonoAnterior("N/A");
                        emp.setDireccionAnterior("N/A");
                    }
                    anadirEmpleado(emp);
                }
            }
            setTabla();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            try{
                if(fr != null){
                    fr.close();
                }
            }catch (Exception ex){
                ex.printStackTrace();
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

        grbtn_Sexo = new javax.swing.ButtonGroup();
        contEmpleado = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        panelFormularioEmpleado = new javax.swing.JPanel();
        lblFormulario = new javax.swing.JLabel();
        lblTelefono = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lblCodigo = new javax.swing.JLabel();
        rbtn_SexoM = new javax.swing.JRadioButton();
        lblSexo = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        lblDireccion = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        rbtn_SexoF = new javax.swing.JRadioButton();
        txtTelefono = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JPanel();
        lbl_btnBuscar = new javax.swing.JLabel();
        btnArchivo = new javax.swing.JPanel();
        lbl_btnArchivo = new javax.swing.JLabel();
        lblAdvertencia = new javax.swing.JLabel();
        panelTablaEmpleado = new javax.swing.JPanel();
        lblTituloTabla = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jtEmpleado = new javax.swing.JTable();
        panelPaginacionEmpleado = new javax.swing.JPanel();
        txtSeleccionEmpleado = new javax.swing.JTextField();
        btnConfirmarIndividual = new javax.swing.JPanel();
        lbl_btnConfirmarIndividual = new javax.swing.JLabel();
        btnConfirmarTodos = new javax.swing.JPanel();
        lbl_btnConfirmarTodos = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        contEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        contEmpleado.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitulo.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Añadir o Modificar Empleado");
        contEmpleado.add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, -1));

        panelFormularioEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        panelFormularioEmpleado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelFormularioEmpleado.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblFormulario.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblFormulario.setForeground(new java.awt.Color(31, 78, 121));
        lblFormulario.setText("FORMULARIO EMPLEADO");
        panelFormularioEmpleado.add(lblFormulario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        lblTelefono.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblTelefono.setText("Teléfono");
        panelFormularioEmpleado.add(lblTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 30, -1, -1));

        txtCodigo.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtCodigo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelFormularioEmpleado.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 100, 35));

        lblCodigo.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblCodigo.setText("Código");
        panelFormularioEmpleado.add(lblCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, 20));

        rbtn_SexoM.setBackground(new java.awt.Color(255, 255, 255));
        grbtn_Sexo.add(rbtn_SexoM);
        rbtn_SexoM.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        rbtn_SexoM.setText("M");
        rbtn_SexoM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_SexoMActionPerformed(evt);
            }
        });
        panelFormularioEmpleado.add(rbtn_SexoM, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 55, -1, -1));

        lblSexo.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblSexo.setText("Sexo");
        panelFormularioEmpleado.add(lblSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 30, -1, 20));

        txtDireccion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtDireccion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelFormularioEmpleado.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 240, 35));

        lblDireccion.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblDireccion.setText("Dirección");
        panelFormularioEmpleado.add(lblDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, 20));

        lblNombre.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblNombre.setText("Nombre");
        panelFormularioEmpleado.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, -1, 20));

        rbtn_SexoF.setBackground(new java.awt.Color(255, 255, 255));
        grbtn_Sexo.add(rbtn_SexoF);
        rbtn_SexoF.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        rbtn_SexoF.setText("F");
        rbtn_SexoF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_SexoFActionPerformed(evt);
            }
        });
        panelFormularioEmpleado.add(rbtn_SexoF, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 55, -1, -1));

        txtTelefono.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtTelefono.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelFormularioEmpleado.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 50, 207, 35));

        txtNombre.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtNombre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelFormularioEmpleado.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 450, 35));

        btnBuscar.setBackground(new java.awt.Color(235, 235, 51));
        btnBuscar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnBuscar.setLayout(new java.awt.BorderLayout());

        lbl_btnBuscar.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_btnBuscar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_btnBuscar.setText("Buscar");
        lbl_btnBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_btnBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btnBuscarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_btnBuscarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_btnBuscarMouseExited(evt);
            }
        });
        btnBuscar.add(lbl_btnBuscar, java.awt.BorderLayout.CENTER);

        panelFormularioEmpleado.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 120, 90, 35));

        btnArchivo.setBackground(new java.awt.Color(235, 235, 51));
        btnArchivo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnArchivo.setLayout(new java.awt.BorderLayout());

        lbl_btnArchivo.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_btnArchivo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_btnArchivo.setText("Subir Archivo");
        lbl_btnArchivo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_btnArchivo.setPreferredSize(new java.awt.Dimension(82, 35));
        lbl_btnArchivo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btnArchivoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_btnArchivoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_btnArchivoMouseExited(evt);
            }
        });
        btnArchivo.add(lbl_btnArchivo, java.awt.BorderLayout.CENTER);

        panelFormularioEmpleado.add(btnArchivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 120, 110, 35));

        lblAdvertencia.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblAdvertencia.setForeground(new java.awt.Color(255, 0, 0));
        lblAdvertencia.setText("Verificar que los datos estén ordenados: Código, Nombre, Sexo, Teléfono y Dirección");
        panelFormularioEmpleado.add(lblAdvertencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 130, -1, -1));

        contEmpleado.add(panelFormularioEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 1080, 170));

        panelTablaEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        panelTablaEmpleado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelTablaEmpleado.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTituloTabla.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblTituloTabla.setForeground(new java.awt.Color(31, 78, 121));
        lblTituloTabla.setText("TABLA DE EMPLEADOS A ACTUALIZAR");
        panelTablaEmpleado.add(lblTituloTabla, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jtEmpleado.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jtEmpleado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "Sexo", "Teléfono", "Dirección", "Nombre Anterior", "Sexo Anterior", "Teléfono Anterior", "Dirección Anterior"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane6.setViewportView(jtEmpleado);

        panelTablaEmpleado.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 1040, 190));

        panelPaginacionEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        panelTablaEmpleado.add(panelPaginacionEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 1040, 30));

        txtSeleccionEmpleado.setEditable(false);
        txtSeleccionEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        txtSeleccionEmpleado.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtSeleccionEmpleado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtSeleccionEmpleado.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        panelTablaEmpleado.add(txtSeleccionEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, 270, 35));

        btnConfirmarIndividual.setBackground(new java.awt.Color(40, 235, 40));
        btnConfirmarIndividual.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnConfirmarIndividual.setLayout(new java.awt.BorderLayout());

        lbl_btnConfirmarIndividual.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_btnConfirmarIndividual.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_btnConfirmarIndividual.setText("Confirmar Selección");
        lbl_btnConfirmarIndividual.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_btnConfirmarIndividual.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lbl_btnConfirmarIndividual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btnConfirmarIndividualMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_btnConfirmarIndividualMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_btnConfirmarIndividualMouseExited(evt);
            }
        });
        btnConfirmarIndividual.add(lbl_btnConfirmarIndividual, java.awt.BorderLayout.CENTER);

        panelTablaEmpleado.add(btnConfirmarIndividual, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 290, 150, 35));

        btnConfirmarTodos.setBackground(new java.awt.Color(40, 235, 40));
        btnConfirmarTodos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnConfirmarTodos.setLayout(new java.awt.BorderLayout());

        lbl_btnConfirmarTodos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_btnConfirmarTodos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_btnConfirmarTodos.setText("Confirmar Todos");
        lbl_btnConfirmarTodos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_btnConfirmarTodos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btnConfirmarTodosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_btnConfirmarTodosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_btnConfirmarTodosMouseExited(evt);
            }
        });
        btnConfirmarTodos.add(lbl_btnConfirmarTodos, java.awt.BorderLayout.CENTER);

        panelTablaEmpleado.add(btnConfirmarTodos, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 290, 150, 35));

        contEmpleado.add(panelTablaEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 1080, 380));

        getContentPane().add(contEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rbtn_SexoMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_SexoMActionPerformed
        empleado.setSexoNuevo("M");
    }//GEN-LAST:event_rbtn_SexoMActionPerformed

    private void rbtn_SexoFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_SexoFActionPerformed
        empleado.setSexoNuevo("F");
    }//GEN-LAST:event_rbtn_SexoFActionPerformed

    private void lbl_btnBuscarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnBuscarMouseEntered
        btnBuscar.setBackground(util.colorCursorEntra(btnBuscar.getBackground()));
    }//GEN-LAST:event_lbl_btnBuscarMouseEntered

    private void lbl_btnBuscarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnBuscarMouseExited
        btnBuscar.setBackground(util.colorCursorSale(btnBuscar.getBackground()));
    }//GEN-LAST:event_lbl_btnBuscarMouseExited

    private void lbl_btnBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnBuscarMouseClicked
        if(verificarEmpleado()){
            try {
                anadirEmpleado(getEmpleado());
                setTabla();
            } catch (SQLException ex) {
                Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ConnectException ex) {
                Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else
            JOptionPane.showMessageDialog(this, "INFORMACIÓN DEL EMPLEADO INCOMPLETA O NO VÁLIDA", "Añadir o modificar empleado", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_lbl_btnBuscarMouseClicked

    private void lbl_btnConfirmarIndividualMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnConfirmarIndividualMouseEntered
        btnConfirmarIndividual.setBackground(util.colorCursorEntra(btnConfirmarIndividual.getBackground()));
    }//GEN-LAST:event_lbl_btnConfirmarIndividualMouseEntered

    private void lbl_btnConfirmarIndividualMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnConfirmarIndividualMouseExited
        btnConfirmarIndividual.setBackground(util.colorCursorSale(btnConfirmarIndividual.getBackground()));
    }//GEN-LAST:event_lbl_btnConfirmarIndividualMouseExited

    private void lbl_btnConfirmarIndividualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnConfirmarIndividualMouseClicked
        if (txtSeleccionEmpleado.getText().length() > 0){
            try {
                EmpleadoMod empleadoConfirmar = new EmpleadoMod();
                empleadoConfirmar.setNombre(jtEmpleado.getValueAt(jtEmpleado.getSelectedRow(), 1).toString());
                empleadoConfirmar.setSexo(jtEmpleado.getValueAt(jtEmpleado.getSelectedRow(), 2).toString());
                empleadoConfirmar.setTelefono(jtEmpleado.getValueAt(jtEmpleado.getSelectedRow(), 3).toString());
                empleadoConfirmar.setDireccion(jtEmpleado.getValueAt(jtEmpleado.getSelectedRow(), 4).toString());
                empleadoAux = empCtrl.buscarFilaPorCodigo(jtEmpleado.getValueAt(jtEmpleado.getSelectedRow(), 0).toString());
                int emp;
                if(empleadoAux != null){
                    empleadoConfirmar.setId(empleadoAux.getId());
                    empleadoConfirmar.setCodigo(empleadoAux.getCodigo());
                    empleadoConfirmar.setEstado(empleadoAux.getEstado());
                    empleadoConfirmar.setAptitud(empleadoAux.getAptitud());
                    emp = actualizar(empleadoConfirmar);
                    if (emp > 0){
                        JOptionPane.showMessageDialog(this, "REGISTRO ACTUALIZADO CON EXITO", "Actualización de Datos", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "ERROR AL ACTUALIZAR EL EMPLEADO. COMUNIQUESE CON TI", "Actualización de Datos", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }else{
                    empleadoConfirmar.setCodigo(jtEmpleado.getValueAt(jtEmpleado.getSelectedRow(), 0).toString());
                    empleadoConfirmar.setEstado("1");
                    empleadoConfirmar.setAptitud("APTO");
                    emp = crear(empleadoConfirmar);
                    if (emp > 0){
                        JOptionPane.showMessageDialog(this, "REGISTRO INGRESADO CON EXITO", "Inserción de Datos", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "ERROR AL INSERTAR EL EMPLEADO. COMUNIQUESE CON TI", "Inserción de Datos", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                removerFila();
            } catch (SQLException ex) {
                Logger.getLogger(ConsultaGeneral.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ConnectException ex) {
                Logger.getLogger(ConsultaGeneral.class.getName()).log(Level.SEVERE, null, ex);
            }
            btnConfirmarIndividual.setBackground(util.colorCursorSale(btnConfirmarIndividual.getBackground()));
        }else
        JOptionPane.showMessageDialog(this, "SELECCIONE UN EMPLEADO", "Selección de Empleado", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_lbl_btnConfirmarIndividualMouseClicked

    private void lbl_btnConfirmarTodosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnConfirmarTodosMouseEntered
        btnConfirmarTodos.setBackground(util.colorCursorEntra(btnConfirmarTodos.getBackground()));
    }//GEN-LAST:event_lbl_btnConfirmarTodosMouseEntered

    private void lbl_btnConfirmarTodosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnConfirmarTodosMouseExited
        btnConfirmarTodos.setBackground(util.colorCursorSale(btnConfirmarTodos.getBackground()));
    }//GEN-LAST:event_lbl_btnConfirmarTodosMouseExited

    private void lbl_btnConfirmarTodosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnConfirmarTodosMouseClicked
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Crear o modificar los empleados en la tabla?", "Confirmación", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if((jtEmpleado.getRowCount() > 0) && confirmacion == JOptionPane.YES_OPTION){
            while(jtEmpleado.getRowCount() > 0){
                jtEmpleado.setRowSelectionInterval(0, 0);
                EmpleadoMod empleadoConfirmar = new EmpleadoMod();
                int emp = 0;
                empleadoConfirmar.setCodigo(jtEmpleado.getValueAt(0, 0).toString());
                empleadoConfirmar.setNombre(jtEmpleado.getValueAt(0, 1).toString());
                empleadoConfirmar.setSexo(jtEmpleado.getValueAt(0, 2).toString());
                empleadoConfirmar.setTelefono(jtEmpleado.getValueAt(0, 3).toString());
                empleadoConfirmar.setDireccion(jtEmpleado.getValueAt(0, 4).toString());
                try {
                    empleadoAux = empCtrl.buscarFilaPorCodigo(empleadoConfirmar.getCodigo());
                    if(empleadoAux != null){
                        empleadoConfirmar.setId(empleadoAux.getId());
                        empleadoConfirmar.setCodigo(empleadoAux.getCodigo());
                        empleadoConfirmar.setEstado(empleadoAux.getEstado());
                        empleadoConfirmar.setAptitud(empleadoAux.getAptitud());
                        emp = actualizar(empleadoConfirmar);
                        if (emp == 0){
                            JOptionPane.showMessageDialog(this, "ERROR AL ACTUALIZAR EL EMPLEADO. COMUNIQUESE CON TI", "Actualización de Datos", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }else{
                        empleadoConfirmar.setCodigo(jtEmpleado.getValueAt(jtEmpleado.getSelectedRow(), 0).toString());
                        empleadoConfirmar.setEstado("1");
                        empleadoConfirmar.setAptitud("APTO");
                        emp = crear(empleadoConfirmar);
                        if (emp == 0){
                            JOptionPane.showMessageDialog(this, "ERROR AL INSERTAR EL EMPLEADO. COMUNIQUESE CON TI", "Inserción de Datos", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    removerFila();
                } catch (SQLException ex) {
                    Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ConnectException ex) {
                    Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            JOptionPane.showMessageDialog(this, "CREACIÓN/ACTUALIZACIÓN DE EMPLEADOS EXITOSA", "Crear/Modificar Empleados", JOptionPane.INFORMATION_MESSAGE);
        }else if(confirmacion == JOptionPane.YES_OPTION)
            JOptionPane.showMessageDialog(this, "LA TABLA ESTÁ VACÍA", "Crear/Modificar Empleados", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_lbl_btnConfirmarTodosMouseClicked

    private void lbl_btnArchivoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnArchivoMouseEntered
        lblAdvertencia.setVisible(true);
        btnArchivo.setBackground(util.colorCursorEntra(btnArchivo.getBackground()));
    }//GEN-LAST:event_lbl_btnArchivoMouseEntered

    private void lbl_btnArchivoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnArchivoMouseExited
        lblAdvertencia.setVisible(false);
        btnArchivo.setBackground(util.colorCursorSale(btnArchivo.getBackground()));
    }//GEN-LAST:event_lbl_btnArchivoMouseExited

    private void lbl_btnArchivoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnArchivoMouseClicked
        JFileChooser seleccionArchivo = new JFileChooser();
        FileNameExtensionFilter filtroArchivo = new FileNameExtensionFilter("Únicamente archivos CSV", "csv");
        seleccionArchivo.setFileFilter(filtroArchivo);
        int seleccion = seleccionArchivo.showOpenDialog(this);
        if(seleccion == JFileChooser.APPROVE_OPTION){
            File archivo = seleccionArchivo.getSelectedFile();
            cargarArchivo(archivo);
        }
    }//GEN-LAST:event_lbl_btnArchivoMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnArchivo;
    private javax.swing.JPanel btnBuscar;
    private javax.swing.JPanel btnConfirmarIndividual;
    private javax.swing.JPanel btnConfirmarTodos;
    private javax.swing.JPanel contEmpleado;
    private javax.swing.ButtonGroup grbtn_Sexo;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jtEmpleado;
    private javax.swing.JLabel lblAdvertencia;
    public javax.swing.JLabel lblCodigo;
    public javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblFormulario;
    public javax.swing.JLabel lblNombre;
    public javax.swing.JLabel lblSexo;
    public javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTituloTabla;
    private javax.swing.JLabel lbl_btnArchivo;
    private javax.swing.JLabel lbl_btnBuscar;
    private javax.swing.JLabel lbl_btnConfirmarIndividual;
    private javax.swing.JLabel lbl_btnConfirmarTodos;
    private javax.swing.JPanel panelFormularioEmpleado;
    private javax.swing.JPanel panelPaginacionEmpleado;
    private javax.swing.JPanel panelTablaEmpleado;
    public javax.swing.JRadioButton rbtn_SexoF;
    public javax.swing.JRadioButton rbtn_SexoM;
    public javax.swing.JTextField txtCodigo;
    public javax.swing.JTextField txtDireccion;
    public javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtSeleccionEmpleado;
    public javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        Object evt = e.getSource();
        if (evt.equals(filasPermitidasEmpleado))
            paginadorEmpleado.eventComboBox(filasPermitidasEmpleado);
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        Object evt = e.getSource();
        if (evt.equals(filasPermitidasEmpleado))
            paginadorEmpleado.actualizarBotonesPaginacion();
    }
}
