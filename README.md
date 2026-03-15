<img width="327" height="411" alt="1773530135458" src="https://github.com/user-attachments/assets/3d928fec-bb86-4d3e-9c72-0552be3f9d96" />

# 🛰️ WirelessUSB

> Transfer files between your Android phone and PC over WiFi — no cables needed.

![Platform](https://img.shields.io/badge/Platform-Android-3DDC84?style=flat-square&logo=android&logoColor=white)
![Language](https://img.shields.io/badge/Language-Kotlin-7F52FF?style=flat-square&logo=kotlin&logoColor=white)
![UI](https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4?style=flat-square&logo=jetpackcompose&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)
![Release](https://img.shields.io/badge/Release-v1.0-blue?style=flat-square)

---

## 📱 Screenshots

| Stopped | Running |
|---|---|
| ![Screenshot_20260315_044624_wirelessUSB](https://github.com/user-attachments/assets/c472fcc2-b937-4266-ad65-ea2860dff4fc) | ![Screenshot_20260315_044643_wirelessUSB](https://github.com/user-attachments/assets/dd07cb12-4d6a-44f2-ad86-26ccf9211659) |

---

## ✨ Features

- 📡 **FTP Server** — Start/Stop with one tap
- 🔐 **Custom Credentials** — Set your own username & password
- 📷 **QR Code** — Scan from PC to connect instantly
- 🔔 **Foreground Service** — Server runs even when app is minimized
- 📂 **Full Storage Access** — Browse entire phone storage from PC
- 🖥️ **Retro Win98 UI** — Classic Windows 98 inspired interface

---

## 🚀 Download

[![Download APK](https://img.shields.io/badge/Download-APK-brightgreen?style=for-the-badge&logo=android)](https://github.com/rohityadav-alpha/WirelessUSB/releases/latest)

---

## 🛠️ Tech Stack

| Layer | Tech |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose + Material3 |
| FTP Server | Apache FTPServer |
| QR Code | ZXing |
| Background | Android Foreground Service |
| Build | Gradle (Kotlin DSL) |

---

## 📖 How to Use

1. Connect Phone & PC to **same WiFi**
2. Open **WirelessUSB** on your phone
3. Set username & password
4. Tap **Start Server**
5. On PC open **File Explorer**
6. Type in address bar:ftp://192.168.x.x:2221
7. Enter credentials → Browse files freely ✅

---

## 🔧 Build from Source


# Clone karo
```brash
git clone https://github.com/YOUR_USERNAME/WirelessUSB.git
```
# Android Studio mein open karo
# Run karo ▶️
Requirements:

Android Studio Hedgehog+

Min SDK: Android 8.0 (API 26)

Target SDK: Android 14 (API 34)
📁 Project Structure
```brash
com.example.wirelessusb/
├── MainActivity.kt           # Main UI
├── FtpServerManager.kt       # FTP Server logic
├── FtpForegroundService.kt   # Background service
├── QrCodeGenerator.kt        # QR Code generator
├── Win98Components.kt        # Retro UI components
└── ui/theme/
    └── Theme.kt              # Win98 color theme
```
