package fersa.bean;

import java.time.LocalDate;
import java.time.LocalTime;

public class MaintenanceBean {
    private String usernameLessor;
    private String usernameRenter;
    private int idMaintenanceReq;
    private int idApartment;
    private LocalDate requestDate;
    private LocalTime requestTime;
    private boolean isAccepted;

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

    public int getIdMaintenanceReq() {
        return idMaintenanceReq;
    }

    public void setIdMaintenanceReq(int idMaintenanceReq) {
        this.idMaintenanceReq = idMaintenanceReq;
    }

    public int getIdApartment() {
        return idApartment;
    }

    public void setIdApartment(int idApartment) {
        this.idApartment = idApartment;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public LocalTime getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(LocalTime requestTime) {
        this.requestTime = requestTime;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }
}
