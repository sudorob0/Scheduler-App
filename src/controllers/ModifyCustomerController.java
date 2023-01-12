package controllers;

import DAO.ContactSQL;
import DAO.CountrySQL;
import DAO.CustomerSQL;
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
import utilities.PopUpBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifyCustomerController implements Initializable {
    public TextField customeridTextField;
    public TextField nameTextField;
    public TextField telephoneTextField;
    public TextField addressTextField;
    public ComboBox countryComboBox;
    public ComboBox divisionComboBox;
    public Button addButton;
    public Button backButton;
    public TextField postalCodeField;
    public Button saveButton;

    public void initialize(URL url, ResourceBundle resourceBundle){

        ObservableList<String> countryNames = FXCollections.observableArrayList();
        ObservableList<Country> countriesObjectList = CountrySQL.getAllCountries();
        countriesObjectList.forEach(country -> countryNames.add(country.getCountry()));
        countryComboBox.setItems(countryNames);

    }

    public void saveButtonClicked(ActionEvent actionEvent) throws SQLException, IOException {
        if (nameTextField.getText() == ""){PopUpBox.errorBox("The name field must be filled out to continue");}
        else if (telephoneTextField.getText() == ""){PopUpBox.errorBox("The telephone field must be filled out to continue");}
        else if (countryComboBox.getSelectionModel().getSelectedItem() == null){PopUpBox.errorBox("The country type field must be filled out to continue");}
        else if (divisionComboBox.getSelectionModel().getSelectedItem() == null){PopUpBox.errorBox("The division field must be filled out to continue");}
        else if (addressTextField.getText() == ""){PopUpBox.errorBox("The address field must be filled out to continue");}
        else if (postalCodeField.getText() == ""){PopUpBox.errorBox("The postal code ID field must be filled out to continue");}
        else {
            if (CustomerSQL.modifyCustomer(nameTextField.getText(), telephoneTextField.getText(), (String) divisionComboBox.getSelectionModel().getSelectedItem(), addressTextField.getText(), postalCodeField.getText())){
                PopUpBox.infoBox("Customer has been successfully added");
                ChangeScene backScene = new ChangeScene();
                backScene.stringToSceneChange(actionEvent, "Customers");
            } else{
                PopUpBox.infoBox("There was a problem adding customer, please try again");
            }
        }
    }

    public void backButtonClicked(ActionEvent actionEvent) throws IOException {
        ChangeScene backScene = new ChangeScene();
        backScene.stringToSceneChange(actionEvent, "Customers");
    }

    /**
     * This method populates the division combobox
     * @param actionEvent activated when a country is selected
     * @throws SQLException for sql errors
     */
    public void countrySelected(ActionEvent actionEvent) throws SQLException {
        divisionComboBox.getSelectionModel().clearSelection();
        String countryName = (String) countryComboBox.getSelectionModel().getSelectedItem();
        Integer countryID = CountrySQL.getCountryID(countryName);
        divisionComboBox.setItems(CountrySQL.getDivisionNames(countryID));
    }
}

