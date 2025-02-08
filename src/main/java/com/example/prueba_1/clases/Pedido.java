package com.example.prueba_1.clases;

import java.time.LocalDate;
import java.util.Date;

public class Pedido {
    //Atributos del pedido
    private Alumno alumno;
    private Bocadillo bocadillo;
    private float precio_pagado;
    private LocalDate fecha;
    private LocalDate fecha_recogida;

    //Constructores
    public Pedido(Alumno alumno, Bocadillo bocadillo, float precio_pagado, LocalDate fecha, LocalDate fecha_recogida) {
        this.alumno = alumno;
        this.bocadillo = bocadillo;
        this.precio_pagado = precio_pagado;
        this.fecha = fecha;
        this.fecha_recogida = fecha_recogida;
    }

    //Getters y Setters


    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Bocadillo getBocadillo() {
        return bocadillo;
    }

    public void setBocadillo(Bocadillo bocadillo) {
        this.bocadillo = bocadillo;
    }

    public float getPrecio_pagado() {
        return precio_pagado;
    }

    public void setPrecio_pagado(float precio_pagado) {
        this.precio_pagado = precio_pagado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalDate getFecha_recogida() {
        return fecha_recogida;
    }

    public void setFecha_recogida(LocalDate fecha_recogida) {
        this.fecha_recogida = fecha_recogida;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "alumno=" + alumno +
                ", bocadillo=" + bocadillo +
                ", precio_pagado=" + precio_pagado +
                ", fecha=" + fecha +
                ", fecha_recogida=" + fecha_recogida +
                '}';
    }
}