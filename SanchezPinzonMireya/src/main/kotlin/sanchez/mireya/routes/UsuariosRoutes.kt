package sanchez.mireya.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.toList
import org.koin.ktor.ext.inject
import sanchez.mireya.dto.UsuarioLoginDTO
import sanchez.mireya.dto.UsuarioRegisterDTO
import sanchez.mireya.dto.UsuarioDTO
import sanchez.mireya.mappers.toDTO
import sanchez.mireya.mappers.toUsuario
import sanchez.mireya.models.Role
import sanchez.mireya.services.TokenService
import sanchez.mireya.services.usuario.UsuarioServiceImpl
import java.util.*

private const val ENDPOINT = "api/usuarios"

fun Application.usuariosRoutes() {
    val usuarioService: UsuarioServiceImpl by inject()
    val tokensService: TokenService by inject()

    routing {
        route("/$ENDPOINT") {

            post("/register") {
                try {
                    val dto = call.receive<UsuarioRegisterDTO>()
                    val user = usuarioService.save(dto.toUsuario())
                    call.respond(HttpStatusCode.Created, user.toDTO())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            post("/login") {
                try {
                    val dto = call.receive<UsuarioLoginDTO>()
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
                get("/me") {
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

                get("/list") {
                    try {
                        val token = call.principal<JWTPrincipal>()

                        val userId = token?.payload?.getClaim("userId").toString().replace("\"", "")
                        val user = usuarioService.findById(UUID.fromString(userId))

                        user.let {
                            if (user.role == Role.ADMIN.name) {
                                val res = mutableListOf<UsuarioDTO>()
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