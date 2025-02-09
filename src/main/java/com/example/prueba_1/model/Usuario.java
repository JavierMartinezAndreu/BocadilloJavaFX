package com.example.prueba_1.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
@Access(AccessType.FIELD)
public class Usuario {
    @Id
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUsuario tipo_usuario;

    public enum TipoUsuario {
        admin,
        cocina,
        alumnado
    }

    @Column
    private String auth_key;

    public Usuario(Long id, String email, String password, TipoUsuario tipo_usuario, String auth_key) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.tipo_usuario = tipo_usuario;
        this.auth_key = auth_key;
    }

    public Usuario() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TipoUsuario getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(TipoUsuario tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    public String getAuth_key() {
        return auth_key;
    }

    public void setAuth_key(String auth_key) {
        this.auth_key = auth_key;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", tipo_usuario=" + tipo_usuario +
                ", auth_key='" + auth_key + '\'' +
                '}';
    }
}