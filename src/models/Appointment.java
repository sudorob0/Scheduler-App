package models;

import java.time.LocalDateTime;

/**
 * Appointment class for all appointments.
 */
public class Appointment {

    private int appointmentID;
    private String appointmentTitle;
    private String appointmentDescription;
    private String appointmentLocation;
    private String appointmentType;
    private LocalDateTime appointmentStartDateTime;
    private LocalDateTime appointmentEndDateTime;
    private int customerID;
    private int userID;
    private int contactID;


    /**
     * Method to create a appointment object
     * @param appointmentID sets the appointment ID.
     * @param appointmentTitle sets the title of the appointment.
     * @param appointmentDescription sets the description of the appointment.
     * @param appointmentLocation sets the location of the appointment.
     * @param appointmentType sets the type of appointment.
     * @param appointmentStartDateTime is a local date time type and sets the appointments date and start time.
     * @param appointmentEndDateTime is a local date time type and sets the appointments date and end time.
     * @param customerID is an integer and sets the customer ID.
     * @param userID is an integer and sets the user ID.
     * @param contactID is an integer and sets the contact ID.
     * */
    public Appointment(int appointmentID, String appointmentTitle, String appointmentDescription,
                       String appointmentLocation, String appointmentType,
                       LocalDateTime appointmentStartDateTime,
                       LocalDateTime appointmentEndDateTime, int customerID, int userID, int contactID) {

        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.appointmentStartDateTime = appointmentStartDateTime;
        this.appointmentEndDateTime = appointmentEndDateTime;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }


    public Appointment() {
    }

    /**
     * Getter for appointmentID,
     * @return the appointment ID.
     * */
    public int getAppointmentID() {

        return appointmentID;
    }

    /** @param appointmentID Setter for setting the appointmentID. */
    public void setAppointmentID(int appointmentID) {

        this.appointmentID = appointmentID;
    }

    /**
     * Getter for appointmentTitle,
     * @return the title of the appointment.
     */
    public String getAppointmentTitle() {

        return appointmentTitle;
    }

    /** @param appointmentTitle Setter for setting the appointmentTitle */
    public void setAppointmentTitle(String appointmentTitle) {

        this.appointmentTitle = appointmentTitle;
    }

    /** Getter for appointmentDescription,
     * @return the description of the appointment. */
    public String getAppointmentDescription() {

        return appointmentDescription;
    }

    /** @param appointmentDescription Setter for setting the appointmentDescription. */
    public void setAppointmentDescription(String appointmentDescription) {

        this.appointmentDescription = appointmentDescription;
    }

    /** Getter for appointmentLocation,
     * @return the location of the appointment. */
    public String getAppointmentLocation() {

        return appointmentLocation;
    }

    /** @param appointmentLocation Setter for setting the appointmentLocation. */
    public void setAppointmentLocation(String appointmentLocation) {

        this.appointmentLocation = appointmentLocation;
    }

    /** Getter for appointmentType,
     * @return the type of appointment. */
    public String getAppointmentType() {

        return appointmentType;
    }

    /** @param appointmentType Setter for setting the appointmentType. */
    public void setAppointmentType(String appointmentType) {

        this.appointmentType = appointmentType;
    }

    /** Getter for appointmentStartDateTime,
     * @return the start date and time together for the appointment. */
    public LocalDateTime getAppointmentStartDateTime() {

        return appointmentStartDateTime;
    }

    /** @param appointmentStartDateTime Setter for setting appointmentStartDateTime. */
    public void setAppointmentStartDateTime(LocalDateTime appointmentStartDateTime) {

        this.appointmentStartDateTime = appointmentStartDateTime;
    }

    /** Getter for appointmentEndDateTime,
     * @return the end date and time together for the appointment. */
    public LocalDateTime getAppointmentEndDateTime() {

        return appointmentEndDateTime;
    }

    /** @param appointmentEndDateTime Setter for setting the appointmentEndDateTime. */
    public void setAppointmentEndDateTime(LocalDateTime appointmentEndDateTime) {

        this.appointmentEndDateTime = appointmentEndDateTime;
    }

    /** Getter for customerID,
     * @return the ID of the customer. */
    public int getCustomerID() {

        return customerID;
    }

    /** @param customerID Setter for setting the customerID. */
    public void setCustomerID(int customerID) {

        this.customerID = customerID;
    }

    /** Getter for userID,
     * @return the ID of the user. */
    public int getUserID() {

        return userID;
    }

    /** @param userID Setter for setting the userID. */
    public void setUserID(int userID) {

        this.userID = userID;
    }

    /** Getter for contactID,
     * @return the ID of the contact. */
    public int getContactID() {

        return contactID;
    }

    /** @param contactID Setter for setting the appointmentContact. */
    public void setAppointmentContact(int contactID) {

        this.contactID = contactID;
    }

}

