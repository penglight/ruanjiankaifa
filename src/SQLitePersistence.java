import java.sql.*;
import java.util.ArrayList;

public class SQLitePersistence implements DataPersistence {
    private static final String DB = "jdbc:sqlite:data.db";

    public SQLitePersistence() {
        createTables();
    }

    private void createTables() {
        try (Connection conn = DriverManager.getConnection(DB);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS products (" +
                    "productID TEXT PRIMARY KEY," +
                    "name TEXT, manufacture TEXT, productionDate TEXT," +
                    "model TEXT, primeCost REAL, retailPrice REAL, stock INTEGER)");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS customers (" +
                    "customerID TEXT PRIMARY KEY," +
                    "username TEXT, password TEXT, leve TEXT, registerTime TEXT," +
                    "costCount INTEGER, phonenumber TEXT, mailBox TEXT, totalSpent REAL)");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS admins (" +
                    "username TEXT PRIMARY KEY, password TEXT, firstLogin INTEGER)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(ArrayList<Customer> customers, ArrayList<Product> products, Administrator admin) {
        try (Connection conn = DriverManager.getConnection(DB)) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM products");
            stmt.executeUpdate("DELETE FROM customers");
            stmt.executeUpdate("DELETE FROM admins");

            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO products VALUES (?,?,?,?,?,?,?,?)");
            for (Product p : products) {
                ps.setString(1, p.getProductID());
                ps.setString(2, p.getName());
                ps.setString(3, p.getManufacture());
                ps.setString(4, p.getProductionDate());
                ps.setString(5, p.getModel());
                ps.setDouble(6, p.getPrimeCost());
                ps.setDouble(7, p.getRetailPrice());
                ps.setInt(8, p.getStock());
                ps.executeUpdate();
            }

            PreparedStatement cs = conn.prepareStatement(
                    "INSERT INTO customers VALUES (?,?,?,?,?,?,?,?,?)");
            for (Customer c : customers) {
                cs.setString(1, c.getCustomerID());
                cs.setString(2, c.getUsername());
                cs.setString(3, PasswordUtil.encrypt(c.getPassword()));  // 密码加密
                cs.setString(4, c.getLeve());
                cs.setString(5, c.getRegisterTime());
                cs.setInt(6, c.getCostCount());
                cs.setString(7, c.getPhonenumber());
                cs.setString(8, c.getMailBox());
                cs.setDouble(9, c.getTotalSpent());
                cs.executeUpdate();
            }

            PreparedStatement as = conn.prepareStatement(
                    "INSERT INTO admins VALUES (?,?,?)");
            as.setString(1, admin.getUsername());
            as.setString(2, PasswordUtil.encrypt(admin.getPassword()));  // 密码加密
            as.setInt(3, admin.isFirstLogin() ? 1 : 0);
            as.executeUpdate();

            System.out.println("数据已保存到SQLite数据库");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public LoadResult load() {
        LoadResult result = new LoadResult();
        try (Connection conn = DriverManager.getConnection(DB)) {
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM products");
            while (rs.next()) {
                result.addProduct(new Product(
                        rs.getString("productID"), rs.getString("name"),
                        rs.getString("manufacture"), rs.getString("productionDate"),
                        rs.getString("model"), rs.getDouble("primeCost"),
                        rs.getDouble("retailPrice"), rs.getInt("stock")));
            }

            rs = stmt.executeQuery("SELECT * FROM customers");
            while (rs.next()) {
                Customer c = new Customer(
                        rs.getString("customerID"), rs.getString("username"),
                        PasswordUtil.decrypt(rs.getString("password")),  // 解密
                        rs.getString("registerTime"), rs.getString("phonenumber"),
                        rs.getString("mailBox"));
                c.setLeve(rs.getString("leve"));
                c.setCostCount(rs.getInt("costCount"));
                c.setTotalSpent(rs.getDouble("totalSpent"));
                c.checkLevelUp();
                result.addCustomer(c);
            }

            rs = stmt.executeQuery("SELECT * FROM admins");
            if (rs.next()) {
                Administrator a = new Administrator();
                a.setUsername(rs.getString("username"));
                a.setPassword(PasswordUtil.decrypt(rs.getString("password")));
                a.setFirstLogin(rs.getInt("firstLogin") == 1);
                result.setAdmin(a);
            }
        } catch (SQLException e) {

        }
        return result;
    }
}
