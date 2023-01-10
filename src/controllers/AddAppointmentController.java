package controllers;

import DAO.ContactSQL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import models.Contact;
import utilities.ChangeScene;
import utilities.PopUpBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {
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

    public void initialize(URL url, ResourceBundle resourceBundle){
        // Populates the type combo box
        ObservableList<String> allTypes = FXCollections.observableArrayList("Planning Session", "De-Briefing", "Follow-up", "Other");
        typeComboBox.setItems(allTypes);

        // Populate the contact combo box
        ObservableList<Contact> contactsList = null;
        try {
            contactsList = ContactSQL.getAllContacts();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ObservableList<String> contactStringList = FXCollections.observableArrayList();
        contactsList.forEach(contact -> contactStringList.add(contact.getContactName()));
        contactComboBox.setItems(contactStringList);

        //
        ObservableList<Customer> customersList = null;
        try {
            customersList = ContactSQL.getAllContacts();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ObservableList<String> contactStringList = FXCollections.observableArrayList();
        contactsList.forEach(contact -> contactStringList.add(contact.getContactName()));
        contactComboBox.setItems(contactStringList);


    }
    private ZonedDateTime convertToEst(LocalDateTime time) {
        return ZonedDateTime.of(time, ZoneId.of("America/New_York"));
    }

    public void backButtonClicked(ActionEvent actionEvent) throws IOException {
        if (PopUpBox.optionBox("Are you sure you want to return to the previous screen without saving?")){
            ChangeScene mainMenuScene = new ChangeScene();
            mainMenuScene.stringToSceneChange(actionEvent, "Appointments");
        }
    }

    public void addButtonClicked(ActionEvent actionEvent) {
    }

}
