package com.example.models


import com.example.serializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Departamento(
    @Serializable(with = UUIDSerializer::class)
    var id: UUID = UUID.randomUUID(),
    val nombre: String,
    val presupuesto: Float
)