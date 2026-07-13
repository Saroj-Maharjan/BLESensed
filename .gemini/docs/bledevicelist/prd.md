# PRD: BLE Device List Screen (`bledevicelist`)

## Overview
A screen for the UGZeroHarm app that scans for Bluetooth Low Energy (BLE) devices in the vicinity, parses advertisement packets (including RSSI, connectability status, advertised service UUIDs, and manufacturer-specific data), and presents this information to the user in a modern, Material 3 styled list. The screen supports Scanning, Scan Stopped, Success (discovered devices list), Loading, Failed, and Empty states.

## Goals
- Provide a robust BLE device scanning mechanism using the `blue-falcon` library.
- Parse advertisement data (device name, MAC address, signal strength/RSSI, connectability, service UUIDs, and manufacturer data bytes) and display it clearly.
- Create a user interface matching the Material 3 design system colors (#6B5000 primary, #FFFBF0 background, #FFFDF6 surface) and rounded styling (12dp card corners).
- Enable the user to start and stop scans dynamically from the UI.
- Support all state coverage: Success, Loading, Failed, and Empty states.

## Non-Goals
- Connecting to/communicating with BLE devices (this feature is strictly for scanning and listing; connection flows and peripheral detail read/writes are out of scope).
- Background BLE scanning when the app is minimized or killed.
- Exporting or saving discovered device list logs to external storage.

## Background & Rationale
UGZeroHarm is a safety and caution utility app. Discovered hardware safety beacons or zero-harm sensors emit BLE advertisements. Users need a reliable, high-density dashboard to scan for and see nearby active safety sensors, verify their signal strength, check battery or device info services, and inspect manufacturer payloads without needing to establish a connection.

## Platform Profile & Capabilities
| Field | Value |
|-------|-------|
| Platform Profile | `platform-capability` |
| Capabilities | `BLE` (Bluetooth Low Energy scanning) |
| Native view | No (Uses pure Compose UI) |
| Sourcing option | `1 multiplatform lib` (`dev.bluefalcon:blue-falcon-core:3.4.5`) |
| iOS-Swift bridge needed | No |

## Design Decisions
| Decision | Choice | Alternatives Considered | Rationale |
|----------|--------|------------------------|-----------|
| **BLE Library** | `Blue Falcon` | `Kable`, Native CoreBluetooth/Android BLE wrapper | User requirement to use blue-falcon. |
| **DI Setup** | `expect/actual val platformModule` | Manual instantiations | Required because BlueFalcon requires an Android Context on Android and no Context on iOS. |
| **State Management** | Single ViewModel with a single state flow of `BleDeviceListUiModel` | Multiple state flows | Adheres to patterns.md Rule 11 (Unified State Flow). |

## Feature Scope
- **Core Functionality**:
  - Scan for BLE peripherals using Blue Falcon.
  - Parse `didDiscoverDevice` delegate payloads: device name, MAC/UUID, RSSI, connectable flag, advertised service UUIDs, and manufacturer data bytes.
  - Expose scanning controls (Start / Stop) to the UI.
- **User Interactions**:
  - Tap "Stop Scan" to stop BLE scanning.
  - Tap "Start Scan" to resume BLE scanning.
  - View individual device cards with high-density advertisements.
- **Data Operations**:
  - Flow-based repository emitting list of scanned devices.
  - Automatic filtering and updates of device lists based on unique MAC address.
  - Conversion of manufacturer data byte arrays to structured hexadecimal strings.

## Requirements Analysis

### User Requirements
- The user can see a list of active BLE devices in range.
- The user can identify device names, signal strength (RSSI), connectability, service UUIDs, and manufacturer payloads.
- The user can see active progress (spinner) when scanning.
- The user can stop and start the scan manually.
- The UI should fall back gracefully if no devices are found or scanning fails.

### Technical Requirements
- Integration with the `dev.bluefalcon:blue-falcon-core` KMP library.
- Android and iOS platform-specific dependencies for Blue Falcon engine.
- Android Bluetooth permissions (`BLUETOOTH_SCAN`, `BLUETOOTH_ADVERTISE`, `BLUETOOTH_CONNECT`, `ACCESS_FINE_LOCATION`) and iOS Bluetooth permissions (`NSBluetoothAlwaysUsageDescription`).
- State management for scanning state and list of devices in `BleDeviceListViewModel`.
- Integration into the app's Koin dependency injection module and `BaseAppNavHost` navigation routing.
- Handoff from `WelcomeRoute` (replacing it as the starting route of the app).

## Data Architecture

### Data Models

#### BleScannedDevice
```kotlin
data class BleScannedDevice(
    val name: String,
    val macAddress: String,
    val rssi: Int?,
    val isConnectable: Boolean,
    val advertisedServices: List<String>,
    val manufacturerData: String?
)
```

### Data Flow
```
BlueFalcon (Native Bluetooth) -> BleScanRepositoryImpl -> BleDeviceListViewModel -> BleDeviceListUiModel -> BLE Devices UI
                                  (StateFlow<List<BleScannedDevice>>)
```

## UI Design
The UI is fully styled according to the Stitch Design Blueprint:
- **Background**: `#FFFBF0`
- **Primary Accent**: `#6B5000`
- **Card Surface**: `#FFFDF6`
- **Muted text**: `#4E4730`
- **Typography**: Manrope for headers and body text; Monospace for MAC addresses and hex byte arrays.

### Screens
- **BleDeviceListScreen**: The primary dashboard showing:
  - Top app bar: "BLE Devices" title with settings icon.
  - Sticky scan status banner: scan state indicator and Stop/Start button.
  - Scrollable device card list (Success state) or a centered search_off icon with "No devices found" text (Empty state).
  - Common shared Loading and Failed screens.

### Navigation
- Route: `BleDeviceListRoute` (instantiated as object / type-safe route)
- Navigation integration: Pushed as the app's start destination.

## Technical Architecture

### Module
- Path: `feature/bledevicelist/`
- Namespace: `com.anchor.ugzeroharm.bledevicelist`

### Layers
- **Data Layer**:
  - `BleScanRepository` interface & implementation.
  - `PermissionDataSource` interface & platform implementations (Android permission checks, iOS CoreBluetooth authorization checks).
  - Handles starting/stopping Blue Falcon scan and mapping discovered devices to `BleScannedDevice`.
- **Presentation Layer**:
  - `BleDeviceListViewModel`: manages scan triggers, permissions orchestration, and state flow.
  - `BleDeviceListUiModel`: holds unified UI state (loading/failed/success status, list of devices, scan status, permission status).
  - `BleDeviceListScreen` and `BleDeviceListContent`: Compose layouts for success, empty, and permissions-blocked states.
- **DI Layer**:
  - `bleDeviceListModule`: wires ViewModel, repository, and platform-specific `platformModule` (for Blue Falcon and PermissionDataSource initialization).

### Dependencies
- `:core:common`
- `:core:designsystem`

## Implementation Plan
- **Complexity**: Complex (Platform capability / scanning states / permissions)
- **Estimated Tasks**: 10
- **Groups**: Platform (5) / UI (3) / Integration (2)

### Task Groups
- **Group 1: Platform & Data Layer** (`platform-agent`)
  - Module setup (settings.gradle.kts, feature/bledevicelist/build.gradle.kts)
  - Define `BleScannedDevice` DTO and `BleScanRepository` / `PermissionDataSource` interfaces.
  - Implement `expect/actual val platformModule` providing `BlueFalcon` and `PermissionDataSource`.
  - Configure AndroidManifest.xml and Info.plist permissions.
  - Implement `BleScanRepositoryImpl` on Android, iOS, and Desktop (fallback).
- **Group 2: UI Layer** (`ui-layer-agent`)
  - Create `BleDeviceListUiModel` (unified state with `UiState<List<BleScannedDevice>>`, scanning flag, and permission status).
  - Implement `BleDeviceListViewModel` managing scan lifecycle and permission responses.
  - Implement `BleDeviceListScreen` and `BleDeviceListContent` (success list, empty list, and permission request layouts) with Preview composables.
- **Group 3: Integration & Handoff** (`integration-agent`)
  - Register Koin dependencies (`bleDeviceListModule` containing `platformModule`).
  - Add navigation route in `BaseAppNavHost` and replace `WelcomeRoute` as the starting route (Welcome handoff).
  - Write `spec.md` and verify successful builds.

## Acceptance Criteria

### Test Scenarios
- **Scan Success**: Navigating to the screen starts BLE scan, progress spinner rotates, discovered devices are listed with correct RSSI, services, and hex data.
- **Empty State**: Scan is active but no devices are found; center displays "No devices found" with search_off icon.
- **Toggle Scan**: Tapping "Stop Scan" halts scanning, banner transitions to "Scan Stopped", progress spinner disappears, and "Start Scan" button is shown. Tapping "Start Scan" resumes active scanning.
- **Fallback Platforms**: On Desktop target, scanning immediately transitions to a mock empty state or failed state showing "BLE scanning unavailable on this platform".

### Technical Verification
- [ ] Build passes: `./gradlew assembleDebug`
- [ ] Bluetooth permissions configured on Android (`AndroidManifest.xml`) and iOS (`Info.plist`).
- [ ] Blue Falcon engine dependency declared for Android (`androidMain`) and iOS (`iosMain`).
- [ ] Desktop actual compiles and returns a graceful failure (Either.Failure) or empty flow.
- [ ] State management uses single `BleDeviceListUiModel` and updates via `setState {}`.
- [ ] Code formatted: `./gradlew ktlintFormat`

## Integration Points
| File | Change Type | Description |
|------|-------------|-------------|
| `settings.gradle.kts` | MODIFIED | Add `include(":feature:bledevicelist")` |
| `composeApp/build.gradle.kts` | MODIFIED | Add `implementation(project(":feature:bledevicelist"))` |
| `initKoin.kt` | MODIFIED | Add `bleDeviceListModule` to `modules(...)` |
| `BaseAppNavHost.kt` | MODIFIED | Replace `WelcomeRoute` with `BleDeviceListRoute` as start destination |
