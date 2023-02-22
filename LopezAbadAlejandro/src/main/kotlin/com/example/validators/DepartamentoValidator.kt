package com.example.validators

import com.example.dto.DepartamentoDto
import io.ktor.server.plugins.requestvalidation.*

fun RequestValidationConfig.departValidator(){
    validate<DepartamentoDto>{dep->
        if (dep.nombre.isBlank()){
            ValidationResult.Invalid("El nombre no puede estar vacío")
        }else{
            ValidationResult.Valid
        }

    }
}