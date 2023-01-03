package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utilities.ChangeScene;

import java.io.IOException;

public class LoginController {
    public TextField usernameTextField;
    public TextField passwordTextField;
    public TextField locationTextField;
    public Button loginButton;

    public Button cancelButton;

    public void loginButtonClicked(ActionEvent actionEvent) throws IOException {
        ChangeScene mainMenuScene = new ChangeScene();
        mainMenuScene.stringToSceneChange(actionEvent, "MainMenu");
    }

    public void cancelButtonClicked(ActionEvent actionEvent) {
    }
}
