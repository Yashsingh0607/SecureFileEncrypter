import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // ---------------- CLI MODE ----------------
        if (args.length > 0) {

            if (args[0].equalsIgnoreCase("--help")) {

                System.out.println("==================================");
                System.out.println("      Secure File Encrypter");
                System.out.println("==================================");
                System.out.println("Usage:");
                System.out.println("  sfe encrypt <file> <password>");
                System.out.println("  sfe decrypt <file> <password>");
                System.out.println();
                System.out.println("Examples:");
                System.out.println("  sfe encrypt test.txt MyPassword");
                System.out.println("  sfe decrypt test.txt.enc MyPassword");

                return;
            }

            try {

                if (args.length == 3) {

                    String command = args[0];
                    String file = args[1];
                    String password = args[2];

                    if (command.equalsIgnoreCase("encrypt")) {

                        FileEncryptor.encryptFile(file, password);
                        return;

                    } else if (command.equalsIgnoreCase("decrypt")) {

                        FileEncryptor.decryptFile(file, password);
                        return;

                    } else {

                        System.out.println("Invalid command.");
                        System.out.println("Use: sfe --help");
                        return;
                    }

                } else {

                    System.out.println("Invalid arguments.");
                    System.out.println("Use: sfe --help");
                    return;
                }

            } catch (Exception e) {

                System.out.println("Error: " + e.getMessage());
                return;
            }
        }

        // ---------------- INTERACTIVE MODE ----------------

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
                System.out.println("Invalid input.");
                continue;
            }

            try {

                if (choice == 1) {

                    System.out.print("Enter file path: ");
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

                    System.out.println("Exiting...");
                    break;
                }

                else {

                    System.out.println("Invalid choice.");
                }

            } catch (Exception e) {

                System.out.println("Operation failed.");
                System.out.println("Reason: " + e.getMessage());
            }
        }

        scanner.close();
    }
}