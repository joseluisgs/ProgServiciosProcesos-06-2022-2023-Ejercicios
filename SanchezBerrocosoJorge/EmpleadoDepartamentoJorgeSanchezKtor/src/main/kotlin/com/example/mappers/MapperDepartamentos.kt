package resa.mario.mappers

import com.example.dto.DepartamentoDTO
import com.example.models.Departamento


fun Departamento.toDTO(): DepartamentoDTO {
    return DepartamentoDTO(
        name = name,
        presupuesto = presupuesto
    )
}

fun DepartamentoDTO.toDepartamento(): Departamento {
    return Departamento(
        name = name,
        presupuesto = presupuesto
    )
}