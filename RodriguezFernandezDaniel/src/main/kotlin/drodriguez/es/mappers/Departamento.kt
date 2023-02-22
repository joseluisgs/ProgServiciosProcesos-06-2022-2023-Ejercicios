package drodriguez.es.mappers

import drodriguez.es.dto.DepartamentoDto
import drodriguez.es.models.Departamento

fun Departamento.toDto(): DepartamentoDto {
    return DepartamentoDto(
        nombreDepartamento
    )
}

fun DepartamentoDto.toDepartamento(): Departamento {
    return Departamento(
        nombreDepartamento=nombreDepartamento
    )
}