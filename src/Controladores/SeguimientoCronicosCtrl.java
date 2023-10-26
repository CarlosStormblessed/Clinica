package Controladores;
import Modelos.SeguimientoCronicosMod;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Conexion.Conexion;
import java.net.ConnectException;

public class SeguimientoCronicosCtrl {
    public int Crear(SeguimientoCronicosMod modelo) throws SQLException, ConnectException {
        
        int result = 0;
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        try {
            String sql = "INSERT INTO SEGUIMIENTO_CRONICOS VALUES ((SELECT IFNULL(MAX(SEGCRO_ID), 0)+1 FROM SEGUIMIENTO_CRONICOS a), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            conn.setAutoCommit(false);
            smt = conn.prepareStatement(sql);
            smt.setString(1, modelo.getClinicaId());
            smt.setString(2, modelo.getFecha() + " " + modelo.getHora());
            smt.setString(3, modelo.getEmpleadoId());
            smt.setString(4, modelo.getEdad());
            smt.setString(5, modelo.getArea());
            smt.setString(6, modelo.getPuesto());
            smt.setString(7, modelo.getDatosSubjetivos());
            smt.setString(8, modelo.getTratamiento());
            smt.setString(9, modelo.getReferencia());
            smt.setString(10, modelo.getTraslado());
            smt.setString(11, modelo.getRevisionSistemasId());
            smt.setString(12, modelo.getResponsable());
            smt.setString(13, modelo.getEstado());
            

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

        SeguimientoCronicosMod modeloBuscar = null;

        String sql = "SELECT IFNULL(MAX(SEGCRO_ID), 0) FROM SEGUIMIENTO_CRONICOS";
        try {
            smt = conn.prepareStatement(sql);
            result = smt.executeQuery();

            while (result.next()) {
                modeloBuscar = new SeguimientoCronicosMod();

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
    
    public int Actualizar(SeguimientoCronicosMod modelo) throws SQLException, ConnectException{
        
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
         int result = 0;
        try {
            String sql = "UPDATE SEGUIMIENTO_CRONICOS SET SEGCRO_EDAD = ?, SEGCRO_AREA = ?, SEGCRO_PUESTO = ?, SEGCRO_DATOSSUB = ?, SEGCRO_TRATAMIENTO = ?, SEGCRO_REFERENCIA = ?, SEGCRO_TRASLADO = ? WHERE SEGCRO_ID = ?";
            conn.setAutoCommit(false);

            smt = conn.prepareStatement(sql);

            smt = conn.prepareStatement(sql);
            smt.setString(1, modelo.getEdad());
            smt.setString(2, modelo.getArea());
            smt.setString(3, modelo.getPuesto());
            smt.setString(4, modelo.getDatosSubjetivos());
            smt.setString(5, modelo.getTratamiento());
            smt.setString(6, modelo.getReferencia());
            smt.setString(7, modelo.getTraslado());
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
    
    public int Eliminar(SeguimientoCronicosMod modelo) throws ConnectException, SQLException{
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        int result = 0;
        try {
            String sql = "UPDATE SEGUIMIENTO_CRONICOS SET SEGCRO_ESTADO = 0 WHERE SEGCRO_ID = ?";
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
    
    public List<SeguimientoCronicosMod> seleccionarTodos() throws SQLException, ConnectException {
        
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        ResultSet result = null;
        List<SeguimientoCronicosMod> lista = new ArrayList<SeguimientoCronicosMod>();

        SeguimientoCronicosMod modeloBuscar = null;

        String sql = "";

        sql = "select  * FROM SEGUIMIENTO_CRONICOS WHERE SEGCRO_ESTADO = 1 ORDER BY SEGCRO_ID ASC";
        //+ filtro;

        try {
            
            smt = conn.prepareStatement(sql);

            result = smt.executeQuery();

            while (result.next()) {
                modeloBuscar = new SeguimientoCronicosMod();

                modeloBuscar.setId(result.getString(1));
                modeloBuscar.setClinicaId(result.getString(2));
                String fechaHora = result.getString(3);
                String[] partesFechaHora = fechaHora.split(" ");
                modeloBuscar.setFecha(partesFechaHora[0]);
                modeloBuscar.setHora(partesFechaHora[1]);
                modeloBuscar.setEmpleadoId(result.getString(4));
                modeloBuscar.setEdad(result.getString(5));
                modeloBuscar.setArea(result.getString(6));
                modeloBuscar.setPuesto(result.getString(7));
                modeloBuscar.setDatosSubjetivos(result.getString(8));
                modeloBuscar.setTratamiento(result.getString(9));
                modeloBuscar.setReferencia(result.getString(10));
                modeloBuscar.setTraslado(result.getString(11));
                modeloBuscar.setRevisionSistemasId(result.getString(12));
                modeloBuscar.setResponsable(result.getString(13));
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
    
    public SeguimientoCronicosMod buscarFila(String id) throws SQLException, ConnectException {
        
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        ResultSet result = null;

        SeguimientoCronicosMod modeloBuscar = null;

        String sql = "";

        sql = "SELECT * FROM SEGUIMIENTO_CRONICOS WHERE SEGCRO_ID = ?";

        try {
            smt = conn.prepareStatement(sql);
            smt.setString(1, id);

            result = smt.executeQuery();

            while (result.next()) {
                modeloBuscar = new SeguimientoCronicosMod();

                modeloBuscar.setId(result.getString(1));
                modeloBuscar.setClinicaId(result.getString(2));
                String fechaHora = result.getString(3);
                String[] partesFechaHora = fechaHora.split(" ");
                modeloBuscar.setFecha(partesFechaHora[0]);
                modeloBuscar.setHora(partesFechaHora[1]);
                modeloBuscar.setEmpleadoId(result.getString(4));
                modeloBuscar.setEdad(result.getString(5));
                modeloBuscar.setArea(result.getString(6));
                modeloBuscar.setPuesto(result.getString(7));
                modeloBuscar.setDatosSubjetivos(result.getString(8));
                modeloBuscar.setTratamiento(result.getString(9));
                modeloBuscar.setReferencia(result.getString(10));
                modeloBuscar.setTraslado(result.getString(11));
                modeloBuscar.setRevisionSistemasId(result.getString(12));
                modeloBuscar.setResponsable(result.getString(13));
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
