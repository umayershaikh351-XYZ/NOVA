package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.AuditLogEntity
import com.example.data.model.LifeGraphEntity
import com.example.data.model.ScratchNoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NovaDao {
    // LifeGraph
    @Query("SELECT * FROM lifegraph_nodes ORDER BY timestamp DESC")
    fun getAllLifeGraphNodes(): Flow<List<LifeGraphEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLifeGraphNode(node: LifeGraphEntity): Long

    @Delete
    suspend fun deleteLifeGraphNode(node: LifeGraphEntity)

    // Audit Logs
    @Query("SELECT * FROM audit_logs ORDER BY timestamp DESC LIMIT 100")
    fun getRecentAuditLogs(): Flow<List<AuditLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuditLog(log: AuditLogEntity): Long

    // Scratch Notes
    @Query("SELECT * FROM scratch_notes ORDER BY pinned DESC, timestamp DESC")
    fun getAllScratchNotes(): Flow<List<ScratchNoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScratchNote(note: ScratchNoteEntity): Long

    @Delete
    suspend fun deleteScratchNote(note: ScratchNoteEntity)
}
