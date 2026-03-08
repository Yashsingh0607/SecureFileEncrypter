import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class AESUtil {

    private static final int AES_KEY_SIZE = 256;
    private static final int GCM_IV_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 128;
    private static final int SALT_LENGTH = 16;
    private static final int PBKDF2_ITERATIONS = 1200000;

    public static byte[] generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return salt;
    }

    public static byte[] generateIV() {
        byte[] iv = new byte[GCM_IV_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        return iv;
    }

    public static SecretKey getKeyFromPassword(String password, byte[] salt) throws Exception {

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(),
                salt,
                PBKDF2_ITERATIONS,
                AES_KEY_SIZE
        );

        SecretKey secret = new SecretKeySpec(
                factory.generateSecret(spec).getEncoded(),
                "AES"
        );

        return secret;
    }

    public static Cipher getEncryptCipher(SecretKey key, byte[] iv) throws Exception {

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        GCMParameterSpec parameterSpec = new GCMParameterSpec(
                GCM_TAG_LENGTH,
                iv
        );

        cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);

        return cipher;
    }

    public static Cipher getDecryptCipher(SecretKey key, byte[] iv) throws Exception {

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        GCMParameterSpec parameterSpec = new GCMParameterSpec(
                GCM_TAG_LENGTH,
                iv
        );

        cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);

        return cipher;
    }
}