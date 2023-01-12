package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Contact;
import models.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerSQL {

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
}
