package com.example.ui.windows

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.BevelStyle
import com.example.ui.components.RetroBevelBox
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

@Composable
fun WorldModelWindow(
    modifier: Modifier = Modifier
) {
    val marketTelemetry = remember {
        listOf(
            "NASDAQ: 19,842.10 (+1.24%)" to PhosphorGreen,
            "S&P 500: 5,910.45 (+0.81%)" to PhosphorGreen,
            "BTC/USD: $84,230.00 (+4.15%)" to CyberCyan,
            "US 10Y: 4.18% (-0.03%)" to AmberCrt,
            "BRENT CRUDE: $73.40 (-1.10%)" to AlertRed
        )
    }

    val globalEvents = remember {
        listOf(
            "INTEL-01" to "Major breakthrough in photonic computing announced by MIT quantum labs.",
            "INTEL-02" to "EU opens fast-track regulatory sandbox for autonomous agent financial operations.",
            "INTEL-03" to "Satellite telemetry detects Category 4 cyclone approaching Pacific shipping corridor.",
            "INTEL-04" to "Federal Reserve keeps benchmark rate steady at 4.25%; hints at dual cuts in Q4.",
            "INTEL-05" to "Global memory foundry reaches 99.4% yield on 1.4nm GAA silicon nodes."
        )
    }

    Column(modifier = modifier.fillMaxSize().padding(8.dp)) {
        // Market Ticker Row
        Text(
            text = "LIVE GLOBAL MACRO DATA FEEDS:",
            fontFamily = TerminalFontFamily,
            fontSize = 9.sp,
            color = AmberCrt
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            marketTelemetry.take(3).forEach { (ticker, color) ->
                RetroBevelBox(
                    modifier = Modifier.weight(1f),
                    style = BevelStyle.SUNKEN,
                    backgroundColor = Color(0xFF06090D)
                ) {
                    Text(
                        text = ticker,
                        fontFamily = TerminalFontFamily,
                        fontSize = 8.sp,
                        color = color,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Live Events Feed
        Text(
            text = "OUTSIDE-WORLD SENSORS & REAL-TIME INTELLIGENCE:",
            fontFamily = TerminalFontFamily,
            fontSize = 9.sp,
            color = CyberCyan
        )
        Spacer(modifier = Modifier.height(4.dp))

        RetroBevelBox(
            modifier = Modifier.fillMaxWidth().weight(1f),
            style = BevelStyle.SUNKEN,
            backgroundColor = Color(0xFF04060A)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(globalEvents) { (tag, event) ->
                    RetroBevelBox(
                        modifier = Modifier.fillMaxWidth(),
                        style = BevelStyle.RAISED,
                        backgroundColor = TerminalSurface
                    ) {
                        Row(
                            modifier = Modifier.padding(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            StatusLamp(color = CyberCyan, size = 5.dp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Column {
                                Text(
                                    text = tag,
                                    fontFamily = TerminalFontFamily,
                                    fontSize = 9.sp,
                                    color = AmberCrt
                                )
                                Text(
                                    text = event,
                                    fontFamily = TerminalFontFamily,
                                    fontSize = 10.sp,
                                    color = TerminalTextPrimary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
