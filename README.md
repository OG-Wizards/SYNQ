# iTantra (ई-तंत्र) 📡

> **Offline Speech-to-Speech Mesh Communication & Private Voice Calling Network**  
> *Zero Internet. Zero SIM. Zero Cloud Servers. Built for Disaster Relief, Defense Resilience & Remote Operations.*

[![Platform](https://img.shields.io/badge/Platform-Android_11+-3DDC84?logo=android&logoColor=white)](#)
[![Language](https://img.shields.io/badge/Language-Kotlin_100%25-7F52FF?logo=kotlin&logoColor=white)](#)
[![UI](https://img.shields.io/badge/UI-Jetpack_Compose_Material_3-4285F4?logo=jetpackcompose&logoColor=white)](#)
[![Architecture](https://img.shields.io/badge/Architecture-MVVM_+_StateFlow-blue)](#)
[![Networking](https://img.shields.io/badge/Mesh-Wi--Fi_Direct_P2P-35D07F)](#)
[![Translation](https://img.shields.io/badge/ML_Kit-On--Device_Neural-FF6F00)](#)

---

## 🌟 Executive Summary

When natural disasters (cyclones, earthquakes, flash floods) strike or defense operations face comms blackouts, cellular towers and fiber backbones collapse. **iTantra** establishes an immediate, decentralized peer-to-peer wireless network directly between Android smartphones.

First responders and affected citizens can speak naturally into their phones in any of **10 Indian languages**. The speech is recognized, transmitted across the multi-hop Wi-Fi Direct mesh, neurally translated on the receiving device completely on-device, spoken aloud via Text-to-Speech, and displayed in real time. In addition, users can make full-duplex private VoIP voice calls and broadcast high-priority Emergency SOS alerts accompanied by urgent Morse code vibration and audible sirens.

---

## 🚀 Key Features

### 1. 🌐 Zero-Infrastructure Wi-Fi Direct Mesh
- **Hardware P2P Radio**: Connects directly device-to-device using `android.net.wifi.p2p` without routers, mobile data, or towers.
- **Loop-Free Mesh Relaying**: Packet deduplication (`seenPackets`), hop TTL countdowns, and non-reflecting forwarding (`sendExcept`).
- **Autonomous Discovery**: Background peer discovery and automatic group negotiation.

### 2. 🗣️ On-Device Neural Speech-to-Speech Pipeline
- **Real-Time Speech Recognition**: Push-to-talk Android `SpeechRecognizer` streaming partial and final utterances.
- **Automatic Model Downloads**: Google ML Kit translation models download in the background upon language selection and cache permanently for **100% offline execution**.
- **Native Android Text-to-Speech**: Utterance synthesis reading messages aloud in the recipient's native dialect.
- **End-to-End Metric Tracking**: Live latency timestamps measuring speech-to-TTS delivery.

### 3. 🇮🇳 10 Indian Languages & Dynamic Full-UI Localization
Switching languages instantly transforms **both the communication pipeline and every UI element in real time** (bottom tabs, buttons, headers, status banners, alerts, and settings) with zero restart required:
- **मराठी (Marathi)**
- **हिन्दी (Hindi)**
- **ગુજરાતી (Gujarati)**
- **বাংলা (Bengali)**
- **தமிழ் (Tamil)**
- **తెలుగు (Telugu)**
- **ಕನ್ನಡ (Kannada)**
- **മലയാളം (Malayalam)**
- **ਪੰਜਾਬੀ (Punjabi)**
- **English**

### 4. 📞 16kHz PCM Full-Duplex Private VoIP Calling
- Real-time two-way voice streaming over raw TCP/UDP socket channels.
- Full-duplex `AudioRecord` capture and `AudioTrack` playback.
- Call state machine: `IDLE` $\to$ `CALLING` $\to$ `INCOMING` $\to$ `CONNECTED` $\to$ `ENDED` with live animated audio waveforms.

### 5. 🚨 Emergency SOS Distress Broadcast
- High-priority distress signal broadcasting across all mesh hops.
- **Haptic Morse Feedback**: Triggers distinct SOS Morse code vibration (`... --- ...`: 3 short, 3 long, 3 short) on both sender and recipient phones.
- **Emergency Siren Tone**: Sounds an audible emergency alarm using Android's `RingtoneManager` (`AudioAttributes.USAGE_ALARM`).
- One-touch presets: *Cyclone Alert*, *Flash Flood*, *Medical Emergency*, *Comms Blackout*, plus custom distress text.

### 6. 📊 Real-Time Telemetry Dashboard
- Live measurement of Speech-to-Text latency, On-Device Translation latency (ms), TTS latency (ms), and End-to-End latency (s).
- Live hardware readings: App RAM (MB), CPU usage (%), and packet payload sizes.

---

## 🛠️ Technology Stack

| Layer | Technology |
| :--- | :--- |
| **Language** | Kotlin 1.9.x |
| **UI Framework** | Jetpack Compose + Material 3 |
| **State Management** | Android Architecture Components `ViewModel` + Kotlin `StateFlow` |
| **Mesh Transport** | Wi-Fi Direct (`WifiP2pManager`) + Local TCP Sockets (`LocalTransport`) |
| **Audio Engine** | 16 kHz 16-bit Mono PCM (`AudioRecord` & `AudioTrack`) |
| **Speech-to-Text** | Android Speech Recognition Service (`SpeechRecognizer`) |
| **Translation** | Google ML Kit On-Device Translation (`TranslateRemoteModel`) |
| **Text-to-Speech** | Android Native TTS (`TextToSpeech`) |
| **Persistence** | Jetpack Room SQLite Database + Encrypted `SharedPreferences` |

---

## 📁 Repository Structure

```
iTantra/
├── app/
│   ├── src/main/
│   │   ├── AndroidManifest.xml          # Permissions (Audio, Wi-Fi Direct, Vibrate, Queries)
│   │   ├── java/com/itantra/app/
│   │   │   ├── MainActivity.kt          # Compose host activity & permission orchestrator
│   │   │   ├── AppViewModel.kt          # Core engine (STT, TTS, VoIP, SOS, Telemetry)
│   │   │   ├── UiStrings.kt             # 10-language dynamic UI localization system
│   │   │   ├── model/
│   │   │   │   └── models.kt            # Data contracts (Peer, Group, CallState, Packet)
│   │   │   ├── network/
│   │   │   │   ├── WifiDirectManager.kt # Wi-Fi P2P discovery & connection handling
│   │   │   │   └── LocalTransport.kt    # Socket server & multi-peer mesh routing
│   │   │   ├── data/
│   │   │   │   └── GroupStore.kt        # Team & group state persistence
│   │   │   └── ui/
│   │   │       └── AppUi.kt             # Jetpack Compose Material 3 UI system
│   │   └── res/                         # Vector icons, app themes, mipmap resources
│   └── build.gradle.kts                 # App build configuration & dependencies
├── web_showcase/                        # Interactive Web Simulator & Presentation Portal
│   ├── index.html                       # Responsive presentation page + phone simulator
│   ├── style.css                        # Glassmorphic dark styling & animations
│   ├── app.js                           # Web audio synthesizer & simulated mesh engine
│   └── vercel.json                      # Vercel deployment configuration
├── gradle/wrapper/                      # Gradle 8.7 wrapper distribution
├── build.gradle.kts                     # Root build configuration
├── gradle.properties                    # JVM settings (Pinned to JDK 17)
├── settings.gradle.kts                  # Project modules
└── README.md                            # Complete documentation
```

---

## 📦 Building & Installing the APK

### Prerequisites
- **Android Studio** (Koala / Ladybug or newer recommended)
- **JDK 17** (Ensure `JAVA_HOME` points to Java 17)
- **Android SDK Platform 35**

### 1. Build Debug APK via Command Line
```powershell
# In project root directory (D:\iTantra)
.\gradlew assembleDebug
```
The compiled APK will be generated at:
```
app/build/outputs/apk/debug/app-debug.apk
```

### 2. Install on Device via ADB
Connect your Android device with USB Debugging enabled:
```powershell
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

---

## 🌐 Interactive Presentation Showcase (Vercel)

The `web_showcase/` directory contains an interactive smartphone simulator designed for presentations and pitch decks. It allows judges and attendees to test the 10-language switching, push-to-talk audio, VoIP calling, and SOS alarms directly in any web browser without an Android device.

To run locally:
```powershell
cd web_showcase
npx serve .
```

---

## 📜 Mesh Packet Protocol

All packets transmitted over the Wi-Fi Direct socket network use structured UTF-8 JSON payloads:

```json
{
  "id": "urn:uuid:6b8b0e8c-8f2c-49b8-a40c-d491f24d7759",
  "sender": "192.168.49.1",
  "type": "SOS",
  "text": "Cyclone Alert: Evacuate to high ground immediately!",
  "sourceLanguage": "en",
  "target": "",
  "group": "",
  "ttl": 5,
  "timestamp": 1774263000000
}
```

- **Loop Prevention**: Each node caches `packet.id` in a concurrent set (`seenPackets`). Duplicate packets are dropped immediately.
- **TTL Decrement**: Each relay decrements `ttl` by 1. Packets with `ttl <= 1` are not forwarded.
- **Non-Reflecting Routing**: Relaying uses `transport.sendExcept(payload, senderHost)` to prevent echoing packets back to the node that sent them.

---

## 📄 License & Attribution
Developed for humanitarian, disaster management, and decentralized emergency resilience operations.
