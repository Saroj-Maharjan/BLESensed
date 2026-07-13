package com.anchor.ugzeroharm.bledevicelist.presentation.ui

import androidx.compose.runtime.Composable

@Composable
actual fun handlePermissions(onPermissionsGranted: () -> Unit): () -> Unit =
    {
        onPermissionsGranted()
    }
