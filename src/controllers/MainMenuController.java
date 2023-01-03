package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import utilities.ChangeScene;

public class MainMenuController {
    public Button customersButton;
    public Button appointmentsButton;
    public Button reportsButton;
    public Button exitButton;
/**
    public void changeScene(ActionEvent actionEvent, String fxmlFile, int sceneWidth, int sceneHeight, String screenTitle) throws IOException {
        String path = "/views/" + fxmlFile;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, sceneWidth, sceneHeight);
        stage.setTitle(screenTitle);
        stage.setScene(scene);
        stage.show();
    }
 */

    public void customersButtonClicked(ActionEvent actionEvent) throws IOException {
        ChangeScene currentChange = new ChangeScene();
        currentChange.stringToSceneChange(actionEvent, "Customers");
    }

    public void appointmentsButtonClicked(ActionEvent actionEvent) throws IOException {
        ChangeScene currentChange = new ChangeScene();
        currentChange.stringToSceneChange(actionEvent, "Appointments");
    }

    public void reportsButtonClicked(ActionEvent actionEvent) {
    }

    public void exitButtonClicked(ActionEvent actionEvent) throws IOException {
        ChangeScene currentChange = new ChangeScene();
        currentChange.stringToSceneChange(actionEvent, "Login");
    }
}
