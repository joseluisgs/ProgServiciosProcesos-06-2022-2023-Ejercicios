package com.example.mappers

import com.example.dto.DepartamentoCreateDto
import com.example.dto.DepartamentoUpdateDto
import com.example.models.Departamento

fun DepartamentoCreateDto.toDepartamento(): Departamento {
    return Departamento(
        id = this.id,
        name = this.name,
    )
}

fun DepartamentoUpdateDto.toDepartamento(): Departamento {
    return Departamento(
        id = null,
        name = this.name,
    )
}