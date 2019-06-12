package fersa.fx_controller;

import fersa.Popup;
import fersa.bean.VisitBean;
import fersa.grasp_controller.ModifyVisitController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class RenterModifyViewFXController implements Initializable {
    @FXML
    Label lbId, lbTimeVisit;
    @FXML
    Button btnConfirm, btnBack;
    @FXML
    DatePicker dpDate;
    @FXML
    ComboBox cbTime;
    int idApartment;
    protected String usernameLessor;
    protected LocalDate dateVisit;
    protected LocalTime timeVisit;
    private LocalDate today = LocalDate.now();
    private LocalTime now = LocalTime.now();
    private ModifyVisitController controller = ModifyVisitController.getInstance();
    protected String[] timetable = {"08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00",
            "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:30", "17:00", "17:30", "18:00", "18:30",
            "19:00"};

    public void initData(String usernameLessor, int id, LocalDate date, LocalTime time) {
        this.usernameLessor = usernameLessor;
        idApartment = id;
        dateVisit = date;
        timeVisit = time;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        lbId.setText(String.valueOf(idApartment));
        lbTimeVisit.setText(dateVisit + " - " + timeVisit);
        ObservableList<String> options = FXCollections.observableArrayList(timetable);
        cbTime.setItems(options);
        setButtonActions();
    }

    protected void setButtonActions() {
        btnBack.setOnAction(e -> {
            try {
                onBtnBackClicked();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        btnConfirm.setOnAction(e -> {
            try {
                onBtnConfirmClicked();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    public void onBtnConfirmClicked() throws IOException {
        Popup popup = new Popup();
        if (popup.showConfirmPopup("Vuoi davvero modificare la visita?")) {
            LocalDate modDate = dpDate.getValue();
            String time = cbTime.getSelectionModel().getSelectedItem().toString();
            LocalTime modTime = LocalTime.parse(time);

            if (checkTimeData(modDate, modTime)) {
                popup.showInfoPopup("Dati inseriti errati!", "Controlla la correttezza di data e ora " +
                        "inseriti");
            } else {
                /*ciò vale per il renter e non il lessor perche l'username del lessor non è utile per trovare le visite*/
                Preferences preferences = Preferences.userRoot().node("fersa/cache");
                String usernameRenter = preferences.get("username", null);
                int m = modifyVisit(modDate, modTime, usernameRenter, false, controller);

                if (m == 1) {
                    /*TODO da ricontrollare il riuso del codice e il metodo dello UserBean*/
                    popup.showInfoPopup("Modifica confermata!", "Un'email di conferma è stata " +
                            "inviata al locatore");
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

    protected int modifyVisit(LocalDate modDate, LocalTime modTime, String usernameRenter, boolean isLessor,
                              ModifyVisitController controller) {
        VisitBean visitBean = new VisitBean();
        visitBean.setIdApartment(idApartment);
        visitBean.setUsernameRenter(usernameRenter);
        visitBean.setUsernameLessor(usernameLessor);
        visitBean.setVisitDate(dateVisit);
        visitBean.setVisitTime(timeVisit);
        visitBean.setModDate(modDate);
        visitBean.setModTime(modTime);
        visitBean.setLessor(isLessor);
        return controller.modifyVisit(visitBean);
    }

    protected boolean checkTimeData(LocalDate modDate, LocalTime modTime) {
        if (modDate.isBefore(today)) return true;
        return modDate.isEqual(today) && modTime.isBefore(now);
    }

    public void onBtnBackClicked() throws IOException {
        loadPreviousScreen();
    }

    public void loadPreviousScreen() throws IOException {
        Stage stage = (Stage) btnConfirm.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/renter_visits_list_view.fxml"));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
