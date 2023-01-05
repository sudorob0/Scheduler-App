package DAO;

import DAO.DBConnection;
import models.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


public class CountriesQuery {

    /**
     * Get all countries from the sql database and return an observable list
     * @return observable list of countries
     */
    public static ObservableList<Country> getAllCountries(){
        ObservableList<Country> countriesList = FXCollections.observableArrayList();

        try{
            String sql = "SELEC * from countries";

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

    /**
     * check if date conversion is working
     */
    public static void checkDateConversion(){
        System.out.println("Create Date Test");
        String sql = "select Create_Date from countries";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("Create_Date");
                System.out.println(("CD: " + ts.toLocalDateTime().toString()));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
