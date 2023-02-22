package com.example.plugins

import com.example.routes.departamentosRoutes
import com.example.routes.empleadosRoutes
import com.example.routes.storageRoutes
import com.example.routes.usersRoutes
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Ejercico PSP!")
        }
    }
    storageRoutes()
    usersRoutes()
    departamentosRoutes()
    empleadosRoutes()
}
