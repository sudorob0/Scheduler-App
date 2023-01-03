package controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import utilities.ChangeScene;

import java.io.IOException;

public class AddCustomerController {
    public TextField customeridTextField;
    public TextField nameTextField;
    public TextField telephoneTextField;
    public TextField addressTextField;
    public TextField zipcodeField;
    public ComboBox countryComboBox;
    public ComboBox divisionComboBox;
    public Button addButton;
    public Button backButton;
    public Button cancelButton;

    public void addButtonClicked(ActionEvent actionEvent) {
    }

    public void backButtonClicked(ActionEvent actionEvent) throws IOException {
        ChangeScene mainMenuScene = new ChangeScene();
        mainMenuScene.stringToSceneChange(actionEvent, "Customers");
    }

    public void cancelButtonClicked(ActionEvent actionEvent) throws IOException {
        ChangeScene mainMenuScene = new ChangeScene();
        mainMenuScene.stringToSceneChange(actionEvent, "Customers");
    }
}
