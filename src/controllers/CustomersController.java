package controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import utilities.ChangeScene;

import java.io.IOException;

public class CustomersController {
    public TableView customersTable;
    public Button addButton;
    public Button backButton;
    public Button modifyButton;
    public Button deleteButton;

    public void backButtonClicked(ActionEvent actionEvent) throws IOException {
        ChangeScene mainMenuScene = new ChangeScene();
        mainMenuScene.stringToSceneChange(actionEvent, "MainMenu");
    }

    public void addButtonClicked(ActionEvent actionEvent) throws IOException {
        ChangeScene mainMenuScene = new ChangeScene();
        mainMenuScene.stringToSceneChange(actionEvent, "AddCustomer");
    }

    public void modifyButtonClicked(ActionEvent actionEvent) {
    }

    public void deleteButtonClicked(ActionEvent actionEvent) {
    }
}
