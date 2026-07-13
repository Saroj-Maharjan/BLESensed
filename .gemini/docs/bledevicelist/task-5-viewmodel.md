# Task 5: BleDeviceListUiModel & ViewModel state management

**Status:** ⏳ Pending
**Group:** UI
**Priority:** High
**Agent:** ui-layer-agent

## Objective
Implement the ViewModel and the unified UI state model for the BLE Device List feature, including permission checking orchestration.

## Implementation Details
1. Create `BleDeviceListUiModel` in `commonMain` conforming to patterns.md Rule 11. It must contain:
   - `val devicesState: UiState<List<BleScannedDevice>>` - standard load slot.
   - `val isScanning: Boolean = false` - scan active flag.
   - `val hasBluetoothPermission: Boolean = false` - permission status.
2. Create `BleDeviceListViewModel` in `commonMain`:
   - Inject `BleScanRepository` and `PermissionDataSource` in constructor.
   - Maintain a private `MutableStateFlow<BleDeviceListUiModel>` and public `StateFlow<BleDeviceListUiModel>`.
   - On init, check `permissionDataSource.hasBluetoothPermission()`.
   - If true, automatically trigger `startScanning()`. If false, set `hasBluetoothPermission = false` in UiModel.
   - Expose methods: `startScanning()` (which checks permission first), `stopScanning()`, and `onPermissionResult(granted: Boolean)` to handle Compose UI permission launcher responses.

## Files Created/Modified

### ADDED Files
- `feature/bledevicelist/src/commonMain/kotlin/com/anchor/ugzeroharm/bledevicelist/presentation/model/BleDeviceListUiModel.kt` - Unified state model.
- `feature/bledevicelist/src/commonMain/kotlin/com/anchor/ugzeroharm/bledevicelist/presentation/viewmodel/BleDeviceListViewModel.kt` - ViewModel.

## Code Reference
- [patterns.md](../../../skills/_shared/patterns.md) § Rule 11 (Unified State Flow)

## Acceptance Criteria

#### Scenario: Scanning states are mapped correctly
- GIVEN the ViewModel is initialized
- WHEN scanning starts
- THEN `uiModel` state MUST have `isScanning` = true
- AND `devicesState` MUST transition from Loading to Success as device results flow in.

#### Scenario: Missing permissions halts scanning
- GIVEN the ViewModel is initialized
- WHEN permissions check fails (returns false)
- THEN `uiModel` state MUST have `hasBluetoothPermission` = false
- AND active BLE scanning MUST NOT start.

### Verification Checklist
- [ ] ViewModel compiles successfully.
- [ ] Inject `PermissionDataSource` and check permission before scanning.
- [ ] Uses `_uiModel.setState { ... }` or similar state update copy utilities.
