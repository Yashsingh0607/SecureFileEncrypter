import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.SecureRandom;

public class FileEncryptor {

    public static void encryptFile(String filePath, SecretKey key) throws Exception {

        File inputFile = new File(filePath);
        if (!inputFile.exists()) {
            throw new Exception("❌ File not found.");
        }

        byte[] fileBytes = readFile(inputFile);

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

        String encryptedFileName = getEncryptedFileName(filePath);
        writeFile(encryptedFileName, combined);

        System.out.println("✅ File encrypted successfully: " + encryptedFileName);
    }

    public static void decryptFile(String filePath, SecretKey key) throws Exception {

        File inputFile = new File(filePath);
        if (!inputFile.exists()) {
            throw new Exception("❌ File not found.");
        }

        byte[] fileBytes = readFile(inputFile);

        byte[] iv = new byte[16];
        byte[] encryptedBytes = new byte[fileBytes.length - 16];

        System.arraycopy(fileBytes, 0, iv, 0, 16);
        System.arraycopy(fileBytes, 16, encryptedBytes, 0, encryptedBytes.length);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        String decryptedFileName = getDecryptedFileName(filePath);
        writeFile(decryptedFileName, decryptedBytes);

        System.out.println("✅ File decrypted successfully: " + decryptedFileName);
    }

    // Helper Methods

    private static byte[] readFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        fis.read(bytes);
        fis.close();
        return bytes;
    }

    private static void writeFile(String filePath, byte[] data) throws IOException {
        FileOutputStream fos = new FileOutputStream(filePath);
        fos.write(data);
        fos.close();
    }

    private static String getEncryptedFileName(String originalPath) {
        int dotIndex = originalPath.lastIndexOf(".");
        if (dotIndex == -1) {
            return originalPath + "_encrypted.enc";
        }
        return originalPath.substring(0, dotIndex) + "_encrypted.enc";
    }

    private static String getDecryptedFileName(String encryptedPath) {
        if (encryptedPath.endsWith("_encrypted.enc")) {
            return encryptedPath.replace("_encrypted.enc", "_decrypted.txt");
        }
        return encryptedPath.replace(".enc", "_decrypted.txt");
    }
}