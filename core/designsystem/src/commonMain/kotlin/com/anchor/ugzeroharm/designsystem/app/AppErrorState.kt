package com.anchor.ugzeroharm.designsystem.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.anchor.ugzeroharm.designsystem.DesignSystemResources
import com.anchor.ugzeroharm.designsystem.XButton
import com.anchor.ugzeroharm.designsystem.XText
import org.jetbrains.compose.resources.stringResource

/**
 * Shared, project-level **Failed** state — the neutral default written on install. Reused by every
 * feature for Rule 4's `UiState.Failed`. Copy and navigation are **parameters** ([title]/[message]
 * from the feature's own strings, [onRetry] the primary action, [retryLabel] defaulting to the
 * shared label, [secondaryAction] an optional nav slot), so nothing app-specific is baked in.
 * Redesign per project via the design pipeline. Built only from generic primitives ([XButton],
 * [XText]); never imported by generic design-system code.
 */
@Composable
fun AppErrorState(
    title: String,
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    retryLabel: String = stringResource(DesignSystemResources.string.retry_label),
    secondaryAction: (@Composable () -> Unit)? = null,
) {
    Column(
        modifier = modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        XText(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        XText(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.widthIn(max = 320.dp).padding(bottom = 24.dp),
        )
        XButton(onClick = onRetry) {
            XText(text = retryLabel)
        }
        secondaryAction?.invoke()
    }
}
