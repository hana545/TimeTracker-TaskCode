package com.example.timetracker.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.timetracker.databinding.ActivityMainBinding
import androidx.activity.viewModels
import com.example.timetracker.db.TimeTrackerDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>{
        MainViewModelFactory(TimeTrackerDatabase.getDatabase(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.dateTime.observe(this) { dateTime ->
            binding.datetimeData.text = dateTime
        }

        initDateTimePicker()

        initSubmitButton()
    }

    private fun initSubmitButton() {
        binding.btnSubmit.setOnClickListener {
           viewModel.insertCheckInDateTime()
        }
        viewModel.insertSuccess.observe(this) {success ->
            if (success) Toast.makeText(this, "Check in date and time saved successfully!", Toast.LENGTH_LONG).show()
            if (!success) Toast.makeText(this, "Couldn't save date and time!", Toast.LENGTH_LONG).show()
        }
    }

    private fun initDateTimePicker() {
        binding.datetimeData.setOnClickListener {
            showDateTimePickerDialog()
            }
    }

    private fun isToday(year: Int, month: Int, day: Int) : Boolean{
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        return year == currentYear && month == currentMonth && day == currentDay
    }

    private fun showDateTimePickerDialog() {
        val calendar = viewModel.dateTime.value?.let { parseDateTime(it) }
        val year = calendar!!.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            if (isValidDate(selectedYear, selectedMonth, selectedDay)) {
                showTimePickerDialog(selectedYear, selectedMonth, selectedDay, hour, minute)
            } else {
                Toast.makeText(this, "Please select a date in the past or today.", Toast.LENGTH_SHORT).show()
            }
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun showTimePickerDialog(year: Int, month: Int, day: Int, hour: Int, minute: Int) {
        val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            if (isToday(year, month, day) && !isValidTime(selectedHour, selectedMinute)) {
                Toast.makeText(this, "Please select a time in the past or the present.", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.updateDateTime(year, month, day, selectedHour, selectedMinute)
            }
        }, hour, minute, true)

        timePickerDialog.show()
    }

    private fun isValidDate(selectedYear: Int, selectedMonth: Int, selectedDay: Int): Boolean {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        return if (selectedYear > currentYear) {
            false
        } else if (selectedYear == currentYear && selectedMonth > currentMonth) {
            false
        } else selectedYear != currentYear || selectedMonth != currentMonth || selectedDay <= currentDay
    }

    private fun isValidTime(hour: Int, minute:Int): Boolean {
        val calendar = Calendar.getInstance()
        val selectedTime = hour*100 + minute
        val currentTime = calendar.get(Calendar.HOUR_OF_DAY)*100 + calendar.get(Calendar.MINUTE)
        return selectedTime <= currentTime
    }

    fun parseDateTime(dateTimeString: String): Calendar? {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        try {
            val date = dateFormat.parse(dateTimeString)
            val calendar = Calendar.getInstance()
            if (date != null) {
                calendar.time = date
                return calendar
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}