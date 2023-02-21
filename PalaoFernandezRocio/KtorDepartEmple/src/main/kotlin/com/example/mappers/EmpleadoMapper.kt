package com.example.mappers

import com.example.dto.EmpleadoDto
import com.example.models.Empleado
import com.example.models.Rol
import java.util.*

fun EmpleadoDto.toEmpleado() : Empleado {
    return Empleado(
        nombre = nombre,
        email = email,
        avatar = avatar,
        idDepartamento = UUID.fromString(idDepartamento),
        rol = Rol.valueOf(rol.uppercase()),
        password = password
    )
}