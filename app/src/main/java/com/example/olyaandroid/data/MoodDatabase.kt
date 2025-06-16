package com.example.olyaandroid.data

// Importing the necessary Android and Room libraries
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// This tells Room that we have a database with 1 entity: MoodEntry, and the database version is 1
@Database(entities = [MoodEntry::class], version = 1)
abstract class MoodDatabase : RoomDatabase() {

    // This function will be used to get access to our DAO (which has the database functions)
    abstract fun moodDao(): MoodDao

    // The companion object is used to make sure we only have one instance of the database
    companion object {
        // @Volatile means this variable is always kept up-to-date for all threads
        @Volatile
        private var INSTANCE: MoodDatabase? = null

        // This function returns the database instance
        fun getDatabase(context: Context): MoodDatabase {
            // If INSTANCE is not null, return it. If it is, create the database.
            return INSTANCE ?: synchronized(this) {
                // Building the database using Room
                val instance = Room.databaseBuilder(
                    context.applicationContext,   // use app context to avoid memory leaks
                    MoodDatabase::class.java,     // tell Room what class represents the DB
                    "mood_database"               // name of the database file
                ).build()

                // Save the instance so we can reuse it later
                INSTANCE = instance

                // Return the new database instance
                instance
            }
        }
    }
}
