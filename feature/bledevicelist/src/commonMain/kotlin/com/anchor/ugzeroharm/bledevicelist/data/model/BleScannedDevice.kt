package com.anchor.ugzeroharm.bledevicelist.data.model

data class BleScannedDevice(
    val name: String,
    val macAddress: String,
    val rssi: Int?,
    val isConnectable: Boolean,
    val advertisedServices: List<String>,
    val manufacturerData: String?,
)
