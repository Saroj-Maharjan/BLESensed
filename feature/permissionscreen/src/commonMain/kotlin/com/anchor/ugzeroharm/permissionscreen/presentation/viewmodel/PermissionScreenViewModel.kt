package com.anchor.ugzeroharm.permissionscreen.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anchor.ugzeroharm.common.setState
import com.anchor.ugzeroharm.permission.PermissionManager
import com.anchor.ugzeroharm.permissionscreen.presentation.ui.PermissionScreenUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class PermissionScreenViewModel(
    private val permissionManager: PermissionManager,
) : ViewModel() {
    private val _uiModel = MutableStateFlow(PermissionScreenUiModel())
    val uiModel: StateFlow<PermissionScreenUiModel> = _uiModel.asStateFlow()

    init {
        observePermissionStates()
        refresh()
    }

    private fun observePermissionStates() {
        viewModelScope.launch {
            combine(
                permissionManager.isBluetoothEnabled,
                permissionManager.isLocationEnabled,
            ) { btEnabled, locEnabled ->
                Pair(btEnabled, locEnabled)
            }.collect { (btEnabled, locEnabled) ->
                _uiModel.setState {
                    copy(
                        isBluetoothEnabled = btEnabled,
                        isLocationEnabled = locEnabled,
                        hasBluetoothPermission = permissionManager.hasBluetoothPermission(),
                        hasLocationPermission = permissionManager.hasLocationPermission(),
                    )
                }
            }
        }
    }

    fun refresh() {
        permissionManager.refresh()
        val hasBt = permissionManager.hasBluetoothPermission()
        val hasLoc = permissionManager.hasLocationPermission()
        _uiModel.setState {
            copy(
                hasBluetoothPermission = hasBt,
                hasLocationPermission = hasLoc,
                isBluetoothEnabled = permissionManager.isBluetoothEnabled.value,
                isLocationEnabled = permissionManager.isLocationEnabled.value,
            )
        }
    }
}
