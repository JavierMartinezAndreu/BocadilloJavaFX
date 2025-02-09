package com.example.prueba_1.service;

import com.example.prueba_1.dao.UsuarioDAO;
import com.example.prueba_1.model.Usuario;

import java.util.List;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void save(Usuario usuario){
        // Validación antes de guardar
        if (usuario.getId() == null || usuario.getId() == 0) {
            throw new IllegalArgumentException("El ID no puede estar vacío.");
        }

        usuarioDAO.save(usuario);
    }

    public List<Usuario> getAll() {
        return usuarioDAO.getAll();
    }

    /*public List<Usuario> getPaginated() {
        return usuarioDAO.getPaginated();
    }*/
}