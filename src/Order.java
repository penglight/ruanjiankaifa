import java.util.ArrayList;

public class Order {
    private String orderId;
    private String orderTime;
    private double totalAmount;
    private String paymentMethod;
    private ArrayList<OrderItem> items;

    public Order(String orderId, String orderTime, String paymentMethod) {
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.paymentMethod = paymentMethod;
        this.items = new ArrayList<>();
    }

    public void addItem(Product product, int quantity, double unitPrice) {
        items.add(new OrderItem(product, quantity, unitPrice));
        totalAmount += unitPrice * quantity;
    }

    public void showOrder() {
        System.out.println("订单号: " + orderId + " 时间: " + orderTime
                + " 支付: " + paymentMethod + " 总金额: " + totalAmount);
        for (OrderItem oi : items) {
            System.out.println("  " + oi.getProduct().getName()
                    + " x" + oi.getQuantity() + " " + oi.getSubtotal());
        }
    }

    public String getOrderId() { return orderId; }
    public String getOrderTime() { return orderTime; }
    public double getTotalAmount() { return totalAmount; }
    public String getPaymentMethod() { return paymentMethod; }
    public ArrayList<OrderItem> getItems() { return items; }
}


