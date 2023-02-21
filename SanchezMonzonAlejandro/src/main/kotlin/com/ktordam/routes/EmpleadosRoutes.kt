package com.ktordam.routes

import com.ktordam.exceptions.EmpleadoBadRequestException
import com.ktordam.exceptions.EmpleadoNotFoundException
import com.ktordam.models.Empleado
import com.ktordam.services.empleados.EmpleadosService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

private const val ENDPOINT = "api/empleados"

/**
 * Función para implementar las rutas de Empleados.
 */
fun Application.empleadosRoutes() {
    val empleadosService: EmpleadosService by inject()

    routing {
        route("/$ENDPOINT") {
            get {
                val result = mutableListOf<Empleado>()

                empleadosService.findAll().collect {
                    result.add(it)
                }

                call.respond(HttpStatusCode.OK, result)
            }

            get("{id}") {
                try {
                    val id = call.parameters["id"]?.toInt()
                    val empleado = id?.let { empleadosService.findById(id) }

                    call.respond(HttpStatusCode.OK, empleado.toString())
                } catch (e: EmpleadoNotFoundException) {
                    call.respond(HttpStatusCode.NotFound, e.message.toString())
                }
            }

            post {
                try {
                    val received = call.receive<Empleado>()
                    val saved = empleadosService.save(received)

                    call.respond(HttpStatusCode.Created, empleadosService.findById(saved.id).toString())
                } catch (e: EmpleadoBadRequestException) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            put("{id}") {
                try {
                    val id = call.parameters["id"]?.toInt()
                    val request = call.receive<Empleado>()
                    val empleado = id?.let { empleadosService.update(id, request) }

                    call.respond(HttpStatusCode.OK, empleado.toString())
                } catch (e: EmpleadoNotFoundException) {
                    call.respond(HttpStatusCode.NotFound, e.message.toString())
                }
            }

            delete("{id}") {
                try {
                    val id = call.parameters["id"]?.toInt()!!

                    empleadosService.delete(id)
                    call.respond(HttpStatusCode.NoContent)
                } catch (e: EmpleadoNotFoundException) {
                    call.respond(HttpStatusCode.NotFound, e.message.toString())
                }
            }
        }
    }
}