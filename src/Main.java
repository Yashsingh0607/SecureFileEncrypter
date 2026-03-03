import javax.crypto.SecretKey;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("\n==================================");
            System.out.println("        Secure File Encrypter");
            System.out.println("==================================");
            System.out.println("1. Encrypt File");
            System.out.println("2. Decrypt File");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("❌ Invalid input. Enter number only.");
                continue;
            }

            try {

                if (choice == 1) {

                    System.out.print("Enter file path to encrypt: ");
                    String path = scanner.nextLine();

                    SecretKey key = AESUtil.generateKey();
                    FileEncryptor.encryptFile(path, key);

                    System.out.println("\n🔑 IMPORTANT: Save this key safely:");
                    System.out.println(AESUtil.encodeKey(key));
                }

                else if (choice == 2) {

                    System.out.print("Enter encrypted file path: ");
                    String path = scanner.nextLine();

                    System.out.print("Enter Base64 key: ");
                    String keyString = scanner.nextLine();

                    SecretKey key = AESUtil.decodeKey(keyString);
                    FileEncryptor.decryptFile(path, key);
                }

                else if (choice == 3) {
                    System.out.println("Exiting program...");
                    break;
                }

                else {
                    System.out.println("❌ Invalid choice.");
                }

            } catch (Exception e) {
                System.out.println("\n❌ Operation failed.");
                System.out.println("Reason: " + e.getMessage());
            }
        }

        scanner.close();
    }
}