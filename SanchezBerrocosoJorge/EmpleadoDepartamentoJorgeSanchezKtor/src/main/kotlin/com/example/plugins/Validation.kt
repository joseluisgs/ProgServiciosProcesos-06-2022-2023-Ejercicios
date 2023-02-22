package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*

fun Application.configureValidation() {
    install(RequestValidation) {
        //usersValidation()
        //representantesValidation()
       // raquetasValidation()
       // tenistasValidation()
    }
}