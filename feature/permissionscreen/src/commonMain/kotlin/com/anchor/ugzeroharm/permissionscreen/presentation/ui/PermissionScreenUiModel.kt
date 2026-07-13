package com.anchor.ugzeroharm.permissionscreen.presentation.ui

data class PermissionScreenUiModel(
    val hasBluetoothPermission: Boolean = false,
    val hasLocationPermission: Boolean = false,
    val isBluetoothEnabled: Boolean = false,
    val isLocationEnabled: Boolean = false,
) {
    fun areAllPermissionsAndServicesGranted(): Boolean =
        hasBluetoothPermission && hasLocationPermission && isBluetoothEnabled && isLocationEnabled
}
