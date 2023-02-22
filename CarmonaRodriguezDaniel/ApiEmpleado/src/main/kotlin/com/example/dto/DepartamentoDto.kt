package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class DepartamentoCreateDto(
    var id: Long,
    var name: String,
)

@Serializable
data class DepartamentoUpdateDto(
    var name: String,
)