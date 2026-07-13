package com.anchor.ugzeroharm.bledevicelist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anchor.ugzeroharm.bledevicelist.data.repository.BleScanRepository
import com.anchor.ugzeroharm.bledevicelist.presentation.ui.BleDeviceListUiState
import com.anchor.ugzeroharm.permission.PermissionManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class BleDeviceListViewModel(
    private val repository: BleScanRepository,
    val permissionManager: PermissionManager,
) : ViewModel() {
    private val _uiState = MutableStateFlow<BleDeviceListUiState>(BleDeviceListUiState.Loading)
    val uiState: StateFlow<BleDeviceListUiState> = _uiState.asStateFlow()

    val isScanning: StateFlow<Boolean> = repository.isScanning

    private var devicesJob: Job? = null
    private var scanTimeoutJob: Job? = null
    private var stateObservationJob: Job? = null

    init {
        observePermissionsAndState()
    }

    private fun observePermissionsAndState() {
        stateObservationJob?.cancel()
        stateObservationJob =
            viewModelScope.launch {
                combine(
                    permissionManager.isBluetoothEnabled,
                    permissionManager.isLocationEnabled,
                ) { btEnabled, locEnabled ->
                    Pair(btEnabled, locEnabled)
                }.collect { (btEnabled, locEnabled) ->
                    checkPermissionsAndScanState(btEnabled, locEnabled)
                }
            }
    }

    private fun checkPermissionsAndScanState(
        btEnabled: Boolean,
        locEnabled: Boolean,
    ) {
        val hasBtPerm = permissionManager.hasBluetoothPermission()
        val hasLocPerm = permissionManager.hasLocationPermission()

        when {
            !hasBtPerm || !hasLocPerm -> {
                stopScanning()
                _uiState.value =
                    BleDeviceListUiState.Failed(
                        errorTitle = "Permissions Required",
                        errorMessage = "UGZeroHarm requires Bluetooth and location permissions to scan for nearby safety beacons.",
                        showPermissionGrant = true,
                    )
            }

            !btEnabled -> {
                stopScanning()
                _uiState.value =
                    BleDeviceListUiState.Failed(
                        errorTitle = "Bluetooth Disabled",
                        errorMessage = "Please enable Bluetooth in your device settings to scan for safety beacons.",
                        showPermissionGrant = false,
                    )
            }

            !locEnabled -> {
                stopScanning()
                _uiState.value =
                    BleDeviceListUiState.Failed(
                        errorTitle = "Location Services Disabled",
                        errorMessage = "Please enable Location services in your device settings to scan for safety beacons.",
                        showPermissionGrant = false,
                    )
            }

            else -> {
                startScanningInternal()
            }
        }
    }

    fun startScanning() {
        observePermissionsAndState()
    }

    private fun startScanningInternal() {
        scanTimeoutJob?.cancel()
        _uiState.value = BleDeviceListUiState.Loading
        repository.startScan()
        observeDevices()

        // Transition from Loading to Empty if no devices found within 2 seconds
        scanTimeoutJob =
            viewModelScope.launch {
                delay(2000)
                if (_uiState.value is BleDeviceListUiState.Loading && repository.scannedDevices.value.isEmpty()) {
                    _uiState.value = BleDeviceListUiState.Empty
                }
            }
    }

    fun stopScanning() {
        repository.stopScan()
        scanTimeoutJob?.cancel()
        devicesJob?.cancel()
    }

    private fun observeDevices() {
        devicesJob?.cancel()
        devicesJob =
            viewModelScope.launch {
                repository.scannedDevices.collect { devices ->
                    if (permissionManager.hasBluetoothPermission() && permissionManager.hasLocationPermission()) {
                        if (devices.isEmpty()) {
                            if (_uiState.value !is BleDeviceListUiState.Loading) {
                                _uiState.value = BleDeviceListUiState.Empty
                            }
                        } else {
                            _uiState.value = BleDeviceListUiState.Success(devices)
                        }
                    }
                }
            }
    }

    override fun onCleared() {
        super.onCleared()
        stateObservationJob?.cancel()
        stopScanning()
    }
}
