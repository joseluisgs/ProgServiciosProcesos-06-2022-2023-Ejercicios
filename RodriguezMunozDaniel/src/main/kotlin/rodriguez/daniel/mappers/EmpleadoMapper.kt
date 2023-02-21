package rodriguez.daniel.mappers

import rodriguez.daniel.dto.EmpleadoDTO
import rodriguez.daniel.dto.EmpleadoDTOcreacion
import rodriguez.daniel.model.Empleado

fun Empleado.toDTO() = EmpleadoDTO(nombre, email, avatar, departamentoId)
fun EmpleadoDTOcreacion.fromDTO() = Empleado(
    id = id, nombre = nombre, email = email,
    avatar = avatar, departamentoId = departamentoId)