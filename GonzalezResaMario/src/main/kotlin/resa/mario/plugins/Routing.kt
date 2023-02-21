package resa.mario.plugins

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import resa.mario.routes.departamentosRoutes
import resa.mario.routes.empleadosRoutes
import resa.mario.routes.storageRoutes
import resa.mario.routes.usuariosRoutes

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Ejercicio Opcional PSP")
        }
    }

    // Llamada a las distintas rutas, del paquete "routes"
    storageRoutes()
    usuariosRoutes()
    departamentosRoutes()
    empleadosRoutes()
}
