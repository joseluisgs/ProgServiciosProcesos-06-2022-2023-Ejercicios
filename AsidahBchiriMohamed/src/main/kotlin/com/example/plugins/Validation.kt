package com.example.plugins

import com.example.services.validators.empleadoValidation
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*

fun Application.configureValidation() {
    install(RequestValidation) {
        empleadoValidation()
    }
}