import java.util.ArrayList;

// 持久化接口：所有版本都实现这两个方法
public interface DataPersistence {
    void save(ArrayList<Customer> customers, ArrayList<Product> products,Administrator admin);
    LoadResult load();
}
