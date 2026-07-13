package com.anchor.ugzeroharm.bledevicelist.presentation.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anchor.ugzeroharm.bledevicelist.data.model.BleScannedDevice
import com.anchor.ugzeroharm.bledevicelist.presentation.viewmodel.BleDeviceListViewModel
import com.anchor.ugzeroharm.designsystem.*
import com.anchor.ugzeroharm.designsystem.app.AppErrorState
import com.anchor.ugzeroharm.designsystem.app.AppLoadingState
import com.anchor.ugzeroharm.designsystem.toolbar.XTopAppBar
import com.anchor.ugzeroharm.designsystem.toolbar.XTopAppBarAlignment
import org.jetbrains.compose.resources.painterResource
import ugzeroharm.feature.bledevicelist.generated.resources.*

@Composable
fun BleDeviceListRoute(
    viewModel: BleDeviceListViewModel,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()
    val isScanning by viewModel.isScanning.collectAsState()
    var showRationaleDialog by remember { mutableStateOf(false) }

    val permissionLauncher =
        handlePermissions(
            onPermissionsGranted = { viewModel.startScanning() },
        )

    if (showRationaleDialog) {
        SimpleDialog(
            onDismissRequest = { showRationaleDialog = false },
            title = { XText(text = "Permissions Required") },
            content = {
                XText(
                    text = "UGZeroHarm requires Bluetooth and Location permissions to scan for nearby safety beacons and zero-harm devices. Please grant these permissions in the next prompt.",
                    style = MaterialTheme.typography.bodyMedium,
                )
            },
            positiveAction = {
                XButton(
                    onClick = {
                        showRationaleDialog = false
                        permissionLauncher()
                    },
                ) {
                    XText("Proceed")
                }
            },
            negativeAction = {
                XOutlinedButton(
                    onClick = { showRationaleDialog = false },
                ) {
                    XText("Cancel")
                }
            },
        )
    }

    BleDeviceListScreen(
        uiState = uiState,
        isScanning = isScanning,
        onStartScan = {
            if (viewModel.permissionManager.hasBluetoothPermission() && viewModel.permissionManager.hasLocationPermission()) {
                viewModel.startScanning()
            } else {
                showRationaleDialog = true
            }
        },
        onStopScan = { viewModel.stopScanning() },
        onSettingsClick = onSettingsClick,
        onRequestPermissions = { showRationaleDialog = true },
        modifier = modifier,
    )
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BleDeviceListScreen(
    uiState: BleDeviceListUiState,
    isScanning: Boolean,
    onStartScan: () -> Unit,
    onStopScan: () -> Unit,
    onSettingsClick: () -> Unit,
    onRequestPermissions: () -> Unit,
    modifier: Modifier = Modifier,
) {
    XScaffold(
        modifier = modifier,
        topBar = {
            XTopAppBar(
                title = { XText(text = "BLE Devices") },
                navigationIcon = {
                    IconButton(onClick = onSettingsClick) {
                        XIcon(
                            painter = painterResource(Res.drawable.settings),
                            contentDescription = "Settings",
                        )
                    }
                },
                alignment = XTopAppBarAlignment.Center,
            )
        },
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background),
        ) {
            // Scanner Status Banner
            StatusBanner(
                isScanning = isScanning,
                onStartScan = onStartScan,
                onStopScan = onStopScan,
            )

            // Screen Content based on state
            Box(modifier = Modifier.fillMaxSize()) {
                when (uiState) {
                    is BleDeviceListUiState.Loading -> {
                        AppLoadingState()
                    }
                    is BleDeviceListUiState.Empty -> {
                        EmptyDevicesView()
                    }
                    is BleDeviceListUiState.Success -> {
                        DevicesListView(devices = uiState.devices)
                    }
                    is BleDeviceListUiState.Failed -> {
                        if (uiState.showPermissionGrant) {
                            AppErrorState(
                                title = uiState.errorTitle,
                                message = uiState.errorMessage,
                                onRetry = onRequestPermissions,
                                retryLabel = "Grant Permission",
                            )
                        } else {
                            AppErrorState(
                                title = uiState.errorTitle,
                                message = uiState.errorMessage,
                                onRetry = onStartScan,
                                retryLabel = "Retry Scan",
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatusBanner(
    isScanning: Boolean,
    onStartScan: () -> Unit,
    onStopScan: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                if (isScanning) {
                    // Rotating scanning radar icon
                    val infiniteTransition = rememberInfiniteTransition()
                    val rotation by infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = 360f,
                        animationSpec =
                            infiniteRepeatable(
                                animation = tween(2000, easing = LinearEasing),
                                repeatMode = RepeatMode.Restart,
                            ),
                    )
                    XIcon(
                        painter = painterResource(Res.drawable.radar),
                        contentDescription = "Scanning",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier =
                            Modifier
                                .size(24.dp)
                                .rotate(rotation),
                    )
                    XText(
                        text = "Scanning for devices...",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary,
                    )
                } else {
                    XIcon(
                        painter = painterResource(Res.drawable.radar_fill),
                        contentDescription = "Scan Stopped",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                        modifier = Modifier.size(24.dp),
                    )
                    XText(
                        text = "Scan Stopped",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            Button(
                onClick = { if (isScanning) onStopScan() else onStartScan() },
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = if (isScanning) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.primary,
                        contentColor = if (isScanning) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onPrimary,
                    ),
                shape = CircleShape,
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            ) {
                XText(
                    text = if (isScanning) "Stop Scan" else "Start Scan",
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }
}

@Composable
private fun EmptyDevicesView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(32.dp),
        ) {
            Box(
                modifier =
                    Modifier
                        .background(MaterialTheme.colorScheme.surfaceVariant, shape = CircleShape)
                        .padding(24.dp),
                contentAlignment = Alignment.Center,
            ) {
                XIcon(
                    painter = painterResource(Res.drawable.search_off),
                    contentDescription = "No Devices",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                    modifier = Modifier.size(60.dp),
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                XText(
                    text = "No devices found",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                XText(
                    text = "Start scanning to discover BLE devices",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DevicesListView(devices: List<BleScannedDevice>) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize(),
    ) {
        items(devices, key = { it.macAddress }) { device ->
            DeviceCard(device = device)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DeviceCard(device: BleScannedDevice) {
    XCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                ) {
                    XText(
                        text = device.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    XText(
                        text = device.macAddress,
                        style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    // RSSI Badge
                    val rssi = device.rssi
                    val signalIcon =
                        if (rssi != null && rssi > -60) {
                            Res.drawable.signal_cellular_alt
                        } else {
                            Res.drawable.signal_cellular_alt_2_bar
                        }

                    Row(
                        modifier =
                            Modifier
                                .background(MaterialTheme.colorScheme.secondaryContainer, shape = RoundedCornerShape(4.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        XIcon(
                            painter = painterResource(signalIcon),
                            contentDescription = "Signal Strength",
                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.size(12.dp),
                        )
                        XText(
                            text = "${rssi ?: -100} dBm",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                        )
                    }

                    // Connectability Badge
                    val connectableColor =
                        if (device.isConnectable) {
                            Color(0xFF4ADE80) // success
                        } else {
                            Color(0xFFFF6B6B) // danger
                        }
                    val connectableText = if (device.isConnectable) "Connectable" else "Broadcast Only"

                    Box(
                        modifier =
                            Modifier
                                .border(1.dp, connectableColor, shape = RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp),
                    ) {
                        XText(
                            text = connectableText,
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                            fontWeight = FontWeight.Bold,
                            color = connectableColor,
                        )
                    }
                }
            }

            XHorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

            // Services
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                XText(
                    text = "ADVERTISED SERVICES",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                )

                if (device.advertisedServices.isEmpty()) {
                    XText(
                        text = "No public services found",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                    )
                } else {
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        device.advertisedServices.forEach { service ->
                            Box(
                                modifier =
                                    Modifier
                                        .background(MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(4.dp))
                                        .padding(horizontal = 8.dp, vertical = 4.dp),
                            ) {
                                XText(
                                    text = service,
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp, fontFamily = FontFamily.Monospace),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }
                    }
                }
            }

            // Manufacturer Data
            if (device.manufacturerData != null) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    XText(
                        text = "MANUFACTURER DATA",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    )
                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f), shape = RoundedCornerShape(6.dp))
                                .padding(8.dp),
                    ) {
                        XText(
                            text = device.manufacturerData,
                            style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }
        }
    }
}
