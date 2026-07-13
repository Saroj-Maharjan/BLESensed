package com.anchor.ugzeroharm.login.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anchor.ugzeroharm.designsystem.XButton
import com.anchor.ugzeroharm.designsystem.XIcon
import com.anchor.ugzeroharm.designsystem.XIconButton
import com.anchor.ugzeroharm.designsystem.XScreen
import com.anchor.ugzeroharm.designsystem.XText
import com.anchor.ugzeroharm.designsystem.toolbar.XTopAppBar
import com.anchor.ugzeroharm.login.presentation.ui.components.DeviceIdForm
import com.anchor.ugzeroharm.login.presentation.ui.components.SafetyInfoCard
import com.anchor.ugzeroharm.login.presentation.ui.components.SetupHeader
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ugzeroharm.feature.login.generated.resources.Res
import ugzeroharm.feature.login.generated.resources.arrow_forward
import ugzeroharm.feature.login.generated.resources.login_btn_start_advertising
import ugzeroharm.feature.login.generated.resources.login_footnote_bluetooth
import ugzeroharm.feature.login.generated.resources.login_title
import ugzeroharm.feature.login.generated.resources.settings

@Composable
fun LoginScreen(
    onLoginSuccess: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var deviceId by remember { mutableStateOf("") }

    val onGenerateRandomId = {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        deviceId = (1..6).map { chars.random() }.joinToString("")
    }

    // Automatically generate a device ID on first composition if empty
    LaunchedEffect(Unit) {
        if (deviceId.isEmpty()) {
            onGenerateRandomId()
        }
    }

    XScreen(
        modifier = modifier,
        topBar = {
            XTopAppBar(
                title = {
                    XText(
                        text = stringResource(Res.string.login_title),
                        style =
                            MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                            ),
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                navigationIcon = null,
                actions = {
                    XIconButton(
                        onClick = { /* No-op settings click */ },
                    ) {
                        XIcon(
                            painter = painterResource(Res.drawable.settings),
                            contentDescription = "Settings",
                        )
                    }
                },
            )
        },
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            // Setup Header Component
            SetupHeader()

            // Device ID Form Component
            DeviceIdForm(
                deviceId = deviceId,
                onDeviceIdChange = { deviceId = it },
                onGenerateRandomId = onGenerateRandomId,
            )

            // Advertising Action Component
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                val isButtonEnabled = deviceId.length == 6

                XButton(
                    onClick = {
                        if (isButtonEnabled) {
                            onLoginSuccess(deviceId)
                        }
                    },
                    enabled = isButtonEnabled,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                    shape = CircleShape,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        XText(
                            text = stringResource(Res.string.login_btn_start_advertising),
                            style =
                                MaterialTheme.typography.labelLarge.copy(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                ),
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        XIcon(
                            painter = painterResource(Res.drawable.arrow_forward),
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(24.dp),
                            contentDescription = null,
                        )
                    }
                }

                XText(
                    text = stringResource(Res.string.login_footnote_bluetooth),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            // Safety Info Card Component
            SafetyInfoCard()
        }
    }
}
