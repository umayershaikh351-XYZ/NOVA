package com.example.data.repository

import com.example.data.local.NovaDao
import com.example.data.model.AuditLogEntity
import com.example.data.model.LifeGraphEntity
import com.example.data.model.ScratchNoteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class NovaRepository(private val dao: NovaDao) {

    val lifeGraphNodes: Flow<List<LifeGraphEntity>> = dao.getAllLifeGraphNodes()
    val auditLogs: Flow<List<AuditLogEntity>> = dao.getRecentAuditLogs()
    val scratchNotes: Flow<List<ScratchNoteEntity>> = dao.getAllScratchNotes()

    suspend fun insertLifeGraphNode(node: LifeGraphEntity) = dao.insertLifeGraphNode(node)
    suspend fun deleteLifeGraphNode(node: LifeGraphEntity) = dao.deleteLifeGraphNode(node)

    suspend fun logAction(actor: String, action: String, status: String, securityLevel: String = "SECRET") {
        dao.insertAuditLog(
            AuditLogEntity(
                actor = actor,
                action = action,
                status = status,
                securityLevel = securityLevel
            )
        )
    }

    suspend fun insertScratchNote(note: ScratchNoteEntity) = dao.insertScratchNote(note)
    suspend fun deleteScratchNote(note: ScratchNoteEntity) = dao.deleteScratchNote(note)

    suspend fun prepopulateIfEmpty() {
        val existing = dao.getAllLifeGraphNodes().first()
        if (existing.isEmpty()) {
            val seedNodes = listOf(
                LifeGraphEntity(
                    title = "Target: Series A Capital Close",
                    category = "GOAL",
                    confidence = 0.94f,
                    detail = "Target $4.5M seed round. Syndicate leads: Founders Fund, Benchmark. Target close date: Nov 15.",
                    connectionKeys = "FINANCE, VENTURE, LEGAL"
                ),
                LifeGraphEntity(
                    title = "Deep Work Focus Block",
                    category = "HABIT",
                    confidence = 0.98f,
                    detail = "08:00 - 11:30 daily. Nova blocks incoming Slack, batches non-critical emails, routes emergency calls.",
                    connectionKeys = "CALENDAR, PRODUCTIVITY"
                ),
                LifeGraphEntity(
                    title = "Relationship: Dr. Evelyn Vance (Chief AI Sci)",
                    category = "RELATIONSHIP",
                    confidence = 0.92f,
                    detail = "PhD advisor & Co-inventor. Preferred comms: Signal / Encrypted Matrix. Bi-weekly review Thursdays 14:00.",
                    connectionKeys = "RESEARCH, INTEL, CONTACTS"
                ),
                LifeGraphEntity(
                    title = "Vault Key: Cloud Cluster Orchestration",
                    category = "CREDENTIAL",
                    confidence = 1.0f,
                    detail = "Kubernetes multi-region deployment key. Enforces 2FA hardware token confirmation on any cluster scaling.",
                    connectionKeys = "INFRASTRUCTURE, ZERO_TRUST"
                ),
                LifeGraphEntity(
                    title = "Dietary: Ketogenic / Intermittent Fasting",
                    category = "PREFERENCE",
                    confidence = 0.89f,
                    detail = "16:8 protocol. Eating window 12:00 - 20:00. Auto-filter restaurant orders for high protein & no refined carbs.",
                    connectionKeys = "HEALTH, BIOMETRICS"
                )
            )
            for (node in seedNodes) {
                dao.insertLifeGraphNode(node)
            }

            // Seed Notes
            dao.insertScratchNote(
                ScratchNoteEntity(
                    title = "NOVA Autonomous Pipeline Spec",
                    content = "- Implement sub-second multi-model router\n- Add zero-knowledge biometric challenge for card payments\n- Branching future simulator in Timeline 3D view\n- Integrate VisionCore OCR with bank statements",
                    tags = "ARCHITECTURE, OS, ROADMAP",
                    pinned = true
                )
            )

            // Seed Audit Logs
            dao.insertAuditLog(
                AuditLogEntity(
                    actor = "KERNEL_BOOT",
                    action = "System integrity verification completed (SHA-256 ok)",
                    status = "SUCCESS",
                    securityLevel = "CONFIDENTIAL"
                )
            )
            dao.insertAuditLog(
                AuditLogEntity(
                    actor = "AGENT_RESEARCH",
                    action = "Parsed 48 competitive filings for Autonomous Agent Architectures",
                    status = "SUCCESS",
                    securityLevel = "SECRET"
                )
            )
            dao.insertAuditLog(
                AuditLogEntity(
                    actor = "SYS_ROUTER",
                    action = "Routed complex mathematical optimization to Claude 3.7 Sonnet",
                    status = "SUCCESS",
                    securityLevel = "SECRET"
                )
            )
        }
    }
}
