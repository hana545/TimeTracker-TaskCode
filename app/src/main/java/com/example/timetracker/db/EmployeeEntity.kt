package com.example.timetracker.db

import android.view.autofill.AutofillId
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee")
data class EmployeeEntity(
    @ColumnInfo(name = "check_in_date") val checkInDate: String,
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Int=100,
) {
}
