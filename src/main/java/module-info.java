module com.example.prueba_1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires org.mariadb.jdbc;


    opens com.example.prueba_1 to javafx.fxml;
    exports com.example.prueba_1;
    exports com.example.prueba_1.controller;
    opens com.example.prueba_1.controller to javafx.fxml;

    exports com.example.prueba_1.dao;
    opens com.example.prueba_1.dao to javafx.fxml, org.hibernate.orm.core;
    exports com.example.prueba_1.util;
    opens com.example.prueba_1.util to javafx.fxml, org.hibernate.orm.core;
    exports com.example.prueba_1.model;
    opens com.example.prueba_1.model to javafx.fxml, org.hibernate.orm.core;
    exports com.example.prueba_1.service;
    opens com.example.prueba_1.service to javafx.fxml, org.hibernate.orm.core;
}