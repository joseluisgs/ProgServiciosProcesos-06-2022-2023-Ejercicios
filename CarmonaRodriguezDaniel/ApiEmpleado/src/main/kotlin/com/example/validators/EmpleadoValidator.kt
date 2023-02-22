package com.example.validators

import com.example.dto.EmpleadoCreateDto
import com.example.dto.EmpleadoUpdateDto
import io.ktor.server.plugins.requestvalidation.*

fun RequestValidationConfig.empleadoValidation() {
    validate<EmpleadoCreateDto> { empleado ->
        if (empleado.name.isBlank()) {
            ValidationResult.Invalid("El nombre no puede estar vacio.")
        } else if (empleado.id <= 0) {
            ValidationResult.Invalid("La id no puede ser igual o inferior a cero.")
        } else if (empleado.departamento.isBlank()) {
            ValidationResult.Invalid("El departamento no puede estar vacio.")
        } else {
            ValidationResult.Valid
        }
    }
    validate<EmpleadoUpdateDto> { empleado ->
        if (empleado.name.isBlank()) {
            ValidationResult.Invalid("El nombre no puede estar vacio.")
        } else {
            ValidationResult.Valid
        }
    }
}