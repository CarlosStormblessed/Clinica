
package Controladores;

import Utilitarios.DatosPaginacion;
import Utilitarios.ModeloTabla;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

public class PaginadorTabla <T>{
    private JTable tabla;
    private JComboBox <Integer> comboboxFilasPermitidas;
    private JPanel panelPaginacionBotones;
    private DatosPaginacion proveedorDatosPaginacion;
    private int [] filasPermitidas;
    private int filaPermitida, limiteBotonesPaginacion = 9;
    private ModeloTabla<T> modeloTabla;
    private int paginaActual = 1;
    
    public PaginadorTabla(JTable tabla, DatosPaginacion<T> proveedorDatosPaginacion, int [] filasPermitidas, int filaPermitida){
        this.tabla = tabla;
        this.proveedorDatosPaginacion = proveedorDatosPaginacion;
        this.filasPermitidas = filasPermitidas;
        this.filaPermitida = filaPermitida;
        init();
    }
    
    private void init(){
        iniciarModeloTabla();
        paginar();
    }
    
    private void iniciarModeloTabla(){
        try{
            this.modeloTabla = (ModeloTabla<T>) this.tabla.getModel();
        }catch (Exception ex){
            System.out.println("Error al iniciar el modelo de tabla: "+ ex.getMessage());
        }
    }
    
    public void crearListadoFilasPermitidas(JPanel panelPaginacion){
        panelPaginacionBotones = new JPanel(new GridLayout(1, limiteBotonesPaginacion, 3, 3));
        panelPaginacion.add(panelPaginacionBotones);
        
        if (filasPermitidas != null){
            Integer arreglo[] = new Integer[filasPermitidas.length];
            for(int i = 0; i < filasPermitidas.length; i++)
                arreglo[i] = filasPermitidas[i];
            comboboxFilasPermitidas = new JComboBox<>(arreglo);
            panelPaginacion.add(Box.createHorizontalStrut(15));
            panelPaginacion.add(new JLabel("Número de filas: "));
            panelPaginacion.add(comboboxFilasPermitidas);
        }
    }
    
    public void eventComboBox(JComboBox comboBox){
        int filaInicialPagina = ((paginaActual-1)*filaPermitida)+1;
        filaPermitida = (Integer) comboBox.getSelectedItem();
        paginaActual = ((filaInicialPagina-1)/filaPermitida)+1;
        paginar();
    }
    
    public void actualizarBotonesPaginacion(){
        panelPaginacionBotones.removeAll();
        
        int totalFilas = proveedorDatosPaginacion.getTotalRowCount();
        int paginas = (int) Math.ceil((double) totalFilas / filaPermitida);
        
        if (paginas > limiteBotonesPaginacion){
            agregarBotonesPaginacion(panelPaginacionBotones, 1);
            if (paginaActual <= (limiteBotonesPaginacion+1)/2){
                agregarRangoBotonesPaginacion(panelPaginacionBotones, 2, limiteBotonesPaginacion-2);
                panelPaginacionBotones.add(crearElipses());
                agregarBotonesPaginacion(panelPaginacionBotones, paginas);
            }else if (paginaActual > (paginas - ((limiteBotonesPaginacion+1)/2))){
                panelPaginacionBotones.add(crearElipses());
                agregarRangoBotonesPaginacion(panelPaginacionBotones, paginas-limiteBotonesPaginacion+3, paginas);
            }
            else{
                panelPaginacionBotones.add(crearElipses());
                int inicio = paginaActual - (limiteBotonesPaginacion - 4)/2;
                int finalizacion = inicio + limiteBotonesPaginacion - 5;
                agregarRangoBotonesPaginacion(panelPaginacionBotones, inicio, finalizacion);
                agregarBotonesPaginacion(panelPaginacionBotones, paginas);
            }
        }else
            agregarRangoBotonesPaginacion(panelPaginacionBotones, 1, paginas);
        panelPaginacionBotones.getParent().validate();
        panelPaginacionBotones.getParent().repaint();
    }
    
    private JLabel crearElipses(){
        return new JLabel("...", SwingConstants.CENTER);
    }
    
    private void agregarRangoBotonesPaginacion(JPanel panel, int inicio, int finalizacion){
        int init = inicio;
        for (inicio = init; inicio <= finalizacion; inicio++){
            agregarBotonesPaginacion(panel, inicio);
        }
    }
    
    private void agregarBotonesPaginacion(JPanel panel, int numeroPagina){
        JToggleButton toogleButton = new JToggleButton(Integer.toString(numeroPagina));
        toogleButton.setMargin(new Insets(1, 3, 1, 3));
        panel.add(toogleButton);
        if (numeroPagina == paginaActual)
            toogleButton.setSelected(true);
        toogleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paginaActual = Integer.parseInt(e.getActionCommand());
                paginar();
            }
        });
    }
    
    private void paginar(){
        int inicio= (paginaActual-1)*filaPermitida;
        int finalizacion= inicio+filaPermitida;
        if (finalizacion > proveedorDatosPaginacion.getTotalRowCount()){
            finalizacion = proveedorDatosPaginacion.getTotalRowCount();
        }
        List<T> lista = proveedorDatosPaginacion.getRows(inicio, finalizacion);
        modeloTabla.setLista(lista);
        modeloTabla.fireTableDataChanged();
    }

    public JComboBox<Integer> getComboboxFilasPermitidas() {
        return comboboxFilasPermitidas;
    }

    public void setComboboxFilasPermitidas(JComboBox<Integer> comboboxFilasPermitidas) {
        this.comboboxFilasPermitidas = comboboxFilasPermitidas;
    }
}
