package com.example.models

import com.example.serializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Usuario(
    @Serializable(with = UUIDSerializer::class)
    var id: UUID = UUID.randomUUID(),
    var nombre: String,
    var email: String,
    var avatar: String,
    var password: String,
    var rol: Rol
)