package fersa.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class MaintenanceRequest {
    private int id;
    private int idApartment;
    private String usernameRenter;
    private LocalDate dateRequest;
    private LocalTime timeRequest;
    private String description;
    private boolean isAccepted;

    public MaintenanceRequest(int id, int idApartment, String usernameRenter, LocalDate dateRequest, LocalTime timeRequest,
                              String description) {
        this.id = id;
        this.idApartment = idApartment;
        this.usernameRenter = usernameRenter;
        this.dateRequest = dateRequest;
        this.timeRequest = timeRequest;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdApartment() {
        return idApartment;
    }

    public void setIdApartment(int idApartment) {
        this.idApartment = idApartment;
    }

    public String getUsernameRenter() {
        return usernameRenter;
    }

    public void setUsernameRenter(String usernameRenter) {
        this.usernameRenter = usernameRenter;
    }

    public LocalDate getDateRequest() {
        return dateRequest;
    }

    public void setDateRequest(LocalDate dateRequest) {
        this.dateRequest = dateRequest;
    }

    public LocalTime getTimeRequest() {
        return timeRequest;
    }

    public void setTimeRequest(LocalTime timeRequest) {
        this.timeRequest = timeRequest;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }
}
