package sanchez.mireya.routes

import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import sanchez.mireya.services.StorageService
import java.util.*

private const val ENDPOINT = "api/storage"

fun Application.storageRoutes() {

    val storageService: StorageService by inject()

    routing {
        route("/$ENDPOINT") {
            get("{name}") {
                try {
                    val name = call.parameters["name"].toString()
                    val file = storageService.getFile(name)

                    call.respond(HttpStatusCode.OK, file.toString())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.NotFound, e.message.toString())
                }
            }
            post() {
                try {
                    val readChannel = call.receiveChannel()
                    val fileName = UUID.randomUUID().toString() + ".png"
                    val res = storageService.saveFile(fileName, readChannel)

                    call.respond(HttpStatusCode.OK, res)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }
        }
    }
}

