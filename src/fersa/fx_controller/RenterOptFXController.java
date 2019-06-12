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

public class RenterOptFXController {
    @FXML
    Button btnDelete, btnModify, btnLogout;
    @FXML
    Label lbWelcomeUser;
    private Preferences preferences;

    @FXML
    private void initialize(){
        preferences = Preferences.userRoot().node("/fersa/cache");
        String username = preferences.get("username", null);
        lbWelcomeUser.setText("Benvenuto " + username + "!");
    }

    public void onBtnDeleteClicked() throws IOException {
       setRenterViewController(0);
    }

    public void onBtnModifyClicked() throws IOException {
        setRenterViewController(1);
    }

    public void onBtnLogoutClicked() throws BackingStoreException, IOException {
        preferences.clear();
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        AnchorPane root = FXMLLoader.load(getClass().getResource("/login_view.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void setRenterViewController(int i) throws IOException {
        Stage stage = (Stage) btnModify.getScene().getWindow();
        RenterVisitsListViewFXController controller = new RenterVisitsListViewFXController();
        controller.initData(i);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/renter_visits_list_view.fxml"));
        loader.setController(controller);
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
