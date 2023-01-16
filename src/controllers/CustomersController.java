package controllers;

import DAO.AppointmentSQL;
import DAO.CustomerSQL;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Customer;
import utilities.ChangeScene;
import utilities.PopUpBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * controller class for the customers view
 */
public class CustomersController implements Initializable {
    public TableView<Customer> customersTable;
    public Button addButton;
    public Button backButton;
    public Button modifyButton;
    public Button deleteButton;
    public TableColumn<Object, Object> customeridCol;
    public TableColumn<Object, Object> nameCol;
    public TableColumn<Object, Object> telephoneCol;
    public TableColumn<Object, Object> addressCol;
    public TableColumn<Object, Object> divisionCol;
    public TableColumn<Object, Object> postalCodeCol;
    public TableColumn<Object, Object> countryCol;

    /**
     * this init method just refreshes the customer table
     * @param url for initialize
     * @param resourceBundle for initialize
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
    refreshCustomersTable();
    }

    /**
     * This method refreshes  and displays data on the customers table.
     */
    public void refreshCustomersTable(){
        try {
            ObservableList<Customer> allCustomersList = CustomerSQL.getAllCustomers();
            customersTable.setItems((allCustomersList));
            customeridCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            telephoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
            addressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
            divisionCol.setCellValueFactory(new PropertyValueFactory<>("customerDivision"));
            postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
            countryCol.setCellValueFactory(new PropertyValueFactory<>("customerCountry"));
            customersTable.refresh();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /**
     * this method takes the user back to the main menu
     * @param actionEvent backButtonClicked
     * @throws IOException for file errors
     */
    public void backButtonClicked(ActionEvent actionEvent) throws IOException {
        ChangeScene mainMenuScene = new ChangeScene();
        mainMenuScene.stringToSceneChange(actionEvent, "MainMenu");
    }

    /**
     * this method takes the user back to the main menu
     * @param actionEvent addButtonClicked
     * @throws IOException for file errors
     */
    public void addButtonClicked(ActionEvent actionEvent) throws IOException {
        ChangeScene mainMenuScene = new ChangeScene();
        mainMenuScene.stringToSceneChange(actionEvent, "AddCustomer");
    }

    /**
     * this method sends the selected customer to the modify scene to be edited
     * @param actionEvent modifyButtonClicked
     */
    public void modifyButtonClicked(ActionEvent actionEvent) {
        if (customersTable.getSelectionModel().getSelectedItem() == null) {
            PopUpBox.errorBox("Please select customer to edit");
        } else {
            Customer selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
            int currentIndex = customersTable.getSelectionModel().getSelectedIndex();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ModifyCustomer.fxml"));
                Parent root = loader.load();
                ModifyCustomerController modifyCustomerController = loader.getController();
                modifyCustomerController.customerToModify(selectedCustomer);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 500, 500);
                stage.setTitle("Modify Customer");
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * this method deletes a selected customer
     * @throws SQLException for sql errors
     */
    public void deleteButtonClicked() throws SQLException {
        Customer selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null){
            PopUpBox.errorBox("Please select an the customer you would like to delete.");
        } else {
            String popUpString = "Are you sure you want to delete this Customer?\nID: " + selectedCustomer.getCustomerID() + "\nName: " + selectedCustomer.getCustomerName();
            if (PopUpBox.optionBox(popUpString)) {
                AppointmentSQL.deleteCustomerAppointments(selectedCustomer.getCustomerID());
                if (CustomerSQL.deleteCustomer(selectedCustomer.getCustomerID())) {
                    PopUpBox.infoBox("Customer and customer's appointments successfully deleted.\n Customer ID: " + selectedCustomer.getCustomerID() + ", Name: " + selectedCustomer.getCustomerName());
                    refreshCustomersTable();
                } else {
                    PopUpBox.infoBox("There was an error deleting appointment, please try again");
                }
            }
        }
    }
}
