import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.util.Arrays;
import javax.crypto.spec.GCMParameterSpec;

public class FileEncryptor {

    public static void encryptFile(String filePath, String password) throws Exception {

        File inputFile = new File(filePath);
        if (!inputFile.exists()) {
            throw new Exception("File not found.");
        }

        byte[] salt = AESUtil.generateSalt();
        byte[] iv = AESUtil.generateIV();

        SecretKey key = AESUtil.generateKeyFromPassword(password, salt);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, gcmSpec);

        String encryptedFileName = getEncryptedFileName(filePath);

        FileInputStream fis = new FileInputStream(inputFile);
        FileOutputStream fos = new FileOutputStream(encryptedFileName);

        // Write salt + iv first
        fos.write(salt);
        fos.write(iv);

        CipherOutputStream cos = new CipherOutputStream(fos, cipher);

        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = fis.read(buffer)) != -1) {
            cos.write(buffer, 0, bytesRead);
        }

        cos.close();
        fis.close();

        System.out.println("✅ File encrypted successfully: " + encryptedFileName);
    }

    public static void decryptFile(String filePath, String password) throws Exception {

        File inputFile = new File(filePath);
        if (!inputFile.exists()) {
            throw new Exception("File not found.");
        }

        FileInputStream fis = new FileInputStream(inputFile);

        byte[] salt = new byte[16];
        byte[] iv = new byte[12];

        fis.read(salt);
        fis.read(iv);

        SecretKey key = AESUtil.generateKeyFromPassword(password, salt);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.DECRYPT_MODE, key, gcmSpec);

        CipherInputStream cis = new CipherInputStream(fis, cipher);

        String decryptedFileName = getDecryptedFileName(filePath);
        FileOutputStream fos = new FileOutputStream(decryptedFileName);

        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = cis.read(buffer)) != -1) {
            fos.write(buffer, 0, bytesRead);
        }

        cis.close();
        fos.close();

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

            String baseName = encryptedPath.replace("_encrypted.enc", "");

            int dotIndex = baseName.lastIndexOf(".");
            if (dotIndex != -1) {
                String name = baseName.substring(0, dotIndex);
                String extension = baseName.substring(dotIndex);
                return name + "_decrypted" + extension;
            }

            return baseName + "_decrypted";
        }

        return encryptedPath.replace(".enc", "_decrypted");
    }
}