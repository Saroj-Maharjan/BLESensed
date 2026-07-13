package com.anchor.ugzeroharm.permission.di

import com.anchor.ugzeroharm.permission.PermissionManager
import com.anchor.ugzeroharm.permission.PermissionManagerDesktop
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual val platformPermissionModule: Module =
    module {
        single<PermissionManager> { PermissionManagerDesktop() }
    }
