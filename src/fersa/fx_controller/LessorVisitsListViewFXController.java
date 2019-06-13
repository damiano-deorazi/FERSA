package fersa.fx_controller;

import fersa.*;
import fersa.bean.VisitBean;
import fersa.bean.UserBean;
import fersa.grasp_controller.DeleteVisitController;
import fersa.model.ApartmentLessorVisit;
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

public class LessorVisitsListViewFXController implements Initializable {
    @FXML
    ListView lvApartments;
    @FXML
    Button btnLogout, btnModify, btnDelete, btnBack;
    @FXML
    Label lbEmptyList;

    private Preferences preferences;
    private VisitBean visitBean;
    private ArrayList<ApartmentLessorVisit> lessorApartments;
    private DeleteVisitController controller = DeleteVisitController.getInstance();

    public LessorVisitsListViewFXController() {}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        preferences = Preferences.userRoot().node("/fersa/cache");
        String username = preferences.get("username", null);
        visitBean = new VisitBean();
        visitBean.setUsernameLessor(username);
        visitBean.setTodayDate(LocalDate.now());
        visitBean.setTodayTime(LocalTime.now());
        visitBean.setLessor(true);
        lessorApartments = controller.searchApartmentsByLessor(visitBean);
        if (lessorApartments == null){
            btnDelete.setVisible(false);
            btnModify.setVisible(false);
            lvApartments.setVisible(false);
        }
        else {
            lbEmptyList.setVisible(false);
            loadListView();
        }
    }

    /**modifica la visita selezionata dal lessor**/
    public void onBtnModifyClicked() throws IOException {
        ApartmentLessorVisit apartment = (ApartmentLessorVisit) lvApartments.getSelectionModel().getSelectedItem();
        int id = apartment.getId();
        LocalDate date = apartment.getDateVisit();
        LocalTime time = apartment.getTimeVisit();
        String usernameRenter = apartment.getUsernameRenterVisit();
        String usernameLessor = visitBean.getUsernameLessor();
        Stage stage = (Stage) btnDelete.getScene().getWindow();
        LessorModifyViewFXController controller = new LessorModifyViewFXController();
        controller.initData(usernameLessor, id, date, time, usernameRenter);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/lessor_modify_view.fxml"));
        loader.setController(controller);
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**cancella la visita selezionata dal lessor**/
    public void onBtnDeleteClicked(){
        Popup popup = new Popup();
        if (popup.showConfirmPopup("Vuoi davvero cancellare la visita?")) {

            ApartmentLessorVisit apartment = (ApartmentLessorVisit) lvApartments.getSelectionModel().getSelectedItem();
            int apartmentId = apartment.getId();
            String usernameRenter = apartment.getUsernameRenterVisit();
            visitBean.setIdApartment(apartmentId);
            visitBean.setVisitDate(apartment.getDateVisit());
            visitBean.setVisitTime(apartment.getTimeVisit());
            visitBean.setUsernameRenter(usernameRenter);

            if (controller.deleteVisit(visitBean)) {
                popup.showInfoPopup("Visita cancellata!", "Un'email di conferma è stata inviata al " +
                        "locatario");
                Iterator<ApartmentLessorVisit> apartments = lessorApartments.iterator();
                while (apartments.hasNext()) {
                    ApartmentLessorVisit apt = apartments.next();
                    if (apt.getId() == apartmentId && apt.getUsernameRenterVisit().equals(usernameRenter)) {
                        apartments.remove();
                        break;
                    }
                }
                if (lessorApartments.size() != 0) {
                    loadListView();
                } else {
                    lvApartments.setVisible(false);
                    btnDelete.setVisible(false);
                    btnModify.setVisible(false);
                    lbEmptyList.setVisible(true);
                }
            } else {
                popup.showInfoPopup("Errore!", "Qualcosa è andato storto nella richiesta");
            }
        }
    }

    public void onBtnBackClicked() throws IOException {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        loadNewScreen(stage, "/lessor_opt_view.fxml");
    }

    public void onBtnLogoutClicked() throws BackingStoreException, IOException {
        preferences.clear();
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        loadNewScreen(stage, "/login_view.fxml");
    }


    private void loadNewScreen(Stage stage, String fxmlFile) throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void loadListView(){
        ObservableList observableList = FXCollections.observableArrayList();
        observableList.setAll(lessorApartments);
        lvApartments.setItems(observableList);
        lvApartments.setCellFactory(listView -> new CustomListCellLessor());
    }
}
