package fersa.fx_controller;

import fersa.model.ApartmentLessorVisit;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class CustomListCellLessorFXController {
    @FXML
    VBox vbxItemList;
    @FXML
    Label lbID, lbUsernameRenter, lbAddress, lbCity, lbCountry, lbDate, lbTime;

    public CustomListCellLessorFXController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/custom_list_cell_lessor.fxml"));
        fxmlLoader.setController(this);
        try {
            vbxItemList = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setInfo(ApartmentLessorVisit apartment) {
        lbID.setText(String.valueOf(apartment.getId()));
        lbUsernameRenter.setText(apartment.getUsernameRenterVisit());
        lbAddress.setText(apartment.getAddress());
        lbCity.setText(apartment.getCity());
        lbCountry.setText(apartment.getCountry());
        lbDate.setText(String.valueOf(apartment.getDateVisit()));
        lbTime.setText(String.valueOf(apartment.getTimeVisit()));
    }

    public VBox getBox() {
        return vbxItemList;
    }
}
