package fersa.bean;

import java.time.LocalDate;
import java.time.LocalTime;

public class VisitBean {
    private String usernameLessor;
    private String usernameRenter;
    private int idApartment;
    private int idMaintenanceRequest;
    private LocalDate todayDate, visitDate, modDate;
    private LocalTime todayTime, visitTime, modTime;
    private boolean isLessor;

    public VisitBean(){}

    public String getUsernameLessor() {
        return usernameLessor;
    }

    public void setUsernameLessor(String usernameLessor) {
        this.usernameLessor = usernameLessor;
    }

    public String getUsernameRenter() {
        return usernameRenter;
    }

    public void setUsernameRenter(String usernameRenter) {
        this.usernameRenter = usernameRenter;
    }

    public LocalDate getTodayDate() {
        return todayDate;
    }

    public int getIdApartment() {
        return idApartment;
    }

    public void setIdApartment(int idApartment) {
        this.idApartment = idApartment;
    }

    public int getIdMaintenanceRequest() {
        return idMaintenanceRequest;
    }

    public void setIdMaintenanceRequest(int idMaintenanceRequest) {
        this.idMaintenanceRequest = idMaintenanceRequest;
    }

    public void setTodayDate(LocalDate todayDate) {
        this.todayDate = todayDate;
    }

    public LocalTime getTodayTime() {
        return todayTime;
    }

    public void setTodayTime(LocalTime todayTime) {
        this.todayTime = todayTime;
    }

    public LocalDate getModDate() {
        return modDate;
    }

    public void setModDate(LocalDate modDate) {
        this.modDate = modDate;
    }

    public LocalTime getModTime() {
        return modTime;
    }

    public void setModTime(LocalTime modTime) {
        this.modTime = modTime;
    }

    public boolean isLessor() {
        return isLessor;
    }

    public void setLessor(boolean lessor) {
        isLessor = lessor;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public LocalTime getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(LocalTime visitTime) {
        this.visitTime = visitTime;
    }
}
