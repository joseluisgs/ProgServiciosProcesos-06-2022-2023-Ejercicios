package resa.mario.routes

import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.post
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.toList
import org.koin.ktor.ext.inject
import resa.mario.dto.UsuarioDTOLogin
import resa.mario.dto.UsuarioDTORegister
import resa.mario.dto.UsuarioDTOResponse
import resa.mario.mappers.toDTO
import resa.mario.mappers.toUsuario
import resa.mario.models.Usuario
import resa.mario.services.TokensService
import resa.mario.services.usuario.UsuarioServiceImpl
import java.util.*

private const val END_POINT = "api/usuarios"

fun Application.usuariosRoutes() {
    val usuarioService: UsuarioServiceImpl by inject()

    val tokensService: TokensService by inject()

    routing {
        route("/$END_POINT") {

            // Ruta para el registro
            post("/register", {
                description = "Register usuario"
                request {
                    body<UsuarioDTORegister> {
                        description = "Se recibe un UsuarioDTORegister"
                    }
                }
                response {
                    HttpStatusCode.Created to {
                        description = "Usuario creado correctamente"
                        body<UsuarioDTOResponse> { description = "UsuarioDTOResponse creado" }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Algun campo incorrecto"
                        body<String> { description = "Mensaje con la excepcion correspondiente" }
                    }
                }
            }) {
                try {
                    val dto = call.receive<UsuarioDTORegister>()
                    val user = usuarioService.save(dto.toUsuario())
                    call.respond(HttpStatusCode.Created, user.toDTO())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            // Ruta para el login
            post("/login", {
                description = "Login usuario"
                request {
                    body<UsuarioDTOLogin> {
                        description = "Se recibe un UsuarioDTOLogin"
                    }
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Usuario encontrado, se genero un token personal"
                        body<String> { description = "Token creado" }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Algun campo incorrecto, usuario no encontrado"
                        body<String> { description = "Mensaje con la excepcion correspondiente" }
                    }
                }
            }) {
                try {
                    val dto = call.receive<UsuarioDTOLogin>()
                    val user = usuarioService.login(dto.username, dto.password)

                    user?.let {
                        val token = tokensService.generateJWT(user)
                        call.respond(HttpStatusCode.OK, token)
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            authenticate {
                // Get /me
                get("/me", {
                    description = "Self Information [Get me]"
                    securitySchemeName = "JWT-Auth"

                    response {
                        HttpStatusCode.OK to {
                            description = "Usuario verificado"
                            body<UsuarioDTOResponse> { description = "UsuarioDTOResponse correspondiente" }
                        }
                        HttpStatusCode.BadRequest to {
                            description = "Token invalido"
                            body<String> { description = "Mensaje con la excepcion correspondiente" }
                        }
                    }
                }) {
                    try {
                        val token = call.principal<JWTPrincipal>()
                        // Los claim vienen con comillas
                        val userId = token?.payload?.getClaim("userId").toString().replace("\"", "")
                        val user = usuarioService.findById(UUID.fromString(userId))

                        user.let {
                            call.respond(HttpStatusCode.OK, user.toDTO())
                        }
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.BadRequest, e.message.toString())
                    }
                }

                // Get users -> SOLO ADMIN
                get("/list", {
                    description = "Listado de usuarios"
                    securitySchemeName = "JWT-Auth"

                    response {
                        HttpStatusCode.OK to {
                            description = "Usuario validado (rol ADMIN) con JWT, listado de usuarioDTOResponse"
                            body<List<UsuarioDTOResponse>> { description = "Lista de UsuariosDTOResponse" }
                        }
                        HttpStatusCode.Unauthorized to {
                            description = "Usuario no validado con JWT, rol insuficiente"
                            body<String> { description = "Mensaje generico de no autorizado" }
                        }
                        HttpStatusCode.BadRequest to {
                            description = "Usuario no validado con JWT, token caducado"
                            body<String> { description = "Mensaje con la excepcion correspondiente" }
                        }
                    }
                }) {
                    try {
                        val token = call.principal<JWTPrincipal>()

                        val userId = token?.payload?.getClaim("userId").toString().replace("\"", "")
                        val user = usuarioService.findById(UUID.fromString(userId))

                        user.let {
                            if (user.role == Usuario.Role.ADMIN.name) {
                                val res = mutableListOf<UsuarioDTOResponse>()
                                usuarioService.findAll().toList().forEach { res.add(it.toDTO()) }

                                call.respond(HttpStatusCode.OK, res)
                            } else {
                                call.respond(HttpStatusCode.Unauthorized, "Not Authorized")
                            }
                        }
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.BadRequest, e.message.toString())
                    }
                }
            }
        }
    }
}