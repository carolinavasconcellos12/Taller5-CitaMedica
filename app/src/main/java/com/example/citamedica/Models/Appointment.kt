package com.example.citamedica.Models

data class Appointment(
    val id: Int,
    val user_id: Int,
    val doctorName: String,
    val scheduledDate: String,
    val scheduledTime: String

)