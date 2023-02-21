package mendoza.js.plugins

import io.github.smiley4.ktorswaggerui.dsl.get
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import mendoza.js.routes.departamentosRoutes
import mendoza.js.routes.empleadosRoutes
import mendoza.js.routes.usersRoutes

fun Application.configureRouting() {
    routing {
        get("/", {
            description = "Empresa Ktor Rest"
            response {
                default {
                    description = "Default Response"
                }
                HttpStatusCode.OK to {
                    description = "Respuesta por defecto"
                    body<String> { description = "el saludo" }
                }
            }
        }) {
            call.respondText("Hola, empresa!")
        }
    }

    departamentosRoutes()
    empleadosRoutes()
    usersRoutes()
}
