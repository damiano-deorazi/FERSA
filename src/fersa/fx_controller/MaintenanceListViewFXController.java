package fersa.fx_controller;

import fersa.bean.MaintenanceBean;
import fersa.grasp_controller.MaintenanceController;
import fersa.model.MaintenanceRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class MaintenanceListViewFXController {
    @FXML
    Button btnLogout, btnBack, btnAccept, btnReject;
    @FXML
    Label lbEmptyList, lbDClickInfo;
    @FXML
    ListView lvApartments;
    private MaintenanceBean maintenanceBean = new MaintenanceBean();
    private Preferences preferences;
    private Popup popup = new Popup();
    private MaintenanceController controller = MaintenanceController.getInstance();

    @FXML
    private void initialize(){
        preferences = Preferences.userRoot().node("/fersa/cache");
        String username = preferences.get("username", null);
        maintenanceBean.setUsernameLessor(username);
        ArrayList<MaintenanceRequest> maintenanceRequests = controller.searchMaintenanceRequest(maintenanceBean);
        ObservableList observableList = FXCollections.observableArrayList();
        if (maintenanceRequests.size() == 0){
            lvApartments.setVisible(false);
            lbDClickInfo.setVisible(false);
            btnAccept.setVisible(false);
            btnReject.setVisible(false);
        }
        else {
            lbEmptyList.setVisible(false);
            observableList.setAll(maintenanceRequests);
            lvApartments.setItems(observableList);
            lvApartments.setCellFactory(listView -> new CustomListCellMaintenance());
            lvApartments.setOnMouseClicked(click -> {
                if (click.getClickCount() == 2) {
                    try {
                        loadDetailsView();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void onBtnAcceptClicked() throws IOException {
        if (popup.showConfirmPopup("Vuoi davvero accettare la richiesta di manutenzione?")) {
            MaintenanceRequest maintenanceRequest = (MaintenanceRequest) lvApartments.getSelectionModel().
                    getSelectedItem();
            maintenanceBean.setIdMaintenanceReq(maintenanceRequest.getId());
            maintenanceBean.setUsernameRenter(maintenanceRequest.getUsernameRenter());
            maintenanceBean.setIdApartment(maintenanceRequest.getIdApartment());
            maintenanceBean.setRequestDate(maintenanceRequest.getDateRequest());
            maintenanceBean.setRequestTime(maintenanceRequest.getTimeRequest());
            maintenanceBean.setAccepted(true);
            if (controller.answerMaintenanceRequest(maintenanceBean)) {
                popup.showInfoPopup("Richiesta di manutenzione accettata!", "Un'email di conferma è " +
                        "stata inviata al locatario");
                Stage stage = (Stage) btnBack.getScene().getWindow();
                loadNewScreen(stage, "/maintenance_list_view.fxml");
            } else {
                popup.showInfoPopup("Errore!", "Qualcosa è andato storto nella richiesta");
            }
        }
    }

    public void onBtnRejectClicked() throws IOException {
        if (popup.showConfirmPopup("Vuoi davvero rifiutare la richiesta di manutenzione??")) {

            MaintenanceRequest maintenanceRequest = (MaintenanceRequest) lvApartments.getSelectionModel().
                    getSelectedItem();
            maintenanceBean.setIdMaintenanceReq(maintenanceRequest.getId());
            maintenanceBean.setUsernameRenter(maintenanceRequest.getUsernameRenter());
            maintenanceBean.setIdApartment(maintenanceRequest.getIdApartment());
            maintenanceBean.setRequestDate(maintenanceRequest.getDateRequest());
            maintenanceBean.setRequestTime(maintenanceRequest.getTimeRequest());
            maintenanceBean.setAccepted(false);
            if (controller.answerMaintenanceRequest(maintenanceBean)) {
                popup.showInfoPopup("Richiesta di manutenzione rifiutata!", "Un'email di conferma è " +
                        "stata inviata al locatario");
                Stage stage = (Stage) btnBack.getScene().getWindow();
                loadNewScreen(stage, "/maintenance_list_view.fxml");
            } else {
                popup.showInfoPopup("Errore!", "Qualcosa è andato storto nella richiesta");
            }
        }
    }

    public void onBtnBackClicked() throws IOException {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        loadNewScreen(stage, "/lessor_opt_view.fxml");
    }

    public void onBtnLogoutClicked() throws IOException, BackingStoreException {
        preferences.clear();
        Stage stage = (Stage) btnBack.getScene().getWindow();
        loadNewScreen(stage, "/login_view.fxml");
    }

    private void loadDetailsView() throws IOException {
        MaintenanceRequest request = (MaintenanceRequest) lvApartments.getSelectionModel().getSelectedItem();
        int idRequest = request.getId();
        String cfRenter = request.getUsernameRenter();
        LocalDate dateRequest = request.getDateRequest();
        LocalTime timeRequest = request.getTimeRequest();
        String description = request.getDescription();
        DetailsMaintenanceRequestFXController controller = new DetailsMaintenanceRequestFXController();
        controller.initData(idRequest, cfRenter, dateRequest, timeRequest, description);
        Stage stage = (Stage) btnBack.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/details_maintenance_request_view.fxml"));
        loader.setController(controller);
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void loadNewScreen(Stage stage, String fxmlFile) throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
