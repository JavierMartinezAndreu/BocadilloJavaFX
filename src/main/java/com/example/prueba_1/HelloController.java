package com.example.prueba_1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloController {
    // Valores del login
    private static final String nombre_usuario = "alumno@gmail.com";
    private static final String contrasenna = "123";

    // Elementos de la interfaz vinculados desde el FXML
    @FXML
    private TextField usuarioField;

    @FXML
    private PasswordField contrasennaField;

    @FXML
    private Button iniciarSesionButton;

    @FXML
    private Label mensajeLabel;

    // Método para inicializar el controlador
    @FXML
    public void initialize() {
        // Configuramos el botón para validar las credenciales al hacer clic
        iniciarSesionButton.setOnAction(event -> validarCredenciales());
    }

    // Método para validar las credenciales
    private void validarCredenciales() {
        String usuarioIngresado = usuarioField.getText();
        String contrasennaIngresada = contrasennaField.getText();

        if (nombre_usuario.equals(usuarioIngresado) && contrasenna.equals(contrasennaIngresada)) {
            redirigirAlumno();
        } else {
            mensajeLabel.setText("Usuario o contraseña incorrectos.");
            mensajeLabel.setStyle("-fx-text-fill: red;");
        }
    }

    // Método para cambiar a la vista de bienvenida
    private void redirigirAlumno() {
        try {
            // Cargar la nueva vista desde el archivo FXML
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("alumno-view.fxml"));
            Scene alumnoScene = new Scene(fxmlLoader.load());

            // Obtener el Stage actual y reemplazar la escena
            Stage currentStage = (Stage) iniciarSesionButton.getScene().getWindow();
            currentStage.setScene(alumnoScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}