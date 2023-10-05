package com.example.citamedica.Models

import java.io.Serializable

data class Usuario(
    val id: Int,
    val nombre: String,
    val apellido: String,
    val email: String,
    val password: String
) : Serializable
