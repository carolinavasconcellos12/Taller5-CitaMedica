package com.example.citamedica.Models

import java.io.Serializable

data class Usuario(
    var id: Int,
    var nombre: String,
    var apellido: String,
    var email: String,
    var password: String
) : Serializable
