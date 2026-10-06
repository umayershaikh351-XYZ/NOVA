package com.example.ui.windows

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.CropSquare
import androidx.compose.material.icons.filled.DesktopWindows
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Mouse
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.NovaBgSurface
import com.example.ui.theme.NovaCardBg
import com.example.ui.theme.NovaCardBorder
import com.example.ui.theme.NovaCyan
import com.example.ui.theme.NovaGreen
import com.example.ui.theme.NovaTextDim
import com.example.ui.theme.NovaTextPrimary
import com.example.ui.theme.NovaTextSecondary
import com.example.ui.theme.TerminalFontFamily

/**
 * Screen 4: VisionCore
 * Exactly matches Reference Image Screen 4:
 * - Header: Back, "VisionCore", LIVE badge, "Screen & Camera Awareness"
 * - Dual Monitor Screen Detected preview card
 * - Detected Objects (5): Laptop, Mouse, Notebook, Plant, Phone
 * - Metrics row: Text 96%, Objects 94%, Confidence 98%
 * - Current View: Desktop Screen (Analyzing... with waveform)
 * - Screenshot & Camera buttons
 */
@Composable
fun VisionCoreWindow(
    onTriggerScan: () -> Unit,
    onBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val detectedItems = listOf(
        "Laptop" to Icons.Default.Computer,
        "Mouse" to Icons.Default.Mouse,
        "Notebook" to Icons.Default.MenuBook,
        "Plant" to Icons.Default.Eco,
        "Phone" to Icons.Default.PhoneAndroid
    )

    val transition = rememberInfiniteTransition(label = "waveform")
    val wavePhase by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(NovaBgSurface)
            .padding(horizontal = 14.dp, vertical = 6.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 1. HEADER
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack, modifier = Modifier.size(32.dp)) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = NovaTextPrimary
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "VisionCore",
                            fontFamily = TerminalFontFamily,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = NovaTextPrimary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        // LIVE Pill Badge
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(NovaGreen.copy(alpha = 0.15f))
                                .border(1.dp, NovaGreen.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(5.dp)
                                        .background(NovaGreen, CircleShape)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "LIVE",
                                    fontFamily = TerminalFontFamily,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = NovaGreen
                                )
                            }
                        }
                    }
                    Text(
                        text = "Screen & Camera Awareness",
                        fontFamily = TerminalFontFamily,
                        fontSize = 10.sp,
                        color = NovaTextSecondary
                    )
                }
            }
        }

        // 2. DETECTED SCREEN FRAME PREVIEW
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, NovaCardBorder, RoundedCornerShape(16.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.vision_workspace),
                contentDescription = "Screen workspace",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // "Screen Detected" Green Pill Overlay (top-left)
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.TopStart)
                    .clip(RoundedCornerShape(8.dp))
                    .background(NovaGreen.copy(alpha = 0.85f))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "Screen Detected",
                    fontFamily = TerminalFontFamily,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }

        // 3. DETECTED OBJECTS (5) ROW
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Detected Objects (5)",
                fontFamily = TerminalFontFamily,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = NovaTextPrimary
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                detectedItems.forEach { (name, icon) ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(64.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(NovaCardBg)
                            .border(1.dp, NovaCardBorder, RoundedCornerShape(12.dp))
                            .padding(6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = name,
                                tint = NovaCyan,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = name,
                                fontFamily = TerminalFontFamily,
                                fontSize = 9.sp,
                                color = NovaTextSecondary,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }

        // 4. METRICS ROW (Text 96%, Objects 94%, Confidence 98%)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(NovaCardBg)
                .border(1.dp, NovaCardBorder, RoundedCornerShape(14.dp))
                .padding(vertical = 12.dp, horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                MetricColumn(label = "Text", value = "96%", color = NovaCyan)
                MetricColumn(label = "Objects", value = "94%", color = NovaGreen)
                MetricColumn(label = "Confidence", value = "98%", color = NovaCyan)
            }
        }

        // 5. CURRENT VIEW CARD WITH WAVEFORM
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text = "Current View",
                fontFamily = TerminalFontFamily,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = NovaTextPrimary
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(NovaCardBg)
                    .border(1.dp, NovaCardBorder, RoundedCornerShape(14.dp))
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(NovaCyan.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.DesktopWindows,
                                contentDescription = null,
                                tint = NovaCyan,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Desktop Screen",
                                fontFamily = TerminalFontFamily,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = NovaTextPrimary
                            )
                            Text(
                                text = "Analyzing...",
                                fontFamily = TerminalFontFamily,
                                fontSize = 10.sp,
                                color = NovaGreen
                            )
                        }
                    }

                    // Mini live waveform animation
                    Canvas(modifier = Modifier.size(width = 46.dp, height = 20.dp)) {
                        val barCount = 7
                        val barWidth = 3f
                        val spacing = (size.width - barCount * barWidth) / (barCount - 1)
                        for (i in 0 until barCount) {
                            val hFactor = 0.3f + 0.7f * kotlin.math.abs(
                                kotlin.math.sin((i * 0.8f + wavePhase * 6.28f).toDouble())
                            ).toFloat()
                            val barH = size.height * hFactor
                            drawRect(
                                color = NovaGreen,
                                topLeft = Offset(i * (barWidth + spacing), (size.height - barH) / 2f),
                                size = androidx.compose.ui.geometry.Size(barWidth, barH)
                            )
                        }
                    }
                }
            }
        }

        // 6. ACTION BUTTONS: Screenshot & Camera
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(NovaCardBg)
                    .border(1.dp, NovaCardBorder, RoundedCornerShape(12.dp))
                    .clickable { onTriggerScan() }
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CropSquare,
                        contentDescription = null,
                        tint = NovaCyan,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Screenshot",
                        fontFamily = TerminalFontFamily,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = NovaCyan
                    )
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(NovaCardBg)
                    .border(1.dp, NovaCardBorder, RoundedCornerShape(12.dp))
                    .clickable { onTriggerScan() }
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = null,
                        tint = NovaGreen,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Camera",
                        fontFamily = TerminalFontFamily,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = NovaGreen
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
private fun MetricColumn(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            fontFamily = TerminalFontFamily,
            fontSize = 10.sp,
            color = NovaTextSecondary
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            fontFamily = TerminalFontFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}
