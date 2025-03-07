package com.example.prueba_1.controller;

import com.example.prueba_1.model.Alumno;
import com.example.prueba_1.model.Pedido;
import com.example.prueba_1.service.PedidoService;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

public class AlumnoPedidosController {

    @FXML
    private Label mensajeBienvenidaLabel;

    @FXML
    private Button botonPedirBocadillo;

    @FXML
    private Button botonCerrarSesion;

    @FXML
    private ComboBox<String> comboBoxFiltro;

    @FXML
    private Button botonFiltrar;

    @FXML
    private TableView<Pedido> tablaPedidos;

    @FXML
    private TableColumn<Pedido, String> columnaBocadillo;

    @FXML
    private TableColumn<Pedido, String> columnaTipo;

    @FXML
    private TableColumn<Pedido, Double> columnaPrecio;

    @FXML
    private TableColumn<Pedido, String> columnaFecha;

    @FXML
    private Label lblTotalGastado;

    private Alumno alumno;

    // Método para inicializar el controlador
    @FXML
    public void initialize(Alumno mi_alumno) {
        this.alumno = mi_alumno;
        botonPedirBocadillo.setOnAction(event -> irAPedir());
        botonCerrarSesion.setOnAction(event -> cerrarSesion());
        botonFiltrar.setOnAction(event -> aplicarFiltro());
        mostrarDatosAlumno(mi_alumno);
        configurarTabla();
        cargarPedidos();
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cerrarSesion() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Cerrar Sesión");
        alerta.setHeaderText(null);
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

    private void configurarTabla() {
        tablaPedidos.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        // Configurar la columna Bocadillo (nombre)
        columnaBocadillo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getBocadillo().getNombre()));
        columnaBocadillo.setPrefWidth(200);

        // Configurar la columna Tipo de Bocadillo
        columnaTipo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getBocadillo().getTipo()));
        columnaTipo.setPrefWidth(150);

        // Configurar la columna Precio (€)
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio_pedido"));
        columnaPrecio.setCellFactory(column -> new TextFieldTableCell<>(
                new StringConverter<Double>() {
                    @Override
                    public String toString(Double value) {
                        return value != null ? String.format("%.2f €", value) : "";
                    }

                    @Override
                    public Double fromString(String string) {
                        return string.replace("€", "").trim().isEmpty() ? 0.0 :
                                Double.parseDouble(string.replace("€", "").trim());
                    }
                }
        ));
        columnaPrecio.setPrefWidth(100);

        // Configurar la columna Fecha con formato DD/MM/AAAA
        columnaFecha.setCellValueFactory(cellData ->
                new SimpleStringProperty(new SimpleDateFormat("dd/MM/yyyy").format(cellData.getValue().getFecha())));
        columnaFecha.setPrefWidth(150);
    }

    private void aplicarFiltro() {
        PedidoService pedidoService = new PedidoService();
        // Obtener la selección del filtro de precio
        String filtroSeleccionado = comboBoxFiltro.getValue();

        // Filtrar según el tipo de filtro seleccionado
        List<Pedido> pedidosFiltrados = null;

        if ("Menor a Mayor".equals(filtroSeleccionado)) {
            // Filtrar de menor a mayor precio
            pedidosFiltrados = pedidoService.getPedidosOrdenadosPorPrecio(alumno.getId(), true);
        } else if ("Mayor a Menor".equals(filtroSeleccionado)) {
            // Filtrar de mayor a menor precio
            pedidosFiltrados = pedidoService.getPedidosOrdenadosPorPrecio(alumno.getId(), false);
        }
        if ("Caliente".equals(filtroSeleccionado)) {
            // Filtrar de menor a mayor precio
            pedidosFiltrados = pedidoService.getPedidosPorTipo(alumno.getId(), "Caliente");
        } else if ("Frio".equals(filtroSeleccionado)) {
            // Filtrar de mayor a menor precio
            pedidosFiltrados = pedidoService.getPedidosPorTipo(alumno.getId(), "Frio");
        }
        if ("Fecha descendente".equals(filtroSeleccionado)) {
            // Filtrar de menor a mayor precio
            pedidosFiltrados = pedidoService.getPedidosPorFecha(alumno.getId(), true);
        } else if ("Fecha ascendente".equals(filtroSeleccionado)) {
            // Filtrar de mayor a menor precio
            pedidosFiltrados = pedidoService.getPedidosPorFecha(alumno.getId(), false);
        }

        if (pedidosFiltrados != null) {
            tablaPedidos.getItems().setAll(pedidosFiltrados);
        }
    }


    private void cargarPedidos() {
        List<Pedido> pedidos = alumno.getPedidos();
        if (pedidos != null && !pedidos.isEmpty()) {
            tablaPedidos.getItems().setAll(pedidos);
        }
        actualizarTotalGastado(pedidos);
    }

    private void actualizarTotalGastado(List<Pedido> pedidos) {
        double total = 0;
        LocalDate hoy = LocalDate.now();
        for (Pedido pedido : pedidos) {
            LocalDate fechaPedido = pedido.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (fechaPedido.getMonth() == hoy.getMonth() && fechaPedido.getYear() == hoy.getYear()) {
                total += pedido.getPrecio_pedido();
            }
        }
        lblTotalGastado.setText(String.format("%.2f €", total));
    }

}