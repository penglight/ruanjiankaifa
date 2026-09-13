import java.util.ArrayList;

public class Customer extends User {
    private String customerId;
    private String leve;
    private String registerTime;
    private int costCount;
    private String phonenumber;
    private String mailBox;
    private ShoppingCart cart;
    private ArrayList<Order> orders;

    public Customer(String customerId, String username, String password) {
        super(username, password);
        this.customerId = customerId;
        this.leve = "BRONZE";
        this.registerTime = "2026-09-13";
        this.costCount = 0;
        this.cart = new ShoppingCart();
        this.orders = new ArrayList<>();
    }


    public Order checkout(String paymentMethod) {
        if (cart.isEmpty()) {
            System.out.println("购物车为空，无法结账");
            return null;
        }
        Order order = new Order("O" + System.currentTimeMillis(),
                 java.time.LocalDateTime.now().toString(), paymentMethod);
        for (CartItem ci : cart.getItems()) {
            order.addItem(ci.getProduct(), ci.getQuantity(),
                    ci.getProduct().getRetailPrice());
        }
        orders.add(order);
        costCount++;
        cart.clear();
        System.out.println("结账成功，金额: " + order.getTotalAmount());
        return order;
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

    public ShoppingCart getCart() { return cart; }
    public ArrayList<Order> getOrders() { return orders; }

    public String getCustomerID() { return customerId; }
    public String getLeve() { return leve; }
    public String getRegisterTime() { return registerTime; }
    public int getCostCount() { return costCount; }
    public String getPhonenumber() { return phonenumber; }
    public String getMailBox() { return mailBox; }
}
