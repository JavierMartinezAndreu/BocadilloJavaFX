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
import java.util.List;

public class AlumnoPedidosController {

    @FXML
    private Label mensajeBienvenidaLabel;

    @FXML
    private Button botonPedirBocadillo;

    @FXML
    private Button botonCerrarSesion;

    @FXML
    private ComboBox<String> comboBoxFiltroPrecio;

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
        String filtroSeleccionado = comboBoxFiltroPrecio.getValue();

        // Filtrar según el tipo de filtro seleccionado
        List<Pedido> pedidosFiltrados = null;

        if ("Menor a Mayor".equals(filtroSeleccionado)) {
            // Filtrar de menor a mayor precio
            pedidosFiltrados = pedidoService.getPedidosOrdenadosPorPrecio(alumno.getId(), true);
        } else if ("Mayor a Menor".equals(filtroSeleccionado)) {
            // Filtrar de mayor a menor precio
            pedidosFiltrados = pedidoService.getPedidosOrdenadosPorPrecio(alumno.getId(), false);
        }

        if (pedidosFiltrados != null) {
            tablaPedidos.getItems().setAll(pedidosFiltrados);
        }
    }


    private void cargarPedidos() {
        List<Pedido> pedidos = alumno.getPedidos();
        if (pedidos != null && !pedidos.isEmpty()) {
            tablaPedidos.getItems().setAll(pedidos); // Agregar solo los pedidos existentes
        }
    }

}