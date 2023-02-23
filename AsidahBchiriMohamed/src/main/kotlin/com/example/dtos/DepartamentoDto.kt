package com.example.dtos

import com.example.models.Departamento
import kotlinx.serialization.Serializable

@Serializable
data class DepartamentoDto(
    val uuid: String,
    val name: String,
    val empleados: List<EmpleadoDto>
)

@Serializable
data class DepartamentoCreateDto(
    val name: String
)

@Serializable
data class DepartamentoPatchDto(
    val idEmpleado : String,
    val idDepartamento : String
)
fun DepartamentoCreateDto.toModel() = Departamento(
    name = this.name
)
