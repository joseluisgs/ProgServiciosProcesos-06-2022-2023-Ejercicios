package com.ktordam.models

import kotlinx.serialization.Serializable

@Serializable
data class Empleado(
    val id: Int,
    val nombre: String,
    val email: String,
    val avatar: String,
    val departamento: Departamento
)
