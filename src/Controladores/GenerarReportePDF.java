package Controladores;

import Conexion.Conexion;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class GenerarReportePDF {
    private String DocumentoId;
    private String[] datosPDF;
    private String[] datosNombresPDF;
    private String Usuario;
    private String TipoReporte;
    private String Fecha;
    
    private ArrayList<String> datosNombres = new ArrayList<String>();
    private ArrayList<String> datos = new ArrayList<String>();
    private ArrayList<String> datosNombresEmpleado = new ArrayList<String>();
    private ArrayList<String> datosEmpleado = new ArrayList<String>();
    private ArrayList<String> datosFinales = new ArrayList<String>();
    private ArrayList<String> datosNombresFinales = new ArrayList<String>();

    public String getDocumentoId() {
        return DocumentoId;
    }

    public void setDocumentoId(String DocumentoId) {
        this.DocumentoId = DocumentoId;
    }

    private String[] getDatosPDF() {
        return datosPDF;
    }

    private void setDatosPDF(String[] Datos) {
        this.datosPDF = Datos;
    }

    private String[] getDatosNombresPDF() {
        return datosNombresPDF;
    }

    private void setDatosNombresPDF(String[] DatosNombres) {
        this.datosNombresPDF = DatosNombres;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }

    public String getTipoReporte() {
        return TipoReporte;
    }

    public void setTipoReporte(String TipoReporte) {
        this.TipoReporte = TipoReporte;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }
    
    public void generarDatosEmpleado(String idEmpleado) throws SQLException, ConnectException{
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        ResultSet result = null;
        String sql = "";
        ArrayList<String> datosNombres = new ArrayList<String>();
        ArrayList<String> datos = new ArrayList<String>();
        datosNombres.add("DATOS DEL EMPLEADO:");
        datos.add("");
        //OBTENER DATOS DEL EMPLEADO
        sql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME='EMPLEADO' LIMIT 4;";
        try {
            smt = conn.prepareStatement(sql);
            result = smt.executeQuery();

            while (result.next()) {
                datosNombres.add(result.getString(1).substring(4)+": ");
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
        conn = conex.connect();
        sql = "SELECT EMP_ID, EMP_CODIGO, EMP_NOMBRE, EMP_SEXO FROM EMPLEADO WHERE EMP_ID = ?";
        try {
            smt = conn.prepareStatement(sql);
            smt.setString(1, idEmpleado);
            result = smt.executeQuery();

            while (result.next()) {
                datos.add(result.getString(1));
                datos.add(result.getString(2));
                datos.add(result.getString(3));
                datos.add(result.getString(4));
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
        this.datosEmpleado.addAll(datos);
        this.datosNombresEmpleado.addAll(datosNombres);
    }
    
    public void generarDatos(String nombreTabla, String idRegistro, String llavePrimaria, String prefijoTabla) throws SQLException, ConnectException{
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        ResultSet result = null;
        ResultSetMetaData resultMetaData = null;
        String sql = "";
        ArrayList<String> datosNombres = new ArrayList<String>();
        ArrayList<String> datos = new ArrayList<String>();
        datosNombres.add(" ");
        datos.add(" ");
        datosNombres.add("DATOS DE " + nombreTabla + ":");
        datos.add("");
        //OBTENER NOMBRE DE COLUMNAS
        conn = conex.connect();
        sql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME=?";
        try {
            smt = conn.prepareStatement(sql);
            smt.setString(1, nombreTabla);

            result = smt.executeQuery();

            while (result.next()) {
                datosNombres.add(result.getString(1).substring(prefijoTabla.length())+": ");                
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
        
        //OBTENER LOS DATOS
        conn = conex.connect();
        sql = "SELECT * FROM "+nombreTabla+" WHERE "+llavePrimaria+" = ?";
        try {
            smt = conn.prepareStatement(sql);
            //smt.setString(1, nombreTabla);
            //smt.setString(2, llavePrimaria);
            smt.setString(1, idRegistro);

            result = smt.executeQuery();
            resultMetaData = result.getMetaData();
            while (result.next()) {
                for(int i = 1; i <= resultMetaData.getColumnCount(); i++)
                    datos.add(result.getString(i));
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
            this.datos.addAll(datos);
            this.datosNombres.addAll(datosNombres);
        }
    }
  
    public void generarReporte(String nombreDocumento){
        
        try{
            this.datosFinales.addAll(this.datosEmpleado);
            this.datosFinales.addAll(this.datos);
            this.datosNombresFinales.addAll(this.datosNombresEmpleado);
            this.datosNombresFinales.addAll(this.datosNombres);
            setDatosNombresPDF(datosNombresFinales.toArray(new String[datosNombresFinales.size()]));
            setDatosPDF(datosFinales.toArray(new String[datosFinales.size()]));
            Document documento = new Document();
            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Desktop/" + nombreDocumento + ".pdf"));
            Image header = Image.getInstance("src/Imagenes/AG_logo.png");
            header.scaleToFit(650, 1000);
            header.setAlignment(Chunk.ALIGN_CENTER);
            
            Paragraph parrafo = new Paragraph();
            parrafo.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo.add("Reporte Creado por: " + getUsuario() + "\n\n");
            parrafo.setFont(FontFactory.getFont("Roboto", 18, Font.BOLD, BaseColor.DARK_GRAY));
            parrafo.add("Reporte de " + getTipoReporte() + "\n\n");
            
            documento.open();
            documento.add(header);
            documento.add(parrafo);
            
            PdfPTable tabla = new PdfPTable(1);
            for(int i = 0; i < this.datosNombresPDF.length; i++){
                tabla.addCell(getDatosNombresPDF()[i] + getDatosPDF()[i]);
            }
            documento.add(tabla);
            documento.close();
            JOptionPane.showMessageDialog(null, "Reporte: " + nombreDocumento + "\ncreado en el escritorio");
        }catch(DocumentException e){
            System.out.println("Error: " + e);
        } catch (IOException ex) {
            System.out.println("Error: " + ex);
        }
    }
}
