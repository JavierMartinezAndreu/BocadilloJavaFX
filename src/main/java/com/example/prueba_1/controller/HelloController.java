package com.example.prueba_1.controller;

import com.example.prueba_1.model.Alumno;
import com.example.prueba_1.model.Usuario;
import com.example.prueba_1.service.AlumnoService;
import com.example.prueba_1.service.UsuarioService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class HelloController {
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

        iniciarSesionButton.setOnAction(event -> validarCredenciales());
    }

    // Método para validar las credenciales
    private void validarCredenciales() {
        String usuarioIngresado = usuarioField.getText();
        String contrasennaIngresada = contrasennaField.getText();

        //Inicializar el listado de usuarios
        UsuarioService usuarioService = new UsuarioService();
        List<Usuario> usuarios = usuarioService.getAll();

        //Comprobar credenciales de los usuarios y su tipo
        for (Usuario mi_usuario : usuarios){
            if (mi_usuario.getEmail().equals(usuarioIngresado) && mi_usuario.getPassword().equals(contrasennaIngresada)){
                //Comprobar si es de tipo alumno, y si existe, hacer login
                if (mi_usuario.getTipo_usuario().equals("alumnado")){
                    AlumnoService alumnoService = new AlumnoService();
                    Alumno alumno = alumnoService.obtenerAlumnoPorUsuario(mi_usuario);
                    if (alumno != null){
                        redirigirAlumno(alumno);
                    }
                } else if (mi_usuario.getTipo_usuario().equals("cocina")) {
                    redirigirCocina();
                }
            } else {
                mensajeLabel.setText("Usuario o contraseña incorrectos.");
                mensajeLabel.setStyle("-fx-text-fill: red;");
            }
        }
    }

    // Método para cambiar a la vista de bienvenida
    private void redirigirAlumno(Alumno alumno) {
        try {
            // Cargar la nueva vista desde el archivo FXML
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/prueba_1/fxml/alumno-view.fxml"));
            Scene alumnoScene = new Scene(fxmlLoader.load());


            AlumnoController controller = fxmlLoader.getController();
            controller.initialize(alumno);

            // Obtener el Stage actual y reemplazar la escena
            Stage currentStage = (Stage) iniciarSesionButton.getScene().getWindow();
            currentStage.setScene(alumnoScene);

            // Configurar el Stage para que se vea en pantalla completa
            currentStage.setMaximized(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void redirigirCocina() {
        try {
            // Cargar la nueva vista desde el archivo FXML
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/prueba_1/fxml/cocina-view.fxml"));
            Scene cocinaScene = new Scene(fxmlLoader.load());


            CocinaController controller = fxmlLoader.getController();
            controller.initialize();

            // Obtener el Stage actual y reemplazar la escena
            Stage currentStage = (Stage) iniciarSesionButton.getScene().getWindow();
            currentStage.setScene(cocinaScene);

            // Configurar el Stage para que se vea en pantalla completa
            currentStage.setMaximized(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}