package com.example.timetracker.mockAPI

import android.icu.util.Calendar
import android.util.Log
import com.example.timetracker.mockAPI.models.DateResponse
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json

object ApiModule {
    suspend fun getDate(): DateResponse {
        // Simulate delay
        delay(1000)

        // Mock the API response
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)+1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = 6
        val minute = 30
        val dateTime = String.format("%d-%02d-%02d %02d:%02d", year, month, day, hour, minute)

        // Serialize the DateResponse object as JSON
        val jsonResponse = Json.decodeFromString<DateResponse>("{\"dateTime\": \"$dateTime\"}")

        return jsonResponse
    }
}