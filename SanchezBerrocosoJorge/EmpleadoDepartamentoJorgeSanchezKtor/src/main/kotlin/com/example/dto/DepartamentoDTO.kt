package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class DepartamentoDTO(
    val name: String,
    val presupuesto: Double
) {
}