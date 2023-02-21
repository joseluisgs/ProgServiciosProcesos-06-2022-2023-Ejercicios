package rodriguez.daniel.routes

import io.github.smiley4.ktorswaggerui.dsl.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import rodriguez.daniel.dto.*
import rodriguez.daniel.exception.UserUnauthorizedException
import rodriguez.daniel.mappers.toDTO
import rodriguez.daniel.model.Role
import rodriguez.daniel.services.tokens.TokenService
import rodriguez.daniel.services.user.UserService
import java.util.*

private const val ENDPOINT = "ejercicioKtor/users"

fun Application.userRoutes() {

    val users: UserService by inject()
    val tokens: TokenService by inject()

    routing {
        route("/$ENDPOINT") {
            post("/register", {
                description = "Register."
                request {
                    body<UserDTOcreacion> {
                        description = "Datos de registro."
                        required = true
                    }
                }
                response {
                    default {
                        description = "Devuelve tu usuario y un token."
                    }
                    HttpStatusCode.Created to {
                        description = "Your information and token."
                        body<UserDTOandToken> { description = "Your information and token." }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Error message from a bad request."
                        body<String> { description = "Error message from a bad request." }
                    }
                }
            }) {
                println("POST Register /$ENDPOINT/register")
                try {
                    val dto = call.receive<UserDTOcreacion>()
                    val user = users.saveUser(dto)
                    val u = users.checkEmailAndPassword(dto.email, dto.password)
                    val token = tokens.generateJWT(u)
                    call.respond(HttpStatusCode.Created, UserDTOandToken(u.toDTO(), token))
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                } catch (e: RequestValidationException) {
                    call.respond(HttpStatusCode.BadRequest, e.reasons)
                }
            }

            get("/login", {
                description = "Login."
                request {
                    body<UserDTOlogin> {
                        description = "Datos de logado."
                        required = true
                    }
                }
                response {
                    default {
                        description = "Devuelve tu usuario y un token."
                    }
                    HttpStatusCode.OK to {
                        description = "Your information and token."
                        body<UserDTOandToken> { description = "Your information and token." }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Error message from a bad request."
                        body<String> { description = "Error message from a bad request." }
                    }
                    HttpStatusCode.Unauthorized to {
                        description = "Error at authenticating."
                        body<String> { description = "Error at authenticating." }
                    }
                }
            }) {
                println("POST Login /$ENDPOINT/login")
                try {
                    val dto = call.receive<UserDTOlogin>()
                    val user = users.checkEmailAndPassword(dto.email, dto.password)
                    val token = tokens.generateJWT(user)
                    call.respond(HttpStatusCode.OK, UserDTOandToken(user.toDTO(), token))
                } catch (e: UserUnauthorizedException) {
                    call.respond(HttpStatusCode.Unauthorized, e.message.toString())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                } catch (e: RequestValidationException) {
                    call.respond(HttpStatusCode.BadRequest, e.reasons)
                }
            }

            authenticate {
                get("/me", {
                    description = "Get your info."
                    request {
                        headerParameter<String>("token") {
                            description = "Token para autenticarte."
                            required = true
                        }
                    }
                    response {
                        default {
                            description = "Devuelve tu usuario."
                        }
                        HttpStatusCode.OK to {
                            description = "Your information."
                            body<UserDTO> { description = "Your information." }
                        }
                        HttpStatusCode.NotFound to {
                            description = "User with id {id} not found."
                            body<String> { description = "User with id {id} not found." }
                        }
                        HttpStatusCode.Unauthorized to {
                            description = "Incorrect token."
                            body<String> { description = "Incorrect token." }
                        }
                    }
                }) {
                    println("GET Me /$ENDPOINT/me")
                    try {
                        val jwt = call.principal<JWTPrincipal>()
                        val userId = jwt?.payload?.getClaim("id")
                            .toString().replace("\"", "")
                        val user = users.findUserById(UUID.fromString(userId.trim()))
                        if (user != null) call.respond(HttpStatusCode.OK, user)
                        else call.respond(HttpStatusCode.NotFound, "User with id $userId not found.")
                    } catch (e: UserUnauthorizedException) {
                        call.respond(HttpStatusCode.Unauthorized, e.message.toString())
                    }
                }

                get("/list", {
                    description = "Get All users."
                    request {
                        headerParameter<String>("token") {
                            description = "Token para autenticarte."
                            required = true
                        }
                    }
                    response {
                        default {
                            description = "Devuelve todos los usuarios."
                        }
                        HttpStatusCode.OK to {
                            description = "List of all users."
                            body<List<UserDTO>> { description = "List of all users." }
                        }
                        HttpStatusCode.Forbidden to {
                            description = "You can't consult all users."
                            body<String> { description = "You can't consult all users." }
                        }
                        HttpStatusCode.Unauthorized to {
                            description = "User with id {id} not found."
                            body<String> { description = "User with id {id} not found." }
                        }
                        HttpStatusCode.NotFound to {
                            description = "There are no users to be found."
                            body<String> { description = "There are no users to be found." }
                        }
                    }
                }) {
                    println("GET Users /$ENDPOINT/list")
                    try {
                        val jwt = call.principal<JWTPrincipal>()
                        val userId = jwt?.payload?.getClaim("id")
                            .toString().replace("\"", "")
                        val user = users.findUserById(UUID.fromString(userId.trim()))
                        if (user != null) {
                            if (user.role == Role.ADMIN) {
                                val res = users.findAllUsers()
                                if (res.isEmpty()) call.respond(HttpStatusCode.NotFound, res)
                                else call.respond(HttpStatusCode.OK, res)
                            } else call.respond(HttpStatusCode.Forbidden, "You can't consult all users.")
                        } else call.respond(HttpStatusCode.Unauthorized, "User with id $userId not found.")
                    } catch (e: UserUnauthorizedException) {
                        call.respond(HttpStatusCode.Unauthorized, e.message.toString())
                    }
                }
            }
        }
    }
}