package fersa.fx_controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class LessorOptFXController{
    @FXML
    Button btnManageVisits, btnManageRequests, btnLogout;
    @FXML
    Label lbWelcomeUser;
    private Preferences preferences;

    @FXML
    private void initialize(){
        preferences = Preferences.userRoot().node("/fersa/cache");
        String username = preferences.get("username", null);
        lbWelcomeUser.setText("Benvenuto " + username + "!");
    }

    public void onBtnManageVisitsClicked() throws IOException {
        loadNewScreen("/lessor_visits_list_view.fxml");
    }

    public void onBtnManageRequestsClicked() throws IOException {
        loadNewScreen("/maintenance_list_view.fxml");
    }

    public void onBtnLogoutClicked() throws BackingStoreException, IOException {
        preferences.clear();
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        AnchorPane root = FXMLLoader.load(getClass().getResource("/login_view.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void loadNewScreen(String fxmlFile) throws IOException {
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        AnchorPane root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
