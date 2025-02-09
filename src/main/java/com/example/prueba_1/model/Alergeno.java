package com.example.prueba_1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "alergenos")
public class Alergeno {
    @Id
    private Long id;

    @Column(nullable = false)
    private String nombre;

    public Alergeno(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Alergeno() {
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
}