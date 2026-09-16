import java.io.Serializable;

public class OrderItem implements Serializable{
    private Product product;
    private int quantity;
    private double unitPrice;   // 购买时的单价快照

    public OrderItem(Product product, int quantity, double unitPrice) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public double getSubtotal() {
        return unitPrice * quantity;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }
}
