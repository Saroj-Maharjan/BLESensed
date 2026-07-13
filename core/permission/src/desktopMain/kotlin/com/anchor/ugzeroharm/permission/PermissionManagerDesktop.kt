package com.anchor.ugzeroharm.permission

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PermissionManagerDesktop : PermissionManager {
    override val isBluetoothEnabled: StateFlow<Boolean> = MutableStateFlow(true).asStateFlow()
    override val isLocationEnabled: StateFlow<Boolean> = MutableStateFlow(true).asStateFlow()

    override fun hasBluetoothPermission(): Boolean = true

    override fun hasLocationPermission(): Boolean = true
}
