package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class to make user queries
 */
public class UserQuery {

    /**
     * This method queries the database for any rows that have the entered username and returns TRUE if the password
     * for that user is the entered password.
     * @param enteredUserName username that the user entered
     * @param enteredPassword password that the user entered
     * @return ether TRUE if the username and password are authenticated or FALSE if they are not
     * @throws SQLException will return False
     */
    public static Boolean authenticateUser(String enteredUserName, String enteredPassword) throws SQLException {
        try {
            String sqlFindUser = "SELECT * FROM users WHERE User_Name = '"+enteredUserName+"'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sqlFindUser);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String userPassword = rs.getString("Password");
                System.out.print(userPassword);
                // Will return TRUE if they match
                return userPassword.equals(enteredPassword);
            }
        } catch (SQLException e) {
            return Boolean.FALSE;
        }
        return Boolean.FALSE;
    }
}
