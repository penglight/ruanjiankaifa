import java.io.*;
import java.util.ArrayList;

public class BinaryPersistence implements DataPersistence {
    private static final String FILE = "data.dat";   // 二进制文件后缀通常用 .dat

    @Override
    public void save(ArrayList<Customer> customers, ArrayList<Product> products,Administrator admin) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE));
            oos.writeObject(customers);   // 整个顾客列表，直接写
            oos.writeObject(products);    // 整个商品列表，直接写
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
            // 按存的顺序读回来，要强转成对应的类型
            ArrayList<Customer> customers = (ArrayList<Customer>) ois.readObject();
            ArrayList<Product> products = (ArrayList<Product>) ois.readObject();
            ois.close();

            if (customers != null) {
                for (Customer c : customers) result.addCustomer(c);
            }
            if (products != null) {
                for (Product p : products) result.addProduct(p);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
}
