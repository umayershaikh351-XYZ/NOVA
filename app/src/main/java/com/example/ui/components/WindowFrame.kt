package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.TerminalModule
import com.example.ui.theme.AlertRed
import com.example.ui.theme.AmberCrt
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.PhosphorGreen
import com.example.ui.theme.TerminalBevelLight
import com.example.ui.theme.TerminalBevelShadow
import com.example.ui.theme.TerminalDarkNavy
import com.example.ui.theme.TerminalFontFamily
import com.example.ui.theme.TerminalSurface
import com.example.ui.theme.TerminalTextPrimary
import kotlin.math.roundToInt

@Composable
fun WindowFrame(
    title: String,
    module: TerminalModule,
    isMaximized: Boolean,
    offsetX: Float,
    offsetY: Float,
    onDrag: (Float, Float) -> Unit,
    onMinimize: () -> Unit,
    onToggleMaximize: () -> Unit,
    onClose: () -> Unit,
    onFocus: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val windowModifier = if (isMaximized) {
        modifier.fillMaxSize()
    } else {
        modifier.offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
    }

    RetroBevelBox(
        modifier = windowModifier.clickable { onFocus() },
        style = BevelStyle.RAISED,
        backgroundColor = TerminalDarkNavy,
        bevelWidth = 3.dp
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Title Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF14202B))
                    .pointerInput(isMaximized) {
                        if (!isMaximized) {
                            detectDragGestures { change, dragAmount ->
                                change.consume()
                                onDrag(dragAmount.x, dragAmount.y)
                            }
                        }
                    }
                    .padding(horizontal = 6.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Window Title with module tag
                Row(verticalAlignment = Alignment.CenterVertically) {
                    StatusLamp(
                        color = when (module) {
                            TerminalModule.COMMAND_LINE -> AmberCrt
                            TerminalModule.VISION_CORE -> CyberCyan
                            TerminalModule.LIFE_GRAPH -> PhosphorGreen
                            else -> PhosphorGreen
                        },
                        size = 5.dp
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "[${module.shortcut}] $title",
                        fontFamily = TerminalFontFamily,
                        fontSize = 11.sp,
                        color = TerminalTextPrimary,
                        letterSpacing = 0.5.sp
                    )
                }

                // Window Controls [_] [□] [X]
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Minimize [_]
                    RetroWindowButton(
                        text = "_",
                        onClick = onMinimize,
                        color = TerminalTextPrimary
                    )

                    // Maximize/Restore [□]
                    RetroWindowButton(
                        text = if (isMaximized) "◱" else "□",
                        onClick = onToggleMaximize,
                        color = CyberCyan
                    )

                    // Close [X]
                    RetroWindowButton(
                        text = "X",
                        onClick = onClose,
                        color = AlertRed
                    )
                }
            }

            // Window Content Canvas
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(TerminalSurface)
            ) {
                content()
            }
        }
    }
}

@Composable
private fun RetroWindowButton(
    text: String,
    onClick: () -> Unit,
    color: Color
) {
    RetroBevelBox(
        modifier = Modifier
            .size(18.dp)
            .clickable { onClick() },
        style = BevelStyle.RAISED,
        backgroundColor = TerminalDarkNavy,
        bevelWidth = 1.dp
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = text,
                fontFamily = TerminalFontFamily,
                fontSize = 10.sp,
                color = color
            )
        }
    }
}
