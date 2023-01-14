package controllers;

import DAO.DBConnection;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import models.Appointment;
import utilities.ChangeScene;
import utilities.LoadReportsTable;
import utilities.PopUpBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {
    public Button generateButton;
    public Button backButton;
    public Button clearButton;
    public ComboBox filterComboBox;
    public TextField filterTextField;
    public TableView reportsTable;
    public ComboBox reportTypeComboBox;
    public Label totalEntriesLabel;

    public void initialize(URL url, ResourceBundle resourceBundle){
        ObservableList<String> allTypes = FXCollections.observableArrayList("Appointments", "Customers", "Contacts");
        reportTypeComboBox.setItems(allTypes);
    }

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


    public void generateButtonClicked(ActionEvent actionEvent) {
        tableViewDisplayData("SELECT * FROM Appointments");
        reportsTable.refresh();
    }

    public void backButtonClicked(ActionEvent actionEvent) throws IOException {
        ChangeScene currentChange = new ChangeScene();
        currentChange.stringToSceneChange(actionEvent, "MainMenu");
    }

    public void clearButtonClicked(ActionEvent actionEvent) {

    }

    public void reportTypeSelected(ActionEvent actionEvent) {
    }
}
