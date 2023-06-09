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
            String sql = "INSERT INTO ANTECEDENTES VALUES ((SELECT IFNULL(MAX(ANT_ID), 0)+1 FROM ANTECEDENTES a), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, 1)";
            System.out.println(modelo.getDiagnostico());
            conn.setAutoCommit(false);
            smt = conn.prepareStatement(sql);
            smt.setString(1, modelo.getMenarquia());
            smt.setString(2, modelo.getFur());
            smt.setString(3, modelo.getCstp());
            smt.setString(4, modelo.getMpf());
            smt.setString(5, modelo.getHv());
            smt.setString(6, modelo.getHm());
            smt.setString(7, modelo.getG());
            smt.setString(8, modelo.getP());
            smt.setString(9, modelo.getAb());
            smt.setString(10, modelo.getFecha());
            smt.setString(11, modelo.getEmpresa1());
            smt.setString(12, modelo.getEmpresa2());
            smt.setString(13, modelo.getEmpresa3());
            smt.setString(14, modelo.getPuesto1());
            smt.setString(15, modelo.getPuesto2());
            smt.setString(16, modelo.getPuesto3());
            smt.setString(17, modelo.getTiempolaborado1());
            smt.setString(18, modelo.getTiempolaborado2());
            smt.setString(19, modelo.getTiempolaborado3());
            smt.setString(20, modelo.getDiagnostico());

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

        String sql = "SELECT IFNULL(MAX(ANT_ID), 0) FROM ANTECEDENTES a WHERE ANT_ESTADO = 1";
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
            String sql = "UPDATE ANTECEDENTES SET ANT_MENARQUIA = ?, ANT_CSTP = ?, ANT_MPF = ?, ANT_HV = ?, ANT_HM = ?, ANT_G = ?, ANT_P = ?, ANT_AB = ?, ANT_FECHA = ?, ANT_EMPRESA1 = ?, ANT_EMPRESA2 = ?, ANT_EMPRESA3 = ?, ANT_PUESTO1 = ?, ANT_PUESTO2 = ?, ANT_PUESTO3 = ?, ANT_TIEMPOLABORADO1 = ?, ANT_TIEMPOLABORADO2 = ?, ANT_TIEMPOLABORADO3 = ?, ANT_DIAGNOSTICO = ?, ANT_EMP_ID = ? WHERE ANT_ID = ?";
            conn.setAutoCommit(false);

            smt = conn.prepareStatement(sql);

            smt = conn.prepareStatement(sql);
            smt.setString(1, modelo.getMenarquia());
            smt.setString(2, modelo.getCstp());
            smt.setString(3, modelo.getMpf());
            smt.setString(4, modelo.getHv());
            smt.setString(5, modelo.getHm());
            smt.setString(6, modelo.getG());
            smt.setString(7, modelo.getP());
            smt.setString(8, modelo.getAb());
            smt.setString(9, modelo.getFecha());
            smt.setString(10, modelo.getEmpresa1());
            smt.setString(11, modelo.getEmpresa2());
            smt.setString(12, modelo.getEmpresa3());
            smt.setString(13, modelo.getPuesto1());
            smt.setString(14, modelo.getPuesto2());
            smt.setString(15, modelo.getPuesto3());
            smt.setString(16, modelo.getTiempolaborado1());
            smt.setString(17, modelo.getTiempolaborado2());
            smt.setString(18, modelo.getTiempolaborado3());
            smt.setString(19, modelo.getDiagnostico());
            smt.setString(20, modelo.getEmpleadoId());
            smt.setString(21, modelo.getId());
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
                modeloBuscar.setMenarquia(result.getString(2));
                modeloBuscar.setFur(result.getString(3));
                modeloBuscar.setCstp(result.getString(4));
                modeloBuscar.setMpf(result.getString(5));
                modeloBuscar.setHv(result.getString(6));
                modeloBuscar.setHm(result.getString(7));
                modeloBuscar.setG(result.getString(8));
                modeloBuscar.setP(result.getString(9));
                modeloBuscar.setAb(result.getString(10));
                modeloBuscar.setFecha(result.getString(11));
                modeloBuscar.setEmpresa1(result.getString(12));
                modeloBuscar.setEmpresa2(result.getString(13));
                modeloBuscar.setEmpresa3(result.getString(14));
                modeloBuscar.setPuesto1(result.getString(15));
                modeloBuscar.setPuesto2(result.getString(16));
                modeloBuscar.setPuesto3(result.getString(17));
                modeloBuscar.setTiempolaborado1(result.getString(18));
                modeloBuscar.setTiempolaborado2(result.getString(19));
                modeloBuscar.setTiempolaborado3(result.getString(20));
                modeloBuscar.setDiagnostico(result.getString(21));
                modeloBuscar.setEmpleadoId(result.getString(22));
                modeloBuscar.setEstado(result.getString(23));
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
                modeloBuscar.setMenarquia(result.getString(2));
                modeloBuscar.setFur(result.getString(3));
                modeloBuscar.setCstp(result.getString(4));
                modeloBuscar.setMpf(result.getString(5));
                modeloBuscar.setHv(result.getString(6));
                modeloBuscar.setHm(result.getString(7));
                modeloBuscar.setG(result.getString(8));
                modeloBuscar.setP(result.getString(9));
                modeloBuscar.setAb(result.getString(10));
                modeloBuscar.setFecha(result.getString(11));
                modeloBuscar.setEmpresa1(result.getString(12));
                modeloBuscar.setEmpresa2(result.getString(13));
                modeloBuscar.setEmpresa3(result.getString(14));
                modeloBuscar.setPuesto1(result.getString(15));
                modeloBuscar.setPuesto2(result.getString(16));
                modeloBuscar.setPuesto3(result.getString(17));
                modeloBuscar.setTiempolaborado1(result.getString(18));
                modeloBuscar.setTiempolaborado2(result.getString(19));
                modeloBuscar.setTiempolaborado3(result.getString(20));
                modeloBuscar.setDiagnostico(result.getString(21));
                modeloBuscar.setEmpleadoId(result.getString(22));
                modeloBuscar.setEmpleadoId(result.getString(23));
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
