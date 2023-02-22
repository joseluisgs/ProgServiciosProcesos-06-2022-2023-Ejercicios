package com.example.dtos

import com.example.models.Empleado
import com.example.utils.serializers.UUIDSerializers
import kotlinx.serialization.Serializable

@Serializable
data class EmpleadoDto(
    val uuid: String,
    val name: String,
    val surname: String,
    val email: String,
    val salary: Double,
    val departamento: String,
    val rol : String = "EMPLEADO"

)

@Serializable
data class EmpleadoCreateDto(
    val name: String,
    val surname: String,
    val email: String,
    val salary: Double,
    val departamento: String,
    val rol : String = "EMPLEADO"
)

@Serializable
data class EmpleadoPatchDto(
    val email: String? = null,
    val salary: Double? = null,
    val departamento: String? = null
)

@Serializable
data class EmpleadoLoginDto(
    val email: String
)

fun Empleado.toDto(): EmpleadoDto = EmpleadoDto(
    uuid = this.uuid.toString(),
    name = this.name,
    surname = this.surname,
    email = this.email,
    salary = this.salary,
    departamento = this.departamento
)