package com.anchor.ugzeroharm.permissionscreen.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anchor.ugzeroharm.designsystem.XButton
import com.anchor.ugzeroharm.designsystem.XCard
import com.anchor.ugzeroharm.designsystem.XIcon
import com.anchor.ugzeroharm.designsystem.XText
import com.anchor.ugzeroharm.designsystem.XTheme
import org.jetbrains.compose.resources.stringResource
import ugzeroharm.feature.permissionscreen.generated.resources.Res
import ugzeroharm.feature.permissionscreen.generated.resources.status_disabled
import ugzeroharm.feature.permissionscreen.generated.resources.status_enabled
import ugzeroharm.feature.permissionscreen.generated.resources.status_granted
import ugzeroharm.feature.permissionscreen.generated.resources.status_missing

@Composable
fun PermissionStatusCard(
    title: String,
    description: String,
    isActive: Boolean,
    isService: Boolean,
    icon: Painter,
    actionLabel: String? = null,
    onActionClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    val statusTextRes =
        if (isService) {
            if (isActive) Res.string.status_enabled else Res.string.status_disabled
        } else {
            if (isActive) Res.string.status_granted else Res.string.status_missing
        }

    val statusColor = if (isActive) XTheme.Colors.Success else XTheme.Colors.Danger
    val iconBgColor = if (isActive) statusColor.copy(alpha = 0.15f) else MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.4f)
    val iconTint = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error

    XCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Icon Background wrapper
                Box(
                    modifier =
                        Modifier
                            .size(44.dp)
                            .background(iconBgColor, shape = CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    XIcon(
                        painter = icon,
                        contentDescription = null,
                        tint = iconTint,
                        modifier = Modifier.size(24.dp),
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    XText(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    XText(
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Status Badge
                Box(
                    modifier =
                        Modifier
                            .border(1.dp, statusColor, shape = RoundedCornerShape(4.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                ) {
                    XText(
                        text = stringResource(statusTextRes),
                        style =
                            MaterialTheme.typography.labelSmall.copy(
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                            ),
                        color = statusColor,
                    )
                }
            }

            // Action Button if not active
            if (!isActive && actionLabel != null && onActionClick != null) {
                Spacer(modifier = Modifier.height(4.dp))
                XButton(
                    onClick = onActionClick,
                    modifier = Modifier.fillMaxWidth().height(44.dp),
                    shape = CircleShape,
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                ) {
                    XText(
                        text = actionLabel,
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                    )
                }
            }
        }
    }
}
