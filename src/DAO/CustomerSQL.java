package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * This class handles all the SQL Queries and statements made to the customer database.
 */
public class CustomerSQL {

    /**
     * gets a list of all customers
     * @return a list of all customer objects
     * @throws SQLException for sql errors
     */
    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        PreparedStatement ps = DBConnection.getConnection().prepareStatement("SELECT * FROM customers AS c INNER JOIN first_level_divisions AS d ON c.Division_ID = d.Division_ID INNER JOIN countries AS co ON co.Country_ID=d.Country_ID");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Customer customer = new Customer(
                    rs.getInt("Customer_ID"),
                    rs.getString("Customer_Name"),
                    rs.getString("Address"),
                    rs.getString("Postal_Code"),
                    rs.getString("Phone"),
                    rs.getString("Division"),
                    rs.getInt("Division_ID"),
                    rs.getString("Country"));
            customerList.add(customer);
        }
        return customerList;
    }

    /**
     * deletes a customer from sql database
     * @param customerID customer ID you want to delete
     * @return true if delete is successful
     * @throws SQLException for sql errors
     */
    public static boolean deleteCustomer(int customerID) throws SQLException {
        try {
            String deleteStatement = "DELETE from customers WHERE Customer_ID=" + customerID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(deleteStatement);
            ps.execute();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * creates a new customer in the sql database
     * @param name name of the customer
     * @param phone customers phone number
     * @param division customers division
     * @param address address of the customer
     * @param postalCode postal/zipcode of the customer
     * @return true if insert statement was successful
     * @throws SQLException for sql errors
     */
    public static boolean addCustomer(String name, String phone, String division, String address, String postalCode) throws SQLException {
        String insertStatement = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Division_ID, Create_Date) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(insertStatement);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setString(5, LocationSQL.getDivisionID(division));
        ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));

        try{
            ps.execute();
            return Boolean.TRUE;
        } catch (SQLException e){
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    /**
     * it modifies an existing customer in the database
     * @param name name of the customer
     * @param phone customers phone number
     * @param division customers division
     * @param address address of the customer
     * @param postalCode postal/zipcode of the customer
     * @return true if insert statement was successful
     * @throws SQLException for sql errors
     */
    public static boolean modifyCustomer(String customerID, String name, String phone, String division, String address, String postalCode) throws SQLException {
        String insertStatement = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ?, Last_Update = ? WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(insertStatement);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setString(5, LocationSQL.getDivisionID(division));
        ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(7, customerID);
        try{
            ps.execute();
            return Boolean.TRUE;
        } catch (SQLException e){
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }
}
