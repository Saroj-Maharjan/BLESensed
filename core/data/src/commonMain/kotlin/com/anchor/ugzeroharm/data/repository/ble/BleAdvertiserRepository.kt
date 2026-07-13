package com.anchor.ugzeroharm.data.repository.ble

import com.anchor.ugzeroharm.common.Either

interface BleAdvertiserRepository {
    val isAdvertising: Boolean

    suspend fun startAdvertising(deviceId: String): Either<Unit>

    suspend fun stopAdvertising(): Either<Unit>
}
