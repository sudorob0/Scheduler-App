package controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Appointment;
import utilities.ChangeScene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import DAO.AppointmentQuery;
import utilities.ChangeScene;

public class AppointmentsController implements Initializable {
    public TableView appointmentsTable;
    public Button addButton;
    public Button backButton;
    public Button modifyButton;
    public Button deleteButton;
    public RadioButton allAppsRadio;
    public ToggleGroup appointments;
    public RadioButton monthlyRadio;
    public RadioButton weeklyRadio;
    public TableColumn appointmentidCol;
    public TableColumn customeridCol;
    public TableColumn useridCol;
    public TableColumn titleCol;
    public TableColumn descriptionCol;
    public TableColumn locationCol;
    public TableColumn contactidCol;
    public TableColumn startDateTimeCol;
    public TableColumn endDateTimeCol;
    public TableColumn typeCol;

    /**
     * This initialize method loads the appointment table.
     * @param url
     * @param resourceBundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle){
        try {
            ObservableList<Appointment> appointmentsList = AppointmentQuery.getAllAppointments();
            System.out.print(appointmentsList);
            appointmentsTable.setItems(AppointmentQuery.getAllAppointments());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        appointmentidCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        startDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentStartDateTime"));
        endDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentEndDateTime"));
        customeridCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        useridCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        contactidCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));

    }

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
        try {
            ObservableList<Appointment> appointmentsList = AppointmentQuery.getAllAppointments();
            System.out.print(appointmentsList);
            appointmentsTable.setItems(AppointmentQuery.getAllAppointments());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        appointmentidCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        startDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentStartDateTime"));
        endDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentEndDateTime"));
        customeridCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        useridCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        contactidCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
    }

    public void monthlyRadioSelected(ActionEvent actionEvent) {
    }

    public void weeklyRadioSelected(ActionEvent actionEvent) {
    }
}
