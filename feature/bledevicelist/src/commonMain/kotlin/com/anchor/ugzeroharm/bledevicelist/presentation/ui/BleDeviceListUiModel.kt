package com.anchor.ugzeroharm.bledevicelist.presentation.ui

import com.anchor.ugzeroharm.bledevicelist.data.model.BleScannedDevice

sealed interface BleDeviceListUiState {
    object Loading : BleDeviceListUiState

    object Empty : BleDeviceListUiState

    data class Success(
        val devices: List<BleScannedDevice>,
    ) : BleDeviceListUiState

    data class Failed(
        val errorTitle: String,
        val errorMessage: String,
        val showPermissionGrant: Boolean = false,
    ) : BleDeviceListUiState
}
