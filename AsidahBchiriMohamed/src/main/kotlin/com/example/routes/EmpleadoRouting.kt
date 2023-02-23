package com.example.routes

import com.example.controllers.EmpleadoController
import com.example.dtos.*
import com.example.exceptions.EmpleadoNotFound
import com.example.models.toModel
import com.example.plugins.endpoint
import com.example.services.TokenService
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


fun Application.empleadoRoutes() {
    val empleadoService: EmpleadoController by inject()
    val tokenService: TokenService by inject()
    routing {
        route("$endpoint/empleados") {

            get("/hola") { call.respondText("Hola empleados") }
            post("/register") {
                try {
                    val dto = call.receive<EmpleadoCreateDto>()
                    val empleado = empleadoService.save(dto.toModel())
                    call.respond(HttpStatusCode.Created, empleado!!.toDto())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            post("/login") {
                try {
                    val dto = call.receive<EmpleadoLoginDto>()
                    val empleado = empleadoService.findByEmail(dto.email)
                    empleado?.let {
                        val token = tokenService.generateJWT(empleado)
                        call.respond(HttpStatusCode.OK, token)
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message!!)
                }
            }
            get {
                empleadoService.findAll().toList().let { res -> call.respond(HttpStatusCode.OK, res) }
            }

            get("{id}") {
                try {
                    val id = call.parameters["id"]!!
                    val res = empleadoService.findById(id)
                    call.respond(HttpStatusCode.OK, res)
                } catch (e: EmpleadoNotFound) {
                    call.respond(HttpStatusCode.NotFound, e.message!!)
                }

            }

            get("{email}") {
                val email = call.parameters["email"]
                empleadoService.findByEmail(email!!).let { call.respond(HttpStatusCode.OK, it!!) }
            }
            authenticate {
                delete("{id}") {
                    try {
                        val jwt = call.principal<JWTPrincipal>()
                        println(jwt.toString())
                        val email = jwt?.payload?.getClaim("email").toString().replace("\"", "")
                        println(email)
                        val empleado = empleadoService.findByEmail(email)
                        empleado.let {
                            val id = call.parameters["id"]
                            id?.let {
                                empleadoService.delete(UUID.fromString(id))
                                call.respond(HttpStatusCode.NoContent)
                            }
                        }

                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.Unauthorized, e.message!!)
                    }
                }
                post {
                    try {
                        val jwt = call.principal<JWTPrincipal>()
                        println(jwt.toString())
                        val rol = jwt?.payload?.getClaim("rol").toString().replace("\"", "")
                        println(rol)
                        if (rol == "ADMIN") {
                            val dto = call.receive<EmpleadoCreateDto>()
                            val empleado = empleadoService.save(dto.toModel())
                            call.respond(HttpStatusCode.Created)
                        } else
                            call.respond(HttpStatusCode.Unauthorized)

                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.BadRequest)
                    }
                }
            }


            patch("{id}") {
                try {
                    val dto = call.receive<EmpleadoPatchDto>()
                    val id = call.parameters["id"]!!
                    val empleado = empleadoService.findById(id)
                    empleado?.let {
                        empleadoService.patchEmpleado(empleado, dto)?.let {
                            call.respond(HttpStatusCode.OK)
                        }
                    } ?: run {
                        call.respond(HttpStatusCode.NotFound)
                    }
                } catch (e: Exception) {
                    call.respondText(e.message!!)
                }
            }
        }
    }
}