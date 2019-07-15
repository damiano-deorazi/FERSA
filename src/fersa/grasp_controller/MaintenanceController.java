package fersa.grasp_controller;

import fersa.MailMaintenanceThread;
import fersa.Mailer;
import fersa.bean.MaintenanceBean;
import fersa.dao.DAOQueryMaintenanceRequest;
import fersa.dao.DAOQueryUser;
import fersa.model.MaintenanceRequest;

import java.util.ArrayList;

public class MaintenanceController {
    private static MaintenanceController maintenanceController = null;

    private MaintenanceController(){}

    public static MaintenanceController getInstance(){
        if (maintenanceController == null){
            maintenanceController = new MaintenanceController();
        }
        return maintenanceController;
    }

    public ArrayList<MaintenanceRequest> searchMaintenanceRequest(MaintenanceBean maintenanceBean) {
        DAOQueryMaintenanceRequest daoQueryMaintenanceRequest = DAOQueryMaintenanceRequest.getInstance();
        return  daoQueryMaintenanceRequest.searchMaintanceRequest(maintenanceBean.getUsernameLessor());
    }

    public boolean answerMaintenanceRequest(MaintenanceBean maintenanceBean) {
        DAOQueryMaintenanceRequest daoQueryMaintenanceRequest = DAOQueryMaintenanceRequest.getInstance();
        if(daoQueryMaintenanceRequest.acceptOrRejectMaintenanceRequest(maintenanceBean.getIdMaintenanceReq(),
                maintenanceBean.isAccepted())){
            DAOQueryUser daoQueryUser = DAOQueryUser.getInstance();
            //Mailer mailer = Mailer.getInstance();
            String emailLessor = daoQueryUser.getEmail(maintenanceBean.getUsernameLessor());
            String emailRenter = daoQueryUser.getEmail(maintenanceBean.getUsernameRenter());
            MailMaintenanceThread t = new MailMaintenanceThread(emailLessor, emailRenter,
                    maintenanceBean.getUsernameLessor(), maintenanceBean.getIdApartment(),
                    maintenanceBean.getRequestDate(), maintenanceBean.getRequestTime(), maintenanceBean.isAccepted());
            Thread thread = new Thread(t);
            thread.start();
            return  true;
        }
        return  false;
    }
}
