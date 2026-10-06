package com.example.ui.windows

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.NovaBgDeep
import com.example.ui.theme.NovaCardBg
import com.example.ui.theme.NovaRed
import com.example.ui.theme.NovaRedDark
import com.example.ui.theme.NovaTextDim
import com.example.ui.theme.NovaTextPrimary
import com.example.ui.theme.NovaTextSecondary
import com.example.ui.theme.TerminalFontFamily

/**
 * Screen 8: Kill Switch
 * Exactly matches Reference Image Screen 8:
 * - Header: NOVA in dim red
 * - Concentric radar rings with red warning triangle
 * - Huge bold "KILL SWITCH"
 * - Subtitle: "This will terminate all active agents, stop automation and disconnect all device access."
 * - Glowing Red [ CONFIRM ] button
 * - Outlined [ CANCEL ] button
 */
@Composable
fun KillSwitchOverlay(
    onDisengageKillSwitch: () -> Unit,
    modifier: Modifier = Modifier
) {
    val transition = rememberInfiniteTransition(label = "pulse")
    val pulse by transition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseRadius"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0C0204))
            .padding(horizontal = 24.dp, vertical = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header
            Text(
                text = "NOVA",
                fontFamily = TerminalFontFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = NovaRed.copy(alpha = 0.6f),
                letterSpacing = 4.sp
            )

            // Center Circular HUD with Warning Icon & Rings
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier.size(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val cx = size.width / 2f
                        val cy = size.height / 2f

                        // Outer radar rings
                        drawCircle(
                            color = NovaRed.copy(alpha = 0.15f),
                            radius = size.minDimension / 2f * 0.95f,
                            center = Offset(cx, cy),
                            style = Stroke(width = 1.2f)
                        )
                        drawCircle(
                            color = NovaRed.copy(alpha = 0.25f),
                            radius = size.minDimension / 2f * 0.75f * pulse,
                            center = Offset(cx, cy),
                            style = Stroke(width = 1.5f)
                        )
                        drawCircle(
                            color = NovaRed.copy(alpha = 0.45f),
                            radius = size.minDimension / 2f * 0.55f,
                            center = Offset(cx, cy),
                            style = Stroke(width = 2f)
                        )
                    }

                    // Warning Triangle in inner circle
                    Box(
                        modifier = Modifier
                            .size(76.dp)
                            .clip(CircleShape)
                            .background(NovaRed.copy(alpha = 0.2f))
                            .border(2.dp, NovaRed, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Warning",
                            tint = NovaRed,
                            modifier = Modifier.size(42.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Title: KILL SWITCH
                Text(
                    text = "KILL SWITCH",
                    fontFamily = TerminalFontFamily,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = NovaRed,
                    letterSpacing = 2.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Description
                Text(
                    text = "This will terminate all active agents,\nstop automation and disconnect\nall device access.",
                    fontFamily = TerminalFontFamily,
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    color = NovaTextSecondary,
                    textAlign = TextAlign.Center
                )
            }

            // Buttons: CONFIRM & CANCEL
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // CONFIRM Button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(NovaRed)
                        .clickable { onDisengageKillSwitch() }
                        .padding(vertical = 14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "CONFIRM",
                        fontFamily = TerminalFontFamily,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        letterSpacing = 1.sp
                    )
                }

                // CANCEL Button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(NovaCardBg)
                        .border(1.dp, Color(0xFF261216), RoundedCornerShape(14.dp))
                        .clickable { onDisengageKillSwitch() }
                        .padding(vertical = 14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "CANCEL",
                        fontFamily = TerminalFontFamily,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = NovaTextSecondary,
                        letterSpacing = 1.sp
                    )
                }
            }
        }
    }
}
