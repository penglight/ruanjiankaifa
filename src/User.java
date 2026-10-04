import java.io.Serializable;

public abstract class User implements Serializable {
    protected String username;
    protected String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public abstract boolean login(String username, String password);
    public abstract boolean changePassword(String newPassword);
    public abstract void logout();
}
