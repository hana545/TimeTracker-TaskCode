package com.example.timetracker.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM employee ORDER BY check_in_date DESC LIMIT 1")
    suspend fun getRecentCheckIn(): EmployeeEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(employee: EmployeeEntity)

}