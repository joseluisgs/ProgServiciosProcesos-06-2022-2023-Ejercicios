package drodriguez.es.routes

import drodriguez.es.dto.DepartamentoDto
import drodriguez.es.mappers.toDepartamento
import drodriguez.es.mappers.toDto
import drodriguez.es.models.User
import drodriguez.es.services.departamento.DepartamentoService
import drodriguez.es.services.users.UserService
import io.github.smiley4.ktorswaggerui.dsl.delete
import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.post
import io.github.smiley4.ktorswaggerui.dsl.put
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.util.*

private const val END_POINT = "departamentos"

fun Application.departamentosRoutes() {

    val departamentoService: DepartamentoService by inject()
    val usuarioService: UserService by inject()

    routing {
        route("/$END_POINT") {

            get({
                description = "Find all departamentos"

            }
            ) {
                val result = mutableListOf<DepartamentoDto>()
                departamentoService.findAll().collect {
                    result.add(it.toDto())
                }
                departamentoService.findAll().collect {
                    println(it)
                }
                call.respond(HttpStatusCode.OK, result)
            }

            get("{id}", {
                description = "Get by id departamento"
                request {
                    pathParameter<String>("id") {
                        description = "Id del departamento"
                        required = true
                    }
                }
            }){
                try {
                    val id = call.parameters["id"]!!
                    val departamento = departamentoService.findById(UUID.fromString(id))

                    call.respond(HttpStatusCode.OK, departamento.toDto())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            post({
                description = "New departamento"
                request {
                    body<DepartamentoDto> {
                        description = "Se recibe un DepartamentoDTO"
                    }
                }
            }
            ) {
                try {
                    val departamentoReceive = call.receive<DepartamentoDto>()
                    val departamentoSave = departamentoService.save(departamentoReceive.toDepartamento())
                    call.respond(
                        HttpStatusCode.Created, departamentoService.findById(departamentoSave.id).toDto()
                    )
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            put("{id}", {
                description = "Actualizar departamento"
                request {
                    pathParameter<String>("id") {
                        description = "Id del departamento"
                        required = true
                    }
                    body<DepartamentoDto> {
                        description = "New dpt"
                    }
                }
            }
            ) {
                try {
                    val id = call.parameters["id"]!!
                    val request = call.receive<DepartamentoDto>()
                    val departamento = departamentoService.update(UUID.fromString(id), request.toDepartamento())
                    call.respond(HttpStatusCode.OK, departamento.toDto())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            authenticate {
                delete("{id}", {
                    description = "Delete id departamento"
                    securitySchemeName = "JWT-Auth"
                    request {
                        pathParameter<String>("id") {
                            description = "Id del departamento a eliminar"
                            required = true
                        }
                    }
                }
                ) {
                    try {
                        val id = call.parameters["id"]!!
                        val token = call.principal<JWTPrincipal>()
                        val userId = token?.payload?.getClaim("userId").toString().replace("\"", "")
                        val user = usuarioService.findById(UUID.fromString(userId))

                        user.let {
                            if (user.rol == User.Rol.JEFE.name) {
                                val departamentoDelete = departamentoService.findById(UUID.fromString(id))
                                departamentoService.delete(departamentoDelete)
                                call.respond(HttpStatusCode.NoContent)
                            } else call.respond(HttpStatusCode.Unauthorized, "Not Authorized")
                        }
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.BadRequest, e.message.toString())
                    }
                }
            }
        }
    }
}
