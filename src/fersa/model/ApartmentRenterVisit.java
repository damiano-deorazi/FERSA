package fersa.model;


import java.time.LocalDate;
import java.time.LocalTime;

public class ApartmentRenterVisit extends Apartment {
    private LocalDate dateVisit;
    private LocalTime timeVisit;

    public ApartmentRenterVisit(int id, String country, String city, String address, String usernameLessor,
                                String description, LocalDate dateVisit, LocalTime timeVisit) {
        super(id, country, city, address, usernameLessor, description);
        this.dateVisit = dateVisit;
        this.timeVisit = timeVisit;
    }

    public LocalDate getDateVisit() {
        return dateVisit;
    }

    public void setDateVisit(LocalDate dateVisit) {
        this.dateVisit = dateVisit;
    }

    public LocalTime getTimeVisit() {
        return timeVisit;
    }

    public void setTimeVisit(LocalTime timeVisit) {
        this.timeVisit = timeVisit;
    }
}
