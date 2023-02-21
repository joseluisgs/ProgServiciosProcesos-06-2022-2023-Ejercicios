package resa.mario.routes

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
import resa.mario.dto.DepartamentoDTO
import resa.mario.mappers.toDTO
import resa.mario.mappers.toDepartamento
import resa.mario.models.Usuario
import resa.mario.services.departamento.DepartamentoServiceImpl
import resa.mario.services.usuario.UsuarioServiceImpl
import java.util.*

private const val END_POINT = "api/departamentos"

fun Application.departamentosRoutes() {

    val departamentoService: DepartamentoServiceImpl by inject()

    val usuarioService: UsuarioServiceImpl by inject()

    routing {
        route("/$END_POINT") {
            // Get All
            // Se aplica Swagger
            get({
                description = "Get all departamentos"
                response {
                    HttpStatusCode.OK to {
                        description = "Lista de departamentosDTO"
                        body<List<DepartamentoDTO>> { description = "Lista de departamentosDTO" }
                    }
                }
            }
                // Se aplican las operaciones request-response reales
            ) {
                val result = mutableListOf<DepartamentoDTO>()

                // Procesamos el flujo
                departamentoService.findAll().collect {
                    result.add(it.toDTO())
                }

                // Para poder ver los id
                departamentoService.findAll().collect {
                    println(it)
                }

                call.respond(HttpStatusCode.OK, result)
            }

            // Get by Id /endpoint/id
            get("{id}", {
                description = "Get by id departamento"
                request {
                    pathParameter<String>("id") {
                        description = "Id del departamento que nos interese encontrar"
                        required = true
                    }
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Id correcto"
                        body<DepartamentoDTO> { description = "DepartamentoDTO solicitado" }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Id incorrecto, o no se encontro el departamento"
                        body<String> { description = "Mensaje con la excepcion correspondiente" }
                    }
                }
            }
            ) {
                try {
                    val id = call.parameters["id"]!!
                    val departamento = departamentoService.findById(UUID.fromString(id))

                    call.respond(HttpStatusCode.OK, departamento.toDTO())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            // Post /endpoint
            /*
             * EN THUNDERCLIENT, HEADERS -> Content-Type __ application/json
             * Luego en BODY -> JSON
            */
            post({
                description = "Post departamento"
                request {
                    body<DepartamentoDTO> {
                        description = "Se recibe un DepartamentoDTO"
                    }
                }
                response {
                    HttpStatusCode.Created to {
                        description = "Departamento creado correctamente"
                        body<DepartamentoDTO> { description = "DepartamentoDTO creado" }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Algun campo incorrecto"
                        body<String> { description = "Mensaje con la excepcion correspondiente" }
                    }
                }
            }
            ) {
                try {
                    val departamentoReceive = call.receive<DepartamentoDTO>()
                    val departamentoSave = departamentoService.save(departamentoReceive.toDepartamento())
                    call.respond(
                        HttpStatusCode.Created, departamentoService.findById(departamentoSave.id).toDTO()
                    )
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            put("{id}", {
                description = "Put departamento"
                request {
                    pathParameter<String>("id") {
                        description = "Id del departamento"
                        required = true
                    }
                    body<DepartamentoDTO> {
                        description = "Se recibe un DepartamentoDTO"
                    }
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Departamento actualizado"
                        body<DepartamentoDTO> { description = "DepartamentoDTO actualizado" }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Algun campo incorrecto, departamento no localizado"
                        body<String> { description = "Mensaje con la excepcion correspondiente" }
                    }
                }
            }
            ) {
                try {
                    val id = call.parameters["id"]!!
                    val request = call.receive<DepartamentoDTO>()
                    val departamento = departamentoService.update(UUID.fromString(id), request.toDepartamento())
                    call.respond(HttpStatusCode.OK, departamento.toDTO())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            // Enunciado, se debe proteger esta ruta con el rol de ADMIN
            authenticate {
                delete("{id}", {
                    description = "Delete by id departamento"
                    securitySchemeName = "JWT-Auth"
                    request {
                        pathParameter<String>("id") {
                            description = "Id del departamento a eliminar"
                            required = true
                        }
                    }

                    response {
                        HttpStatusCode.NoContent to {
                            description = "Departamento eliminado con exito"
                        }

                        HttpStatusCode.Unauthorized to {
                            description = "El usuario no tiene permisos para esta operacion"
                            body<String> { description = "Not Authorized" }
                        }

                        HttpStatusCode.BadRequest to {
                            description = "Departamento no encontrado, o el departamento tiene al menos 1 empleado"
                            body<String> { description = "Mensaje con la excepcion correspondiente" }
                        }
                    }
                }
                ) {
                    try {
                        // Obtenemos tanto el id por parametro como el token, y verificamos al usuario con un claim
                        val id = call.parameters["id"]!!
                        val token = call.principal<JWTPrincipal>()

                        val userId = token?.payload?.getClaim("userId").toString().replace("\"", "")
                        val user = usuarioService.findById(UUID.fromString(userId))

                        user.let {
                            if (user.role == Usuario.Role.ADMIN.name) {
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
