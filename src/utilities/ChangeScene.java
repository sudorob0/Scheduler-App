package utilities;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangeScene {
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

    public void stringToSceneChange(ActionEvent actionEvent, String sceneName) throws IOException {
        String path = "/views/" + sceneName + ".fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        if (sceneName == "Login"){
            Scene scene = new Scene(root, 580, 390);
            stage.setTitle(sceneName);
            stage.setScene(scene);
            stage.show();
        }
        else if (sceneName == "MainMenu"){
            Scene scene = new Scene(root, 360, 370);
            stage.setTitle("Main Menu");
            stage.setScene(scene);
            stage.show();
        }
        else if (sceneName == "Customers"){
            Scene scene = new Scene(root, 1060, 600);
            stage.setTitle(sceneName);
            stage.setScene(scene);
            stage.show();
        }
        else if (sceneName == "AddCustomer"){
            Scene scene = new Scene(root, 500, 500);
            stage.setTitle(sceneName);
            stage.setScene(scene);
            stage.show();
        }
        else if (sceneName == "Appointments"){
            Scene scene = new Scene(root, 1150, 600);
            stage.setTitle(sceneName);
            stage.setScene(scene);
            stage.show();
        }
        else if (sceneName == "AddAppointment"){
            Scene scene = new Scene(root, 850, 500);
            stage.setTitle(sceneName);
            stage.setScene(scene);
            stage.show();
        }

    }
}
