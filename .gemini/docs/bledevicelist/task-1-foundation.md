# Task 1: Module Structure & build.gradle.kts

**Status:** ⏳ Pending
**Group:** Data
**Priority:** High
**Agent:** platform-agent

## Objective
Set up the KMP `:feature:bledevicelist` module structure and configure its Gradle build dependencies.

## Implementation Details
1. Create directories for all targets:
   - `feature/bledevicelist/src/commonMain/kotlin/com/anchor/ugzeroharm/bledevicelist`
   - `feature/bledevicelist/src/androidMain/kotlin/com/anchor/ugzeroharm/bledevicelist`
   - `feature/bledevicelist/src/iosMain/kotlin/com/anchor/ugzeroharm/bledevicelist`
   - `feature/bledevicelist/src/desktopMain/kotlin/com/anchor/ugzeroharm/bledevicelist`
2. Create `feature/bledevicelist/build.gradle.kts`. Configure:
   - Android namespace: `com.anchor.ugzeroharm.bledevicelist`
   - Target configurations (android, iosArm64, iosSimulatorArm64, jvm("desktop")).
   - Include core dependencies in `commonMain`: `:core:common`, `:core:designsystem`, `libs.compose.components.resources`, `libs.koin.core`, `libs.koin.compose`, and Blue Falcon core dependency (`libs.blue.falcon.core` or coordinate `"dev.bluefalcon:blue-falcon-core:3.4.5"`).
   - Platform specific dependencies for Blue Falcon: `libs.blue.falcon.android` in `androidMain`, and `libs.blue.falcon.ios` in `iosMain`.

## Files Created/Modified

### ADDED Files
- `feature/bledevicelist/build.gradle.kts` - Feature build file.

## Code Reference
- [architecture/platform.md](../../../skills/creating-kmp-feature/architecture/platform.md)

## Acceptance Criteria
*(Foundation task: no behavior scenarios required)*

### Verification Checklist
- [ ] Module compiles successfully on its own.
- [ ] Build script contains `:core:common` and `:core:designsystem` projects.
