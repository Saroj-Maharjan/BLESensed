package com.anchor.ugzeroharm.data.datasource.ble

import com.anchor.ugzeroharm.common.Either

class BleAdvertiserDataSourceDesktop : BleAdvertiserDataSource {
    private var _isAdvertising = false
    override val isAdvertising: Boolean
        get() = _isAdvertising

    override fun startAdvertising(deviceId: String): Either<Unit> {
        _isAdvertising = true
        return Either.Success(Unit)
    }

    override fun stopAdvertising(): Either<Unit> {
        _isAdvertising = false
        return Either.Success(Unit)
    }
}
