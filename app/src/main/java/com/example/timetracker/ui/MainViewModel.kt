package com.example.timetracker.ui

import android.icu.util.Calendar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(){

    private val _dateTime = MutableLiveData<String>()
    val dateTime: LiveData<String> get() = _dateTime

    init {
        initDateTime()
    }

    private fun initDateTime() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = 6
        val minute = 30

        _dateTime.value = setDateTime(year, month, day, hour, minute)
    }

    private fun setDateTime(year: Int, month: Int, day: Int, hour: Int, minute: Int) : String{
        return String.format("%02d/%02d/%d  %02d:%02d", day, month + 1, year, hour, minute)
    }

    fun updateDateTime(year: Int, month: Int, day: Int, hour: Int, minute: Int) {
        _dateTime.value = setDateTime(year, month, day, hour, minute)
    }


}