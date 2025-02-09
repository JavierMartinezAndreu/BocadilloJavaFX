package com.example.prueba_1.service;

import com.example.prueba_1.dao.AlumnoDAO;
import com.example.prueba_1.model.Alumno;
import com.example.prueba_1.model.Pedido;
import com.example.prueba_1.model.Usuario;

import java.util.List;

public class AlumnoService {
    private final AlumnoDAO alumnoDAO = new AlumnoDAO();

    public void save(Alumno alumno){
        // Validación antes de guardar
        if (alumno.getId() == null || alumno.getId() == 0) {
            throw new IllegalArgumentException("El ID no puede estar vacío.");
        }

        alumnoDAO.save(alumno);
    }

    public List<Alumno> getAll() {
        return alumnoDAO.getAll();
    }

    public Alumno obtenerAlumnoPorUsuario(Usuario usuario){
        List<Alumno> listado_alumnos = getAll();

        for (Alumno alumno: listado_alumnos){
            if (alumno.getUsuario().getId().equals(usuario.getId())){
                return alumno;
            }

        }

        return null;
    }


}