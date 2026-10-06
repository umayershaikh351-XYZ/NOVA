package com.example.data.model

enum class TerminalModule(val displayName: String, val code: String, val shortcut: String) {
    DASHBOARD("DASHBOARD", "MOD_00", "HOME"),
    COMMAND_LINE("COMMAND LINE", "MOD_01", "CMD"),
    VISION_CORE("VISION CORE", "MOD_02", "VIS"),
    LIFE_GRAPH("LIFE GRAPH", "MOD_03", "LIF"),
    AGENT_PANEL("AGENT PANEL", "MOD_04", "AGE"),
    DEVICES("DEVICES", "MOD_05", "DEV"),
    PERMISSION_DASHBOARD("PERMISSIONS", "MOD_06", "PRM"),
    AMBIENT_MODE("AMBIENT MODE", "MOD_07", "AMB"),
    KILL_SWITCH("KILL SWITCH", "MOD_08", "KIL"),
    WORLD_MODEL("WORLD MODEL", "MOD_09", "WLD"),
    SIGNAL_SCAN("SIGNAL SCAN", "MOD_10", "SIG"),
    SCRATCH_PAD("SCRATCH PAD", "MOD_11", "PAD"),
    TIMELINE_VIEW("TIMELINE 3D", "MOD_12", "TML")
}

enum class AgentStatus(val label: String) {
    RUNNING("RUNNING"),
    ACTIVE("ACTIVE"),
    WAITING("WAITING"),
    IDLE("IDLE"),
    DONE("DONE"),
    PAUSED("PAUSED"),
    ALERT("ALERT")
}

enum class AgentModel(val label: String, val provider: String) {
    GEMINI_2_5("Gemini 2.5 Flash", "Google"),
    CLAUDE_3_7("Claude 3.7 Sonnet", "Anthropic"),
    GPT_4O("GPT-4o Omniverse", "OpenAI"),
    LLAMA_3_LOCAL("Llama 3.3 70B [Local]", "On-Device NPU")
}

data class AgentInfo(
    val id: String,
    val name: String,
    val role: String,
    val status: AgentStatus,
    val model: AgentModel,
    val currentTask: String,
    val progress: Float, // 0f to 1f
    val tokensPerSec: Int,
    val lastTranscript: String,
    val transcriptHistory: List<String> = emptyList(),
    val isUserIntervened: Boolean = false
)

data class PermissionRequest(
    val id: String,
    val actionName: String,
    val target: String,
    val requestingAgent: String,
    val riskLevel: String, // "LOW", "ELEVATED", "CRITICAL"
    val timestamp: Long = System.currentTimeMillis(),
    val approved: Boolean? = null // null = pending
)

data class PermissionItem(
    val key: String,
    val name: String,
    val state: String, // "Allowed", "Limited", "Blocked"
    val isAllowed: Boolean
)

data class DeviceItem(
    val name: String,
    val model: String,
    val isConnected: Boolean
)

data class GoalItem(
    val title: String,
    val percentage: Int,
    val iconType: String
)

data class MemoryItem(
    val text: String,
    val timestampText: String
)

data class WindowState(
    val module: TerminalModule,
    val title: String,
    val isMinimized: Boolean = false,
    val isMaximized: Boolean = false,
    val zIndex: Int = 0,
    val offsetX: Float = 0f,
    val offsetY: Float = 0f
)

data class CommandLineItem(
    val id: Long = System.currentTimeMillis(),
    val text: String,
    val type: CommandType = CommandType.OUTPUT
)

enum class CommandType {
    INPUT,
    OUTPUT,
    SYSTEM,
    ERROR,
    SUCCESS
}
