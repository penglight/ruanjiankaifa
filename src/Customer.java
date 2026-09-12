import java.util.ArrayList;
import java.util.Scanner;
public class Customer extends User{
    String ID;
    String leve;
    String RegisterTime;
    double CostCount;
    String phonenumber;
    String MailBox;
    ShoppingCart cart;

    public void register(){

    }
    public  boolean login(String username,String  password,ShoppingSystem system ){
        ArrayList<Customer> customerList = system.getCustomerList();
        for(Customer temp : customerList){
            if(temp.getUsername().equals(username)){
                if(temp.getPassword().equals(password)){
                    System.out.println("Welcome "+username);
                    return true;
                }else {
                    System.out.println("Wrong password or username!");
                    return false;
                }
            }
        }
        System.out.println("Wrong password or username!");
         return false;
    }
    public   boolean changePassword(String newPassword){}
    public  void logout(){}
    public String getCustomerID() {
        return ID;
    }
    public void setCustomerID(String ID) {
        this.ID = ID;
    }
    public String getLeve() {
        return leve;
    }
    public void setLeve(String leve) {
        this.leve = leve;
    }
    public String getRegisterTime() {
        return RegisterTime;
    }
    public void setRegisterTime(String RegisterTime) {
        this.RegisterTime = RegisterTime;
    }
    public double getCostCount() {
        return CostCount;
    }
    public void setCostCount(double CostCount) {
        this.CostCount = CostCount;
    }
    public String getPhonenumber() {
        return phonenumber;
    }
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
    public String getMailBox() {
        return MailBox;
    }
    public void setMailBox(String MailBox) {
        this.MailBox = MailBox;
    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }




}
