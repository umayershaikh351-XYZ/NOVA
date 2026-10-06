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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.NovaAnimatedCoreLogo
import com.example.ui.theme.NovaBgDeep
import com.example.ui.theme.NovaCardBg
import com.example.ui.theme.NovaCardBorder
import com.example.ui.theme.NovaCyan
import com.example.ui.theme.NovaGreen
import com.example.ui.theme.NovaTextDim
import com.example.ui.theme.NovaTextPrimary
import com.example.ui.theme.NovaTextSecondary
import com.example.ui.theme.TerminalFontFamily
import kotlinx.coroutines.delay

/**
 * Screen 1: Launch / Boot Screen
 * Exactly matches Reference Image Screen 1:
 * - Large circular HUD with rotating geometric orbital rings & glowing core
 * - Bold uppercase "N O V A"
 * - Subtitle: "Neural Omni Virtual Agent"
 * - Tagline: "Not another chatbot. An AI operating system for your life."
 * - Terminal box with green prompt logs & 100% progress bar
 */
@Composable
fun BootSequenceView(
    onBootComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bootSteps = remember {
        listOf(
            "> Initializing NOVA...",
            "> Loading core systems...",
            "> Connecting agents...",
            "> Checking devices...",
            "> Ready."
        )
    }

    val visibleLines = remember { mutableStateListOf<String>() }
    var progress by remember { mutableFloatStateOf(0.1f) }
    var isReady by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        for (i in bootSteps.indices) {
            visibleLines.add(bootSteps[i])
            progress = ((i + 1).toFloat() / bootSteps.size.toFloat())
            delay(400)
        }
        progress = 1.0f
        isReady = true
    }

    // Outer subtle grid background
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(NovaBgDeep)
            .clickable { if (isReady) onBootComplete() }
            .padding(horizontal = 24.dp, vertical = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // 1. TOP & CENTER: THE CORE (Large Animated Geometric Orb & Orbitals)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f, fill = false)
            ) {
                Box(
                    modifier = Modifier.size(220.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Outer HUD Compass Tick Ring
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val cx = size.width / 2f
                        val cy = size.height / 2f
                        val r = size.minDimension / 2f * 0.98f

                        // Fine circular tick perimeter
                        for (deg in 0 until 360 step 15) {
                            val rad = Math.toRadians(deg.toDouble())
                            val isMajor = deg % 45 == 0
                            val tickLen = if (isMajor) 8f else 4f
                            val p1 = Offset((cx + (r - tickLen) * Math.cos(rad)).toFloat(), (cy + (r - tickLen) * Math.sin(rad)).toFloat())
                            val p2 = Offset((cx + r * Math.cos(rad)).toFloat(), (cy + r * Math.sin(rad)).toFloat())
                            drawLine(
                                color = if (isMajor) NovaCyan.copy(alpha = 0.5f) else NovaGreen.copy(alpha = 0.2f),
                                start = p1,
                                end = p2,
                                strokeWidth = if (isMajor) 1.5f else 1f
                            )
                        }
                    }

                    // Rotating animated Core
                    NovaAnimatedCoreLogo(
                        size = 190.dp,
                        primaryColor = NovaGreen,
                        accentColor = NovaCyan
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // 2. WORDMARK: "N O V A"
                Text(
                    text = "N O V A",
                    fontFamily = TerminalFontFamily,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = NovaGreen,
                    letterSpacing = 6.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Neural Omni Virtual Agent",
                    fontFamily = TerminalFontFamily,
                    fontSize = 13.sp,
                    color = NovaCyan,
                    letterSpacing = 1.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Tagline block
                Text(
                    text = "Not another chatbot.\nAn AI operating system\nfor your life.",
                    fontFamily = TerminalFontFamily,
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    color = NovaTextSecondary,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 3. BOTTOM: TERMINAL BOOT STATUS CARD
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(NovaCardBg)
                        .border(1.dp, NovaCardBorder, RoundedCornerShape(14.dp))
                        .padding(14.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        visibleLines.forEach { line ->
                            Text(
                                text = line,
                                fontFamily = TerminalFontFamily,
                                fontSize = 12.sp,
                                color = if (line.contains("Ready")) NovaGreen else NovaCyan,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Progress Bar with 100% label
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .weight(1f)
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = NovaGreen,
                        trackColor = NovaCardBorder,
                        strokeCap = StrokeCap.Round
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "${(progress * 100).toInt()}%",
                        fontFamily = TerminalFontFamily,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = NovaGreen
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Tap to enter callout
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isReady) NovaGreen.copy(alpha = 0.15f) else Color.Transparent)
                        .border(1.dp, if (isReady) NovaGreen else Color.Transparent, RoundedCornerShape(12.dp))
                        .clickable(enabled = isReady) { onBootComplete() }
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isReady) "TAP ANYWHERE TO ENTER OPERATING SYSTEM" else "INITIALIZING NEURAL SUBSYSTEMS...",
                        fontFamily = TerminalFontFamily,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isReady) NovaGreen else NovaTextDim,
                        letterSpacing = 1.sp
                    )
                }
            }
        }
    }
}
