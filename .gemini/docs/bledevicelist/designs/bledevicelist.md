# BLE Device List Screen Design Spec

**Approved**: 2026-06-28

## Design Description
The BLE Device List screen displays active scanning status and discovered Bluetooth Low Energy devices with detailed advertising information. It features a sticky Top App Bar with settings access, a Status Banner with scanning controls (Start/Stop scanning progress indicator), and a list of discovered devices represented by Cards. Each card presents high-density technical information including:
- Device Name & MAC Address
- RSSI Signal Strength (e.g., -64 dBm) and visual signal bars
- Connectability status (Connectable vs. Limited/Broadcast)
- Advertised Services list (with readable service names like Battery, Device Info)
- Manufacturer Specific Data hex block

## Visual Specifications
- **Colors**: Standard M3 color roles from `XTheme.kt`'s light/dark schemes:
  - Background: `MaterialTheme.colorScheme.background` (`#FFFBF0` in Light)
  - Surface: `MaterialTheme.colorScheme.surface` (`#FFFDF6` in Light)
  - Primary Accent: `MaterialTheme.colorScheme.primary` (`#6B5000` in Light)
  - Muted Text: `MaterialTheme.colorScheme.onSurfaceVariant` (`#4E4730` in Light)
  - Outline/Border: `MaterialTheme.colorScheme.outlineVariant` (`#CDC1A0` in Light)
  - Badges/Status Colors: Custom semantic `XTheme.Colors.Success` (for Connectable) and `XTheme.Colors.Danger` / `MaterialTheme.colorScheme.error` (for Limited / error states).
- **Typography**: Paired Manrope typography with Monospace fonts for technical details (MAC Address, Manufacturer data).
- **Layout**: Mobile-first single column, 16dp horizontal paddings, rounded cards (12dp corners) spaced 16dp vertically.
- **Components**: Wrappers around M3 Scaffold (`XScaffold`), custom TopAppBar (`XTopAppBar`), and status badges.

## Screenshots
- Success: `designs/bledevicelist.png`
- Loading: `.claude/docs/_shared/designs/loading.png` (shared)
- Failed: `.claude/docs/_shared/designs/failed.png` (shared)
- Empty: `designs/bledevicelist_empty.png`

<!-- COLOR_AUDIT:BEGIN -->
## Color Audit

Default theme for design: light

### Defined M3 Roles (already in active scheme — XLightColors or XDarkColors)
| Role | Hex (inventory) | Usage in Design |
|------|-----------------|-----------------|
| `primary` | `#6B5000` | Primary buttons, app bar accents, device name text |
| `onPrimary` | `#FFFFFF` | Text on primary buttons ("Stop Scan") |
| `primaryContainer` | `#FDEDB5` | Status banner background accents |
| `onPrimaryContainer`| `#221A00` | Text on primary containers |
| `secondaryContainer`| `#EFE480` | RSSI badge background |
| `onSecondaryContainer`| `#1A1800` | RSSI badge text |
| `background` | `#FFFBF0` | Full screen background |
| `surface` | `#FFFDF6` | Device card background, Top App Bar background |
| `onBackground` | `#1E1B10` | Standard body text |
| `onSurface` | `#1E1B10` | Heading text |
| `onSurfaceVariant` | `#4E4730` | Monospace addresses, service details text |
| `surfaceVariant` | `#EAE0C5` | Inactive status indicator |
| `outlineVariant` | `#CDC1A0` | Card borders, dividers |
| `error` | `#B3261E` | Error screen indicators |
| `onError` | `#FFFFFF` | Text on error buttons |

### Missing M3 Roles (must add to BOTH XLightColors and XDarkColors before implementation)
| Role | Active Scheme Hex | Counterpart Scheme Hex | Usage in Design |
|------|-------------------|----------------------|-----------------|
| (none) | | | |

### Custom Colors (XTheme.Colors.* — justified exceptions only)
| Name | Hex | Justification |
|------|-----|---------------|
| `Success` | `#4ADE80` | High-contrast connectable status badge text/borders |
| `Danger` | `#FFFF6B6B` | High-contrast limited status badge text/borders |
<!-- COLOR_AUDIT:END -->

## Typography Audit

### M3 Type-Scale Roles
- `headlineSmall`: Screen titles ("BLE Devices")
- `titleMedium`: Device Name text
- `bodyMedium`: General info, status text
- `bodySmall`: MONOSPACE labels, hexadecimal manufacturer data, MAC address

### Font family
- Typeface: `Manrope` (matches default `XTheme.kt` variable font)
- Monospace font family: `FontFamily.Monospace` (used for hex data and addresses)

## Motion Spec

### Animations
- **Progress Spinner**: `animate-spin-custom` rotating 0deg to 360deg over 2 seconds linearly. Used for active scanning spinner.
- **Scan Ripple**: `scanner-ripple` sliding from -100% to 100% horizontally over 2 seconds. Reused as a skeleton loader visual effect when updating cards.
