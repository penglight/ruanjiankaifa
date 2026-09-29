import java.util.ArrayList;
import java.util.Scanner;

public class ShoppingSystem {
    private ArrayList<Customer> customerList;
    private ArrayList<Product> productsList;

    private Administrator admin = new Administrator();
    private Scanner sc = new Scanner(System.in);
    private TextPersistence persistence = new TextPersistence();   // v1 //BinaryPersistence(); v2 //ExcelPersistence(); v3//SQLitePersistence v4


    public ShoppingSystem() {
        customerList = new ArrayList<>();
        productsList = new ArrayList<>();
    }

    public ArrayList<Customer> getCustomerList() { return customerList; }
    public ArrayList<Product> getProductList() { return productsList; }

    public void addCustomer(Customer c) { customerList.add(c); }
    public void addProduct(Product p) { productsList.add(p); }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                int choice = sc.nextInt();
                sc.nextLine();
                return choice;
            } else {
                System.out.println("请输入数字！");
                sc.nextLine();   // 清掉错误输入
            }
        }
    }


    // ==================== 程序入口 ====================
    public void run() {
        loadData();        // 启动时读回数据
        showMainMenu();    // 显示主菜单
    }


    // ==================== 主菜单：选择角色 ====================
    private void showMainMenu() {
        while (true) {
            System.out.println("\n===== 购物管理系统 =====");
            System.out.println("1. 管理员登录");
            System.out.println("2. 客户登录");
            System.out.println("3. 客户注册");
            System.out.println("0. 退出");
            int choice = readInt("请选择: ");

            if (choice == 1) {
                adminLogin();
            } else if (choice == 2) {
                customerLogin();
            } else if (choice == 3) {
                customerRegister();
            } else if (choice == 0) {
                saveData();
                System.out.println("再见！");
                return;
            }{
                System.out.println("无效选项");
            }
        }
    }

    // ==================== 管理员登录 ====================
    private void adminLogin() {
        System.out.print("用户名: ");
        String name = sc.nextLine();
        System.out.print("密码: ");
        String pwd = sc.nextLine();

        if (admin.login(name, pwd)) {
            if (admin.isFirstLogin()) {
                forceChangeAdmin();
            }
            adminMenu();
        }
    }

    private void forceChangeAdmin() {
        System.out.println("首次登录，必须修改初始用户名和密码！");

        while (true) {
            System.out.print("请输入新用户名: ");
            String newName = sc.nextLine();
            if (!InputValidator.isValidUsername(newName)) continue;
            System.out.print("请输入新密码: ");
            String newPwd = sc.nextLine();
            if (!InputValidator.isValidPassword(newPwd)) continue;

            if (admin.forceChangeCredentials(newName, newPwd)) {
                System.out.println("修改成功，请记住新用户名和密码。");
                break;
            }

        }
    }


    // ==================== 客户登录 / 注册 ====================
    private void customerLogin() {
        System.out.print("用户名: ");
        String name = sc.nextLine();
        System.out.print("密码: ");
        String pwd = sc.nextLine();

        Customer cur = findCustomer(name, pwd);
        if (cur != null) {
            customerMenu(cur);
        }
    }

    private Customer findCustomer(String name, String pwd) {
        for (Customer c : customerList) {
            if (c.getUsername().equals(name) && c.getPassword().equals(pwd)) {
                System.out.println("登录成功，欢迎 " + name + "！");
                return c;
            }
        }
        System.out.println("用户名或密码错误");
        return null;
    }

    private void customerRegister() {
        System.out.print("请输入用户名: ");
        String name = sc.nextLine();
        if (!InputValidator.isValidUsername(name)) return;
        System.out.print("请输入密码: ");
        String pwd = sc.nextLine();
        if (!InputValidator.isValidPassword(pwd)) return;
        System.out.println("请输入电话号码：");
        String phone = sc.nextLine();
        if (!InputValidator.isValidPhone(phone)) return;
        System.out.println("请输入邮箱：");
        String email = sc.nextLine();
        if (!InputValidator.isValidEmail(email)) return;


        // 检查用户名是否已存在
        boolean exists = false;
        for (Customer c : customerList) {
            if (c.getUsername().equals(name)) {
                exists = true;
                break;
            }
        }
        if (exists) {
            System.out.println("该用户名已被注册！");
            return;
        }

        // 自动生成顾客ID（用时间戳，保证唯一）
        String id = "C" + System.currentTimeMillis();

        customerList.add(new Customer(id, name, pwd, java.time.LocalDateTime.now().toString(),phone,email));
        System.out.println("注册成功！您的ID是：" + id + "，请登录。");
    }



    // ==================== 管理员菜单 ====================
    private void adminMenu() {
        while (true) {
            System.out.println("\n========== 管理员菜单 ==========");
            System.out.println("1. 添加顾客");
            System.out.println("2. 重置顾客密码");
            System.out.println("3. 删除顾客");
            System.out.println("4. 列出所有顾客");
            System.out.println("5. 按ID查询顾客");
            System.out.println("6. 按姓名查询顾客");
            System.out.println("7. 添加商品");
            System.out.println("8. 修改商品");
            System.out.println("9. 删除商品");
            System.out.println("10. 列出所有商品");
            System.out.println("11. 组合查询商品");
            System.out.println("12. 修改管理员密码");
            System.out.println("0. 退出");
            int choice = readInt("请选择: ");

            switch (choice) {
                case 1: addCustomerMenu(); break;
                case 2: resetPwdMenu(); break;
                case 3: deleteCustomerMenu(); break;
                case 4: admin.listAllCustomers(this); break;
                case 5: findCustomerByIdMenu(); break;
                case 6: findCustomerByNameMenu(); break;
                case 7: addProductMenu(); break;
                case 8: updateProductMenu(); break;
                case 9: deleteProductMenu(); break;
                case 10: admin.listAllProducts(this); break;
                case 11: findProductsMenu(); break;
                case 12: changeAdminPwdMenu(); break;
                case 0: admin.logout(); return;
                default: System.out.println("无效选项");
            }
        }
    }

    // ==================== 客户菜单（带参数 cur，且正常闭合）====================
    private void customerMenu(Customer cur) {
        while (true) {
            System.out.println("\n===== 客户菜单 =====");
            System.out.println("1. 浏览所有商品");
            System.out.println("2. 加入购物车");
            System.out.println("3. 查看购物车");
            System.out.println("4. 修改购物车商品数量");
            System.out.println("5. 移除购物车商品");
            System.out.println("6. 结账");
            System.out.println("7. 查看购物历史");
            System.out.println("8. 修改密码");
            System.out.println("0. 退出登录");
            int choice = readInt("请选择: ");

            if (choice == 1) {
                admin.listAllProducts(this);
            } else if (choice == 2) {
                System.out.print("请输入商品ID: ");
                String pid = sc.nextLine();
                System.out.print("请输入数量: ");
                int qty = sc.nextInt();
                sc.nextLine();
                Product prod = findProduct(pid);
                if (prod != null) {
                    cur.getCart().addItem(prod, qty);
                } else {
                    System.out.println("商品不存在");
                }
            } else if (choice == 3) {
                cur.getCart().showCart();
            } else if (choice == 4) {
                System.out.print("商品ID: ");
                String pid = sc.nextLine();
                System.out.print("新数量: ");
                int qty = sc.nextInt();
                sc.nextLine();
                cur.getCart().updateQuantity(pid, qty);
            } else if (choice == 5) {
                System.out.print("商品ID: ");
                String pid = sc.nextLine();
                cur.getCart().removeItem(pid);
            } else if (choice == 6) {
                checkoutMenu(cur);
            } else if (choice == 7) {
                showOrders(cur);
            } else if (choice == 8) {
                System.out.print("新密码: ");
                String newPwd = sc.nextLine();
                cur.changePassword(newPwd);
            } else if (choice == 0) {
                cur.logout();
                return;
            } else {
                System.out.println("无效选项");
            }
        }
    }

    // ==================== 顾客相关菜单（平级方法，不在customerMenu内）====================
    private Product findProduct(String pid) {
        for (Product p : productsList) {
            if (p.getProductID().equals(pid)) return p;
        }
        return null;
    }

    private void checkoutMenu(Customer cur) {
        System.out.println("请选择支付方式: 1.支付宝 2.微信 3.银行卡");
        int pay = sc.nextInt();
        sc.nextLine();
        String method = (pay == 1) ? "支付宝" : (pay == 2) ? "微信" : "银行卡";
        cur.checkout(method);
    }

    private void showOrders(Customer cur) {
        if (cur.getOrders().isEmpty()) {
            System.out.println("暂无购物历史");
            return;
        }
        for (Order o : cur.getOrders()) {
            o.showOrder();
        }
    }

    private void addCustomerMenu() {
        System.out.print("用户名: ");
        String uname = sc.nextLine();
        if (!InputValidator.isValidUsername(uname)) return;
        System.out.print("密码: ");
        String pwd = sc.nextLine();
        if (!InputValidator.isValidPassword(pwd)) return;
        System.out.println("请输入电话号码：");
        String phone = sc.nextLine();
        if (!InputValidator.isValidPhone(phone)) return;
        System.out.println("请输入邮箱：");
        String email = sc.nextLine();
        if (!InputValidator.isValidEmail(email)) return;

        String id = "C" + System.currentTimeMillis();

        admin.addCustomer(this, new Customer(id, uname, pwd, java.time.LocalDateTime.now().toString(),phone,email));
        System.out.println("添加成功！顾客ID：" + id);
    }


    private void resetPwdMenu() {
        System.out.print("顾客ID: ");
        String id = sc.nextLine();
        System.out.print("新密码: ");
        String pwd = sc.nextLine();
        admin.ReCustomerPassword(this, id, pwd);
    }

    private void deleteCustomerMenu() {
        System.out.print("顾客ID: ");
        String id = sc.nextLine();
        admin.deleteCustomer(this, id);
    }

    private void findCustomerByIdMenu() {
        System.out.print("顾客ID: ");
        String id = sc.nextLine();
        admin.findCustomerById(this, id);
    }

    private void findCustomerByNameMenu() {
        System.out.print("顾客姓名: ");
        String name = sc.nextLine();
        admin.findCustomerByName(this, name);
    }

    // ==================== 商品相关菜单 ====================
    private void addProductMenu() {
        System.out.print("ID: "); String id = sc.nextLine();
        if (!InputValidator.isValidProductId(id)) return;
        // 检查商品ID是否已存在
        boolean exists = false;
        for (Product p : productsList) {
            if (p.getProductID().equals(id)) {
                exists = true;
                break;
            }
        }
        if (exists) {
            System.out.println("该商品ID已存在！");
            return;
        }

        System.out.print("名称: "); String name = sc.nextLine();
        System.out.print("厂家: "); String mf = sc.nextLine();
        System.out.print("生产日期: "); String date = sc.nextLine();
        System.out.print("型号: "); String model = sc.nextLine();
        System.out.print("进货价: "); double pc = sc.nextDouble(); sc.nextLine();
        System.out.print("零售价: "); double rp = sc.nextDouble(); sc.nextLine();
        System.out.print("库存: "); int stock = sc.nextInt(); sc.nextLine();

        admin.addProduct(this, new Product(id, name, mf, date, model, pc, rp, stock));
    }

    private void updateProductMenu() {
        System.out.print("要修改的商品ID: "); String oldId = sc.nextLine();
        System.out.print("新ID: "); String id = sc.nextLine();
        System.out.print("新名称: "); String name = sc.nextLine();
        System.out.print("新厂家: "); String mf = sc.nextLine();
        System.out.print("新生产日期: "); String date = sc.nextLine();
        System.out.print("新型号: "); String model = sc.nextLine();
        System.out.print("新进货价: "); double pc = sc.nextDouble(); sc.nextLine();
        System.out.print("新零售价: "); double rp = sc.nextDouble(); sc.nextLine();
        System.out.print("新库存: "); int stock = sc.nextInt(); sc.nextLine();

        admin.updateProduct(this, oldId, new Product(id, name, mf, date, model, pc, rp, stock));
    }

    private void deleteProductMenu() {
        System.out.print("商品ID: "); String id = sc.nextLine();
        admin.deleteProduct(this, id);
    }

    private void findProductsMenu() {
        System.out.print("名称(回车跳过): "); String name = sc.nextLine();
        System.out.print("厂家(回车跳过): "); String mf = sc.nextLine();
        System.out.print("最低价(0跳过): "); double min = sc.nextDouble(); sc.nextLine();
        System.out.print("最高价(0跳过): "); double max = sc.nextDouble(); sc.nextLine();

        ArrayList<Product> result = admin.findProducts(this, name, mf, min, max);
        if (result.isEmpty()) {
            System.out.println("没有匹配的商品");
        } else {
            for (Product p : result) {
                System.out.println(p.getProductID() + " - " + p.getName() + " - " + p.getRetailPrice());
            }
        }
    }

    private void changeAdminPwdMenu() {
        System.out.print("新密码: "); String pwd = sc.nextLine();
        admin.changePassword(pwd);
    }
    // 文本
    private void loadData() {
        persistence.load(customerList, productsList);
        System.out.println("恢复：顾客 " + customerList.size() + " 个，商品 " + productsList.size() + " 个");
    }


    private void saveData() {
        persistence.save(customerList, productsList);
    }

}
