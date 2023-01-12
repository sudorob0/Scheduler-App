package controllers;

import DAO.CountrySQL;
import DAO.UserSQL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import models.Country;
import models.User;
import utilities.ChangeScene;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {
    public TextField customeridTextField;
    public TextField nameTextField;
    public TextField telephoneTextField;
    public TextField addressTextField;
    public TextField zipcodeField;
    public ComboBox countryComboBox;
    public ComboBox divisionComboBox;
    public Button addButton;
    public Button backButton;

    public void initialize(URL url, ResourceBundle resourceBundle){

        ObservableList<String> countryNames = FXCollections.observableArrayList();
        ObservableList<Country> countriesObjectList = CountrySQL.getAllCountries();
        countriesObjectList.forEach(country -> countryNames.add(country.getCountry()));
        countryComboBox.setItems(countryNames);

    }

    public void addButtonClicked(ActionEvent actionEvent) {
    }

    public void backButtonClicked(ActionEvent actionEvent) throws IOException {
        ChangeScene mainMenuScene = new ChangeScene();
        mainMenuScene.stringToSceneChange(actionEvent, "Customers");
    }

    public void countrySelected(ActionEvent actionEvent) throws SQLException {
        divisionComboBox.getSelectionModel().clearSelection();
        String countryName = (String) countryComboBox.getSelectionModel().getSelectedItem();
        Integer countryID = CountrySQL.getCountryID(countryName);
        divisionComboBox.setItems(CountrySQL.getDivisionNames(countryID));
    }
}
