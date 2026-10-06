package com.example.ui.windows

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
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.TerminalModule
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
 * Screen 2: Main Dashboard
 * Exactly matches Reference Image Screen 2:
 * - Hero Card: "Today / Apr 26, 2040 / Better decisions. A calmer you." with mountain graphic
 * - Quick Modules 2x2 grid: LifeGraph, VisionCore, CommandLine, Agent Panel
 * - Recent Activity list: Flight research, Workout plan, New article saved
 */
@Composable
fun MainDashboardView(
    onNavigateModule: (TerminalModule) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(NovaBgSurface)
            .padding(horizontal = 14.dp, vertical = 8.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 1. HERO CARD: "Today / Apr 26, 2040" with Mountain Art
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(136.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(NovaCardBg)
                .border(1.dp, NovaCardBorder, RoundedCornerShape(16.dp))
        ) {
            // Mountain artwork on the right
            Image(
                painter = painterResource(id = R.drawable.nova_hero_mountain),
                contentDescription = "Futuristic mountain serene landscape",
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterEnd),
                contentScale = ContentScale.Crop
            )

            // Gradient vignette overlay for text legibility
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                NovaCardBg.copy(alpha = 0.95f),
                                NovaCardBg.copy(alpha = 0.80f),
                                Color.Transparent
                            ),
                            startX = 0f,
                            endX = 400f
                        )
                    )
            )

            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Today",
                    fontFamily = TerminalFontFamily,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = NovaTextPrimary
                )
                Text(
                    text = "Apr 26, 2040",
                    fontFamily = TerminalFontFamily,
                    fontSize = 11.sp,
                    color = NovaTextSecondary
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Better decisions.\nA calmer you.",
                    fontFamily = TerminalFontFamily,
                    fontSize = 13.sp,
                    lineHeight = 17.sp,
                    color = NovaCyan,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // 2. QUICK MODULES (2x2 Grid)
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Quick Modules",
                fontFamily = TerminalFontFamily,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = NovaTextPrimary
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // LifeGraph Module Tile
                QuickModuleCard(
                    title = "LifeGraph",
                    subtitle = "Your life. Connected.",
                    icon = Icons.Default.Hub,
                    iconColor = NovaGreen,
                    onClick = { onNavigateModule(TerminalModule.LIFE_GRAPH) },
                    modifier = Modifier.weight(1f)
                )

                // VisionCore Module Tile
                QuickModuleCard(
                    title = "VisionCore",
                    subtitle = "See. Understand.",
                    icon = Icons.Default.RemoveRedEye,
                    iconColor = NovaCyan,
                    onClick = { onNavigateModule(TerminalModule.VISION_CORE) },
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // CommandLine Module Tile
                QuickModuleCard(
                    title = "CommandLine",
                    subtitle = "Control. Automate.",
                    icon = Icons.Default.Terminal,
                    iconColor = NovaGreen,
                    onClick = { onNavigateModule(TerminalModule.COMMAND_LINE) },
                    modifier = Modifier.weight(1f)
                )

                // Agent Panel Module Tile
                QuickModuleCard(
                    title = "Agent Panel",
                    subtitle = "Your AI team.",
                    icon = Icons.Default.ViewInAr,
                    iconColor = NovaCyan,
                    onClick = { onNavigateModule(TerminalModule.AGENT_PANEL) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // 3. RECENT ACTIVITY LIST
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Recent Activity",
                fontFamily = TerminalFontFamily,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = NovaTextPrimary
            )

            RecentActivityItem(
                title = "Flight research to Delhi",
                timestamp = "2m ago",
                icon = Icons.Default.Flight,
                iconColor = NovaCyan
            )

            RecentActivityItem(
                title = "Workout plan updated",
                timestamp = "18m ago",
                icon = Icons.Default.FitnessCenter,
                iconColor = NovaGreen
            )

            RecentActivityItem(
                title = "New article saved (Cybersecurity)",
                timestamp = "45m ago",
                icon = Icons.Default.Description,
                iconColor = NovaCyan
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
private fun QuickModuleCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(100.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(NovaCardBg)
            .border(1.dp, NovaCardBorder, RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(iconColor.copy(alpha = 0.15f))
                    .border(1.dp, iconColor.copy(alpha = 0.35f), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconColor,
                    modifier = Modifier.size(18.dp)
                )
            }

            Column {
                Text(
                    text = title,
                    fontFamily = TerminalFontFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = NovaTextPrimary
                )
                Text(
                    text = subtitle,
                    fontFamily = TerminalFontFamily,
                    fontSize = 9.sp,
                    color = NovaTextSecondary,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
private fun RecentActivityItem(
    title: String,
    timestamp: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(NovaCardBg)
            .border(1.dp, NovaCardBorder, RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(iconColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(14.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = title,
                    fontFamily = TerminalFontFamily,
                    fontSize = 11.sp,
                    color = NovaTextPrimary
                )
            }
            Text(
                text = timestamp,
                fontFamily = TerminalFontFamily,
                fontSize = 9.sp,
                color = NovaTextDim
            )
        }
    }
}
