package com.example.validators

import com.example.dto.DepartamentoDto
import io.ktor.server.plugins.requestvalidation.*

fun RequestValidationConfig.departamentoValidation(){
    validate<DepartamentoDto> { departamento ->
        if (departamento.nombre.isBlank()) {
            ValidationResult.Invalid("El nombre no puede estar vacío")
        } else if (departamento.presupuesto < 0) {
            ValidationResult.Invalid("El presupuesto no puede ser negativo")
        } else {
            ValidationResult.Valid
        }
    }
}