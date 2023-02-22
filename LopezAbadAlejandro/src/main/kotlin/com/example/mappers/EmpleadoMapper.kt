package com.example.mappers

import com.example.dto.EmpleadoDto
import com.example.models.Empleado
import com.example.models.Rol
import java.util.*

fun EmpleadoDto.toModel(): Empleado {
    return Empleado(
        nombre=nombre,
        email=email,
        password=password,
        avatar=avatar,
        idDepartamento = UUID.fromString(idDepartamento),
        rol= Rol.valueOf(rol.uppercase()),
    )

}