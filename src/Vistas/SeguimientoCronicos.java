package Vistas;
import javax.swing.JPanel;
import Utilitarios.Utilitarios;
import Utilitarios.DatosPaginacion;
import Utilitarios.ModeloTabla;
import Modelos.EmpleadoMod;
import Modelos.SeguimientoCronicosMod;
import Modelos.RevisionSistemasMod;
import Modelos.UsuarioMod;
import Controladores.PaginadorTabla;
import Controladores.EmpleadoCtrl;
import Controladores.SeguimientoCronicosCtrl;
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
public class SeguimientoCronicos extends javax.swing.JFrame implements ActionListener, TableModelListener{

    String contenidoActual, clinicaId = "1";
    public String nombreContenido = "SeguimientoCronicos", responsable;
    private JComboBox<Integer> filasPermitidasFicha, filasPermitidasEmpleado;
    private Utilitarios util = new Utilitarios();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime now = LocalDateTime.now();
    
    //Modelos
    private SeguimientoCronicosMod seguimientoCronicos = new SeguimientoCronicosMod();
    private RevisionSistemasMod revisionSistemas = new RevisionSistemasMod();
    private EmpleadoMod empleado = new EmpleadoMod();
    private UsuarioMod usuario = new UsuarioMod();
    
    //Controladores
    private SeguimientoCronicosCtrl segCroCtrl = new SeguimientoCronicosCtrl();
    private RevisionSistemasCtrl revSisCtrl = new RevisionSistemasCtrl();
    private EmpleadoCtrl empCtrl = new EmpleadoCtrl();
    private UsuarioCtrl usrCtrl = new UsuarioCtrl();
    private GenerarReportePDF genPDF = new GenerarReportePDF();
    
    private PaginadorTabla <ConsultaGeneral> paginadorSeguimientoCronicos, paginadorEmpleado;
    public SeguimientoCronicos() {
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
        jtSeguimientoCronicos.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent Mouse_evt){
                JTable tabla = (JTable) Mouse_evt.getSource();
                Point punto = Mouse_evt.getPoint();
                int fila = tabla.rowAtPoint(punto);
                if (Mouse_evt.getClickCount() == 1){
                    lbl_SeleccionSeguimientoCronicos.setText((jtSeguimientoCronicos.getValueAt(jtSeguimientoCronicos.getSelectedRow(), 3).toString()) + " " + (jtSeguimientoCronicos.getValueAt(jtSeguimientoCronicos.getSelectedRow(), 1)));
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
        resetSeguimientoCronicos();
        resetEmpleado();
        resetRevisionSistemas();
        resetUsuarios();
        construirEtiquetas();
        setTabla();
        lbl_SeleccionSeguimientoCronicos.setText("");
        panelPaginacionSeguimientoCronicos.removeAll();
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
    
    private void resetSeguimientoCronicos(){
        timeHora.now();
        setSexo("");
        txtEdad.setText("");
        txtArea.setText("");
        txtPuesto.setText("");
        
        seguimientoCronicos.setId("");
        seguimientoCronicos.setFecha("");
        seguimientoCronicos.setHora("");
        seguimientoCronicos.setEdad("");
        seguimientoCronicos.setArea("");
        seguimientoCronicos.setPuesto("");
        seguimientoCronicos.setEmpleadoId("");
        seguimientoCronicos.setClinicaId("");
        seguimientoCronicos.setRevisionSistemasId("");
        seguimientoCronicos.setResponsable("");
        seguimientoCronicos.setEstado("");
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
            codigo = segCroCtrl.getMaxId();
            lblTitulos.setText("<html><center>Formato<br><b>FICHA MÉDICA SEGUIMIENTO PACIENTES CRÓNICOS</b><br>CORRELATIVO: " + codigo + "</center></html>");
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
    
    private boolean verificarseguimientoCronicos(){
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
     * La seguimientoCronicos en pantalla se guardarán en una variable global "seguimientoCronicos"
     */
    private void getSeguimientoCronicos(){
        seguimientoCronicos.setFecha(util.convertirFechaSQL(txtFecha.getText()));
        seguimientoCronicos.setHora(txtHora.getText());
        seguimientoCronicos.setEmpleadoId(empleado.getId());
        seguimientoCronicos.setEdad(txtEdad.getText());
        seguimientoCronicos.setArea(txtArea.getText());
        seguimientoCronicos.setPuesto(txtPuesto.getText());
        seguimientoCronicos.setDatosSubjetivos(txtAreaDatosSubjetivos.getText());
        seguimientoCronicos.setTratamiento(txtAreaTratamiento.getText());
        //Referencia, Traslado
        if(checkReferencia.isSelected())
            seguimientoCronicos.setReferencia("1");
        else
            seguimientoCronicos.setReferencia("0");
        if (checkTraslado.isSelected())
            seguimientoCronicos.setTraslado("1");
        else
            seguimientoCronicos.setTraslado("0");
        
        seguimientoCronicos.setClinicaId(clinicaId);
        seguimientoCronicos.setEmpleadoId(empleado.getId());
        seguimientoCronicos.setRevisionSistemasId(revisionSistemas.getId());
        seguimientoCronicos.setResponsable(responsable);
        seguimientoCronicos.setEstado("1");
    }
    
    /**
     * Los valores dentro de los seguimientoCronicos guardados en la variable global "seguimientoCronicos" se mostrarán en pantalla
     */
    private void setSeguimientoCronicos(){
        txtFecha.setText(util.convertirFechaGUI(seguimientoCronicos.getFecha()));
        txtHora.setText(seguimientoCronicos.getHora());
        txtEdad.setText(seguimientoCronicos.getEdad());
        txtArea.setText(seguimientoCronicos.getArea());
        txtPuesto.setText(seguimientoCronicos.getPuesto());
        txtAreaDatosSubjetivos.setText(seguimientoCronicos.getDatosSubjetivos());
        txtAreaTratamiento.setText(seguimientoCronicos.getTratamiento());
        //Set referencia, traslado
        if(seguimientoCronicos.getReferencia().equals("1"))
            checkReferencia.setSelected(true);
        else
            checkReferencia.setSelected(false);
        if(seguimientoCronicos.getTraslado().equals("1"))
            checkTraslado.setSelected(true);
        else
            checkTraslado.setSelected(false);
        
        responsable = seguimientoCronicos.getResponsable();
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
            return cont_SeguimientoCronicos;
        }catch (Exception ex) {
            Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    //Todo lo relacionado a la Tabla
    private TableModel crearModeloTablaFicha() {
        return new ModeloTabla<SeguimientoCronicosMod>() {
            @Override
            public Object getValueAt(SeguimientoCronicosMod t, int columna) {
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
        
        jtSeguimientoCronicos.setModel(crearModeloTablaFicha());
        DatosPaginacion<SeguimientoCronicosMod> datosPaginacionFicha;
        try {
            datosPaginacionFicha = crearDatosPaginacionSeguimientoCronicos();
            paginadorSeguimientoCronicos = new PaginadorTabla(jtSeguimientoCronicos, datosPaginacionFicha, new int[]{5,10,20,25,50,75,100}, 10);
            paginadorSeguimientoCronicos.crearListadoFilasPermitidas(panelPaginacionSeguimientoCronicos);
            filasPermitidasFicha = paginadorSeguimientoCronicos.getComboboxFilasPermitidas();
            filasPermitidasFicha.addActionListener(this);
            jtSeguimientoCronicos.getModel().addTableModelListener(this);
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
    
    private DatosPaginacion <SeguimientoCronicosMod> crearDatosPaginacionSeguimientoCronicos() throws SQLException, ConnectException{
        List <SeguimientoCronicosMod> lista = segCroCtrl.seleccionarTodos();
        //Reemplazar el id del empleado por el nombre del empleado
        EmpleadoMod emp = new EmpleadoMod();
        SeguimientoCronicosMod ficha = new SeguimientoCronicosMod();
        for (int i = 0; i < lista.size(); i++){
            ficha = lista.get(i);
            emp = empCtrl.buscarFila(ficha.getEmpleadoId());
            ficha.setEmpleadoId(emp.getNombre());
            lista.set(i, ficha);
        }
        return new DatosPaginacion<SeguimientoCronicosMod>(){
            @Override
            public int getTotalRowCount() {
                return lista.size();
            }

            @Override
            public List<SeguimientoCronicosMod> getRows(int startIndex, int endIndex) {
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
    
    private void buscarEmpleadoSeguimientoCronicos(String valorBuscar) throws SQLException, ConnectException{
        seguimientoCronicos = segCroCtrl.buscarFila(valorBuscar);
        lblTitulos.setText("<html><center>Formato<br><b>FICHA MÉDICA SEGUIMIENTO PACIENTES CRÓNICOS</b><br>CORRELATIVO: " + seguimientoCronicos.getId() + "</center></html>");
        empleado = empCtrl.buscarFila(seguimientoCronicos.getEmpleadoId());
        revisionSistemas = revSisCtrl.buscarFila(seguimientoCronicos.getRevisionSistemasId());
        setSeguimientoCronicos();
        setRevisionSistemas();
        setEmpleado();
    }
    
    private void crear() throws SQLException{
        try {
            int segCro = 0, revSis = 0;
            revSis = revSisCtrl.Crear(revisionSistemas);
            if (revSis > 0){
                revisionSistemas.setId(revSisCtrl.getMaxId());
                seguimientoCronicos.setRevisionSistemasId(revisionSistemas.getId());
                segCro = segCroCtrl.Crear(seguimientoCronicos);
                if (segCro > 0) {
                    JOptionPane.showMessageDialog(this, "REGISTRO INGRESADO CON EXITO", "Inserción de Datos", JOptionPane.INFORMATION_MESSAGE);
                    reset();
                } else {
                    JOptionPane.showMessageDialog(this, "ERROR AL INSERTAR LA EVALUACIÓN PERIÓDICA. COMUNIQUESE CON TI", "Inserción de Datos", JOptionPane.ERROR_MESSAGE);
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
                segCro = segCroCtrl.Actualizar(seguimientoCronicos);
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
            conGen = segCroCtrl.Eliminar(seguimientoCronicos);
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
            genPDF.generarDatos(seguimientoCronicos.getNombreTabla(), seguimientoCronicos.getId(), seguimientoCronicos.getLlavePrimaria(), seguimientoCronicos.getPrefijo());
            genPDF.generarDatos(revisionSistemas.getNombreTabla(), revisionSistemas.getId(), revisionSistemas.getLlavePrimaria(), revisionSistemas.getPrefijo());
            genPDF.setDocumentoId(seguimientoCronicos.getId());
            genPDF.setTipoReporte("Seguimientos Cronicos");
            genPDF.setUsuario(usuario.getNombre());
            genPDF.setFecha(seguimientoCronicos.getFecha());
            //System.out.println(genPDF.getDatos());
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, "ERROR " + e.toString() + " AL CREAR EL REPORTE. COMUNIQUESE CON TI", "Reportes", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void crearReporte(){
        try{
            getDatosReporte();
            genPDF.generarReporte("Seguimientos Cronicos - " + empleado.getNombre() + " - " + seguimientoCronicos.getFecha());
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
        cont_SeguimientoCronicos = new javax.swing.JPanel();
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
        lblImpresionClinica = new javax.swing.JLabel();
        jScrollPane25 = new javax.swing.JScrollPane();
        txtAreaImpresionClinica = new javax.swing.JTextArea();
        lblTratamiento = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaTratamiento = new javax.swing.JTextArea();
        lblObservaciones = new javax.swing.JLabel();
        jScrollPane26 = new javax.swing.JScrollPane();
        txtAreaObservaciones = new javax.swing.JTextArea();
        checkReferencia = new javax.swing.JCheckBox();
        checkTraslado = new javax.swing.JCheckBox();
        lblResponsable = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtAreaDatosSubjetivos = new javax.swing.JTextArea();
        lblDatosSubjetivos = new javax.swing.JLabel();
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
        lblGenitourinario = new javax.swing.JLabel();
        jScrollPane22 = new javax.swing.JScrollPane();
        txtAreaGenitourinario = new javax.swing.JTextArea();
        lblNeurologico = new javax.swing.JLabel();
        jScrollPane24 = new javax.swing.JScrollPane();
        txtAreaNeurologico = new javax.swing.JTextArea();
        panelCombobox = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblTituloCombobox = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtSeguimientoCronicos = new javax.swing.JTable();
        btn_Ingresar = new javax.swing.JPanel();
        lbl_btnIngresar = new javax.swing.JLabel();
        panelPaginacionSeguimientoCronicos = new javax.swing.JPanel();
        lbl_SeleccionSeguimientoCronicos = new javax.swing.JLabel();
        lbl_TituloSeleccion = new javax.swing.JLabel();

        timeHora.set24hourMode(true);
        timeHora.setDisplayText(txtHora);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cont_SeguimientoCronicos.setBackground(new java.awt.Color(255, 255, 255));
        cont_SeguimientoCronicos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTituloPrincipal.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        lblTituloPrincipal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTituloPrincipal.setText("Ficha de Seguimiento Pacientes Crónicos");
        cont_SeguimientoCronicos.add(lblTituloPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, -1));

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

        cont_SeguimientoCronicos.add(tablaTitulos, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 30, 600, 90));

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

        cont_SeguimientoCronicos.add(panelBotonesCRUD, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 0, 270, 120));

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

        cont_SeguimientoCronicos.add(btn_Confirmar, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 540, 90, 35));

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

        panelFormulario.add(btnReportes, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 380, 130, 35));

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

        lblImpresionClinica.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblImpresionClinica.setText("Impresión Clínica");
        panelAuxiliar.add(lblImpresionClinica, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        jScrollPane25.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaImpresionClinica.setColumns(20);
        txtAreaImpresionClinica.setRows(5);
        jScrollPane25.setViewportView(txtAreaImpresionClinica);

        panelAuxiliar.add(jScrollPane25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 230, 50));

        lblTratamiento.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblTratamiento.setText("Tratamiento");
        panelAuxiliar.add(lblTratamiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, -1, -1));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaTratamiento.setColumns(20);
        txtAreaTratamiento.setRows(5);
        jScrollPane1.setViewportView(txtAreaTratamiento);

        panelAuxiliar.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 30, 230, 50));

        lblObservaciones.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblObservaciones.setText("Observaciones");
        panelAuxiliar.add(lblObservaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 90, -1, 20));

        jScrollPane26.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaObservaciones.setColumns(20);
        txtAreaObservaciones.setRows(5);
        jScrollPane26.setViewportView(txtAreaObservaciones);

        panelAuxiliar.add(jScrollPane26, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 110, 230, 50));

        checkReferencia.setBackground(new java.awt.Color(255, 255, 255));
        checkReferencia.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        checkReferencia.setText("Referencia");
        panelAuxiliar.add(checkReferencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, -1, -1));

        checkTraslado.setBackground(new java.awt.Color(255, 255, 255));
        checkTraslado.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        checkTraslado.setText("Traslado");
        panelAuxiliar.add(checkTraslado, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 200, -1, -1));

        lblResponsable.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblResponsable.setText("Responsable:");
        panelAuxiliar.add(lblResponsable, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 200, -1, 20));

        jScrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaDatosSubjetivos.setColumns(20);
        txtAreaDatosSubjetivos.setRows(5);
        jScrollPane3.setViewportView(txtAreaDatosSubjetivos);

        panelAuxiliar.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 230, 50));

        lblDatosSubjetivos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblDatosSubjetivos.setText("Datos Subjetivos");
        panelAuxiliar.add(lblDatosSubjetivos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, 20));

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

        panelCartas.add(panelPagina1, "card3");

        panelPagina2.setBackground(new java.awt.Color(255, 255, 255));
        panelPagina2.setMinimumSize(new java.awt.Dimension(566, 365));
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

        lblAlteraciones.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblAlteraciones.setText("Alteraciones");
        panelPagina2.add(lblAlteraciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        jScrollPane13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaAlteraciones.setColumns(20);
        txtAreaAlteraciones.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaAlteraciones.setRows(5);
        jScrollPane13.setViewportView(txtAreaAlteraciones);

        panelPagina2.add(jScrollPane13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 260, 35));

        lblPielFaneras.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblPielFaneras.setText("Piel y Faneras");
        panelPagina2.add(lblPielFaneras, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jScrollPane14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaPielFaneras.setColumns(20);
        txtAreaPielFaneras.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaPielFaneras.setRows(5);
        jScrollPane14.setViewportView(txtAreaPielFaneras);

        panelPagina2.add(jScrollPane14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 260, 35));

        lblCabeza.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblCabeza.setText("Cabeza");
        panelPagina2.add(lblCabeza, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, 20));

        jScrollPane15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaCabeza.setColumns(20);
        txtAreaCabeza.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaCabeza.setRows(5);
        jScrollPane15.setViewportView(txtAreaCabeza);

        panelPagina2.add(jScrollPane15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 260, 35));

        lblOjoidos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblOjoidos.setText("Ojos, oídos, nariz y boca");
        panelPagina2.add(lblOjoidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, 20));

        jScrollPane16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaOjoidos.setColumns(20);
        txtAreaOjoidos.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaOjoidos.setRows(5);
        jScrollPane16.setViewportView(txtAreaOjoidos);

        panelPagina2.add(jScrollPane16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 260, 35));

        lblOrofaringe.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblOrofaringe.setText("Orofaringe");
        panelPagina2.add(lblOrofaringe, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, -1, 20));

        jScrollPane17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaOrofaringe.setColumns(20);
        txtAreaOrofaringe.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaOrofaringe.setRows(5);
        jScrollPane17.setViewportView(txtAreaOrofaringe);

        panelPagina2.add(jScrollPane17, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 30, 260, 35));

        lblCuello.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblCuello.setText("Cuello");
        panelPagina2.add(lblCuello, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 70, -1, 20));

        jScrollPane18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaCuello.setColumns(20);
        txtAreaCuello.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaCuello.setRows(5);
        jScrollPane18.setViewportView(txtAreaCuello);

        panelPagina2.add(jScrollPane18, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 90, 260, 35));

        lblRespiratorio.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblRespiratorio.setText("Cardiopulmonar");
        panelPagina2.add(lblRespiratorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 130, -1, 20));

        jScrollPane19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaCardiopulmonar.setColumns(20);
        txtAreaCardiopulmonar.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaCardiopulmonar.setRows(5);
        jScrollPane19.setViewportView(txtAreaCardiopulmonar);

        panelPagina2.add(jScrollPane19, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 150, 260, 35));

        lblCardiovascular.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblCardiovascular.setText("Torax");
        panelPagina2.add(lblCardiovascular, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 190, -1, 20));

        jScrollPane20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaTorax.setColumns(20);
        txtAreaTorax.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaTorax.setRows(5);
        jScrollPane20.setViewportView(txtAreaTorax);

        panelPagina2.add(jScrollPane20, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 210, 260, 35));

        lblGastrointestinal.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblGastrointestinal.setText("Abdomen");
        panelPagina2.add(lblGastrointestinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, -1, 20));

        jScrollPane21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaAbdomen.setColumns(20);
        txtAreaAbdomen.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaAbdomen.setRows(5);
        jScrollPane21.setViewportView(txtAreaAbdomen);

        panelPagina2.add(jScrollPane21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 260, 35));

        lblExtremidades.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblExtremidades.setText("Extremidades");
        panelPagina2.add(lblExtremidades, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 250, -1, 20));

        jScrollPane23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaExtremidades.setColumns(20);
        txtAreaExtremidades.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaExtremidades.setRows(5);
        jScrollPane23.setViewportView(txtAreaExtremidades);

        panelPagina2.add(jScrollPane23, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 270, 260, 35));

        lblGenitourinario.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblGenitourinario.setText("Sistema Genitourinario");
        panelPagina2.add(lblGenitourinario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, -1, 20));

        jScrollPane22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaGenitourinario.setColumns(20);
        txtAreaGenitourinario.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaGenitourinario.setRows(5);
        jScrollPane22.setViewportView(txtAreaGenitourinario);

        panelPagina2.add(jScrollPane22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 260, 35));

        lblNeurologico.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblNeurologico.setText("Neurológico");
        panelPagina2.add(lblNeurologico, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 310, -1, 20));

        jScrollPane24.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAreaNeurologico.setColumns(20);
        txtAreaNeurologico.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAreaNeurologico.setRows(5);
        jScrollPane24.setViewportView(txtAreaNeurologico);

        panelPagina2.add(jScrollPane24, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 330, 260, 35));

        panelCartas.add(panelPagina2, "card4");

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

        jtSeguimientoCronicos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jtSeguimientoCronicos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jtSeguimientoCronicos);

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

        panelPaginacionSeguimientoCronicos.setBackground(new java.awt.Color(255, 255, 255));
        panelCombobox.add(panelPaginacionSeguimientoCronicos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 980, 30));

        lbl_SeleccionSeguimientoCronicos.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lbl_SeleccionSeguimientoCronicos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lbl_SeleccionSeguimientoCronicos.setPreferredSize(new java.awt.Dimension(300, 40));
        panelCombobox.add(lbl_SeleccionSeguimientoCronicos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        lbl_TituloSeleccion.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lbl_TituloSeleccion.setText("Registro Seleccionado");
        panelCombobox.add(lbl_TituloSeleccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        panelCartasContenido.add(panelCombobox, "card4");

        cont_SeguimientoCronicos.add(panelCartasContenido, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 1080, 480));

        getContentPane().add(cont_SeguimientoCronicos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 600));

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
                    if (verificarseguimientoCronicos()){
                        try {
                            getRevisionSistemas();
                            getSeguimientoCronicos();
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
        if (lbl_SeleccionSeguimientoCronicos.getText().length() > 0){
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
                buscarEmpleadoSeguimientoCronicos(jtSeguimientoCronicos.getValueAt(jtSeguimientoCronicos.getSelectedRow(), 0).toString());
            } catch (SQLException ex) {
                Logger.getLogger(SeguimientoCronicos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ConnectException ex) {
                Logger.getLogger(SeguimientoCronicos.class.getName()).log(Level.SEVERE, null, ex);
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
    private javax.swing.JPanel btn_SeleccionEmpleado;
    private javax.swing.JCheckBox checkLentes;
    private javax.swing.JCheckBox checkReferencia;
    private javax.swing.JCheckBox checkTraslado;
    private javax.swing.JComboBox<String> comboTalla;
    private javax.swing.JPanel cont_SeguimientoCronicos;
    public static javax.swing.ButtonGroup grbtn_Sexo;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
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
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jtEmpleado;
    public javax.swing.JTable jtSeguimientoCronicos;
    public javax.swing.JLabel lblAG;
    public javax.swing.JLabel lblAlteraciones;
    public javax.swing.JLabel lblArea;
    private javax.swing.JLabel lblCabeza;
    private javax.swing.JLabel lblCardiovascular;
    public javax.swing.JLabel lblClinica;
    public javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblCuello;
    private javax.swing.JLabel lblDatosSubjetivos;
    public javax.swing.JLabel lblEdad;
    private javax.swing.JLabel lblExtremidades;
    public javax.swing.JLabel lblFR;
    public javax.swing.JLabel lblFechaMod;
    private javax.swing.JLabel lblFraccion1;
    private javax.swing.JLabel lblFraccion2;
    private javax.swing.JLabel lblGastrointestinal;
    private javax.swing.JLabel lblGenitourinario;
    public javax.swing.JLabel lblGlicemia;
    public javax.swing.JLabel lblHora;
    public javax.swing.JLabel lblIMC;
    private javax.swing.JLabel lblImpresionClinica;
    private javax.swing.JLabel lblInstruccionComboboxEmpleado;
    private javax.swing.JLabel lblNeurologico;
    public javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblObservaciones;
    private javax.swing.JLabel lblOjoDerecho;
    private javax.swing.JLabel lblOjoIzquierdo;
    private javax.swing.JLabel lblOjoidos;
    private javax.swing.JLabel lblOrofaringe;
    private javax.swing.JLabel lblPA;
    public javax.swing.JLabel lblPeso;
    private javax.swing.JLabel lblPielFaneras;
    public javax.swing.JLabel lblPuesto1;
    public javax.swing.JLabel lblPulso;
    private javax.swing.JLabel lblReportes;
    private javax.swing.JLabel lblRespiratorio;
    private javax.swing.JLabel lblResponsable;
    private javax.swing.JLabel lblRuffier;
    public javax.swing.JLabel lblSPO2;
    public javax.swing.JLabel lblSeguridad;
    public javax.swing.JLabel lblSexo;
    public javax.swing.JLabel lblTalla;
    public javax.swing.JLabel lblTemperatura;
    public javax.swing.JLabel lblTituloCombobox;
    public javax.swing.JLabel lblTituloPrincipal;
    public javax.swing.JLabel lblTitulos;
    private javax.swing.JLabel lblTratamiento;
    public javax.swing.JLabel lbl_BtnEliminar;
    public javax.swing.JLabel lbl_InicioInicio;
    private javax.swing.JLabel lbl_RevisionPorSistemas;
    private javax.swing.JLabel lbl_SeleccionSeguimientoCronicos;
    private javax.swing.JLabel lbl_TituloSeleccion;
    public javax.swing.JLabel lbl_btnActualizar;
    public javax.swing.JLabel lbl_btnCrear;
    public javax.swing.JLabel lbl_btnIngresar;
    private javax.swing.JLabel lbl_btnOtroEmpleado;
    private javax.swing.JLabel lbl_btnSeleccionEmpleado;
    public javax.swing.JLabel lbl_btn_Confirmar;
    private javax.swing.JLabel lbl_btn_Pagina1_2;
    private javax.swing.JLabel lbl_btn_Pagina2_1;
    public javax.swing.JPanel panelAG;
    private javax.swing.JPanel panelAuxiliar;
    public javax.swing.JPanel panelBotonesCRUD;
    private javax.swing.JPanel panelCartas;
    private javax.swing.JPanel panelCartasContenido;
    private javax.swing.JPanel panelCartasEmpleado;
    private javax.swing.JPanel panelCombobox;
    private javax.swing.JPanel panelComboboxEmpleado;
    public javax.swing.JPanel panelEdicion;
    private javax.swing.JPanel panelEmpleado;
    public javax.swing.JPanel panelFecha;
    private javax.swing.JPanel panelFormulario;
    public javax.swing.JPanel panelInicio;
    private javax.swing.JPanel panelPagina1;
    private javax.swing.JPanel panelPagina2;
    private javax.swing.JPanel panelPaginacionEmpleado;
    private javax.swing.JPanel panelPaginacionSeguimientoCronicos;
    public javax.swing.JPanel panelSeguridad;
    public javax.swing.JPanel panelTitulos;
    public javax.swing.JRadioButton rbtn_SexoF;
    public javax.swing.JRadioButton rbtn_SexoM;
    public javax.swing.JPanel tablaTitulos;
    private com.raven.swing.TimePicker timeHora;
    public javax.swing.JTextField txtArea;
    private javax.swing.JTextArea txtAreaAbdomen;
    private javax.swing.JTextArea txtAreaAlteraciones;
    private javax.swing.JTextArea txtAreaCabeza;
    private javax.swing.JTextArea txtAreaCardiopulmonar;
    private javax.swing.JTextArea txtAreaCuello;
    private javax.swing.JTextArea txtAreaDatosSubjetivos;
    private javax.swing.JTextArea txtAreaExtremidades;
    private javax.swing.JTextArea txtAreaGenitourinario;
    private javax.swing.JTextArea txtAreaImpresionClinica;
    private javax.swing.JTextArea txtAreaNeurologico;
    private javax.swing.JTextArea txtAreaObservaciones;
    private javax.swing.JTextArea txtAreaOjoidos;
    private javax.swing.JTextArea txtAreaOrofaringe;
    private javax.swing.JTextArea txtAreaPielFaneras;
    private javax.swing.JTextArea txtAreaTorax;
    private javax.swing.JTextArea txtAreaTratamiento;
    public javax.swing.JTextField txtCodigo;
    public javax.swing.JTextField txtEdad;
    public javax.swing.JTextField txtFR;
    public javax.swing.JTextField txtFecha;
    public javax.swing.JTextField txtGlicemia;
    private javax.swing.JTextField txtHora;
    public javax.swing.JTextField txtIMC;
    public javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtOjoDerechoDenominador;
    private javax.swing.JTextField txtOjoDerechoNumerador;
    private javax.swing.JTextField txtOjoIzquierdoDenominador;
    private javax.swing.JTextField txtOjoIzquierdoNumerador;
    private javax.swing.JTextField txtPA;
    public javax.swing.JTextField txtPeso;
    public javax.swing.JLabel txtPreempleoId;
    public javax.swing.JTextField txtPuesto;
    public javax.swing.JTextField txtPulso;
    private javax.swing.JTextField txtRuffier;
    public javax.swing.JTextField txtSPO2;
    private javax.swing.JTextField txtSeleccionEmpleado;
    public javax.swing.JTextField txtTemperatura;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        Object evt = e.getSource();
        if (evt.equals(filasPermitidasFicha))
            paginadorSeguimientoCronicos.eventComboBox(filasPermitidasFicha);
        else if (evt.equals(filasPermitidasEmpleado))
            paginadorEmpleado.eventComboBox(filasPermitidasEmpleado);
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        Object evt = e.getSource();
        if (evt.equals(filasPermitidasFicha))
            paginadorSeguimientoCronicos.actualizarBotonesPaginacion();
        else if (evt.equals(filasPermitidasEmpleado))
            paginadorEmpleado.actualizarBotonesPaginacion();
    }
}