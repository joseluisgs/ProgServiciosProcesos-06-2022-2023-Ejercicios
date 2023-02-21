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
import rodriguez.daniel.dto.EmpleadoDTO
import rodriguez.daniel.dto.EmpleadoDTOcreacion
import rodriguez.daniel.exception.DepartamentoExceptionNotFound
import rodriguez.daniel.exception.EmpleadoExceptionBadRequest
import rodriguez.daniel.exception.EmpleadoExceptionNotFound
import rodriguez.daniel.model.Role
import rodriguez.daniel.services.empleado.EmpleadoService
import rodriguez.daniel.services.storage.StorageService
import rodriguez.daniel.services.user.UserService
import java.util.*

private const val ENDPOINT = "ejercicioKtor/empleados"

fun Application.empleadoRoutes() {
    val empleados: EmpleadoService by inject()
    val users: UserService by inject()
    val storage: StorageService by inject()

    routing {
        route("/$ENDPOINT") {
            get({
                description = "Gets all empleados."
                response {
                    default {
                        description = "Devuelve una lista de DTO de todos los empleados."
                    }
                    HttpStatusCode.OK to {
                        description = "When it searches correctly."
                        body<List<EmpleadoDTO>> { description = "When it searches correctly." }
                    }
                    HttpStatusCode.NotFound to {
                        description = "When there are no empleados."
                        body<List<EmpleadoDTO>> { description = "When there are no empleados." }
                    }
                }
            }) {
                val res = empleados.findAllEmpleados()
                if (res.isEmpty()) call.respond(HttpStatusCode.NotFound, res)
                else call.respond(HttpStatusCode.OK, res)
            }

            get("{id}", {
                description = "Gets an empleado."
                request {
                    pathParameter<String>("id") {
                        description = "Id del empleado."
                        required = true
                    }
                }
                response {
                    default {
                        description = "Devuelve el DTO del empleado buscado."
                    }
                    HttpStatusCode.OK to {
                        description = "When it searches correctly."
                        body<EmpleadoDTO> { description = "When it searches correctly." }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Error when searching."
                        body<String> { description = "Error when searching." }
                    }
                    HttpStatusCode.NotFound to {
                        description = "Couldn't find empleado with id {id}."
                        body<String> { description = "Couldn't find empleado with id {id}." }
                    }
                }
            }) {
                try {
                    val id = UUID.fromString(call.parameters["id"])
                    val res = empleados.findEmpleadoById(id)
                    call.respond(HttpStatusCode.OK, res)
                } catch (e: EmpleadoExceptionNotFound) {
                    call.respond(HttpStatusCode.NotFound, e.message.toString())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            post({
                description = "Creates a new empleado."
                request {
                    body<EmpleadoDTOcreacion> {
                        description = "Nuevo empleado."
                        required = true
                    }
                }
                response {
                    default {
                        description = "Devuelve el DTO del objeto creado."
                    }
                    HttpStatusCode.Created to {
                        description = "When it inserts correctly."
                        body<EmpleadoDTO> { description = "When it inserts correctly." }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Error when creating."
                        body<String> { description = "Error when creating." }
                    }
                    HttpStatusCode.NotFound to {
                        description = "Couldn't find departamento with id {departamento_id}."
                        body<String> { description = "Couldn't find departamento with id {departamento_id}." }
                    }
                }
            }) {
                try {
                    val dep = call.receive<EmpleadoDTOcreacion>()
                    val res = empleados.saveEmpleado(dep)
                    call.respond(HttpStatusCode.Created, res)
                } catch (e: DepartamentoExceptionNotFound) {
                    call.respond(HttpStatusCode.NotFound, e.message.toString())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            // este es solo para cambiar el avatar del empleado
            put("{id}", {
                description = "Changes the avatar from an empleado."
                request {
                    pathParameter<String>("id") {
                        description = "Id del empleado cuyo avatar se quiere cambiar."
                        required = true
                    }
                    body<ByteReadChannel> {
                        description = "Avatar."
                        required = true
                    }
                }
                response {
                    default {
                        description = "Devuelve el DTO del objeto modificado."
                    }
                    HttpStatusCode.Created to {
                        description = "When it changes de avatar correctly."
                        body<EmpleadoDTO> { description = "When it changes de avatar correctly." }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Unable to save file or it is not a valid one."
                        body<String> { description = "Unable to save file or it is not a valid one." }
                    }
                    HttpStatusCode.NotFound to {
                        description = "Couldn't find departamento with id {departamento_id}."
                        body<String> { description = "Couldn't find departamento with id {departamento_id}." }
                    }
                }
            }) {
                try {
                    val readChannel = call.receiveChannel()
                    val fileName = UUID.randomUUID().toString()
                    val saved = storage.saveFile(fileName, readChannel)
                    if (saved == null)
                        call.respond(HttpStatusCode.BadRequest, "Unable to save file or it is not a valid one.")
                    else {
                        if (saved["fileName"] == null)
                            call.respond(HttpStatusCode.BadRequest, "Unable to save file or it is not a valid one.")
                        else {
                            val id = UUID.fromString(call.parameters["id"])
                            val previous = empleados.findEmpleadoById(id)
                            val newAvatar = saved["fileName"] ?: previous.avatar
                            val new = EmpleadoDTOcreacion(
                                id, previous.nombre, previous.email,
                                newAvatar, previous.departamentoId
                            )
                            val res = empleados.saveEmpleado(new)
                            call.respond(HttpStatusCode.Created, res)
                        }
                    }
                } catch (e: EmpleadoExceptionNotFound) {
                    call.respond(HttpStatusCode.NotFound, e.message.toString())
                } catch (e: DepartamentoExceptionNotFound) {
                    call.respond(HttpStatusCode.NotFound, e.message.toString())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            delete("{id}", {
                description = "Deletes an empleado."
                request {
                    headerParameter<String>("token") {
                        description = "Token para autenticarte."
                        required = true
                    }
                    pathParameter<String>("id") {
                        description = "Id de busqueda."
                        required = true
                    }
                }
                response {
                    default {
                        description = "Borra el empleado."
                    }
                    HttpStatusCode.OK to {
                        description = "When it deletes correctly."
                        body<EmpleadoDTO> { description = "When it deletes correctly." }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Unable to delete empleado {id}."
                        body<String> { description = "Unable to delete empleado {id}." }
                    }
                    HttpStatusCode.Unauthorized to {
                        description = "User with id {id} not found."
                        body<String> { description = "User with id {id} not found." }
                    }
                    HttpStatusCode.Forbidden to {
                        description = "You can't delete this."
                        body<String> { description = "You can't delete this." }
                    }
                }
            }) {
                try {
                    val jwt = call.principal<JWTPrincipal>()
                    val userId = jwt?.payload?.getClaim("id")
                        .toString().replace("\"", "")
                    val user = users.findUserById(UUID.fromString(userId.trim()))
                    if (user != null) {
                        if (user.role == Role.ADMIN) {
                            val id = UUID.fromString(call.parameters["id"])
                            val res = empleados.deleteEmpleado(id)
                            call.respond(HttpStatusCode.OK, res)
                        } else call.respond(HttpStatusCode.Forbidden, "You can't consult all users.")
                    } else call.respond(HttpStatusCode.Unauthorized, "User with id $userId not found.")
                } catch (e: EmpleadoExceptionBadRequest) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }
        }
    }
}