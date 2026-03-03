# 🔐 Secure File Encrypter (Java)

A password-based file encryption tool built using Java and modern cryptographic standards.

This project securely encrypts and decrypts files using:

- AES (Advanced Encryption Standard)
- CBC Mode with PKCS5 Padding
- PBKDF2 (Password-Based Key Derivation)
- Random Salt
- Random Initialization Vector (IV)

---

## 🚀 Features

✔ Password-based encryption  
✔ PBKDF2 with 65,536 iterations  
✔ Random Salt for every encryption  
✔ Random IV for security  
✔ Proper encrypted file structure  
✔ Clean CLI interface  
✔ Error handling for wrong password

---

## 📁 Encrypted File Structure

Each encrypted file contains:
[ 16 bytes SALT ]
[ 16 bytes IV ]
[ Ciphertext ]
This ensures strong cryptographic design.

---

## 🛠 How It Works

1. User enters file path and password.
2. A random salt is generated.
3. AES key is derived from password using PBKDF2.
4. Random IV is generated.
5. File is encrypted using AES/CBC.
6. Salt + IV + Ciphertext are stored in the output file.

---

## ▶ How to Run

Compile and run:
javac *.java
java Main

---

## ⚠ Important

- If password is incorrect, decryption will fail.
- Always remember your password.
- This tool does not store passwords.

---

## 🔮 Future Improvements

- AES-GCM (Authenticated Encryption)
- HMAC integrity verification
- GUI version
- Drag & drop encryption
- Secure key vault integration

---

## 📌 Author
YASH VARDHAN SINGH

Built as a cryptography-focused project to demonstrate secure file encryption using Java.