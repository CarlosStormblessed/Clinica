package Vistas;

import javax.swing.JPanel;
import Utilitarios.Utilitarios;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.ButtonModel;
import javax.swing.table.DefaultTableModel;
import Controladores.PreempleoCtrl;
import Controladores.AntecedentesCtrl;
import Modelos.AntecedentesMod;
import Modelos.PreempleoMod;
import java.awt.Color;
import java.net.ConnectException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;

public class Preempleo extends javax.swing.JFrame {

    Utilitarios util = new Utilitarios();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime now = LocalDateTime.now();
    String sexo = "", clinicaId = "", contenidoActual;
    int tiempoLaborado1 = 0, tiempoLaborado2 = 0, tiempoLaborado3 = 0;
    String antecedentesId = "0";
    boolean emp1=false, emp2=false, emp3=false;
    public String nombreContenido = "Preempleo";
    public Preempleo() {
        initComponents();
        contenidoActual = "Inicio";
        tpanel_Contenidos.setSelectedIndex(0);
        btn_Confirmar.setVisible(false);
        lbl_btn_Confirmar.setEnabled(false);
        reset();
    }
    
    private void reset(){
        lblAG.setIcon(util.construirImagen("/Imagenes/AG_logo.png", lblAG.getWidth(), lblAG.getHeight()));
        resetAntecedentes();
        resetPreempleado();
        construirEtiquetas ();
    }
    
    private void resetPreempleado(){
        txtPreempleoId.setText("");
        txtNombre.setText("");
        sexo = "";
        setSexo(sexo);
        txtIdentificacion.setText("");
        txtEdad.setText("");
        txtEstadoCivil.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtNivelAcademico.setText("");
        txtPuestoAplica.setText("");
        antecedentesId = "";
        clinicaId = "";
    }
    
    private void resetAntecedentes(){
        antecedentesId = "0";
        txtMenarquia.setText("");
        txtFUR.setText("");
        txtCSTP.setText("");
        txtHV.setText("");
        txtHM.setText("");
        txtG.setText("");
        txtP.setText("");
        txtAB.setText("");
        txtMPF.setText("");
        txtFecha.setText("");
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
        tiempoLaborado1 = 0;
        tiempoLaborado2 = 0;
        tiempoLaborado3 = 0;
        setTiempoLaborado();
        txtDiagnostico.setText("");
    }
    
    private void construirEtiquetas(){
        lblTitulos.setText("<html><center>Formato<br><b>FICHA MÉDICA PRE-EMPLEO</b><br>CÓDIGO12345</center></html>");
        lblSeguridad.setText("<html><center>Seguridad Industrial y Salud Ocupacional</center></html>");
        lblFechaMod.setText("<html><center>Fecha de<br>modificacion:<br>"+ util.convertirFechaGUI(now.format(dtf)) + "</center></html>");
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
        if ((txtFecha.getText().length()>0) && (txtIdentificacion.getText().length()>0) && (txtNombre.getText().length()>0) && (util.verificarNumero(txtEdad.getText())) && (txtEstadoCivil.getText().length()>0) && (txtDireccion.getText().length()>0) && (txtTelefono.getText().length()>0) && (txtNivelAcademico.getText().length()>0) && (txtPuestoAplica.getText().length()>0) && (verificarSexo()))
            valido = true;
        else valido = false;
        return valido;
    }
    
    private boolean verificarAntecedentes(){
        boolean valido = false;
        if (util.verificarNumero(txtMenarquia.getText()) && (util.verificarNumero(txtFUR.getText())) && (util.verificarNumero(txtFUR.getText())) && (util.verificarNumero(txtG.getText())) && (util.verificarNumero(txtP.getText())) && (util.verificarNumero(txtCSTP.getText())) && (util.verificarNumero(txtHV.getText())) && (util.verificarNumero(txtHM.getText())) && (util.verificarNumero(txtAB.getText())) && (txtDiagnostico.getText().length()>0))
                valido = true;
        return valido;
    }
    
    public JPanel getContenido(){
        return cont_Preempleo;
    }
    
    private void setTiempoLaborado(){
        switch (tiempoLaborado1){
            case 0: 
                grbtn_empresa1.clearSelection();
                break;
            case 1:
                rbtn1_1.setSelected(true);
                break;
            case 2:
                rbtn2_1.setSelected(true);
                break;
            case 3:
                rbtn3_1.setSelected(true);
                break;
            case 4:
                rbtn4_1.setSelected(true);
                break;
            default:
                grbtn_empresa1.clearSelection();
                break;
        }
        switch (tiempoLaborado2){
            case 0: 
                grbtn_empresa2.clearSelection();
                break;
            case 1: 
                rbtn1_2.setSelected(true);
                break;
            case 2:
                rbtn2_2.setSelected(true);
                break;
            case 3:
                rbtn3_2.setSelected(true);
                break;
            case 4:
                rbtn4_2.setSelected(true);
                break;
            default:
                grbtn_empresa2.clearSelection();
                break;
        }
        switch (tiempoLaborado3){
            case 0: 
                grbtn_empresa3.clearSelection();
                break;
            case 1: 
                rbtn1_3.setSelected(true);
                break;
            case 2:
                rbtn2_3.setSelected(true);
                break;
            case 3:
                rbtn3_3.setSelected(true);
                break;
            case 4:
                rbtn4_3.setSelected(true);
                break;
            default:
                grbtn_empresa3.clearSelection();
                break;
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
    
    private AntecedentesMod getAntecedentes(){
        AntecedentesMod antecedentes = new AntecedentesMod();
        antecedentes.setId(antecedentesId);
        antecedentes.setMenarquia(txtMenarquia.getText());
        antecedentes.setFur(txtFUR.getText());
        antecedentes.setCstp(txtCSTP.getText());
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
        antecedentes.setTiempolaborado1(String.valueOf(tiempoLaborado1));
        antecedentes.setTiempolaborado2(String.valueOf(tiempoLaborado2));
        antecedentes.setTiempolaborado3(String.valueOf(tiempoLaborado3));
        antecedentes.setDiagnostico(txtDiagnostico.getText());
        antecedentes.setEmpleadoId("0");
        return antecedentes;
    }
    
    private void setAntecedentes(AntecedentesMod antecedentes){
        antecedentesId = antecedentes.getId();
        txtMenarquia.setText(antecedentes.getMenarquia());
        txtFUR.setText(antecedentes.getFur());
        txtCSTP.setText(antecedentes.getCstp());
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
        tiempoLaborado1 = Integer.valueOf(antecedentes.getTiempolaborado1());
        tiempoLaborado2 = Integer.valueOf(antecedentes.getTiempolaborado2());
        tiempoLaborado3 = Integer.valueOf(antecedentes.getTiempolaborado3());
        setTiempoLaborado();
        txtDiagnostico.setText(antecedentes.getDiagnostico());
    }
    
    private PreempleoMod getPreempleo(){
        PreempleoMod preempleo = new PreempleoMod();
        preempleo.setId(txtPreempleoId.getText());
        preempleo.setFecha(util.convertirFechaSQL(txtFecha.getText()));
        preempleo.setNombre(txtNombre.getText());
        preempleo.setSexo(sexo);
        preempleo.setIdentificacion(txtIdentificacion.getText());
        preempleo.setEdad(txtEdad.getText());
        preempleo.setEstadoCivil(txtEstadoCivil.getText());
        preempleo.setDireccion(txtDireccion.getText());
        preempleo.setTelefono(txtTelefono.getText());
        preempleo.setNivelAcademico(txtNivelAcademico.getText());
        preempleo.setPuestoAplica(txtPuestoAplica.getText());
        preempleo.setClinicaId("1");
        preempleo.setAntecedentesId(antecedentesId);
        
        return preempleo;
    }
    
    private void setPreempleo(PreempleoMod preempleo){
        txtPreempleoId.setText(preempleo.getId());
        txtFecha.setText(util.convertirFechaSQL(preempleo.getFecha()));
        txtNombre.setText(preempleo.getNombre());
        sexo = preempleo.getSexo();
        setSexo(sexo);
        txtIdentificacion.setText(preempleo.getIdentificacion());
        txtEdad.setText(preempleo.getEdad());
        txtEstadoCivil.setText(preempleo.getEstadoCivil());
        txtDireccion.setText(preempleo.getDireccion());
        txtTelefono.setText(preempleo.getTelefono());
        txtNivelAcademico.setText(preempleo.getNivelAcademico());
        txtPuestoAplica.setText(preempleo.getPuestoAplica());
        antecedentesId = preempleo.getAntecedentesId();
        clinicaId = preempleo.getClinicaId();
    }
    
    private void setTabla() throws SQLException, ConnectException{
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
    }
    
    private void setCombobox(String[] valores){
        cbPreempleoId.removeAllItems();
        for (int i = 0; i < valores.length; i++){
            cbPreempleoId.addItem(valores[i]);
        }
    }
    
    private void setBuscarPreempleoAntecedentes(String valorBuscar) throws SQLException, ConnectException{
        PreempleoMod preempleo = new PreempleoMod();
        PreempleoCtrl lista1 = new PreempleoCtrl();
        AntecedentesMod antecedentes = new AntecedentesMod();
        preempleo = lista1.buscarFila(valorBuscar);
        setPreempleo(preempleo);
        AntecedentesCtrl lista2 = new AntecedentesCtrl();
        antecedentes = lista2.buscarFila(preempleo.getAntecedentesId());
        setAntecedentes(antecedentes);
    }
    
    private void crear() throws SQLException{
        AntecedentesCtrl ant = new AntecedentesCtrl();
        PreempleoCtrl preemp = new PreempleoCtrl();
        try {
            int res1 = 0, res2 = 0;
            res1 = ant.Crear(getAntecedentes());
            if (res1 > 0){
                antecedentesId = ant.getMaxId();
                res2 = preemp.Crear(getPreempleo());
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
            AntecedentesCtrl ant = new AntecedentesCtrl();
            PreempleoCtrl preemp = new PreempleoCtrl();
            int res1 = 0, res2 = 0;
            res1 = ant.Actualizar(getAntecedentes());
            if (res1 > 0){
                res2 = preemp.Actualizar(getPreempleo());
            }
            if ((res1 > 0) && (res2 > 0)) {
                JOptionPane.showMessageDialog(this, "REGISTRO ACTUALIZADO CON EXITO", "Inserción de Datos", JOptionPane.INFORMATION_MESSAGE);
                reset();
            } else {
                JOptionPane.showMessageDialog(this, "ERROR AL ACTUALIZAR ANTECEDENTES O FICHA DE PREEMPLEO. COMUNIQUESE CON TI", "Inserción de Datos", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR " + e.toString() + " AL ACTUALIZAR EL REGISTRO. COMUNIQUESE CON TI", "Inserción de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminar() throws SQLException{
        try {
            AntecedentesCtrl ant = new AntecedentesCtrl();
            PreempleoCtrl preemp = new PreempleoCtrl();
            int res1 = 0, res2 = 0;
            res1 = ant.Eliminar(getAntecedentes());
            if (res1 > 0){
                res2 = preemp.Eliminar(getPreempleo());
            }
            if ((res1 > 0) && (res2 > 0)) {
                JOptionPane.showMessageDialog(this, "REGISTRO ELIMINADO CON EXITO", "Inserción de Datos", JOptionPane.INFORMATION_MESSAGE);
                reset();
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
        panelMenu2 = new javax.swing.JPanel();
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
        txtMPF = new javax.swing.JTextField();
        lblDiagnostico = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDiagnostico = new javax.swing.JTextArea();
        panelCombobox = new javax.swing.JPanel();
        cbPreempleoId = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        lblTituloCombobox = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtPreempleo = new javax.swing.JTable();
        btn_Ingresar = new javax.swing.JPanel();
        lbl_btnIngresar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
            .addComponent(lbl_btnCrear, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
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
        panelMenu1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 450, 35));

        lblNombre.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblNombre.setText("Nombre");
        panelMenu1.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, -1, 20));

        lblClinica.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblClinica.setText("Clínica de Atención: Sidegua");
        panelMenu1.add(lblClinica, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 268, 38));

        txtFecha.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtFecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelMenu1.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 147, 35));

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel3.setText("Fecha");
        panelMenu1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, 20));

        lblSexo.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblSexo.setText("Sexo");
        panelMenu1.add(lblSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 180, -1, 20));

        jLabel4.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel4.setText("Edad");
        panelMenu1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 180, -1, 20));

        txtEdad.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtEdad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelMenu1.add(txtEdad, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 200, 67, 35));

        lblIdentificacion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblIdentificacion.setText("No. de Identificación");
        panelMenu1.add(lblIdentificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 60, -1, 20));

        txtIdentificacion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtIdentificacion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelMenu1.add(txtIdentificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 80, 300, 35));

        lblEstadoCivil.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblEstadoCivil.setText("Estado Civil");
        panelMenu1.add(lblEstadoCivil, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 180, -1, 20));

        txtEstadoCivil.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtEstadoCivil.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelMenu1.add(txtEstadoCivil, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 200, 290, 35));

        jLabel5.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel5.setText("Dirección");
        panelMenu1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, -1, 20));

        txtDireccion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtDireccion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelMenu1.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 357, 35));

        txtTelefono.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtTelefono.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelMenu1.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 260, 90, 35));

        lblTelefono.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblTelefono.setText("Teléfono");
        panelMenu1.add(lblTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 240, -1, 20));

        lblNivelAcademico.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblNivelAcademico.setText("Nivel Académico");
        panelMenu1.add(lblNivelAcademico, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, -1, -1));

        txtNivelAcademico.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtNivelAcademico.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelMenu1.add(txtNivelAcademico, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, 207, 35));

        lblPuestoAplica.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblPuestoAplica.setText("Puesto al que aplica");
        panelMenu1.add(lblPuestoAplica, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 300, -1, 20));

        txtPuestoAplica.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtPuestoAplica.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelMenu1.add(txtPuestoAplica, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 320, 240, 35));

        rbtn_SexoM.setBackground(new java.awt.Color(255, 255, 255));
        grbtn_Sexo.add(rbtn_SexoM);
        rbtn_SexoM.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        rbtn_SexoM.setText("M");
        rbtn_SexoM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_SexoMActionPerformed(evt);
            }
        });
        panelMenu1.add(rbtn_SexoM, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, -1, -1));

        rbtn_SexoF.setBackground(new java.awt.Color(255, 255, 255));
        grbtn_Sexo.add(rbtn_SexoF);
        rbtn_SexoF.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        rbtn_SexoF.setText("F");
        rbtn_SexoF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_SexoFActionPerformed(evt);
            }
        });
        panelMenu1.add(rbtn_SexoF, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 200, -1, -1));

        panelFormulario.add(panelMenu1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 490, 370));

        panelMenu2.setBackground(new java.awt.Color(255, 255, 255));
        panelMenu2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblAntecedentesGi.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblAntecedentesGi.setForeground(new java.awt.Color(31, 78, 121));
        lblAntecedentesGi.setText("ANTECEDENTES GINOCOBSTÉTRICOS");
        panelMenu2.add(lblAntecedentesGi, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        lblMenarquia.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblMenarquia.setText("Menarquia");
        panelMenu2.add(lblMenarquia, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, 20));

        txtMenarquia.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtMenarquia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelMenu2.add(txtMenarquia, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 209, 35));

        lblFur.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblFur.setText("FUR");
        panelMenu2.add(lblFur, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, -1, 20));

        txtFUR.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtFUR.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelMenu2.add(txtFUR, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 50, 103, 35));

        lblG.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblG.setText("G");
        panelMenu2.add(lblG, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 30, -1, 20));

        txtG.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtG.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelMenu2.add(txtG, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 50, 103, 35));

        lblP.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblP.setText("P");
        panelMenu2.add(lblP, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 30, -1, 20));

        txtP.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelMenu2.add(txtP, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 50, 106, 35));

        lblCSTP.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblCSTP.setText("CSTP");
        panelMenu2.add(lblCSTP, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, 20));

        txtCSTP.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtCSTP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelMenu2.add(txtCSTP, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 209, 35));

        lblHV.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblHV.setText("HV");
        panelMenu2.add(lblHV, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 90, -1, 20));

        txtHV.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtHV.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelMenu2.add(txtHV, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 110, 103, 35));

        lblHM.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblHM.setText("HM");
        panelMenu2.add(lblHM, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 90, -1, 20));

        txtHM.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtHM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelMenu2.add(txtHM, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 110, 103, 35));

        lblAB.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblAB.setText("AB");
        panelMenu2.add(lblAB, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 90, -1, 20));

        txtAB.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtAB.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelMenu2.add(txtAB, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 110, 106, 35));

        lblMPF.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblMPF.setText("MPF");
        panelMenu2.add(lblMPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, 20));

        lblAntecedentesLaborales.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblAntecedentesLaborales.setForeground(new java.awt.Color(31, 78, 121));
        lblAntecedentesLaborales.setText("ANTECEDENTES LABORALES");
        panelMenu2.add(lblAntecedentesLaborales, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, -1, -1));

        lblEmpresa.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblEmpresa.setText("Empresa");
        panelMenu2.add(lblEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, -1, -1));

        txtEmpresa1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtEmpresa1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtEmpresa1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEmpresa1KeyTyped(evt);
            }
        });
        panelMenu2.add(txtEmpresa1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 120, 35));

        txtEmpresa2.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtEmpresa2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));
        txtEmpresa2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEmpresa2KeyTyped(evt);
            }
        });
        panelMenu2.add(txtEmpresa2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 295, 120, 35));

        txtEmpresa3.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtEmpresa3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));
        txtEmpresa3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEmpresa3KeyTyped(evt);
            }
        });
        panelMenu2.add(txtEmpresa3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 120, 35));

        lblPuesto.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblPuesto.setText("Puesto");
        panelMenu2.add(lblPuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 240, -1, -1));

        txtPuesto1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtPuesto1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        txtPuesto1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPuesto1KeyTyped(evt);
            }
        });
        panelMenu2.add(txtPuesto1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 260, 120, 35));

        txtPuesto2.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtPuesto2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        txtPuesto2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPuesto2KeyTyped(evt);
            }
        });
        panelMenu2.add(txtPuesto2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 295, 120, 35));

        txtPuesto3.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtPuesto3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        txtPuesto3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPuesto3KeyTyped(evt);
            }
        });
        panelMenu2.add(txtPuesto3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 330, 120, 35));

        lblTiempoLaborado.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        lblTiempoLaborado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTiempoLaborado.setText("Tiempo laborado en la empresa");
        panelMenu2.add(lblTiempoLaborado, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 220, 295, 20));

        lbl1anio.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        lbl1anio.setText("1 año o menos");
        panelMenu2.add(lbl1anio, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 240, -1, -1));

        lbl2anios.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        lbl2anios.setText("2 a 4 años");
        panelMenu2.add(lbl2anios, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 240, -1, -1));

        lbl5anios.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        lbl5anios.setText("5 a 10 años");
        panelMenu2.add(lbl5anios, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 240, -1, -1));

        lbl11anios.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        lbl11anios.setText("Más de 10 años");
        panelMenu2.add(lbl11anios, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 240, -1, -1));

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

        panelMenu2.add(panelRbtn1_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 260, 75, 35));

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

        panelMenu2.add(panelRbtn1_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 295, 75, 35));

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

        panelMenu2.add(panelRbtn1_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 330, 75, 35));

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

        panelMenu2.add(panelRbtn2_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 260, 60, 35));

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

        panelMenu2.add(panelRbtn2_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 295, 60, 35));

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

        panelMenu2.add(panelRbtn2_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 330, 60, 35));

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

        panelMenu2.add(panelRbtn3_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 260, 70, 35));

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

        panelMenu2.add(panelRbtn3_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 295, 70, 35));

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

        panelMenu2.add(panelRbtn3_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 330, 70, 35));

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

        panelMenu2.add(panelRbtn4_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 260, 90, 35));

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

        panelMenu2.add(panelRbtn4_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 295, 90, 35));

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

        panelMenu2.add(panelRbtn4_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 330, 90, 35));

        txtMPF.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        txtMPF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelMenu2.add(txtMPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 106, 35));

        panelFormulario.add(panelMenu2, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 0, 590, 370));

        lblDiagnostico.setBackground(new java.awt.Color(255, 255, 255));
        lblDiagnostico.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblDiagnostico.setForeground(new java.awt.Color(31, 78, 121));
        lblDiagnostico.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDiagnostico.setText("¿Previamente ha tenido diagnóstico de alguna enfermedad relacionada al trabajo?");
        panelFormulario.add(lblDiagnostico, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 380, 1080, -1));

        txtDiagnostico.setColumns(20);
        txtDiagnostico.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtDiagnostico.setRows(5);
        jScrollPane1.setViewportView(txtDiagnostico);

        panelFormulario.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 410, 770, 60));

        tpanel_Contenidos.addTab("Formulario", panelFormulario);

        panelCombobox.setBackground(new java.awt.Color(255, 255, 255));
        panelCombobox.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cbPreempleoId.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        cbPreempleoId.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        panelCombobox.add(cbPreempleoId, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 90, 60, -1));

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

        panelCombobox.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 980, 320));

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

        tpanel_Contenidos.addTab("Combobox", panelCombobox);

        cont_Preempleo.add(tpanel_Contenidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 1080, 510));
        tpanel_Contenidos.getAccessibleContext().setAccessibleDescription("");

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
        }
        else {
            rbtn1_1.setEnabled(false);
            rbtn2_1.setEnabled(false);
            rbtn3_1.setEnabled(false);
            rbtn4_1.setEnabled(false);
            grbtn_empresa1.clearSelection();
            tiempoLaborado1 = 0;
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
            tiempoLaborado1 = 0;
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
            tiempoLaborado2 = 0;
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
            tiempoLaborado2 = 0;
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
            tiempoLaborado3 = 0;
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
            tiempoLaborado3 = 0;
            emp3 = false;
        }
    }//GEN-LAST:event_txtPuesto3KeyTyped

    private void rbtn1_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn1_1ActionPerformed
        emp1 = true;
        txtEmpresa2.setEnabled(true);
        txtPuesto2.setEnabled(true);
        tiempoLaborado1 = 1;
    }//GEN-LAST:event_rbtn1_1ActionPerformed

    private void rbtn2_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn2_1ActionPerformed
        emp1 = true;
        txtEmpresa2.setEnabled(true);
        txtPuesto2.setEnabled(true);
        tiempoLaborado1 = 2;
    }//GEN-LAST:event_rbtn2_1ActionPerformed

    private void rbtn3_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn3_1ActionPerformed
        emp1 = true;
        txtEmpresa2.setEnabled(true);
        txtPuesto2.setEnabled(true);
        tiempoLaborado1 = 3;
    }//GEN-LAST:event_rbtn3_1ActionPerformed

    private void rbtn4_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn4_1ActionPerformed
        emp1 = true;
        txtEmpresa2.setEnabled(true);
        txtPuesto2.setEnabled(true);
        tiempoLaborado1 = 4;
    }//GEN-LAST:event_rbtn4_1ActionPerformed

    private void rbtn1_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn1_2ActionPerformed
        emp2 = true;
        txtEmpresa3.setEnabled(true);
        txtPuesto3.setEnabled(true);
        tiempoLaborado2 = 1;
    }//GEN-LAST:event_rbtn1_2ActionPerformed

    private void rbtn2_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn2_2ActionPerformed
        emp2 = true;
        txtEmpresa3.setEnabled(true);
        txtPuesto3.setEnabled(true);
        tiempoLaborado2 = 2;
    }//GEN-LAST:event_rbtn2_2ActionPerformed

    private void rbtn3_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn3_2ActionPerformed
        emp2 = true;
        txtEmpresa3.setEnabled(true);
        txtPuesto3.setEnabled(true);
        tiempoLaborado2 = 3;
    }//GEN-LAST:event_rbtn3_2ActionPerformed

    private void rbtn4_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn4_2ActionPerformed
        emp2 = true;
        txtEmpresa3.setEnabled(true);
        txtPuesto3.setEnabled(true);
        tiempoLaborado2 = 4;
    }//GEN-LAST:event_rbtn4_2ActionPerformed

    private void rbtn1_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn1_3ActionPerformed
        emp3 = true;
        tiempoLaborado3 = 1;
    }//GEN-LAST:event_rbtn1_3ActionPerformed

    private void rbtn2_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn2_3ActionPerformed
        emp3 = true;
        tiempoLaborado3 = 2;
    }//GEN-LAST:event_rbtn2_3ActionPerformed

    private void rbtn3_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn3_3ActionPerformed
        emp3 = true;
        tiempoLaborado3 = 3;
    }//GEN-LAST:event_rbtn3_3ActionPerformed

    private void rbtn4_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn4_3ActionPerformed
        emp3 = true;
        tiempoLaborado3 = 4;
    }//GEN-LAST:event_rbtn4_3ActionPerformed

    private void rbtn_SexoMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_SexoMActionPerformed
        sexo = "M";
    }//GEN-LAST:event_rbtn_SexoMActionPerformed

    private void rbtn_SexoFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_SexoFActionPerformed
        sexo = "F";
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
            if (contenidoActual.equals("Actualizar")){
                tpanel_Contenidos.setSelectedIndex(1);
                btn_Confirmar.setBackground(new Color(92,92,235));
                
                btn_Confirmar.setVisible(true);
            }else if (contenidoActual.equals("Eliminar")){
                tpanel_Contenidos.setSelectedIndex(1);
                btn_Confirmar.setBackground(new Color(235,91,91));
                
                btn_Confirmar.setVisible(true);            
            }
            setBuscarPreempleoAntecedentes(cbPreempleoId.getSelectedItem().toString());
        } catch (SQLException ex) {
        Logger.getLogger(Preempleo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConnectException ex) {
            Logger.getLogger(Preempleo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_lbl_btnIngresarMouseClicked

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btn_Actualizar;
    private javax.swing.JPanel btn_Confirmar;
    private javax.swing.JPanel btn_Crear;
    private javax.swing.JPanel btn_Eliminar;
    private javax.swing.JPanel btn_Ingresar;
    private javax.swing.JComboBox<String> cbPreempleoId;
    private javax.swing.JPanel cont_Preempleo;
    private static javax.swing.ButtonGroup grbtn_Sexo;
    private static javax.swing.ButtonGroup grbtn_empresa1;
    private static javax.swing.ButtonGroup grbtn_empresa2;
    private static javax.swing.ButtonGroup grbtn_empresa3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jtPreempleo;
    private javax.swing.JLabel lbl11anios;
    private javax.swing.JLabel lbl1anio;
    private javax.swing.JLabel lbl2anios;
    private javax.swing.JLabel lbl5anios;
    private javax.swing.JLabel lblAB;
    private javax.swing.JLabel lblAG;
    private javax.swing.JLabel lblAntecedentesGi;
    private javax.swing.JLabel lblAntecedentesLaborales;
    private javax.swing.JLabel lblCSTP;
    private javax.swing.JLabel lblClinica;
    private javax.swing.JLabel lblDiagnostico;
    private javax.swing.JLabel lblEmpresa;
    private javax.swing.JLabel lblEstadoCivil;
    private javax.swing.JLabel lblFechaMod;
    private javax.swing.JLabel lblFur;
    private javax.swing.JLabel lblG;
    private javax.swing.JLabel lblHM;
    private javax.swing.JLabel lblHV;
    private javax.swing.JLabel lblIdentificacion;
    private javax.swing.JLabel lblMPF;
    private javax.swing.JLabel lblMenarquia;
    private javax.swing.JLabel lblNivelAcademico;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblP;
    private javax.swing.JLabel lblPuesto;
    private javax.swing.JLabel lblPuestoAplica;
    private javax.swing.JLabel lblSeguridad;
    private javax.swing.JLabel lblSexo;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTiempoLaborado;
    private javax.swing.JLabel lblTituloCombobox;
    private javax.swing.JLabel lblTituloPrincipal;
    private javax.swing.JLabel lblTitulos;
    private javax.swing.JLabel lbl_BtnEliminar;
    private javax.swing.JLabel lbl_InicioInicio;
    private javax.swing.JLabel lbl_btnActualizar;
    private javax.swing.JLabel lbl_btnCrear;
    private javax.swing.JLabel lbl_btnIngresar;
    private javax.swing.JLabel lbl_btn_Confirmar;
    private javax.swing.JPanel panelAG;
    private javax.swing.JPanel panelBotonesCRUD;
    private javax.swing.JPanel panelCombobox;
    private javax.swing.JPanel panelEdicion;
    private javax.swing.JPanel panelFecha;
    private javax.swing.JPanel panelFormulario;
    private javax.swing.JPanel panelInicio;
    private javax.swing.JPanel panelMenu1;
    private javax.swing.JPanel panelMenu2;
    private javax.swing.JPanel panelRbtn1_1;
    private javax.swing.JPanel panelRbtn1_2;
    private javax.swing.JPanel panelRbtn1_3;
    private javax.swing.JPanel panelRbtn2_1;
    private javax.swing.JPanel panelRbtn2_2;
    private javax.swing.JPanel panelRbtn2_3;
    private javax.swing.JPanel panelRbtn3_1;
    private javax.swing.JPanel panelRbtn3_2;
    private javax.swing.JPanel panelRbtn3_3;
    private javax.swing.JPanel panelRbtn4_1;
    private javax.swing.JPanel panelRbtn4_2;
    private javax.swing.JPanel panelRbtn4_3;
    private javax.swing.JPanel panelSeguridad;
    private javax.swing.JPanel panelTitulos;
    private javax.swing.JRadioButton rbtn1_1;
    private javax.swing.JRadioButton rbtn1_2;
    private javax.swing.JRadioButton rbtn1_3;
    private javax.swing.JRadioButton rbtn2_1;
    private javax.swing.JRadioButton rbtn2_2;
    private javax.swing.JRadioButton rbtn2_3;
    private javax.swing.JRadioButton rbtn3_1;
    private javax.swing.JRadioButton rbtn3_2;
    private javax.swing.JRadioButton rbtn3_3;
    private javax.swing.JRadioButton rbtn4_1;
    private javax.swing.JRadioButton rbtn4_2;
    private javax.swing.JRadioButton rbtn4_3;
    private javax.swing.JRadioButton rbtn_SexoF;
    private javax.swing.JRadioButton rbtn_SexoM;
    private javax.swing.JPanel tablaTitulos;
    private javax.swing.JTabbedPane tpanel_Contenidos;
    private javax.swing.JTextField txtAB;
    private javax.swing.JTextField txtCSTP;
    private javax.swing.JTextArea txtDiagnostico;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEdad;
    private javax.swing.JTextField txtEmpresa1;
    private javax.swing.JTextField txtEmpresa2;
    private javax.swing.JTextField txtEmpresa3;
    private javax.swing.JTextField txtEstadoCivil;
    private javax.swing.JTextField txtFUR;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtG;
    private javax.swing.JTextField txtHM;
    private javax.swing.JTextField txtHV;
    private javax.swing.JTextField txtIdentificacion;
    private javax.swing.JTextField txtMPF;
    private javax.swing.JTextField txtMenarquia;
    private javax.swing.JTextField txtNivelAcademico;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtP;
    private javax.swing.JLabel txtPreempleoId;
    private javax.swing.JTextField txtPuesto1;
    private javax.swing.JTextField txtPuesto2;
    private javax.swing.JTextField txtPuesto3;
    private javax.swing.JTextField txtPuestoAplica;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
