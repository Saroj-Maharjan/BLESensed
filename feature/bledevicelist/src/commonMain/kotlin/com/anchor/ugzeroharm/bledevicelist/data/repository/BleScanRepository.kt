package com.anchor.ugzeroharm.bledevicelist.data.repository

import com.anchor.ugzeroharm.bledevicelist.data.model.BleScannedDevice
import kotlinx.coroutines.flow.StateFlow

interface BleScanRepository {
    val scannedDevices: StateFlow<List<BleScannedDevice>>
    val isScanning: StateFlow<Boolean>

    fun startScan()

    fun stopScan()
}
