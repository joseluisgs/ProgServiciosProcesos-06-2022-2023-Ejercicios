package drodriguez.es.routes

import drodriguez.es.dto.UserCreateDto
import drodriguez.es.dto.UserLoginDto
import drodriguez.es.dto.UserResDto
import drodriguez.es.mappers.toDto
import drodriguez.es.mappers.toUser
import drodriguez.es.models.User
import drodriguez.es.services.TokenServices
import drodriguez.es.services.users.UserService
import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.post
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.util.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.coroutines.flow.toList

private const val END_POINT = "users"

fun Application.usuariosRoutes() {
    val usuarioService: UserService by inject()

    val tokensService: TokenServices by inject()

    routing {
        route("/$END_POINT") {
            post("/registro", {
                request {
                    body<UserCreateDto> {
                    }
                }
                response {
                    HttpStatusCode.Created to {
                        description = "User generated correctamente"
                        body<UserResDto> { description = "User to Dto correcto" }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Datos incorrectos"
                    }
                }
            }) {
                try {
                    val dto = call.receive<UserCreateDto>()
                    val user = usuarioService.save(dto.toUser())
                    call.respond(HttpStatusCode.Created, user.toDto())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            post("/login", {
                description = "Login Users"
                request {
                    body<UserLoginDto> {
                    }
                }
                response {
                    HttpStatusCode.OK to {
                        description = "User search generated token correctamente"
                        body<String> { description = "Token created" }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Datos incorrectos"
                    }
                }
            }) {
                try {
                    val dto = call.receive<UserLoginDto>()
                    val user = usuarioService.login(dto.username, dto.password)

                    user?.let {
                        val token = tokensService.createdToken(user)
                        call.respond(HttpStatusCode.OK, token)
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            authenticate {
                get("/my", {
                    description = "Mis datos"
                    securitySchemeName = "JWT-Auth"

                    response {
                        HttpStatusCode.OK to {
                            description = "User verify"
                        }
                        HttpStatusCode.BadRequest to {
                            description = "Token no validado"
                        }
                    }
                }) {
                    try {
                        val token = call.principal<JWTPrincipal>()
                        val userId = token?.payload?.getClaim("userId").toString().replace("\"", "")
                        val user = usuarioService.findById(UUID.fromString(userId))

                        user.let {
                            call.respond(HttpStatusCode.OK, user.toDto())
                        }
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.BadRequest, e.message.toString())
                    }
                }

                // Get users -> SOLO ADMIN
                get("/all", {
                    description = "Listado de usuarios"
                    securitySchemeName = "JWT-Auth"

                    response {
                        HttpStatusCode.OK to {
                            description = "User validate ROL JEFE"
                        }
                        HttpStatusCode.Unauthorized to {
                            description = "User no validate con JWT no tiene permisos suficientes"
                        }
                        HttpStatusCode.BadRequest to {
                            description = "Usuario no validado con JWT, token caducado"
                        }
                    }
                }) {
                    try {
                        val token = call.principal<JWTPrincipal>()

                        val userId = token?.payload?.getClaim("userId").toString().replace("\"", "")
                        val user = usuarioService.findById(UUID.fromString(userId))
                        user.let {
                            if (user.rol == User.Rol.JEFE.name) {
                                val res = mutableListOf<UserResDto>()
                                usuarioService.findAll().toList().forEach { res.add(it.toDto()) }
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