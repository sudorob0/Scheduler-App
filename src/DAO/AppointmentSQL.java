package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Appointment;

import java.sql.*;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;

public class AppointmentSQL {
    /**
     * This method makes sql queries and returns a list of appointment objects
     * @param sqlQuery provide the sequel query to be run on the sql data base
     * @return list of appointment objects
     * @throws SQLException for sql errors
     */
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
        ObservableList<Appointment> appointmentList = makeAppointmentQuery("SELECT * FROM appointments ORDER BY Appointment_ID");
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
        String deleteStatement = "DELETE from appointments WHERE Appointment_ID=" + appointmentID;
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(deleteStatement);
        ps.execute();
    }

    public static boolean addAppointment(String contactName, String title, String description, String location, String type, Integer customerId, Integer userID, LocalDateTime start, LocalDateTime end) throws SQLException {
        Integer contactID = ContactSQL.getContactID(contactName);
        String insertStatement = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Create_Date, Customer_ID, Contact_ID, User_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(insertStatement);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6, Timestamp.valueOf(end));
        ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
        ps.setInt(8, customerId);
        ps.setInt(9, userID);
        ps.setInt(10, contactID);
        try{
            ps.execute();
            return Boolean.TRUE;
        } catch (SQLException e){
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    /**
     * generates an appointment id by retrieving a list ordered by appointment ids and the adding 1 to the last id in the list
     * @return appointment id integer
     * @throws SQLException for sql errors
     */
    public static int generateAppointmentID() throws SQLException {
        ObservableList<Appointment> appointmentList = getAllAppointments();
        Integer newAppointmentID = appointmentList.get(-1).getAppointmentID() + 1;
        return newAppointmentID;
    }

}
