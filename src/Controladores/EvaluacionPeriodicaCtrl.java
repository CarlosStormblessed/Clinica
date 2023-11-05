package Controladores;
import Modelos.EvaluacionPeriodicaMod;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Conexion.Conexion;
import java.net.ConnectException;

public class EvaluacionPeriodicaCtrl {
    public int Crear(EvaluacionPeriodicaMod modelo) throws SQLException, ConnectException {
        
        int result = 0;
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        try {
            //String sql = "INSERT INTO EVALUACION_PERIODICA VALUES ((SELECT IFNULL(MAX(EVAPER_ID), 0)+1 FROM EVALUACION_PERIODICA a), '?','?','?','?','?','?','?','?',?,'?','?','?','?','?','?',?,?,?,'?',?)";
            String sql = "INSERT INTO EVALUACION_PERIODICA VALUES ((SELECT IFNULL(MAX(EVAPER_ID), 0)+1 FROM EVALUACION_PERIODICA a), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            conn.setAutoCommit(false);
            smt = conn.prepareStatement(sql);
            smt.setString(1, modelo.getFecha());
            smt.setString(2, modelo.getHora());
            smt.setString(3, modelo.getEdad());
            smt.setString(4, modelo.getArea());
            smt.setString(5, modelo.getPuesto());
            smt.setString(6, modelo.getAptitud());
            smt.setString(7, modelo.getEmpleadoId());
            smt.setString(8, modelo.getClinicaId());
            smt.setString(9, modelo.getAntecedentesId());
            smt.setString(10, modelo.getRevisionSistemasId());
            smt.setString(11, modelo.getResponsable());
            smt.setString(12, modelo.getEstado());
            

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

        EvaluacionPeriodicaMod modeloBuscar = null;

        String sql = "SELECT IFNULL(MAX(EVAPER_ID), 0) FROM EVALUACION_PERIODICA";
        try {
            smt = conn.prepareStatement(sql);
            result = smt.executeQuery();

            while (result.next()) {
                modeloBuscar = new EvaluacionPeriodicaMod();

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
    
    public int Actualizar(EvaluacionPeriodicaMod modelo) throws SQLException, ConnectException{
        
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
         int result = 0;
        try {
            String sql = "UPDATE EVALUACION_PERIODICA SET EVAPER_FECHA = ?, EVAPER_HORA = ?, EVAPER_EDAD = ?, EVAPER_AREA = ?, EVAPER_PUESTO = ?, EVAPER_APTITUD = ?, EVAPER_EMP_ID = ?, EVAPER_CLI_ID = ?, EVAPER_ANT_ID = ?, EVAPER_REVSIS_ID = ?, EVAPER_RESPONSABLE = ?, EVAPER_ESTADO = ? WHERE EVAPER_ID = ?";
            conn.setAutoCommit(false);

            smt = conn.prepareStatement(sql);

            smt = conn.prepareStatement(sql);
            smt.setString(1, modelo.getFecha());
            smt.setString(2, modelo.getHora());
            smt.setString(3, modelo.getEdad());
            smt.setString(4, modelo.getArea());
            smt.setString(5, modelo.getPuesto());
            smt.setString(6, modelo.getAptitud());
            smt.setString(7, modelo.getEmpleadoId());
            smt.setString(8, modelo.getClinicaId());
            smt.setString(9, modelo.getAntecedentesId());
            smt.setString(10, modelo.getRevisionSistemasId());
            smt.setString(11, modelo.getResponsable());
            smt.setString(12, modelo.getEstado());
            smt.setString(13, modelo.getId());
                        
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
    
    public int Eliminar(EvaluacionPeriodicaMod modelo) throws ConnectException, SQLException{
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        int result = 0;
        try {
            String sql = "UPDATE EVALUACION_PERIODICA SET EVAPER_ESTADO = 0 WHERE EVAPER_ID = ?";
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
    
    public List<EvaluacionPeriodicaMod> seleccionarTodos() throws SQLException, ConnectException {
        
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        ResultSet result = null;
        List<EvaluacionPeriodicaMod> lista = new ArrayList<EvaluacionPeriodicaMod>();

        EvaluacionPeriodicaMod modeloBuscar = null;

        String sql = "";

        sql = "select  * FROM EVALUACION_PERIODICA WHERE EVAPER_ESTADO = 1 ORDER BY EVAPER_ID ASC";
        //+ filtro;

        try {
            
            smt = conn.prepareStatement(sql);

            result = smt.executeQuery();

            while (result.next()) {
                modeloBuscar = new EvaluacionPeriodicaMod();

                modeloBuscar.setId(result.getString(1));
                modeloBuscar.setFecha(result.getString(2));
                modeloBuscar.setHora(result.getString(3));
                modeloBuscar.setEdad(result.getString(4));
                modeloBuscar.setArea(result.getString(5));
                modeloBuscar.setPuesto(result.getString(6));
                modeloBuscar.setAptitud(result.getString(7));
                modeloBuscar.setEmpleadoId(result.getString(8));
                modeloBuscar.setClinicaId(result.getString(9));
                modeloBuscar.setAntecedentesId(result.getString(10));
                modeloBuscar.setRevisionSistemasId(result.getString(11));
                modeloBuscar.setResponsable(result.getString(12));
                modeloBuscar.setEstado(result.getString(13));
                
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
    
    public EvaluacionPeriodicaMod buscarFila(String id) throws SQLException, ConnectException {
        
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        ResultSet result = null;

        EvaluacionPeriodicaMod modeloBuscar = null;

        String sql = "";

        sql = "SELECT * FROM EVALUACION_PERIODICA WHERE EVAPER_ID = ?";

        try {
            smt = conn.prepareStatement(sql);
            smt.setString(1, id);

            result = smt.executeQuery();

            while (result.next()) {
                modeloBuscar = new EvaluacionPeriodicaMod();

                modeloBuscar.setId(result.getString(1));
                modeloBuscar.setFecha(result.getString(2));
                modeloBuscar.setHora(result.getString(3));
                modeloBuscar.setEdad(result.getString(4));
                modeloBuscar.setArea(result.getString(5));
                modeloBuscar.setPuesto(result.getString(6));
                modeloBuscar.setAptitud(result.getString(7));
                modeloBuscar.setEmpleadoId(result.getString(8));
                modeloBuscar.setClinicaId(result.getString(9));
                modeloBuscar.setAntecedentesId(result.getString(10));
                modeloBuscar.setRevisionSistemasId(result.getString(11));
                modeloBuscar.setResponsable(result.getString(12));
                modeloBuscar.setEstado(result.getString(13));
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
