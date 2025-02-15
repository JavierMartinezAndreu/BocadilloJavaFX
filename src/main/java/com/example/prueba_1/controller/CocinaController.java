package com.example.prueba_1.controller;

import com.example.prueba_1.service.PedidoService;
import com.example.prueba_1.model.Pedido;
import com.example.prueba_1.model.Alumno;
import com.example.prueba_1.model.Bocadillo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class CocinaController {
    @FXML
    private Button botonCerrarSesion;

    @FXML
    private TableView<Pedido> tablaPedidos;

    @FXML
    private TableColumn<Pedido, String> columnaAlumno;

    @FXML
    private TableColumn<Pedido, String> columnaCurso;

    @FXML
    private TableColumn<Pedido, String> columnaBocadillo;

    @FXML
    private TableColumn<Pedido, String> columnaTipo;

    @FXML
    private Button btnAnterior;
    @FXML
    private Button btnSiguiente;
    @FXML
    private TextField txtPagina;
    @FXML
    private Label lblTotal;
    private static final int OFFSET=10;

    private HashMap<String, String> filtros = new HashMap<>();

    private long totalPedidos;


    @FXML
    public void initialize() {
        botonCerrarSesion.setOnAction(event -> cerrarSesion());

        PedidoService pedidoService = new PedidoService();
        List<Pedido> pedidos = pedidoService.getPaginated(1, OFFSET, null);

        configurarTabla();
        cargarPedidos(pedidos);
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

    private void configurarTabla() {
        // Evitar que se agregue una columna extra innecesaria
        tablaPedidos.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        // Configurar la columna Bocadillo (nombre)
        columnaBocadillo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getBocadillo().getNombre()));
        columnaBocadillo.setPrefWidth(200); // Fijar ancho para evitar expansión

        // Configurar la columna Tipo de Bocadillo
        columnaTipo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getBocadillo().getTipo()));
        columnaTipo.setPrefWidth(60);

        // Configurar la columna de Alumno
        columnaAlumno.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAlumno().getNombre()));
        columnaAlumno.setPrefWidth(200);

        // Configurar la columna clase de alumno
        columnaCurso.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAlumno().getCurso().getNombre()));
        columnaCurso.setPrefWidth(60);
    }

    private void cargarPedidos(List<Pedido> pedidos) {
        if (pedidos != null && !pedidos.isEmpty()) {
            tablaPedidos.getItems().setAll(pedidos); // Agregar solo los pedidos existentes
        }
    }

    @FXML
    public void siguientePagina(){
        int page = Integer.parseInt(txtPagina.getText());
        page++;

        PedidoService pedidoService = new PedidoService();
        List<Pedido> pedidos = pedidoService.getPaginated(page, OFFSET, null);
        if (!pedidos.isEmpty()) {
            cargarPedidos(pedidos);

            txtPagina.setText(page + "");
            btnAnterior.setDisable(false);
        } else {
            btnSiguiente.setDisable(true);
        }
    }

    @FXML
    public void anteriorPagina(){
        int page = Integer.parseInt(txtPagina.getText());
        page--;

        if (page>0) {
            PedidoService pedidoService = new PedidoService();
            List<Pedido> pedidos = pedidoService.getPaginated(page, OFFSET, null);
            if (!pedidos.isEmpty()) {
                cargarPedidos(pedidos);
                txtPagina.setText(page + "");
                btnSiguiente.setDisable(false);
            } else {
                btnAnterior.setDisable(true);
            }
        } else {
            btnAnterior.setDisable(true);
        }
    }
}