package com.example.prueba_1.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "alumno")
public class Alumno {
    //ATRIBUTOS

    @Id
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_curso", nullable = false)
    private Curso curso;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "alumno")
    private List<Pedido> pedidos;

    public Alumno(Long id, String nombre, Curso curso, Usuario usuario, List<Pedido> pedidos) {
        this.id = id;
        this.nombre = nombre;
        this.curso = curso;
        this.usuario = usuario;
        this.pedidos = pedidos;
    }

    public Alumno() {
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

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}