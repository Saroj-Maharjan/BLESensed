package com.anchor.ugzeroharm.permission

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import platform.CoreBluetooth.CBCentralManager
import platform.CoreBluetooth.CBCentralManagerDelegateProtocol
import platform.CoreBluetooth.CBManagerAuthorizationAllowedAlways
import platform.CoreBluetooth.CBManagerStatePoweredOn
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.darwin.NSObject

class PermissionManagerIos : PermissionManager {
    private val _isBluetoothEnabled = MutableStateFlow(false)
    override val isBluetoothEnabled: StateFlow<Boolean> = _isBluetoothEnabled.asStateFlow()

    private val _isLocationEnabled = MutableStateFlow(CLLocationManager.locationServicesEnabled())
    override val isLocationEnabled: StateFlow<Boolean> = _isLocationEnabled.asStateFlow()

    private val centralManager: CBCentralManager
    private val locationManager = CLLocationManager()

    private val btDelegate =
        object : NSObject(), CBCentralManagerDelegateProtocol {
            override fun centralManagerDidUpdateState(central: CBCentralManager) {
                _isBluetoothEnabled.value = (central.state == CBManagerStatePoweredOn)
            }
        }

    private val locDelegate =
        object : NSObject(), CLLocationManagerDelegateProtocol {
            override fun locationManagerDidChangeAuthorization(manager: CLLocationManager) {
                _isLocationEnabled.value = CLLocationManager.locationServicesEnabled()
            }
        }

    init {
        centralManager = CBCentralManager(btDelegate, null)
        locationManager.delegate = locDelegate
    }

    override fun hasBluetoothPermission(): Boolean = CBCentralManager.authorization() == CBManagerAuthorizationAllowedAlways

    override fun hasLocationPermission(): Boolean {
        val status = CLLocationManager.authorizationStatus()
        return status == kCLAuthorizationStatusAuthorizedAlways ||
            status == kCLAuthorizationStatusAuthorizedWhenInUse
    }

    override fun refresh() {
        _isBluetoothEnabled.value = (centralManager.state == CBManagerStatePoweredOn)
        _isLocationEnabled.value = CLLocationManager.locationServicesEnabled()
    }
}
