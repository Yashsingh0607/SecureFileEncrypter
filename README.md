🔐 Secure File Encrypter (Java)


A password-based file encryption CLI tool written in Java using modern cryptographic standards.

This tool securely encrypts and decrypts files using:

AES (Advanced Encryption Standard)

CBC Mode with PKCS5 Padding

PBKDF2 Password Key Derivation

Random Salt

Random Initialization Vector (IV)

🚀 Features

✔ Password-based encryption
✔ PBKDF2 with 65,536 iterations
✔ Random salt for every encryption
✔ Random IV for additional security
✔ Clean command line interface
✔ Error handling for incorrect password
✔ Secure encrypted file structure

📦 Installation

Download the latest release from the Releases page.

Or clone the repository:

git clone https://github.com/yourusername/secure-file-encrypter.git
cd secure-file-encrypter

Run the installer:

install.bat

This installs the CLI command:

sfe
▶ Usage
Encrypt a file
sfe encrypt file.txt myPassword
Decrypt a file
sfe decrypt file.txt.enc myPassword

Example output:

Encrypting: 100%
[SUCCESS] File encrypted successfully
🖥 CLI Example
C:\Users> sfe encrypt secret.txt myPassword

Encrypting: 100%
[SUCCESS] File encrypted successfully: secret.txt.enc
📁 Encrypted File Structure

Each encrypted file contains:

[ 16 bytes SALT ]
[ 16 bytes IV ]
[ Ciphertext ]

This ensures strong cryptographic design.

🛠 How It Works

User enters file path and password

Random salt is generated

AES key is derived using PBKDF2

Random IV is generated

File is encrypted using AES/CBC

Salt + IV + Ciphertext are written to the output file

⚠ Security Notes

Incorrect password will cause decryption failure

Passwords are never stored

Always keep your password secure

🔮 Future Improvements

AES-GCM authenticated encryption

HMAC integrity verification

GUI version

Drag & drop encryption

Cross-platform installer

Secure key vault integration

👨‍💻 Author

Yash Vardhan Singh

Built as a cryptography-focused project demonstrating secure file encryption using Java.

⭐ Support

If you like this project, consider giving it a star on GitHub.