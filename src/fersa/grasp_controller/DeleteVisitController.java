package fersa.grasp_controller;

import fersa.MailDeleteVisitThread;
import fersa.Mailer;
import fersa.bean.VisitBean;
import fersa.dao.DAOQueryApartment;
import fersa.dao.DAOQueryUser;
import fersa.dao.DAOQueryVisit;
import fersa.model.ApartmentLessorVisit;
import fersa.model.ApartmentRenterVisit;

import java.util.ArrayList;

public class DeleteVisitController {
    private static DeleteVisitController deleteVisitController = null;

    private DeleteVisitController(){}

    public static DeleteVisitController getInstance(){
        if (deleteVisitController == null){
            deleteVisitController = new DeleteVisitController();
        }
        return deleteVisitController;
    }

    /*decidere se riconvertire in bean la lista delle visite*/
    public ArrayList<ApartmentRenterVisit> searchApartmentsByRenter(VisitBean visitBean) {
        ArrayList<ApartmentRenterVisit> apartmentRenterVisits;
        DAOQueryApartment daoQueryApartment = DAOQueryApartment.getInstance();
        apartmentRenterVisits = daoQueryApartment.getApartmentsByRenter(visitBean.getUsernameRenter(),
                visitBean.getTodayDate(), visitBean.getTodayTime());
        if (apartmentRenterVisits.size() == 0) return null;
        apartmentRenterVisits.removeIf(apartment -> apartment.getDateVisit().isEqual(visitBean.getTodayDate()) &&
                apartment.getTimeVisit().isBefore(visitBean.getTodayTime()));
        return apartmentRenterVisits;
    }

    public ArrayList<ApartmentLessorVisit> searchApartmentsByLessor(VisitBean visitBean) {
        ArrayList<ApartmentLessorVisit> apartmentLessorVisits;
        DAOQueryApartment daoQueryApartment = DAOQueryApartment.getInstance();
        apartmentLessorVisits = daoQueryApartment.getApartmentsByLessor(visitBean.getUsernameLessor(),
                visitBean.getTodayDate(), visitBean.getTodayTime());
        if (apartmentLessorVisits.size() == 0) return null;
        apartmentLessorVisits.removeIf(apartment -> apartment.getDateVisit().isEqual(visitBean.getTodayDate()) &&
                apartment.getTimeVisit().isBefore(visitBean.getTodayTime()));
        return apartmentLessorVisits;
    }

    public boolean deleteVisit(VisitBean visitBean) {
        DAOQueryVisit daoQueryVisit = DAOQueryVisit.getInstance();
        if (daoQueryVisit.deleteVisit(visitBean.getUsernameRenter() , visitBean.getIdApartment())){
            DAOQueryUser daoQueryUser = DAOQueryUser.getInstance();
            //Mailer mailer = Mailer.getInstance();
            String emailRenter = daoQueryUser.getEmail(visitBean.getUsernameRenter());
            String emailLessor = daoQueryUser.getEmail(visitBean.getUsernameLessor());
            if (visitBean.isLessor()) {
                MailDeleteVisitThread t = new MailDeleteVisitThread(emailLessor, emailRenter,
                        visitBean.getUsernameLessor(), visitBean.getIdApartment(), visitBean.getVisitDate(),
                        visitBean.getVisitTime(), true);
                Thread thread = new Thread(t);
                thread.start();
            } else {
                MailDeleteVisitThread t = new MailDeleteVisitThread(emailRenter, emailLessor,
                        visitBean.getUsernameRenter(), visitBean.getIdApartment(), visitBean.getVisitDate(),
                        visitBean.getVisitTime(), false);
                Thread thread = new Thread(t);
                thread.start();
            }
            return true;
        }
        return false;
    }
}
