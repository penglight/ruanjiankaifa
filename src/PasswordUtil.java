import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

    public class PasswordUtil {
        private static final String ALGORITHM = "AES";
        private static final String KEY = "ShoppingSystem01"; // 密钥，必须16个字符

        // 加密：明文 → 密文
        public static String encrypt(String plain) {
            try {
                SecretKeySpec key = new SecretKeySpec(KEY.getBytes("UTF-8"), ALGORITHM);
                Cipher cipher = Cipher.getInstance(ALGORITHM);
                cipher.init(Cipher.ENCRYPT_MODE, key);
                byte[] enc = cipher.doFinal(plain.getBytes("UTF-8"));
                return Base64.getEncoder().encodeToString(enc);
            } catch (Exception e) {
                throw new RuntimeException("加密失败", e);
            }
        }

        // 解密：密文 → 明文
        public static String decrypt(String cipherText) {
            try {
                SecretKeySpec key = new SecretKeySpec(KEY.getBytes("UTF-8"), ALGORITHM);
                Cipher cipher = Cipher.getInstance(ALGORITHM);
                cipher.init(Cipher.DECRYPT_MODE, key);
                byte[] dec = cipher.doFinal(Base64.getDecoder().decode(cipherText));
                return new String(dec, "UTF-8");
            } catch (Exception e) {
                throw new RuntimeException("解密失败", e);
            }
        }
    }


