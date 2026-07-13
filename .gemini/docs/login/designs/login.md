# Login Screen

**Approved**: 2026-06-28T13:37:00Z

## Design Description
The Login Screen (Device Setup) provides a secure, visually warm, and modern interface for registering the user's 6-digit Device ID. This ID is subsequently utilized for BLE (Bluetooth Low Energy) advertisement as part of the UGZeroHarm safety-tracking flow.

The design relies on a structured, centralized form layout embedded in an outlined card. An aesthetic, pulsing safety illustration (shield + BLE beacon search icon) sits above the form to signal the app's safety-first architecture and ongoing radio state.

## Visual Specifications
- **Colors**: Uses warm Caution Amber/Gold accents (`#6B5000`) on top of a soft sand background (`#FFFBF0`).
- **Typography**: App-wide `Manrope` typeface. Uses bold headline sizes (`22sp` for screen title, `18sp` for card header) and monospace layout for the 6-digit Device ID input.
- **Layout**: Centered column containing a logo/radar illustration block, a primary card section (containing the ID text field and random ID generator), a primary action button ("Start Advertising & Continue"), and an informational bottom card detailing security features.
- **Components**: Standard Compose components (`XScaffold`, `XTopAppBar`, `XCard`, outlined input, and buttons).

## Screenshots
- Success: `login.png`
- Loading: `.claude/docs/_shared/designs/loading.png` (shared)
- Failed: `.claude/docs/_shared/designs/failed.png` (shared)

<!-- COLOR_AUDIT:BEGIN -->
## Color Audit

Default theme for design: light

### Defined M3 Roles (already in active scheme — XLightColors)
| Role | Hex (inventory) | Usage in Design |
|------|-----------------|-----------------|
| background | #FFFBF0 | Screen background |
| surface | #FFFDF6 | Top app bar background, main card background, label background |
| primary | #6B5000 | App bar title, form header title, generate ID button text, primary button background, illustration icon |
| onSurface | #1E1B10 | Input text, card header text, safety card title text |
| onSurfaceVariant | #4E4730 | Card description helper text, permissions footnote text, safety card description text |
| outlineVariant | #CDC1A0 | Card border, text field outline border |
| secondaryContainer | #EFE480 | Illustration background pulsing glow |
| onPrimary | #FFFFFF | Primary button text, bluetooth icon color |

### Missing M3 Roles (must add to BOTH XLightColors and XDarkColors before implementation)
| Role | Active Scheme Hex | Counterpart Scheme Hex | Usage in Design |
|------|-------------------|----------------------|-----------------|
| primaryFixed | #ffdf9b | #e8c26d | Illustration border outline |

### Custom Colors (XTheme.Colors.* — justified exceptions only)
| Name | Hex | Justification |
|------|-----|---------------|
| (none) | | |
<!-- COLOR_AUDIT:END -->

<!-- TYPOGRAPHY_AUDIT:BEGIN -->
## Typography Audit

**Design typeface**: Manrope (github-variable; weights [400, 700])
**Theme font**: Manrope — **matches current**

### Text node → M3 role
| Node (usage) | M3 Role | Measured (size/weight) | Override needed? |
|--------------|---------|------------------------|------------------|
| "Device Setup" (app bar title) | titleLarge | 22sp / 700 | no |
| "Enter 6-Digit Device ID" (card header) | titleMedium | 18sp / 700 | yes — `.copy(fontSize = 18.sp)` |
| "This ID is used..." (card helper) | bodyMedium | 14sp / 400 | no |
| "Device ID" (floating textfield label) | labelMedium | 12sp / 600 | no |
| Outlined Text Field value (monospace) | bodyLarge | 18sp / 400 | yes — `.copy(fontFamily = FontFamily.Monospace, fontSize = 18.sp, letterSpacing = 0.1.em)` |
| "Generate Random ID" (secondary button) | labelLarge | 14sp / 700 | yes — `.copy(fontWeight = FontWeight.Bold)` |
| "Start Advertising & Continue" (primary button) | labelLarge | 16sp / 700 | yes — `.copy(fontSize = 16.sp, fontWeight = FontWeight.Bold)` |
| "Make sure Bluetooth..." (footnote) | bodySmall | 12sp / 400 | no |
| "Safety First Architecture" (safety header) | labelLarge | 12sp / 700 | yes — `.copy(fontSize = 12.sp, fontWeight = FontWeight.Bold)` |
| "UGZeroHarm encrypts..." (safety body) | bodySmall | 11sp / 400 | yes — `.copy(fontSize = 11.sp)` |
<!-- TYPOGRAPHY_AUDIT:END -->

<!-- MOTION_AUDIT:BEGIN -->
## Motion Audit

**Motion present**: yes

### Kept motion → Compose
| Element | Family | Compose primitive | Params (dur/easing/repeat/trigger) | Magnitude | Target file |
|---------|--------|-------------------|------------------------------------|-----------|-------------|
| Logo background glow circle (`.animate-pulse-subtle`) | Loading/Attention loop | `rememberInfiniteTransition()` | 3000ms / FastOutSlowInEasing / Repeat | scale: 1.0f -> 1.02f, alpha: 0.2f -> 0.17f | inline in `LoginScreen.kt` |

**Reduced motion**: all kept rows gated by `rememberReducedMotion()` (DS `XMotion.kt` — expectations read OS setting). Durations/easings via `XMotion` tokens.

**Dropped (interaction + web-only)**: hover:bg-surface-container-high, active:scale-95, focus:border-primary, focus:ring-0, hover:bg-surface-variant/30, active:scale-[0.98], hover:bg-primary/90, active:scale-[0.97]
<!-- MOTION_AUDIT:END -->
