package fersa.fx_controller;

import fersa.bean.UserBean;
import fersa.grasp_controller.UserController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.prefs.Preferences;

public class LoginViewFXController {
    @FXML
    TextField tfUsername;
    @FXML
    PasswordField pfPassword;
    @FXML
    Button btnLogin;
    @FXML
    Label lbCheckLogin;
    private UserController userController = UserController.getInstance();

    public void onBtnLoginClicked() throws IOException {
        String username = tfUsername.getText();
        String password = pfPassword.getText();

        if (!checkCredentialFormat(username, password)){
            lbCheckLogin.setText("Invalid username or password");
        }
        else {
            UserBean userBean = new UserBean();
            userBean.setUsername(username);
            userBean.setPassword(password);

            if (userController.validateLogin(userBean)){
                Preferences preferences = Preferences.userRoot().node("fersa/cache");
                preferences.put("username", username); /*forse da rimuovere e chiedere direttamenteal db*/
                //preferences.put("cf", userBean.getUser().getCF());
                if (userBean.isLessor()) {
                    preferences.put("isLessor", "1"); /*idem come sopra*/
                    loadNewScreen("/lessor_opt_view.fxml");
                }
                else {
                    preferences.put("isLessor", "0");
                    loadNewScreen("/renter_opt_view.fxml");
                }
            }
            else {
                lbCheckLogin.setText("Invalid username or password");
            }
        }
    }

    private void loadNewScreen(String fxmlFile) throws IOException {
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        AnchorPane root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private boolean checkCredentialFormat(String username, String password) {
        return username.length() > 5 && password.length() > 5;
    }
}
