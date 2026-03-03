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
                System.out.println("❌ Invalid input.");
                continue;
            }

            try {

                if (choice == 1) {

                    System.out.print("Enter file path to encrypt: ");
                    String path = scanner.nextLine();

                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    FileEncryptor.encryptFile(path, password);

                }

                else if (choice == 2) {

                    System.out.print("Enter encrypted file path: ");
                    String path = scanner.nextLine();

                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    FileEncryptor.decryptFile(path, password);
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