package com.example.services.validators

import com.example.dtos.EmpleadoCreateDto
import com.example.dtos.EmpleadoLoginDto
import com.example.dtos.EmpleadoPatchDto
import io.ktor.server.plugins.requestvalidation.*

fun RequestValidationConfig.empleadoValidation() {
    validate<EmpleadoCreateDto> { empleado ->
        if (empleado.name.isBlank()
            || empleado.surname.isBlank()
            || empleado.email.isBlank()
            || empleado.departamento.isBlank()
        )
            ValidationResult.Invalid("No puede haber campos en blanco")
        else if (empleado.salary <= 0)
            ValidationResult.Invalid("El sueldo no puede ser menor o igual que 0")
        else
            ValidationResult.Valid
    }

    validate<EmpleadoLoginDto> { empleado ->
        if (empleado.email.isBlank())
            ValidationResult.Invalid("El correo no puede estar vacío")
        else
            ValidationResult.Valid
    }
}