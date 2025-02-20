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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class AdministradorController {

    @FXML
    private TableView<Alumno> tablaAlumnos;

    @FXML
    private TableColumn<Alumno, String> columnaNombre;

    @FXML
    private TableColumn<Alumno, String> columnaCurso;

    @FXML
    private TableColumn<Alumno, String> columnaBocadillo;

    @FXML
    private TableColumn<Alumno, Void> columnaAcciones;

    private ObservableList<Alumno> listaAlumnos;

    private AdministradorService administradorService= new AdministradorService();

    private PedidoService pedidoService= new PedidoService();


    @FXML
    public void initialize() {
        AlumnoService alumnoService = new AlumnoService();
        listaAlumnos = FXCollections.observableArrayList(alumnoService.getAll());

        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
        columnaBocadillo.setCellValueFactory(cellData -> {
            Pedido pedido = cellData.getValue().getPedidodehoy();
            return pedido != null ? new ReadOnlyStringWrapper(pedido.getBocadillo().getNombre()) : new ReadOnlyStringWrapper("Sin pedido");
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

        columnaAcciones.setCellFactory(cellFactory);
    }

    private void editarAlumno(Alumno alumno) {
        // Lógica de edición (abrir ventana o formulario)
        System.out.println("Editar alumno: " + alumno.getNombre());
    }



    }

