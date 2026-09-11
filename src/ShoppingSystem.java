import java.util.ArrayList;
public class ShoppingSystem{
    private ArrayList<Customer> customerList ;
    public ShoppingSystem() {
        customerList = new ArrayList<>();
    }
    public ArrayList<Customer> getCustomerList() {
        return customerList;
    }
    public void addCustomer(Customer c) {
        customerList.add(c);
    }
        public static void main(String[]args){
        Administrator admin = new Administrator();
        ShoppingSystem system = new ShoppingSystem();
        }}
