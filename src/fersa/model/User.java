package fersa.model;

public class User {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String CF;
    private String email;
    private boolean isLessor;

    public User(String firstName, String lastName, String username, String password, String CF, String email,
                boolean isLessor) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.CF = CF;
        this.email = email;
        this.isLessor = isLessor;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCF() {
        return CF;
    }

    public void setCF(String CF) {
        this.CF = CF;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLessor() {
        return isLessor;
    }

    public void setLessor(boolean lessor) {
        isLessor = lessor;
    }
}
