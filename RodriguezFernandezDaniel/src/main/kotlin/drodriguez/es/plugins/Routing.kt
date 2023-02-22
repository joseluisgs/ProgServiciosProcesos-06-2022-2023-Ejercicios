package drodriguez.es.plugins

import drodriguez.es.routes.departamentosRoutes
import drodriguez.es.routes.empleadosRoutes
import drodriguez.es.routes.usuariosRoutes
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.resources.Resources
import io.ktor.server.application.*

fun Application.configureRouting() {
    install(Resources)
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }

    usuariosRoutes()
    departamentosRoutes()
    empleadosRoutes()

}


