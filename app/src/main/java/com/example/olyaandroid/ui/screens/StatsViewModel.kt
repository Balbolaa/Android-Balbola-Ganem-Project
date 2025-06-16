package com.example.olyaandroid.ui.screens

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Date


// Data class to represent a mood entry for a single day
data class MoodEntry(
    val date: String,    // Date as a String in format "yyyy-MM-dd"
    val moodLevel: Int,  // Mood level on a scale from 1 to 5
    val moodEmoji: String,  // Emoji representing the mood (e.g., 😊)
    val comment: String     // Optional comment about the mood or day
)

// ViewModel to manage mood data and logic for Stats screen
class StatsViewModel : ViewModel() {

    // List that holds all mood entries and is observable for Compose UI updates
    val moodHistory = mutableStateListOf<MoodEntry>()

    // Function to add a new mood entry with current date
    fun addEntry(moodLevel: Int, moodEmoji: String, comment: String) {
        // Get current date in "yyyy-MM-dd" format
        val date = SimpleDateFormat("yyyy-MM-dd").format(Date())

        // Add new MoodEntry to the moodHistory list
        moodHistory.add(MoodEntry(date, moodLevel, moodEmoji, comment))
    }

    // Calculate average mood level for the last 7 days (or fewer if less data)
    fun averageMoodLast7Days(): Double {
        val last7 = moodHistory.takeLast(7) // Get last 7 entries from history
        return if (last7.isNotEmpty()) {
            // Calculate average of moodLevel values
            last7.map { it.moodLevel }.average()
        } else {
            0.0 // Return 0.0 if no entries available
        }
    }
}
