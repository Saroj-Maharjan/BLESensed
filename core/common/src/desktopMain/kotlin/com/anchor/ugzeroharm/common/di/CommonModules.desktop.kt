package com.anchor.ugzeroharm.common.di

import com.anchor.ugzeroharm.common.util.DesktopLinkHandler
import com.anchor.ugzeroharm.common.util.LinkHandler
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal actual val commonPlatformModule: Module =
    module {
        singleOf(::DesktopLinkHandler).bind<LinkHandler>()
    }
