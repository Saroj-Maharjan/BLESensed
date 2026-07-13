package com.anchor.ugzeroharm.data.di

import com.anchor.ugzeroharm.data.datasource.ble.BleAdvertiserDataSource
import com.anchor.ugzeroharm.data.datasource.ble.BleAdvertiserDataSourceAndroid
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal actual val platformLocalDataSourceModule =
    module {
        single<String>(qualifier = DataStorePathStringQualifier) {
            androidApplication().filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath
        }
        singleOf(::BleAdvertiserDataSourceAndroid).bind<BleAdvertiserDataSource>()
    }
