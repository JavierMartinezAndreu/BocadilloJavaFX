package com.example.prueba_1.service;

import com.example.prueba_1.dao.AdministradorDAO;
import com.example.prueba_1.model.Alumno;
import com.example.prueba_1.model.Pedido;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.List;

public class AdministradorService {

    private AdministradorDAO administradorDAO;

    public AdministradorService() {
        this.administradorDAO = new AdministradorDAO();
    }

    // Obtener todos los alumnos con sus cursos y bocadillos
    public List<Alumno> obtenerTodosLosAlumnosConPedidos() {
        List<Alumno> alumnos = administradorDAO.getAllAlumnos();

        // Para cada alumno, obtener sus pedidos asociados
        for (Alumno alumno : alumnos) {
            List<Pedido> pedidos = obtenerPedidosDeAlumno(alumno);
            alumno.setPedidos(pedidos);
        }

        return alumnos;
    }

    // Obtener los pedidos de un alumno específico
    public List<Pedido> obtenerPedidosDeAlumno(Alumno alumno) {
        if (alumno != null) {
            return alumno.getPedidos(); // Asumiendo que la relación está mapeada en el modelo
        } else {
            return new ArrayList<>();
        }
    }

    // Obtener un alumno por ID
    public Alumno obtenerAlumnoPorId(Long id) {
        return administradorDAO.getAlumnoById(id);
    }

    // Eliminar un alumno
    public void borrarAlumno(Alumno alumno) {
        administradorDAO.borrarAlumno(alumno);
    }

    // Editar o actualizar un alumno
    public void actualizarAlumno(Alumno alumno) {
        administradorDAO.saveOrUpdate(alumno);
    }
}
