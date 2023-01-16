package controllers;

import DAO.AppointmentSQL;
import DAO.ContactSQL;
import DAO.CustomerSQL;
import DAO.UserSQL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.Appointment;
import models.Contact;
import models.Customer;
import models.User;
import utilities.ChangeScene;
import utilities.PopUpBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.ResourceBundle;


/**
 * this class is to control the modify appointments screen
 */
public class ModifyAppointmentController implements Initializable {
    public TextField appointmentidTextField;
    public TextField titleTextField;
    public TextField locationTextField;
    public ComboBox<Integer> useridComboBox;
    public ComboBox<Integer> customeridComboBox;
    public Button saveButton;
    public Button backButton;
    public ComboBox<String> typeComboBox;
    public ComboBox<String> contactComboBox;
    public DatePicker startDatePicker;
    public DatePicker endDatePicker;
    public ComboBox<String> startTimeComboBox;
    public ComboBox<String> endTimeComboBox;
    public TextArea descriptionTextArea;

    /**
     * The initialize populates all of the combo boxes
     * @param url for init
     * @param resourceBundle for init
     */
    public void initialize(URL url, ResourceBundle resourceBundle){
        // Populates the type combo box
        ObservableList<String> allTypes = FXCollections.observableArrayList("Planning Session", "De-Briefing", "Follow-up", "1 on 1", "Other");
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
        LocalTime startTime = LocalTime.of(7,0);
        LocalTime endTime = LocalTime.of(23, 0);
        time.add(startTime.toString());
        while (startTime.isBefore(endTime)){
            startTime = startTime.plusMinutes(15);
            time.add(startTime.toString());
        }
        startTimeComboBox.setItems(time);
        endTimeComboBox.setItems(time);


    }

    /**
     * This method populates all of the fields with the appointment object sent over from the appointment controller
     * @param appointment appointment object to be modified
     * @throws SQLException for sql errors
     */
    public void appointmentToModify(Appointment appointment) throws SQLException {
        appointmentidTextField.setText(String.valueOf(appointment.getAppointmentID()));
        titleTextField.setText(appointment.getAppointmentTitle());
        locationTextField.setText(appointment.getAppointmentLocation());
        useridComboBox.setValue(appointment.getUserID());
        customeridComboBox.setValue(appointment.getCustomerID());
        typeComboBox.setValue(appointment.getAppointmentType());
        contactComboBox.setValue(ContactSQL.getContactName(appointment.getContactID()));
        startDatePicker.setValue(appointment.getAppointmentStartDateTime().toLocalDate());
        endDatePicker.setValue(appointment.getAppointmentEndDateTime().toLocalDate());
        startTimeComboBox.setValue(appointment.getAppointmentStartDateTime().toLocalTime().toString());
        endTimeComboBox.setValue(appointment.getAppointmentEndDateTime().toLocalTime().toString());
        descriptionTextArea.setText(appointment.getAppointmentDescription());
    }


    /**
     * this method converts from the users local time to eastern time
     * @param time provide local time
     * @return time converted to eastern
     */
    private LocalTime convertToEst(LocalDateTime time) {
        return LocalTime.from(ZonedDateTime.of(time, ZoneId.of("America/New_York")));
    }

    /**
     * This method check for any overlapping appointments and will display an error message telling the user to change
     * their appointment time
     * @param enteredStartDT this is the entered start date/time
     * @param enteredEndDT this is the entered end date/time
     * @return this returns true if there are NO overlapping appointments and false if there are overlapping appointments
     * @throws SQLException for sql errors
     */
    private boolean checkForOverlap(ZonedDateTime enteredStartDT, ZonedDateTime enteredEndDT) throws SQLException {
        ObservableList<Appointment> overlappingAppointments = AppointmentSQL.findOverLappingAppointments(enteredStartDT, enteredEndDT);
        if (overlappingAppointments.size() > 0) {
            Appointment overlappingApp = overlappingAppointments.get(0);
            PopUpBox.errorBox("This appointment overlaps with a previously scheduled appointment.\nOverlapping Appointment ID: " + overlappingApp.getAppointmentID() + "\nStart time: " + overlappingApp.getAppointmentStartDateTime() + "\nEnd time: " + overlappingApp.getAppointmentEndDateTime());
            return false;
        }
        return true;
    }


    /**
     * this method takes the user back one screen when the back button is clicked
     * @param actionEvent the back button being clicked
     * @throws IOException for IO errors
     */
    public void backButtonClicked(ActionEvent actionEvent) throws IOException {
        if (PopUpBox.optionBox("Are you sure you want to return to the previous screen without saving?")){
            ChangeScene newScene = new ChangeScene();
            newScene.stringToSceneChange(actionEvent, "Appointments");
        }
    }

    /**
     * This method first validates that all fields are filled out, it then validates that the appointment start and end
     * date/times are valid. If everything is correct it will create an appointment in the sql database.
     * @param actionEvent add button clicked
     * @throws SQLException catches sql errors for sql statement
     * @throws IOException catches IO errors for scene change
     */
    public void saveButtonClicked(ActionEvent actionEvent) throws SQLException, IOException {

        // assign variables
        String enteredTitle = titleTextField.getText();
        String enteredLocation = locationTextField.getText();
        Integer enteredUserID = useridComboBox.getSelectionModel().getSelectedItem();
        Integer enteredCustomerID = customeridComboBox.getSelectionModel().getSelectedItem();
        String enteredType = typeComboBox.getSelectionModel().getSelectedItem();
        String enteredContact = contactComboBox.getSelectionModel().getSelectedItem();

        // Validating all required fields are filled out
        if (enteredTitle.equals("")){PopUpBox.errorBox("The title field must be filled out to continue");}
        else if (enteredLocation.equals("")){PopUpBox.errorBox("The location field must be filled out to continue");}
        else if (enteredUserID == null){PopUpBox.errorBox("The user ID field must be filled out to continue");}
        else if (enteredCustomerID == null){PopUpBox.errorBox("The customer ID field must be filled out to continue");}
        else if (enteredType == null){PopUpBox.errorBox("The appointment type field must be filled out to continue");}
        else if (enteredContact == null){PopUpBox.errorBox("The contact field must be filled out to continue");}
        else if (startDatePicker.getValue() == null){PopUpBox.errorBox("The start date field must be filled out to continue");}
        else if (startTimeComboBox.getSelectionModel().getSelectedItem() == null){PopUpBox.errorBox("The start time field must be filled out to continue");}
        else if (endTimeComboBox.getSelectionModel().getSelectedItem() == null){PopUpBox.errorBox("The end time field must be filled out to continue");}
        else {
            // If no end date is provided then use the start date
            if (endDatePicker.getValue() == null) {
                endDatePicker.setValue(startDatePicker.getValue());
            }
            LocalTime enteredStartTime = LocalTime.parse(startTimeComboBox.getSelectionModel().getSelectedItem());
            LocalTime enteredEndTime = LocalTime.parse(endTimeComboBox.getSelectionModel().getSelectedItem());

            // Save full start and end date/times to one variable
            LocalDateTime enteredStartDT = LocalDateTime.of(startDatePicker.getValue(), enteredStartTime);
            LocalDateTime enteredEndDT = LocalDateTime.of(endDatePicker.getValue(), enteredEndTime);
            LocalTime enteredStartTimeEst = convertToEst(enteredStartDT);
            LocalTime enteredEndTimeEst = convertToEst(enteredEndDT);

            // Zoned the local time with the users default zone
            ZoneId zoneid = ZoneId.systemDefault();
            ZonedDateTime zonedStartDT = ZonedDateTime.of(LocalDateTime.of(startDatePicker.getValue(), enteredStartTime), zoneid);
            ZonedDateTime zonedEndDT = ZonedDateTime.of(LocalDateTime.of(endDatePicker.getValue(), enteredEndTime), zoneid);

            // Validate that appointment is within business hours
            if (enteredStartTimeEst.isBefore(LocalTime.of(8,0)) || enteredEndTimeEst.isBefore(LocalTime.of(8, 0)) || enteredStartTimeEst.isAfter(LocalTime.of(22, 0)) || enteredEndTimeEst.isAfter(LocalTime.of(22, 0))) {
                PopUpBox.errorBox("Appointments can only be scheduled within the business hours of 8am to 10pm EST.");
            }
            // Validate end date/time is after start date/time
            else if (enteredStartDT.isAfter(enteredEndDT) || enteredStartDT.isEqual(enteredEndDT)) {
                PopUpBox.errorBox("The end date/time has to be after the start data/time");
            }
            // Validate that appointment does not take place in the past
            else if (enteredStartDT.isBefore(LocalDateTime.now())) {
                PopUpBox.errorBox("Appointments can not be scheduled in the past. Please select a time in the future.");
            }
            // Check for appointment overlapping
            else if (checkForOverlap(zonedStartDT, zonedEndDT)) {
                // add appointment
                if (AppointmentSQL.modifyAppointment(appointmentidTextField.getText(), ContactSQL.getContactID(enteredContact), enteredTitle, descriptionTextArea.getText(), enteredLocation, enteredType, enteredCustomerID, enteredUserID, enteredStartDT, enteredEndDT)) {
                    PopUpBox.infoBox("Appointment has been successfully saved");
                    ChangeScene newScene = new ChangeScene();
                    newScene.stringToSceneChange(actionEvent, "Appointments");
                } else {
                    PopUpBox.errorBox("There has been an error adding your appointment. Please try again.");
                }
            }
        }
    }
}
