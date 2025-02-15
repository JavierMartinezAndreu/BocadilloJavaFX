package com.example.prueba_1.service;

import com.example.prueba_1.dao.CursoDAO;
import com.example.prueba_1.model.Bocadillo;
import com.example.prueba_1.model.Curso;

import java.util.List;

public class CursoService {
    private final CursoDAO cursoDAO = new CursoDAO();

    public void save(Curso curso){
        // Validación antes de guardar
        if (curso.getId() == null || curso.getId() == 0) {
            throw new IllegalArgumentException("El ID no puede estar vacío.");
        }

        cursoDAO.save(curso);
    }

    public List<Curso> getAll() {
        return cursoDAO.getAll();
    }
}