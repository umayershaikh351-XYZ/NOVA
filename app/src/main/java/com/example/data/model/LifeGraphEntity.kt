package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lifegraph_nodes")
data class LifeGraphEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val category: String, // HABIT, GOAL, RELATIONSHIP, PREFERENCE, CREDENTIAL, VAULT
    val confidence: Float,
    val detail: String,
    val connectionKeys: String, // comma separated related tags/nodes
    val timestamp: Long = System.currentTimeMillis()
)
