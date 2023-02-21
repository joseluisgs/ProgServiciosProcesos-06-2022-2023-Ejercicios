package com.ktordam.routes

import com.ktordam.exceptions.DepartamentoBadRequestException
import com.ktordam.exceptions.DepartamentoNotFoundException
import com.ktordam.models.Departamento
import com.ktordam.services.departamentos.DepartamentosService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

private const val ENDPOINT = "api/departamentos"

/**
 * Función para implementar las rutas de Departamentos.
 */
fun Application.departamentosRoutes() {
    val departamentosService: DepartamentosService by inject()

    routing {
        route("/$ENDPOINT") {
            get {
                val result = mutableListOf<Departamento>()

                departamentosService.findAll().collect {
                    result.add(it)
                }

                call.respond(HttpStatusCode.OK, result)
            }

            get("{id}") {
                try {
                    val id = call.parameters["id"]?.toInt()
                    val departamento = id?.let { departamentosService.findById(id) }

                    call.respond(HttpStatusCode.OK, departamento.toString())
                } catch (e: DepartamentoNotFoundException) {
                    call.respond(HttpStatusCode.NotFound, e.message.toString())
                }
            }

            post {
                try {
                    val received = call.receive<Departamento>()
                    val saved = departamentosService.save(received)

                    call.respond(HttpStatusCode.Created, departamentosService.findById(saved.id).toString())
                } catch (e: DepartamentoBadRequestException) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            put("{id}") {
                try {
                    val id = call.parameters["id"]?.toInt()
                    val request = call.receive<Departamento>()
                    val departamento = id?.let { departamentosService.update(id, request) }

                    call.respond(HttpStatusCode.OK, departamento.toString())
                } catch (e: DepartamentoNotFoundException) {
                    call.respond(HttpStatusCode.NotFound, e.message.toString())
                }
            }

            delete("{id}") {
                try {
                    val id = call.parameters["id"]?.toInt()!!

                    departamentosService.delete(id)
                    call.respond(HttpStatusCode.NoContent)
                } catch (e: DepartamentoNotFoundException) {
                    call.respond(HttpStatusCode.NotFound, e.message.toString())
                }
            }
        }
    }
}