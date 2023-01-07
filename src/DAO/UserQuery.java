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
    public static Boolean authenticateUser(String userName, String password) throws SQLException {
        try {
            String sqlFindUser = "SELECT User_Name = '" +userName+ "' FROM users";
            System.out.print(sqlFindUser);
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sqlFindUser);
            ResultSet rs = ps.executeQuery();
            System.out.print(rs.getRow());
        } catch (Exception e) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }
}
