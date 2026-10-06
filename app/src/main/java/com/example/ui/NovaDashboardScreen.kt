package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.TerminalModule
import com.example.ui.components.AgentPanel
import com.example.ui.components.BottomStatusBar
import com.example.ui.components.CrtScanlineOverlay
import com.example.ui.components.ModuleDock
import com.example.ui.components.TerminalTopBar
import com.example.ui.theme.NovaBgDeep
import com.example.ui.theme.TerminalFontFamily
import com.example.ui.windows.AmbientModeWindow
import com.example.ui.windows.BootSequenceView
import com.example.ui.windows.CommandLineWindow
import com.example.ui.windows.DeviceIntegrationWindow
import com.example.ui.windows.KillSwitchOverlay
import com.example.ui.windows.LifeGraphWindow
import com.example.ui.windows.MainDashboardView
import com.example.ui.windows.PermissionDashboardWindow
import com.example.ui.windows.ScratchPadWindow
import com.example.ui.windows.SignalScanWindow
import com.example.ui.windows.TimelineWindow
import com.example.ui.windows.VisionCoreWindow
import com.example.ui.windows.WorldModelWindow

@Composable
fun NovaDashboardScreen(
    viewModel: NovaMainViewModel,
    modifier: Modifier = Modifier
) {
    val isBooting by viewModel.isBooting.collectAsStateWithLifecycle()
    val isKillSwitchActive by viewModel.isKillSwitchActive.collectAsStateWithLifecycle()
    val isSoundMuted by viewModel.isSoundMuted.collectAsStateWithLifecycle()
    val trustLevel by viewModel.trustLevel.collectAsStateWithLifecycle()
    val isVaultLocked by viewModel.isVaultLocked.collectAsStateWithLifecycle()
    val tokensPerSec by viewModel.tokensPerSec.collectAsStateWithLifecycle()

    val lifeGraphNodes by viewModel.lifeGraphNodes.collectAsStateWithLifecycle()
    val auditLogs by viewModel.auditLogs.collectAsStateWithLifecycle()
    val scratchNotes by viewModel.scratchNotes.collectAsStateWithLifecycle()
    val agents by viewModel.agents.collectAsStateWithLifecycle()
    val selectedAgentId by viewModel.selectedAgentId.collectAsStateWithLifecycle()
    val pendingPermissions by viewModel.pendingPermissions.collectAsStateWithLifecycle()
    val commandHistory by viewModel.commandHistory.collectAsStateWithLifecycle()

    val activeModule by viewModel.activeModule.collectAsStateWithLifecycle()
    val openWindows by viewModel.openWindows.collectAsStateWithLifecycle()

    // Screen 1: Launch / Boot Screen
    if (isBooting) {
        BootSequenceView(
            onBootComplete = { viewModel.completeBoot() },
            modifier = modifier
        )
        return
    }

    // Screen 8: Full Emergency Kill Switch Mode
    if (isKillSwitchActive || activeModule == TerminalModule.KILL_SWITCH) {
        KillSwitchOverlay(
            onDisengageKillSwitch = {
                viewModel.disengageKillSwitch()
                viewModel.openModule(TerminalModule.DASHBOARD)
            },
            modifier = modifier
        )
        return
    }

    Box(modifier = modifier.fillMaxSize().background(NovaBgDeep)) {
        Scaffold(
            topBar = {
                TerminalTopBar(
                    activeAgentCount = agents.count { it.status.name == "RUNNING" || it.status.name == "ACTIVE" },
                    trustLevel = trustLevel,
                    onTrustLevelClick = { viewModel.cycleTrustLevel() },
                    isSoundMuted = isSoundMuted,
                    onToggleSound = { viewModel.toggleSound() },
                    onKillSwitchClick = { viewModel.openModule(TerminalModule.KILL_SWITCH) },
                    onLogoClick = { viewModel.openModule(TerminalModule.DASHBOARD) }
                )
            },
            containerColor = NovaBgDeep
        ) { innerPadding ->
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                val isWideScreen = maxWidth >= 880.dp

                Row(modifier = Modifier.fillMaxSize()) {
                    // LEFT SIDEBAR: ICON DOCK (Screen 2 Dock)
                    ModuleDock(
                        activeModule = activeModule,
                        openModules = openWindows.keys,
                        onSelectModule = { module ->
                            viewModel.openModule(module)
                        }
                    )

                    // MAIN CANVAS: Dynamic Module Screen
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        when (activeModule) {
                            TerminalModule.DASHBOARD -> {
                                MainDashboardView(
                                    onNavigateModule = { module ->
                                        viewModel.openModule(module)
                                    }
                                )
                            }
                            TerminalModule.LIFE_GRAPH -> {
                                LifeGraphWindow(
                                    nodes = lifeGraphNodes,
                                    onAddNode = { title, cat, det, conn ->
                                        viewModel.addLifeGraphNode(title, cat, det, conn)
                                    },
                                    onDeleteNode = { viewModel.deleteLifeGraphNode(it) },
                                    onBack = { viewModel.openModule(TerminalModule.DASHBOARD) }
                                )
                            }
                            TerminalModule.VISION_CORE -> {
                                VisionCoreWindow(
                                    onTriggerScan = {
                                        viewModel.audioEngine.playBeep()
                                        viewModel.executeCommand("scan")
                                    },
                                    onBack = { viewModel.openModule(TerminalModule.DASHBOARD) }
                                )
                            }
                            TerminalModule.COMMAND_LINE -> {
                                CommandLineWindow(
                                    history = commandHistory,
                                    onExecuteCommand = { viewModel.executeCommand(it) },
                                    onBack = { viewModel.openModule(TerminalModule.DASHBOARD) }
                                )
                            }
                            TerminalModule.AGENT_PANEL -> {
                                AgentPanel(
                                    agents = agents,
                                    selectedAgentId = selectedAgentId,
                                    onSelectAgent = { viewModel.selectAgent(it) },
                                    onPauseAgent = { viewModel.pauseAgent(it) },
                                    onResumeAgent = { viewModel.resumeAgent(it) },
                                    onInterveneAgent = { viewModel.interveneAgent(it) },
                                    onTakeOverAgent = { viewModel.takeOverAgent(it) },
                                    onChangeModel = { id, model -> viewModel.changeAgentModel(id, model) },
                                    onKillSwitchClick = { viewModel.openModule(TerminalModule.KILL_SWITCH) }
                                )
                            }
                            TerminalModule.PERMISSION_DASHBOARD -> {
                                PermissionDashboardWindow(
                                    pendingRequests = pendingPermissions,
                                    auditLogs = auditLogs,
                                    onApprove = { viewModel.approvePermission(it) },
                                    onReject = { viewModel.rejectPermission(it) },
                                    onBack = { viewModel.openModule(TerminalModule.DASHBOARD) }
                                )
                            }
                            TerminalModule.AMBIENT_MODE -> {
                                AmbientModeWindow()
                            }
                            TerminalModule.DEVICES -> {
                                DeviceIntegrationWindow(
                                    onBack = { viewModel.openModule(TerminalModule.DASHBOARD) }
                                )
                            }
                            TerminalModule.SCRATCH_PAD -> {
                                ScratchPadWindow(
                                    notes = scratchNotes,
                                    onAddNote = { title, content, tags ->
                                        viewModel.addScratchNote(title, content, tags)
                                    },
                                    onDeleteNote = { viewModel.deleteScratchNote(it) }
                                )
                            }
                            TerminalModule.WORLD_MODEL -> {
                                WorldModelWindow()
                            }
                            TerminalModule.SIGNAL_SCAN -> {
                                SignalScanWindow()
                            }
                            TerminalModule.TIMELINE_VIEW -> {
                                TimelineWindow(
                                    onSimulateFuture = { viewModel.audioEngine.playBeep() }
                                )
                            }
                            TerminalModule.KILL_SWITCH -> {
                                KillSwitchOverlay(
                                    onDisengageKillSwitch = {
                                        viewModel.disengageKillSwitch()
                                        viewModel.openModule(TerminalModule.DASHBOARD)
                                    }
                                )
                            }
                        }
                    }

                    // RIGHT DOCKED AGENT PANEL (Visible when screen is wide)
                    if (isWideScreen && activeModule != TerminalModule.AGENT_PANEL) {
                        AgentPanel(
                            agents = agents,
                            selectedAgentId = selectedAgentId,
                            onSelectAgent = { viewModel.selectAgent(it) },
                            onPauseAgent = { viewModel.pauseAgent(it) },
                            onResumeAgent = { viewModel.resumeAgent(it) },
                            onInterveneAgent = { viewModel.interveneAgent(it) },
                            onTakeOverAgent = { viewModel.takeOverAgent(it) },
                            onChangeModel = { id, model -> viewModel.changeAgentModel(id, model) },
                            onKillSwitchClick = { viewModel.openModule(TerminalModule.KILL_SWITCH) },
                            modifier = Modifier.width(320.dp)
                        )
                    }
                }
            }
        }

        // CRT Scanline Overlay
        CrtScanlineOverlay(
            enableScanlines = true,
            enableRadarSweep = false
        )
    }
}
