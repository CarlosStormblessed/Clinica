package Vistas;

import javax.swing.JPanel;
import Utilitarios.Utilitarios;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.ButtonModel;
import javax.swing.table.DefaultTableModel;
import Controladores.PreempleoCtrl;
import Controladores.AntecedentesCtrl;
import Controladores.PaginadorTabla;
import Modelos.AntecedentesMod;
import Modelos.PreempleoMod;
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

import dateChooser.dateChooser.*;
import java.awt.CardLayout;
import javax.swing.JTabbedPane;

public class Preempleo extends javax.swing.JFrame implements ActionListener, TableModelListener{

    Utilitarios util = new Utilitarios();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime now = LocalDateTime.now();
    String contenidoActual;
    boolean emp1=true, emp2=false, emp3=false;
    public String nombreContenido = "Preempleo", empleadoId;
    public JComboBox<Integer> filasPermitidas;
    private PreempleoMod preempleo = new PreempleoMod();
    private AntecedentesMod antecedentes = new AntecedentesMod();
    private PreempleoCtrl preempCtrl = new PreempleoCtrl();
    private AntecedentesCtrl antCtrl = new AntecedentesCtrl();
    private PaginadorTabla <Preempleo> paginador;
    public Preempleo() throws SQLException, ConnectException {
        initComponents();
        contenidoActual = "Inicio";
        tpanel_Contenidos.setSelectedIndex(0);
        btn_Confirmar.setVisible(false);
        lbl_btn_Confirmar.setEnabled(false);
        jtPreempleo.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent Mouse_evt){
                JTable tabla = (JTable) Mouse_evt.getSource();
                Point punto = Mouse_evt.getPoint();
                int fila = tabla.rowAtPoint(punto);
                if (Mouse_evt.getClickCount() == 1){
                    lbl_SeleccionPreempleo.setText(jtPreempleo.getValueAt(jtPreempleo.getSelectedRow(), 2).toString());
                }
            }
        });
        reset();
    }
    
    private void reset(){
        lblAG.setIcon(util.construirImagen("/Imagenes/AG_logo.png", lblAG.getWidth(), lblAG.getHeight()));
        resetAntecedentes();
        resetPreempleado();
        construirEtiquetas();
        panelPaginacion.removeAll();
    }
    
    private void resetPreempleado(){
        txtPreempleoId.setText("");
        txtNombre.setText("");
        setSexo("");
        txtIdentificacion.setText("");
        txtEdad.setText("");
        txtEstadoCivil.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtNivelAcademico.setText("");
        txtPuestoAplica.setText("");
        preempleo.setClinicaId("");
        preempleo.setId("");
        preempleo.setNombre("");
        preempleo.setSexo("");
        preempleo.setIdentificacion("");
        preempleo.setEdad("");
        preempleo.setEstadoCivil("");
        preempleo.setDireccion("");
        preempleo.setTelefono("");
        preempleo.setNivelAcademico("");
        preempleo.setPuestoAplica("");
        preempleo.setAntecedentesId("");
        preempleo.setEstado("");
        
    }
    
    private void resetAntecedentes(){
        txtMenarquia.setText("");
        txtFUR.setText("");
        txtCSTP.setText("");
        txtHV.setText("");
        txtHM.setText("");
        txtG.setText("");
        txtP.setText("");
        txtAB.setText("");
        txtMPF.setText("");
        txtEmpresa1.setText("");
        txtEmpresa2.setText("");
        txtEmpresa3.setText("");
        txtPuesto1.setText("");
        txtPuesto2.setText("");
        txtPuesto3.setText("");
        rbtn1_1.setEnabled(false);
        rbtn1_2.setEnabled(false);
        rbtn1_3.setEnabled(false);
        rbtn2_1.setEnabled(false);
        rbtn2_2.setEnabled(false);
        rbtn2_3.setEnabled(false);
        rbtn3_1.setEnabled(false);
        rbtn3_2.setEnabled(false);
        rbtn3_3.setEnabled(false);
        rbtn4_1.setEnabled(false);
        rbtn4_2.setEnabled(false);
        rbtn4_3.setEnabled(false);
        txtEmpresa2.setEnabled(false);
        txtPuesto2.setEnabled(false);
        txtEmpresa3.setEnabled(false);
        txtPuesto3.setEnabled(false);
        
        txtDiagnostico.setText("");
        
        antecedentes.setId("");
        antecedentes.setMenarquia("");
        antecedentes.setCstp("");
        antecedentes.setFur("");
        antecedentes.setMpf("");
        antecedentes.setHv("");
        antecedentes.setHm("");
        antecedentes.setG("");
        antecedentes.setP("");
        antecedentes.setAb("");
        antecedentes.setFecha("");
        antecedentes.setEmpresa1("");
        antecedentes.setEmpresa2("");
        antecedentes.setEmpresa3("");
        antecedentes.setPuesto1("");
        antecedentes.setPuesto2("");
        antecedentes.setPuesto3("");
        antecedentes.setTiempolaborado1("0");
        antecedentes.setTiempolaborado2("0");
        antecedentes.setTiempolaborado3("0");
        antecedentes.setDiagnostico("");
        antecedentes.setFamiliares("");
        antecedentes.setMedicos("");
        antecedentes.setTratamientos("");
        antecedentes.setLaboratorios("");
        antecedentes.setQuirurgicos("");
        antecedentes.setTraumaticos("");
        antecedentes.setAlergicos("");
        antecedentes.setVicios("");
        antecedentes.setEmpleadoId("");
        antecedentes.setEstado("");
        setTiempoLaborado();
    }
    
    private void construirEtiquetas(){
        lblTitulos.setText("<html><center>Formato<br><b>FICHA MÉDICA PRE-EMPLEO</b><br>CÓDIGO12345</center></html>");
        lblSeguridad.setText("<html><center>Seguridad Industrial y Salud Ocupacional</center></html>");
        lblFechaMod.setText("<html><center>Fecha de<br>modificacion:<br>"+ util.convertirFechaGUI(now.format(dtf)) + "</center></html>");
        lbl_SeleccionPreempleo.setText("");
        txtFecha.setText(util.convertirFechaGUI(now.format(dtf)));
    }
    
    private boolean verificarEmpresas(){
        boolean valido = false;
        if (emp1){
            if (emp2){
                if (emp3)
                    valido = true;
                else if ((txtEmpresa3.getText().length()>0) || (txtPuesto3.getText().length()>0))
                    valido = false;
                else
                    valido = true;
            }else if ((txtEmpresa2.getText().length()>0) || (txtPuesto2.getText().length()>0))
                valido = false;
            else
                valido = true;
        }
        return valido;
    }
    
    private boolean verificarSexo(){
        boolean valido = false;
        if (grbtn_Sexo.isSelected(rbtn_SexoM.getModel()) || (grbtn_Sexo.isSelected(rbtn_SexoF.getModel())))
            valido = true;
        return valido;
    }
    
    private boolean verificarPreempleado(){
        boolean valido = false;
        if ((txtFecha.getText().length()>0) && (txtIdentificacion.getText().length()>0) && (util.verificarNumero(txtIdentificacion.getText())) && (txtNombre.getText().length()>0) && (util.verificarNumero(txtEdad.getText())) && (txtEstadoCivil.getText().length()>0) && (txtDireccion.getText().length()>0) && (txtTelefono.getText().length()>0) && (txtNivelAcademico.getText().length()>0) && (txtPuestoAplica.getText().length()>0) && (verificarSexo()))
            valido = true;
        else valido = false;
        return valido;
    }
    
    private boolean verificarAntecedentes(){
        boolean valido = false;
        if (((util.verificarNumero(txtG.getText())) || (txtG.getText().length() == 0)) && ((util.verificarNumero(txtP.getText())) || (txtP.getText().length() == 0)) && ((util.verificarNumero(txtHV.getText())) || (txtHV.getText().length() == 0)) && ((util.verificarNumero(txtHM.getText())) || (txtHM.getText().length() == 0)) && (util.verificarNumero(txtAB.getText()) || (txtAB.getText().length() == 0)) && (txtDiagnostico.getText().length()>0))
                valido = true;
        return valido;
    }
    
    public JPanel getContenido(){
        try{
            return cont_Preempleo;
        }catch (Exception ex) {
            Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    private void setTiempoLaborado(){
        switch (Integer.valueOf(antecedentes.getTiempolaborado1())){
            case 0: 
                grbtn_empresa1.clearSelection();
                break;
            case 1:
                rbtn1_1.setSelected(true);
                grbtn_empresa1.setSelected(rbtn1_1.getModel(), emp1);
                break;
            case 2:
                rbtn2_1.setSelected(true);
                grbtn_empresa1.setSelected(rbtn2_1.getModel(), emp1);
                break;
            case 3:
                rbtn3_1.setSelected(true);
                grbtn_empresa1.setSelected(rbtn3_1.getModel(), emp1);
                break;
            case 4:
                rbtn4_1.setSelected(true);
                grbtn_empresa1.setSelected(rbtn4_1.getModel(), emp1);
                break;
            default:
                grbtn_empresa1.clearSelection();
                break;
        }
        switch (Integer.valueOf(antecedentes.getTiempolaborado2())){
            case 0: 
                grbtn_empresa2.clearSelection();
                break;
            case 1: 
                rbtn1_2.setSelected(true);
                grbtn_empresa2.setSelected(rbtn1_2.getModel(), emp2);
                break;
            case 2:
                rbtn2_2.setSelected(true);
                grbtn_empresa2.setSelected(rbtn2_2.getModel(), emp2);
                break;
            case 3:
                rbtn3_2.setSelected(true);
                grbtn_empresa2.setSelected(rbtn3_2.getModel(), emp2);
                break;
            case 4:
                rbtn4_2.setSelected(true);
                grbtn_empresa2.setSelected(rbtn4_2.getModel(), emp2);
                break;
            default:
                grbtn_empresa2.clearSelection();
                break;
        }
        switch (Integer.valueOf(antecedentes.getTiempolaborado3())){
            case 0: 
                grbtn_empresa3.clearSelection();
                break;
            case 1: 
                rbtn1_3.setSelected(true);
                grbtn_empresa3.setSelected(rbtn1_3.getModel(), emp3);
                break;
            case 2:
                rbtn2_3.setSelected(true);
                grbtn_empresa3.setSelected(rbtn2_3.getModel(), emp3);
                break;
            case 3:
                rbtn3_3.setSelected(true);
                grbtn_empresa3.setSelected(rbtn3_3.getModel(), emp3);
                break;
            case 4:
                rbtn4_3.setSelected(true);
                grbtn_empresa3.setSelected(rbtn4_3.getModel(), emp3);
                break;
            default:
                grbtn_empresa3.clearSelection();
                break;
        }
        if (Integer.valueOf(antecedentes.getTiempolaborado1()) > 0){
            rbtn1_1.setEnabled(true);
            rbtn2_1.setEnabled(true);
            rbtn3_1.setEnabled(true);
            rbtn4_1.setEnabled(true);
            emp1 = true;
        }else if (Integer.valueOf(antecedentes.getTiempolaborado2()) > 0){
            rbtn1_2.setEnabled(true);
            rbtn2_2.setEnabled(true);
            rbtn3_2.setEnabled(true);
            rbtn4_2.setEnabled(true);
            emp2 = true;
        }else if (Integer.valueOf(antecedentes.getTiempolaborado3()) > 0){
            rbtn4_3.setEnabled(true);
            rbtn3_3.setEnabled(true);
            rbtn2_3.setEnabled(true);
            rbtn1_3.setEnabled(true);
            emp3 = true;
        }
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
    
    /**
     * Los antecedentes en pantalla se guardarán en una variable global "antecedentes"
     */
    private void getAntecedentes(){
        antecedentes.setMenarquia(util.convertirFechaSQL(txtMenarquia.getText()));
        antecedentes.setFur(util.convertirFechaSQL(txtFUR.getText()));
        antecedentes.setCstp(util.convertirFechaSQL(txtCSTP.getText()));
        antecedentes.setMpf("");
        antecedentes.setHv(txtHV.getText());
        antecedentes.setHm(txtHM.getText());
        antecedentes.setG(txtG.getText());
        antecedentes.setP(txtP.getText());
        antecedentes.setAb(txtAB.getText());
        antecedentes.setMpf(txtMPF.getText());
        antecedentes.setFecha(util.convertirFechaSQL(txtFecha.getText()));
        antecedentes.setEmpresa1(txtEmpresa1.getText());
        antecedentes.setEmpresa2(txtEmpresa2.getText());
        antecedentes.setEmpresa3(txtEmpresa3.getText());
        antecedentes.setPuesto1(txtPuesto1.getText());
        antecedentes.setPuesto2(txtPuesto2.getText());
        antecedentes.setPuesto3(txtPuesto3.getText());
        antecedentes.setDiagnostico(txtDiagnostico.getText());
        antecedentes.setFamiliares(txtAreaFamiliares.getText());
        antecedentes.setMedicos(txtAreaMedicos.getText());
        antecedentes.setTratamientos(txtAreaTratamientos.getText());
        antecedentes.setLaboratorios(txtAreaLaboratorios.getText());
        antecedentes.setQuirurgicos(txtAreaQuirurgicos.getText());
        antecedentes.setTraumaticos(txtAreaTraumaticos.getText());
        antecedentes.setAlergicos(txtAreaAlergicos.getText());
        antecedentes.setVicios(txtAreaVicios.getText());
        
        antecedentes.setEmpleadoId(empleadoId);
    }
    
    /**
     * Los valores dentro de los antecedentes guardados en la variable global "antecedentes" se mostrarán en pantalla
     */
    private void setAntecedentes(){
        txtMenarquia.setText(util.convertirFechaGUI(antecedentes.getMenarquia()));
        txtFUR.setText(util.convertirFechaGUI(antecedentes.getFur()));
        txtCSTP.setText(util.convertirFechaGUI(antecedentes.getCstp()));
        txtHV.setText(antecedentes.getHv());
        txtHM.setText(antecedentes.getHm());
        txtG.setText(antecedentes.getG());
        txtP.setText(antecedentes.getP());
        txtAB.setText(antecedentes.getAb());
        txtMPF.setText(antecedentes.getMpf());
        txtFecha.setText(util.convertirFechaGUI(antecedentes.getFecha()));
        txtEmpresa1.setText(antecedentes.getEmpresa1());
        txtEmpresa2.setText(antecedentes.getEmpresa2());
        txtEmpresa3.setText(antecedentes.getEmpresa3());
        txtPuesto1.setText(antecedentes.getPuesto1());
        txtPuesto2.setText(antecedentes.getPuesto2());
        txtPuesto3.setText(antecedentes.getPuesto3());
        setTiempoLaborado();
        txtDiagnostico.setText(antecedentes.getDiagnostico());
        txtAreaFamiliares.setText(antecedentes.getFamiliares());
        txtAreaMedicos.setText(antecedentes.getMedicos());
        txtAreaTratamientos.setText(antecedentes.getTratamientos());
        txtAreaLaboratorios.setText(antecedentes.getLaboratorios());
        txtAreaQuirurgicos.setText(antecedentes.getQuirurgicos());
        txtAreaTraumaticos.setText(antecedentes.getTraumaticos());
        txtAreaAlergicos.setText(antecedentes.getAlergicos());
        txtAreaVicios.setText(antecedentes.getVicios());
    }
    
    /**
     * Los valores de preempleo en pantalla se guardarán en una variable global "preempleo"
     */
    private void getPreempleo(){
        preempleo.setId(txtPreempleoId.getText());
        preempleo.setFecha(util.convertirFechaSQL(txtFecha.getText()));
        preempleo.setNombre(txtNombre.getText());
        preempleo.setIdentificacion(txtIdentificacion.getText());
        preempleo.setEdad(txtEdad.getText());
        preempleo.setEstadoCivil(txtEstadoCivil.getText());
        preempleo.setDireccion(txtDireccion.getText());
        preempleo.setTelefono(txtTelefono.getText());
        preempleo.setNivelAcademico(txtNivelAcademico.getText());
        preempleo.setPuestoAplica(txtPuestoAplica.getText());
        preempleo.setClinicaId("1");
        preempleo.setAntecedentesId(antecedentes.getId());
        
    }
    
    /**
     * Los valores dentro del preempleo guardados en la variable global "preempleo" se mostrarán en pantalla
     */
    private void setPreempleo(){
        txtPreempleoId.setText(preempleo.getId());
        txtFecha.setText(util.convertirFechaSQL(preempleo.getFecha()));
        txtNombre.setText(preempleo.getNombre());
        setSexo(preempleo.getSexo());
        txtIdentificacion.setText(preempleo.getIdentificacion());
        txtEdad.setText(preempleo.getEdad());
        txtEstadoCivil.setText(preempleo.getEstadoCivil());
        txtDireccion.setText(preempleo.getDireccion());
        txtTelefono.setText(preempleo.getTelefono());
        txtNivelAcademico.setText(preempleo.getNivelAcademico());
        txtPuestoAplica.setText(preempleo.getPuestoAplica());
    }
    
    //Todo lo relacionado a la Tabla
    private TableModel crearModeloTabla() {
        return new ModeloTabla<PreempleoMod>() {
            @Override
            public Object getValueAt(PreempleoMod t, int columna) {
                switch(columna){
                    case 0:
                        return t.getId();
                    case 1:
                        return t.getFecha();
                    case 2:
                        return t.getNombre();
                    case 3:
                        return t.getSexo();
                    case 4:
                        return t.getIdentificacion();
                    case 5:
                        return t.getNivelAcademico();
                    case 6:
                        return t.getPuestoAplica();
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
                        return "Sexo";
                    case 4:
                        return "Identificación";
                    case 5:
                        return "Nivel Académico";
                    case 6:
                        return "Puesto al que Aplica";
                }
                return null;
            }

            @Override
            public int getColumnCount() {
                return 7;
            }
        };
    }
    
    private void setTabla() throws SQLException, ConnectException{
        /*
        DefaultTableModel modelo = new DefaultTableModel();
        int cantidadColumnas = jtPreempleo.getColumnCount();

        String[] campos = new String[cantidadColumnas];
        for (int i = 0; i < cantidadColumnas; i++)
        {
            campos[i] = jtPreempleo.getColumnName(i);
        }
        modelo = util.consultaMixta("SELECT PREEMP_ID, PREEMP_FECHA, PREEMP_NOMBRE, PREEMP_SEXO, PREEMP_IDENTIFICACION, PREEMP_NIVELACADEMICO, PREEMP_PUESTOAPLICA FROM preempleo WHERE PREEMP_ESTADO = 1", campos);
        jtPreempleo.setModel(modelo);
        String[] idCombobox = new String[modelo.getRowCount()];
        for (int i = 0; i < modelo.getRowCount(); i++){
            idCombobox[i] = String.valueOf(modelo.getValueAt(i, 0));
        }
        setCombobox(idCombobox);
        */
        jtPreempleo.setModel(crearModeloTabla());
        DatosPaginacion<PreempleoMod> datosPaginacion = crearDatosPaginacion();
        paginador = new PaginadorTabla(jtPreempleo, datosPaginacion, new int[]{5,10,20,25,50,75,100}, 10);
        paginador.crearListadoFilasPermitidas(panelPaginacion);
        filasPermitidas = paginador.getComboboxFilasPermitidas();
        filasPermitidas.addActionListener(this);
        jtPreempleo.getModel().addTableModelListener(this);
        filasPermitidas.setSelectedItem(Integer.parseInt("10"));
    }
    
    private DatosPaginacion <PreempleoMod> crearDatosPaginacion() throws SQLException, ConnectException{
        List <PreempleoMod> lista = preempCtrl.seleccionarTodos();
        return new DatosPaginacion<PreempleoMod>(){
            @Override
            public int getTotalRowCount() {
                return lista.size();
            }

            @Override
            public List<PreempleoMod> getRows(int startIndex, int endIndex) {
                return lista.subList(startIndex, endIndex);
            }
        };
    }
    
    private void setBuscarPreempleoAntecedentes(String valorBuscar) throws SQLException, ConnectException{
        PreempleoCtrl lista1 = new PreempleoCtrl();
        preempleo = lista1.buscarFila(valorBuscar);
        setPreempleo();
        AntecedentesCtrl lista2 = new AntecedentesCtrl();
        antecedentes = lista2.buscarFila(preempleo.getAntecedentesId());
        setAntecedentes();
    }
    
    private void crear() throws SQLException{
        try {
            int res1 = 0, res2 = 0;
            res1 = antCtrl.Crear(antecedentes);
            if (res1 > 0){
                preempleo.setAntecedentesId(antCtrl.getMaxId());
                res2 = preempCtrl.Crear(preempleo);
            }
            if ((res1 > 0) && (res2 > 0)) {
                JOptionPane.showMessageDialog(this, "REGISTRO INGRESADO CON EXITO", "Inserción de Datos", JOptionPane.INFORMATION_MESSAGE);
                reset();
            } else {
                JOptionPane.showMessageDialog(this, "ERROR AL INSERTAR ANTECEDENTES O FICHA DE PREEMPLEO. COMUNIQUESE CON TI", "Inserción de Datos", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR " + e.toString() + " AL INSERTAR EL REGISTRO. COMUNIQUESE CON TI", "Inserción de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void actualizar() throws SQLException{
        try {
            int res1 = 0, res2 = 0;
            res1 = antCtrl.Actualizar(antecedentes);
            if (res1 > 0){
                res2 = preempCtrl.Actualizar(preempleo);
            }
            if ((res1 > 0) && (res2 > 0)) {
                JOptionPane.showMessageDialog(this, "REGISTRO ACTUALIZADO CON EXITO", "Inserción de Datos", JOptionPane.INFORMATION_MESSAGE);
                reset();
                tpanel_Contenidos.setSelectedIndex(2);
            } else {
                JOptionPane.showMessageDialog(this, "ERROR AL ACTUALIZAR ANTECEDENTES O FICHA DE PREEMPLEO. COMUNIQUESE CON TI", "Inserción de Datos", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR " + e.toString() + " AL ACTUALIZAR EL REGISTRO. COMUNIQUESE CON TI", "Inserción de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminar() throws SQLException{
        try {
            int res1 = 0, res2 = 0;
            res1 = antCtrl.Eliminar(antecedentes);
            if (res1 > 0){
                res2 = preempCtrl.Eliminar(preempleo);
            }
            if ((res1 > 0) && (res2 > 0)) {
                JOptionPane.showMessageDialog(this, "REGISTRO ELIMINADO CON EXITO", "Inserción de Datos", JOptionPane.INFORMATION_MESSAGE);
                reset();
                tpanel_Contenidos.setSelectedIndex(2);
            } else {
                JOptionPane.showMessageDialog(this, "ERROR AL ELIMINAR ANTECEDENTES O FICHA DE PREEMPLEO. COMUNIQUESE CON TI", "Inserción de Datos", JOptionPane.ERROR_MESSAGE);
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
        fechaMenarquia = new com.raven.datechooser.DateChooser();
        fechaFUR = new com.raven.datechooser.DateChooser();
        fechaCSTP = new com.raven.datechooser.DateChooser();
        cont_Preempleo = new javax.swing.JPanel();
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
        panelMenu1 = new javax.swing.JPanel();
        txtNombre = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        lblClinica = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        lblSexo = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtEdad = new javax.swing.JTextField();
        lblIdentificacion = new javax.swing.JLabel();
        txtIdentificacion = new javax.swing.JTextField();
        lblEstadoCivil = new javax.swing.JLabel();
        txtEstadoCivil = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        lblTelefono = new javax.swing.JLabel();
        lblNivelAcademico = new javax.swing.JLabel();
        txtNivelAcademico = new javax.swing.JTextField();
        lblPuestoAplica = new javax.swing.JLabel();
        txtPuestoAplica = new javax.swing.JTextField();
        rbtn_SexoM = new javax.swing.JRadioButton();
        rbtn_SexoF = new javax.swing.JRadioButton();
        panelCartas = new javax.swing.JPanel();
        panelMenu2 = new javax.swing.JPanel();
        panelAntecedentesGino = new javax.swing.JPanel();
        lblAntecedentesGi = new javax.swing.JLabel();
        lblMenarquia = new javax.swing.JLabel();
        txtMenarquia = new javax.swing.JTextField();
        lblFur = new javax.swing.JLabel();
        txtFUR = new javax.swing.JTextField();
        lblG = new javax.swing.JLabel();
        txtG = new javax.swing.JTextField();
        lblP = new javax.swing.JLabel();
        txtP = new javax.swing.JTextField();
        lblCSTP = new javax.swing.JLabel();
        txtCSTP = new javax.swing.JTextField();
        lblHV = new javax.swing.JLabel();
        txtHV = new javax.swing.JTextField();
        lblHM = new javax.swing.JLabel();
        txtHM = new javax.swing.JTextField();
        lblAB = new javax.swing.JLabel();
        txtAB = new javax.swing.JTextField();
        lblMPF = new javax.swing.JLabel();
        txtMPF = new javax.swing.JTextField();
        btn_Menu3 = new javax.swing.JPanel();
        lbl_btn_Menu3 = new javax.swing.JLabel();
        panelAntecedentesLaborales = new javax.swing.JPanel();
        lblAntecedentesLaborales = new javax.swing.JLabel();
        lblEmpresa = new javax.swing.JLabel();
        txtEmpresa1 = new javax.swing.JTextField();
        txtEmpresa2 = new javax.swing.JTextField();
        txtEmpresa3 = new javax.swing.JTextField();
        lblPuesto = new javax.swing.JLabel();
        txtPuesto1 = new javax.swing.JTextField();
        txtPuesto2 = new javax.swing.JTextField();
        txtPuesto3 = new javax.swing.JTextField();
        lblTiempoLaborado = new javax.swing.JLabel();
        lbl1anio = new javax.swing.JLabel();
        lbl2anios = new javax.swing.JLabel();
        lbl5anios = new javax.swing.JLabel();
        lbl11anios = new javax.swing.JLabel();
        panelRbtn1_1 = new javax.swing.JPanel();
        rbtn1_1 = new javax.swing.JRadioButton();
        panelRbtn1_2 = new javax.swing.JPanel();
        rbtn1_2 = new javax.swing.JRadioButton();
        panelRbtn1_3 = new javax.swing.JPanel();
        rbtn1_3 = new javax.swing.JRadioButton();
        panelRbtn2_1 = new javax.swing.JPanel();
        rbtn2_1 = new javax.swing.JRadioButton();
        panelRbtn2_2 = new javax.swing.JPanel();
        rbtn2_2 = new javax.swing.JRadioButton();
        panelRbtn2_3 = new javax.swing.JPanel();
        rbtn2_3 = new javax.swing.JRadioButton();
        panelRbtn3_1 = new javax.swing.JPanel();
        rbtn3_1 = new javax.swing.JRadioButton();
        panelRbtn3_2 = new javax.swing.JPanel();
        rbtn3_2 = new javax.swing.JRadioButton();
        panelRbtn3_3 = new javax.swing.JPanel();
        rbtn3_3 = new javax.swing.JRadioButton();
        panelRbtn4_1 = new javax.swing.JPanel();
        rbtn4_1 = new javax.swing.JRadioButton();
        panelRbtn4_2 = new javax.swing.JPanel();
        rbtn4_2 = new javax.swing.JRadioButton();
        panelRbtn4_3 = new javax.swing.JPanel();
        rbtn4_3 = new javax.swing.JRadioButton();
        panelMenu3 = new javax.swing.JPanel();
        lbl_AntecedentesGenerales = new javax.swing.JLabel();
        lbl_Familiares = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtAreaFamiliares = new javax.swing.JTextArea();
        lbl_Medicos = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtAreaMedicos = new javax.swing.JTextArea();
        lbl_Tratamientos = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtAreaTratamientos = new javax.swing.JTextArea();
        lbl_Laboratorios = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtAreaLaboratorios = new javax.swing.JTextArea();
        lbl_Quirurgicos = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        txtAreaQuirurgicos = new javax.swing.JTextArea();
        lbl_Traumaticos = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        txtAreaTraumaticos = new javax.swing.JTextArea();
        lbl_Alergicos = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        txtAreaAlergicos = new javax.swing.JTextArea();
        lbl_Vicios = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        txtAreaVicios = new javax.swing.JTextArea();
        btn_Menu2 = new javax.swing.JPanel();
        lbl_btn_Menu2 = new javax.swing.JLabel();
        panel_Diagnostico = new javax.swing.JPanel();
        lblDiagnostico = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDiagnostico = new javax.swing.JTextArea();
        panelCombobox = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblTituloCombobox = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtPreempleo = new javax.swing.JTable();
        btn_Ingresar = new javax.swing.JPanel();
        lbl_btnIngresar = new javax.swing.JLabel();
        panelPaginacion = new javax.swing.JPanel();
        lbl_SeleccionPreempleo = new javax.swing.JLabel();
        lbl_TituloSeleccion = new javax.swing.JLabel();

        fechaMenarquia.setForeground(new java.awt.Color(87, 87, 238));
        fechaMenarquia.setDateFormat("dd/MM/yyyy");
        fechaMenarquia.setTextRefernce(txtMenarquia);

        fechaFUR.setForeground(new java.awt.Color(87, 87, 238));
        fechaFUR.setTextRefernce(txtFUR);

        fechaCSTP.setForeground(new java.awt.Color(87, 87, 238));
        fechaCSTP.setTextRefernce(txtCSTP);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        cont_Preempleo.setBackground(new java.awt.Color(255, 255, 255));
        cont_Preempleo.setPreferredSize(new java.awt.Dimension(1080, 600));
        cont_Preempleo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelBotonesCRUD.setBackground(new java.awt.Color(255, 255, 255));
        panelBotonesCRUD.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_Crear.setBackground(new java.awt.Color(40, 235, 40));
        btn_Crear.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        lbl_btnCrear.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
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
            .addComponent(lbl_BtnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
        );
        btn_EliminarLayout.setVerticalGroup(
            btn_EliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_BtnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
        );

        panelBotonesCRUD.add(btn_Eliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, -1, 35));

        cont_Preempleo.add(panelBotonesCRUD, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 0, 270, 120));

        lblTituloPrincipal.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        lblTituloPrincipal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTituloPrincipal.setText("Ficha de Preempleo");
        cont_Preempleo.add(lblTituloPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, -1));

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

        cont_Preempleo.add(tablaTitulos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 1080, 90));

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

        cont_Preempleo.add(btn_Confirmar, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 540, 90, 35));

        tpanel_Contenidos.setBackground(new java.awt.Color(255, 255, 255));
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

        panelMenu1.setBackground(new java.awt.Color(255, 255, 255));
        panelMenu1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtNombre.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtNombre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtNombre.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        panelMenu1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 450, 35));

        lblNombre.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblNombre.setText("Nombre");
        panelMenu1.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, -1, 20));

        lblClinica.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblClinica.setText("Clínica de Atención: Sidegua");
        panelMenu1.add(lblClinica, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 268, 38));

        txtFecha.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtFecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtFecha.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtFecha.setEnabled(false);
        panelMenu1.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 147, 35));

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel3.setText("Fecha");
        panelMenu1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 20));

        lblSexo.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblSexo.setText("Sexo");
        panelMenu1.add(lblSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, -1, 20));

        jLabel4.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel4.setText("Edad");
        panelMenu1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 180, -1, 20));

        txtEdad.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtEdad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtEdad.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        panelMenu1.add(txtEdad, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 200, 67, 35));

        lblIdentificacion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblIdentificacion.setText("No. de Identificación");
        panelMenu1.add(lblIdentificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 60, -1, 20));

        txtIdentificacion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtIdentificacion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtIdentificacion.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        panelMenu1.add(txtIdentificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 300, 35));

        lblEstadoCivil.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblEstadoCivil.setText("Estado Civil");
        panelMenu1.add(lblEstadoCivil, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 180, -1, 20));

        txtEstadoCivil.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtEstadoCivil.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtEstadoCivil.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        panelMenu1.add(txtEstadoCivil, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 200, 290, 35));

        jLabel5.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel5.setText("Dirección");
        panelMenu1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, 20));

        txtDireccion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtDireccion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtDireccion.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        panelMenu1.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 357, 35));

        txtTelefono.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtTelefono.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtTelefono.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        panelMenu1.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 260, 90, 35));

        lblTelefono.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblTelefono.setText("Teléfono");
        panelMenu1.add(lblTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 240, -1, 20));

        lblNivelAcademico.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblNivelAcademico.setText("Nivel Académico");
        panelMenu1.add(lblNivelAcademico, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, -1, -1));

        txtNivelAcademico.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtNivelAcademico.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtNivelAcademico.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        panelMenu1.add(txtNivelAcademico, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 207, 35));

        lblPuestoAplica.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblPuestoAplica.setText("Puesto al que aplica");
        panelMenu1.add(lblPuestoAplica, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 300, -1, 20));

        txtPuestoAplica.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtPuestoAplica.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtPuestoAplica.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        panelMenu1.add(txtPuestoAplica, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 320, 240, 35));

        rbtn_SexoM.setBackground(new java.awt.Color(255, 255, 255));
        grbtn_Sexo.add(rbtn_SexoM);
        rbtn_SexoM.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        rbtn_SexoM.setText("M");
        rbtn_SexoM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_SexoMActionPerformed(evt);
            }
        });
        panelMenu1.add(rbtn_SexoM, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, -1, -1));

        rbtn_SexoF.setBackground(new java.awt.Color(255, 255, 255));
        grbtn_Sexo.add(rbtn_SexoF);
        rbtn_SexoF.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        rbtn_SexoF.setText("F");
        rbtn_SexoF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_SexoFActionPerformed(evt);
            }
        });
        panelMenu1.add(rbtn_SexoF, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, -1, -1));

        panelFormulario.add(panelMenu1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 490, 370));

        panelCartas.setLayout(new java.awt.CardLayout());

        panelMenu2.setBackground(new java.awt.Color(255, 255, 255));
        panelMenu2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelAntecedentesGino.setBackground(new java.awt.Color(255, 255, 255));
        panelAntecedentesGino.setMinimumSize(new java.awt.Dimension(566, 0));
        panelAntecedentesGino.setPreferredSize(new java.awt.Dimension(556, 100));
        panelAntecedentesGino.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblAntecedentesGi.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblAntecedentesGi.setForeground(new java.awt.Color(31, 78, 121));
        lblAntecedentesGi.setText("ANTECEDENTES GINOCOBSTÉTRICOS");
        panelAntecedentesGino.add(lblAntecedentesGi, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        lblMenarquia.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblMenarquia.setText("Menarquia");
        panelAntecedentesGino.add(lblMenarquia, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 20));

        txtMenarquia.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtMenarquia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMenarquia.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        panelAntecedentesGino.add(txtMenarquia, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 209, 35));

        lblFur.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblFur.setText("FUR");
        panelAntecedentesGino.add(lblFur, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, -1, 20));

        txtFUR.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtFUR.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtFUR.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        panelAntecedentesGino.add(txtFUR, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 40, 103, 35));

        lblG.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblG.setText("G");
        panelAntecedentesGino.add(lblG, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 20, -1, 20));

        txtG.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtG.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtG.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        panelAntecedentesGino.add(txtG, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 40, 103, 35));

        lblP.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblP.setText("P");
        panelAntecedentesGino.add(lblP, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 20, -1, 20));

        txtP.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtP.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        panelAntecedentesGino.add(txtP, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 40, 106, 35));

        lblCSTP.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblCSTP.setText("CSTP");
        panelAntecedentesGino.add(lblCSTP, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, 20));

        txtCSTP.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtCSTP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCSTP.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        panelAntecedentesGino.add(txtCSTP, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 209, 35));

        lblHV.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblHV.setText("HV");
        panelAntecedentesGino.add(lblHV, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 80, -1, 20));

        txtHV.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtHV.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtHV.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        panelAntecedentesGino.add(txtHV, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 103, 35));

        lblHM.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblHM.setText("HM");
        panelAntecedentesGino.add(lblHM, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 80, -1, 20));

        txtHM.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtHM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtHM.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        panelAntecedentesGino.add(txtHM, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 100, 103, 35));

        lblAB.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblAB.setText("AB");
        panelAntecedentesGino.add(lblAB, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 80, -1, 20));

        txtAB.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtAB.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtAB.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        panelAntecedentesGino.add(txtAB, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 100, 106, 35));

        lblMPF.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblMPF.setText("MPF");
        panelAntecedentesGino.add(lblMPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, 20));

        txtMPF.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtMPF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMPF.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        panelAntecedentesGino.add(txtMPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 423, 35));

        btn_Menu3.setBackground(new java.awt.Color(204, 204, 235));
        btn_Menu3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btn_Menu3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_btn_Menu3.setFont(new java.awt.Font("Roboto", 1, 11)); // NOI18N
        lbl_btn_Menu3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_btn_Menu3.setText("Página 2 ->");
        lbl_btn_Menu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btn_Menu3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_btn_Menu3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_btn_Menu3MouseExited(evt);
            }
        });
        btn_Menu3.add(lbl_btn_Menu3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 106, 35));

        panelAntecedentesGino.add(btn_Menu3, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 160, 106, 35));

        panelMenu2.add(panelAntecedentesGino, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 566, 205));

        panelAntecedentesLaborales.setBackground(new java.awt.Color(255, 255, 255));
        panelAntecedentesLaborales.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblAntecedentesLaborales.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblAntecedentesLaborales.setForeground(new java.awt.Color(31, 78, 121));
        lblAntecedentesLaborales.setText("ANTECEDENTES LABORALES");
        panelAntecedentesLaborales.add(lblAntecedentesLaborales, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        lblEmpresa.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblEmpresa.setText("Empresa");
        panelAntecedentesLaborales.add(lblEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        txtEmpresa1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtEmpresa1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtEmpresa1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtEmpresa1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEmpresa1KeyTyped(evt);
            }
        });
        panelAntecedentesLaborales.add(txtEmpresa1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 120, 35));

        txtEmpresa2.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtEmpresa2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));
        txtEmpresa2.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtEmpresa2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEmpresa2KeyTyped(evt);
            }
        });
        panelAntecedentesLaborales.add(txtEmpresa2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 85, 120, 35));

        txtEmpresa3.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtEmpresa3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));
        txtEmpresa3.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtEmpresa3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEmpresa3KeyTyped(evt);
            }
        });
        panelAntecedentesLaborales.add(txtEmpresa3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 120, 35));

        lblPuesto.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblPuesto.setText("Puesto");
        panelAntecedentesLaborales.add(lblPuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, -1, -1));

        txtPuesto1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtPuesto1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        txtPuesto1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtPuesto1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPuesto1KeyTyped(evt);
            }
        });
        panelAntecedentesLaborales.add(txtPuesto1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 120, 35));

        txtPuesto2.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtPuesto2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        txtPuesto2.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtPuesto2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPuesto2KeyTyped(evt);
            }
        });
        panelAntecedentesLaborales.add(txtPuesto2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 85, 120, 35));

        txtPuesto3.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtPuesto3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        txtPuesto3.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtPuesto3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPuesto3KeyTyped(evt);
            }
        });
        panelAntecedentesLaborales.add(txtPuesto3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 120, 35));

        lblTiempoLaborado.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        lblTiempoLaborado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTiempoLaborado.setText("Tiempo laborado en la empresa");
        panelAntecedentesLaborales.add(lblTiempoLaborado, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 295, 20));

        lbl1anio.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        lbl1anio.setText("1 año o menos");
        panelAntecedentesLaborales.add(lbl1anio, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 30, -1, -1));

        lbl2anios.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        lbl2anios.setText("2 a 4 años");
        panelAntecedentesLaborales.add(lbl2anios, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, -1, -1));

        lbl5anios.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        lbl5anios.setText("5 a 10 años");
        panelAntecedentesLaborales.add(lbl5anios, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 30, -1, -1));

        lbl11anios.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        lbl11anios.setText("Más de 10 años");
        panelAntecedentesLaborales.add(lbl11anios, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 30, -1, -1));

        panelRbtn1_1.setBackground(new java.awt.Color(255, 255, 255));
        panelRbtn1_1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(0, 0, 0)));

        rbtn1_1.setBackground(new java.awt.Color(255, 255, 255));
        grbtn_empresa1.add(rbtn1_1);
        rbtn1_1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rbtn1_1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rbtn1_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn1_1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRbtn1_1Layout = new javax.swing.GroupLayout(panelRbtn1_1);
        panelRbtn1_1.setLayout(panelRbtn1_1Layout);
        panelRbtn1_1Layout.setHorizontalGroup(
            panelRbtn1_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRbtn1_1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(rbtn1_1)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        panelRbtn1_1Layout.setVerticalGroup(
            panelRbtn1_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRbtn1_1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbtn1_1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelAntecedentesLaborales.add(panelRbtn1_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 50, 75, 35));

        panelRbtn1_2.setBackground(new java.awt.Color(255, 255, 255));
        panelRbtn1_2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));

        rbtn1_2.setBackground(new java.awt.Color(255, 255, 255));
        grbtn_empresa2.add(rbtn1_2);
        rbtn1_2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rbtn1_2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rbtn1_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn1_2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRbtn1_2Layout = new javax.swing.GroupLayout(panelRbtn1_2);
        panelRbtn1_2.setLayout(panelRbtn1_2Layout);
        panelRbtn1_2Layout.setHorizontalGroup(
            panelRbtn1_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRbtn1_2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(rbtn1_2)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        panelRbtn1_2Layout.setVerticalGroup(
            panelRbtn1_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRbtn1_2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbtn1_2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelAntecedentesLaborales.add(panelRbtn1_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 85, 75, 35));

        panelRbtn1_3.setBackground(new java.awt.Color(255, 255, 255));
        panelRbtn1_3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));

        rbtn1_3.setBackground(new java.awt.Color(255, 255, 255));
        grbtn_empresa3.add(rbtn1_3);
        rbtn1_3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rbtn1_3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rbtn1_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn1_3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRbtn1_3Layout = new javax.swing.GroupLayout(panelRbtn1_3);
        panelRbtn1_3.setLayout(panelRbtn1_3Layout);
        panelRbtn1_3Layout.setHorizontalGroup(
            panelRbtn1_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRbtn1_3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(rbtn1_3)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        panelRbtn1_3Layout.setVerticalGroup(
            panelRbtn1_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRbtn1_3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbtn1_3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelAntecedentesLaborales.add(panelRbtn1_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 120, 75, 35));

        panelRbtn2_1.setBackground(new java.awt.Color(255, 255, 255));
        panelRbtn2_1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(0, 0, 0)));

        rbtn2_1.setBackground(new java.awt.Color(255, 255, 255));
        grbtn_empresa1.add(rbtn2_1);
        rbtn2_1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rbtn2_1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rbtn2_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn2_1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRbtn2_1Layout = new javax.swing.GroupLayout(panelRbtn2_1);
        panelRbtn2_1.setLayout(panelRbtn2_1Layout);
        panelRbtn2_1Layout.setHorizontalGroup(
            panelRbtn2_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRbtn2_1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(rbtn2_1)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        panelRbtn2_1Layout.setVerticalGroup(
            panelRbtn2_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRbtn2_1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbtn2_1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelAntecedentesLaborales.add(panelRbtn2_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 50, 60, 35));

        panelRbtn2_2.setBackground(new java.awt.Color(255, 255, 255));
        panelRbtn2_2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));

        rbtn2_2.setBackground(new java.awt.Color(255, 255, 255));
        grbtn_empresa2.add(rbtn2_2);
        rbtn2_2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rbtn2_2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rbtn2_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn2_2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRbtn2_2Layout = new javax.swing.GroupLayout(panelRbtn2_2);
        panelRbtn2_2.setLayout(panelRbtn2_2Layout);
        panelRbtn2_2Layout.setHorizontalGroup(
            panelRbtn2_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRbtn2_2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(rbtn2_2)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        panelRbtn2_2Layout.setVerticalGroup(
            panelRbtn2_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRbtn2_2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbtn2_2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelAntecedentesLaborales.add(panelRbtn2_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 85, 60, 35));

        panelRbtn2_3.setBackground(new java.awt.Color(255, 255, 255));
        panelRbtn2_3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));

        rbtn2_3.setBackground(new java.awt.Color(255, 255, 255));
        grbtn_empresa3.add(rbtn2_3);
        rbtn2_3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rbtn2_3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rbtn2_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn2_3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRbtn2_3Layout = new javax.swing.GroupLayout(panelRbtn2_3);
        panelRbtn2_3.setLayout(panelRbtn2_3Layout);
        panelRbtn2_3Layout.setHorizontalGroup(
            panelRbtn2_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRbtn2_3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(rbtn2_3)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        panelRbtn2_3Layout.setVerticalGroup(
            panelRbtn2_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRbtn2_3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbtn2_3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelAntecedentesLaborales.add(panelRbtn2_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 120, 60, 35));

        panelRbtn3_1.setBackground(new java.awt.Color(255, 255, 255));
        panelRbtn3_1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(0, 0, 0)));

        rbtn3_1.setBackground(new java.awt.Color(255, 255, 255));
        grbtn_empresa1.add(rbtn3_1);
        rbtn3_1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rbtn3_1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rbtn3_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn3_1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRbtn3_1Layout = new javax.swing.GroupLayout(panelRbtn3_1);
        panelRbtn3_1.setLayout(panelRbtn3_1Layout);
        panelRbtn3_1Layout.setHorizontalGroup(
            panelRbtn3_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRbtn3_1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(rbtn3_1)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        panelRbtn3_1Layout.setVerticalGroup(
            panelRbtn3_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRbtn3_1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rbtn3_1)
                .addContainerGap())
        );

        panelAntecedentesLaborales.add(panelRbtn3_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, 70, 35));

        panelRbtn3_2.setBackground(new java.awt.Color(255, 255, 255));
        panelRbtn3_2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));

        rbtn3_2.setBackground(new java.awt.Color(255, 255, 255));
        grbtn_empresa2.add(rbtn3_2);
        rbtn3_2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rbtn3_2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rbtn3_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn3_2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRbtn3_2Layout = new javax.swing.GroupLayout(panelRbtn3_2);
        panelRbtn3_2.setLayout(panelRbtn3_2Layout);
        panelRbtn3_2Layout.setHorizontalGroup(
            panelRbtn3_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRbtn3_2Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(rbtn3_2)
                .addGap(24, 24, 24))
        );
        panelRbtn3_2Layout.setVerticalGroup(
            panelRbtn3_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRbtn3_2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rbtn3_2)
                .addContainerGap())
        );

        panelAntecedentesLaborales.add(panelRbtn3_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 85, 70, 35));

        panelRbtn3_3.setBackground(new java.awt.Color(255, 255, 255));
        panelRbtn3_3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));

        rbtn3_3.setBackground(new java.awt.Color(255, 255, 255));
        grbtn_empresa3.add(rbtn3_3);
        rbtn3_3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rbtn3_3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rbtn3_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn3_3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRbtn3_3Layout = new javax.swing.GroupLayout(panelRbtn3_3);
        panelRbtn3_3.setLayout(panelRbtn3_3Layout);
        panelRbtn3_3Layout.setHorizontalGroup(
            panelRbtn3_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRbtn3_3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(rbtn3_3)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        panelRbtn3_3Layout.setVerticalGroup(
            panelRbtn3_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRbtn3_3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbtn3_3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelAntecedentesLaborales.add(panelRbtn3_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 120, 70, 35));

        panelRbtn4_1.setBackground(new java.awt.Color(255, 255, 255));
        panelRbtn4_1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(0, 0, 0)));

        rbtn4_1.setBackground(new java.awt.Color(255, 255, 255));
        grbtn_empresa1.add(rbtn4_1);
        rbtn4_1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rbtn4_1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rbtn4_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn4_1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRbtn4_1Layout = new javax.swing.GroupLayout(panelRbtn4_1);
        panelRbtn4_1.setLayout(panelRbtn4_1Layout);
        panelRbtn4_1Layout.setHorizontalGroup(
            panelRbtn4_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRbtn4_1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(rbtn4_1)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        panelRbtn4_1Layout.setVerticalGroup(
            panelRbtn4_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRbtn4_1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbtn4_1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelAntecedentesLaborales.add(panelRbtn4_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 50, 90, 35));

        panelRbtn4_2.setBackground(new java.awt.Color(255, 255, 255));
        panelRbtn4_2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));

        rbtn4_2.setBackground(new java.awt.Color(255, 255, 255));
        grbtn_empresa2.add(rbtn4_2);
        rbtn4_2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rbtn4_2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rbtn4_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn4_2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRbtn4_2Layout = new javax.swing.GroupLayout(panelRbtn4_2);
        panelRbtn4_2.setLayout(panelRbtn4_2Layout);
        panelRbtn4_2Layout.setHorizontalGroup(
            panelRbtn4_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRbtn4_2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(rbtn4_2)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        panelRbtn4_2Layout.setVerticalGroup(
            panelRbtn4_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRbtn4_2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbtn4_2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelAntecedentesLaborales.add(panelRbtn4_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 85, 90, 35));

        panelRbtn4_3.setBackground(new java.awt.Color(255, 255, 255));
        panelRbtn4_3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));

        rbtn4_3.setBackground(new java.awt.Color(255, 255, 255));
        grbtn_empresa3.add(rbtn4_3);
        rbtn4_3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rbtn4_3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rbtn4_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn4_3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRbtn4_3Layout = new javax.swing.GroupLayout(panelRbtn4_3);
        panelRbtn4_3.setLayout(panelRbtn4_3Layout);
        panelRbtn4_3Layout.setHorizontalGroup(
            panelRbtn4_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRbtn4_3Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(rbtn4_3)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        panelRbtn4_3Layout.setVerticalGroup(
            panelRbtn4_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRbtn4_3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbtn4_3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelAntecedentesLaborales.add(panelRbtn4_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 120, 90, 35));

        panelMenu2.add(panelAntecedentesLaborales, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 205, 566, 160));

        panelCartas.add(panelMenu2, "card2");

        panelMenu3.setBackground(new java.awt.Color(255, 255, 255));
        panelMenu3.setMinimumSize(new java.awt.Dimension(566, 365));
        panelMenu3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_AntecedentesGenerales.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_AntecedentesGenerales.setForeground(new java.awt.Color(31, 78, 121));
        lbl_AntecedentesGenerales.setText("ANTECEDENTES GENERALES");
        panelMenu3.add(lbl_AntecedentesGenerales, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        lbl_Familiares.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Familiares.setText("Familiares");
        panelMenu3.add(lbl_Familiares, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, 20));

        txtAreaFamiliares.setColumns(20);
        txtAreaFamiliares.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaFamiliares.setRows(5);
        jScrollPane3.setViewportView(txtAreaFamiliares);

        panelMenu3.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 260, 40));

        lbl_Medicos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Medicos.setText("Médicos");
        panelMenu3.add(lbl_Medicos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, 20));

        txtAreaMedicos.setColumns(20);
        txtAreaMedicos.setRows(5);
        jScrollPane4.setViewportView(txtAreaMedicos);

        panelMenu3.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 260, 40));

        lbl_Tratamientos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Tratamientos.setText("Tratamientos o medicamentos actuales");
        panelMenu3.add(lbl_Tratamientos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, 20));

        txtAreaTratamientos.setColumns(20);
        txtAreaTratamientos.setRows(5);
        jScrollPane5.setViewportView(txtAreaTratamientos);

        panelMenu3.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 260, 40));

        lbl_Laboratorios.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Laboratorios.setText("Laboratorios o estudios recientes");
        panelMenu3.add(lbl_Laboratorios, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, 20));

        txtAreaLaboratorios.setColumns(20);
        txtAreaLaboratorios.setRows(5);
        jScrollPane6.setViewportView(txtAreaLaboratorios);

        panelMenu3.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 260, 40));

        lbl_Quirurgicos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Quirurgicos.setText("Quirúrgicos");
        panelMenu3.add(lbl_Quirurgicos, new org.netbeans.lib.awtextra.AbsoluteConstraints(283, 30, -1, 20));

        txtAreaQuirurgicos.setColumns(20);
        txtAreaQuirurgicos.setRows(5);
        jScrollPane7.setViewportView(txtAreaQuirurgicos);

        panelMenu3.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(283, 50, 260, 40));

        lbl_Traumaticos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Traumaticos.setText("Traumáticos");
        panelMenu3.add(lbl_Traumaticos, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 100, -1, 20));

        txtAreaTraumaticos.setColumns(20);
        txtAreaTraumaticos.setRows(5);
        jScrollPane8.setViewportView(txtAreaTraumaticos);

        panelMenu3.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 120, 260, 40));

        lbl_Alergicos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Alergicos.setText("Alérgicos");
        panelMenu3.add(lbl_Alergicos, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 170, -1, 20));

        txtAreaAlergicos.setColumns(20);
        txtAreaAlergicos.setRows(5);
        jScrollPane9.setViewportView(txtAreaAlergicos);

        panelMenu3.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 190, 260, 40));

        lbl_Vicios.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Vicios.setText("Vicios y manías");
        panelMenu3.add(lbl_Vicios, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 240, -1, 20));

        txtAreaVicios.setColumns(20);
        txtAreaVicios.setRows(5);
        jScrollPane10.setViewportView(txtAreaVicios);

        panelMenu3.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 260, 260, 40));

        btn_Menu2.setBackground(new java.awt.Color(204, 204, 235));
        btn_Menu2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btn_Menu2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_btn_Menu2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_btn_Menu2.setText("<- Página 1");
        lbl_btn_Menu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btn_Menu2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_btn_Menu2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_btn_Menu2MouseExited(evt);
            }
        });
        btn_Menu2.add(lbl_btn_Menu2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 106, 35));

        panelMenu3.add(btn_Menu2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 320, 106, 35));

        panelCartas.add(panelMenu3, "card3");

        panelFormulario.add(panelCartas, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 10, 566, 365));

        panel_Diagnostico.setBackground(new java.awt.Color(255, 255, 255));
        panel_Diagnostico.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblDiagnostico.setBackground(new java.awt.Color(255, 255, 255));
        lblDiagnostico.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblDiagnostico.setForeground(new java.awt.Color(31, 78, 121));
        lblDiagnostico.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDiagnostico.setText("¿Previamente ha tenido diagnóstico de alguna enfermedad relacionada al trabajo?");
        panel_Diagnostico.add(lblDiagnostico, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 25));

        panelFormulario.add(panel_Diagnostico, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 380, 1080, 25));

        txtDiagnostico.setColumns(20);
        txtDiagnostico.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtDiagnostico.setRows(5);
        jScrollPane1.setViewportView(txtDiagnostico);

        panelFormulario.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 410, 770, 60));

        tpanel_Contenidos.addTab("Formulario", panelFormulario);

        panelCombobox.setBackground(new java.awt.Color(255, 255, 255));
        panelCombobox.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Seleccione una Ficha de Preempleo");
        panelCombobox.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 1080, -1));

        lblTituloCombobox.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        lblTituloCombobox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTituloCombobox.setText("Título");
        panelCombobox.add(lblTituloCombobox, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, -1));

        jtPreempleo.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jtPreempleo.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jtPreempleo);

        panelCombobox.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 980, 170));

        btn_Ingresar.setBackground(new java.awt.Color(92, 92, 235));
        btn_Ingresar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        lbl_btnIngresar.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
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

        panelCombobox.add(btn_Ingresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 90, 90, 35));

        panelPaginacion.setBackground(new java.awt.Color(255, 255, 255));
        panelCombobox.add(panelPaginacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 980, 30));

        lbl_SeleccionPreempleo.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lbl_SeleccionPreempleo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lbl_SeleccionPreempleo.setPreferredSize(new java.awt.Dimension(300, 40));
        panelCombobox.add(lbl_SeleccionPreempleo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        lbl_TituloSeleccion.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lbl_TituloSeleccion.setText("Registro Seleccionado");
        panelCombobox.add(lbl_TituloSeleccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        tpanel_Contenidos.addTab("Combobox", panelCombobox);

        cont_Preempleo.add(tpanel_Contenidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 1080, 510));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cont_Preempleo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cont_Preempleo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            if (verificarEmpresas()){
                if (verificarPreempleado()){
                    if (verificarAntecedentes()){
                        try {
                            getAntecedentes();
                            getPreempleo();
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
                    JOptionPane.showMessageDialog(this, "Información de antecedentes incompleta o no válida", "Inserción de Datos", JOptionPane.INFORMATION_MESSAGE);
                }else
                    JOptionPane.showMessageDialog(this, "Información del preempleado incompleta o no válida", "Inserción de Datos", JOptionPane.INFORMATION_MESSAGE);
            }else
                JOptionPane.showMessageDialog(this, "Empresas no válidas", "Inserción de Datos", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_lbl_btn_ConfirmarMouseClicked

    private void txtEmpresa1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmpresa1KeyTyped
        if (txtEmpresa1.getText().length() >= 1 && (txtPuesto1.getText().length() >= 1)){
            rbtn1_1.setEnabled(true);
            rbtn2_1.setEnabled(true);
            rbtn3_1.setEnabled(true);
            rbtn4_1.setEnabled(true);
        } else if ((txtEmpresa1.getText().length() >= 0) && (txtPuesto1.getText().length() >= 0) && !(rbtn1_1.isSelected() || rbtn2_1.isSelected() || rbtn3_1.isSelected() || rbtn4_1.isSelected()))
            emp1 = true;
        else {
            rbtn1_1.setEnabled(false);
            rbtn2_1.setEnabled(false);
            rbtn3_1.setEnabled(false);
            rbtn4_1.setEnabled(false);
            grbtn_empresa1.clearSelection();
            antecedentes.setTiempolaborado1("0");
            txtEmpresa2.setEnabled(false);
            txtPuesto2.setEnabled(false);
            txtEmpresa2.setText("");
            txtPuesto2.setText("");
            emp1 = false;
        }
    }//GEN-LAST:event_txtEmpresa1KeyTyped

    private void txtPuesto1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPuesto1KeyTyped
        if (txtEmpresa1.getText().length() >= 1 && (txtPuesto1.getText().length() >= 1)){
            rbtn1_1.setEnabled(true);
            rbtn2_1.setEnabled(true);
            rbtn3_1.setEnabled(true);
            rbtn4_1.setEnabled(true);
        }
        else {
            rbtn1_1.setEnabled(false);
            rbtn2_1.setEnabled(false);
            rbtn3_1.setEnabled(false);
            rbtn4_1.setEnabled(false);
            grbtn_empresa1.clearSelection();
            antecedentes.setTiempolaborado1("0");
            txtEmpresa2.setEnabled(false);
            txtPuesto2.setEnabled(false);
            txtEmpresa2.setText("");
            txtPuesto2.setText("");
            emp1 = false;
        }
    }//GEN-LAST:event_txtPuesto1KeyTyped

    private void txtEmpresa2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmpresa2KeyTyped
        if (txtEmpresa2.getText().length() >= 1 && (txtPuesto2.getText().length() >= 1) && (rbtn1_1.isEnabled())){
            rbtn1_2.setEnabled(true);
            rbtn2_2.setEnabled(true);
            rbtn3_2.setEnabled(true);
            rbtn4_2.setEnabled(true);
        }
        else {
            rbtn1_2.setEnabled(false);
            rbtn2_2.setEnabled(false);
            rbtn3_2.setEnabled(false);
            rbtn4_2.setEnabled(false);
            grbtn_empresa2.clearSelection();
            antecedentes.setTiempolaborado1("0");
            txtEmpresa3.setEnabled(false);
            txtPuesto3.setEnabled(false);
            txtEmpresa3.setText("");
            txtPuesto3.setText("");
            emp2 = false;
        }
    }//GEN-LAST:event_txtEmpresa2KeyTyped

    private void txtPuesto2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPuesto2KeyTyped
        if (txtEmpresa2.getText().length() >= 1 && (txtPuesto2.getText().length() >= 1) && (rbtn1_1.isEnabled())){
            rbtn1_2.setEnabled(true);
            rbtn2_2.setEnabled(true);
            rbtn3_2.setEnabled(true);
            rbtn4_2.setEnabled(true);
        }
        else {
            rbtn1_2.setEnabled(false);
            rbtn2_2.setEnabled(false);
            rbtn3_2.setEnabled(false);
            rbtn4_2.setEnabled(false);
            grbtn_empresa2.clearSelection();
            antecedentes.setTiempolaborado2("0");
            txtEmpresa3.setEnabled(false);
            txtPuesto3.setEnabled(false);
            txtEmpresa3.setText("");
            txtPuesto3.setText("");
            emp2 = false;
        }
    }//GEN-LAST:event_txtPuesto2KeyTyped

    private void txtEmpresa3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmpresa3KeyTyped
        if (txtEmpresa2.getText().length() >= 1 && (txtPuesto2.getText().length() >= 1) && (rbtn1_1.isEnabled())){
            rbtn1_3.setEnabled(true);
            rbtn2_3.setEnabled(true);
            rbtn3_3.setEnabled(true);
            rbtn4_3.setEnabled(true);
        }
        else {
            rbtn1_3.setEnabled(false);
            rbtn2_3.setEnabled(false);
            rbtn3_3.setEnabled(false);
            rbtn4_3.setEnabled(false);
            grbtn_empresa3.clearSelection();
            antecedentes.setTiempolaborado3("0");
            emp3 = false;
        }
    }//GEN-LAST:event_txtEmpresa3KeyTyped

    private void txtPuesto3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPuesto3KeyTyped
        if (txtEmpresa2.getText().length() >= 1 && (txtPuesto2.getText().length() >= 1) && (rbtn1_1.isEnabled())){
            rbtn1_3.setEnabled(true);
            rbtn2_3.setEnabled(true);
            rbtn3_3.setEnabled(true);
            rbtn4_3.setEnabled(true);
        }
        else {
            rbtn1_3.setEnabled(false);
            rbtn2_3.setEnabled(false);
            rbtn3_3.setEnabled(false);
            rbtn4_3.setEnabled(false);
            grbtn_empresa3.clearSelection();
            antecedentes.setTiempolaborado3("0");
            emp3 = false;
        }
    }//GEN-LAST:event_txtPuesto3KeyTyped

    private void rbtn1_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn1_1ActionPerformed
        emp1 = true;
        txtEmpresa2.setEnabled(true);
        txtPuesto2.setEnabled(true);
        antecedentes.setTiempolaborado1("1");
    }//GEN-LAST:event_rbtn1_1ActionPerformed

    private void rbtn2_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn2_1ActionPerformed
        emp1 = true;
        txtEmpresa2.setEnabled(true);
        txtPuesto2.setEnabled(true);
        antecedentes.setTiempolaborado1("2");
    }//GEN-LAST:event_rbtn2_1ActionPerformed

    private void rbtn3_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn3_1ActionPerformed
        emp1 = true;
        txtEmpresa2.setEnabled(true);
        txtPuesto2.setEnabled(true);
        antecedentes.setTiempolaborado1("3");
    }//GEN-LAST:event_rbtn3_1ActionPerformed

    private void rbtn4_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn4_1ActionPerformed
        emp1 = true;
        txtEmpresa2.setEnabled(true);
        txtPuesto2.setEnabled(true);
        antecedentes.setTiempolaborado1("4");
    }//GEN-LAST:event_rbtn4_1ActionPerformed

    private void rbtn1_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn1_2ActionPerformed
        emp2 = true;
        txtEmpresa3.setEnabled(true);
        txtPuesto3.setEnabled(true);
        antecedentes.setTiempolaborado2("1");
    }//GEN-LAST:event_rbtn1_2ActionPerformed

    private void rbtn2_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn2_2ActionPerformed
        emp2 = true;
        txtEmpresa3.setEnabled(true);
        txtPuesto3.setEnabled(true);
        antecedentes.setTiempolaborado2("2");
    }//GEN-LAST:event_rbtn2_2ActionPerformed

    private void rbtn3_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn3_2ActionPerformed
        emp2 = true;
        txtEmpresa3.setEnabled(true);
        txtPuesto3.setEnabled(true);
        antecedentes.setTiempolaborado2("3");
    }//GEN-LAST:event_rbtn3_2ActionPerformed

    private void rbtn4_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn4_2ActionPerformed
        emp2 = true;
        txtEmpresa3.setEnabled(true);
        txtPuesto3.setEnabled(true);
        antecedentes.setTiempolaborado2("4");
    }//GEN-LAST:event_rbtn4_2ActionPerformed

    private void rbtn1_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn1_3ActionPerformed
        emp3 = true;
        antecedentes.setTiempolaborado3("1");
    }//GEN-LAST:event_rbtn1_3ActionPerformed

    private void rbtn2_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn2_3ActionPerformed
        emp3 = true;
        antecedentes.setTiempolaborado3("2");
    }//GEN-LAST:event_rbtn2_3ActionPerformed

    private void rbtn3_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn3_3ActionPerformed
        emp3 = true;
        antecedentes.setTiempolaborado3("3");
    }//GEN-LAST:event_rbtn3_3ActionPerformed

    private void rbtn4_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn4_3ActionPerformed
        emp3 = true;
        antecedentes.setTiempolaborado3("4");
    }//GEN-LAST:event_rbtn4_3ActionPerformed

    private void rbtn_SexoMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_SexoMActionPerformed
        preempleo.setSexo("M");
    }//GEN-LAST:event_rbtn_SexoMActionPerformed

    private void rbtn_SexoFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_SexoFActionPerformed
        preempleo.setSexo("F");
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
            try {
                setTabla();
            } catch (SQLException ex) {
                Logger.getLogger(Preempleo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ConnectException ex) {
                Logger.getLogger(Preempleo.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            try {
                setTabla();
            } catch (SQLException ex) {
                Logger.getLogger(Preempleo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ConnectException ex) {
                Logger.getLogger(Preempleo.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            if (lbl_SeleccionPreempleo.getText().length() > 0){
                if (contenidoActual.equals("Actualizar")){
                    tpanel_Contenidos.setSelectedIndex(1);
                    btn_Confirmar.setBackground(new Color(92,92,235));

                    btn_Confirmar.setVisible(true);
                }else if (contenidoActual.equals("Eliminar")){
                    tpanel_Contenidos.setSelectedIndex(1);
                    btn_Confirmar.setBackground(new Color(235,91,91));

                    btn_Confirmar.setVisible(true);            
                }
                setBuscarPreempleoAntecedentes(jtPreempleo.getValueAt(jtPreempleo.getSelectedRow(), 0).toString());
            }else
                JOptionPane.showMessageDialog(null, "Seleccione un registro de la tabla");
        } catch (SQLException ex) {
        Logger.getLogger(Preempleo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConnectException ex) {
            Logger.getLogger(Preempleo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_lbl_btnIngresarMouseClicked

    private void lbl_btn_Menu3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_Menu3MouseClicked
        panelCartas.removeAll();
        panelCartas.add(panelMenu3);
        panelCartas.repaint();
        panelCartas.revalidate();
        btn_Menu3.setBackground(util.colorCursorSale(btn_Menu3.getBackground()));
    }//GEN-LAST:event_lbl_btn_Menu3MouseClicked

    private void lbl_btn_Menu3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_Menu3MouseEntered
        btn_Menu3.setBackground(util.colorCursorEntra(btn_Menu3.getBackground()));
    }//GEN-LAST:event_lbl_btn_Menu3MouseEntered

    private void lbl_btn_Menu3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_Menu3MouseExited
        btn_Menu3.setBackground(util.colorCursorSale(btn_Menu3.getBackground()));
    }//GEN-LAST:event_lbl_btn_Menu3MouseExited

    private void lbl_btn_Menu2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_Menu2MouseEntered
        btn_Menu2.setBackground(util.colorCursorEntra(btn_Menu2.getBackground()));
    }//GEN-LAST:event_lbl_btn_Menu2MouseEntered

    private void lbl_btn_Menu2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_Menu2MouseExited
        btn_Menu2.setBackground(util.colorCursorSale(btn_Menu2.getBackground()));
    }//GEN-LAST:event_lbl_btn_Menu2MouseExited

    private void lbl_btn_Menu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_Menu2MouseClicked
        panelCartas.removeAll();
        panelCartas.add(panelMenu2);
        panelCartas.repaint();
        panelCartas.revalidate();
        btn_Menu2.setBackground(util.colorCursorSale(btn_Menu2.getBackground()));
    }//GEN-LAST:event_lbl_btn_Menu2MouseClicked

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel btn_Actualizar;
    public javax.swing.JPanel btn_Confirmar;
    public javax.swing.JPanel btn_Crear;
    public javax.swing.JPanel btn_Eliminar;
    public javax.swing.JPanel btn_Ingresar;
    private javax.swing.JPanel btn_Menu2;
    private javax.swing.JPanel btn_Menu3;
    public javax.swing.JPanel cont_Preempleo;
    private com.raven.datechooser.DateChooser fechaCSTP;
    private com.raven.datechooser.DateChooser fechaFUR;
    private com.raven.datechooser.DateChooser fechaMenarquia;
    public static javax.swing.ButtonGroup grbtn_Sexo;
    public static javax.swing.ButtonGroup grbtn_empresa1;
    public static javax.swing.ButtonGroup grbtn_empresa2;
    public static javax.swing.ButtonGroup grbtn_empresa3;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    public javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    public javax.swing.JTable jtPreempleo;
    public javax.swing.JLabel lbl11anios;
    public javax.swing.JLabel lbl1anio;
    public javax.swing.JLabel lbl2anios;
    public javax.swing.JLabel lbl5anios;
    public javax.swing.JLabel lblAB;
    public javax.swing.JLabel lblAG;
    public javax.swing.JLabel lblAntecedentesGi;
    public javax.swing.JLabel lblAntecedentesLaborales;
    public javax.swing.JLabel lblCSTP;
    public javax.swing.JLabel lblClinica;
    public javax.swing.JLabel lblDiagnostico;
    public javax.swing.JLabel lblEmpresa;
    public javax.swing.JLabel lblEstadoCivil;
    public javax.swing.JLabel lblFechaMod;
    public javax.swing.JLabel lblFur;
    public javax.swing.JLabel lblG;
    public javax.swing.JLabel lblHM;
    public javax.swing.JLabel lblHV;
    public javax.swing.JLabel lblIdentificacion;
    public javax.swing.JLabel lblMPF;
    public javax.swing.JLabel lblMenarquia;
    public javax.swing.JLabel lblNivelAcademico;
    public javax.swing.JLabel lblNombre;
    public javax.swing.JLabel lblP;
    public javax.swing.JLabel lblPuesto;
    public javax.swing.JLabel lblPuestoAplica;
    public javax.swing.JLabel lblSeguridad;
    public javax.swing.JLabel lblSexo;
    public javax.swing.JLabel lblTelefono;
    public javax.swing.JLabel lblTiempoLaborado;
    public javax.swing.JLabel lblTituloCombobox;
    public javax.swing.JLabel lblTituloPrincipal;
    public javax.swing.JLabel lblTitulos;
    private javax.swing.JLabel lbl_Alergicos;
    private javax.swing.JLabel lbl_AntecedentesGenerales;
    public javax.swing.JLabel lbl_BtnEliminar;
    private javax.swing.JLabel lbl_Familiares;
    public javax.swing.JLabel lbl_InicioInicio;
    private javax.swing.JLabel lbl_Laboratorios;
    private javax.swing.JLabel lbl_Medicos;
    private javax.swing.JLabel lbl_Quirurgicos;
    private javax.swing.JLabel lbl_SeleccionPreempleo;
    private javax.swing.JLabel lbl_TituloSeleccion;
    private javax.swing.JLabel lbl_Tratamientos;
    private javax.swing.JLabel lbl_Traumaticos;
    private javax.swing.JLabel lbl_Vicios;
    public javax.swing.JLabel lbl_btnActualizar;
    public javax.swing.JLabel lbl_btnCrear;
    public javax.swing.JLabel lbl_btnIngresar;
    public javax.swing.JLabel lbl_btn_Confirmar;
    private javax.swing.JLabel lbl_btn_Menu2;
    private javax.swing.JLabel lbl_btn_Menu3;
    public javax.swing.JPanel panelAG;
    private javax.swing.JPanel panelAntecedentesGino;
    private javax.swing.JPanel panelAntecedentesLaborales;
    public javax.swing.JPanel panelBotonesCRUD;
    private javax.swing.JPanel panelCartas;
    public javax.swing.JPanel panelCombobox;
    public javax.swing.JPanel panelEdicion;
    public javax.swing.JPanel panelFecha;
    public javax.swing.JPanel panelFormulario;
    public javax.swing.JPanel panelInicio;
    public javax.swing.JPanel panelMenu1;
    public javax.swing.JPanel panelMenu2;
    private javax.swing.JPanel panelMenu3;
    private javax.swing.JPanel panelPaginacion;
    public javax.swing.JPanel panelRbtn1_1;
    public javax.swing.JPanel panelRbtn1_2;
    public javax.swing.JPanel panelRbtn1_3;
    public javax.swing.JPanel panelRbtn2_1;
    public javax.swing.JPanel panelRbtn2_2;
    public javax.swing.JPanel panelRbtn2_3;
    public javax.swing.JPanel panelRbtn3_1;
    public javax.swing.JPanel panelRbtn3_2;
    public javax.swing.JPanel panelRbtn3_3;
    public javax.swing.JPanel panelRbtn4_1;
    public javax.swing.JPanel panelRbtn4_2;
    public javax.swing.JPanel panelRbtn4_3;
    public javax.swing.JPanel panelSeguridad;
    public javax.swing.JPanel panelTitulos;
    private javax.swing.JPanel panel_Diagnostico;
    public javax.swing.JRadioButton rbtn1_1;
    public javax.swing.JRadioButton rbtn1_2;
    public javax.swing.JRadioButton rbtn1_3;
    public javax.swing.JRadioButton rbtn2_1;
    public javax.swing.JRadioButton rbtn2_2;
    public javax.swing.JRadioButton rbtn2_3;
    public javax.swing.JRadioButton rbtn3_1;
    public javax.swing.JRadioButton rbtn3_2;
    public javax.swing.JRadioButton rbtn3_3;
    public javax.swing.JRadioButton rbtn4_1;
    public javax.swing.JRadioButton rbtn4_2;
    public javax.swing.JRadioButton rbtn4_3;
    public javax.swing.JRadioButton rbtn_SexoF;
    public javax.swing.JRadioButton rbtn_SexoM;
    public javax.swing.JPanel tablaTitulos;
    public javax.swing.JTabbedPane tpanel_Contenidos;
    public javax.swing.JTextField txtAB;
    private javax.swing.JTextArea txtAreaAlergicos;
    private javax.swing.JTextArea txtAreaFamiliares;
    private javax.swing.JTextArea txtAreaLaboratorios;
    private javax.swing.JTextArea txtAreaMedicos;
    private javax.swing.JTextArea txtAreaQuirurgicos;
    private javax.swing.JTextArea txtAreaTratamientos;
    private javax.swing.JTextArea txtAreaTraumaticos;
    private javax.swing.JTextArea txtAreaVicios;
    public javax.swing.JTextField txtCSTP;
    public javax.swing.JTextArea txtDiagnostico;
    public javax.swing.JTextField txtDireccion;
    public javax.swing.JTextField txtEdad;
    public javax.swing.JTextField txtEmpresa1;
    public javax.swing.JTextField txtEmpresa2;
    public javax.swing.JTextField txtEmpresa3;
    public javax.swing.JTextField txtEstadoCivil;
    public javax.swing.JTextField txtFUR;
    public javax.swing.JTextField txtFecha;
    public javax.swing.JTextField txtG;
    public javax.swing.JTextField txtHM;
    public javax.swing.JTextField txtHV;
    public javax.swing.JTextField txtIdentificacion;
    public javax.swing.JTextField txtMPF;
    public javax.swing.JTextField txtMenarquia;
    public javax.swing.JTextField txtNivelAcademico;
    public javax.swing.JTextField txtNombre;
    public javax.swing.JTextField txtP;
    public javax.swing.JLabel txtPreempleoId;
    public javax.swing.JTextField txtPuesto1;
    public javax.swing.JTextField txtPuesto2;
    public javax.swing.JTextField txtPuesto3;
    public javax.swing.JTextField txtPuestoAplica;
    public javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        Object evt = e.getSource();
        if (evt.equals(filasPermitidas))
        paginador.eventComboBox(filasPermitidas);
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        paginador.actualizarBotonesPaginacion();
    }

}
