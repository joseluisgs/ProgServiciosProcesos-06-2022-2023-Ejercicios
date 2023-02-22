package com.example.routes

import com.example.dto.DepartamentoDTO
import com.example.models.User
import com.example.services.departamento.DepartamentoService
import com.example.services.users.UserServiceImpl
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
import resa.mario.mappers.toDTO
import resa.mario.mappers.toDepartamento
import java.util.*


private const val END_POINT = "api/departamentos"

fun Application.departamentosRoutes() {

    val departamentoService: DepartamentoService by inject()

    val usuarioService: UserServiceImpl by inject()

    routing {
        route("/$END_POINT") {
            get({
                description = "Todos los departamentos"
                response {
                    HttpStatusCode.OK to {
                        description = "Lista de departamentos"
                        body<List<DepartamentoDTO>> { description = "Lista de departamentos" }
                    }
                }
            }
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

            // GetById
            get("{id}", {
                description = "FindById"
                request {
                    pathParameter<String>("id") {
                        description = "Id del departamento"
                        required = true
                    }
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Id correcto"
                        body<DepartamentoDTO> { description = "DepartamentoDTO solicitado" }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Id incorrecto"
                        body<String> { description = "Excepcion" }
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

            // Post Departamento

            post({
                description = "Post departamento"
                request {
                    body<DepartamentoDTO> {
                        description = "DepartamentoDTO"
                    }
                }
                response {
                    HttpStatusCode.Created to {
                        description = "Departamento creado"
                        body<DepartamentoDTO> { description = "DepartamentoDTO creado" }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Algun campo incorrecto"
                        body<String> { description = "Excepcion" }
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
                        description = "DepartamentoDTO actualizado"
                        body<DepartamentoDTO> { description = "DepartamentoDTO actualizado" }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Algun campo incorrecto"
                        body<String> { description = "Excepcion" }
                    }
                }
            }
            ) {
                try {
                    val id = call.parameters["id"]!!
                    val request = call.receive<DepartamentoDTO>()
                    val departamento = departamentoService.update( request.toDepartamento())
                    call.respond(HttpStatusCode.OK, departamento.toDTO())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            authenticate {
                delete("{id}", {
                    description = "DeleteById departamento"
                    securitySchemeName = "JWT-Auth"
                    request {
                        pathParameter<String>("id") {
                            description = "Id del departamento"
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
                            description = "Departamento no encontrado"
                            body<String> { description = "Excepcion" }
                        }
                    }
                }
                ) {
                    try { val id = call.parameters["id"]!!
                        val token = call.principal<JWTPrincipal>()

                        val userId = token?.payload?.getClaim("userId").toString().replace("\"", "")
                        val user = usuarioService.findById(UUID.fromString(userId))

                        user.let {
                            if (user.type == User.Type.ADMIN.name) {
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