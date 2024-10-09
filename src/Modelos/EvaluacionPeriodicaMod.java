package Modelos;

public class EvaluacionPeriodicaMod {
    private String id;
    private String fecha;
    private String hora;
    private String edad;
    private String area;
    private String puesto;
    private String aptitud;
    private String restricciones;
    private String empleadoId;
    private String clinicaId;
    private String antecedentesId;
    private String revisionSistemasId;
    private String responsable;
    private String estado;
    
    private final String nombreTabla = "EVALUACION_PERIODICA";
    private final String prefijo = "EVAPER_";
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

    public String getAptitud() {
        return aptitud;
    }

    public void setAptitud(String aptitud) {
        this.aptitud = aptitud;
    }

    public String getRestricciones() {
        return restricciones;
    }

    public void setRestricciones(String restricciones) {
        this.restricciones = restricciones;
    }
    
    public String getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(String empleadoId) {
        this.empleadoId = empleadoId;
    }

    public String getClinicaId() {
        return clinicaId;
    }

    public void setClinicaId(String clinicaId) {
        this.clinicaId = clinicaId;
    }

    public String getAntecedentesId() {
        return antecedentesId;
    }

    public void setAntecedentesId(String antecedentesId) {
        this.antecedentesId = antecedentesId;
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