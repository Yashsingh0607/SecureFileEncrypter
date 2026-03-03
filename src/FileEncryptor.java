import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.util.Arrays;

public class FileEncryptor {

    public static void encryptFile(String filePath, String password) throws Exception {

        File inputFile = new File(filePath);
        if (!inputFile.exists()) {
            throw new Exception("File not found.");
        }

        byte[] fileBytes = readFile(inputFile);

        // Generate salt + IV
        byte[] salt = AESUtil.generateSalt();
        byte[] iv = AESUtil.generateIV();

        // Derive key from password
        SecretKey key = AESUtil.generateKeyFromPassword(password, salt);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

        byte[] encryptedBytes = cipher.doFinal(fileBytes);

        // Combine salt + IV + encrypted data
        byte[] combined = new byte[salt.length + iv.length + encryptedBytes.length];

        System.arraycopy(salt, 0, combined, 0, salt.length);
        System.arraycopy(iv, 0, combined, salt.length, iv.length);
        System.arraycopy(encryptedBytes, 0, combined, salt.length + iv.length, encryptedBytes.length);

        String encryptedFileName = getEncryptedFileName(filePath);
        writeFile(encryptedFileName, combined);

        System.out.println("✅ File encrypted successfully: " + encryptedFileName);
    }

    public static void decryptFile(String filePath, String password) throws Exception {

        File inputFile = new File(filePath);
        if (!inputFile.exists()) {
            throw new Exception("File not found.");
        }

        byte[] fileBytes = readFile(inputFile);

        if (fileBytes.length < 32) {
            throw new Exception("Invalid encrypted file.");
        }

        // Extract salt, IV, ciphertext
        byte[] salt = Arrays.copyOfRange(fileBytes, 0, 16);
        byte[] iv = Arrays.copyOfRange(fileBytes, 16, 32);
        byte[] encryptedBytes = Arrays.copyOfRange(fileBytes, 32, fileBytes.length);

        // Regenerate key from password + salt
        SecretKey key = AESUtil.generateKeyFromPassword(password, salt);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        String decryptedFileName = getDecryptedFileName(filePath);
        writeFile(decryptedFileName, decryptedBytes);

        System.out.println("✅ File decrypted successfully: " + decryptedFileName);
    }

    // ---------------- Helper Methods ----------------

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