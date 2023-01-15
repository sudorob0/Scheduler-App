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

    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        ObservableList<Appointment> appointmentList = makeAppointmentQuery("SELECT * FROM appointments ORDER BY Appointment_ID");
        return appointmentList;
    }

    public static ObservableList<Appointment> getAppointmentsWithinFifteenMins(Integer userID) throws SQLException {
        //convert to UTC-0 since that is what the database is on
        ZonedDateTime currentDBtime = DBConnection.convertToDBTime(ZonedDateTime.now());
        String rightNow = String.valueOf(currentDBtime);
        String inFifteenMins = String.valueOf(currentDBtime.plusMinutes(15));
        String sqlQuery = "SELECT * FROM Appointments WHERE Start between ('"+rightNow+"') and ('"+inFifteenMins+"') and User_id = '"+userID+"';";
        //EXAMPLE QUERY: SELECT * FROM Appointments WHERE Start between ('2023-01-14T20:41:16.298123900Z') and ('2023-01-14T20:56:16.298123900Z') and User_id = '1';
        ObservableList<Appointment> appointmentList = makeAppointmentQuery(sqlQuery);
        return appointmentList;
    }

    /**
     * This method does a query to find overlapping appointments and returns any overlapping appointments
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


    public static ObservableList<Appointment> getAppointmentsByMonth() throws SQLException {
        String thisYear = String.valueOf(LocalDate.now().getYear());
        String thisMonth = String.valueOf(LocalDate.now().getMonthValue());
        String nextMonth = String.valueOf(LocalDate.now().getMonthValue() + 1);
        String sqlQuery = "SELECT * FROM appointments WHERE Start between ('"+thisYear+"-"+thisMonth+"-1') and ('"+thisYear+"-"+nextMonth+"-1 01:00:00');";
        // EXAMPLE: SELECT * FROM client_schedule.appointments WHERE Start between ('2023-1-1') and ('2023-2-1 01:00:00');
        ObservableList<Appointment> appointmentList = makeAppointmentQuery(sqlQuery);
        return appointmentList;
    }

    public static ObservableList<Appointment> getAppointmentsByWeek() throws SQLException {
        String dayOfWeek = LocalDate.now().getDayOfWeek().toString();
        int daysUntilEndOfWeek = 0;
            switch (dayOfWeek) {
                case "Sunday":  daysUntilEndOfWeek=6;
                    break;
                case "Monday":  daysUntilEndOfWeek=5;
                    break;
                case "Tuesday":  daysUntilEndOfWeek=4;
                    break;
                case "Wednesday":  daysUntilEndOfWeek=3;
                    break;
                case "Thursday":  daysUntilEndOfWeek=2;
                    break;
                case "Friday":  daysUntilEndOfWeek=1;
                    break;
                case "Saturday":  daysUntilEndOfWeek=0;
                    break;
            }
        String endOfWeek = String.valueOf(LocalDate.now().plusDays(daysUntilEndOfWeek));
        String beginningOfWeek = String.valueOf(LocalDate.now().plusDays(daysUntilEndOfWeek-6));
        // EXAMPLE VALUE: SELECT * FROM client_schedule.appointments WHERE Start between ('2023-01-08') and ('2023-01-14');
        String sqlQuery = "SELECT * FROM appointments WHERE Start between ('"+beginningOfWeek+"') and ('"+endOfWeek+"');";
        ObservableList<Appointment> appointmentList = makeAppointmentQuery(sqlQuery);
        return appointmentList;
    }

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
