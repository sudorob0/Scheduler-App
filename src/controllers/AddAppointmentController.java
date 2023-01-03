package controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import utilities.ChangeScene;

import java.io.IOException;

public class AddAppointmentController {
    public TextField appointmentidTextField;
    public TextField titleTextField;
    public TextField locationTextField;
    public TextField descriptionTextField;
    public ComboBox useridComboBox;
    public ComboBox customeridComboBox;
    public Button addButton;
    public Button backButton;
    public ComboBox typeComboBox;
    public ComboBox contactComboBox;
    public DatePicker startDatePicker;
    public DatePicker startTimePicker;
    public DatePicker endDatePicker;
    public DatePicker endTimePicker;
    public Button clearButton;

    public void backButtonClicked(ActionEvent actionEvent) throws IOException {
        ChangeScene mainMenuScene = new ChangeScene();
        mainMenuScene.stringToSceneChange(actionEvent, "Appointments");
    }

    public void addButtonClicked(ActionEvent actionEvent) {
    }

    public void clearButtonClicked(ActionEvent actionEvent) {
    }
}
