# Task 9: Navigation integration & Welcome handoff

**Status:** ⏳ Pending
**Group:** Integration
**Priority:** High
**Agent:** integration-agent

## Objective
Wire the feature module dependencies, load DI definitions in the main app module, wire navigation, and perform the Welcome handoff.

## Implementation Details
1. In `settings.gradle.kts`:
   - Add `include(":feature:bledevicelist")`.
2. In `composeApp/build.gradle.kts`:
   - Add `implementation(project(":feature:bledevicelist"))` to the KMP commonMain dependencies block.
3. In `composeApp/src/commonMain/kotlin/com/anchor/ugzeroharm/initKoin.kt`:
   - Import `bleDeviceListModule` and include it in the `modules(...)` initialization list.
4. In `composeApp/src/commonMain/kotlin/com/anchor/ugzeroharm/BaseAppNavHost.kt`:
   - Import `BleDeviceListRoute` and `bleDeviceListScreen`.
   - Change `startDestination = BleDeviceListRoute`.
   - Add the `bleDeviceListScreen()` builder extension block to the NavHost content lambda.
5. Welcome handoff:
   - Identify welcome screens in `composeApp` (e.g. `WelcomeScreen.kt` or `WelcomeRoute.kt`).
   - Remove these files and delete any references to them in code.
6. Verify build:
   - Run `./gradlew assembleDebug` to verify project compiles.
   - Run `./gradlew ktlintFormat` to format files.

## Files Created/Modified

### MODIFIED Files
- `settings.gradle.kts` - Include module.
- `composeApp/build.gradle.kts` - Add dependency.
- `composeApp/src/commonMain/kotlin/com/anchor/ugzeroharm/initKoin.kt` - Load Koin module.
- `composeApp/src/commonMain/kotlin/com/anchor/ugzeroharm/BaseAppNavHost.kt` - Wire navigation.

### DELETED Files
- `composeApp/src/commonMain/kotlin/com/anchor/ugzeroharm/WelcomeScreen.kt` (or similar welcome files)

## Code Reference
- [patterns.md](../../../skills/_shared/patterns.md) § Integration Points & Handoffs

## Acceptance Criteria

#### Scenario: Welcome handoff compiles and launches feature screen
- GIVEN the project is built
- WHEN the application starts
- THEN it MUST bypass the welcome message
- AND the BLE Device List screen MUST load directly as the app start destination.

### Verification Checklist
- [ ] `./gradlew assembleDebug` completes successfully.
- [ ] Welcome screen and assets are deleted.
- [ ] Code is formatted with `./gradlew ktlintFormat`.
