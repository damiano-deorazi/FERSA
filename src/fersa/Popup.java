package fersa;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Popup {

    public Popup(){}

    public void showInfoPopup(String header, String content) {
        Alert popup = new Alert(Alert.AlertType.INFORMATION);
        popup.setTitle("Info");
        popup.setHeaderText(header);
        popup.setContentText(content);
        popup.showAndWait();
    }

    public boolean showConfirmPopup(String content) {
        Alert popup = new Alert(Alert.AlertType.CONFIRMATION, content);
        popup.setTitle("Warning");
        Optional<ButtonType> result = popup.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    public void showEmailErrorPopup() {
        Alert popup = new Alert(Alert.AlertType.ERROR);
        popup.setTitle("Error");
        popup.setHeaderText("Errore invio email");
        popup.setContentText("Cliccare OK per riprovare");
        popup.showAndWait();
    }
}
