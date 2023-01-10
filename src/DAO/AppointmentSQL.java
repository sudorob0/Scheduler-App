package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Appointment;
import models.Contact;

import java.sql.*;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;

public class AppointmentSQL {
    public static ObservableList<Appointment> makeAppointmentQuery(String sqlQuery) throws SQLException {
        ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sqlQuery);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Appointment appointment = new Appointment(
            rs.getInt("Appointment_ID"),
            rs.getString("Title"),
            rs.getString("Description"),
            rs.getString("Location"),
            rs.getString("Type"),
            rs.getTimestamp("Start").toLocalDateTime(),
            rs.getTimestamp("End").toLocalDateTime(),
            rs.getInt("Customer_ID"),
            rs.getInt("User_ID"),
            rs.getInt("Contact_ID"));
            appointmentsList.add(appointment);
        }
        return appointmentsList;
    }

    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        ObservableList<Appointment> appointmentList = makeAppointmentQuery("SELECT * FROM appointments");
        return appointmentList;
    }

    public static ObservableList<Appointment> getAppointmentsByMonth() throws SQLException {
        ObservableList<Appointment> appointmentList = makeAppointmentQuery("SELECT * FROM client_schedule.appointments WHERE Start >= (CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 1 MONTH AND Start < LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY;");
        return appointmentList;
    }

    public static ObservableList<Appointment> getAppointmentsByWeek() throws SQLException {
        ObservableList<Appointment> appointmentList = makeAppointmentQuery("SELECT * FROM client_schedule.appointments WHERE Start >= (CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 1 WEEK AND Start < LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY;");
        return appointmentList;
    }

    public static void deleteAppointment(int appointmentID) throws SQLException {
        String deleteStatement = "DELETE from appointments WHERE Appointment_ID=" +appointmentID;
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(deleteStatement);
        ps.execute();
    }

    public static boolean addAppointment(String contactName, String title, String location, String type, LocalDateTime start, LocalDateTime end, Integer customerId, Integer userID) throws SQLException {
        Integer contactID = ContactSQL.getContactID(contactName);
        System.out.print(contactID);
        return Boolean.TRUE;
    }
}
