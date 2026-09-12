import java.util.ArrayList;
import java.util.Scanner;
public class Administrator extends User {
    public Administrator() {
        username = "admin";
        password = "yninfo#777";
    }

    @Override
    public boolean login(String name, String pwd) {
        if (username.equals(name) && password.equals(pwd)) {
            System.out.println("You are successfully logged in");
            return true;
        } else {
            System.out.println("Invalid username or password");
            return false;
        }
    }

    public boolean changePassword(String newPassword) {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter your new password:");
        newPassword = input.nextLine();
        if (newPassword.equals(password)) {
            System.out.println("new password can not be the same as the old password");
            return false;
        } else {
            System.out.println("Password changed successfully");
            return true;
        }
    }

    public void ReCustomerPassword(ShoppingSystem system, String targetId, String newPwd) {
        System.out.println("Please enter the customer ID you want to re-customer:");
        Scanner input = new Scanner(System.in);
        targetId = input.next();
        ArrayList<Customer> customerList = system.getCustomerList();
        for (Customer temp : customerList) {
            if (temp.getCustomerID().equals(targetId)) {
                System.out.println("please enter the new password:");
                newPwd = input.nextLine();
                temp.setPassword(newPwd);
                System.out.println("successfully changed password");
                return;
            }
        }
        System.out.println("not found");
    }

    public void listAllCustomers(ShoppingSystem system) {
        ArrayList<Customer> customerList = system.getCustomerList();
        if (customerList.isEmpty()) {
            System.out.println("暂无顾客信息");
            return;
        }
        for (Customer temp : customerList) {
            System.out.println("ID：" + temp.getCustomerID());
            System.out.println("用户名：" + temp.getUsername());
            System.out.println("等级：" + temp.getLeve());
            System.out.println("注册时间：" + temp.getRegisterTime());
            System.out.println("消费次数：" + temp.getCostCount());
            System.out.println("手机号：" + temp.getPhonenumber());
            System.out.println("邮箱：" + temp.getMailBox());
            System.out.println("------------------------");
        }
    }

    public void deleteCustomer(String customerId, ShoppingSystem system) {
        ArrayList<Customer> customerList = system.getCustomerList();
        System.out.println("Please enter the ID you want to delete:");
        Scanner input = new Scanner(System.in);
        customerId = input.next();
        System.out.println("are you sure that you want to delete the customer" + customerId + "?");
        System.out.println("yes or no");
        String delete = input.nextLine();
        if (delete.equals("yes")) {
            int index = -1;
            //普通for循环查找下标
            for (int i = 0; i < customerList.size(); i++) {
                Customer temp = customerList.get(i);
                //如果customerId是int类型用 ==
                if (temp.getCustomerID().equals(customerId)) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                customerList.remove(index);
                System.out.println("successfully deleted");
            } else {
                System.out.println("not found");
            }
        } else {
            System.out.println("cancelled");

        }


    }

    public void findCustomer(ShoppingSystem system) {
        ArrayList<Customer> customerList = system.getCustomerList();
        System.out.println("Please choose the ID or name to search,or list all the customers:");
        Scanner input = new Scanner(System.in);
        String searchType = input.nextLine();

        if (searchType.equals("name")) {
            System.out.println("Please enter the name you want to search:");
            String name = input.nextLine();
            boolean isFind = false;
            for (Customer temp : customerList) {
                if (temp.getUsername().equals(name)) {
                    System.out.println("the information of " + temp.getUsername() + " as follows:");
                    System.out.println("ID：" + temp.getCustomerID());
                    System.out.println("等级：" + temp.getLeve());
                    System.out.println("注册时间：" + temp.getRegisterTime());
                    System.out.println("消费次数：" + temp.getCostCount());
                    System.out.println("手机号：" + temp.getPhonenumber());
                    System.out.println("邮箱：" + temp.getMailBox());
                    System.out.println("------------------------");
                    isFind = true;
                }
            }
            if (!isFind) {
                System.out.println("没有找到该姓名的顾客");
            }
        } else if (searchType.equals("id")) {
            System.out.println("Please enter the ID you want to search:");
            String customerId = input.nextLine();
            boolean isFind = false;
            for (Customer temp : customerList) {
                // 如果CustomerID是int类型，改成 temp.getCustomerID() == Integer.parseInt(customerId)
                if (temp.getCustomerID().equals(customerId)) {
                    System.out.println("the information of " + temp.getCustomerID() + " as follows:");
                    System.out.println("username：" + temp.getUsername());
                    System.out.println("等级：" + temp.getLeve());
                    System.out.println("注册时间：" + temp.getRegisterTime());
                    System.out.println("消费次数：" + temp.getCostCount());
                    System.out.println("手机号：" + temp.getPhonenumber());
                    System.out.println("邮箱：" + temp.getMailBox());
                    System.out.println("------------------------");
                    isFind = true;
                }
            }
            if (!isFind) {
                System.out.println("没有找到该ID的顾客");
            }
        } else if (searchType.equals("listall")) {
            listAllCustomers(system);
        } else {
            System.out.println("输入错误，请输入 name / id / listall");
        }}
    public void listAllProducts(ShoppingSystem system){
            ArrayList<Product> productsList = system.getProductList();
for (Product temp:productsList){
    System.out.println("ID:"+temp.getProductID());
    System.out.println("name"+temp.getName());
    System.out.println("manufacture:"+temp.getManufacture());
    System.out.println("productiondate:"+temp.getProductionDate());
    System.out.println("model:"+temp.getModel());
    System.out.println("primecost:"+temp.getPrimeCost());
    System.out.println("retailprice:"+temp.getRetailPrice());
    System.out.println("stock:"+temp.getStock());}

}
    public boolean addProduct(ShoppingSystem system) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter product ID:");
        String productID = sc.nextLine();

        System.out.println("Please enter product name:");
        String name = sc.nextLine();

        System.out.println("Please enter manufacturer:");
        String manufacture = sc.nextLine();

        System.out.println("Please enter production date:");
        String productionDate = sc.nextLine();

        System.out.println("Please enter model:");
        String model = sc.nextLine();

        System.out.println("Please enter prime cost:");
        double primeCost = sc.nextDouble();

        System.out.println("Please enter retail price:");
        double retailPrice = sc.nextDouble();

        System.out.println("Please enter stock:");
        int stock = sc.nextInt();

        Product product = new Product(productID, name, manufacture,
                productionDate, model,
                primeCost, retailPrice, stock);

        system.getProductList().add(product);
        System.out.println("Product added successfully!");
        return true;
    }

    public boolean updateProduct(ShoppingSystem system) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the product ID to update:");
        String id = sc.nextLine();

        ArrayList<Product> productList = system.getProductList();
        for (Product p : productList) {
            if (p.getProductID().equals(id)) {

                System.out.println("Please enter new name:");
                p.setName(sc.nextLine());
                System.out.println("Please enter new manufacturer:");
                p.setManufacture(sc.nextLine());
                System.out.println("Please enter new production date:");
                p.setProductionDate(sc.nextLine());
                System.out.println("Please enter new model:");
                p.setModel(sc.nextLine());
                System.out.println("Please enter new prime cost:");
                p.setPrimeCost(sc.nextDouble());
                System.out.println("Please enter new retail price:");
                p.setRetailPrice(sc.nextDouble());
                System.out.println("Please enter new stock:");
                p.setStock(sc.nextInt());

                System.out.println("Product updated successfully!");
                return true;
            }
        }
        System.out.println("Product not found!");
        return false;
    }

    public boolean deleteProduct(ShoppingSystem system) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the product ID to delete:");
        String id = sc.nextLine();

        ArrayList<Product> productList = system.getProductList();
        for (int i = 0; i < productList.size(); i++) {
            Product p = productList.get(i);
            if (p.getProductID().equals(id)) {
                productList.remove(i);
                System.out.println("Product deleted successfully!");
                return true;
            }
        }
        System.out.println("Product not found!");
        return false;
    }

    public ArrayList<Product> findProducts(ShoppingSystem system,
                                           String name,
                                           String manufacture,
                                           double minPrice,
                                           double maxPrice) {
        ArrayList<Product> result = new ArrayList<>();
        ArrayList<Product> productList = system.getProductList();

        for (Product p : productList) {
            boolean match = true;

            // 名称模糊匹配（不区分大小写）
            if (name != null && !name.isEmpty()
                    && !p.getName().toLowerCase().contains(name.toLowerCase())) {
                match = false;
            }
            // 厂家匹配
            if (manufacture != null && !manufacture.isEmpty()
                    && !p.getManufacture().equals(manufacture)) {
                match = false;
            }
            // 零售价区间
            if (minPrice > 0 && p.getRetailPrice() < minPrice) {
                match = false;
            }
            if (maxPrice > 0 && p.getRetailPrice() > maxPrice) {
                match = false;
            }

            if (match) {
                result.add(p);
            }
        }
        return result;
    }
            public void logout () {
        System.out.print("Successfully logged out");
            }
}



