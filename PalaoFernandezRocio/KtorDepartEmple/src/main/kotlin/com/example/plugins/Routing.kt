package com.example.plugins

import com.example.routing.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("API Rochiio")
        }
    }

    departamentoRoutes()
    empleadoRoutes()
    loginRoutes()
    storageRouting()
    usuariosRoutes()
}
