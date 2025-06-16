package com.example.olyaandroid.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.olyaandroid.data.MoodDatabase
import com.example.olyaandroid.data.MoodEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// Data class to hold the UI state for the MoodStats screen
data class MoodStatsState(
    val moodEntries: List<MoodEntry> = emptyList(),  // List of mood entries (last 7 days)
    val averageMood: Double = 0.0                     // Average mood calculated from entries
)

// ViewModel class that manages the state and data for MoodStatsScreen
class MoodStatsViewModel(application: Application) : AndroidViewModel(application) {

    // Get instance of the database and DAO for accessing mood data
    private val database = MoodDatabase.getDatabase(application)
    private val moodDao = database.moodDao()

    // Mutable state flow to hold the current UI state
    private val _uiState = MutableStateFlow(MoodStatsState())
    // Expose an immutable StateFlow for the UI to observe
    val uiState: StateFlow<MoodStatsState> = _uiState.asStateFlow()

    // Initialize the ViewModel by loading last 7 days of mood entries from the DB
    init {
        viewModelScope.launch {
            // Collect mood entries from the database DAO as a Flow
            moodDao.getLastSevenDaysMoods().collect { entries ->
                // Calculate average mood from the list of entries
                val average = entries.map { it.mood }.average()
                // Update the UI state with new data
                _uiState.value = MoodStatsState(
                    moodEntries = entries,
                    averageMood = average
                )
            }
        }
    }

    // Helper function (currently unused) to calculate the date 7 days ago as String "yyyy-MM-dd"
    private fun getSevenDaysAgoDate(): String {
        val calendar = java.util.Calendar.getInstance()
        calendar.add(java.util.Calendar.DAY_OF_YEAR, -7)  // Subtract 7 days from today
        val format = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
        return format.format(calendar.time)
    }
}
