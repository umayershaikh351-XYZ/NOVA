package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.NovaAmber
import com.example.ui.theme.NovaCyan
import com.example.ui.theme.NovaGreen
import kotlin.math.cos
import kotlin.math.sin

/**
 * Animated NOVA Core Logo
 * "A geometric pulsing core — hexagonal shape made of thin lines, with a glowing dot at center.
 * Around the core, 3–4 thin orbital lines rotate slowly, representing the agent swarm."
 */
@Composable
fun NovaAnimatedCoreLogo(
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    primaryColor: Color = NovaGreen,
    accentColor: Color = NovaCyan,
    orbitColor: Color = NovaAmber
) {
    val transition = rememberInfiniteTransition(label = "novaCore")

    // Heartbeat breath pulse every 2000ms
    val breathPulse by transition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "breath"
    )

    // Orbital ring 1 rotation
    val rot1 by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rot1"
    )

    // Orbital ring 2 rotation (counter-rotation)
    val rot2 by transition.animateFloat(
        initialValue = 360f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(18000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rot2"
    )

    // Orbital ring 3 rotation
    val rot3 by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(24000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rot3"
    )

    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val cx = w / 2f
        val cy = h / 2f
        val maxR = minOf(w, h) / 2f

        // 1. Orbital Ring 1 (Outer fine dashed ring)
        rotate(rot1, pivot = Offset(cx, cy)) {
            drawCircle(
                color = primaryColor.copy(alpha = 0.25f),
                radius = maxR * 0.94f,
                center = Offset(cx, cy),
                style = Stroke(width = 1.2f)
            )
            // Orbiting Node 1 (Agent 1)
            drawCircle(
                color = primaryColor,
                radius = maxR * 0.045f,
                center = Offset(cx + maxR * 0.94f, cy)
            )
        }

        // 2. Orbital Ring 2 (Middle inclined ring)
        rotate(rot2, pivot = Offset(cx, cy)) {
            drawCircle(
                color = orbitColor.copy(alpha = 0.28f),
                radius = maxR * 0.78f,
                center = Offset(cx, cy),
                style = Stroke(width = 1.2f)
            )
            // Orbiting Node 2 (Agent 2)
            drawCircle(
                color = orbitColor,
                radius = maxR * 0.04f,
                center = Offset(cx, cy - maxR * 0.78f)
            )
        }

        // 3. Orbital Ring 3 (Inner ring)
        rotate(rot3, pivot = Offset(cx, cy)) {
            drawCircle(
                color = accentColor.copy(alpha = 0.35f),
                radius = maxR * 0.62f,
                center = Offset(cx, cy),
                style = Stroke(width = 1.2f)
            )
            // Orbiting Node 3 (Agent 3)
            drawCircle(
                color = accentColor,
                radius = maxR * 0.035f,
                center = Offset(cx - maxR * 0.62f, cy)
            )
        }

        // 4. Hexagonal Core (Sharp geometric lines)
        val hexR = maxR * 0.44f * breathPulse
        val hexPath = Path()
        for (i in 0 until 6) {
            val angleRad = Math.toRadians((i * 60 - 30).toDouble())
            val px = (cx + hexR * cos(angleRad)).toFloat()
            val py = (cy + hexR * sin(angleRad)).toFloat()
            if (i == 0) hexPath.moveTo(px, py) else hexPath.lineTo(px, py)
        }
        hexPath.close()

        drawPath(
            path = hexPath,
            color = primaryColor,
            style = Stroke(width = 1.8f)
        )

        // Inner Inverted Hexagon / Wireframe Facets
        val innerHexR = hexR * 0.58f
        val innerPath = Path()
        for (i in 0 until 6) {
            val angleRad = Math.toRadians((i * 60).toDouble())
            val px = (cx + innerHexR * cos(angleRad)).toFloat()
            val py = (cy + innerHexR * sin(angleRad)).toFloat()
            if (i == 0) innerPath.moveTo(px, py) else innerPath.lineTo(px, py)
        }
        innerPath.close()

        drawPath(
            path = innerPath,
            color = orbitColor.copy(alpha = 0.8f),
            style = Stroke(width = 1.2f)
        )

        // 5. Center Glowing Eye / Heartbeat Dot
        // Outer aura
        drawCircle(
            color = accentColor.copy(alpha = 0.4f),
            radius = maxR * 0.14f * breathPulse,
            center = Offset(cx, cy)
        )
        // Solid bright center core
        drawCircle(
            color = accentColor,
            radius = maxR * 0.07f * breathPulse,
            center = Offset(cx, cy)
        )
    }
}
