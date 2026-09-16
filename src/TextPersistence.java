import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TextPersistence {
    private static final String FILE = "data.txt";   // 存到这个文件

    // ===== 存：把两个列表写进文件 =====
    public void save(ArrayList<Customer> customers, ArrayList<Product> products) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE));

            writer.write("[PRODUCT]");       // 标记下面都是商品
            writer.newLine();
            for (Product p : products) {
                writer.write(p.getProductID() + "," + p.getName() + "," + p.getManufacture() + ","
                        + p.getProductionDate() + "," + p.getModel() + "," + p.getPrimeCost() + ","
                        + p.getRetailPrice() + "," + p.getStock());
                writer.newLine();
            }

            writer.write("[CUSTOMER]");      // 标记下面都是顾客
            writer.newLine();
            for (Customer c : customers) {
                // 密码加密后存
                writer.write(c.getCustomerID() + "," + c.getUsername() + ","
                        + PasswordUtil.encrypt(c.getPassword()) + "," + c.getLeve() + ","
                        + c.getRegisterTime() + "," + c.getCostCount() + ","
                        + c.getPhonenumber() + "," + c.getMailBox());
                writer.newLine();
            }

            writer.close();
            System.out.println("数据已保存到文件");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ===== 取：把文件内容读回两个列表 =====
    public void load(ArrayList<Customer> customers, ArrayList<Product> products) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE));
            String line;
            String section = "";
            while ((line = reader.readLine()) != null) {
                if (line.equals("[PRODUCT]")) { section = "PRODUCT"; continue; }
                if (line.equals("[CUSTOMER]")) { section = "CUSTOMER"; continue; }

                String[] d = line.split(",", -1);   // 拆开
                if (section.equals("PRODUCT") && d.length == 8) {
                    products.add(new Product(d[0], d[1], d[2], d[3], d[4],
                            Double.parseDouble(d[5]), Double.parseDouble(d[6]),
                            Integer.parseInt(d[7])));
                } else if (section.equals("CUSTOMER") && d.length == 8) {
                    Customer c = new Customer(d[0], d[1], PasswordUtil.decrypt(d[2]),d[4]);
                    c.setLeve(d[3]);
                    c.setCostCount(Integer.parseInt(d[5]));
                    c.setPhonenumber(d[6]);
                    c.setMailBox(d[7]);
                    customers.add(c);
                }
            }
            reader.close();
            System.out.println("已从文件读回数据");
        } catch (IOException e) {

        }
    }


}
