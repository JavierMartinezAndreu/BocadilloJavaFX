package com.example.prueba_1.controllers;

import com.example.prueba_1.clases.Alumno;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AlumnoController {
    @FXML
    private Label mensajeBienvenidaLabel;

    public void mostrarDatosAlumno(Alumno alumno) {
        mensajeBienvenidaLabel.setText("¡Bienvenido, " + alumno.getNombre() + "!");
    }
}