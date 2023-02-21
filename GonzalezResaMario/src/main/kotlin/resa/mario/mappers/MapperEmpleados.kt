package resa.mario.mappers

import resa.mario.dto.EmpleadoDTO
import resa.mario.models.Empleado
import java.util.UUID

/**
 * Funcion que permite pasar de modelo a DTO
 *
 * @return [EmpleadoDTO]
 */
fun Empleado.toDTO(): EmpleadoDTO {
    return EmpleadoDTO(
        nombre = nombre,
        email = email,
        departamentoId = departamentoId.toString(),
        avatar = avatar
    )
}

/**
 * Funcion que permite pasar de DTO a modelo
 *
 * @return [Empleado]
 */
fun EmpleadoDTO.toEmpleado(): Empleado {
    return Empleado(
        nombre = nombre,
        email = email,
        departamentoId = departamentoId?.let { UUID.fromString(it) },
        avatar = avatar ?: "https://cdn-icons-png.flaticon.com/512/2550/2550260.png"
    )
}