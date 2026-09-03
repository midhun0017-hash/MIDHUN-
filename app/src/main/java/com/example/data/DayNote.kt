package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "day_notes")
data class DayNote(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: String, // Format: YYYY-MM-DD
    val time: String, // Format: HH:MM:SS
    val note: String,
    val timestamp: Long = System.currentTimeMillis()
)
