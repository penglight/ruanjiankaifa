import java.util.ArrayList;


public class LoadResult {
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<Product> products = new ArrayList<>();
    private Administrator admin;

    public void setAdmin(Administrator admin) { this.admin = admin; }
    public Administrator getAdmin() { return admin; }
    public boolean hasData (){
        return !customers.isEmpty() || !products.isEmpty();
    }
    public void addCustomer(Customer c) { customers.add(c); }
    public void addProduct(Product p) { products.add(p); }
    public ArrayList<Customer> getCustomers() { return customers; }
    public ArrayList<Product> getProducts() { return products; }

}

