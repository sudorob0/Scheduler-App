package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import utilities.ChangeScene;

public class MainMenuController implements Initializable {
    public Button customersButton;
    public Button appointmentsButton;
    public Button reportsButton;
    public Button logoutButton;

    public void initialize(URL url, ResourceBundle resourceBundle){

    }
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

    public void logoutButtonClicked(ActionEvent actionEvent) throws IOException {
        ChangeScene currentChange = new ChangeScene();
        currentChange.stringToSceneChange(actionEvent, "Login");
    }
}
