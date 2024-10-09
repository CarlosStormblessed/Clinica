package Modelos;

public class FichaClinicaMod {
    private String id;
    private String fecha;
    private String hora;
    private String edad;
    private String area;
    private String puesto;
    private String motivo;
    private String historia;
    private String tratamiento;
    private String referencia;
    private String traslado;
    private String suspension;
    private String clinicaId;
    private String empleadoId;
    private String revisionSistemasId;
    private String responsable;
    private String realizado;
    private String revisado;
    private String autorizado;
    private String estado;
    
    private final String nombreTabla = "CONSULTA_GENERAL";
    private final String prefijo = "CONGEN_";
    private final String llavePrimaria = prefijo+"ID";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getTraslado() {
        return traslado;
    }

    public void setTraslado(String traslado) {
        this.traslado = traslado;
    }

    public String getSuspension() {
        return suspension;
    }

    public void setSuspension(String patologia) {
        this.suspension = patologia;
    }

    public String getClinicaId() {
        return clinicaId;
    }

    public void setClinicaId(String clinicaId) {
        this.clinicaId = clinicaId;
    }

    public String getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(String empleadoId) {
        this.empleadoId = empleadoId;
    }

    public String getRevisionSistemasId() {
        return revisionSistemasId;
    }

    public void setRevisionSistemasId(String revisionSistemasId) {
        this.revisionSistemasId = revisionSistemasId;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getRealizado() {
        return realizado;
    }

    public void setRealizado(String realizado) {
        this.realizado = realizado;
    }

    public String getRevisado() {
        return revisado;
    }

    public void setRevisado(String revisado) {
        this.revisado = revisado;
    }

    public String getAutorizado() {
        return autorizado;
    }

    public void setAutorizado(String autorizado) {
        this.autorizado = autorizado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombreTabla() {
        return nombreTabla;
    }

    public String getPrefijo() {
        return prefijo;
    }

    public String getLlavePrimaria() {
        return llavePrimaria;
    }
}
