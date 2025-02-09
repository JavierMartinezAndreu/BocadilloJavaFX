package com.example.prueba_1.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "pedidos")
public class Pedido {
    //Atributos del pedido

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_alumno", nullable = false)
    private Alumno alumno;

    @ManyToOne
    @JoinColumn(name = "id_bocadillo", nullable = false)
    private Bocadillo bocadillo;

    @Column(nullable = false)
    private double precio_pedido;

    @Column(nullable = false)
    private Date fecha;

    @Column(nullable = false)
    private Date fecha_recogida;

    public Pedido(Long id, Alumno alumno, Bocadillo bocadillo, double precio_pedido, Date fecha, Date fecha_recogida) {
        this.id = id;
        this.alumno = alumno;
        this.bocadillo = bocadillo;
        this.precio_pedido = precio_pedido;
        this.fecha = fecha;
        this.fecha_recogida = fecha_recogida;
    }

    public Pedido() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public double getPrecio_pedido() {
        return precio_pedido;
    }

    public void setPrecio_pedido(double precio_pedido) {
        this.precio_pedido = precio_pedido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFecha_recogida() {
        return fecha_recogida;
    }

    public void setFecha_recogida(Date fecha_recogida) {
        this.fecha_recogida = fecha_recogida;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", alumno=" + alumno +
                ", bocadillo=" + bocadillo +
                ", precio_pedido=" + precio_pedido +
                ", fecha=" + fecha +
                ", fecha_recogida=" + fecha_recogida +
                '}';
    }
}