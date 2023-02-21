package rodriguez.daniel.plugins

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import rodriguez.daniel.routes.departamentoRoutes
import rodriguez.daniel.routes.empleadoRoutes
import rodriguez.daniel.routes.storageRoutes
import rodriguez.daniel.routes.userRoutes

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }

    userRoutes()
    storageRoutes()
    empleadoRoutes()
    departamentoRoutes()
}
