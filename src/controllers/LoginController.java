package controllers;

import DAO.AppointmentSQL;
import DAO.DBConnection;
import DAO.UserSQL;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Appointment;
import utilities.ChangeScene;
import utilities.PopUpBox;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public ResourceBundle rb;
    public TextField usernameTextField;
    public TextField passwordTextField;
    public Button loginButton;
    public Label titleLabel;
    public Label userNameLabel;
    public Label passwordLabel;
    public Button exitButton;
    public Label locationTextField;

    /**
     * This initialize method displays the text in the language that the user has set on their local machine and displays
     * their location.
     * @param url
     * @param resourceBundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rb = ResourceBundle.getBundle("languages/login", Locale.getDefault());
        titleLabel.setText(rb.getString("titleLabel"));
        userNameLabel.setText(rb.getString("userNameLabel"));
        passwordLabel.setText(rb.getString("passwordLabel"));
        loginButton.setText(rb.getString("loginButton"));
        exitButton.setText(rb.getString("exitButton"));
        locationTextField.setText(ZoneId.systemDefault().toString());
    }

    /**
     * This method is used to display an error message
     * @param message string you want displayed
     */
    public void errorBox(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(rb.getString("error"));
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * This method runs a query on the users table and checks to make user the password for that user is correct.
     * @param actionEvent login button clicked
     * @throws IOException for file errors
     * @throws SQLException for sql errors
     */
    public void loginButtonClicked(ActionEvent actionEvent) throws IOException, SQLException {
        String userNameString = usernameTextField.getText();
        String passwordString = passwordTextField.getText();
        Boolean test = UserSQL.authenticateUser(userNameString, passwordString);
        // Input validation
        if (userNameString == "") {
            errorBox(rb.getString("enterValidUsername"));
        } else if (passwordString == "") {
            errorBox(rb.getString("enterValidPassword"));
            BufferedWriter log = new BufferedWriter(new FileWriter("login_activity.txt", true));
            log.append(String.valueOf(ZonedDateTime.now(ZoneOffset.UTC))).append("UTC-Login Attempt - USERNAME:" + userNameString + " NULL PASSWORD\n");
            log.flush();
            log.close();
            // Authenticate user with their username and password
        } else if (UserSQL.authenticateUser(userNameString, passwordString)) {
            BufferedWriter log = new BufferedWriter(new FileWriter("login_activity.txt", true));
            log.append(String.valueOf(ZonedDateTime.now(ZoneOffset.UTC))).append("UTC-Login Attempt - USERNAME:" + userNameString + " LOGIN SUCCESSFUL\n");
            log.flush();
            log.close();
            appointmentAlert(userNameString);
            ChangeScene mainMenuScene = new ChangeScene();
            mainMenuScene.stringToSceneChange(actionEvent, "MainMenu");
        } else {
            BufferedWriter log = new BufferedWriter(new FileWriter("login_activity.txt", true));
            log.append(String.valueOf(ZonedDateTime.now(ZoneOffset.UTC))).append("UTC-Login Attempt - USERNAME:" + userNameString + " LOGIN FAILED\n");
            log.flush();
            log.close();
            errorBox(rb.getString("incorrectUsernamePassword"));
        }
    }

    /**
     * this method displays an alert for any appointments the user has in the next 15 mins
     * @param userName username of user login in
     * @throws SQLException fo sql errors
     */
    private void appointmentAlert(String userName) throws SQLException {
        ObservableList<Appointment> appointmentsInFifteenMins = AppointmentSQL.getAppointmentsWithinFifteenMins(UserSQL.getUserID(userName));
        if (appointmentsInFifteenMins.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rb.getString("information"));
            alert.setContentText(rb.getString("noAppointments"));
            alert.showAndWait();
        } else {
            for (Appointment appointment: appointmentsInFifteenMins){
                String alertString = rb.getString("upcomingAppointment") + appointment.getAppointmentID()+ rb.getString("at") + appointment.getAppointmentStartDateTime();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(rb.getString("information"));
                alert.setContentText(alertString);
                alert.showAndWait();
            }
        }
    }

    /**
     * Method to close the application
     * @param Exit requires the user to click the exit button
     */
    public void exitButtonClicked(ActionEvent Exit) {
        Stage stage = (Stage) ((Node) Exit.getSource()).getScene().getWindow();
        stage.close();
    }
}
