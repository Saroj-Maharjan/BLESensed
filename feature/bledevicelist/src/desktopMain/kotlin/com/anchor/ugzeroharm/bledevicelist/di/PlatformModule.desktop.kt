package com.anchor.ugzeroharm.bledevicelist.di

import com.anchor.ugzeroharm.bledevicelist.data.datasource.BleScanner
import com.anchor.ugzeroharm.bledevicelist.data.datasource.BleScannerDesktop
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual val platformModule: Module =
    module {
        single<BleScanner> { BleScannerDesktop() }
    }
