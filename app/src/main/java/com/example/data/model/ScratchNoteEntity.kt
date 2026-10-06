package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scratch_notes")
data class ScratchNoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val content: String,
    val tags: String,
    val pinned: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)
