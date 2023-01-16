package controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Appointment;
import utilities.ChangeScene;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import DAO.AppointmentSQL;
import utilities.PopUpBox;

/**
 * This class is to control the appointments screen
 */
public class AppointmentsController implements Initializable {
    public TableView<Appointment> appointmentsTable;
    public Button addButton;
    public Button backButton;
    public Button modifyButton;
    public Button deleteButton;
    public RadioButton allAppsRadio;
    public ToggleGroup appointments;
    public RadioButton monthlyRadio;
    public RadioButton weeklyRadio;
    public TableColumn<Object, Object> appointmentidCol;
    public TableColumn<Object, Object> customeridCol;
    public TableColumn<Object, Object> useridCol;
    public TableColumn<Object, Object> titleCol;
    public TableColumn<Object, Object> descriptionCol;
    public TableColumn<Object, Object> locationCol;
    public TableColumn<Object, Object> contactidCol;
    public TableColumn<Object, Object> startDateTimeCol;
    public TableColumn<Object, Object> endDateTimeCol;
    public TableColumn<Object, Object> typeCol;

    /**
     * This method checks which radio button is selected and then refreshes the appointmentTable.
     *  LAMBDA EXPRESSION: These lambda expressions call this method refreshAppointmentsTable() when one of the radio buttons are selected
     *  These expressions replaced 3 methods and make the code more readable by grouping them all together.
     * @throws SQLException for any SQL errors
     */
    public void refreshAppointmentsTable() throws SQLException {
         // LAMBDA EXPRESSION: These lambda expressions call this method refreshAppointmentsTable() when one of the radio buttons are selected
         // These expressions replaced 3 methods and make the code more readable by grouping them all together.
        allAppsRadio.setOnAction(e -> {
            try {
                refreshAppointmentsTable();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        monthlyRadio.setOnAction(e -> {
            try {
                refreshAppointmentsTable();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        weeklyRadio.setOnAction(e -> {
            try {
                refreshAppointmentsTable();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        if (allAppsRadio.isSelected()) {
            ObservableList<Appointment> appointmentsList = AppointmentSQL.getAllAppointments();
            appointmentsTable.setItems(appointmentsList);
        } else if (monthlyRadio.isSelected()) {
            ObservableList<Appointment> appointmentsList = AppointmentSQL.getAppointmentsByMonth();
            appointmentsTable.setItems(appointmentsList);
        } else if (weeklyRadio.isSelected()) {
            ObservableList<Appointment> appointmentsList = AppointmentSQL.getAppointmentsByWeek();
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
     *
     * @param url            for initialize
     * @param resourceBundle for initialize
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            refreshAppointmentsTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * this method takes the user to the add appointment scene
     *
     * @param actionEvent add button clicked
     * @throws IOException for file errors
     */
    public void addButtonClicked(ActionEvent actionEvent) throws IOException {
        ChangeScene changeScene = new ChangeScene();
        changeScene.stringToSceneChange(actionEvent, "AddAppointment");
    }

    /**
     * this method takes the user to the main menu scene
     *
     * @param actionEvent add button clicked
     * @throws IOException for file errors
     */
    public void backButtonClicked(ActionEvent actionEvent) throws IOException {
        ChangeScene currentChange = new ChangeScene();
        currentChange.stringToSceneChange(actionEvent, "MainMenu");
    }

    /**
     * this method sends the selected appointment to the modify appointment screen
     *
     * @param actionEvent modify button clicked
     */
    public void modifyButtonClicked(ActionEvent actionEvent) {

        if (appointmentsTable.getSelectionModel().getSelectedItem() == null) {
            PopUpBox.errorBox("Please select an appointment to edit");
        } else {
            Appointment selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();
            int currentIndex = appointmentsTable.getSelectionModel().getSelectedIndex();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ModifyAppointment.fxml"));
                Parent root = loader.load();
                ModifyAppointmentController modifyAppointmentController = loader.getController();
                modifyAppointmentController.appointmentToModify(selectedAppointment);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 850, 500);
                stage.setTitle("Modify Appointment");
                stage.setScene(scene);
                stage.show();

            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * this method deletes the selected appointment form the sql database
     *
     * @throws SQLException for sql errors
     */
    public void deleteButtonClicked() throws SQLException {
        Appointment selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            PopUpBox.errorBox("Please select an the appointment you would like to delete.");
        } else {
            String popUpString = "Are you sure you want to delete this Appointment?\nID: " + selectedAppointment.getAppointmentID() + "\nName: " + selectedAppointment.getAppointmentTitle();
            if (PopUpBox.optionBox(popUpString)) {
                if (AppointmentSQL.deleteAppointment(selectedAppointment.getAppointmentID())) {
                    refreshAppointmentsTable();
                    PopUpBox.infoBox("Appointment successfully canceled.\n Appointment ID: " + selectedAppointment.getAppointmentID() + ", Type: " + selectedAppointment.getAppointmentType());
                } else {
                    PopUpBox.errorBox("Error canceling appointment, please try again");
                }
            }
        }

        // all this was replaced with a Lambda expression
        /**
         * this method is just a trigger for the refresh table method
         * @throws SQLException for sql errors

        public void allAppsRadioSelected() throws SQLException {
        refreshAppointmentsTable();
        }
        /**
         * this method is just a trigger for the refresh table method
         * @throws SQLException for sql errors


        public void monthlyRadioSelected() throws SQLException {
        refreshAppointmentsTable();
        }
        /**
         * this method is just a trigger for the refresh table method
         * @throws SQLException for sql errors

        public void weeklyRadioSelected() throws SQLException {
        refreshAppointmentsTable();
        }
         */
    }
}

