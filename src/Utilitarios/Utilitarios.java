package Utilitarios;

import java.awt.Color;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import Conexion.Conexion;
import java.net.ConnectException;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;

public class Utilitarios {
    
    public Utilitarios(){
    }
    
    /**
     * Método que construye una imagen con anchura y altura
     * @param path URL de la imagen.
     * @param width anchura de la etiqueta.
     * @param height altura de la etiqueta.
     * @return icono.
     */
    public Icon construirImagen(String path, int width, int height) {
        try {
            ImageIcon imagen = new ImageIcon(getClass().getResource(path));
            Image imagenEscalada = imagen.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
            Icon icono = new ImageIcon(imagenEscalada);
            return icono;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    /**
     * Método que cambia el color 20 puntos hacia arriba para aclararlo.
     * @param colorInicial Color inicial del componente.
     * @return colorFinal Color 20 puntos más claro que el inicial en cada byte (Rojo, Verde y Azul).
     */
    public Color colorCursorEntra(Color colorInicial){
        int R = (colorInicial.getRed() + 20), G = (colorInicial.getGreen() + 20), B = (colorInicial.getBlue() + 20);
        if (R>255)
            R = 255;
        if (G > 255)
            G = 255;
        if (B > 255)
            B = 255;
        Color colorFinal = new Color(R,G,B);
        return colorFinal;
    }
    
    /**
     * Método que cambia el color 20 puntos hacia abajo para oscurecerlo.
     * @param colorInicial Color inicial del componente.
     * @return colorFinal. Color 20 puntos más oscuro que el inicial en cada byte (Rojo, Verde y Azul).
     */
    public Color colorCursorSale(Color colorInicial){
        int R = (colorInicial.getRed() - 20), G = (colorInicial.getGreen() - 20), B = (colorInicial.getBlue() - 20);
        if (R>255)
            R = 255;
        if (G > 255)
            G = 255;
        if (B > 255)
            B = 255;
        Color colorFinal = new Color(R,G,B);
        return colorFinal;
    }
    /**
     * Método que cambia el formato de fecha a uno estándar.
     * @param fecha Fecha del sistema.
     * @return  Fecha con el formato cambiado (dd-MM-yyyy).
     */
    public String convertirFechaSQL(String fecha) {
        String f = "";

        SimpleDateFormat fromUser = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            f = myFormat.format(fromUser.parse(fecha));
        } catch (ParseException e) {
        }
        return f;
    }
    
    /**
     * Método que cambia el formato de fecha a uno estándar.
     * @param fecha Fecha del sistema.
     * @return  Fecha con el formato cambiado (dd-MM-yyyy).
     */
    public String convertirFechaGUI(String fecha) {
        String f = "";

        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            f = myFormat.format(fromUser.parse(fecha));
        } catch (ParseException e) {
        }
        return f;
    }
    /**
     * Método que verifica si una cadena pertenece a un número.
     * @param numero Fecha del sistema.
     * @return  Verdadero si es un número, falso la cadena está vacía o no es un número..
     */
    public boolean verificarNumero(String numero){
        if (numero == null)
            return false;
        try {
            double d = Double.parseDouble(numero);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
    public DefaultTableModel consultaMixta(String sql, String[] columnas) throws SQLException, ConnectException{
        DefaultTableModel modelo = new DefaultTableModel();
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        ResultSet result = null;
        try {
            smt = conn.prepareStatement(sql);
            result = smt.executeQuery();
            ResultSetMetaData rsMd = result.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            for (int i = 0; i<columnas.length; i++)
                modelo.addColumn(columnas[i]);
            while(result.next()){
                Object[] fila = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++){
                    fila[i] = result.getObject(i+1);
                }
                modelo.addRow(fila);
            }
        } catch (Exception e) {
        } finally {
            if (smt != null) {
                smt.close();
            }
            if (result != null) {
                smt.close();
            }
            if (conn != null) {
                conex.disconnect(conn);
                conn.close();
                conn = null;
            }
        }
        return modelo;
    }
}
