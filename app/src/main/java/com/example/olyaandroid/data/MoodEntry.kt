package com.example.olyaandroid.data

// Importing Room annotations
import androidx.room.Entity
import androidx.room.PrimaryKey

// This tells Room that this class represents a table in the database called "mood_entries"
@Entity(tableName = "mood_entries")
data class MoodEntry(
    // This is the primary key (unique ID) for each row in the table
    // autoGenerate = true means Room will automatically give each new entry a unique ID
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // The date the mood was recorded (as a String, e.g. "2025-06-16")
    val date: String,

    // The mood value (e.g. 1 to 5, where 5 is very happy and 1 is very sad)
    val mood: Int
)
