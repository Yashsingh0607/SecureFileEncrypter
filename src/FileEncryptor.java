import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import java.io.*;

public class FileEncryptor {

    private static final int BUFFER_SIZE = 65536;

    public static void encryptFile(String inputPath, String password) throws Exception {

        File inputFile = new File(inputPath);
        String outputPath = inputPath + ".enc";

        FileInputStream fis = new FileInputStream(inputFile);
        FileOutputStream fos = new FileOutputStream(outputPath);

        byte[] salt = AESUtil.generateSalt();
        byte[] iv = AESUtil.generateIV();

        fos.write(salt);
        fos.write(iv);

        SecretKey key = AESUtil.getKeyFromPassword(password, salt);
        Cipher cipher = AESUtil.getEncryptCipher(key, iv);

        CipherOutputStream cos = new CipherOutputStream(fos, cipher);

        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead;

        long totalSize = inputFile.length();
        long processed = 0;
        int lastProgress = -1;

        while ((bytesRead = fis.read(buffer)) != -1) {

            cos.write(buffer, 0, bytesRead);

            processed += bytesRead;
            int progress = (int)((processed * 100) / totalSize);

            if (progress != lastProgress) {
                System.out.print("\rEncrypting: " + progress + "%");
                lastProgress = progress;
            }
        }

        cos.close();
        fis.close();

        System.out.println("\n[SUCCESS] File encrypted successfully: " + outputPath);
    }

    public static void decryptFile(String inputPath, String password) throws Exception {

        File inputFile = new File(inputPath);
        String outputPath = inputPath.replace(".enc", "");

        FileInputStream fis = new FileInputStream(inputFile);

        byte[] salt = new byte[16];
        byte[] iv = new byte[12];

        fis.read(salt);
        fis.read(iv);

        SecretKey key = AESUtil.getKeyFromPassword(password, salt);
        Cipher cipher = AESUtil.getDecryptCipher(key, iv);

        CipherInputStream cis = new CipherInputStream(fis, cipher);
        FileOutputStream fos = new FileOutputStream(outputPath);

        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead;

        long totalSize = inputFile.length();
        long processed = 0;
        int lastProgress = -1;

        while ((bytesRead = cis.read(buffer)) != -1) {

            fos.write(buffer, 0, bytesRead);

            processed += bytesRead;
            int progress = (int)((processed * 100) / totalSize);

            if (progress != lastProgress) {
                System.out.print("\rDecrypting: " + progress + "%");
                lastProgress = progress;
            }
        }

        cis.close();
        fos.close();

        System.out.println("\n[SUCCESS] File decrypted successfully: " + outputPath);
    }
}