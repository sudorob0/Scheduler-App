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
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Appointment;
import models.Customer;
import utilities.ChangeScene;
import utilities.PopUpBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomersController implements Initializable {
    public TableView customersTable;
    public Button addButton;
    public Button backButton;
    public Button modifyButton;
    public Button deleteButton;
    public TableColumn customeridCol;
    public TableColumn nameCol;
    public TableColumn telephoneCol;
    public TableColumn addressCol;
    public TableColumn divisionCol;
    public TableColumn postalCodeCol;
    public TableColumn countryCol;

    public void initialize(URL url, ResourceBundle resourceBundle) {
    refreshCustomersTable();
    }

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

    public void backButtonClicked(ActionEvent actionEvent) throws IOException {
        ChangeScene mainMenuScene = new ChangeScene();
        mainMenuScene.stringToSceneChange(actionEvent, "MainMenu");
    }

    public void addButtonClicked(ActionEvent actionEvent) throws IOException {
        ChangeScene mainMenuScene = new ChangeScene();
        mainMenuScene.stringToSceneChange(actionEvent, "AddCustomer");
    }

    public void modifyButtonClicked(ActionEvent actionEvent) {
        if (customersTable.getSelectionModel().getSelectedItem() == null) {
            PopUpBox.errorBox("Please select customer to edit");
        } else {
            Customer selectedCustomer = (Customer) customersTable.getSelectionModel().getSelectedItem();
            int currentIndex = customersTable.getSelectionModel().getSelectedIndex();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ModifyCustomer.fxml"));
                Parent root = loader.load();
                ModifyCustomerController modifyCustomerController = loader.getController();
                modifyCustomerController.customerToModify(currentIndex, selectedCustomer);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 500, 500);
                stage.setTitle("Modify Part");
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteButtonClicked(ActionEvent actionEvent) throws SQLException {
        Customer selectedCustomer = (Customer) customersTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null){
            PopUpBox.errorBox("Please select an the customer you would like to delete.");
        } else {
            String popUpString = "Are you sure you want to delete this Customer?\nID: " + selectedCustomer.getCustomerID() + "\nName: " + selectedCustomer.getCustomerName();
            if (PopUpBox.optionBox(popUpString)) {
                AppointmentSQL.deleteCustomerAppointments(selectedCustomer.getCustomerID());
                CustomerSQL.deleteCustomer(selectedCustomer.getCustomerID());
                refreshCustomersTable();
                //3a2
                PopUpBox.infoBox("Customer and customer's appointments successfully deleted.\n Customer ID: " + selectedCustomer.getCustomerID() + ", Name: " + selectedCustomer.getCustomerName());
            }
        }
    }
}
