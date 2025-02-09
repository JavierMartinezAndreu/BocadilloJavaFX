package com.example.prueba_1.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bocadillo")
public class Bocadillo {
    //Atributos de bocadillo

    @Id
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String ingredientes;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private double precio_venta_publico;

    @Column(nullable = false)
    private String dia_semana;

    @ManyToMany
    @JoinTable(
            name = "bocadillo_alergeno", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "id_bocadillo"), // Columna que referencia a la tabla bocadillo
            inverseJoinColumns = @JoinColumn(name = "id_alergeno") // Columna que referencia a la tabla alergeno
    )
    private List<Alergeno> alergenos;

    public Bocadillo(Long id, String nombre, String ingredientes, String tipo, double precio_venta_publico, String dia_semana, List<Alergeno> alergenos) {
        this.id = id;
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.tipo = tipo;
        this.precio_venta_publico = precio_venta_publico;
        this.dia_semana = dia_semana;
        this.alergenos = alergenos;
    }

    public Bocadillo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public double getPrecio_venta_publico() {
        return precio_venta_publico;
    }

    public void setPrecio_venta_publico(double precio_venta_publico) {
        this.precio_venta_publico = precio_venta_publico;
    }

    public String getDia_semana() {
        return dia_semana;
    }

    public void setDia_semana(String dia_semana) {
        this.dia_semana = dia_semana;
    }

    public List<Alergeno> getAlergenos() {
        return alergenos;
    }

    public void setAlergenos(List<Alergeno> alergenos) {
        this.alergenos = alergenos;
    }
}