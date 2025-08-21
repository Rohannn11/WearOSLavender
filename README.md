
# WearOSLavender

**WearOSLavender** is a comprehensive Android WearOS application designed for **KidSafe**, a child safety and tracking solution. This smartwatch application provides parents and guardians with a reliable, feature-rich tool to monitor and ensure the safety of children through real-time tracking, emergency features, and health monitoring capabilities.

---

## Table of Contents

1. [Project Overview](#project-overview)
2. [Key Features](#key-features)
3. [Architecture & Components](#architecture--components)
4. [Project Structure](#project-structure)
5. [Setup & Installation](#setup--installation)
6. [Usage Guide](#usage-guide)
7. [Technical Implementation](#technical-implementation)
8. [Development](#development)
9. [Contributing](#contributing)
10. [License](#license)

---

## Project Overview

WearOSLavender is a sophisticated child safety application that runs on WearOS smartwatches, leveraging Android's capabilities to provide comprehensive monitoring and safety features. The application combines real-time location tracking, health monitoring, emergency response systems, and intuitive user interfaces to create a complete child safety ecosystem.

### Target Audience
- Parents and guardians monitoring children''s safety
- Educational institutions requiring student tracking
- Healthcare providers monitoring pediatric patients
- Emergency services for child safety protocols

---

## Key Features

### **Safety & Emergency**
- **SOS Emergency Button**: Instant emergency alerts with panic cancellation
- **Speed Tracking**: Monitor movement speed with customizable alerts
- **Panic Mode**: Emergency state management with automated notifications
- **Emergency Contact Management**: Quick access to emergency contacts

### **Location & Movement**
- **Real-time GPS Tracking**: Continuous location monitoring
- **Geofencing**: Set safe zones with entry/exit notifications
- **Location History**: Track movement patterns and visited locations
- **Speed Monitoring**: Alert when speed thresholds are exceeded

### **Health Monitoring**
- **Heart Rate Tracking**: Continuous heart rate monitoring
- **Health Alerts**: Automated alerts for abnormal health readings
- **Health Data Logging**: Historical health data storage and analysis

### **Communication & Connectivity**
- **Contact Management**: Manage emergency and regular contacts
- **Notification System**: Push notifications to parent devices
- **Cloud Integration**: Secure data synchronization

### **User Experience**
- **Intuitive WearOS Interface**: Child-friendly UI design
- **Theme Customization**: Personalized interface themes
- **Activity-based Navigation**: Easy navigation between features

---

## Architecture & Components

### Core Components

#### **Activities**
- **`MainActivity.kt`**: Main application entry point and dashboard
- **`ContactsScreen.kt`**: Contact management interface
- **`LocationScreen.kt`**: Location tracking and map display
- **`WearAppScreen.kt`**: Main WearOS interface controller

#### **Models & Data Management**
- **`ContactsViewModel.kt`**: Contact data management and operations
- **`ContactsViewModelFactory.kt`**: ViewModel factory for dependency injection
- **`LocationViewModel.kt`**: Location data and tracking logic
- **`HeartRateViewModel.kt`**: Health monitoring data management
- **`HeartRateViewModelFactory.kt`**: Health data ViewModel factory
- **`SpeedTrackingViewModel.kt`**: Speed monitoring and alert management

#### **Safety & Emergency Features**
- **`EmergencyContactManager.kt`**: Emergency contact handling
- **`PanicModeButton.kt`**: Emergency SOS functionality
- **`PanicModeViewModel.kt`**: Panic state management
- **`SOSButton.kt`**: Emergency alert system
- **`SOSViewModel.kt`**: SOS feature logic and coordination

### Design Patterns
- **MVVM Architecture**: Model-View-ViewModel pattern for separation of concerns
- **Factory Pattern**: ViewModel factories for dependency management
- **Observer Pattern**: Real-time data updates and notifications
- **Repository Pattern**: Data management abstraction

---

## Project Structure

```
WearOSLavender/

├── app/                                    # Main application module
│   ├── src/main/java/com/yourpackage/      # Kotlin source files
│   │   ├── MainActivity.kt                 # Main app entry point
│   │   ├── ContactsScreen.kt               # Contact management UI
│   │   ├── ContactsViewModel.kt            # Contact data logic
│   │   ├── ContactsViewModelFactory.kt     # Contact ViewModel factory
│   │   ├── EmergencyContactManager.kt      # Emergency contact handling
│   │   ├── HeartRateViewModel.kt           # Heart rate monitoring
│   │   ├── HeartRateViewModelFactory.kt    # Heart rate ViewModel factory
│   │   ├── LocationScreen.kt               # Location tracking UI
│   │   ├── LocationViewModel.kt            # Location data management
│   │   ├── PanicModeButton.kt             # Emergency SOS button
│   │   ├── PanicModeViewModel.kt          # Panic mode logic
│   │   ├── SOSButton.kt                   # SOS functionality
│   │   ├── SOSViewModel.kt                # SOS feature coordination
│   │   ├── SpeedTrackingViewModel.kt      # Speed monitoring
│   │   └── WearAppScreen.kt               # Main WearOS interface
│   │
│   ├── src/main/res/                      # UI resources
│   │   ├── layout/                        # XML layout files
│   │   ├── drawable/                      # Graphics and icons
│   │   ├── values/                        # Colors, strings, dimensions
│   │   └── mipmap/                        # App icons
│   │
│   └── AndroidManifest.xml               # App configuration and permissions
│
├── gradle/                               # Gradle wrapper files
├── .idea/                               # Android Studio configuration
├── build.gradle.kts                    # Kotlin-based build script
├── gradle.properties                   # Gradle properties
├── gradlew / gradlew.bat              # Gradle wrapper scripts
├── settings.gradle.kts                # Project settings
└── README.md                          # Project documentation
```

---

## Setup & Installation

### Prerequisites
- **Android Studio**: Latest version with WearOS support
- **WearOS SDK**: API level 28+ recommended
- **Kotlin**: Version 1.8+
- **WearOS Device/Emulator**: For testing

### Installation Steps

1. **Clone the Repository**
   ```bash
   git clone https://github.com/Rohannn11/WearOSLavender.git
   cd WearOSLavender
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned directory

3. **Configure Dependencies**
   ```bash
   ./gradlew build
   ```

4. **Set up WearOS Device**
   - Enable Developer Options on WearOS device
   - Enable ADB debugging
   - Pair device with development machine

5. **Deploy Application**
   ```bash
   ./gradlew installDebug
   ```

### Required Permissions
```xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.BODY_SENSORS" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.SEND_SMS" />
<uses-permission android:name="android.permission.CALL_PHONE" />
```

---

## Usage Guide

### Initial Setup
1. **Launch Application**: Open WearOSLavender on your WearOS device
2. **Grant Permissions**: Allow location, sensor, and communication permissions
3. **Configure Contacts**: Add emergency and regular contacts
4. **Set Preferences**: Configure tracking intervals and alert thresholds

### Core Functions

#### **Location Tracking**
- Access through main dashboard
- View real-time location on map
- Set geofencing boundaries
- Monitor movement history

#### **Emergency Features**
- **SOS Button**: Press and hold for emergency alert
- **Panic Mode**: Automatic emergency state activation
- **Speed Alerts**: Configurable speed threshold notifications

#### **Health Monitoring**
- Continuous heart rate tracking
- Health data visualization
- Abnormal reading alerts

#### **Contact Management**
- Add/remove emergency contacts
- Update contact information
- Test emergency notifications

---

## Technical Implementation

### Key Technologies
- **Kotlin**: Primary programming language
- **Android Jetpack Compose**: Modern UI toolkit
- **WearOS API**: Wear-specific functionality
- **Location Services**: GPS and network location
- **Sensor Framework**: Heart rate and movement sensors
- **Firebase**: Cloud backend integration
- **Room Database**: Local data persistence

### Performance Optimizations
- **Battery Efficiency**: Optimized location updates
- **Memory Management**: Efficient ViewModel lifecycle
- **Background Processing**: Smart service management
- **Data Sync**: Intelligent cloud synchronization

### Security Features
- **Data Encryption**: End-to-end encrypted communications
- **Secure Storage**: Encrypted local data storage
- **Privacy Controls**: User consent management
- **Access Controls**: Permission-based feature access

---

## Development

### Building from Source
```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease

# Run tests
./gradlew test

# Generate APK
./gradlew build
```

### Code Style
- Follow Kotlin coding conventions
- Use meaningful variable and function names
- Implement proper error handling
- Add comprehensive documentation

### Testing
- Unit tests for ViewModels and business logic
- Integration tests for component interactions
- UI tests for user interface validation
- Device testing on multiple WearOS versions

---

## Contributing

We welcome contributions to WearOSLavender! Please follow these guidelines:

### Getting Started
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

### Development Guidelines
- Follow existing code patterns
- Write comprehensive tests
- Update documentation
- Ensure backward compatibility

### Issue Reporting
- Use GitHub Issues for bug reports
- Provide detailed reproduction steps
- Include device and OS version information
- Attach relevant logs when possible

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

```
MIT License

Copyright (c) 2024 WearOSLavender

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

---

## Support

For questions, issues, or support:
- 4E7 Email: [support@wearoslavender.com]
- 41B Issues: [GitHub Issues](https://github.com/Rohannn11/WearOSLavender/issues)
- 4D6 Documentation: [Wiki](https://github.com/Rohannn11/WearOSLavender/wiki)

---

**Built with ❤️ for child safety and peace of mind**
