# Task 8: DI integration (Koin bleDeviceListModule wiring)

**Status:** ⏳ Pending
**Group:** Integration
**Priority:** High
**Agent:** integration-agent

## Objective
Implement Koin module bindings in `commonMain` and integrate Koin dependencies.

## Implementation Details
1. Create `BleDeviceListModule.kt` in `commonMain`:
   - Declare `val bleDeviceListModule = module { ... }`.
   - Include platform-specific bindings: `includes(platformModule)`.
   - Register `BleScanRepositoryImpl` bound to `BleScanRepository`.
   - Register `BleDeviceListViewModel` using `viewModelOf(::BleDeviceListViewModel)`.

## Files Created/Modified

### ADDED Files
- `feature/bledevicelist/src/commonMain/kotlin/com/anchor/ugzeroharm/bledevicelist/di/BleDeviceListModule.kt` - Koin DI module definitions.

## Code Reference
- [architecture/platform.md](../../../skills/creating-kmp-feature/architecture/platform.md)

## Acceptance Criteria
*(DI Integration task: no behavior scenarios required)*

### Verification Checklist
- [ ] Module compiles successfully on Android, iOS, and Desktop.
- [ ] Correctly includes `platformModule` to load platform actuals.
