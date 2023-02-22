package com.example.models

import com.example.serializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Empleado(
    @Serializable(with = UUIDSerializer::class)
    var id: UUID = UUID.randomUUID(),
    var nombre: String,
    var email: String,
    var password: String,
    var avatar: String,
    @Serializable(with= UUIDSerializer::class)
    var idDepartamento: UUID,
    var rol: Rol
)