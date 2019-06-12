package fersa.fx_controller;

import fersa.Popup;
import fersa.bean.VisitBean;
import fersa.grasp_controller.ModifyVisitController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class LessorModifyViewFXController extends RenterModifyViewFXController {
    @FXML
    Label lbUsernameRenter;
    private String usernameRenter;
    private ModifyVisitController controller = ModifyVisitController.getInstance();

    public void initData(String usernameLessor, int id, LocalDate date, LocalTime time, String usernameRenter) {
        super.usernameLessor = usernameLessor;
        super.idApartment = id;
        super.dateVisit = date;
        super.timeVisit = time;
        this.usernameRenter = usernameRenter;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lbId.setText(String.valueOf(idApartment));
        lbTimeVisit.setText(dateVisit + " - " + timeVisit);
        ObservableList<String> options = FXCollections.observableArrayList(super.timetable);
        cbTime.setItems(options);
        lbUsernameRenter.setText(usernameRenter);
        setButtonActions();
    }

    @Override
    public void onBtnConfirmClicked() throws IOException{
        Popup popup = new Popup();
        if (popup.showConfirmPopup("Vuoi davvero cancellare la visita?")) {
            LocalDate modDate = dpDate.getValue();
            String time = cbTime.getSelectionModel().getSelectedItem().toString();
            LocalTime modTime = LocalTime.parse(time);

            if (checkTimeData(modDate, modTime)) {
                popup.showInfoPopup("Dati inseriti errati!", "Controlla la correttezza di data e " +
                        "ora inseriti");
            } else {
                /*ciò vale per il renter e non il lessor perche il cf del lessor non è utile per trovare le visite*/
                int m = modifyVisit(modDate, modTime, usernameRenter, true, controller);

                if (m == 1) {
                    popup.showInfoPopup("Modifica confermata!", "Un'email di conferma è stata " +
                            "inviata al locatorario");
                    loadPreviousScreen();
                } else if (m == 0) {
                    popup.showInfoPopup("Errore nella modifica della visita!", null);
                } else if (m == -1) {
                    popup.showInfoPopup("Data selezionata non disponibile!", "Esiste un'altra " +
                            "prenotazione per la data selezionata");
                }
            }
        }
    }

    public void loadPreviousScreen() throws IOException {
        Stage stage = (Stage) btnConfirm.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/lessor_visits_list_view.fxml"));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
