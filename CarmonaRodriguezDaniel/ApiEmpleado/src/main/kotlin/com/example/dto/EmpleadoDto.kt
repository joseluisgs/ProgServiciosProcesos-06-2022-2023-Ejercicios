package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class EmpleadoCreateDto(
    var id: Long,
    var name: String,
    var departamento: String,
)

@Serializable
data class EmpleadoUpdateDto(
    var name: String,
    var available: Boolean,
)
