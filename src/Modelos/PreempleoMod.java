package Modelos;

public class PreempleoMod {
    private String id;
    private String fecha;
    private String nombre;
    private String sexo;
    private String identificacion;
    private String edad;
    private String estadoCivil;
    private String direccion;
    private String telefono;
    private String nivelAcademico;
    private String puestoAplica;
    private String empresa1;
    private String empresa2;
    private String empresa3;
    private String puesto1;
    private String puesto2;
    private String puesto3;
    private String tiempoLaborado1;
    private String tiempoLaborado2;
    private String tiempoLaborado3;
    private String aptitud;
    private String restricciones;
    private String clinicaId;
    private String empleadoId;
    private String antecedentesId;
    private String revisionSistemasId;
    private String responsable;
    private String realizado;
    private String revisado;
    private String autorizado;
    private String estado;
    
    private final String nombreTabla = "PREEMPLEO";
    private final String prefijo = "PREEMP_";
    private final String llavePrimaria = prefijo+"ID";
    
    public PreempleoMod(){
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNivelAcademico() {
        return nivelAcademico;
    }

    public void setNivelAcademico(String nivelAcademico) {
        this.nivelAcademico = nivelAcademico;
    }

    public String getPuestoAplica() {
        return puestoAplica;
    }

    public void setPuestoAplica(String puestoAplica) {
        this.puestoAplica = puestoAplica;
    }

    public String getEmpresa1() {
        return empresa1;
    }

    public void setEmpresa1(String empresa1) {
        this.empresa1 = empresa1;
    }

    public String getEmpresa2() {
        return empresa2;
    }

    public void setEmpresa2(String empresa2) {
        this.empresa2 = empresa2;
    }

    public String getEmpresa3() {
        return empresa3;
    }

    public void setEmpresa3(String empresa3) {
        this.empresa3 = empresa3;
    }

    public String getPuesto1() {
        return puesto1;
    }

    public void setPuesto1(String puesto1) {
        this.puesto1 = puesto1;
    }

    public String getPuesto2() {
        return puesto2;
    }

    public void setPuesto2(String puesto2) {
        this.puesto2 = puesto2;
    }

    public String getPuesto3() {
        return puesto3;
    }

    public void setPuesto3(String puesto3) {
        this.puesto3 = puesto3;
    }

    public String getTiempoLaborado1() {
        return tiempoLaborado1;
    }

    public void setTiempoLaborado1(String tiempoLaborado1) {
        this.tiempoLaborado1 = tiempoLaborado1;
    }

    public String getTiempoLaborado2() {
        return tiempoLaborado2;
    }

    public void setTiempoLaborado2(String tiempoLaborado2) {
        this.tiempoLaborado2 = tiempoLaborado2;
    }

    public String getTiempoLaborado3() {
        return tiempoLaborado3;
    }

    public void setTiempoLaborado3(String tiempoLaborado3) {
        this.tiempoLaborado3 = tiempoLaborado3;
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
