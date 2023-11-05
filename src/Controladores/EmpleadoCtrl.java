package Controladores;
import Modelos.EmpleadoMod;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Conexion.Conexion;
import java.net.ConnectException;

public class EmpleadoCtrl {
    
    public int Crear(EmpleadoMod modelo) throws SQLException, ConnectException {
        
        int result = 0;
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        try {

            String sql = "INSERT INTO EMPLEADO VALUES ((SELECT IFNULL(MAX(EMP_ID), 0)+1 FROM EMPLEADO e), ?,?,?,?,?)";
            conn.setAutoCommit(false);
            smt = conn.prepareStatement(sql);
            smt.setString(1, modelo.getCodigo());
            smt.setString(2, modelo.getNombre());
            smt.setString(3, modelo.getSexo());
            smt.setString(4, modelo.getDireccion());
            smt.setString(5, modelo.getTelefono());

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

        EmpleadoMod modeloBuscar = null;

        String sql = "SELECT IFNULL(MAX(EMP_ID), 0) FROM EMPLEADO";
        try {
            smt = conn.prepareStatement(sql);
            result = smt.executeQuery();

            while (result.next()) {
                modeloBuscar = new EmpleadoMod();

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
    
    public int Actualizar(EmpleadoMod modelo) throws SQLException, ConnectException{
        
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
         int result = 0;
        try {
            String sql = "UPDATE EMPLEADO SET EMP_NOMBRE = ?, EMP_SEXO = ?, EMP_DIRECCION = ?, TELEFONO = ? WHERE EMP_ID = ?";
            conn.setAutoCommit(false);

            smt = conn.prepareStatement(sql);

            smt.setString(1, modelo.getNombre());
            smt.setString(2, modelo.getSexo());
            smt.setString(3, modelo.getDireccion());
            smt.setString(4, modelo.getTelefono());
            smt.setString(5, modelo.getId());
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
    
    public int Eliminar(EmpleadoMod modelo) throws ConnectException, SQLException{
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
         int result = 0;
        try {
            String sql = "UPDATE EMPLEADO SET EMP_ESTADO = 0 WHERE EMP_ID = ?";
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
    
    public List<EmpleadoMod> seleccionarTodos() throws SQLException, ConnectException {
        
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        ResultSet result = null;
        List<EmpleadoMod> lista = new ArrayList<EmpleadoMod>();

        EmpleadoMod modeloBuscar = null;

        String sql = "";

        sql = "select  * FROM EMPLEADO WHERE EMP_ESTADO = 1 ORDER BY EMP_ID ASC";
        //+ filtro;

        try {
            
            smt = conn.prepareStatement(sql);

            result = smt.executeQuery();

            while (result.next()) {
                modeloBuscar = new EmpleadoMod();

                modeloBuscar.setId(result.getString(1));
                modeloBuscar.setCodigo(result.getString(2));
                modeloBuscar.setNombre(result.getString(3));
                modeloBuscar.setSexo(result.getString(4));
                modeloBuscar.setDireccion(result.getString(5));
                modeloBuscar.setTelefono(result.getString(6));
                modeloBuscar.setEstado(result.getString(7));
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
    
    public EmpleadoMod buscarFila(String id) throws SQLException, ConnectException {
        
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        ResultSet result = null;

        EmpleadoMod modeloBuscar = null;

        String sql = "";

        sql = "select * FROM EMPLEADO where EMP_ID = ? AND EMP_ESTADO = 1";

        try {
            smt = conn.prepareStatement(sql);
            smt.setString(1, id);

            result = smt.executeQuery();

            while (result.next()) {
                modeloBuscar = new EmpleadoMod();

                modeloBuscar.setId(result.getString(1));
                modeloBuscar.setCodigo(result.getString(2));
                modeloBuscar.setNombre(result.getString(3));
                modeloBuscar.setSexo(result.getString(4));
                modeloBuscar.setDireccion(result.getString(5));
                modeloBuscar.setTelefono(result.getString(6));
                modeloBuscar.setEstado(result.getString(7));
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
