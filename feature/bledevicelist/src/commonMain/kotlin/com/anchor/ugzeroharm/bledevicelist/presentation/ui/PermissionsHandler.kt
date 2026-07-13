package com.anchor.ugzeroharm.bledevicelist.presentation.ui

import androidx.compose.runtime.Composable

@Composable
expect fun handlePermissions(onPermissionsGranted: () -> Unit): () -> Unit
