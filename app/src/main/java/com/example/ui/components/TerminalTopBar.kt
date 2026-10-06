package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.NovaBgSurface
import com.example.ui.theme.NovaCardBorder
import com.example.ui.theme.NovaCyan
import com.example.ui.theme.NovaGreen
import com.example.ui.theme.NovaTextDim
import com.example.ui.theme.NovaTextPrimary
import com.example.ui.theme.NovaTextSecondary
import com.example.ui.theme.TerminalFontFamily

/**
 * Top Bar matching Reference Image Screen 2:
 * - Animated NOVA Core Logo + "NOVA" + "System Online"
 * - Badges: "Agents 03/08" (cyan) and "Trust 82%" (green)
 * - Search icon and Notification bell
 */
@Composable
fun TerminalTopBar(
    activeAgentCount: Int,
    trustLevel: Int,
    onTrustLevelClick: () -> Unit,
    isSoundMuted: Boolean,
    onToggleSound: () -> Unit,
    onKillSwitchClick: () -> Unit,
    onLogoClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(NovaBgSurface)
            .border(1.dp, NovaCardBorder.copy(alpha = 0.5f))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // LEFT: Animated Core Logo + NOVA + System Online
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onLogoClick() }
            ) {
                NovaAnimatedCoreLogo(size = 30.dp)
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "NOVA",
                        fontFamily = TerminalFontFamily,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = NovaTextPrimary,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "System Online",
                        fontFamily = TerminalFontFamily,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Medium,
                        color = NovaGreen
                    )
                }
            }

            // RIGHT: Badges & Icons
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Badge 1: Agents 03/08
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(NovaCyan.copy(alpha = 0.12f))
                        .border(1.dp, NovaCyan.copy(alpha = 0.35f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Agents",
                            fontFamily = TerminalFontFamily,
                            fontSize = 8.sp,
                            color = NovaTextDim
                        )
                        Text(
                            text = "03/08",
                            fontFamily = TerminalFontFamily,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = NovaCyan
                        )
                    }
                }

                // Badge 2: Trust 82%
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(NovaGreen.copy(alpha = 0.12f))
                        .border(1.dp, NovaGreen.copy(alpha = 0.35f), RoundedCornerShape(8.dp))
                        .clickable { onTrustLevelClick() }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Trust",
                            fontFamily = TerminalFontFamily,
                            fontSize = 8.sp,
                            color = NovaTextDim
                        )
                        Text(
                            text = "82%",
                            fontFamily = TerminalFontFamily,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = NovaGreen
                        )
                    }
                }

                // Search Icon
                IconButton(onClick = {}, modifier = Modifier.size(30.dp)) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = NovaTextSecondary,
                        modifier = Modifier.size(17.dp)
                    )
                }

                // Notification Bell Icon
                IconButton(onClick = {}, modifier = Modifier.size(30.dp)) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifications",
                        tint = NovaTextSecondary,
                        modifier = Modifier.size(17.dp)
                    )
                }
            }
        }
    }
}
