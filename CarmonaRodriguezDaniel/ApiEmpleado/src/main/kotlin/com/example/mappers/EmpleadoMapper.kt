package com.example.mappers

import com.example.dto.EmpleadoCreateDto
import com.example.dto.EmpleadoUpdateDto
import com.example.models.Empleado

fun EmpleadoCreateDto.toEmpleado(): Empleado {
    return Empleado(
        id = this.id,
        name = this.name,
        departamento = null,
        available = true
    )
}

fun EmpleadoUpdateDto.toEmpleado(): Empleado {
    return Empleado(
        id = null,
        name = this.name,
        departamento = null,
        available = this.available
    )
}
