package com.example.timetracker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.sql.Time

@Database(
    entities = [
        EmployeeEntity::class,
    ],
    version = 1
)
abstract class TimeTrackerDatabase : RoomDatabase() {

    companion object {

        @Volatile
        private var INSTANCE: TimeTrackerDatabase? = null

        fun getDatabase(context: Context): TimeTrackerDatabase {
            return INSTANCE ?: synchronized(this) {
                val database = Room.databaseBuilder(
                    context = context,
                    klass = TimeTrackerDatabase::class.java,
                    name = "time_tracker_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = database
                database
            }
        }
    }

    abstract fun employeeDao(): EmployeeDao
}
