package controllers;

import DAO.AppointmentSQL;
import DAO.ContactSQL;
import DAO.CustomerSQL;
import DAO.UserSQL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import models.Contact;
import models.Customer;
import models.User;
import utilities.ChangeScene;
import utilities.PopUpBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        try {
            ObservableList<Contact>contactsList = ContactSQL.getAllContacts();
            ObservableList<String> contactStringList = FXCollections.observableArrayList();
            contactsList.forEach(contact -> contactStringList.add(contact.getContactName()));
            contactComboBox.setItems(contactStringList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Populate customer id combo box
        try {
            ObservableList<Integer> customersIDList = FXCollections.observableArrayList();
            ObservableList<Customer> customersList = CustomerSQL.getAllCustomers();
            customersList.forEach(customer -> customersIDList.add(customer.getCustomerID()));
            customeridComboBox.setItems(customersIDList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Populate user id combo box
        try {
            ObservableList<Integer> userIDList = FXCollections.observableArrayList();
            ObservableList<User> userList = UserSQL.getAllUsers();
            userList.forEach(user -> userIDList.add(user.getUserID()));
            useridComboBox.setItems(userIDList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


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
        String enteredTitle = titleTextField.getText();
        String enteredLocation = locationTextField.getText();
        String enteredDesctiption = descriptionTextField.getText();
        Integer enteredUserID = (Integer) useridComboBox.getSelectionModel().getSelectedItem();
        Integer enteredCustomerID = (Integer) customeridComboBox.getSelectionModel().getSelectedItem();
        String enteredType = (String) typeComboBox.getSelectionModel().getSelectedItem();
        String enteredContact = (String) contactComboBox.getSelectionModel().getSelectedItem();
        LocalDateTime enteredStartTime = LocalDateTime.of(startDatePicker.getValue(), startTimePicker.getValue());
        startTimePicker.getValue();
        endDatePicker.getValue();
        endTimePicker.getValue();
        AppointmentSQL.addAppointment("'Daddy Warbucks'");
    }

}
