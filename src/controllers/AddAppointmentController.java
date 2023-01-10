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
    public DatePicker endDatePicker;
    public ComboBox startTimeComboBox;
    public ComboBox endTimeComboBox;

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

        // Populate the start and end times with available appointment times with 15 minute increments
        ObservableList<String> time = FXCollections.observableArrayList();
        LocalTime startTime = LocalTime.of(8,0);
        LocalTime endTime = LocalTime.of(22, 0);
        time.add(startTime.toString());
        while (startTime.isBefore(endTime)){
            startTime = startTime.plusMinutes(15);
            time.add(startTime.toString());
        }
        startTimeComboBox.setItems(time);
        endTimeComboBox.setItems(time);


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

        if (titleTextField.getText() == ""){PopUpBox.errorBox("The title field must be filled out to continue");}
        else if (locationTextField.getText() == ""){PopUpBox.errorBox("The location field must be filled out to continue");}
        else if (useridComboBox.getSelectionModel().getSelectedItem() == null){PopUpBox.errorBox("The user ID field must be filled out to continue");}
        else if (customeridComboBox.getSelectionModel().getSelectedItem() == null){PopUpBox.errorBox("The customer ID field must be filled out to continue");}
        else if (typeComboBox.getSelectionModel().getSelectedItem() == null){PopUpBox.errorBox("The appointment type field must be filled out to continue");}
        else if (contactComboBox.getSelectionModel().getSelectedItem() == null){PopUpBox.errorBox("The contact field must be filled out to continue");}
        else{

        }



        String enteredTitle = titleTextField.getText();
        String enteredLocation = locationTextField.getText();
        String enteredDescription = descriptionTextField.getText();
        Integer enteredUserID = (Integer) useridComboBox.getSelectionModel().getSelectedItem();
        Integer enteredCustomerID = (Integer) customeridComboBox.getSelectionModel().getSelectedItem();
        String enteredType = (String) typeComboBox.getSelectionModel().getSelectedItem();
        String enteredContact = (String) contactComboBox.getSelectionModel().getSelectedItem();
        //LocalTime enteredStartTime = (LocalTime) startTimeComboBox.getValue();
        //LocalTime enteredEndTime = (LocalTime) endTimeComboBox.getValue();
        //LocalDateTime enteredStartDT = LocalDateTime.of(startDatePicker.getValue(), enteredStartTime);
        //LocalDateTime enteredEndDT = LocalDateTime.of(endDatePicker.getValue(), enteredEndTime);

        //AppointmentSQL.addAppointment("'Daddy Warbucks'");
    }

}
