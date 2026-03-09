🔐 Secure File Encrypter (Java)

A password-based file encryption CLI tool built in Java using modern cryptographic standards.

This project securely encrypts and decrypts files using:

AES (Advanced Encryption Standard)

CBC Mode with PKCS5 Padding

PBKDF2 (Password-Based Key Derivation)

Random Salt

Random Initialization Vector (IV)

🚀 Features

✔ Password-based encryption
✔ PBKDF2 with 65,536 iterations
✔ Random salt generated for every encryption
✔ Random IV for additional security
✔ Proper encrypted file structure
✔ Clean command-line interface (CLI)
✔ Error handling for incorrect password

📦 Installation

1️⃣ Clone the repository

git clone https://github.com/yourusername/secure-file-encrypter.git

2️⃣ Navigate to the project

cd secure-file-encrypter

3️⃣ Run the installer

install.bat

This installs the CLI command:

sfe
▶ Usage
Encrypt a file
sfe encrypt file.txt myPassword
Decrypt a file
sfe decrypt file.txt.enc myPassword
📁 Encrypted File Structure

Each encrypted file contains:

[ 16 bytes SALT ]
[ 16 bytes IV ]
[ Ciphertext ]

This ensures strong cryptographic design.

🛠 How It Works

User enters file path and password

A random salt is generated

AES key is derived using PBKDF2

A random IV is generated

File is encrypted using AES/CBC

Salt + IV + Ciphertext are written to the output file

⚠ Important

If the password is incorrect, decryption will fail

Passwords are never stored

Always remember your password

🔮 Future Improvements

AES-GCM (authenticated encryption)

HMAC integrity verification

GUI version

Drag & drop encryption

Secure key vault integration

👨‍💻 Author

Yash Vardhan Singh

Built as a cryptography-focused project to demonstrate secure file encryption using Java.