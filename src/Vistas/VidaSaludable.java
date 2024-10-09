package Vistas;
import javax.swing.JPanel;
import Utilitarios.Utilitarios;
import Utilitarios.DatosPaginacion;
import Utilitarios.ModeloTabla;
import Modelos.EmpleadoMod;
import Modelos.VidaSaludableMod;
import Modelos.RevisionSistemasMod;
import Modelos.UsuarioMod;
import Controladores.PaginadorTabla;
import Controladores.EmpleadoCtrl;
import Controladores.VidaSaludableCtrl;
import Controladores.RevisionSistemasCtrl;
import Controladores.UsuarioCtrl;
import Controladores.GenerarReportePDF;
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
public class VidaSaludable extends javax.swing.JFrame implements ActionListener, TableModelListener{

    String contenidoActual, clinicaId = "1";
    public String nombreContenido = "VidaSaludable", responsable;
    private JComboBox<Integer> filasPermitidasFicha, filasPermitidasEmpleado;
    private Utilitarios util = new Utilitarios();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime now = LocalDateTime.now();
    
    //Modelos
    private VidaSaludableMod vidaSaludable = new VidaSaludableMod();
    private RevisionSistemasMod revisionSistemas = new RevisionSistemasMod();
    private EmpleadoMod empleado = new EmpleadoMod();
    private UsuarioMod usuario = new UsuarioMod();
    
    //Controladores
    private VidaSaludableCtrl vidSalCtrl = new VidaSaludableCtrl();
    private RevisionSistemasCtrl revSisCtrl = new RevisionSistemasCtrl();
    private EmpleadoCtrl empCtrl = new EmpleadoCtrl();
    private UsuarioCtrl usrCtrl = new UsuarioCtrl();
    private GenerarReportePDF genPDF = new GenerarReportePDF();
    
    private PaginadorTabla <ConsultaGeneral> paginadorVidaSaludable, paginadorEmpleado;
    public VidaSaludable() {
        initComponents();
        contenidoActual = "Inicio";
        setCartaContenido(panelInicio);
        setCartas(panelPagina1);
        setCartaEmpleado(panelComboboxEmpleado);
        btn_Confirmar.setVisible(false);
        btnReportes.setVisible(false);
        lbl_btn_Confirmar.setEnabled(false);
        txtHora.setEnabled(false);
        timeHora.set24hourMode(true);
        jtVidaSaludable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent Mouse_evt){
                JTable tabla = (JTable) Mouse_evt.getSource();
                Point punto = Mouse_evt.getPoint();
                int fila = tabla.rowAtPoint(punto);
                if (Mouse_evt.getClickCount() == 1){
                    lbl_SeleccionVidaSaludable.setText((jtVidaSaludable.getValueAt(jtVidaSaludable.getSelectedRow(), 3).toString()) + " " + (jtVidaSaludable.getValueAt(jtVidaSaludable.getSelectedRow(), 1)));
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
        resetVidaSaludable();
        resetEmpleado();
        resetRevisionSistemas();
        resetUsuarios();
        construirEtiquetas();
        setTabla();
        lbl_SeleccionVidaSaludable.setText("");
        panelPaginacionVidaSaludable.removeAll();
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
    
    private void resetVidaSaludable(){
        timeHora.now();
        setSexo("");
        txtEdad.setText("");
        txtArea.setText("");
        txtPuesto.setText("");
        txtPesoInicial.setText("lb");
        txtPesoInicial.setForeground(Color.gray);
        txtPesoRecomendado.setText("lb");
        txtPesoRecomendado.setForeground(Color.gray);
        txtBrazos.setText("cm");
        txtBrazos.setForeground(Color.gray);
        txtCintura.setText("cm");
        txtCintura.setForeground(Color.gray);
        txtCadera.setText("cm");
        txtCadera.setForeground(Color.gray);
        txtAbdomen.setText("cm");
        txtAbdomen.setForeground(Color.gray);
        txtIndiceCinturaCadera.setText("cm");
        txtIndiceCinturaCadera.setForeground(Color.gray);
        txtInterpretacionRiesgo.setText("");
        txtAreaDiagnostico.setText("");
        
        vidaSaludable.setId("");
        vidaSaludable.setFecha("");
        vidaSaludable.setHora("");
        vidaSaludable.setEdad("");
        vidaSaludable.setArea("");
        vidaSaludable.setPuesto("");
        vidaSaludable.setPesoInicial("");
        vidaSaludable.setPesoRecomendado("");
        vidaSaludable.setActividad("");
        vidaSaludable.setTipoEjercicio("");
        vidaSaludable.setFrecuenciaEjercicio("");
        vidaSaludable.setDuracionEjercicio("");
        vidaSaludable.setMedidaBrazo("");
        vidaSaludable.setMedidaCintura("");
        vidaSaludable.setMedidaCadera("");
        vidaSaludable.setMedidaAbdomen("");
        vidaSaludable.setIndiceCinturaCadera("");
        vidaSaludable.setInterpretacionRiesgo("");
        vidaSaludable.setDiagnosticoNutricional("");
        vidaSaludable.setPlan("");
        vidaSaludable.setEmpleadoId("");
        vidaSaludable.setClinicaId("");
        vidaSaludable.setRevisionSistemasId("");
        vidaSaludable.setResponsable("");
        vidaSaludable.setEstado("");
    }
    
    private void resetRevisionSistemas(){
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
        txtRuffier.setText("");
        txtOjoDerechoNumerador.setText("");
        txtOjoIzquierdoNumerador.setText("");
        txtOjoDerechoDenominador.setText("");
        txtOjoIzquierdoDenominador.setText("");
        checkLentes.setSelected(false);
        
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
            listaUsuarios = usrCtrl.seleccionarTodosUsuarios();
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
            codigo = vidSalCtrl.getMaxId();
            lblTitulos.setText("<html><center>Formato<br><b>FICHA MÉDICA SEGUIMIENTO VIDA SALUDABLE</b><br>CORRELATIVO: " + codigo + "</center></html>");
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
    
    private boolean verificarEmpleado(){
        boolean valido = false;
        if ((txtNombre.getText().length()>0) && (txtCodigo.getText().length()>0) && (verificarSexo()))
            valido = true;
        else valido = false;
        return valido;
    }
    
    private boolean verificarVidaSaludable(){
        boolean valido = false;
        if ((util.verificarFlotante(txtEdad.getText())) && (txtArea.getText().length()>0) && (txtPuesto.getText().length()>0) && (txtHora.getText().length() > 0))
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
     * La vidaSaludable en pantalla se guardarán en una variable global "vidaSaludable"
     */
    private void getVidaSaludable(){
        vidaSaludable.setFecha(util.convertirFechaSQL(txtFecha.getText()));
        vidaSaludable.setHora(txtHora.getText());
        vidaSaludable.setEdad(txtEdad.getText());
        vidaSaludable.setArea(txtArea.getText());
        vidaSaludable.setPuesto(txtPuesto.getText());
        if(!(txtPesoInicial.getText().equals("lb")) && (txtPesoInicial.getText().length() > 0) && (util.verificarFlotante(txtPesoInicial.getText())))
            vidaSaludable.setPesoInicial(txtPesoInicial.getText());
        else
            vidaSaludable.setPesoInicial("");
        if(!(txtPesoRecomendado.getText().equals("lb")) && (txtPesoRecomendado.getText().length() > 0) && (util.verificarFlotante(txtPesoRecomendado.getText())))
            vidaSaludable.setPesoRecomendado(txtPesoRecomendado.getText());
        else
            vidaSaludable.setPesoRecomendado("");
        //Actividad
        if(checkMuyLigera.isSelected())
            vidaSaludable.setActividad("1");
        else if (checkLigera.isSelected())
            vidaSaludable.setActividad("2");
        else if (checkModerada.isSelected())
            vidaSaludable.setActividad("3");
        else if (checkPesada.isSelected())
            vidaSaludable.setActividad("4");
        else if (checkExcepcional.isSelected())
            vidaSaludable.setActividad("5");
        else
            vidaSaludable.setActividad("");
        vidaSaludable.setTipoEjercicio(txtTipoEjercicio.getText());
        vidaSaludable.setFrecuenciaEjercicio(txtFrecuenciaEjercicio.getText());
        vidaSaludable.setDuracionEjercicio(txtDuracionEjercicio.getText());
        //Medidas
        if(!(txtBrazos.getText().equals("cm") && (txtBrazos.getText().length() > 0)) && (util.verificarFlotante(txtBrazos.getText())))
            vidaSaludable.setMedidaBrazo(txtBrazos.getText());
        else
            vidaSaludable.setMedidaBrazo("");
        if(!(txtCintura.getText().equals("cm") && (txtCintura.getText().length() > 0)) && (util.verificarFlotante(txtCintura.getText())))
            vidaSaludable.setMedidaCintura(txtCintura.getText());
        else
            vidaSaludable.setMedidaCintura("");
        if(!(txtCadera.getText().equals("cm") && (txtCadera.getText().length() > 0)) && (util.verificarFlotante(txtCadera.getText())))
            vidaSaludable.setMedidaCadera(txtCadera.getText());
        else
            vidaSaludable.setMedidaCadera("");
        if(!(txtAbdomen.getText().equals("cm") && (txtAbdomen.getText().length() > 0)) && (util.verificarFlotante(txtAbdomen.getText())))
            vidaSaludable.setMedidaAbdomen(txtAbdomen.getText());
        else
            vidaSaludable.setMedidaAbdomen("");
        if(!(txtIndiceCinturaCadera.getText().equals("cm") && (txtIndiceCinturaCadera.getText().length() > 0)) && (util.verificarFlotante(txtIndiceCinturaCadera.getText())))
            vidaSaludable.setIndiceCinturaCadera(txtIndiceCinturaCadera.getText());
        else
            vidaSaludable.setIndiceCinturaCadera("");
        if(!(txtInterpretacionRiesgo.getText().length() > 0))
            vidaSaludable.setInterpretacionRiesgo(txtInterpretacionRiesgo.getText());
        else
            vidaSaludable.setInterpretacionRiesgo("");
        vidaSaludable.setDiagnosticoNutricional(txtAreaDiagnostico.getText());
        vidaSaludable.setPlan(txtAreaPlan.getText());
        
        vidaSaludable.setClinicaId(clinicaId);
        vidaSaludable.setEmpleadoId(empleado.getId());
        vidaSaludable.setRevisionSistemasId(revisionSistemas.getId());
        vidaSaludable.setResponsable(responsable);
        vidaSaludable.setEstado("1");
    }
    
    /**
     * Los valores dentro de los vidaSaludable guardados en la variable global "vidaSaludable" se mostrarán en pantalla
     */
    private void setVidaSaludable(){
        txtFecha.setText(util.convertirFechaGUI(vidaSaludable.getFecha()));
        txtHora.setText(vidaSaludable.getHora());
        txtEdad.setText(vidaSaludable.getEdad());
        txtArea.setText(vidaSaludable.getArea());
        txtPuesto.setText(vidaSaludable.getPuesto());
        txtPesoInicial.setText(vidaSaludable.getPesoInicial());
        txtPesoInicial.setForeground(Color.black);
        txtPesoRecomendado.setText(vidaSaludable.getPesoRecomendado());
        txtPesoRecomendado.setForeground(Color.black);
        //Actividad
        switch(vidaSaludable.getActividad()){
            case "1":
                grbtn_ActividadFisica.setSelected(checkMuyLigera.getModel(), true);
                break;
            case "2":
                grbtn_ActividadFisica.setSelected(checkLigera.getModel(), true);
                break;
            case "3":
                grbtn_ActividadFisica.setSelected(checkModerada.getModel(), true);
                break;
            case "4":
                grbtn_ActividadFisica.setSelected(checkPesada.getModel(), true);
                break;
            case "5":
                grbtn_ActividadFisica.setSelected(checkExcepcional.getModel(), true);
                break;
            default:
                break;
        }
        txtTipoEjercicio.setText(vidaSaludable.getTipoEjercicio());
        txtFrecuenciaEjercicio.setText(vidaSaludable.getFrecuenciaEjercicio());
        txtDuracionEjercicio.setText(vidaSaludable.getDuracionEjercicio());
        txtBrazos.setText(vidaSaludable.getMedidaBrazo());
        txtBrazos.setForeground(Color.black);
        txtCintura.setText(vidaSaludable.getMedidaCintura());
        txtCintura.setForeground(Color.black);
        txtCadera.setText(vidaSaludable.getMedidaCadera());
        txtCadera.setForeground(Color.black);
        txtAbdomen.setText(vidaSaludable.getMedidaAbdomen());
        txtAbdomen.setForeground(Color.black);
        txtIndiceCinturaCadera.setText(vidaSaludable.getIndiceCinturaCadera());
        txtIndiceCinturaCadera.setForeground(Color.black);
        txtInterpretacionRiesgo.setText(vidaSaludable.getInterpretacionRiesgo());
        txtInterpretacionRiesgo.setForeground(Color.black);
        txtAreaDiagnostico.setText(vidaSaludable.getDiagnosticoNutricional());
        txtAreaPlan.setText(vidaSaludable.getPlan());
        
        responsable = vidaSaludable.getResponsable();
    }
    
    /**
     * Los datos del examen físico (revisionSistemas) en pantalla se guardarán en una variable global "revisionSistemas"
     */
    private void getRevisionSistemas(){
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
            revisionSistemas.setFr("");
        if (!(txtGlicemia.getText().equals("mg/dl")))
            revisionSistemas.setGlicemia(txtGlicemia.getText());
        else
            revisionSistemas.setFr("");
        if (!(txtPeso.getText().equals("lb")))
            revisionSistemas.setPeso(txtPeso.getText());
        else
            revisionSistemas.setFr("");
        if (!(txtIMC.getText().equals("Kg/m^2")))
            revisionSistemas.setImc(txtIMC.getText());
        else
            revisionSistemas.setImc("");
        revisionSistemas.setTalla(comboTalla.getSelectedItem().toString());
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
        revisionSistemas.setObservaciones(txtAreaObservaciones.getText());
        revisionSistemas.setEstado("1");
    }
    
    /**
     * Los valores dentro de la variable revisionSistemas guardados en la variable global "revisionSistemas" se mostrarán en pantalla
     */
    private void setRevisionSistemas(){
        if(revisionSistemas.getTemperatura().length() > 0){
            txtTemperatura.setForeground(Color.black);
            txtTemperatura.setText(revisionSistemas.getTemperatura());
        }else{
            txtTemperatura.setForeground(Color.gray);
            txtTemperatura.setText("°C");
        }
        if (revisionSistemas.getPulso().length() > 0){
            txtPulso.setForeground(Color.black);
            txtPulso.setText(revisionSistemas.getPulso());
        }else{
            txtPulso.setForeground(Color.gray);
            txtPulso.setText("LPM");
        }
        if (revisionSistemas.getSpo2().length() > 0){
            txtSPO2.setForeground(Color.black);
            txtSPO2.setText(revisionSistemas.getSpo2());
        }else{
            txtSPO2.setForeground(Color.gray);
            txtSPO2.setText("%");
        }
        if (revisionSistemas.getFr().length() > 0){
            txtFR.setForeground(Color.black);
            txtFR.setText(revisionSistemas.getFr());
        }else{
            txtFR.setForeground(Color.gray);
            txtFR.setText("RPM");
        }
        if (revisionSistemas.getPa().length() > 0){
            txtPA.setForeground(Color.black);
            txtPA.setText(revisionSistemas.getPa());
        }else{
            txtPA.setForeground(Color.gray);
            txtPA.setText("mmHg");
        }
        if (revisionSistemas.getGlicemia().length() > 0){
            txtGlicemia.setForeground(Color.black);
            txtGlicemia.setText(revisionSistemas.getGlicemia());
        }else{
            txtGlicemia.setForeground(Color.gray);
            txtGlicemia.setText("mg/dl");
        }
        if (revisionSistemas.getPeso().length() > 0){
            txtPeso.setForeground(Color.black);
            txtPeso.setText(revisionSistemas.getPeso());
        }else{
            txtPeso.setForeground(Color.gray);
            txtPeso.setText("lb");
        }
        if (revisionSistemas.getImc().length() > 0){
            txtIMC.setForeground(Color.black);
            txtIMC.setText(revisionSistemas.getImc());
        }else
        {
            txtIMC.setForeground(Color.gray);
            txtIMC.setText("Kg/m^2");
        }
        switch(revisionSistemas.getTalla()){
            case "S":
                comboTalla.setSelectedIndex(0);
                break;
            case "M":
                comboTalla.setSelectedIndex(1);
                break;
            case "L":
                comboTalla.setSelectedIndex(2);
                break;
            case "XL":
                comboTalla.setSelectedIndex(3);
                break;
            default:
                comboTalla.setSelectedIndex(0);
        }
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
            return cont_VidaSaludable;
        }catch (Exception ex) {
            Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    //Todo lo relacionado a la Tabla
    private TableModel crearModeloTablaFicha() {
        return new ModeloTabla<VidaSaludableMod>() {
            @Override
            public Object getValueAt(VidaSaludableMod t, int columna) {
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
        
        jtVidaSaludable.setModel(crearModeloTablaFicha());
        DatosPaginacion<VidaSaludableMod> datosPaginacionFicha;
        try {
            datosPaginacionFicha = crearDatosPaginacionVidaSaludable();
            paginadorVidaSaludable = new PaginadorTabla(jtVidaSaludable, datosPaginacionFicha, new int[]{5,10,20,25,50,75,100}, 10);
            paginadorVidaSaludable.crearListadoFilasPermitidas(panelPaginacionVidaSaludable);
            filasPermitidasFicha = paginadorVidaSaludable.getComboboxFilasPermitidas();
            filasPermitidasFicha.addActionListener(this);
            jtVidaSaludable.getModel().addTableModelListener(this);
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
    
    private DatosPaginacion <VidaSaludableMod> crearDatosPaginacionVidaSaludable() throws SQLException, ConnectException{
        List <VidaSaludableMod> lista = vidSalCtrl.seleccionarTodos();
        //Reemplazar el id del empleado por el nombre del empleado
        EmpleadoMod emp = new EmpleadoMod();
        VidaSaludableMod ficha = new VidaSaludableMod();
        for (int i = 0; i < lista.size(); i++){
            ficha = lista.get(i);
            emp = empCtrl.buscarFila(ficha.getEmpleadoId());
            ficha.setEmpleadoId(emp.getNombre());
            lista.set(i, ficha);
        }
        return new DatosPaginacion<VidaSaludableMod>(){
            @Override
            public int getTotalRowCount() {
                return lista.size();
            }

            @Override
            public List<VidaSaludableMod> getRows(int startIndex, int endIndex) {
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
    
    private void buscarEmpleadoVidaSaludable(String valorBuscar) throws SQLException, ConnectException{
        vidaSaludable = vidSalCtrl.buscarFila(valorBuscar);
        lblTitulos.setText("<html><center>Formato<br><b>FICHA MÉDICA SEGUIMIENTO VIDA SALUDABLE</b><br>CORRELATIVO: " + vidaSaludable.getId() + "</center></html>");
        empleado = empCtrl.buscarFila(vidaSaludable.getEmpleadoId());
        revisionSistemas = revSisCtrl.buscarFila(vidaSaludable.getRevisionSistemasId());
        setVidaSaludable();
        setRevisionSistemas();
        setEmpleado();
    }
    
    private void crear() throws SQLException{
        try {
            int segCro = 0, revSis = 0;
            revSis = revSisCtrl.Crear(revisionSistemas);
            if (revSis > 0){
                revisionSistemas.setId(revSisCtrl.getMaxId());
                vidaSaludable.setRevisionSistemasId(revisionSistemas.getId());
                segCro = vidSalCtrl.Crear(vidaSaludable);
                if (segCro > 0) {
                    JOptionPane.showMessageDialog(this, "REGISTRO INGRESADO CON EXITO", "Inserción de Datos", JOptionPane.INFORMATION_MESSAGE);
                    reset();
                } else {
                    JOptionPane.showMessageDialog(this, "ERROR AL INSERTAR LA FICHA DE VIDA SALUDABLE. COMUNIQUESE CON TI", "Inserción de Datos", JOptionPane.ERROR_MESSAGE);
                }
            }
            else
                JOptionPane.showMessageDialog(this, "ERROR AL INSERTAR EL EXAMEN FÍSICO. COMUNIQUESE CON TI", "Inserción de Datos", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR " + e.toString() + " AL INSERTAR EL REGISTRO. COMUNIQUESE CON TI", "Inserción de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void actualizar() throws SQLException{
        try {
            int segCro = 0, revSis = 0, ant = 0;
            revSis = revSisCtrl.Actualizar(revisionSistemas);
            if (revSis > 0){
                segCro = vidSalCtrl.Actualizar(vidaSaludable);
                if (segCro > 0) {
                    JOptionPane.showMessageDialog(this, "REGISTRO ACTUALIZADO CON EXITO", "Inserción de Datos", JOptionPane.INFORMATION_MESSAGE);
                    reset();
                    setTabla();
                    setCartaContenido(panelCombobox);
                } else {
                    JOptionPane.showMessageDialog(this, "ERROR AL ACTUALIZAR EVALUACIÓN. COMUNIQUESE CON TI", "Actualización de Datos", JOptionPane.ERROR_MESSAGE);
                }
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
            conGen = vidSalCtrl.Eliminar(vidaSaludable);
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
    
    private void getDatosReporte(){
        try{
            genPDF.generarDatosEmpleado(empleado.getId());
            genPDF.generarDatos(vidaSaludable.getNombreTabla(), vidaSaludable.getId(), vidaSaludable.getLlavePrimaria(), vidaSaludable.getPrefijo());
            genPDF.generarDatos(revisionSistemas.getNombreTabla(), revisionSistemas.getId(), revisionSistemas.getLlavePrimaria(), revisionSistemas.getPrefijo());
            genPDF.setDocumentoId(vidaSaludable.getId());
            genPDF.setTipoReporte("Vida Saludable");
            genPDF.setUsuario(usuario.getNombre());
            genPDF.setFecha(vidaSaludable.getFecha());
            //System.out.println(genPDF.getDatos());
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, "ERROR " + e.toString() + " AL CREAR EL REPORTE. COMUNIQUESE CON TI", "Reportes", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void crearReporte(){
        try{
            getDatosReporte();
            genPDF.generarReporte("Vida Saludable - " + empleado.getNombre() + " - " + vidaSaludable.getFecha());
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, "ERROR " + e.toString() + " AL CREAR EL REPORTE. COMUNIQUESE CON TI", "Reportes", JOptionPane.ERROR_MESSAGE);
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

        timeHora = new com.raven.swing.TimePicker();
        grbtn_Sexo = new javax.swing.ButtonGroup();
        grbtn_ActividadFisica = new javax.swing.ButtonGroup();
        cont_VidaSaludable = new javax.swing.JPanel();
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
        btnReportes = new javax.swing.JPanel();
        lblReportes = new javax.swing.JLabel();
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
        panelAuxiliar = new javax.swing.JPanel();
        lblPlan = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaPlan = new javax.swing.JTextArea();
        panelCartas = new javax.swing.JPanel();
        panelPagina1 = new javax.swing.JPanel();
        lbl_RevisionPorSistemas = new javax.swing.JLabel();
        btn_Pagina1_2 = new javax.swing.JPanel();
        lbl_btn_Pagina1_2 = new javax.swing.JLabel();
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
        comboTalla = new javax.swing.JComboBox<>();
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
        panelPagina2 = new javax.swing.JPanel();
        btn_Pagina2_1 = new javax.swing.JPanel();
        lbl_btn_Pagina2_1 = new javax.swing.JLabel();
        btn_Pagina2_3 = new javax.swing.JPanel();
        lbl_btn_Pagina2_3 = new javax.swing.JLabel();
        lblPesoInicial = new javax.swing.JLabel();
        txtPesoInicial = new javax.swing.JTextField();
        lblPesoRecomendado = new javax.swing.JLabel();
        txtPesoRecomendado = new javax.swing.JTextField();
        lblActividadFisica = new javax.swing.JLabel();
        panelActividadFisica = new javax.swing.JPanel();
        checkMuyLigera = new javax.swing.JCheckBox();
        checkLigera = new javax.swing.JCheckBox();
        checkModerada = new javax.swing.JCheckBox();
        checkPesada = new javax.swing.JCheckBox();
        checkExcepcional = new javax.swing.JCheckBox();
        lblEjercicio = new javax.swing.JLabel();
        panelEjercicio = new javax.swing.JPanel();
        panelEjercicios1 = new javax.swing.JPanel();
        lblTipo = new javax.swing.JLabel();
        lblFrecuencia = new javax.swing.JLabel();
        lblDuracion = new javax.swing.JLabel();
        txtTipoEjercicio = new javax.swing.JTextField();
        txtFrecuenciaEjercicio = new javax.swing.JTextField();
        txtDuracionEjercicio = new javax.swing.JTextField();
        lblMedidas = new javax.swing.JLabel();
        panelMedidas = new javax.swing.JPanel();
        panelMedidas1 = new javax.swing.JPanel();
        lblBrazos = new javax.swing.JLabel();
        lblCintura = new javax.swing.JLabel();
        lblCadera = new javax.swing.JLabel();
        txtBrazos = new javax.swing.JTextField();
        txtCintura = new javax.swing.JTextField();
        txtCadera = new javax.swing.JTextField();
        panelMedidas2 = new javax.swing.JPanel();
        lblAbdominal = new javax.swing.JLabel();
        lblCinturaCadera = new javax.swing.JLabel();
        lblInterpretacionRiesgo = new javax.swing.JLabel();
        txtAbdomen = new javax.swing.JTextField();
        txtIndiceCinturaCadera = new javax.swing.JTextField();
        txtInterpretacionRiesgo = new javax.swing.JTextField();
        panelPagina3 = new javax.swing.JPanel();
        btn_Pagina3_2 = new javax.swing.JPanel();
        lbl_btn_Pagina3_2 = new javax.swing.JLabel();
        lblObservaciones = new javax.swing.JLabel();
        jScrollPane26 = new javax.swing.JScrollPane();
        txtAreaObservaciones = new javax.swing.JTextArea();
        lblResponsable = new javax.swing.JLabel();
        lblDiagnostico = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtAreaDiagnostico = new javax.swing.JTextArea();
        panelCombobox = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblTituloCombobox = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtVidaSaludable = new javax.swing.JTable();
        btn_Ingresar = new javax.swing.JPanel();
        lbl_btnIngresar = new javax.swing.JLabel();
        panelPaginacionVidaSaludable = new javax.swing.JPanel();
        lbl_SeleccionVidaSaludable = new javax.swing.JLabel();
        lbl_TituloSeleccion = new javax.swing.JLabel();

        timeHora.set24hourMode(true);
        timeHora.setDisplayText(txtHora);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cont_VidaSaludable.setBackground(new java.awt.Color(255, 255, 255));
        cont_VidaSaludable.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTituloPrincipal.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        lblTituloPrincipal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTituloPrincipal.setText("Ficha de Seguimiento Vida Saludable");
        cont_VidaSaludable.add(lblTituloPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, -1));

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

        cont_VidaSaludable.add(tablaTitulos, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 30, 600, 90));

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

        cont_VidaSaludable.add(panelBotonesCRUD, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 0, 270, 120));

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

        cont_VidaSaludable.add(btn_Confirmar, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 540, 90, 35));

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

        btnReportes.setBackground(new java.awt.Color(235, 235, 51));
        btnReportes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnReportes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblReportes.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblReportes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblReportes.setText("Generar Reporte");
        lblReportes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblReportesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblReportesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblReportesMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnReportesLayout = new javax.swing.GroupLayout(btnReportes);
        btnReportes.setLayout(btnReportesLayout);
        btnReportesLayout.setHorizontalGroup(
            btnReportesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblReportes, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
        );
        btnReportesLayout.setVerticalGroup(
            btnReportesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblReportes, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
        );

        panelFormulario.add(btnReportes, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 375, 130, 35));

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

        panelAuxiliar.setBackground(new java.awt.Color(255, 255, 255));
        panelAuxiliar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblPlan.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblPlan.setText("Plan");
        panelAuxiliar.add(lblPlan, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaPlan.setColumns(20);
        txtAreaPlan.setRows(5);
        jScrollPane1.setViewportView(txtAreaPlan);

        panelAuxiliar.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 470, 150));

        panelFormulario.add(panelAuxiliar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 490, 255));

        panelCartas.setBackground(new java.awt.Color(255, 255, 255));
        panelCartas.setLayout(new java.awt.CardLayout());

        panelPagina1.setBackground(new java.awt.Color(255, 255, 255));
        panelPagina1.setMinimumSize(new java.awt.Dimension(566, 365));
        panelPagina1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_RevisionPorSistemas.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_RevisionPorSistemas.setForeground(new java.awt.Color(31, 78, 121));
        lbl_RevisionPorSistemas.setText("EXAMEN FÍSICO");
        panelPagina1.add(lbl_RevisionPorSistemas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

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

        panelPagina1.add(btn_Pagina1_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(293, 370, 106, 35));

        lblTemperatura.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblTemperatura.setText("Temperatura");
        panelPagina1.add(lblTemperatura, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, 20));

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
        panelPagina1.add(txtTemperatura, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 175, 35));

        lblGlicemia.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblGlicemia.setText("Glicemia");
        panelPagina1.add(lblGlicemia, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 30, -1, 20));

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
        panelPagina1.add(txtGlicemia, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 50, 175, 35));

        lblPA.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblPA.setText("P/A");
        panelPagina1.add(lblPA, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 30, -1, -1));

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
        panelPagina1.add(txtPA, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, 175, 35));

        lblPulso.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblPulso.setText("Pulso");
        panelPagina1.add(lblPulso, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, 20));

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
        panelPagina1.add(txtPulso, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 175, 35));

        lblSPO2.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblSPO2.setText("sPO2");
        panelPagina1.add(lblSPO2, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 100, -1, 20));

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
        panelPagina1.add(txtSPO2, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 120, 175, 35));

        lblFR.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblFR.setText("FR");
        panelPagina1.add(lblFR, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 100, -1, 20));

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
        panelPagina1.add(txtFR, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 120, 175, 35));

        lblIMC.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblIMC.setText("IMC");
        panelPagina1.add(lblIMC, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, 20));

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
        panelPagina1.add(txtIMC, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 175, 35));

        lblPeso.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblPeso.setText("Peso");
        panelPagina1.add(lblPeso, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 170, -1, 20));

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
        panelPagina1.add(txtPeso, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 190, 175, 35));

        lblTalla.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblTalla.setText("Talla");
        panelPagina1.add(lblTalla, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 170, -1, 20));

        comboTalla.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        comboTalla.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "S", "M", "L", "XL" }));
        panelPagina1.add(comboTalla, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 190, 175, 35));

        lblRuffier.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblRuffier.setText("Ruffier");
        panelPagina1.add(lblRuffier, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, 20));

        txtRuffier.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtRuffier.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelPagina1.add(txtRuffier, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 143, 35));

        lblOjoDerecho.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblOjoDerecho.setText("Agudeza ojo derecho");
        panelPagina1.add(lblOjoDerecho, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 240, -1, 20));

        txtOjoDerechoNumerador.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtOjoDerechoNumerador.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelPagina1.add(txtOjoDerechoNumerador, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 260, 40, 35));

        lblFraccion1.setFont(new java.awt.Font("Roboto", 0, 36)); // NOI18N
        lblFraccion1.setText("/");
        panelPagina1.add(lblFraccion1, new org.netbeans.lib.awtextra.AbsoluteConstraints(203, 260, -1, 35));

        txtOjoDerechoDenominador.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtOjoDerechoDenominador.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelPagina1.add(txtOjoDerechoDenominador, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 260, 40, 35));

        lblOjoIzquierdo.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblOjoIzquierdo.setText("Agudeza ojo izquierdo");
        panelPagina1.add(lblOjoIzquierdo, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 240, -1, 20));

        txtOjoIzquierdoNumerador.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtOjoIzquierdoNumerador.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelPagina1.add(txtOjoIzquierdoNumerador, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 260, 40, 35));

        lblFraccion2.setFont(new java.awt.Font("Roboto", 0, 36)); // NOI18N
        lblFraccion2.setText("/");
        panelPagina1.add(lblFraccion2, new org.netbeans.lib.awtextra.AbsoluteConstraints(353, 260, -1, 35));

        txtOjoIzquierdoDenominador.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtOjoIzquierdoDenominador.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelPagina1.add(txtOjoIzquierdoDenominador, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 260, 40, 35));

        checkLentes.setBackground(new java.awt.Color(255, 255, 255));
        checkLentes.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        checkLentes.setText("Usa Lentes");
        panelPagina1.add(checkLentes, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 260, -1, 35));

        panelCartas.add(panelPagina1, "card1");

        panelPagina2.setBackground(new java.awt.Color(255, 255, 255));
        panelPagina2.setMinimumSize(new java.awt.Dimension(566, 365));
        panelPagina2.setPreferredSize(new java.awt.Dimension(566, 405));
        panelPagina2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        panelPagina2.add(btn_Pagina2_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(167, 370, 106, 35));

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

        panelPagina2.add(btn_Pagina2_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(293, 370, 106, 35));

        lblPesoInicial.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblPesoInicial.setText("Peso Inicial");
        panelPagina2.add(lblPesoInicial, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        txtPesoInicial.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtPesoInicial.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtPesoInicial.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPesoInicialFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPesoInicialFocusLost(evt);
            }
        });
        panelPagina2.add(txtPesoInicial, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 175, 35));

        lblPesoRecomendado.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblPesoRecomendado.setText("Peso mínimo Recomendado");
        panelPagina2.add(lblPesoRecomendado, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, -1, 20));

        txtPesoRecomendado.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtPesoRecomendado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtPesoRecomendado.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPesoRecomendadoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPesoRecomendadoFocusLost(evt);
            }
        });
        panelPagina2.add(txtPesoRecomendado, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 30, 175, 35));

        lblActividadFisica.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblActividadFisica.setForeground(new java.awt.Color(31, 78, 121));
        lblActividadFisica.setText("ACTIVIDAD FÍSICA");
        panelPagina2.add(lblActividadFisica, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, 20));

        panelActividadFisica.setBackground(new java.awt.Color(255, 255, 255));
        panelActividadFisica.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        checkMuyLigera.setBackground(new java.awt.Color(255, 255, 255));
        grbtn_ActividadFisica.add(checkMuyLigera);
        checkMuyLigera.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        checkMuyLigera.setText("Muy Ligera");
        checkMuyLigera.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelActividadFisica.add(checkMuyLigera);

        checkLigera.setBackground(new java.awt.Color(255, 255, 255));
        grbtn_ActividadFisica.add(checkLigera);
        checkLigera.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        checkLigera.setText("Ligera");
        checkLigera.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelActividadFisica.add(checkLigera);

        checkModerada.setBackground(new java.awt.Color(255, 255, 255));
        grbtn_ActividadFisica.add(checkModerada);
        checkModerada.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        checkModerada.setText("Moderada");
        checkModerada.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelActividadFisica.add(checkModerada);

        checkPesada.setBackground(new java.awt.Color(255, 255, 255));
        grbtn_ActividadFisica.add(checkPesada);
        checkPesada.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        checkPesada.setText("Pesada");
        checkPesada.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelActividadFisica.add(checkPesada);

        checkExcepcional.setBackground(new java.awt.Color(255, 255, 255));
        grbtn_ActividadFisica.add(checkExcepcional);
        checkExcepcional.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        checkExcepcional.setText("Excepcional");
        checkExcepcional.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelActividadFisica.add(checkExcepcional);

        panelPagina2.add(panelActividadFisica, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 430, -1));

        lblEjercicio.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblEjercicio.setForeground(new java.awt.Color(31, 78, 121));
        lblEjercicio.setText("EJERCICIO");
        panelPagina2.add(lblEjercicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, 20));

        panelEjercicio.setBackground(new java.awt.Color(255, 255, 255));
        panelEjercicio.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 0));

        panelEjercicios1.setBackground(new java.awt.Color(255, 255, 255));
        panelEjercicios1.setPreferredSize(new java.awt.Dimension(535, 20));
        panelEjercicios1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        lblTipo.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblTipo.setText("Tipo");
        lblTipo.setPreferredSize(new java.awt.Dimension(180, 20));
        panelEjercicios1.add(lblTipo);

        lblFrecuencia.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblFrecuencia.setText("Frecuencia");
        lblFrecuencia.setPreferredSize(new java.awt.Dimension(180, 20));
        panelEjercicios1.add(lblFrecuencia);

        lblDuracion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblDuracion.setText("Duración");
        lblDuracion.setPreferredSize(new java.awt.Dimension(175, 20));
        panelEjercicios1.add(lblDuracion);

        panelEjercicio.add(panelEjercicios1);

        txtTipoEjercicio.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtTipoEjercicio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtTipoEjercicio.setPreferredSize(new java.awt.Dimension(175, 35));
        panelEjercicio.add(txtTipoEjercicio);

        txtFrecuenciaEjercicio.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtFrecuenciaEjercicio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtFrecuenciaEjercicio.setPreferredSize(new java.awt.Dimension(175, 35));
        panelEjercicio.add(txtFrecuenciaEjercicio);

        txtDuracionEjercicio.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtDuracionEjercicio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtDuracionEjercicio.setPreferredSize(new java.awt.Dimension(175, 35));
        panelEjercicio.add(txtDuracionEjercicio);

        panelPagina2.add(panelEjercicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 546, 60));

        lblMedidas.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblMedidas.setForeground(new java.awt.Color(31, 78, 121));
        lblMedidas.setText("MEDIDAS");
        panelPagina2.add(lblMedidas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, -1, -1));

        panelMedidas.setBackground(new java.awt.Color(255, 255, 255));
        panelMedidas.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 0));

        panelMedidas1.setBackground(new java.awt.Color(255, 255, 255));
        panelMedidas1.setMinimumSize(new java.awt.Dimension(546, 20));
        panelMedidas1.setPreferredSize(new java.awt.Dimension(535, 25));
        panelMedidas1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING, 0, 5));

        lblBrazos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblBrazos.setText("Circunferencia Brazos");
        lblBrazos.setPreferredSize(new java.awt.Dimension(180, 20));
        panelMedidas1.add(lblBrazos);

        lblCintura.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblCintura.setText("Circunferencia Cintura");
        lblCintura.setPreferredSize(new java.awt.Dimension(180, 20));
        panelMedidas1.add(lblCintura);

        lblCadera.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblCadera.setText("Circunferencia Cadera");
        lblCadera.setPreferredSize(new java.awt.Dimension(175, 20));
        panelMedidas1.add(lblCadera);

        panelMedidas.add(panelMedidas1);

        txtBrazos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtBrazos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtBrazos.setPreferredSize(new java.awt.Dimension(175, 35));
        txtBrazos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBrazosFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBrazosFocusLost(evt);
            }
        });
        panelMedidas.add(txtBrazos);

        txtCintura.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtCintura.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCintura.setPreferredSize(new java.awt.Dimension(175, 35));
        txtCintura.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCinturaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCinturaFocusLost(evt);
            }
        });
        txtCintura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCinturaKeyReleased(evt);
            }
        });
        panelMedidas.add(txtCintura);

        txtCadera.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtCadera.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCadera.setPreferredSize(new java.awt.Dimension(175, 35));
        txtCadera.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCaderaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCaderaFocusLost(evt);
            }
        });
        txtCadera.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCaderaKeyReleased(evt);
            }
        });
        panelMedidas.add(txtCadera);

        panelMedidas2.setBackground(new java.awt.Color(255, 255, 255));
        panelMedidas2.setMinimumSize(new java.awt.Dimension(546, 20));
        panelMedidas2.setPreferredSize(new java.awt.Dimension(535, 25));
        panelMedidas2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING, 0, 5));

        lblAbdominal.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblAbdominal.setText("Circunferencia Abdomen");
        lblAbdominal.setMaximumSize(new java.awt.Dimension(45, 20));
        lblAbdominal.setMinimumSize(new java.awt.Dimension(45, 20));
        lblAbdominal.setPreferredSize(new java.awt.Dimension(180, 20));
        panelMedidas2.add(lblAbdominal);

        lblCinturaCadera.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblCinturaCadera.setText("Índice Cintura/Cadera");
        lblCinturaCadera.setMaximumSize(new java.awt.Dimension(45, 20));
        lblCinturaCadera.setMinimumSize(new java.awt.Dimension(45, 20));
        lblCinturaCadera.setPreferredSize(new java.awt.Dimension(180, 20));
        panelMedidas2.add(lblCinturaCadera);

        lblInterpretacionRiesgo.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblInterpretacionRiesgo.setText("Interpretación riesgo Índice");
        lblInterpretacionRiesgo.setMaximumSize(new java.awt.Dimension(45, 20));
        lblInterpretacionRiesgo.setMinimumSize(new java.awt.Dimension(45, 20));
        lblInterpretacionRiesgo.setPreferredSize(new java.awt.Dimension(175, 20));
        panelMedidas2.add(lblInterpretacionRiesgo);

        panelMedidas.add(panelMedidas2);

        txtAbdomen.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtAbdomen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtAbdomen.setPreferredSize(new java.awt.Dimension(175, 35));
        txtAbdomen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtAbdomenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAbdomenFocusLost(evt);
            }
        });
        panelMedidas.add(txtAbdomen);

        txtIndiceCinturaCadera.setEditable(false);
        txtIndiceCinturaCadera.setBackground(new java.awt.Color(255, 255, 255));
        txtIndiceCinturaCadera.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtIndiceCinturaCadera.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtIndiceCinturaCadera.setPreferredSize(new java.awt.Dimension(175, 35));
        panelMedidas.add(txtIndiceCinturaCadera);

        txtInterpretacionRiesgo.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtInterpretacionRiesgo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtInterpretacionRiesgo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtInterpretacionRiesgo.setPreferredSize(new java.awt.Dimension(175, 35));
        panelMedidas.add(txtInterpretacionRiesgo);

        panelPagina2.add(panelMedidas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 546, 130));

        panelCartas.add(panelPagina2, "card2");

        panelPagina3.setBackground(new java.awt.Color(255, 255, 255));
        panelPagina3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        panelPagina3.add(btn_Pagina3_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(167, 370, 106, 35));

        lblObservaciones.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblObservaciones.setText("Observaciones");
        panelPagina3.add(lblObservaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        jScrollPane26.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaObservaciones.setColumns(20);
        txtAreaObservaciones.setRows(5);
        jScrollPane26.setViewportView(txtAreaObservaciones);

        panelPagina3.add(jScrollPane26, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 546, 150));

        lblResponsable.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblResponsable.setText("Responsable:");
        panelPagina3.add(lblResponsable, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 370, -1, 20));

        lblDiagnostico.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblDiagnostico.setText("Diagnóstico de Estado Nutricional");
        panelPagina3.add(lblDiagnostico, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, 20));

        jScrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaDiagnostico.setColumns(20);
        txtAreaDiagnostico.setRows(5);
        jScrollPane4.setViewportView(txtAreaDiagnostico);

        panelPagina3.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 546, 150));

        panelCartas.add(panelPagina3, "card3");

        panelFormulario.add(panelCartas, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 10, 566, 405));

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

        jtVidaSaludable.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jtVidaSaludable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jtVidaSaludable);

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

        panelPaginacionVidaSaludable.setBackground(new java.awt.Color(255, 255, 255));
        panelCombobox.add(panelPaginacionVidaSaludable, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 980, 30));

        lbl_SeleccionVidaSaludable.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lbl_SeleccionVidaSaludable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lbl_SeleccionVidaSaludable.setPreferredSize(new java.awt.Dimension(300, 40));
        panelCombobox.add(lbl_SeleccionVidaSaludable, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        lbl_TituloSeleccion.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lbl_TituloSeleccion.setText("Registro Seleccionado");
        panelCombobox.add(lbl_TituloSeleccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        panelCartasContenido.add(panelCombobox, "card4");

        cont_VidaSaludable.add(panelCartasContenido, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 1080, 480));

        getContentPane().add(cont_VidaSaludable, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lbl_btnCrearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnCrearMouseClicked
        if (!contenidoActual.equals("Formulario")){
            setCartaContenido(panelFormulario);
            btn_Confirmar.setBackground(new Color(40,235,40));
            btn_Confirmar.setVisible(true);
            btnReportes.setVisible(false);
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
            btnReportes.setVisible(false);
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
            btnReportes.setVisible(false);
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

    private void lbl_btn_ConfirmarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_ConfirmarMouseClicked
        if (lbl_btn_Confirmar.isEnabled()){
            if (verificarRevisionSistemas()){
                if (verificarEmpleado()){
                    if (verificarVidaSaludable()){
                        try {
                            getRevisionSistemas();
                            getVidaSaludable();
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
                JOptionPane.showMessageDialog(this, "Información del empleado incompleta o no válida", "Inserción de Datos", JOptionPane.INFORMATION_MESSAGE);
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

    private void lbl_btnIngresarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btnIngresarMouseClicked
        if (lbl_SeleccionVidaSaludable.getText().length() > 0){
            if (contenidoActual.equals("Actualizar")){
                setCartaContenido(panelFormulario);
                btn_Confirmar.setBackground(new Color(92,92,235));

                btn_Confirmar.setVisible(true);
                btnReportes.setVisible(true);
            }else if (contenidoActual.equals("Eliminar")){
                setCartaContenido(panelFormulario);
                btn_Confirmar.setBackground(new Color(235,91,91));

                btn_Confirmar.setVisible(true);
                btnReportes.setVisible(false);
            }
            try {
                buscarEmpleadoVidaSaludable(jtVidaSaludable.getValueAt(jtVidaSaludable.getSelectedRow(), 0).toString());
            } catch (SQLException ex) {
                Logger.getLogger(VidaSaludable.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ConnectException ex) {
                Logger.getLogger(VidaSaludable.class.getName()).log(Level.SEVERE, null, ex);
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

    private void lbl_btn_Pagina2_3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_Pagina2_3MouseClicked
        setCartas(panelPagina3);
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

    private void txtPesoInicialFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesoInicialFocusGained
        if (String.valueOf(txtPesoInicial.getText()).equals("lb")){
            txtPesoInicial.setText("");
            txtPesoInicial.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtPesoInicialFocusGained

    private void txtPesoInicialFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesoInicialFocusLost
        if (!(util.verificarFlotante(txtPesoInicial.getText())) && (txtPesoInicial.getText().length() > 0))
            txtPesoInicial.requestFocus();
        if (txtPesoInicial.getText().isEmpty()){
            txtPesoInicial.setText("lb");
            txtPesoInicial.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtPesoInicialFocusLost

    private void txtPesoRecomendadoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesoRecomendadoFocusGained
        if (String.valueOf(txtPesoRecomendado.getText()).equals("lb")){
            txtPesoRecomendado.setText("");
            txtPesoRecomendado.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtPesoRecomendadoFocusGained

    private void txtPesoRecomendadoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesoRecomendadoFocusLost
        if (!(util.verificarFlotante(txtPesoRecomendado.getText())) && (txtPesoRecomendado.getText().length() > 0))
            txtPesoRecomendado.requestFocus();
        if (txtPesoRecomendado.getText().isEmpty()){
            txtPesoRecomendado.setText("lb");
            txtPesoRecomendado.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtPesoRecomendadoFocusLost

    private void txtBrazosFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBrazosFocusGained
        if (String.valueOf(txtBrazos.getText()).equals("cm")){
            txtBrazos.setText("");
            txtBrazos.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtBrazosFocusGained

    private void txtBrazosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBrazosFocusLost
        if (!(util.verificarFlotante(txtBrazos.getText())) && (txtBrazos.getText().length() > 0))
            txtBrazos.requestFocus();
        if (txtBrazos.getText().isEmpty()){
            txtBrazos.setText("cm");
            txtBrazos.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtBrazosFocusLost

    private void txtCinturaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCinturaFocusGained
        if (String.valueOf(txtCintura.getText()).equals("cm")){
            txtCintura.setText("");
            txtCintura.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtCinturaFocusGained

    private void txtCinturaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCinturaFocusLost
        if (!(util.verificarFlotante(txtCintura.getText())) && (txtCintura.getText().length() > 0))
            txtCintura.requestFocus();
        if (txtCintura.getText().isEmpty()){
            txtCintura.setText("cm");
            txtCintura.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtCinturaFocusLost

    private void txtCaderaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCaderaFocusGained
        if (String.valueOf(txtCadera.getText()).equals("cm")){
            txtCadera.setText("");
            txtCadera.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtCaderaFocusGained

    private void txtCaderaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCaderaFocusLost
        if (!(util.verificarFlotante(txtCadera.getText())) && (txtCadera.getText().length() > 0))
            txtCadera.requestFocus();
        if (txtCadera.getText().isEmpty()){
            txtCadera.setText("cm");
            txtCadera.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtCaderaFocusLost

    private void txtAbdomenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAbdomenFocusGained
        if (String.valueOf(txtAbdomen.getText()).equals("cm")){
            txtAbdomen.setText("");
            txtAbdomen.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtAbdomenFocusGained

    private void txtAbdomenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAbdomenFocusLost
        if (!(util.verificarFlotante(txtAbdomen.getText())) && (txtAbdomen.getText().length() > 0))
            txtAbdomen.requestFocus();
        if (txtAbdomen.getText().isEmpty()){
            txtAbdomen.setText("cm");
            txtAbdomen.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtAbdomenFocusLost

    private void txtCinturaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCinturaKeyReleased
        if(util.verificarFlotante(txtCintura.getText()) && (util.verificarFlotante(txtCadera.getText())) && (Double.valueOf(txtCadera.getText()) > 0)){
            double indiceCinturaCadera;
            indiceCinturaCadera = Double.valueOf(txtCintura.getText())/Double.valueOf(txtCadera.getText());
            txtIndiceCinturaCadera.setText(String.format("%.2f", indiceCinturaCadera));
        } else if (txtCadera.getText().length() == 0 || ((util.verificarFlotante(txtCadera.getText())) && (Double.valueOf(txtCadera.getText()) == 0.0)))
            txtIndiceCinturaCadera.setText("cm");
    }//GEN-LAST:event_txtCinturaKeyReleased

    private void txtCaderaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCaderaKeyReleased
        if(util.verificarFlotante(txtCintura.getText()) && (util.verificarFlotante(txtCadera.getText())) && (Double.valueOf(txtCadera.getText()) > 0)){
            double indiceCinturaCadera;
            indiceCinturaCadera = Double.valueOf(txtCintura.getText())/Double.valueOf(txtCadera.getText());
            txtIndiceCinturaCadera.setText(String.format("%.2f", indiceCinturaCadera));
        } else if (txtCadera.getText().length() == 0 || ((util.verificarFlotante(txtCadera.getText())) && (Double.valueOf(txtCadera.getText()) == 0.0)))
            txtIndiceCinturaCadera.setText("cm");
    }//GEN-LAST:event_txtCaderaKeyReleased

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

    private void lblReportesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblReportesMouseClicked
        crearReporte();
    }//GEN-LAST:event_lblReportesMouseClicked

    private void lblReportesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblReportesMouseEntered
        btnReportes.setBackground(util.colorCursorEntra(btnReportes.getBackground()));
    }//GEN-LAST:event_lblReportesMouseEntered

    private void lblReportesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblReportesMouseExited
        btnReportes.setBackground(util.colorCursorSale(btnReportes.getBackground()));
    }//GEN-LAST:event_lblReportesMouseExited

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnReportes;
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
    private javax.swing.JPanel btn_SeleccionEmpleado;
    private javax.swing.JCheckBox checkExcepcional;
    private javax.swing.JCheckBox checkLentes;
    private javax.swing.JCheckBox checkLigera;
    private javax.swing.JCheckBox checkModerada;
    private javax.swing.JCheckBox checkMuyLigera;
    private javax.swing.JCheckBox checkPesada;
    private javax.swing.JComboBox<String> comboTalla;
    private javax.swing.JPanel cont_VidaSaludable;
    private javax.swing.ButtonGroup grbtn_ActividadFisica;
    public static javax.swing.ButtonGroup grbtn_Sexo;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane26;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jtEmpleado;
    public javax.swing.JTable jtVidaSaludable;
    public javax.swing.JLabel lblAG;
    private javax.swing.JLabel lblAbdominal;
    private javax.swing.JLabel lblActividadFisica;
    public javax.swing.JLabel lblArea;
    private javax.swing.JLabel lblBrazos;
    private javax.swing.JLabel lblCadera;
    private javax.swing.JLabel lblCintura;
    private javax.swing.JLabel lblCinturaCadera;
    public javax.swing.JLabel lblClinica;
    public javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblDiagnostico;
    private javax.swing.JLabel lblDuracion;
    public javax.swing.JLabel lblEdad;
    private javax.swing.JLabel lblEjercicio;
    public javax.swing.JLabel lblFR;
    public javax.swing.JLabel lblFechaMod;
    private javax.swing.JLabel lblFraccion1;
    private javax.swing.JLabel lblFraccion2;
    private javax.swing.JLabel lblFrecuencia;
    public javax.swing.JLabel lblGlicemia;
    public javax.swing.JLabel lblHora;
    public javax.swing.JLabel lblIMC;
    private javax.swing.JLabel lblInstruccionComboboxEmpleado;
    private javax.swing.JLabel lblInterpretacionRiesgo;
    private javax.swing.JLabel lblMedidas;
    public javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblObservaciones;
    private javax.swing.JLabel lblOjoDerecho;
    private javax.swing.JLabel lblOjoIzquierdo;
    private javax.swing.JLabel lblPA;
    public javax.swing.JLabel lblPeso;
    private javax.swing.JLabel lblPesoInicial;
    private javax.swing.JLabel lblPesoRecomendado;
    private javax.swing.JLabel lblPlan;
    public javax.swing.JLabel lblPuesto1;
    public javax.swing.JLabel lblPulso;
    private javax.swing.JLabel lblReportes;
    private javax.swing.JLabel lblResponsable;
    private javax.swing.JLabel lblRuffier;
    public javax.swing.JLabel lblSPO2;
    public javax.swing.JLabel lblSeguridad;
    public javax.swing.JLabel lblSexo;
    public javax.swing.JLabel lblTalla;
    public javax.swing.JLabel lblTemperatura;
    private javax.swing.JLabel lblTipo;
    public javax.swing.JLabel lblTituloCombobox;
    public javax.swing.JLabel lblTituloPrincipal;
    public javax.swing.JLabel lblTitulos;
    public javax.swing.JLabel lbl_BtnEliminar;
    public javax.swing.JLabel lbl_InicioInicio;
    private javax.swing.JLabel lbl_RevisionPorSistemas;
    private javax.swing.JLabel lbl_SeleccionVidaSaludable;
    private javax.swing.JLabel lbl_TituloSeleccion;
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
    public javax.swing.JPanel panelAG;
    private javax.swing.JPanel panelActividadFisica;
    private javax.swing.JPanel panelAuxiliar;
    public javax.swing.JPanel panelBotonesCRUD;
    private javax.swing.JPanel panelCartas;
    private javax.swing.JPanel panelCartasContenido;
    private javax.swing.JPanel panelCartasEmpleado;
    private javax.swing.JPanel panelCombobox;
    private javax.swing.JPanel panelComboboxEmpleado;
    public javax.swing.JPanel panelEdicion;
    private javax.swing.JPanel panelEjercicio;
    private javax.swing.JPanel panelEjercicios1;
    private javax.swing.JPanel panelEmpleado;
    public javax.swing.JPanel panelFecha;
    private javax.swing.JPanel panelFormulario;
    public javax.swing.JPanel panelInicio;
    private javax.swing.JPanel panelMedidas;
    private javax.swing.JPanel panelMedidas1;
    private javax.swing.JPanel panelMedidas2;
    private javax.swing.JPanel panelPagina1;
    private javax.swing.JPanel panelPagina2;
    private javax.swing.JPanel panelPagina3;
    private javax.swing.JPanel panelPaginacionEmpleado;
    private javax.swing.JPanel panelPaginacionVidaSaludable;
    public javax.swing.JPanel panelSeguridad;
    public javax.swing.JPanel panelTitulos;
    public javax.swing.JRadioButton rbtn_SexoF;
    public javax.swing.JRadioButton rbtn_SexoM;
    public javax.swing.JPanel tablaTitulos;
    private com.raven.swing.TimePicker timeHora;
    public javax.swing.JTextField txtAbdomen;
    public javax.swing.JTextField txtArea;
    private javax.swing.JTextArea txtAreaDiagnostico;
    private javax.swing.JTextArea txtAreaObservaciones;
    private javax.swing.JTextArea txtAreaPlan;
    public javax.swing.JTextField txtBrazos;
    public javax.swing.JTextField txtCadera;
    public javax.swing.JTextField txtCintura;
    public javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDuracionEjercicio;
    public javax.swing.JTextField txtEdad;
    public javax.swing.JTextField txtFR;
    public javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtFrecuenciaEjercicio;
    public javax.swing.JTextField txtGlicemia;
    private javax.swing.JTextField txtHora;
    public javax.swing.JTextField txtIMC;
    public javax.swing.JTextField txtIndiceCinturaCadera;
    public javax.swing.JTextField txtInterpretacionRiesgo;
    public javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtOjoDerechoDenominador;
    private javax.swing.JTextField txtOjoDerechoNumerador;
    private javax.swing.JTextField txtOjoIzquierdoDenominador;
    private javax.swing.JTextField txtOjoIzquierdoNumerador;
    private javax.swing.JTextField txtPA;
    public javax.swing.JTextField txtPeso;
    public javax.swing.JTextField txtPesoInicial;
    public javax.swing.JTextField txtPesoRecomendado;
    public javax.swing.JLabel txtPreempleoId;
    public javax.swing.JTextField txtPuesto;
    public javax.swing.JTextField txtPulso;
    private javax.swing.JTextField txtRuffier;
    public javax.swing.JTextField txtSPO2;
    private javax.swing.JTextField txtSeleccionEmpleado;
    public javax.swing.JTextField txtTemperatura;
    private javax.swing.JTextField txtTipoEjercicio;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        Object evt = e.getSource();
        if (evt.equals(filasPermitidasFicha))
            paginadorVidaSaludable.eventComboBox(filasPermitidasFicha);
        else if (evt.equals(filasPermitidasEmpleado))
            paginadorEmpleado.eventComboBox(filasPermitidasEmpleado);
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        Object evt = e.getSource();
        if (evt.equals(filasPermitidasFicha))
            paginadorVidaSaludable.actualizarBotonesPaginacion();
        else if (evt.equals(filasPermitidasEmpleado))
            paginadorEmpleado.actualizarBotonesPaginacion();
    }
}