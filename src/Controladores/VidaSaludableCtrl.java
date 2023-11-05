package Controladores;
import Modelos.VidaSaludableMod;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Conexion.Conexion;
import java.net.ConnectException;

public class VidaSaludableCtrl {
    public int Crear(VidaSaludableMod modelo) throws SQLException, ConnectException {

        int result = 0;
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        try {
            String sql = "INSERT INTO VIDA_SALUDABLE VALUES ((SELECT IFNULL(MAX(VIDSAL_ID), 0)+1 FROM VIDA_SALUDABLE a), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            conn.setAutoCommit(false);
            smt = conn.prepareStatement(sql);
            smt.setString(1, modelo.getFecha() + " " + modelo.getHora());
            smt.setString(2, modelo.getEdad());
            smt.setString(3, modelo.getArea());
            smt.setString(4, modelo.getPuesto());
            smt.setString(5, modelo.getPesoInicial());
            smt.setString(6, modelo.getPesoRecomendado());
            smt.setString(7, modelo.getActividad());
            smt.setString(8, modelo.getTipoEjercicio());
            smt.setString(9, modelo.getFrecuenciaEjercicio());
            smt.setString(10, modelo.getDuracionEjercicio());
            smt.setString(11, modelo.getMedidaBrazo());
            smt.setString(12, modelo.getMedidaCintura());
            smt.setString(13, modelo.getMedidaCadera());
            smt.setString(14, modelo.getMedidaAbdomen());
            smt.setString(15, modelo.getIndiceCinturaCadera());
            smt.setString(16, modelo.getInterpretacionRiesgo());
            smt.setString(17, modelo.getDiagnosticoNutricional());
            smt.setString(18, modelo.getPlan());
            smt.setString(19, modelo.getClinicaId());
            smt.setString(20, modelo.getEmpleadoId());
            smt.setString(21, modelo.getRevisionSistemasId());
            smt.setString(22, modelo.getResponsable());
            smt.setString(23, modelo.getEstado());

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

        VidaSaludableMod modeloBuscar = null;

        String sql = "SELECT IFNULL(MAX(VIDSAL_ID), 0) FROM VIDA_SALUDABLE";
        try {
            smt = conn.prepareStatement(sql);
            result = smt.executeQuery();

            while (result.next()) {
                modeloBuscar = new VidaSaludableMod();

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

    public int Actualizar(VidaSaludableMod modelo) throws SQLException, ConnectException {

        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        int result = 0;
        try {
            String sql = "UPDATE VIDA_SALUDABLE SET VIDSAL_EDAD = ?, VIDSAL_AREA = ?, VIDSAL_PUESTO = ?, VIDSAL_PESOINICIAL = ?, VIDSAL_PESORECOMENDADO = ?, VIDSAL_ACTIVIDAD = ?, VIDSAL_TIPOEJERCICIO = ?, VIDSAL_FRECUENCIAEJERCICIO = ?, VIDSAL_DURACIONEJERCICIO = ?, VIDSAL_MEDIDABRAZO = ?, VIDSAL_MEDIDACINTURA = ?, VIDSAL_MEDIDACADERA = ?, VIDSAL_MEDIDAABDOMEN = ?, VIDSAL_INDICECINTURACADERA = ?, VIDSAL_INTERPRETACIONRIESGO = ?, VIDSAL_DIAGNOSTICONUTRICIONAL = ?, VIDSAL_PLAN = ? WHERE VIDSAL_ID = ?";
            conn.setAutoCommit(false);

            smt = conn.prepareStatement(sql);

            smt = conn.prepareStatement(sql);
            smt.setString(1, modelo.getEdad());
            smt.setString(2, modelo.getArea());
            smt.setString(3, modelo.getPuesto());
            smt.setString(4, modelo.getPesoInicial());
            smt.setString(5, modelo.getPesoRecomendado());
            smt.setString(6, modelo.getActividad());
            smt.setString(7, modelo.getTipoEjercicio());
            smt.setString(8, modelo.getFrecuenciaEjercicio());
            smt.setString(9, modelo.getDuracionEjercicio());
            smt.setString(10, modelo.getMedidaBrazo());
            smt.setString(11, modelo.getMedidaCintura());
            smt.setString(12, modelo.getMedidaCadera());
            smt.setString(13, modelo.getMedidaAbdomen());
            smt.setString(14, modelo.getIndiceCinturaCadera());
            smt.setString(15, modelo.getInterpretacionRiesgo());
            smt.setString(16, modelo.getDiagnosticoNutricional());
            smt.setString(17, modelo.getPlan());
            smt.setString(18, modelo.getId());

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

    public int Eliminar(VidaSaludableMod modelo) throws ConnectException, SQLException {
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        int result = 0;
        try {
            String sql = "UPDATE VIDA_SALUDABLE SET VIDSAL_ESTADO = 0 WHERE VIDSAL_ID = ?";
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

    public List<VidaSaludableMod> seleccionarTodos() throws SQLException, ConnectException {

        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        ResultSet result = null;
        List<VidaSaludableMod> lista = new ArrayList<VidaSaludableMod>();

        VidaSaludableMod modeloBuscar = null;

        String sql = "";

        sql = "select  * FROM VIDA_SALUDABLE WHERE VIDSAL_ESTADO = 1 ORDER BY VIDSAL_ID ASC";
        //+ filtro;

        try {

            smt = conn.prepareStatement(sql);

            result = smt.executeQuery();

            while (result.next()) {
                modeloBuscar = new VidaSaludableMod();

                modeloBuscar.setId(result.getString(1));
                String fechaHora = result.getString(2);
                String[] partesFechaHora = fechaHora.split(" ");
                modeloBuscar.setFecha(partesFechaHora[0]);
                modeloBuscar.setHora(partesFechaHora[1]);
                modeloBuscar.setEdad(result.getString(3));
                modeloBuscar.setArea(result.getString(4));
                modeloBuscar.setPuesto(result.getString(5));
                modeloBuscar.setPesoInicial(result.getString(6));
                modeloBuscar.setPesoRecomendado(result.getString(7));
                modeloBuscar.setActividad(result.getString(8));
                modeloBuscar.setTipoEjercicio(result.getString(9));
                modeloBuscar.setFrecuenciaEjercicio(result.getString(10));
                modeloBuscar.setDuracionEjercicio(result.getString(11));
                modeloBuscar.setMedidaBrazo(result.getString(12));
                modeloBuscar.setMedidaCintura(result.getString(13));
                modeloBuscar.setMedidaCadera(result.getString(14));
                modeloBuscar.setMedidaAbdomen(result.getString(15));
                modeloBuscar.setIndiceCinturaCadera(result.getString(16));
                modeloBuscar.setInterpretacionRiesgo(result.getString(17));
                modeloBuscar.setDiagnosticoNutricional(result.getString(18));
                modeloBuscar.setPlan(result.getString(19));
                modeloBuscar.setClinicaId(result.getString(20));
                modeloBuscar.setEmpleadoId(result.getString(21));
                modeloBuscar.setRevisionSistemasId(result.getString(22));
                modeloBuscar.setResponsable(result.getString(23));
                modeloBuscar.setEstado(result.getString(24));
                
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

    public VidaSaludableMod buscarFila(String id) throws SQLException, ConnectException {

        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        ResultSet result = null;

        VidaSaludableMod modeloBuscar = null;

        String sql = "";

        sql = "SELECT * FROM VIDA_SALUDABLE WHERE VIDSAL_ID = ?";

        try {
            smt = conn.prepareStatement(sql);
            smt.setString(1, id);

            result = smt.executeQuery();

            while (result.next()) {
                modeloBuscar = new VidaSaludableMod();

                modeloBuscar.setId(result.getString(1));
                String fechaHora = result.getString(2);
                String[] partesFechaHora = fechaHora.split(" ");
                modeloBuscar.setFecha(partesFechaHora[0]);
                modeloBuscar.setHora(partesFechaHora[1]);
                modeloBuscar.setEdad(result.getString(3));
                modeloBuscar.setArea(result.getString(4));
                modeloBuscar.setPuesto(result.getString(5));
                modeloBuscar.setPesoInicial(result.getString(6));
                modeloBuscar.setPesoRecomendado(result.getString(7));
                modeloBuscar.setActividad(result.getString(8));
                modeloBuscar.setTipoEjercicio(result.getString(9));
                modeloBuscar.setFrecuenciaEjercicio(result.getString(10));
                modeloBuscar.setDuracionEjercicio(result.getString(11));
                modeloBuscar.setMedidaBrazo(result.getString(12));
                modeloBuscar.setMedidaCintura(result.getString(13));
                modeloBuscar.setMedidaCadera(result.getString(14));
                modeloBuscar.setMedidaAbdomen(result.getString(15));
                modeloBuscar.setIndiceCinturaCadera(result.getString(16));
                modeloBuscar.setInterpretacionRiesgo(result.getString(17));
                modeloBuscar.setDiagnosticoNutricional(result.getString(18));
                modeloBuscar.setPlan(result.getString(19));
                modeloBuscar.setClinicaId(result.getString(20));
                modeloBuscar.setEmpleadoId(result.getString(21));
                modeloBuscar.setRevisionSistemasId(result.getString(22));
                modeloBuscar.setResponsable(result.getString(23));
                modeloBuscar.setEstado(result.getString(24));
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
