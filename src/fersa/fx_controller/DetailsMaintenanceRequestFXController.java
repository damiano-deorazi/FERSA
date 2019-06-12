package fersa.fx_controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class DetailsMaintenanceRequestFXController implements Initializable {
    @FXML
    Label lbDate, lbTime, lbIdRequest, lbCFRenter, lbDescription;
    @FXML
    Button btnBack;
    private int idRequest;
    private String cfRenter, description;
    private LocalTime timeRequest;
    private LocalDate dateRequest;

    public void initData(int idRequest, String cfRenter, LocalDate dateRequest, LocalTime timeRequest,
                         String description) {
        this.idRequest = idRequest;
        this.cfRenter = cfRenter;
        this.description = description;
        this.timeRequest = timeRequest;
        this.dateRequest = dateRequest;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbIdRequest.setText(String.valueOf(idRequest));
        lbCFRenter.setText(cfRenter);
        lbDate.setText(String.valueOf(dateRequest));
        lbTime.setText(String.valueOf(timeRequest));
        lbDescription.setText(description);
        btnBack.setOnAction(e -> {
            try {
                onBtnBackClicked();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    private void onBtnBackClicked() throws IOException {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        AnchorPane root = FXMLLoader.load(getClass().getResource("/maintenance_list_view.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
