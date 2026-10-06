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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AuditLogEntity
import com.example.data.model.PermissionRequest
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
 * Screen 7: Permissions Dashboard
 * Exactly matches Reference Image Screen 7:
 * - Header: Back, "Permissions", "Manage what NOVA can access."
 * - Permission list: Camera, Mic, Location, Files, Calendar, Contacts, Email (Limited), Payments (Blocked)
 * - High-Risk Actions: Payment / Account Changes -> Requires your approval >
 * - Manage Advanced Permissions button
 */
@Composable
fun PermissionDashboardWindow(
    pendingRequests: List<PermissionRequest>,
    auditLogs: List<AuditLogEntity>,
    onApprove: (String) -> Unit,
    onReject: (String) -> Unit,
    onBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val permissionStates = remember {
        mutableStateMapOf(
            "Camera" to true,
            "Microphone" to true,
            "Location" to true,
            "Files" to true,
            "Calendar" to true,
            "Contacts" to true,
            "Email" to true,
            "Payments" to false
        )
    }

    val permissionIcons = mapOf(
        "Camera" to Icons.Default.CameraAlt,
        "Microphone" to Icons.Default.Mic,
        "Location" to Icons.Default.LocationOn,
        "Files" to Icons.Default.Folder,
        "Calendar" to Icons.Default.CalendarMonth,
        "Contacts" to Icons.Default.Contacts,
        "Email" to Icons.Default.Email,
        "Payments" to Icons.Default.Payments
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
                    text = "Permissions",
                    fontFamily = TerminalFontFamily,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = NovaTextPrimary
                )
                Text(
                    text = "Manage what NOVA can access.",
                    fontFamily = TerminalFontFamily,
                    fontSize = 10.sp,
                    color = NovaTextSecondary
                )
            }
        }

        // 2. PERMISSIONS LIST
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Camera", "Microphone", "Location", "Files", "Calendar", "Contacts", "Email", "Payments").forEach { key ->
                val isEnabled = permissionStates[key] ?: false
                val isLimited = key == "Email"
                val isBlocked = key == "Payments" && !isEnabled

                val statusText = when {
                    isBlocked -> "Blocked"
                    isLimited -> "Limited"
                    isEnabled -> "Allowed"
                    else -> "Blocked"
                }

                val statusColor = when {
                    isBlocked -> NovaRed
                    isLimited -> NovaAmber
                    isEnabled -> NovaGreen
                    else -> NovaRed
                }

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
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(statusColor.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = permissionIcons[key] ?: Icons.Default.CameraAlt,
                                    contentDescription = key,
                                    tint = statusColor,
                                    modifier = Modifier.size(15.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = key,
                                fontFamily = TerminalFontFamily,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = NovaTextPrimary
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = statusText,
                                fontFamily = TerminalFontFamily,
                                fontSize = 10.sp,
                                color = statusColor
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Switch(
                                checked = isEnabled,
                                onCheckedChange = { permissionStates[key] = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = NovaGreen,
                                    checkedTrackColor = NovaGreen.copy(alpha = 0.35f),
                                    uncheckedThumbColor = NovaTextDim,
                                    uncheckedTrackColor = NovaCardBorder
                                )
                            )
                        }
                    }
                }
            }
        }

        // 3. HIGH RISK ACTIONS CARD
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text = "High-Risk Actions",
                fontFamily = TerminalFontFamily,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = NovaTextPrimary
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(NovaCardBg)
                    .border(1.dp, NovaCardBorder, RoundedCornerShape(12.dp))
                    .clickable { /* Advanced Approval */ }
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Payment / Account Changes",
                            fontFamily = TerminalFontFamily,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = NovaTextPrimary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Requires your approval",
                            fontFamily = TerminalFontFamily,
                            fontSize = 10.sp,
                            color = NovaCyan
                        )
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Expand",
                        tint = NovaTextSecondary
                    )
                }
            }
        }

        // 4. BOTTOM ACTION: Manage Advanced Permissions
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(NovaCardBg)
                .border(1.dp, NovaCardBorder, RoundedCornerShape(12.dp))
                .clickable { /* Advanced Permissions */ }
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Tune,
                    contentDescription = null,
                    tint = NovaCyan,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Manage Advanced Permissions",
                    fontFamily = TerminalFontFamily,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = NovaCyan
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
    }
}
