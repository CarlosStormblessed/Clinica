package Vistas;

import javax.swing.JPanel;
import Utilitarios.Utilitarios;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.ButtonModel;
import javax.swing.table.DefaultTableModel;
import Controladores.PreempleoCtrl;
import Controladores.AntecedentesCtrl;
import Controladores.RevisionSistemasCtrl;
import Controladores.UsuarioCtrl;
import Controladores.PaginadorTabla;
import Modelos.AntecedentesMod;
import Modelos.PreempleoMod;
import Modelos.RevisionSistemasMod;
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
import java.util.ArrayList;
import javax.swing.JTabbedPane;

public class Preempleo extends javax.swing.JFrame implements ActionListener, TableModelListener{

    Utilitarios util = new Utilitarios();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime now = LocalDateTime.now();
    String contenidoActual;
    boolean emp1=true, emp2=false, emp3=false;
    public String nombreContenido = "Preempleo", responsable = "";
    public JComboBox<Integer> filasPermitidas;
    private PreempleoMod preempleo = new PreempleoMod();
    private AntecedentesMod antecedentes = new AntecedentesMod();
    private RevisionSistemasMod revisionSistemas = new RevisionSistemasMod();
    private UsuarioMod usuario = new UsuarioMod();
    private PreempleoCtrl preempCtrl = new PreempleoCtrl();
    private AntecedentesCtrl antCtrl = new AntecedentesCtrl();
    private RevisionSistemasCtrl revSisCtrl = new RevisionSistemasCtrl();
    private UsuarioCtrl usuarioCtrl = new UsuarioCtrl();
    private PaginadorTabla <Preempleo> paginador;
    public Preempleo() throws SQLException, ConnectException {
        initComponents();
        contenidoActual = "Inicio";
        setCartaContenido(panelInicio);
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
        resetRevisionSistemas();
        resetPreempleado();
        construirEtiquetas();
        setTabla();
        lbl_SeleccionPreempleo.setText("");
        panelPaginacion.removeAll();
        if (responsable.length() > 0)
            resetUsuarios();
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
        
        preempleo.setId("");
        preempleo.setFecha("");
        preempleo.setNombre("");
        preempleo.setSexo("");
        preempleo.setIdentificacion("");
        preempleo.setEdad("");
        preempleo.setEstadoCivil("");
        preempleo.setDireccion("");
        preempleo.setTelefono("");
        preempleo.setNivelAcademico("");
        preempleo.setPuestoAplica("");
        preempleo.setEmpresa1("");
        preempleo.setEmpresa2("");
        preempleo.setEmpresa3("");
        preempleo.setPuesto1("");
        preempleo.setPuesto2("");
        preempleo.setPuesto3("");
        preempleo.setTiempoLaborado1("0");
        preempleo.setTiempoLaborado2("0");
        preempleo.setTiempoLaborado3("0");
        preempleo.setClinicaId("");
        preempleo.setAntecedentesId("");
        preempleo.setRevisionSistemasId("");
        preempleo.setResponsable("");
        preempleo.setRealizado("");
        preempleo.setRevisado("");
        preempleo.setAutorizado("");
        preempleo.setEstado("");
        setTiempoLaborado();
        
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
        txtDiagnostico.setText("");
        
        antecedentes.setDiagnostico("");
        antecedentes.setFamiliares("");
        antecedentes.setMedicos("");
        antecedentes.setTratamientos("");
        antecedentes.setLaboratorios("");
        antecedentes.setQuirurgicos("");
        antecedentes.setTraumaticos("");
        antecedentes.setAlergicos("");
        antecedentes.setVicios("");
        antecedentes.setEstado("");
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
    }
    
    private void resetRevisionSistemas(){
        txtAreaAlteraciones.setText("");
        txtAreaPielFaneras.setText("");
        txtAreaCabeza.setText("");
        txtAreaOjoidos.setText("");
        txtAreaOrofaringe.setText("");
        txtAreaCuello.setText("");
        txtAreaRespiratorio.setText("");
        txtAreaCardiovascular.setText("");
        txtAreaGastrointestinal.setText("");
        txtAreaGenitourinario.setText("");
        txtAreaExtremidades.setText("");
        txtAreaNeurologico.setText("");
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
        combo_Talla.setSelectedIndex(0);
        txtOjoDerecho.setText("");
        txtOjoIzquierdo.setText("");
        checkLentes.setSelected(false);
        txtAreaImpresionClinica.setText("");
        
        revisionSistemas.setAlteraciones("");
        revisionSistemas.setPielFaneras("");
        revisionSistemas.setCabeza("");
        revisionSistemas.setOjoOidoNarizBoca("");
        revisionSistemas.setOrofarinje("");
        revisionSistemas.setCuello("");
        revisionSistemas.setRespiratorio("");
        revisionSistemas.setCardiovascular("");
        revisionSistemas.setGastrointestinal("");
        revisionSistemas.setGenitourinario("");
        revisionSistemas.setExtremidades("");
        revisionSistemas.setNeurologico("");
        revisionSistemas.setTemperatura("");
        revisionSistemas.setPulso("");
        revisionSistemas.setSpo2("");
        revisionSistemas.setFr("");
        revisionSistemas.setPa("");
        revisionSistemas.setGlicemia("");
        revisionSistemas.setPeso("");
        revisionSistemas.setTalla("");
        revisionSistemas.setImc("");
        revisionSistemas.setRuffier("");
        revisionSistemas.setOjoDerecho("");
        revisionSistemas.setOjoIzquierdo("");
        revisionSistemas.setAnteojos("");
        revisionSistemas.setImpresionClinica("");
        revisionSistemas.setObservaciones("");
        revisionSistemas.setEstado("");
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
            usuario = usuarioCtrl.buscarFila(responsable);
            lblResponsable.setText("Responsable: " + usuario.getNombre());
        } catch (SQLException ex) {
            Logger.getLogger(Preempleo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConnectException ex) {
            Logger.getLogger(Preempleo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void construirEtiquetas(){
        try {
            String codigo = preempCtrl.getMaxId();
            lblTitulos.setText("<html><center>Formato<br><b>FICHA MÉDICA PRE-EMPLEO</b><br>CÓDIGO " + codigo + "</center></html>");
            lblSeguridad.setText("<html><center>Seguridad Industrial y Salud Ocupacional</center></html>");
            lblFechaMod.setText("<html><center>Fecha de<br>modificacion:<br>"+ util.convertirFechaGUI(now.format(dtf)) + "</center></html>");
            lbl_SeleccionPreempleo.setText("");
            txtFecha.setText(util.convertirFechaGUI(now.format(dtf)));
            lblResponsable.setText("Responsable: " + responsable);
        } catch (SQLException ex) {
            Logger.getLogger(Preempleo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConnectException ex) {
            Logger.getLogger(Preempleo.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    
    private void setCartaContenido(JPanel carta){
        panelCartasContenido.removeAll();
        panelCartasContenido.add(carta);
        panelCartasContenido.repaint();
        panelCartasContenido.revalidate();
    }
    
    private void setCartas (JPanel carta){
        panelCartas.removeAll();
        panelCartas.add(carta);
        panelCartas.repaint();
        panelCartas.revalidate();
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
        switch (preempleo.getTiempoLaborado1()){
            case "0": 
                grbtn_empresa1.clearSelection();
                break;
            case "1":
                rbtn1_1.setSelected(true);
                grbtn_empresa1.setSelected(rbtn1_1.getModel(), emp1);
                break;
            case "2":
                rbtn2_1.setSelected(true);
                grbtn_empresa1.setSelected(rbtn2_1.getModel(), emp1);
                break;
            case "3":
                rbtn3_1.setSelected(true);
                grbtn_empresa1.setSelected(rbtn3_1.getModel(), emp1);
                break;
            case "4":
                rbtn4_1.setSelected(true);
                grbtn_empresa1.setSelected(rbtn4_1.getModel(), emp1);
                break;
            default:
                grbtn_empresa1.clearSelection();
                break;
        }
        switch (preempleo.getTiempoLaborado2()){
            case "0": 
                grbtn_empresa2.clearSelection();
                break;
            case "1": 
                rbtn1_2.setSelected(true);
                grbtn_empresa2.setSelected(rbtn1_2.getModel(), emp2);
                break;
            case "2":
                rbtn2_2.setSelected(true);
                grbtn_empresa2.setSelected(rbtn2_2.getModel(), emp2);
                break;
            case "3":
                rbtn3_2.setSelected(true);
                grbtn_empresa2.setSelected(rbtn3_2.getModel(), emp2);
                break;
            case "4":
                rbtn4_2.setSelected(true);
                grbtn_empresa2.setSelected(rbtn4_2.getModel(), emp2);
                break;
            default:
                grbtn_empresa2.clearSelection();
                break;
        }
        switch (preempleo.getTiempoLaborado3()){
            case "0": 
                grbtn_empresa3.clearSelection();
                break;
            case "1": 
                rbtn1_3.setSelected(true);
                grbtn_empresa3.setSelected(rbtn1_3.getModel(), emp3);
                break;
            case "2":
                rbtn2_3.setSelected(true);
                grbtn_empresa3.setSelected(rbtn2_3.getModel(), emp3);
                break;
            case "3":
                rbtn3_3.setSelected(true);
                grbtn_empresa3.setSelected(rbtn3_3.getModel(), emp3);
                break;
            case "4":
                rbtn4_3.setSelected(true);
                grbtn_empresa3.setSelected(rbtn4_3.getModel(), emp3);
                break;
            default:
                grbtn_empresa3.clearSelection();
                break;
        }
        if ((preempleo.getTiempoLaborado1().length() > 0) && !(preempleo.getTiempoLaborado1().equals("0"))){
            rbtn1_1.setEnabled(true);
            rbtn2_1.setEnabled(true);
            rbtn3_1.setEnabled(true);
            rbtn4_1.setEnabled(true);
            emp1 = true;
        }else if ((preempleo.getTiempoLaborado2().length() > 0) && !(preempleo.getTiempoLaborado2().equals("0"))){
            rbtn1_2.setEnabled(true);
            rbtn2_2.setEnabled(true);
            rbtn3_2.setEnabled(true);
            rbtn4_2.setEnabled(true);
            emp2 = true;
        }else if ((preempleo.getTiempoLaborado3().length() > 0) && !(preempleo.getTiempoLaborado3().equals("0"))){
            rbtn4_3.setEnabled(true);
            rbtn3_3.setEnabled(true);
            rbtn2_3.setEnabled(true);
            rbtn1_3.setEnabled(true);
            emp3 = true;
        }
    }
    
    private void getAptitud(){
        if(rbtn_Aptitud1.isSelected())
            preempleo.setAptitud("1");
        else if (rbtn_Aptitud2.isSelected())
            preempleo.setAptitud("2");
        else if (rbtn_Aptitud3.isSelected())
            preempleo.setAptitud("3");
        else if (rbtn_Aptitud4.isSelected())
            preempleo.setAptitud("4");
    }
    
    private void setAptitud(String aptitud){
        switch (aptitud){
            case "1":
                grbtn_Aptitud.setSelected(rbtn_Aptitud1.getModel(), true);
                rbtn_Aptitud1.setSelected(true);
                break;
            case "2":
                grbtn_Aptitud.setSelected(rbtn_Aptitud2.getModel(), true);
                rbtn_Aptitud2.setSelected(true);
                break;
            case "3":
                grbtn_Aptitud.setSelected(rbtn_Aptitud3.getModel(), true);
                rbtn_Aptitud3.setSelected(true);
                break;
            case "4":
                grbtn_Aptitud.setSelected(rbtn_Aptitud4.getModel(), true);
                rbtn_Aptitud4.setSelected(true);
                break;
            default:
                grbtn_Aptitud.clearSelection();
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
    
    /**
     * Los antecedentes en pantalla se guardarán en una variable global "antecedentes"
     */
    private void getAntecedentes(){
        antecedentes.setDiagnostico(txtDiagnostico.getText());
        antecedentes.setFamiliares(txtAreaFamiliares.getText());
        antecedentes.setMedicos(txtAreaMedicos.getText());
        antecedentes.setTratamientos(txtAreaTratamientos.getText());
        antecedentes.setLaboratorios(txtAreaLaboratorios.getText());
        antecedentes.setQuirurgicos(txtAreaQuirurgicos.getText());
        antecedentes.setTraumaticos(txtAreaTraumaticos.getText());
        antecedentes.setAlergicos(txtAreaAlergicos.getText());
        antecedentes.setVicios(txtAreaVicios.getText());
        
        antecedentes.setMenarquia(util.convertirFechaSQL(txtMenarquia.getText()));
        antecedentes.setFur(util.convertirFechaSQL(txtFUR.getText()));
        antecedentes.setG(txtG.getText());
        antecedentes.setP(txtP.getText());
        antecedentes.setCstp(util.convertirFechaSQL(txtCSTP.getText()));
        antecedentes.setHv(txtHV.getText());
        antecedentes.setHm(txtHM.getText());
        antecedentes.setAb(txtAB.getText());
        antecedentes.setMpf(txtMPF.getText());
        
        antecedentes.setDiagnostico(txtDiagnostico.getText());
        antecedentes.setEstado("1");
    }
    
    /**
     * Los valores dentro de los antecedentes guardados en la variable global "antecedentes" se mostrarán en pantalla
     */
    private void setAntecedentes(){
        txtAreaFamiliares.setText(antecedentes.getFamiliares());
        txtAreaMedicos.setText(antecedentes.getMedicos());
        txtAreaTratamientos.setText(antecedentes.getTratamientos());
        txtAreaLaboratorios.setText(antecedentes.getLaboratorios());
        txtAreaQuirurgicos.setText(antecedentes.getQuirurgicos());
        txtAreaTraumaticos.setText(antecedentes.getTraumaticos());
        txtAreaAlergicos.setText(antecedentes.getAlergicos());
        txtAreaVicios.setText(antecedentes.getVicios());
        txtMenarquia.setText(util.convertirFechaGUI(antecedentes.getMenarquia()));
        txtFUR.setText(util.convertirFechaGUI(antecedentes.getFur()));
        txtG.setText(antecedentes.getG());
        txtP.setText(antecedentes.getP());
        txtCSTP.setText(util.convertirFechaGUI(antecedentes.getCstp()));
        txtHV.setText(antecedentes.getHv());
        txtHM.setText(antecedentes.getHm());
        txtAB.setText(antecedentes.getAb());
        txtMPF.setText(antecedentes.getMpf());
        txtDiagnostico.setText(antecedentes.getDiagnostico());
    }
    
    /**
     * Los datos del examen físico (revisionSistemas) en pantalla se guardarán en una variable global "revisionSistemas"
     */
    private void getRevisionSistemas(){
        revisionSistemas.setAlteraciones(txtAreaAlteraciones.getText());
        revisionSistemas.setPielFaneras(txtAreaPielFaneras.getText());
        revisionSistemas.setCabeza(txtAreaCabeza.getText());
        revisionSistemas.setOjoOidoNarizBoca(txtAreaOjoidos.getText());
        revisionSistemas.setOrofarinje(txtAreaOrofaringe.getText());
        revisionSistemas.setCuello(txtAreaCuello.getText());
        revisionSistemas.setRespiratorio(txtAreaRespiratorio.getText());
        revisionSistemas.setCardiovascular(txtAreaCardiovascular.getText());
        revisionSistemas.setGastrointestinal(txtAreaGastrointestinal.getText());
        revisionSistemas.setGenitourinario(txtAreaGenitourinario.getText());
        revisionSistemas.setExtremidades(txtAreaExtremidades.getText());
        revisionSistemas.setNeurologico(txtAreaNeurologico.getText());
        revisionSistemas.setTemperatura(txtTemperatura.getText());
        revisionSistemas.setPulso(txtPulso.getText());
        revisionSistemas.setSpo2(txtSPO2.getText());
        revisionSistemas.setFr(txtFR.getText());
        revisionSistemas.setPa(txtPA.getText());
        revisionSistemas.setGlicemia(txtGlicemia.getText());
        revisionSistemas.setPeso(txtPeso.getText());
        revisionSistemas.setTalla(combo_Talla.getSelectedItem().toString());
        revisionSistemas.setImc(txtIMC.getText());
        revisionSistemas.setRuffier(txtRuffier.getText());
        revisionSistemas.setOjoDerecho(txtOjoDerecho.getText());
        revisionSistemas.setOjoIzquierdo(txtOjoIzquierdo.getText());
        if(checkLentes.isSelected())
            revisionSistemas.setAnteojos("1");
        else
            revisionSistemas.setAnteojos("0");
        revisionSistemas.setImpresionClinica(txtAreaImpresionClinica.getText());
        revisionSistemas.setObservaciones(txtAreaObservaciones.getText());
        revisionSistemas.setEstado("1");
    }
    
    /**
     * Los valores dentro de la variable revisionSistemas guardados en la variable global "revisionSistemas" se mostrarán en pantalla
     */
    private void setRevisionSistemas(){
        txtAreaAlteraciones.setText(revisionSistemas.getAlteraciones());
        txtAreaPielFaneras.setText(revisionSistemas.getPielFaneras());
        txtAreaCabeza.setText(revisionSistemas.getCabeza());
        txtAreaOjoidos.setText(revisionSistemas.getOjoOidoNarizBoca());
        txtAreaOrofaringe.setText(revisionSistemas.getOrofarinje());
        txtAreaCuello.setText(revisionSistemas.getCuello());
        txtAreaRespiratorio.setText(revisionSistemas.getRespiratorio());
        txtAreaCardiovascular.setText(revisionSistemas.getCardiovascular());
        txtAreaGastrointestinal.setText(revisionSistemas.getGastrointestinal());
        txtAreaGenitourinario.setText(revisionSistemas.getGenitourinario());
        txtAreaExtremidades.setText(revisionSistemas.getExtremidades());
        txtAreaNeurologico.setText(revisionSistemas.getNeurologico());
        txtTemperatura.setText(revisionSistemas.getTemperatura());
        txtPulso.setText(revisionSistemas.getPulso());
        txtSPO2.setText(revisionSistemas.getSpo2());
        txtFR.setText(revisionSistemas.getFr());
        txtPA.setText(revisionSistemas.getPa());
        txtGlicemia.setText(revisionSistemas.getGlicemia());
        txtPeso.setText(revisionSistemas.getPeso());
        //Set Talla
        switch(revisionSistemas.getTalla()){
            case "S":
                combo_Talla.setSelectedIndex(0);
                break;
            case "M":
                combo_Talla.setSelectedIndex(1);
                break;
            case "L":
                combo_Talla.setSelectedIndex(2);
                break;
            case "XL":
                combo_Talla.setSelectedIndex(3);
                break;
            case "XXL":
                combo_Talla.setSelectedIndex(4);
                break;
            default:
                break;
        }
        txtIMC.setText(revisionSistemas.getImc());
        txtRuffier.setText(revisionSistemas.getRuffier());
        txtOjoDerecho.setText(revisionSistemas.getOjoDerecho());
        txtOjoIzquierdo.setText(revisionSistemas.getOjoIzquierdo());
        // Set Lentes
        switch(revisionSistemas.getAnteojos()){
            case "0":
                checkLentes.setSelected(false);
                break;
            case "1":
                checkLentes.setSelected(true);
                break;
        }
        txtAreaImpresionClinica.setText(revisionSistemas.getImpresionClinica());
        txtAreaObservaciones.setText(revisionSistemas.getObservaciones());
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
        preempleo.setFecha(util.convertirFechaSQL(txtFecha.getText()));
        preempleo.setEmpresa1(txtEmpresa1.getText());
        preempleo.setEmpresa2(txtEmpresa2.getText());
        preempleo.setEmpresa3(txtEmpresa3.getText());
        preempleo.setPuesto1(txtPuesto1.getText());
        preempleo.setPuesto2(txtPuesto2.getText());
        preempleo.setPuesto3(txtPuesto3.getText());
        getAptitud();
        preempleo.setClinicaId("1");
        preempleo.setAntecedentesId(antecedentes.getId());
        preempleo.setRevisionSistemasId(revisionSistemas.getId());
        preempleo.setResponsable(responsable);
        
        //get Realizado, Revisado y Autorizado
        String [] partesCombobox = combo_Realizado.getSelectedItem().toString().split(". ");
        preempleo.setRealizado(partesCombobox[0]);
        partesCombobox = combo_Revisado.getSelectedItem().toString().split(". ");
        preempleo.setRevisado(partesCombobox[0]);
        partesCombobox = combo_Autorizado.getSelectedItem().toString().split(". ");
        preempleo.setAutorizado(partesCombobox[0]);
        preempleo.setRealizado(responsable);
        preempleo.setEstado("1");
        
    }
    
    /**
     * Los valores dentro del preempleo guardados en la variable global "preempleo" se mostrarán en pantalla
     */
    private void setPreempleo(){
        txtPreempleoId.setText(preempleo.getId());
        txtFecha.setText(util.convertirFechaGUI(preempleo.getFecha()));
        txtNombre.setText(preempleo.getNombre());
        setSexo(preempleo.getSexo());
        txtIdentificacion.setText(preempleo.getIdentificacion());
        txtEdad.setText(preempleo.getEdad());
        txtEstadoCivil.setText(preempleo.getEstadoCivil());
        txtDireccion.setText(preempleo.getDireccion());
        txtTelefono.setText(preempleo.getTelefono());
        txtNivelAcademico.setText(preempleo.getNivelAcademico());
        txtPuestoAplica.setText(preempleo.getPuestoAplica());
        
        txtEmpresa1.setText(preempleo.getEmpresa1());
        txtEmpresa2.setText(preempleo.getEmpresa2());
        txtEmpresa3.setText(preempleo.getEmpresa3());
        txtPuesto1.setText(preempleo.getPuesto1());
        txtPuesto2.setText(preempleo.getPuesto2());
        txtPuesto3.setText(preempleo.getPuesto3());
        setTiempoLaborado();
        setAptitud(preempleo.getAptitud());
        
        setComboboxUsuarios();
    }
    
    
    
    private void setComboboxUsuarios(){
        String[] elementos = new String[combo_Autorizado.getModel().getSize()];
        for (int i = 0; i < elementos.length; i++){
            String[] partesAutorizado;
            elementos[i] = combo_Autorizado.getItemAt(i);
            partesAutorizado = elementos[i].split(". ");
            if (preempleo.getAutorizado().equals(partesAutorizado[0]))
                combo_Autorizado.setSelectedIndex(i);
        }
        elementos = new String[combo_Realizado.getModel().getSize()];
        for (int i = 0; i < elementos.length; i++){
            String[] partesRealizado;
            elementos[i] = combo_Realizado.getItemAt(i);
            partesRealizado = elementos[i].split(". ");
            if (preempleo.getRealizado().equals(partesRealizado[0]))
                combo_Realizado.setSelectedIndex(i);
        }
        elementos = new String[combo_Revisado.getModel().getSize()];
        for (int i = 0; i < elementos.length; i++){
            String[] partesRevisado;
            elementos[i] = combo_Revisado.getItemAt(i);
            partesRevisado = elementos[i].split(". ");
            if (preempleo.getRevisado().equals(partesRevisado[0]))
                combo_Revisado.setSelectedIndex(i);
        }
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
    
    private void setTabla(){
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
        setComboboxUsuarios(idCombobox);
        */
        try{
            jtPreempleo.setModel(crearModeloTabla());
            DatosPaginacion<PreempleoMod> datosPaginacion = crearDatosPaginacion();
            paginador = new PaginadorTabla(jtPreempleo, datosPaginacion, new int[]{5,10,20,25,50,75,100}, 10);
            paginador.crearListadoFilasPermitidas(panelPaginacion);
            filasPermitidas = paginador.getComboboxFilasPermitidas();
            filasPermitidas.addActionListener(this);
            jtPreempleo.getModel().addTableModelListener(this);
            filasPermitidas.setSelectedItem(Integer.parseInt("10"));
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaGeneral.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConnectException ex) {
            Logger.getLogger(ConsultaGeneral.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    
    /**
     * @param valorBuscar: id del formulario Preempleo en la variable preempleo
     */
    private void setBuscarPreempleoAntecedentesRevisionSistemas(String valorBuscar) throws SQLException, ConnectException{
        
        preempleo = preempCtrl.buscarFila(valorBuscar);
        lblTitulos.setText("<html><center>Formato<br><b>FICHA MÉDICA PRE-EMPLEO</b><br>CÓDIGO " + preempleo.getId() + "</center></html>");
        setPreempleo();
        antecedentes = antCtrl.buscarFila(preempleo.getAntecedentesId());
        setAntecedentes();
        revisionSistemas = revSisCtrl.buscarFila(preempleo.getRevisionSistemasId());
        setRevisionSistemas();
    }
    
    private void crear() throws SQLException{
        try {
            int resAntecedentes = 0, resRevisionSistemas = 0, resPreempleo = 0;
            resAntecedentes = antCtrl.Crear(antecedentes);
            if (resAntecedentes > 0){
                resRevisionSistemas = revSisCtrl.Crear(revisionSistemas);
                if (resRevisionSistemas > 0){
                    preempleo.setAntecedentesId(antCtrl.getMaxId());
                    preempleo.setRevisionSistemasId(revSisCtrl.getMaxId());
                    resPreempleo = preempCtrl.Crear(preempleo);
                }
            }
            if ((resAntecedentes > 0) && (resRevisionSistemas > 0) && (resPreempleo > 0)) {
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
            int resAntecedentes = 0, resRevisionSistemas = 0, resPreempleo = 0;
            resAntecedentes = antCtrl.Actualizar(antecedentes);
            if (resAntecedentes > 0){
                
                resRevisionSistemas = revSisCtrl.Actualizar(revisionSistemas);
                if (resRevisionSistemas > 0){
                    
                    resPreempleo = preempCtrl.Actualizar(preempleo);
                }
            }
            if ((resAntecedentes > 0) && (resRevisionSistemas > 0) && (resPreempleo > 0)) {
                JOptionPane.showMessageDialog(this, "REGISTRO ACTUALIZADO CON EXITO", "Inserción de Datos", JOptionPane.INFORMATION_MESSAGE);
                reset();
                setTabla();
                setCartaContenido(panelFormulario);
            } else {
                JOptionPane.showMessageDialog(this, "ERROR AL ACTUALIZAR ANTECEDENTES O FICHA DE PREEMPLEO. COMUNIQUESE CON TI", "Inserción de Datos", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR " + e.toString() + " AL ACTUALIZAR EL REGISTRO. COMUNIQUESE CON TI", "Inserción de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminar() throws SQLException{
        try {
            int resAntecedentes = 0, resRevisionSistemas = 0, resPreempleo = 0;
            resAntecedentes = antCtrl.Eliminar(antecedentes);
            if (resAntecedentes > 0){
                resRevisionSistemas = revSisCtrl.Eliminar(revisionSistemas);
                if (resRevisionSistemas > 0)
                    resPreempleo = preempCtrl.Eliminar(preempleo);
            }
            if ((resAntecedentes > 0) && (resRevisionSistemas > 0) && (resPreempleo > 0)) {
                JOptionPane.showMessageDialog(this, "REGISTRO ELIMINADO CON EXITO", "Inserción de Datos", JOptionPane.INFORMATION_MESSAGE);
                reset();
                setTabla();
                setCartaContenido(panelFormulario);
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
        grbtn_Aptitud = new javax.swing.ButtonGroup();
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
        panelCartasContenido = new javax.swing.JPanel();
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
        panelPagina1 = new javax.swing.JPanel();
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
        btn_Pagina1_2 = new javax.swing.JPanel();
        lbl_btn_Pagina1_2 = new javax.swing.JLabel();
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
        panelPagina2 = new javax.swing.JPanel();
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
        btn_Pagina2_1 = new javax.swing.JPanel();
        lbl_btn_Pagina2_1 = new javax.swing.JLabel();
        btn_Pagina2_3 = new javax.swing.JPanel();
        lbl_btn_Pagina2_3 = new javax.swing.JLabel();
        panelPagina3 = new javax.swing.JPanel();
        lbl_RevisionPorSistemas = new javax.swing.JLabel();
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
        combo_Talla = new javax.swing.JComboBox<>();
        lblIMC = new javax.swing.JLabel();
        txtIMC = new javax.swing.JTextField();
        lblPA = new javax.swing.JLabel();
        txtPA = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtRuffier = new javax.swing.JTextField();
        lblOjoDerecho = new javax.swing.JLabel();
        txtOjoDerecho = new javax.swing.JTextField();
        lblOjoIzquierdo = new javax.swing.JLabel();
        txtOjoIzquierdo = new javax.swing.JTextField();
        checkLentes = new javax.swing.JCheckBox();
        btn_Pagina3_2 = new javax.swing.JPanel();
        lbl_btn_Pagina3_2 = new javax.swing.JLabel();
        btn_Pagina3_4 = new javax.swing.JPanel();
        lbl_btn_Pagina3_4 = new javax.swing.JLabel();
        panelPagina4 = new javax.swing.JPanel();
        lblAlteraciones = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        txtAreaAlteraciones = new javax.swing.JTextArea();
        lblPielFaneras = new javax.swing.JLabel();
        jScrollPane14 = new javax.swing.JScrollPane();
        txtAreaPielFaneras = new javax.swing.JTextArea();
        lblCabeza = new javax.swing.JLabel();
        jScrollPane15 = new javax.swing.JScrollPane();
        txtAreaCabeza = new javax.swing.JTextArea();
        lblOjoidos = new javax.swing.JLabel();
        jScrollPane16 = new javax.swing.JScrollPane();
        txtAreaOjoidos = new javax.swing.JTextArea();
        lblOrofaringe = new javax.swing.JLabel();
        jScrollPane17 = new javax.swing.JScrollPane();
        txtAreaOrofaringe = new javax.swing.JTextArea();
        lblCuello = new javax.swing.JLabel();
        jScrollPane18 = new javax.swing.JScrollPane();
        txtAreaCuello = new javax.swing.JTextArea();
        lblRespiratorio = new javax.swing.JLabel();
        jScrollPane19 = new javax.swing.JScrollPane();
        txtAreaRespiratorio = new javax.swing.JTextArea();
        lblCardiovascular = new javax.swing.JLabel();
        jScrollPane20 = new javax.swing.JScrollPane();
        txtAreaCardiovascular = new javax.swing.JTextArea();
        btn_Pagina4_3 = new javax.swing.JPanel();
        lbl_btn_Pagina4_3 = new javax.swing.JLabel();
        btn_Pagina4_5 = new javax.swing.JPanel();
        lbl_btn_Pagina4_5 = new javax.swing.JLabel();
        lblGastrointestinal = new javax.swing.JLabel();
        jScrollPane21 = new javax.swing.JScrollPane();
        txtAreaGastrointestinal = new javax.swing.JTextArea();
        lblExtremidades = new javax.swing.JLabel();
        jScrollPane23 = new javax.swing.JScrollPane();
        txtAreaExtremidades = new javax.swing.JTextArea();
        panelPagina5 = new javax.swing.JPanel();
        btn_Pagina5_4 = new javax.swing.JPanel();
        lbl_btn_Pagina5_4 = new javax.swing.JLabel();
        lblGenitourinario = new javax.swing.JLabel();
        jScrollPane22 = new javax.swing.JScrollPane();
        txtAreaGenitourinario = new javax.swing.JTextArea();
        lblNeurologico = new javax.swing.JLabel();
        jScrollPane24 = new javax.swing.JScrollPane();
        txtAreaNeurologico = new javax.swing.JTextArea();
        lblImpresionClinica = new javax.swing.JLabel();
        jScrollPane25 = new javax.swing.JScrollPane();
        txtAreaImpresionClinica = new javax.swing.JTextArea();
        lblObservaciones = new javax.swing.JLabel();
        jScrollPane26 = new javax.swing.JScrollPane();
        txtAreaObservaciones = new javax.swing.JTextArea();
        lblAptitud = new javax.swing.JLabel();
        rbtn_Aptitud1 = new javax.swing.JRadioButton();
        rbtn_Aptitud2 = new javax.swing.JRadioButton();
        rbtn_Aptitud3 = new javax.swing.JRadioButton();
        rbtn_Aptitud4 = new javax.swing.JRadioButton();
        lblResponsable = new javax.swing.JLabel();
        lbl_Realizado = new javax.swing.JLabel();
        combo_Realizado = new javax.swing.JComboBox<>();
        lbl_Revisado = new javax.swing.JLabel();
        combo_Revisado = new javax.swing.JComboBox<>();
        lbl_Autorizado = new javax.swing.JLabel();
        combo_Autorizado = new javax.swing.JComboBox<>();
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
        fechaFUR.setDateFormat("dd/MM/yyyy");
        fechaFUR.setTextRefernce(txtFUR);

        fechaCSTP.setForeground(new java.awt.Color(87, 87, 238));
        fechaCSTP.setDateFormat("dd/MM/yyyy");
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

        panelCartasContenido.setLayout(new java.awt.CardLayout());

        panelInicio.setBackground(new java.awt.Color(255, 255, 255));
        panelInicio.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_InicioInicio.setFont(new java.awt.Font("Roboto", 0, 48)); // NOI18N
        lbl_InicioInicio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_InicioInicio.setText("Seleccione una acción");
        panelInicio.add(lbl_InicioInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 430));

        panelCartasContenido.add(panelInicio, "card2");

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

        panelPagina1.setBackground(new java.awt.Color(255, 255, 255));
        panelPagina1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        txtMenarquia.setBackground(new java.awt.Color(255, 255, 255));
        txtMenarquia.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtMenarquia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMenarquia.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        panelAntecedentesGino.add(txtMenarquia, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 209, 35));

        lblFur.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblFur.setText("FUR");
        panelAntecedentesGino.add(lblFur, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, -1, 20));

        txtFUR.setBackground(new java.awt.Color(255, 255, 255));
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

        txtCSTP.setBackground(new java.awt.Color(255, 255, 255));
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

        btn_Pagina1_2.setBackground(new java.awt.Color(204, 204, 235));
        btn_Pagina1_2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btn_Pagina1_2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_btn_Pagina1_2.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        lbl_btn_Pagina1_2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_btn_Pagina1_2.setText("Página 2 ->");
        lbl_btn_Pagina1_2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_btn_Pagina1_2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btn_Pagina1_2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_btn_Pagina1_2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_btn_Pagina1_2MouseExited(evt);
            }
        });
        btn_Pagina1_2.add(lbl_btn_Pagina1_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 106, 35));

        panelAntecedentesGino.add(btn_Pagina1_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 160, 106, 35));

        panelPagina1.add(panelAntecedentesGino, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 566, 205));

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

        panelPagina1.add(panelAntecedentesLaborales, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 205, 566, 160));

        panelCartas.add(panelPagina1, "card2");

        panelPagina2.setBackground(new java.awt.Color(255, 255, 255));
        panelPagina2.setMinimumSize(new java.awt.Dimension(566, 365));
        panelPagina2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_AntecedentesGenerales.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_AntecedentesGenerales.setForeground(new java.awt.Color(31, 78, 121));
        lbl_AntecedentesGenerales.setText("ANTECEDENTES GENERALES");
        panelPagina2.add(lbl_AntecedentesGenerales, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        lbl_Familiares.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Familiares.setText("Familiares");
        panelPagina2.add(lbl_Familiares, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, 20));

        jScrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaFamiliares.setColumns(20);
        txtAreaFamiliares.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaFamiliares.setRows(5);
        jScrollPane3.setViewportView(txtAreaFamiliares);

        panelPagina2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 260, 40));

        lbl_Medicos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Medicos.setText("Médicos");
        panelPagina2.add(lbl_Medicos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, 20));

        jScrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaMedicos.setColumns(20);
        txtAreaMedicos.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaMedicos.setRows(5);
        jScrollPane4.setViewportView(txtAreaMedicos);

        panelPagina2.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 260, 40));

        lbl_Tratamientos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Tratamientos.setText("Tratamientos o medicamentos actuales");
        panelPagina2.add(lbl_Tratamientos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, 20));

        jScrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaTratamientos.setColumns(20);
        txtAreaTratamientos.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaTratamientos.setRows(5);
        jScrollPane5.setViewportView(txtAreaTratamientos);

        panelPagina2.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 260, 40));

        lbl_Laboratorios.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Laboratorios.setText("Laboratorios o estudios recientes");
        panelPagina2.add(lbl_Laboratorios, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, 20));

        jScrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaLaboratorios.setColumns(20);
        txtAreaLaboratorios.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaLaboratorios.setRows(5);
        jScrollPane6.setViewportView(txtAreaLaboratorios);

        panelPagina2.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 260, 40));

        lbl_Quirurgicos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Quirurgicos.setText("Quirúrgicos");
        panelPagina2.add(lbl_Quirurgicos, new org.netbeans.lib.awtextra.AbsoluteConstraints(283, 30, -1, 20));

        jScrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaQuirurgicos.setColumns(20);
        txtAreaQuirurgicos.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaQuirurgicos.setRows(5);
        jScrollPane7.setViewportView(txtAreaQuirurgicos);

        panelPagina2.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(283, 50, 260, 40));

        lbl_Traumaticos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Traumaticos.setText("Traumáticos");
        panelPagina2.add(lbl_Traumaticos, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 100, -1, 20));

        jScrollPane8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaTraumaticos.setColumns(20);
        txtAreaTraumaticos.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaTraumaticos.setRows(5);
        jScrollPane8.setViewportView(txtAreaTraumaticos);

        panelPagina2.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 120, 260, 40));

        lbl_Alergicos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Alergicos.setText("Alérgicos");
        panelPagina2.add(lbl_Alergicos, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 170, -1, 20));

        jScrollPane9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaAlergicos.setColumns(20);
        txtAreaAlergicos.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaAlergicos.setRows(5);
        jScrollPane9.setViewportView(txtAreaAlergicos);

        panelPagina2.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 190, 260, 40));

        lbl_Vicios.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Vicios.setText("Vicios y manías");
        panelPagina2.add(lbl_Vicios, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 240, -1, 20));

        jScrollPane10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaVicios.setColumns(20);
        txtAreaVicios.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaVicios.setRows(5);
        jScrollPane10.setViewportView(txtAreaVicios);

        panelPagina2.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 260, 260, 40));

        btn_Pagina2_1.setBackground(new java.awt.Color(204, 204, 235));
        btn_Pagina2_1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btn_Pagina2_1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_btn_Pagina2_1.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        lbl_btn_Pagina2_1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_btn_Pagina2_1.setText("<- Página 1");
        lbl_btn_Pagina2_1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_btn_Pagina2_1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btn_Pagina2_1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_btn_Pagina2_1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_btn_Pagina2_1MouseExited(evt);
            }
        });
        btn_Pagina2_1.add(lbl_btn_Pagina2_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 106, 35));

        panelPagina2.add(btn_Pagina2_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(167, 320, 106, 35));

        btn_Pagina2_3.setBackground(new java.awt.Color(204, 204, 235));
        btn_Pagina2_3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btn_Pagina2_3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_btn_Pagina2_3.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        lbl_btn_Pagina2_3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_btn_Pagina2_3.setText("Página 3 ->");
        lbl_btn_Pagina2_3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_btn_Pagina2_3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btn_Pagina2_3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_btn_Pagina2_3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_btn_Pagina2_3MouseExited(evt);
            }
        });
        btn_Pagina2_3.add(lbl_btn_Pagina2_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 106, 35));

        panelPagina2.add(btn_Pagina2_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(293, 320, 106, 35));

        panelCartas.add(panelPagina2, "card3");

        panelPagina3.setBackground(new java.awt.Color(255, 255, 255));
        panelPagina3.setMinimumSize(new java.awt.Dimension(566, 365));
        panelPagina3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_RevisionPorSistemas.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_RevisionPorSistemas.setForeground(new java.awt.Color(31, 78, 121));
        lbl_RevisionPorSistemas.setText("EXAMEN FÍSICO");
        panelPagina3.add(lbl_RevisionPorSistemas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        lblTemperatura.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblTemperatura.setText("Temperatura");
        panelPagina3.add(lblTemperatura, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, 20));

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
        panelPagina3.add(txtTemperatura, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 175, 35));

        lblPulso.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblPulso.setText("Pulso");
        panelPagina3.add(lblPulso, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, 20));

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
        panelPagina3.add(txtPulso, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 175, 35));

        lblSPO2.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblSPO2.setText("sPO2");
        panelPagina3.add(lblSPO2, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 100, -1, 20));

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
        panelPagina3.add(txtSPO2, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 120, 175, 35));

        lblFR.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblFR.setText("FR");
        panelPagina3.add(lblFR, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 100, -1, 20));

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
        panelPagina3.add(txtFR, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 120, 175, 35));

        lblGlicemia.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblGlicemia.setText("Glicemia");
        panelPagina3.add(lblGlicemia, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 30, -1, 20));

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
        panelPagina3.add(txtGlicemia, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 50, 175, 35));

        lblPeso.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblPeso.setText("Peso");
        panelPagina3.add(lblPeso, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 170, -1, 20));

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
        panelPagina3.add(txtPeso, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 190, 175, 35));

        lblTalla.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblTalla.setText("Talla");
        panelPagina3.add(lblTalla, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 170, -1, 20));

        combo_Talla.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        combo_Talla.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "S", "M", "L", "XL", "XXL" }));
        combo_Talla.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelPagina3.add(combo_Talla, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 190, 175, 35));

        lblIMC.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblIMC.setText("IMC");
        panelPagina3.add(lblIMC, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, 20));

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
        panelPagina3.add(txtIMC, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 175, 35));

        lblPA.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblPA.setText("P/A");
        panelPagina3.add(lblPA, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 30, -1, -1));

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
        panelPagina3.add(txtPA, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, 175, 35));

        jLabel2.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel2.setText("Ruffier");
        panelPagina3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, 20));

        txtRuffier.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtRuffier.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelPagina3.add(txtRuffier, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 143, 35));

        lblOjoDerecho.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblOjoDerecho.setText("Agudeza ojo derecho");
        panelPagina3.add(lblOjoDerecho, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 240, -1, 20));

        txtOjoDerecho.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtOjoDerecho.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelPagina3.add(txtOjoDerecho, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 260, 143, 35));

        lblOjoIzquierdo.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblOjoIzquierdo.setText("Agudeza ojo izquierdo");
        panelPagina3.add(lblOjoIzquierdo, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 240, -1, 20));

        txtOjoIzquierdo.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtOjoIzquierdo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelPagina3.add(txtOjoIzquierdo, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 260, 143, 35));

        checkLentes.setBackground(new java.awt.Color(255, 255, 255));
        checkLentes.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        checkLentes.setText("Usa Lentes");
        panelPagina3.add(checkLentes, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 260, -1, 35));

        btn_Pagina3_2.setBackground(new java.awt.Color(204, 204, 235));
        btn_Pagina3_2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btn_Pagina3_2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_btn_Pagina3_2.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        lbl_btn_Pagina3_2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_btn_Pagina3_2.setText("<- Página 2");
        lbl_btn_Pagina3_2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_btn_Pagina3_2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btn_Pagina3_2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_btn_Pagina3_2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_btn_Pagina3_2MouseExited(evt);
            }
        });
        btn_Pagina3_2.add(lbl_btn_Pagina3_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 106, 35));

        panelPagina3.add(btn_Pagina3_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(167, 320, 106, 35));

        btn_Pagina3_4.setBackground(new java.awt.Color(204, 204, 235));
        btn_Pagina3_4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btn_Pagina3_4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_btn_Pagina3_4.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        lbl_btn_Pagina3_4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_btn_Pagina3_4.setText("Página 4 ->");
        lbl_btn_Pagina3_4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_btn_Pagina3_4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btn_Pagina3_4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_btn_Pagina3_4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_btn_Pagina3_4MouseExited(evt);
            }
        });
        btn_Pagina3_4.add(lbl_btn_Pagina3_4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 106, 35));

        panelPagina3.add(btn_Pagina3_4, new org.netbeans.lib.awtextra.AbsoluteConstraints(293, 320, 106, 35));

        panelCartas.add(panelPagina3, "card4");

        panelPagina4.setBackground(new java.awt.Color(255, 255, 255));
        panelPagina4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblAlteraciones.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblAlteraciones.setText("Alteraciones");
        panelPagina4.add(lblAlteraciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        jScrollPane13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaAlteraciones.setColumns(20);
        txtAreaAlteraciones.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaAlteraciones.setRows(5);
        jScrollPane13.setViewportView(txtAreaAlteraciones);

        panelPagina4.add(jScrollPane13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 260, 35));

        lblPielFaneras.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblPielFaneras.setText("Piel y Faneras");
        panelPagina4.add(lblPielFaneras, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jScrollPane14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaPielFaneras.setColumns(20);
        txtAreaPielFaneras.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaPielFaneras.setRows(5);
        jScrollPane14.setViewportView(txtAreaPielFaneras);

        panelPagina4.add(jScrollPane14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 260, 35));

        lblCabeza.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblCabeza.setText("Cabeza");
        panelPagina4.add(lblCabeza, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, 20));

        jScrollPane15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaCabeza.setColumns(20);
        txtAreaCabeza.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaCabeza.setRows(5);
        jScrollPane15.setViewportView(txtAreaCabeza);

        panelPagina4.add(jScrollPane15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 260, 35));

        lblOjoidos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblOjoidos.setText("Ojos, oídos, nariz y boca");
        panelPagina4.add(lblOjoidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, 20));

        jScrollPane16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaOjoidos.setColumns(20);
        txtAreaOjoidos.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaOjoidos.setRows(5);
        jScrollPane16.setViewportView(txtAreaOjoidos);

        panelPagina4.add(jScrollPane16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 260, 35));

        lblOrofaringe.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblOrofaringe.setText("Orofaringe");
        panelPagina4.add(lblOrofaringe, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, -1, 20));

        jScrollPane17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaOrofaringe.setColumns(20);
        txtAreaOrofaringe.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaOrofaringe.setRows(5);
        jScrollPane17.setViewportView(txtAreaOrofaringe);

        panelPagina4.add(jScrollPane17, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 30, 260, 35));

        lblCuello.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblCuello.setText("Cuello");
        panelPagina4.add(lblCuello, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 70, -1, 20));

        jScrollPane18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaCuello.setColumns(20);
        txtAreaCuello.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaCuello.setRows(5);
        jScrollPane18.setViewportView(txtAreaCuello);

        panelPagina4.add(jScrollPane18, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 90, 260, 35));

        lblRespiratorio.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblRespiratorio.setText("Sistema Respiratorio");
        panelPagina4.add(lblRespiratorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 130, -1, 20));

        jScrollPane19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaRespiratorio.setColumns(20);
        txtAreaRespiratorio.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaRespiratorio.setRows(5);
        jScrollPane19.setViewportView(txtAreaRespiratorio);

        panelPagina4.add(jScrollPane19, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 150, 260, 35));

        lblCardiovascular.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblCardiovascular.setText("Sistema Cardiovascular");
        panelPagina4.add(lblCardiovascular, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 190, -1, 20));

        jScrollPane20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaCardiovascular.setColumns(20);
        txtAreaCardiovascular.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaCardiovascular.setRows(5);
        jScrollPane20.setViewportView(txtAreaCardiovascular);

        panelPagina4.add(jScrollPane20, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 210, 260, 35));

        btn_Pagina4_3.setBackground(new java.awt.Color(204, 204, 235));
        btn_Pagina4_3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btn_Pagina4_3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_btn_Pagina4_3.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        lbl_btn_Pagina4_3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_btn_Pagina4_3.setText("<- Página 3");
        lbl_btn_Pagina4_3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_btn_Pagina4_3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btn_Pagina4_3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_btn_Pagina4_3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_btn_Pagina4_3MouseExited(evt);
            }
        });
        btn_Pagina4_3.add(lbl_btn_Pagina4_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 106, 35));

        panelPagina4.add(btn_Pagina4_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(167, 320, 106, 35));

        btn_Pagina4_5.setBackground(new java.awt.Color(204, 204, 235));
        btn_Pagina4_5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btn_Pagina4_5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_btn_Pagina4_5.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        lbl_btn_Pagina4_5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_btn_Pagina4_5.setText("Página 5 ->");
        lbl_btn_Pagina4_5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_btn_Pagina4_5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btn_Pagina4_5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_btn_Pagina4_5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_btn_Pagina4_5MouseExited(evt);
            }
        });
        btn_Pagina4_5.add(lbl_btn_Pagina4_5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 106, 35));

        panelPagina4.add(btn_Pagina4_5, new org.netbeans.lib.awtextra.AbsoluteConstraints(293, 320, 106, 35));

        lblGastrointestinal.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblGastrointestinal.setText("Sistema Gastrointestinal");
        panelPagina4.add(lblGastrointestinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, -1, 20));

        jScrollPane21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaGastrointestinal.setColumns(20);
        txtAreaGastrointestinal.setRows(5);
        jScrollPane21.setViewportView(txtAreaGastrointestinal);

        panelPagina4.add(jScrollPane21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 260, 35));

        lblExtremidades.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblExtremidades.setText("Extremidades");
        panelPagina4.add(lblExtremidades, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 250, -1, 20));

        jScrollPane23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaExtremidades.setColumns(20);
        txtAreaExtremidades.setRows(5);
        jScrollPane23.setViewportView(txtAreaExtremidades);

        panelPagina4.add(jScrollPane23, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 270, 260, 35));

        panelCartas.add(panelPagina4, "card5");

        panelPagina5.setBackground(new java.awt.Color(255, 255, 255));
        panelPagina5.setMinimumSize(new java.awt.Dimension(566, 365));
        panelPagina5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_Pagina5_4.setBackground(new java.awt.Color(204, 204, 235));
        btn_Pagina5_4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btn_Pagina5_4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_btn_Pagina5_4.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        lbl_btn_Pagina5_4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_btn_Pagina5_4.setText("<- Página 4");
        lbl_btn_Pagina5_4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_btn_Pagina5_4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btn_Pagina5_4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_btn_Pagina5_4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_btn_Pagina5_4MouseExited(evt);
            }
        });
        btn_Pagina5_4.add(lbl_btn_Pagina5_4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 106, 35));

        panelPagina5.add(btn_Pagina5_4, new org.netbeans.lib.awtextra.AbsoluteConstraints(167, 320, 106, 35));

        lblGenitourinario.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblGenitourinario.setText("Sistema Genitourinario");
        panelPagina5.add(lblGenitourinario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        jScrollPane22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaGenitourinario.setColumns(20);
        txtAreaGenitourinario.setRows(5);
        jScrollPane22.setViewportView(txtAreaGenitourinario);

        panelPagina5.add(jScrollPane22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 260, 35));

        lblNeurologico.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblNeurologico.setText("Neurológico");
        panelPagina5.add(lblNeurologico, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, -1, 20));

        jScrollPane24.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaNeurologico.setColumns(20);
        txtAreaNeurologico.setRows(5);
        jScrollPane24.setViewportView(txtAreaNeurologico);

        panelPagina5.add(jScrollPane24, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 30, 260, 35));

        lblImpresionClinica.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblImpresionClinica.setText("Impresión Clínica");
        panelPagina5.add(lblImpresionClinica, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, 20));

        jScrollPane25.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaImpresionClinica.setColumns(20);
        txtAreaImpresionClinica.setRows(5);
        jScrollPane25.setViewportView(txtAreaImpresionClinica);

        panelPagina5.add(jScrollPane25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 260, 35));

        lblObservaciones.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblObservaciones.setText("Observaciones");
        panelPagina5.add(lblObservaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 70, -1, 20));

        jScrollPane26.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaObservaciones.setColumns(20);
        txtAreaObservaciones.setRows(5);
        jScrollPane26.setViewportView(txtAreaObservaciones);

        panelPagina5.add(jScrollPane26, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 90, 260, 35));

        lblAptitud.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblAptitud.setForeground(new java.awt.Color(31, 78, 121));
        lblAptitud.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAptitud.setText("APTITUD");
        panelPagina5.add(lblAptitud, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));

        rbtn_Aptitud1.setBackground(new java.awt.Color(255, 255, 255));
        grbtn_Aptitud.add(rbtn_Aptitud1);
        rbtn_Aptitud1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        rbtn_Aptitud1.setText("Apto");
        panelPagina5.add(rbtn_Aptitud1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, -1, 30));

        rbtn_Aptitud2.setBackground(new java.awt.Color(255, 255, 255));
        grbtn_Aptitud.add(rbtn_Aptitud2);
        rbtn_Aptitud2.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        rbtn_Aptitud2.setText("Apto con restricciones");
        panelPagina5.add(rbtn_Aptitud2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 180, -1, 30));

        rbtn_Aptitud3.setBackground(new java.awt.Color(255, 255, 255));
        grbtn_Aptitud.add(rbtn_Aptitud3);
        rbtn_Aptitud3.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        rbtn_Aptitud3.setText("Reevaluación");
        panelPagina5.add(rbtn_Aptitud3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 180, -1, 30));

        rbtn_Aptitud4.setBackground(new java.awt.Color(255, 255, 255));
        grbtn_Aptitud.add(rbtn_Aptitud4);
        rbtn_Aptitud4.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        rbtn_Aptitud4.setText("No apto para el puesto");
        panelPagina5.add(rbtn_Aptitud4, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 180, -1, 30));

        lblResponsable.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblResponsable.setText("Responsable:");
        panelPagina5.add(lblResponsable, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 320, -1, 20));

        lbl_Realizado.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Realizado.setText("Realizado");
        panelPagina5.add(lbl_Realizado, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, -1, 20));

        combo_Realizado.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        panelPagina5.add(combo_Realizado, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 175, 30));

        lbl_Revisado.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Revisado.setText("Revisado");
        panelPagina5.add(lbl_Revisado, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 220, -1, 20));

        combo_Revisado.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        panelPagina5.add(combo_Revisado, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 250, 175, 30));

        lbl_Autorizado.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Autorizado.setText("Autorizado");
        panelPagina5.add(lbl_Autorizado, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 220, -1, 20));

        combo_Autorizado.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        panelPagina5.add(combo_Autorizado, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 250, 175, 30));

        panelCartas.add(panelPagina5, "card6");

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

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtDiagnostico.setColumns(20);
        txtDiagnostico.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtDiagnostico.setRows(5);
        jScrollPane1.setViewportView(txtDiagnostico);

        panelFormulario.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 410, 770, 60));

        panelCartasContenido.add(panelFormulario, "card3");

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

        panelCartasContenido.add(panelCombobox, "card4");

        cont_Preempleo.add(panelCartasContenido, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 1080, 480));

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
                            getRevisionSistemas();
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
            preempleo.setTiempoLaborado1("0");
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
            preempleo.setTiempoLaborado1("0");
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
            preempleo.setTiempoLaborado1("0");
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
            preempleo.setTiempoLaborado2("0");
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
            preempleo.setTiempoLaborado3("0");
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
            preempleo.setTiempoLaborado3("0");
            emp3 = false;
        }
    }//GEN-LAST:event_txtPuesto3KeyTyped

    private void rbtn1_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn1_1ActionPerformed
        emp1 = true;
        txtEmpresa2.setEnabled(true);
        txtPuesto2.setEnabled(true);
        preempleo.setTiempoLaborado1("1");
    }//GEN-LAST:event_rbtn1_1ActionPerformed

    private void rbtn2_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn2_1ActionPerformed
        emp1 = true;
        txtEmpresa2.setEnabled(true);
        txtPuesto2.setEnabled(true);
        preempleo.setTiempoLaborado1("2");
    }//GEN-LAST:event_rbtn2_1ActionPerformed

    private void rbtn3_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn3_1ActionPerformed
        emp1 = true;
        txtEmpresa2.setEnabled(true);
        txtPuesto2.setEnabled(true);
        preempleo.setTiempoLaborado1("3");
    }//GEN-LAST:event_rbtn3_1ActionPerformed

    private void rbtn4_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn4_1ActionPerformed
        emp1 = true;
        txtEmpresa2.setEnabled(true);
        txtPuesto2.setEnabled(true);
        preempleo.setTiempoLaborado1("4");
    }//GEN-LAST:event_rbtn4_1ActionPerformed

    private void rbtn1_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn1_2ActionPerformed
        emp2 = true;
        txtEmpresa3.setEnabled(true);
        txtPuesto3.setEnabled(true);
        preempleo.setTiempoLaborado2("1");
    }//GEN-LAST:event_rbtn1_2ActionPerformed

    private void rbtn2_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn2_2ActionPerformed
        emp2 = true;
        txtEmpresa3.setEnabled(true);
        txtPuesto3.setEnabled(true);
        preempleo.setTiempoLaborado2("2");
    }//GEN-LAST:event_rbtn2_2ActionPerformed

    private void rbtn3_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn3_2ActionPerformed
        emp2 = true;
        txtEmpresa3.setEnabled(true);
        txtPuesto3.setEnabled(true);
        preempleo.setTiempoLaborado2("3");
    }//GEN-LAST:event_rbtn3_2ActionPerformed

    private void rbtn4_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn4_2ActionPerformed
        emp2 = true;
        txtEmpresa3.setEnabled(true);
        txtPuesto3.setEnabled(true);
        preempleo.setTiempoLaborado2("4");
    }//GEN-LAST:event_rbtn4_2ActionPerformed

    private void rbtn1_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn1_3ActionPerformed
        emp3 = true;
        preempleo.setTiempoLaborado3("1");
    }//GEN-LAST:event_rbtn1_3ActionPerformed

    private void rbtn2_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn2_3ActionPerformed
        emp3 = true;
        preempleo.setTiempoLaborado3("2");
    }//GEN-LAST:event_rbtn2_3ActionPerformed

    private void rbtn3_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn3_3ActionPerformed
        emp3 = true;
        preempleo.setTiempoLaborado3("3");
    }//GEN-LAST:event_rbtn3_3ActionPerformed

    private void rbtn4_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn4_3ActionPerformed
        emp3 = true;
        preempleo.setTiempoLaborado3("4");
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
            setCartaContenido(panelFormulario);
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
            setCartaContenido(panelCombobox);
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
            setCartaContenido(panelCombobox);
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
            if (lbl_SeleccionPreempleo.getText().length() > 0){
                if (contenidoActual.equals("Actualizar")){
                    setCartaContenido(panelFormulario);
                    btn_Confirmar.setBackground(new Color(92,92,235));

                    btn_Confirmar.setVisible(true);
                }else if (contenidoActual.equals("Eliminar")){
                    setCartaContenido(panelFormulario);
                    btn_Confirmar.setBackground(new Color(235,91,91));

                    btn_Confirmar.setVisible(true);            
                }
                setBuscarPreempleoAntecedentesRevisionSistemas(jtPreempleo.getValueAt(jtPreempleo.getSelectedRow(), 0).toString());
            }else
                JOptionPane.showMessageDialog(null, "Seleccione un registro de la tabla");
        } catch (SQLException ex) {
        Logger.getLogger(Preempleo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConnectException ex) {
            Logger.getLogger(Preempleo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_lbl_btnIngresarMouseClicked

    private void lbl_btn_Pagina1_2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_Pagina1_2MouseClicked
        setCartas(panelPagina2);
        btn_Pagina1_2.setBackground(util.colorCursorSale(btn_Pagina1_2.getBackground()));
    }//GEN-LAST:event_lbl_btn_Pagina1_2MouseClicked

    private void lbl_btn_Pagina1_2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_Pagina1_2MouseEntered
        btn_Pagina1_2.setBackground(util.colorCursorEntra(btn_Pagina1_2.getBackground()));
    }//GEN-LAST:event_lbl_btn_Pagina1_2MouseEntered

    private void lbl_btn_Pagina1_2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_Pagina1_2MouseExited
        btn_Pagina1_2.setBackground(util.colorCursorSale(btn_Pagina1_2.getBackground()));
    }//GEN-LAST:event_lbl_btn_Pagina1_2MouseExited

    private void lbl_btn_Pagina2_1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_Pagina2_1MouseEntered
        btn_Pagina2_1.setBackground(util.colorCursorEntra(btn_Pagina2_1.getBackground()));
    }//GEN-LAST:event_lbl_btn_Pagina2_1MouseEntered

    private void lbl_btn_Pagina2_1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_Pagina2_1MouseExited
        btn_Pagina2_1.setBackground(util.colorCursorSale(btn_Pagina2_1.getBackground()));
    }//GEN-LAST:event_lbl_btn_Pagina2_1MouseExited

    private void lbl_btn_Pagina2_1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_Pagina2_1MouseClicked
        setCartas(panelPagina1);
        btn_Pagina2_1.setBackground(util.colorCursorSale(btn_Pagina2_1.getBackground()));
    }//GEN-LAST:event_lbl_btn_Pagina2_1MouseClicked

    private void lbl_btn_Pagina3_2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_Pagina3_2MouseClicked
        setCartas(panelPagina2);
        btn_Pagina3_2.setBackground(util.colorCursorSale(btn_Pagina3_2.getBackground()));
    }//GEN-LAST:event_lbl_btn_Pagina3_2MouseClicked

    private void lbl_btn_Pagina3_2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_Pagina3_2MouseEntered
        btn_Pagina3_2.setBackground(util.colorCursorEntra(btn_Pagina3_2.getBackground()));
    }//GEN-LAST:event_lbl_btn_Pagina3_2MouseEntered

    private void lbl_btn_Pagina3_2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_Pagina3_2MouseExited
        btn_Pagina3_2.setBackground(util.colorCursorSale(btn_Pagina3_2.getBackground()));
    }//GEN-LAST:event_lbl_btn_Pagina3_2MouseExited

    private void lbl_btn_Pagina2_3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_Pagina2_3MouseClicked
        setCartas(panelPagina3);
        btn_Pagina2_3.setBackground(util.colorCursorSale(btn_Pagina2_3.getBackground()));
    }//GEN-LAST:event_lbl_btn_Pagina2_3MouseClicked

    private void lbl_btn_Pagina2_3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_Pagina2_3MouseEntered
        btn_Pagina2_3.setBackground(util.colorCursorEntra(btn_Pagina2_3.getBackground()));
    }//GEN-LAST:event_lbl_btn_Pagina2_3MouseEntered

    private void lbl_btn_Pagina2_3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_Pagina2_3MouseExited
        btn_Pagina2_3.setBackground(util.colorCursorSale(btn_Pagina2_3.getBackground()));
    }//GEN-LAST:event_lbl_btn_Pagina2_3MouseExited

    private void lbl_btn_Pagina3_4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_Pagina3_4MouseClicked
        setCartas(panelPagina4);
        btn_Pagina3_4.setBackground(util.colorCursorSale(btn_Pagina3_4.getBackground()));
    }//GEN-LAST:event_lbl_btn_Pagina3_4MouseClicked

    private void lbl_btn_Pagina3_4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_Pagina3_4MouseEntered
        btn_Pagina3_4.setBackground(util.colorCursorEntra(btn_Pagina3_4.getBackground()));
    }//GEN-LAST:event_lbl_btn_Pagina3_4MouseEntered

    private void lbl_btn_Pagina3_4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_Pagina3_4MouseExited
        btn_Pagina3_4.setBackground(util.colorCursorSale(btn_Pagina3_4.getBackground()));
    }//GEN-LAST:event_lbl_btn_Pagina3_4MouseExited

    private void lbl_btn_Pagina4_3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_Pagina4_3MouseClicked
        setCartas(panelPagina3);
        btn_Pagina4_3.setBackground(util.colorCursorSale(btn_Pagina4_3.getBackground()));
    }//GEN-LAST:event_lbl_btn_Pagina4_3MouseClicked

    private void lbl_btn_Pagina4_3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_Pagina4_3MouseEntered
        btn_Pagina4_3.setBackground(util.colorCursorEntra(btn_Pagina4_3.getBackground()));
    }//GEN-LAST:event_lbl_btn_Pagina4_3MouseEntered

    private void lbl_btn_Pagina4_3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_Pagina4_3MouseExited
        btn_Pagina4_3.setBackground(util.colorCursorSale(btn_Pagina4_3.getBackground()));
    }//GEN-LAST:event_lbl_btn_Pagina4_3MouseExited

    private void lbl_btn_Pagina5_4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_Pagina5_4MouseClicked
        setCartas(panelPagina4);
        btn_Pagina5_4.setBackground(util.colorCursorSale(btn_Pagina5_4.getBackground()));
    }//GEN-LAST:event_lbl_btn_Pagina5_4MouseClicked

    private void lbl_btn_Pagina5_4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_Pagina5_4MouseEntered
        btn_Pagina5_4.setBackground(util.colorCursorEntra(btn_Pagina5_4.getBackground()));
    }//GEN-LAST:event_lbl_btn_Pagina5_4MouseEntered

    private void lbl_btn_Pagina5_4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_Pagina5_4MouseExited
        btn_Pagina5_4.setBackground(util.colorCursorSale(btn_Pagina5_4.getBackground()));
    }//GEN-LAST:event_lbl_btn_Pagina5_4MouseExited

    private void lbl_btn_Pagina4_5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_Pagina4_5MouseClicked
        setCartas(panelPagina5);
        btn_Pagina4_5.setBackground(util.colorCursorSale(btn_Pagina4_5.getBackground()));
    }//GEN-LAST:event_lbl_btn_Pagina4_5MouseClicked

    private void lbl_btn_Pagina4_5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_Pagina4_5MouseEntered
        btn_Pagina4_5.setBackground(util.colorCursorEntra(btn_Pagina4_5.getBackground()));
    }//GEN-LAST:event_lbl_btn_Pagina4_5MouseEntered

    private void lbl_btn_Pagina4_5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_Pagina4_5MouseExited
        btn_Pagina4_5.setBackground(util.colorCursorSale(btn_Pagina4_5.getBackground()));
    }//GEN-LAST:event_lbl_btn_Pagina4_5MouseExited

    private void txtPAFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPAFocusLost
        if (!(util.verificarNumero(txtPA.getText())) && (txtPA.getText().length() > 0))
        txtPA.requestFocus();
        if (txtPA.getText().isEmpty()){
            txtPA.setText("mmHg");
            txtPA.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtPAFocusLost

    private void txtPAFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPAFocusGained
        if (String.valueOf(txtPA.getText()).equals("mmHg")){
            txtPA.setText("");
            txtPA.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtPAFocusGained

    private void txtIMCFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIMCFocusLost
        if (!(util.verificarNumero(txtIMC.getText())) && (txtIMC.getText().length() > 0))
        txtIMC.requestFocus();
        if (txtIMC.getText().isEmpty()){
            txtIMC.setText("Kg/m^2");
            txtIMC.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtIMCFocusLost

    private void txtIMCFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIMCFocusGained
        if (String.valueOf(txtIMC.getText()).equals("Kg/m^2")){
            txtIMC.setText("");
            txtIMC.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtIMCFocusGained

    private void txtPesoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesoFocusLost
        if (!(util.verificarNumero(txtPeso.getText())) && (txtPeso.getText().length() > 0))
        txtPeso.requestFocus();
        if (txtPeso.getText().isEmpty()){
            txtPeso.setText("lb");
            txtPeso.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtPesoFocusLost

    private void txtPesoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesoFocusGained
        if (String.valueOf(txtPeso.getText()).equals("lb")){
            txtPeso.setText("");
            txtPeso.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtPesoFocusGained

    private void txtGlicemiaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGlicemiaFocusLost
        if (!(util.verificarNumero(txtGlicemia.getText())) && (txtGlicemia.getText().length() > 0))
        txtGlicemia.requestFocus();
        if (txtGlicemia.getText().isEmpty()){
            txtGlicemia.setText("mg/dl");
            txtGlicemia.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtGlicemiaFocusLost

    private void txtGlicemiaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGlicemiaFocusGained
        if (String.valueOf(txtGlicemia.getText()).equals("mg/dl")){
            txtGlicemia.setText("");
            txtGlicemia.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtGlicemiaFocusGained

    private void txtFRFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFRFocusLost
        if (!(util.verificarNumero(txtFR.getText())) && (txtFR.getText().length() > 0))
        txtFR.requestFocus();
        if (txtFR.getText().isEmpty()){
            txtFR.setText("RPM");
            txtFR.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtFRFocusLost

    private void txtFRFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFRFocusGained
        if (String.valueOf(txtFR.getText()).equals("RPM")){
            txtFR.setText("");
            txtFR.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtFRFocusGained

    private void txtSPO2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSPO2FocusLost
        if (!(util.verificarNumero(txtSPO2.getText())) && (txtSPO2.getText().length() > 0))
        txtSPO2.requestFocus();
        if (txtSPO2.getText().isEmpty()){
            txtSPO2.setText("%");
            txtSPO2.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtSPO2FocusLost

    private void txtSPO2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSPO2FocusGained
        if (String.valueOf(txtSPO2.getText()).equals("%")){
            txtSPO2.setText("");
            txtSPO2.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtSPO2FocusGained

    private void txtPulsoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPulsoFocusLost
        if (!(util.verificarNumero(txtPulso.getText())) && (txtPulso.getText().length() > 0))
        txtPulso.requestFocus();
        if (txtPulso.getText().isEmpty()){
            txtPulso.setText("LPM");
            txtPulso.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtPulsoFocusLost

    private void txtPulsoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPulsoFocusGained
        if (String.valueOf(txtPulso.getText()).equals("LPM")){
            txtPulso.setText("");
            txtPulso.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtPulsoFocusGained

    private void txtTemperaturaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTemperaturaFocusLost
        if (!(util.verificarNumero(txtTemperatura.getText())) && (txtTemperatura.getText().length() > 0))
        txtTemperatura.requestFocus();
        if (txtTemperatura.getText().isEmpty()){
            txtTemperatura.setText("°C");
            txtTemperatura.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtTemperaturaFocusLost

    private void txtTemperaturaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTemperaturaFocusGained
        if (String.valueOf(txtTemperatura.getText()).equals("°C")){
            txtTemperatura.setText("");
            txtTemperatura.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtTemperaturaFocusGained

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel btn_Actualizar;
    public javax.swing.JPanel btn_Confirmar;
    public javax.swing.JPanel btn_Crear;
    public javax.swing.JPanel btn_Eliminar;
    public javax.swing.JPanel btn_Ingresar;
    private javax.swing.JPanel btn_Pagina1_2;
    private javax.swing.JPanel btn_Pagina2_1;
    private javax.swing.JPanel btn_Pagina2_3;
    private javax.swing.JPanel btn_Pagina3_2;
    private javax.swing.JPanel btn_Pagina3_4;
    private javax.swing.JPanel btn_Pagina4_3;
    private javax.swing.JPanel btn_Pagina4_5;
    private javax.swing.JPanel btn_Pagina5_4;
    private javax.swing.JCheckBox checkLentes;
    private javax.swing.JComboBox<String> combo_Autorizado;
    private javax.swing.JComboBox<String> combo_Realizado;
    private javax.swing.JComboBox<String> combo_Revisado;
    private javax.swing.JComboBox<String> combo_Talla;
    public javax.swing.JPanel cont_Preempleo;
    private com.raven.datechooser.DateChooser fechaCSTP;
    private com.raven.datechooser.DateChooser fechaFUR;
    private com.raven.datechooser.DateChooser fechaMenarquia;
    private javax.swing.ButtonGroup grbtn_Aptitud;
    public static javax.swing.ButtonGroup grbtn_Sexo;
    public static javax.swing.ButtonGroup grbtn_empresa1;
    public static javax.swing.ButtonGroup grbtn_empresa2;
    public static javax.swing.ButtonGroup grbtn_empresa3;
    public javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    public javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane24;
    private javax.swing.JScrollPane jScrollPane25;
    private javax.swing.JScrollPane jScrollPane26;
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
    public javax.swing.JLabel lblAlteraciones;
    public javax.swing.JLabel lblAntecedentesGi;
    public javax.swing.JLabel lblAntecedentesLaborales;
    private javax.swing.JLabel lblAptitud;
    public javax.swing.JLabel lblCSTP;
    private javax.swing.JLabel lblCabeza;
    private javax.swing.JLabel lblCardiovascular;
    public javax.swing.JLabel lblClinica;
    private javax.swing.JLabel lblCuello;
    public javax.swing.JLabel lblDiagnostico;
    public javax.swing.JLabel lblEmpresa;
    public javax.swing.JLabel lblEstadoCivil;
    private javax.swing.JLabel lblExtremidades;
    public javax.swing.JLabel lblFR;
    public javax.swing.JLabel lblFechaMod;
    public javax.swing.JLabel lblFur;
    public javax.swing.JLabel lblG;
    private javax.swing.JLabel lblGastrointestinal;
    private javax.swing.JLabel lblGenitourinario;
    public javax.swing.JLabel lblGlicemia;
    public javax.swing.JLabel lblHM;
    public javax.swing.JLabel lblHV;
    public javax.swing.JLabel lblIMC;
    public javax.swing.JLabel lblIdentificacion;
    private javax.swing.JLabel lblImpresionClinica;
    public javax.swing.JLabel lblMPF;
    public javax.swing.JLabel lblMenarquia;
    private javax.swing.JLabel lblNeurologico;
    public javax.swing.JLabel lblNivelAcademico;
    public javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblObservaciones;
    private javax.swing.JLabel lblOjoDerecho;
    private javax.swing.JLabel lblOjoIzquierdo;
    private javax.swing.JLabel lblOjoidos;
    private javax.swing.JLabel lblOrofaringe;
    public javax.swing.JLabel lblP;
    private javax.swing.JLabel lblPA;
    public javax.swing.JLabel lblPeso;
    private javax.swing.JLabel lblPielFaneras;
    public javax.swing.JLabel lblPuesto;
    public javax.swing.JLabel lblPuestoAplica;
    public javax.swing.JLabel lblPulso;
    private javax.swing.JLabel lblRespiratorio;
    private javax.swing.JLabel lblResponsable;
    public javax.swing.JLabel lblSPO2;
    public javax.swing.JLabel lblSeguridad;
    public javax.swing.JLabel lblSexo;
    public javax.swing.JLabel lblTalla;
    public javax.swing.JLabel lblTelefono;
    public javax.swing.JLabel lblTemperatura;
    public javax.swing.JLabel lblTiempoLaborado;
    public javax.swing.JLabel lblTituloCombobox;
    public javax.swing.JLabel lblTituloPrincipal;
    public javax.swing.JLabel lblTitulos;
    private javax.swing.JLabel lbl_Alergicos;
    private javax.swing.JLabel lbl_AntecedentesGenerales;
    private javax.swing.JLabel lbl_Autorizado;
    public javax.swing.JLabel lbl_BtnEliminar;
    private javax.swing.JLabel lbl_Familiares;
    public javax.swing.JLabel lbl_InicioInicio;
    private javax.swing.JLabel lbl_Laboratorios;
    private javax.swing.JLabel lbl_Medicos;
    private javax.swing.JLabel lbl_Quirurgicos;
    private javax.swing.JLabel lbl_Realizado;
    private javax.swing.JLabel lbl_Revisado;
    private javax.swing.JLabel lbl_RevisionPorSistemas;
    private javax.swing.JLabel lbl_SeleccionPreempleo;
    private javax.swing.JLabel lbl_TituloSeleccion;
    private javax.swing.JLabel lbl_Tratamientos;
    private javax.swing.JLabel lbl_Traumaticos;
    private javax.swing.JLabel lbl_Vicios;
    public javax.swing.JLabel lbl_btnActualizar;
    public javax.swing.JLabel lbl_btnCrear;
    public javax.swing.JLabel lbl_btnIngresar;
    public javax.swing.JLabel lbl_btn_Confirmar;
    private javax.swing.JLabel lbl_btn_Pagina1_2;
    private javax.swing.JLabel lbl_btn_Pagina2_1;
    private javax.swing.JLabel lbl_btn_Pagina2_3;
    private javax.swing.JLabel lbl_btn_Pagina3_2;
    private javax.swing.JLabel lbl_btn_Pagina3_4;
    private javax.swing.JLabel lbl_btn_Pagina4_3;
    private javax.swing.JLabel lbl_btn_Pagina4_5;
    private javax.swing.JLabel lbl_btn_Pagina5_4;
    public javax.swing.JPanel panelAG;
    private javax.swing.JPanel panelAntecedentesGino;
    private javax.swing.JPanel panelAntecedentesLaborales;
    public javax.swing.JPanel panelBotonesCRUD;
    private javax.swing.JPanel panelCartas;
    private javax.swing.JPanel panelCartasContenido;
    public javax.swing.JPanel panelCombobox;
    public javax.swing.JPanel panelEdicion;
    public javax.swing.JPanel panelFecha;
    public javax.swing.JPanel panelFormulario;
    public javax.swing.JPanel panelInicio;
    public javax.swing.JPanel panelMenu1;
    public javax.swing.JPanel panelPagina1;
    private javax.swing.JPanel panelPagina2;
    private javax.swing.JPanel panelPagina3;
    private javax.swing.JPanel panelPagina4;
    private javax.swing.JPanel panelPagina5;
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
    private javax.swing.JRadioButton rbtn_Aptitud1;
    private javax.swing.JRadioButton rbtn_Aptitud2;
    private javax.swing.JRadioButton rbtn_Aptitud3;
    private javax.swing.JRadioButton rbtn_Aptitud4;
    public javax.swing.JRadioButton rbtn_SexoF;
    public javax.swing.JRadioButton rbtn_SexoM;
    public javax.swing.JPanel tablaTitulos;
    public javax.swing.JTextField txtAB;
    private javax.swing.JTextArea txtAreaAlergicos;
    private javax.swing.JTextArea txtAreaAlteraciones;
    private javax.swing.JTextArea txtAreaCabeza;
    private javax.swing.JTextArea txtAreaCardiovascular;
    private javax.swing.JTextArea txtAreaCuello;
    private javax.swing.JTextArea txtAreaExtremidades;
    private javax.swing.JTextArea txtAreaFamiliares;
    private javax.swing.JTextArea txtAreaGastrointestinal;
    private javax.swing.JTextArea txtAreaGenitourinario;
    private javax.swing.JTextArea txtAreaImpresionClinica;
    private javax.swing.JTextArea txtAreaLaboratorios;
    private javax.swing.JTextArea txtAreaMedicos;
    private javax.swing.JTextArea txtAreaNeurologico;
    private javax.swing.JTextArea txtAreaObservaciones;
    private javax.swing.JTextArea txtAreaOjoidos;
    private javax.swing.JTextArea txtAreaOrofaringe;
    private javax.swing.JTextArea txtAreaPielFaneras;
    private javax.swing.JTextArea txtAreaQuirurgicos;
    private javax.swing.JTextArea txtAreaRespiratorio;
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
    public javax.swing.JTextField txtFR;
    public javax.swing.JTextField txtFUR;
    public javax.swing.JTextField txtFecha;
    public javax.swing.JTextField txtG;
    public javax.swing.JTextField txtGlicemia;
    public javax.swing.JTextField txtHM;
    public javax.swing.JTextField txtHV;
    public javax.swing.JTextField txtIMC;
    public javax.swing.JTextField txtIdentificacion;
    public javax.swing.JTextField txtMPF;
    public javax.swing.JTextField txtMenarquia;
    public javax.swing.JTextField txtNivelAcademico;
    public javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtOjoDerecho;
    private javax.swing.JTextField txtOjoIzquierdo;
    public javax.swing.JTextField txtP;
    private javax.swing.JTextField txtPA;
    public javax.swing.JTextField txtPeso;
    public javax.swing.JLabel txtPreempleoId;
    public javax.swing.JTextField txtPuesto1;
    public javax.swing.JTextField txtPuesto2;
    public javax.swing.JTextField txtPuesto3;
    public javax.swing.JTextField txtPuestoAplica;
    public javax.swing.JTextField txtPulso;
    private javax.swing.JTextField txtRuffier;
    public javax.swing.JTextField txtSPO2;
    public javax.swing.JTextField txtTelefono;
    public javax.swing.JTextField txtTemperatura;
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
