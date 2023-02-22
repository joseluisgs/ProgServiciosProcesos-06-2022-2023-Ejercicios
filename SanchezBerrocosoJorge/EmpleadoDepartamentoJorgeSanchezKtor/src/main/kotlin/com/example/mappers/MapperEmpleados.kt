package resa.mario.mappers

import com.example.dto.EmpleadoDTO
import com.example.models.Empleado
import java.util.UUID


fun Empleado.toDTO(): EmpleadoDTO {
    return EmpleadoDTO(
        name = name,
        email = email,
        departamentoId = departamentoId.toString(),
        avatar = avatar
    )
}


fun EmpleadoDTO.toEmpleado(): Empleado {
    return Empleado(
        name = name,
        email = email,
        departamentoId = departamentoId?.let { UUID.fromString(it) },
        avatar = avatar ?: "https://upload.wikimedia.org/wikipedia/commons/f/f4/User_Avatar_2.png"
    )
}