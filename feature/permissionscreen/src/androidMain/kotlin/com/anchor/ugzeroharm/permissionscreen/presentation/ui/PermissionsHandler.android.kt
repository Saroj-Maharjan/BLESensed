package com.anchor.ugzeroharm.permissionscreen.presentation.ui

import android.Manifest
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun handlePermissions(onPermissionsGranted: () -> Unit): () -> Unit {
    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions(),
        ) { permissions ->
            val allGranted = permissions.values.all { it }
            if (allGranted) {
                onPermissionsGranted()
            }
        }

    return {
        val neededPermissions =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                arrayOf(
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.BLUETOOTH_ADVERTISE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                )
            } else {
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                )
            }
        launcher.launch(neededPermissions)
    }
}

@Composable
actual fun rememberSettingsLauncher(type: SettingsType): () -> Unit {
    val context = LocalContext.current
    return {
        val action =
            when (type) {
                SettingsType.BLUETOOTH -> Settings.ACTION_BLUETOOTH_SETTINGS
                SettingsType.LOCATION -> Settings.ACTION_LOCATION_SOURCE_SETTINGS
            }
        val intent =
            Intent(action).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
        context.startActivity(intent)
    }
}
