<img width="327" height="411" alt="17735301354581" src="https://github.com/user-attachments/assets/d7a0104c-8cb4-4a5f-bf4a-5f5ea57605d6" />

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
| ![Screenshot_20260315_044624_wirelessUSB](https://github.com/user-attachments/assets/c472fcc2-b937-4266-ad65-ea2860dff4fc) | ![Screenshot_20260315_044643_wirelessUSB](https://res.cloudinary.com/dzemzfh9o/image/upload/v1773559934/Screenshot_20260315_044643_wirelessUSB_bixr0q.jpg) |

---
## PC view
| ![Screenshot_20260315_044624_wirelessUSB](https://res.cloudinary.com/dzemzfh9o/image/upload/v1773563508/Screenshot_2026-03-15_135713_irrmfh.png)
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
git clone https://github.com/rohityadav-alpha/WirelessUSB.git
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
## Downloads
- `Android`
```brash
https://github.com/rohityadav-alpha/WirelessUSB/releases/download/v1.0/wirelessUSB.apk
```
- `Window`
```brash
https://github.com/rohityadav-alpha/WirelessUSB/releases/download/v1.0/Penless-Setup.exe
```

Latest releases are in the **Releases** section.

- `WirelessUSB.apk` – Android app (HTTP + FTP).
- `Penless-Setup.exe` – Windows app (HTTP).
---
## Important

Both apps do the **same job**: wireless file transfer.  
Install **only one**:

- Use **`WirelessUSB.apk`** if you want everything from your **Android phone**.
- Use **`Penless-Setup.exe`** if you want everything from your **Windows PC**.

> **Note (FTP recommended use):**  
> FTP works best when your **PC is connected to your mobile hotspot**.  
> In this setup, the phone is the hotspot and the PC connects to that hotspot Wi‑Fi.
---
## How to use – Android (`WirelessUSB.apk`)

1. Connect your phone and PC to the **same network**  
   - Best for FTP: make your **phone a hotspot** and connect the PC to that hotspot.
2. Open the Android app → it shows **IP:PORT** values, for example:  
   - HTTP: `http://192.168.1.5:8080`  
   - FTP: `ftp://192.168.1.5:2121`
3. On your PC:
   - **Browser (HTTP):** open Chrome/Edge and paste the `http://IP:PORT` URL to upload/download files via web UI.
   - **File Explorer (FTP):** open File Explorer, type `ftp://IP:PORT` in the address bar, press Enter, and manage files like a normal folder.
---
## How to use – Windows (`Penless-Setup.exe`)

1. Install `Penless-Setup.exe` on your PC and open the app.
2. Make sure your phone is on the **same Wi‑Fi** as your PC.
3. In the Windows app, check the **server address** (example: `http://192.168.1.10:8080`).
4. On your phone, open any browser and enter that `http://IP:PORT` address.
5. Use the web page on your phone to upload and download files from the PC wirelessly.
---
## UPCOMING FEATURES FOR NEW ANDROID VERSION 

<!--StartFragment--><html><head></head><body>
Feature | v1.0 | v2.0
-- | -- | --
FTP Server | Yes | Yes
HTTP Browser Server | No | Yes
Dual QR Code | No | Yes
Connection Log | No | Yes
Copy / Share URL | No | Yes
WiFi Warning | No | Yes
App Crash on Stop | Yes | Fixed

</body></html><!--EndFragment-->
