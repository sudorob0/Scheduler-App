package DAO;

import DAO.DBConnection;
import models.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class CountriesQuery {

    public static ObservableList<Country> getAllCountries(){
        ObservableList<Country> countriesList = FXCollections.observableArrayList();

        return countriesList;
    }

    public static void checkDateConversion(){
        System.out.println("Create Date Test");
    }
}
