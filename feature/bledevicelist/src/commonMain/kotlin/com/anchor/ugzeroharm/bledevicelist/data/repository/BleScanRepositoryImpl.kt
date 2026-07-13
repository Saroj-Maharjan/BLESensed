package com.anchor.ugzeroharm.bledevicelist.data.repository

import com.anchor.ugzeroharm.bledevicelist.data.datasource.BleScanner
import com.anchor.ugzeroharm.bledevicelist.data.model.BleScannedDevice
import kotlinx.coroutines.flow.StateFlow

class BleScanRepositoryImpl(
    private val bleScanner: BleScanner,
) : BleScanRepository {
    override val scannedDevices: StateFlow<List<BleScannedDevice>> = bleScanner.scannedDevices
    override val isScanning: StateFlow<Boolean> = bleScanner.isScanning

    override fun startScan() {
        bleScanner.startScan()
    }

    override fun stopScan() {
        bleScanner.stopScan()
    }
}
