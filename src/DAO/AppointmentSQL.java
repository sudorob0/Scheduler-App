package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Appointment;
import java.sql.*;
import java.sql.PreparedStatement;
import java.time.*;

public class AppointmentSQL {
    /**
     * This method makes sql queries and returns a list of appointment objects
     * @param sqlQuery provide the sequel query to be run on the sql data base
     * @return list of appointment objects
     * @throws SQLException for sql errors
     */
    public static ObservableList<Appointment> makeAppointmentQuery(String sqlQuery) throws SQLException {
        //System.out.print(sqlQuery);
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

    /**
     * gets a list of Appointment Objects
     * @return list of appointment objects
     * @throws SQLException for sql errors
     */
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        return makeAppointmentQuery("SELECT * FROM appointments ORDER BY Appointment_ID");
    }

    /**
     * gets a list of appointments for a user withing 15minutes of the query
     * @param userID userID that is logging in
     * @return list of appointment objects with in 15mins
     * @throws SQLException for sql errors
     */
    public static ObservableList<Appointment> getAppointmentsWithinFifteenMins(Integer userID) throws SQLException {
        //convert to UTC-0 since that is what the database is on
        ZonedDateTime currentDBtime = DBConnection.convertToDBTime(ZonedDateTime.now());
        String rightNow = String.valueOf(currentDBtime);
        String inFifteenMins = String.valueOf(currentDBtime.plusMinutes(15));
        String sqlQuery = "SELECT * FROM Appointments WHERE Start between ('"+rightNow+"') and ('"+inFifteenMins+"') and User_id = '"+userID+"';";
        //EXAMPLE QUERY: SELECT * FROM Appointments WHERE Start between ('2023-01-14T20:41:16.298123900Z') and ('2023-01-14T20:56:16.298123900Z') and User_id = '1';
        return makeAppointmentQuery(sqlQuery);
    }

    /**
     * This method does a query to find overlapping appointments and returns any overlapping appointments
     * It runs 4 total queries if it doesn't find any overlapping appointments it returns an empty list
     * @param startDT provide start date time of the appointment
     * @param endDT provide end date time of the appointment
     * @return a list of appointment that overlap
     * @throws SQLException for sql errors
     */
    public static ObservableList<Appointment> findOverLappingAppointments(ZonedDateTime startDT, ZonedDateTime endDT) throws SQLException {
        //convert to UTC-0 since that is what the database is on
        String startDateTime = String.valueOf(DBConnection.convertToDBTime(startDT));
        String endDateTime = String.valueOf(DBConnection.convertToDBTime(endDT));
        // EXAMPLE QUERY: SELECT * FROM client_schedule.appointments WHERE Start > ('2023-01-15 15:50:00') and Start < ('2023-01-15 16:10:00');
        String sqlQuery = "SELECT * FROM appointments WHERE Start > ('" + startDateTime + "') and Start < ('" + endDateTime + "');";
        //System.out.print(sqlQuery+"\n");
        //EXAMPLE QUERY: SELECT * FROM Appointments WHERE Start between ('2023-01-14T20:41:16.298123900Z') and ('2023-01-14T20:56:16.298123900Z') and User_id = '1';
        ObservableList<Appointment> appointmentList = makeAppointmentQuery(sqlQuery);
        if (appointmentList.size() > 0) {
            return appointmentList;
        }
        sqlQuery = "SELECT * FROM appointments WHERE End > ('" + startDateTime + "') and End < ('" + endDateTime + "');";
        //System.out.print(sqlQuery+"\n");
        //EXAMPLE QUERY: SELECT * FROM Appointments WHERE Start between ('2023-01-14T20:41:16.298123900Z') and ('2023-01-14T20:56:16.298123900Z') and User_id = '1';
        appointmentList = makeAppointmentQuery(sqlQuery);
        if (appointmentList.size() > 0) {
            return appointmentList;
        }
        sqlQuery = "SELECT * FROM appointments WHERE Start < ('" + startDateTime + "') and End > ('" + startDateTime + "');";
        //System.out.print(sqlQuery + "\n");
        ////EXAMPLE QUERY: SELECT * FROM Appointments WHERE Start between ('2023-01-14T20:41:16.298123900Z') and ('2023-01-14T20:56:16.298123900Z') and User_id = '1';
        appointmentList = makeAppointmentQuery(sqlQuery);
        if (appointmentList.size() > 0) {
            return appointmentList;
        }
        sqlQuery = "SELECT * FROM appointments WHERE Start < ('" + endDateTime + "') and End > ('" + endDateTime + "');";
        //System.out.print(sqlQuery+"\n"+"\n");
        //EXAMPLE QUERY: SELECT * FROM Appointments WHERE Start between ('2023-01-14T20:41:16.298123900Z') and ('2023-01-14T20:56:16.298123900Z') and User_id = '1';
        appointmentList = makeAppointmentQuery(sqlQuery);
        return appointmentList;
    }

    /**
     * get all appointments within the current month
     * @return list of all appointments within the current month
     * @throws SQLException for sql error
     */
    public static ObservableList<Appointment> getAppointmentsByMonth() throws SQLException {
        String thisYear = String.valueOf(LocalDate.now().getYear());
        String thisMonth = String.valueOf(LocalDate.now().getMonthValue());
        String nextMonth = String.valueOf(LocalDate.now().getMonthValue() + 1);
        String sqlQuery = "SELECT * FROM appointments WHERE Start between ('"+thisYear+"-"+thisMonth+"-1') and ('"+thisYear+"-"+nextMonth+"-1 01:00:00');";
        // EXAMPLE: SELECT * FROM client_schedule.appointments WHERE Start between ('2023-1-1') and ('2023-2-1 01:00:00');
        return makeAppointmentQuery(sqlQuery);
    }

    /**
     * Gets a a list of appointment objects for the current week
     * @return list of appointments objects in the current week
     * @throws SQLException for sql errors
     */
    public static ObservableList<Appointment> getAppointmentsByWeek() throws SQLException {
        String dayOfWeek = LocalDate.now().getDayOfWeek().toString();
        // change the current weekday to an integer
        int daysUntilEndOfWeek = switch (dayOfWeek) {
            case "Sunday" -> 6;
            case "Monday" -> 5;
            case "Tuesday" -> 4;
            case "Wednesday" -> 3;
            case "Thursday" -> 2;
            case "Friday" -> 1;
            case "Saturday" -> 0;
            default -> 0;
        };
        String endOfWeek = String.valueOf(LocalDate.now().plusDays(daysUntilEndOfWeek));
        String beginningOfWeek = String.valueOf(LocalDate.now().plusDays(daysUntilEndOfWeek-6));
        // EXAMPLE VALUE: SELECT * FROM client_schedule.appointments WHERE Start between ('2023-01-08') and ('2023-01-14');
        String sqlQuery = "SELECT * FROM appointments WHERE Start between ('"+beginningOfWeek+"') and ('"+endOfWeek+"');";
        return makeAppointmentQuery(sqlQuery);
    }

    /**
     * deletes one appointment from the sql database
     * @param appointmentID appointment ID to be deleted
     * @return true if the delete was successful
     * @throws SQLException for sql error
     */
    public static boolean deleteAppointment(int appointmentID) throws SQLException {
        try {
            String deleteStatement = "DELETE from appointments WHERE Appointment_ID=" + appointmentID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(deleteStatement);
            ps.execute();
            return true;
        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This deletes all appointments for a customer
     * @param customerID customerID that is getting deleted
     * @return true if statement was successful
     * @throws SQLException for sql errors
     */
    public static boolean deleteCustomerAppointments(int customerID) throws SQLException {
        try {
            String deleteStatement = "DELETE from appointments WHERE Customer_ID=" + customerID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(deleteStatement);
            ps.execute();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method adds one new appointment
     * @param contactName name of contact
     * @param title title of the appointment
     * @param description description for appointment
     * @param location location of appointment
     * @param type type of appointment
     * @param customerId customer id that the appointment is for
     * @param userID userID seeing customer
     * @param start start time of appointment
     * @param end end time of appointment
     * @return true if successful
     * @throws SQLException for sql errors
     */
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
     * This method adds one new appointment
     * @param contact name of contact
     * @param title title of the appointment
     * @param description description for appointment
     * @param location location of appointment
     * @param type type of appointment
     * @param customerID customer id that the appointment is for
     * @param userID userID seeing customer
     * @param startDateTime start time of appointment
     * @param endDateTime end time of appointment
     * @return true if successful
     * @throws SQLException for sql errors
     */
    public static boolean modifyAppointment(String appointmentID, Integer contact, String title, String description, String location, String type, Integer customerID, Integer userID, LocalDateTime startDateTime, LocalDateTime endDateTime) throws SQLException {
        String insertStatement = "UPDATE Appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(insertStatement);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(startDateTime));
        ps.setTimestamp(6, Timestamp.valueOf(endDateTime));
        ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
        ps.setInt(8, customerID);
        ps.setInt(9, userID);
        ps.setInt(10, contact);
        ps.setString(11, appointmentID);

        try{
            ps.execute();
            return Boolean.TRUE;
        } catch (SQLException e){
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }
}
