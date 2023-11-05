package Controladores;
import Modelos.AntecedentesMod;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Conexion.Conexion;
import java.net.ConnectException;

public class AntecedentesCtrl {
    public int Crear(AntecedentesMod modelo) throws SQLException, ConnectException {
        
        int result = 0;
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        try {
            //String sql = "INSERT INTO ANTECEDENTES VALUES ((SELECT IFNULL(MAX(ANT_ID), 0)+1 FROM ANTECEDENTES a), '?','?','?','?','?','?','?','?',?,'?','?','?','?','?','?',?,?,?,'?',?)";
            String sql = "INSERT INTO ANTECEDENTES VALUES ((SELECT IFNULL(MAX(ANT_ID), 0)+1 FROM ANTECEDENTES a), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            conn.setAutoCommit(false);
            smt = conn.prepareStatement(sql);
            smt.setString(1, modelo.getFamiliares());
            smt.setString(2, modelo.getMedicos());
            smt.setString(3, modelo.getTratamientos());
            smt.setString(4, modelo.getLaboratorios());
            smt.setString(5, modelo.getQuirurgicos());
            smt.setString(6, modelo.getTraumaticos());
            smt.setString(7, modelo.getAlergicos());
            smt.setString(8, modelo.getVicios());
            smt.setString(9, modelo.getMenarquia());
            smt.setString(10, modelo.getFur());
            smt.setString(11, modelo.getG());
            smt.setString(12, modelo.getP());
            smt.setString(13, modelo.getCstp());
            smt.setString(14, modelo.getHv());
            smt.setString(15, modelo.getHm());
            smt.setString(16, modelo.getAb());
            smt.setString(17, modelo.getMpf());
            smt.setString(18, modelo.getDiagnostico());
            smt.setString(19, modelo.getEstado());
            

            smt.executeUpdate();

            conn.commit();

            return 1;

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            conn.rollback();
            return 0;
        } finally {
            if (smt != null) {
                smt.close();
            }
            if (conn != null) {
                conex.disconnect(conn);
                conn.close();
                conn = null;
            }
        }
    }
    
    public String getMaxId() throws SQLException, ConnectException{
        String id = "";
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        ResultSet result = null;

        AntecedentesMod modeloBuscar = null;

        String sql = "SELECT IFNULL(MAX(ANT_ID), 0) FROM ANTECEDENTES";
        try {
            smt = conn.prepareStatement(sql);
            result = smt.executeQuery();

            while (result.next()) {
                modeloBuscar = new AntecedentesMod();

                id = result.getString(1);
                
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
        return id;
    }
    
    public int Actualizar(AntecedentesMod modelo) throws SQLException, ConnectException{
        
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
         int result = 0;
        try {
            String sql = "UPDATE ANTECEDENTES SET ANT_FAMILIARES = ?, ANT_MEDICOS = ?, ANT_TRATAMIENTOS = ?, ANT_LABORATORIOS = ?, ANT_QUIRURGICOS = ?, ANT_TRAUMATICOS = ?, ANT_ALERGICOS = ?, ANT_VICIOS = ?, ANT_MENARQUIA = ?, ANT_FUR = ?, ANT_G = ?, ANT_P = ?, ANT_CSTP = ?, ANT_HV = ?, ANT_HM = ?, ANT_AB = ?, ANT_MPF = ?, ANT_DIAGNOSTICO = ? WHERE ANT_ID = ?";
            conn.setAutoCommit(false);

            smt = conn.prepareStatement(sql);

            smt = conn.prepareStatement(sql);
            smt.setString(1, modelo.getFamiliares());
            smt.setString(2, modelo.getMedicos());
            smt.setString(3, modelo.getTratamientos());
            smt.setString(4, modelo.getLaboratorios());
            smt.setString(5, modelo.getQuirurgicos());
            smt.setString(6, modelo.getTraumaticos());
            smt.setString(7, modelo.getAlergicos());
            smt.setString(8, modelo.getVicios());
            smt.setString(9, modelo.getMenarquia());
            smt.setString(10, modelo.getFur());
            smt.setString(11, modelo.getG());
            smt.setString(12, modelo.getP());
            smt.setString(13, modelo.getCstp());
            smt.setString(14, modelo.getHv());
            smt.setString(15, modelo.getHm());
            smt.setString(16, modelo.getAb());
            smt.setString(17, modelo.getMpf());
            smt.setString(18, modelo.getDiagnostico());
            smt.setString(19, modelo.getId());
            
            smt.executeUpdate();
            conn.commit();

            return 1;

        } catch (Exception e) {
            conn.rollback();
            return 0;
        } finally {
            if (smt != null) {
                smt.close();
            }
            if (conn != null) {
                conex.disconnect(conn);
                conn.close();
                conn = null;
            }
        }
    }
    
    public int Eliminar(AntecedentesMod modelo) throws ConnectException, SQLException{
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        int result = 0;
        try {
            String sql = "UPDATE ANTECEDENTES SET ANT_ESTADO = 0 WHERE ANT_ID = ?";
            conn.setAutoCommit(false);

            smt = conn.prepareStatement(sql);

            smt = conn.prepareStatement(sql);
            smt.setString(1, modelo.getId());
            smt.executeUpdate();
            conn.commit();

            return 1;

        } catch (Exception e) {
            conn.rollback();
            return 0;
        } finally {
            if (smt != null) {
                smt.close();
            }
            if (conn != null) {
                conex.disconnect(conn);
                conn.close();
                conn = null;
            }
        }
    }
    
    public List<AntecedentesMod> seleccionarTodos() throws SQLException, ConnectException {
        
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        ResultSet result = null;
        List<AntecedentesMod> lista = new ArrayList<AntecedentesMod>();

        AntecedentesMod modeloBuscar = null;

        String sql = "";

        sql = "select  * FROM Antecedentes WHERE ANT_ESTADO = 1 ORDER BY ANT_ID ASC";
        //+ filtro;

        try {
            
            smt = conn.prepareStatement(sql);

            result = smt.executeQuery();

            while (result.next()) {
                modeloBuscar = new AntecedentesMod();

                modeloBuscar.setId(result.getString(1));
                modeloBuscar.setFamiliares(result.getString(2));
                modeloBuscar.setMedicos(result.getString(3));
                modeloBuscar.setTratamientos(result.getString(4));
                modeloBuscar.setLaboratorios(result.getString(5));
                modeloBuscar.setQuirurgicos(result.getString(6));
                modeloBuscar.setTraumaticos(result.getString(7));
                modeloBuscar.setAlergicos(result.getString(8));
                modeloBuscar.setVicios(result.getString(9));
                modeloBuscar.setMenarquia(result.getString(10));
                modeloBuscar.setFur(result.getString(11));
                modeloBuscar.setG(result.getString(12));
                modeloBuscar.setP(result.getString(13));
                modeloBuscar.setCstp(result.getString(14));
                modeloBuscar.setHv(result.getString(15));
                modeloBuscar.setHm(result.getString(16));
                modeloBuscar.setAb(result.getString(17));
                modeloBuscar.setMpf(result.getString(18));
                modeloBuscar.setDiagnostico(result.getString(19));
                modeloBuscar.setEstado(result.getString(20));
                lista.add(modeloBuscar);
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
        return lista;
    }
    
    public AntecedentesMod buscarFila(String id) throws SQLException, ConnectException {
        
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        ResultSet result = null;

        AntecedentesMod modeloBuscar = null;

        String sql = "";

        sql = "SELECT * FROM ANTECEDENTES WHERE ANT_ID = ?";

        try {
            smt = conn.prepareStatement(sql);
            smt.setString(1, id);

            result = smt.executeQuery();

            while (result.next()) {
                modeloBuscar = new AntecedentesMod();

                modeloBuscar.setId(result.getString(1));
                modeloBuscar.setFamiliares(result.getString(2));
                modeloBuscar.setMedicos(result.getString(3));
                modeloBuscar.setTratamientos(result.getString(4));
                modeloBuscar.setLaboratorios(result.getString(5));
                modeloBuscar.setQuirurgicos(result.getString(6));
                modeloBuscar.setTraumaticos(result.getString(7));
                modeloBuscar.setAlergicos(result.getString(8));
                modeloBuscar.setVicios(result.getString(9));
                modeloBuscar.setMenarquia(result.getString(10));
                modeloBuscar.setFur(result.getString(11));
                modeloBuscar.setG(result.getString(12));
                modeloBuscar.setP(result.getString(13));
                modeloBuscar.setCstp(result.getString(14));
                modeloBuscar.setHv(result.getString(15));
                modeloBuscar.setHm(result.getString(16));
                modeloBuscar.setAb(result.getString(17));
                modeloBuscar.setMpf(result.getString(18));
                modeloBuscar.setDiagnostico(result.getString(19));
                modeloBuscar.setEstado(result.getString(20));
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
        return modeloBuscar;
    }
}
