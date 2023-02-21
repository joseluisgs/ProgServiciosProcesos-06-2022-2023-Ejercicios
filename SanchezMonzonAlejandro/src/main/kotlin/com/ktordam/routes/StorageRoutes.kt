package com.ktordam.routes

import com.ktordam.services.storage.StorageService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.util.*

private const val ENDPOINT = "api/storage"

/**
 * Función para implementar las rutas de Storage.
 */
fun Application.storageRoutes() {
    val storageService: StorageService by inject()

    routing {
        route("/$ENDPOINT") {
            get("{name}") {
                try {
                    val nombre = call.parameters["name"].toString()
                    val fichero = storageService.getFile(nombre)

                    call.respond(HttpStatusCode.OK, fichero.toString())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.NotFound, e.message.toString())
                }
            }

            post {
                try {
                    val readChannel = call.receiveChannel()
                    val nombre = UUID.randomUUID().toString() + ".png"
                    val result = storageService.saveFile(nombre, readChannel)

                    call.respond(HttpStatusCode.OK, result)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }
        }
    }
}