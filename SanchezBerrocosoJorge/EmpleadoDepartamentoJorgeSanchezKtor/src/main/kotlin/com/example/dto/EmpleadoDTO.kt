package com.example.dto

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
class EmpleadoDTO(
    val name: String,
    val email: String,
    val departamentoId: String? = null,
    val avatar: String?,
) {
}