package com.anchor.ugzeroharm.login.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.anchor.ugzeroharm.designsystem.XCard
import com.anchor.ugzeroharm.designsystem.XIcon
import com.anchor.ugzeroharm.designsystem.XText
import com.anchor.ugzeroharm.designsystem.XTextButton
import com.anchor.ugzeroharm.designsystem.XTextField
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ugzeroharm.feature.login.generated.resources.Res
import ugzeroharm.feature.login.generated.resources.login_btn_generate_id
import ugzeroharm.feature.login.generated.resources.login_desc_device_id
import ugzeroharm.feature.login.generated.resources.login_header_device_id
import ugzeroharm.feature.login.generated.resources.login_label_device_id
import ugzeroharm.feature.login.generated.resources.login_placeholder_device_id
import ugzeroharm.feature.login.generated.resources.refresh

@Composable
fun DeviceIdForm(
    deviceId: String,
    onDeviceIdChange: (String) -> Unit,
    onGenerateRandomId: () -> Unit,
    modifier: Modifier = Modifier,
) {
    XCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            // Text Header
            XText(
                text = stringResource(Res.string.login_header_device_id),
                style =
                    MaterialTheme.typography.titleMedium.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                color = MaterialTheme.colorScheme.onSurface,
            )

            // Text Helper
            XText(
                text = stringResource(Res.string.login_desc_device_id),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            // Input Section
            XTextField(
                value = deviceId,
                onValueChange = { input ->
                    // Limit to 6 alphanumeric uppercase characters
                    val filtered = input.take(6).filter { it.isLetterOrDigit() }.uppercase()
                    onDeviceIdChange(filtered)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                keyboardOptions =
                    KeyboardOptions(
                        capitalization = KeyboardCapitalization.Characters,
                    ),
                textStyle =
                    MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 18.sp,
                        letterSpacing = 0.1.em,
                    ),
                colors =
                    OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.outline,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                    ),
                label = {
                    XText(
                        text = stringResource(Res.string.login_label_device_id),
                    )
                },
                placeholder = {
                    XText(
                        text = stringResource(Res.string.login_placeholder_device_id),
                    )
                },
            )

            // Secondary Button to Generate Random ID
            XTextButton(
                onClick = onGenerateRandomId,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    XIcon(
                        painter = painterResource(Res.drawable.refresh),
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp),
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    XText(
                        text = stringResource(Res.string.login_btn_generate_id),
                        style =
                            MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                            ),
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
    }
}
