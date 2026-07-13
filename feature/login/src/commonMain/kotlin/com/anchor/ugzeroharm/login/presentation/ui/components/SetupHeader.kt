package com.anchor.ugzeroharm.login.presentation.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.anchor.ugzeroharm.designsystem.XIcon
import com.anchor.ugzeroharm.designsystem.motion.rememberReducedMotion
import org.jetbrains.compose.resources.painterResource
import ugzeroharm.feature.login.generated.resources.Res
import ugzeroharm.feature.login.generated.resources.bluetooth_searching
import ugzeroharm.feature.login.generated.resources.shield_with_heart_fill

@Composable
fun SetupHeader(modifier: Modifier = Modifier) {
    val isReducedMotion = rememberReducedMotion()

    val scale: Float
    val alpha: Float

    if (isReducedMotion) {
        scale = 1.0f
        alpha = 0.2f
    } else {
        val infiniteTransition = rememberInfiniteTransition(label = "pulse_glow")
        val scaleVal by infiniteTransition.animateFloat(
            initialValue = 1.0f,
            targetValue = 1.02f,
            animationSpec =
                infiniteRepeatable(
                    animation = tween(durationMillis = 3000, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse,
                ),
            label = "scale",
        )
        val alphaVal by infiniteTransition.animateFloat(
            initialValue = 0.2f,
            targetValue = 0.17f,
            animationSpec =
                infiniteRepeatable(
                    animation = tween(durationMillis = 3000, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse,
                ),
            label = "alpha",
        )
        scale = scaleVal
        alpha = alphaVal
    }

    Box(
        modifier = modifier.size(192.dp),
        contentAlignment = Alignment.Center,
    ) {
        // Pulse Background
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .scale(scale)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = alpha),
                        shape = CircleShape,
                    ).blur(32.dp),
        )

        // Shield Container
        Box(
            modifier =
                Modifier
                    .size(128.dp)
                    .background(Color.White, shape = CircleShape)
                    .border(4.dp, MaterialTheme.colorScheme.primaryFixed, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            XIcon(
                painter = painterResource(Res.drawable.shield_with_heart_fill),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(72.dp),
                contentDescription = null,
            )
        }

        // Bluetooth Icon Badge
        Box(
            modifier =
                Modifier
                    .size(40.dp)
                    .align(Alignment.TopEnd)
                    .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
                    .border(4.dp, MaterialTheme.colorScheme.background, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            XIcon(
                painter = painterResource(Res.drawable.bluetooth_searching),
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(24.dp),
                contentDescription = null,
            )
        }
    }
}
