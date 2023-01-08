package controllers;

import DAO.AppointmentQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import DAO.AppointmentQuery.*;
import models.Appointment;
import utilities.ChangeScene;
import utilities.PopUpBox;

public class MainMenuController implements Initializable {
    public Button customersButton;
    public Button appointmentsButton;
    public Button reportsButton;
    public Button logoutButton;

    public void initialize(URL url, ResourceBundle resourceBundle){
        for (Appointment appointment: AppointmentQuery.getAllAppointments()) {
            AppointmentStartDateTime = appointment.getAppointmentStartDateTime();
            if ((startTime.isAfter(minus15MinutesFromTime) || startTime.isEqual(minus15MinutesFromTime)) && (startTime.isBefore(add15MinutesToTime) || (startTime.isEqual(add15MinutesToTime)))) {
                getAppointmentID = appointment.getAppointmentID();
                appointmentStartingTime = startTime;
                appointmentIn15Minutes = true;
            }
        }
        if (appointmentIn15Minutes) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "There is an appointment within 15 minutes with an appointment ID of: " + getAppointmentID +
                            " and a start time of: " + appointmentStartingTime);
            Optional<ButtonType> confirmation = alert.showAndWait();
            System.out.println("There is an appointment within 15 minutes");
        }

        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No upcoming appointments.");
            alert.setTitle("No upcoming appointments...");
            alert.setHeaderText("No appointments.");
            alert.setContentText("There are no upcoming appointment in the next 15 minutes.");
            alert.showAndWait();
        }
        PopUpBox.errorBox("sdf");
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
