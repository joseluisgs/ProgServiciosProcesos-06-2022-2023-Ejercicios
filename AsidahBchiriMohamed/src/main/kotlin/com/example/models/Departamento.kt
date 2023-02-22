package com.example.models

import com.example.utils.serializers.UUIDSerializers
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Departamento(
    @Serializable(UUIDSerializers::class)
    val uuid : UUID = UUID.randomUUID(),
    val name: String,
    var empleados : MutableList<Empleado> = mutableListOf()
)

