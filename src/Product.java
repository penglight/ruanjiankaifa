public class Product {
    private String name;
    private String productID;
    private String manufacture;
    private String productionDate;
    private String model;
    private double primeCost;
    private double retailPrice;
    private int stock;

    public Product(String productID, String name, String manufacture,
                   String productionDate, String model,
                   double primeCost, double retailPrice, int stock) {
        this.productID = productID;
        this.name = name;
        this.manufacture = manufacture;
        this.productionDate = productionDate;
        this.model = model;
        this.primeCost = primeCost;
        this.retailPrice = retailPrice;
        this.stock = stock;
    }

    public Product(String productID, String name, String manufacture, double retailPrice) {
        this.productID = productID;
        this.name = name;
        this.manufacture = manufacture;
        this.retailPrice = retailPrice;
    }

    public Product() {
    }

    public String getProductID() { return productID; }
    public void setProductID(String productID) { this.productID = productID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getManufacture() { return manufacture; }
    public void setManufacture(String manufacture) { this.manufacture = manufacture; }

    public String getProductionDate() { return productionDate; }
    public void setProductionDate(String productionDate) { this.productionDate = productionDate; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public double getPrimeCost() { return primeCost; }
    public void setPrimeCost(double primeCost) { this.primeCost = primeCost; }

    public double getRetailPrice() { return retailPrice; }
    public void setRetailPrice(double retailPrice) { this.retailPrice = retailPrice; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}