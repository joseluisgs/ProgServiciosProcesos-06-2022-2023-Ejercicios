package com.example.routing

import com.example.dto.LoginDto
import com.example.repositories.empleado.EmpleadoRepositoryImpl
import com.example.repositories.usuario.UsuarioRepositoryImpl
import com.example.services.password.BcryptService
import com.example.services.token.TokenService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.loginRoutes(){
    val empleados: EmpleadoRepositoryImpl by inject()
    val usuarios: UsuarioRepositoryImpl by inject()
    val bcryptService: BcryptService by inject()
    val tokenService: TokenService by inject()

    routing {
        post("/login/empleados"){
            val login = call.receive<LoginDto>()
            val find = empleados.findEmpleadoByEmail(login.email)
            find?.let {
                if (bcryptService.verifyPassword(login.password, it.password)){
                    val token = tokenService.generarTokenEmpleado(it)
                    call.respond(HttpStatusCode.OK, token)
                }else{
                    call.respond(HttpStatusCode.NotFound,"Email y/o contraseña incorrecto")
                }
            } ?: run{
                call.respond(HttpStatusCode.NotFound,"Email y/o contraseña incorrecto")
            }
        }

        post("/login/usuarios"){
            val login = call.receive<LoginDto>()
            val find = usuarios.findByEmail(login.email)
            find?.let {
                if (bcryptService.verifyPassword(login.password, it.password)){
                    val token = tokenService.generarToken(it)
                    call.respond(HttpStatusCode.OK, token)
                }else{
                    call.respond(HttpStatusCode.NotFound,"Email y/o contraseña incorrecto")
                }
            } ?: run{
                call.respond(HttpStatusCode.NotFound,"Email y/o contraseña incorrecto")
            }
        }

    }
}