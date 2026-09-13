import java.util.ArrayList;

public class ShoppingCart {
    private ArrayList<CartItem> items;

    public ShoppingCart() {
        items = new ArrayList<>();
    }


    public void addItem(Product product, int quantity) {
        for (CartItem ci : items) {
            if (ci.getProduct().getProductID().equals(product.getProductID())) {
                ci.setQuantity(ci.getQuantity() + quantity);
                System.out.println("已加入购物车，数量累计 " + ci.getQuantity());
                return;
            }
        }
        items.add(new CartItem(product, quantity));
        System.out.println("已加入购物车，数量 " + quantity);
    }

    public void showCart() {
        if (items.isEmpty()) {
            System.out.println("购物车为空");
            return;
        }
        for (CartItem ci : items) {
            System.out.println(ci.getProduct().getProductID() + " - "
                    + ci.getProduct().getName() + " x" + ci.getQuantity()
                    + " 小计:" + ci.getSubtotal());
        }
        System.out.println("合计: " + getTotalPrice());
    }

    public double getTotalPrice() {
        double total = 0;
        for (CartItem ci : items) total += ci.getSubtotal();
        return total;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public ArrayList<CartItem> getItems() { return items; }

    public void clear() {
        items.clear();
    }
}

