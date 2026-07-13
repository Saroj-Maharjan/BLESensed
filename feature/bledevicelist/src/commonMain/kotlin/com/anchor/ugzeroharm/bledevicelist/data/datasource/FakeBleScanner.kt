package com.anchor.ugzeroharm.bledevicelist.data.datasource

import com.anchor.ugzeroharm.bledevicelist.data.model.BleScannedDevice
import kotlinx.coroutines.flow.StateFlow

class FakeBleScanner : BleScanner {
    override val scannedDevices: StateFlow<List<BleScannedDevice>>
        get() = TODO("Not yet implemented")
    override val isScanning: StateFlow<Boolean>
        get() = TODO("Not yet implemented")

    override fun startScan() {
        TODO("Not yet implemented")
    }

    override fun stopScan() {
        TODO("Not yet implemented")
    }
}
