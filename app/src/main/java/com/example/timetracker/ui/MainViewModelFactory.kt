package com.example.timetracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.timetracker.db.TimeTrackerDatabase

class MainViewModelFactory (private val database: TimeTrackerDatabase) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(database = database) as T
        }
        throw IllegalArgumentException("Sorry, can't work with this")
    }
}
