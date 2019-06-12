package fersa.bean;


public class UserBean {
    private String username;
    private String password;
    private boolean isLessor;

    public UserBean(){}

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

    public boolean isLessor() {
        return isLessor;
    }

    public void setIsLessor(boolean isLessor) {
        this.isLessor = isLessor;
    }
}
