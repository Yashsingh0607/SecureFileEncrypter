import javax.crypto.SecretKey;

public class Main {
    public static void main(String[] args) throws Exception {

        SecretKey key = AESUtil.generateKey();

        String message = "Sensitive file content";

        String encrypted = AESUtil.encrypt(message, key);
        System.out.println("Encrypted: " + encrypted);

        String decrypted = AESUtil.decrypt(encrypted, key);
        System.out.println("Decrypted: " + decrypted);
    }
}