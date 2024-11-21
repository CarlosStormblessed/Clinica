package Modelos;

public class SeguimientoCronicosMod {
    private String id;
    private String clinicaId;
    private String fecha;
    private String hora;
    private String empleadoId;
    private String edad;
    private String area;
    private String puesto;
    private String datosSubjetivos;
    private String tratamiento;
    private String referencia;
    private String traslado;
    private String revisionSistemasId;
    private String responsable;
    private String estado;
    
    private final String nombreTabla = "SEGUIMIENTO_CRONICOS";
    private final String prefijo = "SEGCRO_";
    private final String llavePrimaria = prefijo+"ID";

    public String getClinicaId() {
        return clinicaId;
    }

    public void setClinicaId(String clinicaId) {
        this.clinicaId = clinicaId;
    }

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

    public String getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(String empleadoId) {
        this.empleadoId = empleadoId;
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

    public String getDatosSubjetivos() {
        return datosSubjetivos;
    }

    public void setDatosSubjetivos(String datosSubjetivos) {
        this.datosSubjetivos = datosSubjetivos;
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
