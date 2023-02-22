package com.example.validators

import com.example.dto.LoginDto
import com.example.dto.UsuarioDto
import io.ktor.server.plugins.requestvalidation.*

fun RequestValidationConfig.usuarioValidator(){
    validate<UsuarioDto>{dto->
        if(dto.nombre.isEmpty()){
            ValidationResult.Invalid("El nombre no puede estar vacío")
        }else{
            ValidationResult.Valid
        }
    }
}


fun RequestValidationConfig.loginValidator(){
    validate<LoginDto>{ log ->
        if(log.email.isEmpty()){
            ValidationResult.Invalid("El email no puede estar vacío")
        }else if (log.password.isEmpty()){
            ValidationResult.Invalid("La contraseña no puede estar vacía")
        }else{
            ValidationResult.Valid
        }
    }
}
