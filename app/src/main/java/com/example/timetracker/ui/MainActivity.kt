package com.example.timetracker.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.timetracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDateTimePicker()
    }

    private fun initDateTimePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = 6
        val minute = 30

        binding.datetimeData.apply {
            text = setDateTime(year, month, day, hour, minute)
            setOnClickListener {
            showDateTimePickerDialog(year, month, day, hour, minute)
            }
        }
    }

    private fun setDateTime(year: Int, month: Int, day: Int, hour: Int, minute: Int) : String{
        return String.format("%02d/%02d/%d  %02d:%02d", day, month + 1, year, hour, minute)
    }

    private fun isToday(year: Int, month: Int, day: Int) : Boolean{
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        return year == currentYear && month == currentMonth && day == currentDay
    }

    private fun showDateTimePickerDialog(year: Int, month: Int, day: Int, hour: Int, minute: Int) {
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
                binding.datetimeData.text = setDateTime(year, month, day, selectedHour, selectedMinute)
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
}