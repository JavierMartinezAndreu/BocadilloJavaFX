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
    private ObservableList<Bocadillo> lista_bocadillos;

    @FXML
    private ObservableList<Pedido> lista_pedidos;

    private Alumno alumno;


    // Método para inicializar el controlador
    @FXML
    public void initialize(Alumno alumno) {
        this.alumno = alumno;
        botonCerrarSesion.setOnAction(event -> cerrarSesion());
        mostrarDatosAlumno(alumno);
        cargarBocadillos();
        cargarPedidos(alumno);
        mostrarBocadillos(lista_bocadillos, lista_pedidos, alumno);
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

    public void cargarBocadillos(){
        //Instanciar bocadillos y guardarlos en una lista
        BocadilloService bocadilloService = new BocadilloService();
        lista_bocadillos = FXCollections.observableArrayList(bocadilloService.getAll());
    }

    public void cargarPedidos(Alumno alumno){
        AlumnoService alumnoService = new AlumnoService();
        lista_pedidos = FXCollections.observableArrayList(alumnoService.obtenerPedidosAlumno(alumno));
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
            if (bocadillo.getDia_semana().toLowerCase().equals(diaHoy)) {
                // Crear un nuevo VBox para cada bocadillo
                VBox vbox = new VBox();
                vbox.setStyle("-fx-background-color: #ffffff; -fx-border-radius: 15px; -fx-background-radius: 15px; -fx-border-color: #4CAF50; -fx-border-width: 2px;");
                vbox.setPadding(new Insets(8));

                // Establecer un tamaño fijo para cada VBox (en este caso, para asegurar el mismo tamaño)
                vbox.setPrefHeight(150);
                vbox.setPrefWidth(250);

                //Declarar BocadilloService para obtener alérgenos
                BocadilloService bocadilloService = new BocadilloService();

                // Crear y agregar etiquetas con la información del bocadillo
                Label nombreLabel = new Label("Nombre: " + bocadillo.getNombre());
                Label ingredientesLabel = new Label("Ingredientes: " + bocadillo.getIngredientes());
                Label tipoLabel = new Label("Tipo: " + bocadillo.getTipo());
                Label alergenosLabel = new Label(bocadilloService.obtenerAlergenosComoString(bocadillo));
                Label precioLabel = new Label("Precio: " + String.format("%.2f€", bocadillo.getPrecio_venta_publico()));

                // Agregar las etiquetas al VBox
                vbox.getChildren().addAll(nombreLabel, ingredientesLabel, tipoLabel, alergenosLabel, precioLabel);

                // Establecer un alineamiento de las etiquetas (para asegurarse de que el texto no se corta)
                vbox.setAlignment(Pos.TOP_CENTER);

                // Verificar si ya existe un pedido del mismo Bocadillo y Alumno para el día de hoy
                boolean pedido_existente = false;
                if (!lista_bocadillos.isEmpty()){
                    for (Pedido pedido : lista_pedidos) {
                        // Convertir la fecha de tipo Date a LocalDate
                        LocalDate fechaPedido = pedido.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        if (pedido.getBocadillo().getId().equals(bocadillo.getId()) && pedido.getAlumno().getId().equals(alumno.getId()) && fechaPedido.equals(LocalDate.now())) {
                            pedido_existente = true;
                            break;
                        }
                    }
                }

                // Crear el botón para hacer el pedido
                Button botonPedido = new Button(pedido_existente ? "Reservado" : "Pedir");
                VBox.setMargin(botonPedido, new Insets(10, 0, 0, 0));

                // Si ya está reservado, deshabilitar el botón y aplicar un estilo sombreado al VBox
                if (pedido_existente) {
                    botonPedido.setDisable(true);
                    vbox.setStyle("-fx-background-color: #f0f0f0; -fx-border-radius: 15px; -fx-background-radius: 15px; -fx-border-color: #999999; -fx-border-width: 2px;");
                } else {
                    botonPedido.setOnAction(event -> {
                        // Llamamos al método ReservarBocadillo
                        ReservarBocadillo(alumno, bocadillo, (ObservableList<Pedido>) lista_pedidos);
                    });
                }

                // Agregar el botón al VBox
                vbox.getChildren().add(botonPedido);

                // Agregar el VBox al HBox
                bocadillosHBox.getChildren().add(vbox);
            }
        }
    }


    public void ReservarBocadillo(Alumno alumno, Bocadillo bocadillo, ObservableList<Pedido> lista_pedidos_alumno) {

        // Crear y guardar el nuevo pedido
        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setAlumno(alumno);
        nuevoPedido.setBocadillo(bocadillo);
        nuevoPedido.setPrecio_pedido(bocadillo.getPrecio_venta_publico());
        nuevoPedido.setFecha(java.sql.Date.valueOf(LocalDate.now()));
        nuevoPedido.setFecha_recogida(null);

        // Guardar el pedido
        PedidoService pedidoService = new PedidoService();
        pedidoService.guardarOActualizar(nuevoPedido, lista_pedidos_alumno);

        // Recargar el alumno desde la base de datos
        AlumnoService alumnoService = new AlumnoService();
        this.alumno = alumnoService.obtenerAlumnoPorId(alumno.getId());

        // Recargar los pedidos y los bocadillos después de hacer la reserva
        cargarPedidos(alumno);  // Actualizar la lista de pedidos
        cargarBocadillos();     // Actualizar la lista de bocadillos

        // Volver a mostrar los bocadillos con los datos actualizados
        mostrarBocadillos(lista_bocadillos, lista_pedidos, alumno);  // Mostrar bocadillos actualizados
    }

}