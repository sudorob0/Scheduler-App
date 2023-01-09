module app.code {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens controllers to javafx.fxml;
    exports controllers;


    opens main to javafx.fxml;
    exports main;

    opens models to javafx.fxml;
    exports models;


}