package Controladores;
import Modelos.RevisionSistemasMod;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Conexion.Conexion;
import java.net.ConnectException;
//SQL: REVISION_SISTEMAS (REVSIS)
public class RevisionSistemasCtrl {
    public int Crear(RevisionSistemasMod modelo) throws SQLException, ConnectException {
        
        int result = 0;
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        try {

            String sql = "INSERT INTO REVISION_SISTEMAS VALUES ((SELECT IFNULL(MAX(REVSIS_ID), 0)+1 FROM REVISION_SISTEMAS c), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            conn.setAutoCommit(false);
            smt = conn.prepareStatement(sql);
            smt.setString(1, modelo.getAlteraciones());
            smt.setString(2, modelo.getPielFaneras());
            smt.setString(3, modelo.getCabeza());
            smt.setString(4, modelo.getOjoOidoNarizBoca());
            smt.setString(5, modelo.getOrofarinje());
            smt.setString(6, modelo.getCuello());
            smt.setString(7, modelo.getCardiopulmonar());
            smt.setString(8, modelo.getTorax());
            smt.setString(9, modelo.getAbdomen());
            smt.setString(10, modelo.getGenitourinario());
            smt.setString(11, modelo.getExtremidades());
            smt.setString(12, modelo.getNeurologico());
            smt.setString(13, modelo.getTemperatura());
            smt.setString(14, modelo.getPulso());
            smt.setString(15, modelo.getSpo2());
            smt.setString(16, modelo.getFr());
            smt.setString(17, modelo.getPa());
            smt.setString(18, modelo.getGlicemia());
            smt.setString(19, modelo.getPeso());
            smt.setString(20, modelo.getTalla());
            smt.setString(21, modelo.getImc());
            smt.setString(22, modelo.getRuffier());
            smt.setString(23, modelo.getOjoDerecho());
            smt.setString(24, modelo.getOjoIzquierdo());
            smt.setString(25, modelo.getAnteojos());
            smt.setString(26, modelo.getImpresionClinica());
            smt.setString(27, modelo.getObservaciones());
            smt.setString(28, modelo.getEstado());
            
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

        RevisionSistemasMod modeloBuscar = null;

        String sql = "SELECT IFNULL(MAX(REVSIS_ID), 0) FROM REVISION_SISTEMAS";
        try {
            smt = conn.prepareStatement(sql);
            result = smt.executeQuery();

            while (result.next()) {
                modeloBuscar = new RevisionSistemasMod();

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
    
    public int Actualizar(RevisionSistemasMod modelo) throws SQLException, ConnectException{
        
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
         int result = 0;
        try {
            String sql = "UPDATE REVISION_SISTEMAS SET REVSIS_ALTERACIONES = ?, REVSIS_PIELFANERAS = ?, REVSIS_CABEZA = ?, REVSIS_OJOIDONARIZBOCA = ?, REVSIS_OROFARINGE = ?, REVSIS_CUELLO = ?, REVSIS_CARDIOPULMONAR = ?, REVSIS_TORAX = ?, REVSIS_ABDOMEN =?, REVSIS_GENITOURINARIO = ?, REVSIS_EXTREMIDADES = ?, REVSIS_NEUROLOGICO = ?, REVSIS_TEMPERATURA = ?, REVSIS_PULSO = ?, REVSIS_SPO2 = ?, REVSIS_FR = ?, REVSIS_PA = ?, REVSIS_GLICEMIA = ?, REVSIS_PESO = ?, REVSIS_TALLA = ?, REVSIS_IMC = ?, REVSIS_RUFFIER = ?, REVSIS_OJODERECHO = ?, REVSIS_OJOIZQUIERDO = ?, REVSIS_ANTEOJOS = ?, REVSIS_IMPRESIONCLINICA = ?, REVSIS_OBSERVACIONES = ? WHERE REVSIS_ID = ?";
            conn.setAutoCommit(false);

            smt = conn.prepareStatement(sql);

            smt.setString(1, modelo.getAlteraciones());
            smt.setString(2, modelo.getPielFaneras());
            smt.setString(3, modelo.getCabeza());
            smt.setString(4, modelo.getOjoOidoNarizBoca());
            smt.setString(5, modelo.getOrofarinje());
            smt.setString(6, modelo.getCuello());
            smt.setString(7, modelo.getCardiopulmonar());
            smt.setString(8, modelo.getTorax());
            smt.setString(9, modelo.getAbdomen());
            smt.setString(10, modelo.getGenitourinario());
            smt.setString(11, modelo.getExtremidades());
            smt.setString(12, modelo.getNeurologico());
            smt.setString(13, modelo.getTemperatura());
            smt.setString(14, modelo.getPulso());
            smt.setString(15, modelo.getSpo2());
            smt.setString(16, modelo.getFr());
            smt.setString(17, modelo.getPa());
            smt.setString(18, modelo.getGlicemia());
            smt.setString(19, modelo.getPeso());
            smt.setString(20, modelo.getTalla());
            smt.setString(21, modelo.getImc());
            smt.setString(22, modelo.getRuffier());
            smt.setString(23, modelo.getOjoDerecho());
            smt.setString(24, modelo.getOjoIzquierdo());
            smt.setString(25, modelo.getAnteojos());
            smt.setString(26, modelo.getImpresionClinica());
            smt.setString(27, modelo.getObservaciones());
            smt.setString(28, modelo.getId());
            
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
    
    public int Eliminar(RevisionSistemasMod modelo) throws ConnectException, SQLException{
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
         int result = 0;
        try {
            String sql = "UPDATE REVISION_SISTEMAS SET REVSIS_ESTADO = 0 WHERE REVSIS_ID = ?";
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
    
    public List<RevisionSistemasMod> seleccionarTodos() throws SQLException, ConnectException {
        
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        ResultSet result = null;
        List<RevisionSistemasMod> lista = new ArrayList<RevisionSistemasMod>();

        RevisionSistemasMod modeloBuscar = null;

        String sql = "";

        sql = "SELECT * FROM REVISION_SISTEMAS WHERE REVSIS_ESTADO = 1 ORDER BY REVSIS_ID ASC";
        //+ filtro;

        try {
            
            smt = conn.prepareStatement(sql);

            result = smt.executeQuery();

            while (result.next()) {
                modeloBuscar = new RevisionSistemasMod();

                modeloBuscar.setId(result.getString(1));
                modeloBuscar.setAlteraciones(result.getString(2));
                modeloBuscar.setPielFaneras(result.getString(3));
                modeloBuscar.setCabeza(result.getString(4));
                modeloBuscar.setOjoOidoNarizBoca(result.getString(5));
                modeloBuscar.setOrofarinje(result.getString(6));
                modeloBuscar.setCuello(result.getString(7));
                modeloBuscar.setCardiopulmonar(result.getString(8));
                modeloBuscar.setTorax(result.getString(9));
                modeloBuscar.setAbdomen(result.getString(10));
                modeloBuscar.setGenitourinario(result.getString(11));
                modeloBuscar.setExtremidades(result.getString(12));
                modeloBuscar.setNeurologico(result.getString(13));
                modeloBuscar.setTemperatura(result.getString(14));
                modeloBuscar.setPulso(result.getString(15));
                modeloBuscar.setSpo2(result.getString(16));
                modeloBuscar.setFr(result.getString(17));
                modeloBuscar.setPa(result.getString(18));
                modeloBuscar.setGlicemia(result.getString(19));
                modeloBuscar.setPeso(result.getString(20));
                modeloBuscar.setTalla(result.getString(21));
                modeloBuscar.setImc(result.getString(22));
                modeloBuscar.setRuffier(result.getString(23));
                modeloBuscar.setOjoDerecho(result.getString(24));
                modeloBuscar.setOjoIzquierdo(result.getString(25));
                modeloBuscar.setAnteojos(result.getString(26));
                modeloBuscar.setImpresionClinica(result.getString(27));
                modeloBuscar.setObservaciones(result.getString(28));
                modeloBuscar.setEstado(result.getString(29));
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
    
    public RevisionSistemasMod buscarFila(String id) throws SQLException, ConnectException {
        
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        ResultSet result = null;

        RevisionSistemasMod modeloBuscar = null;

        String sql = "";

        sql = "SELECT * FROM REVISION_SISTEMAS where REVSIS_ID = ? AND REVSIS_ESTADO = 1";

        try {
            smt = conn.prepareStatement(sql);
            smt.setString(1, id);

            result = smt.executeQuery();

            while (result.next()) {
                modeloBuscar = new RevisionSistemasMod();

                modeloBuscar.setId(result.getString(1));
                modeloBuscar.setAlteraciones(result.getString(2));
                modeloBuscar.setPielFaneras(result.getString(3));
                modeloBuscar.setCabeza(result.getString(4));
                modeloBuscar.setOjoOidoNarizBoca(result.getString(5));
                modeloBuscar.setOrofarinje(result.getString(6));
                modeloBuscar.setCuello(result.getString(7));
                modeloBuscar.setCardiopulmonar(result.getString(8));
                modeloBuscar.setTorax(result.getString(9));
                modeloBuscar.setAbdomen(result.getString(10));
                modeloBuscar.setGenitourinario(result.getString(11));
                modeloBuscar.setExtremidades(result.getString(12));
                modeloBuscar.setNeurologico(result.getString(13));
                modeloBuscar.setTemperatura(result.getString(14));
                modeloBuscar.setPulso(result.getString(15));
                modeloBuscar.setSpo2(result.getString(16));
                modeloBuscar.setFr(result.getString(17));
                modeloBuscar.setPa(result.getString(18));
                modeloBuscar.setGlicemia(result.getString(19));
                modeloBuscar.setPeso(result.getString(20));
                modeloBuscar.setTalla(result.getString(21));
                modeloBuscar.setImc(result.getString(22));
                modeloBuscar.setRuffier(result.getString(23));
                modeloBuscar.setOjoDerecho(result.getString(24));
                modeloBuscar.setOjoIzquierdo(result.getString(25));
                modeloBuscar.setAnteojos(result.getString(26));
                modeloBuscar.setImpresionClinica(result.getString(27));
                modeloBuscar.setObservaciones(result.getString(28));
                modeloBuscar.setEstado(result.getString(29));
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
