package com.example.timetracker.mockAPI.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DateResponse(
    @SerialName("dateTime") val datetime: String
)