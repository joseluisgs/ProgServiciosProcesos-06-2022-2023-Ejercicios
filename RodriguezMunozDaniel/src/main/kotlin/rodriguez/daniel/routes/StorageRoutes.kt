package rodriguez.daniel.routes

import io.github.smiley4.ktorswaggerui.dsl.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.utils.io.*
import org.koin.ktor.ext.inject
import rodriguez.daniel.dto.UserDTO
import rodriguez.daniel.model.Role
import rodriguez.daniel.services.storage.StorageService
import rodriguez.daniel.services.user.UserService
import java.io.File
import java.util.*

private const val ENDPOINT = "ejercicioKtor/storage"

fun Application.storageRoutes() {
    val storage: StorageService by inject()
    val users: UserService by inject()

    routing {
        route("/$ENDPOINT") {
            post({
                description = "Inserts a file."
                request {
                    body<ByteReadChannel> {
                        description = "Archivo a guardar."
                        required = true
                    }
                }
                response {
                    default {
                        description = "Devuelve un mapa con los detalles del archivo."
                    }
                    HttpStatusCode.OK to {
                        description = "When it saves correctly."
                        body<Map<String, String>> { description = "Map with the file details." }
                    }
                    HttpStatusCode.InternalServerError to {
                        description = "Could not save in {fileName}."
                        body<String> { description = "Could not save in {fileName}." }
                    }
                }
            }) {
                println("POST /$ENDPOINT")
                try {
                    val readChannel = call.receiveChannel()
                    val fileName = UUID.randomUUID().toString()
                    val res = storage.saveFile(fileName, readChannel)
                    if (res != null) call.respond(HttpStatusCode.OK, res)
                    else call.respond(HttpStatusCode.InternalServerError, "Could not save in $fileName.")
                } catch (e: Exception) {
                    call.respondText("Error: ${e.message}", status = HttpStatusCode.InternalServerError)
                }
            }

            get("{fileName}", {
                description = "Gets a file."
                request {
                    pathParameter<String>("fileName") {
                        description = "Nombre del archivo."
                        required = true
                    }
                }
                response {
                    default {
                        description = "Devuelve el archivo."
                    }
                    HttpStatusCode.OK to {
                        description = "Returns the file."
                        body<File> { description = "Returns the file." }
                    }
                    HttpStatusCode.NotFound to {
                        description = "File with name {fileName} not found."
                        body<String> { description = "File with name {fileName} not found." }
                    }
                }
            }) {
                println("GET /$ENDPOINT/{fileName}")
                val fileName = call.parameters["fileName"].toString()
                val file = storage.getFile(fileName)
                if (file != null) call.respondFile(file)
                else call.respond(HttpStatusCode.NotFound, "file with name $fileName not found")
            }

            authenticate {
                delete("{fileName}", {
                    description = "Deletes a file."
                    request {
                        headerParameter<String>("token") {
                            description = "Token para autenticarte."
                            required = true
                        }
                        pathParameter<String>("fileName") {
                            description = "Nombre del archivo."
                            required = true
                        }
                    }
                    response {
                        default {
                            description = "Devuelve no content despues de borrar."
                        }
                        HttpStatusCode.NoContent to {
                            description = "When it deletes correctly."
                        }
                        HttpStatusCode.NotFound to {
                            description = "File with name {fileName} not found."
                            body<String> { description = "File with name {fileName} not found." }
                        }
                        HttpStatusCode.Unauthorized to {
                            description = "Incorrect token."
                            body<String> { description = "Incorrect token." }
                        }
                        HttpStatusCode.Forbidden to {
                            description = "You can't delete this."
                            body<String> { description = "You can't delete this." }
                        }
                    }
                }) {
                    println("DELETE /$ENDPOINT/{fileName}")
                    val jwt = call.principal<JWTPrincipal>()
                    val userId = jwt?.payload?.getClaim("id")
                        .toString().replace("\"", "")
                    val user = users.findUserById(UUID.fromString(userId.trim()))
                    if (user != null) {
                        if (user.role == Role.ADMIN) {
                            val fileName = call.parameters["fileName"].toString()
                            val deleted = storage.deleteFile(fileName)
                            if (deleted == null) call.respond(HttpStatusCode.NotFound, "file with name $fileName not found.")
                            else call.respond(HttpStatusCode.NoContent)
                        }
                        else call.respond(HttpStatusCode.Forbidden, "You can't delete this.")
                    }
                    else call.respond(HttpStatusCode.Unauthorized, "Could not authenticate. User not found.")
                }
            }
        }
    }
}