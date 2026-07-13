package com.anchor.ugzeroharm.designsystem

import ugzeroharm.core.designsystem.generated.resources.Res
import ugzeroharm.core.designsystem.generated.resources.arrow_back
import ugzeroharm.core.designsystem.generated.resources.bolt
import ugzeroharm.core.designsystem.generated.resources.cd_back
import ugzeroharm.core.designsystem.generated.resources.chevron_right
import ugzeroharm.core.designsystem.generated.resources.ds_image_placeholder
import ugzeroharm.core.designsystem.generated.resources.failed_background
import ugzeroharm.core.designsystem.generated.resources.ic_ug_zero_harm
import ugzeroharm.core.designsystem.generated.resources.retry_label
import ugzeroharm.core.designsystem.generated.resources.warning

/**
 * Created by Ali Sadeghi
 * on 28,Apr,2025
 */
object DesignSystemResources {
    object drawable {
        // Generic loading/error fallback for remote images rendered via AsyncImage.
        val arrow_back = Res.drawable.arrow_back
        val bolt = Res.drawable.bolt
        val chevron_right = Res.drawable.chevron_right
        val ds_image_placeholder = Res.drawable.ds_image_placeholder
        val failed_background = Res.drawable.failed_background
        val ic_ug_zero_harm = Res.drawable.ic_ug_zero_harm
        val warning = Res.drawable.warning
    }

    object string {
        val retry_label = Res.string.retry_label
        val cd_back = Res.string.cd_back
    }
}
