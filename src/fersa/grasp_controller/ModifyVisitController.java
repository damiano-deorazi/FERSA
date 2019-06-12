package fersa.grasp_controller;

import fersa.Mailer;
import fersa.bean.VisitBean;
import fersa.dao.DAOQueryUser;
import fersa.dao.DAOQueryVisit;


public class ModifyVisitController {
    private static ModifyVisitController modifyVisitController = null;

    private ModifyVisitController(){}

    public static ModifyVisitController getInstance(){
        if (modifyVisitController == null){
            modifyVisitController = new ModifyVisitController();
        }
        return modifyVisitController;
    }

    public int modifyVisit(VisitBean visitBean) {
        DAOQueryVisit daoQueryVisit = DAOQueryVisit.getInstance();
        int visits = daoQueryVisit.getVisitByIdAndTime(visitBean.getIdApartment(), visitBean.getModDate(),
                visitBean.getModTime());
        if (visits == 0) {
            if (daoQueryVisit.modifyVisit(visitBean.getUsernameRenter(), visitBean.getIdApartment(),
                    visitBean.getModDate(), visitBean.getModTime())){
                DAOQueryUser daoQueryUser = DAOQueryUser.getInstance();
                Mailer mailer = Mailer.getInstance();
                String emailRenter = daoQueryUser.getEmail(visitBean.getUsernameRenter());
                String emailLessor = daoQueryUser.getEmail(visitBean.getUsernameLessor());
                if (visitBean.isLessor()) {
                    mailer.sendModifyVisitEmail(emailLessor, emailRenter, visitBean.getUsernameLessor(),
                            visitBean.getIdApartment(), visitBean.getVisitDate(), visitBean.getVisitTime(),
                            visitBean.getModDate(), visitBean.getModTime(), true);
                }
                else{
                    mailer.sendModifyVisitEmail(emailRenter, emailLessor, visitBean.getUsernameRenter(),
                            visitBean.getIdApartment(), visitBean.getVisitDate(), visitBean.getVisitTime(),
                            visitBean.getModDate(), visitBean.getModTime(), false);
                }
                return 1;
            }
        }
        return -1;
    }
}
