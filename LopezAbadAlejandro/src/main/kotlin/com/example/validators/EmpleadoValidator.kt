package com.example.validators

import com.example.dto.EmpleadoDto
import io.ktor.server.plugins.requestvalidation.*

fun RequestValidationConfig.emplValidator(){
    validate<EmpleadoDto> { empl ->
        if (empl.nombre.isEmpty()){
            ValidationResult.Invalid("El nombre no puede estar vacío")
        }else if(empl.idDepartamento.isEmpty()) {
            ValidationResult.Invalid("Tiene que tener un idDepartamento")
        }else{
            ValidationResult.Valid
        }
    }
}