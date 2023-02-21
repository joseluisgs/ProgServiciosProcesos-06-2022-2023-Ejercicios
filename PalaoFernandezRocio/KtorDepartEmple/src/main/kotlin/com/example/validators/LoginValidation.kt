package com.example.validators

import com.example.dto.LoginDto
import io.ktor.server.plugins.requestvalidation.*

fun RequestValidationConfig.loginValidation(){
    validate<LoginDto>{login ->
        if(login.email.isEmpty()){
            ValidationResult.Invalid("El email no puede estar vacío")
        }else if (login.password.isEmpty()){
            ValidationResult.Invalid("La contraseña no puede estar vacía")
        }else{
            ValidationResult.Valid
        }
    }
}