package com.example.plugins

import com.example.routes.departamentoRoutes
import com.example.routes.empleadoRoutes
import com.example.routes.storageRoutes
import com.example.routes.userRoutes
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }
    empleadoRoutes()
    departamentoRoutes()
    userRoutes()
    storageRoutes()
}
