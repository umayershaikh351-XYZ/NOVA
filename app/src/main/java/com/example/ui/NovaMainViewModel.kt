package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.TerminalAudioEngine
import com.example.data.local.NovaDatabase
import com.example.data.model.AgentInfo
import com.example.data.model.AgentModel
import com.example.data.model.AgentStatus
import com.example.data.model.AuditLogEntity
import com.example.data.model.CommandLineItem
import com.example.data.model.CommandType
import com.example.data.model.LifeGraphEntity
import com.example.data.model.PermissionRequest
import com.example.data.model.ScratchNoteEntity
import com.example.data.model.TerminalModule
import com.example.data.model.WindowState
import com.example.data.repository.NovaRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NovaMainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = NovaDatabase.getDatabase(application)
    private val repository = NovaRepository(database.novaDao())
    val audioEngine = TerminalAudioEngine(application)

    // Boot & System State
    private val _isBooting = MutableStateFlow(true)
    val isBooting: StateFlow<Boolean> = _isBooting.asStateFlow()

    private val _isKillSwitchActive = MutableStateFlow(false)
    val isKillSwitchActive: StateFlow<Boolean> = _isKillSwitchActive.asStateFlow()

    private val _isSoundMuted = MutableStateFlow(false)
    val isSoundMuted: StateFlow<Boolean> = _isSoundMuted.asStateFlow()

    private val _trustLevel = MutableStateFlow(4) // 1 to 5
    val trustLevel: StateFlow<Int> = _trustLevel.asStateFlow()

    private val _isVaultLocked = MutableStateFlow(true)
    val isVaultLocked: StateFlow<Boolean> = _isVaultLocked.asStateFlow()

    // Room Database Flows
    val lifeGraphNodes: StateFlow<List<LifeGraphEntity>> = repository.lifeGraphNodes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val auditLogs: StateFlow<List<AuditLogEntity>> = repository.auditLogs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val scratchNotes: StateFlow<List<ScratchNoteEntity>> = repository.scratchNotes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Active Agent Swarm
    private val _agents = MutableStateFlow<List<AgentInfo>>(emptyList())
    val agents: StateFlow<List<AgentInfo>> = _agents.asStateFlow()

    private val _selectedAgentId = MutableStateFlow<String?>(null)
    val selectedAgentId: StateFlow<String?> = _selectedAgentId.asStateFlow()

    // Pending Permissions
    private val _pendingPermissions = MutableStateFlow<List<PermissionRequest>>(emptyList())
    val pendingPermissions: StateFlow<List<PermissionRequest>> = _pendingPermissions.asStateFlow()

    // Command Line History
    private val _commandHistory = MutableStateFlow<List<CommandLineItem>>(emptyList())
    val commandHistory: StateFlow<List<CommandLineItem>> = _commandHistory.asStateFlow()

    // Window Management & Spatial Canvas
    private val _activeModule = MutableStateFlow(TerminalModule.DASHBOARD)
    val activeModule: StateFlow<TerminalModule> = _activeModule.asStateFlow()

    private val _openWindows = MutableStateFlow<Map<TerminalModule, WindowState>>(emptyMap())
    val openWindows: StateFlow<Map<TerminalModule, WindowState>> = _openWindows.asStateFlow()

    // Telemetry
    private val _tokensPerSec = MutableStateFlow(142)
    val tokensPerSec: StateFlow<Int> = _tokensPerSec.asStateFlow()

    init {
        viewModelScope.launch {
            repository.prepopulateIfEmpty()
            initInitialAgents()
            initDefaultWindows()
            initCommandLineHistory()
            initPendingPermissions()
            startAgentSimulationTicker()
        }
    }

    private fun initInitialAgents() {
        _agents.value = listOf(
            AgentInfo(
                id = "AG_01",
                name = "Research Agent",
                role = "Market & AI Scanning",
                status = AgentStatus.RUNNING,
                model = AgentModel.CLAUDE_3_7,
                currentTask = "Scanning latest AI developments... 3/8 sources analyzed...",
                progress = 0.38f,
                tokensPerSec = 84,
                lastTranscript = "Synthesizing cross-jurisdictional AI research papers.",
                transcriptHistory = listOf("Scanning arXiv & patent databases.")
            ),
            AgentInfo(
                id = "AG_02",
                name = "Code Agent",
                role = "Autonomous Engineering",
                status = AgentStatus.RUNNING,
                model = AgentModel.GEMINI_2_5,
                currentTask = "Building NOVA module... 12% complete...",
                progress = 0.12f,
                tokensPerSec = 118,
                lastTranscript = "Compiled Compose preview kernel in 320ms.",
                transcriptHistory = listOf("Initialized code parser.")
            ),
            AgentInfo(
                id = "AG_03",
                name = "Travel Agent",
                role = "Logistics & Flights",
                status = AgentStatus.ACTIVE,
                model = AgentModel.GPT_4O,
                currentTask = "Searching best flight options... 4 options found...",
                progress = 0.75f,
                tokensPerSec = 62,
                lastTranscript = "Compared 14 flight legs for Delhi itinerary.",
                transcriptHistory = listOf("Found direct nonstop routing.")
            ),
            AgentInfo(
                id = "AG_04",
                name = "Health Agent",
                role = "Biometrics & Sleep",
                status = AgentStatus.WAITING,
                model = AgentModel.LLAMA_3_LOCAL,
                currentTask = "Awaiting your input (sleep schedule)",
                progress = 0.50f,
                tokensPerSec = 0,
                lastTranscript = "Sleep recovery 6h 45m calculated. Rest needed.",
                transcriptHistory = listOf("Awaiting operator wake/sleep confirmation.")
            ),
            AgentInfo(
                id = "AG_05",
                name = "Finance Agent",
                role = "Portfolio & Subscriptions",
                status = AgentStatus.IDLE,
                model = AgentModel.GPT_4O,
                currentTask = "Monitoring market trends.",
                progress = 0.10f,
                tokensPerSec = 0,
                lastTranscript = "NASDAQ: +1.24%. All expense limits nominal.",
                transcriptHistory = listOf("Idle. Next audit scheduled at 18:00.")
            ),
            AgentInfo(
                id = "AG_06",
                name = "Creative Agent",
                role = "UI & Visual Synthesis",
                status = AgentStatus.DONE,
                model = AgentModel.GEMINI_2_5,
                currentTask = "Designs ready for review.",
                progress = 1.0f,
                tokensPerSec = 0,
                lastTranscript = "Vector icon assets & hero mountain graphics rendered.",
                transcriptHistory = listOf("Completed 8 concept board assets.")
            )
        )
    }

    private fun initDefaultWindows() {
        val defaultWindows = mutableMapOf<TerminalModule, WindowState>()
        defaultWindows[TerminalModule.DASHBOARD] = WindowState(
            module = TerminalModule.DASHBOARD,
            title = "DASHBOARD",
            isMinimized = false,
            isMaximized = true,
            zIndex = 1
        )
        _openWindows.value = defaultWindows
    }

    private fun initCommandLineHistory() {
        _commandHistory.value = listOf(
            CommandLineItem(text = "NOVA INTELLIGENCE KERNEL v4.0.9", type = CommandType.SYSTEM),
            CommandLineItem(text = "Type 'help' to inspect available operator commands.", type = CommandType.OUTPUT),
            CommandLineItem(text = "System nominal. Multi-model cognitive pipeline active.", type = CommandType.SUCCESS)
        )
    }

    private fun initPendingPermissions() {
        _pendingPermissions.value = listOf(
            PermissionRequest(
                id = "PERM_01",
                actionName = "EXECUTE CLOUD BILLING DOWNSIZE",
                target = "AWS Cost Optimizer (Terminating 4x H100 pods)",
                requestingAgent = "FINANCIAL ARBITRAGE SENTRY",
                riskLevel = "ELEVATED"
            ),
            PermissionRequest(
                id = "PERM_02",
                actionName = "DISPATCH VENTURE UPDATE TO PARTNERS",
                target = "Draft email to benchmark@vc.com & foundersfund@vc.com",
                requestingAgent = "DEEP RESEARCH SYNAPSE",
                riskLevel = "CRITICAL"
            )
        )
    }

    // Live Agent Telemetry Simulation Loop
    private fun startAgentSimulationTicker() {
        viewModelScope.launch {
            var counter = 0
            while (true) {
                delay(3000)
                if (!_isKillSwitchActive.value) {
                    counter++
                    _tokensPerSec.value = 130 + (counter % 35)

                    // Update agent progress slightly
                    _agents.value = _agents.value.map { agent ->
                        if (agent.status == AgentStatus.RUNNING) {
                            val newProgress = (agent.progress + 0.04f).let { if (it > 1f) 0.1f else it }
                            agent.copy(
                                progress = newProgress,
                                tokensPerSec = 60 + (counter * 7 % 70)
                            )
                        } else {
                            agent
                        }
                    }
                }
            }
        }
    }

    // Module / Window Navigation
    fun openModule(module: TerminalModule) {
        audioEngine.playKeypress()
        _activeModule.value = module

        val currentMap = _openWindows.value.toMutableMap()
        if (!currentMap.containsKey(module)) {
            currentMap[module] = WindowState(
                module = module,
                title = module.displayName,
                isMinimized = false,
                isMaximized = true,
                zIndex = currentMap.size + 1
            )
        } else {
            val existing = currentMap[module]!!
            currentMap[module] = existing.copy(isMinimized = false, isMaximized = true)
        }
        _openWindows.value = currentMap
    }

    fun closeWindow(module: TerminalModule) {
        audioEngine.playKeypress()
        val currentMap = _openWindows.value.toMutableMap()
        currentMap.remove(module)
        _openWindows.value = currentMap

        if (_activeModule.value == module) {
            _activeModule.value = currentMap.keys.firstOrNull() ?: TerminalModule.LIFE_GRAPH
        }
    }

    fun toggleMaximizeWindow(module: TerminalModule) {
        audioEngine.playKeypress()
        val currentMap = _openWindows.value.toMutableMap()
        val existing = currentMap[module] ?: return
        currentMap[module] = existing.copy(isMaximized = !existing.isMaximized)
        _openWindows.value = currentMap
    }

    fun minimizeWindow(module: TerminalModule) {
        audioEngine.playKeypress()
        val currentMap = _openWindows.value.toMutableMap()
        val existing = currentMap[module] ?: return
        currentMap[module] = existing.copy(isMinimized = true)
        _openWindows.value = currentMap
    }

    fun updateWindowOffset(module: TerminalModule, dx: Float, dy: Float) {
        val currentMap = _openWindows.value.toMutableMap()
        val existing = currentMap[module] ?: return
        currentMap[module] = existing.copy(
            offsetX = existing.offsetX + dx,
            offsetY = existing.offsetY + dy
        )
        _openWindows.value = currentMap
    }

    // Agent Actions
    fun selectAgent(agentId: String) {
        audioEngine.playKeypress()
        _selectedAgentId.value = if (_selectedAgentId.value == agentId) null else agentId
    }

    fun pauseAgent(agentId: String) {
        audioEngine.playBeep()
        _agents.value = _agents.value.map {
            if (it.id == agentId) it.copy(status = AgentStatus.PAUSED, tokensPerSec = 0) else it
        }
        viewModelScope.launch {
            repository.logAction("OPERATOR", "Paused agent $agentId", "SUCCESS")
        }
    }

    fun resumeAgent(agentId: String) {
        audioEngine.playBeep()
        _agents.value = _agents.value.map {
            if (it.id == agentId) it.copy(status = AgentStatus.RUNNING, tokensPerSec = 80) else it
        }
        viewModelScope.launch {
            repository.logAction("OPERATOR", "Resumed agent $agentId", "SUCCESS")
        }
    }

    fun interveneAgent(agentId: String) {
        audioEngine.playBeep()
        _agents.value = _agents.value.map {
            if (it.id == agentId) it.copy(
                isUserIntervened = true,
                currentTask = "INTERVENTION: Awaiting operator inline guidance"
            ) else it
        }
        appendCommandLine("INTERVENTION TRIGGERED for agent $agentId. Enter instructions.", CommandType.SYSTEM)
    }

    fun takeOverAgent(agentId: String) {
        audioEngine.playWarning()
        _agents.value = _agents.value.map {
            if (it.id == agentId) it.copy(
                status = AgentStatus.ALERT,
                currentTask = "MANUAL OVERRIDE ENGAGED BY OPERATOR"
            ) else it
        }
        appendCommandLine("MANUAL TAKEOVER engaged for agent $agentId. AI autonomy suspended.", CommandType.ERROR)
    }

    fun changeAgentModel(agentId: String, newModel: AgentModel) {
        audioEngine.playBeep()
        _agents.value = _agents.value.map {
            if (it.id == agentId) it.copy(model = newModel) else it
        }
        viewModelScope.launch {
            repository.logAction("ROUTER", "Agent $agentId routed to ${newModel.label}", "SUCCESS")
        }
    }

    // Command Line Execution
    fun executeCommand(rawCommand: String) {
        audioEngine.playKeypress()
        val cmd = rawCommand.trim()
        if (cmd.isEmpty()) return

        appendCommandLine("OPERATOR@NOVA:~$ $cmd", CommandType.INPUT)

        val parts = cmd.split(" ")
        val mainCmd = parts[0].lowercase()

        when (mainCmd) {
            "help" -> {
                appendCommandLine(
                    """
                    AVAILABLE OPERATOR COMMANDS:
                    • help                  - Show this manual
                    • status                - Display system telemetry & trust status
                    • agents                - List all active cognitive sub-routines
                    • spawn <name>          - Instantiate a new agent sub-process
                    • kill <agent_id>       - Terminate an agent immediately
                    • vault                 - Inspect cryptographic memory vault state
                    • scan                  - Trigger VisionCore optical OCR scan
                    • audit                 - Show recent immutable security audit logs
                    • clear                 - Clear terminal buffer
                    • sleep / killswitch    - Engage emergency lockdown protocol
                    • reboot                - Restart NOVA into BIOS sequence
                    """.trimIndent(),
                    CommandType.OUTPUT
                )
            }
            "status" -> {
                appendCommandLine(
                    """
                    SYSTEM STATUS REPORT:
                    • KERNEL: ONLINE (Trust Level ${_trustLevel.value}/5)
                    • ACTIVE AGENTS: ${_agents.value.count { it.status == AgentStatus.RUNNING }} running
                    • MEMORY VAULT: AES-256-GCM ARMED
                    • THROUGHPUT: ${_tokensPerSec.value} tokens/sec
                    • AUTONOMY: 90% Digital Knowledge Work delegated
                    """.trimIndent(),
                    CommandType.SUCCESS
                )
            }
            "agents" -> {
                val list = _agents.value.joinToString("\n") { "• [${it.id}] ${it.name} (${it.status.label}) - ${it.model.label}" }
                appendCommandLine(list, CommandType.OUTPUT)
            }
            "spawn" -> {
                val name = parts.drop(1).joinToString(" ").ifEmpty { "AUTONOMOUS SUB-AGENT" }
                val newId = "AG_0${_agents.value.size + 1}"
                val newAgent = AgentInfo(
                    id = newId,
                    name = name.uppercase(),
                    role = "Custom Cognitive Task",
                    status = AgentStatus.RUNNING,
                    model = AgentModel.GEMINI_2_5,
                    currentTask = "Executing operator spawned macro",
                    progress = 0.2f,
                    tokensPerSec = 95,
                    lastTranscript = "Spawned successfully. Sub-process initialized.",
                    transcriptHistory = listOf("Spawned by operator command: $cmd")
                )
                _agents.value = _agents.value + newAgent
                audioEngine.playBeep()
                appendCommandLine("SUCCESS: Spawned agent [$newId] $name", CommandType.SUCCESS)
            }
            "kill" -> {
                val targetId = parts.getOrNull(1)?.uppercase()
                if (targetId != null) {
                    _agents.value = _agents.value.filterNot { it.id == targetId }
                    audioEngine.playWarning()
                    appendCommandLine("TERMINATED: Agent $targetId decommissioned.", CommandType.ERROR)
                } else {
                    appendCommandLine("ERROR: Specify agent id (e.g. 'kill AG_01')", CommandType.ERROR)
                }
            }
            "vault" -> {
                appendCommandLine("VAULT STATUS: ENCRYPTED // 0 leaks // Zero-knowledge biometric gate ARMED.", CommandType.SUCCESS)
            }
            "scan" -> {
                openModule(TerminalModule.VISION_CORE)
                appendCommandLine("LAUNCHING VISION CORE OPTICAL PARSER...", CommandType.SYSTEM)
            }
            "audit" -> {
                viewModelScope.launch {
                    val logs = repository.auditLogs
                    appendCommandLine("LEDGER DUMP: Check PERMISSION DASHBOARD [PD] for full visual audit.", CommandType.OUTPUT)
                }
            }
            "clear" -> {
                _commandHistory.value = emptyList()
            }
            "sleep", "killswitch" -> {
                engageKillSwitch()
            }
            "reboot" -> {
                audioEngine.playWarning()
                _isBooting.value = true
                appendCommandLine("REBOOTING KERNEL...", CommandType.SYSTEM)
            }
            else -> {
                appendCommandLine("UNKNOWN COMMAND: '$cmd'. Type 'help' for syntax.", CommandType.ERROR)
            }
        }
    }

    private fun appendCommandLine(text: String, type: CommandType) {
        val list = _commandHistory.value.toMutableList()
        list.add(CommandLineItem(text = text, type = type))
        if (list.size > 200) list.removeAt(0)
        _commandHistory.value = list
    }

    // Permission Approval Handlers
    fun approvePermission(id: String) {
        audioEngine.playBeep()
        val request = _pendingPermissions.value.find { it.id == id }
        _pendingPermissions.value = _pendingPermissions.value.filterNot { it.id == id }
        if (request != null) {
            viewModelScope.launch {
                repository.logAction("OPERATOR", "APPROVED: ${request.actionName}", "SUCCESS", "SECRET")
            }
            appendCommandLine("ACTION AUTHORIZED: ${request.actionName} by operator.", CommandType.SUCCESS)
        }
    }

    fun rejectPermission(id: String) {
        audioEngine.playWarning()
        val request = _pendingPermissions.value.find { it.id == id }
        _pendingPermissions.value = _pendingPermissions.value.filterNot { it.id == id }
        if (request != null) {
            viewModelScope.launch {
                repository.logAction("OPERATOR", "REJECTED: ${request.actionName}", "BLOCKED", "SECRET")
            }
            appendCommandLine("ACTION REJECTED: ${request.actionName} was denied by operator.", CommandType.ERROR)
        }
    }

    // LifeGraph database interactions
    fun addLifeGraphNode(title: String, category: String, detail: String, connections: String) {
        audioEngine.playBeep()
        viewModelScope.launch {
            repository.insertLifeGraphNode(
                LifeGraphEntity(
                    title = title,
                    category = category,
                    confidence = 0.95f,
                    detail = detail,
                    connectionKeys = connections
                )
            )
            repository.logAction("USER", "Added memory node: $title", "SUCCESS")
        }
    }

    fun deleteLifeGraphNode(node: LifeGraphEntity) {
        audioEngine.playKeypress()
        viewModelScope.launch {
            repository.deleteLifeGraphNode(node)
            repository.logAction("USER", "Purged memory node: ${node.title}", "SUCCESS")
        }
    }

    // Scratch Notes
    fun addScratchNote(title: String, content: String, tags: String) {
        audioEngine.playBeep()
        viewModelScope.launch {
            repository.insertScratchNote(
                ScratchNoteEntity(
                    title = title,
                    content = content,
                    tags = tags
                )
            )
        }
    }

    fun deleteScratchNote(note: ScratchNoteEntity) {
        audioEngine.playKeypress()
        viewModelScope.launch {
            repository.deleteScratchNote(note)
        }
    }

    // Trust Level Cycle
    fun cycleTrustLevel() {
        audioEngine.playKeypress()
        val next = if (_trustLevel.value >= 5) 1 else _trustLevel.value + 1
        _trustLevel.value = next
        viewModelScope.launch {
            repository.logAction("OPERATOR", "Set trust level to $next", "SUCCESS")
        }
        appendCommandLine("TRUST CLEARANCE RE-CALIBRATED TO LEVEL $next", CommandType.SYSTEM)
    }

    // Kill Switch
    fun engageKillSwitch() {
        audioEngine.playKillSwitchAlarm()
        _isKillSwitchActive.value = true
        _agents.value = _agents.value.map { it.copy(status = AgentStatus.PAUSED, tokensPerSec = 0) }
        viewModelScope.launch {
            repository.logAction("KILL_SWITCH", "Emergency lockdown engaged by operator", "ABORTED", "TOP_SECRET")
        }
    }

    fun disengageKillSwitch() {
        audioEngine.playBeep()
        _isKillSwitchActive.value = false
        _agents.value = _agents.value.map { it.copy(status = AgentStatus.RUNNING, tokensPerSec = 75) }
        viewModelScope.launch {
            repository.logAction("OPERATOR", "Disengaged lockdown and restored kernel", "SUCCESS")
        }
        appendCommandLine("LOCKDOWN DISENGAGED. Neural Swarm resumed.", CommandType.SUCCESS)
    }

    // Sound toggle
    fun toggleSound() {
        val next = !_isSoundMuted.value
        _isSoundMuted.value = next
        audioEngine.isMuted = next
        if (!next) {
            audioEngine.playBeep()
        }
    }

    // Boot complete
    fun completeBoot() {
        audioEngine.playBeep()
        _isBooting.value = false
    }

    override fun onCleared() {
        super.onCleared()
        audioEngine.release()
    }
}
