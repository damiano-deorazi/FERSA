package fersa.fx_controller;

import fersa.fx_controller.CustomListCellMaintenaceFXController;
import fersa.model.MaintenanceRequest;
import javafx.scene.control.ListCell;

public class CustomListCellMaintenance extends ListCell<MaintenanceRequest> {
    @Override
    public void updateItem(MaintenanceRequest request, boolean empty){
        super.updateItem(request, empty);
        if (empty){
            setGraphic(null);
        }
        else {
            CustomListCellMaintenaceFXController dataCustomListCell = new CustomListCellMaintenaceFXController();
            dataCustomListCell.setInfo(request);
            setGraphic(dataCustomListCell.getBox());
        }
    }
}
