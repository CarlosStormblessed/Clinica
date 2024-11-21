package Controladores;
import Modelos.PreempleoMod;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Conexion.Conexion;
import java.net.ConnectException;

public class PreempleoCtrl {
    
    public int Crear(PreempleoMod modelo) throws SQLException, ConnectException {
        
        int result = 0;
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        try {

            String sql = "INSERT INTO PREEMPLEO VALUES ((SELECT IFNULL(MAX(PREEMP_ID), 0)+1 FROM PREEMPLEO p), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, (SELECT CLI_ID FROM CLINICA WHERE CLI_ID = ?), (SELECT EMP_ID FROM EMPLEADO WHERE EMP_ID = ?), (SELECT ANT_ID FROM ANTECEDENTES WHERE ANT_ID = ?), (SELECT REVSIS_ID FROM REVISION_SISTEMAS WHERE REVSIS_ID = ?), ?, ?, ?, ?, ?)";
            conn.setAutoCommit(false);
            smt = conn.prepareStatement(sql);
            smt.setString(1, modelo.getFecha());
            smt.setString(2, modelo.getNombre());
            smt.setString(3, modelo.getSexo());
            smt.setString(4, modelo.getIdentificacion());
            smt.setString(5, modelo.getEdad());
            smt.setString(6, modelo.getEstadoCivil());
            smt.setString(7, modelo.getDireccion());
            smt.setString(8, modelo.getTelefono());
            smt.setString(9, modelo.getNivelAcademico());
            smt.setString(10, modelo.getPuestoAplica());
            smt.setString(11, modelo.getEmpresa1());
            smt.setString(12, modelo.getEmpresa2());
            smt.setString(13, modelo.getEmpresa3());
            smt.setString(14, modelo.getPuesto1());
            smt.setString(15, modelo.getPuesto2());
            smt.setString(16, modelo.getPuesto3());
            smt.setString(17, modelo.getTiempoLaborado1());
            smt.setString(18, modelo.getTiempoLaborado2());
            smt.setString(19, modelo.getTiempoLaborado3());
            smt.setString(20, modelo.getAptitud());
            smt.setString(21, modelo.getRestricciones());
            smt.setString(22, modelo.getClinicaId());
            smt.setString(23, modelo.getEmpleadoId());
            smt.setString(24, modelo.getAntecedentesId());
            smt.setString(25, modelo.getRevisionSistemasId());
            smt.setString(26, modelo.getResponsable());
            smt.setString(27, modelo.getRealizado());
            smt.setString(28, modelo.getRevisado());
            smt.setString(29, modelo.getAutorizado());
            smt.setString(30, modelo.getEstado());
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

        PreempleoMod modeloBuscar = null;

        String sql = "SELECT IFNULL(MAX(PREEMP_ID), 0) FROM PREEMPLEO";
        try {
            smt = conn.prepareStatement(sql);
            result = smt.executeQuery();

            while (result.next()) {
                modeloBuscar = new PreempleoMod();

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
    
    public int Actualizar(PreempleoMod modelo) throws SQLException, ConnectException{
        
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
         int result = 0;
        try {
            String sql = "UPDATE PREEMPLEO SET PREEMP_FECHA = ?, PREEMP_NOMBRE = ?, PREEMP_SEXO = ?, PREEMP_IDENTIFICACION = ?, PREEMP_EDAD = ?, PREEMP_CIVIL = ?, PREEMP_DIRECCION = ?, PREEMP_TELEFONO = ?, PREEMP_NIVELACADEMICO = ?, PREEMP_PUESTOAPLICA = ?, PREEMP_EMPRESA1 = ?, PREEMP_EMPRESA2 = ?, PREEMP_EMPRESA3 = ?, PREEMP_PUESTO1 = ?, PREEMP_PUESTO2 = ?, PREEMP_PUESTO3 = ?, PREEMP_TIEMPOLABORADO1 = ?, PREEMP_TIEMPOLABORADO2 = ?, PREEMP_TIEMPOLABORADO3 = ?, PREEMP_APTITUD = ?, PREEMP_RESTRICCIONES = ? WHERE PREEMP_ID = ?";
            conn.setAutoCommit(false);

            smt = conn.prepareStatement(sql);

            smt.setString(1, modelo.getFecha());
            smt.setString(2, modelo.getNombre());
            smt.setString(3, modelo.getSexo());
            smt.setString(4, modelo.getIdentificacion());
            smt.setString(5, modelo.getEdad());
            smt.setString(6, modelo.getEstadoCivil());
            smt.setString(7, modelo.getDireccion());
            smt.setString(8, modelo.getTelefono());
            smt.setString(9, modelo.getNivelAcademico());
            smt.setString(10, modelo.getPuestoAplica());
            smt.setString(11, modelo.getEmpresa1());
            smt.setString(12, modelo.getEmpresa2());
            smt.setString(13, modelo.getEmpresa3());
            smt.setString(14, modelo.getPuesto1());
            smt.setString(15, modelo.getPuesto2());
            smt.setString(16, modelo.getPuesto3());
            smt.setString(17, modelo.getTiempoLaborado1());
            smt.setString(18, modelo.getTiempoLaborado2());
            smt.setString(19, modelo.getTiempoLaborado3());
            smt.setString(20, modelo.getAptitud());
            smt.setString(21, modelo.getRestricciones());
            smt.setString(22, modelo.getId());
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
    
    public int Eliminar(PreempleoMod modelo) throws ConnectException, SQLException{
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
         int result = 0;
        try {
            String sql = "UPDATE PREEMPLEO SET PREEMP_ESTADO = 0 WHERE PREEMP_ID = ?";
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
     
    public List<PreempleoMod> seleccionarTodos() throws SQLException, ConnectException {
        
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        ResultSet result = null;
        List<PreempleoMod> lista = new ArrayList<PreempleoMod>();

        PreempleoMod modeloBuscar = null;

        String sql = "";

        sql = "select  * FROM PREEMPLEO WHERE PREEMP_ESTADO = 1 ORDER BY PREEMP_ID ASC";
        //+ filtro;

        try {
            
            smt = conn.prepareStatement(sql);

            result = smt.executeQuery();

            while (result.next()) {
                modeloBuscar = new PreempleoMod();

                modeloBuscar.setId(result.getString(1));
                modeloBuscar.setFecha(result.getString(2));
                modeloBuscar.setNombre(result.getString(3));
                modeloBuscar.setSexo(result.getString(4));
                modeloBuscar.setIdentificacion(result.getString(5));
                modeloBuscar.setEdad(result.getString(6));
                modeloBuscar.setEstadoCivil(result.getString(7));
                modeloBuscar.setDireccion(result.getString(8));
                modeloBuscar.setTelefono(result.getString(9));
                modeloBuscar.setNivelAcademico(result.getString(10));
                modeloBuscar.setPuestoAplica(result.getString(11));
                modeloBuscar.setEmpresa1(result.getString(12));
                modeloBuscar.setEmpresa2(result.getString(13));
                modeloBuscar.setEmpresa3(result.getString(14));
                modeloBuscar.setPuesto1(result.getString(15));
                modeloBuscar.setPuesto2(result.getString(16));
                modeloBuscar.setPuesto3(result.getString(16));
                modeloBuscar.setTiempoLaborado1(result.getString(18));
                modeloBuscar.setTiempoLaborado2(result.getString(19));
                modeloBuscar.setTiempoLaborado3(result.getString(20));
                modeloBuscar.setAptitud(result.getString(21));
                modeloBuscar.setRestricciones(result.getString(22));
                modeloBuscar.setClinicaId(result.getString(23));
                modeloBuscar.setEmpleadoId(result.getString(24));
                modeloBuscar.setAntecedentesId(result.getString(25));
                modeloBuscar.setRevisionSistemasId(result.getString(26));
                modeloBuscar.setResponsable(result.getString(27));
                modeloBuscar.setRealizado(result.getString(28));
                modeloBuscar.setRevisado(result.getString(29));
                modeloBuscar.setAutorizado(result.getString(30));
                modeloBuscar.setEstado(result.getString(31));
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
     
    public PreempleoMod buscarFila(String id) throws SQLException, ConnectException {
        
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.connect();
        ResultSet result = null;

        PreempleoMod modeloBuscar = null;

        String sql = "";

        sql = "select * FROM PREEMPLEO where PREEMP_ID = ? AND PREEMP_ESTADO = 1";

        try {
            smt = conn.prepareStatement(sql);
            smt.setString(1, id);

            result = smt.executeQuery();

            while (result.next()) {
                modeloBuscar = new PreempleoMod();

                modeloBuscar.setId(result.getString(1));
                modeloBuscar.setFecha(result.getString(2));
                modeloBuscar.setNombre(result.getString(3));
                modeloBuscar.setSexo(result.getString(4));
                modeloBuscar.setIdentificacion(result.getString(5));
                modeloBuscar.setEdad(result.getString(6));
                modeloBuscar.setEstadoCivil(result.getString(7));
                modeloBuscar.setDireccion(result.getString(8));
                modeloBuscar.setTelefono(result.getString(9));
                modeloBuscar.setNivelAcademico(result.getString(10));
                modeloBuscar.setPuestoAplica(result.getString(11));
                modeloBuscar.setEmpresa1(result.getString(12));
                modeloBuscar.setEmpresa2(result.getString(13));
                modeloBuscar.setEmpresa3(result.getString(14));
                modeloBuscar.setPuesto1(result.getString(15));
                modeloBuscar.setPuesto2(result.getString(16));
                modeloBuscar.setPuesto3(result.getString(16));
                modeloBuscar.setTiempoLaborado1(result.getString(18));
                modeloBuscar.setTiempoLaborado2(result.getString(19));
                modeloBuscar.setTiempoLaborado3(result.getString(20));
                modeloBuscar.setAptitud(result.getString(21));
                modeloBuscar.setRestricciones(result.getString(22));
                modeloBuscar.setClinicaId(result.getString(23));
                modeloBuscar.setEmpleadoId(result.getString(24));
                modeloBuscar.setAntecedentesId(result.getString(25));
                modeloBuscar.setRevisionSistemasId(result.getString(26));
                modeloBuscar.setResponsable(result.getString(27));
                modeloBuscar.setRealizado(result.getString(28));
                modeloBuscar.setRevisado(result.getString(29));
                modeloBuscar.setAutorizado(result.getString(30));
                modeloBuscar.setEstado(result.getString(31));
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
