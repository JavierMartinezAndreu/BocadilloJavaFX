package com.example.prueba_1.controller;

import com.example.prueba_1.model.Alumno;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class AlumnoPedidosController {

    @FXML
    private Label mensajeBienvenidaLabel;

    @FXML
    private Button botonPedirBocadillo;

    @FXML
    private Button botonCerrarSesion;

    private Alumno alumno;

    // Método para inicializar el controlador
    @FXML
    public void initialize(Alumno mi_alumno) {
        this.alumno = mi_alumno;
        botonPedirBocadillo.setOnAction(event -> irAPedir());
        botonCerrarSesion.setOnAction(event -> cerrarSesion());
        mostrarDatosAlumno(mi_alumno);
    }

    public void mostrarDatosAlumno(Alumno alumno) {
        mensajeBienvenidaLabel.setText("Bienvenido " + alumno.getNombre());
    }

    public void irAPedir() {
        try {
            // Cargar la vista de historial
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/prueba_1/fxml/alumno-view.fxml"));
            Parent root = fxmlLoader.load();

            // Obtener el controlador de la nueva vista
            AlumnoController controller = fxmlLoader.getController();

            // Pasar el objeto Alumno al controlador
            controller.initialize(alumno);

            // Obtener el Stage actual y reemplazar la escena (sin crear un nuevo Stage)
            Stage currentStage = (Stage) botonPedirBocadillo.getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setFullScreen(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cerrarSesion(){
        try {
            // Cargar la nueva vista desde el archivo FXML
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/prueba_1/fxml/hello-view.fxml"));
            Scene helloScene = new Scene(fxmlLoader.load());

            HelloController controller = fxmlLoader.getController();

            // Obtener el Stage actual y reemplazar la escena
            Stage currentStage = (Stage) botonCerrarSesion.getScene().getWindow();
            currentStage.setScene(helloScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}