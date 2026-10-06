package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "audit_logs")
data class AuditLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val actor: String,
    val action: String,
    val status: String, // SUCCESS, PENDING_APPROVAL, BLOCKED, ABORTED
    val securityLevel: String = "SECRET"
)
