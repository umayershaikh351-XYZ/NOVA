package com.example.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AmberCrt
import com.example.ui.theme.PhosphorGreen
import com.example.ui.theme.TerminalBevelLight
import com.example.ui.theme.TerminalBevelShadow
import com.example.ui.theme.TerminalFontFamily
import com.example.ui.theme.TerminalSurface

enum class BevelStyle {
    RAISED,
    SUNKEN,
    FLAT
}

/**
 * Authentic 1990s retro OS beveled panel.
 * Raised panels have light top/left edges and dark bottom/right edges.
 * Sunken panels have dark top/left edges and light bottom/right edges.
 */
@Composable
fun RetroBevelBox(
    modifier: Modifier = Modifier,
    style: BevelStyle = BevelStyle.RAISED,
    bevelWidth: Dp = 2.dp,
    backgroundColor: Color = TerminalSurface,
    lightColor: Color = TerminalBevelLight,
    shadowColor: Color = TerminalBevelShadow,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .background(backgroundColor)
            .drawBehind {
                val stroke = bevelWidth.toPx()
                val w = size.width
                val h = size.height

                val topCol = if (style == BevelStyle.RAISED) lightColor else shadowColor
                val leftCol = if (style == BevelStyle.RAISED) lightColor else shadowColor
                val bottomCol = if (style == BevelStyle.RAISED) shadowColor else lightColor
                val rightCol = if (style == BevelStyle.RAISED) shadowColor else lightColor

                if (style != BevelStyle.FLAT) {
                    // Top line
                    drawLine(topCol, Offset(0f, stroke / 2), Offset(w, stroke / 2), strokeWidth = stroke)
                    // Left line
                    drawLine(leftCol, Offset(stroke / 2, 0f), Offset(stroke / 2, h), strokeWidth = stroke)
                    // Bottom line
                    drawLine(bottomCol, Offset(0f, h - stroke / 2), Offset(w, h - stroke / 2), strokeWidth = stroke)
                    // Right line
                    drawLine(rightCol, Offset(w - stroke / 2, 0f), Offset(w - stroke / 2, h), strokeWidth = stroke)
                }
            }
            .padding(bevelWidth + 1.dp),
        content = content
    )
}

/**
 * Clickable chunky 1990s terminal push button with audio click integration
 */
@Composable
fun RetroButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isPressed: Boolean = false,
    textColor: Color = PhosphorGreen,
    accentColor: Color? = null,
    enabled: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }

    RetroBevelBox(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                role = Role.Button,
                onClick = onClick
            ),
        style = if (isPressed) BevelStyle.SUNKEN else BevelStyle.RAISED,
        backgroundColor = if (isPressed) Color(0xFF0C1218) else TerminalSurface,
        bevelWidth = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (accentColor != null) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(accentColor, CircleShape)
                )
                Spacer(modifier = Modifier.width(6.dp))
            }
            Text(
                text = text,
                color = if (enabled) textColor else Color(0xFF45584E),
                fontFamily = TerminalFontFamily,
                fontSize = 11.sp,
                letterSpacing = 0.8.sp
            )
        }
    }
}

/**
 * Pulsing hardware status lamp (Green/Amber/Red)
 */
@Composable
fun StatusLamp(
    color: Color = PhosphorGreen,
    modifier: Modifier = Modifier,
    isPulsing: Boolean = true,
    size: Dp = 8.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "lamp")
    val alpha by if (isPulsing) {
        infiniteTransition.animateFloat(
            initialValue = 0.4f,
            targetValue = 1.0f,
            animationSpec = infiniteRepeatable(
                animation = tween(800),
                repeatMode = RepeatMode.Reverse
            ),
            label = "lampAlpha"
        )
    } else {
        remember { androidx.compose.runtime.mutableFloatStateOf(1.0f) }
    }

    Box(
        modifier = modifier
            .size(size)
            .background(color.copy(alpha = alpha), CircleShape)
            .border(1.dp, color, CircleShape)
    )
}
