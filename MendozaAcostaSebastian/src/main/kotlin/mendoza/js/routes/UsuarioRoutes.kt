package mendoza.js.routes

import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.post
import io.github.smiley4.ktorswaggerui.dsl.put
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import mendoza.js.dto.*
import mendoza.js.exceptions.UserBadRequestException
import mendoza.js.exceptions.UserNotFoundException
import mendoza.js.exceptions.UserUnauthorizedException
import mendoza.js.mappers.toDto
import mendoza.js.mappers.toModel
import mendoza.js.models.User
import mendoza.js.service.tokens.TokensService
import mendoza.js.service.users.UsersService
import mendoza.js.utils.toUUID
import mu.KotlinLogging
import org.koin.ktor.ext.inject

private val logger = KotlinLogging.logger { }

private const val ENDPOINT = "api/users"
fun Application.usersRoutes() {

    val usersService: UsersService by inject()
    val tokenService: TokensService by inject()

    routing {
        route("/$ENDPOINT") {
            post("/register", {
                description = "Post Register: Registrar un nuevo usuario"
                request {
                    body<UserCreateDto> {
                        description = "Usuario a agregar"
                    }
                }
                response {
                    default {
                        description = "Usuario a agregar"
                    }
                    HttpStatusCode.Created to {
                        description = "Usuario añadido"
                        body<UserDto> { description = "Nuevo usuario añadido" }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "No se ha podido agregar el usuario"
                        body<String> { description = "No se ha podido agregar el usuario" }
                    }
                }
            }) {
                logger.debug { "POST Register /$ENDPOINT/register" }
                try {
                    val dto = call.receive<UserCreateDto>()
                    val user = usersService.save(dto.toModel())
                    call.respond(HttpStatusCode.Created, user.toDto())
                } catch (e: UserBadRequestException) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                } catch (e: RequestValidationException) {
                    call.respond(HttpStatusCode.BadRequest, e.reasons)
                }
            }

            post("/login", {
                description = "Post Login: Loging de usuario"
                request {
                    body<UserLoginDto> {
                        description = "Usuario para login"
                    }
                }
                response {
                    default {
                        description = "Usuario loggeado"
                    }
                    HttpStatusCode.OK to {
                        description = "Usuario loggeado"
                        body<UserWithTokenDto> { description = "Usuario loggeado y con token" }
                    }
                    HttpStatusCode.Unauthorized to {
                        description = "No se está autorizado"
                        body<String> { description = "No se está autorizado" }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "No se ha podido loggear"
                        body<String> { description = "No se ha podido loggear" }
                    }
                }
            }) {
                logger.debug { "POST Login /$ENDPOINT/login" }
                try {
                    val dto = call.receive<UserLoginDto>()
                    val user = usersService.checkUserNameAndPassword(dto.username, dto.password)
                    user?.let {
                        val token = tokenService.generateJWT(user)
                        call.respond(HttpStatusCode.OK, UserWithTokenDto(user.toDto(), token))
                    }
                } catch (e: UserUnauthorizedException) {
                    call.respond(HttpStatusCode.Unauthorized, e.message.toString())
                } catch (e: UserBadRequestException) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                } catch (e: RequestValidationException) {
                    call.respond(HttpStatusCode.BadRequest, e.reasons)
                }
            }

            authenticate {
                get("/me", {
                    description = "Get me: Información del usuario"
                    request {
                        body<JWTPrincipal> {
                            description = "Token del usuario"
                        }
                    }
                    response {
                        default {
                            description = "Información del usuario"
                        }
                        HttpStatusCode.OK to {
                            description = "Información del usuario"
                            body<UserDto> { description = "Información del usuario" }
                        }
                        HttpStatusCode.Unauthorized to {
                            description = "No tiene autorización"
                            body<String> { description = "No está autorizado" }
                        }
                    }
                }) {
                    logger.debug { "GET Me /$ENDPOINT/me" }
                    try {
                        val jwt = call.principal<JWTPrincipal>()
                        val userId = jwt?.payload?.getClaim("userId")
                            .toString().replace("\"", "")
                        val user = usersService.findById(userId.toUUID())
                        user.let {
                            call.respond(HttpStatusCode.OK, user.toDto())
                        }
                    } catch (e: UserNotFoundException) {
                        call.respond(HttpStatusCode.Unauthorized, "Usuario no encontrado o no autenticado")
                    }
                }

                put("/me", {
                    description = "Put me: Actualizar información de usuario"
                    request {
                        body<JWTPrincipal> {
                            description = "Token de usuario"
                        }
                    }
                    response {
                        default {
                            description = "Usuario a actualizar"
                        }
                        HttpStatusCode.OK to {
                            description = "Usuario actualizado"
                            body<UserDto> { description = "Usuario actualizado" }
                        }
                        HttpStatusCode.Unauthorized to {
                            description = "Usuario no encontrado o no autenticado"
                            body<String> { description = "Usuario no encontrado o no autenticado" }
                        }
                        HttpStatusCode.BadRequest to {
                            description = "Error al actualizar usuario"
                            body<String> { description = "Error al actualizar usuario" }
                        }
                    }
                }) {
                    logger.debug { "PUT Me /$ENDPOINT/me" }
                    try {
                        val jwt = call.principal<JWTPrincipal>()
                        val userId = jwt?.payload?.getClaim("userId")
                            .toString().replace("\"", "").toUUID()
                        val user = usersService.findById(userId)
                        val dto = call.receive<UserUpdateDto>()
                        user.let {
                            var userUpdated = user.copy(
                                nombre = dto.nombre,
                                username = dto.username,
                                email = dto.email,
                            )
                            userUpdated = usersService.update(userId, userUpdated)!!
                            call.respond(HttpStatusCode.OK, userUpdated.toDto())
                        }
                    } catch (e: UserNotFoundException) {
                        call.respond(HttpStatusCode.Unauthorized, "Usuario no encontrado o no autenticado")
                    } catch (e: RequestValidationException) {
                        call.respond(HttpStatusCode.BadRequest, e.reasons)
                    } catch (e: UserBadRequestException) {
                        call.respond(HttpStatusCode.BadRequest, e.message.toString())
                    }
                }

                get("/list", {
                    description = "Get Users: Obtener todos los usuarios"
                    request {
                        body<JWTPrincipal> {
                            description = "Token de usuario"
                        }
                    }
                    response {
                        default {
                            description = "Lista de usuarios"
                        }
                        HttpStatusCode.OK to {
                            body<MutableList<UserDto>> { description = "Lista de usuarios" }
                        }
                        HttpStatusCode.Unauthorized to {
                            description = "Usuarios no encontrados o no hay autorización"
                            body<String> { description = "Error al listar todos los usuarios" }
                        }
                    }
                }) {
                    logger.debug { "GET Users /$ENDPOINT/list" }
                    try {
                        val jwt = call.principal<JWTPrincipal>()
                        val userId = jwt?.payload?.getClaim("userId")
                            .toString().replace("\"", "")
                        val user = usersService.findById(userId.toUUID())
                        user.let {
                            if (user.role == User.Role.ADMIN) {
                                val res = mutableListOf<UserDto>()
                                usersService.findAll(null).collect {
                                    res.add(it.toDto())
                                }
                                call.respond(HttpStatusCode.OK, res)
                            } else {
                                call.respond(
                                    HttpStatusCode.Unauthorized,
                                    "No estas autorizado a realizar esta operación"
                                )
                            }
                        }
                    } catch (e: UserNotFoundException) {
                        call.respond(HttpStatusCode.Unauthorized, "Usuario no encontrado o no autenticado")
                    }
                }
            }
        }
    }
}