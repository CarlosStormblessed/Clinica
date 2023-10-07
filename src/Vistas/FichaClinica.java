package Vistas;

import javax.swing.JPanel;
import Utilitarios.Utilitarios;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.ButtonModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;
import Controladores.FichaClinicaCtrl;
import Controladores.EmpleadoCtrl;
import Controladores.PaginadorTabla;
import Controladores.UsuarioCtrl;
import Modelos.FichaClinicaMod;
import Modelos.EmpleadoMod;
import Modelos.UsuarioMod;
import Utilitarios.DatosPaginacion;
import Utilitarios.ModeloTabla;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.ConnectException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class FichaClinica extends javax.swing.JFrame implements ActionListener, TableModelListener{

    Utilitarios util = new Utilitarios();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime now = LocalDateTime.now();
    String contenidoActual, clinica_id = "1";
    public String nombreContenido = "FichaClinica", responsable;
    public JComboBox<Integer> filasPermitidasFicha, filasPermitidasEmpleado;
    private EmpleadoMod empleado = new EmpleadoMod();
    private FichaClinicaMod fichaClinica = new FichaClinicaMod();
    private UsuarioMod usuario = new UsuarioMod();
    private EmpleadoCtrl empleadoCtrl = new EmpleadoCtrl();
    private FichaClinicaCtrl fichaClinicaCtrl = new FichaClinicaCtrl();
    private UsuarioCtrl usuarioCtrl = new UsuarioCtrl();
    private PaginadorTabla <FichaClinica> paginadorFicha, paginadorEmpleado;
    public FichaClinica() throws SQLException, ConnectException {
        initComponents();
        contenidoActual = "Inicio";
        tpanel_Contenidos.setSelectedIndex(0);
        btn_Confirmar.setVisible(false);
        lbl_btn_Confirmar.setEnabled(false);
        txtHora.setEnabled(false);
        timeHora.set24hourMode(true);
        jtFichaClinica.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent Mouse_evt){
                JTable tabla = (JTable) Mouse_evt.getSource();
                Point punto = Mouse_evt.getPoint();
                int fila = tabla.rowAtPoint(punto);
                if (Mouse_evt.getClickCount() == 1){
                    lbl_SeleccionFichaClinica.setText(jtFichaClinica.getValueAt(jtFichaClinica.getSelectedRow(), 2).toString());
                }
            }
        });
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
        reset();
        resetUsuarios();
    }
    
    private void reset(){
        lblAG.setIcon(util.construirImagen("/Imagenes/AG_logo.png", lblAG.getWidth(), lblAG.getHeight()));
        resetFichaClinica();
        resetEmpleado();
        construirEtiquetas();
        panelPaginacionFicha.removeAll();
    }
    
    private void resetEmpleado(){
        txtPreempleoId.setText("");
        txtNombre.setText("");
        setSexo("");
        txtEdad.setText("");
        txtCodigo.setText("");
        txtArea.setText("");
        txtPuesto.setText("");
        empleado.setCodigo("");
        empleado.setDireccion("");
        empleado.setId("");
        empleado.setNombre("");
        empleado.setSexo("");
        empleado.setTelefono("");
        empleado.setEstado("");
        tpanel_Empleado.setSelectedIndex(1);
    }
    
    private void resetFichaClinica(){
        txtTemperatura.setText("");
        txtPulso.setText("");
        txtGlicemia.setText("");
        txtPeso.setText("");
        txtTalla.setSelectedIndex(0);
        txtSPO2.setText("");
        txtFR.setText("");
        txtIMC.setText("");
        txtMotivoConsulta.setText("");
        txtFecha.setText("");
        
        fichaClinica.setId("");
        fichaClinica.setEmpleado_id("");
        fichaClinica.setFecha("");
        fichaClinica.setFr("");
        fichaClinica.setGlicemia("");
        fichaClinica.setImc("");
        fichaClinica.setPa("");
        fichaClinica.setPeso("");
        fichaClinica.setPulso("");
        fichaClinica.setSpo2("");
        fichaClinica.setTalla("");
        fichaClinica.setTemperatura("");
    }
    
    private void resetUsuarios(){
        try {
            List<UsuarioMod> listaUsuarios = new ArrayList<UsuarioMod>();
            listaUsuarios = usuarioCtrl.seleccionarTodos();
            List<String> nombresUsuarios = new ArrayList<String>();
            for (int i = 0; i < listaUsuarios.size(); i++){
                combo_Autorizado.addItem(listaUsuarios.get(i).getId() + ". " + listaUsuarios.get(i).getNombre());
                combo_Realizado.addItem(listaUsuarios.get(i).getId() + ". " + listaUsuarios.get(i).getNombre());
                combo_Revisado.addItem(listaUsuarios.get(i).getId() + ". " + listaUsuarios.get(i).getNombre());
            }
        } catch (SQLException ex) {
            Logger.getLogger(FichaClinica.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConnectException ex) {
            Logger.getLogger(FichaClinica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void construirEtiquetas(){
        lblTitulos.setText("<html><center>Formato<br><b>FICHA MÉDICA PRE-EMPLEO</b><br>CÓDIGO12345</center></html>");
        lblSeguridad.setText("<html><center>Seguridad Industrial y Salud Ocupacional</center></html>");
        lblFechaMod.setText("<html><center>Fecha de<br>modificacion:<br>"+ util.convertirFechaGUI(now.format(dtf)) + "</center></html>");
        lbl_SeleccionFichaClinica.setText("");
        txtTemperatura.setText("°C");
        txtTemperatura.setForeground(Color.gray);
        txtGlicemia.setText("mg/dl");
        txtGlicemia.setForeground(Color.gray);
        txtPA.setText("mmHg");
        txtPA.setForeground(Color.gray);
        txtPulso.setText("LPM");
        txtPulso.setForeground(Color.gray);
        txtSPO2.setText("%");
        txtSPO2.setForeground(Color.gray);
        txtFR.setText("RPM");
        txtFR.setForeground(Color.gray);
        txtPeso.setText("lb");
        txtPeso.setForeground(Color.gray);
        txtIMC.setText("Kg/m^2");
        txtIMC.setForeground(Color.gray);
        txtFecha.setText(util.convertirFechaGUI(now.format(dtf)));
    }
    
    private boolean verificarSexo(){
        boolean valido = false;
        if (grbtn_Sexo.isSelected(rbtn_SexoM.getModel()) || (grbtn_Sexo.isSelected(rbtn_SexoF.getModel())))
            valido = true;
        return valido;
    }
    
    private boolean verificarEmpleado(){
        boolean valido = false;
        if ((txtFecha.getText().length()>0) && (txtNombre.getText().length()>0) && (util.verificarNumero(txtEdad.getText())) && (txtCodigo.getText().length()>0) && (txtArea.getText().length()>0) && (txtPuesto.getText().length()>0) && (verificarSexo()))
            valido = true;
        else valido = false;
        return valido;
    }
    
    private boolean verificarFichaClinica(){
        boolean valido = false;
        if ((util.verificarNumero(txtTemperatura.getText()) || (txtTemperatura.getText().length() == 0)) && ((util.verificarNumero(txtPulso.getText())) || (txtPulso.getText().length() == 0)) && ((util.verificarNumero(txtSPO2.getText())) || (txtSPO2.getText().length() == 0)) && ((util.verificarNumero(txtFR.getText())) || (txtFR.getText().length() == 0)) && ((util.verificarNumero(txtGlicemia.getText())) || (txtGlicemia.getText().length() == 0)) && ((util.verificarNumero(txtPeso.getText())) || (txtPeso.getText().length() == 0)) && (util.verificarNumero(txtIMC.getText()) || (txtIMC.getText().length() == 0)))
                valido = true;
        return valido;
    }
    
    public JPanel getContenido(){
        try{
            return cont_FichaClinica;
        }catch (Exception ex) {
            Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    private void setSexo (String sexo){
        switch (sexo){
            case "M":
                rbtn_SexoM.setSelected(true);
                break;
            case "F":
                rbtn_SexoF.setSelected(true);
                break;
            default:
                grbtn_Sexo.clearSelection();
        }
    }
    
    private void getSexo (){
        if (rbtn_SexoM.isSelected())
            empleado.setSexo("M");
        else if (rbtn_SexoF.isSelected())
            empleado.setSexo("F");
    }
    
    /**
     * La fichaClinica en pantalla se guardarán en una variable global "fichaClinica"
     */
    private void getFichaClinica(){
        fichaClinica.setClinica_id(clinica_id);
        fichaClinica.setEmpleado_id(txtCodigo.getText());
        fichaClinica.setFecha(util.convertirFechaSQL(txtFecha.getText()));
        fichaClinica.setHora(txtHora.getText());
        fichaClinica.setEdad(txtEdad.getText());
        fichaClinica.setArea(txtArea.getText());
        fichaClinica.setPuesto(txtPuesto.getText());
        fichaClinica.setTemperatura(txtTemperatura.getText());
        fichaClinica.setPulso(txtPulso.getText());
        fichaClinica.setSpo2(txtSPO2.getText());
        fichaClinica.setFr(txtFR.getText());
        fichaClinica.setPa(txtPA.getText());
        fichaClinica.setGlicemia(txtGlicemia.getText());
        fichaClinica.setPeso(txtPeso.getText());
        fichaClinica.setTalla(txtTalla.getSelectedItem().toString());
        fichaClinica.setImc(txtIMC.getText());
        fichaClinica.setMotivo(txtMotivoConsulta.getText());
        fichaClinica.setHistoriaActual(txtAreaHistoriaEnfermedadActual.getText());
        fichaClinica.setExamenHallazgos(txtAreaExamenFisico.getText());
        fichaClinica.setImpresionClinica(txtAreaImpresionClinica.getText());
        fichaClinica.setTratamiento(txtAreaTratamiento.getText());
        //Referencia, Traslado, Patología
        if(checkReferencia.isSelected())
            fichaClinica.setReferencia("1");
        else
            fichaClinica.setReferencia("0");
        if (checkTraslado.isSelected())
            fichaClinica.setTraslado("1");
        else
            fichaClinica.setTraslado("0");
        if (checkPatologia.isSelected())
            fichaClinica.setPatologia("1");
        else
            fichaClinica.setPatologia("0");
        fichaClinica.setObservaciones(txtAreaObservaciones.getText());
        fichaClinica.setResponsable(responsable);
        //Obtener valores de combobox
        String [] partesCombobox = combo_Realizado.getSelectedItem().toString().split(". ");
        fichaClinica.setRealizado(partesCombobox[0]);
        partesCombobox = combo_Revisado.getSelectedItem().toString().split(". ");
        fichaClinica.setRevisado(partesCombobox[0]);
        partesCombobox = combo_Autorizado.getSelectedItem().toString().split(". ");
        fichaClinica.setAutorizado(partesCombobox[0]);
        fichaClinica.setEstado("1");
    }
    
    /**
     * Los valores dentro de los fichaClinica guardados en la variable global "fichaClinica" se mostrarán en pantalla
     */
    private void setFichaClinica(){
        txtFecha.setText(util.convertirFechaGUI(fichaClinica.getFecha()));
        txtHora.setText(fichaClinica.getHora());
        txtEdad.setText(fichaClinica.getEdad());
        txtArea.setText(fichaClinica.getArea());
        txtPuesto.setText(fichaClinica.getPuesto());
        txtTemperatura.setText(fichaClinica.getTemperatura());
        txtTemperatura.setForeground(Color.black);
        txtPulso.setText(fichaClinica.getPulso());
        txtPulso.setForeground(Color.black);
        txtSPO2.setText(fichaClinica.getSpo2());
        txtSPO2.setForeground(Color.black);
        txtFR.setText(fichaClinica.getFr());
        txtFR.setForeground(Color.black);
        txtPA.setText(fichaClinica.getPa());
        txtPA.setForeground(Color.black);
        txtGlicemia.setText(fichaClinica.getGlicemia());
        txtGlicemia.setForeground(Color.black);
        txtPeso.setText(fichaClinica.getPeso());
        txtPeso.setForeground(Color.black);
        //set Talla
        switch(fichaClinica.getTalla()){
            case "S":
                txtTalla.setSelectedIndex(0);
                break;
            case "M":
                txtTalla.setSelectedIndex(1);
                break;
            case "L":
                txtTalla.setSelectedIndex(2);
                break;
            case "XL":
                txtTalla.setSelectedIndex(3);
                break;
            default:
                break;
        }
        txtIMC.setText(fichaClinica.getImc());
        txtIMC.setForeground(Color.black);
        txtMotivoConsulta.setText(fichaClinica.getMotivo());
        txtAreaHistoriaEnfermedadActual.setText(fichaClinica.getHistoriaActual());
        txtAreaExamenFisico.setText(fichaClinica.getExamenHallazgos());
        txtAreaImpresionClinica.setText(fichaClinica.getImpresionClinica());
        txtAreaTratamiento.setText(fichaClinica.getTratamiento());
        //Set referencia, traslado, patología
        if(fichaClinica.getReferencia() == "1")
            checkReferencia.setSelected(true);
        else
            checkReferencia.setSelected(false);
        if(fichaClinica.getTraslado() == "1")
            checkTraslado.setSelected(true);
        else
            checkTraslado.setSelected(false);
        if(fichaClinica.getPatologia() == "1")
            checkPatologia.setSelected(true);
        else
            checkPatologia.setSelected(false);
        txtAreaObservaciones.setText(fichaClinica.getObservaciones());
        responsable = fichaClinica.getResponsable();
        
        setCombobox();
    }
    
    private void setCombobox(){
        String[] elementos = new String[combo_Autorizado.getModel().getSize()];
        for (int i = 0; i < elementos.length; i++){
            String[] partesAutorizado;
            elementos[i] = combo_Autorizado.getItemAt(i);
            partesAutorizado = elementos[i].split(". ");
            if (fichaClinica.getAutorizado().equals(partesAutorizado[0]))
                combo_Autorizado.setSelectedIndex(i);
        }
        elementos = new String[combo_Realizado.getModel().getSize()];
        for (int i = 0; i < elementos.length; i++){
            String[] partesRealizado;
            elementos[i] = combo_Realizado.getItemAt(i);
            partesRealizado = elementos[i].split(". ");
            if (fichaClinica.getRealizado().equals(partesRealizado[0]))
                combo_Realizado.setSelectedIndex(i);
        }
        elementos = new String[combo_Revisado.getModel().getSize()];
        for (int i = 0; i < elementos.length; i++){
            String[] partesRevisado;
            elementos[i] = combo_Revisado.getItemAt(i);
            partesRevisado = elementos[i].split(". ");
            if (fichaClinica.getRevisado().equals(partesRevisado[0]))
                combo_Revisado.setSelectedIndex(i);
        }
    }
    
    /**
     * Los valores de empleado en pantalla se guardarán en una variable global "empleado"
     */
    private void getEmpleado(){
        empleado.setCodigo(txtCodigo.getText());
        empleado.setNombre(txtNombre.getText());
        getSexo();
    }
    
    /**
     * Los valores dentro del empleado guardados en la variable global "empleado" se mostrarán en pantalla
     */
    private void setEmpleado(){
        txtNombre.setText(empleado.getNombre());
        txtCodigo.setText(empleado.getCodigo());
        setSexo(empleado.getSexo());
    }
    
    //Todo lo relacionado a la Tabla
    private TableModel crearModeloTablaFicha() {
        return new ModeloTabla<FichaClinicaMod>() {
            @Override
            public Object getValueAt(FichaClinicaMod t, int columna) {
                switch(columna){
                    case 0:
                        return t.getId();
                    case 1:
                        return t.getFecha();
                    case 2:
                        return t.getEmpleado_id();
                    case 3:
                        return t.getMotivo();
                    case 4:
                        return t.getExamenHallazgos();
                }
                return null;
            }

            @Override
            public String getColumnName(int columna) {
                switch(columna){
                    case 0:
                        return "Id";
                    case 1:
                        return "Fecha";
                    case 2:
                        return "Nombre";
                    case 3:
                        return "Motivo";
                    case 4:
                        return "Hallazgos";
                }
                return null;
            }

            @Override
            public int getColumnCount() {
                return 5;
            }
        };
    }
    
    private TableModel crearModeloTablaEmpleado() {
        return new ModeloTabla<EmpleadoMod>() {
            @Override
            public Object getValueAt(EmpleadoMod t, int columna) {
                switch(columna){
                    case 0:
                        return t.getId();
                    case 1:
                        return t.getNombre();
                    case 2:
                        return t.getSexo();
                    case 3:
                        return t.getTelefono();
                }
                return null;
            }

            @Override
            public String getColumnName(int columna) {
                switch(columna){
                    case 0:
                        return "Id";
                    case 1:
                        return "Nombre";
                    case 2:
                        return "Sexo";
                    case 3:
                        return "Telefono";
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
        /*
        DefaultTableModel modelo = new DefaultTableModel();
        int cantidadColumnas = jtPreempleo.getColumnCount();

        String[] campos = new String[cantidadColumnas];
        for (int i = 0; i < cantidadColumnas; i++)
        {
            campos[i] = jtPreempleo.getColumnName(i);
        }
        modelo = util.consultaMixta("SELECT PREEMP_ID, PREEMP_FECHA, PREEMP_NOMBRE, PREEMP_SEXO, PREEMP_IDENTIFICACION, PREEMP_NIVELACADEMICO, PREEMP_PUESTOAPLICA FROM empleado WHERE PREEMP_ESTADO = 1", campos);
        jtPreempleo.setModel(modelo);
        String[] idCombobox = new String[modelo.getRowCount()];
        for (int i = 0; i < modelo.getRowCount(); i++){
            idCombobox[i] = String.valueOf(modelo.getValueAt(i, 0));
        }
        setCombobox(idCombobox);
        */
        jtFichaClinica.setModel(crearModeloTablaFicha());
        DatosPaginacion<FichaClinicaMod> datosPaginacionFicha;
        try {
            datosPaginacionFicha = crearDatosPaginacionFicha();
            paginadorFicha = new PaginadorTabla(jtFichaClinica, datosPaginacionFicha, new int[]{5,10,20,25,50,75,100}, 10);
            paginadorFicha.crearListadoFilasPermitidas(panelPaginacionFicha);
            filasPermitidasFicha = paginadorFicha.getComboboxFilasPermitidas();
            filasPermitidasFicha.addActionListener(this);
            jtFichaClinica.getModel().addTableModelListener(this);
            filasPermitidasFicha.setSelectedItem(Integer.parseInt("10"));
            jtEmpleado.setModel(crearModeloTablaEmpleado());
            DatosPaginacion<EmpleadoMod> datosPaginacionEmpleado = crearDatosPaginacionEmpleado();
            paginadorEmpleado = new PaginadorTabla(jtEmpleado, datosPaginacionEmpleado, new int[]{5,10,20,25,50,75,100},10);
            paginadorEmpleado.crearListadoFilasPermitidas(panelPaginacionEmpleado);
            filasPermitidasEmpleado = paginadorEmpleado.getComboboxFilasPermitidas();
            filasPermitidasEmpleado.addActionListener(this);
            filasPermitidasEmpleado.setSelectedItem(Integer.parseInt("10"));
        } catch (SQLException ex) {
            Logger.getLogger(FichaClinica.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConnectException ex) {
            Logger.getLogger(FichaClinica.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private DatosPaginacion <FichaClinicaMod> crearDatosPaginacionFicha() throws SQLException, ConnectException{
        List <FichaClinicaMod> lista = fichaClinicaCtrl.seleccionarTodos();
        //Reemplazar el id del empleado por el nombre del empleado
        EmpleadoMod emp = new EmpleadoMod();
        FichaClinicaMod ficha = new FichaClinicaMod();
        for (int i = 0; i < lista.size(); i++){
            ficha = lista.get(i);
            emp = empleadoCtrl.buscarFila(ficha.getEmpleado_id());
            ficha.setEmpleado_id(emp.getNombre());
            lista.set(i, ficha);
        }
        return new DatosPaginacion<FichaClinicaMod>(){
            @Override
            public int getTotalRowCount() {
                return lista.size();
            }

            @Override
            public List<FichaClinicaMod> getRows(int startIndex, int endIndex) {
                return lista.subList(startIndex, endIndex);
            }
        };
    }
    
    private DatosPaginacion <EmpleadoMod> crearDatosPaginacionEmpleado() throws SQLException, ConnectException{
        List <EmpleadoMod> lista = empleadoCtrl.seleccionarTodos();
        //Reemplazar el id del empleado por el nombre del empleado
        
        return new DatosPaginacion<EmpleadoMod>(){
            @Override
            public int getTotalRowCount() {
                return lista.size();
            }

            @Override
            public List<EmpleadoMod> getRows(int startIndex, int endIndex) {
                return lista.subList(startIndex, endIndex);
            }
        };
    }
    
    private void buscarEmpleadoFichaClinica(String valorBuscar) throws SQLException, ConnectException{
        fichaClinica = fichaClinicaCtrl.buscarFila(valorBuscar);
        setFichaClinica();
        empleado = empleadoCtrl.buscarFila(fichaClinica.getEmpleado_id());
        setEmpleado();
    }
    
    private void crear() throws SQLException{
        try {
            int res1 = 0;
            res1 = fichaClinicaCtrl.Crear(fichaClinica);
            if (res1 > 0) {
                JOptionPane.showMessageDialog(this, "REGISTRO INGRESADO CON EXITO", "Inserción de Datos", JOptionPane.INFORMATION_MESSAGE);
                reset();
            } else {
                JOptionPane.showMessageDialog(this, "ERROR AL INSERTAR FICHA CLÍNICA. COMUNIQUESE CON TI", "Inserción de Datos", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR " + e.toString() + " AL INSERTAR EL REGISTRO. COMUNIQUESE CON TI", "Inserción de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void actualizar() throws SQLException{
        try {
            int res1 = 0;
            res1 = fichaClinicaCtrl.Actualizar(fichaClinica);
            if (res1 > 0) {
                JOptionPane.showMessageDialog(this, "REGISTRO ACTUALIZADO CON EXITO", "Inserción de Datos", JOptionPane.INFORMATION_MESSAGE);
                reset();
                tpanel_Contenidos.setSelectedIndex(2);
            } else {
                JOptionPane.showMessageDialog(this, "ERROR AL ACTUALIZAR FICHA CLÍNICA. COMUNIQUESE CON TI", "Inserción de Datos", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR " + e.toString() + " AL ACTUALIZAR EL REGISTRO. COMUNIQUESE CON TI", "Inserción de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminar() throws SQLException{
        try {
            int res1 = 0;
            res1 = fichaClinicaCtrl.Eliminar(fichaClinica);
            if (res1 > 0) {
                JOptionPane.showMessageDialog(this, "REGISTRO ELIMINADO CON EXITO", "Inserción de Datos", JOptionPane.INFORMATION_MESSAGE);
                reset();
                tpanel_Contenidos.setSelectedIndex(2);
            } else {
                JOptionPane.showMessageDialog(this, "ERROR AL ELIMINAR FICHA CLÍNICA. COMUNIQUESE CON TI", "Inserción de Datos", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR " + e.toString() + " AL ELIMINAR EL REGISTRO. COMUNIQUESE CON TI", "Inserción de Datos", JOptionPane.ERROR_MESSAGE);
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
        grbtn_empresa1 = new javax.swing.ButtonGroup();
        grbtn_empresa2 = new javax.swing.ButtonGroup();
        grbtn_empresa3 = new javax.swing.ButtonGroup();
        timeHora = new com.raven.swing.TimePicker();
        cont_FichaClinica = new javax.swing.JPanel();
        panelBotonesCRUD = new javax.swing.JPanel();
        btn_Crear = new javax.swing.JPanel();
        lbl_btnCrear = new javax.swing.JLabel();
        btn_Actualizar = new javax.swing.JPanel();
        lbl_btnActualizar = new javax.swing.JLabel();
        btn_Eliminar = new javax.swing.JPanel();
        lbl_BtnEliminar = new javax.swing.JLabel();
        lblTituloPrincipal = new javax.swing.JLabel();
        tablaTitulos = new javax.swing.JPanel();
        panelAG = new javax.swing.JPanel();
        lblAG = new javax.swing.JLabel();
        panelTitulos = new javax.swing.JPanel();
        lblTitulos = new javax.swing.JLabel();
        panelEdicion = new javax.swing.JPanel();
        txtPreempleoId = new javax.swing.JLabel();
        panelSeguridad = new javax.swing.JPanel();
        lblSeguridad = new javax.swing.JLabel();
        panelFecha = new javax.swing.JPanel();
        lblFechaMod = new javax.swing.JLabel();
        btn_Confirmar = new javax.swing.JPanel();
        lbl_btn_Confirmar = new javax.swing.JLabel();
        tpanel_Contenidos = new javax.swing.JTabbedPane();
        panelInicio = new javax.swing.JPanel();
        lbl_InicioInicio = new javax.swing.JLabel();
        panelFormulario = new javax.swing.JPanel();
        panelDatosMedicos = new javax.swing.JPanel();
        lblDatosMedicos = new javax.swing.JLabel();
        lblTemperatura = new javax.swing.JLabel();
        txtTemperatura = new javax.swing.JTextField();
        lblPulso = new javax.swing.JLabel();
        txtPulso = new javax.swing.JTextField();
        lblSPO2 = new javax.swing.JLabel();
        txtSPO2 = new javax.swing.JTextField();
        lblFR = new javax.swing.JLabel();
        txtFR = new javax.swing.JTextField();
        lblGlicemia = new javax.swing.JLabel();
        txtGlicemia = new javax.swing.JTextField();
        lblPeso = new javax.swing.JLabel();
        txtPeso = new javax.swing.JTextField();
        lblTalla = new javax.swing.JLabel();
        txtTalla = new javax.swing.JComboBox<>();
        lblIMC = new javax.swing.JLabel();
        txtIMC = new javax.swing.JTextField();
        lblMotivoConsulta = new javax.swing.JLabel();
        txtMotivoConsulta = new javax.swing.JTextField();
        lblPA = new javax.swing.JLabel();
        txtPA = new javax.swing.JTextField();
        panelMenu2 = new javax.swing.JPanel();
        lblAntecedentesLaborales = new javax.swing.JLabel();
        lblHistoriaEnfermedadActual = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaHistoriaEnfermedadActual = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtAreaExamenFisico = new javax.swing.JTextArea();
        lblExamenFisico = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtAreaImpresionClinica = new javax.swing.JTextArea();
        lblImpresionClinica = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtAreaTratamiento = new javax.swing.JTextArea();
        lblHistoriaEnfermedadActual1 = new javax.swing.JLabel();
        checkReferencia = new javax.swing.JCheckBox();
        checkTraslado = new javax.swing.JCheckBox();
        checkPatologia = new javax.swing.JCheckBox();
        lblObservaciones = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        txtAreaObservaciones = new javax.swing.JTextArea();
        lbl_Realizado = new javax.swing.JLabel();
        combo_Realizado = new javax.swing.JComboBox<>();
        lbl_Revisado = new javax.swing.JLabel();
        combo_Revisado = new javax.swing.JComboBox<>();
        lbl_Autorizado = new javax.swing.JLabel();
        combo_Autorizado = new javax.swing.JComboBox<>();
        panelCorrector = new javax.swing.JPanel();
        tpanel_Empleado = new javax.swing.JTabbedPane();
        panelMenu1 = new javax.swing.JPanel();
        txtNombre = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        lblClinica = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        lblSexo = new javax.swing.JLabel();
        lblEdad = new javax.swing.JLabel();
        txtEdad = new javax.swing.JTextField();
        lblHora = new javax.swing.JLabel();
        lblCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lblArea = new javax.swing.JLabel();
        txtArea = new javax.swing.JTextField();
        lblPuesto1 = new javax.swing.JLabel();
        txtPuesto = new javax.swing.JTextField();
        rbtn_SexoM = new javax.swing.JRadioButton();
        rbtn_SexoF = new javax.swing.JRadioButton();
        btn_OtroEmpleado = new javax.swing.JPanel();
        lbl_btnOtroEmpleado = new javax.swing.JLabel();
        txtHora = new javax.swing.JTextField();
        panelComboboxEmpleado = new javax.swing.JPanel();
        lblInstruccionComboboxEmpleado = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jtEmpleado = new javax.swing.JTable();
        txtSeleccionEmpleado = new javax.swing.JTextField();
        panelPaginacionEmpleado = new javax.swing.JPanel();
        btn_SeleccionEmpleado = new javax.swing.JPanel();
        lbl_btnSeleccionEmpleado = new javax.swing.JLabel();
        panelCombobox = new javax.swing.JPanel();
        lblInstruccion = new javax.swing.JLabel();
        lblTituloCombobox = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtFichaClinica = new javax.swing.JTable();
        btn_Ingresar = new javax.swing.JPanel();
        lbl_btnIngresar = new javax.swing.JLabel();
        panelPaginacionFicha = new javax.swing.JPanel();
        lbl_SeleccionFichaClinica = new javax.swing.JLabel();
        lbl_TituloSeleccion = new javax.swing.JLabel();

        timeHora.setDisplayText(txtHora);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        cont_FichaClinica.setBackground(new java.awt.Color(255, 255, 255));
        cont_FichaClinica.setPreferredSize(new java.awt.Dimension(1080, 600));
        cont_FichaClinica.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelBotonesCRUD.setBackground(new java.awt.Color(255, 255, 255));
        panelBotonesCRUD.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_Crear.setBackground(new java.awt.Color(40, 235, 40));
        btn_Crear.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        lbl_btnCrear.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        lbl_btnCrear.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_btnCrear.setText("Crear");
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
            .addComponent(lbl_btnCrear, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
        );
        btn_CrearLayout.setVerticalGroup(
            btn_CrearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_btnCrear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelBotonesCRUD.add(btn_Crear, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 90, 35));

        btn_Actualizar.setBackground(new java.awt.Color(92, 92, 235));
        btn_Actualizar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        lbl_btnActualizar.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        lbl_btnActualizar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_btnActualizar.setText("Actualizar");
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
            .addComponent(lbl_btnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
        );
        btn_ActualizarLayout.setVerticalGroup(
            btn_ActualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_btnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
        );

        panelBotonesCRUD.add(btn_Actualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 90, 35));

        btn_Eliminar.setBackground(new java.awt.Color(235, 91, 91));
        btn_Eliminar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        lbl_BtnEliminar.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        lbl_BtnEliminar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_BtnEliminar.setText("Eliminar");
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
            .addComponent(lbl_BtnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
        );
        btn_EliminarLayout.setVerticalGroup(
            btn_EliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_BtnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
        );

        panelBotonesCRUD.add(btn_Eliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, -1, 35));

        cont_FichaClinica.add(panelBotonesCRUD, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 0, 270, 120));

        lblTituloPrincipal.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        lblTituloPrincipal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTituloPrincipal.setText("Ficha Clínica");
        cont_FichaClinica.add(lblTituloPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, -1));

        tablaTitulos.setBackground(new java.awt.Color(255, 255, 255));
        tablaTitulos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelAG.setBackground(new java.awt.Color(255, 255, 255));
        panelAG.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout panelAGLayout = new javax.swing.GroupLayout(panelAG);
        panelAG.setLayout(panelAGLayout);
        panelAGLayout.setHorizontalGroup(
            panelAGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAG, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
        );
        panelAGLayout.setVerticalGroup(
            panelAGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAGLayout.createSequentialGroup()
                .addComponent(lblAG, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        tablaTitulos.add(panelAG, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 0, 100, 45));

        panelTitulos.setBackground(new java.awt.Color(255, 255, 255));
        panelTitulos.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(0, 0, 0)));

        lblTitulos.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        lblTitulos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout panelTitulosLayout = new javax.swing.GroupLayout(panelTitulos);
        panelTitulos.setLayout(panelTitulosLayout);
        panelTitulosLayout.setHorizontalGroup(
            panelTitulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTitulosLayout.createSequentialGroup()
                .addComponent(lblTitulos, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelTitulosLayout.setVerticalGroup(
            panelTitulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTitulosLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblTitulos, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tablaTitulos.add(panelTitulos, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 0, 400, 45));

        panelEdicion.setBackground(new java.awt.Color(255, 255, 255));
        panelEdicion.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout panelEdicionLayout = new javax.swing.GroupLayout(panelEdicion);
        panelEdicion.setLayout(panelEdicionLayout);
        panelEdicionLayout.setHorizontalGroup(
            panelEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEdicionLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(txtPreempleoId, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelEdicionLayout.setVerticalGroup(
            panelEdicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEdicionLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(txtPreempleoId, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tablaTitulos.add(panelEdicion, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 45, 100, 45));

        panelSeguridad.setBackground(new java.awt.Color(255, 255, 255));
        panelSeguridad.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));

        lblSeguridad.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        lblSeguridad.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout panelSeguridadLayout = new javax.swing.GroupLayout(panelSeguridad);
        panelSeguridad.setLayout(panelSeguridadLayout);
        panelSeguridadLayout.setHorizontalGroup(
            panelSeguridadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSeguridad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        panelSeguridadLayout.setVerticalGroup(
            panelSeguridadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSeguridadLayout.createSequentialGroup()
                .addComponent(lblSeguridad, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        tablaTitulos.add(panelSeguridad, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 45, 400, 45));

        panelFecha.setBackground(new java.awt.Color(255, 255, 255));
        panelFecha.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(0, 0, 0)));

        lblFechaMod.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        lblFechaMod.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFechaMod.setText("Fecha");

        javax.swing.GroupLayout panelFechaLayout = new javax.swing.GroupLayout(panelFecha);
        panelFecha.setLayout(panelFechaLayout);
        panelFechaLayout.setHorizontalGroup(
            panelFechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFechaLayout.createSequentialGroup()
                .addComponent(lblFechaMod, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelFechaLayout.setVerticalGroup(
            panelFechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFechaLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblFechaMod, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tablaTitulos.add(panelFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 0, 100, 90));

        cont_FichaClinica.add(tablaTitulos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 1080, 90));

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

        cont_FichaClinica.add(btn_Confirmar, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 540, 90, 35));

        tpanel_Contenidos.setToolTipText("");

        panelInicio.setBackground(new java.awt.Color(255, 255, 255));
        panelInicio.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_InicioInicio.setFont(new java.awt.Font("Roboto", 0, 48)); // NOI18N
        lbl_InicioInicio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_InicioInicio.setText("Seleccione una acción");
        panelInicio.add(lbl_InicioInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 472));

        tpanel_Contenidos.addTab("Inicio", panelInicio);

        panelFormulario.setBackground(new java.awt.Color(255, 255, 255));
        panelFormulario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelDatosMedicos.setBackground(new java.awt.Color(255, 255, 255));
        panelDatosMedicos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblDatosMedicos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblDatosMedicos.setForeground(new java.awt.Color(31, 78, 121));
        lblDatosMedicos.setText("DATOS MÉDICOS");
        panelDatosMedicos.add(lblDatosMedicos, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        lblTemperatura.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblTemperatura.setText("Temperatura");
        panelDatosMedicos.add(lblTemperatura, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, 20));

        txtTemperatura.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtTemperatura.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtTemperatura.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTemperaturaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTemperaturaFocusLost(evt);
            }
        });
        panelDatosMedicos.add(txtTemperatura, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 86, 35));

        lblPulso.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblPulso.setText("Pulso");
        panelDatosMedicos.add(lblPulso, new org.netbeans.lib.awtextra.AbsoluteConstraints(303, 30, -1, 20));

        txtPulso.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtPulso.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtPulso.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPulsoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPulsoFocusLost(evt);
            }
        });
        panelDatosMedicos.add(txtPulso, new org.netbeans.lib.awtextra.AbsoluteConstraints(303, 50, 86, 35));

        lblSPO2.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblSPO2.setText("sPO2");
        panelDatosMedicos.add(lblSPO2, new org.netbeans.lib.awtextra.AbsoluteConstraints(394, 30, -1, 20));

        txtSPO2.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtSPO2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtSPO2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSPO2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSPO2FocusLost(evt);
            }
        });
        panelDatosMedicos.add(txtSPO2, new org.netbeans.lib.awtextra.AbsoluteConstraints(394, 50, 86, 35));

        lblFR.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblFR.setText("FR");
        panelDatosMedicos.add(lblFR, new org.netbeans.lib.awtextra.AbsoluteConstraints(121, 90, -1, 20));

        txtFR.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtFR.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtFR.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtFRFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFRFocusLost(evt);
            }
        });
        panelDatosMedicos.add(txtFR, new org.netbeans.lib.awtextra.AbsoluteConstraints(121, 110, 86, 35));

        lblGlicemia.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblGlicemia.setText("Glicemia");
        panelDatosMedicos.add(lblGlicemia, new org.netbeans.lib.awtextra.AbsoluteConstraints(121, 30, -1, 20));

        txtGlicemia.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtGlicemia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtGlicemia.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtGlicemiaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtGlicemiaFocusLost(evt);
            }
        });
        panelDatosMedicos.add(txtGlicemia, new org.netbeans.lib.awtextra.AbsoluteConstraints(121, 50, 86, 35));

        lblPeso.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblPeso.setText("Peso");
        panelDatosMedicos.add(lblPeso, new org.netbeans.lib.awtextra.AbsoluteConstraints(212, 90, -1, 20));

        txtPeso.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtPeso.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtPeso.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPesoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPesoFocusLost(evt);
            }
        });
        panelDatosMedicos.add(txtPeso, new org.netbeans.lib.awtextra.AbsoluteConstraints(212, 110, 86, 35));

        lblTalla.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblTalla.setText("Talla");
        panelDatosMedicos.add(lblTalla, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, 20));

        txtTalla.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtTalla.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "S", "M", "L", "XL", " " }));
        panelDatosMedicos.add(txtTalla, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 86, 35));

        lblIMC.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblIMC.setText("IMC");
        panelDatosMedicos.add(lblIMC, new org.netbeans.lib.awtextra.AbsoluteConstraints(303, 90, -1, 20));

        txtIMC.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtIMC.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtIMC.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtIMCFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtIMCFocusLost(evt);
            }
        });
        panelDatosMedicos.add(txtIMC, new org.netbeans.lib.awtextra.AbsoluteConstraints(303, 110, 177, 35));

        lblMotivoConsulta.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblMotivoConsulta.setText("Motivo de Consulta");
        panelDatosMedicos.add(lblMotivoConsulta, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, -1, 20));

        txtMotivoConsulta.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtMotivoConsulta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelDatosMedicos.add(txtMotivoConsulta, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 450, 35));

        lblPA.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblPA.setText("P/A");
        panelDatosMedicos.add(lblPA, new org.netbeans.lib.awtextra.AbsoluteConstraints(212, 30, -1, -1));

        txtPA.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtPA.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtPA.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPAFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPAFocusLost(evt);
            }
        });
        panelDatosMedicos.add(txtPA, new org.netbeans.lib.awtextra.AbsoluteConstraints(212, 50, 86, 35));

        panelFormulario.add(panelDatosMedicos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 215, 490, 265));

        panelMenu2.setBackground(new java.awt.Color(255, 255, 255));
        panelMenu2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblAntecedentesLaborales.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblAntecedentesLaborales.setForeground(new java.awt.Color(31, 78, 121));
        lblAntecedentesLaborales.setText("ANTECEDENTES Y HALLAZGOS MÉDICOS");
        panelMenu2.add(lblAntecedentesLaborales, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        lblHistoriaEnfermedadActual.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblHistoriaEnfermedadActual.setText("Historia de la Enfermedad Actual");
        panelMenu2.add(lblHistoriaEnfermedadActual, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        txtAreaHistoriaEnfermedadActual.setColumns(20);
        txtAreaHistoriaEnfermedadActual.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaHistoriaEnfermedadActual.setRows(5);
        jScrollPane1.setViewportView(txtAreaHistoriaEnfermedadActual);

        panelMenu2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 550, 40));

        txtAreaExamenFisico.setColumns(20);
        txtAreaExamenFisico.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaExamenFisico.setRows(5);
        jScrollPane3.setViewportView(txtAreaExamenFisico);

        panelMenu2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 550, 40));

        lblExamenFisico.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblExamenFisico.setText("Examen Físico (Hallazgos patológicos)");
        panelMenu2.add(lblExamenFisico, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        txtAreaImpresionClinica.setColumns(20);
        txtAreaImpresionClinica.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaImpresionClinica.setRows(5);
        jScrollPane4.setViewportView(txtAreaImpresionClinica);

        panelMenu2.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 550, 40));

        lblImpresionClinica.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblImpresionClinica.setText("Impresión Clínica");
        panelMenu2.add(lblImpresionClinica, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, -1));

        txtAreaTratamiento.setColumns(20);
        txtAreaTratamiento.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaTratamiento.setRows(5);
        jScrollPane5.setViewportView(txtAreaTratamiento);

        panelMenu2.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 550, 40));

        lblHistoriaEnfermedadActual1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblHistoriaEnfermedadActual1.setText("Tratamiento");
        panelMenu2.add(lblHistoriaEnfermedadActual1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, -1, -1));

        checkReferencia.setBackground(new java.awt.Color(255, 255, 255));
        checkReferencia.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        checkReferencia.setText("Referencia");
        panelMenu2.add(checkReferencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 320, -1, -1));

        checkTraslado.setBackground(new java.awt.Color(255, 255, 255));
        checkTraslado.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        checkTraslado.setText("Traslado");
        panelMenu2.add(checkTraslado, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 320, -1, -1));

        checkPatologia.setBackground(new java.awt.Color(255, 255, 255));
        checkPatologia.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        checkPatologia.setText("Patología relacionada al trabajo");
        panelMenu2.add(checkPatologia, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 320, -1, -1));

        lblObservaciones.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblObservaciones.setText("Observaciones");
        panelMenu2.add(lblObservaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, -1, -1));

        txtAreaObservaciones.setColumns(20);
        txtAreaObservaciones.setRows(5);
        jScrollPane7.setViewportView(txtAreaObservaciones);

        panelMenu2.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 550, 40));

        lbl_Realizado.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Realizado.setText("Realizado");
        panelMenu2.add(lbl_Realizado, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, -1, -1));

        combo_Realizado.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        panelMenu2.add(combo_Realizado, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, 135, 30));

        lbl_Revisado.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Revisado.setText("Revisado");
        panelMenu2.add(lbl_Revisado, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 410, -1, -1));

        combo_Revisado.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        panelMenu2.add(combo_Revisado, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 430, 135, 30));

        lbl_Autorizado.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Autorizado.setText("Autorizado");
        panelMenu2.add(lbl_Autorizado, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 410, -1, -1));

        combo_Autorizado.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        panelMenu2.add(combo_Autorizado, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 430, 135, 30));

        panelFormulario.add(panelMenu2, new org.netbeans.lib.awtextra.AbsoluteConstraints(485, 0, 590, 480));

        panelCorrector.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelCorrectorLayout = new javax.swing.GroupLayout(panelCorrector);
        panelCorrector.setLayout(panelCorrectorLayout);
        panelCorrectorLayout.setHorizontalGroup(
            panelCorrectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 9, Short.MAX_VALUE)
        );
        panelCorrectorLayout.setVerticalGroup(
            panelCorrectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );

        panelFormulario.add(panelCorrector, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 9, 220));

        panelMenu1.setBackground(new java.awt.Color(255, 255, 255));
        panelMenu1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtNombre.setEditable(false);
        txtNombre.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtNombre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelMenu1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 450, 35));

        lblNombre.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblNombre.setText("Nombre");
        panelMenu1.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, 20));

        lblClinica.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblClinica.setText("Clínica de Atención: Sidegua");
        panelMenu1.add(lblClinica, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 190, 30));

        txtFecha.setEditable(false);
        txtFecha.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtFecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelMenu1.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 100, 35));

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel3.setText("Fecha");
        panelMenu1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, 20));

        lblSexo.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblSexo.setText("Sexo");
        panelMenu1.add(lblSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 90, -1, 20));

        lblEdad.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblEdad.setText("Edad");
        panelMenu1.add(lblEdad, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 90, -1, 20));

        txtEdad.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtEdad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelMenu1.add(txtEdad, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 110, 67, 35));

        lblHora.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblHora.setText("Hora");
        panelMenu1.add(lblHora, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, -1, 20));

        lblCodigo.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblCodigo.setText("Código");
        panelMenu1.add(lblCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 90, -1, 20));

        txtCodigo.setEditable(false);
        txtCodigo.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtCodigo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelMenu1.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 110, 100, 35));

        lblArea.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblArea.setText("Área de Trabajo");
        panelMenu1.add(lblArea, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));

        txtArea.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtArea.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelMenu1.add(txtArea, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 207, 35));

        lblPuesto1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblPuesto1.setText("Puesto");
        panelMenu1.add(lblPuesto1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 150, -1, 20));

        txtPuesto.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtPuesto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelMenu1.add(txtPuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 170, 240, 35));

        rbtn_SexoM.setBackground(new java.awt.Color(255, 255, 255));
        grbtn_Sexo.add(rbtn_SexoM);
        rbtn_SexoM.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        rbtn_SexoM.setText("M");
        rbtn_SexoM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_SexoMActionPerformed(evt);
            }
        });
        panelMenu1.add(rbtn_SexoM, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, -1, -1));

        rbtn_SexoF.setBackground(new java.awt.Color(255, 255, 255));
        grbtn_Sexo.add(rbtn_SexoF);
        rbtn_SexoF.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        rbtn_SexoF.setText("F");
        rbtn_SexoF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_SexoFActionPerformed(evt);
            }
        });
        panelMenu1.add(rbtn_SexoF, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 110, -1, -1));

        btn_OtroEmpleado.setBackground(new java.awt.Color(255, 102, 102));
        btn_OtroEmpleado.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btn_OtroEmpleado.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_btnOtroEmpleado.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lbl_btnOtroEmpleado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_btnOtroEmpleado.setText("Cambiar Empleado");
        lbl_btnOtroEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btnOtroEmpleadoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_btnOtroEmpleadoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_btnOtroEmpleadoMouseExited(evt);
            }
        });
        btn_OtroEmpleado.add(lbl_btnOtroEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 40));

        panelMenu1.add(btn_OtroEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 0, 120, 40));

        txtHora.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtHora.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtHora.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtHora.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtHoraMouseClicked(evt);
            }
        });
        panelMenu1.add(txtHora, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, 70, 35));

        tpanel_Empleado.addTab("Info Empleado", panelMenu1);

        panelComboboxEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        panelComboboxEmpleado.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblInstruccionComboboxEmpleado.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        lblInstruccionComboboxEmpleado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInstruccionComboboxEmpleado.setText("Seleccione un Empleado");
        panelComboboxEmpleado.add(lblInstruccionComboboxEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 485, -1));

        jtEmpleado.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jtEmpleado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(jtEmpleado);

        panelComboboxEmpleado.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, 90));

        txtSeleccionEmpleado.setEditable(false);
        txtSeleccionEmpleado.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        panelComboboxEmpleado.add(txtSeleccionEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 270, -1));

        panelPaginacionEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        panelComboboxEmpleado.add(panelPaginacionEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 450, 30));

        btn_SeleccionEmpleado.setBackground(new java.awt.Color(40, 235, 40));
        btn_SeleccionEmpleado.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btn_SeleccionEmpleado.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_btnSeleccionEmpleado.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_btnSeleccionEmpleado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_btnSeleccionEmpleado.setText("Seleccionar Empleado");
        lbl_btnSeleccionEmpleado.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lbl_btnSeleccionEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btnSeleccionEmpleadoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_btnSeleccionEmpleadoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_btnSeleccionEmpleadoMouseExited(evt);
            }
        });
        btn_SeleccionEmpleado.add(lbl_btnSeleccionEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 30));

        panelComboboxEmpleado.add(btn_SeleccionEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(332, 25, 140, 30));

        tpanel_Empleado.addTab("Seleccion Empleado", panelComboboxEmpleado);

        panelFormulario.add(tpanel_Empleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -20, 490, 240));

        tpanel_Contenidos.addTab("Formulario", panelFormulario);

        panelCombobox.setBackground(new java.awt.Color(255, 255, 255));
        panelCombobox.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblInstruccion.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        lblInstruccion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInstruccion.setText("Seleccione una Ficha Clínica");
        panelCombobox.add(lblInstruccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 1080, -1));

        lblTituloCombobox.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        lblTituloCombobox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTituloCombobox.setText("Título");
        panelCombobox.add(lblTituloCombobox, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, -1));

        jtFichaClinica.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jtFichaClinica.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Fecha", "Nombre", "Sexo", "Identificación", "Nivel Académico", "Puesto al que Aplica"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jtFichaClinica);

        panelCombobox.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 980, 170));

        btn_Ingresar.setBackground(new java.awt.Color(92, 92, 235));
        btn_Ingresar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        lbl_btnIngresar.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        lbl_btnIngresar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_btnIngresar.setText("Ingresar");
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

        panelCombobox.add(btn_Ingresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 90, 90, 35));

        panelPaginacionFicha.setBackground(new java.awt.Color(255, 255, 255));
        panelCombobox.add(panelPaginacionFicha, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 980, 30));

        lbl_SeleccionFichaClinica.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lbl_SeleccionFichaClinica.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lbl_SeleccionFichaClinica.setPreferredSize(new java.awt.Dimension(300, 40));
        panelCombobox.add(lbl_SeleccionFichaClinica, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        lbl_TituloSeleccion.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lbl_TituloSeleccion.setText("Registro Seleccionado");
        panelCombobox.add(lbl_TituloSeleccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        tpanel_Contenidos.addTab("Combobox", panelCombobox);

        cont_FichaClinica.add(tpanel_Contenidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 1080, 510));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cont_FichaClinica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cont_FichaClinica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lbl_btn_ConfirmarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_ConfirmarMouseEntered
        if (lbl_btn_Confirmar.isEnabled())
            btn_Confirmar.setBackground(util.colorCursorEntra(btn_Confirmar.getBackground()));
    }//GEN-LAST:event_lbl_btn_ConfirmarMouseEntered

    private void lbl_btn_ConfirmarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_ConfirmarMouseExited
        if (lbl_btn_Confirmar.isEnabled())
            btn_Confirmar.setBackground(util.colorCursorSale(btn_Confirmar.getBackground()));
    }//GEN-LAST:event_lbl_btn_ConfirmarMouseExited

    private void lbl_btn_ConfirmarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_ConfirmarMouseClicked
        if (lbl_btn_Confirmar.isEnabled()){
            if (verificarEmpleado()){
                if (verificarFichaClinica()){
                    try {
                        getFichaClinica();
                        getEmpleado();
                        switch (contenidoActual){
                            case "Eliminar":
                                eliminar();
                                break;
                            case "Actualizar":
                                actualizar();
                                break;
                            case "Crear":
                                crear();
                                break;
                            default:
                                break;
                        }
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(this, "ERROR " + e.toString() + " AL INSERTAR EL REGISTRO. COMUNIQUESE CON TI", "Inserción de Datos", JOptionPane.ERROR_MESSAGE);
                    }
                }else
                JOptionPane.showMessageDialog(this, "Información de ficha clínica incompleta o no válida", "Inserción de Datos", JOptionPane.INFORMATION_MESSAGE);
            }else
                JOptionPane.showMessageDialog(this, "Información del empleado incompleta o no válida", "Inserción de Datos", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_lbl_btn_ConfirmarMouseClicked

    private void rbtn_SexoMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_SexoMActionPerformed
        empleado.setSexo("M");
    }//GEN-LAST:event_rbtn_SexoMActionPerformed

    private void rbtn_SexoFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_SexoFActionPerformed
        empleado.setSexo("F");
    }//GEN-LAST:event_rbtn_SexoFActionPerformed

    private void lbl_btnCrearMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnCrearMouseEntered
        btn_Crear.setBackground(util.colorCursorEntra(btn_Crear.getBackground()));
    }//GEN-LAST:event_lbl_btnCrearMouseEntered

    private void lbl_btnCrearMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnCrearMouseExited
        btn_Crear.setBackground(util.colorCursorSale(btn_Crear.getBackground()));
    }//GEN-LAST:event_lbl_btnCrearMouseExited

    private void lbl_btnActualizarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnActualizarMouseEntered
        btn_Actualizar.setBackground(util.colorCursorEntra(btn_Actualizar.getBackground()));
    }//GEN-LAST:event_lbl_btnActualizarMouseEntered

    private void lbl_btnActualizarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnActualizarMouseExited
        btn_Actualizar.setBackground(util.colorCursorSale(btn_Actualizar.getBackground()));
    }//GEN-LAST:event_lbl_btnActualizarMouseExited

    private void lbl_BtnEliminarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_BtnEliminarMouseEntered
        btn_Eliminar.setBackground(util.colorCursorEntra(btn_Eliminar.getBackground()));
    }//GEN-LAST:event_lbl_BtnEliminarMouseEntered

    private void lbl_BtnEliminarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_BtnEliminarMouseExited
        btn_Eliminar.setBackground(util.colorCursorSale(btn_Eliminar.getBackground()));
    }//GEN-LAST:event_lbl_BtnEliminarMouseExited

    private void lbl_btnCrearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnCrearMouseClicked
        if (!contenidoActual.equals("Formulario")){
            tpanel_Contenidos.setSelectedIndex(1);
            btn_Confirmar.setBackground(new Color(40,235,40));
            btn_Confirmar.setVisible(true);
            lbl_btn_Confirmar.setText("Ingresar");
            contenidoActual = "Crear";
            lbl_btn_Confirmar.setEnabled(true);
            reset();
            setTabla();
        }
    }//GEN-LAST:event_lbl_btnCrearMouseClicked

    private void lbl_btnActualizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnActualizarMouseClicked
        if (!contenidoActual.equals("Actualizar")){
            btn_Confirmar.setVisible(false);
            tpanel_Contenidos.setSelectedIndex(2);
            btn_Ingresar.setBackground(new Color(92,92,235));
            lblTituloCombobox.setText("Actualizar");
            lbl_btn_Confirmar.setText("Actualizar");
            contenidoActual = "Actualizar";
            lbl_btn_Confirmar.setEnabled(true);
            reset();
            setTabla();
        }
    }//GEN-LAST:event_lbl_btnActualizarMouseClicked

    private void lbl_BtnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_BtnEliminarMouseClicked
        if (!contenidoActual.equals("Eliminar")){
            btn_Confirmar.setVisible(false);
            tpanel_Contenidos.setSelectedIndex(2);
            btn_Ingresar.setBackground(new Color(235,91,91));
            lblTituloCombobox.setText("Eliminar");
            lbl_btn_Confirmar.setText("Eliminar");
            contenidoActual = "Eliminar";
            lbl_btn_Confirmar.setEnabled(true);
            reset();
            setTabla();
        }
    }//GEN-LAST:event_lbl_BtnEliminarMouseClicked

    private void lbl_btnIngresarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnIngresarMouseEntered
        btn_Ingresar.setBackground(util.colorCursorEntra(btn_Ingresar.getBackground()));
    }//GEN-LAST:event_lbl_btnIngresarMouseEntered

    private void lbl_btnIngresarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnIngresarMouseExited
        btn_Ingresar.setBackground(util.colorCursorSale(btn_Ingresar.getBackground()));
    }//GEN-LAST:event_lbl_btnIngresarMouseExited

    private void lbl_btnIngresarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnIngresarMouseClicked
        try {
            if (lbl_SeleccionFichaClinica.getText().length() > 0){
                if (contenidoActual.equals("Actualizar")){
                    tpanel_Contenidos.setSelectedIndex(1);
                    btn_Confirmar.setBackground(new Color(92,92,235));

                    btn_Confirmar.setVisible(true);
                }else if (contenidoActual.equals("Eliminar")){
                    tpanel_Contenidos.setSelectedIndex(1);
                    btn_Confirmar.setBackground(new Color(235,91,91));

                    btn_Confirmar.setVisible(true);            
                }
                buscarEmpleadoFichaClinica(jtFichaClinica.getValueAt(jtFichaClinica.getSelectedRow(), 0).toString());
            }else
                JOptionPane.showMessageDialog(null, "Seleccione un registro de la tabla");
        } catch (SQLException ex) {
        Logger.getLogger(FichaClinica.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConnectException ex) {
            Logger.getLogger(FichaClinica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_lbl_btnIngresarMouseClicked

    private void lbl_btnOtroEmpleadoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnOtroEmpleadoMouseEntered
        btn_OtroEmpleado.setBackground(util.colorCursorEntra(btn_OtroEmpleado.getBackground()));
    }//GEN-LAST:event_lbl_btnOtroEmpleadoMouseEntered

    private void lbl_btnOtroEmpleadoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnOtroEmpleadoMouseExited
        btn_OtroEmpleado.setBackground(util.colorCursorSale(btn_OtroEmpleado.getBackground()));
    }//GEN-LAST:event_lbl_btnOtroEmpleadoMouseExited

    private void lbl_btnOtroEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnOtroEmpleadoMouseClicked
        tpanel_Empleado.setSelectedIndex(1);
    }//GEN-LAST:event_lbl_btnOtroEmpleadoMouseClicked

    private void lbl_btnSeleccionEmpleadoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnSeleccionEmpleadoMouseEntered
        btn_SeleccionEmpleado.setBackground(util.colorCursorEntra(btn_SeleccionEmpleado.getBackground()));
    }//GEN-LAST:event_lbl_btnSeleccionEmpleadoMouseEntered

    private void lbl_btnSeleccionEmpleadoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnSeleccionEmpleadoMouseExited
        btn_SeleccionEmpleado.setBackground(util.colorCursorSale(btn_SeleccionEmpleado.getBackground()));
    }//GEN-LAST:event_lbl_btnSeleccionEmpleadoMouseExited

    private void lbl_btnSeleccionEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnSeleccionEmpleadoMouseClicked
        if (txtSeleccionEmpleado.getText().length() > 0){
            try {
                empleado = empleadoCtrl.buscarFila(jtEmpleado.getValueAt(jtEmpleado.getSelectedRow(), 0).toString());
            } catch (SQLException ex) {
                Logger.getLogger(FichaClinica.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ConnectException ex) {
                Logger.getLogger(FichaClinica.class.getName()).log(Level.SEVERE, null, ex);
            }
            setEmpleado();
            tpanel_Empleado.setSelectedIndex(0);
        }else
            JOptionPane.showMessageDialog(this, "SELECCIONE UN EMPLEADO", "Selección de Empleado", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_lbl_btnSeleccionEmpleadoMouseClicked

    private void txtTemperaturaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTemperaturaFocusGained
        if (String.valueOf(txtTemperatura.getText()).equals("°C")){
            txtTemperatura.setText("");
            txtTemperatura.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtTemperaturaFocusGained

    private void txtTemperaturaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTemperaturaFocusLost
        if (txtTemperatura.getText().isEmpty()){
            txtTemperatura.setText("°C");
            txtTemperatura.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtTemperaturaFocusLost

    private void txtGlicemiaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGlicemiaFocusGained
        if (String.valueOf(txtGlicemia.getText()).equals("mg/dl")){
            txtGlicemia.setText("");
            txtGlicemia.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtGlicemiaFocusGained

    private void txtGlicemiaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGlicemiaFocusLost
        if (txtGlicemia.getText().isEmpty()){
            txtGlicemia.setText("mg/dl");
            txtGlicemia.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtGlicemiaFocusLost

    private void txtPAFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPAFocusGained
        if (String.valueOf(txtPA.getText()).equals("mmHg")){
            txtPA.setText("");
            txtPA.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtPAFocusGained

    private void txtPAFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPAFocusLost
        if (txtPA.getText().isEmpty()){
            txtPA.setText("mmHg");
            txtPA.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtPAFocusLost

    private void txtPulsoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPulsoFocusGained
        if (String.valueOf(txtPulso.getText()).equals("LPM")){
            txtPulso.setText("");
            txtPulso.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtPulsoFocusGained

    private void txtPulsoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPulsoFocusLost
        if (txtPulso.getText().isEmpty()){
            txtPulso.setText("LPM");
            txtPulso.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtPulsoFocusLost

    private void txtSPO2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSPO2FocusGained
        if (String.valueOf(txtSPO2.getText()).equals("%")){
            txtSPO2.setText("");
            txtSPO2.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtSPO2FocusGained

    private void txtSPO2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSPO2FocusLost
        if (txtSPO2.getText().isEmpty()){
            txtSPO2.setText("%");
            txtSPO2.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtSPO2FocusLost

    private void txtFRFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFRFocusGained
        if (String.valueOf(txtFR.getText()).equals("RPM")){
            txtFR.setText("");
            txtFR.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtFRFocusGained

    private void txtFRFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFRFocusLost
        if (txtFR.getText().isEmpty()){
            txtFR.setText("RPM");
            txtFR.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtFRFocusLost

    private void txtPesoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesoFocusGained
        if (String.valueOf(txtPeso.getText()).equals("lb")){
            txtPeso.setText("");
            txtPeso.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtPesoFocusGained

    private void txtPesoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesoFocusLost
        if (txtPeso.getText().isEmpty()){
            txtPeso.setText("lb");
            txtPeso.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtPesoFocusLost

    private void txtIMCFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIMCFocusGained
        if (String.valueOf(txtIMC.getText()).equals("Kg/m^2")){
            txtIMC.setText("");
            txtIMC.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtIMCFocusGained

    private void txtIMCFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIMCFocusLost
        if (txtIMC.getText().isEmpty()){
            txtIMC.setText("Kg/m^2");
            txtIMC.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtIMCFocusLost

    private void txtHoraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtHoraMouseClicked
        timeHora.showPopup(panelMenu1, 0, 0);
    }//GEN-LAST:event_txtHoraMouseClicked

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel btn_Actualizar;
    public javax.swing.JPanel btn_Confirmar;
    public javax.swing.JPanel btn_Crear;
    public javax.swing.JPanel btn_Eliminar;
    public javax.swing.JPanel btn_Ingresar;
    private javax.swing.JPanel btn_OtroEmpleado;
    private javax.swing.JPanel btn_SeleccionEmpleado;
    private javax.swing.JCheckBox checkPatologia;
    private javax.swing.JCheckBox checkReferencia;
    private javax.swing.JCheckBox checkTraslado;
    private javax.swing.JComboBox<String> combo_Autorizado;
    private javax.swing.JComboBox<String> combo_Realizado;
    private javax.swing.JComboBox<String> combo_Revisado;
    public javax.swing.JPanel cont_FichaClinica;
    public static javax.swing.ButtonGroup grbtn_Sexo;
    public static javax.swing.ButtonGroup grbtn_empresa1;
    public static javax.swing.ButtonGroup grbtn_empresa2;
    public static javax.swing.ButtonGroup grbtn_empresa3;
    public javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTable jtEmpleado;
    public javax.swing.JTable jtFichaClinica;
    public javax.swing.JLabel lblAG;
    public javax.swing.JLabel lblAntecedentesLaborales;
    public javax.swing.JLabel lblArea;
    public javax.swing.JLabel lblClinica;
    public javax.swing.JLabel lblCodigo;
    public javax.swing.JLabel lblDatosMedicos;
    public javax.swing.JLabel lblEdad;
    private javax.swing.JLabel lblExamenFisico;
    public javax.swing.JLabel lblFR;
    public javax.swing.JLabel lblFechaMod;
    public javax.swing.JLabel lblGlicemia;
    private javax.swing.JLabel lblHistoriaEnfermedadActual;
    private javax.swing.JLabel lblHistoriaEnfermedadActual1;
    public javax.swing.JLabel lblHora;
    public javax.swing.JLabel lblIMC;
    private javax.swing.JLabel lblImpresionClinica;
    public javax.swing.JLabel lblInstruccion;
    private javax.swing.JLabel lblInstruccionComboboxEmpleado;
    public javax.swing.JLabel lblMotivoConsulta;
    public javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblObservaciones;
    private javax.swing.JLabel lblPA;
    public javax.swing.JLabel lblPeso;
    public javax.swing.JLabel lblPuesto1;
    public javax.swing.JLabel lblPulso;
    public javax.swing.JLabel lblSPO2;
    public javax.swing.JLabel lblSeguridad;
    public javax.swing.JLabel lblSexo;
    public javax.swing.JLabel lblTalla;
    public javax.swing.JLabel lblTemperatura;
    public javax.swing.JLabel lblTituloCombobox;
    public javax.swing.JLabel lblTituloPrincipal;
    public javax.swing.JLabel lblTitulos;
    private javax.swing.JLabel lbl_Autorizado;
    public javax.swing.JLabel lbl_BtnEliminar;
    public javax.swing.JLabel lbl_InicioInicio;
    private javax.swing.JLabel lbl_Realizado;
    private javax.swing.JLabel lbl_Revisado;
    private javax.swing.JLabel lbl_SeleccionFichaClinica;
    private javax.swing.JLabel lbl_TituloSeleccion;
    public javax.swing.JLabel lbl_btnActualizar;
    public javax.swing.JLabel lbl_btnCrear;
    public javax.swing.JLabel lbl_btnIngresar;
    private javax.swing.JLabel lbl_btnOtroEmpleado;
    private javax.swing.JLabel lbl_btnSeleccionEmpleado;
    public javax.swing.JLabel lbl_btn_Confirmar;
    public javax.swing.JPanel panelAG;
    public javax.swing.JPanel panelBotonesCRUD;
    public javax.swing.JPanel panelCombobox;
    private javax.swing.JPanel panelComboboxEmpleado;
    private javax.swing.JPanel panelCorrector;
    private javax.swing.JPanel panelDatosMedicos;
    public javax.swing.JPanel panelEdicion;
    public javax.swing.JPanel panelFecha;
    public javax.swing.JPanel panelFormulario;
    public javax.swing.JPanel panelInicio;
    public javax.swing.JPanel panelMenu1;
    public javax.swing.JPanel panelMenu2;
    private javax.swing.JPanel panelPaginacionEmpleado;
    private javax.swing.JPanel panelPaginacionFicha;
    public javax.swing.JPanel panelSeguridad;
    public javax.swing.JPanel panelTitulos;
    public javax.swing.JRadioButton rbtn_SexoF;
    public javax.swing.JRadioButton rbtn_SexoM;
    public javax.swing.JPanel tablaTitulos;
    private com.raven.swing.TimePicker timeHora;
    public javax.swing.JTabbedPane tpanel_Contenidos;
    private javax.swing.JTabbedPane tpanel_Empleado;
    public javax.swing.JTextField txtArea;
    private javax.swing.JTextArea txtAreaExamenFisico;
    private javax.swing.JTextArea txtAreaHistoriaEnfermedadActual;
    private javax.swing.JTextArea txtAreaImpresionClinica;
    private javax.swing.JTextArea txtAreaObservaciones;
    private javax.swing.JTextArea txtAreaTratamiento;
    public javax.swing.JTextField txtCodigo;
    public javax.swing.JTextField txtEdad;
    public javax.swing.JTextField txtFR;
    public javax.swing.JTextField txtFecha;
    public javax.swing.JTextField txtGlicemia;
    private javax.swing.JTextField txtHora;
    public javax.swing.JTextField txtIMC;
    public javax.swing.JTextField txtMotivoConsulta;
    public javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPA;
    public javax.swing.JTextField txtPeso;
    public javax.swing.JLabel txtPreempleoId;
    public javax.swing.JTextField txtPuesto;
    public javax.swing.JTextField txtPulso;
    public javax.swing.JTextField txtSPO2;
    private javax.swing.JTextField txtSeleccionEmpleado;
    private javax.swing.JComboBox<String> txtTalla;
    public javax.swing.JTextField txtTemperatura;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        Object evt = e.getSource();
        if (evt.equals(filasPermitidasFicha))
            paginadorFicha.eventComboBox(filasPermitidasFicha);
        else if (evt.equals(filasPermitidasEmpleado))
            paginadorEmpleado.eventComboBox(filasPermitidasEmpleado);
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        Object evt = e.getSource();
        if (evt.equals(filasPermitidasFicha))
            paginadorFicha.actualizarBotonesPaginacion();
        else if (evt.equals(filasPermitidasEmpleado))
            paginadorEmpleado.actualizarBotonesPaginacion();
    }

}
