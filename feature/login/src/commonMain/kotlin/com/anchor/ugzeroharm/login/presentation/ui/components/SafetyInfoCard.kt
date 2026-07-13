package com.anchor.ugzeroharm.login.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anchor.ugzeroharm.designsystem.XIcon
import com.anchor.ugzeroharm.designsystem.XText
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ugzeroharm.feature.login.generated.resources.Res
import ugzeroharm.feature.login.generated.resources.login_safety_body
import ugzeroharm.feature.login.generated.resources.login_safety_header
import ugzeroharm.feature.login.generated.resources.security

@Composable
fun SafetyInfoCard(modifier: Modifier = Modifier) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                    shape = RoundedCornerShape(12.dp),
                ).padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // Icon Holder
        Box(
            modifier =
                Modifier
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
                    .shadow(1.dp, RoundedCornerShape(8.dp)),
        ) {
            XIcon(
                painter = painterResource(Res.drawable.security),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp),
                contentDescription = null,
            )
        }

        // Text Column
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            XText(
                text = stringResource(Res.string.login_safety_header),
                style =
                    MaterialTheme.typography.labelLarge.copy(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                color = MaterialTheme.colorScheme.onSurface,
            )

            XText(
                text = stringResource(Res.string.login_safety_body),
                style =
                    MaterialTheme.typography.bodySmall.copy(
                        fontSize = 11.sp,
                    ),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
