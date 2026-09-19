public class InputValidator {

    // 用户名：3-20位，只能字母、数字、下划线
    public static boolean isValidUsername(String username) {
        if (username == null || username.length() < 3 || username.length() > 20) {
            System.out.println("用户名长度需3-20位");
            return false;
        }
        if (!username.matches("^[a-zA-Z0-9_]+$")) {
            System.out.println("用户名只能包含字母、数字、下划线");
            return false;
        }
        return true;
    }

    // 密码：6-20位
    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 6 || password.length() > 20) {
            System.out.println("密码长度需6-20位");
            return false;
        }
        return true;
    }

    // 手机号：11位数字，1开头
    public static boolean isValidPhone(String phone) {
        if (phone == null || !phone.matches("^1[3-9]\\d{9}$")) {
            System.out.println("手机号格式不正确（需11位，1开头）");
            return false;
        }
        return true;
    }

    // 邮箱：包含@和.
    public static boolean isValidEmail(String email) {
        if (email == null || !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            System.out.println("邮箱格式不正确（示例：abc@qq.com）");
            return false;
        }
        return true;
    }

    // 商品ID：非空，字母数字
    public static boolean isValidProductId(String id) {
        if (id == null || id.isEmpty()) {
            System.out.println("商品ID不能为空");
            return false;
        }
        if (!id.matches("^[a-zA-Z0-9]+$")) {
            System.out.println("商品ID只能包含字母和数字");
            return false;
        }
        return true;
    }
}

