package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class DepartamentoDto(
    val nombre:String,
    val presupuesto:String
) {
}