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

    /**
     * This method gets a contactID when provided with a contacts name
     * @param contactName name of the contact you need an id for
     * @return contactID
     * @throws SQLException for sql errors
     */
    public static Integer getContactID(String contactName) throws SQLException {
        PreparedStatement ps = DBConnection.getConnection().prepareStatement("SELECT * from contacts WHERE Contact_Name = '" + contactName + "';");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            return rs.getInt("Contact_ID");
        }
        return null;
    }

    /**
     * This method gets a contact name when provided with a contacts id
     * @param contactID id of the contact you need an name for
     * @return contactName
     * @throws SQLException for sql errors
     */
    public static String getContactName(Integer contactID) throws SQLException {
        PreparedStatement ps = DBConnection.getConnection().prepareStatement("SELECT * from contacts WHERE Contact_ID = '" + contactID + "';");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            return rs.getString("Contact_Name");
        }
        return null;
    }
}
