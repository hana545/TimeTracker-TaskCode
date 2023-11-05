package com.example.timetracker.ui

import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timetracker.db.EmployeeEntity
import com.example.timetracker.db.TimeTrackerDatabase
import com.example.timetracker.mockAPI.ApiModule
import kotlinx.coroutines.launch

class MainViewModel(private val database: TimeTrackerDatabase)  : ViewModel() {

    private val _dateTime = MutableLiveData<String>()
    val dateTime: LiveData<String> get() = _dateTime

    private val _insertSuccess = MutableLiveData<Boolean>()
    val insertSuccess: LiveData<Boolean> get() = _insertSuccess

    init {
        initDateTime()
    }

    private fun initDateTime() {
        viewModelScope.launch {
            fetchRecentDateTime()
        }
    }

    private suspend fun fetchRecentDateTime() {
        val recentDateTime = database.employeeDao().getRecentCheckIn()
        if (recentDateTime != null && recentDateTime.checkInDate.isNotEmpty()) {
            _dateTime.value = recentDateTime.checkInDate
        } else {
            //fetch with API
            getDateTimewithAPI()
        }
    }

    private fun setDateTime(year: Int, month: Int, day: Int, hour: Int, minute: Int): String {
        return String.format("%d-%02d-%02d  %02d:%02d", year, month + 1, day, hour, minute)
    }

    fun updateDateTime(year: Int, month: Int, day: Int, hour: Int, minute: Int) {
        _dateTime.value = setDateTime(year, month, day, hour, minute)
    }

    fun insertCheckInDateTime() {
        viewModelScope.launch {
            try {
                dateTime.value?.let { EmployeeEntity(it) }?.let { database.employeeDao().insert(it) }
                _insertSuccess.value = true
            } catch (ups: Exception) {
                Log.e("INSERTDATABASE", "ups: " + ups.toString())
                _insertSuccess.value = false
            }
        }
    }

    fun getDateTimewithAPI() =
        viewModelScope.launch {
            try {
                val response = ApiModule.getDate()
                _dateTime.value = response.datetime
            } catch (ups: Exception) {
                Log.e("DATETIMEAPI", "ups "+ups.toString())
            }
        }


}