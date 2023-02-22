package com.example.routes

import com.example.controllers.DepartamentoController
import com.example.dtos.DepartamentoCreateDto
import com.example.dtos.DepartamentoPatchDto
import com.example.dtos.toModel
import com.example.plugins.endpoint
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.toList
import org.koin.ktor.ext.inject


fun Application.departamentoRoutes() {
    val departamentoService: DepartamentoController by inject()
    routing {
        route("$endpoint/departamentos") {
            get("/hola") {
                call.respondText("Hola departamentos")
            }
            get {
                try {
                    val res = departamentoService.findAll().toList()
                    call.respond(HttpStatusCode.OK, res)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            get("{id}") {
                try {
                    val id = call.parameters["id"]!!
                    val res = departamentoService.findById(id)
                    call.respond(HttpStatusCode.OK, res)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }
            authenticate {
                post {
                    try {
                        val jwt = call.principal<JWTPrincipal>()
                        println(jwt.toString())
                        val rol = jwt?.payload?.getClaim("rol").toString().replace("\"", "")
                        println(rol)
                        if (rol == "ADMIN") {
                            val dto = call.receive<DepartamentoCreateDto>()
                            val departamento = departamentoService.save(dto.toModel())
                            call.respond(HttpStatusCode.Created)
                        } else
                            call.respond(HttpStatusCode.Unauthorized)
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.BadRequest, e.message.toString())
                    }
                }

                patch {
                    val jwt = call.principal<JWTPrincipal>()
                    println(jwt.toString())
                    val rol = jwt?.payload?.getClaim("rol").toString().replace("\"", "")
                    println(rol)
                    if (rol == "ADMIN") {
                        val dto = call.receive<DepartamentoPatchDto>()
                        val departamento = departamentoService.addEmpleado(dto)
                        call.respond(HttpStatusCode.Created)
                    } else
                        call.respond(HttpStatusCode.Unauthorized)
                }
            }

        }
    }
}
