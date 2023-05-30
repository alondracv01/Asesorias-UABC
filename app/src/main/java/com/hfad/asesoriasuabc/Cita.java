package com.hfad.asesoriasuabc;

public class Cita {
    private String asesor;
    private String materia;
    private String fecha;
    private String hora;
    private String estado;
    private int id;

    public Cita(String asesor, String materia, String fecha, String hora, String estado, int id) {
        this.asesor = asesor;
        this.materia = materia;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAsesor() {
        return asesor;
    }

    public void setAsesor(String asesor) {
        this.asesor = asesor;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
