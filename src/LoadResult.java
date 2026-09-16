import java.util.ArrayList;


public class LoadResult {
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<Product> products = new ArrayList<>();

    public boolean hasData() {
        return !customers.isEmpty() || !products.isEmpty();
    }
    public void addCustomer(Customer c) { customers.add(c); }
    public void addProduct(Product p) { products.add(p); }
    public ArrayList<Customer> getCustomers() { return customers; }
    public ArrayList<Product> getProducts() { return products; }
}

