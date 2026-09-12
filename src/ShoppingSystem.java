import java.util.ArrayList;

public class ShoppingSystem {
    // 属性：顾客列表 + 商品列表
    private ArrayList<Customer> customerList;
    private ArrayList<Product> productsList;

    // 构造方法：初始化两个列表
    public ShoppingSystem() {
        customerList = new ArrayList<>();
        productsList = new ArrayList<>();
    }

    // 获取顾客列表
    public ArrayList<Customer> getCustomerList() {
        return customerList;
    }

    // 获取商品列表
    public ArrayList<Product> getProductList() {
        return productsList;
    }

    // 添加顾客
    public void addCustomer(Customer c) {
        customerList.add(c);
    }

    // 添加商品
    public void addProduct(Product p) {
        productsList.add(p);
    }

    public static void main(String[] args) {

        ShoppingSystem system = new ShoppingSystem();




    }
}
