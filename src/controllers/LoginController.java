package controllers;

import DAO.UserQuery;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utilities.ChangeScene;
import utilities.PopUpBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController {
    public TextField usernameTextField;
    public TextField passwordTextField;
    public Button loginButton;
    public Label titleLabel;
    public Label userNameLabel;
    public Label passwordLabel;
    public Button exitButton;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        ResourceBundle rb = ResourceBundle.getBundle("languages/login", Locale.getDefault());
        titleLabel.setText(rb.getString("titleLabel"));
        userNameLabel.setText(rb.getString("userNameLabel"));
        passwordLabel.setText(rb.getString("passwordLabel"));
        loginButton.setText(rb.getString("loginButton"));
        exitButton.setText(rb.getString("exitButton"));
    }

    public void loginButtonClicked(ActionEvent actionEvent) throws IOException, SQLException {
        String userNameString = usernameTextField.getText();
        String passwordString = passwordTextField.getText();
        // Input validation
        if (userNameString == "") {
            PopUpBox.errorBox("Please enter valid username");
        } else if (passwordString == "") {
            PopUpBox.errorBox("Please enter valid password");
            // Authenticate user with their username and password
        } else if (UserQuery.authenticateUser(userNameString, passwordString)) {
            ChangeScene mainMenuScene = new ChangeScene();
            mainMenuScene.stringToSceneChange(actionEvent, "MainMenu");
        } else {
            System.out.print("\nIncorrect username or password, please try again. If you continue to have issues contact your system admin.");
        }
    }


    public void exitButtonClicked(ActionEvent Exit) {
        Stage stage = (Stage) ((Node) Exit.getSource()).getScene().getWindow();
        stage.close();
    }
}
