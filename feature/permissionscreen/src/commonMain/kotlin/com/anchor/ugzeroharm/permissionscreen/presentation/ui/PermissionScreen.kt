package com.anchor.ugzeroharm.permissionscreen.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anchor.ugzeroharm.designsystem.XScreen
import com.anchor.ugzeroharm.designsystem.XText
import com.anchor.ugzeroharm.designsystem.toolbar.XTopAppBar
import com.anchor.ugzeroharm.permissionscreen.presentation.ui.components.PermissionStatusCard
import com.anchor.ugzeroharm.permissionscreen.presentation.viewmodel.PermissionScreenViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ugzeroharm.feature.permissionscreen.generated.resources.Res
import ugzeroharm.feature.permissionscreen.generated.resources.bluetooth_searching
import ugzeroharm.feature.permissionscreen.generated.resources.btn_enable_bluetooth
import ugzeroharm.feature.permissionscreen.generated.resources.btn_enable_location
import ugzeroharm.feature.permissionscreen.generated.resources.btn_grant
import ugzeroharm.feature.permissionscreen.generated.resources.location_on
import ugzeroharm.feature.permissionscreen.generated.resources.permission_bt_desc
import ugzeroharm.feature.permissionscreen.generated.resources.permission_bt_service_desc
import ugzeroharm.feature.permissionscreen.generated.resources.permission_bt_service_title
import ugzeroharm.feature.permissionscreen.generated.resources.permission_bt_title
import ugzeroharm.feature.permissionscreen.generated.resources.permission_loc_desc
import ugzeroharm.feature.permissionscreen.generated.resources.permission_loc_service_desc
import ugzeroharm.feature.permissionscreen.generated.resources.permission_loc_service_title
import ugzeroharm.feature.permissionscreen.generated.resources.permission_loc_title
import ugzeroharm.feature.permissionscreen.generated.resources.permission_subtitle
import ugzeroharm.feature.permissionscreen.generated.resources.permission_title

@Composable
fun PermissionScreen(
    viewModel: PermissionScreenViewModel,
    onAllPermissionsGranted: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiModel by viewModel.uiModel.collectAsState()

    // Auto-forward once all conditions are met
    LaunchedEffect(uiModel) {
        if (uiModel.areAllPermissionsAndServicesGranted()) {
            onAllPermissionsGranted()
        }
    }

    val permissionLauncher =
        handlePermissions(
            onPermissionsGranted = { viewModel.refresh() },
        )

    val bluetoothSettingsLauncher = rememberSettingsLauncher(SettingsType.BLUETOOTH)
    val locationSettingsLauncher = rememberSettingsLauncher(SettingsType.LOCATION)

    PermissionScreenRoot(
        uiModel = uiModel,
        onRequestPermissions = { permissionLauncher() },
        onOpenBluetoothSettings = { bluetoothSettingsLauncher() },
        onOpenLocationSettings = { locationSettingsLauncher() },
        modifier = modifier,
    )
}

@Composable
fun PermissionScreenRoot(
    uiModel: PermissionScreenUiModel,
    onRequestPermissions: () -> Unit,
    onOpenBluetoothSettings: () -> Unit,
    onOpenLocationSettings: () -> Unit,
    modifier: Modifier = Modifier,
) {
    XScreen(
        modifier = modifier,
        topBar = {
            XTopAppBar(
                title = {
                    XText(
                        text = stringResource(Res.string.permission_title),
                        style =
                            MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                            ),
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                navigationIcon = null,
                actions = {},
            )
        },
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            // Subtitle Description
            XText(
                text = stringResource(Res.string.permission_subtitle),
                style =
                    MaterialTheme.typography.bodyMedium.copy(
                        lineHeight = 22.sp,
                    ),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            )

            // Bluetooth Permission Card
            PermissionStatusCard(
                title = stringResource(Res.string.permission_bt_title),
                description = stringResource(Res.string.permission_bt_desc),
                isActive = uiModel.hasBluetoothPermission,
                isService = false,
                icon = painterResource(Res.drawable.bluetooth_searching),
                actionLabel = stringResource(Res.string.btn_grant),
                onActionClick = onRequestPermissions,
            )

            // Location Permission Card
            PermissionStatusCard(
                title = stringResource(Res.string.permission_loc_title),
                description = stringResource(Res.string.permission_loc_desc),
                isActive = uiModel.hasLocationPermission,
                isService = false,
                icon = painterResource(Res.drawable.location_on),
                actionLabel = stringResource(Res.string.btn_grant),
                onActionClick = onRequestPermissions,
            )

            // Bluetooth Service Card
            PermissionStatusCard(
                title = stringResource(Res.string.permission_bt_service_title),
                description = stringResource(Res.string.permission_bt_service_desc),
                isActive = uiModel.isBluetoothEnabled,
                isService = true,
                icon = painterResource(Res.drawable.bluetooth_searching),
                actionLabel = stringResource(Res.string.btn_enable_bluetooth),
                onActionClick = onOpenBluetoothSettings,
            )

            // Location Service Card
            PermissionStatusCard(
                title = stringResource(Res.string.permission_loc_service_title),
                description = stringResource(Res.string.permission_loc_service_desc),
                isActive = uiModel.isLocationEnabled,
                isService = true,
                icon = painterResource(Res.drawable.location_on),
                actionLabel = stringResource(Res.string.btn_enable_location),
                onActionClick = onOpenLocationSettings,
            )
        }
    }
}
