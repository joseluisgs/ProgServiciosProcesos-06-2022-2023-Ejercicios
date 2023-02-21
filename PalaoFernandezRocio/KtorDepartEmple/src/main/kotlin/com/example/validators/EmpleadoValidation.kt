package com.example.validators

import com.example.dto.EmpleadoDto
import io.ktor.server.plugins.requestvalidation.*

fun RequestValidationConfig.empleadoValidation(){
    validate<EmpleadoDto>{ empleado ->
        if (empleado.nombre.isEmpty()){
            ValidationResult.Invalid("El nombre no puede estar vacío")
        }else if (empleado.email.isEmpty()){
            ValidationResult.Invalid("El email no puede estar vacío")
        }else if(!empleado.email.matches(Regex("^[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\$"))) {
            ValidationResult.Invalid("El formato de email es incorrecto")
        }else if(empleado.idDepartamento.isEmpty()){
            ValidationResult.Invalid("El idDepartamento no puede estar vacío")
        }else{
            ValidationResult.Valid
        }
    }
}