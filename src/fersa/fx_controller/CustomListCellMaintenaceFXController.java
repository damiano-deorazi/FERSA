package fersa.fx_controller;

import fersa.model.MaintenanceRequest;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class CustomListCellMaintenaceFXController {
    @FXML
    VBox vbxItemList;
    @FXML
    Label lbRequestId, lbIdAppartment, lbUsernameRenter, lbDate;

    public CustomListCellMaintenaceFXController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/custom_list_cell_maintenance.fxml"));
        fxmlLoader.setController(this);
        try {
            vbxItemList = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e); //lasciarlo o no?
        }
    }

    public void setInfo(MaintenanceRequest request) {
        lbRequestId.setText(String.valueOf(request.getId()));
        lbIdAppartment.setText(String.valueOf(request.getIdApartment()));
        lbUsernameRenter.setText(request.getUsernameRenter());
        lbDate.setText(String.valueOf(request.getDateRequest()));
    }

    public Node getBox() {
        return vbxItemList;
    }
}
