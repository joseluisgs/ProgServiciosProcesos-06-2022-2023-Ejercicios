package sanchez.mireya.plugins

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.http.content.*
import io.ktor.server.application.*
import sanchez.mireya.routes.*

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        static("/static") {
            resources("static")
        }
    }

    //TODO: añadir las rutas
    testRoutes()
    empleadosRoutes()
    departamentosRoutes()
    usuariosRoutes()
    storageRoutes()
}
