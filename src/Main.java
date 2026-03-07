public class Main {

    public static void main(String[] args) {

        if (args.length < 3) {
            System.out.println("Usage:");
            System.out.println("Encrypt: java -jar SecureFileEncrypter.jar encrypt <file> <password>");
            System.out.println("Decrypt: java -jar SecureFileEncrypter.jar decrypt <file> <password>");
            return;
        }

        String command = args[0];
        String filePath = args[1];
        String password = args[2];

        try {
            if (command.equalsIgnoreCase("encrypt")) {
                FileEncryptor.encryptFile(filePath, password);
            } else if (command.equalsIgnoreCase("decrypt")) {
                FileEncryptor.decryptFile(filePath, password);
            } else {
                System.out.println("Invalid command. Use encrypt or decrypt.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}