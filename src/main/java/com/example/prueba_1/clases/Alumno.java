package com.example.prueba_1.clases;

public class Alumno {
    //ATRIBUTOS
    private String nombre;
    private String id_curso_alumno;
    private String id_email_usuario;
    private String contrasenna;
    private boolean fecha_baja;
    private int id_descuento;
    private float deuda;

    //CONSTRUCTORES
    public Alumno(String nombre, String id_curso_alumno, String id_email_usuario, boolean fecha_baja, int id_descuento, float deuda, String contrasenna) {
        this.nombre = nombre;
        this.id_curso_alumno = id_curso_alumno;
        this.id_email_usuario = id_email_usuario;
        this.contrasenna = contrasenna;
        this.fecha_baja = fecha_baja;
        this.id_descuento = id_descuento;
        this.deuda = deuda;
    }

    public Alumno(String nombre, String id_curso_alumno, String id_email_usuario, String contrasenna) {
        this.nombre = nombre;
        this.id_curso_alumno = id_curso_alumno;
        this.id_email_usuario = id_email_usuario;
        this.contrasenna = contrasenna;
    }

    //GETTERS Y SETTERS

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId_curso_alumno() {
        return id_curso_alumno;
    }

    public void setId_curso_alumno(String id_curso_alumno) {
        this.id_curso_alumno = id_curso_alumno;
    }

    public String getId_email_usuario() {
        return id_email_usuario;
    }

    public void setId_email_usuario(String id_email_usuario) {
        this.id_email_usuario = id_email_usuario;
    }

    public String getContrasenna() {
        return contrasenna;
    }

    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }

    public boolean isFecha_baja() {
        return fecha_baja;
    }

    public void setFecha_baja(boolean fecha_baja) {
        this.fecha_baja = fecha_baja;
    }

    public int getId_descuento() {
        return id_descuento;
    }

    public void setId_descuento(int id_descuento) {
        this.id_descuento = id_descuento;
    }

    public float getDeuda() {
        return deuda;
    }

    public void setDeuda(float deuda) {
        this.deuda = deuda;
    }
}