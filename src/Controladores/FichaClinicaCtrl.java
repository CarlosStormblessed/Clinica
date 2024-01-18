package Controladores;
import Modelos.FichaClinicaMod;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Conexion.Conexion;
import java.net.ConnectException;
//SQL: CONSULTA_GENERAL (CONGEN)
public class FichaClinicaCtrl {
    public int Crear(FichaClinicaMod modelo) throws SQLException, ConnectException {
        
        int result = 0;
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        try {

            String sql = "INSERT INTO CONSULTA_GENERAL VALUES ((SELECT IFNULL(MAX(CONGEN_ID), 0)+1 FROM CONSULTA_GENERAL c), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            conn.setAutoCommit(false);
            smt = conn.prepareStatement(sql);
            smt.setString(1, modelo.getFecha() + " " + modelo.getHora());
            smt.setString(2, modelo.getEdad());
            smt.setString(3, modelo.getArea());
            smt.setString(4, modelo.getPuesto());
            smt.setString(5, modelo.getMotivo());
            smt.setString(6, modelo.getHistoria());
            smt.setString(7, modelo.getTratamiento());
            smt.setString(8, modelo.getReferencia());
            smt.setString(9, modelo.getTraslado());
            smt.setString(10, modelo.getSuspension());
            smt.setString(11, modelo.getClinicaId());
            smt.setString(12, modelo.getEmpleadoId());
            smt.setString(13, modelo.getRevisionSistemasId());
            smt.setString(14, modelo.getResponsable());
            smt.setString(15, modelo.getRealizado());
            smt.setString(16, modelo.getRevisado());
            smt.setString(17, modelo.getAutorizado());
            smt.setString(18, modelo.getEstado());
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
    
    public String getMaxId() throws SQLException, ConnectException{
        String id = "";
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        ResultSet result = null;

        FichaClinicaMod modeloBuscar = null;

        String sql = "SELECT IFNULL(MAX(CONGEN_ID), 0) FROM CONSULTA_GENERAL";
        try {
            smt = conn.prepareStatement(sql);
            result = smt.executeQuery();

            while (result.next()) {
                modeloBuscar = new FichaClinicaMod();

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
    
    public int Actualizar(FichaClinicaMod modelo) throws SQLException, ConnectException{
        
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
         int result = 0;
        try {
            String sql = "UPDATE CONSULTA_GENERAL SET CONGEN_FECHAHORA = ?, CONGEN_HISTORIA = ?, CONGEN_TRATAMIENTO = ?, CONGEN_REFERENCIA = ?, CONGEN_TRASLADO = ?, CONGEN_SUSPENSION = ?, CONGEN_RESPONSABLE = ? WHERE CONGEN_ID = ?";
            conn.setAutoCommit(false);

            smt = conn.prepareStatement(sql);
            smt.setString(1, modelo.getFecha() + " " + modelo.getHora());
            smt.setString(2, modelo.getHistoria());
            smt.setString(3, modelo.getTratamiento());
            smt.setString(4, modelo.getReferencia());
            smt.setString(5, modelo.getTraslado());
            smt.setString(6, modelo.getSuspension());
            smt.setString(7, modelo.getResponsable());
            smt.setString(8, modelo.getId());
            
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
    
    public int Eliminar(FichaClinicaMod modelo) throws ConnectException, SQLException{
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
         int result = 0;
        try {
            String sql = "UPDATE CONSULTA_GENERAL SET CONGEN_ESTADO = 0 WHERE CONGEN_ID = ?";
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
    
    public List<FichaClinicaMod> seleccionarTodos() throws SQLException, ConnectException {
        
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        ResultSet result = null;
        List<FichaClinicaMod> lista = new ArrayList<FichaClinicaMod>();

        FichaClinicaMod modeloBuscar = null;

        String sql = "";

        sql = "SELECT * FROM CONSULTA_GENERAL WHERE CONGEN_ESTADO = 1 ORDER BY CONGEN_ID ASC";
        //+ filtro;

        try {
            
            smt = conn.prepareStatement(sql);

            result = smt.executeQuery();

            while (result.next()) {
                modeloBuscar = new FichaClinicaMod();

                modeloBuscar.setId(result.getString(1));
                String fechaHora = result.getString(2);
                String[] partesFechaHora = fechaHora.split(" ");
                modeloBuscar.setFecha(partesFechaHora[0]);
                modeloBuscar.setHora(partesFechaHora[1]);
                modeloBuscar.setEdad(result.getString(3));
                modeloBuscar.setArea(result.getString(4));
                modeloBuscar.setPuesto(result.getString(5));
                modeloBuscar.setMotivo(result.getString(6));
                modeloBuscar.setHistoria(result.getString(7));
                modeloBuscar.setTratamiento(result.getString(8));
                modeloBuscar.setReferencia(result.getString(9));
                modeloBuscar.setTraslado(result.getString(10));
                modeloBuscar.setSuspension(result.getString(11));
                modeloBuscar.setClinicaId(result.getString(12));
                modeloBuscar.setEmpleadoId(result.getString(13));
                modeloBuscar.setRevisionSistemasId(result.getString(14));
                modeloBuscar.setResponsable(result.getString(15));
                modeloBuscar.setRealizado(result.getString(16));
                modeloBuscar.setRevisado(result.getString(17));
                modeloBuscar.setAutorizado(result.getString(18));
                modeloBuscar.setEstado(result.getString(19));
                
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
    
    public FichaClinicaMod buscarFila(String id) throws SQLException, ConnectException {
        
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        ResultSet result = null;

        FichaClinicaMod modeloBuscar = null;

        String sql = "";

        sql = "SELECT * FROM CONSULTA_GENERAL where CONGEN_ID = ? AND CONGEN_ESTADO = 1";

        try {
            smt = conn.prepareStatement(sql);
            smt.setString(1, id);

            result = smt.executeQuery();

            while (result.next()) {
                modeloBuscar = new FichaClinicaMod();

                modeloBuscar.setId(result.getString(1));
                String fechaHora = result.getString(2);
                String[] partesFechaHora = fechaHora.split(" ");
                modeloBuscar.setFecha(partesFechaHora[0]);
                modeloBuscar.setHora(partesFechaHora[1]);
                modeloBuscar.setEdad(result.getString(3));
                modeloBuscar.setArea(result.getString(4));
                modeloBuscar.setPuesto(result.getString(5));
                modeloBuscar.setMotivo(result.getString(6));
                modeloBuscar.setHistoria(result.getString(7));
                modeloBuscar.setTratamiento(result.getString(8));
                modeloBuscar.setReferencia(result.getString(9));
                modeloBuscar.setTraslado(result.getString(10));
                modeloBuscar.setSuspension(result.getString(11));
                modeloBuscar.setClinicaId(result.getString(12));
                modeloBuscar.setEmpleadoId(result.getString(13));
                modeloBuscar.setRevisionSistemasId(result.getString(14));
                modeloBuscar.setResponsable(result.getString(15));
                modeloBuscar.setRealizado(result.getString(16));
                modeloBuscar.setRevisado(result.getString(17));
                modeloBuscar.setAutorizado(result.getString(18));
                modeloBuscar.setEstado(result.getString(19));            }
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
