package sanchez.mireya.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private const val ENDPOINT = "api/test"

fun Application.testRoutes() {
    routing {
        route("/$ENDPOINT") {
            get {
                call.respond(HttpStatusCode.OK, "Get test")
            }
        }
    }

}