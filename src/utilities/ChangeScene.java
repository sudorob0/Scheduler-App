package utilities;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangeScene {

    /**
     * this method is to change a scene given quite a few parameters I started using the stringToSceneChange more
     */
    public void changeScene(ActionEvent actionEvent, String fxmlFile, int sceneWidth, int sceneHeight, String screenTitle) throws IOException {
        String path = "/views/" + fxmlFile;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, sceneWidth, sceneHeight);
        stage.setTitle(screenTitle);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method changes the scene based on the string parameter
     * @param actionEvent passed in from a button click
     * @param sceneName a string of the scene to change to
     * @throws IOException for directory errors
     */
    public void stringToSceneChange(ActionEvent actionEvent, String sceneName) throws IOException {
        String path = "/views/" + sceneName + ".fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        switch (sceneName) {
            case "Login" -> {
                Scene scene = new Scene(root, 580, 390);
                stage.setTitle(sceneName);
                stage.setScene(scene);
                stage.show();
            }
            case "MainMenu" -> {
                Scene scene = new Scene(root, 360, 370);
                stage.setTitle("Main Menu");
                stage.setScene(scene);
                stage.show();
            }
            case "Customers" -> {
                Scene scene = new Scene(root, 1170, 600);
                stage.setTitle(sceneName);
                stage.setScene(scene);
                stage.show();
            }
            case "AddCustomer" -> {
                Scene scene = new Scene(root, 500, 500);
                stage.setTitle(sceneName);
                stage.setScene(scene);
                stage.show();
            }
            case "Appointments" -> {
                Scene scene = new Scene(root, 1150, 600);
                stage.setTitle(sceneName);
                stage.setScene(scene);
                stage.show();
            }
            case "AddAppointment" -> {
                Scene scene = new Scene(root, 850, 500);
                stage.setTitle(sceneName);
                stage.setScene(scene);
                stage.show();
            }
            case "Reports" -> {
                Scene scene = new Scene(root, 1200, 800);
                stage.setTitle(sceneName);
                stage.setScene(scene);
                stage.show();
            }
            default -> {
                Scene scene = new Scene(root, 1200, 600);
                stage.setTitle(sceneName);
                stage.setScene(scene);
                stage.show();
            }
        }

    }
}
