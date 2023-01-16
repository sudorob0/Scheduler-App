package controllers;

import DAO.CustomerSQL;
import DAO.DBConnection;
import DAO.UserSQL;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import models.Customer;
import models.User;
import utilities.ChangeScene;
import utilities.PopUpBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * this class is for all the methods that control the reports screen
 */
public class ReportsController implements Initializable {
    public Button generateButton;
    public Button backButton;
    public Button clearButton;
    public ComboBox filterComboBox;
    public TextField filterTextField;
    public TableView reportsTable;
    public ComboBox<String> reportTypeComboBox;
    public Label totalEntriesLabel;
    public Label firstFilterLabel;
    public ComboBox firstFilterComboBox;
    public ComboBox<String> secondFilterComboBox;
    public Label secondFilterLabel;

    /**
     * Initialize method to load the report type combo box
     * @param url
     * @param resourceBundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle){
        ObservableList<String> allTypes = FXCollections.observableArrayList("Appointments", "Customer Appointments", "User Appointments");
        reportTypeComboBox.setItems(allTypes);
    }

    /**
     * This method dynamically displays data from the sql database to the table view
     * @param sqlQuery string that will populate the display table
     */
    public void tableViewDisplayData(String sqlQuery) {
        Connection connection;
        ObservableList<Object> data = FXCollections.observableArrayList();
        try {
            connection = DBConnection.getConnection();
            ResultSet rs = connection.createStatement().executeQuery(sqlQuery);
            // create columns by looping through the column in the sql database
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                //get sql column name
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                Callback callback = (Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString());
                col.setCellValueFactory(callback);
                reportsTable.getColumns().addAll(col);
            }
            int numberOfEntries = 0;
            // Loop through data and add to list
            while (rs.next()) {

                ObservableList<String> row = FXCollections.observableArrayList();
                // loop through the rows and add the stings into their cells
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    // Conditional statement to handle null entries
                    if (rs.getString(i) != null){
                        row.add(rs.getString(i));
                    } else {
                        row.add("");
                    }
                }
                numberOfEntries += 1;
                data.add(row);
            }
            totalEntriesLabel.setText(String.valueOf(numberOfEntries));
            // Add to reports table
            reportsTable.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            PopUpBox.errorBox("Error loading data");
        }
    }


    /**
     * this method checks if there is data to populate the display table and
     * if there is the it will call the the tableViewDisplayData to display the data
     * @param actionEvent
     */
    public void generateButtonClicked(ActionEvent actionEvent) {
        String sqlQuery = generateSQL();
        if (!sqlQuery.equals("")) {
            tableViewDisplayData(generateSQL());
            reportsTable.refresh();
        } else {
            PopUpBox.errorBox("Please make valid selection");
        }
    }

    /**
     * Changes the scene back to the main menu
     * @param actionEvent backButtonClicked
     * @throws IOException for file errors
     */
    public void backButtonClicked(ActionEvent actionEvent) throws IOException {
        ChangeScene currentChange = new ChangeScene();
        currentChange.stringToSceneChange(actionEvent, "MainMenu");
    }

    /**
     * clears all the combo boxes
     * @param actionEvent clearButtonClicked
     */
    public void clearButtonClicked(ActionEvent actionEvent) {
        try {
            reportTypeComboBox.getSelectionModel().clearSelection();
            firstFilterLabel.setText("Filter");
            firstFilterComboBox.getSelectionModel().clearSelection();
            secondFilterLabel.setText("Filter");
            secondFilterComboBox.getSelectionModel().clearSelection();
        } catch (Exception e){
        }
    }

    /**
     * This method loads the filter combo boxes based on what the user selected in the report combo box
     * @param actionEvent report combo box selected
     */
    public void reportTypeSelected(ActionEvent actionEvent) {
        String reportTypeSelected = reportTypeComboBox.getSelectionModel().getSelectedItem();
        firstFilterComboBox.getSelectionModel().clearSelection();
        secondFilterComboBox.getSelectionModel().clearSelection();
        firstFilterComboBox.getItems().clear();
        secondFilterComboBox.getItems().clear();
        if (reportTypeSelected.equals("Appointments")){
            firstFilterLabel.setText("Type Filter :");
            ObservableList<String> allTypes = FXCollections.observableArrayList("Planning Session", "De-Briefing", "Follow-up", "1 on 1", "Other");
            firstFilterComboBox.setItems(allTypes);

            secondFilterLabel.setText("Month Filter :");
            ObservableList<String> months = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
            secondFilterComboBox.setItems(months);
        } else if (reportTypeSelected.equals("Customer Appointments")) {
            firstFilterLabel.setText("ID Filter :");
            try {
                ObservableList<Integer> customersIDList = FXCollections.observableArrayList();
                ObservableList<Customer> customersList = CustomerSQL.getAllCustomers();
                customersList.forEach(customer -> customersIDList.add(customer.getCustomerID()));
                firstFilterComboBox.setItems(customersIDList);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            secondFilterLabel.setText("No Filter");
        } else if (reportTypeSelected.equals("User Appointments")) {
            firstFilterLabel.setText("ID Filter :");
            try {
                ObservableList<Integer> userIDList = FXCollections.observableArrayList();
                ObservableList<User> userList = UserSQL.getAllUsers();
                userList.forEach(user -> userIDList.add(user.getUserID()));
                firstFilterComboBox.setItems(userIDList);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            secondFilterLabel.setText("No Filter");
        } else {}
    }

    /**
     * This generate the sql queries based on what is selected in the combo boxes
     * its a lot of conditionals and combining strings together
     * @return
     */
    public String generateSQL(){
        Object reportType = reportTypeComboBox.getSelectionModel().getSelectedItem();
        String appointmentQuery = "SELECT * FROM Appointments";
        if (reportType == null) {
            return "";
        } else if (reportType == "Appointments"){
            Object typeFilter = firstFilterComboBox.getSelectionModel().getSelectedItem();
            Object monthFilter = secondFilterComboBox.getSelectionModel().getSelectedItem();
            if (typeFilter != null && monthFilter != null) {
                String nextMonth = String.valueOf(Integer.valueOf(monthFilter.toString()) + 1);
                String currentYear = String.valueOf(LocalDate.now().getYear());
                String typeString = typeFilter.toString();
                // Example query SELECT * FROM Appointments WHERE Type = 'Planning Session' and Start between ('2023-1-1') and ('2023-2-1');
                String totalQuery = appointmentQuery+ " WHERE Type = '" + typeString + "' and Start between ('"+currentYear+"-"+monthFilter+"-1') and ('"+currentYear+"-"+nextMonth+"-1');";
                //System.out.print(totalQuery);
                return totalQuery;
            } else if (monthFilter != null) {
                String nextMonth = String.valueOf(Integer.valueOf(monthFilter.toString()) + 1);
                String currentYear = String.valueOf(LocalDate.now().getYear());
                // date format = 2023-02-14
                String totalQuery = appointmentQuery + " WHERE Start between ('"+currentYear+"-"+monthFilter+"-1') and ('"+currentYear+"-"+nextMonth+"-1');";
                return totalQuery;
            } else if (typeFilter != null) {
                String totalQuery = appointmentQuery + " WHERE Type = '" + typeFilter +"';";
                return totalQuery;
            } else {
                // if both filters are equal to null
                return appointmentQuery;
            }
        } else if (reportType == "Customer Appointments"){
            if (firstFilterComboBox.getSelectionModel().getSelectedItem() == null){return appointmentQuery;}
            String customerID = firstFilterComboBox.getSelectionModel().getSelectedItem().toString();
            String totalQuery = appointmentQuery + " WHERE Customer_ID = '" + customerID + "';";
            return totalQuery;
        } else if (reportType == "User Appointments") {
            if (firstFilterComboBox.getSelectionModel().getSelectedItem() == null){return appointmentQuery;}
            String userID = firstFilterComboBox.getSelectionModel().getSelectedItem().toString();
            String totalQuery = appointmentQuery + " WHERE User_ID = '" + userID + "';";
            return totalQuery;
        } else {
            return "";
        }
    }
}
