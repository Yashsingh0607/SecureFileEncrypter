# 🔐 Secure File Encrypter

A secure password-based file encryption utility written in Java using modern cryptographic standards.

This tool allows users to encrypt and decrypt files from the command line while protecting data with strong encryption, authenticated encryption, random salts, and secure key derivation.

---

## 🚀 Features

✅ AES-256 Encryption

✅ AES-GCM Authenticated Encryption

✅ PBKDF2-HMAC-SHA256 Key Derivation

✅ 1,200,000 PBKDF2 Iterations

✅ Random Salt Generation

✅ Random IV Generation

✅ File Integrity Verification

✅ Password-Based Security

✅ Command Line Interface (CLI)

✅ Interactive Mode Support

✅ Large File Streaming Support

---

## 🔒 Cryptography Used

### Encryption Algorithm

* AES-256-GCM

### Key Derivation

* PBKDF2WithHmacSHA256
* 1,200,000 iterations
* Random 16-byte salt

### Authentication

AES-GCM provides:

* Confidentiality
* Integrity
* Authentication

Any modification to the encrypted file will cause decryption to fail.

---

## 📦 Installation

### Prerequisites

* Java 8 or later

Verify Java installation:

```bash
java -version
```

### Clone Repository

```bash
git clone https://github.com/Yashsingh0607/SecureFileEncrypter.git
cd SecureFileEncrypter
```

### Install CLI Command

Run:

```bash
install.bat
```

This creates:

```text
C:\tools\SecureFileEncrypter.jar
C:\tools\sfe.bat
```

---

## 🖥 Usage

### Show Help

```bash
java -jar SecureFileEncrypter.jar --help
```

### Encrypt File

```bash
java -jar SecureFileEncrypter.jar encrypt file.txt MyPassword
```

### Decrypt File

```bash
java -jar SecureFileEncrypter.jar decrypt file.txt.enc MyPassword
```

---

## Example

Encrypt:

```bash
java -jar SecureFileEncrypter.jar encrypt secret.txt MyPassword
```

Output:

```text
Encrypting: 100%
[SUCCESS] File encrypted successfully:
secret.txt.enc
```

Decrypt:

```bash
java -jar SecureFileEncrypter.jar decrypt secret.txt.enc MyPassword
```

Output:

```text
Decrypting: 100%
[SUCCESS] File decrypted successfully:
secret.txt
```

---

## 📁 Encrypted File Structure

Each encrypted file contains:

```text
[16 Bytes Salt]
[12 Bytes IV]
[Ciphertext + Authentication Tag]
```

This allows secure key regeneration and authenticated decryption.

---

## ⚙ How It Works

1. User provides a file and password.
2. A random salt is generated.
3. PBKDF2 derives a secure AES key.
4. A random IV is generated.
5. File data is encrypted using AES-GCM.
6. Salt and IV are stored alongside the ciphertext.
7. During decryption, the key is regenerated from the password and salt.
8. AES-GCM verifies file integrity before releasing plaintext.

---

## 🛡 Security Notes

* Passwords are never stored.
* Every encryption operation uses a unique salt.
* Every encryption operation uses a unique IV.
* AES-GCM detects tampering automatically.
* Incorrect passwords cause decryption failure.
* File contents are streamed, allowing encryption of large files.

---

## 🛠 Technologies Used

* Java
* Java Cryptography Architecture (JCA)
* AES-GCM
* PBKDF2-HMAC-SHA256
* SecureRandom
* File Streams
* Command Line Interface

---

## 🔮 Future Improvements

* Folder Encryption
* JavaFX GUI
* Drag-and-Drop Support
* Multi-File Encryption
* Cross-Platform Installer
* Password Strength Meter
* Unit Testing with JUnit
* Maven Build Support

---

## 👨‍💻 Author

Yash Vardhan Singh

Secure File Encrypter was built as a cryptography-focused Java project demonstrating modern password-based file encryption techniques.

---

## ⭐ Support

If you found this project useful, consider starring the repository.
