package main;

import DAO.DBConnection;

import controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;


public class Main extends Application {
    /**
     * This method is to initialize the stage for the application
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root, 580, 390));
        primaryStage.show();
    }
    /**
     * Launches application
     * @param args
     */
    public static void main(String[] args) {
        // language testing
        Locale.setDefault(new Locale("fr"));
        // open db connection
        DBConnection.openConnection();
        launch(args);
        DBConnection.closeConnection();
    }
}
