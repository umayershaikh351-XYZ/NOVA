package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WbCloudy
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.data.model.TerminalModule
import com.example.ui.theme.NovaBgDeep
import com.example.ui.theme.NovaCardBorder
import com.example.ui.theme.NovaCyan
import com.example.ui.theme.NovaGreen
import com.example.ui.theme.NovaRed
import com.example.ui.theme.NovaTextDim

/**
 * Left Sidebar Icon Dock matching Reference Image Screen 2:
 * Home, CommandLine, VisionCore, LifeGraph, AgentPanel, Devices, Permissions, AmbientMode, KillSwitch
 */
@Composable
fun ModuleDock(
    activeModule: TerminalModule,
    openModules: Set<TerminalModule>,
    onSelectModule: (TerminalModule) -> Unit,
    modifier: Modifier = Modifier
) {
    val dockItems = listOf(
        TerminalModule.DASHBOARD to Icons.Default.Home,
        TerminalModule.COMMAND_LINE to Icons.Default.Terminal,
        TerminalModule.VISION_CORE to Icons.Default.RemoveRedEye,
        TerminalModule.LIFE_GRAPH to Icons.Default.Hub,
        TerminalModule.AGENT_PANEL to Icons.Default.ViewInAr,
        TerminalModule.DEVICES to Icons.Default.Devices,
        TerminalModule.PERMISSION_DASHBOARD to Icons.Default.Security,
        TerminalModule.AMBIENT_MODE to Icons.Default.WbCloudy,
        TerminalModule.KILL_SWITCH to Icons.Default.Warning
    )

    Box(
        modifier = modifier
            .fillMaxHeight()
            .width(52.dp)
            .background(NovaBgDeep)
            .border(1.dp, NovaCardBorder.copy(alpha = 0.5f))
            .padding(vertical = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            dockItems.forEach { (module, icon) ->
                val isSelected = activeModule == module
                val isKillSwitch = module == TerminalModule.KILL_SWITCH

                val iconColor = when {
                    isKillSwitch -> NovaRed
                    isSelected -> NovaGreen
                    else -> NovaTextDim
                }

                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            if (isSelected) NovaGreen.copy(alpha = 0.15f)
                            else if (isKillSwitch) NovaRed.copy(alpha = 0.1f)
                            else androidx.compose.ui.graphics.Color.Transparent
                        )
                        .border(
                            1.dp,
                            if (isSelected) NovaGreen
                            else if (isKillSwitch) NovaRed.copy(alpha = 0.4f)
                            else androidx.compose.ui.graphics.Color.Transparent,
                            RoundedCornerShape(10.dp)
                        )
                        .clickable { onSelectModule(module) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = module.displayName,
                        tint = iconColor,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
