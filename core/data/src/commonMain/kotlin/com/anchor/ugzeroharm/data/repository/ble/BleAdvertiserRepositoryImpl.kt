package com.anchor.ugzeroharm.data.repository.ble

import com.anchor.ugzeroharm.common.Either
import com.anchor.ugzeroharm.data.datasource.ble.BleAdvertiserDataSource

class BleAdvertiserRepositoryImpl(
    private val advertiserDataSource: BleAdvertiserDataSource,
) : BleAdvertiserRepository {
    override val isAdvertising: Boolean
        get() = advertiserDataSource.isAdvertising

    override suspend fun startAdvertising(deviceId: String): Either<Unit> = advertiserDataSource.startAdvertising(deviceId)

    override suspend fun stopAdvertising(): Either<Unit> = advertiserDataSource.stopAdvertising()
}
