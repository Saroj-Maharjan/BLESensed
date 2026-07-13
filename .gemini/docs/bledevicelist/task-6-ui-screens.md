# Task 6: BleDeviceListScreen Composable Layouts (Success & Empty states)

**Status:** ⏳ Pending
**Group:** UI
**Priority:** High
**Agent:** ui-layer-agent

## Objective
Build the screens and layout components for the BLE scanner screen matching the design tokens, fonts, and layouts, including permission request launchers.

## Implementation Details
1. Create `DeviceCard.kt` in `commonMain` under components. It must contain:
   - Rounded corner shapes (12dp) and outline borders.
   - Signal strength RSSI indicator and connectable chips.
   - FlowRow displaying service UUID tags.
   - Text block displaying manufacturer hex data in monospace font.
2. Create `BleDeviceListScreen.kt` in `commonMain` containing:
   - `BleDeviceListScreen` (ScreenRoot pattern taking ViewModel).
     - On Android, use `rememberLauncherForActivityResult(RequestMultiplePermissions())` to register the runtime permissions launcher. If `uiModel.hasBluetoothPermission == false`, launch it. When permissions are granted, call `viewModel.onPermissionResult(true)`.
   - `BleDeviceListContent` (Content view taking `uiModel` and click callbacks).
   - If `hasBluetoothPermission == false`, display a centered permission request card with a "Grant Permission" button triggering the request callback, explaining why UGZeroHarm needs Bluetooth permissions.
   - Sticky TopAppBar and status scanner progress banner.
   - LazyColumn of `DeviceCard` components (Success list).
   - Centered Empty state view with `search_off` icon (when device list is empty).
   - Integration with shared project `LoadingScreen` and `FailedScreen` based on `devicesState`.
3. Add `@Preview` blocks for `BleDeviceListContent` (covering scanning, stopped, success list, empty list, and permission requested states).

## Files Created/Modified

### ADDED Files
- `feature/bledevicelist/src/commonMain/kotlin/com/anchor/ugzeroharm/bledevicelist/presentation/ui/components/DeviceCard.kt` - Device Card item.
- `feature/bledevicelist/src/commonMain/kotlin/com/anchor/ugzeroharm/bledevicelist/presentation/ui/BleDeviceListScreen.kt` - Screen layout file.

## Code Reference
- [patterns.md](../../../skills/_shared/patterns.md) § Rule 10 (ScreenRoot / Content separation)
- [bledevicelist_blueprint.md](../../../docs/bledevicelist/designs/bledevicelist_blueprint.md)

## Acceptance Criteria

#### Scenario: User toggles scanner scanning status
- GIVEN the scanner is active (scanning progress banner is showing)
- WHEN the user clicks "Stop Scan"
- THEN the progress spinner MUST disappear
- AND the status text MUST change to "Scan Stopped"
- AND a "Start Scan" button MUST appear.

#### Scenario: Permission request is triggered
- GIVEN the user is on the scanner screen and permissions are missing
- WHEN the user clicks "Grant Permission"
- THEN the system permissions dialog MUST be prompted to the user.

### Verification Checklist
- [ ] `@Preview` added for each top-level `@Composable` (private, same file, wrapped in `XTheme { ... }`).
- [ ] No Material 3 components used directly; wraps them using `XScaffold` or local styles.
- [ ] Monospace typography used for hex bytes and MAC address nodes.
- [ ] Device cards use 12dp roundness.
