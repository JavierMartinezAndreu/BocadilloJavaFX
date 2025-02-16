package com.example.prueba_1.controller;

import com.example.prueba_1.model.Alumno;
import com.example.prueba_1.model.Bocadillo;
import com.example.prueba_1.model.Pedido;
import com.example.prueba_1.service.AlumnoService;
import com.example.prueba_1.service.BocadilloService;
import com.example.prueba_1.service.PedidoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlumnoController {
    @FXML
    private HBox bocadillosHBox;

    @FXML
    private Label mensajeBienvenidaLabel;

    @FXML
    private Button botonCerrarSesion;

    @FXML
    private Button botonHistorial;

    @FXML
    private List<Bocadillo> lista_bocadillos;

    @FXML
    private List<Pedido> lista_pedidos;

    private Alumno alumno;


    // Método para inicializar el controlador
    @FXML
    public void initialize(Alumno mi_alumno) {
        alumno=mi_alumno;
        botonCerrarSesion.setOnAction(event -> cerrarSesion());
        mostrarDatosAlumno(alumno);
        botonHistorial.setOnAction(event -> irAHistorial());
        cargarBocadillos();
        cargarPedidos(alumno);
        mostrarBocadillos(lista_bocadillos, lista_pedidos, alumno);
    }

    public void mostrarDatosAlumno(Alumno alumno) {
        mensajeBienvenidaLabel.setText("Bienvenido " + alumno.getNombre());
    }

    public void irAHistorial() {
        try {
            // Cargar la vista de historial
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/prueba_1/fxml/alumno-pedidos-view.fxml"));
            Parent root = fxmlLoader.load();

            // Obtener el controlador de la nueva vista
            AlumnoPedidosController controller = fxmlLoader.getController();

            //Recargar objeto alumno para tener los pedidos nuevos
            AlumnoService alumnoService = new AlumnoService();
            Alumno mi_alumno =  alumnoService.actualizarAlumnoPorId(alumno.getId());

            // Pasar el objeto Alumno al controlador
            controller.initialize(mi_alumno);

            // Obtener el Stage actual y reemplazar la escena (sin crear un nuevo Stage)
            Stage currentStage = (Stage) botonHistorial.getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setMaximized(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cerrarSesion() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Cerrar Sesión");
        alerta.setHeaderText(null); // Sin encabezado para un diseño más limpio
        alerta.setContentText("¿Seguro que quieres cerrar sesión?\nPerderás el acceso a tu cuenta.");

        // Personalizar los botones
        ButtonType botonAceptar = new ButtonType("Sí, cerrar sesión", ButtonBar.ButtonData.OK_DONE);
        ButtonType botonCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

        alerta.getButtonTypes().setAll(botonAceptar, botonCancelar);

        // Aplicar estilo con CSS
        DialogPane dialogPane = alerta.getDialogPane();
        dialogPane.lookup(".content.label").setStyle("-fx-font-size: 14px; -fx-text-fill: #333333; -fx-font-weight: bold;");

        // Cambiar el color de los botones
        Button okButton = (Button) dialogPane.lookupButton(botonAceptar);
        okButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px;");

        Button cancelButton = (Button) dialogPane.lookupButton(botonCancelar);
        cancelButton.setStyle("-fx-background-color: #DDDDDD; -fx-text-fill: black; -fx-font-size: 13px;");

        // Mostrar alerta y esperar la respuesta del usuario
        Optional<ButtonType> resultado = alerta.showAndWait();

        // Si el usuario elige cerrar sesión
        if (resultado.isPresent() && resultado.get() == botonAceptar) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/prueba_1/fxml/hello-view.fxml"));
                Scene helloScene = new Scene(fxmlLoader.load());

                Stage currentStage = (Stage) botonCerrarSesion.getScene().getWindow();
                currentStage.setScene(helloScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void cargarBocadillos(){
        //Instanciar bocadillos y guardarlos en una lista
        BocadilloService bocadilloService = new BocadilloService();
        lista_bocadillos =  bocadilloService.getAll();
    }

    public void cargarPedidos(Alumno alumno){
        AlumnoService alumnoService = new AlumnoService();
        lista_pedidos = alumnoService.obtenerPedidosAlumno(alumno);
    }

    public void mostrarBocadillos(List<Bocadillo> lista_bocadillos, List<Pedido> lista_pedidos, Alumno alumno) {
        String diaHoy = LocalDate.now().getDayOfWeek().name().toLowerCase();
        bocadillosHBox.getChildren().clear();
        bocadillosHBox.setSpacing(40);

        for (Bocadillo bocadillo : lista_bocadillos) {
            if (bocadillo.getDia_semana().toLowerCase().equals(diaHoy)) {
                VBox vbox = new VBox();
                vbox.setStyle("-fx-background-color: #ffffff; -fx-border-radius: 15px; -fx-background-radius: 15px; -fx-border-color: #4CAF50; -fx-border-width: 2px;");
                vbox.setPadding(new Insets(8));
                vbox.setPrefHeight(150);
                vbox.setPrefWidth(250);

                BocadilloService bocadilloService = new BocadilloService();

                Label nombreLabel = new Label("Nombre: " + bocadillo.getNombre());
                Label ingredientesLabel = new Label("Ingredientes: " + bocadillo.getIngredientes());
                Label tipoLabel = new Label("Tipo: " + bocadillo.getTipo());
                Label alergenosLabel = new Label(bocadilloService.obtenerAlergenosComoString(bocadillo));
                Label precioLabel = new Label("Precio: " + String.format("%.2f€", bocadillo.getPrecio_venta_publico()));
                vbox.getChildren().addAll(nombreLabel, ingredientesLabel, tipoLabel, alergenosLabel, precioLabel);
                vbox.setAlignment(Pos.TOP_CENTER);

                boolean pedido_existente = false;
                Pedido pedido_a_cancelar = null;
                for (Pedido pedido : lista_pedidos) {
                    LocalDate fechaPedido = pedido.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    if (pedido.getBocadillo().getId().equals(bocadillo.getId()) && pedido.getAlumno().getId().equals(alumno.getId()) && fechaPedido.equals(LocalDate.now())) {
                        pedido_existente = true;
                        pedido_a_cancelar = pedido;
                        break;
                    }
                }

                Button botonPedido = new Button(pedido_existente ? "Cancelar pedido" : "Pedir");
                VBox.setMargin(botonPedido, new Insets(10, 0, 0, 0));

                if (pedido_existente) {
                    botonPedido.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white;");
                    // Aplicar un estilo sombreado al VBox cuando el bocadillo esté reservado
                    vbox.setStyle("-fx-background-color: #f0f0f0; " +
                            "-fx-border-radius: 15px; " +
                            "-fx-background-radius: 15px; " +
                            "-fx-border-color: #999999; " +
                            "-fx-border-width: 2px; " +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 3);");
                    botonPedido.setOnAction(event -> {
                        cancelarPedido();
                    });
                } else {
                    botonPedido.setOnAction(event -> {
                        ReservarBocadillo(alumno, bocadillo, lista_pedidos);
                    });
                }

                vbox.getChildren().add(botonPedido);
                bocadillosHBox.getChildren().add(vbox);
            }
        }
    }



    public void ReservarBocadillo(Alumno alumno, Bocadillo bocadillo, List<Pedido> lista_pedidos_alumno) {

        // Crear y guardar el nuevo pedido
        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setAlumno(alumno);
        nuevoPedido.setBocadillo(bocadillo);
        nuevoPedido.setPrecio_pedido(bocadillo.getPrecio_venta_publico());
        nuevoPedido.setFecha(java.sql.Date.valueOf(LocalDate.now()));
        nuevoPedido.setRecogido(false);

        // Guardar el pedido
        PedidoService pedidoService = new PedidoService();
        pedidoService.guardarOActualizar(nuevoPedido, lista_pedidos_alumno);

        AlumnoService alumnoService = new AlumnoService();
        Alumno mi_alumno =  alumnoService.actualizarAlumnoPorId(alumno.getId());

        // Recargar los pedidos y los bocadillos después de hacer la reserva
        cargarPedidos(mi_alumno);  // Actualizar la lista de pedidos
        cargarBocadillos();     // Actualizar la lista de bocadillos

        // Volver a mostrar los bocadillos con los datos actualizados
        mostrarBocadillos(lista_bocadillos, lista_pedidos, mi_alumno);  // Mostrar bocadillos actualizados
    }

    public void cancelarPedido() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Cancelar Reserva");
        alerta.setHeaderText(null); // Sin encabezado para un diseño más limpio
        alerta.setContentText("¿Seguro que quieres cancelar la reserva?");

        // Personalizar los botones
        ButtonType botonAceptar = new ButtonType("Sí, cancelar reserva", ButtonBar.ButtonData.OK_DONE);
        ButtonType botonCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

        alerta.getButtonTypes().setAll(botonAceptar, botonCancelar);

        // Aplicar estilo con CSS
        DialogPane dialogPane = alerta.getDialogPane();
        dialogPane.lookup(".content.label").setStyle("-fx-font-size: 14px; -fx-text-fill: #333333; -fx-font-weight: bold;");

        // Cambiar el color de los botones
        Button okButton = (Button) dialogPane.lookupButton(botonAceptar);
        okButton.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px;");

        Button cancelButton = (Button) dialogPane.lookupButton(botonCancelar);
        cancelButton.setStyle("-fx-background-color: #DDDDDD; -fx-text-fill: black; -fx-font-size: 13px;");

        // Mostrar alerta y esperar la respuesta del usuario
        Optional<ButtonType> resultado = alerta.showAndWait();

        // Si el usuario elige cerrar sesión
        if (resultado.isPresent() && resultado.get() == botonAceptar) {
            Pedido pedido_a_cancelar = null;
            for (Pedido pedido : lista_pedidos) {
                // Convertir la fecha de tipo Date a LocalDate
                LocalDate fechaPedido = pedido.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                if (fechaPedido.equals(LocalDate.now())) {
                    pedido_a_cancelar = pedido; // Guardamos el pedido encontrado
                    break;
                }
            }

            PedidoService pedidoService = new PedidoService();
            pedidoService.eliminarPedido(pedido_a_cancelar);
            AlumnoService alumnoService = new AlumnoService();
            Alumno mi_alumno = alumnoService.actualizarAlumnoPorId(alumno.getId());
            cargarPedidos(mi_alumno);
            mostrarBocadillos(lista_bocadillos, lista_pedidos, mi_alumno);
        }
    }

}