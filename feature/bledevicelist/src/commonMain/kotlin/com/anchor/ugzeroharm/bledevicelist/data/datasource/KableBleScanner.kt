package com.anchor.ugzeroharm.bledevicelist.data.datasource

import com.anchor.ugzeroharm.bledevicelist.data.model.BleScannedDevice
import com.juul.kable.PlatformAdvertisement
import com.juul.kable.Scanner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class KableBleScanner(
    private val scanner: Scanner<PlatformAdvertisement>,
) : BleScanner {
    private val _scannedDevices = MutableStateFlow<List<BleScannedDevice>>(emptyList())
    override val scannedDevices: StateFlow<List<BleScannedDevice>> = _scannedDevices.asStateFlow()

    private val _isScanning = MutableStateFlow(false)
    override val isScanning: StateFlow<Boolean> = _isScanning.asStateFlow()

    private val scannerScope = CoroutineScope(Dispatchers.Default)
    private var scanJob: Job? = null

    private val devicesMutex = Mutex()
    private val foundDevices = mutableMapOf<String, BleScannedDevice>()

    override fun startScan() {
        if (_isScanning.value) return
        _isScanning.value = true
        _scannedDevices.value = emptyList()
        foundDevices.clear()

        scanJob =
            scannerScope.launch {
                try {
                    scanner.advertisements.collect { advertisement ->
                        val device =
                            BleScannedDevice(
                                name = advertisement.name ?: "Unknown Device",
                                macAddress = advertisement.identifier.toString(),
                                rssi = advertisement.rssi,
                                isConnectable = true,
                                advertisedServices = advertisement.uuids.map { it.toHexString() },
                                manufacturerData = advertisement.manufacturerData?.data?.contentToString(),
                            )

                        devicesMutex.withLock {
                            foundDevices[device.macAddress] = device
                            _scannedDevices.value =
                                foundDevices.values.toList().sortedByDescending { it.rssi ?: -100 }
                        }
                    }
                } catch (ex: Exception) {
                    _isScanning.value = false
                    ex.printStackTrace()
                }
            }
    }

    override fun stopScan() {
        if (!_isScanning.value) return
        _isScanning.value = false
        scanJob?.cancel()
        scanJob = null
    }
}
