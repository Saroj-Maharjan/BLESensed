# Compose Implementation Blueprint: Login

## Design Tokens

| Hex | M3 Role | Usage |
|-----|---------|-------|
| #FFFBF0 | background | Screen background |
| #FFFDF6 | surface | Top app bar background, main card background, label background |
| #6B5000 | primary | App bar title, form header title, generate ID button text, primary button background, illustration icon |
| #1E1B10 | onSurface | Input text, card header text, safety card title text |
| #4E4730 | onSurfaceVariant | Card description helper text, permissions footnote text, safety card description text |
| #CDC1A0 | outlineVariant | Card border, text field outline border |
| #EFE480 | secondaryContainer | Illustration background pulsing glow |
| #FFFFFF | onPrimary | Primary button text, bluetooth icon color |
| #ffdf9b | primaryFixed | Illustration border outline (missing role counterpart: #e8c26d in dark) |

## Typography Scale

> Typography is app-global (see `_shared/patterns.md` → "Typography"). Each text node maps to an **M3 type-scale role** exactly as each fill maps to an M3 color role — implementation uses `style = MaterialTheme.typography.{role}`, not raw `fontSize`. The `Size`/`Weight`/`Letter Spacing` columns are the design's measured values from the token inventory; they exist to (a) pick the closest role and (b) flag a divergence that needs an override or a theme change (recorded under *Typography Updates Required*). The design typeface (`Manrope`) and how it compares to the theme's current font are captured in the contract, not here.

| Usage | M3 Role | Size (sp) | Weight | Letter Spacing | Text Transform | Color Role |
|-------|---------|-----------|--------|----------------|----------------|------------|
| Screen title | titleLarge | 22 | Bold (700) | 0 | none | primary |
| Card header | titleMedium | 18 | Bold (700) | 0 | none | onSurface |
| Card helper | bodyMedium | 14 | Normal (400) | 0 | none | onSurfaceVariant |
| Textfield label | labelMedium | 12 | SemiBold (600) | 0 | none | outline |
| Monospace input | bodyLarge | 18 | Normal (400) | 0.1em | none | onSurface |
| Secondary button | labelLarge | 14 | Bold (700) | 0 | none | primary |
| Primary button | labelLarge | 16 | Bold (700) | 0 | none | onPrimary |
| Footnote | bodySmall | 12 | Normal (400) | 0 | none | onSurfaceVariant |
| Safety card title | labelLarge | 12 | Bold (700) | 0 | none | onSurface |
| Safety card body | bodySmall | 11 | Normal (400) | 0 | none | onSurfaceVariant |

## Spacing Grid

| Context | Property | Value (dp) |
|---------|----------|------------|
| Screen Canvas | horizontal padding | 16 |
| Screen Canvas | vertical padding | 24 |
| Screen Canvas | spacing between sections | 24 |
| Card Container | internal padding | 24 |
| Card Container | child gap spacing | 20 |
| Illustration Container | size (width x height) | 192 x 192 |

## Component Tree

### Shared Screen container (all states)
- `XScreen` (Rule 13 — feature screens use `XScreen`, never `XScaffold`/`Scaffold`; the one Scaffold lives in `App.kt` and owns all insets)
  - topBar: `XTopAppBar` (title = "Device Setup", navigationIcon = null, actions = settings button)
  - content: **[state-specific content slot]** (fills `XScreen`'s weight box; no `paddingValues` to thread)

### Success State
- `Column` (modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(24.dp))
  - `SetupHeader` (Custom component -> `SetupHeader.kt`)
    - `Box` (modifier = Modifier.size(192.dp))
      - Pulse Background: `Box` (modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.2f), shape = CircleShape).blur(32.dp)) [Pulsing animation: scale 1.0f -> 1.02f, alpha 0.2f -> 0.17f]
      - Shield Container: `Box` (modifier = Modifier.size(128.dp).background(Color.White, shape = CircleShape).border(4.dp, MaterialTheme.colorScheme.primaryFixed, CircleShape), contentAlignment = Alignment.Center)
        - `XIcon` (painter = painterResource(Res.drawable.shield_with_heart_fill), tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(72.dp))
      - Bluetooth Icon Badge: `Box` (modifier = Modifier.align(Alignment.TopEnd).size(40.dp).background(MaterialTheme.colorScheme.primary, shape = CircleShape).border(4.dp, MaterialTheme.colorScheme.background, CircleShape), contentAlignment = Alignment.Center)
        - `XIcon` (painter = painterResource(DesignSystemResources.drawable.bluetooth_searching), tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(24.dp))
  - `DeviceIdForm` (Custom component -> `DeviceIdForm.kt`)
    - `XCard` (modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant))
      - `Column` (modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(20.dp))
        - Text Header: `XText`("Enter 6-Digit Device ID", style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp))
        - Text Helper: `XText`("This ID is used...", style = MaterialTheme.typography.bodyMedium)
        - Input Section: `Box` (modifier = Modifier.fillMaxWidth())
          - `OutlinedTextField` (value = deviceId, onValueChange = ..., modifier = Modifier.fillMaxWidth(), textStyle = MaterialTheme.typography.bodyLarge.copy(fontFamily = FontFamily.Monospace, fontSize = 18.sp, letterSpacing = 0.1.em))
        - Secondary Button: `XButton` (onClick = ..., colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.primary))
          - `XIcon` (painter = painterResource(DesignSystemResources.drawable.refresh), modifier = Modifier.size(20.dp))
          - `XText`("Generate Random ID")
  - `AdvertisingAction` (inline or in main screen)
    - `Column` (modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp))
      - Primary Button: `XButton` (onClick = ..., modifier = Modifier.fillMaxWidth().height(56.dp), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.onPrimary), shape = CircleShape)
        - `XText`("Start Advertising & Continue", style = MaterialTheme.typography.labelLarge.copy(fontSize = 16.sp, fontWeight = FontWeight.Bold))
        - `XIcon` (painter = painterResource(Res.drawable.arrow_forward), modifier = Modifier.size(24.dp))
      - Footnote: `XText`("Make sure Bluetooth permissions are granted.", style = MaterialTheme.typography.bodySmall)
  - `SafetyInfoCard` (Custom component -> `SafetyInfoCard.kt`)
    - `Row` (modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surfaceContainerLow, shape = RoundedCornerShape(12.dp)).padding(16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp))
      - Icon Holder: `Box` (modifier = Modifier.background(Color.White, shape = RoundedCornerShape(8.dp)).padding(8.dp).shadow(1.dp, RoundedCornerShape(8.dp)))
        - `XIcon` (painter = painterResource(Res.drawable.security), tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
      - Text Column: `Column` (modifier = Modifier.weight(1f))
        - Header: `XText`("Safety First Architecture", style = MaterialTheme.typography.labelLarge.copy(fontSize = 12.sp, fontWeight = FontWeight.Bold))
        - Body: `XText`("UGZeroHarm encrypts...", style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp))

### Loading State
Shared screen — see: `.claude/docs/_shared/designs/loading.png`
Token inventory: `.claude/docs/_shared/designs/extracted/tokens_loading.md`
- `AppLoadingState()` (defined in `com.anchor.ugzeroharm.designsystem.app.AppLoadingState`)

### Failed State
Shared screen — see: `.claude/docs/_shared/designs/failed.png`
Token inventory: `.claude/docs/_shared/designs/extracted/tokens_failed.md`
- `AppErrorState` (defined in `com.anchor.ugzeroharm.designsystem.app.AppErrorState`)
  - title: `XText`("Something went wrong")
  - message: `XText`("Check connection and try again.")
  - onRetry: lambda callback

### Empty State
Omitted (not list-based screen).

## String Inventory

> Every user-facing text node in the design → a proposed string-resource key (Rule 12). The implementer creates `composeResources/values/strings.xml` from this table and references the keys via `stringResource(Res.string.*)`. Exclude: repository-supplied data (names, dates, tickers), single-glyph symbols ($ , ₿ , % , ✓), and control sentinels. Key naming: {area}_{purpose} snake_case.

| Key | Default (English) value | Where used | Notes |
|-----|-------------------------|------------|-------|
| login_title | Device Setup | top bar | Screen header |
| login_header_device_id | Enter 6-Digit Device ID | DeviceIdForm card | Card title |
| login_desc_device_id | This ID is used as the unique name for Bluetooth Low Energy advertisement. | DeviceIdForm card | Card helper description |
| login_label_device_id | Device ID | DeviceIdForm TextField | Floating label |
| login_placeholder_device_id | e.g., A1B2C3 | DeviceIdForm TextField | Input field placeholder |
| login_btn_generate_id | Generate Random ID | DeviceIdForm secondary button | Secondary button text |
| login_btn_start_advertising | Start Advertising & Continue | AdvertisingAction primary button | Main action button |
| login_footnote_bluetooth | Make sure Bluetooth permissions are granted. | AdvertisingAction footnote | Hint text |
| login_safety_header | Safety First Architecture | SafetyInfoCard title | Small informational header |
| login_safety_body | UGZeroHarm encrypts local broadcast metadata to ensure your tracking keys remain private and secure. | SafetyInfoCard content | Small informational description |

## Motion

> Every kept animation in the design → one row. Built from the Step 1.16 Motion Audit. Every kept row is gated by `rememberReducedMotion()`.

| Element | Family | Compose primitive | Params (dur / easing / repeat / trigger) | Magnitude | Target file |
|---------|--------|-------------------|------------------------------------------|-----------|-------------|
| SetupHeader background glow circle | Loading/Attention loop | `rememberInfiniteTransition()` | 3000ms / FastOutSlowInEasing / Repeat | scale: 1.0f -> 1.02f, alpha: 0.2f -> 0.17f | Inline in `SetupHeader.kt` |

**Reduced motion**: all kept animations gated by `rememberReducedMotion()` — reduced ⇒ skip to end/target state (scale = 1.0f, alpha = 0.2f). Durations/easings via `XMotion` tokens.

**Dropped (interaction + web-only)**: hover:bg-surface-container-high, active:scale-95, focus:border-primary, focus:ring-0, hover:bg-surface-variant/30, active:scale-[0.98], hover:bg-primary/90, active:scale-[0.97]

## Pre-Implementation Contract

### XTheme Updates Required

| Role | Active Scheme Hex | Counterpart Scheme Hex | Usage |
|------|-------------------|----------------------|-------|
| primaryFixed | #ffdf9b | #e8c26d | Illustration border outline |

### Typography Updates Required

**Type-scale role overrides**:

| Node | Chosen Role | Stock Role Value | Measured Value | Override |
|------|-------------|------------------|----------------|----------|
| "Enter 6-Digit Device ID" (card header) | titleMedium | 16sp/Normal | 18sp/Bold | `style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp, fontWeight = FontWeight.Bold)` |
| Monospace input field | bodyLarge | 16sp/Normal | 18sp/Monospace | `style = MaterialTheme.typography.bodyLarge.copy(fontFamily = FontFamily.Monospace, fontSize = 18.sp, letterSpacing = 0.1.em)` |
| "Generate Random ID" | labelLarge | 14sp/Medium | 14sp/Bold | `style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)` |
| "Start Advertising & Continue" | labelLarge | 14sp/Medium | 16sp/Bold | `style = MaterialTheme.typography.labelLarge.copy(fontSize = 16.sp, fontWeight = FontWeight.Bold)` |
| "Safety First Architecture" | labelLarge | 14sp/Medium | 12sp/Bold | `style = MaterialTheme.typography.labelLarge.copy(fontSize = 12.sp, fontWeight = FontWeight.Bold)` |
| "UGZeroHarm encrypts..." | bodySmall | 12sp/Normal | 11sp/Normal | `style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp)` |

### Color Audit

#### Defined Roles
| Role | Hex | Usage |
|------|-----|-------|
| background | #FFFBF0 | Screen background |
| surface | #FFFDF6 | Top app bar background, main card background, label background |
| primary | #6B5000 | App bar title, card header title, buttons text/background, illustration icon |
| onSurface | #1E1B10 | Input text, card header text, safety card title text |
| onSurfaceVariant | #4E4730 | Muted text |
| outlineVariant | #CDC1A0 | Card border, text field outline border |
| secondaryContainer | #EFE480 | Pulse background glow |
| onPrimary | #FFFFFF | Primary button text, bluetooth icon color |

#### Missing Roles (must add before implementation)
| Role | Active Scheme Hex | Counterpart Scheme Hex | Usage |
|------|-------------------|----------------------|-------|
| primaryFixed | #ffdf9b | #e8c26d | Illustration border outline |

#### Custom Colors (justified exceptions only)
| Name | Hex | Justification |
|------|-----|---------------|
| (none) | | |

#### Component Overrides (divergences from X-component defaults)

| Component | Property | HTML Value | X-component Default | Override Required |
|-----------|----------|-----------|-------------------|------------------|
| (none) | | | | |

## Post-Implementation Checklist

- [ ] Every text node uses `style = MaterialTheme.typography.{role}` (or an `XTextDefaults` preset) — no raw `fontSize`/`fontWeight` except the recorded *Type-scale role overrides*
- [ ] All XTheme missing roles (`primaryFixed`) added to BOTH XLightColors and XDarkColors
- [ ] Every component in blueprint Component Tree exists in implementation
- [ ] Every Modifier in blueprint (border, shadow, alpha, padding, size) is present in code
- [ ] Every icon rendered per `icons.json`: `bluetooth_searching` -> `DesignSystemResources.drawable.bluetooth_searching`, `arrow_forward` -> `Res.drawable.arrow_forward`, etc.
- [ ] All colors use MaterialTheme.colorScheme.{role} — no raw Color() hex
- [ ] Component override sizes/colors from Pre-Implementation Contract applied
- [ ] Every String Inventory key exists in `composeResources/values/strings.xml` and is referenced via `stringResource` — no hardcoded display literals (Rule 12)
- [ ] Every `## Motion` row implemented in `SetupHeader.kt` pulsing animation, gated by `rememberReducedMotion()`; no interaction/hover motion present
- [ ] Build passes: `./gradlew :feature:login:assembleAndroidMain`
- [ ] Code formatted: `./gradlew :feature:login:ktlintFormat`
