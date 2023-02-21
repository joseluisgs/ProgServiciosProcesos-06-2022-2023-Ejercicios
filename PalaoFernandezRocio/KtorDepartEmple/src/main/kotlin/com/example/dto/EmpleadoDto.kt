package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class EmpleadoDto(
    var nombre: String,
    var email: String,
    var password: String,
    var avatar: String,
    var idDepartamento: String,
    var rol: String
)
