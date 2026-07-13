package com.anchor.ugzeroharm.bledevicelist.data.datasource

import com.anchor.ugzeroharm.bledevicelist.data.model.BleScannedDevice
import kotlinx.coroutines.flow.StateFlow

interface BleScanner {
    val scannedDevices: StateFlow<List<BleScannedDevice>>
    val isScanning: StateFlow<Boolean>

    fun startScan()

    fun stopScan()
}
