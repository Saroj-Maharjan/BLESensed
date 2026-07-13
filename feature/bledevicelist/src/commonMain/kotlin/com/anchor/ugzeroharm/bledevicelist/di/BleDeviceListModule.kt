package com.anchor.ugzeroharm.bledevicelist.di

import com.anchor.ugzeroharm.bledevicelist.data.datasource.BleScanner
import com.anchor.ugzeroharm.bledevicelist.data.datasource.KableBleScanner
import com.anchor.ugzeroharm.bledevicelist.data.repository.BleScanRepository
import com.anchor.ugzeroharm.bledevicelist.data.repository.BleScanRepositoryImpl
import com.anchor.ugzeroharm.bledevicelist.presentation.viewmodel.BleDeviceListViewModel
import com.juul.kable.Filter
import com.juul.kable.PlatformAdvertisement
import com.juul.kable.Scanner
import com.juul.kable.logs.Logging
import com.juul.kable.logs.SystemLogEngine
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val bleDeviceListModule =
    module {
        includes(platformModule)
        single<Scanner<PlatformAdvertisement>> {
            Scanner {
                filters {
                    match {
                        manufacturerData =
                            listOf(
                                Filter.ManufacturerData(
                                    0x4C,
                                    byteArrayOf((0x12).toByte(), (0x19).toByte()),
                                    byteArrayOf((0xFF).toByte(), (0x00).toByte()),
                                ),
                            )
                    }
                }

                logging {
                    engine = SystemLogEngine
                    level = Logging.Level.Warnings
                    format = Logging.Format.Multiline
                }
            }
        }
        single<BleScanner> { KableBleScanner(get()) }
        single<BleScanRepository> { BleScanRepositoryImpl(get()) }
        viewModelOf(::BleDeviceListViewModel)
    }
