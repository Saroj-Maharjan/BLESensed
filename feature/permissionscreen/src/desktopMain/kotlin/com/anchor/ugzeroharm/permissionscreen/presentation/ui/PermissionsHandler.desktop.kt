package com.anchor.ugzeroharm.permissionscreen.presentation.ui

import androidx.compose.runtime.Composable

@Composable
actual fun handlePermissions(onPermissionsGranted: () -> Unit): () -> Unit =
    {
        onPermissionsGranted()
    }

@Composable
actual fun rememberSettingsLauncher(type: SettingsType): () -> Unit = {}
