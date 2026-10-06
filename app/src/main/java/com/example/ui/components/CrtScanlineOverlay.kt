package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.ui.theme.PhosphorGreen

@Composable
fun CrtScanlineOverlay(
    modifier: Modifier = Modifier,
    enableScanlines: Boolean = true,
    enableRadarSweep: Boolean = true
) {
    val transition = rememberInfiniteTransition(label = "crt")

    val radarProgress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 6500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "radarProgress"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height

        // 1. CRT Scanlines
        if (enableScanlines) {
            val lineSpacing = 4f
            var y = 0f
            while (y < h) {
                drawLine(
                    color = Color.Black.copy(alpha = 0.14f),
                    start = Offset(0f, y),
                    end = Offset(w, y),
                    strokeWidth = 1.2f
                )
                y += lineSpacing
            }
        }

        // 2. Slow Radar Sweep Line (top to bottom)
        if (enableRadarSweep) {
            val sweepY = radarProgress * h
            val sweepHeight = 90f
            val startY = (sweepY - sweepHeight).coerceAtLeast(0f)

            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        PhosphorGreen.copy(alpha = 0.04f),
                        PhosphorGreen.copy(alpha = 0.12f),
                        PhosphorGreen.copy(alpha = 0.02f)
                    ),
                    startY = startY,
                    endY = sweepY
                ),
                topLeft = Offset(0f, startY),
                size = androidx.compose.ui.geometry.Size(w, sweepY - startY)
            )

            // Sharp radar scanline edge
            drawLine(
                color = PhosphorGreen.copy(alpha = 0.28f),
                start = Offset(0f, sweepY),
                end = Offset(w, sweepY),
                strokeWidth = 1.5f
            )
        }

        // 3. Vignette Border (Dark edges of CRT curved tube)
        drawRect(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color.Transparent,
                    Color.Transparent,
                    Color.Black.copy(alpha = 0.40f)
                ),
                center = Offset(w / 2f, h / 2f),
                radius = (w.coerceAtLeast(h) * 0.75f)
            )
        )
    }
}
