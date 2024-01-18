package Vistas;
import javax.swing.JPanel;
import Utilitarios.Utilitarios;
import Utilitarios.DatosPaginacion;
import Utilitarios.ModeloTabla;
import Modelos.AntecedentesMod;
import Modelos.EmpleadoMod;
import Modelos.EvaluacionPeriodicaMod;
import Modelos.RevisionSistemasMod;
import Modelos.UsuarioMod;
import Controladores.PaginadorTabla;
import Controladores.AntecedentesCtrl;
import Controladores.EmpleadoCtrl;
import Controladores.EvaluacionPeriodicaCtrl;
import Controladores.RevisionSistemasCtrl;
import Controladores.UsuarioCtrl;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

public class EvaluacionPeriodica extends javax.swing.JFrame implements ActionListener, TableModelListener{

    String contenidoActual, clinicaId = "1";
    public String nombreContenido = "EvaluacionPeriodica", responsable;
    private JComboBox<Integer> filasPermitidasFicha, filasPermitidasEmpleado;
    private Utilitarios util = new Utilitarios();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime now = LocalDateTime.now();
    
    //Modelos
    private AntecedentesMod antecedentes = new AntecedentesMod();
    private EvaluacionPeriodicaMod evaluacionPeriodica = new EvaluacionPeriodicaMod();
    private RevisionSistemasMod revisionSistemas = new RevisionSistemasMod();
    private EmpleadoMod empleado = new EmpleadoMod();
    private UsuarioMod usuario = new UsuarioMod();
    //Controladores
    private AntecedentesCtrl antCtrl = new AntecedentesCtrl();
    private EvaluacionPeriodicaCtrl evaPerCtrl = new EvaluacionPeriodicaCtrl();
    private RevisionSistemasCtrl revSisCtrl = new RevisionSistemasCtrl();
    private EmpleadoCtrl empCtrl = new EmpleadoCtrl();
    private UsuarioCtrl usrCtrl = new UsuarioCtrl();
    private PaginadorTabla <ConsultaGeneral> paginadorEvaluacionPeriodica, paginadorEmpleado;
    public EvaluacionPeriodica() {
        initComponents();
        contenidoActual = "Inicio";
        setCartaContenido(panelInicio);
        setCartas(panelPagina1);
        setCartaEmpleado(panelComboboxEmpleado);
        btn_Confirmar.setVisible(false);
        lbl_btn_Confirmar.setEnabled(false);
        txtHora.setEnabled(false);
        timeHora.set24hourMode(true);
        jtEvaluacionPeriodica.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent Mouse_evt){
                JTable tabla = (JTable) Mouse_evt.getSource();
                Point punto = Mouse_evt.getPoint();
                int fila = tabla.rowAtPoint(punto);
                if (Mouse_evt.getClickCount() == 1){
                    lbl_SeleccionEvaluacionPeriodica.setText(jtEvaluacionPeriodica.getValueAt(jtEvaluacionPeriodica.getSelectedRow(), 3).toString());
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
    }
    
    private void reset(){
        lblAG.setIcon(util.construirImagen("/Imagenes/AG_logo.png", lblAG.getWidth(), lblAG.getHeight()));
        resetUsuarios();
        resetEvaluacionPeriodica();
        resetEmpleado();
        resetRevisionSistemas();
        construirEtiquetas();
        setTabla();
        lbl_SeleccionEvaluacionPeriodica.setText("");
        panelPaginacionEvaluacionPeriodica.removeAll();
        panelPaginacionEmpleado.removeAll();
    }
    
    private void resetEmpleado(){
        txtPreempleoId.setText("");
        txtNombre.setText("");
        txtEdad.setText("");
        txtCodigo.setText("");
        txtSeleccionEmpleado.setText("");
        
        empleado.setCodigo("");
        empleado.setDireccion("");
        empleado.setId("");
        empleado.setNombre("");
        empleado.setSexo("");
        empleado.setTelefono("");
        empleado.setEstado("");
        setCartaEmpleado(panelComboboxEmpleado);
    }
    
    private void resetEvaluacionPeriodica(){
        timeHora.now();
        setSexo("");
        txtEdad.setText("");
        txtArea.setText("");
        txtPuesto.setText("");
        txtAreaRestricciones.setText("");
        txtAreaRestricciones.setEnabled(false);
        
        evaluacionPeriodica.setId("");
        evaluacionPeriodica.setFecha("");
        evaluacionPeriodica.setHora("");
        evaluacionPeriodica.setEdad("");
        evaluacionPeriodica.setArea("");
        evaluacionPeriodica.setPuesto("");
        evaluacionPeriodica.setAptitud("");
        evaluacionPeriodica.setEmpleadoId("");
        evaluacionPeriodica.setClinicaId("");
        evaluacionPeriodica.setAntecedentesId("");
        evaluacionPeriodica.setRevisionSistemasId("");
        evaluacionPeriodica.setResponsable("");
        evaluacionPeriodica.setEstado("");
    }
    
    private void resetRevisionSistemas(){
        txtAreaAlteraciones.setText("");
        txtAreaPielFaneras.setText("");
        txtAreaCabeza.setText("");
        txtAreaOjoidos.setText("");
        txtAreaOrofaringe.setText("");
        txtAreaCuello.setText("");
        txtAreaCardiopulmonar.setText("");
        txtAreaTorax.setText("");
        txtAreaAbdomen.setText("");
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
        txtTalla.setText("m");
        txtTalla.setForeground(Color.gray);
        txtOjoDerechoNumerador.setText("");
        txtOjoIzquierdoNumerador.setText("");
        checkLentes.setSelected(false);
        txtAreaImpresionClinica.setText("");
        
        revisionSistemas.setAlteraciones("");
        revisionSistemas.setPielFaneras("");
        revisionSistemas.setCabeza("");
        revisionSistemas.setOjoOidoNarizBoca("");
        revisionSistemas.setOrofarinje("");
        revisionSistemas.setCuello("");
        revisionSistemas.setCardiopulmonar("");
        revisionSistemas.setTorax("");
        revisionSistemas.setAbdomen("");
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
            listaUsuarios = usrCtrl.seleccionarTodos();
            List<String> nombresUsuarios = new ArrayList<String>();
            for (int i = 0; i < listaUsuarios.size(); i++){
                if (listaUsuarios.get(i).getId().equals(responsable)){
                    usuario = usrCtrl.buscarFila(responsable);
                    lblResponsable.setText("Responsable: " + usuario.getNombre());
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaGeneral.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConnectException ex) {
            Logger.getLogger(ConsultaGeneral.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void construirEtiquetas(){
        String codigo;
        try {
            codigo = evaPerCtrl.getMaxId();
            lblTitulos.setText("<html><center>Formato<br><b>FICHA MÉDICA EVALUACIÓN RIESGOS CRÍTICOS / EVALUACIÓN PERIÓDICA</b><br>CORRELATIVO: " + codigo + "</center></html>");
            lblSeguridad.setText("<html><center>Salud Ocupacional</center></html>");
            lblFechaMod.setText("<html><center>Fecha de<br>modificacion:<br>"+ util.convertirFechaGUI(now.format(dtf)) + "</center></html>");
            
            txtFecha.setText(util.convertirFechaGUI(now.format(dtf)));
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaGeneral.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConnectException ex) {
            Logger.getLogger(ConsultaGeneral.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean verificarSexo(){
        boolean valido = false;
        if (grbtn_Sexo.isSelected(rbtn_SexoM.getModel()) || (grbtn_Sexo.isSelected(rbtn_SexoF.getModel())))
            valido = true;
        return valido;
    }
    
    private boolean verificarAptitud(){
        boolean valido = false;
        if((rbtn_Aptitud1.isSelected()) || ((rbtn_Aptitud2.isSelected()) && (txtAreaRestricciones.getText().length()>0)) || (rbtn_Aptitud3.isSelected()) || (rbtn_Aptitud4.isSelected()))
            valido = true;
        return valido;
    }
    
    private boolean verificarEmpleado(){
        boolean valido = false;
        if ((txtNombre.getText().length()>0) && (txtCodigo.getText().length()>0))
            valido = true;
        else valido = false;
        return valido;
    }
    
    private boolean verificarEvaluacionPeriodica(){
        boolean valido = false;
        if ((util.verificarFlotante(txtEdad.getText())) && (verificarSexo()) && (txtArea.getText().length()>0) && (txtPuesto.getText().length()>0) && (txtHora.getText().length() > 0) && (verificarAptitud()))
            valido = true;
        return valido;
    }
    
    private boolean verificarAntecedentes(){
        boolean valido = false;
        if (((util.verificarFlotante(txtG.getText())) || (txtG.getText().length() == 0)) && ((util.verificarFlotante(txtP.getText())) || (txtP.getText().length() == 0)) && ((util.verificarFlotante(txtHV.getText())) || (txtHV.getText().length() == 0)) && ((util.verificarFlotante(txtHM.getText())) || (txtHM.getText().length() == 0)) && (util.verificarFlotante(txtAB.getText()) || (txtAB.getText().length() == 0)) && (txtDiagnostico.getText().length()>0))
                valido = true;
        return valido;
    }
    
    private boolean verificarRevisionSistemas(){
        boolean valido = false;
        if ((util.verificarFlotante(txtTemperatura.getText()) || (txtTemperatura.getText().equals("°C"))) && 
           ((util.verificarFlotante(txtPulso.getText())) || (txtPulso.getText().equals("LPM"))) && ((util.verificarFlotante(txtSPO2.getText())) || (txtSPO2.getText().equals("%"))) && 
           ((util.verificarFlotante(txtFR.getText())) || (txtFR.getText().equals("RPM"))) && ((util.verificarFlotante(txtGlicemia.getText())) || (txtGlicemia.getText().equals("mg/dl"))) && 
           ((util.verificarFlotante(txtPeso.getText())) || (txtPeso.getText().equals("lb"))) && (util.verificarFlotante(txtIMC.getText()) || (txtIMC.getText().equals("Kg/m^2"))) && 
           ((util.verificarFlotante(txtRuffier.getText())) || (txtRuffier.getText().equals(""))) &&
           (((txtOjoDerechoNumerador.getText().length()>0) && (txtOjoDerechoDenominador.getText().length()>0) && (txtOjoIzquierdoNumerador.getText().length()>0) && (txtOjoIzquierdoDenominador.getText().length()>0)) ||
           (((txtOjoDerechoNumerador.getText().length()>0) && (txtOjoDerechoDenominador.getText().length()>0)) && !((txtOjoIzquierdoNumerador.getText().length()>0) && (txtOjoIzquierdoDenominador.getText().length()>0))) ||
           (((txtOjoIzquierdoNumerador.getText().length()>0) && (txtOjoIzquierdoDenominador.getText().length()>0)) && !((txtOjoDerechoNumerador.getText().length()>0) && (txtOjoDerechoDenominador.getText().length()>0))) ||
           !((txtOjoDerechoDenominador.getText().length()>0) || (txtOjoDerechoNumerador.getText().length()>0) || (txtOjoIzquierdoNumerador.getText().length()>0) || (txtOjoIzquierdoDenominador.getText().length()>0))))
                valido = true;
        return valido;
    }
    
    private void setCartaContenido(JPanel carta){
        panelCartasContenido.removeAll();
        panelCartasContenido.add(carta);
        panelCartasContenido.repaint();
        panelCartasContenido.revalidate();
    }
    
    private void setCartaEmpleado(JPanel carta){
        panelCartasEmpleado.removeAll();
        panelCartasEmpleado.add(carta);
        panelCartasEmpleado.repaint();
        panelCartasEmpleado.revalidate();
    }
    
    private void setCartas (JPanel carta){
        panelCartas.removeAll();
        panelCartas.add(carta);
        panelCartas.repaint();
        panelCartas.revalidate();
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
     * La evaluacionPeriodica en pantalla se guardarán en una variable global "evaluacionPeriodica"
     */
    private void getEvaluacionPeriodica(){
        evaluacionPeriodica.setFecha(util.convertirFechaSQL(txtFecha.getText()));
        evaluacionPeriodica.setHora(txtHora.getText());
        evaluacionPeriodica.setEdad(txtEdad.getText());
        evaluacionPeriodica.setArea(txtArea.getText());
        evaluacionPeriodica.setPuesto(txtPuesto.getText());
        
        if(rbtn_Aptitud1.isSelected())
            evaluacionPeriodica.setAptitud("1");
        else if(rbtn_Aptitud2.isSelected())
            evaluacionPeriodica.setAptitud("2");
        else if(rbtn_Aptitud3.isSelected())
            evaluacionPeriodica.setAptitud("3");
        else if(rbtn_Aptitud4.isSelected())
            evaluacionPeriodica.setAptitud("4");
        
        evaluacionPeriodica.setRestricciones(txtAreaRestricciones.getText());
        evaluacionPeriodica.setClinicaId(clinicaId);
        evaluacionPeriodica.setEmpleadoId(empleado.getId());
        evaluacionPeriodica.setRevisionSistemasId(revisionSistemas.getId());
        evaluacionPeriodica.setResponsable(responsable);
        evaluacionPeriodica.setEstado("1");
    }
    
    /**
     * Los valores dentro de los evaluacionPeriodica guardados en la variable global "evaluacionPeriodica" se mostrarán en pantalla
     */
    private void setEvaluacionPeriodica(){
        txtFecha.setText(util.convertirFechaGUI(evaluacionPeriodica.getFecha()));
        txtHora.setText(evaluacionPeriodica.getHora());
        txtEdad.setText(evaluacionPeriodica.getEdad());
        txtArea.setText(evaluacionPeriodica.getArea());
        txtPuesto.setText(evaluacionPeriodica.getPuesto());
        
        switch(evaluacionPeriodica.getAptitud()){
            case "1":
                grbtn_Aptitud.setSelected(rbtn_Aptitud1.getModel(), true);
                break;
            case "2":
                grbtn_Aptitud.setSelected(rbtn_Aptitud2.getModel(), true);
                break;
            case "3":
                grbtn_Aptitud.setSelected(rbtn_Aptitud3.getModel(), true);
                break;
            case "4":
                grbtn_Aptitud.setSelected(rbtn_Aptitud4.getModel(), true);
                break;
            default:
                
                break;
        }
        txtAreaRestricciones.setText(evaluacionPeriodica.getRestricciones());
        txtAreaObservaciones.setText(revisionSistemas.getObservaciones());
        responsable = evaluacionPeriodica.getResponsable();
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
        revisionSistemas.setCardiopulmonar(txtAreaCardiopulmonar.getText());
        revisionSistemas.setTorax(txtAreaTorax.getText());
        revisionSistemas.setAbdomen(txtAreaAbdomen.getText());
        revisionSistemas.setGenitourinario(txtAreaGenitourinario.getText());
        revisionSistemas.setExtremidades(txtAreaExtremidades.getText());
        revisionSistemas.setNeurologico(txtAreaNeurologico.getText());
        if (!(txtTemperatura.getText().equals("°C")))
            revisionSistemas.setTemperatura(txtTemperatura.getText());
        else
            revisionSistemas.setTemperatura("");
        if (!(txtPulso.getText().equals("LPM")))
            revisionSistemas.setPulso(txtPulso.getText());
        else
            revisionSistemas.setPulso("");
        if (!(txtSPO2.getText().equals("%")))
            revisionSistemas.setSpo2(txtSPO2.getText());
        else
            revisionSistemas.setSpo2("");
        if (!(txtFR.getText().equals("RPM")))
            revisionSistemas.setFr(txtFR.getText());
        else
            revisionSistemas.setFr("");
        if (!(txtPA.getText().equals("mmHg")))
            revisionSistemas.setPa(txtPA.getText());
        else
            revisionSistemas.setPa("");
        if (!(txtGlicemia.getText().equals("mg/dl")))
            revisionSistemas.setGlicemia(txtGlicemia.getText());
        else
            revisionSistemas.setGlicemia("");
        if (!(txtPeso.getText().equals("lb")))
            revisionSistemas.setPeso(txtPeso.getText());
        else
            revisionSistemas.setPeso("");
        if (!(txtIMC.getText().equals("Kg/m^2")))
            revisionSistemas.setImc(txtIMC.getText());
        else
            revisionSistemas.setImc("");
        revisionSistemas.setTalla(txtTalla.getText());
        revisionSistemas.setRuffier(txtRuffier.getText());
        if((txtOjoDerechoNumerador.getText().length()>0) && (txtOjoDerechoDenominador.getText().length()>0))
            revisionSistemas.setOjoDerecho(txtOjoDerechoNumerador.getText() + "/" + txtOjoDerechoDenominador.getText());
        else
            revisionSistemas.setOjoDerecho("");
        if((txtOjoIzquierdoNumerador.getText().length()>0) && (txtOjoIzquierdoDenominador.getText().length()>0))
            revisionSistemas.setOjoIzquierdo(txtOjoIzquierdoNumerador.getText() + "/" + txtOjoIzquierdoDenominador.getText());
        else
            revisionSistemas.setOjoIzquierdo("");
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
        txtAreaCardiopulmonar.setText(revisionSistemas.getCardiopulmonar());
        txtAreaTorax.setText(revisionSistemas.getTorax());
        txtAreaAbdomen.setText(revisionSistemas.getAbdomen());
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
        txtTalla.setText(revisionSistemas.getTalla());
        txtIMC.setText(revisionSistemas.getImc());
        txtRuffier.setText(revisionSistemas.getRuffier());
        if(revisionSistemas.getOjoDerecho().length()>1){
            String [] partesOjoDerecho = revisionSistemas.getOjoDerecho().split("/");
            txtOjoDerechoNumerador.setText(partesOjoDerecho[0]);
            txtOjoDerechoDenominador.setText(partesOjoDerecho[1]);
        } else{
            txtOjoDerechoNumerador.setText("");
            txtOjoDerechoDenominador.setText("");
        }
        if(revisionSistemas.getOjoIzquierdo().length()>1){
            String [] partesOjoIzquierdo = revisionSistemas.getOjoIzquierdo().split("/");
            txtOjoIzquierdoNumerador.setText(partesOjoIzquierdo[0]);
            txtOjoIzquierdoDenominador.setText(partesOjoIzquierdo[1]);
        }else{
            txtOjoIzquierdoNumerador.setText("");
            txtOjoIzquierdoDenominador.setText("");
        }
        
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
        setCartaEmpleado(panelEmpleado);
    }
    
    public JPanel getContenido(){
        try{
            return cont_EvaluacionPeriodica;
        }catch (Exception ex) {
            Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    //Todo lo relacionado a la Tabla
    private TableModel crearModeloTablaFicha() {
        return new ModeloTabla<EvaluacionPeriodicaMod>() {
            @Override
            public Object getValueAt(EvaluacionPeriodicaMod t, int columna) {
                switch(columna){
                    case 0:
                        return t.getId();
                    case 1:
                        return t.getFecha();
                    case 2:
                        return t.getHora();
                    case 3:
                        return t.getEmpleadoId();
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
                        return "Hora";
                    case 3:
                        return "Nombre";
                }
                return null;
            }

            @Override
            public int getColumnCount() {
                return 4;
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
        
        jtEvaluacionPeriodica.setModel(crearModeloTablaFicha());
        DatosPaginacion<EvaluacionPeriodicaMod> datosPaginacionFicha;
        try {
            datosPaginacionFicha = crearDatosPaginacionEvaluacionPeriodica();
            paginadorEvaluacionPeriodica = new PaginadorTabla(jtEvaluacionPeriodica, datosPaginacionFicha, new int[]{5,10,20,25,50,75,100}, 10);
            paginadorEvaluacionPeriodica.crearListadoFilasPermitidas(panelPaginacionEvaluacionPeriodica);
            filasPermitidasFicha = paginadorEvaluacionPeriodica.getComboboxFilasPermitidas();
            filasPermitidasFicha.addActionListener(this);
            jtEvaluacionPeriodica.getModel().addTableModelListener(this);
            filasPermitidasFicha.setSelectedItem(Integer.parseInt("10"));
            
            jtEmpleado.setModel(crearModeloTablaEmpleado());
            DatosPaginacion<EmpleadoMod> datosPaginacionEmpleado = crearDatosPaginacionEmpleado();
            paginadorEmpleado = new PaginadorTabla(jtEmpleado, datosPaginacionEmpleado, new int[]{5,10,20,25,50,75,100},10);
            paginadorEmpleado.crearListadoFilasPermitidas(panelPaginacionEmpleado);
            filasPermitidasEmpleado = paginadorEmpleado.getComboboxFilasPermitidas();
            filasPermitidasEmpleado.addActionListener(this);
            filasPermitidasEmpleado.setSelectedItem(Integer.parseInt("10"));
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaGeneral.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConnectException ex) {
            Logger.getLogger(ConsultaGeneral.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private DatosPaginacion <EvaluacionPeriodicaMod> crearDatosPaginacionEvaluacionPeriodica() throws SQLException, ConnectException{
        List <EvaluacionPeriodicaMod> lista = evaPerCtrl.seleccionarTodos();
        //Reemplazar el id del empleado por el nombre del empleado
        EmpleadoMod emp = new EmpleadoMod();
        EvaluacionPeriodicaMod ficha = new EvaluacionPeriodicaMod();
        for (int i = 0; i < lista.size(); i++){
            ficha = lista.get(i);
            emp = empCtrl.buscarFila(ficha.getEmpleadoId());
            ficha.setEmpleadoId(emp.getNombre());
            lista.set(i, ficha);
        }
        return new DatosPaginacion<EvaluacionPeriodicaMod>(){
            @Override
            public int getTotalRowCount() {
                return lista.size();
            }

            @Override
            public List<EvaluacionPeriodicaMod> getRows(int startIndex, int endIndex) {
                return lista.subList(startIndex, endIndex);
            }
        };
    }
    
    private DatosPaginacion <EmpleadoMod> crearDatosPaginacionEmpleado() throws SQLException, ConnectException{
        List <EmpleadoMod> lista = empCtrl.seleccionarTodosActivos();
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
    
    private void buscarEmpleadoEvaluacionPeriodica(String valorBuscar) throws SQLException, ConnectException{
        evaluacionPeriodica = evaPerCtrl.buscarFila(valorBuscar);
        lblTitulos.setText("<html><center>Formato<br><b>FICHA MÉDICA EVALUACIÓN PERIÓDICA / RIESGOS CRÍTICOS</b><br>CÓDIGO " + evaluacionPeriodica.getId() + "</center></html>");
        empleado = empCtrl.buscarFila(evaluacionPeriodica.getEmpleadoId());
        antecedentes = antCtrl.buscarFila(evaluacionPeriodica.getAntecedentesId());
        revisionSistemas = revSisCtrl.buscarFila(evaluacionPeriodica.getRevisionSistemasId());
        setEvaluacionPeriodica();
        setAntecedentes();
        setRevisionSistemas();
        setEmpleado();
    }
    
    private void crear() throws SQLException{
        try {
            int evaPer = 0, revSis = 0, ant = 0;
            revSis = revSisCtrl.Crear(revisionSistemas);
            if (revSis > 0){
                revisionSistemas.setId(revSisCtrl.getMaxId());
                evaluacionPeriodica.setRevisionSistemasId(revisionSistemas.getId());
                ant = antCtrl.Crear(antecedentes);
                if (ant > 0){
                    antecedentes.setId(antCtrl.getMaxId());
                    evaluacionPeriodica.setAntecedentesId(antecedentes.getId());
                    evaPer = evaPerCtrl.Crear(evaluacionPeriodica);
                    if (evaPer > 0) {
                        JOptionPane.showMessageDialog(this, "REGISTRO INGRESADO CON EXITO", "Inserción de Datos", JOptionPane.INFORMATION_MESSAGE);
                        reset();
                    } else {
                        JOptionPane.showMessageDialog(this, "ERROR AL INSERTAR LA EVALUACIÓN PERIÓDICA. COMUNIQUESE CON TI", "Inserción de Datos", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                    JOptionPane.showMessageDialog(this, "ERROR AL INSERTAR LOS ANTECDENTES. COMUNIQUESE CON TI", "Inserción de Datos", JOptionPane.ERROR_MESSAGE);
            }
            else
                JOptionPane.showMessageDialog(this, "ERROR AL INSERTAR EL EXAMEN FÍSICO. COMUNIQUESE CON TI", "Inserción de Datos", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR " + e.toString() + " AL INSERTAR EL REGISTRO. COMUNIQUESE CON TI", "Inserción de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void actualizar() throws SQLException{
        try {
            int evaPer = 0, revSis = 0, ant = 0;
            revSis = revSisCtrl.Actualizar(revisionSistemas);
            if (revSis > 0){
                ant = antCtrl.Actualizar(antecedentes);
                if (ant > 0){
                    evaPer = evaPerCtrl.Actualizar(evaluacionPeriodica);
                    if (evaPer > 0) {
                        JOptionPane.showMessageDialog(this, "REGISTRO ACTUALIZADO CON EXITO", "Inserción de Datos", JOptionPane.INFORMATION_MESSAGE);
                        reset();
                        setTabla();
                        setCartaContenido(panelCombobox);
                    } else {
                        JOptionPane.showMessageDialog(this, "ERROR AL ACTUALIZAR LA EVALUACIÓN PERIÓDICA. COMUNIQUESE CON TI", "Actualización de Datos", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                    JOptionPane.showMessageDialog(this, "ERROR AL ACTUALIZAR LOS ANTECDENTES. COMUNIQUESE CON TI", "Actualización de Datos", JOptionPane.ERROR_MESSAGE);
            }
            else
                JOptionPane.showMessageDialog(this, "ERROR AL ACTUALIZAR EL EXAMEN FÍSICO. COMUNIQUESE CON TI", "Actualización de Datos", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR " + e.toString() + " AL ACTUALIZAR EL REGISTRO. COMUNIQUESE CON TI", "Actualización de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminar() throws SQLException{
        try {
            int conGen = 0;
            conGen = evaPerCtrl.Eliminar(evaluacionPeriodica);
            if (conGen > 0) {
                JOptionPane.showMessageDialog(this, "REGISTRO ELIMINADO CON EXITO", "Inserción de Datos", JOptionPane.INFORMATION_MESSAGE);
                reset();
                setTabla();
                setCartaContenido(panelCombobox);
            } else {
                JOptionPane.showMessageDialog(this, "ERROR AL ELIMINAR LA EVALUACIÓN PERIÓDICA. COMUNIQUESE CON TI", "Eliminación de Datos", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR " + e.toString() + " AL ELIMINAR EL REGISTRO. COMUNIQUESE CON TI", "Eliminación de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grbtn_Sexo = new javax.swing.ButtonGroup();
        fechaMenarquia = new com.raven.datechooser.DateChooser();
        fechaFUR = new com.raven.datechooser.DateChooser();
        fechaCSTP = new com.raven.datechooser.DateChooser();
        grbtn_Aptitud = new javax.swing.ButtonGroup();
        timeHora = new com.raven.swing.TimePicker();
        cont_EvaluacionPeriodica = new javax.swing.JPanel();
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
        panelBotonesCRUD = new javax.swing.JPanel();
        btn_Crear = new javax.swing.JPanel();
        lbl_btnCrear = new javax.swing.JLabel();
        btn_Actualizar = new javax.swing.JPanel();
        lbl_btnActualizar = new javax.swing.JLabel();
        btn_Eliminar = new javax.swing.JPanel();
        lbl_BtnEliminar = new javax.swing.JLabel();
        btn_Confirmar = new javax.swing.JPanel();
        lbl_btn_Confirmar = new javax.swing.JLabel();
        panelCartasContenido = new javax.swing.JPanel();
        panelInicio = new javax.swing.JPanel();
        lbl_InicioInicio = new javax.swing.JLabel();
        panelFormulario = new javax.swing.JPanel();
        panelCartasEmpleado = new javax.swing.JPanel();
        panelEmpleado = new javax.swing.JPanel();
        btn_OtroEmpleado = new javax.swing.JPanel();
        lbl_btnOtroEmpleado = new javax.swing.JLabel();
        lblArea = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        lblCodigo = new javax.swing.JLabel();
        txtEdad = new javax.swing.JTextField();
        rbtn_SexoM = new javax.swing.JRadioButton();
        lblSexo = new javax.swing.JLabel();
        txtHora = new javax.swing.JTextField();
        txtPuesto = new javax.swing.JTextField();
        lblPuesto1 = new javax.swing.JLabel();
        lblClinica = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        rbtn_SexoF = new javax.swing.JRadioButton();
        txtArea = new javax.swing.JTextField();
        lblHora = new javax.swing.JLabel();
        lblEdad = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        panelComboboxEmpleado = new javax.swing.JPanel();
        btn_SeleccionEmpleado = new javax.swing.JPanel();
        lbl_btnSeleccionEmpleado = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jtEmpleado = new javax.swing.JTable();
        panelPaginacionEmpleado = new javax.swing.JPanel();
        txtSeleccionEmpleado = new javax.swing.JTextField();
        lblInstruccionComboboxEmpleado = new javax.swing.JLabel();
        panelAntecedentesGino = new javax.swing.JPanel();
        lblAntecedentesGi = new javax.swing.JLabel();
        lblMPF = new javax.swing.JLabel();
        txtMPF = new javax.swing.JTextField();
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
        panelCartas = new javax.swing.JPanel();
        panelPagina1 = new javax.swing.JPanel();
        lbl_AntecedentesGenerales = new javax.swing.JLabel();
        btn_Pagina1_2 = new javax.swing.JPanel();
        lbl_btn_Pagina1_2 = new javax.swing.JLabel();
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
        jScrollPane12 = new javax.swing.JScrollPane();
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
        panelPagina2 = new javax.swing.JPanel();
        lbl_RevisionPorSistemas = new javax.swing.JLabel();
        btn_Pagina2_1 = new javax.swing.JPanel();
        lbl_btn_Pagina2_1 = new javax.swing.JLabel();
        btn_Pagina2_3 = new javax.swing.JPanel();
        lbl_btn_Pagina2_3 = new javax.swing.JLabel();
        lblTemperatura = new javax.swing.JLabel();
        txtTemperatura = new javax.swing.JTextField();
        lblGlicemia = new javax.swing.JLabel();
        txtGlicemia = new javax.swing.JTextField();
        lblPA = new javax.swing.JLabel();
        txtPA = new javax.swing.JTextField();
        lblPulso = new javax.swing.JLabel();
        txtPulso = new javax.swing.JTextField();
        lblSPO2 = new javax.swing.JLabel();
        txtSPO2 = new javax.swing.JTextField();
        lblFR = new javax.swing.JLabel();
        txtFR = new javax.swing.JTextField();
        lblIMC = new javax.swing.JLabel();
        txtIMC = new javax.swing.JTextField();
        lblPeso = new javax.swing.JLabel();
        txtPeso = new javax.swing.JTextField();
        lblTalla = new javax.swing.JLabel();
        txtTalla = new javax.swing.JTextField();
        lblRuffier = new javax.swing.JLabel();
        txtRuffier = new javax.swing.JTextField();
        lblOjoDerecho = new javax.swing.JLabel();
        txtOjoDerechoNumerador = new javax.swing.JTextField();
        lblFraccion1 = new javax.swing.JLabel();
        txtOjoDerechoDenominador = new javax.swing.JTextField();
        lblOjoIzquierdo = new javax.swing.JLabel();
        txtOjoIzquierdoNumerador = new javax.swing.JTextField();
        lblFraccion2 = new javax.swing.JLabel();
        txtOjoIzquierdoDenominador = new javax.swing.JTextField();
        checkLentes = new javax.swing.JCheckBox();
        panelPagina3 = new javax.swing.JPanel();
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
        txtAreaCardiopulmonar = new javax.swing.JTextArea();
        lblCardiovascular = new javax.swing.JLabel();
        jScrollPane20 = new javax.swing.JScrollPane();
        txtAreaTorax = new javax.swing.JTextArea();
        lblGastrointestinal = new javax.swing.JLabel();
        jScrollPane21 = new javax.swing.JScrollPane();
        txtAreaAbdomen = new javax.swing.JTextArea();
        lblExtremidades = new javax.swing.JLabel();
        jScrollPane23 = new javax.swing.JScrollPane();
        txtAreaExtremidades = new javax.swing.JTextArea();
        btn_Pagina3_2 = new javax.swing.JPanel();
        lbl_btn_Pagina3_2 = new javax.swing.JLabel();
        btn_Pagina3_4 = new javax.swing.JPanel();
        lbl_btn_Pagina3_4 = new javax.swing.JLabel();
        panelPagina4 = new javax.swing.JPanel();
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
        lblRestricciones = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        txtAreaRestricciones = new javax.swing.JTextArea();
        btn_Pagina4_3 = new javax.swing.JPanel();
        lbl_btn_Pagina4_3 = new javax.swing.JLabel();
        lblResponsable = new javax.swing.JLabel();
        panel_Diagnostico = new javax.swing.JPanel();
        lblDiagnostico = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDiagnostico = new javax.swing.JTextArea();
        panelCombobox = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblTituloCombobox = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtEvaluacionPeriodica = new javax.swing.JTable();
        btn_Ingresar = new javax.swing.JPanel();
        lbl_btnIngresar = new javax.swing.JLabel();
        panelPaginacionEvaluacionPeriodica = new javax.swing.JPanel();
        lbl_SeleccionEvaluacionPeriodica = new javax.swing.JLabel();
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

        timeHora.set24hourMode(true);
        timeHora.setDisplayText(txtHora);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cont_EvaluacionPeriodica.setBackground(new java.awt.Color(255, 255, 255));
        cont_EvaluacionPeriodica.setPreferredSize(new java.awt.Dimension(1080, 600));
        cont_EvaluacionPeriodica.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTituloPrincipal.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        lblTituloPrincipal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTituloPrincipal.setText("Ficha de Evaluación de Riesgos Críticos / Evaluación Periódica");
        cont_EvaluacionPeriodica.add(lblTituloPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, -1));

        tablaTitulos.setBackground(new java.awt.Color(255, 255, 255));
        tablaTitulos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelAG.setBackground(new java.awt.Color(255, 255, 255));
        panelAG.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelAG.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panelAG.add(lblAG, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 1, 98, 43));

        tablaTitulos.add(panelAG, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 45));

        panelTitulos.setBackground(new java.awt.Color(255, 255, 255));
        panelTitulos.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        panelTitulos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitulos.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        lblTitulos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panelTitulos.add(lblTitulos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 1, 400, 43));

        tablaTitulos.add(panelTitulos, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 0, 400, 45));

        panelEdicion.setBackground(new java.awt.Color(255, 255, 255));
        panelEdicion.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));
        panelEdicion.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panelEdicion.add(txtPreempleoId, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 0, 100, 45));

        tablaTitulos.add(panelEdicion, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 45, 100, 45));

        panelSeguridad.setBackground(new java.awt.Color(255, 255, 255));
        panelSeguridad.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        panelSeguridad.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblSeguridad.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        lblSeguridad.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panelSeguridad.add(lblSeguridad, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 45));

        tablaTitulos.add(panelSeguridad, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 45, 400, 45));

        panelFecha.setBackground(new java.awt.Color(255, 255, 255));
        panelFecha.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(0, 0, 0)));
        panelFecha.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblFechaMod.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        lblFechaMod.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFechaMod.setText("Fecha");
        panelFecha.add(lblFechaMod, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 1, 100, 90));

        tablaTitulos.add(panelFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 0, 100, 90));

        cont_EvaluacionPeriodica.add(tablaTitulos, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 30, 600, 90));

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

        cont_EvaluacionPeriodica.add(panelBotonesCRUD, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 0, 270, 120));

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

        cont_EvaluacionPeriodica.add(btn_Confirmar, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 540, 90, 35));

        panelCartasContenido.setBackground(new java.awt.Color(255, 255, 255));
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

        panelCartasEmpleado.setLayout(new java.awt.CardLayout());

        panelEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        panelEmpleado.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_OtroEmpleado.setBackground(new java.awt.Color(255, 102, 102));
        btn_OtroEmpleado.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btn_OtroEmpleado.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_btnOtroEmpleado.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lbl_btnOtroEmpleado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_btnOtroEmpleado.setText("Cambiar Empleado");
        lbl_btnOtroEmpleado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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

        panelEmpleado.add(btn_OtroEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 0, 120, 40));

        lblArea.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblArea.setText("Área de Trabajo");
        panelEmpleado.add(lblArea, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel3.setText("Fecha");
        panelEmpleado.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, 20));

        txtFecha.setEditable(false);
        txtFecha.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtFecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelEmpleado.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 100, 35));

        txtCodigo.setEditable(false);
        txtCodigo.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtCodigo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelEmpleado.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 110, 100, 35));

        lblCodigo.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblCodigo.setText("Código");
        panelEmpleado.add(lblCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 90, -1, 20));

        txtEdad.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtEdad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelEmpleado.add(txtEdad, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 110, 67, 35));

        rbtn_SexoM.setBackground(new java.awt.Color(255, 255, 255));
        grbtn_Sexo.add(rbtn_SexoM);
        rbtn_SexoM.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        rbtn_SexoM.setText("M");
        rbtn_SexoM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_SexoMActionPerformed(evt);
            }
        });
        panelEmpleado.add(rbtn_SexoM, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, -1, -1));

        lblSexo.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblSexo.setText("Sexo");
        panelEmpleado.add(lblSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 90, -1, 20));

        txtHora.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtHora.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtHora.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtHora.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtHoraMouseClicked(evt);
            }
        });
        panelEmpleado.add(txtHora, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, 70, 35));

        txtPuesto.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtPuesto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelEmpleado.add(txtPuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 170, 240, 35));

        lblPuesto1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblPuesto1.setText("Puesto");
        panelEmpleado.add(lblPuesto1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 150, -1, 20));

        lblClinica.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblClinica.setText("Clínica de Atención: Sidegua");
        panelEmpleado.add(lblClinica, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 190, 30));

        lblNombre.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblNombre.setText("Nombre");
        panelEmpleado.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, 20));

        rbtn_SexoF.setBackground(new java.awt.Color(255, 255, 255));
        grbtn_Sexo.add(rbtn_SexoF);
        rbtn_SexoF.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        rbtn_SexoF.setText("F");
        rbtn_SexoF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_SexoFActionPerformed(evt);
            }
        });
        panelEmpleado.add(rbtn_SexoF, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 110, -1, -1));

        txtArea.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtArea.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelEmpleado.add(txtArea, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 207, 35));

        lblHora.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblHora.setText("Hora");
        panelEmpleado.add(lblHora, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, -1, 20));

        lblEdad.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblEdad.setText("Edad");
        panelEmpleado.add(lblEdad, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 90, -1, 20));

        txtNombre.setEditable(false);
        txtNombre.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtNombre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelEmpleado.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 450, 35));

        panelCartasEmpleado.add(panelEmpleado, "card2");

        panelComboboxEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        panelComboboxEmpleado.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_SeleccionEmpleado.setBackground(new java.awt.Color(40, 235, 40));
        btn_SeleccionEmpleado.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btn_SeleccionEmpleado.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_btnSeleccionEmpleado.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_btnSeleccionEmpleado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_btnSeleccionEmpleado.setText("Seleccionar Empleado");
        lbl_btnSeleccionEmpleado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        btn_SeleccionEmpleado.add(lbl_btnSeleccionEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        panelComboboxEmpleado.add(btn_SeleccionEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(332, 45, 140, 40));

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

        panelComboboxEmpleado.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, 90));

        panelPaginacionEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        panelComboboxEmpleado.add(panelPaginacionEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 450, 30));

        txtSeleccionEmpleado.setEditable(false);
        txtSeleccionEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        txtSeleccionEmpleado.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtSeleccionEmpleado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtSeleccionEmpleado.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        panelComboboxEmpleado.add(txtSeleccionEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 270, 30));

        lblInstruccionComboboxEmpleado.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        lblInstruccionComboboxEmpleado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInstruccionComboboxEmpleado.setText("Seleccione un Empleado");
        panelComboboxEmpleado.add(lblInstruccionComboboxEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 485, -1));

        panelCartasEmpleado.add(panelComboboxEmpleado, "card3");

        panelFormulario.add(panelCartasEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 490, 215));

        panelAntecedentesGino.setBackground(new java.awt.Color(255, 255, 255));
        panelAntecedentesGino.setMinimumSize(new java.awt.Dimension(566, 0));
        panelAntecedentesGino.setPreferredSize(new java.awt.Dimension(556, 100));
        panelAntecedentesGino.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblAntecedentesGi.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblAntecedentesGi.setForeground(new java.awt.Color(31, 78, 121));
        lblAntecedentesGi.setText("ANTECEDENTES GINOCOBSTÉTRICOS");
        panelAntecedentesGino.add(lblAntecedentesGi, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        lblMPF.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblMPF.setText("MPF");
        panelAntecedentesGino.add(lblMPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(298, 80, -1, 20));

        txtMPF.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtMPF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMPF.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        panelAntecedentesGino.add(txtMPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(298, 100, 182, 35));

        lblMenarquia.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblMenarquia.setText("Menarquia");
        panelAntecedentesGino.add(lblMenarquia, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 20));

        txtMenarquia.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtMenarquia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMenarquia.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtMenarquia.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMenarquiaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMenarquiaFocusLost(evt);
            }
        });
        panelAntecedentesGino.add(txtMenarquia, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 86, 35));

        lblFur.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblFur.setText("FUR");
        panelAntecedentesGino.add(lblFur, new org.netbeans.lib.awtextra.AbsoluteConstraints(106, 20, -1, 20));

        txtFUR.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtFUR.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtFUR.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        panelAntecedentesGino.add(txtFUR, new org.netbeans.lib.awtextra.AbsoluteConstraints(106, 40, 86, 35));

        lblG.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblG.setText("G");
        panelAntecedentesGino.add(lblG, new org.netbeans.lib.awtextra.AbsoluteConstraints(202, 20, -1, 20));

        txtG.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtG.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtG.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtG.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtGFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtGFocusLost(evt);
            }
        });
        panelAntecedentesGino.add(txtG, new org.netbeans.lib.awtextra.AbsoluteConstraints(202, 40, 86, 35));

        lblP.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblP.setText("P");
        panelAntecedentesGino.add(lblP, new org.netbeans.lib.awtextra.AbsoluteConstraints(298, 20, -1, 20));

        txtP.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtP.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtP.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPFocusLost(evt);
            }
        });
        panelAntecedentesGino.add(txtP, new org.netbeans.lib.awtextra.AbsoluteConstraints(298, 40, 86, 35));

        lblCSTP.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblCSTP.setText("CSTP");
        panelAntecedentesGino.add(lblCSTP, new org.netbeans.lib.awtextra.AbsoluteConstraints(394, 20, -1, 20));

        txtCSTP.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtCSTP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCSTP.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtCSTP.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCSTPFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCSTPFocusLost(evt);
            }
        });
        panelAntecedentesGino.add(txtCSTP, new org.netbeans.lib.awtextra.AbsoluteConstraints(394, 40, 86, 35));

        lblHV.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblHV.setText("HV");
        panelAntecedentesGino.add(lblHV, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, 20));

        txtHV.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtHV.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtHV.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtHV.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtHVFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHVFocusLost(evt);
            }
        });
        panelAntecedentesGino.add(txtHV, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 86, 35));

        lblHM.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblHM.setText("HM");
        panelAntecedentesGino.add(lblHM, new org.netbeans.lib.awtextra.AbsoluteConstraints(106, 80, -1, 20));

        txtHM.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtHM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtHM.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtHM.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtHMFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHMFocusLost(evt);
            }
        });
        panelAntecedentesGino.add(txtHM, new org.netbeans.lib.awtextra.AbsoluteConstraints(106, 100, 86, 35));

        lblAB.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblAB.setText("AB");
        panelAntecedentesGino.add(lblAB, new org.netbeans.lib.awtextra.AbsoluteConstraints(202, 80, -1, 20));

        txtAB.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtAB.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtAB.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtAB.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtABFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtABFocusLost(evt);
            }
        });
        panelAntecedentesGino.add(txtAB, new org.netbeans.lib.awtextra.AbsoluteConstraints(202, 100, 86, 35));

        panelFormulario.add(panelAntecedentesGino, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 225, 490, 150));

        panelCartas.setLayout(new java.awt.CardLayout());

        panelPagina1.setBackground(new java.awt.Color(255, 255, 255));
        panelPagina1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_AntecedentesGenerales.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_AntecedentesGenerales.setForeground(new java.awt.Color(31, 78, 121));
        lbl_AntecedentesGenerales.setText("ANTECEDENTES GENERALES");
        panelPagina1.add(lbl_AntecedentesGenerales, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

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

        panelPagina1.add(btn_Pagina1_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(293, 320, 106, 35));

        lbl_Familiares.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Familiares.setText("Familiares");
        panelPagina1.add(lbl_Familiares, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, 20));

        jScrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaFamiliares.setColumns(20);
        txtAreaFamiliares.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaFamiliares.setRows(5);
        jScrollPane3.setViewportView(txtAreaFamiliares);

        panelPagina1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 260, 40));

        lbl_Medicos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Medicos.setText("Médicos");
        panelPagina1.add(lbl_Medicos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, 20));

        jScrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaMedicos.setColumns(20);
        txtAreaMedicos.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaMedicos.setRows(5);
        jScrollPane4.setViewportView(txtAreaMedicos);

        panelPagina1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 260, 40));

        lbl_Tratamientos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Tratamientos.setText("Tratamientos o medicamentos actuales");
        panelPagina1.add(lbl_Tratamientos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, 20));

        jScrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaTratamientos.setColumns(20);
        txtAreaTratamientos.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaTratamientos.setRows(5);
        jScrollPane5.setViewportView(txtAreaTratamientos);

        panelPagina1.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 260, 40));

        lbl_Laboratorios.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Laboratorios.setText("Laboratorios o estudios recientes");
        panelPagina1.add(lbl_Laboratorios, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, 20));

        jScrollPane12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaLaboratorios.setColumns(20);
        txtAreaLaboratorios.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaLaboratorios.setRows(5);
        jScrollPane12.setViewportView(txtAreaLaboratorios);

        panelPagina1.add(jScrollPane12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 260, 40));

        lbl_Quirurgicos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Quirurgicos.setText("Quirúrgicos");
        panelPagina1.add(lbl_Quirurgicos, new org.netbeans.lib.awtextra.AbsoluteConstraints(283, 30, -1, 20));

        jScrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaQuirurgicos.setColumns(20);
        txtAreaQuirurgicos.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaQuirurgicos.setRows(5);
        jScrollPane7.setViewportView(txtAreaQuirurgicos);

        panelPagina1.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(283, 50, 260, 40));

        lbl_Traumaticos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Traumaticos.setText("Traumáticos");
        panelPagina1.add(lbl_Traumaticos, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 100, -1, 20));

        jScrollPane8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaTraumaticos.setColumns(20);
        txtAreaTraumaticos.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaTraumaticos.setRows(5);
        jScrollPane8.setViewportView(txtAreaTraumaticos);

        panelPagina1.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 120, 260, 40));

        lbl_Alergicos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Alergicos.setText("Alérgicos");
        panelPagina1.add(lbl_Alergicos, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 170, -1, 20));

        jScrollPane9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaAlergicos.setColumns(20);
        txtAreaAlergicos.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaAlergicos.setRows(5);
        jScrollPane9.setViewportView(txtAreaAlergicos);

        panelPagina1.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 190, 260, 40));

        lbl_Vicios.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Vicios.setText("Vicios y manías");
        panelPagina1.add(lbl_Vicios, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 240, -1, 20));

        jScrollPane10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaVicios.setColumns(20);
        txtAreaVicios.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaVicios.setRows(5);
        jScrollPane10.setViewportView(txtAreaVicios);

        panelPagina1.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 260, 260, 40));

        panelCartas.add(panelPagina1, "card2");

        panelPagina2.setBackground(new java.awt.Color(255, 255, 255));
        panelPagina2.setMinimumSize(new java.awt.Dimension(566, 365));
        panelPagina2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_RevisionPorSistemas.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_RevisionPorSistemas.setForeground(new java.awt.Color(31, 78, 121));
        lbl_RevisionPorSistemas.setText("EXAMEN FÍSICO");
        panelPagina2.add(lbl_RevisionPorSistemas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

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

        lblTemperatura.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblTemperatura.setText("Temperatura");
        panelPagina2.add(lblTemperatura, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, 20));

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
        panelPagina2.add(txtTemperatura, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 175, 35));

        lblGlicemia.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblGlicemia.setText("Glicemia");
        panelPagina2.add(lblGlicemia, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 30, -1, 20));

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
        panelPagina2.add(txtGlicemia, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 50, 175, 35));

        lblPA.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblPA.setText("P/A");
        panelPagina2.add(lblPA, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 30, -1, -1));

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
        panelPagina2.add(txtPA, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, 175, 35));

        lblPulso.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblPulso.setText("Pulso");
        panelPagina2.add(lblPulso, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, 20));

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
        panelPagina2.add(txtPulso, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 175, 35));

        lblSPO2.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblSPO2.setText("sPO2");
        panelPagina2.add(lblSPO2, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 100, -1, 20));

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
        panelPagina2.add(txtSPO2, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 120, 175, 35));

        lblFR.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblFR.setText("FR");
        panelPagina2.add(lblFR, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 100, -1, 20));

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
        panelPagina2.add(txtFR, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 120, 175, 35));

        lblIMC.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblIMC.setText("IMC");
        panelPagina2.add(lblIMC, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, 20));

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
        panelPagina2.add(txtIMC, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 175, 35));

        lblPeso.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblPeso.setText("Peso");
        panelPagina2.add(lblPeso, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 170, -1, 20));

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
        panelPagina2.add(txtPeso, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 190, 175, 35));

        lblTalla.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblTalla.setText("Talla");
        panelPagina2.add(lblTalla, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 170, -1, 20));

        txtTalla.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtTalla.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtTalla.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTallaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTallaFocusLost(evt);
            }
        });
        panelPagina2.add(txtTalla, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 190, 175, 35));

        lblRuffier.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblRuffier.setText("Ruffier");
        panelPagina2.add(lblRuffier, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, 20));

        txtRuffier.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtRuffier.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelPagina2.add(txtRuffier, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 143, 35));

        lblOjoDerecho.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblOjoDerecho.setText("Agudeza ojo derecho");
        panelPagina2.add(lblOjoDerecho, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 240, -1, 20));

        txtOjoDerechoNumerador.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtOjoDerechoNumerador.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelPagina2.add(txtOjoDerechoNumerador, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 260, 40, 35));

        lblFraccion1.setFont(new java.awt.Font("Roboto", 0, 36)); // NOI18N
        lblFraccion1.setText("/");
        panelPagina2.add(lblFraccion1, new org.netbeans.lib.awtextra.AbsoluteConstraints(203, 260, -1, 35));

        txtOjoDerechoDenominador.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtOjoDerechoDenominador.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelPagina2.add(txtOjoDerechoDenominador, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 260, 40, 35));

        lblOjoIzquierdo.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblOjoIzquierdo.setText("Agudeza ojo izquierdo");
        panelPagina2.add(lblOjoIzquierdo, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 240, -1, 20));

        txtOjoIzquierdoNumerador.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtOjoIzquierdoNumerador.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelPagina2.add(txtOjoIzquierdoNumerador, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 260, 40, 35));

        lblFraccion2.setFont(new java.awt.Font("Roboto", 0, 36)); // NOI18N
        lblFraccion2.setText("/");
        panelPagina2.add(lblFraccion2, new org.netbeans.lib.awtextra.AbsoluteConstraints(353, 260, -1, 35));

        txtOjoIzquierdoDenominador.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtOjoIzquierdoDenominador.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelPagina2.add(txtOjoIzquierdoDenominador, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 260, 40, 35));

        checkLentes.setBackground(new java.awt.Color(255, 255, 255));
        checkLentes.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        checkLentes.setText("Usa Lentes");
        panelPagina2.add(checkLentes, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 260, -1, 35));

        panelCartas.add(panelPagina2, "card3");

        panelPagina3.setBackground(new java.awt.Color(255, 255, 255));
        panelPagina3.setMinimumSize(new java.awt.Dimension(566, 365));
        panelPagina3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblAlteraciones.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblAlteraciones.setText("Alteraciones");
        panelPagina3.add(lblAlteraciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        jScrollPane13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaAlteraciones.setColumns(20);
        txtAreaAlteraciones.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaAlteraciones.setRows(5);
        jScrollPane13.setViewportView(txtAreaAlteraciones);

        panelPagina3.add(jScrollPane13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 260, 35));

        lblPielFaneras.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblPielFaneras.setText("Piel y Faneras");
        panelPagina3.add(lblPielFaneras, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jScrollPane14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaPielFaneras.setColumns(20);
        txtAreaPielFaneras.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaPielFaneras.setRows(5);
        jScrollPane14.setViewportView(txtAreaPielFaneras);

        panelPagina3.add(jScrollPane14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 260, 35));

        lblCabeza.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblCabeza.setText("Cabeza");
        panelPagina3.add(lblCabeza, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, 20));

        jScrollPane15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaCabeza.setColumns(20);
        txtAreaCabeza.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaCabeza.setRows(5);
        jScrollPane15.setViewportView(txtAreaCabeza);

        panelPagina3.add(jScrollPane15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 260, 35));

        lblOjoidos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblOjoidos.setText("Ojos, oídos, nariz y boca");
        panelPagina3.add(lblOjoidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, 20));

        jScrollPane16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaOjoidos.setColumns(20);
        txtAreaOjoidos.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaOjoidos.setRows(5);
        jScrollPane16.setViewportView(txtAreaOjoidos);

        panelPagina3.add(jScrollPane16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 260, 35));

        lblOrofaringe.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblOrofaringe.setText("Orofaringe");
        panelPagina3.add(lblOrofaringe, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, -1, 20));

        jScrollPane17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaOrofaringe.setColumns(20);
        txtAreaOrofaringe.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaOrofaringe.setRows(5);
        jScrollPane17.setViewportView(txtAreaOrofaringe);

        panelPagina3.add(jScrollPane17, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 30, 260, 35));

        lblCuello.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblCuello.setText("Cuello");
        panelPagina3.add(lblCuello, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 70, -1, 20));

        jScrollPane18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaCuello.setColumns(20);
        txtAreaCuello.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaCuello.setRows(5);
        jScrollPane18.setViewportView(txtAreaCuello);

        panelPagina3.add(jScrollPane18, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 90, 260, 35));

        lblRespiratorio.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblRespiratorio.setText("Cardiopulmonar");
        panelPagina3.add(lblRespiratorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 130, -1, 20));

        jScrollPane19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaCardiopulmonar.setColumns(20);
        txtAreaCardiopulmonar.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaCardiopulmonar.setRows(5);
        jScrollPane19.setViewportView(txtAreaCardiopulmonar);

        panelPagina3.add(jScrollPane19, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 150, 260, 35));

        lblCardiovascular.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblCardiovascular.setText("Torax");
        panelPagina3.add(lblCardiovascular, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 190, -1, 20));

        jScrollPane20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaTorax.setColumns(20);
        txtAreaTorax.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaTorax.setRows(5);
        jScrollPane20.setViewportView(txtAreaTorax);

        panelPagina3.add(jScrollPane20, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 210, 260, 35));

        lblGastrointestinal.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblGastrointestinal.setText("Abdomen");
        panelPagina3.add(lblGastrointestinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, -1, 20));

        jScrollPane21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaAbdomen.setColumns(20);
        txtAreaAbdomen.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaAbdomen.setRows(5);
        jScrollPane21.setViewportView(txtAreaAbdomen);

        panelPagina3.add(jScrollPane21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 260, 35));

        lblExtremidades.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblExtremidades.setText("Extremidades");
        panelPagina3.add(lblExtremidades, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 250, -1, 20));

        jScrollPane23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaExtremidades.setColumns(20);
        txtAreaExtremidades.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaExtremidades.setRows(5);
        jScrollPane23.setViewportView(txtAreaExtremidades);

        panelPagina3.add(jScrollPane23, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 270, 260, 35));

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

        lblGenitourinario.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblGenitourinario.setText("Sistema Genitourinario");
        panelPagina4.add(lblGenitourinario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        jScrollPane22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaGenitourinario.setColumns(20);
        txtAreaGenitourinario.setRows(5);
        jScrollPane22.setViewportView(txtAreaGenitourinario);

        panelPagina4.add(jScrollPane22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 260, 35));

        lblNeurologico.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblNeurologico.setText("Neurológico");
        panelPagina4.add(lblNeurologico, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, -1, 20));

        jScrollPane24.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaNeurologico.setColumns(20);
        txtAreaNeurologico.setRows(5);
        jScrollPane24.setViewportView(txtAreaNeurologico);

        panelPagina4.add(jScrollPane24, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 30, 260, 35));

        lblImpresionClinica.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblImpresionClinica.setText("Impresión Clínica");
        panelPagina4.add(lblImpresionClinica, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, 20));

        jScrollPane25.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaImpresionClinica.setColumns(20);
        txtAreaImpresionClinica.setRows(5);
        jScrollPane25.setViewportView(txtAreaImpresionClinica);

        panelPagina4.add(jScrollPane25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 260, 35));

        lblObservaciones.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblObservaciones.setText("Observaciones");
        panelPagina4.add(lblObservaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 70, -1, 20));

        jScrollPane26.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaObservaciones.setColumns(20);
        txtAreaObservaciones.setRows(5);
        jScrollPane26.setViewportView(txtAreaObservaciones);

        panelPagina4.add(jScrollPane26, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 90, 260, 35));

        lblAptitud.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblAptitud.setForeground(new java.awt.Color(31, 78, 121));
        lblAptitud.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAptitud.setText("APTITUD");
        panelPagina4.add(lblAptitud, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));

        rbtn_Aptitud1.setBackground(new java.awt.Color(255, 255, 255));
        grbtn_Aptitud.add(rbtn_Aptitud1);
        rbtn_Aptitud1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        rbtn_Aptitud1.setText("Apto");
        panelPagina4.add(rbtn_Aptitud1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 165, -1, 30));

        rbtn_Aptitud2.setBackground(new java.awt.Color(255, 255, 255));
        grbtn_Aptitud.add(rbtn_Aptitud2);
        rbtn_Aptitud2.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        rbtn_Aptitud2.setText("Apto con restricciones");
        rbtn_Aptitud2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rbtn_Aptitud2StateChanged(evt);
            }
        });
        panelPagina4.add(rbtn_Aptitud2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 165, -1, 30));

        rbtn_Aptitud3.setBackground(new java.awt.Color(255, 255, 255));
        grbtn_Aptitud.add(rbtn_Aptitud3);
        rbtn_Aptitud3.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        rbtn_Aptitud3.setText("Reevaluación");
        panelPagina4.add(rbtn_Aptitud3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 165, -1, 30));

        rbtn_Aptitud4.setBackground(new java.awt.Color(255, 255, 255));
        grbtn_Aptitud.add(rbtn_Aptitud4);
        rbtn_Aptitud4.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        rbtn_Aptitud4.setText("No apto para el puesto");
        panelPagina4.add(rbtn_Aptitud4, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 165, -1, 30));

        lblRestricciones.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblRestricciones.setText("Restricciones");
        panelPagina4.add(lblRestricciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, 20));

        jScrollPane11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaRestricciones.setColumns(20);
        txtAreaRestricciones.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaRestricciones.setRows(5);
        jScrollPane11.setViewportView(txtAreaRestricciones);

        panelPagina4.add(jScrollPane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 546, 35));

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

        lblResponsable.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblResponsable.setText("Responsable:");
        panelPagina4.add(lblResponsable, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 320, -1, 20));

        panelCartas.add(panelPagina4, "card5");

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
        jLabel1.setText("Seleccione una Ficha de Evaluación Periódica");
        panelCombobox.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 1080, -1));

        lblTituloCombobox.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        lblTituloCombobox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTituloCombobox.setText("Título");
        panelCombobox.add(lblTituloCombobox, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, -1));

        jtEvaluacionPeriodica.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jtEvaluacionPeriodica.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jtEvaluacionPeriodica);

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

        panelPaginacionEvaluacionPeriodica.setBackground(new java.awt.Color(255, 255, 255));
        panelCombobox.add(panelPaginacionEvaluacionPeriodica, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 980, 30));

        lbl_SeleccionEvaluacionPeriodica.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lbl_SeleccionEvaluacionPeriodica.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lbl_SeleccionEvaluacionPeriodica.setPreferredSize(new java.awt.Dimension(300, 40));
        panelCombobox.add(lbl_SeleccionEvaluacionPeriodica, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        lbl_TituloSeleccion.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lbl_TituloSeleccion.setText("Registro Seleccionado");
        panelCombobox.add(lbl_TituloSeleccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        panelCartasContenido.add(panelCombobox, "card4");

        cont_EvaluacionPeriodica.add(panelCartasContenido, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 1080, 480));

        getContentPane().add(cont_EvaluacionPeriodica, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lbl_btn_ConfirmarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_ConfirmarMouseClicked
        if (lbl_btn_Confirmar.isEnabled()){
            if (verificarRevisionSistemas()){
                if (verificarEmpleado()){
                    if (verificarAntecedentes()){
                        if (verificarEvaluacionPeriodica()){
                            try {
                            getAntecedentes();
                            getRevisionSistemas();
                            getEvaluacionPeriodica();
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
                            }catch (SQLException e) {
                                JOptionPane.showMessageDialog(this, "ERROR " + e.toString() + " AL INSERTAR EL REGISTRO. COMUNIQUESE CON TI", "Inserción de Datos", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        
                    }else
                    JOptionPane.showMessageDialog(this, "Información de antecedentes incompleta o no válida", "Inserción de Datos", JOptionPane.INFORMATION_MESSAGE);
                }else
                JOptionPane.showMessageDialog(this, "Información del preempleado incompleta o no válida", "Inserción de Datos", JOptionPane.INFORMATION_MESSAGE);
            }else
            JOptionPane.showMessageDialog(this, "Información del formulario no válida", "Inserción de Datos", JOptionPane.INFORMATION_MESSAGE);
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
        if (lbl_SeleccionEvaluacionPeriodica.getText().length() > 0){
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
                buscarEmpleadoEvaluacionPeriodica(jtEvaluacionPeriodica.getValueAt(jtEvaluacionPeriodica.getSelectedRow(), 0).toString());
            } catch (SQLException ex) {
                Logger.getLogger(EvaluacionPeriodica.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ConnectException ex) {
                Logger.getLogger(EvaluacionPeriodica.class.getName()).log(Level.SEVERE, null, ex);
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

    private void lbl_btnOtroEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnOtroEmpleadoMouseClicked
        setCartaEmpleado(panelComboboxEmpleado);
        btn_OtroEmpleado.setBackground(util.colorCursorSale(btn_OtroEmpleado.getBackground()));
    }//GEN-LAST:event_lbl_btnOtroEmpleadoMouseClicked

    private void lbl_btnOtroEmpleadoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnOtroEmpleadoMouseEntered
        btn_OtroEmpleado.setBackground(util.colorCursorEntra(btn_OtroEmpleado.getBackground()));
    }//GEN-LAST:event_lbl_btnOtroEmpleadoMouseEntered

    private void lbl_btnOtroEmpleadoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnOtroEmpleadoMouseExited
        btn_OtroEmpleado.setBackground(util.colorCursorSale(btn_OtroEmpleado.getBackground()));
    }//GEN-LAST:event_lbl_btnOtroEmpleadoMouseExited

    private void rbtn_SexoMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_SexoMActionPerformed
        empleado.setSexo("M");
    }//GEN-LAST:event_rbtn_SexoMActionPerformed

    private void txtHoraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtHoraMouseClicked
        timeHora.showPopup(panelEmpleado, 0, 0);
    }//GEN-LAST:event_txtHoraMouseClicked

    private void rbtn_SexoFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_SexoFActionPerformed
        empleado.setSexo("F");
    }//GEN-LAST:event_rbtn_SexoFActionPerformed

    private void lbl_btnSeleccionEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnSeleccionEmpleadoMouseClicked
        if (txtSeleccionEmpleado.getText().length() > 0){
            try {
                empleado = empCtrl.buscarFila(jtEmpleado.getValueAt(jtEmpleado.getSelectedRow(), 0).toString());
            } catch (SQLException ex) {
                Logger.getLogger(ConsultaGeneral.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ConnectException ex) {
                Logger.getLogger(ConsultaGeneral.class.getName()).log(Level.SEVERE, null, ex);
            }
            setEmpleado();
            setCartaEmpleado(panelEmpleado);
            btn_SeleccionEmpleado.setBackground(util.colorCursorSale(btn_SeleccionEmpleado.getBackground()));
        }else
        JOptionPane.showMessageDialog(this, "SELECCIONE UN EMPLEADO", "Selección de Empleado", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_lbl_btnSeleccionEmpleadoMouseClicked

    private void lbl_btnSeleccionEmpleadoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnSeleccionEmpleadoMouseEntered
        btn_SeleccionEmpleado.setBackground(util.colorCursorEntra(btn_SeleccionEmpleado.getBackground()));
    }//GEN-LAST:event_lbl_btnSeleccionEmpleadoMouseEntered

    private void lbl_btnSeleccionEmpleadoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnSeleccionEmpleadoMouseExited
        btn_SeleccionEmpleado.setBackground(util.colorCursorSale(btn_SeleccionEmpleado.getBackground()));
    }//GEN-LAST:event_lbl_btnSeleccionEmpleadoMouseExited

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

    private void lbl_btn_Pagina2_1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_Pagina2_1MouseClicked
        setCartas(panelPagina1);
        btn_Pagina2_1.setBackground(util.colorCursorSale(btn_Pagina2_1.getBackground()));
    }//GEN-LAST:event_lbl_btn_Pagina2_1MouseClicked

    private void lbl_btn_Pagina2_1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_Pagina2_1MouseEntered
        btn_Pagina2_1.setBackground(util.colorCursorEntra(btn_Pagina2_1.getBackground()));
    }//GEN-LAST:event_lbl_btn_Pagina2_1MouseEntered

    private void lbl_btn_Pagina2_1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_Pagina2_1MouseExited
        btn_Pagina2_1.setBackground(util.colorCursorSale(btn_Pagina2_1.getBackground()));
    }//GEN-LAST:event_lbl_btn_Pagina2_1MouseExited

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

    private void txtTemperaturaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTemperaturaFocusGained
        if (String.valueOf(txtTemperatura.getText()).equals("°C")){
            txtTemperatura.setText("");
            txtTemperatura.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtTemperaturaFocusGained

    private void txtTemperaturaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTemperaturaFocusLost
        if (!(util.verificarFlotante(txtTemperatura.getText())) && (txtTemperatura.getText().length() > 0))
        txtTemperatura.requestFocus();
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
        if (!(util.verificarFlotante(txtGlicemia.getText())) && (txtGlicemia.getText().length() > 0))
        txtGlicemia.requestFocus();
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
        if (!(util.verificarFlotante(txtPA.getText())) && (txtPA.getText().length() > 0))
        txtPA.requestFocus();
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
        if (!(util.verificarFlotante(txtPulso.getText())) && (txtPulso.getText().length() > 0))
        txtPulso.requestFocus();
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
        if (!(util.verificarFlotante(txtSPO2.getText())) && (txtSPO2.getText().length() > 0))
        txtSPO2.requestFocus();
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
        if (!(util.verificarFlotante(txtFR.getText())) && (txtFR.getText().length() > 0))
        txtFR.requestFocus();
        if (txtFR.getText().isEmpty()){
            txtFR.setText("RPM");
            txtFR.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtFRFocusLost

    private void txtIMCFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIMCFocusGained
        if (String.valueOf(txtIMC.getText()).equals("Kg/m^2")){
            txtIMC.setText("");
            txtIMC.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtIMCFocusGained

    private void txtIMCFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIMCFocusLost
        if (!(util.verificarFlotante(txtIMC.getText())) && (txtIMC.getText().length() > 0))
        txtIMC.requestFocus();
        if (txtIMC.getText().isEmpty()){
            txtIMC.setText("Kg/m^2");
            txtIMC.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtIMCFocusLost

    private void txtPesoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesoFocusGained
        if (String.valueOf(txtPeso.getText()).equals("lb")){
            txtPeso.setText("");
            txtPeso.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtPesoFocusGained

    private void txtPesoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesoFocusLost
        if (!(util.verificarFlotante(txtPeso.getText())) && (txtPeso.getText().length() > 0))
        txtPeso.requestFocus();
        if (txtPeso.getText().isEmpty()){
            txtPeso.setText("lb");
            txtPeso.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtPesoFocusLost

    private void txtTallaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTallaFocusGained
        if (String.valueOf(txtTalla.getText()).equals("m")){
            txtTalla.setText("");
            txtTalla.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtTallaFocusGained

    private void txtTallaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTallaFocusLost
        if (!(util.verificarFlotante(txtTalla.getText())) && (txtTalla.getText().length() > 0))
        txtTalla.requestFocus();
        if (txtTalla.getText().isEmpty()){
            txtTalla.setText("m");
            txtTalla.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtTallaFocusLost

    private void rbtn_Aptitud2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rbtn_Aptitud2StateChanged
        if(rbtn_Aptitud2.isSelected())
        txtAreaRestricciones.setEnabled(true);
        else{
            txtAreaRestricciones.setEnabled(false);
            txtAreaRestricciones.setText("");
        }
    }//GEN-LAST:event_rbtn_Aptitud2StateChanged

    private void txtMenarquiaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMenarquiaFocusGained
        if (String.valueOf(txtMenarquia.getText()).equals("años")){
            txtMenarquia.setText("");
            txtMenarquia.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtMenarquiaFocusGained

    private void txtMenarquiaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMenarquiaFocusLost
        if (!(util.verificarEntero(txtMenarquia.getText())) && (txtMenarquia.getText().length() > 0))
        txtMenarquia.requestFocus();
        if (txtMenarquia.getText().isEmpty()){
            txtMenarquia.setText("años");
            txtMenarquia.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtMenarquiaFocusLost

    private void txtGFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGFocusGained
        if (String.valueOf(txtG.getText()).equals("Gestaciones")){
            txtG.setText("");
            txtG.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtGFocusGained

    private void txtGFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGFocusLost
        if (!(util.verificarEntero(txtG.getText())) && (txtG.getText().length() > 0))
        txtG.requestFocus();
        if (txtG.getText().isEmpty()){
            txtG.setText("Gestaciones");
            txtG.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtGFocusLost

    private void txtPFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPFocusGained
        if (String.valueOf(txtP.getText()).equals("Partos")){
            txtP.setText("");
            txtP.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtPFocusGained

    private void txtPFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPFocusLost
        if (!(util.verificarEntero(txtP.getText())) && (txtP.getText().length() > 0))
        txtP.requestFocus();
        if (txtP.getText().isEmpty()){
            txtP.setText("Partos");
            txtP.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtPFocusLost

    private void txtCSTPFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCSTPFocusGained
        if (String.valueOf(txtCSTP.getText()).equals("Cesáreas")){
            txtCSTP.setText("");
            txtCSTP.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtCSTPFocusGained

    private void txtCSTPFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCSTPFocusLost
        if (!(util.verificarEntero(txtCSTP.getText())) && (txtCSTP.getText().length() > 0))
        txtCSTP.requestFocus();
        if (txtCSTP.getText().isEmpty()){
            txtCSTP.setText("Cesáreas");
            txtCSTP.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtCSTPFocusLost

    private void txtHVFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHVFocusGained
        if (String.valueOf(txtHV.getText()).equals("Hijos vivos")){
            txtHV.setText("");
            txtHV.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtHVFocusGained

    private void txtHVFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHVFocusLost
        if (!(util.verificarEntero(txtHV.getText())) && (txtHV.getText().length() > 0))
        txtHV.requestFocus();
        if (txtHV.getText().isEmpty()){
            txtHV.setText("Hijos vivos");
            txtHV.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtHVFocusLost

    private void txtHMFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHMFocusGained
        if (String.valueOf(txtHM.getText()).equals("Hijos muertos")){
            txtHM.setText("");
            txtHM.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtHMFocusGained

    private void txtHMFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHMFocusLost
        if (!(util.verificarEntero(txtHM.getText())) && (txtHM.getText().length() > 0))
        txtHM.requestFocus();
        if (txtHM.getText().isEmpty()){
            txtHM.setText("Hijos muertos");
            txtHM.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtHMFocusLost

    private void txtABFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtABFocusGained
        if (String.valueOf(txtAB.getText()).equals("Abortos")){
            txtAB.setText("");
            txtAB.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtABFocusGained

    private void txtABFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtABFocusLost
        if (!(util.verificarEntero(txtAB.getText())) && (txtAB.getText().length() > 0))
        txtAB.requestFocus();
        if (txtAB.getText().isEmpty()){
            txtAB.setText("Abortos");
            txtAB.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtABFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel btn_Actualizar;
    public javax.swing.JPanel btn_Confirmar;
    public javax.swing.JPanel btn_Crear;
    public javax.swing.JPanel btn_Eliminar;
    public javax.swing.JPanel btn_Ingresar;
    private javax.swing.JPanel btn_OtroEmpleado;
    private javax.swing.JPanel btn_Pagina1_2;
    private javax.swing.JPanel btn_Pagina2_1;
    private javax.swing.JPanel btn_Pagina2_3;
    private javax.swing.JPanel btn_Pagina3_2;
    private javax.swing.JPanel btn_Pagina3_4;
    private javax.swing.JPanel btn_Pagina4_3;
    private javax.swing.JPanel btn_SeleccionEmpleado;
    private javax.swing.JCheckBox checkLentes;
    private javax.swing.JPanel cont_EvaluacionPeriodica;
    private com.raven.datechooser.DateChooser fechaCSTP;
    private com.raven.datechooser.DateChooser fechaFUR;
    private com.raven.datechooser.DateChooser fechaMenarquia;
    private javax.swing.ButtonGroup grbtn_Aptitud;
    public static javax.swing.ButtonGroup grbtn_Sexo;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
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
    private javax.swing.JTable jtEmpleado;
    public javax.swing.JTable jtEvaluacionPeriodica;
    public javax.swing.JLabel lblAB;
    public javax.swing.JLabel lblAG;
    public javax.swing.JLabel lblAlteraciones;
    public javax.swing.JLabel lblAntecedentesGi;
    private javax.swing.JLabel lblAptitud;
    public javax.swing.JLabel lblArea;
    public javax.swing.JLabel lblCSTP;
    private javax.swing.JLabel lblCabeza;
    private javax.swing.JLabel lblCardiovascular;
    public javax.swing.JLabel lblClinica;
    public javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblCuello;
    public javax.swing.JLabel lblDiagnostico;
    public javax.swing.JLabel lblEdad;
    private javax.swing.JLabel lblExtremidades;
    public javax.swing.JLabel lblFR;
    public javax.swing.JLabel lblFechaMod;
    private javax.swing.JLabel lblFraccion1;
    private javax.swing.JLabel lblFraccion2;
    public javax.swing.JLabel lblFur;
    public javax.swing.JLabel lblG;
    private javax.swing.JLabel lblGastrointestinal;
    private javax.swing.JLabel lblGenitourinario;
    public javax.swing.JLabel lblGlicemia;
    public javax.swing.JLabel lblHM;
    public javax.swing.JLabel lblHV;
    public javax.swing.JLabel lblHora;
    public javax.swing.JLabel lblIMC;
    private javax.swing.JLabel lblImpresionClinica;
    private javax.swing.JLabel lblInstruccionComboboxEmpleado;
    public javax.swing.JLabel lblMPF;
    public javax.swing.JLabel lblMenarquia;
    private javax.swing.JLabel lblNeurologico;
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
    public javax.swing.JLabel lblPuesto1;
    public javax.swing.JLabel lblPulso;
    private javax.swing.JLabel lblRespiratorio;
    private javax.swing.JLabel lblResponsable;
    private javax.swing.JLabel lblRestricciones;
    private javax.swing.JLabel lblRuffier;
    public javax.swing.JLabel lblSPO2;
    public javax.swing.JLabel lblSeguridad;
    public javax.swing.JLabel lblSexo;
    public javax.swing.JLabel lblTalla;
    public javax.swing.JLabel lblTemperatura;
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
    private javax.swing.JLabel lbl_RevisionPorSistemas;
    private javax.swing.JLabel lbl_SeleccionEvaluacionPeriodica;
    private javax.swing.JLabel lbl_TituloSeleccion;
    private javax.swing.JLabel lbl_Tratamientos;
    private javax.swing.JLabel lbl_Traumaticos;
    private javax.swing.JLabel lbl_Vicios;
    public javax.swing.JLabel lbl_btnActualizar;
    public javax.swing.JLabel lbl_btnCrear;
    public javax.swing.JLabel lbl_btnIngresar;
    private javax.swing.JLabel lbl_btnOtroEmpleado;
    private javax.swing.JLabel lbl_btnSeleccionEmpleado;
    public javax.swing.JLabel lbl_btn_Confirmar;
    private javax.swing.JLabel lbl_btn_Pagina1_2;
    private javax.swing.JLabel lbl_btn_Pagina2_1;
    private javax.swing.JLabel lbl_btn_Pagina2_3;
    private javax.swing.JLabel lbl_btn_Pagina3_2;
    private javax.swing.JLabel lbl_btn_Pagina3_4;
    private javax.swing.JLabel lbl_btn_Pagina4_3;
    public javax.swing.JPanel panelAG;
    private javax.swing.JPanel panelAntecedentesGino;
    public javax.swing.JPanel panelBotonesCRUD;
    private javax.swing.JPanel panelCartas;
    private javax.swing.JPanel panelCartasContenido;
    private javax.swing.JPanel panelCartasEmpleado;
    public javax.swing.JPanel panelCombobox;
    private javax.swing.JPanel panelComboboxEmpleado;
    public javax.swing.JPanel panelEdicion;
    private javax.swing.JPanel panelEmpleado;
    public javax.swing.JPanel panelFecha;
    private javax.swing.JPanel panelFormulario;
    public javax.swing.JPanel panelInicio;
    public javax.swing.JPanel panelPagina1;
    private javax.swing.JPanel panelPagina2;
    private javax.swing.JPanel panelPagina3;
    private javax.swing.JPanel panelPagina4;
    private javax.swing.JPanel panelPaginacionEmpleado;
    private javax.swing.JPanel panelPaginacionEvaluacionPeriodica;
    public javax.swing.JPanel panelSeguridad;
    public javax.swing.JPanel panelTitulos;
    private javax.swing.JPanel panel_Diagnostico;
    private javax.swing.JRadioButton rbtn_Aptitud1;
    private javax.swing.JRadioButton rbtn_Aptitud2;
    private javax.swing.JRadioButton rbtn_Aptitud3;
    private javax.swing.JRadioButton rbtn_Aptitud4;
    public javax.swing.JRadioButton rbtn_SexoF;
    public javax.swing.JRadioButton rbtn_SexoM;
    public javax.swing.JPanel tablaTitulos;
    private com.raven.swing.TimePicker timeHora;
    public javax.swing.JTextField txtAB;
    public javax.swing.JTextField txtArea;
    private javax.swing.JTextArea txtAreaAbdomen;
    private javax.swing.JTextArea txtAreaAlergicos;
    private javax.swing.JTextArea txtAreaAlteraciones;
    private javax.swing.JTextArea txtAreaCabeza;
    private javax.swing.JTextArea txtAreaCardiopulmonar;
    private javax.swing.JTextArea txtAreaCuello;
    private javax.swing.JTextArea txtAreaExtremidades;
    private javax.swing.JTextArea txtAreaFamiliares;
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
    private javax.swing.JTextArea txtAreaRestricciones;
    private javax.swing.JTextArea txtAreaTorax;
    private javax.swing.JTextArea txtAreaTratamientos;
    private javax.swing.JTextArea txtAreaTraumaticos;
    private javax.swing.JTextArea txtAreaVicios;
    public javax.swing.JTextField txtCSTP;
    public javax.swing.JTextField txtCodigo;
    public javax.swing.JTextArea txtDiagnostico;
    public javax.swing.JTextField txtEdad;
    public javax.swing.JTextField txtFR;
    public javax.swing.JTextField txtFUR;
    public javax.swing.JTextField txtFecha;
    public javax.swing.JTextField txtG;
    public javax.swing.JTextField txtGlicemia;
    public javax.swing.JTextField txtHM;
    public javax.swing.JTextField txtHV;
    private javax.swing.JTextField txtHora;
    public javax.swing.JTextField txtIMC;
    public javax.swing.JTextField txtMPF;
    public javax.swing.JTextField txtMenarquia;
    public javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtOjoDerechoDenominador;
    private javax.swing.JTextField txtOjoDerechoNumerador;
    private javax.swing.JTextField txtOjoIzquierdoDenominador;
    private javax.swing.JTextField txtOjoIzquierdoNumerador;
    public javax.swing.JTextField txtP;
    private javax.swing.JTextField txtPA;
    public javax.swing.JTextField txtPeso;
    public javax.swing.JLabel txtPreempleoId;
    public javax.swing.JTextField txtPuesto;
    public javax.swing.JTextField txtPulso;
    private javax.swing.JTextField txtRuffier;
    public javax.swing.JTextField txtSPO2;
    private javax.swing.JTextField txtSeleccionEmpleado;
    private javax.swing.JTextField txtTalla;
    public javax.swing.JTextField txtTemperatura;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        Object evt = e.getSource();
        if (evt.equals(filasPermitidasFicha))
            paginadorEvaluacionPeriodica.eventComboBox(filasPermitidasFicha);
        else if (evt.equals(filasPermitidasEmpleado))
            paginadorEmpleado.eventComboBox(filasPermitidasEmpleado);
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        Object evt = e.getSource();
        if (evt.equals(filasPermitidasFicha))
            paginadorEvaluacionPeriodica.actualizarBotonesPaginacion();
        else if (evt.equals(filasPermitidasEmpleado))
            paginadorEmpleado.actualizarBotonesPaginacion();
    }
}
