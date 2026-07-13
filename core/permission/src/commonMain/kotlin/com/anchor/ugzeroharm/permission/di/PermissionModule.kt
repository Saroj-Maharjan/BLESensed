package com.anchor.ugzeroharm.permission.di

import org.koin.dsl.module

val permissionModule =
    module {
        includes(platformPermissionModule)
    }
