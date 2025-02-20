package com.example.prueba_1.controller;

import com.example.prueba_1.model.Alumno;
import com.example.prueba_1.model.Pedido;
import com.example.prueba_1.service.AdministradorService;
import com.example.prueba_1.service.AlumnoService;
import com.example.prueba_1.service.PedidoService;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class AdministradorController {
    @FXML
    private Button botonCerrarSesion;

    @FXML
    private TableView<Alumno> tablaAlumnos;

    // Nueva columna para mostrar el ID del alumno
    @FXML
    private TableColumn<Alumno, Long> columnaId;

    @FXML
    private TableColumn<Alumno, String> columnaNombre;

    // Se actualiza para mostrar el nombre del curso asociado
    @FXML
    private TableColumn<Alumno, String> columnaCurso;

    // Se actualiza para mostrar el Bocadillo del pedido de hoy
    @FXML
    private TableColumn<Alumno, String> columnaBocadillo;

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

    private ObservableList<Alumno> listaAlumnos;

    private AdministradorService administradorService = new AdministradorService();

    private PedidoService pedidoService = new PedidoService();

    @FXML
    public void initialize() {
        botonCerrarSesion.setOnAction(event -> cerrarSesion());
        AlumnoService alumnoService = new AlumnoService();
        listaAlumnos = FXCollections.observableArrayList(alumnoService.getPaginated(1, OFFSET));

        // Configurar la columna de ID
        columnaId.setCellValueFactory(new PropertyValueFactory<>("id"));

        // Configurar la columna de Nombre
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        // Configurar la columna de Curso para mostrar el nombre del curso relacionado
        columnaCurso.setCellValueFactory(cellData -> {
            if (cellData.getValue().getCurso() != null) {
                return new ReadOnlyStringWrapper(cellData.getValue().getCurso().getNombre());
            } else {
                return new ReadOnlyStringWrapper("Sin curso");
            }
        });

        // Configurar la columna de Bocadillo para mostrar el pedido de hoy (nombre del bocadillo)
        columnaBocadillo.setCellValueFactory(cellData -> {
            Alumno alumno = cellData.getValue();
            // Obtenemos todos los pedidos de hoy
            List<Pedido> pedidosDeHoy = pedidoService.getPedidosDeHoy();
            // Filtramos para obtener el pedido del alumno
            Pedido pedidoHoy = pedidosDeHoy.stream()
                    .filter(p -> p.getAlumno().getId().equals(alumno.getId()))
                    .findFirst()
                    .orElse(null);
            return pedidoHoy != null
                    ? new ReadOnlyStringWrapper(pedidoHoy.getBocadillo().getNombre())
                    : new ReadOnlyStringWrapper("Sin pedido");
        });

        agregarBotonesAccion();
        tablaAlumnos.setItems(listaAlumnos);
    }

    private void agregarBotonesAccion() {
        Callback<TableColumn<Alumno, Void>, TableCell<Alumno, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnEliminar = new Button("Eliminar");

            {
                btnEditar.setOnAction(event -> {
                    Alumno alumno = getTableView().getItems().get(getIndex());
                    editarAlumno(alumno);
                });

                btnEliminar.setOnAction(event -> {
                    Alumno alumno = getTableView().getItems().get(getIndex());
                    borrarAlumno(alumno);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox botones = new HBox(10, btnEditar, btnEliminar);
                    setGraphic(botones);
                }
            }
        };

    }

    private void editarAlumno(Alumno alumno) {
        // Lógica de edición (abrir ventana o formulario)
        System.out.println("Editar alumno: " + alumno.getNombre());
    }

    private void borrarAlumno(Alumno alumno) {
        // Lógica de borrado
        System.out.println("Borrar alumno: " + alumno.getNombre());
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

    @FXML
    public void siguientePagina(){
        int page = Integer.parseInt(txtPagina.getText());
        page++;

        AlumnoService alumnoService = new AlumnoService();
        List<Alumno> listaPaginated = alumnoService.getPaginated(page, OFFSET);
        if (!listaPaginated.isEmpty()) {
            ObservableList<Alumno> observableList = FXCollections.observableArrayList(listaPaginated);
            tablaAlumnos.setItems(observableList);
            txtPagina.setText(String.valueOf(page));
            btnAnterior.setDisable(false);
        } else {
            btnSiguiente.setDisable(true);
        }
    }


    @FXML
    public void anteriorPagina(){
        int page = Integer.parseInt(txtPagina.getText());
        page--;

        if (page > 0) {
            AlumnoService alumnoService = new AlumnoService();
            List<Alumno> listaPaginated = alumnoService.getPaginated(page, OFFSET);
            if (!listaPaginated.isEmpty()) {
                ObservableList<Alumno> observableList = FXCollections.observableArrayList(listaPaginated);
                tablaAlumnos.setItems(observableList);
                txtPagina.setText(String.valueOf(page));
                btnSiguiente.setDisable(false);
            } else {
                btnAnterior.setDisable(true);
            }
        } else {
            btnAnterior.setDisable(true);
        }
    }

}
