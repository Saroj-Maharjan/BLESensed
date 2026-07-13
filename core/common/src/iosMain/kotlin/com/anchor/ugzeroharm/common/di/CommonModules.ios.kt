package com.anchor.ugzeroharm.common.di

import com.anchor.ugzeroharm.common.util.IOSLinkHandler
import com.anchor.ugzeroharm.common.util.LinkHandler
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Created by Ali Sadeghi
 * on 07,May,2025
 */

internal actual val commonPlatformModule: Module =
    module {
        singleOf(::IOSLinkHandler).bind<LinkHandler>()
    }
