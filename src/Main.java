import javax.crypto.SecretKey;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("===== Secure File Encrypter =====");
        System.out.println("1. Encrypt File");
        System.out.println("2. Decrypt File");
        System.out.print("Choose option (1 or 2): ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        try {

            if (choice == 1) {

                System.out.print("Enter file path to encrypt: ");
                String path = scanner.nextLine();

                SecretKey key = AESUtil.generateKey();

                FileEncryptor.encryptFile(path, key);

                System.out.println("Encryption completed successfully.");
                System.out.println("IMPORTANT: Save this key safely:");
                System.out.println(AESUtil.encodeKey(key));

            } else if (choice == 2) {

                System.out.print("Enter encrypted file path: ");
                String path = scanner.nextLine();

                System.out.print("Enter Base64 key: ");
                String keyString = scanner.nextLine();

                SecretKey key = AESUtil.decodeKey(keyString);

                FileEncryptor.decryptFile(path, key);

                System.out.println("Decryption completed successfully.");

            } else {
                System.out.println("Invalid choice.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        scanner.close();
    }
}