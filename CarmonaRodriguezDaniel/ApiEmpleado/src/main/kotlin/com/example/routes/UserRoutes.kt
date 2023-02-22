package com.example.routes

import com.example.dto.UserLoginDto
import com.example.models.User
import com.example.services.TokenService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.mindrot.jbcrypt.BCrypt

fun Application.userRoutes() {
    val tokenService: TokenService by inject()
    val users = mutableListOf(
        User("dani", BCrypt.hashpw("1234", BCrypt.gensalt(12)), User.Role.USER),
        User("pepito123", BCrypt.hashpw("1234", BCrypt.gensalt(12)), User.Role.ADMIN)
    )
    routing {
        post("/login") {
            try {
                val login = call.receive<UserLoginDto>()
                val user = users.find { it.username.equals(login.username) }
                user?.let {
                    if (BCrypt.checkpw(login.password, it.password)) {
                        val token = tokenService.generateJWT(it)
                        call.respond(HttpStatusCode.OK, token)
                    } else {
                        call.respond(HttpStatusCode.Unauthorized, "El nombre de usuario o contraseña son incorrectos.")
                    }
                }
            } catch (e: RequestValidationException) {
                call.respond(HttpStatusCode.BadRequest, e.reasons)
            }
        }
        post("/register") {
            try {
                val login = call.receive<UserLoginDto>()
                val user = users.find { it.username.equals(login.username) }
                user?.let {
                    call.respond(HttpStatusCode.BadRequest, "Ya existe el nombre de usuario.")
                } ?: kotlin.run {
                    users.add(User(login.username, BCrypt.hashpw(login.password, BCrypt.gensalt(12))))
                    call.respond(HttpStatusCode.Created, "Usuario creado.")
                }
            } catch (e: RequestValidationException) {
                call.respond(HttpStatusCode.BadRequest, e.reasons)
            }
        }
    }
}