# BLE Device List Screen Compose Implementation Blueprint

This blueprint describes the design tokens, visual specifications, spacing grid, component tree, and implementation requirements for the BLE Device List feature (`bledevicelist`).

## Goal Description
Scaffold the BLE Device List screen using the Material 3 design system configurations. The feature must handle device discovery scanning, display detailed BLE advertisement information (RSSI, MAC Address, Connectable status, Advertised Services, Manufacturer Data), and support Loading, Failed, and Empty states.

## Design Tokens

| Token Name | Value | M3 Role | Usage |
|------------|-------|---------|-------|
| `--bg-background` | `#FFFBF0` | `background` | Screen background fill |
| `--bg-surface` | `#FFFDF6` | `surface` | Card background fill, Top App Bar background |
| `--text-primary` | `#6B5000` | `primary` | Active Scan status title, primary buttons, device name |
| `--text-on-surface-variant` | `#4E4730` | `onSurfaceVariant` | Muted descriptions, Monospace labels |
| `--border-outline-variant` | `#CDC1A0` | `outlineVariant` | Card borders, dividers |
| `--color-success` | `#4ADE80` | `Success` (Custom Status) | Connectable badge text/border |
| `--color-danger` | `#xFFFF6B6B` | `Danger` (Custom Status) | Limited/Broadcast badge text/border |

## Spacing Grid

- `4dp` (`gap-1` / `py-0.5`) - Small gap within items
- `8dp` (`py-2` / `gap-2` / `p-2`) - Margins, item paddings
- `12dp` (`py-3` / `gap-3` / `mb-3`) - Card interior margins
- `16dp` (`px-4` / `p-4` / `space-y-4`) - Card padding, device list spacing
- `24dp` (`py-6`) - Page vertical padding

## Component Tree

### Success State

```
XScaffold
├── TopAppBar (title = "BLE Devices", navigationIcon = settings)
└── Column (Modifier.fillMaxSize())
    ├── StatusBanner (Modifier.fillMaxWidth(), background = surfaceContainerLow/95%)
    │   ├── Row (Modifier.padding(horizontal = 16.dp, vertical = 12.dp))
    │   │   ├── Row (alignment = Center, gap = 12.dp)
    │   │   │   ├── CircularProgressIndicator (color = primary, size = 20.dp)
    │   │   │   └── Text("Scanning for devices...", style = bodyMedium, fontWeight = SemiBold)
    │   │   └── Button("Stop Scan", colors = primaryContainer/onPrimaryContainer, shape = CircleShape)
    └── LazyColumn (Modifier.padding(horizontal = 16.dp, vertical = 24.dp), verticalArrangement = 16.dp)
        └── DeviceCard (shape = RoundedCornerShape(12.dp), border = outlineVariant, containerColor = surface)
            └── Column (Modifier.padding(16.dp))
                ├── Row (Modifier.fillMaxWidth(), horizontalArrangement = SpaceBetween)
                │   ├── Column (gap = 2.dp)
                │   │   ├── Text(device.name, style = titleMedium, fontWeight = Bold)
                │   │   └── Text(device.macAddress, style = bodySmall, fontFamily = Monospace)
                │   └── Column (alignment = End, gap = 8.dp)
                │       ├── RSSIBadge (value = device.rssi, containerColor = secondaryContainer, textColor = onSecondaryContainer)
                │       └── ConnectabilityBadge (isConnectable = device.isConnectable, containerColor = success/danger)
                ├── HorizontalDivider (color = outlineVariant)
                ├── Column (Modifier.padding(top = 12.dp))
                │   ├── Text("Advertised Services", style = labelSmall, color = outline, uppercase = true)
                │   └── FlowRow (gap = 8.dp)
                │       └── ServiceChip (service.uuid, service.name, containerColor = surfaceContainerHigh)
                └── Column (Modifier.padding(top = 12.dp))
                    ├── Text("Manufacturer Data", style = labelSmall, color = outline, uppercase = true)
                    └── Box (Modifier.background(surfaceContainerLowest), shape = RoundedCornerShape(8.dp), padding = 8.dp)
                        └── Text(device.manufacturerDataHex, style = bodySmall, fontFamily = Monospace, breakAll = true)
```

### Empty State

```
XScaffold
├── TopAppBar (title = "BLE Devices", navigationIcon = settings)
└── Column (Modifier.fillMaxSize())
    ├── StatusBanner (Modifier.fillMaxWidth(), background = surfaceContainerLow/95%)
    │   ├── Row (Modifier.padding(horizontal = 16.dp, vertical = 12.dp))
    │   │   ├── Row (alignment = Center, gap = 12.dp)
    │   │   │   ├── Icon(radar_fill, opacity = 40%)
    │   │   │   └── Text("Scan Stopped", style = bodyMedium)
    │   │   └── Button("Start Scan", colors = primary, shape = CircleShape)
    └── Box (Modifier.fillMaxSize(), contentAlignment = Center)
        └── Column (alignment = Center, gap = 16.dp)
            └── Box (Modifier.background(surfaceContainerHigh), shape = CircleShape, padding = 24.dp)
                └── Icon(search_off, size = 60.dp, color = outline, opacity = 40%)
            └── Column (alignment = Center, gap = 4.dp)
                ├── Text("No devices found", style = titleMedium, fontWeight = Bold)
                └── Text("Start scanning to discover BLE devices", style = bodyMedium, color = onSurfaceVariant/60%)
```

## String Inventory

| String Key | English Value | Usage |
|------------|---------------|-------|
| `bledevicelist_title` | "BLE Devices" | App Bar Title |
| `bledevicelist_status_scanning` | "Scanning for devices..." | Scanner status banner (scanning) |
| `bledevicelist_status_stopped` | "Scan Stopped" | Scanner status banner (stopped) |
| `bledevicelist_action_stop` | "Stop Scan" | Banner action button (scanning) |
| `bledevicelist_action_start` | "Start Scan" | Banner action button (stopped) |
| `bledevicelist_empty_title` | "No devices found" | Empty list visual header |
| `bledevicelist_empty_subtitle` | "Start scanning to discover BLE devices" | Empty list visual instruction |
| `bledevicelist_device_connectable` | "Connectable" | Connectable status chip label |
| `bledevicelist_device_broadcast_only` | "Broadcast Only" | Non-connectable status chip label |
| `bledevicelist_device_services` | "Advertised Services" | Services section card title |
| `bledevicelist_device_mfg_data` | "Manufacturer Data" | Manufacturer data section card title |
| `bledevicelist_device_no_services` | "No public services found" | Empty services card subtitle |

## Pre-Implementation Contract

### APIs to Define

To interact with the `blue-falcon` library in common code, define a domain model mapping properties from `BluetoothPeripheral` and advertisement data:

```kotlin
data class BleScannedDevice(
    val name: String,
    val macAddress: String,
    val rssi: Int?,
    val isConnectable: Boolean,
    val advertisedServices: List<String>,
    val manufacturerData: String?
)
```

And a Repository interface supplying the Flow of scanned devices:

```kotlin
interface BleScanRepository {
    val scannedDevices: StateFlow<List<BleScannedDevice>>
    val isScanning: StateFlow<Boolean>

    fun startScan()
    fun stopScan()
}
```

### Koin DI Module
Register the repository and ViewModel in a feature Koin module:

```kotlin
val bleDeviceListModule = module {
    single<BleScanRepository> { BleScanRepositoryImpl(blueFalcon = get()) }
    viewModelOf(::BleDeviceListViewModel)
}
```

## Post-Implementation Checklist

- [ ] Add the `blue-falcon` KMP dependency `implementation("dev.bluefalcon:blue-falcon:3.4.5")` (or latest) to build configurations.
- [ ] Initialize `BlueFalcon` in Android app container (passing `context`) and iOS app container (passing `null`) and provide it as a Koin dependency.
- [ ] Implement `BleScanRepository` using KMP flow collector or delegate listener from `BlueFalcon.peripherals`.
- [ ] Parse BLE advertisement fields on discovery (`LocalName`, `ManufacturerData`, `ServiceUUIDsKey`, `IsConnectable`) and map to `BleScannedDevice`.
- [ ] Handle BLE location and Bluetooth scan permissions natively on both Android (`AndroidManifest.xml` permissions request) and iOS (`Info.plist` descriptions).
- [ ] Bind `BleDeviceListViewModel` state flows to the Composable screen UI.
- [ ] Implement the looping scan spinner animations using Compose animation APIs.
- [ ] Verify that UI matches the success and empty state designs exactly.
- [ ] Run UI integration checks against local simulators/devices.
