package com.example.validators

import com.example.dto.UserLoginDto
import io.ktor.server.plugins.requestvalidation.*

fun RequestValidationConfig.userValidation() {
    validate<UserLoginDto> { user ->
        if (user.username.isBlank()) {
            ValidationResult.Invalid("El nombre de usuario no puede estar vacío.")
        } else if (user.password.isBlank()) {
            ValidationResult.Invalid("La contraseña no puede estar vacía.")
        } else {
            ValidationResult.Valid
        }
    }
}