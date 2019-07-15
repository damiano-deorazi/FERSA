package fersa.fx_controller;

import fersa.fx_controller.CustomListCellRenterFXController;
import fersa.model.ApartmentRenterVisit;
import javafx.scene.control.ListCell;

public class CustomListCellRenter extends ListCell<ApartmentRenterVisit> {
    @Override
    public void updateItem(ApartmentRenterVisit apartmentRenterVisit, boolean empty){
        super.updateItem(apartmentRenterVisit, empty);
        if (empty){
             setGraphic(null);
        }
        else {
            CustomListCellRenterFXController dataCustomListCell = new CustomListCellRenterFXController();
            dataCustomListCell.setInfo(apartmentRenterVisit);
            setGraphic(dataCustomListCell.getBox());
        }
    }
}
