package com.example.routes

import com.example.dto.UserLoginDTO
import com.example.dto.UserRegisterDTO
import com.example.dto.UserResponseDTO
import com.example.models.User
import com.example.services.tokens.TokensService
import com.example.services.users.UserServiceImpl
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
import resa.mario.mappers.toDTO
import resa.mario.mappers.toUsuario
import java.util.*

private const val ENDPOINNT = "api/users"

fun Application.usersRoutes() {

    val usuarioService: UserServiceImpl by inject()

    val tokensService: TokensService by inject()


    routing {
        route("/$ENDPOINNT") {


            //Registro
            post("/register", {
                description = "Registrar usuario"
                request {
                    body<UserRegisterDTO> {
                        description = "Se recibe un UsuarioRegisterDto"
                    }
                }
                response {
                    HttpStatusCode.Created to {
                        description = "Usuario creado correctamente"
                        body<UserRegisterDTO> { description = "UsuarioResponseDTO creado" }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Algun campo incorrecto"
                        body<String> { description = "Incorrecto" }
                    }
                }
            }) {
                try {
                    val dto = call.receive<UserRegisterDTO>()
                    val user = usuarioService.save(dto.toUsuario())
                    call.respond(HttpStatusCode.Created, user.toDTO())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }




            //Login
            post("/login", {
                description = "Login usuario"
                request {
                    body<UserLoginDTO> {
                        description = "Se recibe un Usuario Login"
                    }
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Usuario encontrado, se genera un token"
                        body<String> { description = "Token creado" }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Algun campo incorrecto, usuario no encontrado"
                        body<String> { description = "Excepcion" }
                    }
                }
            }) {
                try {
                    val dto = call.receive<UserLoginDTO>()
                    val user = usuarioService.login(dto.username, dto.password)

                    user?.let {
                        val token = tokensService.generateJWT(user)
                        call.respond(HttpStatusCode.OK, token)
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Excepcion")
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
                            body<UserResponseDTO> { description = "UsuarioDTOResponse correspondiente" }
                        }
                        HttpStatusCode.BadRequest to {
                            description = "Token invalido"
                            body<String> { description = "Mensaje con la excepcion correspondiente" }
                        }
                    }
                }) {
                    try {
                        val token = call.principal<JWTPrincipal>()
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
                    description = "Listado de user"
                    securitySchemeName = "JWT-Auth"

                    response {
                        HttpStatusCode.OK to {
                            description = "User validado"
                            body<List<UserResponseDTO>> { description = "Lista de Users" }
                        }
                        HttpStatusCode.Unauthorized to {
                            description = "User no validado"
                            body<String> { description = "No autorizadoo" }
                        }
                        HttpStatusCode.BadRequest to {
                            description = "User no validado"
                            body<String> { description = "No autorizado" }
                        }
                    }
                }) {
                    try {
                        val token = call.principal<JWTPrincipal>()

                        val userId = token?.payload?.getClaim("userId").toString().replace("\"", "")
                        val user = usuarioService.findById(UUID.fromString(userId))

                        user.let {
                            if (user.type == User.Type.ADMIN.name) {
                                val res = mutableListOf<UserResponseDTO>()
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