package drodriguez.es.mappers

import drodriguez.es.dto.EmpleadoDto
import drodriguez.es.models.Empleado
import java.util.*

fun Empleado.toDto(): EmpleadoDto {
    return EmpleadoDto(
        nombre = nombre,
        email = email,
        avatar = avatar,
        departamentoId = departamentoId.toString()
    )
}

fun EmpleadoDto.toEmpleado(): Empleado {
    return Empleado(
        nombre = nombre,
        email = email,
        avatar = avatar,
        departamentoId = departamentoId?.let { UUID.fromString(it) }
    )
}