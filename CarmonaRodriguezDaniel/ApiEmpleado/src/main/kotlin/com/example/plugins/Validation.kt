package com.example.plugins

import com.example.validators.departamentoValidation
import com.example.validators.empleadoValidation
import com.example.validators.userValidation
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*

fun Application.configureValidation() {
    install(RequestValidation) {
        empleadoValidation()
        departamentoValidation()
        userValidation()
    }
}