## WearOSLavender
WearOSLavender is a WearOS application developed as part of KidSafe, a comprehensive child safety and tracking solution. Designed for WearOS smartwatches, it empowers parents and guardians to monitor children's safety through real-time location tracking, geofencing, and emergency alerts. The app leverages WearOS capabilities to provide a lightweight, user-friendly, and extensible platform for child safety.

Table of Contents

Project Overview
Features
Technical Details
Project Structure
Setup & Installation
Usage
Workflow Diagram
Contributing
License


Project Overview
WearOSLavender is a WearOS application designed to ensure child safety through seamless integration with WearOS smartwatch hardware. It provides real-time monitoring, emergency notifications, and geofencing capabilities to keep parents informed about their child's location and safety. The app is built using Kotlin and follows modern Android development practices, making it modular and extensible for future enhancements, such as integration with parent mobile apps or cloud-based services.
The application is tailored for WearOS devices, utilizing GPS, sensors, and WearOS-specific APIs to deliver a responsive and efficient user experience. It is part of the broader KidSafe ecosystem, focusing on child safety and parental peace of mind.

Features

Real-Time Location Tracking: Uses GPS and network-based location services to provide accurate, real-time location updates of the child’s WearOS device.
Geofencing: Allows guardians to set safe zones and receive alerts if the child enters or exits predefined areas.
SOS/Emergency Notifications: Enables children to send instant SOS alerts to guardians with a single tap, including their current location.
Battery Optimization: Designed to minimize battery consumption on WearOS devices while maintaining reliable performance.
WearOS-Optimized UI: Provides a simple, intuitive interface tailored for small smartwatch screens.
Modular Architecture: Easily extensible for additional features, such as integration with cloud services or parent companion apps.
Push Notifications: Sends alerts to guardians via WearOS notifications or through integration with external services.


Technical Details

Platform: WearOS (Android-based smartwatch OS)
Language: Kotlin
Minimum SDK: WearOS 2.0 (API Level 28)
Dependencies:
AndroidX libraries for WearOS compatibility
Google Play Services for location and geofencing
Kotlin Coroutines for asynchronous operations
Gradle (Kotlin DSL) for build management


Key APIs:
Wearable API for WearOS-specific functionality
Fused Location Provider for accurate location tracking
Android Notifications for alerts


Development Tools:
Android Studio
Gradle 8.0+
Kotlin 1.9+




Project Structure
The project follows a standard Android project structure, optimized for WearOS development:
WearOSLavender/
├── app/                              # Main application module
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/wearoslavender/
│   │   │   │   ├── MainActivity.kt        # Entry point activity for the app
│   │   │   │   ├── LocationService.kt     # Background service for location tracking
│   │   │   │   ├── GeofenceManager.kt     # Handles geofencing logic
│   │   │   │   ├── SosAlert.kt           # Manages SOS notifications
│   │   │   │   └── models/               # Data models for location and alerts
│   │   │   ├── res/
│   │   │   │   ├── layout/               # XML layouts for WearOS UI
│   │   │   │   ├── drawable/             # Icons and image assets
│   │   │   │   ├── values/               # Strings, colors, and themes
│   │   │   │   └── wear.xml              # WearOS-specific configurations
│   │   │   └── AndroidManifest.xml       # App permissions and configuration
│   ├── build.gradle.kts                  # Module-level build script
├── gradle/                               # Gradle wrapper files
├── .idea/                                # Android Studio IDE configuration
├── build.gradle.kts                      # Project-level build script
├── gradle.properties                     # Gradle properties
├── gradlew / gradlew.bat                 # Gradle wrapper scripts
└── settings.gradle.kts                   # Gradle project settings

Key Files

MainActivity.kt: The main entry point, displaying the dashboard with location, SOS, and settings options.
LocationService.kt: A foreground service that continuously tracks the child’s location using Fused Location Provider.
GeofenceManager.kt: Manages geofencing logic, including setting up safe zones and triggering alerts.
SosAlert.kt: Handles SOS functionality, sending emergency notifications with location data.
AndroidManifest.xml: Declares permissions (e.g., ACCESS_FINE_LOCATION, FOREGROUND_SERVICE) and WearOS-specific configurations.


Setup & Installation
Prerequisites

Android Studio (latest stable version)
WearOS device or emulator (API Level 28+)
Google Play Services enabled
Gradle 8.0 or higher

Steps

Clone the Repository:
git clone https://github.com/Rohannn11/WearOSLavender.git


Open in Android Studio:

Launch Android Studio and select Open an existing project.
Navigate to the cloned WearOSLavender directory.


Sync Gradle:

Click Sync Project with Gradle Files in Android Studio.
Ensure all dependencies are downloaded.


Configure WearOS Device:

Connect a WearOS smartwatch via USB or use a WearOS emulator.
Enable Developer Options and USB Debugging on the device.


Build and Run:

Select the WearOS device/emulator as the target.
Click Run to build and deploy the app.


Permissions:

Grant location and notification permissions when prompted on the WearOS device.




Usage

Launch the App:

Open WearOSLavender on the WearOS smartwatch.
The main dashboard displays the current location, geofence status, and an SOS button.


Set Up Geofencing:

Configure safe zones via the companion app (if integrated) or directly on the device.
Receive alerts when the child enters or exits a geofenced area.


Send SOS Alerts:

Press the SOS button to instantly notify guardians with the child’s current location.


Monitor Location:

View real-time location updates on the dashboard or through the parent app (if integrated).


Extend Functionality:

Add new modules in app/src/main/java/com/example/wearoslavender/.
Update UI layouts in app/src/main/res/layout/.




Workflow Diagram
Below is a high-level workflow of how WearOSLavender operates:
graph TD
    A[Child Wears Smartwatch] --> B[App Launches]
    B --> C[LocationService Starts]
    C --> D{Fused Location Provider}
    D --> E[Real-Time Location Updates]
    E --> F[GeofenceManager]
    F -->|Enter/Exit Geofence| G[Send Notification to Guardian]
    B --> H[SOS Button Pressed]
    H --> I[SosAlert]
    I --> J[Send Emergency Notification with Location]
    G --> K[Guardian Receives Alert]
    J --> K


Contributing
Contributions are welcome! To contribute to WearOSLavender:

Fork the repository.
Create a new branch (git checkout -b feature/your-feature).
Make your changes and commit (git commit -m "Add your feature").
Push to the branch (git push origin feature/your-feature).
Open a pull request with a detailed description of your changes.

Please ensure your code follows:

Kotlin coding conventions
Proper documentation
Unit tests for new features (if applicable)


License
This project is licensed under the MIT License:
MIT License

Copyright (c) 2025 Rohannn11

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
