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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.BevelStyle
import com.example.ui.components.RetroBevelBox
import com.example.ui.components.StatusLamp
import com.example.ui.theme.AmberCrt
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.PhosphorGreen
import com.example.ui.theme.TerminalFontFamily
import com.example.ui.theme.TerminalSurface
import com.example.ui.theme.TerminalTextDim
import com.example.ui.theme.TerminalTextPrimary
import com.example.ui.theme.TerminalTextSecondary

@Composable
fun SignalScanWindow(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize().padding(8.dp)) {
        Text(
            text = "BIOMETRIC & COGNITIVE TELEMETRY (SMART BAND SYNC):",
            fontFamily = TerminalFontFamily,
            fontSize = 9.sp,
            color = AmberCrt
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Biometric Stats Matrix
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            BiometricStatBox("HRV", "78 ms", "OPTIMAL", PhosphorGreen, Modifier.weight(1f))
            BiometricStatBox("RESTING HR", "54 BPM", "LOW STRESS", CyberCyan, Modifier.weight(1f))
            BiometricStatBox("SLEEP SCORE", "92%", "8H 14M RECOVERY", PhosphorGreen, Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Electrocardiogram / HRV Waveform canvas
        Text(
            text = "REAL-TIME PHOTOPLETHYSMOGRAPHY WAVEFORM:",
            fontFamily = TerminalFontFamily,
            fontSize = 9.sp,
            color = CyberCyan
        )
        Spacer(modifier = Modifier.height(4.dp))

        RetroBevelBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp),
            style = BevelStyle.SUNKEN,
            backgroundColor = Color(0xFF03070A)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height
                val midY = h / 2f

                // Grid background
                var gx = 0f
                while (gx < w) {
                    drawLine(Color(0x1820E874), Offset(gx, 0f), Offset(gx, h), strokeWidth = 1f)
                    gx += 20f
                }
                var gy = 0f
                while (gy < h) {
                    drawLine(Color(0x1820E874), Offset(0f, gy), Offset(w, gy), strokeWidth = 1f)
                    gy += 20f
                }

                // Simulated ECG pulse waveform
                val points = listOf(
                    0.0f to 0f, 0.1f to 0f, 0.15f to -8f, 0.2f to 0f, 0.25f to -40f,
                    0.3f to 25f, 0.35f to -5f, 0.4f to 0f, 0.5f to 10f, 0.6f to 0f,
                    0.7f to 0f, 0.75f to -8f, 0.8f to 0f, 0.85f to -40f, 0.9f to 25f,
                    0.95f to 0f, 1.0f to 0f
                )

                for (i in 0 until points.size - 1) {
                    val p1 = points[i]
                    val p2 = points[i + 1]
                    drawLine(
                        color = PhosphorGreen,
                        start = Offset(p1.first * w, midY + p1.second),
                        end = Offset(p2.first * w, midY + p2.second),
                        strokeWidth = 2f
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Cognitive Load and Recommendation
        RetroBevelBox(
            modifier = Modifier.fillMaxWidth().weight(1f),
            style = BevelStyle.SUNKEN,
            backgroundColor = TerminalSurface
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    StatusLamp(color = PhosphorGreen, size = 6.dp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "AUTOPILOT COGNITIVE RECOMMENDATION:",
                        fontFamily = TerminalFontFamily,
                        fontSize = 10.sp,
                        color = PhosphorGreen
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "• Peak alertness window active until 11:45. NOVA has auto-blocked incoming notifications.",
                    fontFamily = TerminalFontFamily,
                    fontSize = 10.sp,
                    color = TerminalTextPrimary
                )
                Text(
                    text = "• Cortisol index is nominal (12.4 ug/dL). Scheduled 15-min walk at 13:00 to sustain afternoon stamina.",
                    fontFamily = TerminalFontFamily,
                    fontSize = 10.sp,
                    color = TerminalTextSecondary
                )
                Text(
                    text = "• Hydration reminder: +500ml water suggested before gym session.",
                    fontFamily = TerminalFontFamily,
                    fontSize = 10.sp,
                    color = AmberCrt
                )
            }
        }
    }
}

@Composable
private fun BiometricStatBox(
    title: String,
    value: String,
    status: String,
    accent: Color,
    modifier: Modifier = Modifier
) {
    RetroBevelBox(
        modifier = modifier,
        style = BevelStyle.SUNKEN,
        backgroundColor = Color(0xFF06090D)
    ) {
        Column(modifier = Modifier.padding(6.dp)) {
            Text(text = title, fontFamily = TerminalFontFamily, fontSize = 8.sp, color = TerminalTextDim)
            Text(text = value, fontFamily = TerminalFontFamily, fontSize = 12.sp, color = accent)
            Text(text = status, fontFamily = TerminalFontFamily, fontSize = 7.sp, color = TerminalTextSecondary)
        }
    }
}
