package es.ruymi.routes


import es.ruymi.dto.*
import es.ruymi.exceptions.UserBadRequestException
import es.ruymi.exceptions.UserNotFoundException
import es.ruymi.exceptions.UserUnauthorizedException
import es.ruymi.mappers.toDto
import es.ruymi.mappers.toEntity
import es.ruymi.models.User
import es.ruymi.services.storage.StorageService
import es.ruymi.services.tokens.TokensService
import es.ruymi.services.users.UsersService
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import mu.KotlinLogging
import org.koin.ktor.ext.inject
import java.util.*

private val logger = KotlinLogging.logger {}

private const val ENDPOINT = "api/users"

fun Application.usersRoutes() {

    val usersService: UsersService by inject()
    val tokenService: TokensService by inject()
    val storageService: StorageService by inject()

    routing {
        route("/$ENDPOINT") {

            // Post -> /register
            post("/register") {
                logger.debug { "POST Register /$ENDPOINT/register" }
                try {
                    val dto = call.receive<UserCreateDto>()
                    val user = usersService.save(dto.toEntity())
                    call.respond(HttpStatusCode.Created, user.toDto())
                } catch (e: UserBadRequestException) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            // Post -> /login
            post("/login") {
                logger.debug { "POST Login /$ENDPOINT/login" }
                try {
                    val dto = call.receive<UserLoginDto>()
                    val user = usersService.checkUserNameAndPassword(dto.usuario, dto.password)
                    user?.let {
                        val token = tokenService.generateJWT(user)
                        call.respond(HttpStatusCode.OK, UserWithTokenDto(user.toDto(), token))
                    }
                } catch (e: UserUnauthorizedException) {
                    call.respond(HttpStatusCode.Unauthorized, e.message.toString())
                } catch (e: UserBadRequestException) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            // Estas rutas están autenticadas --> Protegidas por JWT
            // datos del usuario
            authenticate {
                get("/me") {
                    logger.debug { "GET Me /$ENDPOINT/me" }
                    try {
                        val jwt = call.principal<JWTPrincipal>()
                        val userId = jwt?.payload?.getClaim("username")
                            .toString().replace("\"", "")
                        val user = usersService.findByUsername(userId)
                        call.respond(HttpStatusCode.OK, user!!.toDto())

                    } catch (e: UserNotFoundException) {
                        call.respond(HttpStatusCode.Unauthorized, "Usuario no encontrado o no autenticado")
                    }
                }

                // Actualizar datos del usuario
                put("/me") {
                    logger.debug { "PUT Me /$ENDPOINT/me" }
                    try {
                        val jwt = call.principal<JWTPrincipal>()
                        val userId = UUID.fromString(jwt?.payload?.getClaim("userId")
                            .toString().replace("\"", ""))
                        val user = usersService.findById(userId)
                        val dto = call.receive<UserUpdateDto>()
                        user.let {
                            var userUpdated = user.copy(
                                correo = dto.correo,
                                usuario = dto.usuario,
                            )
                            userUpdated = usersService.update(userId, userUpdated)!!
                            call.respond(HttpStatusCode.OK, userUpdated.toDto())
                        }
                    } catch (e: UserNotFoundException) {
                        call.respond(HttpStatusCode.Unauthorized, "Usuario no encontrado o no autenticado")
                    }  catch (e: UserBadRequestException) {
                        call.respond(HttpStatusCode.BadRequest, e.message.toString())
                    }
                }

                patch("/me") {
                    logger.debug { "PUT Me /$ENDPOINT/me" }
                    try {
                        val jwt = call.principal<JWTPrincipal>()
                        val userId = jwt?.payload?.getClaim("userId")
                            .toString().replace("\"", "")
                        val user = usersService.findById(UUID.fromString(userId))
                        logger.debug { "Tomando datos multiparte" }
                        var newFileName: String = ""
                        val multipartData = call.receiveMultipart()
                        multipartData.forEachPart { part ->
                            if (part is PartData.FileItem) {
                                val fileName = part.originalFileName as String
                                val fileBytes = part.streamProvider().readBytes()
                                val fileExtension = fileName.substringAfterLast(".")
                                newFileName = "$userId.$fileExtension"
                                val res = storageService.saveFile(newFileName, fileBytes)
                                newFileName = if (call.request.origin.scheme == "https") {
                                    res["secureUrl"].toString()
                                } else {
                                    res["baseUrl"].toString()

                                }
                            }
                            part.dispose()
                        }

                        user.let {
                            call.respond(HttpStatusCode.OK, user.toDto())
                        }
                    } catch (e: UserNotFoundException) {
                        call.respond(HttpStatusCode.Unauthorized, "Usuario no encontrado o no autenticado")
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.BadRequest, e.message.toString())
                    }
                }


                // Get -> /users --> solo si eres admin
                get("/list") {
                    logger.debug { "GET Users /$ENDPOINT/list" }
                    try {
                        val jwt = call.principal<JWTPrincipal>()
                        val userId = jwt?.payload?.getClaim("userId")
                            .toString().replace("\"", "")
                        val user = usersService.findById(UUID.fromString(userId))
                        user.let {
                            if (user.rol == User.Rol.ADMIN) {
                                val res = mutableListOf<UserDTO>()
                                usersService.findAll().collect {
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