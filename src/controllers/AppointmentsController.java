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
import utilities.PopUpBox;
import utilities.PopUpBox.*;

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

    public void refreshAppointmentsTable() throws SQLException {
        if (allAppsRadio.isSelected()){
            ObservableList<Appointment> appointmentsList = AppointmentQuery.getAllAppointments();
            appointmentsTable.setItems(appointmentsList);
        } else if (monthlyRadio.isSelected()) {
            ObservableList<Appointment> appointmentsList = AppointmentQuery.getAppointmentsByMonth();
            appointmentsTable.setItems(appointmentsList);
        } else if (weeklyRadio.isSelected()) {
            ObservableList<Appointment> appointmentsList = AppointmentQuery.getAppointmentsByWeek();
            appointmentsTable.setItems(appointmentsList);
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
        appointmentsTable.refresh();
    }

    /**
     * This initialize method loads the appointment table.
     * @param url
     * @param resourceBundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle){
        try {
            refreshAppointmentsTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
        Appointment selectedAppointment = (Appointment) appointmentsTable.getSelectionModel().getSelectedItem();
    }

    public void deleteButtonClicked(ActionEvent actionEvent) throws SQLException {
        Appointment selectedAppointment = (Appointment) appointmentsTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null){
            PopUpBox.errorBox("Please select an the appointment you would like to delete.");
        } else {
            String popUpString = "Are you sure you want to delete this Appointment?\nID: " + selectedAppointment.getAppointmentID() + "\nName: " + selectedAppointment.getAppointmentTitle();
            if (PopUpBox.optionBox(popUpString)){
                AppointmentQuery.deleteAppointment(selectedAppointment.getAppointmentID());
                refreshAppointmentsTable();
            }
        }
    }

    public void allAppsRadioSelected(ActionEvent actionEvent) throws SQLException {
        refreshAppointmentsTable();
    }

    public void monthlyRadioSelected(ActionEvent actionEvent) throws SQLException {
        refreshAppointmentsTable();
    }

    public void weeklyRadioSelected(ActionEvent actionEvent) throws SQLException {
        refreshAppointmentsTable();
    }
}
