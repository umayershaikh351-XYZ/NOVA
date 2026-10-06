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
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Laptop
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.Watch
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
import com.example.data.model.DeviceItem
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
 * Screen 10: Device Integration
 * Exactly matches Reference Image Screen 10:
 * - Header: Back, "Devices", "Connected hardware & neural bridges"
 * - List: This Phone, Laptop, Smartwatch, Earbuds, AR Glasses, Smart Home, Car
 * - Multi-Device Sync card
 */
@Composable
fun DeviceIntegrationWindow(
    onBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val devices = listOf(
        DeviceItem("This Phone", "NOVA Mobile App", true),
        DeviceItem("Laptop", "Windows 11", true),
        DeviceItem("Smartwatch", "Galaxy Watch", true),
        DeviceItem("Earbuds", "Galaxy Buds", true),
        DeviceItem("AR Glasses", "Not Connected", false),
        DeviceItem("Smart Home", "Not Connected", false),
        DeviceItem("Car", "Not Connected", false)
    )

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
                    text = "Devices",
                    fontFamily = TerminalFontFamily,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = NovaTextPrimary
                )
                Text(
                    text = "Connected hardware & neural bridges",
                    fontFamily = TerminalFontFamily,
                    fontSize = 10.sp,
                    color = NovaTextSecondary
                )
            }
        }

        // 2. DEVICES LIST
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            devices.forEach { device ->
                val icon = when (device.name) {
                    "This Phone" -> Icons.Default.PhoneAndroid
                    "Laptop" -> Icons.Default.Laptop
                    "Smartwatch" -> Icons.Default.Watch
                    "Earbuds" -> Icons.Default.Headphones
                    "AR Glasses" -> Icons.Default.Visibility
                    "Smart Home" -> Icons.Default.Home
                    else -> Icons.Default.DirectionsCar
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(NovaCardBg)
                        .border(1.dp, NovaCardBorder, RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp, vertical = 10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (device.isConnected) NovaGreen.copy(alpha = 0.15f)
                                        else NovaTextDim.copy(alpha = 0.15f)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = device.name,
                                    tint = if (device.isConnected) NovaGreen else NovaTextSecondary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = device.name,
                                    fontFamily = TerminalFontFamily,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = NovaTextPrimary
                                )
                                Text(
                                    text = device.model,
                                    fontFamily = TerminalFontFamily,
                                    fontSize = 10.sp,
                                    color = NovaTextSecondary
                                )
                            }
                        }

                        if (device.isConnected) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(NovaGreen, CircleShape)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Reconnect",
                                tint = NovaTextDim,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }

        // 3. MULTI-DEVICE SYNC CARD
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(NovaCardBg)
                .border(1.dp, NovaCardBorder, RoundedCornerShape(14.dp))
                .padding(14.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(NovaCyan.copy(alpha = 0.15f))
                        .border(1.dp, NovaCyan.copy(alpha = 0.4f), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Sync,
                        contentDescription = null,
                        tint = NovaCyan,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Multi-Device Sync",
                        fontFamily = TerminalFontFamily,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = NovaTextPrimary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Keep your data and agents in sync across all your devices.",
                        fontFamily = TerminalFontFamily,
                        fontSize = 9.sp,
                        color = NovaTextSecondary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
    }
}
