import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import java.io.*;

public class FileEncryptor {

    private static final int BUFFER_SIZE = 65536;

    public static void encryptFile(String inputPath, String password) throws Exception {

        File inputFile = new File(inputPath);

        if (!inputFile.exists()) {
            throw new FileNotFoundException("File not found.");
        }

        String outputPath = inputPath + ".enc";

        byte[] salt = AESUtil.generateSalt();
        byte[] iv = AESUtil.generateIV();

        SecretKey key = AESUtil.getKeyFromPassword(password, salt);
        Cipher cipher = AESUtil.getEncryptCipher(key, iv);

        try (
                FileInputStream fis = new FileInputStream(inputFile);
                FileOutputStream fos = new FileOutputStream(outputPath)
        ) {

            fos.write(salt);
            fos.write(iv);

            try (CipherOutputStream cos = new CipherOutputStream(fos, cipher)) {

                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead;

                long totalSize = inputFile.length();
                long processed = 0;
                int lastProgress = -1;

                while ((bytesRead = fis.read(buffer)) != -1) {

                    cos.write(buffer, 0, bytesRead);

                    processed += bytesRead;

                    int progress = totalSize == 0
                            ? 100
                            : (int) ((processed * 100) / totalSize);

                    if (progress != lastProgress) {
                        System.out.print("\rEncrypting: " + progress + "%");
                        lastProgress = progress;
                    }
                }
            }
        }

        System.out.println("\n[SUCCESS] File encrypted successfully:");
        System.out.println(outputPath);
    }

    public static void decryptFile(String inputPath, String password) throws Exception {

        File inputFile = new File(inputPath);

        if (!inputFile.exists()) {
            throw new FileNotFoundException("File not found.");
        }

        if (inputFile.length() < 28) {
            throw new IOException("Invalid encrypted file.");
        }

        String outputPath = inputPath.replace(".enc", "");

        try (
                FileInputStream fis = new FileInputStream(inputFile)
        ) {

            byte[] salt = new byte[16];
            byte[] iv = new byte[12];

            if (fis.read(salt) != 16) {
                throw new IOException("Unable to read salt.");
            }

            if (fis.read(iv) != 12) {
                throw new IOException("Unable to read IV.");
            }

            SecretKey key = AESUtil.getKeyFromPassword(password, salt);
            Cipher cipher = AESUtil.getDecryptCipher(key, iv);

            try (
                    CipherInputStream cis = new CipherInputStream(fis, cipher);
                    FileOutputStream fos = new FileOutputStream(outputPath)
            ) {

                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead;

                long totalSize = inputFile.length();
                long processed = 0;
                int lastProgress = -1;

                while ((bytesRead = cis.read(buffer)) != -1) {

                    fos.write(buffer, 0, bytesRead);

                    processed += bytesRead;

                    int progress = totalSize == 0
                            ? 100
                            : (int) ((processed * 100) / totalSize);

                    if (progress != lastProgress) {
                        System.out.print("\rDecrypting: " + progress + "%");
                        lastProgress = progress;
                    }
                }
            }
        }
        catch (IOException e) {

            File outputFile = new File(outputPath);

            if (outputFile.exists()) {
                outputFile.delete();
            }

            throw new Exception(
                    "Wrong password or file has been modified."
            );
        }

        System.out.println("\n[SUCCESS] File decrypted successfully:");
        System.out.println(outputPath);
    }
}