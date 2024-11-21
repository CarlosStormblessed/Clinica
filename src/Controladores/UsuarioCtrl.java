package Controladores;
import Modelos.UsuarioMod;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Conexion.*;
import java.net.ConnectException;

public class UsuarioCtrl {
    public int Crear(UsuarioMod modelo) throws SQLException, ConnectException {
        
        int result = 0;
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        Encriptacion encript = new Encriptacion();
        try {

            String sql = "INSERT INTO USUARIO VALUES ((SELECT IFNULL(MAX(USR_ID), 0)+1 FROM USUARIO u), ?,?,?,?,?)";
            conn.setAutoCommit(false);
            smt = conn.prepareStatement(sql);
            smt.setString(1, modelo.getUsuario());
            smt.setString(2, modelo.getNombre());
            smt.setString(3, encript.encriptar(modelo.getPassword()));
            smt.setString(4, modelo.getRol());
            smt.setString(5, modelo.getEstado());
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

        UsuarioMod modeloBuscar = null;

        String sql = "SELECT IFNULL(MAX(USR_ID), 0) FROM USUARIO";
        try {
            smt = conn.prepareStatement(sql);
            result = smt.executeQuery();

            while (result.next()) {
                modeloBuscar = new UsuarioMod();

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
    
    public int Actualizar(UsuarioMod modelo) throws SQLException, ConnectException{
        
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        Encriptacion encript = new Encriptacion();
         int result = 0;
        try {
            String sql = "UPDATE USUARIO SET USR_USUARIO = ?, USR_NOMBRE = ?, USR_PASSWORD = ?, USR_ROL = ? WHERE USR_ID = ?";
            conn.setAutoCommit(false);

            smt = conn.prepareStatement(sql);

            smt.setString(1, modelo.getUsuario());
            smt.setString(2, modelo.getNombre());
            smt.setString(3, encript.encriptar(modelo.getPassword()));
            smt.setString(4, modelo.getRol());
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
    
    public int Eliminar(UsuarioMod modelo) throws ConnectException, SQLException{
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
         int result = 0;
        try {
            String sql = "UPDATE USUARIO SET USR_ESTADO = 0 WHERE USR_ID = ?";
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
    
    public List<UsuarioMod> seleccionarTodos() throws SQLException, ConnectException {
        
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        Encriptacion encript = new Encriptacion();
        ResultSet result = null;
        List<UsuarioMod> lista = new ArrayList<UsuarioMod>();

        UsuarioMod modeloBuscar = null;

        String sql = "";

        sql = "SELECT * FROM USUARIO WHERE USR_ESTADO = 1 ORDER BY USR_ID ASC";
        //+ filtro;

        try {
            
            smt = conn.prepareStatement(sql);

            result = smt.executeQuery();

            while (result.next()) {
                modeloBuscar = new UsuarioMod();

                modeloBuscar.setId(result.getString(1));
                modeloBuscar.setUsuario(result.getString(2));
                modeloBuscar.setNombre(result.getString(3));
                modeloBuscar.setPassword(encript.desencriptar(result.getString(4)));
                modeloBuscar.setRol(result.getString(5));
                modeloBuscar.setEstado(result.getString(6));
                
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
    
    public List<UsuarioMod> seleccionarTodosUsuarios() throws SQLException, ConnectException {
        
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        Encriptacion encript = new Encriptacion();
        ResultSet result = null;
        List<UsuarioMod> lista = new ArrayList<UsuarioMod>();

        UsuarioMod modeloBuscar = null;

        String sql = "";

        sql = "SELECT * FROM USUARIO WHERE USR_ESTADO = 1 AND USR_ROL NOT LIKE 'admin' ORDER BY USR_ID ASC;";
        //+ filtro;

        try {
            
            smt = conn.prepareStatement(sql);

            result = smt.executeQuery();

            while (result.next()) {
                modeloBuscar = new UsuarioMod();

                modeloBuscar.setId(result.getString(1));
                modeloBuscar.setUsuario(result.getString(2));
                modeloBuscar.setNombre(result.getString(3));
                modeloBuscar.setPassword(encript.desencriptar(result.getString(4)));
                modeloBuscar.setRol(result.getString(5));
                modeloBuscar.setEstado(result.getString(6));
                
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
    
    /**
     * Método que cuenta los usuarios activos que tengan el rol 'admin' o 'medico'
     * @param opcion 1: Contar administradores. 2: Contar personal médico.
     * @return Número total de usuarios. -1 si se obtuvo una opción no válida.
     * @throws SQLException
     * @throws ConnectException 
     */
    public int contarUsuarios(int opcion) throws SQLException, ConnectException{
        int usuarios = 0;
        String sql = "";
        switch (opcion){
            case 1:
                sql = "SELECT COUNT(*) FROM USUARIO WHERE USR_ROL = 'admin' AND USR_ESTADO = 1";
                break;
            case 2:
                sql = "SELECT COUNT(*) FROM USUARIO WHERE USR_ROL = 'medico' AND USR_ESTADO = 1";
                break;
            default:
                return -1;
        }
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        Encriptacion encript = new Encriptacion();
        ResultSet result = null;
        try {
            smt = conn.prepareStatement(sql);

            result = smt.executeQuery();

            while (result.next()) {
                usuarios = result.getInt(1);
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
        return usuarios;
    }
    
    public UsuarioMod buscarFila(String id) throws SQLException, ConnectException {
        
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        Encriptacion encript = new Encriptacion();
        ResultSet result = null;

        UsuarioMod modeloBuscar = null;

        String sql = "";

        sql = "SELECT * FROM USUARIO WHERE USR_ID = ? AND USR_ESTADO = 1";

        try {
            smt = conn.prepareStatement(sql);
            smt.setString(1, id);

            result = smt.executeQuery();

            while (result.next()) {
                modeloBuscar = new UsuarioMod();

                modeloBuscar.setId(result.getString(1));
                modeloBuscar.setUsuario(result.getString(2));
                modeloBuscar.setNombre(result.getString(3));
                modeloBuscar.setPassword(encript.desencriptar(result.getString(4)));
                modeloBuscar.setRol(result.getString(5));
                modeloBuscar.setEstado(result.getString(6));
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
