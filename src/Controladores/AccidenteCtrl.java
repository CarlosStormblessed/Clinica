package Controladores;
import Modelos.AccidenteMod;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Conexion.Conexion;
import java.net.ConnectException;
public class AccidenteCtrl {
    public int Crear(AccidenteMod modelo) throws SQLException, ConnectException {

        int result = 0;
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        try {
            String sql = "INSERT INTO ACCIDENTE_INCIDENTE VALUES ((SELECT IFNULL(MAX(ACCINC_ID), 0)+1 FROM ACCIDENTE_INCIDENTE a), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            conn.setAutoCommit(false);
            smt = conn.prepareStatement(sql);
            smt.setString(1, modelo.getClinicaId());
            smt.setString(2, modelo.getFecha());
            smt.setString(3, modelo.getHora());
            smt.setString(4, modelo.getEdad());
            smt.setString(5, modelo.getArea());
            smt.setString(6, modelo.getPuesto());
            smt.setString(7, modelo.getRelato());
            smt.setString(8, modelo.getDatosSubjetivos());
            smt.setString(9, modelo.getClasificacion());
            smt.setString(10, modelo.getTratamiento());
            smt.setString(11, modelo.getReferencia());
            smt.setString(12, modelo.getTraslado());
            smt.setString(13, modelo.getReincorporacion());
            smt.setString(14, modelo.getEmpleadoId());
            smt.setString(15, modelo.getRevisionSistemasId());
            smt.setString(16, modelo.getResponsable());
            smt.setString(17, modelo.getEstado());

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

    public String getMaxId() throws SQLException, ConnectException {
        String id = "";
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        ResultSet result = null;

        AccidenteMod modeloBuscar = null;

        String sql = "SELECT IFNULL(MAX(ACCINC_ID), 0) FROM ACCIDENTE_INCIDENTE";
        try {
            smt = conn.prepareStatement(sql);
            result = smt.executeQuery();

            while (result.next()) {
                modeloBuscar = new AccidenteMod();

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

    public int Actualizar(AccidenteMod modelo) throws SQLException, ConnectException {

        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        int result = 0;
        try {
            String sql = "UPDATE ACCIDENTE_INCIDENTE SET ACCINC_EDAD = ?, ACCINC_AREA = ?, ACCINC_PUESTO = ?, ACCINC_RELATO = ?, ACCINC_DATOSSUB = ?, ACCINC_CLASIFICACION = ?, ACCINC_TRATAMIENTO = ?, ACCINC_REFERENCIA = ?, ACCINC_TRASLADO = ?, ACCINC_REINCORPORACION = ? WHERE ACCINC_ID = ?";
            conn.setAutoCommit(false);

            smt = conn.prepareStatement(sql);

            smt = conn.prepareStatement(sql);
            smt.setString(1, modelo.getEdad());
            smt.setString(2, modelo.getArea());
            smt.setString(3, modelo.getPuesto());
            smt.setString(4, modelo.getRelato());
            smt.setString(5, modelo.getDatosSubjetivos());
            smt.setString(6, modelo.getClasificacion());
            smt.setString(7, modelo.getTratamiento());
            smt.setString(8, modelo.getReferencia());
            smt.setString(9, modelo.getTraslado());
            smt.setString(10, modelo.getReincorporacion());
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

    public int Eliminar(AccidenteMod modelo) throws ConnectException, SQLException {
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        int result = 0;
        try {
            String sql = "UPDATE ACCIDENTE_INCIDENTE SET ACCINC_ESTADO = 0 WHERE ACCINC_ID = ?";
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

    public List<AccidenteMod> seleccionarTodos() throws SQLException, ConnectException {

        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        ResultSet result = null;
        List<AccidenteMod> lista = new ArrayList<AccidenteMod>();

        AccidenteMod modeloBuscar = null;

        String sql = "";

        sql = "select  * FROM ACCIDENTE_INCIDENTE WHERE ACCINC_ESTADO = 1 ORDER BY ACCINC_ID ASC";
        //+ filtro;

        try {

            smt = conn.prepareStatement(sql);

            result = smt.executeQuery();

            while (result.next()) {
                modeloBuscar = new AccidenteMod();

                modeloBuscar.setId(result.getString(1));
                modeloBuscar.setClinicaId(result.getString(2));
                modeloBuscar.setFecha(result.getString(3));
                modeloBuscar.setHora(result.getString(4));
                modeloBuscar.setEdad(result.getString(5));
                modeloBuscar.setArea(result.getString(6));
                modeloBuscar.setPuesto(result.getString(7));
                modeloBuscar.setRelato(result.getString(8));
                modeloBuscar.setDatosSubjetivos(result.getString(9));
                modeloBuscar.setClasificacion(result.getString(10));
                modeloBuscar.setTratamiento(result.getString(11));
                modeloBuscar.setReferencia(result.getString(12));
                modeloBuscar.setTraslado(result.getString(13));
                modeloBuscar.setReincorporacion(result.getString(14));
                modeloBuscar.setEmpleadoId(result.getString(15));
                modeloBuscar.setRevisionSistemasId(result.getString(16));
                modeloBuscar.setResponsable(result.getString(17));
                modeloBuscar.setEstado(result.getString(18));

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

    public AccidenteMod buscarFila(String id) throws SQLException, ConnectException {

        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        ResultSet result = null;

        AccidenteMod modeloBuscar = null;

        String sql = "";

        sql = "SELECT * FROM ACCIDENTE_INCIDENTE WHERE ACCINC_ID = ?";

        try {
            smt = conn.prepareStatement(sql);
            smt.setString(1, id);

            result = smt.executeQuery();

            while (result.next()) {
                modeloBuscar = new AccidenteMod();

                modeloBuscar.setId(result.getString(1));
                modeloBuscar.setClinicaId(result.getString(2));
                modeloBuscar.setFecha(result.getString(3));
                modeloBuscar.setHora(result.getString(4));
                modeloBuscar.setEdad(result.getString(5));
                modeloBuscar.setArea(result.getString(6));
                modeloBuscar.setPuesto(result.getString(7));
                modeloBuscar.setRelato(result.getString(8));
                modeloBuscar.setDatosSubjetivos(result.getString(9));
                modeloBuscar.setClasificacion(result.getString(10));
                modeloBuscar.setTratamiento(result.getString(11));
                modeloBuscar.setReferencia(result.getString(12));
                modeloBuscar.setTraslado(result.getString(13));
                modeloBuscar.setReincorporacion(result.getString(14));
                modeloBuscar.setEmpleadoId(result.getString(15));
                modeloBuscar.setRevisionSistemasId(result.getString(16));
                modeloBuscar.setResponsable(result.getString(17));
                modeloBuscar.setEstado(result.getString(18));
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
