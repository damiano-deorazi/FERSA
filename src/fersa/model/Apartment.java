package fersa.model;

public class Apartment {
    private int id;
    private String country;
    private String city;
    private String address;
    private String usernameLessor;
    private String description;

    public Apartment(int id, String country, String city, String address, String usernameLessor, String description) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.address = address;
        this.usernameLessor = usernameLessor;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsernameLessor() {
        return usernameLessor;
    }

    public void setUsernameLessor(String usernameLessor) {
        this.usernameLessor = usernameLessor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
