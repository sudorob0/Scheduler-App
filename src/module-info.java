module app.code {
    requires javafx.controls;
    requires javafx.fxml;


    opens controllers to javafx.fxml;
    exports controllers;


    opens main to javafx.fxml;
    exports main;


}