package com.example.validators

import com.example.dto.UsuarioDto
import com.example.models.Rol
import io.ktor.server.plugins.requestvalidation.*

fun RequestValidationConfig.usuarioValidation(){
    validate<UsuarioDto> { usuarioDto ->
        if (usuarioDto.nombre.isEmpty())
            ValidationResult.Invalid("El nombre no puede estar vacío")
        if (usuarioDto.email.isEmpty())
            ValidationResult.Invalid("El email no puede estar vacío")
        if (!usuarioDto.email.matches(Regex("^[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\$")))
            ValidationResult.Invalid("El formato de email es incorrecto")
        if (usuarioDto.rol.isEmpty())
            ValidationResult.Invalid("El rol no puede estar vacío")
        if (usuarioDto.password.isEmpty())
            ValidationResult.Invalid("El password no puede estar vacío")
        if (!Rol.values().map { it.toString() }.contains(usuarioDto.rol.uppercase()))
            ValidationResult.Invalid("EL rol es incorrecto")
        else
            ValidationResult.Valid
    }
}