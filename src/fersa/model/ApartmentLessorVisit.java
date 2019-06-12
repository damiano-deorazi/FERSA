package fersa.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class ApartmentLessorVisit extends ApartmentRenterVisit {
    private String usernameRenterVisit;

    public ApartmentLessorVisit(int id, String country, String city, String address, String usernameLessor,
                                String description, LocalDate dateVisit, LocalTime timeVisit,
                                String usernameRenterVisit) {
        super(id, country, city, address, usernameLessor, description, dateVisit, timeVisit);
        this.usernameRenterVisit = usernameRenterVisit;
    }

    public String getUsernameRenterVisit() {
        return usernameRenterVisit;
    }

    public void setUsernameRenterVisit(String usernameRenterVisit) {
        this.usernameRenterVisit = usernameRenterVisit;
    }

}
