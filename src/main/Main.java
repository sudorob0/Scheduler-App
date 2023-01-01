package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    /**
     * This method is to initialize the stage for the application
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 580, 390));
        primaryStage.show();
    }
    /**
     * Launches application
     * @param args
     */
    public static void main(String[] args) {
        // this is used to add test data

        launch(args);
    }
}
