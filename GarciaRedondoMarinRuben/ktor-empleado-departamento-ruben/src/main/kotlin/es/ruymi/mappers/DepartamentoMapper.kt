package es.ruymi.mappers

import es.ruymi.dto.DepartamentoDTO
import es.ruymi.models.Departamento
import java.util.*


fun Departamento.toDto(): DepartamentoDTO{
    return DepartamentoDTO(
        id = id.toString(),
        nombre = nombre,
        presupuesto = presupuesto
    )
}

fun DepartamentoDTO.toEntity(): Departamento{
    return Departamento(
        id = UUID.fromString(id),
        nombre = nombre,
        presupuesto = presupuesto
    )
}