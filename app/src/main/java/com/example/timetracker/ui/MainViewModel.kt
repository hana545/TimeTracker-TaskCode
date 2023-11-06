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


    private val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean> get() = _progress

    init {
        initDateTime()
    }

    private fun initDateTime() {
        _progress.value = true
        viewModelScope.launch {
            fetchRecentDateTime()
        }
    }

    private suspend fun fetchRecentDateTime() {
        //fetch from DB
        val recentDateTime = database.employeeDao().getRecentCheckIn()
        if (recentDateTime != null && recentDateTime.checkInDate.isNotEmpty()) {
            _dateTime.value = recentDateTime.checkInDate
            _progress.value = false
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
        _progress.value = true
        viewModelScope.launch {
            try {
                dateTime.value?.let { EmployeeEntity(it) }?.let { database.employeeDao().insert(it) }
                _insertSuccess.value = true
                _progress.value = false
            } catch (ups: Exception) {
                Log.e("INSERTDATABASE", "ups: " + ups.toString())
                _insertSuccess.value = false
                _progress.value = false
            }
        }
    }

    private suspend fun getDateTimewithAPI() {
        try {
            val response = ApiModule.getDate()
            _dateTime.value = response.datetime
            _progress.value = false
        } catch (ups: Exception) {
            Log.e("DATETIMEAPI", "ups "+ups.toString())
        }
    }


}