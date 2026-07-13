package com.anchor.ugzeroharm.data.datasource.ble

import com.anchor.ugzeroharm.common.Either

interface BleAdvertiserDataSource {
    val isAdvertising: Boolean

    fun startAdvertising(deviceId: String): Either<Unit>

    fun stopAdvertising(): Either<Unit>
}
