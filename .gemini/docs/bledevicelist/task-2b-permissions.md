# Task 2b: Expect/Actual PermissionDataSource & Manifest Setup

**Status:** ⏳ Pending
**Group:** Data
**Priority:** High
**Agent:** platform-agent

## Objective
Implement `PermissionDataSource` with per-platform checks and configure AndroidManifest/InfoPlist requirements.

## Implementation Details
1. Create `PermissionDataSource` interface in `commonMain`:
   ```kotlin
   interface PermissionDataSource {
       fun hasBluetoothPermission(): Boolean
   }
   ```
2. Implement `PermissionDataSourceAndroid` in `androidMain` checking `Manifest.permission.BLUETOOTH_SCAN` and `BLUETOOTH_CONNECT` (API 31+) or `ACCESS_FINE_LOCATION` (API <31).
3. Implement `PermissionDataSourceIos` in `iosMain` checking `CBCentralManager.authorization` (returning true if authorized).
4. Implement `PermissionDataSourceDesktop` in `desktopMain` returning `true`.
5. Modify `androidApp/src/main/AndroidManifest.xml` to declare the Bluetooth and Location permissions:
   - `android.permission.BLUETOOTH`, `android.permission.BLUETOOTH_ADMIN`, `android.permission.BLUETOOTH_SCAN` (with `usesPermissionFlags="neverForLocation"`), `android.permission.BLUETOOTH_CONNECT`, `android.permission.ACCESS_FINE_LOCATION`, `android.permission.ACCESS_COARSE_LOCATION`.
6. Modify `iosApp/iosApp/Info.plist` to add `NSBluetoothAlwaysUsageDescription` key with a cautious description.

## Files Created/Modified

### ADDED Files
- `feature/bledevicelist/src/commonMain/kotlin/com/anchor/ugzeroharm/bledevicelist/data/datasource/PermissionDataSource.kt` - Common interface.
- `feature/bledevicelist/src/androidMain/kotlin/com/anchor/ugzeroharm/bledevicelist/data/datasource/PermissionDataSourceAndroid.kt` - Android check.
- `feature/bledevicelist/src/iosMain/kotlin/com/anchor/ugzeroharm/bledevicelist/data/datasource/PermissionDataSourceIos.kt` - iOS check.
- `feature/bledevicelist/src/desktopMain/kotlin/com/anchor/ugzeroharm/bledevicelist/data/datasource/PermissionDataSourceDesktop.kt` - Desktop fallback.

### MODIFIED Files
- `androidApp/src/main/AndroidManifest.xml` - Android permissions declaration.
- `iosApp/iosApp/Info.plist` - iOS Bluetooth usage description key.

## Code Reference
- [architecture/platform.md](../../../skills/creating-kmp-feature/architecture/platform.md)

## Acceptance Criteria
*(Platform Permission check: no behavior scenarios required)*

### Verification Checklist
- [ ] Interface compiles on all platforms.
- [ ] Android implementation uses checkSelfPermission.
- [ ] iOS implementation uses central manager authorization check.
- [ ] Info.plist and AndroidManifest are updated correctly.
