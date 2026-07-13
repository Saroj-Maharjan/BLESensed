package com.anchor.ugzeroharm.bledevicelist.data.datasource

import com.anchor.ugzeroharm.bledevicelist.data.model.BleScannedDevice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BleScannerDesktop : BleScanner {
    private val _scannedDevices = MutableStateFlow<List<BleScannedDevice>>(emptyList())
    override val scannedDevices: StateFlow<List<BleScannedDevice>> = _scannedDevices.asStateFlow()

    private val _isScanning = MutableStateFlow(false)
    override val isScanning: StateFlow<Boolean> = _isScanning.asStateFlow()

    private val scannerScope = CoroutineScope(Dispatchers.Default)
    private var mockScanJob: Job? = null

    override fun startScan() {
        if (_isScanning.value) return
        _isScanning.value = true
        _scannedDevices.value = emptyList()

        mockScanJob =
            scannerScope.launch {
                val mockDevices = mutableListOf<BleScannedDevice>()

                delay(1000)
                if (!_isScanning.value) return@launch
                mockDevices.add(
                    BleScannedDevice(
                        name = "ZeroHarm-Tracker-A1",
                        macAddress = "AA:BB:CC:DD:EE:FF",
                        rssi = -64,
                        isConnectable = true,
                        advertisedServices = listOf("0x180F (Battery)", "0x180A (Device Info)"),
                        manufacturerData = "0x004C (Apple): 02 15 FD 3A 6A 81 A2",
                    ),
                )
                _scannedDevices.value = mockDevices.toList()

                delay(1500)
                if (!_isScanning.value) return@launch
                mockDevices.add(
                    BleScannedDevice(
                        name = "UG-Safety-Sensor",
                        macAddress = "11:22:33:44:55:66",
                        rssi = -42,
                        isConnectable = true,
                        advertisedServices = listOf("0x1803 (Link Loss)"),
                        manufacturerData = "0x00E0 (Google): AA FE 10 00 03",
                    ),
                )
                _scannedDevices.value = mockDevices.toList()

                delay(2000)
                if (!_isScanning.value) return@launch
                mockDevices.add(
                    BleScannedDevice(
                        name = "Unknown Beacon",
                        macAddress = "FA:22:90:33:EE:41",
                        rssi = -88,
                        isConnectable = false,
                        advertisedServices = emptyList(),
                        manufacturerData = "0x0059 (Nordic): 01 09 20 02 54 32",
                    ),
                )
                _scannedDevices.value = mockDevices.toList()
            }
    }

    override fun stopScan() {
        if (!_isScanning.value) return
        _isScanning.value = false
        mockScanJob?.cancel()
        mockScanJob = null
    }
}
