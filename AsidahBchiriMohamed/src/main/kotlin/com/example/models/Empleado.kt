package com.example.models

import com.example.dtos.EmpleadoCreateDto
import com.example.utils.serializers.UUIDSerializers
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Empleado(
    @Serializable(UUIDSerializers::class)
    val uuid: UUID = UUID.randomUUID(),
    val name: String,
    val surname: String,
    var email: String,
    var salary: Double,
    var departamento: String,
    var rol: TypeRole = TypeRole.EMPLEADO

)

enum class TypeRole {
    EMPLEADO, ADMIN
}

fun EmpleadoCreateDto.toModel(): Empleado = Empleado(
    uuid = UUID.randomUUID(),
    name = this.name,
    surname = this.surname,
    email = this.email,
    salary = this.salary,
    departamento = this.departamento
)
