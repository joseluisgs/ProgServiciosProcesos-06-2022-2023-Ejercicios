package rodriguez.daniel.routes

import io.github.smiley4.ktorswaggerui.dsl.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import rodriguez.daniel.dto.DepartamentoDTO
import rodriguez.daniel.dto.DepartamentoDTOcreacion
import rodriguez.daniel.dto.EmpleadoDTO
import rodriguez.daniel.dto.EmpleadoDTOcreacion
import rodriguez.daniel.exception.DepartamentoExceptionBadRequest
import rodriguez.daniel.exception.DepartamentoExceptionNotFound
import rodriguez.daniel.model.Role
import rodriguez.daniel.services.departamento.DepartamentoService
import rodriguez.daniel.services.user.UserService
import java.util.*

private const val ENDPOINT = "ejercicioKtor/departamentos"

fun Application.departamentoRoutes() {
    val departamentos: DepartamentoService by inject()
    val users: UserService by inject()

    routing {
        route("/$ENDPOINT") {
            get({
                description = "Gets all departamentos."
                response {
                    default {
                        description = "Devuelve una lista de DTO de todos los departamentos."
                    }
                    HttpStatusCode.OK to {
                        description = "When it searches correctly."
                        body<List<DepartamentoDTO>> { description = "When it searches correctly." }
                    }
                    HttpStatusCode.NotFound to {
                        description = "When there are no departamentos."
                        body<List<DepartamentoDTO>> { description = "When there are no departamentos." }
                    }
                }
            }) {
                val res = departamentos.findAllDepartamentos()
                if (res.isEmpty()) call.respond(HttpStatusCode.NotFound, res)
                else call.respond(HttpStatusCode.OK, res)
            }

            get("{id}", {
                description = "Gets a departamento."
                request {
                    pathParameter<String>("id") {
                        description = "Id del departamento."
                        required = true
                    }
                }
                response {
                    default {
                        description = "Devuelve el DTO del departamento buscado."
                    }
                    HttpStatusCode.OK to {
                        description = "When it searches correctly."
                        body<DepartamentoDTO> { description = "When it searches correctly." }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Error when searching."
                        body<String> { description = "Error when searching." }
                    }
                    HttpStatusCode.NotFound to {
                        description = "Couldn't find departamento with id {id}."
                        body<String> { description = "Couldn't find departamento with id {id}." }
                    }
                }
            }) {
                try {
                    val id = UUID.fromString(call.parameters["id"])
                    val res = departamentos.findDepartamentoById(id)
                    call.respond(HttpStatusCode.OK, res)
                } catch (e: DepartamentoExceptionNotFound) {
                    call.respond(HttpStatusCode.NotFound, e.message.toString())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            post({
                description = "Creates a new departamento."
                request {
                    body<DepartamentoDTOcreacion> {
                        description = "Nuevo departamento."
                        required = true
                    }
                }
                response {
                    default {
                        description = "Devuelve el DTO del objeto creado."
                    }
                    HttpStatusCode.Created to {
                        description = "When it inserts correctly."
                        body<DepartamentoDTO> { description = "When it inserts correctly." }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Error when creating."
                        body<String> { description = "Error when creating." }
                    }
                }
            }) {
                try {
                    val dep = call.receive<DepartamentoDTOcreacion>()
                    val res = departamentos.saveDepartamento(dep)
                    call.respond(HttpStatusCode.Created, res)
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
                        description = "Borra el departamento."
                    }
                    HttpStatusCode.OK to {
                        description = "When it deletes correctly."
                        body<EmpleadoDTO> { description = "When it deletes correctly." }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Cannot delete departamento. It has empleados attached to it."
                        body<String> { description = "Cannot delete departamento. It has empleados attached to it." }
                    }
                    HttpStatusCode.NotFound to {
                        description = "Couldn't find departamento with id {id}."
                        body<String> { description = "Couldn't find departamento with id {id}." }
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
                            val res = departamentos.deleteDepartamento(id)
                            call.respond(HttpStatusCode.OK, res)
                        } else call.respond(HttpStatusCode.Forbidden, "You can't delete this.")
                    } else call.respond(HttpStatusCode.Unauthorized, "User with id $userId not found.")
                } catch (e: DepartamentoExceptionNotFound) {
                    call.respond(HttpStatusCode.NotFound, e.message.toString())
                } catch (e: DepartamentoExceptionBadRequest) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }
        }
    }
}