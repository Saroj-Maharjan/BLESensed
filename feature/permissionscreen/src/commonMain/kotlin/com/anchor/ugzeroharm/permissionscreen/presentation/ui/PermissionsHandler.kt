package com.anchor.ugzeroharm.permissionscreen.presentation.ui

import androidx.compose.runtime.Composable

enum class SettingsType {
    BLUETOOTH,
    LOCATION,
}

@Composable
expect fun handlePermissions(onPermissionsGranted: () -> Unit): () -> Unit

@Composable
expect fun rememberSettingsLauncher(type: SettingsType): () -> Unit
