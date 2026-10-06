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
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.example.data.model.CommandLineItem
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
 * Screen 5: CommandLine
 * Exactly matches Reference Image Screen 5:
 * - Header: Back, "CommandLine", "Direct control. Natural language."
 * - Command prompt card: "NOVA: // > plan a 7 day fitness routine for me"
 * - Routing to agents: Health Agent (checked), Research Agent (checked), Calendar Agent (...)
 * - Processing... progress bar (62%)
 * - Live Output: Personalized plan, Checking schedule, Workout options, Meal plan
 * - Type a command input field with send button
 */
@Composable
fun CommandLineWindow(
    history: List<CommandLineItem>,
    onExecuteCommand: (String) -> Unit,
    onBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var commandInput by remember { mutableStateOf("") }
    var activePrompt by remember { mutableStateOf("plan a 7 day fitness routine for me") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(NovaBgSurface)
            .padding(horizontal = 14.dp, vertical = 6.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // 1. HEADER
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
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
                        text = "CommandLine",
                        fontFamily = TerminalFontFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = NovaTextPrimary
                    )
                    Text(
                        text = "Direct control. Natural language.",
                        fontFamily = TerminalFontFamily,
                        fontSize = 10.sp,
                        color = NovaTextSecondary
                    )
                }
            }

            // 2. COMMAND PROMPT CARD: "NOVA: // > plan a 7 day fitness routine for me"
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(NovaCardBg)
                    .border(1.dp, NovaCardBorder, RoundedCornerShape(14.dp))
                    .padding(14.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "NOVA: //",
                        fontFamily = TerminalFontFamily,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = NovaCyan
                    )
                    Text(
                        text = "> $activePrompt",
                        fontFamily = TerminalFontFamily,
                        fontSize = 13.sp,
                        color = NovaGreen,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // 3. ROUTING TO AGENTS SECTION
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Routing to agents...",
                    fontFamily = TerminalFontFamily,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = NovaTextSecondary
                )

                // Agent Route 1: Health Agent
                AgentRouteRow(
                    name = "Health Agent",
                    icon = Icons.Default.FitnessCenter,
                    iconColor = NovaGreen,
                    isChecked = true
                )

                // Agent Route 2: Research Agent
                AgentRouteRow(
                    name = "Research Agent",
                    icon = Icons.Default.Search,
                    iconColor = NovaCyan,
                    isChecked = true
                )

                // Agent Route 3: Calendar Agent
                AgentRouteRow(
                    name = "Calendar Agent",
                    icon = Icons.Default.CalendarMonth,
                    iconColor = NovaCyan,
                    isChecked = false,
                    isPending = true
                )
            }

            // 4. PROCESSING PROGRESS BAR (62%)
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Processing...",
                        fontFamily = TerminalFontFamily,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = NovaTextSecondary
                    )
                    Text(
                        text = "62%",
                        fontFamily = TerminalFontFamily,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = NovaGreen
                    )
                }

                LinearProgressIndicator(
                    progress = { 0.62f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = NovaGreen,
                    trackColor = NovaCardBorder,
                    strokeCap = StrokeCap.Round
                )
            }

            // 5. LIVE OUTPUT CARD
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "Live Output",
                    fontFamily = TerminalFontFamily,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = NovaTextSecondary
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(NovaCardBg)
                        .border(1.dp, NovaCardBorder, RoundedCornerShape(14.dp))
                        .padding(14.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        OutputBulletItem("Creating personalized plan...")
                        OutputBulletItem("Checking your schedule...")
                        OutputBulletItem("Finding best workout options...")
                        OutputBulletItem("Generating meal plan...")
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

        // 6. BOTTOM INPUT ROW: "Type a command..." + Send Arrow Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(NovaCardBg)
                .border(1.dp, NovaCardBorder, RoundedCornerShape(14.dp))
                .padding(horizontal = 8.dp, vertical = 2.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = commandInput,
                    onValueChange = { commandInput = it },
                    placeholder = {
                        Text(
                            text = "Type a command...",
                            fontFamily = TerminalFontFamily,
                            fontSize = 11.sp,
                            color = NovaTextDim
                        )
                    },
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = NovaGreen,
                        unfocusedTextColor = NovaTextPrimary,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        cursorColor = NovaGreen
                    ),
                    singleLine = true
                )

                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(NovaGreen.copy(alpha = 0.2f))
                        .border(1.dp, NovaGreen, CircleShape)
                        .clickable {
                            if (commandInput.isNotBlank()) {
                                activePrompt = commandInput
                                onExecuteCommand(commandInput)
                                commandInput = ""
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send",
                        tint = NovaGreen,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun AgentRouteRow(
    name: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    isChecked: Boolean,
    isPending: Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(NovaCardBg)
            .border(1.dp, NovaCardBorder, RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(26.dp)
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
                    text = name,
                    fontFamily = TerminalFontFamily,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = NovaTextPrimary
                )
            }

            if (isChecked) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Completed",
                    tint = NovaGreen,
                    modifier = Modifier.size(16.dp)
                )
            } else if (isPending) {
                Text(
                    text = "...",
                    fontFamily = TerminalFontFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = NovaTextSecondary
                )
            }
        }
    }
}

@Composable
private fun OutputBulletItem(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "•",
            fontFamily = TerminalFontFamily,
            fontSize = 14.sp,
            color = NovaGreen
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontFamily = TerminalFontFamily,
            fontSize = 11.sp,
            color = NovaTextPrimary
        )
    }
}
