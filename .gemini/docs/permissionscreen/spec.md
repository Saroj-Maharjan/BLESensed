# Specification: Permission Screen

## Goals
- Provide a clear, user-friendly UI explaining why Bluetooth and Location permissions/services are required.
- Dynamically display the real-time status of each permission and system service.
- Provide direct action buttons to request permissions.
- Automatically navigate to the Login screen once all conditions are met.
- Reactively monitor permission/service changes across the app, redirecting back to this screen if any are revoked or disabled.

## Non-Goals
- Handling actual BLE scanning or business logic (this is owned by `bledevicelist`).
- Handling user authentication (this is owned by `login`).

## Background & Rationale
UGZeroHarm is a safety beacon scanning app. It cannot perform its core function without Bluetooth scanning and Location services (required by Android/iOS to scan for BLE beacons). Gating the application on startup and monitoring state changes at runtime ensures the app never runs in a broken state.

## Design Decisions
| Decision | Choice | Rationale |
|----------|--------|-----------|
| Gating Mech | Navigation Gate + Start Destination | Setting `PermissionRoute` as start destination and reactively checking permissions globally allows seamless redirect and prevents bypass. |
| Service States | Bluetooth + Location Enabled | Gating on both runtime permissions AND active hardware services ensures we don't proceed to Login if scanning will fail. |

## Sourcing & Platform Sourcing (Rule 14)
- Uses the existing `:core:permission` module's `PermissionManager` to check permission statuses and enabled flows.
- Defines an expect/actual `handlePermissions` contract to request runtime permissions on Android and stub them on other platforms.
- Defines an expect/actual `rememberSettingsLauncher` contract to open app details settings on Android and system settings on iOS.

## Technical Architecture & Implementation
The permissionscreen feature is implemented under the package `com.anchor.ugzeroharm.permissionscreen`.

### Component Directory Structure
```
feature/permissionscreen/src/commonMain/
├── kotlin/com/anchor/ugzeroharm/permissionscreen/
│   ├── di/
│   │   └── PermissionScreenModule.kt
│   ├── presentation/
│   │   ├── navigation/
│   │   │   └── PermissionRoute.kt
│   │   ├── viewmodel/
│   │   │   └── PermissionScreenViewModel.kt
│   │   └── ui/
│   │       ├── PermissionScreenUiModel.kt
│   │       ├── PermissionScreen.kt
│   │       ├── PermissionsHandler.kt (expect)
│   │       └── components/
│   │           └── PermissionStatusCard.kt
```

### Key Classes
- **`PermissionScreenUiModel`**: Holds state representing the status of location and bluetooth permissions and services.
- **`PermissionScreenViewModel`**: Collects Bluetooth and Location service active flows from `PermissionManager`, refreshes states on resume/init, and exposes the combined `PermissionScreenUiModel`.
- **`PermissionScreen` & `PermissionScreenRoot`**: Wraps the screen with the ViewModel state, auto-navigates to the Login screen once all states are active, and renders the status cards in a scrollable column.
- **`PermissionsHandler` (expect/actual)**: Implements native permission requesting and settings intent launching.

### Integration
- **Gradle**: Registered in `settings.gradle.kts` and included in `composeApp/build.gradle.kts`.
- **Koin**: `permissionScreenModule` included in `initKoin.kt`.
- **Navigation & Gating**: `BaseAppNavHost.kt` sets `PermissionRoute` as the start destination. It observes the reactive permission state and lifecycle ON_RESUME events, auto-redirecting to `PermissionRoute` and clearing the backstack if any required permission or service becomes missing or disabled.
