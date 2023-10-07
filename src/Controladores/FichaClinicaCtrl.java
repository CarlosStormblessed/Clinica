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

            String sql = "INSERT INTO CONSULTA_GENERAL VALUES ((SELECT IFNULL(MAX(CONGEN_ID), 0)+1 FROM CONSULTA_GENERAL c), ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,1)";
            conn.setAutoCommit(false);
            smt = conn.prepareStatement(sql);
            smt.setString(1, modelo.getClinica_id());
            smt.setString(2, modelo.getEmpleado_id());
            smt.setString(3, modelo.getFecha() + " " + modelo.getHora());
            smt.setString(4, modelo.getEdad());
            smt.setString(5, modelo.getArea());
            smt.setString(6, modelo.getPuesto());
            smt.setString(7, modelo.getTemperatura());
            smt.setString(8, modelo.getPulso());
            smt.setString(9, modelo.getSpo2());
            smt.setString(10, modelo.getFr());
            smt.setString(11, modelo.getPa());
            smt.setString(12, modelo.getGlicemia());
            smt.setString(13, modelo.getPeso());
            smt.setString(14, modelo.getTalla());
            smt.setString(15, modelo.getImc());
            smt.setString(16, modelo.getMotivo());
            smt.setString(17, modelo.getHistoriaActual());
            smt.setString(18, modelo.getExamenHallazgos());
            smt.setString(19, modelo.getImpresionClinica());
            smt.setString(20, modelo.getTratamiento());
            smt.setString(21, modelo.getReferencia());
            smt.setString(22, modelo.getTraslado());
            smt.setString(23, modelo.getPatologia());
            smt.setString(24, modelo.getObservaciones());
            smt.setString(25, modelo.getResponsable());
            smt.setString(26, modelo.getRealizado());
            smt.setString(27, modelo.getRevisado());
            smt.setString(28, modelo.getAutorizado());
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
    
    public int Actualizar(FichaClinicaMod modelo) throws SQLException, ConnectException{
        
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
         int result = 0;
        try {
            String sql = "UPDATE CONSULTA_GENERAL SET CONGEN_TEMPERATURA = ?, CONGEN_PULSO = ?, CONGEN_SPO2 = ?, CONGEN_FR = ?, CONGEN_PA = ?, CONGEN_GLICEMIA = ?, CONGEN_PESO = ?, CONGEN_TALLA = ?, CONGEN_IMC = ?, CONGEN_MOTIVO = ?, CONGEN_HISTORIAACTUAL = ?, CONGEN_EXAMENHALLAZGOS = ?, CONGEN_IMPRESIONCLINICA = ?, CONGEN_TRATAMIENTO = ?, CONGEN_REFERENCIA = ?, CONGEN_TRASLADO = ?, CONGEN_PATOLOGIA = ?, CONGEN_OBSERVACIONES = ?, CONGEN_RESPONSABLE = ?, CONGEN_REALIZADO = ?, CONGEN_REVISADO = ?, CONGEN_AUTORIZADO = ? WHERE CONGEN_ID = ?";
            conn.setAutoCommit(false);

            smt = conn.prepareStatement(sql);

            smt.setString(1, modelo.getTemperatura());
            smt.setString(2, modelo.getPulso());
            smt.setString(3, modelo.getSpo2());
            smt.setString(4, modelo.getFr());
            smt.setString(5, modelo.getPa());
            smt.setString(6, modelo.getGlicemia());
            smt.setString(7, modelo.getPeso());
            smt.setString(8, modelo.getTalla());
            smt.setString(9, modelo.getImc());
            smt.setString(10, modelo.getMotivo());
            smt.setString(11, modelo.getHistoriaActual());
            smt.setString(12, modelo.getExamenHallazgos());
            smt.setString(13, modelo.getImpresionClinica());
            smt.setString(14, modelo.getTratamiento());
            smt.setString(15, modelo.getReferencia());
            smt.setString(16, modelo.getTraslado());
            smt.setString(17, modelo.getPatologia());
            smt.setString(18, modelo.getObservaciones());
            smt.setString(19, modelo.getResponsable());
            smt.setString(20, modelo.getRealizado());
            smt.setString(21, modelo.getRevisado());
            smt.setString(22, modelo.getAutorizado());
            smt.setString(23, modelo.getId());
            
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
                modeloBuscar.setClinica_id(result.getString(2));
                modeloBuscar.setEmpleado_id(result.getString(3));
                String fechaHora = result.getString(4);
                String[] partesFechaHora = fechaHora.split(" ");
                modeloBuscar.setFecha(partesFechaHora[0]);
                modeloBuscar.setHora(partesFechaHora[1]);
                modeloBuscar.setEdad(result.getString(5));
                modeloBuscar.setArea(result.getString(6));
                modeloBuscar.setPuesto(result.getString(7));
                modeloBuscar.setTemperatura(result.getString(8));
                modeloBuscar.setPulso(result.getString(9));
                modeloBuscar.setSpo2(result.getString(10));
                modeloBuscar.setFr(result.getString(11));
                modeloBuscar.setPa(result.getString(12));
                modeloBuscar.setGlicemia(result.getString(13));
                modeloBuscar.setPeso(result.getString(14));
                modeloBuscar.setTalla(result.getString(15));
                modeloBuscar.setImc(result.getString(16));
                modeloBuscar.setMotivo(result.getString(17));
                modeloBuscar.setHistoriaActual(result.getString(18));
                modeloBuscar.setExamenHallazgos(result.getString(19));
                modeloBuscar.setImpresionClinica(result.getString(20));
                modeloBuscar.setTratamiento(result.getString(21));
                modeloBuscar.setReferencia(result.getString(22));
                modeloBuscar.setTraslado(result.getString(23));
                modeloBuscar.setPatologia(result.getString(24));
                modeloBuscar.setObservaciones(result.getString(25));
                modeloBuscar.setResponsable(result.getString(26));
                modeloBuscar.setRealizado(result.getString(27));
                modeloBuscar.setRevisado(result.getString(28));
                modeloBuscar.setAutorizado(result.getString(29));
                modeloBuscar.setEstado(result.getString(30));
                
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
                modeloBuscar.setClinica_id(result.getString(2));
                modeloBuscar.setEmpleado_id(result.getString(3));
                String fechaHora = result.getString(4);
                String[] partesFechaHora = fechaHora.split(" ");
                modeloBuscar.setFecha(partesFechaHora[0]);
                modeloBuscar.setHora(partesFechaHora[1]);
                modeloBuscar.setEdad(result.getString(5));
                modeloBuscar.setArea(result.getString(6));
                modeloBuscar.setPuesto(result.getString(7));
                modeloBuscar.setTemperatura(result.getString(8));
                modeloBuscar.setPulso(result.getString(9));
                modeloBuscar.setSpo2(result.getString(10));
                modeloBuscar.setFr(result.getString(11));
                modeloBuscar.setPa(result.getString(12));
                modeloBuscar.setGlicemia(result.getString(13));
                modeloBuscar.setPeso(result.getString(14));
                modeloBuscar.setTalla(result.getString(15));
                modeloBuscar.setImc(result.getString(16));
                modeloBuscar.setMotivo(result.getString(17));
                modeloBuscar.setHistoriaActual(result.getString(18));
                modeloBuscar.setExamenHallazgos(result.getString(19));
                modeloBuscar.setImpresionClinica(result.getString(20));
                modeloBuscar.setTratamiento(result.getString(21));
                modeloBuscar.setReferencia(result.getString(22));
                modeloBuscar.setTraslado(result.getString(23));
                modeloBuscar.setPatologia(result.getString(24));
                modeloBuscar.setObservaciones(result.getString(25));
                modeloBuscar.setResponsable(result.getString(26));
                modeloBuscar.setRealizado(result.getString(27));
                modeloBuscar.setRevisado(result.getString(28));
                modeloBuscar.setAutorizado(result.getString(29));
                modeloBuscar.setEstado(result.getString(30));
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
