package fersa.fx_controller;

import fersa.model.ApartmentRenterVisit;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class CustomListCellRenterFXController {
    @FXML
    VBox vbxItemList;
    @FXML
    Label lbID, lbUsernameLessor, lbAddress, lbCity, lbCountry, lbDate, lbTime;

    public CustomListCellRenterFXController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/custom_list_cell_renter.fxml"));
        fxmlLoader.setController(this);
        try {
            vbxItemList = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setInfo(ApartmentRenterVisit apartmentRenterVisit) {
        lbID.setText(String.valueOf(apartmentRenterVisit.getId()));
        lbUsernameLessor.setText(apartmentRenterVisit.getUsernameLessor());
        lbAddress.setText(apartmentRenterVisit.getAddress());
        lbCity.setText(apartmentRenterVisit.getCity());
        lbCountry.setText(apartmentRenterVisit.getCountry());
        lbDate.setText(String.valueOf(apartmentRenterVisit.getDateVisit()));
        lbTime.setText(String.valueOf(apartmentRenterVisit.getTimeVisit()));
    }

    public VBox getBox() {
        return vbxItemList;
    }
}
