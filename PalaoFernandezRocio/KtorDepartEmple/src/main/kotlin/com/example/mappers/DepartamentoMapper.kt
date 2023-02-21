package com.example.mappers

import com.example.dto.DepartamentoDto
import com.example.models.Departamento

fun DepartamentoDto.toDepartamento(): Departamento {
    return Departamento(
        nombre = nombre,
        presupuesto = presupuesto
    )
}