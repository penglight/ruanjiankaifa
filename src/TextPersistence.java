import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TextPersistence implements DataPersistence {   // ✅

    private static final String FILE = "data.txt";


    public void save(ArrayList<Customer> customers, ArrayList<Product> products) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE));

            writer.write("[PRODUCT]");
            writer.newLine();
            for (Product p : products) {
                writer.write(p.getProductID() + "," + p.getName() + "," + p.getManufacture() + ","
                        + p.getProductionDate() + "," + p.getModel() + "," + p.getPrimeCost() + ","
                        + p.getRetailPrice() + "," + p.getStock());
                writer.newLine();
            }

            writer.write("[CUSTOMER]");
            writer.newLine();
            for (Customer c : customers) {

                writer.write(c.getCustomerID() + "," + c.getUsername() + ","
                        + PasswordUtil.encrypt(c.getPassword()) + "," + c.getLeve() + ","
                        + c.getRegisterTime() + "," + c.getCostCount() + ","
                        + c.getPhonenumber() + "," + c.getMailBox()
                        + c.getTotalSpent());
                writer.newLine();
            }

            writer.close();
            System.out.println("数据已保存到文件");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public LoadResult load() {
        LoadResult result = new LoadResult();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE));
            String line;
            String section = "";
            while ((line = reader.readLine()) != null) {
                if (line.equals("[PRODUCT]")) { section = "PRODUCT"; continue; }
                if (line.equals("[CUSTOMER]")) { section = "CUSTOMER"; continue; }

                String[] d = line.split(",", -1);
                if (section.equals("PRODUCT") && d.length == 8) {
                    result.addProduct(new Product(d[0], d[1], d[2], d[3], d[4],
                            Double.parseDouble(d[5]), Double.parseDouble(d[6]),
                            Integer.parseInt(d[7])));
                } else if (section.equals("CUSTOMER") && d.length == 9) {
                    Customer c = new Customer(d[0], d[1], PasswordUtil.decrypt(d[2]),
                            d[4], d[6], d[7]);
                    c.setLeve(d[3]);
                    c.setCostCount(Integer.parseInt(d[5]));
                    c.setTotalSpent(Double.parseDouble(d[8]));
                    c.checkLevelUp();
                    result.addCustomer(c);
                }
            }
            reader.close();
        } catch (IOException e) {

        }
        return result;
    }



}
