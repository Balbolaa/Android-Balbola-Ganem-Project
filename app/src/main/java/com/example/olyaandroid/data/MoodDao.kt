package com.example.olyaandroid.data

// Importing necessary stuff for Room database and Kotlin Flows
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// This is the Data Access Object (DAO) for interacting with the mood_entries table
@Dao
interface MoodDao {

    // This function inserts a mood entry into the database
    // If there's already an entry with the same ID, it will replace it
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMood(moodEntry: MoodEntry)

    // This function gets the last 7 mood entries, ordered from newest to oldest
    // It returns a Flow, so we can observe changes in real-time
    @Query("SELECT * FROM mood_entries ORDER BY date DESC LIMIT 7")
    fun getLastSevenDaysMoods(): Flow<List<MoodEntry>>

    // This function calculates the average mood since a certain date
    // The startDate is passed as a parameter
    // It's a suspend function because it might take time to run
    @Query("SELECT AVG(mood) FROM mood_entries WHERE date >= :startDate")
    suspend fun getAverageMoodSince(startDate: String): Double?
}
