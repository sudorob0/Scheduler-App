package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import utilities.ChangeScene;

import java.io.IOException;
import utilities.ChangeScene;

public class AppointmentsController {
    public TableView appointmentsTable;
    public Button addButton;
    public Button backButton;
    public Button modifyButton;
    public Button deleteButton;
    public RadioButton allAppsRadio;
    public ToggleGroup appointments;
    public RadioButton monthlyRadio;
    public RadioButton weeklyRadio;

    public void addButtonClicked(ActionEvent actionEvent) throws IOException {
        ChangeScene mainMenuScene = new ChangeScene();
        mainMenuScene.stringToSceneChange(actionEvent, "AddAppointment");
    }

    public void backButtonClicked(ActionEvent actionEvent) throws IOException {
        ChangeScene currentChange = new ChangeScene();
        currentChange.stringToSceneChange(actionEvent, "MainMenu");
    }

    public void modifyButtonClicked(ActionEvent actionEvent) {
    }

    public void deleteButtonClicked(ActionEvent actionEvent) {
    }

    public void allAppsRadioSelected(ActionEvent actionEvent) {
    }

    public void monthlyRadioSelected(ActionEvent actionEvent) {
    }

    public void weeklyRadioSelected(ActionEvent actionEvent) {
    }
}
