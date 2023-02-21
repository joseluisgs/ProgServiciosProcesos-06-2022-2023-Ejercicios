package resa.mario.mappers

import resa.mario.dto.DepartamentoDTO
import resa.mario.models.Departamento

/**
 * Funcion que permite pasar de modelo a DTO
 *
 * @return [DepartamentoDTO]
 */
fun Departamento.toDTO(): DepartamentoDTO {
    return DepartamentoDTO(
        nombre, presupuesto
    )
}

/**
 * Funcion que permite pasar de DTO a modelo
 *
 * @return [Departamento]
 */
fun DepartamentoDTO.toDepartamento(): Departamento {
    return Departamento(
        nombre = nombre,
        presupuesto = presupuesto
    )
}