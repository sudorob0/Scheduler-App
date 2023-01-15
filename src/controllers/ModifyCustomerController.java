package controllers;

import DAO.LocationSQL;
import DAO.CustomerSQL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import models.Country;
import models.Customer;
import utilities.ChangeScene;
import utilities.PopUpBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifyCustomerController implements Initializable {
    public TextField customeridTextField;
    public TextField nameTextField;
    public TextField telephoneTextField;
    public TextField addressTextField;
    public ComboBox<String> countryComboBox;
    public ComboBox<String> divisionComboBox;
    public Button addButton;
    public Button backButton;
    public TextField postalCodeField;
    public Button saveButton;

    /**
     * initialize method to populate the countries combo box
     * @param url for init
     * @param resourceBundle for init
     */
    public void initialize(URL url, ResourceBundle resourceBundle){

        ObservableList<String> countryNames = FXCollections.observableArrayList();
        ObservableList<Country> countriesObjectList = LocationSQL.getAllCountries();
        countriesObjectList.forEach(country -> countryNames.add(country.getCountry()));
        countryComboBox.setItems(countryNames);

    }

    /**
     * Set the fields to the customer attributes that were sent from the customer screen
     * @param customer customer to edited
     */
    public void customerToModify(Customer customer) {
        customeridTextField.setText(String.valueOf(customer.getCustomerID()));
        nameTextField.setText(customer.getCustomerName());
        telephoneTextField.setText(customer.getCustomerPhone());
        countryComboBox.setValue(customer.getCustomerCountry());
        divisionComboBox.setValue(customer.getCustomerDivision());
        addressTextField.setText(customer.getCustomerAddress());
        postalCodeField.setText(customer.getCustomerPostalCode());
    }

    /**
     * Validate the data entered in the fields, then save the data to the customer sql database
     * @param actionEvent saveButtonClicked
     * @throws SQLException for sql errors
     * @throws IOException for file errors
     */
    public void saveButtonClicked(ActionEvent actionEvent) throws SQLException, IOException {
        if (nameTextField.getText().equals("")){PopUpBox.errorBox("The name field must be filled out to continue");}
        else if (telephoneTextField.getText().equals("")){PopUpBox.errorBox("The telephone field must be filled out to continue");}
        else if (countryComboBox.getSelectionModel().getSelectedItem() == null){PopUpBox.errorBox("The country type field must be filled out to continue");}
        else if (divisionComboBox.getSelectionModel().getSelectedItem() == null){PopUpBox.errorBox("The division field must be filled out to continue");}
        else if (addressTextField.getText().equals("")){PopUpBox.errorBox("The address field must be filled out to continue");}
        else if (postalCodeField.getText().equals("")){PopUpBox.errorBox("The postal code ID field must be filled out to continue");}
        else {
            if (CustomerSQL.modifyCustomer(customeridTextField.getText(), nameTextField.getText(), telephoneTextField.getText(), divisionComboBox.getSelectionModel().getSelectedItem(), addressTextField.getText(), postalCodeField.getText())){
                PopUpBox.infoBox("Customer has been successfully changed");
                ChangeScene backScene = new ChangeScene();
                backScene.stringToSceneChange(actionEvent, "Customers");
            } else{
                PopUpBox.infoBox("There was a problem editing the customer, please try again");
            }
        }
    }

    /**
     * this method changes the scene back to the customers view
     * @param actionEvent backButtonClicked
     * @throws IOException for file errors
     */
    public void backButtonClicked(ActionEvent actionEvent) throws IOException {
        ChangeScene backScene = new ChangeScene();
        backScene.stringToSceneChange(actionEvent, "Customers");
    }

    /**
     * This method populates the division combo box
     * @throws SQLException for sql errors
     */
    public void countrySelected() throws SQLException {
        divisionComboBox.getSelectionModel().clearSelection();
        String countryName = countryComboBox.getSelectionModel().getSelectedItem();
        Integer countryID = LocationSQL.getCountryID(countryName);
        divisionComboBox.setItems(LocationSQL.getDivisionNames(countryID));
    }
}

