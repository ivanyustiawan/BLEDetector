# BLEDetector

A native Android application for detecting nearby Bluetooth Low Energy (BLE) devices in real time. 
The application scans BLE advertising signals and displays device information, including device name,
MAC address, signal strength (RSSI), first detection time, last detection time, and detection count.

## Features

- **Real-time BLE Scanning** — Detect nearby BLE devices through Bluetooth Low Energy advertising.
- **Device Information** — Display device name, MAC address, RSSI, and detection timestamps.
- **Search and Filter** — Search detected devices by name.
- **Detection Tracking** — Track when a device was first and last detected and how many times it was detected.
- **Local Persistence** — Store detected device information locally using Room.
- **Permission Handling** — Handle Bluetooth permissions and Bluetooth adapter availability.
- **Lifecycle Awareness** — Manage scanning according to the application's lifecycle.

## Tech Stack

1. Kotlin 
2. Android SDK
3. Jetpack Compose
4. Material 3 
5. Bluetooth Low Energy API 
6. Room 
7. Kotlin Coroutines 
8. Flow / StateFlow 
9. ViewModel 
10. Repository Pattern 
11. Gradle 
12. MVVM

## Architecture

The project follows a modular architecture that separates presentation, business logic, core utilities, and data persistence. It uses a combination of MVVM, the Repository Pattern, and dependency inversion to improve maintainability, testability, and separation of concerns.

### Module Structure

```text
BLEDetector/
├── app/
│   ├── screen/
│   ├── activity/
│   └── viewmodel/
│
├── core/
│   ├── helper/
│   ├── database/
│   └── bluetooth/
│       └── BluetoothController
│
├── domain/
│   ├── model/
│   ├── BluetoothController (interface)
│   └── HistoryRepository (interface)
│
└── data/
    └── HistoryRepositoryImpl
```

### Module Responsibilities

#### 1. App Module

The `app` module handles the presentation layer and application entry point.

- **Screen:** Displays the BLE device list, search functionality, and device information using Jetpack Compose.
- **Activity:** Provides the Android application entry point and hosts the Compose UI.
- **ViewModel:** Manages UI state, processes user interactions, and coordinates operations through domain interfaces.

**Why:** Keeps UI-related logic separate from Bluetooth implementation and data persistence.

#### 2. Core Module

The `core` module contains reusable infrastructure components and platform-specific utilities.

- **Helper:** Provides shared utility functions.
- **Database:** Configures Room database components, entities, and DAOs where applicable.
- **Bluetooth Controller:** Implements BLE scanning and interacts with Android Bluetooth APIs.

**Why:** Centralizes reusable technical components and isolates platform-specific functionality from the presentation layer.

#### 3. Domain Module

The `domain` module contains business models and abstractions required by the application.

- **Model:** Defines application-level models, such as BLE device and detection history.
- **Bluetooth Controller Interface:** Defines the contract for Bluetooth scanning operations.
- **History Repository Interface:** Defines operations for retrieving and storing detection history.

**Why:** Keeps business rules independent of implementation details, allowing data sources and infrastructure components to be replaced or tested independently.

#### 4. Data Module

The `data` module implements the data-access contracts defined in the domain module.

- **HistoryRepositoryImpl:** Implements the history repository interface and coordinates history data operations, such as saving and retrieving records from the local database.

**Why:** Separates data-access implementation from business logic and allows the repository to change its underlying storage mechanism without requiring changes to the UI.

## Known Issues and Limitations

Unexpected Bluetooth Deactivation: The application does not yet fully handle scenarios where Bluetooth is suddenly disabled while scanning is in progress. Scanning state recovery and user feedback need further improvement.
Second Screen Not Implemented: The second screen has not yet been implemented, so the application flow and functionality related to that screen are incomplete.

## Development Constraints

Limited Development Time: Due to time constraints, not all planned features and edge cases could be completed within the development period.
Incomplete Edge-Case Handling: Handling unexpected Bluetooth deactivation during an active scan requires further implementation and testing.
Incomplete UI Flow: The second screen remains unimplemented and requires additional development to complete the intended user experience.

