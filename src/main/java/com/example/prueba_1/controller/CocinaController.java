package com.example.prueba_1.controller;

import com.example.prueba_1.model.Curso;
import com.example.prueba_1.service.CursoService;
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

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
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
    private TextField txtNombre;

    @FXML
    private ComboBox comboBoxCurso;

    @FXML
    private ComboBox comboBoxTipo;

    @FXML
    private TableColumn<Pedido, String> columnaAlumno;

    @FXML
    private TableColumn<Pedido, String> columnaCurso;

    @FXML
    private TableColumn<Pedido, String> columnaBocadillo;

    @FXML
    private TableColumn<Pedido, String> columnaTipo;

    @FXML
    private TableColumn<Pedido, Boolean> columnaRecogido;

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

        // Rellenar los combo box
        CursoService cursoService = new CursoService();
        List<Curso> cursos = cursoService.getAll();
        comboBoxCurso.getItems().clear();
        for (Curso curso: cursos){
            comboBoxCurso.getItems().add(curso.getNombre());
        }
        comboBoxTipo.getItems().clear();  // Limpiar los elementos existentes
        comboBoxTipo.getItems().add("caliente");
        comboBoxTipo.getItems().add("frio");


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

        // Evitar la selección de filas pero permitir celdas interactivas
        tablaPedidos.getSelectionModel().setCellSelectionEnabled(true);
        tablaPedidos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Configurar columnas de texto
        columnaBocadillo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getBocadillo().getNombre()));
        columnaBocadillo.setPrefWidth(190);

        columnaTipo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getBocadillo().getTipo()));
        columnaTipo.setPrefWidth(80);

        columnaAlumno.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAlumno().getNombre()));
        columnaAlumno.setPrefWidth(230);

        columnaCurso.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAlumno().getCurso().getNombre()));
        columnaCurso.setPrefWidth(60);

        // Configurar columna Recogido con CheckBox funcional
        columnaRecogido.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().getRecogido()));

        columnaRecogido.setCellFactory(tc -> new TableCell<Pedido, Boolean>() {
            private final CheckBox checkBox = new CheckBox();

            {
                checkBox.setOnAction(event -> {
                    Pedido pedido = getTableView().getItems().get(getIndex());
                    boolean nuevoValor = checkBox.isSelected();

                    pedido.setRecogido(nuevoValor); // Actualizar el modelo

                    // Guardar el cambio en la base de datos
                    PedidoService pedidoService = new PedidoService();
                    pedidoService.entregarPedido(pedido);

                    // Refrescar la tabla para actualizar la vista
                    getTableView().refresh();
                });
            }

            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    checkBox.setSelected(item);
                    setGraphic(checkBox);
                }
            }
        });
    }




    private void cargarPedidos(List<Pedido> pedidos) {
        if (pedidos != null && !pedidos.isEmpty()) {
            tablaPedidos.getItems().setAll(pedidos); // Agregar solo los pedidos existentes
        }
    }

    @FXML
    public void filtrarBuscador() {
        filtros.clear();

        // Filtro por el nombre de alumno (si existe en el cuadro de texto)
        if (!txtNombre.getText().isEmpty()) {
            filtros.put("nombre", txtNombre.getText());
        }

        // Filtro por el tipo de bocadillo
        if (comboBoxTipo.getValue() != null) {
            filtros.put("tipoBocadillo", (String) comboBoxTipo.getValue());
        }

        // Filtro por el curso (si se ha seleccionado un curso en el combo box)
        if (comboBoxCurso.getValue() != null) {
            filtros.put("nombreCurso", (String) comboBoxCurso.getValue());
        }

        // Llamar al servicio de Pedido para obtener el total de pedidos y los pedidos paginados
        PedidoService pedidoService = new PedidoService();

        // Obtener el total de pedidos que coinciden con los filtros aplicados
        totalPedidos = pedidoService.count(filtros);

        // Obtener la lista de pedidos paginados que coinciden con los filtros
        List<Pedido> pedidos = pedidoService.getPaginated(1, OFFSET, filtros);

        // Cargar los pedidos en la tabla
        cargarPedidos(pedidos);

        // Actualizar la interfaz de usuario
        txtPagina.setText("1");
        btnSiguiente.setDisable(false);
        lblTotal.setText("Total registros: " + totalPedidos + " - Total páginas: " + Math.round(Math.ceil((float) totalPedidos / (float) OFFSET)));
    }

    @FXML
    public void resetearFiltros() {
        // Limpiar los campos de texto y los ComboBox
        txtNombre.clear();
        comboBoxTipo.getSelectionModel().clearSelection();
        comboBoxCurso.getSelectionModel().clearSelection();
        comboBoxCurso.setValue("Seleccione un curso");
        comboBoxTipo.setValue("Seleccione tipo de bocadillo");



        // Limpiar el HashMap de filtros
        filtros.clear();

        // Volver a cargar los pedidos sin filtros
        PedidoService pedidoService = new PedidoService();
        List<Pedido> pedidos = pedidoService.getPaginated(1, OFFSET, null);

        // Recargar la tabla de pedidos
        cargarPedidos(pedidos);

        // Reiniciar la interfaz de usuario
        txtPagina.setText("1");
        btnSiguiente.setDisable(false);
        lblTotal.setText("Total registros: " + pedidos.size() + " - Total páginas: " + Math.round(Math.ceil((float) pedidos.size() / (float) OFFSET)));
    }



    @FXML
    public void siguientePagina(){
        int page = Integer.parseInt(txtPagina.getText());
        page++;

        PedidoService pedidoService = new PedidoService();
        List<Pedido> pedidos = pedidoService.getPaginated(page, OFFSET, filtros);
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
            List<Pedido> pedidos = pedidoService.getPaginated(page, OFFSET, filtros);
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