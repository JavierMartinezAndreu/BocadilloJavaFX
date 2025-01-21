package com.example.prueba_1.clases;

import java.util.Date;

public class Bocadillo {
    //Atributos de bocadillo
    private String nombre;
    private Date fecha_baja;
    private String ingredientes;
    private String tipo;
    private float precio;
    private String alergenos;
    private String dia_semana;

    //Constructores
    public Bocadillo(String nombre, Date fecha_baja, String ingredientes, String tipo, float precio, String alergenos, String dia_semana) {
        this.nombre = nombre;
        this.fecha_baja = fecha_baja;
        this.ingredientes = ingredientes;
        this.tipo = tipo;
        this.precio = precio;
        this.alergenos = alergenos;
        this.dia_semana = dia_semana;
    }

    //Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha_baja() {
        return fecha_baja;
    }

    public void setFecha_baja(Date fecha_baja) {
        this.fecha_baja = fecha_baja;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getAlergenos() {
        return alergenos;
    }

    public void setAlergenos(String alergenos) {
        this.alergenos = alergenos;
    }

    public String getDia_semana() {
        return dia_semana;
    }

    public void setDia_semana(String dia_semana) {
        this.dia_semana = dia_semana;
    }
}