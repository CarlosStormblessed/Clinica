package Modelos;

public class VidaSaludableMod {
    private String id;
    private String fecha;
    private String hora;
    private String edad;
    private String area;
    private String puesto;
    private String pesoInicial;
    private String pesoRecomendado;
    private String actividad;
    private String tipoEjercicio;
    private String frecuenciaEjercicio;
    private String duracionEjercicio;
    private String medidaBrazo;
    private String medidaCintura;
    private String medidaCadera;
    private String medidaAbdomen;
    private String indiceCinturaCadera;
    private String interpretacionRiesgo;
    private String diagnosticoNutricional;
    private String plan;
    private String clinicaId;
    private String empleadoId;
    private String revisionSistemasId;
    private String responsable;
    private String estado;
    
    private final String nombreTabla = "VIDA_SALUDABLE";
    private final String prefijo = "VIDSAL_";
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

    public String getPesoInicial() {
        return pesoInicial;
    }

    public void setPesoInicial(String pesoInicial) {
        this.pesoInicial = pesoInicial;
    }

    public String getPesoRecomendado() {
        return pesoRecomendado;
    }

    public void setPesoRecomendado(String pesoRecomendado) {
        this.pesoRecomendado = pesoRecomendado;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getTipoEjercicio() {
        return tipoEjercicio;
    }

    public void setTipoEjercicio(String tipoEjercicio) {
        this.tipoEjercicio = tipoEjercicio;
    }

    public String getFrecuenciaEjercicio() {
        return frecuenciaEjercicio;
    }

    public void setFrecuenciaEjercicio(String frecuenciaEjercicio) {
        this.frecuenciaEjercicio = frecuenciaEjercicio;
    }

    public String getDuracionEjercicio() {
        return duracionEjercicio;
    }

    public void setDuracionEjercicio(String duracionEjercicio) {
        this.duracionEjercicio = duracionEjercicio;
    }

    public String getMedidaBrazo() {
        return medidaBrazo;
    }

    public void setMedidaBrazo(String medidaBrazo) {
        this.medidaBrazo = medidaBrazo;
    }

    public String getMedidaCintura() {
        return medidaCintura;
    }

    public void setMedidaCintura(String medidaCintura) {
        this.medidaCintura = medidaCintura;
    }

    public String getMedidaCadera() {
        return medidaCadera;
    }

    public void setMedidaCadera(String medidaCadera) {
        this.medidaCadera = medidaCadera;
    }

    public String getMedidaAbdomen() {
        return medidaAbdomen;
    }

    public void setMedidaAbdomen(String medidaAbdomen) {
        this.medidaAbdomen = medidaAbdomen;
    }

    public String getIndiceCinturaCadera() {
        return indiceCinturaCadera;
    }

    public void setIndiceCinturaCadera(String indiceCinturaCadera) {
        this.indiceCinturaCadera = indiceCinturaCadera;
    }

    public String getInterpretacionRiesgo() {
        return interpretacionRiesgo;
    }

    public void setInterpretacionRiesgo(String interpretacionRiesgo) {
        this.interpretacionRiesgo = interpretacionRiesgo;
    }

    public String getDiagnosticoNutricional() {
        return diagnosticoNutricional;
    }

    public void setDiagnosticoNutricional(String diagnosticoNutricional) {
        this.diagnosticoNutricional = diagnosticoNutricional;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
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
