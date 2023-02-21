package es.ruymi.mappers

import es.ruymi.dto.EmpleadoDTO
import es.ruymi.models.Empleado
import java.util.*

fun Empleado.toDto() : EmpleadoDTO{
    return EmpleadoDTO(
        id = id.toString(),
        nombre = nombre,
        email = email,
        avatar = avatar,
        departamento = departamento.toDto()
    )
}

fun EmpleadoDTO.toEntity() : Empleado{
    return Empleado(
        id = UUID.fromString(id),
        nombre = nombre,
        email = email,
        avatar = avatar,
        departamento = departamento.toEntity()
    )
}