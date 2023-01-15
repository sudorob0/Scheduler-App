package controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import java.io.IOException;
import utilities.ChangeScene;

public class MainMenuController {
    public Button customersButton;
    public Button appointmentsButton;
    public Button reportsButton;
    public Button logoutButton;

    /**
     * take user to customers scene
     * @param actionEvent customersButtonClicked
     * @throws IOException for file errors
     */
    public void customersButtonClicked(ActionEvent actionEvent) throws IOException {
        ChangeScene currentChange = new ChangeScene();
        currentChange.stringToSceneChange(actionEvent, "Customers");
    }

    /**
     * take user to appointments scene
     * @param actionEvent appointmentsButtonClicked
     * @throws IOException for file errors
     */
    public void appointmentsButtonClicked(ActionEvent actionEvent) throws IOException {
        ChangeScene currentChange = new ChangeScene();
        currentChange.stringToSceneChange(actionEvent, "Appointments");
    }

    /**
     * take user to reports scene
     * @param actionEvent reportsButtonClicked
     * @throws IOException for file errors
     */
    public void reportsButtonClicked(ActionEvent actionEvent) throws IOException {
        ChangeScene currentChange = new ChangeScene();
        currentChange.stringToSceneChange(actionEvent, "Reports");
    }

    /**
     * take user to login scene
     * @param actionEvent logoutButtonClicked
     * @throws IOException for file errors
     */
    public void logoutButtonClicked(ActionEvent actionEvent) throws IOException {
        ChangeScene currentChange = new ChangeScene();
        currentChange.stringToSceneChange(actionEvent, "Login");
    }
}
