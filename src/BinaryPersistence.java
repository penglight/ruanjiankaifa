import java.io.*;
import java.util.ArrayList;

public class BinaryPersistence implements DataPersistence {
    private static final String FILE = "data.dat";   // 二进制文件后缀通常用 .dat

    @Override
    public void save(ArrayList<Customer> customers, ArrayList<Product> products, Administrator admin) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE));
            oos.writeObject(customers);
            oos.writeObject(products);
            oos.writeObject(admin);
            oos.close();
            System.out.println("数据已保存到二进制文件");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public LoadResult load() {
        LoadResult result = new LoadResult();
        File f = new File(FILE);
        if (!f.exists()) return result;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE));
            ArrayList<Customer> customers = (ArrayList<Customer>) ois.readObject();
            ArrayList<Product> products = (ArrayList<Product>) ois.readObject();
            ois.close();

            if (customers != null) {
                for (Customer c : customers) result.addCustomer(c);
            }
            if (products != null) {
                for (Product p : products) result.addProduct(p);
            }
            Administrator admin = (Administrator) ois.readObject();
            result.setAdmin(admin);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }



        return result;
    }
}
