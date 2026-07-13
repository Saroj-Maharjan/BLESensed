package com.anchor.ugzeroharm.designsystem.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anchor.ugzeroharm.designsystem.XCircularProgressIndicator

/**
 * Shared, project-level **Loading** state — the neutral default written on install. Reused by every
 * feature for Rule 4's `UiState.Loading`. Redesign per project via the design pipeline. Built only
 * from generic primitives ([XCircularProgressIndicator]); never imported by generic design-system code.
 */
@Composable
fun AppLoadingState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        XCircularProgressIndicator(
            modifier = Modifier.size(48.dp),
        )
        Text(
            text = "INITIALIZING SAFETY PROTOCOLS",
            style =
                MaterialTheme.typography.labelMedium.copy(
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Bold,
                ),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 24.dp),
        )
    }
}
