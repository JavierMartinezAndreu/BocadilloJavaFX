module com.example.prueba_1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.prueba_1 to javafx.fxml;
    exports com.example.prueba_1;
}