@file:OptIn(ExperimentalForeignApi::class)

package com.anchor.ugzeroharm.data.di

import com.anchor.ugzeroharm.data.datasource.ble.BleAdvertiserDataSource
import com.anchor.ugzeroharm.data.datasource.ble.BleAdvertiserDataSourceIos
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

internal actual val platformLocalDataSourceModule: Module =
    module {
        single<String>(qualifier = DataStorePathStringQualifier) {
            val directory =
                NSFileManager.defaultManager.URLForDirectory(
                    directory = NSDocumentDirectory,
                    inDomain = NSUserDomainMask,
                    appropriateForURL = null,
                    create = false,
                    error = null,
                )
            requireNotNull(directory).path + "/$DATA_STORE_FILE_NAME"
        }
        singleOf(::BleAdvertiserDataSourceIos).bind<BleAdvertiserDataSource>()
    }
