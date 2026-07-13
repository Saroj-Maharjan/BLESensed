package com.anchor.ugzeroharm.permission

import kotlinx.coroutines.flow.StateFlow

interface PermissionManager {
    fun hasBluetoothPermission(): Boolean

    fun hasLocationPermission(): Boolean

    val isBluetoothEnabled: StateFlow<Boolean>
    val isLocationEnabled: StateFlow<Boolean>

    fun refresh()
}
