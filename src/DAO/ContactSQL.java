package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for contact SQL queries
 */
public class ContactSQL {
    /**
     * Method queries the contact data base and returns a list of contact objects
     * @return a list of contact objects
     * @throws SQLException for any SQL errors
     */
    public static ObservableList<Contact> getAllContacts() throws SQLException {
        ObservableList<Contact> contactsList = FXCollections.observableArrayList();
        PreparedStatement ps = DBConnection.getConnection().prepareStatement("SELECT * from contacts");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Contact contact = new Contact(
                    rs.getInt("Contact_ID"),
                    rs.getString("Contact_Name"),
                    rs.getString("Email"));
            contactsList.add(contact);
        }
        return contactsList;
    }
}
