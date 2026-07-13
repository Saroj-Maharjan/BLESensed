package com.anchor.ugzeroharm.permissionscreen.di

import com.anchor.ugzeroharm.permissionscreen.presentation.viewmodel.PermissionScreenViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val permissionScreenModule: Module =
    module {
        viewModelOf(::PermissionScreenViewModel)
    }
