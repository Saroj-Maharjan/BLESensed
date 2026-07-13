package com.anchor.ugzeroharm.data.di

import com.anchor.ugzeroharm.data.datasource.ble.BleAdvertiserDataSource
import com.anchor.ugzeroharm.data.datasource.ble.BleAdvertiserDataSourceDesktop
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal actual val platformLocalDataSourceModule =
    module {
        singleOf(::BleAdvertiserDataSourceDesktop).bind<BleAdvertiserDataSource>()
    }
