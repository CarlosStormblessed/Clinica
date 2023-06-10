package Controladores;
import Modelos.PreempleoMod;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Conexion.Conexion;
import java.net.ConnectException;

public class PreempleoCtrl {
    
    public int Crear(PreempleoMod modelo) throws SQLException, ConnectException {
        
        int result = 0;
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        try {

            String sql = "INSERT INTO PREEMPLEO VALUES ((SELECT IFNULL(MAX(PREEMP_ID), 0)+1 FROM PREEMPLEO p), ?,?,?,?,?,?,?,?,?,?,(SELECT CLI_ID FROM CLINICA WHERE CLI_ID = ?), (SELECT ANT_ID FROM ANTECEDENTES WHERE ANT_ID = ?), 1)";
            conn.setAutoCommit(false);
            smt = conn.prepareStatement(sql);
            smt.setString(1, modelo.getFecha());
            smt.setString(2, modelo.getNombre());
            smt.setString(3, modelo.getSexo());
            smt.setString(4, modelo.getIdentificacion());
            smt.setString(5, modelo.getEdad());
            smt.setString(6, modelo.getEstadoCivil());
            smt.setString(7, modelo.getDireccion());
            smt.setString(8, modelo.getTelefono());
            smt.setString(9, modelo.getNivelAcademico());
            smt.setString(10, modelo.getPuestoAplica());
            smt.setString(11, modelo.getClinicaId());
            smt.setString(12, modelo.getAntecedentesId());
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
    
    public int Actualizar(PreempleoMod modelo) throws SQLException, ConnectException{
        
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
         int result = 0;
        try {
            String sql = "UPDATE PREEMPLEO SET PREEMP_FECHA = ?, PREEMP_NOMBRE = ?, PREEMP_SEXO = ?, PREEMP_IDENTIFICACION = ?, PREEMP_EDAD = ?, PREEMP_CIVIL = ?, PREEMP_DIRECCION = ?, PREEMP_TELEFONO = ?, PREEMP_NIVELACADEMICO = ?, PREEMP_PUESTOAPLICA = ? WHERE PREEMP_ID = ?";
            conn.setAutoCommit(false);

            smt = conn.prepareStatement(sql);

            smt.setString(1, modelo.getFecha());
            smt.setString(2, modelo.getNombre());
            smt.setString(3, modelo.getSexo());
            smt.setString(4, modelo.getIdentificacion());
            smt.setString(5, modelo.getEdad());
            smt.setString(6, modelo.getEstadoCivil());
            smt.setString(7, modelo.getDireccion());
            smt.setString(8, modelo.getTelefono());
            smt.setString(9, modelo.getNivelAcademico());
            smt.setString(10, modelo.getPuestoAplica());
            smt.setString(11, modelo.getId());
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
    
    public int Eliminar(PreempleoMod modelo) throws ConnectException, SQLException{
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
         int result = 0;
        try {
            String sql = "UPDATE PREEMPLEO SET PREEMP_ESTADO = 0 WHERE PREEMP_ID = ?";
            conn.setAutoCommit(false);

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
     
    public List<PreempleoMod> seleccionarTodos() throws SQLException, ConnectException {
        
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        ResultSet result = null;
        List<PreempleoMod> lista = new ArrayList<PreempleoMod>();

        PreempleoMod modeloBuscar = null;

        String sql = "";

        sql = "select  * FROM PREEMPLEO ORDER BY PREEMP_ID ASC";
        //+ filtro;

        try {
            
            smt = conn.prepareStatement(sql);

            result = smt.executeQuery();

            while (result.next()) {
                modeloBuscar = new PreempleoMod();

                modeloBuscar.setId(result.getString(1));
                modeloBuscar.setFecha(result.getString(2));
                modeloBuscar.setNombre(result.getString(3));
                modeloBuscar.setSexo(result.getString(4));
                modeloBuscar.setIdentificacion(result.getString(5));
                modeloBuscar.setEdad(result.getString(6));
                modeloBuscar.setEstadoCivil(result.getString(7));
                modeloBuscar.setDireccion(result.getString(8));
                modeloBuscar.setTelefono(result.getString(9));
                modeloBuscar.setNivelAcademico(result.getString(10));
                modeloBuscar.setPuestoAplica(result.getString(11));
                modeloBuscar.setClinicaId(result.getString(12));
                modeloBuscar.setAntecedentesId(result.getString(13));
                modeloBuscar.setEstado(result.getString(14));
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
     
    public PreempleoMod buscarFila(String id) throws SQLException, ConnectException {
        
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        ResultSet result = null;

        PreempleoMod modeloBuscar = null;

        String sql = "";

        sql = "select * FROM PREEMPLEO where PREEMP_ID = ?";

        try {
            smt = conn.prepareStatement(sql);
            smt.setString(1, id);

            result = smt.executeQuery();

            while (result.next()) {
                modeloBuscar = new PreempleoMod();

                modeloBuscar.setId(result.getString(1));
                modeloBuscar.setFecha(result.getString(2));
                modeloBuscar.setNombre(result.getString(3));
                modeloBuscar.setSexo(result.getString(4));
                modeloBuscar.setIdentificacion(result.getString(5));
                modeloBuscar.setEdad(result.getString(6));
                modeloBuscar.setEstadoCivil(result.getString(7));
                modeloBuscar.setDireccion(result.getString(8));
                modeloBuscar.setTelefono(result.getString(9));
                modeloBuscar.setNivelAcademico(result.getString(10));
                modeloBuscar.setPuestoAplica(result.getString(11));
                modeloBuscar.setClinicaId(result.getString(12));
                modeloBuscar.setAntecedentesId(result.getString(13));
                modeloBuscar.setEstado(result.getString(14));
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
