import java.sql.*;
import java.util.ArrayList;

public class SQLitePersistence implements DataPersistence {
    private static final String DB_URL = "jdbc:sqlite:data.db";

    // 构造方法：启动时建表（如果表不存在）
    public SQLitePersistence() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            // 建商品表
            stmt.execute("CREATE TABLE IF NOT EXISTS products (" +
                    "productID TEXT PRIMARY KEY, " +
                    "name TEXT, " +
                    "manufacture TEXT, " +
                    "productionDate TEXT, " +
                    "model TEXT, " +
                    "primeCost REAL, " +
                    "retailPrice REAL, " +
                    "stock INTEGER)");

            // 建顾客表
            stmt.execute("CREATE TABLE IF NOT EXISTS customers (" +
                    "customerID TEXT PRIMARY KEY, " +
                    "username TEXT, " +
                    "password TEXT, " +
                    "leve TEXT, " +
                    "registerTime TEXT, " +
                    "costCount INTEGER, " +
                    "phonenumber TEXT, " +
                    "mailBox TEXT)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ===== 存：清空表再插入 =====
    @Override
    public void save(ArrayList<Customer> customers, ArrayList<Product> products,Administrator admin) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {

            // 先清空两张表
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("DELETE FROM products");
                stmt.execute("DELETE FROM customers");
            }

            // 插入商品
            String insertProduct = "INSERT INTO products VALUES (?,?,?,?,?,?,?,?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertProduct)) {
                for (Product p : products) {
                    pstmt.setString(1, p.getProductID());
                    pstmt.setString(2, p.getName());
                    pstmt.setString(3, p.getManufacture());
                    pstmt.setString(4, p.getProductionDate());
                    pstmt.setString(5, p.getModel());
                    pstmt.setDouble(6, p.getPrimeCost());
                    pstmt.setDouble(7, p.getRetailPrice());
                    pstmt.setInt(8, p.getStock());
                    pstmt.executeUpdate();
                }
            }

            // 插入顾客（密码加密）
            String insertCustomer = "INSERT INTO customers VALUES (?,?,?,?,?,?,?,?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertCustomer)) {
                for (Customer c : customers) {
                    pstmt.setString(1, c.getCustomerID());
                    pstmt.setString(2, c.getUsername());
                    pstmt.setString(3, PasswordUtil.encrypt(c.getPassword()));  // 加密
                    pstmt.setString(4, c.getLeve());
                    pstmt.setString(5, c.getRegisterTime());
                    pstmt.setInt(6, c.getCostCount());
                    pstmt.setString(7, c.getPhonenumber());
                    pstmt.setString(8, c.getMailBox());
                    pstmt.executeUpdate();
                }
            }

            System.out.println("数据已保存到 SQLite 数据库");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ===== 取：从数据库查询 =====
    @Override
    public LoadResult load() {
        LoadResult result = new LoadResult();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            // 查商品
            ResultSet rs = stmt.executeQuery("SELECT * FROM products");
            while (rs.next()) {
                String id = rs.getString("productID");
                String name = rs.getString("name");
                String mf = rs.getString("manufacture");
                String date = rs.getString("productionDate");
                String model = rs.getString("model");
                double pc = rs.getDouble("primeCost");
                double rp = rs.getDouble("retailPrice");
                int stock = rs.getInt("stock");
                result.addProduct(new Product(id, name, mf, date, model, pc, rp, stock));
            }
            rs.close();

            // 查顾客
            rs = stmt.executeQuery("SELECT * FROM customers");
            while (rs.next()) {
                String id = rs.getString("customerID");
                String name = rs.getString("username");
                String pwd = PasswordUtil.decrypt(rs.getString("password"));  // 解密
                String leve = rs.getString("leve");
                String time = rs.getString("registerTime");
                int cost = rs.getInt("costCount");
                String phone = rs.getString("phonenumber");
                String mail = rs.getString("mailBox");

                Customer c = new Customer(id, name, pwd,time,phone,mail);
                c.setLeve(leve);
                c.setCostCount(cost);
                result.addCustomer(c);
            }
            rs.close();

        } catch (SQLException e) {
            // 数据库文件不存在时会报这个错，忽略即可
        }
        return result;
    }
}
