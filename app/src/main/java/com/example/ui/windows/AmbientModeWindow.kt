package com.example.ui.windows

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WbCloudy
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.NovaAnimatedCoreLogo
import com.example.ui.theme.NovaAmber
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
 * Screen 9: Ambient Mode (Home Screen)
 * Exactly matches Reference Image Screen 9:
 * - Header: NOVA Animated Logo, "Ambient Mode", Settings icon
 * - Weather card: Mumbai 28°C Partly Cloudy, Fri, Apr 26 2026
 * - Today's Schedule: 10:00 Team Meeting, 14:00 Study, 18:00 Gym, 21:00 Family Call
 * - Health Triad: Health 72 bpm, Steps 4,320 / 10,000, Sleep 6h 45m
 * - Motivational grid quote: "Small steps every day lead to big results."
 */
@Composable
fun AmbientModeWindow(
    modifier: Modifier = Modifier
) {
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
                NovaAnimatedCoreLogo(size = 28.dp)
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "NOVA",
                        fontFamily = TerminalFontFamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = NovaTextPrimary
                    )
                    Text(
                        text = "Ambient Mode",
                        fontFamily = TerminalFontFamily,
                        fontSize = 10.sp,
                        color = NovaCyan
                    )
                }
            }

            IconButton(onClick = {}, modifier = Modifier.size(32.dp)) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = NovaTextSecondary
                )
            }
        }

        // 2. WEATHER & DATE WIDGET
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(NovaCardBg)
                .border(1.dp, NovaCardBorder, RoundedCornerShape(14.dp))
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.WbCloudy,
                        contentDescription = "Weather",
                        tint = NovaAmber,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Mumbai",
                            fontFamily = TerminalFontFamily,
                            fontSize = 11.sp,
                            color = NovaTextSecondary
                        )
                        Text(
                            text = "28°C",
                            fontFamily = TerminalFontFamily,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = NovaTextPrimary
                        )
                        Text(
                            text = "Partly Cloudy",
                            fontFamily = TerminalFontFamily,
                            fontSize = 9.sp,
                            color = NovaTextDim
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Fri, Apr 26",
                        fontFamily = TerminalFontFamily,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = NovaCyan
                    )
                    Text(
                        text = "2026",
                        fontFamily = TerminalFontFamily,
                        fontSize = 11.sp,
                        color = NovaTextSecondary
                    )
                }
            }
        }

        // 3. TODAY'S SCHEDULE
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Today's Schedule",
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
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    ScheduleRow("10:00", "Team Meeting")
                    ScheduleRow("14:00", "Study (Cybersecurity)")
                    ScheduleRow("18:00", "Gym")
                    ScheduleRow("21:00", "Family Call")
                }
            }
        }

        // 4. HEALTH METRICS TRIAD
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AmbientMetricCard(
                icon = Icons.Default.Favorite,
                title = "Health",
                value = "72 bpm",
                status = "Good",
                color = NovaGreen,
                modifier = Modifier.weight(1f)
            )
            AmbientMetricCard(
                icon = Icons.Default.DirectionsWalk,
                title = "Steps",
                value = "4,320",
                status = "/10,000",
                color = NovaCyan,
                modifier = Modifier.weight(1f)
            )
            AmbientMetricCard(
                icon = Icons.Default.Bedtime,
                title = "Sleep",
                value = "6h 45m",
                status = "Fair",
                color = NovaCyan,
                modifier = Modifier.weight(1f)
            )
        }

        // 5. MOTIVATIONAL GRID QUOTE
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(NovaCardBg)
                .border(1.dp, NovaGreen.copy(alpha = 0.35f), RoundedCornerShape(14.dp))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "\"Small steps every day\nlead to big results.\"",
                fontFamily = TerminalFontFamily,
                fontSize = 12.sp,
                lineHeight = 18.sp,
                fontWeight = FontWeight.Medium,
                color = NovaGreen,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
private fun ScheduleRow(time: String, title: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = time,
            fontFamily = TerminalFontFamily,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = NovaCyan,
            modifier = Modifier.width(48.dp)
        )
        Text(
            text = title,
            fontFamily = TerminalFontFamily,
            fontSize = 11.sp,
            color = NovaTextPrimary
        )
    }
}

@Composable
private fun AmbientMetricCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String,
    status: String,
    color: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(NovaCardBg)
            .border(1.dp, NovaCardBorder, RoundedCornerShape(12.dp))
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = color,
                    modifier = Modifier.size(13.dp)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                fontFamily = TerminalFontFamily,
                fontSize = 9.sp,
                color = NovaTextSecondary
            )
            Text(
                text = value,
                fontFamily = TerminalFontFamily,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = NovaTextPrimary
            )
            Text(
                text = status,
                fontFamily = TerminalFontFamily,
                fontSize = 8.sp,
                color = NovaTextDim
            )
        }
    }
}
