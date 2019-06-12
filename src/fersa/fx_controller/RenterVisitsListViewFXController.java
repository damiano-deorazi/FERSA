package fersa.fx_controller;

import fersa.CustomListCellRenter;
import fersa.Popup;
import fersa.bean.VisitBean;
import fersa.grasp_controller.DeleteVisitController;
import fersa.model.ApartmentRenterVisit;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class RenterVisitsListViewFXController implements Initializable {
    @FXML
    ListView lvApartments;
    @FXML
    Button btnLogout, btnAction, btnBack;
    @FXML
    Label lbEmptyList;

    private Preferences preferences;
    private int infoBit;
    private VisitBean visitBean;
    private ArrayList<ApartmentRenterVisit> apartmentRenterVisits;
    private DeleteVisitController controller = DeleteVisitController.getInstance();

    public RenterVisitsListViewFXController() {}

    /**utile per settare il bottone su cancella o modifica**/
    public void initData(int i){
        infoBit = i;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        preferences = Preferences.userRoot().node("/fersa/cache");
        String username = preferences.get("username", null);
        setLogoutAction();
        setButtonActions();
        visitBean = new VisitBean();
        visitBean.setUsernameRenter(username);
        visitBean.setTodayDate(LocalDate.now());
        visitBean.setTodayTime(LocalTime.now());
        visitBean.setLessor(false);
        apartmentRenterVisits = controller.searchApartmentsByRenter(visitBean);
        if (apartmentRenterVisits == null){
            btnAction.setVisible(false);
            lvApartments.setVisible(false);
        }
        else {
            lbEmptyList.setVisible(false);
            loadListView();
        }
    }

    /**modifica la visita selezionata dal renter**/
    private void onBtnModifyClicked() throws IOException {
        ApartmentRenterVisit apartmentRenterVisit = (ApartmentRenterVisit) lvApartments.getSelectionModel().
                getSelectedItem();
        int idApartment = apartmentRenterVisit.getId();
        String usernameLessor = apartmentRenterVisit.getUsernameLessor();
        LocalDate date = apartmentRenterVisit.getDateVisit();
        LocalTime time = apartmentRenterVisit.getTimeVisit();
        Stage stage = (Stage) btnAction.getScene().getWindow();
        RenterModifyViewFXController controller = new RenterModifyViewFXController();
        controller.initData(usernameLessor, idApartment, date, time);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/renter_modify_view.fxml"));
        loader.setController(controller);
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**cancella la visita selezionata dal renter**/
    private void onBtnDeleteClicked() {
        Popup popup = new Popup();
        if (popup.showConfirmPopup("Vuoi davvero cancellare la visita?")) {
            ApartmentRenterVisit apartmentRenterVisit = (ApartmentRenterVisit) lvApartments.getSelectionModel().
                    getSelectedItem();
            int apartmentId = apartmentRenterVisit.getId();
            visitBean.setIdApartment(apartmentId);

            if (controller.deleteVisit(visitBean)) {
                popup.showInfoPopup("Visita cancellata!", "Un'mail di conferma è stata inviata " +
                        "al locatore");
                Iterator<ApartmentRenterVisit> apartments = apartmentRenterVisits.iterator();
                while (apartments.hasNext()) {
                    ApartmentRenterVisit apt = apartments.next();
                    if (apt.getId() == apartmentId) {
                        apartments.remove();
                        break;
                    }
                }
                if (apartmentRenterVisits.size() != 0) {
                    loadListView();
                } else {
                    lvApartments.setVisible(false);
                    btnAction.setVisible(false);
                    lbEmptyList.setVisible(true);
                }
            } else {
                popup.showInfoPopup("Errore!", "Qualcosa è andato storto nella richiesta");
            }
        }
    }

    private void onBtnBackClicked() throws IOException {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        loadNewScreen(stage, "/renter_opt_view.fxml");
    }

    private void onBtnLogoutClicked() throws BackingStoreException, IOException {
        preferences.clear();
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        loadNewScreen(stage, "/login_view.fxml");
    }

    private void setLogoutAction(){
        btnLogout.setOnAction(e -> {
            try {
                onBtnLogoutClicked();
            } catch (BackingStoreException | IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    private void setButtonActions(){
        if (infoBit == 0){
            btnAction.setText("Cancella");
            btnAction.setOnAction(e -> onBtnDeleteClicked());
        }
        else {
            btnAction.setText("Modifica");
            btnAction.setOnAction(e -> {
                try {
                    onBtnModifyClicked();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });
        }

        btnBack.setOnAction(e -> {
            try {
                onBtnBackClicked();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    private void loadListView(){
        ObservableList observableList = FXCollections.observableArrayList();
        observableList.setAll(apartmentRenterVisits);
        lvApartments.setItems(observableList);
        lvApartments.setCellFactory(listView -> new CustomListCellRenter());
    }

    private void loadNewScreen(Stage stage, String fxmlFile) throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
