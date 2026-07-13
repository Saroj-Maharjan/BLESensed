# Task 4: BleScanRepositoryImpl scanner flow

**Status:** ⏳ Pending
**Group:** Data
**Priority:** High
**Agent:** platform-agent

## Objective
Implement `BleScanRepositoryImpl` in `commonMain` utilizing `BlueFalcon` scanner and delegates.

## Implementation Details
1. Create `BleScanRepositoryImpl` class in `commonMain` conforming to `BleScanRepository` and implementing `BlueFalconDelegate`.
2. Maintain mutable state flow of unique discovered devices: `val _scannedDevices = MutableStateFlow<List<BleScannedDevice>>(emptyList())`.
3. In `startScan()`:
   - Call `blueFalcon.delegates.add(this)` (or legacy delegate registration).
   - Clear existing list: `_scannedDevices.value = emptyList()`.
   - Call `blueFalcon.scan()`. Update scanning state Flow.
4. In `stopScan()`:
   - Call `blueFalcon.stopScan()`.
   - Call `blueFalcon.delegates.remove(this)`. Update scanning state Flow.
5. In `didDiscoverDevice(peripheral, advertisementData)`:
   - Parse `peripheral.name` (or fallback to "Unknown Device").
   - Parse `peripheral.uuid` / `peripheral.device` mac address.
   - Parse `peripheral.rssi` signal level.
   - Extract advertised services from `advertisementData[AdvertisementDataRetrievalKeys.ServiceUUIDsKey]`.
   - Extract manufacturer bytes from `advertisementData[AdvertisementDataRetrievalKeys.ManufacturerData]`, format to hex string.
   - Extract connectability from `advertisementData[AdvertisementDataRetrievalKeys.IsConnectable]`.
   - Map to `BleScannedDevice` and update `_scannedDevices` list (replacing device if MAC matches, ensuring no duplicates in the list).

## Files Created/Modified

### ADDED Files
- `feature/bledevicelist/src/commonMain/kotlin/com/anchor/ugzeroharm/bledevicelist/data/repository/BleScanRepositoryImpl.kt` - Repository implementation.

## Code Reference
- [architecture/platform.md](../../../skills/creating-kmp-feature/architecture/platform.md)

## Acceptance Criteria
*(Data Repository task: no behavior scenarios required)*

### Verification Checklist
- [ ] Compiles successfully on all targets.
- [ ] Handles duplicate devices by MAC address / UUID gracefully.
- [ ] Gracefully fails or stays empty on Desktop when BlueFalcon is null.
