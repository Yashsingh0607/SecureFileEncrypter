import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

public class AESUtil {

    private static final int KEY_SIZE = 128; // 128-bit AES
    private static final int ITERATIONS = 65536;

    // Generate AES key from password + salt
    public static SecretKey generateKeyFromPassword(String password, byte[] salt) throws Exception {

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        KeySpec spec = new PBEKeySpec(
                password.toCharArray(),
                salt,
                ITERATIONS,
                KEY_SIZE
        );

        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), "AES");
    }

    // Generate random salt
    public static byte[] generateSalt() {
        byte[] salt = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return salt;
    }

    // Generate random IV
    public static byte[] generateIV() {
        byte[] iv = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        return iv;
    }

    // Encode to Base64 (for debugging if needed)
    public static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }
}