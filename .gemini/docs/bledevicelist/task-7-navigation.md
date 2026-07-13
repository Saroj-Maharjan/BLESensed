# Task 7: BleDeviceListNavigation routing and extensions

**Status:** ⏳ Pending
**Group:** UI
**Priority:** High
**Agent:** ui-layer-agent

## Objective
Define the type-safe navigation route and NavGraphBuilder extensions for the BLE Device List feature.

## Implementation Details
1. Create `BleDeviceListNavigation.kt` in `commonMain`:
   - Declare `@Serializable object BleDeviceListRoute`.
   - Implement `fun NavGraphBuilder.bleDeviceListScreen(...)` extension function wrapping the `BleDeviceListScreen` initialization and binding.

## Files Created/Modified

### ADDED Files
- `feature/bledevicelist/src/commonMain/kotlin/com/anchor/ugzeroharm/bledevicelist/presentation/navigation/BleDeviceListNavigation.kt` - Navigation routing helper.

## Code Reference
- [patterns.md](../../../skills/_shared/patterns.md) § Rule 10 (Navigation extensions)

## Acceptance Criteria

#### Scenario: Navigating to scanner route launches screen
- GIVEN the NavHost is initialized
- WHEN the route `BleDeviceListRoute` is triggered
- THEN the `BleDeviceListScreen` MUST load and start scanning.

### Verification Checklist
- [ ] NavGraphBuilder extension compiles successfully.
- [ ] Route uses type-safe serializable object.
