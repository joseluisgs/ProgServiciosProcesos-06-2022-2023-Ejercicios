package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class UsuarioDto(
    var nombre: String,
    var email: String,
    var avatar: String,
    var password: String,
    var rol: String
)