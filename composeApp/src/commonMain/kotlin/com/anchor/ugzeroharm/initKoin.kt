package com.anchor.ugzeroharm

import com.anchor.ugzeroharm.bledevicelist.di.bleDeviceListModule
import com.anchor.ugzeroharm.common.di.commonModule
import com.anchor.ugzeroharm.data.config.BuildOptionProvider
import com.anchor.ugzeroharm.data.di.dataModule
import com.anchor.ugzeroharm.login.di.loginModule
import com.anchor.ugzeroharm.permission.di.permissionModule
import com.anchor.ugzeroharm.permissionscreen.di.permissionScreenModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

private val appModule =
    module {
        singleOf(::BuildOptionProviderImpl).bind<BuildOptionProvider>()
    }

fun initKoin(appDeclaration: KoinAppDeclaration = {}): KoinApplication =
    startKoin {
        appDeclaration()
        modules(
            appModule,
            commonModule,
            dataModule,
            permissionModule,
            bleDeviceListModule,
            loginModule,
            permissionScreenModule,
        )
    }
