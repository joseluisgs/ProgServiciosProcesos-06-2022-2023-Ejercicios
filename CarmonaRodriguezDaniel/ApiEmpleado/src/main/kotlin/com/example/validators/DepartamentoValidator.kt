package com.example.validators

import com.example.dto.DepartamentoCreateDto
import com.example.dto.DepartamentoUpdateDto
import io.ktor.server.plugins.requestvalidation.*

fun RequestValidationConfig.departamentoValidation() {
    validate<DepartamentoCreateDto> { departamento ->
        if (departamento.name.isBlank()) {
            ValidationResult.Invalid("El nombre no puede estar vacio.")
        } else if (departamento.id <= 0) {
            ValidationResult.Invalid("La id no puede ser igual o inferior a cero.")
        } else {
            ValidationResult.Valid
        }
    }
    validate<DepartamentoUpdateDto> { departamento ->
        if (departamento.name.isBlank()) {
            ValidationResult.Invalid("El nombre no puede estar vacio.")
        } else {
            ValidationResult.Valid
        }
    }

}