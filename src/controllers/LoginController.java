package controllers;

import DAO.AppointmentSQL;
import DAO.DBConnection;
import DAO.UserSQL;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
     * This method runs a query on the users table and checks to make user the password for that user is correct.
     * @param actionEvent login button clicked
     * @throws IOException
     * @throws SQLException
     */
    public void loginButtonClicked(ActionEvent actionEvent) throws IOException, SQLException {
        String userNameString = usernameTextField.getText();
        String passwordString = passwordTextField.getText();
        Boolean test = UserSQL.authenticateUser(userNameString, passwordString);
        // Input validation
        if (userNameString == "") {
            PopUpBox.errorBox(rb.getString("enterValidUsername"));
        } else if (passwordString == "") {
            PopUpBox.errorBox(rb.getString("enterValidPassword"));
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
            PopUpBox.errorBox(rb.getString("incorrectUsernamePassword"));
        }
    }

    private void appointmentAlert(String userName) throws SQLException {
        ObservableList<Appointment> appointmentsInFifteenMins = AppointmentSQL.getAppointmentsWithinFifteenMins(UserSQL.getUserID(userName));
        if (appointmentsInFifteenMins.size() == 0) {
            PopUpBox.infoBox(rb.getString("noAppointments"));
        } else {
            for (Appointment appointment: appointmentsInFifteenMins){
                String alertString = rb.getString("upcomingAppointment") + appointment.getAppointmentID()+ rb.getString("at") + appointment.getAppointmentStartDateTime();
                PopUpBox.infoBox(alertString);
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
