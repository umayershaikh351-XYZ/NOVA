package com.example.ui.windows

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.BevelStyle
import com.example.ui.components.RetroBevelBox
import com.example.ui.components.RetroButton
import com.example.ui.components.StatusLamp
import com.example.ui.theme.AlertRed
import com.example.ui.theme.AmberCrt
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.PhosphorGreen
import com.example.ui.theme.TerminalFontFamily
import com.example.ui.theme.TerminalSurface
import com.example.ui.theme.TerminalTextDim
import com.example.ui.theme.TerminalTextPrimary
import com.example.ui.theme.TerminalTextSecondary

data class TimelineNode(
    val title: String,
    val timeHorizon: String,
    val probability: Int,
    val impact: String,
    val state: String // "COMMITTED", "SIMULATED", "ALTERNATIVE"
)

@Composable
fun TimelineWindow(
    onSimulateFuture: () -> Unit,
    modifier: Modifier = Modifier
) {
    val branches = remember {
        mutableStateListOf(
            TimelineNode(
                title = "ORIGIN: Q3 Production Launch & Initial 5,000 Signups",
                timeHorizon = "T - 30 DAYS",
                probability = 100,
                impact = "BASE_REALITY",
                state = "COMMITTED"
            ),
            TimelineNode(
                title = "PRESENT: Series A Term Sheet Execution + Enterprise Pilots",
                timeHorizon = "NOW (DAY 0)",
                probability = 100,
                impact = "CRITICAL_PATH",
                state = "COMMITTED"
            ),
            TimelineNode(
                title = "BRANCH ALPHA: Agent Swarm drives 40% inbound leads conversion",
                timeHorizon = "T + 60 DAYS",
                probability = 84,
                impact = "+$350K ARR EXTENSION",
                state = "SIMULATED"
            ),
            TimelineNode(
                title = "BRANCH BETA: Conservative expansion without AI automation",
                timeHorizon = "T + 90 DAYS",
                probability = 32,
                impact = "SLOW GROWTH / -6 MO RUNWAY",
                state = "ALTERNATIVE"
            )
        )
    }

    Column(modifier = modifier.fillMaxSize().padding(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "BRANCHING TIMELINE & LIFE SIMULATOR:",
                fontFamily = TerminalFontFamily,
                fontSize = 9.sp,
                color = CyberCyan
            )

            RetroButton(
                text = "SIMULATE NEXT 90 DAYS >>",
                onClick = {
                    onSimulateFuture()
                    branches.add(
                        TimelineNode(
                            title = "SIMULATED BRANCH GAMMA: Digital Twin fully delegated with 92% accuracy",
                            timeHorizon = "T + 120 DAYS",
                            probability = 76,
                            impact = "+18 HRS/WEEK FOCUS RECLAIMED",
                            state = "SIMULATED"
                        )
                    )
                },
                textColor = PhosphorGreen
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Visual Branching Graph Canvas
        RetroBevelBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            style = BevelStyle.SUNKEN,
            backgroundColor = Color(0xFF04070B)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height
                val midY = h / 2f

                // Root branch line
                drawLine(PhosphorGreen, Offset(30f, midY), Offset(w * 0.45f, midY), strokeWidth = 3f)

                // Branching fork
                drawLine(CyberCyan, Offset(w * 0.45f, midY), Offset(w * 0.85f, midY - 22f), strokeWidth = 2.5f)
                drawLine(AmberCrt, Offset(w * 0.45f, midY), Offset(w * 0.85f, midY + 22f), strokeWidth = 2f)

                // Nodes
                drawCircle(PhosphorGreen, radius = 6f, center = Offset(30f, midY))
                drawCircle(PhosphorGreen, radius = 7f, center = Offset(w * 0.45f, midY))
                drawCircle(CyberCyan, radius = 6f, center = Offset(w * 0.85f, midY - 22f))
                drawCircle(AmberCrt, radius = 5f, center = Offset(w * 0.85f, midY + 22f))
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Branch Nodes List
        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(branches) { node ->
                RetroBevelBox(
                    modifier = Modifier.fillMaxWidth(),
                    style = BevelStyle.RAISED,
                    backgroundColor = TerminalSurface
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                StatusLamp(
                                    color = when (node.state) {
                                        "COMMITTED" -> PhosphorGreen
                                        "SIMULATED" -> CyberCyan
                                        else -> AmberCrt
                                    },
                                    size = 5.dp
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "[${node.timeHorizon}]",
                                    fontFamily = TerminalFontFamily,
                                    fontSize = 9.sp,
                                    color = AmberCrt
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = node.state,
                                    fontFamily = TerminalFontFamily,
                                    fontSize = 9.sp,
                                    color = when (node.state) {
                                        "COMMITTED" -> PhosphorGreen
                                        "SIMULATED" -> CyberCyan
                                        else -> AmberCrt
                                    }
                                )
                            }

                            Text(
                                text = "${node.probability}% PROBABILITY",
                                fontFamily = TerminalFontFamily,
                                fontSize = 9.sp,
                                color = PhosphorGreen
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = node.title,
                            fontFamily = TerminalFontFamily,
                            fontSize = 11.sp,
                            color = TerminalTextPrimary
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "PREDICTED IMPACT: ${node.impact}",
                            fontFamily = TerminalFontFamily,
                            fontSize = 9.sp,
                            color = TerminalTextSecondary
                        )
                    }
                }
            }
        }
    }
}
