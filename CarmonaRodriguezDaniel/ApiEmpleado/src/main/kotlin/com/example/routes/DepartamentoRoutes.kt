package com.example.routes

import com.example.dto.DepartamentoCreateDto
import com.example.dto.DepartamentoUpdateDto
import com.example.exceptions.DepartamentoBadRequestException
import com.example.exceptions.DepartamentoNotFoundException
import com.example.mappers.toDepartamento
import com.example.models.Departamento
import com.example.repositories.departamentos.DepartamentoRepository
import com.example.repositories.empleados.EmpleadoRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.util.pipeline.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.toList
import org.koin.ktor.ext.inject

private const val ENDPOINT = "/departamentos"

fun Application.departamentoRoutes() {
    val repository: DepartamentoRepository by inject()
    val empleados: EmpleadoRepository by inject()
    routing {
        route(ENDPOINT) {
            get {
                val departamentos = mutableListOf<Departamento>()

                repository.findAll().collect {
                    departamentos.add(it)
                }
                call.respond(HttpStatusCode.OK, departamentos)
            }
            get("/{id}") {
                try {
                    val id = call.parameters["id"]!!.toLong()
                    val result = repository.findById(id)
                    result?.let {
                        call.respond(HttpStatusCode.OK, it)
                    } ?: call.respond(HttpStatusCode.NotFound, "No existe el departamento con id: $id")
                } catch (e: NumberFormatException) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }
            get("/{id}/empleados") {
                try {
                    val id = call.parameters["id"]!!.toLong()
                    val result = repository.findById(id)
                    result?.let { departamento ->
                        val empleados = empleados.findAll().toList().filter { it.departamento == departamento.id }
                        call.respond(HttpStatusCode.OK, empleados)
                    } ?: call.respond(HttpStatusCode.NotFound, "No existe el departamento con id: $id")
                } catch (e: NumberFormatException) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }
            authenticate {
                post {
                    try {
                        validateRol()
                        val departamento = call.receive<DepartamentoCreateDto>()
                        val res = repository.save(departamento.toDepartamento())
                        call.respond(HttpStatusCode.Created, res)
                    } catch (e: DepartamentoBadRequestException) {
                        call.respond(HttpStatusCode.BadRequest, e.message.toString())
                    } catch (e: RequestValidationException) {
                        call.respond(HttpStatusCode.BadRequest, e.reasons)
                    }
                }
                put("/{id}") {
                    try {
                        validateRol()
                        val id = call.parameters["id"]!!.toLong()
                        val departamento = call.receive<DepartamentoUpdateDto>()
                        val result = repository.update(id, departamento.toDepartamento())
                        call.respond(HttpStatusCode.OK, result)
                    } catch (e: NumberFormatException) {
                        call.respond(HttpStatusCode.BadRequest, e.message.toString())
                    } catch (e: DepartamentoNotFoundException) {
                        call.respond(HttpStatusCode.NotFound, e.message.toString())
                    } catch (e: RequestValidationException) {
                        call.respond(HttpStatusCode.BadRequest, e.reasons)
                    }
                }
            }
        }
        webSocket("/updates/departamentos") {
            try {
                repository.addSuscriptor(this.hashCode()) {
                    sendSerialized(it)
                }
                sendSerialized("Updates Web Socket: Departamento")
                for (frame in incoming) {
                    if (frame.frameType == FrameType.CLOSE) {
                        break
                    } else if (frame is Frame.Text) {
                        println("Mensaje recibido: ${frame.readText()}")
                    }
                }
            } finally {
                repository.removeSuscriptor(this.hashCode())
            }
        }
    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.validateRol() {
    val jwt = call.principal<JWTPrincipal>()
    val rol = jwt?.payload?.getClaim("rol").toString().replace("\"", "")
    if (rol != "ADMIN") {
        call.respond(HttpStatusCode.Forbidden, "No tienes permisos para realizar la acción.")
    }
}