package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Appointment;
import java.sql.*;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;

public class AppointmentQuery {
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();
        String sqlQuery = "SELECT * FROM appointments";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sqlQuery);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String appointmentTitle = rs.getString("Title");
            String appointmentDescription = rs.getString("Description");
            String appointmentLocation = rs.getString("Location");
            String appointmentType = rs.getString("Type");
            LocalDateTime appointmentStart = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime appointmentEnd = rs.getTimestamp("End").toLocalDateTime();
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");
            Appointment appointment = new Appointment(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, appointmentStart, appointmentEnd, customerID, userID, contactID);
            appointmentsList.add(appointment);
        }
        return appointmentsList;
    }

    public static ObservableList<Appointment> getAppointmentsByMonth() {
        ObservableList<Appointment> AppointmentsByMonth = FXCollections.observableArrayList();

        String sqlQuery = "SELECT * FROM client_schedule.appointments WHERE Start >= (CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 1 MONTH AND Start < LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY;";
    }
}
