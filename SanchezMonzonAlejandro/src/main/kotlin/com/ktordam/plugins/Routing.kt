package com.ktordam.plugins

import com.ktordam.routes.departamentosRoutes
import com.ktordam.routes.empleadosRoutes
import com.ktordam.routes.storageRoutes
import com.ktordam.routes.usuariosRoutes
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.http.content.*
import io.ktor.server.application.*

/**
 * Función que sirve para configurar las rutas de la API.
 */
fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("Ktor DAM - Alejandro Sánchez Monzón")
        }

        static("/static") {
            resources("static")
        }
    }

    empleadosRoutes()
    departamentosRoutes()
    usuariosRoutes()
    storageRoutes()
}
