# Task 3: Expect/Actual Platform Module & BlueFalcon actuals

**Status:** ⏳ Pending
**Group:** Data
**Priority:** High
**Agent:** platform-agent

## Objective
Set up the `expect/actual val platformModule: Module` to construct the platform-specific BlueFalcon engine instance and bind the `PermissionDataSource`.

## Implementation Details
1. In `commonMain`:
   - Declare `internal expect val platformModule: Module`
2. In `androidMain`:
   - Bind `BlueFalcon(context = androidContext(), serviceUUID = null)` to DI module.
   - Bind `PermissionDataSourceAndroid(context = androidContext())` as `PermissionDataSource`.
3. In `iosMain`:
   - Bind `BlueFalcon(context = null, serviceUUID = null)` to DI module.
   - Bind `PermissionDataSourceIos()` as `PermissionDataSource`.
4. In `desktopMain`:
   - Bind `null` or a placeholder mock to DI module (since desktop does not support BLE engine).
   - Bind `PermissionDataSourceDesktop()` as `PermissionDataSource`.

## Files Created/Modified

### ADDED Files
- `feature/bledevicelist/src/commonMain/kotlin/com/anchor/ugzeroharm/bledevicelist/di/PlatformModule.kt` - Expect platform module.
- `feature/bledevicelist/src/androidMain/kotlin/com/anchor/ugzeroharm/bledevicelist/di/PlatformModule.android.kt` - Android actual module.
- `feature/bledevicelist/src/iosMain/kotlin/com/anchor/ugzeroharm/bledevicelist/di/PlatformModule.ios.kt` - iOS actual module.
- `feature/bledevicelist/src/desktopMain/kotlin/com/anchor/ugzeroharm/bledevicelist/di/PlatformModule.desktop.kt` - Desktop actual module.

## Code Reference
- [architecture/platform.md](../../../skills/creating-kmp-feature/architecture/platform.md)

## Acceptance Criteria
*(DI setup: no behavior scenarios required)*

### Verification Checklist
- [ ] Compiles successfully for Android, iOS, and Desktop targets.
- [ ] Android implementation references `androidContext()` and binds `PermissionDataSourceAndroid`.
- [ ] iOS implementation binds `PermissionDataSourceIos`.
- [ ] Desktop actual compiles and binds `PermissionDataSourceDesktop`.
