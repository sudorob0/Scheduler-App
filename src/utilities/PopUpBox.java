package utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Class to create popup boxes
 */
public class PopUpBox {

    /**
     * This method will display an information message with a provided string
     * @param message string that will be displayed in error message
     */
    public static void infoBox(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * This method will display an error message with a provided string
     * @param message string that will be displayed in error message
     */
    public static void errorBox(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * This message will create a pop up box that will give the user the ablity to confirm their selection
     * @param message custom message will be displayed in popup window
     * @return boolean TRUE if user confirms selection
     */
    public static Boolean optionBox(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}
