package com.example.ui.components

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AgentInfo
import com.example.data.model.AgentModel
import com.example.data.model.AgentStatus
import com.example.ui.theme.NovaAmber
import com.example.ui.theme.NovaBgSurface
import com.example.ui.theme.NovaCardBg
import com.example.ui.theme.NovaCardBorder
import com.example.ui.theme.NovaCyan
import com.example.ui.theme.NovaGreen
import com.example.ui.theme.NovaRed
import com.example.ui.theme.NovaTextDim
import com.example.ui.theme.NovaTextPrimary
import com.example.ui.theme.NovaTextSecondary
import com.example.ui.theme.TerminalFontFamily

/**
 * Screen 6: Agent Panel
 * Exactly matches Reference Image Screen 6:
 * - Header: Agent icon, "Agent Panel", "8 Active Agents", Bell
 * - Cards: Research Agent, Code Agent, Travel Agent, Health Agent, Finance Agent, Creative Agent
 * - Status pills (RUNNING, ACTIVE, WAITING, IDLE, DONE)
 * - Waveform monitors
 * - Actions: Pause All, Kill Switch
 */
@Composable
fun AgentPanel(
    agents: List<AgentInfo>,
    selectedAgentId: String?,
    onSelectAgent: (String) -> Unit,
    onPauseAgent: (String) -> Unit,
    onResumeAgent: (String) -> Unit,
    onInterveneAgent: (String) -> Unit,
    onTakeOverAgent: (String) -> Unit,
    onChangeModel: (String, AgentModel) -> Unit,
    onKillSwitchClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val transition = rememberInfiniteTransition(label = "agentWave")
    val wavePhase by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(NovaBgSurface)
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            // 1. HEADER
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Agent Panel",
                        fontFamily = TerminalFontFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = NovaTextPrimary
                    )
                    Text(
                        text = "8 Active Agents",
                        fontFamily = TerminalFontFamily,
                        fontSize = 11.sp,
                        color = NovaTextSecondary
                    )
                }

                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(NovaCardBg)
                        .border(1.dp, NovaCardBorder, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifications",
                        tint = NovaTextSecondary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            // 2. AGENTS LIST
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(agents, key = { it.id }) { agent ->
                    AgentCard(
                        agent = agent,
                        wavePhase = wavePhase,
                        onClick = { onSelectAgent(agent.id) },
                        onPause = { onPauseAgent(agent.id) }
                    )
                }
            }
        }

        // 3. BOTTOM BUTTONS: [Pause All] and [Kill Switch]
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Pause All Button
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(NovaCardBg)
                    .border(1.dp, NovaCyan, RoundedCornerShape(12.dp))
                    .clickable { /* Pause all agents */ }
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Pause,
                        contentDescription = null,
                        tint = NovaCyan,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Pause All",
                        fontFamily = TerminalFontFamily,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = NovaCyan
                    )
                }
            }

            // Kill Switch Button
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(NovaRed.copy(alpha = 0.15f))
                    .border(1.dp, NovaRed, RoundedCornerShape(12.dp))
                    .clickable { onKillSwitchClick() }
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = NovaRed,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Kill Switch",
                        fontFamily = TerminalFontFamily,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = NovaRed
                    )
                }
            }
        }
    }
}

@Composable
private fun AgentCard(
    agent: AgentInfo,
    wavePhase: Float,
    onClick: () -> Unit,
    onPause: () -> Unit
) {
    val statusColor = when (agent.status) {
        AgentStatus.RUNNING, AgentStatus.ACTIVE -> NovaGreen
        AgentStatus.WAITING -> NovaAmber
        AgentStatus.DONE -> NovaGreen
        AgentStatus.IDLE -> NovaTextDim
        AgentStatus.ALERT -> NovaRed
        AgentStatus.PAUSED -> NovaTextSecondary
    }

    val iconVector = when {
        agent.name.contains("RESEARCH") -> Icons.Default.Search
        agent.name.contains("CODE") -> Icons.Default.Code
        agent.name.contains("TRAVEL") -> Icons.Default.Flight
        agent.name.contains("HEALTH") -> Icons.Default.FitnessCenter
        agent.name.contains("FINANCE") -> Icons.Default.Savings
        else -> Icons.Default.Palette
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(NovaCardBg)
            .border(1.dp, NovaCardBorder, RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .padding(12.dp)
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
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(statusColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = iconVector,
                        contentDescription = null,
                        tint = statusColor,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = agent.name,
                            fontFamily = TerminalFontFamily,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = NovaTextPrimary
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(5.dp)
                                .background(statusColor, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = agent.status.label,
                            fontFamily = TerminalFontFamily,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = statusColor
                        )
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = agent.currentTask,
                        fontFamily = TerminalFontFamily,
                        fontSize = 10.sp,
                        color = NovaTextSecondary,
                        maxLines = 1
                    )
                }
            }

            // Right side: Waveform (if running) or dismiss 'x' button
            if (agent.status == AgentStatus.RUNNING) {
                Canvas(modifier = Modifier.size(width = 38.dp, height = 18.dp)) {
                    val barCount = 5
                    val barWidth = 3f
                    val spacing = (size.width - barCount * barWidth) / (barCount - 1)
                    for (i in 0 until barCount) {
                        val hFactor = 0.25f + 0.75f * kotlin.math.abs(
                            kotlin.math.sin((i * 1.1f + wavePhase * 6.28f).toDouble())
                        ).toFloat()
                        val barH = size.height * hFactor
                        drawRect(
                            color = NovaGreen,
                            topLeft = Offset(i * (barWidth + spacing), (size.height - barH) / 2f),
                            size = androidx.compose.ui.geometry.Size(barWidth, barH)
                        )
                    }
                }
            } else {
                IconButton(onClick = onPause, modifier = Modifier.size(24.dp)) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Dismiss",
                        tint = NovaTextDim,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}
