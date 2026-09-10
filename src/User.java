public abstract class User {
    String username;
    String password;
    public abstract boolean login(String username,String  password );
    public  abstract boolean changePassword(String newPassword);
    public abstract void logout();


}
