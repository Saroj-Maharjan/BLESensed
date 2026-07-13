package com.anchor.ugzeroharm.data.datasource.ble

import com.anchor.ugzeroharm.common.Either
import com.anchor.ugzeroharm.common.ErrorModel
import platform.CoreBluetooth.CBAdvertisementDataLocalNameKey
import platform.CoreBluetooth.CBPeripheralManager
import platform.CoreBluetooth.CBPeripheralManagerDelegateProtocol
import platform.CoreBluetooth.CBPeripheralManagerStatePoweredOn
import platform.darwin.NSObject

class BleAdvertiserDataSourceIos : BleAdvertiserDataSource {
    private var _isAdvertising = false
    override val isAdvertising: Boolean
        get() = _isAdvertising

    private var peripheralManager: CBPeripheralManager? = null
    private var pendingDeviceId: String? = null

    private val delegate =
        object : NSObject(), CBPeripheralManagerDelegateProtocol {
            override fun peripheralManagerDidUpdateState(peripheral: CBPeripheralManager) {
                if (peripheral.state == CBPeripheralManagerStatePoweredOn) {
                    pendingDeviceId?.let { deviceId ->
                        startAdvertisingInternal(deviceId)
                        pendingDeviceId = null
                    }
                }
            }
        }

    override fun startAdvertising(deviceId: String): Either<Unit> {
        if (peripheralManager == null) {
            peripheralManager = CBPeripheralManager(delegate = delegate, queue = null)
        }

        val manager = peripheralManager!!
        if (manager.state == CBPeripheralManagerStatePoweredOn) {
            return startAdvertisingInternal(deviceId)
        } else {
            pendingDeviceId = deviceId
            return Either.Success(Unit)
        }
    }

    private fun startAdvertisingInternal(deviceId: String): Either<Unit> {
        val manager = peripheralManager ?: return Either.Failure(ErrorModel.Message("PeripheralManager not initialized"))

        stopAdvertising()

        val advertisementData =
            mapOf<Any?, Any?>(
                CBAdvertisementDataLocalNameKey to deviceId,
            )

        return try {
            manager.startAdvertising(advertisementData)
            _isAdvertising = true
            Either.Success(Unit)
        } catch (e: Exception) {
            Either.Failure(ErrorModel.Exception(e))
        }
    }

    override fun stopAdvertising(): Either<Unit> {
        peripheralManager?.let { manager ->
            if (manager.isAdvertising) {
                manager.stopAdvertising()
            }
        }
        _isAdvertising = false
        pendingDeviceId = null
        return Either.Success(Unit)
    }
}
