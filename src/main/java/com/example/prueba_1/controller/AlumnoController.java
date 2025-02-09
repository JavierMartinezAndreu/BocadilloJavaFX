package com.example.prueba_1.controller;

import com.example.prueba_1.model.Alumno;
import com.example.prueba_1.model.Bocadillo;
import com.example.prueba_1.model.Pedido;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AlumnoController {
    @FXML
    private HBox bocadillosHBox; // El contenedor en el que se agregarán los VBox dinámicos.

    @FXML
    private Label mensajeBienvenidaLabel;

    @FXML
    private Button botonCerrarSesion;

    @FXML
    private List<Bocadillo> lista_bocadillos;

    @FXML
    private List<Pedido> lista_pedidos;

    // Método para inicializar el controlador
    @FXML
    public void initialize(Alumno alumno) {
        /*botonCerrarSesion.setOnAction(event -> cerrarSesion());
        mostrarDatosAlumno(alumno);
        cargarBocadillos();
        cargarPedidos(alumno, lista_bocadillos);
        mostrarBocadillos(lista_bocadillos, lista_pedidos, alumno);*/
    }

    public void mostrarDatosAlumno(Alumno alumno) {
        mensajeBienvenidaLabel.setText("Bienvenido " + alumno.getNombre());
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

    /*public void cargarBocadillos(){
        lista_bocadillos = new ArrayList<>();


        lista_bocadillos.add(new Bocadillo("Bocadillo mixtoL", null, "Pan, queso y jamón york", "Frío", 2.50F, "Gluten, lácteos", "monday"));
        lista_bocadillos.add(new Bocadillo("Bocadillo de BaconL", null, "Pan, y bacon", "Caliente", 2.50F, "Gluten", "monday"));

        lista_bocadillos.add(new Bocadillo("Bocadillo mixtoM", null, "Pan, queso y jamón york", "Frío", 2.50F, "Gluten, lácteos", "tuesday"));
        lista_bocadillos.add(new Bocadillo("Bocadillo de BaconM", null, "Pan, y bacon", "Caliente", 2.50F, "Gluten", "tuesday"));

        lista_bocadillos.add(new Bocadillo("Bocadillo mixtoX", null, "Pan, queso y jamón york", "Frío", 2.50F, "Gluten, lácteos", "wednesday"));
        lista_bocadillos.add(new Bocadillo("Bocadillo de BaconX", null, "Pan, y bacon", "Caliente", 2.50F, "Gluten", "wednesday"));

        lista_bocadillos.add(new Bocadillo("Bocadillo mixtoJ", null, "Pan, queso y jamón york", "Frío", 2.50F, "Gluten, lácteos", "thursday"));
        lista_bocadillos.add(new Bocadillo("Bocadillo de BaconJ", null, "Pan, y bacon", "Caliente", 2.50F, "Gluten", "thursday"));

        lista_bocadillos.add(new Bocadillo("Bocadillo mixtoV", null, "Pan, queso y jamón york", "Frío", 2.50F, "Gluten, lácteos", "friday"));
        lista_bocadillos.add(new Bocadillo("Bocadillo de BaconV", null, "Pan, y bacon", "Caliente", 2.50F, "Gluten", "friday"));

        lista_bocadillos.add(new Bocadillo("Bocadillo mixtoS", null, "Pan, queso y jamón york", "Frío", 2.50F, "Gluten, lácteos", "saturday"));
        lista_bocadillos.add(new Bocadillo("Bocadillo de BaconS", null, "Pan, y bacon", "Caliente", 2.50F, "Gluten", "saturday"));

        lista_bocadillos.add(new Bocadillo("Bocadillo mixtoD", null, "Pan, queso y jamón york", "Frío", 2.50F, "Gluten, lácteos", "sunday"));
        lista_bocadillos.add(new Bocadillo("Bocadillo de BaconD", null, "Pan, y bacon", "Caliente", 2.50F, "Gluten", "sunday"));
    }

    public void cargarPedidos(Alumno alumno, List<Bocadillo> lista_bocadillos){
        lista_pedidos = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.FEBRUARY, 6); // Recuerda que los meses en Calendar son 0-indexados
        Date date = calendar.getTime();

        lista_pedidos.add(new Pedido(20L, alumno, lista_bocadillos.get(0), lista_bocadillos.get(0).getPrecio(), date, null));
        System.out.println(lista_pedidos.get(0).toString());
    }

    public void mostrarBocadillos(List<Bocadillo> lista_bocadillos, List<Pedido> lista_pedidos, Alumno alumno) {
        // Obtener el día actual
        String diaHoy = LocalDate.now().getDayOfWeek().name().toLowerCase();

        // Limpiar el HBox antes de agregar los nuevos VBox
        bocadillosHBox.getChildren().clear();

        // Establecer el espacio entre los VBox dentro del HBox
        bocadillosHBox.setSpacing(40);

        // Filtrar bocadillos que coinciden con el día de la semana
        for (Bocadillo bocadillo : lista_bocadillos) {
            if (bocadillo.getDia_semana().equals(diaHoy)) {
                // Crear un nuevo VBox para cada bocadillo
                VBox vbox = new VBox();
                vbox.setStyle("-fx-background-color: #ffffff; -fx-border-radius: 15px; -fx-background-radius: 15px; -fx-border-color: #4CAF50; -fx-border-width: 2px;");
                vbox.setPadding(new Insets(8));

                // Establecer un tamaño fijo para cada VBox (en este caso, para asegurar el mismo tamaño)
                vbox.setPrefHeight(150);
                vbox.setPrefWidth(250);

                // Crear y agregar etiquetas con la información del bocadillo
                Label nombreLabel = new Label("Nombre: " + bocadillo.getNombre());
                Label ingredientesLabel = new Label("Ingredientes: " + bocadillo.getIngredientes());
                Label tipoLabel = new Label("Tipo: " + bocadillo.getTipo());
                Label alergenosLabel = new Label("Alérgenos: " + bocadillo.getAlergenos());
                Label precioLabel = new Label("Precio: " + String.format("%.2f€", bocadillo.getPrecio()));

                // Agregar las etiquetas al VBox
                vbox.getChildren().addAll(nombreLabel, ingredientesLabel, tipoLabel, alergenosLabel, precioLabel);

                // Establecer un alineamiento de las etiquetas (para asegurarse de que el texto no se corta)
                vbox.setAlignment(Pos.TOP_CENTER);

                // Verificar si ya existe un pedido del mismo Bocadillo y Alumno para el día de hoy
                boolean pedidoExistente = false;
                for (Pedido pedido : lista_pedidos) {
                    if (pedido.getBocadillo().equals(bocadillo) && pedido.getAlumno().equals(alumno) && pedido.getFecha().equals(LocalDate.now())) {
                        pedidoExistente = true;
                        break;
                    }
                }

                // Crear el botón para hacer el pedido
                Button botonPedido = new Button(pedidoExistente ? "Reservado" : "Pedir");
                VBox.setMargin(botonPedido, new Insets(10, 0, 0, 0));

                // Si ya está reservado, deshabilitar el botón y aplicar un estilo sombreado al VBox
                if (pedidoExistente) {
                    botonPedido.setDisable(true);
                    vbox.setStyle("-fx-background-color: #f0f0f0; -fx-border-radius: 15px; -fx-background-radius: 15px; -fx-border-color: #999999; -fx-border-width: 2px;");
                } else {
                    botonPedido.setOnAction(event -> {
                        // Llamamos al método ReservarBocadillo
                        ReservarBocadillo(alumno, bocadillo, lista_pedidos);
                    });
                }

                // Agregar el botón al VBox
                vbox.getChildren().add(botonPedido);

                // Agregar el VBox al HBox
                bocadillosHBox.getChildren().add(vbox);
            }
        }
    }


    public void ReservarBocadillo(Alumno alumno, Bocadillo bocadillo, List<Pedido> lista_pedidos) {
        // Verificar si ya existe un pedido del mismo Bocadillo y Alumno para el día de hoy
        boolean pedido_existente = false;
        for (Pedido pedido : lista_pedidos) {
            if (pedido.getAlumno().equals(alumno) && pedido.getFecha().equals(LocalDate.now())) {
                pedido.setBocadillo(bocadillo);
                pedido.setPrecio_pedido(bocadillo.getPrecio());
                pedido_existente = true;
                break;
            }
        }

        if (!pedido_existente){
            // Si no existe el pedido, crear uno nuevo
            LocalDate localDateNow = LocalDate.now();
            Date currentDate = Date.from(localDateNow.atStartOfDay(ZoneId.systemDefault()).toInstant());

            Pedido nuevoPedido = new Pedido(23L, alumno, bocadillo, bocadillo.getPrecio(), currentDate, null);
            lista_pedidos.add(nuevoPedido);
            System.out.println("Nuevo pedido creado: " + nuevoPedido);
        }


        // Luego de hacer el pedido, recargamos la lista de bocadillos
        mostrarBocadillos(lista_bocadillos, lista_pedidos, alumno);
    }*/
}