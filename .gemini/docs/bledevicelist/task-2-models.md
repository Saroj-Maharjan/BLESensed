# Task 2: Models & Repository Interface

**Status:** ⏳ Pending
**Group:** Data
**Priority:** High
**Agent:** platform-agent

## Objective
Define the common immutable BLE model `BleScannedDevice` and the `BleScanRepository` interface in `commonMain`.

## Implementation Details
1. Create `BleScannedDevice` in `commonMain` containing:
   - `name: String`
   - `macAddress: String`
   - `rssi: Int?`
   - `isConnectable: Boolean`
   - `advertisedServices: List<String>`
   - `manufacturerData: String?`
2. Create `BleScanRepository` interface in `commonMain` declaring:
   - `val scannedDevices: StateFlow<List<BleScannedDevice>>`
   - `val isScanning: StateFlow<Boolean>`
   - `fun startScan()`
   - `fun stopScan()`

## Files Created/Modified

### ADDED Files
- `feature/bledevicelist/src/commonMain/kotlin/com/anchor/ugzeroharm/bledevicelist/data/model/BleScannedDevice.kt` - Immutable DTO.
- `feature/bledevicelist/src/commonMain/kotlin/com/anchor/ugzeroharm/bledevicelist/data/repository/BleScanRepository.kt` - Repository interface.

## Code Reference
- [architecture/platform.md](../../../skills/creating-kmp-feature/architecture/platform.md)

## Acceptance Criteria
*(Data task: no behavior scenarios required)*

### Verification Checklist
- [ ] DTO compiles and contains all advertisement details parsed from design spec.
- [ ] Repository interface exposes public Flows representing scanning status and discovered peripherals.
