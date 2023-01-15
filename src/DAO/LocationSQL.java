package DAO;

import DAO.DBConnection;
import models.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


public class LocationSQL {

    /**
     * Get all countries from the sql database and return an observable list
     * @return observable list of countries
     */
    public static ObservableList<Country> getAllCountries(){
        ObservableList<Country> countriesList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from countries";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Country country = new Country(countryID, countryName);
                countriesList.add(country);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return countriesList;
    }

    public static Integer getCountryID(String countryName) throws SQLException {
        PreparedStatement ps = DBConnection.getConnection().prepareStatement("SELECT * from countries WHERE Country = '" + countryName + "';");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Integer countryID = rs.getInt("Country_ID");
            return countryID;
        }
        return null;
    }

    public static String getDivisionID(String divisionName) throws SQLException {
        PreparedStatement ps = DBConnection.getConnection().prepareStatement("SELECT * from first_level_divisions WHERE Division = '" + divisionName + "';");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            String divisionID = rs.getString("Division_ID");
            return divisionID;
        }
        return null;
    }

    public static ObservableList<String> getDivisionNames(Integer countryID) throws SQLException {
        ObservableList<String> divisionNames = FXCollections.observableArrayList();
        PreparedStatement ps = DBConnection.getConnection().prepareStatement("SELECT * from first_level_divisions WHERE Country_ID = '" + countryID + "';");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            String countryName = rs.getString("Division");
            divisionNames.add(countryName);
        }
        return divisionNames;
    }
}
