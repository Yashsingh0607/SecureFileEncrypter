import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.SecureRandom;

public class FileEncryptor {

    public static void encryptFile(String filePath, SecretKey key) throws Exception {

        File inputFile = new File(filePath);

        if (!inputFile.exists()) {
            throw new Exception("File not found.");
        }

        FileInputStream fis = new FileInputStream(inputFile);
        byte[] fileBytes = new byte[(int) inputFile.length()];
        fis.read(fileBytes);
        fis.close();

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        byte[] iv = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

        byte[] encryptedBytes = cipher.doFinal(fileBytes);

        // Combine IV + encrypted data
        byte[] combined = new byte[iv.length + encryptedBytes.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encryptedBytes, 0, combined, iv.length, encryptedBytes.length);

        FileOutputStream fos = new FileOutputStream(filePath + ".enc");
        fos.write(combined);
        fos.close();
    }

    public static void decryptFile(String filePath, SecretKey key) throws Exception {

        File inputFile = new File(filePath);

        if (!inputFile.exists()) {
            throw new Exception("File not found.");
        }

        FileInputStream fis = new FileInputStream(inputFile);
        byte[] fileBytes = new byte[(int) inputFile.length()];
        fis.read(fileBytes);
        fis.close();

        byte[] iv = new byte[16];
        byte[] encryptedBytes = new byte[fileBytes.length - 16];

        System.arraycopy(fileBytes, 0, iv, 0, 16);
        System.arraycopy(fileBytes, 16, encryptedBytes, 0, encryptedBytes.length);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        FileOutputStream fos = new FileOutputStream(filePath.replace(".enc", "_decrypted"));
        fos.write(decryptedBytes);
        fos.close();
    }
}
