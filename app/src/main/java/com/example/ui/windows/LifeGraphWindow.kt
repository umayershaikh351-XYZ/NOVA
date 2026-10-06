package com.example.ui.windows

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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.GoalItem
import com.example.data.model.LifeGraphEntity
import com.example.data.model.MemoryItem
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
 * Screen 3: LifeGraph
 * Exactly matches Reference Image Screen 3:
 * - Header: Back arrow, "LifeGraph", "Your goals, habits, relationships & more."
 * - Category Tabs: Goals (active), Habits, Relationships, Preferences
 * - Goal Progress Cards: Learn Cybersecurity (82%), Build NOVA (61%), Fitness & Health (47%), Financial Freedom (38%), Travel the World (25%)
 * - Recent Memories: Deep work, AI/Cybersecurity, Birthday
 * - View Full Graph action button
 */
@Composable
fun LifeGraphWindow(
    nodes: List<LifeGraphEntity>,
    onAddNode: (String, String, String, String) -> Unit,
    onDeleteNode: (LifeGraphEntity) -> Unit,
    onBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf("Goals") }
    val tabs = listOf("Goals", "Habits", "Relationships", "Preferences")

    val goals = remember {
        listOf(
            GoalItem("Learn Cybersecurity", 82, "security"),
            GoalItem("Build NOVA", 61, "nova"),
            GoalItem("Fitness & Health", 47, "fitness"),
            GoalItem("Financial Freedom", 38, "finance"),
            GoalItem("Travel the World", 25, "travel")
        )
    }

    val memories = remember {
        listOf(
            MemoryItem("You prefer deep work in the morning", "2 days ago"),
            MemoryItem("You're interested in AI, space and cybersecurity", "5 days ago"),
            MemoryItem("Your birthday is on July 12", "1 week ago")
        )
    }

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
                    Text(
                        text = "LifeGraph",
                        fontFamily = TerminalFontFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = NovaTextPrimary
                    )
                    Text(
                        text = "Your goals, habits, relationships & more.",
                        fontFamily = TerminalFontFamily,
                        fontSize = 10.sp,
                        color = NovaTextSecondary
                    )
                }
            }

            IconButton(onClick = {}, modifier = Modifier.size(32.dp)) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Menu",
                    tint = NovaTextSecondary
                )
            }
        }

        // 2. CATEGORY PILL TABS
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            tabs.forEach { tab ->
                val isSelected = selectedTab == tab
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (isSelected) NovaGreen.copy(alpha = 0.2f) else NovaCardBg)
                        .border(
                            1.dp,
                            if (isSelected) NovaGreen else NovaCardBorder,
                            RoundedCornerShape(20.dp)
                        )
                        .clickable { selectedTab = tab }
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tab,
                        fontFamily = TerminalFontFamily,
                        fontSize = 10.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) NovaGreen else NovaTextSecondary
                    )
                }
            }
        }

        // 3. GOALS LIST
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            goals.forEach { goal ->
                GoalProgressCard(goal = goal)
            }
        }

        // 4. RECENT MEMORIES SECTION
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Recent Memories",
                fontFamily = TerminalFontFamily,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = NovaTextPrimary
            )

            memories.forEach { mem ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(NovaCardBg)
                        .border(1.dp, NovaCardBorder, RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(NovaCyan.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = when {
                                    mem.text.contains("deep work") -> Icons.Default.Lightbulb
                                    mem.text.contains("birthday") -> Icons.Default.Celebration
                                    else -> Icons.Default.AutoAwesome
                                },
                                contentDescription = null,
                                tint = NovaCyan,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = mem.text,
                                fontFamily = TerminalFontFamily,
                                fontSize = 11.sp,
                                color = NovaTextPrimary
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = mem.timestampText,
                                fontFamily = TerminalFontFamily,
                                fontSize = 9.sp,
                                color = NovaTextDim
                            )
                        }
                    }
                }
            }
        }

        // 5. VIEW FULL GRAPH ACTION BUTTON
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, NovaGreen, RoundedCornerShape(12.dp))
                .background(NovaGreen.copy(alpha = 0.1f))
                .clickable { /* Graph navigation */ }
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = null,
                    tint = NovaGreen,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "View Full Graph",
                    fontFamily = TerminalFontFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = NovaGreen,
                    letterSpacing = 0.5.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
private fun GoalProgressCard(goal: GoalItem) {
    val progressColor = when (goal.iconType) {
        "security" -> NovaGreen
        "nova" -> NovaCyan
        "fitness" -> NovaAmber
        "finance" -> Color(0xFFBA68C8)
        else -> NovaCyan
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(NovaCardBg)
            .border(1.dp, NovaCardBorder, RoundedCornerShape(14.dp))
            .padding(12.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(progressColor.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = when (goal.iconType) {
                                "security" -> Icons.Default.Security
                                "nova" -> Icons.Default.Sync
                                "fitness" -> Icons.Default.FitnessCenter
                                "finance" -> Icons.Default.Savings
                                else -> Icons.Default.Public
                            },
                            contentDescription = null,
                            tint = progressColor,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = goal.title,
                        fontFamily = TerminalFontFamily,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = NovaTextPrimary
                    )
                }

                Text(
                    text = "${goal.percentage}%",
                    fontFamily = TerminalFontFamily,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = progressColor
                )
            }

            LinearProgressIndicator(
                progress = { goal.percentage / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = progressColor,
                trackColor = NovaCardBorder,
                strokeCap = StrokeCap.Round
            )
        }
    }
}
