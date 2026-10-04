import java.util.ArrayList;
import java.io.Serializable;

public class Administrator extends User implements Serializable{

    private boolean firstLogin = true;

    public Administrator() {
        super("admin", "ynuinfo#777");
    }

    @Override
    public boolean login(String name, String pwd) {
        if (getUsername().equals(name) && getPassword().equals(pwd)) {
            System.out.println("You are successfully logged in");
            return true;
        }
        System.out.println("Invalid username or password");
        return false;
    }

    @Override
    public boolean changePassword(String newPassword) {
        if (newPassword == null || newPassword.isEmpty()) {
            System.out.println("Password cannot be empty!");
            return false;
        }
        if (newPassword.equals(getPassword())) {
            System.out.println("New password can not be the same as the old password");
            return false;
        }
        setPassword(newPassword);
        System.out.println("Password changed successfully");
        return true;
    }

    @Override
    public void logout() {
        System.out.println("Successfully logged out");
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }


    public boolean forceChangeCredentials(String newUsername, String newPassword) {
        if (newPassword.equals(getPassword())) {
            System.out.println("新密码不能与原密码一致，请重新输入！");
            return false;   // 不修改，让调用方重新输入
        }
        setUsername(newUsername);
        setPassword(newPassword);
        firstLogin = false;
        System.out.println("Initial credentials changed successfully!");
        return true;
    }


    // ==================== 顾客管理 ====================

    public void addCustomer(ShoppingSystem system, Customer customer) {
        system.getCustomerList().add(customer);
        System.out.println("Customer added successfully");
    }

    public void ReCustomerPassword(ShoppingSystem system, String targetId, String newPwd) {
        for (Customer temp : system.getCustomerList()) {
            if (temp.getCustomerID().equals(targetId)) {
                temp.setPassword(newPwd);
                System.out.println("Successfully changed password");
                return;
            }
        }
        System.out.println("Customer not found");
    }

    public void deleteCustomer(ShoppingSystem system, String customerId) {
        ArrayList<Customer> list = system.getCustomerList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getCustomerID().equals(customerId)) {
                list.remove(i);
                System.out.println("Successfully deleted");
                return;
            }
        }
        System.out.println("Customer not found");
    }

    public void listAllCustomers(ShoppingSystem system) {
        ArrayList<Customer> list = system.getCustomerList();
        if (list.isEmpty()) {
            System.out.println("暂无顾客信息");
            return;
        }
        for (Customer temp : list) {
            printCustomerInfo(temp);
        }
    }

    public void findCustomerById(ShoppingSystem system, String customerId) {
        for (Customer temp : system.getCustomerList()) {
            if (temp.getCustomerID().equals(customerId)) {
                printCustomerInfo(temp);
                return;
            }
        }
        System.out.println("没有找到该ID的顾客");
    }

    public void findCustomerByName(ShoppingSystem system, String name) {
        boolean isFind = false;
        for (Customer temp : system.getCustomerList()) {
            if (temp.getUsername().equals(name)) {
                printCustomerInfo(temp);
                isFind = true;
            }
        }
        if (!isFind) {
            System.out.println("没有找到该姓名的顾客");
        }
    }

    private void printCustomerInfo(Customer temp) {
        System.out.println("ID：" + temp.getCustomerID());
        System.out.println("用户名：" + temp.getUsername());
        System.out.println("等级：" + temp.getLeve());
        System.out.println("注册时间：" + temp.getRegisterTime());
        System.out.println("消费次数：" + temp.getCostCount());
        System.out.println("手机号：" + temp.getPhonenumber());
        System.out.println("邮箱：" + temp.getMailBox());
        System.out.println("------------------------");
    }

    // ==================== 商品管理 ====================

    public void addProduct(ShoppingSystem system, Product product) {
        system.getProductList().add(product);
        System.out.println("Product added successfully!");
    }

    public void updateProduct(ShoppingSystem system, String productId, Product updatedProduct) {
        ArrayList<Product> list = system.getProductList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getProductID().equals(productId)) {
                list.set(i, updatedProduct);
                System.out.println("Product updated successfully!");
                return;
            }
        }
        System.out.println("Product not found!");
    }

    public void deleteProduct(ShoppingSystem system, String productId) {
        ArrayList<Product> list = system.getProductList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getProductID().equals(productId)) {
                list.remove(i);
                System.out.println("Product deleted successfully!");
                return;
            }
        }
        System.out.println("Product not found!");
    }

    public void listAllProducts(ShoppingSystem system) {
        ArrayList<Product> list = system.getProductList();
        if (list.isEmpty()) {
            System.out.println("暂无商品信息");
            return;
        }
        for (Product temp : list) {
            System.out.println("ID: " + temp.getProductID());
            System.out.println("name: " + temp.getName());
            System.out.println("manufacture: " + temp.getManufacture());
            System.out.println("productiondate: " + temp.getProductionDate());
            System.out.println("model: " + temp.getModel());
            System.out.println("primecost: " + temp.getPrimeCost());
            System.out.println("retailprice: " + temp.getRetailPrice());
            System.out.println("stock: " + temp.getStock());
            System.out.println("------------------------");
        }
    }

    public ArrayList<Product> findProducts(ShoppingSystem system, String name,
                                           String manufacture, double minPrice, double maxPrice) {
        ArrayList<Product> result = new ArrayList<>();
        for (Product p : system.getProductList()) {
            boolean match = true;
            if (name != null && !name.isEmpty()
                    && !p.getName().toLowerCase().contains(name.toLowerCase())) {
                match = false;
            }
            if (manufacture != null && !manufacture.isEmpty()
                    && !p.getManufacture().equals(manufacture)) {
                match = false;
            }
            if (minPrice > 0 && p.getRetailPrice() < minPrice) {
                match = false;
            }
            if (maxPrice > 0 && p.getRetailPrice() > maxPrice) {
                match = false;
            }
            if (match) {
                result.add(p);
            }
        }
        return result;
    }
}