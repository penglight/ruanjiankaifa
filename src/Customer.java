import java.util.ArrayList;

public class Customer extends User {
    private String customerId;
    private String leve;
    private String registerTime;
    private int costCount;
    private String phonenumber;
    private String mailBox;
    private ArrayList<Product> cart;
    private ArrayList<String> orders;

    public Customer(String customerId, String username, String password) {
        super(username, password);
        this.customerId = customerId;
        this.leve = "BRONZE";
        this.registerTime = "2026-09-12";
        this.costCount = 0;
        this.cart = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    @Override
    public boolean login(String username, String password) {
        return getUsername().equals(username) && getPassword().equals(password);
    }

    @Override
    public boolean changePassword(String newPassword) {
        if (newPassword == null || newPassword.isEmpty()) return false;
        if (newPassword.equals(getPassword())) return false;
        setPassword(newPassword);
        System.out.println("密码修改成功");
        return true;
    }

    @Override
    public void logout() {
        System.out.println("已退出登录");
    }

    // ===== 购物车 =====
    public void addToCart(ShoppingSystem system, String productId, int qty) {
        for (Product p : system.getProductList()) {
            if (p.getProductID().equals(productId)) {
                for (int i = 0; i < qty; i++) cart.add(p);
                System.out.println("已加入购物车，数量 " + qty);
                return;
            }
        }
        System.out.println("商品不存在");
    }

    public void showCart() {
        if (cart.isEmpty()) { System.out.println("购物车为空"); return; }
        double total = 0;
        for (Product p : cart) {
            System.out.println(p.getProductID() + " - " + p.getName() + " - " + p.getRetailPrice());
            total += p.getRetailPrice();
        }
        System.out.println("合计: " + total);
    }

    public void checkout(ShoppingSystem system) {
        if (cart.isEmpty()) { System.out.println("购物车为空，无法结账"); return; }
        double total = 0;
        for (Product p : cart) total += p.getRetailPrice();
        orders.add("订单时间: " + registerTime + ", 金额: " + total + ", 商品数: " + cart.size());
        costCount++;
        cart.clear();
        System.out.println("结账成功，金额: " + total);
    }

    public void showOrderHistory() {
        if (orders.isEmpty()) { System.out.println("暂无购物历史"); return; }
        for (String o : orders) System.out.println(o);
    }

    // ===== getter =====
    public String getCustomerID() { return customerId; }
    public String getLeve() { return leve; }
    public String getRegisterTime() { return registerTime; }
    public int getCostCount() { return costCount; }
    public String getPhonenumber() { return phonenumber; }
    public String getMailBox() { return mailBox; }
}