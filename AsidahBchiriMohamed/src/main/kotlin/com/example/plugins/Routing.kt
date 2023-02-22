package com.example.plugins

import com.example.routes.departamentoRoutes
import com.example.routes.empleadoRoutes
import com.example.routes.storageRouting
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*

const val endpoint = "/"
fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }

    empleadoRoutes()
    departamentoRoutes()
    storageRouting()
}
