package com.ktordam.routes

import com.ktordam.dto.UsuarioDTO
import com.ktordam.dto.UsuarioLoginDTO
import com.ktordam.dto.UsuarioRegisterDTO
import com.ktordam.dto.UsuarioTokenDTO
import com.ktordam.exceptions.UsuariosBadRequestException
import com.ktordam.exceptions.UsuariosUnauthorizedException
import com.ktordam.mappers.toDTO
import com.ktordam.mappers.toModel
import com.ktordam.models.Usuario
import com.ktordam.services.token.TokenService
import com.ktordam.services.usuarios.UsuariosService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.toList
import org.koin.ktor.ext.inject
import java.util.*

private const val ENDPOINT = "api/usuarios"

/**
 * Función para implementar las rutas de Usuarios.
 */
fun Application.usuariosRoutes() {
    val usuariosService: UsuariosService by inject()
    val tokenService: TokenService by inject()

    routing {
        route("/$ENDPOINT") {
            post("/register") {
                try {
                    val dto = call.receive<UsuarioRegisterDTO>()
                    usuariosService.save(dto.toModel())

                    call.respond(HttpStatusCode.Created, dto)
                }  catch (e: UsuariosBadRequestException) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            post("/login") {
                try {
                    val dto = call.receive<UsuarioLoginDTO>()
                    val usuario = usuariosService.login(dto.username, dto.password)

                    usuario?.let {
                        val token = tokenService.generateJWT(usuario)
                        call.respond(HttpStatusCode.OK, UsuarioTokenDTO(usuario.toDTO(), token))
                    }
                } catch (e: UsuariosUnauthorizedException) {
                    call.respond(HttpStatusCode.Unauthorized, e.message.toString())
                } catch (e: UsuariosBadRequestException) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            authenticate {
                get("/me") {
                    try {
                        val token = call.principal<JWTPrincipal>()
                        val id = token?.payload?.getClaim("userId").toString().replace("\"", "")
                        val usuario = usuariosService.findByUUID(UUID.fromString(id))

                        usuario.let {
                            call.respond(HttpStatusCode.OK, usuario.toDTO())
                        }
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.BadRequest, e.message.toString())
                    }
                }

                get("/list") {
                    try {
                        val token = call.principal<JWTPrincipal>()

                        val id = token?.payload?.getClaim("userId").toString().replace("\"", "")
                        val usuario = usuariosService.findByUUID(UUID.fromString(id))

                        usuario.let {
                            if (usuario.role == Usuario.Role.ADMIN.name) {
                                val res = mutableListOf<UsuarioDTO>()
                                usuariosService.findAll().toList().forEach { res.add(it.toDTO()) }

                                call.respond(HttpStatusCode.OK, res)
                            } else {
                                call.respond(HttpStatusCode.Unauthorized, "No autorizado")
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