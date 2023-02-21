package resa.mario.routes

import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.post
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import resa.mario.services.StorageService
import java.io.File
import java.util.*

private const val END_POINT = "api/storage"

fun Application.storageRoutes() {

    val storageService: StorageService by inject()

    routing {
        route("/$END_POINT") {
            // get file by name
            get("{name}", {
                description = "Get by name file"
                request {
                    pathParameter<String>("name") {
                        description = "Nombre del archivo a buscar"
                        required = true
                    }
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Nombre correcto"
                        body<File> { description = "Ruta del archivo solicitado" }
                    }
                    HttpStatusCode.NotFound to {
                        description = "Archivo no localizado"
                        body<String> { description = "Mensaje con la excepcion correspondiente" }
                    }
                }
            }) {
                try {
                    val name = call.parameters["name"].toString()
                    val file = storageService.getFile(name)

                    call.respond(HttpStatusCode.OK, file.toString())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.NotFound, e.message.toString())
                }
            }

            // Post file
            post({
                description = "Post file"

                response {
                    HttpStatusCode.OK to {
                        description = "Archivo almacenado correctamente"
                        body<File> { description = "Archivo almacenado " }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Error en la subida de archivo"
                        body<String> { description = "Mensaje con la excepcion correspondiente" }
                    }
                }
            }) {
                try {
                    val readChannel = call.receiveChannel()

                    // Damos nombre al archivo, y lo almacenamos con el metodo correspondiente
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