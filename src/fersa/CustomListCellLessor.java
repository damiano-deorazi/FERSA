package fersa;

import fersa.fx_controller.CustomListCellLessorFXController;
import fersa.model.ApartmentLessorVisit;
import javafx.scene.control.ListCell;

public class CustomListCellLessor extends ListCell<ApartmentLessorVisit> {
    @Override
    public void updateItem(ApartmentLessorVisit apartment, boolean empty){
        super.updateItem(apartment, empty);
        if (empty){
            setGraphic(null);
        }
        else {
            CustomListCellLessorFXController dataCustomListCell = new CustomListCellLessorFXController();
            dataCustomListCell.setInfo(apartment);
            setGraphic(dataCustomListCell.getBox());
        }
    }
}
