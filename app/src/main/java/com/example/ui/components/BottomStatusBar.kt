package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AmberCrt
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.PhosphorGreen
import com.example.ui.theme.TerminalDarkNavy
import com.example.ui.theme.TerminalFontFamily
import com.example.ui.theme.TerminalTextDim
import com.example.ui.theme.TerminalTextPrimary

@Composable
fun BottomStatusBar(
    currentRouter: String,
    tokensPerSec: Int,
    runningSubroutines: Int,
    isVaultLocked: Boolean,
    modifier: Modifier = Modifier
) {
    RetroBevelBox(
        modifier = modifier.fillMaxWidth(),
        style = BevelStyle.RAISED,
        backgroundColor = TerminalDarkNavy,
        bevelWidth = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // ROUTING
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "ROUTER: ",
                    fontFamily = TerminalFontFamily,
                    fontSize = 9.sp,
                    color = TerminalTextDim
                )
                Text(
                    text = currentRouter,
                    fontFamily = TerminalFontFamily,
                    fontSize = 9.sp,
                    color = CyberCyan
                )
            }

            // TELEMETRY
            Row(verticalAlignment = Alignment.CenterVertically) {
                StatusLamp(color = PhosphorGreen, size = 5.dp, isPulsing = true)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "TELEMETRY: ",
                    fontFamily = TerminalFontFamily,
                    fontSize = 9.sp,
                    color = TerminalTextDim
                )
                Text(
                    text = "$tokensPerSec T/s",
                    fontFamily = TerminalFontFamily,
                    fontSize = 9.sp,
                    color = PhosphorGreen
                )
            }

            // SWARM
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "SWARM: ",
                    fontFamily = TerminalFontFamily,
                    fontSize = 9.sp,
                    color = TerminalTextDim
                )
                Text(
                    text = "$runningSubroutines SUB-ROUTINES",
                    fontFamily = TerminalFontFamily,
                    fontSize = 9.sp,
                    color = AmberCrt
                )
            }

            // VAULT STATE
            Row(verticalAlignment = Alignment.CenterVertically) {
                StatusLamp(
                    color = if (isVaultLocked) PhosphorGreen else AmberCrt,
                    size = 5.dp,
                    isPulsing = false
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (isVaultLocked) "VAULT: AES-256 ARMED" else "VAULT: UNLOCKED",
                    fontFamily = TerminalFontFamily,
                    fontSize = 9.sp,
                    color = if (isVaultLocked) PhosphorGreen else AmberCrt
                )
            }
        }
    }
}
